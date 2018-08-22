package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.OperationalShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.lambda.EdgeCheck;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.lambda.Lookup;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.lambda.NACNodeCheck;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.lambda.NodeCheck;

import language.BindingType;
import language.TGGRuleNode;

public class LocalPatternSearch {
	
	private OperationalShortcutRule osr;
	private SearchPlan searchPlan;
	private Set<EObject> currentCandidates;
	private Map<String, EObject> name2candidates;
	
	private Component firstComponent;
	
	protected LocalPatternSearch(OperationalShortcutRule osr) {
		this.osr = osr;
		this.searchPlan = osr.createSearchPlan();
		buildComponents();
	}
	
	private void buildComponents() {
		Component lastComponent = null;
		for(Pair<SearchKey, Lookup> entry : searchPlan.lookUpPlan) {
			Component lookupComp = new LookupComponent(entry.getRight(), entry.getLeft());
			
			if(firstComponent == null)
				firstComponent = lookupComp;
			else 
				lookupComp.setNextComponent(lastComponent);
			
			Component nodeCheckComp;
			if(entry.getLeft().reverse) 
				nodeCheckComp = new NodeCheckComponent(searchPlan.key2nodeCheck.get(entry.getLeft().sourceNode), entry.getLeft().sourceNode);
			else
				nodeCheckComp = new NodeCheckComponent(searchPlan.key2nodeCheck.get(entry.getLeft().targetNode), entry.getLeft().targetNode);
			lookupComp.setNextComponent(nodeCheckComp);
			
			lastComponent = nodeCheckComp;
		}
		
		for(SearchKey key : searchPlan.key2nacNodeCheck.keySet()) {
			Component nacNodeCheckComp = new NACNodeCheckComponent(searchPlan.key2nacNodeCheck.get(key), key);
			lastComponent.setNextComponent(nacNodeCheckComp);
			
			lastComponent = nacNodeCheckComp;
		}
		
		for(SearchKey key : searchPlan.key2edgeCheck.keySet()) {
			Component nacNodeCheckComp = new EdgeCheckComponent(searchPlan.key2edgeCheck.get(key), key);
			lastComponent.setNextComponent(nacNodeCheckComp);
			
			lastComponent = nacNodeCheckComp;
		}
	}
	
	protected SCMatch findMatch(Map<String, EObject> name2entryNodeElem) {
		if(firstComponent == null)
			throw new RuntimeException("No components found for pattern matching!");
		
		this.name2candidates = name2entryNodeElem;
		
		switch(firstComponent.apply()) {
			case SUCCESS: 
				return new SCMatch(osr.getScRule().getName(), name2candidates);
			case FAILURE: 
				return null;
			case NEGATIVE: 
				return null;
			default: return null;
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
		
		public LookupComponent(Lookup lookup, SearchKey key) {
			super();
			this.lookup = lookup;
			this.lookupSourceName = key.reverse ? key.targetNode.getName() : key.sourceNode.getName();
			this.lookupTargetName = key.reverse ? key.sourceNode.getName() : key.targetNode.getName();
			this.isNegative = key.edge.getBindingType() == BindingType.NEGATIVE;
		}
		
		@Override
		public ReturnState apply() {
			Object lookupTarget = lookup.lookup(name2candidates.get(lookupSourceName));
			
			if (lookupTarget != null && lookupTarget instanceof List<?>) {
				List<EObject> candidateList = (List<EObject>) lookupTarget;
				for(EObject candidate : candidateList) {
					if(currentCandidates.contains(candidate))
						continue;
					
					name2candidates.put(lookupTargetName, candidate);
					
					switch(nextComponent.apply()) {
						case SUCCESS: 
							return ReturnState.SUCCESS;
						case NEGATIVE: 
							currentCandidates.remove(candidate);
							if(isNegative)
								return ReturnState.NEGATIVE;
							continue;
						case FAILURE: 
							currentCandidates.remove(candidate);
							continue;
					}
				}
				return ReturnState.FAILURE;
			}
		
			name2candidates.put(lookupTargetName, (EObject) lookupTarget);
			return nextComponent.apply();
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
			 if(check.checkConstraint(name2candidates.get(nodeName))) {
				 if(nextComponent == null)
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
		
		public EdgeCheckComponent(EdgeCheck check, SearchKey key) {
			super();
			this.check = check;
			this.sourceName = key.sourceNode.getName();
			this.targetName = key.targetNode.getName();
			this.isNegative = key.edge.getBindingType() == BindingType.NEGATIVE;
		}

		@Override
		public ReturnState apply() {
			 if(check.checkConstraint(name2candidates.get(sourceName), name2candidates.get(targetName))) {
				 if(nextComponent == null)
					 return ReturnState.SUCCESS;
				 return nextComponent.apply();
			 }
			 return isNegative ? ReturnState.NEGATIVE : ReturnState.FAILURE;
		}
	}
	
	private class NACNodeCheckComponent extends Component {
		NACNodeCheck check;
		String sourceName;
		
		public NACNodeCheckComponent(NACNodeCheck check, SearchKey key) {
			super();
			this.check = check;
			this.sourceName = key.reverse ? key.targetNode.getName() : key.sourceNode.getName();
		}

		@Override
		public ReturnState apply() {
			 if(check.checkConstraint(name2candidates.get(sourceName))) {
				 if(nextComponent == null)
					 return ReturnState.SUCCESS;
				 return nextComponent.apply();
			 }
			 return ReturnState.NEGATIVE;
		}
	}
	
	enum ReturnState {
		SUCCESS, NEGATIVE, FAILURE
	}
}
