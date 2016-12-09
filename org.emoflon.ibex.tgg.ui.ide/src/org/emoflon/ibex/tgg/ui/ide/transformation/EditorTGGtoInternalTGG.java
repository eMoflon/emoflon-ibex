package org.emoflon.ibex.tgg.ui.ide.transformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.xtext.parser.antlr.UnorderedGroupHelper.Collector;
import org.moflon.tgg.mosl.tgg.Adornment;
import org.moflon.tgg.mosl.tgg.AttrCond;
import org.moflon.tgg.mosl.tgg.AttrCondDef;
import org.moflon.tgg.mosl.tgg.AttributeAssignment;
import org.moflon.tgg.mosl.tgg.AttributeConstraint;
import org.moflon.tgg.mosl.tgg.AttributeExpression;
import org.moflon.tgg.mosl.tgg.CorrType;
import org.moflon.tgg.mosl.tgg.CorrVariablePattern;
import org.moflon.tgg.mosl.tgg.EnumExpression;
import org.moflon.tgg.mosl.tgg.Expression;
import org.moflon.tgg.mosl.tgg.LinkVariablePattern;
import org.moflon.tgg.mosl.tgg.LiteralExpression;
import org.moflon.tgg.mosl.tgg.LocalVariable;
import org.moflon.tgg.mosl.tgg.ObjectVariablePattern;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.tgg.Param;
import org.moflon.tgg.mosl.tgg.ParamValue;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGG;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.inplaceAttributes.InplaceAttributesFactory;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;
import language.basic.expressions.ExpressionsFactory;
import language.basic.expressions.TGGAttributeExpression;
import language.basic.expressions.TGGEnumExpression;
import language.basic.expressions.TGGExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.basic.expressions.TGGParamValue;
import language.csp.CspFactory;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;
import language.csp.TGGAttributeVariable;
import language.csp.definition.DefinitionFactory;
import language.csp.definition.TGGAttributeConstraintAdornment;
import language.csp.definition.TGGAttributeConstraintDefinition;
import language.csp.definition.TGGAttributeConstraintDefinitionLibrary;
import language.csp.definition.TGGAttributeConstraintParameterDefinition;

public class EditorTGGtoInternalTGG {

	private EcoreFactory ecoreFactory = EcoreFactory.eINSTANCE;
	private LanguageFactory tggFactory = LanguageFactory.eINSTANCE;
	private HashMap<EObject, EObject> xtextToTGG = new HashMap<>();
	private HashMap<EObject, EObject> tggToXtext = new HashMap<>();

	public TGGProject convertXtextTGG(TripleGraphGrammarFile xtextTGG) {
		EPackage corrPackage = createCorrModel(xtextTGG);
		TGG tgg = createTGG(xtextTGG);
		tgg.setCorr(corrPackage);
		return new TGGProject(corrPackage, tgg);
	}

	private TGG createTGG(TripleGraphGrammarFile xtextTGG) {
		TGG tgg = tggFactory.createTGG();
		tgg.setName(xtextTGG.getSchema().getName());
		tgg.getSrc().addAll(xtextTGG.getSchema().getSourceTypes());
		tgg.getTrg().addAll(xtextTGG.getSchema().getTargetTypes());
		tgg.setAttributeConstraintDefinitionLibrary(createAttributeConditionDefinitionLibrary(xtextTGG.getSchema().getAttributeCondDefs()));
		
		map(xtextTGG, tgg);

		for (Rule xtextRule : xtextTGG.getRules()) {
			TGGRule tggRule = tggFactory.createTGGRule();
			tggRule.setName(xtextRule.getName());
			tggRule.setAbstract(xtextRule.isAbstractRule());
			tgg.getRules().add(tggRule);
			map(xtextRule, tggRule);

			tggRule.getNodes().addAll(createTGGRuleNodes(xtextRule.getSourcePatterns(), DomainType.SRC));
			tggRule.getNodes().addAll(createTGGRuleNodes(xtextRule.getTargetPatterns(), DomainType.TRG));
			tggRule.getNodes().addAll(createTGGRuleNodesFromCorrOVs(xtextRule.getCorrespondencePatterns()));

			tggRule.getEdges().addAll(createTGGRuleEdges(tggRule));
			
			tggRule.setAttributeConditionLibrary(createAttributeConditionLibrary(xtextRule.getAttrConditions()));
		}
		return addOppositeEdges(tgg);
	}
	
	private TGGAttributeConstraintDefinitionLibrary createAttributeConditionDefinitionLibrary(EList<AttrCondDef> attrCondDefs) {
		TGGAttributeConstraintDefinitionLibrary library = DefinitionFactory.eINSTANCE.createTGGAttributeConstraintDefinitionLibrary();
		
		for(AttrCondDef attrCondDef : attrCondDefs) {
			TGGAttributeConstraintDefinition definition = DefinitionFactory.eINSTANCE.createTGGAttributeConstraintDefinition();
			definition.setName(attrCondDef.getName());
			definition.setUserDefined(attrCondDef.isUserDefined());
			definition.getGenAdornments().addAll(attrCondDef.getAllowedGenAdornments().stream().map(adornment -> creatAttributeConditionAdornment(adornment)).collect(Collectors.toList()));
			definition.getSyncAdornments().addAll(attrCondDef.getAllowedSyncAdornments().stream().map(adornment -> creatAttributeConditionAdornment(adornment)).collect(Collectors.toList()));
			definition.getParameterDefinitions().addAll(attrCondDef.getParams().stream().map(parameterDef -> createAttributeConstraintParameterDefinition(parameterDef)).collect(Collectors.toList()));
			library.getTggAttributeConstraintDefinitions().add(definition);
			map(attrCondDef, definition);
		}
		
		return library;
	}
	
	private TGGAttributeConstraintLibrary createAttributeConditionLibrary(Collection<AttrCond> attrConds) {
		TGGAttributeConstraintLibrary library = CspFactory.eINSTANCE.createTGGAttributeConstraintLibrary();
		Map<Integer, TGGParamValue> paramValues = new HashMap<Integer, TGGParamValue>();
		
		library.getTggAttributeConstraints().addAll(attrConds.stream().map(attrCond -> createAttributeConstraint(attrCond, paramValues)).collect(Collectors.toList()));
		library.getParameterValues().addAll(paramValues.values());
		
		return library;
	}
	
	private TGGAttributeConstraint createAttributeConstraint(AttrCond attrCond, Map<Integer, TGGParamValue> foundValues) {
		TGGAttributeConstraint attributeConstraint = CspFactory.eINSTANCE.createTGGAttributeConstraint();
		attributeConstraint.setDefinition((TGGAttributeConstraintDefinition) xtextToTGG.get(attrCond.getName()));
		for(ParamValue paramValue : attrCond.getValues()) {
			TGGParamValue newTGGParamValue = createParamValue(paramValue);
			TGGParamValue checkedEntry = foundValues.putIfAbsent(newTGGParamValue.hashCode(), newTGGParamValue);
			attributeConstraint.getParameters().add(checkedEntry != null ? checkedEntry : newTGGParamValue);
			attributeConstraint.setDefinition((TGGAttributeConstraintDefinition) xtextToTGG.get(attrCond.getName()));
		}
		return attributeConstraint;
	}
	
	private TGGParamValue createParamValue(ParamValue paramValue) {
		if(paramValue instanceof LocalVariable) {
			TGGAttributeVariable attrVariable = CspFactory.eINSTANCE.createTGGAttributeVariable();
			attrVariable.setName(((LocalVariable) paramValue).getName());
			return attrVariable;
		}
		if(paramValue instanceof Expression) {
			return createExpression((Expression) paramValue);
		}
		return null;
	}
	
	private TGGAttributeConstraintAdornment creatAttributeConditionAdornment(Adornment adornment) {
		TGGAttributeConstraintAdornment tggAdornment = DefinitionFactory.eINSTANCE.createTGGAttributeConstraintAdornment();
		tggAdornment.getValue().addAll(adornment.getValue());
		return tggAdornment;
	}
	
	private TGGAttributeConstraintParameterDefinition createAttributeConstraintParameterDefinition(Param parameter) {
		TGGAttributeConstraintParameterDefinition parameterDefinition = DefinitionFactory.eINSTANCE.createTGGAttributeConstraintParameterDefinition();
		parameterDefinition.setName(parameter.getParamName());
		parameterDefinition.setType(parameter.getType());
		map(parameter, parameterDefinition);
		return parameterDefinition;
	}

	private Collection<TGGRuleEdge> createTGGRuleEdges(TGGRule tggRule) {
		ArrayList<TGGRuleEdge> result = new ArrayList<>();

		for (TGGRuleNode node : tggRule.getNodes()) {
			if (node.getDomainType() == DomainType.SRC || node.getDomainType() == DomainType.TRG) {
				ObjectVariablePattern ov = (ObjectVariablePattern) tggToXtext.get(node);
				for (LinkVariablePattern lv : ov.getLinkVariablePatterns()) {
					TGGRuleEdge tggEdge = tggFactory.createTGGRuleEdge();
					tggEdge.setType(lv.getType());
					tggEdge.setSrcNode(node);
					tggEdge.setTrgNode((TGGRuleNode) xtextToTGG.get(lv.getTarget()));
					tggEdge.setBindingType(getBindingType(lv.getOp()));
					tggEdge.setDomainType(node.getDomainType());
					tggEdge.setName(tggEdge.getSrcNode().getName() + "__" + tggEdge.getType().getName() + "__"
							+ tggEdge.getTrgNode().getName() + "_eMoflonEdge");
					map(lv, tggEdge);
					result.add(tggEdge);
				}
			}
		}

		return result;
	}

	private Collection<TGGRuleNode> createTGGRuleNodesFromCorrOVs(Collection<CorrVariablePattern> corrOVs) {
		ArrayList<TGGRuleNode> result = new ArrayList<>();
		for (CorrVariablePattern cv : corrOVs) {
			TGGRuleCorr corrNode = tggFactory.createTGGRuleCorr();
			corrNode.setName(cv.getName());
			corrNode.setType((EClass) xtextToTGG.get(cv.getType()));
			corrNode.setBindingType(getBindingType(cv.getOp()));
			corrNode.setDomainType(DomainType.CORR);
			corrNode.setSource((TGGRuleNode) xtextToTGG.get(cv.getSource()));
			corrNode.setTarget((TGGRuleNode) xtextToTGG.get(cv.getTarget()));
			result.add(corrNode);
			map(cv, corrNode);
		}
		return result;
	}

	private Collection<TGGRuleNode> createTGGRuleNodes(Collection<ObjectVariablePattern> ovs, DomainType domainType) {
		ArrayList<TGGRuleNode> result = new ArrayList<>();
		Map<TGGRuleNode, ObjectVariablePattern> rule2patMap = new HashMap<TGGRuleNode, ObjectVariablePattern>();
		for (ObjectVariablePattern ov : ovs) {
			TGGRuleNode tggNode = getTGGRuleNode(ov);
			tggNode.setDomainType(domainType);
			rule2patMap.put(tggNode, ov);
			result.add(tggNode);
		}
		
		// This has to be done separately since some attribute expression may reference nodes that are not yet existent
		for (TGGRuleNode tggNode : result) {
			ObjectVariablePattern ov = rule2patMap.get(tggNode);
			tggNode.getAttrExpr().addAll(ov.getAttributeAssignments().stream().map(assignment -> createTGGInplaceAttributeExpression(result, tggNode, assignment)).collect(Collectors.toList()));
			tggNode.getAttrExpr().addAll(ov.getAttributeConstraints().stream().map(constraint -> createTGGInplaceAttributeExpression(result, tggNode, constraint)).collect(Collectors.toList()));
		}
		
		return result;
	}

	private TGGInplaceAttributeExpression createTGGInplaceAttributeExpression(Collection<TGGRuleNode> allNodes, TGGRuleNode node, AttributeConstraint constraint) {
		TGGInplaceAttributeExpression tiae = InplaceAttributesFactory.eINSTANCE.createTGGInplaceAttributeExpression();
		tiae.setAttribute(node.getType().getEAttributes().stream().filter(attr -> attr.getName().equals(constraint.getAttribute().getName())).findFirst().get());
		tiae.setValueExpr(createExpression(constraint.getValueExp()));
		tiae.setOperator(convertOperator(constraint.getOp()));
		return tiae;
	}
	
	private TGGInplaceAttributeExpression createTGGInplaceAttributeExpression(Collection<TGGRuleNode> allNodes, TGGRuleNode node, AttributeAssignment assignment) {
		TGGInplaceAttributeExpression tiae = InplaceAttributesFactory.eINSTANCE.createTGGInplaceAttributeExpression();
		tiae.setAttribute(node.getType().getEAttributes().stream().filter(attr -> attr.getName().equals(assignment.getAttribute().getName())).findFirst().get());
		tiae.setValueExpr(createExpression(assignment.getValueExp()));
		tiae.setOperator(TGGAttributeConstraintOperators.EQUAL);
		return tiae;
	}
	
	private TGGExpression createExpression(org.moflon.tgg.mosl.tgg.Expression expression) {
		if (expression instanceof LiteralExpression) {
			LiteralExpression le = (LiteralExpression) expression;
			TGGLiteralExpression tle = ExpressionsFactory.eINSTANCE.createTGGLiteralExpression();
			tle.setValue(le.getValue());
			return tle;
		}
		if (expression instanceof EnumExpression) {
			EnumExpression ee = (EnumExpression) expression;
			TGGEnumExpression tee = ExpressionsFactory.eINSTANCE.createTGGEnumExpression();
			tee.setEenum(ee.getEenum());
			tee.setLiteral(ee.getLiteral());
			return tee;
		}
		if (expression instanceof AttributeExpression) {
			AttributeExpression ae = (AttributeExpression) expression;
			TGGAttributeExpression tae = ExpressionsFactory.eINSTANCE.createTGGAttributeExpression();
			tae.setAttribute(ae.getAttribute());
			tae.setObjectVar((TGGRuleNode) xtextToTGG.get(ae.getObjectVar()));
			return tae;
		}
		return null;
	}
	
	// TODO!
	private TGGAttributeConstraintOperators convertOperator(String operator) {
		switch(operator) {
		case "==": 
		case ":=": return TGGAttributeConstraintOperators.EQUAL;
		case "!=": return TGGAttributeConstraintOperators.UNEQUAL;
		case ">=": return TGGAttributeConstraintOperators.GR_EQUAL;
		case "<=": return TGGAttributeConstraintOperators.LE_EQUAL;
		case ">":  return TGGAttributeConstraintOperators.GREATER;
		case "<":  return TGGAttributeConstraintOperators.LESSER;
		default: return null;
		}
	}
	
	private TGGRuleNode getTGGRuleNode(ObjectVariablePattern ov) {
		TGGRuleNode tggNode = tggFactory.createTGGRuleNode();
		tggNode.setName(ov.getName());
		tggNode.setType(ov.getType());
		tggNode.setBindingType(getBindingType(ov.getOp()));
		map(ov, tggNode);
		return tggNode;
	}

	private BindingType getBindingType(Operator op) {
		if (op == null)
			return BindingType.CONTEXT;
		String value = op.getValue();
		if ("++".equals(value))
			return BindingType.CREATE;
		if ("!".equals(value))
			return BindingType.NEGATIVE;
		return null;
	}

	private EPackage createCorrModel(TripleGraphGrammarFile xtextTGG) {

		EPackage corrModel = ecoreFactory.createEPackage();

		corrModel.setName(xtextTGG.getSchema().getName());
		corrModel.setNsPrefix(xtextTGG.getSchema().getName());
		corrModel.setNsURI("platform:/plugin/" + corrModel.getName() + "/model/" + corrModel.getName() + ".ecore");

		for (CorrType ct : xtextTGG.getSchema().getCorrespondenceTypes()) {
			corrModel.getEClassifiers().add(createEClass(ct));
		}

		for (CorrType ct : xtextTGG.getSchema().getCorrespondenceTypes()) {

			if (ct.getSuper() != null) {
				((EClass) xtextToTGG.get(ct)).getESuperTypes().add((EClass) xtextToTGG.get(ct.getSuper()));
			}
		}

		return corrModel;
	}

	private EClass createEClass(CorrType ct) {
		EClass corrClass = ecoreFactory.createEClass();
		corrClass.setName(ct.getName());

		EReference srcRef = ecoreFactory.createEReference();
		srcRef.setName("source");
		srcRef.setLowerBound(0);
		srcRef.setUpperBound(1);
		srcRef.setEType(ct.getSource());
		corrClass.getEStructuralFeatures().add(srcRef);

		EReference trgRef = ecoreFactory.createEReference();
		trgRef.setName("target");
		trgRef.setLowerBound(0);
		trgRef.setUpperBound(1);
		trgRef.setEType(ct.getTarget());
		corrClass.getEStructuralFeatures().add(trgRef);

		map(ct, corrClass);

		return corrClass;
	}

	private void map(EObject xtextObject, EObject tggObject) {
		xtextToTGG.put(xtextObject, tggObject);
		tggToXtext.put(tggObject, xtextObject);
	}

	private TGG addOppositeEdges(TGG tggModel) {
		tggModel.getRules().stream().flatMap(r -> r.getEdges().stream()).forEach(e -> {
			if (e.getType().getEOpposite() != null) {
				EReference oppositeRef = e.getType().getEOpposite();
				TGGRule rule = (TGGRule) e.eContainer();
				if (rule.getEdges().stream().noneMatch(oe -> oe.getType().equals(oppositeRef)
						&& oe.getSrcNode().equals(e.getTrgNode()) && oe.getTrgNode().equals(e.getSrcNode()))) {
					TGGRuleEdge oppositeEdge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
					oppositeEdge.setBindingType(e.getBindingType());
					oppositeEdge.setDomainType(e.getDomainType());
					oppositeEdge.setType(oppositeRef);
					oppositeEdge.setSrcNode(e.getTrgNode());
					oppositeEdge.setTrgNode(e.getSrcNode());
					oppositeEdge.setName(oppositeEdge.getSrcNode().getName() + "__" + oppositeRef.getName()
							+ "__" + oppositeEdge.getTrgNode().getName() + "_eMoflonEdge");
					rule.getEdges().add(oppositeEdge);
				}
				
			}
		});
		return tggModel;
	}
}
