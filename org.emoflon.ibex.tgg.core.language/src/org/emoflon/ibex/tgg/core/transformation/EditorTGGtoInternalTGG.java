package org.emoflon.ibex.tgg.core.transformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.ide.admin.IbexTGGBuilder;
import org.moflon.tgg.mosl.tgg.Adornment;
import org.moflon.tgg.mosl.tgg.AttrCond;
import org.moflon.tgg.mosl.tgg.AttrCondDef;
import org.moflon.tgg.mosl.tgg.AttributeAssignment;
import org.moflon.tgg.mosl.tgg.AttributeConstraint;
import org.moflon.tgg.mosl.tgg.AttributeExpression;
import org.moflon.tgg.mosl.tgg.ComplementRule;
import org.moflon.tgg.mosl.tgg.ContextLinkVariablePattern;
import org.moflon.tgg.mosl.tgg.ContextObjectVariablePattern;
import org.moflon.tgg.mosl.tgg.CorrType;
import org.moflon.tgg.mosl.tgg.CorrVariablePattern;
import org.moflon.tgg.mosl.tgg.EnumExpression;
import org.moflon.tgg.mosl.tgg.Expression;
import org.moflon.tgg.mosl.tgg.LinkVariablePattern;
import org.moflon.tgg.mosl.tgg.LiteralExpression;
import org.moflon.tgg.mosl.tgg.LocalVariable;
import org.moflon.tgg.mosl.tgg.Nac;
import org.moflon.tgg.mosl.tgg.ObjectVariablePattern;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.tgg.Param;
import org.moflon.tgg.mosl.tgg.ParamValue;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.tgg.TggFactory;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;
import org.moflon.util.LogUtils;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.NAC;
import language.TGG;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
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
import language.inplaceAttributes.InplaceAttributesFactory;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;

public class EditorTGGtoInternalTGG {

	private EcoreFactory ecoreFactory = EcoreFactory.eINSTANCE;
	private LanguageFactory tggFactory = LanguageFactory.eINSTANCE;
	private HashMap<EObject, EObject> xtextToTGG = new HashMap<>();
	private HashMap<EObject, EObject> tggToXtext = new HashMap<>();
	private HashMap<String, EObject> xTextCorrToCorrClass = new HashMap<>();
	
	private static final Logger logger = Logger.getLogger(EditorTGGtoInternalTGG.class);
	public static final String INTERNAL_TGG_MODEL = EditorTGGtoInternalTGG.class.getName();

	private TGGProject convertXtextTGG(TripleGraphGrammarFile xtextTGG, TripleGraphGrammarFile flattenedXtextTGG) {
		EPackage corrPackage = createCorrModel(xtextTGG);
		TGG tgg = createTGG(xtextTGG);
		TGG flattenedTgg = createTGG(flattenedXtextTGG);
		tgg.setCorr(corrPackage);
		flattenedTgg.setCorr(corrPackage);
		return new TGGProject(corrPackage, tgg, flattenedTgg);
	}
	
	public Optional<TGGProject> generateInternalModels(TripleGraphGrammarFile xtextParsedTGG, TripleGraphGrammarFile flattenedXtextParsedTGG, IProject project) {
		Optional<TGGProject> tggProject = Optional.of(convertXtextTGG(xtextParsedTGG, flattenedXtextParsedTGG));

		tggProject.ifPresent(p -> {
			try {
				ResourceSet rs = xtextParsedTGG.eResource().getResourceSet();
				IFile corrFile = project.getFolder(IbexTGGBuilder.MODEL_FOLDER).getFile(project.getName() + IbexTGGBuilder.ECORE_FILE_EXTENSION);
				IbexTGGBuilder.saveModelInProject(corrFile, rs, p.getCorrPackage());
				IFile tggFile = project.getFolder(IbexTGGBuilder.MODEL_FOLDER).getFile(project.getName() + IbexTGGBuilder.INTERNAL_TGG_MODEL_EXTENSION);
				IbexTGGBuilder.saveModelInProject(tggFile, rs, p.getTggModel());
				IFile flattenedTggFile = project.getFolder(IbexTGGBuilder.MODEL_FOLDER).getFile(project.getName() + IbexTGGBuilder.INTERNAL_TGG_FLATTENED_MODEL_EXTENSION);
				IbexTGGBuilder.saveModelInProject(flattenedTggFile, rs, p.getFlattenedTggModel());
			} catch (IOException e) {
				LogUtils.error(logger, e);
			}
		});
		
		return tggProject;
	}

	private TGG createTGG(TripleGraphGrammarFile xtextTGG) {
		xtextToTGG = new HashMap<>();
		tggToXtext = new HashMap<>();
		
		TGG tgg = tggFactory.createTGG();
		tgg.setName(xtextTGG.getSchema().getName());
		tgg.getSrc().addAll(xtextTGG.getSchema().getSourceTypes());
		tgg.getTrg().addAll(xtextTGG.getSchema().getTargetTypes());
		tgg.setAttributeConstraintDefinitionLibrary(
				createAttributeConditionDefinitionLibrary(xtextTGG.getSchema().getAttributeCondDefs()));

		map(xtextTGG, tgg);
		
		translateXTextRulesToTGGRules(xtextTGG, tgg);
		translateXTextRuleRefinementsToTGGRuleRefinements(xtextTGG, tgg);
		translateXTextComplementRulesToTGGComplementRules(xtextTGG, tgg);
		translateXTextNacsToTGGNacs(xtextTGG, tgg);

		tgg = addOppositeEdges(tgg);

		return tgg;
	}
	
	private void translateXTextRulesToTGGRules(TripleGraphGrammarFile xtextTGG, TGG tgg) {	
		for (Rule xtextRule : xtextTGG.getRules()) {
			TGGRule tggRule = tggFactory.createTGGRule();
			tggRule.setName(xtextRule.getName());
			tggRule.setAbstract(xtextRule.isAbstractRule());
			tgg.getRules().add(tggRule);
			map(xtextRule, tggRule);

			tggRule.getNodes().addAll(createTGGRuleNodes(xtextRule.getSourcePatterns(), DomainType.SRC));
			tggRule.getNodes().addAll(createTGGRuleNodes(xtextRule.getTargetPatterns(), DomainType.TRG));
			tggRule.getNodes().addAll(createTGGRuleNodesFromCorrOVs(xtextRule.getCorrespondencePatterns()));

			tggRule.getEdges().addAll(createTGGRuleEdges(tggRule.getNodes()));

			tggRule.setAttributeConditionLibrary(createAttributeConditionLibrary(xtextRule.getAttrConditions()));
		}
	}
	
	private void translateXTextRuleRefinementsToTGGRuleRefinements(TripleGraphGrammarFile xtextTGG, TGG tgg) {
        for (TGGRule tggRule : tgg.getRules()) {
            tggRule.getRefines().addAll(((Rule)tggToXtext.get(tggRule)).getSupertypes().stream()
                                                                 .map(r -> (TGGRule)xtextToTGG.get(r))
                                                                 .collect(Collectors.toList()));
        }
	}

	private void translateXTextNacsToTGGNacs(TripleGraphGrammarFile xtextTGG, TGG tgg) {
        for (Nac xtextNac : xtextTGG.getNacs()) {
			NAC tggNac = LanguageFactory.eINSTANCE.createNAC();
			tggNac.setName(xtextNac.getName());
			TGGRule rule = (TGGRule) xtextToTGG.get(xtextNac.getRule());
			rule.getNacs().add(tggNac);
			map(xtextNac, tggNac);
			
			tggNac.getNodes().addAll(createTGGRuleNodes(toOVPatterns(xtextNac.getSourcePatterns()), DomainType.SRC));
			tggNac.getNodes().addAll(createTGGRuleNodes(toOVPatterns(xtextNac.getTargetPatterns()), DomainType.TRG));
			
			tggNac.getEdges().addAll(createTGGRuleEdges(tggNac.getNodes()));
			
			tggNac.setAttributeConditionLibrary(createAttributeConditionLibrary(xtextNac.getAttrConditions()));
		}	
	}

	private void translateXTextComplementRulesToTGGComplementRules(TripleGraphGrammarFile xtextTGG, TGG tgg) {
        for (ComplementRule xtextCompRule : xtextTGG.getComplementRules()) {
			TGGComplementRule tggComplementRule = tggFactory.createTGGComplementRule();
			tggComplementRule.setName(xtextCompRule.getName());
			TGGRule kernel = (TGGRule) xtextToTGG.get(xtextCompRule.getKernel());
			tggComplementRule.setKernel(kernel);
			tgg.getRules().add(tggComplementRule);
			map(xtextCompRule, tggComplementRule);

			tggComplementRule.getNodes().addAll(createTGGRuleNodes(xtextCompRule.getSourcePatterns(), DomainType.SRC));
			tggComplementRule.getNodes().addAll(createTGGRuleNodes(xtextCompRule.getTargetPatterns(), DomainType.TRG));
			tggComplementRule.getNodes().addAll(createTGGRuleNodesFromCorrOVs(xtextCompRule.getCorrespondencePatterns()));

			tggComplementRule.getEdges().addAll(createTGGRuleEdges(tggComplementRule.getNodes()));

			tggComplementRule.setAttributeConditionLibrary(createAttributeConditionLibrary(xtextCompRule.getAttrConditions()));
		
			tggComplementRule.setBounded(hasAdditionalContext(tggComplementRule));	
        }
	}

	private boolean hasAdditionalContext(TGGComplementRule tggComplementRule) {
		Collection<TGGRuleNode> contextComplementRuleNodes = tggComplementRule.getNodes().stream()
				.filter(n -> n.getBindingType().equals(BindingType.CONTEXT))
				.collect(Collectors.toSet());
		Collection<TGGRuleNode> kernelNodes = tggComplementRule.getKernel().getNodes();
		for (TGGRuleNode node : contextComplementRuleNodes) {
			if(kernelNodes.stream().noneMatch(cn -> cn.getName().equals(node.getName())))
				return true;
		}
		return false;
	}

	private Collection<ObjectVariablePattern> toOVPatterns(EList<ContextObjectVariablePattern> patterns) {
		return patterns.stream()
				.map(p -> toOVPattern(p))
				.collect(Collectors.toList());
	}

	private HashMap<ContextObjectVariablePattern, ObjectVariablePattern> cOVtoOV = new HashMap<>();
	private ObjectVariablePattern toOVPattern(ContextObjectVariablePattern cp) {
		if (!cOVtoOV.containsKey(cp)) {
			ObjectVariablePattern ovP = TggFactory.eINSTANCE.createObjectVariablePattern();
			ovP.setName(cp.getName());
			ovP.setType(cp.getType());
			cOVtoOV.put(cp, ovP);
			ovP.getAttributeConstraints().addAll(cp.getAttributeConstraints());
			ovP.getLinkVariablePatterns().addAll(toLPPatterns(cp.getLinkVariablePatterns()));
		}
		
		return cOVtoOV.get(cp);
	}

	private Collection<? extends LinkVariablePattern> toLPPatterns(Collection<ContextLinkVariablePattern> linkVariablePatterns) {
		return linkVariablePatterns.stream()
				.map(p -> toLVPattern(p))
				.collect(Collectors.toList());
	}

	private LinkVariablePattern toLVPattern(ContextLinkVariablePattern p) {
		LinkVariablePattern lvP = TggFactory.eINSTANCE.createLinkVariablePattern();
		lvP.setType(p.getType());
		lvP.setTarget(toOVPattern(p.getTarget()));
		return lvP;
	}

	private TGGAttributeConstraintDefinitionLibrary createAttributeConditionDefinitionLibrary(
			EList<AttrCondDef> attrCondDefs) {
		TGGAttributeConstraintDefinitionLibrary library = DefinitionFactory.eINSTANCE
				.createTGGAttributeConstraintDefinitionLibrary();

		for (AttrCondDef attrCondDef : attrCondDefs) {
			TGGAttributeConstraintDefinition definition = DefinitionFactory.eINSTANCE
					.createTGGAttributeConstraintDefinition();
			definition.setName(attrCondDef.getName());
			definition.setUserDefined(attrCondDef.isUserDefined());
			definition.getGenAdornments().addAll(attrCondDef.getAllowedGenAdornments().stream()
					.map(adornment -> creatAttributeConditionAdornment(adornment)).collect(Collectors.toList()));
			definition.getSyncAdornments().addAll(attrCondDef.getAllowedSyncAdornments().stream()
					.map(adornment -> creatAttributeConditionAdornment(adornment)).collect(Collectors.toList()));
			definition.getParameterDefinitions()
					.addAll(attrCondDef.getParams().stream()
							.map(parameterDef -> createAttributeConstraintParameterDefinition(parameterDef))
							.collect(Collectors.toList()));
			library.getTggAttributeConstraintDefinitions().add(definition);
			map(attrCondDef, definition);
		}

		return library;
	}

	private TGGAttributeConstraintLibrary createAttributeConditionLibrary(Collection<AttrCond> attrConds) {
		TGGAttributeConstraintLibrary library = CspFactory.eINSTANCE.createTGGAttributeConstraintLibrary();
		ParamValueSet paramValues = new ParamValueSet();

		library.getTggAttributeConstraints().addAll(attrConds.stream()
				.map(attrCond -> createAttributeConstraint(attrCond, paramValues)).collect(Collectors.toList()));
		library.getParameterValues().addAll(paramValues.getCollection());

		return library;
	}

	private TGGAttributeConstraint createAttributeConstraint(AttrCond attrCond, ParamValueSet foundValues) {
		TGGAttributeConstraint attributeConstraint = CspFactory.eINSTANCE.createTGGAttributeConstraint();
		attributeConstraint.setDefinition((TGGAttributeConstraintDefinition) xtextToTGG.get(attrCond.getName()));
		for (ParamValue paramValue : attrCond.getValues()) {
			TGGParamValue newTGGParamValue = createParamValue(
					(TGGAttributeConstraintDefinition) xtextToTGG.get(attrCond.getName()), paramValue);
			TGGParamValue checkedEntry = foundValues.putIfAbsent(newTGGParamValue);
			attributeConstraint.setDefinition((TGGAttributeConstraintDefinition) xtextToTGG.get(attrCond.getName()));
			attributeConstraint.getParameters().add(checkedEntry);
		}
		return attributeConstraint;
	}

	private TGGParamValue createParamValue(TGGAttributeConstraintDefinition definition, ParamValue paramValue) {
		int index = ((AttrCond) paramValue.eContainer()).getValues().indexOf(paramValue);
		TGGAttributeConstraintParameterDefinition paramDef = definition.getParameterDefinitions().get(index);

		if (paramValue instanceof LocalVariable) {
			TGGAttributeVariable attrVariable = CspFactory.eINSTANCE.createTGGAttributeVariable();
			attrVariable.setName(((LocalVariable) paramValue).getName());
			attrVariable.setParameterDefinition(paramDef);
			return attrVariable;
		}
		if (paramValue instanceof Expression) {
			TGGExpression exp = createExpression((Expression) paramValue);
			exp.setParameterDefinition(paramDef);
			return exp;
		}
		return null;
	}

	private TGGAttributeConstraintAdornment creatAttributeConditionAdornment(Adornment adornment) {
		TGGAttributeConstraintAdornment tggAdornment = DefinitionFactory.eINSTANCE
				.createTGGAttributeConstraintAdornment();
		tggAdornment.getValue().addAll(adornment.getValue());
		return tggAdornment;
	}

	private TGGAttributeConstraintParameterDefinition createAttributeConstraintParameterDefinition(Param parameter) {
		TGGAttributeConstraintParameterDefinition parameterDefinition = DefinitionFactory.eINSTANCE
				.createTGGAttributeConstraintParameterDefinition();
		parameterDefinition.setName(parameter.getParamName());
		parameterDefinition.setType(parameter.getType());
		map(parameter, parameterDefinition);
		return parameterDefinition;
	}

	private Collection<TGGRuleEdge> createTGGRuleEdges(Collection<TGGRuleNode> nodes) {
		ArrayList<TGGRuleEdge> result = new ArrayList<>();

		for (TGGRuleNode node : nodes) {
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
			corrNode.setType((EClass) xTextCorrToCorrClass.get(cv.getType().getName()));
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

		// This has to be done separately since some attribute expression may
		// reference nodes that are not yet existent
		for (TGGRuleNode tggNode : result) {
			ObjectVariablePattern ov = rule2patMap.get(tggNode);
			tggNode.getAttrExpr()
					.addAll(ov.getAttributeAssignments().stream()
							.map(assignment -> createTGGInplaceAttributeExpression(result, tggNode, assignment))
							.collect(Collectors.toList()));
			tggNode.getAttrExpr()
					.addAll(ov.getAttributeConstraints().stream()
							.map(constraint -> createTGGInplaceAttributeExpression(result, tggNode, constraint))
							.collect(Collectors.toList()));
		}

		return result;
	}

	private TGGInplaceAttributeExpression createTGGInplaceAttributeExpression(Collection<TGGRuleNode> allNodes,
			TGGRuleNode node, AttributeConstraint constraint) {
		TGGInplaceAttributeExpression tiae = InplaceAttributesFactory.eINSTANCE.createTGGInplaceAttributeExpression();
		tiae.setAttribute(node.getType().getEAllAttributes().stream()
				.filter(attr -> attr.getName().equals(constraint.getAttribute().getName())).findFirst().get());
		tiae.setValueExpr(createExpression(constraint.getValueExp()));
		tiae.setOperator(convertOperator(constraint.getOp()));
		return tiae;
	}

	private TGGInplaceAttributeExpression createTGGInplaceAttributeExpression(Collection<TGGRuleNode> allNodes,
			TGGRuleNode node, AttributeAssignment assignment) {
		TGGInplaceAttributeExpression tiae = InplaceAttributesFactory.eINSTANCE.createTGGInplaceAttributeExpression();
		tiae.setAttribute(node.getType().getEAllAttributes().stream()
				.filter(attr -> attr.getName().equals(assignment.getAttribute().getName())).findFirst().get());
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

	private TGGAttributeConstraintOperators convertOperator(String operator) {
		switch (operator) {
		case "==":
		case ":=":
			return TGGAttributeConstraintOperators.EQUAL;
		case "!=":
			return TGGAttributeConstraintOperators.UNEQUAL;
		case ">=":
			return TGGAttributeConstraintOperators.GR_EQUAL;
		case "<=":
			return TGGAttributeConstraintOperators.LE_EQUAL;
		case ">":
			return TGGAttributeConstraintOperators.GREATER;
		case "<":
			return TGGAttributeConstraintOperators.LESSER;
		default:
			return null;
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
		return null;
	}

	private EPackage createCorrModel(TripleGraphGrammarFile xtextTGG) {

		EPackage corrModel = ecoreFactory.createEPackage();

		corrModel.setName(xtextTGG.getSchema().getName());
		corrModel.setNsPrefix(xtextTGG.getSchema().getName());
		corrModel.setNsURI("platform:/resource/" + corrModel.getName() + "/model/" + corrModel.getName() + ".ecore");

		for (CorrType ct : xtextTGG.getSchema().getCorrespondenceTypes()) {
			corrModel.getEClassifiers().add(createEClass(ct));
		}

		for (CorrType ct : xtextTGG.getSchema().getCorrespondenceTypes()) {

			if (ct.getSuper() != null) {
				((EClass) xTextCorrToCorrClass.get(ct.getName())).getESuperTypes().add((EClass) xTextCorrToCorrClass.get(ct.getSuper().getName()));
			}
		}

		return corrModel;
	}

	private EClass createEClass(CorrType ct) {
		EClass corrClass = ecoreFactory.createEClass();
		corrClass.setName(ct.getName());

		if (ct.getSource() != null) {
			EReference srcRef = ecoreFactory.createEReference();
			srcRef.setName("source");
			srcRef.setLowerBound(0);
			srcRef.setUpperBound(1);
			srcRef.setEType(ct.getSource());
			corrClass.getEStructuralFeatures().add(srcRef);
		}

		if (ct.getTarget() != null) {
			EReference trgRef = ecoreFactory.createEReference();
			trgRef.setName("target");
			trgRef.setLowerBound(0);
			trgRef.setUpperBound(1);
			trgRef.setEType(ct.getTarget());
			corrClass.getEStructuralFeatures().add(trgRef);
		}
		
		xTextCorrToCorrClass.put(ct.getName(), corrClass);

		return corrClass;
	}

	private void map(EObject xtextObject, EObject tggObject) {
		xtextToTGG.put(xtextObject, tggObject);
		tggToXtext.put(tggObject, xtextObject);
	}

	private TGG addOppositeEdges(TGG tggModel) {
		Collection<TGGRuleEdge> edgesWithOpposite = tggModel.getRules().stream().flatMap(r -> r.getEdges().stream())
				.filter(e -> e.getType().getEOpposite() != null).collect(Collectors.toCollection(LinkedHashSet::new));
		edgesWithOpposite.forEach(e -> {
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
				oppositeEdge.setName(oppositeEdge.getSrcNode().getName() + "__" + oppositeRef.getName() + "__"
						+ oppositeEdge.getTrgNode().getName() + "_eMoflonEdge");
				rule.getEdges().add(oppositeEdge);
			}

		});
		return tggModel;
	}
}
