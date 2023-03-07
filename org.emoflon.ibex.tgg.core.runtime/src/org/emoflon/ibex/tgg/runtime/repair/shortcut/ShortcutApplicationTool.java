package org.emoflon.ibex.tgg.runtime.repair.shortcut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.IGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.IRedInterpreter;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.SimpleTGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.TGGMatchParameterOrderProvider;
import org.emoflon.ibex.tgg.runtime.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.runtime.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderSupport;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule.HigherOrderRuleComponent;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.ShortcutRule.SCInputRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.updatepolicy.IShortcutRuleUpdatePolicy;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.SCMatch;
import org.emoflon.ibex.tgg.runtime.repair.strategies.RepairApplicationPoint;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuleApplication;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TempContainer;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.util.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.util.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.util.benchmark.Timer;
import org.emoflon.ibex.tgg.util.benchmark.Times;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

import language.TGGRuleEdge;
import language.TGGRuleNode;

/**
 * This class handles all operationalized shortcut rules and their application to fix a broken
 * match.
 * 
 * @author lfritsche
 *
 */
public class ShortcutApplicationTool implements TimeMeasurable {

	protected final static Logger logger = Logger.getLogger(ShortcutApplicationTool.class);

	private int numOfDeletedNodes = 0;

	protected final Times times = new Times();

	private final IbexOptions options;
	private final IGreenInterpreter greenInterpreter;
	private final IRedInterpreter redInterpreter;
	private final ShortcutPatternProvider shortcutPatternProvider;

	private TGGResourceHandler resourceHandler;

	public ShortcutApplicationTool(IbexOptions options, IGreenInterpreter greenInterpreter, IRedInterpreter redInterpreter, //
			ShortcutPatternProvider shortcutPatternProvider) {
		TimeRegistry.register(this);

		this.options = options;
		this.greenInterpreter = greenInterpreter;
		this.redInterpreter = redInterpreter;
		this.shortcutPatternProvider = shortcutPatternProvider;

		this.resourceHandler = options.resourceHandler();
	}

	public Collection<ITGGMatch> repairAtApplicationPoint(RepairApplicationPoint applPoint) {
		Collection<OperationalShortcutRule> opShortcutRules = shortcutPatternProvider.getOperationalShortcutRules(applPoint);
		return repairAtApplicationMatch(opShortcutRules, applPoint.getApplicationMatch());
	}

	public Map<SCMatch, OperationalShortcutRule> isRepairable(RepairApplicationPoint applPoint, String replacingRuleName) {
		Map<SCMatch, OperationalShortcutRule> result = new HashMap<>();

		Collection<OperationalShortcutRule> opShortcutRules = shortcutPatternProvider.getOperationalShortcutRules(applPoint);

		for (OperationalShortcutRule opSCR : opShortcutRules) {
			if (opSCR.getOperationalizedSCR().getReplacingRule().getName().equals(replacingRuleName)) {
				SCMatch shortcutMatch = (SCMatch) matchShortcutRule(opSCR, applPoint.getApplicationMatch());
				if (shortcutMatch != null)
					result.put(shortcutMatch, opSCR);
			}
		}

		return result;
	}

	private Collection<ITGGMatch> repairAtApplicationMatch(Collection<OperationalShortcutRule> rules, ITGGMatch applMatch) {
		if (rules == null || rules.isEmpty())
			return null;

		Collection<OperationalShortcutRule> copiedRules = new ArrayList<>(rules);
		IShortcutRuleUpdatePolicy policy = options.repair.shortcutRuleUpdatePolicy();
		do {
			OperationalShortcutRule osr = policy.chooseOneShortcutRule(copiedRules, applMatch);
			if (osr == null)
				return null;

			LoggerConfig.log(LoggerConfig.log_repair(), () -> //
			"Repair attempt at: " + applMatch.getPatternName() + "(" + applMatch.hashCode() + ") with " + osr.getName());
			Timer.start();
			ITGGMatch newMatch = matchShortcutRule(osr, applMatch);
			times.addTo("processBrokenMatch", Timer.stop());
			if (newMatch == null) {
				copiedRules.remove(osr);
				continue;
			}

			// TODO lfritsche, amoeller: we have to make sure that we do not delete elements that are used
			// (context) or will be recreated
			// this is due to the missing injectivity checks
			Timer.start();
			Optional<ITGGMatch> newCoMatch = processCreations(osr, newMatch);
			times.addTo("processCreations", Timer.stop());
			if (!newCoMatch.isPresent()) {
				copiedRules.remove(osr);
				continue;
			}

			Timer.start();
			processDeletions(osr, newMatch);
			times.addTo("processDeletions", Timer.stop());

			Timer.start();
			processAttributes(osr, newMatch);
			times.addTo("processAttributes", Timer.stop());

			return transformToReplacingMatches(osr, newCoMatch.get());

		} while (!copiedRules.isEmpty());

		return null;
	}

	private SCMatch matchShortcutRule(OperationalShortcutRule osr, ITGGMatch applMatch) {
		ShortcutRule shortcutRule = osr.getOperationalizedSCR();

		Map<String, EObject> name2entryNodeElt = shortcutRule.getOriginalRule() instanceof HigherOrderTGGRule hoRule //
				? getEntryNodeElts(shortcutRule, hoRule)
				: getEntryNodeElts(shortcutRule, applMatch);

		return shortcutPatternProvider.getPatternSearch(osr).findMatch(name2entryNodeElt);
	}

	private Map<String, EObject> getEntryNodeElts(ShortcutRule shortcutRule, ITGGMatch entryMatch) {
		Map<String, EObject> name2entryNodeElt = new HashMap<>();

		for (String param : entryMatch.getParameterNames()) {
			EObject mappedObject = (EObject) entryMatch.get(param);

			TGGNode scNode = shortcutRule.mapOriginalNodeNameToSCNode(param);
			if (scNode == null) {
				// special case is the rule application node which we want to add anyway:
				if (!(mappedObject instanceof TGGRuleApplication))
					continue;
			}

			name2entryNodeElt.put(param, mappedObject);
		}

		return name2entryNodeElt;
	}

	private Map<String, EObject> getEntryNodeElts(ShortcutRule shortcutRule, HigherOrderTGGRule hoRule) {
		Map<String, EObject> name2entryNodeElt = new HashMap<>();

		for (HigherOrderRuleComponent component : hoRule.getComponents()) {
			for (String param : component.match.getParameterNames()) {
				EObject mappedObject = (EObject) component.match.get(param);
				String hoRuleNodeName = HigherOrderSupport.findHigherOrderNodeName(component, param);

				TGGNode scNode = null;
				if (hoRuleNodeName != null)
					scNode = shortcutRule.mapOriginalNodeNameToSCNode(hoRuleNodeName);

				if (scNode == null) {
					// special case is the rule application node which we want to add anyway:
					if (mappedObject instanceof TGGRuleApplication) {
						name2entryNodeElt.put(param + component.id, mappedObject);
					}
					continue;
				}

				name2entryNodeElt.putIfAbsent(scNode.getName(), mappedObject);
			}
		}

		return name2entryNodeElt;
	}

	/**
	 * Transforms the given operationalized shortcut rule match into a match conforming to a target rule
	 * match
	 * 
	 * @param osr
	 * @param scMatch
	 * @return
	 */
	private Collection<ITGGMatch> transformToReplacingMatches(OperationalShortcutRule osr, ITGGMatch scMatch) {
		ShortcutRule shortcutRule = osr.getOperationalizedSCR();

		if (shortcutRule.getReplacingRule() instanceof HigherOrderTGGRule hoRule)
			return transformToReplacingMatches(shortcutRule, hoRule, scMatch);
		else
			return Collections.singletonList(transformToReplacingMatch(shortcutRule, scMatch));
	}

	private ITGGMatch transformToReplacingMatch(ShortcutRule shortcutRule, ITGGMatch scMatch) {
		ITGGMatch tempMatch = new SimpleTGGMatch(shortcutRule.getReplacingRule().getName() + PatternSuffixes.CONSISTENCY);

		shortcutRule.getReplacingRule().getNodes().forEach( //
				n -> tempMatch.put(n.getName(), scMatch.get(shortcutRule.mapRuleNodeToSCNode(n, SCInputRule.REPLACING).getName())));

		IGreenPatternFactory greenFactory = options.patterns.greenPatternFactories().get(shortcutRule.getReplacingRule().getName());
		IGreenPattern greenPattern = greenFactory.create(PatternType.FWD);
		greenPattern.createMarkers(shortcutRule.getReplacingRule().getName(), tempMatch);

		ITGGMatch newMatch = new SimpleTGGMatch(tempMatch.getPatternName());
		for (String p : TGGMatchParameterOrderProvider.getParams(PatternSuffixes.removeSuffix(tempMatch.getPatternName()))) {
			if (tempMatch.getParameterNames().contains(p))
				newMatch.put(p, tempMatch.get(p));
		}

		return newMatch;
	}

	private Collection<ITGGMatch> transformToReplacingMatches(ShortcutRule shortcutRule, HigherOrderTGGRule hoRule, ITGGMatch scMatch) {
		Collection<ITGGMatch> result = new LinkedList<>();

		for (HigherOrderRuleComponent component : hoRule.getComponents()) {
			ITGGMatch tempMatch = new SimpleTGGMatch(component.rule.getName() + PatternSuffixes.CONSISTENCY);

			for (TGGNode node : component.rule.getNodes()) {
				TGGNode hoNode = HigherOrderSupport.getHigherOrderElement(component, node);
				TGGNode scNode = shortcutRule.mapRuleNodeToSCNode(hoNode, SCInputRule.REPLACING);
				tempMatch.put(node.getName(), scMatch.get(scNode.getName()));
			}

			IGreenPatternFactory greenFactory = options.patterns.greenPatternFactories().get(component.rule.getName());
			IGreenPattern greenPattern = greenFactory.create(PatternType.FWD);
			greenPattern.createMarkers(component.rule.getName(), tempMatch);

			ITGGMatch newMatch = new SimpleTGGMatch(tempMatch.getPatternName());
			for (String p : TGGMatchParameterOrderProvider.getParams(PatternSuffixes.removeSuffix(tempMatch.getPatternName()))) {
				if (tempMatch.getParameterNames().contains(p))
					newMatch.put(p, tempMatch.get(p));
			}
			result.add(tempMatch);
		}

		return result;
	}

	private void processDeletions(OperationalShortcutRule osr, ITGGMatch brokenMatch) {
		ShortcutRule shortcutRule = osr.getOperationalizedSCR();

		Collection<TGGNode> deletedRuleNodes = TGGFilterUtil.filterNodes(shortcutRule.getNodes(), BindingType.DELETE);
		Collection<TGGEdge> deletedRuleEdges = TGGFilterUtil.filterEdges(shortcutRule.getEdges(), BindingType.DELETE);
		Collection<TGGEdge> createdRuleEdges = TGGFilterUtil.filterEdges(shortcutRule.getEdges(), BindingType.CREATE);

		Set<EMFEdge> edgesToRevoke = new HashSet<>();
		// Collect edges to revoke.
		deletedRuleEdges.forEach(e -> {
			EMFEdge toBeDeletedRuntimeEdge = getRuntimeEdge(brokenMatch, e);
			boolean isSingle = !toBeDeletedRuntimeEdge.getType().isMany();

			Collection<TGGRuleEdge> conflictingEdges = createdRuleEdges.stream() //
					.filter(edge -> edge.getType().equals(e.getType())).collect(Collectors.toList());
			for (TGGRuleEdge conflictingEdge : conflictingEdges) {
				EMFEdge toBeCreatedRuntimeEdge = getRuntimeEdge(brokenMatch, conflictingEdge);
				// if the runtime edge is null, this means that the element necessary to find it has been created
				// anew by processCreations and we can ignore it.
				if (toBeCreatedRuntimeEdge == null)
					return;

				// we have to handle cases here where deletions should not be performed if the edge was just created
				if (isSingle && toBeCreatedRuntimeEdge.getSource().equals(toBeDeletedRuntimeEdge.getSource()))
					return;

				// if disable injectivity is activated and both edges are equal, we do not have to perform a
				// deletion
				if (options.repair.disableInjectivity() && toBeCreatedRuntimeEdge.equals(toBeDeletedRuntimeEdge))
					return;
			}

			if (toBeDeletedRuntimeEdge.getSource() != null && toBeDeletedRuntimeEdge.getTarget() != null)
				edgesToRevoke
						.add(new EMFEdge(toBeDeletedRuntimeEdge.getSource(), toBeDeletedRuntimeEdge.getTarget(), toBeDeletedRuntimeEdge.getType()));
		});

		Set<EObject> nodesToRevoke = new HashSet<>();
		deletedRuleNodes.forEach(n -> nodesToRevoke.add((EObject) brokenMatch.get(n.getName())));

		numOfDeletedNodes += nodesToRevoke.size();
		redInterpreter.revoke(nodesToRevoke, edgesToRevoke);

		Collection<TGGRuleNode> contextRuleNodes = TGGFilterUtil.filterNodes(shortcutRule.getNodes(), BindingType.CONTEXT);
		for (TGGRuleNode n : contextRuleNodes) {
			EObject e = (EObject) brokenMatch.get(n.getName());
			if (e.eContainer() == null && e.eResource() == null
					|| e.eResource() != null && e.eResource().getContents().get(0) instanceof TempContainer) {
				if (n.getDomainType().equals(DomainType.SRC))
					resourceHandler.getSourceResource().getContents().add(e);
				if (n.getDomainType().equals(DomainType.TRG))
					resourceHandler.getTargetResource().getContents().add(e);
			}
		}
	}

	private Optional<ITGGMatch> processCreations(OperationalShortcutRule osr, ITGGMatch newMatch) {
		return greenInterpreter.apply(osr.getGreenPattern(), osr.getOperationalizedSCR().getReplacingRule().getName(), newMatch);
	}

	private void processAttributes(OperationalShortcutRule osr, ITGGMatch newMatch) {
		DomainType objDomain = getObjectDomain(osr.getType());
		if (objDomain == null)
			return;

		IbexGreenInterpreter ibexGreenInterpr;
		try {
			ibexGreenInterpr = (IbexGreenInterpreter) greenInterpreter;
		} catch (Exception e) {
			throw new RuntimeException("IbexGreenInterpreter implementation is needed", e);
		}
		TGGFilterUtil.filterNodes(osr.getOperationalizedSCR().getNodes(), objDomain).stream() //
				.filter(n -> osr.getOperationalizedSCR().getPreservedNodes().contains(n)) //
				.forEach(n -> ibexGreenInterpr.applyInPlaceAttributeAssignments(newMatch, n, (EObject) newMatch.get(n.getName())));
	}

	private DomainType getObjectDomain(PatternType type) {
		return switch (type) {
			case FWD -> DomainType.TRG;
			case BWD -> DomainType.SRC;
			default -> null;
		};
	}

	public int countDeletedElements() {
		return numOfDeletedNodes;
	}

	@Override
	public Times getTimes() {
		return times;
	}
}
