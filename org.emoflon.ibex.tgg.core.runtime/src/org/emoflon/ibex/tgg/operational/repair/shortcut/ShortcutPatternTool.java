package org.emoflon.ibex.tgg.operational.repair.shortcut;

import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.ArrayList;
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
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.IGreenInterpreter;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.SimpleTGGMatch;
import org.emoflon.ibex.tgg.operational.matches.TGGMatchParameterOrderProvider;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalSCFactory;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule.SCInputRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.LocalPatternSearch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.updatepolicy.IShortcutRuleUpdatePolicy;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.SCMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.SCPersistence;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;

import language.BindingType;
import language.DomainType;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.TGGRuleApplication;
import runtime.TempContainer;

/**
 * This class handles all operationalized shortcut rules and their application to fix a broken
 * match.
 * 
 * @author lfritsche
 *
 */
public class ShortcutPatternTool {

	protected final static Logger logger = Logger.getLogger(ShortcutPatternTool.class);

	private int numOfDeletedNodes = 0;

	private PropagatingOperationalStrategy strategy;
	private TGGResourceHandler resourceHandler;
	private Collection<ShortcutRule> scRules;
	private Map<PatternType, Map<String, Collection<OperationalShortcutRule>>> tggRule2opSCRule;
	private Map<OperationalShortcutRule, LocalPatternSearch> rule2matcher;

	private IGreenInterpreter greenInterpreter;
	private IRedInterpreter redInterpreter;

	public ShortcutPatternTool(PropagatingOperationalStrategy strategy, Collection<ShortcutRule> scRules, Set<PatternType> types) {
		this.scRules = scRules;
		this.strategy = strategy;
		this.resourceHandler = strategy.getOptions().resourceHandler();
		initialize(types);
	}

	private void initialize(Set<PatternType> types) {
		OperationalSCFactory factory = new OperationalSCFactory(strategy, scRules);

		tggRule2opSCRule = new HashMap<>();
		for (PatternType type : types) {
			tggRule2opSCRule.put(type, factory.createOperationalRules(type));
		}

		rule2matcher = new HashMap<>();
		tggRule2opSCRule.values().stream() //
				.flatMap(m -> m.values().stream()) //
				.flatMap(c -> c.stream()) //
				.forEach(r -> rule2matcher.put(r, new LocalPatternSearch(r, strategy.getOptions())));

		greenInterpreter = strategy.getGreenInterpreter();
		redInterpreter = strategy.getRedInterpreter();

		tggRule2opSCRule.forEach((type, map) -> LoggerConfig.log(LoggerConfig.log_repair(), //
				() -> "Generated " + map.values().stream().map(s -> s.size()).reduce(0, (a, b) -> a + b) //
						+ " " + type.name() + " Repair Rules"));

		persistSCRules();
	}

	private void persistSCRules() {
		SCPersistence persistence = new SCPersistence(strategy);
		persistence.saveSCRules(scRules);
		persistence.saveOperationalFWDSCRules(tggRule2opSCRule.get(PatternType.FWD).values().stream() //
				.flatMap(c -> c.stream()).collect(Collectors.toList()));
		persistence.saveOperationalBWDSCRules(tggRule2opSCRule.get(PatternType.BWD).values().stream() //
				.flatMap(c -> c.stream()).collect(Collectors.toList()));
	}

	public ITGGMatch processBrokenMatch(PatternType type, ITGGMatch brokenMatch) {
		if (type == null)
			return null;

		if (tggRule2opSCRule.containsKey(type)) {
			return processBrokenMatch(tggRule2opSCRule.get(type).get(brokenMatch.getRuleName()), brokenMatch);
		}
		return null;
	}

	public Map<SCMatch, OperationalShortcutRule> isRepairable(PatternType type, ITGGMatch brokenMatch, String replacingRuleName) {
		Map<SCMatch, OperationalShortcutRule> result = new HashMap<>();
		
		if (type == null)
			return result;

		if (tggRule2opSCRule.containsKey(type)) {
			Collection<OperationalShortcutRule> opSCRs = tggRule2opSCRule.get(type).get(brokenMatch.getRuleName());
			for (OperationalShortcutRule opSCR : opSCRs) {
				if (opSCR.getOpScRule().getReplacingRule().getName().equals(replacingRuleName)) {
					SCMatch shortcutMatch = (SCMatch) processBrokenMatch(opSCR, brokenMatch);
					if (shortcutMatch != null)
						result.put(shortcutMatch, opSCR);
				}
			}
		}
		return result;
	}

	private ITGGMatch processBrokenMatch(Collection<OperationalShortcutRule> rules, ITGGMatch brokenMatch) {
		if (rules == null)
			return null;

		Collection<OperationalShortcutRule> copiedRules = new ArrayList<>(rules);
		IShortcutRuleUpdatePolicy policy = strategy.getOptions().repair.shortcutRuleUpdatePolicy();
		do {
			OperationalShortcutRule osr = policy.chooseOneShortcutRule(copiedRules, brokenMatch);
			if (osr == null)
				return null;

			LoggerConfig.log(LoggerConfig.log_repair(), () -> //
			"Repair attempt: " + brokenMatch.getPatternName() + "(" + brokenMatch.hashCode() + ") with " + osr.getName());
			ITGGMatch newMatch = processBrokenMatch(osr, brokenMatch);
			if (newMatch == null) {
				copiedRules.remove(osr);
				continue;
			}

			// TODO lfritsche, amoeller: we have to make sure that we do not delete elements that are used
			// (context) or will be recreated
			// this is due to the missing injectivity checks
			Optional<ITGGMatch> newCoMatch = processCreations(osr, newMatch);
			if (!newCoMatch.isPresent()) {
				copiedRules.remove(osr);
				continue;
			}

			processDeletions(osr, newMatch);

			processAttributes(osr, newMatch);

			return transformToReplacingMatch(osr, newCoMatch.get());

		} while (!copiedRules.isEmpty());
		return null;
	}

	private ITGGMatch processBrokenMatch(OperationalShortcutRule osr, ITGGMatch brokenMatch) {
		Map<String, EObject> name2entryNodeElem = new HashMap<>();
		for (String param : brokenMatch.getParameterNames()) {
			TGGRuleNode scNode = osr.getOpScRule().mapOriginalToSCNodeNode(param);
			if (scNode == null) {
				// special case is the rule application node which we add here!
				if (!((EObject) brokenMatch.get(param) instanceof TGGRuleApplication))
					continue;
			}

			name2entryNodeElem.put(param, (EObject) brokenMatch.get(param));
		}
		return rule2matcher.get(osr).findMatch(name2entryNodeElem);
	}

	/**
	 * transforms the given operationalized shortcut rule match into a match conforming to a target rule
	 * match
	 * 
	 * @param osr
	 * @param scMatch
	 * @return
	 */
	private ITGGMatch transformToReplacingMatch(OperationalShortcutRule osr, ITGGMatch scMatch) {
		ITGGMatch tempMatch = new SimpleTGGMatch(osr.getOpScRule().getReplacingRule().getName() + PatternSuffixes.CONSISTENCY);

		osr.getOpScRule().getReplacingRule().getNodes()
				.forEach(n -> tempMatch.put(n.getName(), scMatch.get(osr.getOpScRule().mapRuleNodeToSCRuleNode(n, SCInputRule.REPLACING).getName())));

		IGreenPatternFactory greenFactory = strategy.getGreenFactory(osr.getOpScRule().getReplacingRule().getName());
		IGreenPattern greenPattern = greenFactory.create(PatternType.FWD);
		greenPattern.createMarkers(osr.getOpScRule().getReplacingRule().getName(), tempMatch);

		ITGGMatch newMatch = new SimpleTGGMatch(tempMatch.getPatternName());
		for (String p : TGGMatchParameterOrderProvider.getParams(PatternSuffixes.removeSuffix(tempMatch.getPatternName()))) {
			if (tempMatch.getParameterNames().contains(p))
				newMatch.put(p, tempMatch.get(p));
		}

		return newMatch;
	}

	private void processDeletions(OperationalShortcutRule osr, ITGGMatch brokenMatch) {
		Collection<TGGRuleNode> deletedRuleNodes = TGGFilterUtil.filterNodes(osr.getOpScRule().getNodes(), BindingType.DELETE);
		Collection<TGGRuleEdge> deletedRuleEdges = TGGFilterUtil.filterEdges(osr.getOpScRule().getEdges(), BindingType.DELETE);
		Collection<TGGRuleEdge> createdRuleEdges = TGGFilterUtil.filterEdges(osr.getOpScRule().getEdges(), BindingType.CREATE);

		Set<EMFEdge> edgesToRevoke = new HashSet<>();
		// Collect edges to revoke.
		deletedRuleEdges.forEach(e -> {
			EMFEdge toBeDeletedRuntimeEdge = getRuntimeEdge(brokenMatch, e);
			boolean isSingle = !toBeDeletedRuntimeEdge.getType().isMany();

			Collection<TGGRuleEdge> conflictingEdges = createdRuleEdges.stream() //
					.filter(edge -> edge.getType().equals(e.getType())).collect(Collectors.toList());
			for (TGGRuleEdge conflictingEdge : conflictingEdges) {
				EMFEdge toBeCreatedRuntimeEdge = getRuntimeEdge(brokenMatch, conflictingEdge);
				// we have to handle cases here where deletions should not be performed if the edge was just created
				if (isSingle && toBeCreatedRuntimeEdge.getSource().equals(toBeDeletedRuntimeEdge.getSource()))
					return;
				if (strategy.getOptions().repair.disableInjectivity() && toBeCreatedRuntimeEdge.equals(toBeDeletedRuntimeEdge))
					return;
			}

			if (toBeDeletedRuntimeEdge.getSource() != null && toBeDeletedRuntimeEdge.getTarget() != null)
				edgesToRevoke.add(new EMFEdge(toBeDeletedRuntimeEdge.getSource(), toBeDeletedRuntimeEdge.getTarget(), toBeDeletedRuntimeEdge.getType()));
		});

		Set<EObject> nodesToRevoke = new HashSet<>();
		deletedRuleNodes.forEach(n -> nodesToRevoke.add((EObject) brokenMatch.get(n.getName())));

		numOfDeletedNodes += nodesToRevoke.size();
		redInterpreter.revoke(nodesToRevoke, edgesToRevoke);

		Collection<TGGRuleNode> contextRuleNodes = TGGFilterUtil.filterNodes(osr.getOpScRule().getNodes(), BindingType.CONTEXT);
		for (TGGRuleNode n : contextRuleNodes) {
			EObject e = (EObject) brokenMatch.get(n.getName());
			if (e.eContainer() == null && e.eResource() == null || e.eResource() != null && e.eResource().getContents().get(0) instanceof TempContainer) {
				if (n.getDomainType().equals(DomainType.SRC))
					resourceHandler.getSourceResource().getContents().add(e);
				if (n.getDomainType().equals(DomainType.TRG))
					resourceHandler.getTargetResource().getContents().add(e);
			}
		}
	}

	private Optional<ITGGMatch> processCreations(OperationalShortcutRule osr, ITGGMatch brokenMatch) {
		return greenInterpreter.apply(osr.getGreenPattern(), osr.getOpScRule().getReplacingRule().getName(), brokenMatch);
	}

	private void processAttributes(OperationalShortcutRule osr, ITGGMatch match) {
		DomainType objDomain = getObjectDomain(osr.getType());
		if (objDomain == null)
			return;

		try {
			IbexGreenInterpreter ibexGI = (IbexGreenInterpreter) greenInterpreter;
			TGGFilterUtil.filterNodes(osr.getOpScRule().getNodes(), objDomain).stream() //
					.filter(n -> osr.getOpScRule().getPreservedNodes().contains(n)) //
					.forEach(n -> ibexGI.applyInPlaceAttributeAssignments(match, n, (EObject) match.get(n.getName())));
		} catch (Exception e) {
			throw new RuntimeException("IbexGreenInterpreter implementation is needed", e);
		}
	}

	private DomainType getObjectDomain(PatternType type) {
		switch (type) {
		case FWD:
			return DomainType.TRG;
		case BWD:
			return DomainType.SRC;
		default:
			return null;
		}
	}

	public int countDeletedElements() {
		return numOfDeletedNodes;
	}
}
