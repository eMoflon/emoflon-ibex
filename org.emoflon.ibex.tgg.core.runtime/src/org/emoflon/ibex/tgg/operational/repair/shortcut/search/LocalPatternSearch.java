package org.emoflon.ibex.tgg.operational.repair.shortcut.search;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.CSPCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.EdgeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.AttrCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.Lookup;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.NACNodeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.NodeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.SCMatch;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirection;

import language.BindingType;
import language.TGGRuleNode;

/**
 * 
 * This class handles the pattern search by using the operationalized shortcut
 * rule search plan. This SearchPlan is used to created Component structure
 * consisting of Lookup-, NACNodeCheck-, NodeCheck- and EdgeCheckComponents. A
 * call to the first component with the elements that are to be preserved (a
 * priori knowledge) returns a match if such exists conforming to the underlying
 * pattern. The operations used by each component are generated in
 * OperationalShortcutRule.
 * 
 * @author lfritsche
 *
 */
public class LocalPatternSearch {

	private OperationalShortcutRule osr;
	private SearchPlan searchPlan;
	private Set<EObject> currentCandidates;
	private Map<String, EObject> name2candidates;

	private Component firstComponent;

	public LocalPatternSearch(OperationalShortcutRule osr) {
		this.osr = osr;
		this.searchPlan = osr.createSearchPlan();

		// TODO lfritsche: clear up
		if (searchPlan != null)
			buildComponents();
	}

	private void buildComponents() {
		Component lastComponent = null;

		Set<Component> attrCheckComponents = new HashSet<>();

		for (Pair<SearchKey, Lookup> entry : searchPlan.lookUpPlan) {
			Component lookupComp = new LookupComponent(entry.getRight(), entry.getLeft());

			if (firstComponent == null)
				firstComponent = lookupComp;
			else
				lastComponent.setNextComponent(lookupComp);

			TGGRuleNode nodeForCheck = entry.getLeft().reverse ? //
					entry.getLeft().sourceNode : entry.getLeft().targetNode;
			Component nodeCheckComp = new NodeCheckComponent(searchPlan.key2nodeCheck.get(nodeForCheck), nodeForCheck);

			if (searchPlan.key2AttrCheck.containsKey(nodeForCheck))
				attrCheckComponents.add( //
						new AttrCheckComponent(searchPlan.key2AttrCheck.get(nodeForCheck), nodeForCheck));

			lookupComp.setNextComponent(nodeCheckComp);
			lastComponent = nodeCheckComp;
		}

		for (SearchKey key : searchPlan.key2nacNodeCheck.keySet()) {
			Component nacNodeCheckComp = new NACNodeCheckComponent(searchPlan.key2nacNodeCheck.get(key), key);
			lastComponent.setNextComponent(nacNodeCheckComp);
			lastComponent = nacNodeCheckComp;
		}

		for (SearchKey key : searchPlan.key2edgeCheck.keySet()) {
			Component edgeNodeCheckComp = new EdgeCheckComponent(searchPlan.key2edgeCheck.get(key), key);
			lastComponent.setNextComponent(edgeNodeCheckComp);

			lastComponent = edgeNodeCheckComp;
		}

		for (Component comp : attrCheckComponents) {
			lastComponent.setNextComponent(comp);
			lastComponent = comp;
		}

		for (TGGRuleNode mergedNode : osr.getScRule().getMergedNodes()) {
			if (!searchPlan.key2AttrCheck.containsKey(mergedNode) || skipAttrCheck(mergedNode))
				continue;

			Component accessNodeAttrCheckComp = new AttrCheckComponent( //
					searchPlan.key2AttrCheck.get(mergedNode), mergedNode);

			lastComponent.setNextComponent(accessNodeAttrCheckComp);
			lastComponent = accessNodeAttrCheckComp;
		}

		Component cspCheckComp = new CSPCheckComponent(searchPlan.cspCheck);
		lastComponent.setNextComponent(cspCheckComp);
		lastComponent = cspCheckComp;
	}

	private boolean skipAttrCheck(TGGRuleNode mergedNode) {
		if (osr.getScRule().getPreservedNodes().contains(mergedNode))
			switch (mergedNode.getDomainType()) {
			case SRC:
				if (osr.getDirection() == PropagationDirection.BACKWARD)
					return true;
			case TRG:
				if (osr.getDirection() == PropagationDirection.FORWARD)
					return true;
			default:
				return false;
			}
		return false;
	}

	public SCMatch findMatch(Map<String, EObject> name2entryNodeElem) {
		if (firstComponent == null)
			throw new RuntimeException("No components found for pattern matching!");

		this.name2candidates = name2entryNodeElem;
		this.currentCandidates = new HashSet<>();
		currentCandidates.addAll(name2candidates.values());

		switch (firstComponent.apply()) {
		case SUCCESS:
			return new SCMatch(osr.getScRule().getName(), name2candidates);
		case FAILURE:
			return null;
		case NEGATIVE:
			return null;
		default:
			return null;
		}
	}

	private abstract class Component {
		Component nextComponent;

		public Component() {
		}

		public void setNextComponent(Component nextComponent) {
			this.nextComponent = nextComponent;
		}

		abstract public ReturnState apply();
	}

	private class LookupComponent extends Component {
		Lookup lookup;
		String lookupSourceName;
		String lookupTargetName;
		boolean isNegative;
		boolean isRelaxed;

		public LookupComponent(Lookup lookup, SearchKey key) {
			super();

			TGGRuleNode sourceNode = key.reverse ? key.targetNode : key.sourceNode;
			TGGRuleNode targetNode = key.reverse ? key.sourceNode : key.targetNode;

			this.lookup = lookup;
			this.lookupSourceName = sourceNode.getName();
			this.lookupTargetName = targetNode.getName();
			this.isNegative = key.edge.getBindingType() == BindingType.NEGATIVE;
			this.isRelaxed = key.edge.getBindingType() == BindingType.RELAXED //
					|| (targetNode.getBindingType() == BindingType.RELAXED //
							&& key.edge.getBindingType() == BindingType.DELETE);
		}

		@Override
		public ReturnState apply() {
			EObject oldCandidate = name2candidates.get(lookupSourceName);
			if (oldCandidate == null)
				return isRelaxed ? nextComponent.apply() : ReturnState.FAILURE;

			Object lookupTarget = lookup.lookup(oldCandidate);
			if (lookupTarget == null)
				return isRelaxed ? nextComponent.apply() : ReturnState.FAILURE;

			if (lookupTarget instanceof List<?>) {
				@SuppressWarnings("unchecked")
				List<EObject> candidateList = (List<EObject>) lookupTarget;
				for (EObject candidate : candidateList) {
					if (currentCandidates.contains(candidate))
						continue;

					currentCandidates.add(candidate);
					name2candidates.put(lookupTargetName, candidate);

					switch (nextComponent.apply()) {
					case SUCCESS:
						return ReturnState.SUCCESS;
					case NEGATIVE:
						currentCandidates.remove(candidate);
						if (isNegative)
							return ReturnState.NEGATIVE;
						continue;
					case FAILURE:
						currentCandidates.remove(candidate);
						continue;
					}
				}
				return isRelaxed ? nextComponent.apply() : ReturnState.FAILURE;
			}

			if (currentCandidates.contains((EObject) lookupTarget))
				return ReturnState.FAILURE;

			name2candidates.put(lookupTargetName, (EObject) lookupTarget);
			currentCandidates.add((EObject) lookupTarget);

			ReturnState state = nextComponent.apply();
			if (state != ReturnState.SUCCESS)
				currentCandidates.remove(lookupTarget);

			return state;
		}

	}

	private class NodeCheckComponent extends Component {
		NodeCheck check;
		String nodeName;

		public NodeCheckComponent(NodeCheck check, TGGRuleNode node) {
			super();
			this.check = check;
			this.nodeName = node.getName();
		}

		@Override
		public ReturnState apply() {
			if (check.checkConstraint(name2candidates.get(nodeName))) {
				if (nextComponent == null)
					return ReturnState.SUCCESS;
				return nextComponent.apply();
			}
			return ReturnState.FAILURE;
		}

	}

	private class AttrCheckComponent extends Component {
		AttrCheck check;
		String nodeName;

		public AttrCheckComponent(AttrCheck check, TGGRuleNode node) {
			super();
			this.check = check;
			this.nodeName = node.getName();
		}

		@Override
		public ReturnState apply() {
			if (check.checkAttributes(name2candidates.get(nodeName), name2candidates)) {
				if (nextComponent == null)
					return ReturnState.SUCCESS;
				return nextComponent.apply();
			}
			return ReturnState.FAILURE;
		}
	}

	private class EdgeCheckComponent extends Component {
		EdgeCheck check;
		String sourceName;
		String targetName;
		boolean isNegative;
		boolean isSrcRelaxed;
		boolean isTrgRelaxed;

		public EdgeCheckComponent(EdgeCheck check, SearchKey key) {
			super();
			this.check = check;
			this.sourceName = key.sourceNode.getName();
			this.targetName = key.targetNode.getName();
			this.isNegative = key.edge.getBindingType() == BindingType.NEGATIVE;
			this.isSrcRelaxed = key.sourceNode.getBindingType() == BindingType.RELAXED;
			this.isTrgRelaxed = key.targetNode.getBindingType() == BindingType.RELAXED;
		}

		@Override
		public ReturnState apply() {
			EObject srcCandidate = name2candidates.get(sourceName);
			if (srcCandidate == null)
				if (isSrcRelaxed)
					return nextComponent == null ? ReturnState.SUCCESS : nextComponent.apply();
				else
					return ReturnState.FAILURE;

			EObject trgCandidate = name2candidates.get(targetName);
			if (trgCandidate == null)
				if (isTrgRelaxed)
					return nextComponent == null ? ReturnState.SUCCESS : nextComponent.apply();
				else
					return ReturnState.FAILURE;

			if (check.checkConstraint(srcCandidate, trgCandidate)) {
				if (nextComponent == null)
					return ReturnState.SUCCESS;
				return nextComponent.apply();
			}
			return isNegative ? ReturnState.NEGATIVE : ReturnState.FAILURE;
		}
	}

	private class NACNodeCheckComponent extends Component {
		NACNodeCheck check;
		String sourceName;
		boolean isSrcRelaxed;

		public NACNodeCheckComponent(NACNodeCheck check, SearchKey key) {
			super();
			this.check = check;
			TGGRuleNode srcNode = key.reverse ? key.targetNode : key.sourceNode;
			this.sourceName = srcNode.getName();
			this.isSrcRelaxed = srcNode.getBindingType() == BindingType.RELAXED;
		}

		@Override
		public ReturnState apply() {
			EObject srcCandidate = name2candidates.get(sourceName);
			if (srcCandidate == null)
				if (isSrcRelaxed)
					return nextComponent == null ? ReturnState.SUCCESS : nextComponent.apply();
				else
					return ReturnState.FAILURE;

			if (check.checkConstraint(srcCandidate, currentCandidates)) {
				if (nextComponent == null)
					return ReturnState.SUCCESS;
				return nextComponent.apply();
			}
			return ReturnState.NEGATIVE;
		}
	}

	private class CSPCheckComponent extends Component {
		CSPCheck cspCheck;

		public CSPCheckComponent(CSPCheck cspCheck) {
			this.cspCheck = cspCheck;
		}

		@Override
		public ReturnState apply() {
			if (cspCheck.checkConstraint(name2candidates))
				return ReturnState.SUCCESS;
			return ReturnState.FAILURE;
		}
	}

	enum ReturnState {
		SUCCESS, NEGATIVE, FAILURE
	}
}
