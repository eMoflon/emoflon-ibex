package org.emoflon.ibex.tgg.compiler;

import static org.emoflon.ibex.common.slimgt.util.SlimGTModelUtil.getContainer;
import static org.emoflon.ibex.tgg.compiler.TGGRuleDerivedFieldsTool.fillDerivedTGGRuleFields;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModelMetadata;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNullValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BinaryExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BinaryOperator;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanBinaryExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanBinaryOperator;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanUnaryExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanUnaryOperator;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;
import org.emoflon.ibex.common.slimgt.slimGT.ArithmeticLiteral;
import org.emoflon.ibex.common.slimgt.slimGT.BooleanBracket;
import org.emoflon.ibex.common.slimgt.slimGT.BooleanConjunction;
import org.emoflon.ibex.common.slimgt.slimGT.BooleanDisjunction;
import org.emoflon.ibex.common.slimgt.slimGT.BooleanImplication;
import org.emoflon.ibex.common.slimgt.slimGT.BooleanLiteral;
import org.emoflon.ibex.common.slimgt.slimGT.BooleanNegation;
import org.emoflon.ibex.common.slimgt.slimGT.BracketExpression;
import org.emoflon.ibex.common.slimgt.slimGT.Constant;
import org.emoflon.ibex.common.slimgt.slimGT.DoubleLiteral;
import org.emoflon.ibex.common.slimgt.slimGT.EnumExpression;
import org.emoflon.ibex.common.slimgt.slimGT.ExpArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.IntegerLiteral;
import org.emoflon.ibex.common.slimgt.slimGT.MinMaxArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.NodeAttributeExpression;
import org.emoflon.ibex.common.slimgt.slimGT.NodeExpression;
import org.emoflon.ibex.common.slimgt.slimGT.ProductArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.SlimParameter;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleAttributeAssignment;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleCondition;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleConfiguration;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleEdgeContext;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleEdgeCreation;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleInvocation;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleInvocationType;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleNodeContext;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleNodeCreation;
import org.emoflon.ibex.common.slimgt.slimGT.StochasticArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.StringLiteral;
import org.emoflon.ibex.common.slimgt.slimGT.SumArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.UnaryArithmeticExpression;
import org.emoflon.ibex.common.slimgt.util.SlimGTEMFUtil;
import org.emoflon.ibex.common.transformation.DataTypeUtil;
import org.emoflon.ibex.common.transformation.SlimGtToIBeXCoreTransformer;
import org.emoflon.ibex.tgg.analysis.ACAnalysis;
import org.emoflon.ibex.tgg.analysis.ACStrategy;
import org.emoflon.ibex.tgg.compiler.builder.AttrCondDefLibraryProvider;
import org.emoflon.ibex.tgg.compiler.builder.CorrespondenceBuilderUtil;
import org.emoflon.ibex.tgg.compiler.defaults.TGGBuildUtil;
import org.emoflon.ibex.tgg.tggl.scoping.TGGLScopeProvider;
import org.emoflon.ibex.tgg.tggl.tGGL.AttributeCondition;
import org.emoflon.ibex.tgg.tggl.tGGL.AttributeConditionDefinition;
import org.emoflon.ibex.tgg.tggl.tGGL.CorrespondenceType;
import org.emoflon.ibex.tgg.tggl.tGGL.DerivableNodeAttributeExpression;
import org.emoflon.ibex.tgg.tggl.tGGL.EditorFile;
import org.emoflon.ibex.tgg.tggl.tGGL.ExpressionOperand;
import org.emoflon.ibex.tgg.tggl.tGGL.LocalVariable;
import org.emoflon.ibex.tgg.tggl.tGGL.SlimRule;
import org.emoflon.ibex.tgg.tggl.tGGL.SlimRuleNode;
import org.emoflon.ibex.tgg.tggl.tGGL.TGGCorrRule;
import org.emoflon.ibex.tgg.tggl.tGGL.TGGCorrespondenceNode;
import org.emoflon.ibex.tgg.tggl.tGGL.TGGCorrespondenceNodeContext;
import org.emoflon.ibex.tgg.tggl.tGGL.TGGCorrespondenceNodeCreation;
import org.emoflon.ibex.tgg.tggl.tGGL.TGGDomainRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGPattern;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGLocalVariable;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;

import TGGRuntimeModel.TGGRuntimeModelPackage;

public class TGGLToTGGModelTransformer extends SlimGtToIBeXCoreTransformer<EditorFile, TGGModel, IBeXTGGModelFactory> {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private EcoreFactory ecoreFactory = EcoreFactory.eINSTANCE;
	private CSPFactory cspFactory = CSPFactory.eINSTANCE;
	private TGGRuntimeModelPackage runtimePackage = TGGRuntimeModelPackage.eINSTANCE;
	
	private Map<Object, EObject> tggl2tggModel = new ConcurrentHashMap<>();
	private Map<EClass, EReference> corrType2sourceRef = new ConcurrentHashMap<EClass, EReference>();
	private Map<EClass, EReference> corrType2targetRef = new ConcurrentHashMap<EClass, EReference>();
	
	private Map<NodeAttributeEntry, TGGAttributeConstraintParameterValue> nodeAttributeEntry2parameterValue = new HashMap<>();
	private EPackage correspondenceModel;

	private IBeXFeatureConfig featureConfig;
	
	public TGGLToTGGModelTransformer(EditorFile editorFile, final IProject project) {
		super(editorFile, project);
	}

	@Override
	protected IBeXTGGModelFactory initFactory() {
		return IBeXTGGModelFactory.eINSTANCE;
	}

	@Override
	protected TGGModel createNewModel() {
		return factory.createTGGModel();
	}

	@Override
	protected IBeXModelMetadata createModelMetadata() {
		IBeXModelMetadata metadata = superFactory.createIBeXModelMetadata();
		metadata.setProject(project.getName());
		metadata.setProjectPath(project.getLocation().toPortableString());

		var usedPackages = getUsedPackages();
		for (EPackage pkg : usedPackages) {
			EPackageDependency dependency = null;
			try {
				dependency = transformPackage(pkg);
			} catch (IOException e) {
				continue;
			}
			
			metadata.getDependencies().add(dependency);
			metadata.getName2package().put(dependency.getSimpleName(), dependency);
		}

		// Add the ecore package if not present
//		if (!metadata.getName2package().containsKey("ecore")) {
//			try {
//				EPackageDependency dependency = transformPackage(EcorePackage.eINSTANCE);
//				metadata.getDependencies().add(dependency);
//				metadata.getName2package().put(dependency.getSimpleName(), dependency);
//			} catch (IOException e) {
//			}
//		}

		return metadata;
	}
	
	private Collection<EPackage> getUsedPackages() {
		var schema = editorFile.getSchema();
		var packages = new HashSet<EPackage>();
		packages.addAll(TGGLScopeProvider.getReferencedPackages(schema.getSourceTypes()));
		packages.addAll(TGGLScopeProvider.getReferencedPackages(schema.getTargetTypes()));
		return packages;
	}

	@Override
	public TGGModel transform() {
		featureConfig = superFactory.createIBeXFeatureConfig();
		model.setFeatureConfig(featureConfig);
		model.setName(editorFile.getSchema().getName());
		model.setRuleSet(factory.createTGGRuleSet());
		
		createCorrespondenceModel();
		model.getSource().addAll(TGGLScopeProvider.getReferencedPackages(editorFile.getSchema().getSourceTypes()));
		model.getTarget().addAll(TGGLScopeProvider.getReferencedPackages(editorFile.getSchema().getTargetTypes()));

		createAttributeConstraintLibraries();
		
		// Pattern are only transformed if actually called by a tgg rule
		var rules = editorFile.getRules().stream().filter(rule -> !rule.isAbstract()).map(this::transformRule).toList();
		model.getRuleSet().getRules().addAll(rules);
		
		ProtocolGenerator protocolGenerator = new ProtocolGenerator();
		var protocolInformation = protocolGenerator.createProtocol(model);
		
		var operationalizer = new TGGOperationalizer();
		operationalizer.operationalizeTGGRules(model, protocolInformation);
	 
		// merge both corr and protocol metamodel together to make imports easier
		var corrModel = model.getCorrespondence();
		corrModel.getEClassifiers().addAll(protocolInformation.metamodel().getEClassifiers());
		
		postProcessing();
		
		saveModels(model);
		
		return model;
	}
	
	private void saveModels(TGGModel model) {
		
		try {
			ResourceSet rs = new ResourceSetImpl();
			IFile corrFile = project.getFolder(TGGBuildUtil.MODEL_FOLDER)//
					.getFile(getNameOfGeneratedFile(project) + TGGBuildUtil.ECORE_FILE_EXTENSION);
			TGGBuildUtil.saveModelInProject(corrFile, rs, model.getCorrespondence());
			IFile corrGenFile = project.getFolder(TGGBuildUtil.MODEL_FOLDER)//
					.getFile(getNameOfGeneratedFile(project) + TGGBuildUtil.GENMODEL_FILE_EXTENSION);
			IFile tggFile = project.getFolder(TGGBuildUtil.MODEL_FOLDER)//
					.getFile(getNameOfGeneratedFile(project) + TGGBuildUtil.INTERNAL_TGG_MODEL_EXTENSION);
			TGGBuildUtil.saveModelInProject(tggFile, rs, model);
			
			// build correspondence code
			CorrespondenceBuilderUtil corrBuilderUtil = new CorrespondenceBuilderUtil(project);
			URI base = URI.createPlatformResourceURI("/", true);
			corrBuilderUtil.generateMetaModelCode(base, corrFile.getFullPath().toPortableString().substring(1), corrGenFile.getFullPath().toPortableString().substring(1), model.getCorrespondence());
		} catch (IOException e) {
			LogUtils.error(logger, e);
		}
	}
	
	private String getNameOfGeneratedFile(IProject project) {
		return MoflonUtil.lastCapitalizedSegmentOf(project.getName());
	}

	private void createAttributeConstraintLibraries() {
		for(var xtextConditionLibrary : editorFile.getLibraries()) {
			var constraintDefinitionLibrary = cspFactory.createTGGAttributeConstraintDefinitionLibrary();
			constraintDefinitionLibrary.setName(xtextConditionLibrary.getName());
			if(xtextConditionLibrary.getName().equals(AttrCondDefLibraryProvider.DEFAULT_ATTR_COND_LIB_NAME)) {
				constraintDefinitionLibrary.setPackageName(AttrCondDefLibraryProvider.ATTR_COND_DEF_PREDEFINED_PACKAGE);
			}
			else {
				constraintDefinitionLibrary.setPackageName(AttrCondDefLibraryProvider.ATTR_COND_DEF_USERDEFINED_PACKAGE + "." + project.getName().toLowerCase() + "." + constraintDefinitionLibrary.getName().toLowerCase());
			}
			model.getAttributeConstraintDefinitionLibraries().add(constraintDefinitionLibrary);
			
			for(var xtextConditionDefinition : xtextConditionLibrary.getAttributeCondDefs()) {
				var attributeConstraintDefinition = transformAttributeConstraintDefinition(xtextConditionDefinition);
				constraintDefinitionLibrary.getTggAttributeConstraintDefinitions().add(attributeConstraintDefinition);
			}
		}
	}

	private TGGAttributeConstraintDefinition transformAttributeConstraintDefinition(AttributeConditionDefinition xtextConditionDefinition) {
		if(tggl2tggModel.containsKey(xtextConditionDefinition))
			return (TGGAttributeConstraintDefinition) tggl2tggModel.get(xtextConditionDefinition);
		
		var attributeConstraintDefinition = cspFactory.createTGGAttributeConstraintDefinition();
		tggl2tggModel.put(xtextConditionDefinition, attributeConstraintDefinition);
		attributeConstraintDefinition.setName(xtextConditionDefinition.getName());
		
		for(var xtextParameterBinding : xtextConditionDefinition.getParams()) {
			var parameterDefinition = transformAttributeConstraintParameterDefinition(xtextParameterBinding);
			attributeConstraintDefinition.getParameterDefinitions().add(parameterDefinition);
		}
		
		for(var xtextGenBindings : xtextConditionDefinition.getAllowedGenBindings()) {
			var binding = cspFactory.createTGGAttributeConstraintBinding();
			binding.getValue().addAll(xtextGenBindings.getValue());
			attributeConstraintDefinition.getGenBindings().add(binding);
		}
		
		for(var xtextGenBindings : xtextConditionDefinition.getAllowedSyncBindings()) {
			var binding = cspFactory.createTGGAttributeConstraintBinding();
			binding.getValue().addAll(xtextGenBindings.getValue());
			attributeConstraintDefinition.getSyncBindings().add(binding);
		}
		return attributeConstraintDefinition;
	}
	
	private TGGAttributeConstraintParameterDefinition transformAttributeConstraintParameterDefinition(SlimParameter parameter) {
		if(tggl2tggModel.containsKey(parameter)) {
			return (TGGAttributeConstraintParameterDefinition) tggl2tggModel.get(parameter);
		}
		var parameterDefinition = cspFactory.createTGGAttributeConstraintParameterDefinition();
		tggl2tggModel.put(parameter, parameterDefinition);
		parameterDefinition.setName(parameter.getName());
		parameterDefinition.setType(parameter.getType());
		return parameterDefinition;
	}

	private EPackage createCorrespondenceModel() {
		correspondenceModel = ecoreFactory.createEPackage();
		correspondenceModel.setName(project.getName());
		correspondenceModel.setNsPrefix(project.getName());
		correspondenceModel.setNsURI("platform:/resource/" + project.getName() + "/model/" + project.getName() + ".ecore");
		
		var xtextCorrTypes = editorFile.getSchema().getCorrespondenceTypes();
		for(var xtextCorrType : xtextCorrTypes) {
			correspondenceModel.getEClassifiers().add(transformCorrespondenceType(xtextCorrType));
		}
		
		model.setCorrespondence(correspondenceModel);
		
		return correspondenceModel;
	}

	protected EClass transformCorrespondenceType(CorrespondenceType xtextCorrType) {
		if(tggl2tggModel.containsKey(xtextCorrType))
			return (EClass) tggl2tggModel.get(xtextCorrType);
		
		var runtimeCorrType = runtimePackage.getCorrespondence();

		var corrType = ecoreFactory.createEClass();
		tggl2tggModel.put(xtextCorrType, corrType);
		
		corrType.setName(xtextCorrType.getName());
		corrType.setAbstract(false);
		if(xtextCorrType.getSuper() == null)
			corrType.getESuperTypes().add(runtimeCorrType);
		else
			corrType.getESuperTypes().add(transformCorrespondenceType(xtextCorrType.getSuper()));
		
		EClass superCorr = xtextCorrType.getSuper() == null ? null : transformCorrespondenceType(xtextCorrType.getSuper());
		
		if(superCorr == null) {
			EReference source = ecoreFactory.createEReference();
			source.setName("source");
			source.setUpperBound(1);
			source.setEType(xtextCorrType.getSource());
			corrType2sourceRef.put(corrType, source);
			
			EReference target = ecoreFactory.createEReference();
			target.setName("target");
			target.setUpperBound(1);
			target.setEType(xtextCorrType.getTarget());
			corrType2targetRef.put(corrType, target);
			
			corrType.getEStructuralFeatures().add(source);
			corrType.getEStructuralFeatures().add(target);
		}
		else {
			// register the EReference for sub correspondence types
			EReference source = (EReference) superCorr.getEStructuralFeature("source");
			EReference target = (EReference) superCorr.getEStructuralFeature("target");
			corrType2sourceRef.put(corrType, source);
			corrType2targetRef.put(corrType, target);
		}
		
		return corrType;
	}
	
	public TGGRule transformRule(org.emoflon.ibex.tgg.tggl.tGGL.TGGRule rule) {
		if(tggl2tggModel.containsKey(rule))
			return (TGGRule) tggl2tggModel.get(rule);
		
		var internalRule = factory.createTGGRule();
		tggl2tggModel.put(rule, internalRule);
		
		internalRule.setName(rule.getName());
		for(var node : rule.getSourceRule().getContextNodes()) {
			internalRule.getNodes().add(transformTGGNode((SlimRuleNode) node.getContext(), BindingType.CONTEXT, DomainType.SOURCE));
		}
		for(var node : rule.getSourceRule().getCreatedNodes()) {
			internalRule.getNodes().add(transformTGGNode((SlimRuleNode) node.getCreation(), BindingType.CREATE, DomainType.SOURCE));
		}
		
		for(var node : rule.getCorrRule().getContextCorrespondenceNodes()) {
			TGGCorrespondence correspondenceNode = transformTGGCorrespondenceNode((TGGCorrespondenceNode) node.getContext(), BindingType.CONTEXT, DomainType.CORRESPONDENCE);
			internalRule.getNodes().add(correspondenceNode);
			internalRule.getCorrespondenceNodes().add(correspondenceNode);
		}
		for(var node : rule.getCorrRule().getCreatedCorrespondenceNodes()) {
			TGGCorrespondence correspondenceNode = transformTGGCorrespondenceNode((TGGCorrespondenceNode) node.getCreation(), BindingType.CREATE, DomainType.CORRESPONDENCE);
			internalRule.getNodes().add(correspondenceNode);
			internalRule.getCorrespondenceNodes().add(correspondenceNode);
		}
		
		for(var node : rule.getTargetRule().getContextNodes()) {
			internalRule.getNodes().add(transformTGGNode((SlimRuleNode) node.getContext(), BindingType.CONTEXT, DomainType.TARGET));
		}
		for(var node : rule.getTargetRule().getCreatedNodes()) {
			internalRule.getNodes().add(transformTGGNode((SlimRuleNode) node.getCreation(), BindingType.CREATE, DomainType.TARGET));
		}
		
		internalRule.getAllNodes().addAll(internalRule.getNodes());
		// now we have to extract the edges and add them to the rule
		Collection<TGGEdge> transferredEdges = new LinkedList<>();
		internalRule.getAllNodes().forEach(n -> transferredEdges.addAll(n.getOutgoingEdges().stream().map(TGGEdge.class::cast).toList()));
		internalRule.getEdges().addAll(transferredEdges);
		internalRule.getAllEdges().addAll(internalRule.getEdges());
		
		internalRule.setAttributeConstraints(cspFactory.createTGGAttributeConstraintSet());
		internalRule.getNodes().forEach(n -> internalRule.getAttributeAssignments().addAll(n.getAttributeAssignments()));
		
		var precondition = factory.createTGGPattern();
		precondition.setAttributeConstraints(cspFactory.createTGGAttributeConstraintSet());
		precondition.setName(rule.getName());
		
		model.getPatternSet().getPatterns().add(precondition);
		internalRule.setPrecondition(precondition);
		populatePrecondition(rule, internalRule, precondition);
		
		var ruleAttributeConstraints = internalRule.getAttributeConstraints();
		for(var attributeCondition : rule.getAttrConditions()) {
			ruleAttributeConstraints.getTggAttributeConstraints().add(transformAttributeCondition(attributeCondition, ruleAttributeConstraints));
		}
		((TGGPattern) internalRule.getPrecondition()).setAttributeConstraints(EcoreUtil.copy(ruleAttributeConstraints));
		
		for(var invocation : rule.getSourceRule().getInvocations()) {
			precondition.getInvocations().add(transformInvocation(precondition, invocation));
		}
		
		for(var invocation : rule.getTargetRule().getInvocations()) {
			precondition.getInvocations().add(transformInvocation(precondition, invocation));
		}
		
		var creation = superFactory.createIBeXRuleDelta();
		creation.getNodes().addAll(filterNodes(internalRule.getNodes(), BindingType.CREATE));
		creation.getEdges().addAll(filterEdges(internalRule.getEdges(), BindingType.CREATE));
		internalRule.setCreation(creation);
		
		fillDerivedTGGRuleFields(internalRule);
		
		internalRule.setNoGeneratedInjectivityConstraints(rule.getConfiguration() != null && rule.getConfiguration().getConfigurations().contains(SlimRuleConfiguration.DISABLE_INJECTIVITY_CONSTRAINTS));
		
		return internalRule;
	}
	

	private void populatePrecondition(org.emoflon.ibex.tgg.tggl.tGGL.TGGRule tggRule, TGGRule internalRule, IBeXPattern precondition) {
		precondition.getSignatureNodes().addAll(filterNodes(internalRule.getNodes(), BindingType.CONTEXT));
		precondition.getEdges().addAll(filterEdges(internalRule.getEdges(), BindingType.CONTEXT));
		
		for(var sourceCondition : tggRule.getSourceRule().getConditions()) {
			precondition.getConditions().add(transformSlimRuleCondition(sourceCondition));
		}
		
		for(var targetCondition : tggRule.getTargetRule().getConditions()) {
			precondition.getConditions().add(transformSlimRuleCondition(targetCondition));
		}
		
		for(var invocation : tggRule.getSourceRule().getInvocations()) {
			precondition.getInvocations().add(transformInvocation(precondition, invocation));
		}
		
		for(var invocation : tggRule.getTargetRule().getInvocations()) {
			precondition.getInvocations().add(transformInvocation(precondition, invocation));
		}
		
		fillConditionReferencesForNodes(precondition.getConditions());
	}
	
	private void fillConditionReferencesForNodes(EList<BooleanExpression> conditions) {
		for (var condition : conditions) {
			if (!(condition instanceof RelationalExpression relationalExpression))
				continue;
			if (relationalExpression.getLhs() instanceof IBeXAttributeValue attributeValue)
				((TGGNode) attributeValue.getNode()).getReferencedByConditions().add(condition);
			if (relationalExpression.getRhs() instanceof IBeXAttributeValue attributeValue)
				((TGGNode) attributeValue.getNode()).getReferencedByConditions().add(condition);
		}
	}

	private Collection<TGGNode> filterNodes(Collection<TGGNode> nodes, BindingType binding) {
		return nodes.stream().filter(n -> n.getBindingType() == binding).collect(Collectors.toSet());
	}
	
	private Collection<TGGEdge> filterEdges(Collection<TGGEdge> edges, BindingType binding) {
		return edges.stream().filter(n -> n.getBindingType() == binding).collect(Collectors.toSet());
	}

	private BooleanExpression transformSlimRuleCondition(SlimRuleCondition condition) {
		return transformBoolExpression(condition.getExpression());
	}
	
	private TGGAttributeConstraint transformAttributeCondition(AttributeCondition attributeCondition, TGGAttributeConstraintSet attributeConstraints) {
		if(tggl2tggModel.containsKey(attributeCondition))
			return (TGGAttributeConstraint) tggl2tggModel.get(attributeCondition);
		
		var attributeConstraint = cspFactory.createTGGAttributeConstraint();
		tggl2tggModel.put(attributeCondition, attributeConstraint);
		
		var definition = transformAttributeConstraintDefinition(attributeCondition.getName());
		attributeConstraint.setDefinition(definition);
		
		for(var i=0; i < attributeCondition.getValues().size(); i++) {
			var value = attributeCondition.getValues().get(i);
			if(value instanceof org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression aritExpr) {
				var valueExpression = transformArithmeticExpression(aritExpr);
				var isDerived = aritExpr instanceof DerivableNodeAttributeExpression;
				
				// we have to check if this parameter already exists
				EObject node = null;
				EAttribute attribute = null;
				if(aritExpr instanceof ExpressionOperand expressionOperand) {					
					if(expressionOperand.getOperand() instanceof NodeAttributeExpression expr) {
						node = expr.getNodeExpression().getNode();
						attribute = expr.getFeature();
					}
					if(expressionOperand.getOperand() instanceof DerivableNodeAttributeExpression expr) {
						node = expr.getNodeExpression().getNode();
						attribute = expr.getFeature();
					}
				}
				
				TGGAttributeConstraintParameterValue paramValue = null;
				if(node != null && attribute != null) {
					var parameter = new NodeAttributeEntry(node, attribute);
					
					// if this is true, then there was no parameter like this before and we have to create it
					// else we simply look it up and add it
					if(nodeAttributeEntry2parameterValue.containsKey(parameter)) {
						paramValue = nodeAttributeEntry2parameterValue.get(parameter);
					}
					else {
						paramValue = cspFactory.createTGGAttributeConstraintParameterValue();
						nodeAttributeEntry2parameterValue.put(parameter, paramValue);
					}
				}

				if(paramValue == null) {
					paramValue = cspFactory.createTGGAttributeConstraintParameterValue();
				}

				if(paramValue.getParameterDefinition() == null) {
					paramValue.setParameterDefinition(definition.getParameterDefinitions().get(i));
					paramValue.setDerived(isDerived);
					paramValue.setExpression(valueExpression);
					attributeConstraints.getParameters().add(paramValue);
				}
				
				// if this param value is new or a constant then we add it to both the constraint and the set of constraints
				attributeConstraint.getParameters().add(paramValue);
			} else {
				throw new RuntimeException(value + " could not be converted to an internal model element");
			}
		}
		
		return attributeConstraint;
	}

	protected TGGCorrespondence transformTGGCorrespondenceNode(TGGCorrespondenceNode node, BindingType binding, DomainType domain) {
		if(tggl2tggModel.containsKey(node))
			return (TGGCorrespondence) tggl2tggModel.get(node);
		
		var corrNode = factory.createTGGCorrespondence();
		tggl2tggModel.put(node, corrNode);
		model.getNodeSet().getNodes().add(corrNode);

		corrNode.setName(node.getName());
		corrNode.setBindingType(binding);
		corrNode.setDomainType(domain);
		corrNode.setOperationType(
		 switch(binding) {
				case CREATE -> IBeXOperationType.CREATION;
				case DELETE -> IBeXOperationType.DELETION;
				default -> IBeXOperationType.CONTEXT;
				}
		);
		corrNode.setType(transformCorrespondenceType(node.getType()));
		
		var source = transformTGGNode(node.getSource(), getBindingType(node.getSource()), DomainType.SOURCE);
		var target = transformTGGNode(node.getTarget(), getBindingType(node.getTarget()), DomainType.TARGET);
		
		corrNode.setSource(source);
		corrNode.setTarget(target);
		
		transformTGGEdge(new EdgeSignature(node, node.getSource(), corrType2sourceRef.get(corrNode.getType())), binding, domain);
		transformTGGEdge(new EdgeSignature(node, node.getTarget(), corrType2targetRef.get(corrNode.getType())), binding, domain);
		
		return corrNode;
	}
	
	private TGGEdge transformTGGEdge(EdgeSignature edgeSignature, BindingType binding, DomainType domain) {
		if(tggl2tggModel.containsKey(edgeSignature))
			return (TGGEdge) tggl2tggModel.get(edgeSignature);
		
		var edge = factory.createTGGEdge();
		model.getEdgeSet().getEdges().add(edge);
		
		edge.setOperationType(switch(binding) {
				case CREATE -> IBeXOperationType.CREATION;
				case DELETE -> IBeXOperationType.DELETION;
				default -> IBeXOperationType.CONTEXT;
				}
		);
		edge.setType(edgeSignature.type());
		edge.setBindingType(binding);
		edge.setDomainType(domain);
		
		
		var source = transformNode(edgeSignature.source(), getBindingType(edgeSignature.source()), getDomainType(edgeSignature.source()));
		var target = transformNode(edgeSignature.target(), getBindingType(edgeSignature.target()), getDomainType(edgeSignature.target()));
		edge.setName(source.getName() + "_" + edgeSignature.type().getName()+"_" + target.getName());
		edge.setSource(source);
		edge.setTarget(target);
		
		return edge;
	}
	
	private IBeXNode transformNode(EObject input) {
		return transformNode(input, getBindingType(input), getDomainType(input));
	}
	
	private IBeXNode transformNode(EObject input, BindingType bindingType, DomainType domainType) {
		if(input instanceof TGGCorrespondenceNode corrNode)
			return transformTGGCorrespondenceNode(corrNode, bindingType, domainType);
		
		if(input instanceof SlimRuleNode slimNode) 
			return transformTGGNode(slimNode, bindingType, domainType);
		
		throw new RuntimeException("Element could not be transformed to an ibex node: " + input );
	}

	private BindingType getBindingType(EObject obj) {
		var contextEdge = getContainer(obj, SlimRuleEdgeContext.class);
		var creationEdge = getContainer(obj, SlimRuleEdgeCreation.class);
		
		var context = getContainer(obj, SlimRuleNodeContext.class);
		var creation = getContainer(obj, SlimRuleNodeCreation.class);
		
		var corrContext = getContainer(obj, TGGCorrespondenceNodeContext.class);
		var corrCreation = getContainer(obj, TGGCorrespondenceNodeCreation.class);

		if(contextEdge != null)
			return BindingType.CONTEXT;
		if(corrContext != null)
			return BindingType.CONTEXT;
		if(context != null)
			return BindingType.CONTEXT;

		if(creationEdge != null)
			return BindingType.CREATE;
		if(corrCreation != null)
			return BindingType.CREATE;
		if(creation != null)
			return BindingType.CREATE;
		
		throw new RuntimeException("Cannot determine bindingtype for " + obj);
	}
	
	public static DomainType getDomainType(EObject context) {
		var tggRule = getContainer(context, org.emoflon.ibex.tgg.tggl.tGGL.TGGRule.class);
		var rule = getContainer(context, TGGDomainRule.class);
		if (rule != null) {
			if (tggRule.getSourceRule().equals(rule))
				return DomainType.SOURCE;
			if (tggRule.getTargetRule().equals(rule))
				return DomainType.TARGET;
		}

		// else it must be a correspondence node
		var corrRule = getContainer(context, TGGCorrRule.class);
		if (corrRule != null) {
			return DomainType.CORRESPONDENCE;
		}
		
		var slimRule = getContainer(context, SlimRule.class);
		if(slimRule != null)
			return DomainType.PATTERN;

		throw new RuntimeException("Could not identify domain of element " + context);
	}
	
	protected TGGNode transformTGGNode(SlimRuleNode node, BindingType binding, DomainType domain) {
		if(tggl2tggModel.containsKey(node))
			return (TGGNode) tggl2tggModel.get(node);
		
		var tggNode = factory.createTGGNode();
		tggl2tggModel.put(node, tggNode);
		model.getNodeSet().getNodes().add(tggNode);
		
		tggNode.setBindingType(binding);
		tggNode.setDomainType(domain);
		tggNode.setName(node.getName());
		tggNode.setType(node.getType());
		tggNode.setOperationType(switch(binding) {
				case CREATE -> IBeXOperationType.CREATION;
				case DELETE -> IBeXOperationType.DELETION;
				default -> IBeXOperationType.CONTEXT;
				}
		);
		
		for(var contextEdge : node.getContextEdges()) {
			var context = contextEdge.getContext();
			transformTGGEdge(new EdgeSignature(node, context.getTarget(), context.getType()), BindingType.CONTEXT, domain);
		}
		for(var createdEdge : node.getCreatedEdges()) {
			var creation = createdEdge.getCreation();
			transformTGGEdge(new EdgeSignature(node, creation.getTarget(), creation.getType()), BindingType.CREATE, domain);
		}
		
		for(var assignment : node.getAssignments()) {			
			var tggNodeAssignment = transformAttributeAssignment(node, assignment);
			tggNode.getAttributeAssignments().add(tggNodeAssignment);
		}
		
		return tggNode;
	}

	protected IBeXPattern transformPattern(SlimRule pattern) {
		if(tggl2tggModel.containsKey(pattern)) {
			return (IBeXPattern) tggl2tggModel.get(pattern);
		}
			
		var internPattern = superFactory.createIBeXPattern();
		internPattern.setName(pattern.getName());
		model.getPatternSet().getPatterns().add(internPattern);
		
		for(var node : pattern.getContextNodes()) {
			if(!node.isLocal())
				internPattern.getSignatureNodes().add(transformTGGNode((SlimRuleNode) node.getContext(), BindingType.CONTEXT, DomainType.PATTERN));	
		}
		
		if(!pattern.getCreatedNodes().isEmpty()) {
			throw new RuntimeException("Patterns are not allowed to create elements!");
		}
		
		for(var invocation : pattern.getInvocations()) {
			internPattern.getInvocations().add(transformInvocation(internPattern, invocation));
		}
		
		model.getNodeSet().getNodes().addAll(internPattern.getSignatureNodes());
		internPattern.setEmpty(!internPattern.getSignatureNodes().isEmpty() && !internPattern.getLocalNodes().isEmpty());
		return internPattern;
	}

	private IBeXPatternInvocation transformInvocation(IBeXPattern parentPattern, SlimRuleInvocation slimInvocation) {
		var invocation = superFactory.createIBeXPatternInvocation();
		invocation.setPositive(slimInvocation.getType() == SlimRuleInvocationType.POSITIVE);
		
		invocation.setInvokedBy(parentPattern);
		invocation.setInvocation(transformPattern((SlimRule) slimInvocation.getSupportPattern()));
		
		for(var slimMapping : slimInvocation.getMappings().getMappings()) {
			SlimRuleNode source = (SlimRuleNode) slimMapping.getSource();
			SlimRuleNode target = (SlimRuleNode) slimMapping.getTarget();
			invocation.getMapping().put(
					transformNode(source, getBindingType(source), getDomainType(source)), 
					transformNode(target, getBindingType(target), getDomainType(target)));
		}
		return invocation;
	}

	protected EPackageDependency transformPackage(final EPackage pkg) throws IOException {
		EPackageDependency dependency = superFactory.createEPackageDependency();

		dependency.setSimpleName(pkg.getName());
		dependency.setHasAlias(false);

		// Set package metadata
		GenPackage genPack = SlimGTEMFUtil.getGenPack(pkg);
		dependency.setFullyQualifiedName(SlimGTEMFUtil.getFQName(genPack));
		dependency.setPackage(pkg);
		dependency.setPackageURI(pkg.getNsURI());
		dependency.setFactoryClassName(SlimGTEMFUtil.getFactoryClassName(genPack));
		dependency.setPackageClassName(SlimGTEMFUtil.getPackageClassName(pkg));
		dependency.setEcoreURI(pkg.eResource().getURI().toString());

		// Set package project metadata and check if it has a project, a proper ecore
		// and genmodel file
		// TODO: Finish this -> for now: set to false
		dependency.setPackageHasProject(false);
		dependency.setEcoreHasLocation(false);
		dependency.setGenmodelHasLocation(false);

		// Add classifier name to fqn map
		pkg.getEClassifiers().forEach(cls -> dependency.getClassifierName2FQN().put(cls.getName(),
				dependency.getFullyQualifiedName() + "." + cls.getName()));

		return dependency;
	}
	
	protected IBeXAttributeAssignment transformAttributeAssignment(final SlimRuleNode assignee,
			final SlimRuleAttributeAssignment assignment) {
		IBeXAttributeAssignment attributeAssign = superFactory.createIBeXAttributeAssignment();
		attributeAssign.setNode((IBeXNode) tggl2tggModel.get(assignee));
		attributeAssign.setAttribute(assignment.getType());
		ValueExpression value = transformValueExpression(assignment.getValue());
		attributeAssign.setValue(value);
		return attributeAssign;
	}

	protected BooleanExpression transformBoolExpression(org.emoflon.ibex.common.slimgt.slimGT.BooleanExpression expression) {
		if (expression instanceof BooleanImplication impl) {
			featureConfig.setBooleanExpressions(true);
			BooleanBinaryExpression binary = arithmeticFactory.createBooleanBinaryExpression();
			binary.setLhs(transformBoolExpression(impl.getLeft()));
			binary.setRhs(transformBoolExpression(impl.getRight()));
			binary.setOperator(BooleanBinaryOperator.IMPLICATION);
			return binary;
		} else if (expression instanceof BooleanDisjunction disj) {
			featureConfig.setBooleanExpressions(true);
			BooleanBinaryExpression binary = arithmeticFactory.createBooleanBinaryExpression();
			binary.setLhs(transformBoolExpression(disj.getLeft()));
			binary.setRhs(transformBoolExpression(disj.getRight()));
			binary.setOperator(switch (disj.getOperator()) {
			case OR -> {
				yield BooleanBinaryOperator.OR;
			}
			case XOR -> {
				yield BooleanBinaryOperator.XOR;
			}
			default -> throw new UnsupportedOperationException("Unkown boolean operator: " + disj.getOperator());
			});
			return binary;
		} else if (expression instanceof BooleanConjunction conj) {
			featureConfig.setBooleanExpressions(true);
			BooleanBinaryExpression binary = arithmeticFactory.createBooleanBinaryExpression();
			binary.setLhs(transformBoolExpression(conj.getLeft()));
			binary.setRhs(transformBoolExpression(conj.getRight()));
			binary.setOperator(switch (conj.getOperator()) {
			case AND -> {
				yield BooleanBinaryOperator.AND;
			}
			default -> throw new UnsupportedOperationException("Unkown boolean operator: " + conj.getOperator());
			});
			return binary;
		} else if (expression instanceof BooleanNegation neg) {
			featureConfig.setBooleanExpressions(true);
			BooleanUnaryExpression unary = arithmeticFactory.createBooleanUnaryExpression();
			unary.setOperand(transformBoolExpression(neg.getOperand()));
			unary.setOperator(BooleanUnaryOperator.NEGATION);
			return unary;
		} else if (expression instanceof BooleanBracket brack) {
			featureConfig.setBooleanExpressions(true);
			BooleanUnaryExpression unary = arithmeticFactory.createBooleanUnaryExpression();
			unary.setOperand(transformBoolExpression(brack.getOperand()));
			unary.setOperator(BooleanUnaryOperator.BRACKET);
			return unary;
		} else if (expression instanceof org.emoflon.ibex.common.slimgt.slimGT.ValueExpression val) {
			featureConfig.setBooleanExpressions(true);
			ValueExpression gtValue = transformValueExpression(val);
			if (gtValue instanceof IBeXBooleanValue boolValue) {
				return boolValue;
			} else if (gtValue instanceof IBeXAttributeValue atrValue
					&& atrValue.getType() == EcorePackage.Literals.EBOOLEAN) {
				return atrValue;
			} else {
				throw new UnsupportedOperationException(
						"Value expression does not return a boolean value: " + expression);
			}
		} else if (expression instanceof org.emoflon.ibex.common.slimgt.slimGT.RelationalExpression rel) {
			RelationalExpression gtRelation = arithmeticFactory.createRelationalExpression();
			gtRelation.setLhs(transformValueExpression(rel.getLhs()));
			gtRelation.setRhs(transformValueExpression(rel.getRhs()));
			gtRelation.setOperator(switch (rel.getRelation()) {
			case EQUAL -> {
				if (gtRelation.getLhs().getType() != EcorePackage.Literals.ESTRING
						&& (gtRelation.getLhs().getType() instanceof EDataType
								|| gtRelation.getLhs().getType() instanceof EEnumLiteral)) {
					yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator.EQUAL;
				} else {
					yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator.OBJECT_EQUALS;
				}
			}
			case GREATER -> {
				yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator.GREATER;
			}
			case GREATER_OR_EQUAL -> {
				yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator.GREATER_OR_EQUAL;
			}
			case SMALLER -> {
				yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator.SMALLER;
			}
			case SMALLER_OR_EQUAL -> {
				yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator.SMALLER_OR_EQUAL;
			}
			case UNEQUAL -> {
				if (gtRelation.getLhs().getType() != EcorePackage.Literals.ESTRING
						&& (gtRelation.getLhs().getType() instanceof EDataType
								|| gtRelation.getLhs().getType() instanceof EEnumLiteral)) {
					yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator.UNEQUAL;
				} else {
					yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator.OBJECT_NOT_EQUALS;
				}
			}
			default -> throw new UnsupportedOperationException("Unknown boolean operator: " + rel.getRelation());
			});
			return gtRelation;
		} else {
			throw new UnsupportedOperationException("Unkown arithmetic operation type: " + expression);
		}
	}

	protected ValueExpression transformValueExpression(org.emoflon.ibex.common.slimgt.slimGT.ValueExpression value) {
		return transformArithmeticExpression((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) value);
	}

	protected ValueExpression transformArithmeticExpression(org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression expression) {
		if (expression instanceof SumArithmeticExpression sum) {
			featureConfig.setArithmeticExpressions(true);
			BinaryExpression binary = arithmeticFactory.createBinaryExpression();
			binary.setLhs(
					(ArithmeticExpression) transformArithmeticExpression((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) sum.getLhs()));
			binary.setRhs(
					(ArithmeticExpression) transformArithmeticExpression((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) sum.getRhs()));
			binary.setType(DataTypeUtil.mergeDataTypes(binary.getLhs(), binary.getRhs()));
			binary.setOperator(switch (sum.getOperator()) {
			case MINUS -> {
				yield BinaryOperator.SUBTRACT;
			}
			case PLUS -> {
				yield BinaryOperator.ADD;
			}
			default -> throw new UnsupportedOperationException("Unknown arithmetic operator: " + sum.getOperator());
			});

			return binary;
		} else if (expression instanceof ProductArithmeticExpression prod) {
			featureConfig.setArithmeticExpressions(true);
			BinaryExpression binary = arithmeticFactory.createBinaryExpression();
			binary.setLhs(
					(ArithmeticExpression) transformArithmeticExpression((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) prod.getLhs()));
			binary.setRhs(
					(ArithmeticExpression) transformArithmeticExpression((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) prod.getRhs()));
			binary.setType(DataTypeUtil.mergeDataTypes(binary.getLhs(), binary.getRhs()));
			binary.setOperator(switch (prod.getOperator()) {
			case MULT -> {
				yield BinaryOperator.MULTIPLY;
			}
			case DIV -> {
				yield BinaryOperator.DIVIDE;
			}
			case MOD -> {
				yield BinaryOperator.MOD;
			}
			default -> throw new UnsupportedOperationException("Unknown arithmetic operator: " + prod.getOperator());
			});

			return binary;
		} else if (expression instanceof ExpArithmeticExpression exp) {
			featureConfig.setArithmeticExpressions(true);
			BinaryExpression binary = arithmeticFactory.createBinaryExpression();
			binary.setLhs(
					(ArithmeticExpression) transformArithmeticExpression((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) exp.getLhs()));
			binary.setRhs(
					(ArithmeticExpression) transformArithmeticExpression((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) exp.getRhs()));
			binary.setType(EcorePackage.Literals.EDOUBLE);
			binary.setOperator(switch (exp.getOperator()) {
			case POW -> {
				yield BinaryOperator.POW;
			}
			case LOG -> {
				yield BinaryOperator.LOG;
			}
			default -> throw new UnsupportedOperationException("Unknown arithmetic operator: " + exp.getOperator());
			});

			return binary;
		} else if (expression instanceof StochasticArithmeticExpression stoc) {
			featureConfig.setArithmeticExpressions(true);
			return switch (stoc.getDistribution()) {
			case NORMAL -> {
				BinaryExpression binary = arithmeticFactory.createBinaryExpression();
				binary.setLhs((ArithmeticExpression) transformArithmeticExpression(stoc.getMean()));
				binary.setRhs((ArithmeticExpression) transformArithmeticExpression(stoc.getSd()));
				binary.setType(EcorePackage.Literals.EDOUBLE);
				binary.setOperator(BinaryOperator.NORMAL_DISTRIBUTION);
				yield binary;
			}
			case UNIFORM -> {
				BinaryExpression binary = arithmeticFactory.createBinaryExpression();
				binary.setLhs((ArithmeticExpression) transformArithmeticExpression(stoc.getMean()));
				binary.setRhs((ArithmeticExpression) transformArithmeticExpression(stoc.getSd()));
				binary.setType(EcorePackage.Literals.EDOUBLE);
				binary.setOperator(BinaryOperator.UNIFORM_DISTRIBUTION);
				yield binary;
			}
			case EXPONENTIAL -> {
				UnaryExpression unary = arithmeticFactory.createUnaryExpression();
				unary.setOperand((ArithmeticExpression) transformArithmeticExpression(stoc.getMean()));
				unary.setType(EcorePackage.Literals.EDOUBLE);
				unary.setOperator(
						org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator.EXPONENTIAL_DISTRIBUTION);
				yield unary;
			}
			default ->
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + stoc.getDistribution());
			};
		} else if (expression instanceof MinMaxArithmeticExpression minMax) {
			featureConfig.setArithmeticExpressions(true);
			BinaryExpression binary = arithmeticFactory.createBinaryExpression();
			binary.setLhs(
					(ArithmeticExpression) transformArithmeticExpression((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) minMax.getLhs()));
			binary.setRhs(
					(ArithmeticExpression) transformArithmeticExpression((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) minMax.getRhs()));
			binary.setType(DataTypeUtil.mergeDataTypes(binary.getLhs(), binary.getRhs()));
			binary.setOperator(switch (minMax.getMinMaxOperator()) {
			case MIN -> {
				yield BinaryOperator.MIN;
			}
			case MAX -> {
				yield BinaryOperator.MAX;
			}
			default ->
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + minMax.getMinMaxOperator());
			});

			return binary;
		} else if (expression instanceof UnaryArithmeticExpression un) {
			featureConfig.setArithmeticExpressions(true);
			UnaryExpression unary = arithmeticFactory.createUnaryExpression();
			unary.setOperand((ArithmeticExpression) transformArithmeticExpression((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) expression.getLhs()));
			unary.setOperator(switch (un.getOperator()) {
			case NEG -> {
				unary.setType(unary.getOperand().getType());
				yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator.NEGATIVE;
			}
			case ABS -> {
				unary.setType(unary.getOperand().getType());
				yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator.ABSOLUTE;
			}
			case SQRT -> {
				unary.setType(EcorePackage.Literals.EDOUBLE);
				yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator.SQRT;
			}
			case SIN -> {
				unary.setType(EcorePackage.Literals.EDOUBLE);
				yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator.SIN;
			}
			case COS -> {
				unary.setType(EcorePackage.Literals.EDOUBLE);
				yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator.COS;
			}
			case TAN -> {
				unary.setType(EcorePackage.Literals.EDOUBLE);
				yield org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator.TAN;
			}
			default -> throw new UnsupportedOperationException("Unknown arithmetic operator: " + un.getOperator());
			});
			return unary;
		} else if (expression instanceof BracketExpression brack) {
			featureConfig.setArithmeticExpressions(true);
			UnaryExpression unary = arithmeticFactory.createUnaryExpression();
			unary.setOperand((ArithmeticExpression) transformArithmeticExpression((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) expression.getLhs()));
			unary.setType(unary.getOperand().getType());
			unary.setOperator(org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator.BRACKET);
			return unary;
		} else if (expression instanceof DerivableNodeAttributeExpression nae) {
			IBeXAttributeValue atrValue = superFactory.createIBeXAttributeValue();
			atrValue.setNode(transformNode(nae.getNodeExpression().getNode()));
			atrValue.setType(nae.getFeature().getEType());
			atrValue.setAttribute(nae.getFeature());
			return atrValue;
		} else if (expression instanceof org.emoflon.ibex.common.slimgt.slimGT.ExpressionOperand op) {
			if (op.getOperand() instanceof NodeExpression ne) {
				IBeXNodeValue nodeValue = superFactory.createIBeXNodeValue();
				
				nodeValue.setNode(transformNode(ne.getNode()));
				nodeValue.setType(nodeValue.getNode().getType());
				return nodeValue;
			} else if (op.getOperand() instanceof NodeAttributeExpression nae) {
				IBeXAttributeValue atrValue = superFactory.createIBeXAttributeValue();
				atrValue.setNode(transformNode(nae.getNodeExpression().getNode()));
				atrValue.setType(nae.getFeature().getEType());
				atrValue.setAttribute(nae.getFeature());
				return atrValue;
			} else if (op.getOperand() instanceof DerivableNodeAttributeExpression nae) {
				IBeXAttributeValue atrValue = superFactory.createIBeXAttributeValue();
				atrValue.setNode(transformNode(nae.getNodeExpression().getNode()));
				atrValue.setType(nae.getFeature().getEType());
				atrValue.setAttribute(nae.getFeature());
				return atrValue;
			} else if (op.getOperand() instanceof ArithmeticLiteral lit) {
				if (lit.getValue() instanceof DoubleLiteral d) {
					org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.DoubleLiteral gtD = arithmeticFactory
							.createDoubleLiteral();
					gtD.setValue(d.getValue());
					gtD.setType(EcorePackage.Literals.EDOUBLE);
					return gtD;
				} else if (lit.getValue() instanceof IntegerLiteral i) {
					org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IntegerLiteral gtI = arithmeticFactory
							.createIntegerLiteral();
					gtI.setValue(i.getValue());
					gtI.setType(EcorePackage.Literals.EINT);
					return gtI;
				} else if (lit.getValue() instanceof StringLiteral s) {
					IBeXStringValue gtS = superFactory.createIBeXStringValue();
					gtS.setValue(s.getValue());
					gtS.setType(EcorePackage.Literals.ESTRING);
					return gtS;
				} else if (lit.getValue() instanceof BooleanLiteral b) {
					IBeXBooleanValue gtB = superFactory.createIBeXBooleanValue();
					gtB.setValue(b.isValue());
					gtB.setType(EcorePackage.Literals.EBOOLEAN);
					return gtB;
				} else {
					throw new UnsupportedOperationException("Unkown arithmetic literal type: " + lit);
				}
			} else if (op.getOperand() instanceof EnumExpression en) {
				IBeXEnumValue enumVal = superFactory.createIBeXEnumValue();
				enumVal.setLiteral(en.getLiteral());
				enumVal.setType(en.getLiteral().getEEnum());
				return enumVal;
			} else if (op.getOperand() instanceof LocalVariable lo) {
				TGGLocalVariable localVariable = cspFactory.createTGGLocalVariable();
				localVariable.setName(lo.getName());
				
				var condition = (AttributeCondition) expression.eContainer();
				
				// this condition must have already been created to giving null for the constraint set will not be a problem
				TGGAttributeConstraint constraint = transformAttributeCondition(condition, null);
				var index = condition.getValues().indexOf(expression);
				localVariable.setType(constraint.getDefinition().getParameterDefinitions().get(index).getType());
				return localVariable;
			} else if (op.getOperand() instanceof Constant con) {
				return switch (con.getValue()) {
				case E -> {
					org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.DoubleLiteral gtD = arithmeticFactory
							.createDoubleLiteral();
					gtD.setValue(Math.E);
					gtD.setType(EcorePackage.Literals.EDOUBLE);
					yield gtD;
				}
				case NULL -> {
					IBeXNullValue nullVal = superFactory.createIBeXNullValue();
					nullVal.setType(EcorePackage.Literals.EOBJECT);
					yield (ArithmeticExpression) nullVal;
				}
				case PI -> {
					org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.DoubleLiteral gtD = arithmeticFactory
							.createDoubleLiteral();
					gtD.setValue(Math.PI);
					gtD.setType(EcorePackage.Literals.EDOUBLE);
					yield gtD;
				}
				default -> throw new UnsupportedOperationException("Unkown constant: " + con);
				};
			} else {
				throw new UnsupportedOperationException("Unkown arithmetic operand type: " + op.getOperand());
			}
		} else {
			throw new UnsupportedOperationException("Unkown arithmetic operation type: " + expression);
		}
	}
	
	protected void postProcessing() {
		Comparator<IBeXNamedElement> comparator = new Comparator<IBeXNamedElement>() {
			@Override
			public int compare(IBeXNamedElement o1, IBeXNamedElement o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
	}
	
}

record NodeAttributeEntry(EObject node, EAttribute attribute) {
	
}

record EdgeSignature(EObject source, EObject target, EReference type) {
	
}

record NodePair(EObject first, EObject second) {
	
}
