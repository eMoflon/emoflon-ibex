package org.emoflon.ibex.tgg.compiler.defaults;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.editor.builder.TGGBuildUtil;
import org.emoflon.ibex.tgg.editor.tgg.Adornment;
import org.emoflon.ibex.tgg.editor.tgg.AttrCond;
import org.emoflon.ibex.tgg.editor.tgg.AttrCondDef;
import org.emoflon.ibex.tgg.editor.tgg.AttributeAssignment;
import org.emoflon.ibex.tgg.editor.tgg.AttributeConstraint;
import org.emoflon.ibex.tgg.editor.tgg.AttributeExpression;
import org.emoflon.ibex.tgg.editor.tgg.ContextLinkVariablePattern;
import org.emoflon.ibex.tgg.editor.tgg.ContextObjectVariablePattern;
import org.emoflon.ibex.tgg.editor.tgg.CorrType;
import org.emoflon.ibex.tgg.editor.tgg.CorrVariablePattern;
import org.emoflon.ibex.tgg.editor.tgg.EnumExpression;
import org.emoflon.ibex.tgg.editor.tgg.Expression;
import org.emoflon.ibex.tgg.editor.tgg.LinkVariablePattern;
import org.emoflon.ibex.tgg.editor.tgg.LiteralExpression;
import org.emoflon.ibex.tgg.editor.tgg.LocalVariable;
import org.emoflon.ibex.tgg.editor.tgg.ObjectVariablePattern;
import org.emoflon.ibex.tgg.editor.tgg.Operator;
import org.emoflon.ibex.tgg.editor.tgg.OperatorPattern;
import org.emoflon.ibex.tgg.editor.tgg.Param;
import org.emoflon.ibex.tgg.editor.tgg.ParamValue;
import org.emoflon.ibex.tgg.editor.tgg.Rule;
import org.emoflon.ibex.tgg.editor.tgg.TggFactory;
import org.emoflon.ibex.tgg.editor.tgg.TripleGraphGrammarFile;
import org.emoflon.ibex.tgg.transformation.ParamValueSet;
import org.emoflon.ibex.tgg.transformation.TGGProject;
import org.emoflon.ibex.tgg.util.TGGModelUtils;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtil;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGG;
import language.TGGAttributeConstraint;
import language.TGGAttributeConstraintAdornment;
import language.TGGAttributeConstraintDefinition;
import language.TGGAttributeConstraintDefinitionLibrary;
import language.TGGAttributeConstraintLibrary;
import language.TGGAttributeConstraintOperators;
import language.TGGAttributeConstraintParameterDefinition;
import language.TGGAttributeExpression;
import language.TGGAttributeVariable;
import language.TGGEnumExpression;
import language.TGGExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGLiteralExpression;
import language.TGGParamValue;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.RuntimePackage;

public class EditorTGGtoInternalTGG {

	private EcoreFactory ecoreFactory = EcoreFactory.eINSTANCE;
	private LanguageFactory tggFactory = LanguageFactory.eINSTANCE;
	private HashMap<EObject, EObject> xtextToTGG = new HashMap<>();
	private HashMap<EObject, EObject> tggToXtext = new HashMap<>();
	private HashMap<String, EClass> xTextCorrToCorrClass = new HashMap<>();

	private static final Logger logger = Logger.getLogger(EditorTGGtoInternalTGG.class);
	public static final String INTERNAL_TGG_MODEL = EditorTGGtoInternalTGG.class.getName();

	private TGGProject convertXtextTGG(TripleGraphGrammarFile xtextTGG, TripleGraphGrammarFile flattenedXtextTGG,
			IProject project) {
		EPackage corrPackage = createCorrModel(flattenedXtextTGG, project);
		org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.put(corrPackage.getNsURI(), corrPackage);
		TGG tgg = createTGG(xtextTGG);
		TGG flattenedTgg = createTGG(flattenedXtextTGG);
		flattenedTgg.setCorr(corrPackage);
		return new TGGProject(corrPackage, tgg, flattenedTgg);
	}

	public Optional<TGGProject> generateInternalModels(TripleGraphGrammarFile xtextParsedTGG,
			TripleGraphGrammarFile flattenedXtextParsedTGG, IProject project) {
		Optional<TGGProject> tggProject = Optional
				.of(convertXtextTGG(xtextParsedTGG, flattenedXtextParsedTGG, project));

		tggProject.ifPresent(p -> {
			try {
				ResourceSet rs = xtextParsedTGG.eResource().getResourceSet();
				EcoreUtil.resolveAll(rs);
				IFile corrFile = project.getFolder(TGGBuildUtil.MODEL_FOLDER)//
						.getFile(getNameOfGeneratedFile(project) + TGGBuildUtil.ECORE_FILE_EXTENSION);
				TGGBuildUtil.saveModelInProject(corrFile, rs, p.getCorrPackage());
				IFile tggFile = project.getFolder(TGGBuildUtil.MODEL_FOLDER)//
						.getFile(getNameOfGeneratedFile(project) + TGGBuildUtil.INTERNAL_TGG_MODEL_EXTENSION);
				TGGBuildUtil.saveModelInProject(tggFile, rs, p.getTggModel());
				IFile flattenedTggFile = project.getFolder(TGGBuildUtil.MODEL_FOLDER)//
						.getFile(getNameOfGeneratedFile(project)
								+ TGGBuildUtil.INTERNAL_TGG_FLATTENED_MODEL_EXTENSION);
				TGGBuildUtil.saveModelInProject(flattenedTggFile, rs, p.getFlattenedTggModel());
			} catch (IOException e) {
				LogUtils.error(logger, e);
			}
		});

		return tggProject;
	}
	
	private String getNameOfGeneratedFile(IProject project) {
		return MoflonUtil.lastCapitalizedSegmentOf(project.getName());
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

		tgg = addOppositeEdges(tgg);

		return tgg;
	}

	private void translateXTextRulesToTGGRules(TripleGraphGrammarFile xtextTGG, TGG tgg) {
		for (Rule xtextRule : xtextTGG.getRules()) {
			Map<ObjectVariablePattern, ObjectVariablePattern> ovToCopy = new HashMap<>();
			
			TGGRule tggRule = tggFactory.createTGGRule();
			tggRule.setName(xtextRule.getName());
			tggRule.setAbstract(xtextRule.isAbstractRule());
			tgg.getRules().add(tggRule);
			map(xtextRule, tggRule);

			tggRule.getNodes().addAll(createTGGRuleNodes(xtextRule, xtextRule.getSourcePatterns(), DomainType.SRC, ovToCopy));
			tggRule.getNodes().addAll(createTGGRuleNodes(xtextRule, xtextRule.getTargetPatterns(), DomainType.TRG, ovToCopy));
			tggRule.getNodes().addAll(createTGGRuleNodesFromCorrOVs(xtextRule, tggRule, xtextRule.getCorrespondencePatterns(), ovToCopy));

			tggRule.getEdges().addAll(createTGGRuleEdges(tggRule.getNodes()));

			tggRule.setAttributeConditionLibrary(createAttributeConditionLibrary(xtextRule.getAttrConditions()));
		}
	}

	private void translateXTextRuleRefinementsToTGGRuleRefinements(TripleGraphGrammarFile xtextTGG, TGG tgg) {
		for (TGGRule tggRule : tgg.getRules()) {
			tggRule.getRefines().addAll(((Rule) tggToXtext.get(tggRule)).getSupertypes().stream()
					.map(r -> (TGGRule) xtextToTGG.get(r)).collect(Collectors.toList()));
		}
	}

//	private Collection<ObjectVariablePattern> toOVPatterns(EList<ContextObjectVariablePattern> patterns) {
//		return patterns.stream().map(p -> toOVPattern(p)).collect(Collectors.toList());
//	}

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

	private Collection<? extends LinkVariablePattern> toLPPatterns(
			Collection<ContextLinkVariablePattern> linkVariablePatterns) {
		return linkVariablePatterns.stream().map(p -> toLVPattern(p)).collect(Collectors.toList());
	}

	private LinkVariablePattern toLVPattern(ContextLinkVariablePattern p) {
		LinkVariablePattern lvP = TggFactory.eINSTANCE.createLinkVariablePattern();
		lvP.setType(p.getType());
		lvP.setTarget(toOVPattern(p.getTarget()));
		return lvP;
	}

	private TGGAttributeConstraintDefinitionLibrary createAttributeConditionDefinitionLibrary(
			EList<AttrCondDef> attrCondDefs) {
		TGGAttributeConstraintDefinitionLibrary library = LanguageFactory.eINSTANCE
				.createTGGAttributeConstraintDefinitionLibrary();

		for (AttrCondDef attrCondDef : attrCondDefs) {
			TGGAttributeConstraintDefinition definition = LanguageFactory.eINSTANCE
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
		TGGAttributeConstraintLibrary library = LanguageFactory.eINSTANCE.createTGGAttributeConstraintLibrary();
		ParamValueSet paramValues = new ParamValueSet();

		library.getTggAttributeConstraints().addAll(attrConds.stream()
				.map(attrCond -> createAttributeConstraint(attrCond, paramValues)).collect(Collectors.toList()));
		library.getParameterValues().addAll(paramValues.getCollection());

		return library;
	}

	private TGGAttributeConstraint createAttributeConstraint(AttrCond attrCond, ParamValueSet foundValues) {
		TGGAttributeConstraint attributeConstraint = LanguageFactory.eINSTANCE.createTGGAttributeConstraint();
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

		if (paramValue instanceof LocalVariable localVar) {
			TGGAttributeVariable attrVariable = LanguageFactory.eINSTANCE.createTGGAttributeVariable();
			attrVariable.setName(localVar.getName());
			attrVariable.setParameterDefinition(paramDef);
			return attrVariable;
		}
		if (paramValue instanceof Expression expr) {
			TGGExpression tggExpr = createExpression(expr);
			tggExpr.setParameterDefinition(paramDef);
			return tggExpr;
		}
		return null;
	}

	private TGGAttributeConstraintAdornment creatAttributeConditionAdornment(Adornment adornment) {
		TGGAttributeConstraintAdornment tggAdornment = LanguageFactory.eINSTANCE
				.createTGGAttributeConstraintAdornment();
		tggAdornment.getValue().addAll(adornment.getValue());
		return tggAdornment;
	}

	private TGGAttributeConstraintParameterDefinition createAttributeConstraintParameterDefinition(Param parameter) {
		TGGAttributeConstraintParameterDefinition parameterDefinition = LanguageFactory.eINSTANCE
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
					TGGRuleEdge tggEdge = createTGGRuleEdge(node, (TGGRuleNode) xtextToTGG.get(lv.getTarget()), lv.getType(), getBindingType(lv.getOp()));
					map(lv, tggEdge);
					result.add(tggEdge);
				}
			}
		}

		return result;
	}

	private TGGRuleEdge createTGGRuleEdge(TGGRuleNode srcNode, TGGRuleNode trgNode, EReference eType, BindingType bType) {
		TGGRuleEdge tggEdge = tggFactory.createTGGRuleEdge();
		tggEdge.setType(eType);
		tggEdge.setSrcNode(srcNode);
		tggEdge.setTrgNode(trgNode);
		tggEdge.setBindingType(bType);
		tggEdge.setDomainType(srcNode.getDomainType());
		tggEdge.setName(tggEdge.getSrcNode().getName() + "__" + tggEdge.getType().getName() + "__"
				+ tggEdge.getTrgNode().getName() + "_eMoflonEdge");
		return tggEdge;
	}

	private Collection<TGGRuleNode> createTGGRuleNodesFromCorrOVs(Rule xtextRule, TGGRule rule, Collection<CorrVariablePattern> corrOVs, Map<ObjectVariablePattern, ObjectVariablePattern> ovToCopy) {
		ArrayList<TGGRuleNode> result = new ArrayList<>();
		for (CorrVariablePattern cv : corrOVs) {
			TGGRuleCorr corrNode = tggFactory.createTGGRuleCorr();
			corrNode.setName(cv.getName());
			corrNode.setType((EClass) xTextCorrToCorrClass.get(cv.getType().getName()));
			corrNode.setBindingType(getBindingType(cv.getOp()));
			corrNode.setDomainType(DomainType.CORR);
			corrNode.setSource((TGGRuleNode) xtextToTGG.get(cv.getSource()));
			
			// the source may stem from another rule (refinement)
			TGGRuleNode sourceNode = (TGGRuleNode) xtextToTGG.get(cv.getSource());
			if(sourceNode == null) {
				sourceNode = (TGGRuleNode) xtextToTGG.get(ovToCopy.get(cv.getSource()));
			}
			if(sourceNode == null) {
				ObjectVariablePattern sourceOVCopy = createOVCopy(ovToCopy, cv.getSource());
				xtextRule.getSourcePatterns().add(sourceOVCopy);
				sourceNode = createTGGRuleNode(sourceOVCopy, DomainType.SRC);
				rule.getNodes().add(sourceNode);
			}
			corrNode.setSource(sourceNode);
			
			// the target may stem from another rule (refinement)
			TGGRuleNode targetNode = (TGGRuleNode) xtextToTGG.get(cv.getTarget());
			if(targetNode == null) {
				targetNode = (TGGRuleNode) xtextToTGG.get(ovToCopy.get(cv.getTarget()));
			}
			if(targetNode == null) {
				ObjectVariablePattern targetOVCopy = createOVCopy(ovToCopy, cv.getTarget());
				xtextRule.getTargetPatterns().add(targetOVCopy);
				targetNode = createTGGRuleNode(targetOVCopy, DomainType.TRG);
				rule.getNodes().add(targetNode);
			}
			corrNode.setTarget(targetNode);


			rule.getEdges().add(createTGGRuleEdge(corrNode, corrNode.getSource(), (EReference) corrNode.getType().getEStructuralFeature("source"), corrNode.getBindingType()));
			rule.getEdges().add(createTGGRuleEdge(corrNode, corrNode.getTarget(), (EReference) corrNode.getType().getEStructuralFeature("target"), corrNode.getBindingType()));
			
			result.add(corrNode);
			map(cv, corrNode);
		}
		return result;
	}

	private ObjectVariablePattern createOVCopy(Map<ObjectVariablePattern, ObjectVariablePattern> ovToCopy, ObjectVariablePattern ov) {
		ObjectVariablePattern targetOVCopy = EcoreUtil.copy(ov);

		// clear copy of all linkvariables, attribute assignments and constraints as they become redundant when flattening the rules
		targetOVCopy.getLinkVariablePatterns().clear();
		targetOVCopy.getAttributeAssignments().clear();
		targetOVCopy.getAttributeConstraints().clear();
		
		ovToCopy.put(ov, targetOVCopy);
		return targetOVCopy;
	}

	private Collection<TGGRuleNode> createTGGRuleNodes(Rule xtextRule, Collection<ObjectVariablePattern> ovs, DomainType domainType, Map<ObjectVariablePattern, ObjectVariablePattern> ovToCopy) { ArrayList<TGGRuleNode> result = new ArrayList<>();
		Collection<ObjectVariablePattern> additionalOVs = new HashSet<>();
		for (ObjectVariablePattern ov : ovs) {
			result.add(createTGGRuleNode(ov, domainType));
			
			// We can not rely for all nodes to be directly contained in the pattern. 
			// Some nodes may stem from refined rules and are referenced as targets of references.
			for(LinkVariablePattern lv : ov.getLinkVariablePatterns()) {
				ObjectVariablePattern targetOV = lv.getTarget();
				
				// if the source and target share the same container, we can continue
				if(targetOV.eContainer().equals(ov.eContainer()))
					continue;
				
				ObjectVariablePattern targetOVCopy = ovToCopy.get(targetOV);
				if(targetOVCopy == null) {
					targetOVCopy = createOVCopy(ovToCopy, targetOV);
					additionalOVs.add(targetOVCopy);
					result.add(createTGGRuleNode(targetOVCopy, domainType));
				}

				// replace target of linkvariable with copy
				lv.setTarget(targetOVCopy);
			}
		}
		
		// add all newly created ovs that are copies from refined nodes to the original collection
		ovs.addAll(additionalOVs);

		// This has to be done separately since some attribute expression may
		// reference nodes that are not yet existent
		for (TGGRuleNode tggNode : result) {
			ObjectVariablePattern ov = (ObjectVariablePattern) tggToXtext.get(tggNode);
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
		TGGInplaceAttributeExpression tiae = LanguageFactory.eINSTANCE.createTGGInplaceAttributeExpression();
		tiae.setAttribute(node.getType().getEAllAttributes().stream()
				.filter(attr -> attr.getName().equals(constraint.getAttribute().getName())).findFirst().get());
		tiae.setValueExpr(createExpression(constraint.getValueExp()));
		tiae.setOperator(convertOperator(constraint.getOp()));
		return tiae;
	}

	private TGGInplaceAttributeExpression createTGGInplaceAttributeExpression(Collection<TGGRuleNode> allNodes,
			TGGRuleNode node, AttributeAssignment assignment) {
		TGGInplaceAttributeExpression tiae = LanguageFactory.eINSTANCE.createTGGInplaceAttributeExpression();
		tiae.setAttribute(node.getType().getEAllAttributes().stream()
				.filter(attr -> attr.getName().equals(assignment.getAttribute().getName())).findFirst().get());
		tiae.setValueExpr(createExpression(assignment.getValueExp()));
		tiae.setOperator(TGGAttributeConstraintOperators.EQUAL);
		return tiae;
	}

	private TGGExpression createExpression(org.emoflon.ibex.tgg.editor.tgg.Expression expression) {
		if (expression instanceof LiteralExpression le) {
			TGGLiteralExpression tle = LanguageFactory.eINSTANCE.createTGGLiteralExpression();
			tle.setValue(le.getValue());
			return tle;
		}
		if (expression instanceof EnumExpression ee) {
			TGGEnumExpression tee = LanguageFactory.eINSTANCE.createTGGEnumExpression();
			tee.setEenum(ee.getEenum());
			tee.setLiteral(ee.getLiteral());
			return tee;
		}
		if (expression instanceof AttributeExpression ae) {
			TGGAttributeExpression tae = LanguageFactory.eINSTANCE.createTGGAttributeExpression();
			tae.setAttribute(ae.getAttribute());
			tae.setObjectVar((TGGRuleNode) xtextToTGG.get(ae.getObjectVar()));
			tae.setDerived(ae.isDerived());
			return tae;
		}
		return null;
	}

	private TGGAttributeConstraintOperators convertOperator(String operator) {
		return switch (operator) {
			case "==", ":=" -> TGGAttributeConstraintOperators.EQUAL;
			case "!=" -> TGGAttributeConstraintOperators.UNEQUAL;
			case ">=" -> TGGAttributeConstraintOperators.GR_EQUAL;
			case "<=" -> TGGAttributeConstraintOperators.LE_EQUAL;
			case ">" -> TGGAttributeConstraintOperators.GREATER;
			case "<" -> TGGAttributeConstraintOperators.LESSER;
			default -> null;
		};
	}

	private TGGRuleNode createTGGRuleNode(ObjectVariablePattern ov, DomainType domain) {
		if(xtextToTGG.containsKey(ov)) {
			return (TGGRuleNode) xtextToTGG.get(ov);
		}
		TGGRuleNode tggNode = tggFactory.createTGGRuleNode();
		tggNode.setName(ov.getName());
		tggNode.setType(ov.getType());
		tggNode.setBindingType(getBindingType(ov.getOp()));
		map(ov, tggNode);
		tggNode.setDomainType(domain);
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

	private EPackage createCorrModel(TripleGraphGrammarFile xtextTGG, IProject project) {
		String qualifiedName = xtextTGG.getSchema().getName();
		
		EPackage corrModel = ecoreFactory.createEPackage();
		corrModel.setName(MoflonUtil.lastSegmentOf(qualifiedName));
		corrModel.setNsPrefix(qualifiedName);
		corrModel.setNsURI("platform:/resource/" + project.getName() + "/model/" + MoflonUtil.lastCapitalizedSegmentOf(corrModel.getName()) + ".ecore");

		BasicEMap<String, String> map = new BasicEMap<>();
		int index = qualifiedName.lastIndexOf('.');
		if(index >= 0) {
			EAnnotation genAnnotation = ecoreFactory.createEAnnotation();
			genAnnotation.setSource("http://www.eclipse.org/emf/2002/GenModel");
			String prefix = qualifiedName.substring(0, index);
			map.put("basePackage", prefix);
			genAnnotation.getDetails().putAll(map);
			corrModel.getEAnnotations().add(genAnnotation);
		}
		
		for (CorrType ct : xtextTGG.getSchema().getCorrespondenceTypes()) {
			corrModel.getEClassifiers().add(createEClass(ct));
		}

		for (CorrType ct : xtextTGG.getSchema().getCorrespondenceTypes()) {
			if (ct.getSuper() != null) {
				((EClass) xTextCorrToCorrClass.get(ct.getName())).getESuperTypes()
						.add((EClass) xTextCorrToCorrClass.get(ct.getSuper().getName()));
			}
			else {
				((EClass) xTextCorrToCorrClass.get(ct.getName())).getESuperTypes().add(RuntimePackage.eINSTANCE.getCorrespondenceNode());
			}
		}

		for (Rule rule : xtextTGG.getRules()) {
			if (!rule.isAbstractRule()) {
				corrModel.getEClassifiers().add(createMarkerClass(rule));
			}
		}
		
		return corrModel;
	}

	private EClassifier createMarkerClass(Rule rule) {
		return createMarkerClass(rule.getName(), rule.getSourcePatterns(), rule.getCorrespondencePatterns(),
				rule.getTargetPatterns());
	}

	private EClassifier createMarkerClass(String ruleName, List<ObjectVariablePattern> sourcePatterns,
			List<CorrVariablePattern> corrPatterns, List<ObjectVariablePattern> targetPatterns) {
		EClass markerClass = ecoreFactory.createEClass();
		markerClass.setName(TGGModelUtils.getMarkerTypeName(ruleName));
		markerClass.getESuperTypes().add(RuntimePackage.eINSTANCE.getTGGRuleApplication());
		for (ObjectVariablePattern ovPattern : sourcePatterns)
			markerClass.getEStructuralFeatures()
					.add(createMarkerRef(ovPattern, ovPattern.getName(), ovPattern.getType(), DomainType.SRC));

		for (ObjectVariablePattern ovPattern : targetPatterns)
			markerClass.getEStructuralFeatures()
					.add(createMarkerRef(ovPattern, ovPattern.getName(), ovPattern.getType(), DomainType.TRG));

		for (CorrVariablePattern ovPattern : corrPatterns)
			markerClass.getEStructuralFeatures().add(createMarkerRef(ovPattern, ovPattern.getName(),
					xTextCorrToCorrClass.get(ovPattern.getType().getName()), DomainType.CORR));

		return markerClass;
	}

	private EReference createMarkerRef(OperatorPattern ovPattern, String name, EClass type, DomainType domain) {
		EReference ref = ecoreFactory.createEReference();
		BindingType binding = ovPattern.getOp() != null && "++".equals(ovPattern.getOp().getValue())
				? BindingType.CREATE
				: BindingType.CONTEXT;
		ref.setName(TGGModelUtils.getMarkerRefName(binding, domain, name));
		ref.setLowerBound(1);
		ref.setUpperBound(1);
		ref.setEType(type);
		return ref;
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
