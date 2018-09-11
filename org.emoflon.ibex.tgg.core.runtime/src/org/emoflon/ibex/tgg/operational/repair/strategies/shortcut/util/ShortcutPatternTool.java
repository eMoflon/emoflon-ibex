package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.gen.GENBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.SimpleMatch;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.GreenSCPattern;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.InterfaceSCFactory;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.OperationalShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGCollectionUtil;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.BindingType;
import language.DomainType;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.TGGRuleApplication;

public class ShortcutPatternTool {
	
	private OperationalStrategy strategy;
	private Collection<ShortcutRule> scRules;
	private Map<String, Collection<OperationalShortcutRule>> tggRule2srcSCRule;
	private Map<String, Collection<OperationalShortcutRule>> tggRule2trgSCRule;
	private Map<OperationalShortcutRule, GreenSCPattern> rule2greenPattern;
	private Map<OperationalShortcutRule, LocalPatternSearch> rule2matcher;
	
	private IbexGreenInterpreter greenInterpreter;
	
	public ShortcutPatternTool(OperationalStrategy strategy, Collection<ShortcutRule> scRules) {
		this.scRules = scRules;
		this.strategy = strategy;
		initialize();
	}
	
	private void initialize() {
		InterfaceSCFactory factory = new InterfaceSCFactory(strategy, scRules);
		tggRule2srcSCRule = factory.createOperationalRules(SyncDirection.FORWARD);
		tggRule2trgSCRule = factory.createOperationalRules(SyncDirection.BACKWARD);
		rule2matcher = new HashMap<>();
		tggRule2srcSCRule.values().stream().flatMap(c -> c.stream()).forEach(r -> rule2matcher.put(r, new LocalPatternSearch(r)));
		tggRule2trgSCRule.values().stream().flatMap(c -> c.stream()).forEach(r -> rule2matcher.put(r, new LocalPatternSearch(r)));
		rule2greenPattern = new HashMap<>();
		rule2matcher.keySet().stream().forEach(osc -> rule2greenPattern.put(osc, new GreenSCPattern(strategy.getGreenFactory(osc.getScRule().getTargetRule().getName()), osc)));
		greenInterpreter = new IbexGreenInterpreter(strategy);
	}
	
	public IMatch processBrokenMatch(SyncDirection direction, IMatch brokenMatch) {
		String ruleName = PatternSuffixes.removeSuffix(brokenMatch.getPatternName());
		switch(direction) {
		case FORWARD:
			return processBrokenMatch(tggRule2srcSCRule.get(ruleName), brokenMatch);
		case BACKWARD:
			return processBrokenMatch(tggRule2trgSCRule.get(ruleName), brokenMatch);
		default:
			return null;
		}
	}

	private IMatch processBrokenMatch(Collection<OperationalShortcutRule> rules, IMatch brokenMatch) {
		for(OperationalShortcutRule osr : rules) {
			IMatch newMatch = processBrokenMatch(osr, brokenMatch);
			if(newMatch == null)
				continue;
			
			processCreations(osr, newMatch);
			processDeletions(osr, newMatch);
			
			return transformToTargetMatch(osr, newMatch);
		}
		return null;
	}
	
	private IMatch transformToTargetMatch(OperationalShortcutRule osr, IMatch scMatch) {
		IMatch newMatch = new SimpleMatch(osr.getScRule().getTargetRule().getName() + PatternSuffixes.CONSISTENCY);
		
		osr.getScRule().getTargetRule().getNodes().forEach(n -> {
			newMatch.put(n.getName(), scMatch.get(osr.getScRule().mapRuleNodeToSCRuleNode(n).getName()));
		});
		
		String oldRaName = ConsistencyPattern.getProtocolNodeName(osr.getScRule().getSourceRule().getName());
		String raName = ConsistencyPattern.getProtocolNodeName(osr.getScRule().getTargetRule().getName());
		((TGGRuleApplication) scMatch.get(oldRaName)).setName(osr.getScRule().getTargetRule().getName());
		newMatch.put(raName, scMatch.get(oldRaName));
		
		return newMatch;
	}

	private IMatch processBrokenMatch(OperationalShortcutRule osr, IMatch brokenMatch) {
		Map<String, EObject> name2entryNodeElem = new HashMap<>();	
		for(String param : brokenMatch.getParameterNames()) {
			TGGRuleNode scNode = osr.getScRule().mapSrcToSCNodeNode(param);
			if(scNode == null || !osr.getScRule().getMergedNodes().contains(scNode))
				continue;
			
			name2entryNodeElem.put(scNode.getName(), (EObject) brokenMatch.get(param));
		}
		return rule2matcher.get(osr).findMatch(name2entryNodeElem);
	}
	
	/**
	 * Revokes (i. e. deletes) the given nodes and edges.
	 * 
	 * @param nodesToRevoke
	 *            the nodes to revoke
	 * @param edgesToRevoke
	 *            the edges to revoke
	 */
	private void revoke(final Set<EObject> nodesToRevoke, final Set<EMFEdge> edgesToRevoke) {
		EMFManipulationUtils.delete(nodesToRevoke, edgesToRevoke, node -> strategy.addToTrash(node));
	}
	
	private void processDeletions(OperationalShortcutRule osc, IMatch brokenMatch) {
		Collection<TGGRuleNode> deletedRuleNodes = TGGCollectionUtil.filterNodes(osc.getScRule().getNodes(), BindingType.DELETE);
		Collection<TGGRuleEdge> deletedRuleEdges = TGGCollectionUtil.filterEdges(osc.getScRule().getEdges(), BindingType.DELETE);
		
		Set<EMFEdge> edgesToRevoke = new HashSet<>();
		// Collect created edges to revoke.
		deletedRuleEdges.forEach(e -> {
			EMFEdge runtimeEdge = strategy.getRuntimeEdge(brokenMatch, e);
			strategy.removeCreatedEdge(runtimeEdge);
			edgesToRevoke.add(new EMFEdge(runtimeEdge.getSource(), runtimeEdge.getTarget(), runtimeEdge.getType()));
		});
		
		Set<EObject> nodesToRevoke = new HashSet<>();
		deletedRuleNodes.forEach(n -> nodesToRevoke.add((EObject) brokenMatch.get(n.getName())));
		
		revoke(nodesToRevoke, edgesToRevoke);
		
		Collection<TGGRuleNode> contextRuleNodes = TGGCollectionUtil.filterNodes(osc.getScRule().getNodes(), BindingType.CONTEXT);
		for(TGGRuleNode n : contextRuleNodes) {
			EObject e = (EObject) brokenMatch.get(n.getName());
			if(e.eContainer() == null && e.eResource() == null) {
				if(n.getDomainType().equals(DomainType.SRC))
					strategy.getSourceResource().getContents().add(e);
				if(n.getDomainType().equals(DomainType.TRG))
					strategy.getTargetResource().getContents().add(e);
			}
		}
	}
	
	private void processCreations(OperationalShortcutRule osc, IMatch brokenMatch) {
		greenInterpreter.apply(rule2greenPattern.get(osc), osc.getScRule().getTargetRule().getName(), brokenMatch);
	}
}
