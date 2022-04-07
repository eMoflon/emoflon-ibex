package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

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
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.util.ConsoleUtil;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;

import language.BindingType;
import language.LanguageFactory;
import language.NAC;
import language.TGG;
import language.TGGAttributeConstraint;
import language.TGGAttributeConstraintDefinition;
import language.TGGAttributeConstraintLibrary;
import language.TGGAttributeConstraintParameterDefinition;
import language.TGGAttributeExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGParamValue;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import language.impl.LanguageFactoryImpl;
import language.impl.TGGRuleImpl;

public class HigherOrderTGGRule extends TGGRuleImpl {

	private final LinkedList<HigherOrderRuleComponent> components;
	private final Map<ITGGMatch, HigherOrderRuleComponent> match2component;

	protected HigherOrderTGGRule() {
		super();
		this.components = new LinkedList<>();
		this.match2component = new HashMap<>();

		initTGGRuleContents();
	}

	protected void addComponent(TGGRule rule, ITGGMatch match, Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping) {
		HigherOrderRuleComponent component = new HigherOrderRuleComponent(rule, contextMapping);
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
		public final Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping;
		private final Map<String, TGGRuleNode> name2ruleNode;

		private HigherOrderRuleComponent(TGGRule rule, Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping) {
			this.rule = rule;
			this.contextMapping = contextMapping;
			this.name2ruleNode = rule.getNodes().stream() //
					.collect(Collectors.toMap(n -> n.getName(), n -> n));
		}

		public ComponentSpecificRuleElement getComponentSpecificRuleElement(TGGRuleElement ruleElement) {
			if (rule.getNodes().contains(ruleElement) || rule.getEdges().contains(ruleElement))
				return new ComponentSpecificRuleElement(ruleElement, this);
			else
				return null;
		}

		public TGGRuleNode getNodeFromName(String nodeName) {
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
		adaptInplaceAttrExpr(rule, component);
		transferNACs(rule, component);
	}

	private void populateElts(TGGRule rule, HigherOrderRuleComponent component, Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping) {
		for (TGGRuleNode node : rule.getNodes()) {
			ComponentSpecificRuleElement componentNode = contextMapping.get(node);
			if (componentNode != null)
				transformHigherOrderNode(component, node, componentNode);
			else
				createNewHigherOrderNode(component, node);
		}
		for (TGGRuleEdge edge : rule.getEdges()) {
			ComponentSpecificRuleElement componentEdge = contextMapping.get(edge);
			if (componentEdge != null)
				transformHigherOrderEdge(component, edge, componentEdge);
			else
				createNewHigherOrderEdge(component, edge);
		}
	}

	private void transformHigherOrderNode(HigherOrderRuleComponent component, TGGRuleNode node, ComponentSpecificRuleElement mappedComponentNode) {
		TGGRuleNode higherOrderNode = (TGGRuleNode) componentElt2higherOrderElt.get(mappedComponentNode);
		if (higherOrderNode == null)
			throw new RuntimeException("Inconsistent context mapping!");

		ComponentSpecificRuleElement componentNode = component.getComponentSpecificRuleElement(node);
		componentElt2higherOrderElt.put(componentNode, higherOrderNode);
	}

	private void transformHigherOrderEdge(HigherOrderRuleComponent component, TGGRuleEdge edge, ComponentSpecificRuleElement mappedComponentNode) {
		TGGRuleEdge higherOrderEdge = (TGGRuleEdge) componentElt2higherOrderElt.get(mappedComponentNode);
		if (higherOrderEdge == null)
			throw new RuntimeException("Inconsistent context mapping!");

		ComponentSpecificRuleElement componentEdge = component.getComponentSpecificRuleElement(edge);
		componentElt2higherOrderElt.put(componentEdge, higherOrderEdge);
	}

	private void createNewHigherOrderNode(HigherOrderRuleComponent component, TGGRuleNode node) {
		TGGRuleNode higherOrderNode = (TGGRuleNode) LanguageFactory.eINSTANCE.create(node.eClass());

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
		higherOrderNode.getAttrExpr().addAll(EcoreUtil.copyAll(node.getAttrExpr()));

		super.getNodes().add(higherOrderNode);

		ComponentSpecificRuleElement componentNode = component.getComponentSpecificRuleElement(node);
		componentElt2higherOrderElt.put(componentNode, higherOrderNode);
	}

	private void createNewHigherOrderEdge(HigherOrderRuleComponent component, TGGRuleEdge edge) {
		TGGRuleEdge higherOrderEdge = LanguageFactoryImpl.eINSTANCE.createTGGRuleEdge();

		TGGRuleNode hoSrcNode = (TGGRuleNode) componentElt2higherOrderElt.get(component.getComponentSpecificRuleElement(edge.getSrcNode()));
		TGGRuleNode hoTrgNode = (TGGRuleNode) componentElt2higherOrderElt.get(component.getComponentSpecificRuleElement(edge.getTrgNode()));

		higherOrderEdge.setSrcNode(hoSrcNode);
		higherOrderEdge.setTrgNode(hoTrgNode);

		if (hoSrcNode instanceof TGGRuleCorr hoCorrNode) {
			switch (hoTrgNode.getDomainType()) {
				case SRC -> hoCorrNode.setSource(hoTrgNode);
				case TRG -> hoCorrNode.setTarget(hoTrgNode);
				default -> throw new IllegalArgumentException("Unexpected value: " + hoTrgNode.getDomainType());
			}
		}

		String newName = higherOrderEdge.getSrcNode().getName() + "__" + edge.getType().getName() + "__" + higherOrderEdge.getTrgNode().getName();

		higherOrderEdge.setName(newName);
		higherOrderEdge.setBindingType(edge.getBindingType());
		higherOrderEdge.setDomainType(edge.getDomainType());
		higherOrderEdge.setType(edge.getType());

		super.getEdges().add(higherOrderEdge);

		ComponentSpecificRuleElement componentEdge = component.getComponentSpecificRuleElement(edge);
		componentElt2higherOrderElt.put(componentEdge, higherOrderEdge);
	}

	private void adaptInplaceAttrExpr(TGGRule rule, HigherOrderRuleComponent component) {
		nodes.stream() //
				.flatMap(n -> n.getAttrExpr().stream()) //
				.filter(e -> e.getValueExpr() instanceof TGGAttributeExpression) //
				.map(e -> (TGGAttributeExpression) e.getValueExpr()) //
				.forEach(attrExpr -> {
					ComponentSpecificRuleElement compSpecRuleElt = component.getComponentSpecificRuleElement(attrExpr.getObjectVar());
					attrExpr.setObjectVar((TGGRuleNode) componentElt2higherOrderElt.get(compSpecRuleElt));
				});

		// Edge case: higher-order rules are able to create multiple rule applications at once. This causes
		// in-place attribute assignment expressions to reference green nodes. In case of utilizing
		// higher-order rules to create short-cut rules, green nodes with such in-place attribute
		// expressions may turn to context nodes, but it's referenced node doesn't. This creates a scenario
		// where the in-place attribute assignment expression has to be converted to a CSP to be applied
		// properly:

		Collection<TGGInplaceAttributeExpression> toBeDeletedInplAttrExpr = new LinkedList<>();
		for (TGGRuleNode node : TGGFilterUtil.filterNodes(nodes, BindingType.CREATE)) {
			for (TGGInplaceAttributeExpression inplAttrExpr : node.getAttrExpr()) {
				if (inplAttrExpr.getValueExpr()instanceof TGGAttributeExpression attrExpr) {
					if (attrExpr.getObjectVar().getBindingType() == BindingType.CREATE) {
						toBeDeletedInplAttrExpr.add(inplAttrExpr);
						createEqualityCSP(rule, node, inplAttrExpr.getAttribute(), EcoreUtil.copy(attrExpr));
					}
				}
			}
		}
		toBeDeletedInplAttrExpr.forEach(e -> EcoreUtil.delete(e));
	}

	private void createEqualityCSP(TGGRule rule, TGGRuleNode lhsNode, EAttribute lhsAttr, TGGAttributeExpression rhsAttrExpr) {
		TGGAttributeExpression lhsAttrExpr = LanguageFactory.eINSTANCE.createTGGAttributeExpression();
		lhsAttrExpr.setAttribute(lhsAttr);
		lhsAttrExpr.setObjectVar(lhsNode);

		TGGAttributeConstraintDefinition attrConstraintDef = getAttrConstraintDef(lhsAttr, (TGG) rule.eContainer());
		TGGAttributeConstraintParameterDefinition lhsParamDef;
		TGGAttributeConstraintParameterDefinition rhsParamDef;
		try {
			lhsParamDef = attrConstraintDef.getParameterDefinitions().get(0);
			rhsParamDef = attrConstraintDef.getParameterDefinitions().get(1);
		} catch (IndexOutOfBoundsException e) {
			throw new RuntimeException("This attribute constraint definition must have two parameter definitions!", e);
		}
		lhsAttrExpr.setParameterDefinition(lhsParamDef);
		rhsAttrExpr.setParameterDefinition(rhsParamDef);

		TGGAttributeConstraint attrConstraint = LanguageFactory.eINSTANCE.createTGGAttributeConstraint();
		attrConstraint.getParameters().add(lhsAttrExpr);
		attrConstraint.getParameters().add(rhsAttrExpr);
		attrConstraint.setDefinition(attrConstraintDef);

		rule.getAttributeConditionLibrary().getTggAttributeConstraints().add(attrConstraint);
	}

	private TGGAttributeConstraintDefinition getAttrConstraintDef(EAttribute attr, TGG tgg) {
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
		for (TGGAttributeConstraintDefinition def : tgg.getAttributeConstraintDefinitionLibrary().getTggAttributeConstraintDefinitions()) {
			if (def.getName().equals(defName))
				return def;
		}
		return null;
	}

	private void transferAttrCondLibrary(TGGRule rule, HigherOrderRuleComponent component) {
		TGGAttributeConstraintLibrary copiedLib = EcoreUtil.copy(rule.getAttributeConditionLibrary());

		for (TGGParamValue paramValue : copiedLib.getParameterValues()) {
			if (paramValue instanceof TGGAttributeExpression attrExpr) {
				ComponentSpecificRuleElement componentNode = component.getComponentSpecificRuleElement(attrExpr.getObjectVar());
				TGGRuleNode higherOrderObjVar = (TGGRuleNode) componentElt2higherOrderElt.get(componentNode);
				if (higherOrderObjVar == null)
					throw new RuntimeException("Inconsistent higher-order rule construction! Cannot find rule element.");
				attrExpr.setObjectVar(higherOrderObjVar);
			}
		}

		if (super.getAttributeConditionLibrary() == null) {
			super.setAttributeConditionLibrary(copiedLib);
		} else {
			super.getAttributeConditionLibrary().getParameterValues().addAll(copiedLib.getParameterValues());
			super.getAttributeConditionLibrary().getTggAttributeConstraints().addAll(copiedLib.getTggAttributeConstraints());
		}
	}

	private void transferNACs(TGGRule rule, HigherOrderRuleComponent component) {
		if (rule.getNacs().isEmpty())
			return;

		Collection<NAC> copiedNacs = EcoreUtil.copyAll(rule.getNacs());
		for (NAC nac : copiedNacs) {

			// TODO continue

		}
	}

}
