package org.emoflon.ibex.tgg.operational.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.gervarro.democles.specification.emf.Constant;
import org.gervarro.democles.specification.emf.ConstraintParameter;
import org.gervarro.democles.specification.emf.ConstraintVariable;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.Attribute;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypeFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable;
import org.gervarro.democles.specification.emf.constraint.relational.RelationalConstraint;
import org.gervarro.democles.specification.emf.constraint.relational.RelationalConstraintFactory;

import language.TGGRuleNode;
import language.basic.expressions.TGGAttributeExpression;
import language.basic.expressions.TGGEnumExpression;
import language.basic.expressions.TGGExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;

public class DemoclesAttributeHelper {

	private final static String ATTR_STRING = "_attr_";

	private Map<EMFVariable, Attribute> varToAttr;

	private Map<EMFVariable, List<Pair<ConstraintVariable, RelationalConstraint>>> varToVar;

	public DemoclesAttributeHelper() {
		varToAttr = new HashMap<>();
		varToVar = new HashMap<>();
	}

	protected void extractAttributeVariables(TGGRuleNode node, EMFVariable nodeVar) {
		for (TGGInplaceAttributeExpression attrExpr : node.getAttrExpr()) {
			// extract the expression and create a new variable for it
			EMFVariable valueVar = EMFTypeFactory.eINSTANCE.createEMFVariable();
			TGGExpression expr = attrExpr.getValueExpr();
			if (expr instanceof TGGAttributeExpression) {
				TGGAttributeExpression aExpr = (TGGAttributeExpression) expr;
				valueVar.setName(node.getName() + ATTR_STRING + aExpr.getAttribute().getName());
				valueVar.setEClassifier(aExpr.getAttribute().getEAttributeType());

				// create an emf variable for the attribute field
				EMFVariable var = EMFTypeFactory.eINSTANCE.createEMFVariable();
				var.setEClassifier(expr.getParameterDefinition().getType());
				String varName = node.getName() + ATTR_STRING + var.getEClassifier().getName();
				var.setName(varName);

				Optional<EMFVariable> entry = varToVar.keySet().stream().filter(v -> v.getName().equals(varName)).findFirst();

				if (entry.isPresent()) {
					var = entry.get();
				}

				varToAttr.putIfAbsent(var, extractAttribute(nodeVar, var, attrExpr.getAttribute()));
				List<Pair<ConstraintVariable, RelationalConstraint>> list = varToVar.getOrDefault(var, new ArrayList<Pair<ConstraintVariable, RelationalConstraint>>());
				list.add(Pair.of(valueVar, extractRelationalConstraint(valueVar, var, attrExpr.getOperator())));
				varToVar.put(var, list);
			}
		}
	}

	protected void extractConstants(TGGRuleNode node, EMFVariable nodeVar) {
		for (TGGInplaceAttributeExpression attrExpr : node.getAttrExpr()) {
			// create a new variable for the constant
			Constant constant = SpecificationFactory.eINSTANCE.createConstant();
			TGGExpression expr = attrExpr.getValueExpr();
			if (expr instanceof TGGLiteralExpression) {
				TGGLiteralExpression tle = (TGGLiteralExpression) attrExpr.getValueExpr();
				constant.setValue(convertLiteral(tle.getValue(), attrExpr.getAttribute().getEAttributeType()));
			} else if (expr instanceof TGGEnumExpression) {
				constant.setValue(((TGGEnumExpression) attrExpr.getValueExpr()).getLiteral());
			} else
				continue;

			// create an emf variable for the attribute field
			EMFVariable var = EMFTypeFactory.eINSTANCE.createEMFVariable();
			var.setEClassifier(attrExpr.getAttribute().getEAttributeType());
			String varName = node.getName() + ATTR_STRING + var.getEClassifier().getName();
			var.setName(varName);

			Optional<EMFVariable> entry = varToVar.keySet().stream().filter(v -> v.getName().equals(varName)).findFirst();
			if (entry.isPresent()) {
				var = entry.get();
			}

			varToAttr.putIfAbsent(var, extractAttribute(nodeVar, var, attrExpr.getAttribute()));
			List<Pair<ConstraintVariable, RelationalConstraint>> list = varToVar.getOrDefault(var, new ArrayList<Pair<ConstraintVariable, RelationalConstraint>>());
			list.add(Pair.of(constant, extractRelationalConstraint(var, constant, attrExpr.getOperator())));
			varToVar.put(var, list);
		}
	}

	protected Attribute extractAttribute(EMFVariable from, EMFVariable to, EAttribute attribute) {
		Attribute attr = EMFTypeFactory.eINSTANCE.createAttribute();
		ConstraintParameter parameter = SpecificationFactory.eINSTANCE.createConstraintParameter();
		attr.getParameters().add(parameter);
		parameter.setReference(from);
		ConstraintParameter parameter2 = SpecificationFactory.eINSTANCE.createConstraintParameter();
		attr.getParameters().add(parameter2);
		parameter2.setReference(to);
		attr.setEModelElement(attribute);
		return attr;
	}

	protected RelationalConstraint extractRelationalConstraint(ConstraintVariable from, ConstraintVariable to, TGGAttributeConstraintOperators operator) {
		RelationalConstraint constraint;
		switch (operator) {
		case EQUAL:
			constraint = RelationalConstraintFactory.eINSTANCE.createEqual();
			break;
		case GR_EQUAL:
			constraint = RelationalConstraintFactory.eINSTANCE.createLargerOrEqual();
			break;
		case GREATER:
			constraint = RelationalConstraintFactory.eINSTANCE.createLarger();
			break;
		case LE_EQUAL:
			constraint = RelationalConstraintFactory.eINSTANCE.createSmallerOrEqual();
			break;
		case LESSER:
			constraint = RelationalConstraintFactory.eINSTANCE.createSmaller();
			break;
		case UNEQUAL:
			constraint = RelationalConstraintFactory.eINSTANCE.createUnequal();
			break;
		default:
			return null;
		}
		ConstraintParameter parameter = SpecificationFactory.eINSTANCE.createConstraintParameter();
		constraint.getParameters().add(parameter);
		parameter.setReference(from);
		ConstraintParameter parameter2 = SpecificationFactory.eINSTANCE.createConstraintParameter();
		constraint.getParameters().add(parameter2);
		parameter2.setReference(to);
		return constraint;
	}

	protected void resolveAttributeVariables(Collection<EMFVariable> nodeVars) {
		for (EMFVariable key : varToVar.keySet()) {
			for (Pair<ConstraintVariable, RelationalConstraint> value : varToVar.get(key)) {
				if (value.getLeft() instanceof EMFVariable) {
					EMFVariable var = (EMFVariable) value.getLeft();

					Stream<EMFVariable> varStream = varToAttr.keySet().stream().filter(k -> k.getName().equals(var.getName()));
					Optional<EMFVariable> valueAttrVarOptional = varStream.findFirst();
					if (!valueAttrVarOptional.isPresent()) {
						Optional<EMFVariable> valueAttrNode = nodeVars.stream().filter(v -> v.getName().equals(var.getName().split(ATTR_STRING)[0])).findFirst();
						if (!valueAttrNode.isPresent()) {
							// makes this exception more specific
							throw new RuntimeException("Detected a AttributeValueExpression that referenced a non-existing ObjectVariable!");
						}
//						varToAttr.put(var, extractAttribute(valueAttrNode.get(), var, (EDataType) var.getEClassifier()));
					} else {
						EMFVariable valueAttrVar = valueAttrVarOptional.get();
						Collection<Pair<ConstraintVariable, RelationalConstraint>> varCollection = varToVar.get(var);

						// correct the entities in this collection and replace
						// the duplicate variable with valueAttrVar
						List<Pair<ConstraintVariable, RelationalConstraint>> correctedList = new ArrayList<>();
						for (Pair<ConstraintVariable, RelationalConstraint> p : varCollection) {
							ConstraintParameter constraintParameter = p.getRight().getParameters().stream().filter(para -> para.getReference() instanceof EMFVariable).filter(para -> ((EMFVariable) para.getReference()).getName().equals(valueAttrVar.getName())).findFirst()
									.get();
							
							constraintParameter.setReference(valueAttrVar);
							correctedList.add(Pair.of(valueAttrVar, p.getRight()));
						}
						varToVar.put(var, correctedList);
					}
				}
			}
		}
	}
	
	protected Collection<Attribute> getAttributes() {
		return varToAttr.values();
	}
	
	protected Collection<RelationalConstraint> getRelationalConstraints() {
		return varToVar.values().stream().flatMap(Collection::stream).map(p -> p.getRight()).collect(Collectors.toList());
	}
	
	protected Collection<EMFVariable> getEMFVariables() {
		return varToAttr.keySet();
	}
	
	protected Collection<Constant> getConstants() {
		return varToVar.values().stream().flatMap(Collection::stream).map(p -> p.getLeft()).filter(e -> e instanceof Constant).map(e -> (Constant) e).collect(Collectors.toList());
	}
	
	protected Object convertLiteral(String literal, EDataType type) {
		if(type.equals(EcorePackage.Literals.ESTRING) ) {
			return literal;
		}
		if(type.equals(EcorePackage.Literals.EINT) ) {
			return Integer.parseInt(literal);
		}
		if(type.equals(EcorePackage.Literals.EFLOAT) ) {
			return Float.parseFloat(literal);
		}
		if(type.equals(EcorePackage.Literals.EDOUBLE) ) {
			return Double.parseDouble(literal);
		}
		if(type.equals(EcorePackage.Literals.EBOOLEAN) ) {
			return Boolean.parseBoolean(literal);
		}
		if(type.equals(EcorePackage.Literals.ECHAR) ) {
			return literal.length() == 0 ? '\0' : literal.charAt(0);
		}
		if(type.equals(EcorePackage.Literals.ELONG) ) {
			return Long.parseLong(literal);
		}
		
		throw new RuntimeException(type + " is not yet supported as a Datatype");
	}
	
	protected void clearAll() {
		varToAttr = new HashMap<>();
		varToVar = new HashMap<>();
	}
}