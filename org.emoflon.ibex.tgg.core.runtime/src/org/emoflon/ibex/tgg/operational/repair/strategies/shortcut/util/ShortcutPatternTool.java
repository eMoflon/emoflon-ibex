package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getProtocolNodeName;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.IGreenInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.SimpleMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.GreenSCPattern;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.InterfaceSCFactory;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.OperationalShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.ShortcutRule.SCInputRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGCollectionUtil;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

import language.BindingType;
import language.DomainType;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.RuntimePackage;
import runtime.TGGRuleApplication;
import runtime.TempContainer;

/**
 * This class handles all operationalized shortcut rules and their application to fix a broken match.
 * 
 * @author lfritsche
 *
 */
public class ShortcutPatternTool {
	
	protected final static Logger logger = Logger.getLogger(ShortcutPatternTool.class);
	
	private int numOfDeletedNodes = 0;
	private int numOfDeletedEdges = 0;
	
	private SYNC strategy;
	private Collection<ShortcutRule> scRules;
	private Map<String, Collection<OperationalShortcutRule>> tggRule2srcSCRule;
	private Map<String, Collection<OperationalShortcutRule>> tggRule2trgSCRule;
	private Map<OperationalShortcutRule, LocalPatternSearch> rule2matcher;
	
	private IGreenInterpreter greenInterpreter;
	
	public ShortcutPatternTool(SYNC strategy, Collection<ShortcutRule> scRules) {
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
		greenInterpreter = strategy.getGreenInterpreter();
		
		persistSCRules();
	}
	
	private void persistSCRules() {
		SCPersistence persistence = new SCPersistence(strategy);
		persistence.saveSCRules(scRules);
		persistence.saveOperationalFWDSCRules(tggRule2srcSCRule.values().stream().flatMap(c -> c.stream()).collect(Collectors.toList()));
		persistence.saveOperationalBWDSCRules(tggRule2trgSCRule.values().stream().flatMap(c -> c.stream()).collect(Collectors.toList()));
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
		if(rules == null)
			return null;
		
		for(OperationalShortcutRule osr : rules) {
			// TODO lfritsche: clear up
			if(rule2matcher.get(osr) == null)
				continue;
			
			logger.debug("Attempt repair of " + brokenMatch.getPatternName() + " with " + osr.getScRule().getName() + " (" + brokenMatch.hashCode() + ")");
			
			IMatch newMatch = processBrokenMatch(osr, brokenMatch);
			if(newMatch == null)
				continue;

			Optional<IMatch> newCoMatch = processCreations(osr, newMatch);
			if(!newCoMatch.isPresent())
				continue;

			processDeletions(osr, newMatch);
			
			return transformToTargetMatch(osr, newCoMatch.get());
		}
		return null;
	}
	
	/**
	 * transforms the given operationalized shortcut rule match into a match conforming to a target rule match
	 * @param osr
	 * @param scMatch
	 * @return
	 */
	private IMatch transformToTargetMatch(OperationalShortcutRule osr, IMatch scMatch) {
		IMatch newMatch = new SimpleMatch(osr.getScRule().getTargetRule().getName() + PatternSuffixes.CONSISTENCY);
		
		osr.getScRule().getTargetRule().getNodes().forEach(n -> 
			newMatch.put(n.getName(), scMatch.get(osr.getScRule().mapRuleNodeToSCRuleNode(n, SCInputRule.TARGET).getName()))
		);
		
		IGreenPatternFactory greenFactory = strategy.getGreenFactory(osr.getScRule().getTargetRule().getName());
		IGreenPattern greenPattern = greenFactory.create(TGGPatternUtil.getFWDBlackPatternName(osr.getScRule().getTargetRule().getName()));
		greenPattern.createMarkers(osr.getScRule().getTargetRule().getName(), newMatch);
		
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
	private void revokeElements(final Set<EObject> nodesToRevoke, final Set<EMFEdge> edgesToRevoke) {
		EMFManipulationUtils.delete(nodesToRevoke, edgesToRevoke, node -> strategy.addToTrash(node));
	}
	
	private void processDeletions(OperationalShortcutRule osc, IMatch brokenMatch) {
		Collection<TGGRuleNode> deletedRuleNodes = TGGCollectionUtil.filterNodes(osc.getScRule().getNodes(), BindingType.DELETE);
		Collection<TGGRuleEdge> deletedRuleEdges = TGGCollectionUtil.filterEdges(osc.getScRule().getEdges(), BindingType.DELETE);
		
		Set<EMFEdge> edgesToRevoke = new HashSet<>();
		// Collect edges to revoke.
		deletedRuleEdges.forEach(e -> {
			EMFEdge runtimeEdge = strategy.getRuntimeEdge(brokenMatch, e);
			edgesToRevoke.add(new EMFEdge(runtimeEdge.getSource(), runtimeEdge.getTarget(), runtimeEdge.getType()));
		});
		
		Set<EObject> nodesToRevoke = new HashSet<>();
		deletedRuleNodes.forEach(n -> nodesToRevoke.add((EObject) brokenMatch.get(n.getName())));
		
		numOfDeletedNodes += nodesToRevoke.size();
		numOfDeletedEdges += edgesToRevoke.stream().filter(e -> !e.getSource().eClass().equals(RuntimePackage.eINSTANCE.getTGGRuleApplication())).count();
		revokeElements(nodesToRevoke, edgesToRevoke);
		
		Collection<TGGRuleNode> contextRuleNodes = TGGCollectionUtil.filterNodes(osc.getScRule().getNodes(), BindingType.CONTEXT);
		for(TGGRuleNode n : contextRuleNodes) {
			EObject e = (EObject) brokenMatch.get(n.getName());
			if(e.eContainer() == null && e.eResource() == null || e.eResource() != null && e.eResource().getContents().get(0) instanceof TempContainer) {
				if(n.getDomainType().equals(DomainType.SRC))
					strategy.getSourceResource().getContents().add(e);
				if(n.getDomainType().equals(DomainType.TRG))
					strategy.getTargetResource().getContents().add(e);
			}
		}
	}
	
	private Optional<IMatch> processCreations(OperationalShortcutRule osc, IMatch brokenMatch) {
		return greenInterpreter.apply(osc.getGreenPattern(), osc.getScRule().getTargetRule().getName(), brokenMatch);
	}

	public int countDeletedElements() {
		return numOfDeletedNodes + numOfDeletedEdges;
	}
}
