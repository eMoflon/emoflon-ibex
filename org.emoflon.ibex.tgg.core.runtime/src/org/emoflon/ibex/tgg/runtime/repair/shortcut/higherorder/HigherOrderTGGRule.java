package org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGPattern;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.util.debug.ConsoleUtil;

import language.LanguageFactory;
import language.NAC;
import language.TGGAttributeConstraintLibrary;
import language.TGGAttributeExpression;
import language.TGGParamValue;

public class HigherOrderTGGRule extends TGGRuleImpl {

	private final LinkedList<HigherOrderRuleComponent> components;
	private final Map<ITGGMatch, HigherOrderRuleComponent> match2component;

	private int componentCounter;

	protected HigherOrderTGGRule() {
		super();
		this.components = new LinkedList<>();
		this.match2component = new HashMap<>();

		this.componentCounter = 0;

		initTGGRuleContents();
	}

	protected void addComponent(TGGRule rule, ITGGMatch match, Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping) {
		HigherOrderRuleComponent component = new HigherOrderRuleComponent(rule, match, contextMapping);
		components.addLast(component);
		match2component.put(match, component);

		populateTGGRule(rule, component, contextMapping);
	}

	public List<HigherOrderRuleComponent> getComponents() {
		return components;
	}

	public HigherOrderRuleComponent getComponent(ITGGMatch match) {
		return match2component.get(match);
	}

	public HigherOrderRuleComponent getClosureComponent() {
		return components.getLast();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HigherOrderTGGRule [\n");
		for (HigherOrderRuleComponent component : components) {
			builder.append(ConsoleUtil.indent(component.rule.getName(), 2, true));
			builder.append("\n");
		}
		builder.append("]");
		return builder.toString();
	}

	public class HigherOrderRuleComponent {
		public final TGGRule rule;
		public final ITGGMatch match;
		public final Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping;
		public final int id;
		private final Map<String, TGGNode> name2ruleNode;

		private HigherOrderRuleComponent(TGGRule rule, ITGGMatch match, Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping) {
			this.rule = rule;
			this.match = match;
			this.contextMapping = contextMapping;
			this.id = componentCounter++;
			this.name2ruleNode = rule.getNodes().stream() //
					.collect(Collectors.toMap(n -> n.getName(), n -> n));
		}

		public ComponentSpecificRuleElement getComponentSpecificRuleElement(TGGRuleElement ruleElement) {
			if (rule.getNodes().contains(ruleElement) || rule.getEdges().contains(ruleElement))
				return new ComponentSpecificRuleElement(ruleElement, this);
			else
				return null;
		}

		public TGGNode getNodeFromName(String nodeName) {
			return name2ruleNode.get(nodeName);
		}

		@Override
		public String toString() {
			return "HigherOrderRuleComponent [rule: " + rule.getName() + "]";
		}
	}

	public class ComponentSpecificRuleElement {
		public final TGGRuleElement ruleElement;
		public final HigherOrderRuleComponent component;

		private ComponentSpecificRuleElement(TGGRuleElement ruleElement, HigherOrderRuleComponent component) {
			this.ruleElement = ruleElement;
			this.component = component;
		}

		public TGGRuleElement getRespectiveHigherOrderElement() {
			if (componentElt2higherOrderElt == null)
				return null;
			return componentElt2higherOrderElt.get(this);
		}

		@Override
		public int hashCode() {
			return Objects.hash(component, ruleElement);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ComponentSpecificRuleElement other = (ComponentSpecificRuleElement) obj;
			return Objects.equals(component, other.component) && Objects.equals(ruleElement, other.ruleElement);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ComponentSpecificRuleElement [\n");
			builder.append("  element:   ");
			builder.append(ruleElement);
			builder.append("\n  component: ");
			builder.append(ConsoleUtil.indent(component.toString(), 2, false));
			builder.append("\n]");
			return builder.toString();
		}
	}

	//// HigherOrderRule Elements ////

	private Map<ComponentSpecificRuleElement, TGGRuleElement> componentElt2higherOrderElt;
	private Set<String> higherOrderNodeNames;

	private void initTGGRuleContents() {
		super.setName("HigherOrder");
		super.setAbstract(false);
		this.componentElt2higherOrderElt = new HashMap<>();
		this.higherOrderNodeNames = new HashSet<>();
	}

	private void populateTGGRule(TGGRule rule, HigherOrderRuleComponent component, Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping) {
		super.setName(super.getName() + "_" + rule.getName());

		populateElts(rule, component, contextMapping);
		transferAttrCondLibrary(rule, component);
		adaptAttributeAssignments(rule, component);
		transferNACs(rule, component);
	}

	private void populateElts(TGGRule rule, HigherOrderRuleComponent component, Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping) {
		for (TGGNode node : rule.getNodes()) {
			ComponentSpecificRuleElement componentNode = contextMapping.get(node);
			if (componentNode != null)
				transformHigherOrderNode(component, node, componentNode);
			else
				createNewHigherOrderNode(component, node);
		}
		for (TGGEdge edge : rule.getEdges()) {
			ComponentSpecificRuleElement componentEdge = contextMapping.get(edge);
			if (componentEdge != null)
				transformHigherOrderEdge(component, edge, componentEdge);
			else
				createNewHigherOrderEdge(component, edge);
		}
	}

	private void transformHigherOrderNode(HigherOrderRuleComponent component, TGGNode node, ComponentSpecificRuleElement mappedComponentNode) {
		TGGNode higherOrderNode = (TGGNode) componentElt2higherOrderElt.get(mappedComponentNode);
		if (higherOrderNode == null)
			throw new RuntimeException("Inconsistent context mapping!");

		ComponentSpecificRuleElement componentNode = component.getComponentSpecificRuleElement(node);
		componentElt2higherOrderElt.put(componentNode, higherOrderNode);

		// TODO transfer inplace attributes!
	}

	private void transformHigherOrderEdge(HigherOrderRuleComponent component, TGGEdge edge, ComponentSpecificRuleElement mappedComponentNode) {
		TGGEdge higherOrderEdge = (TGGEdge) componentElt2higherOrderElt.get(mappedComponentNode);
		if (higherOrderEdge == null)
			throw new RuntimeException("Inconsistent context mapping!");

		ComponentSpecificRuleElement componentEdge = component.getComponentSpecificRuleElement(edge);
		componentElt2higherOrderElt.put(componentEdge, higherOrderEdge);
	}

	private void createNewHigherOrderNode(HigherOrderRuleComponent component, TGGNode node) {
		TGGNode higherOrderNode = IBeXTGGModelFactory.eINSTANCE.createTGGNode();

		String newName = node.getName();
		char c = 'a';
		while (higherOrderNodeNames.contains(newName + "-" + c))
			c++;
		newName += "-" + c;
		higherOrderNodeNames.add(newName);

		higherOrderNode.setName(newName);
		higherOrderNode.setBindingType(node.getBindingType());
		higherOrderNode.setDomainType(node.getDomainType());
		higherOrderNode.setType(node.getType());
		higherOrderNode.getAttributeAssignments().addAll(EcoreUtil.copyAll(node.getAttributeAssignments()));

		super.getNodes().add(higherOrderNode);

		ComponentSpecificRuleElement componentNode = component.getComponentSpecificRuleElement(node);
		componentElt2higherOrderElt.put(componentNode, higherOrderNode);
	}

	private void createNewHigherOrderEdge(HigherOrderRuleComponent component, TGGEdge edge) {
		TGGEdge higherOrderEdge = IBeXTGGModelFactory.eINSTANCE.createTGGEdge();

		TGGNode hoSrcNode = (TGGNode) componentElt2higherOrderElt.get(component.getComponentSpecificRuleElement((TGGNode) edge.getSource()));
		TGGNode hoTrgNode = (TGGNode) componentElt2higherOrderElt.get(component.getComponentSpecificRuleElement((TGGNode) edge.getTarget()));

		higherOrderEdge.setSource(hoSrcNode);
		higherOrderEdge.setTarget(hoTrgNode);

		if (hoSrcNode instanceof TGGCorrespondence hoCorrNode) {
			switch (hoTrgNode.getDomainType()) {
				case SOURCE -> hoCorrNode.setSource(hoTrgNode);
				case TARGET -> hoCorrNode.setTarget(hoTrgNode);
				default -> throw new IllegalArgumentException("Unexpected value: " + hoTrgNode.getDomainType());
			}
		}

		String newName = higherOrderEdge.getSource().getName() + "__" + edge.getType().getName() + "__" + higherOrderEdge.getTarget().getName();

		higherOrderEdge.setName(newName);
		higherOrderEdge.setBindingType(edge.getBindingType());
		higherOrderEdge.setDomainType(edge.getDomainType());
		higherOrderEdge.setType(edge.getType());

		super.getEdges().add(higherOrderEdge);

		ComponentSpecificRuleElement componentEdge = component.getComponentSpecificRuleElement(edge);
		componentElt2higherOrderElt.put(componentEdge, higherOrderEdge);
	}

	private void adaptAttributeAssignments(TGGRule rule, HigherOrderRuleComponent component) {
		rule.getNodes().stream() //
				.filter(n -> n.getBindingType() == BindingType.CREATE) //
				.map(n -> {
					ComponentSpecificRuleElement compSpecRuleElt = component.getComponentSpecificRuleElement((TGGNode) n);
					return (TGGNode) componentElt2higherOrderElt.get(compSpecRuleElt);
				}) //
				.flatMap(n -> n.getAttributeAssignments().stream()) //
				.filter(e -> e.getValue() instanceof IBeXAttributeValue) //
				.map(e -> (IBeXAttributeValue) e.getValue()) //
				.forEach(attrValue -> {
					ComponentSpecificRuleElement compSpecRuleElt = component.getComponentSpecificRuleElement((TGGNode) attrValue.getNode());
					TGGNode newNode = (TGGNode) componentElt2higherOrderElt.get(compSpecRuleElt);
					if (newNode != null)
						attrValue.setNode(newNode);
				});

		// Edge case: higher-order rules are able to create multiple rule applications at once. This causes
		// in-place attribute assignment expressions to reference green nodes. In case of utilizing
		// higher-order rules to create short-cut rules, green nodes with such in-place attribute
		// expressions may turn to context nodes, but it's referenced node doesn't. This creates a scenario
		// where the in-place attribute assignment expression has to be converted to a CSP to be applied
		// properly:

		Collection<IBeXAttributeAssignment> toBeDeletedAttrAssignments = new LinkedList<>();
		for (TGGNode node : TGGFilterUtil.filterNodes(nodes, BindingType.CREATE)) {
			for (var attributeAssignment : node.getAttributeAssignments()) {
				if (attributeAssignment.getValue() instanceof IBeXAttributeValue attrValue) {
					if (((TGGNode) attrValue.getNode()).getBindingType() == BindingType.CREATE) {
						toBeDeletedAttrAssignments.add(attributeAssignment);
						createEqualityCSP(rule, node, attributeAssignment.getAttribute(), EcoreUtil.copy(attrValue));
					}
				}
			}
		}
		toBeDeletedAttrAssignments.forEach(e -> EcoreUtil.delete(e));
	}

	private void createEqualityCSP(TGGRule rule, TGGNode lhsNode, EAttribute lhsAttr, IBeXAttributeValue rhsAttributeValue) {
		IBeXAttributeValue lhsAttributeValue = IBeXCoreModelFactory.eINSTANCE.createIBeXAttributeValue();
		lhsAttributeValue.setAttribute(lhsAttr);
		lhsAttributeValue.setNode(lhsNode);

		TGGAttributeConstraintDefinition attrConstraintDef = getAttrConstraintDef(lhsAttr, (TGGModel) rule.eContainer().eContainer());
		TGGAttributeConstraintParameterDefinition lhsParamDef;
		TGGAttributeConstraintParameterDefinition rhsParamDef;
		try {
			lhsParamDef = attrConstraintDef.getParameterDefinitions().get(0);
			rhsParamDef = attrConstraintDef.getParameterDefinitions().get(1);
		} catch (IndexOutOfBoundsException e) {
			throw new RuntimeException("This attribute constraint definition must have two parameter definitions!", e);
		}

		var lhsParameterValue = CSPFactory.eINSTANCE.createTGGAttributeConstraintParameterValue();
		lhsParameterValue.setParameterDefinition(lhsParamDef);
		lhsParameterValue.setExpression(lhsAttributeValue);
		
		var rhsParameterValue = CSPFactory.eINSTANCE.createTGGAttributeConstraintParameterValue();
		rhsParameterValue.setParameterDefinition(rhsParamDef);
		rhsParameterValue.setExpression(rhsAttributeValue);
		
		TGGAttributeConstraint attrConstraint = CSPFactory.eINSTANCE.createTGGAttributeConstraint();
		attrConstraint.getParameters().add(lhsParameterValue);
		attrConstraint.getParameters().add(rhsParameterValue);
		attrConstraint.setDefinition(attrConstraintDef);

		var attributeConstraintSet = ((TGGPattern) this.getPrecondition()).getAttributeConstraints();
		attributeConstraintSet.getParameters().add(lhsParameterValue);
		attributeConstraintSet.getParameters().add(rhsParameterValue);
		attributeConstraintSet.getTggAttributeConstraints().add(attrConstraint);
	}

	private TGGAttributeConstraintDefinition getAttrConstraintDef(EAttribute attr, TGGModel tggModel) {
		String defName = switch (attr.getEAttributeType().getName()) {
			case "EString" -> "eq_string";
			case "EInt" -> "eq_int";
			case "EFloat" -> "eq_float";
			case "EDouble" -> "eq_double";
			case "ELong" -> "eq_long";
			case "EChar" -> "eq_char";
			case "EBoolean" -> "eq_boolean";
			default -> throw new IllegalArgumentException("Unexpected value: " + attr.getEAttributeType());
		};
		// FIXME determine predefined library
		for (TGGAttributeConstraintDefinition def : tggModel.getAttributeConstraintDefinitionLibraries().get(0).getTggAttributeConstraintDefinitions()) {
			if (def.getName().equals(defName))
				return def;
		}
		return null;
	}

	private void transferAttrCondLibrary(TGGRule rule, HigherOrderRuleComponent component) {
		var copiedConstraintSet = EcoreUtil.copy(((TGGPattern) this.getPrecondition()).getAttributeConstraints());

		for (var parameterValue : copiedConstraintSet.getParameters()) {
			if (parameterValue.getExpression() instanceof IBeXAttributeValue attributeValue) {
				ComponentSpecificRuleElement componentNode = component.getComponentSpecificRuleElement((TGGNode) attributeValue.getNode());
				TGGNode higherOrderObjVar = (TGGNode) componentElt2higherOrderElt.get(componentNode);
				if (higherOrderObjVar == null)
					throw new RuntimeException("Inconsistent higher-order rule construction! Cannot find rule element.");
				attributeValue.setNode(higherOrderObjVar);
			}
		}

		TGGPattern preConditionPattern = (TGGPattern) super.getPrecondition();
		if (preConditionPattern.getAttributeConstraints() == null) {
			preConditionPattern.setAttributeConstraints(copiedConstraintSet);
		} else {
			preConditionPattern.getAttributeConstraints().getParameters().addAll(copiedConstraintSet.getParameters());
			preConditionPattern.getAttributeConstraints().getTggAttributeConstraints().addAll(copiedConstraintSet.getTggAttributeConstraints());
		}
	}

	private void transferNACs(TGGRule rule, HigherOrderRuleComponent component) {
		if (rule.getNacs().isEmpty())
			return;

		Collection<NAC> copiedNacs = EcoreUtil.copyAll(rule.getNacs());
		for (NAC nac : copiedNacs) {
			// TODO check if this works
			for (TGGNode nacNode : nac.getNodes()) {
				TGGNode ruleNode = component.getNodeFromName(nacNode.getName());
				if (ruleNode != null) {
					TGGNode hoNode = HigherOrderSupport.getHigherOrderElement(component, ruleNode);
					nacNode.setName(hoNode.getName());
				}
			}
		}
	}

}
