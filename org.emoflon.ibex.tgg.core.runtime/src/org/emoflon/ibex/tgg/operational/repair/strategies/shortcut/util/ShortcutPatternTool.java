package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.GreenSCPattern;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.InterfaceSCFactory;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.OperationalShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGCollectionUtil;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.BindingType;
import language.TGGRuleEdge;
import language.TGGRuleNode;

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
		InterfaceSCFactory factory = new InterfaceSCFactory(scRules);
		tggRule2srcSCRule = factory.createOperationalRules(SyncDirection.FORWARD);
		tggRule2trgSCRule = factory.createOperationalRules(SyncDirection.BACKWARD);
		rule2matcher = new HashMap<>();
		tggRule2srcSCRule.values().stream().flatMap(c -> c.stream()).forEach(r -> rule2matcher.put(r, new LocalPatternSearch(r)));
		tggRule2trgSCRule.values().stream().flatMap(c -> c.stream()).forEach(r -> rule2matcher.put(r, new LocalPatternSearch(r)));
		rule2matcher.keySet().stream().forEach(osc -> rule2greenPattern.put(osc, new GreenSCPattern(strategy, osc)));
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
			
			processDeletions(osr, brokenMatch);
			processCreations(osr, brokenMatch);
		}
		return null;
	}

	private IMatch processBrokenMatch(OperationalShortcutRule osr, IMatch brokenMatch) {
		Map<String, EObject> name2entryNodeElem = new HashMap<>();	
		for(String param : brokenMatch.getParameterNames()) {
			TGGRuleNode scNode = osr.getScRule().getMappedSCRuleNode(param);
			if(scNode == null)
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
	}
	
	private void processCreations(OperationalShortcutRule osc, IMatch brokenMatch) {
		greenInterpreter.apply(rule2greenPattern.get(osc), osc.getScRule().getTargetRule().getName(), brokenMatch);
	}
}
