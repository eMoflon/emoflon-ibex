package org.emoflon.ibex.gt.transformation;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXMatchCountValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModelMetadata;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNullValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;
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
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator;
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
import org.emoflon.ibex.common.slimgt.slimGT.CountExpression;
import org.emoflon.ibex.common.slimgt.slimGT.DoubleLiteral;
import org.emoflon.ibex.common.slimgt.slimGT.EnumExpression;
import org.emoflon.ibex.common.slimgt.slimGT.ExpArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.IntegerLiteral;
import org.emoflon.ibex.common.slimgt.slimGT.MinMaxArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.NodeAttributeExpression;
import org.emoflon.ibex.common.slimgt.slimGT.NodeExpression;
import org.emoflon.ibex.common.slimgt.slimGT.ProductArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleAttributeAssignment;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleCondition;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleConfiguration;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleEdge;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleInvocation;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleNodeMapping;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleNodeMappings;
import org.emoflon.ibex.common.slimgt.slimGT.StochasticArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.StringLiteral;
import org.emoflon.ibex.common.slimgt.slimGT.SumArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.UnaryArithmeticExpression;
import org.emoflon.ibex.common.slimgt.util.SlimGTEMFUtil;
import org.emoflon.ibex.common.slimgt.util.SlimGTModelUtil;
import org.emoflon.ibex.common.transformation.DataTypeUtil;
import org.emoflon.ibex.common.transformation.SlimGtToIBeXCoreTransformer;
import org.emoflon.ibex.gt.gtl.gTL.EdgeIteratorOperator;
import org.emoflon.ibex.gt.gtl.gTL.EditorFile;
import org.emoflon.ibex.gt.gtl.gTL.ExpressionOperand;
import org.emoflon.ibex.gt.gtl.gTL.GTLEdgeIterator;
import org.emoflon.ibex.gt.gtl.gTL.GTLEdgeIteratorAttributeAssignment;
import org.emoflon.ibex.gt.gtl.gTL.GTLEdgeIteratorReference;
import org.emoflon.ibex.gt.gtl.gTL.GTLIteratorAttributeExpression;
import org.emoflon.ibex.gt.gtl.gTL.GTLParameterExpression;
import org.emoflon.ibex.gt.gtl.gTL.GTLRuleNodeDeletion;
import org.emoflon.ibex.gt.gtl.gTL.GTLRuleType;
import org.emoflon.ibex.gt.gtl.gTL.GTLRuleWatchDog;
import org.emoflon.ibex.gt.gtl.gTL.Import;
import org.emoflon.ibex.gt.gtl.gTL.SlimParameter;
import org.emoflon.ibex.gt.gtl.gTL.SlimRule;
import org.emoflon.ibex.gt.gtl.gTL.SlimRuleNode;
import org.emoflon.ibex.gt.gtl.gTL.SlimRuleNodeContext;
import org.emoflon.ibex.gt.gtl.gTL.SlimRuleNodeCreation;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeAssignment;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeReference;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorEdge;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameterValue;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelFactory;
import org.moflon.core.utilities.LogUtils;

public class GTLtoGTModelTransformer extends SlimGtToIBeXCoreTransformer<EditorFile, GTModel, IBeXGTModelFactory> {
	private Logger logger = Logger.getLogger(GTLtoGTModelTransformer.class);

	protected final Map<SlimRule, GTRule> rule2rule = Collections.synchronizedMap(new HashMap<>());
	protected final Map<SlimRule, GTPattern> pattern2pattern = Collections.synchronizedMap(new HashMap<>());
	protected final Map<SlimRuleNode, IBeXNode> node2node = Collections.synchronizedMap(new HashMap<>());
	protected final Map<SlimRuleEdge, IBeXEdge> edge2edge = Collections.synchronizedMap(new HashMap<>());
	protected final Map<SlimParameter, GTParameter> param2param = Collections.synchronizedMap(new HashMap<>());
	protected final Map<GTLEdgeIterator, GTForEachExpression> iterator2iterator = Collections
			.synchronizedMap(new HashMap<>());

	protected final Map<SlimRuleNode, List<Consumer<IBeXNode>>> pendingNodeJobs = Collections
			.synchronizedMap(new HashMap<>());
	protected final Map<GTLEdgeIterator, List<Consumer<GTForEachExpression>>> pendingIteratorJobs = Collections
			.synchronizedMap(new HashMap<>());
	protected final List<Runnable> pendingInvocationJobs = Collections.synchronizedList(new LinkedList<>());

	public GTLtoGTModelTransformer(final EditorFile editorFile, final IProject project) {
		super(editorFile, project);
	}

	@Override
	protected IBeXGTModelFactory initFactory() {
		return IBeXGTModelFactory.eINSTANCE;
	}

	@Override
	protected GTModel createNewModel() {
		return factory.createGTModel();
	}

	@Override
	protected IBeXModelMetadata createModelMetadata() {
		IBeXModelMetadata metadata = superFactory.createIBeXModelMetadata();
		metadata.setProject(project.getName());
		metadata.setProjectPath(project.getLocation().toPortableString());
		metadata.setPackage(editorFile.getPackage().getName());
		metadata.setPackagePath("/src/" + metadata.getPackage().replace(".", "/"));

		for (Import imp : editorFile.getImports().stream().map(imp -> (Import) imp).collect(Collectors.toList())) {
			EPackageDependency dependency = null;
			try {
				dependency = transform(imp);
			} catch (IOException e) {
				LogUtils.error(logger, e);
				continue;
			}
			metadata.getDependencies().add(dependency);
			metadata.getName2package().put(dependency.getSimpleName(), dependency);
		}

		// Add the ecore package if not present
		if (!metadata.getName2package().containsKey("ecore")) {
			try {
				EPackageDependency dependency = transform(EcorePackage.eINSTANCE);
				metadata.getDependencies().add(dependency);
				metadata.getName2package().put(dependency.getSimpleName(), dependency);
			} catch (IOException e) {
				LogUtils.error(logger, e);
			}
		}

		return metadata;
	}

	protected EPackageDependency transform(final Import imp) throws IOException {
		EPackage pkg = SlimGTEMFUtil.loadMetamodel(imp.getName());
		return transform(pkg);
	}

	protected EPackageDependency transform(final EPackage pkg) throws IOException {
		EPackageDependency dependency = superFactory.createEPackageDependency();

		dependency.setSimpleName(pkg.getName());
		dependency.setHasAlias(false);

		// Set package metadata
		if (pkg.getName().equals("ecore")) {
			dependency.setFullyQualifiedName("org.eclipse.emf.ecore");
			dependency.setPackage(pkg);
			dependency.setPackageURI(pkg.getNsURI());
			dependency.setFactoryClassName("EcoreFactory");
			dependency.setPackageClassName("EcorePackage");
			dependency.setEcoreURI(pkg.eResource().getURI().toString());
			dependency.setPackageHasProject(false);
			dependency.setEcoreHasLocation(false);
			dependency.setGenmodelHasLocation(false);
		} else {
			GenPackage genPack = SlimGTEMFUtil.getGenPack(pkg);
			dependency.setFullyQualifiedName(SlimGTEMFUtil.getFQName(genPack));
			dependency.setPackage(pkg);
			dependency.setPackageURI(pkg.getNsURI());
			dependency.setFactoryClassName(SlimGTEMFUtil.getFactoryClassName(genPack));
			dependency.setPackageClassName(SlimGTEMFUtil.getPackageClassName(pkg));
			dependency.setEcoreURI(pkg.eResource().getURI().toString());
			IProject other = SlimGTEMFUtil.getProjectOfEPackage(project, pkg);
			if (other == null) {
				dependency.setPackageHasProject(false);
				dependency.setEcoreHasLocation(false);
				dependency.setGenmodelHasLocation(false);
			} else {
				dependency.setPackageHasProject(true);
				dependency.setProjectName(other.getName());
				dependency.setProjectLocation(other.getLocation().toPortableString());
				// Check if it has a proper ecore and genmodel file
				// TODO: Finish this -> for now: set to false
				dependency.setEcoreHasLocation(false);
				dependency.setGenmodelHasLocation(false);
			}

		}

		// Add classifier name to fqn map
		pkg.getEClassifiers().forEach(cls -> dependency.getClassifierName2FQN().put(cls.getName(),
				dependency.getFullyQualifiedName() + "." + cls.getName()));

		return dependency;
	}

	@Override
	public GTModel transform() {
		model.setRuleSet(factory.createGTRuleSet());

		editorFile.getRules().parallelStream().filter(rule -> !rule.isAbstract()).forEach(rule -> {
			// Transform to gt pattern
			if (rule.getType() == GTLRuleType.PATTERN) {
				transformPrecondition(rule);
			} else { // Transform to gt rule
				transformRule(rule);
			}
		});

		postProcessing();

		return model;
	}

	protected void postProcessing() {
		Comparator<IBeXNamedElement> comparator = new Comparator<IBeXNamedElement>() {
			@Override
			public int compare(IBeXNamedElement o1, IBeXNamedElement o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};

		List<GTRule> rules = new LinkedList<>(rule2rule.values());
		Collections.sort(rules, comparator);
		model.getRuleSet().getRules().addAll(rules);

		List<GTPattern> patterns = new LinkedList<>(pattern2pattern.values());
		Collections.sort(patterns, comparator);
		model.getPatternSet().getPatterns().addAll(patterns);

		model.setFeatureConfig(superFactory.createIBeXFeatureConfig());
		patterns.forEach(p -> {
			if (p.getUsedFeatures().isArithmeticExpressions())
				model.getFeatureConfig().setArithmeticExpressions(true);
			if (p.getUsedFeatures().isBooleanExpressions())
				model.getFeatureConfig().setBooleanExpressions(true);
			if (p.getUsedFeatures().isCountExpressions())
				model.getFeatureConfig().setCountExpressions(true);
			if (p.getUsedFeatures().isParameterExpressions())
				model.getFeatureConfig().setParameterExpressions(true);
		});

		node2node.entrySet().stream().filter(entry -> pendingNodeJobs.containsKey(entry.getKey())).forEach(
				entry -> pendingNodeJobs.get(entry.getKey()).forEach(consumer -> consumer.accept(entry.getValue())));

		iterator2iterator.entrySet().stream().filter(entry -> pendingIteratorJobs.containsKey(entry.getKey()))
				.forEach(entry -> pendingIteratorJobs.get(entry.getKey())
						.forEach(consumer -> consumer.accept(entry.getValue())));
		pendingInvocationJobs.forEach(i -> i.run());

		List<IBeXNode> nodes = new LinkedList<>(node2node.values());
		Collections.sort(nodes, comparator);
		model.getNodeSet().getNodes().addAll(nodes);

		List<IBeXEdge> edges = new LinkedList<>(edge2edge.values());
		Collections.sort(edges, comparator);
		model.getEdgeSet().getEdges().addAll(edges);
	}

	protected GTRule transformRule(SlimRule rule) {
		if (rule.getType() != GTLRuleType.RULE)
			return null;

		if (rule2rule.containsKey(rule)) {
			return rule2rule.get(rule);
		}

		GTRule gtRule = factory.createGTRule();
		gtRule.setName(rule.getName());
		rule2rule.put(rule, gtRule);

		// Parameters
		for (SlimParameter param : rule.getParameters()) {
			gtRule.getParameters().add(transform(param));
		}

		// Precondition pattern
		GTPattern precondition = transformPrecondition(rule);
		gtRule.setPrecondition(precondition);

		// Postcondition pattern
		GTPattern postcondition = transformPostcondition(rule);
		gtRule.setPostcondition(postcondition);

		// Add all nodes and edges
		Set<IBeXNode> nodes = new HashSet<>();
		nodes.addAll(precondition.getSignatureNodes());
		nodes.addAll(precondition.getLocalNodes());
		nodes.addAll(postcondition.getSignatureNodes());
		nodes.addAll(postcondition.getLocalNodes());
		Set<IBeXEdge> edges = new HashSet<>();
		edges.addAll(precondition.getEdges());
		edges.addAll(postcondition.getEdges());
		gtRule.getAllNodes().addAll(nodes);
		gtRule.getAllEdges().addAll(edges);

		// Deletion delta
		gtRule.setDeletion(transformDeletion(precondition));

		// Creation delta
		gtRule.setCreation(transformCreation(postcondition));

		// Attribute assignments
		for (SlimRuleNode context : rule.getContextNodes().stream().map(n -> (SlimRuleNodeContext) n)
				.filter(n -> !n.isLocal()).map(n -> (SlimRuleNode) n.getContext()).collect(Collectors.toList())) {
			for (SlimRuleAttributeAssignment assignment : context.getAssignments()) {
				IBeXAttributeAssignment gtAssign = transform(context, assignment);
				gtRule.getAttributeAssignments().add(gtAssign);
			}

		}
		for (SlimRuleNode creation : rule.getCreatedNodes().stream().map(n -> (SlimRuleNodeCreation) n)
				.map(n -> (SlimRuleNode) n.getCreation()).collect(Collectors.toList())) {
			for (SlimRuleAttributeAssignment assignment : creation.getAssignments()) {
				IBeXAttributeAssignment gtAssign = transform(creation, assignment);
				gtRule.getAttributeAssignments().add(gtAssign);
			}
		}

		// Edge iterators
		for (SlimRuleNode context : rule.getContextNodes().stream().map(n -> (SlimRuleNodeContext) n)
				.filter(n -> !n.isLocal()).map(n -> (SlimRuleNode) n.getContext()).collect(Collectors.toList())) {
			for (GTLEdgeIterator iterator : context.getEdgeIterators()) {
				GTForEachExpression forEach = transform(context, iterator);
				gtRule.getForEachOperations().add(forEach);
			}

		}
		for (SlimRuleNode deletion : rule.getDeletedNodes().stream().map(n -> n.getDeletion())
				.collect(Collectors.toList())) {
			for (GTLEdgeIterator iterator : deletion.getEdgeIterators()) {
				GTForEachExpression forEach = transform(deletion, iterator);
				gtRule.getForEachOperations().add(forEach);
			}
		}

		// Probabilities
		if (rule.isStochastic()) {
			IBeXFeatureConfig features = superFactory.createIBeXFeatureConfig();
			ArithmeticExpression probability = (ArithmeticExpression) transform(rule.getProbability(), features);
			gtRule.setProbability(probability);
		}

		return gtRule;
	}

	protected GTParameter transform(final SlimParameter param) {
		if (param2param.containsKey(param)) {
			return param2param.get(param);
		}

		GTParameter gtParam = factory.createGTParameter();
		gtParam.setName(param.getName());
		gtParam.setType(param.getType());
		param2param.put(param, gtParam);

		return gtParam;
	}

	protected GTPattern transformPrecondition(final SlimRule pattern) {
		if (pattern2pattern.containsKey(pattern)) {
			return pattern2pattern.get(pattern);
		}

		GTPattern gtPattern = factory.createGTPattern();
		gtPattern.setName(pattern.getName());
		pattern2pattern.put(pattern, gtPattern);

		// Transform parameters if pattern ist not a rule
		if (pattern.getType() == GTLRuleType.PATTERN) {
			for (SlimParameter param : pattern.getParameters()) {
				gtPattern.getParameters().add(transform(param));
			}
		}
		// Context nodes
		for (SlimRuleNodeContext context : pattern.getContextNodes().stream().map(n -> (SlimRuleNodeContext) n)
				.collect(Collectors.toList())) {
			IBeXNode gtNode = transformPrecondition((SlimRuleNode) context.getContext());
			if (context.isLocal()) {
				gtPattern.getLocalNodes().add(gtNode);
			} else {
				gtPattern.getSignatureNodes().add(gtNode);
			}
			gtNode.setOperationType(IBeXOperationType.CONTEXT);
		}

		// Deleted nodes that must be present as context
		for (GTLRuleNodeDeletion deletion : pattern.getDeletedNodes()) {
			IBeXNode gtNode = transformPrecondition(deletion.getDeletion());
			gtPattern.getSignatureNodes().add(gtNode);
			gtNode.setOperationType(IBeXOperationType.DELETION);
		}

		// Attribute and node conditions
		IBeXFeatureConfig features = superFactory.createIBeXFeatureConfig();
		gtPattern.setUsedFeatures(features);
		for (SlimRuleCondition condition : pattern.getConditions()) {
			BooleanExpression expression = transform(condition.getExpression(), features);
			gtPattern.getConditions().add(expression);
		}

		// Invocations
		for (SlimRuleInvocation invocation : pattern.getInvocations()) {
			IBeXPatternInvocation gtInvocation = transform(pattern, (SlimRule) invocation.getSupportPattern(),
					invocation.getMappings());
			gtPattern.getInvocations().add(gtInvocation);
		}

		// Watch dogs
		for (GTLRuleWatchDog watchDog : pattern.getWatchDogs()) {
			GTWatchDog gtWatch = transform(watchDog);
			gtPattern.getWatchDogs().add(gtWatch);
		}

		// Automatic injectivity constraints, if necessary
		if (!pattern.isConfigured() || !pattern.getConfiguration().getConfigurations()
				.contains(SlimRuleConfiguration.DISABLE_INJECTIVITY_CONSTRAINTS)) {
			Set<IBeXNodePair> pairs = new HashSet<>();
			for (IBeXNode n1 : gtPattern.getSignatureNodes()) {
				for (IBeXNode n2 : gtPattern.getSignatureNodes()) {
					if (n2.equals(n1))
						continue;

					IBeXNodePair pair = new IBeXNodePair(n1, n2);
					if (!pairs.contains(pair))
						pairs.add(pair);
				}
			}

			for (IBeXNodePair pair : pairs) {
				RelationalExpression relation = arithmeticFactory.createRelationalExpression();
				relation.setOperator(RelationalOperator.OBJECT_NOT_EQUALS);
				IBeXNodeValue v1 = superFactory.createIBeXNodeValue();
				v1.setNode(pair.n1());
				v1.setType(EcorePackage.Literals.EOBJECT);
				IBeXNodeValue v2 = superFactory.createIBeXNodeValue();
				v2.setNode(pair.n2());
				v2.setType(EcorePackage.Literals.EOBJECT);
				relation.setLhs(v1);
				relation.setRhs(v2);
				gtPattern.getConditions().add(relation);
			}
		}

		if (gtPattern.getSignatureNodes().isEmpty() && gtPattern.getLocalNodes().isEmpty()
				&& gtPattern.getConditions().isEmpty())
			gtPattern.setEmpty(true);

		return gtPattern;
	}

	protected GTPattern transformPostcondition(SlimRule pattern) {
		GTPattern gtPattern = factory.createGTPattern();
		gtPattern.setName(pattern.getName() + "CoPattern");

		// Context nodes
		for (SlimRuleNodeContext context : pattern.getContextNodes().stream().map(n -> (SlimRuleNodeContext) n)
				.collect(Collectors.toList())) {
			IBeXNode gtNode = transformPostcondition((SlimRuleNode) context.getContext());
			if (context.isLocal()) {
				gtPattern.getLocalNodes().add(gtNode);
			} else {
				gtPattern.getSignatureNodes().add(gtNode);
			}
			gtNode.setOperationType(IBeXOperationType.CONTEXT);
		}

		// Created nodes that must be present after the rule has been applied
		for (SlimRuleNodeCreation creation : pattern.getCreatedNodes().stream().map(n -> (SlimRuleNodeCreation) n)
				.collect(Collectors.toList())) {
			IBeXNode gtNode = transformPostcondition((SlimRuleNode) creation.getCreation());
			gtPattern.getSignatureNodes().add(gtNode);
			gtNode.setOperationType(IBeXOperationType.CREATION);
		}

		// Attribute and node conditions that must hold after the rule has been applied
		IBeXFeatureConfig features = superFactory.createIBeXFeatureConfig();
		for (SlimRuleCondition condition : pattern.getConditions()) {
			BooleanExpression expression = transform(condition.getExpression(), features);
			gtPattern.getConditions().add(expression);
		}

		// Invocations are irrelevant for the postcondition, so are watch dogs and
		// injectivity constraints

		if (gtPattern.getSignatureNodes().isEmpty() && gtPattern.getLocalNodes().isEmpty()
				&& gtPattern.getConditions().isEmpty())
			gtPattern.setEmpty(true);

		return gtPattern;
	}

	protected IBeXNode transformPrecondition(SlimRuleNode node) {
		IBeXNode gtNode = null;
		if (node2node.containsKey(node)) {
			gtNode = node2node.get(node);
		} else {
			gtNode = superFactory.createIBeXNode();
			gtNode.setName(node.getName());
			gtNode.setType(node.getType());
			node2node.put(node, gtNode);
		}

		for (SlimRuleEdge edge : node.getContextEdges().stream().map(e -> e.getContext())
				.collect(Collectors.toList())) {
			IBeXEdge gtEdge = transform(gtNode, edge);
			gtEdge.setOperationType(IBeXOperationType.CONTEXT);
		}

		for (SlimRuleEdge edge : node.getDeletedEdges().stream().map(e -> e.getDeletion())
				.collect(Collectors.toList())) {
			IBeXEdge gtEdge = transform(gtNode, edge);
			gtEdge.setOperationType(IBeXOperationType.DELETION);
		}

		// Skip attribute assignments and edge iterators, since these are irrelevant for
		// pattern matching.

		return gtNode;
	}

	protected IBeXNode transformPostcondition(SlimRuleNode node) {
		IBeXNode gtNode = null;
		if (node2node.containsKey(node)) {
			gtNode = node2node.get(node);
		} else {
			gtNode = superFactory.createIBeXNode();
			gtNode.setName(node.getName());
			gtNode.setType(node.getType());
			node2node.put(node, gtNode);
		}

		for (SlimRuleEdge edge : node.getContextEdges().stream().map(e -> e.getContext())
				.collect(Collectors.toList())) {
			IBeXEdge gtEdge = transform(gtNode, edge);
			gtEdge.setOperationType(IBeXOperationType.CONTEXT);
		}

		for (SlimRuleEdge edge : node.getCreatedEdges().stream().map(e -> e.getCreation())
				.collect(Collectors.toList())) {
			IBeXEdge gtEdge = transform(gtNode, edge);
			gtEdge.setOperationType(IBeXOperationType.CREATION);
		}

		// Skip attribute assignments and edge iterators, since the postcondition is
		// more of a nice to have
		// and technically irrelevant for GT-rule application by the interpreter.

		return gtNode;
	}

	protected IBeXEdge transform(IBeXNode gtSourceNode, SlimRuleEdge edge) {
		if (edge2edge.containsKey(edge)) {
			return edge2edge.get(edge);
		}

		IBeXEdge gtEdge = superFactory.createIBeXEdge();
		edge2edge.put(edge, gtEdge);

		gtEdge.setSource(gtSourceNode);
		if (node2node.containsKey(edge.getTarget())) {
			gtEdge.setTarget(node2node.get(edge.getTarget()));
			gtEdge.setType(edge.getType());
			setEdgeName(gtEdge);
		} else {
			addPendingNodeConsumer((SlimRuleNode) edge.getTarget(), (node) -> {
				gtEdge.setTarget(node);
				gtEdge.setType(edge.getType());
				setEdgeName(gtEdge);
			});
		}
		gtEdge.setType(edge.getType());

		return gtEdge;
	}

	protected IBeXRuleDelta transformDeletion(final GTPattern precondition) {
		IBeXRuleDelta delta = superFactory.createIBeXRuleDelta();
		for (IBeXNode node : precondition.getSignatureNodes()) {
			if (node.getOperationType() == IBeXOperationType.DELETION) {
				delta.getNodes().add(node);
			}
		}

		for (IBeXEdge edge : precondition.getEdges()) {
			if (edge.getOperationType() == IBeXOperationType.DELETION) {
				delta.getEdges().add(edge);
			}
		}

		if (delta.getNodes().isEmpty() && delta.getEdges().isEmpty())
			delta.setEmpty(true);
		else
			delta.setEmpty(false);

		return delta;
	}

	protected IBeXRuleDelta transformCreation(GTPattern postcondition) {
		IBeXRuleDelta delta = superFactory.createIBeXRuleDelta();
		for (IBeXNode node : postcondition.getSignatureNodes()) {
			if (node.getOperationType() == IBeXOperationType.CREATION) {
				delta.getNodes().add(node);
			}
		}

		for (IBeXEdge edge : postcondition.getEdges()) {
			if (edge.getOperationType() == IBeXOperationType.CREATION) {
				delta.getEdges().add(edge);
			}
		}

		if (delta.getNodes().isEmpty() && delta.getEdges().isEmpty())
			delta.setEmpty(true);
		else
			delta.setEmpty(false);

		return delta;
	}

	protected IBeXAttributeAssignment transform(final SlimRuleNode assignee,
			final SlimRuleAttributeAssignment assignment) {
		IBeXAttributeAssignment gtAssign = superFactory.createIBeXAttributeAssignment();
		gtAssign.setNode(node2node.get(assignee));
		gtAssign.setAttribute(assignment.getType());
		IBeXFeatureConfig features = superFactory.createIBeXFeatureConfig();
		ValueExpression value = transform(assignment.getValue(), features);
		gtAssign.setValue(value);
		return gtAssign;
	}

	protected BooleanExpression transform(org.emoflon.ibex.common.slimgt.slimGT.BooleanExpression expression,
			IBeXFeatureConfig features) {
		if (expression instanceof BooleanImplication impl) {
			features.setBooleanExpressions(true);
			BooleanBinaryExpression binary = arithmeticFactory.createBooleanBinaryExpression();
			binary.setLhs(transform(impl.getLeft(), features));
			binary.setRhs(transform(impl.getRight(), features));
			binary.setOperator(BooleanBinaryOperator.IMPLICATION);
			return binary;
		} else if (expression instanceof BooleanDisjunction disj) {
			features.setBooleanExpressions(true);
			BooleanBinaryExpression binary = arithmeticFactory.createBooleanBinaryExpression();
			binary.setLhs(transform(disj.getLeft(), features));
			binary.setRhs(transform(disj.getRight(), features));
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
			features.setBooleanExpressions(true);
			BooleanBinaryExpression binary = arithmeticFactory.createBooleanBinaryExpression();
			binary.setLhs(transform(conj.getLeft(), features));
			binary.setRhs(transform(conj.getRight(), features));
			binary.setOperator(switch (conj.getOperator()) {
			case AND -> {
				yield BooleanBinaryOperator.AND;
			}
			default -> throw new UnsupportedOperationException("Unkown boolean operator: " + conj.getOperator());
			});
			return binary;
		} else if (expression instanceof BooleanNegation neg) {
			features.setBooleanExpressions(true);
			BooleanUnaryExpression unary = arithmeticFactory.createBooleanUnaryExpression();
			unary.setOperand(transform(neg.getOperand(), features));
			unary.setOperator(BooleanUnaryOperator.NEGATION);
			return unary;
		} else if (expression instanceof BooleanBracket brack) {
			features.setBooleanExpressions(true);
			BooleanUnaryExpression unary = arithmeticFactory.createBooleanUnaryExpression();
			unary.setOperand(transform(brack.getOperand(), features));
			unary.setOperator(BooleanUnaryOperator.BRACKET);
			return unary;
		} else if (expression instanceof org.emoflon.ibex.common.slimgt.slimGT.ValueExpression val) {
			features.setBooleanExpressions(true);
			ValueExpression gtValue = transform(val, features);
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
			gtRelation.setLhs(transform(rel.getLhs(), features));
			gtRelation.setRhs(transform(rel.getRhs(), features));
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

	protected ValueExpression transform(org.emoflon.ibex.common.slimgt.slimGT.ValueExpression value,
			IBeXFeatureConfig features) {
		return transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) value, features);
	}

	protected ArithmeticExpression transform(org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression expression,
			IBeXFeatureConfig features) {
		if (expression instanceof SumArithmeticExpression sum) {
			features.setArithmeticExpressions(true);
			BinaryExpression binary = arithmeticFactory.createBinaryExpression();
			binary.setLhs(
					transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) sum.getLhs(), features));
			binary.setRhs(
					transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) sum.getRhs(), features));
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
			features.setArithmeticExpressions(true);
			BinaryExpression binary = arithmeticFactory.createBinaryExpression();
			binary.setLhs(
					transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) prod.getLhs(), features));
			binary.setRhs(
					transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) prod.getRhs(), features));
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
			features.setArithmeticExpressions(true);
			BinaryExpression binary = arithmeticFactory.createBinaryExpression();
			binary.setLhs(
					transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) exp.getLhs(), features));
			binary.setRhs(
					transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) exp.getRhs(), features));
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
			features.setArithmeticExpressions(true);
			return switch (stoc.getDistribution()) {
			case NORMAL -> {
				BinaryExpression binary = arithmeticFactory.createBinaryExpression();
				binary.setLhs(transform(stoc.getMean(), features));
				binary.setRhs(transform(stoc.getSd(), features));
				binary.setType(EcorePackage.Literals.EDOUBLE);
				binary.setOperator(BinaryOperator.NORMAL_DISTRIBUTION);
				yield binary;
			}
			case UNIFORM -> {
				BinaryExpression binary = arithmeticFactory.createBinaryExpression();
				binary.setLhs(transform(stoc.getMean(), features));
				binary.setRhs(transform(stoc.getSd(), features));
				binary.setType(EcorePackage.Literals.EDOUBLE);
				binary.setOperator(BinaryOperator.UNIFORM_DISTRIBUTION);
				yield binary;
			}
			case EXPONENTIAL -> {
				UnaryExpression unary = arithmeticFactory.createUnaryExpression();
				unary.setOperand(transform(stoc.getMean(), features));
				unary.setType(EcorePackage.Literals.EDOUBLE);
				unary.setOperator(
						org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator.EXPONENTIAL_DISTRIBUTION);
				yield unary;
			}
			default ->
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + stoc.getDistribution());
			};
		} else if (expression instanceof MinMaxArithmeticExpression minMax) {
			features.setArithmeticExpressions(true);
			BinaryExpression binary = arithmeticFactory.createBinaryExpression();
			binary.setLhs(
					transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) minMax.getLhs(), features));
			binary.setRhs(
					transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) minMax.getRhs(), features));
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
			features.setArithmeticExpressions(true);
			UnaryExpression unary = arithmeticFactory.createUnaryExpression();
			unary.setOperand(transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) expression.getLhs(),
					features));
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
			features.setArithmeticExpressions(true);
			UnaryExpression unary = arithmeticFactory.createUnaryExpression();
			unary.setOperand(transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) expression.getLhs(),
					features));
			unary.setType(unary.getOperand().getType());
			unary.setOperator(org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator.BRACKET);
			return unary;
		} else if (expression instanceof ExpressionOperand op) {
			if (op.getOperand() instanceof NodeExpression ne) {
				IBeXNodeValue nodeValue = superFactory.createIBeXNodeValue();
				if (node2node.containsKey(ne.getNode())) {
					nodeValue.setNode(node2node.get(ne.getNode()));
					nodeValue.setType(nodeValue.getNode().getType());
				} else {
					addPendingNodeConsumer((SlimRuleNode) ne.getNode(), (node) -> {
						nodeValue.setNode(node);
						nodeValue.setType(node.getType());
					});
				}
				return nodeValue;
			} else if (op.getOperand() instanceof NodeAttributeExpression nae) {
				IBeXAttributeValue atrValue = superFactory.createIBeXAttributeValue();
				if (node2node.containsKey(nae.getNodeExpression().getNode())) {
					atrValue.setNode(node2node.get(nae.getNodeExpression().getNode()));
				} else {
					addPendingNodeConsumer((SlimRuleNode) nae.getNodeExpression().getNode(), (node) -> {
						atrValue.setNode(node);
					});
				}
				atrValue.setType(nae.getFeature().getEType());
				atrValue.setAttribute(nae.getFeature());
				return atrValue;
			} else if (op.getOperand() instanceof GTLIteratorAttributeExpression iae) {
				GTIteratorAttributeReference itrRef = factory.createGTIteratorAttributeReference();
				GTLEdgeIterator iterator = SlimGTModelUtil.getContainer(iae, GTLEdgeIterator.class);
				if (iterator2iterator.containsKey(iterator)) {
					itrRef.setIterator(iterator2iterator.get(iterator));
				} else {
					addPendingIteratorConsumer(iterator, (itr) -> {
						itrRef.setIterator(itr);
					});
				}
				itrRef.setAttribute(iae.getFeature());
				itrRef.setType(iae.getFeature().getEType());
				return (ArithmeticExpression) itrRef;
			} else if (op.getOperand() instanceof GTLParameterExpression param) {
				SlimRuleCondition condition = SlimGTModelUtil.getContainer(param, SlimRuleCondition.class);
				if (condition != null)
					features.setParameterExpressions(true);

				GTParameterValue paramValue = factory.createGTParameterValue();
				paramValue.setParameter(param2param.get(param.getParameter()));
				paramValue.setType(paramValue.getParameter().getType());
				return paramValue;
			} else if (op.getOperand() instanceof CountExpression count) {
				features.setCountExpressions(true);
				IBeXMatchCountValue gtCount = superFactory.createIBeXMatchCountValue();
				gtCount.setType(EcorePackage.Literals.EINT);
				SlimRule invoker = SlimGTModelUtil.getContainer(count, SlimRule.class);
				gtCount.setInvocation(transform(invoker, (SlimRule) count.getSupportPattern(), count.getMappings()));
				return gtCount;
			} else if (op.getOperand() instanceof ArithmeticLiteral lit) {
				if (lit instanceof DoubleLiteral d) {
					org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.DoubleLiteral gtD = arithmeticFactory
							.createDoubleLiteral();
					gtD.setValue(d.getValue());
					gtD.setType(EcorePackage.Literals.EDOUBLE);
					return gtD;
				} else if (lit instanceof IntegerLiteral i) {
					org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IntegerLiteral gtI = arithmeticFactory
							.createIntegerLiteral();
					gtI.setValue(i.getValue());
					gtI.setType(EcorePackage.Literals.EINT);
					return gtI;
				} else if (lit instanceof StringLiteral s) {
					IBeXStringValue gtS = superFactory.createIBeXStringValue();
					gtS.setValue(s.getValue());
					gtS.setType(EcorePackage.Literals.ESTRING);
					return (ArithmeticExpression) gtS;
				} else if (lit instanceof BooleanLiteral b) {
					IBeXBooleanValue gtB = superFactory.createIBeXBooleanValue();
					gtB.setValue(b.isValue());
					gtB.setType(EcorePackage.Literals.EBOOLEAN);
					return (ArithmeticExpression) gtB;
				} else {
					throw new UnsupportedOperationException("Unkown arithmetic literal type: " + lit);
				}
			} else if (op.getOperand() instanceof EnumExpression en) {
				IBeXEnumValue enumVal = superFactory.createIBeXEnumValue();
				enumVal.setLiteral(en.getLiteral());
				enumVal.setType(en.getLiteral().getEEnum());
				return (ArithmeticExpression) enumVal;
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

	protected IBeXPatternInvocation transform(final SlimRule invoker, final SlimRule invokee,
			final SlimRuleNodeMappings mappings) {
		IBeXPatternInvocation gtInvocation = superFactory.createIBeXPatternInvocation();

		pendingInvocationJobs.add(new Runnable() {

			@Override
			public void run() {
				GTPattern gtInvoker = pattern2pattern.get(invoker);
				GTPattern gtInvokee = pattern2pattern.get(invokee);
				gtInvocation.setInvokedBy(gtInvoker);
				gtInvocation.setInvocation(gtInvokee);
				gtInvoker.getDependencies().add(gtInvokee);

				for (SlimRuleNodeMapping mapping : mappings.getMappings()) {
					IBeXNode src = node2node.get(mapping.getSource());
					IBeXNode trg = node2node.get(mapping.getTarget());
					gtInvocation.getMapping().put(src, trg);
				}
			}
		});

		return gtInvocation;
	}

	protected GTWatchDog transform(GTLRuleWatchDog watchDog) {
		GTWatchDog gtWatch = factory.createGTWatchDog();
		gtWatch.setNode(node2node.get(watchDog.getNodeAttribute().getNodeExpression().getNode()));
		gtWatch.setAttribute(watchDog.getNodeAttribute().getFeature());
		return gtWatch;
	}

	protected GTForEachExpression transform(final SlimRuleNode node, final GTLEdgeIterator iterator) {
		if (iterator2iterator.containsKey(iterator)) {
			return iterator2iterator.get(iterator);
		}

		GTForEachExpression forEach = factory.createGTForEachExpression();
		iterator2iterator.put(iterator, forEach);
		forEach.setSource(node2node.get(node));
		forEach.setReference(iterator.getType());

		IBeXNode iteratorNode = superFactory.createIBeXNode();
		forEach.setIterator(iteratorNode);

		iteratorNode.setName(iterator.getName());
		if (iterator.isCasting()) {
			iteratorNode.setType(iterator.getSubType());
		} else {
			iteratorNode.setType((EClass) iterator.getType().getEType());
		}

		for (GTLEdgeIteratorAttributeAssignment iteratorAssignment : iterator.getIteratorAttributes()) {
			if (iteratorAssignment.getAttribute() instanceof NodeAttributeExpression nae) {
				IBeXAttributeAssignment gtAssign = superFactory.createIBeXAttributeAssignment();
				gtAssign.setNode(node2node.get(nae.getNodeExpression().getNode()));
				gtAssign.setAttribute(nae.getNodeExpression().getFeature());
				IBeXFeatureConfig features = superFactory.createIBeXFeatureConfig();
				ValueExpression value = transform(iteratorAssignment.getValue(), features);
				gtAssign.setValue(value);
				forEach.getAttributeAssignments().add(gtAssign);
			} else if (iteratorAssignment.getAttribute() instanceof GTLIteratorAttributeExpression iae) {
				GTIteratorAttributeAssignment gtAssign = factory.createGTIteratorAttributeAssignment();
				gtAssign.setIterator(forEach);
				gtAssign.setNode(forEach.getIterator());
				gtAssign.setAttribute(iae.getFeature());
				IBeXFeatureConfig features = superFactory.createIBeXFeatureConfig();
				ValueExpression value = transform(iteratorAssignment.getValue(), features);
				gtAssign.setValue(value);
				forEach.getAttributeAssignments().add(gtAssign);
			}
		}

		for (GTLEdgeIteratorReference iteratorReference : iterator.getReferences()) {
			GTIteratorEdge edge = factory.createGTIteratorEdge();
			edge.setIterator(forEach);
			edge.setSource(node2node.get(iteratorReference.getSource()));
			edge.setTarget(forEach.getIterator());
			edge.setType(iteratorReference.getType());

			if (iteratorReference.getOperator() == EdgeIteratorOperator.CREATE) {
				edge.setOperationType(IBeXOperationType.CREATION);
				forEach.getCreated().add(edge);
			} else {
				edge.setOperationType(IBeXOperationType.DELETION);
				forEach.getDeleted().add(edge);
			}

			setEdgeName(edge);
		}

		return forEach;
	}

	protected void addPendingNodeConsumer(final SlimRuleNode node, Consumer<IBeXNode> consumer) {
		List<Consumer<IBeXNode>> consumers = pendingNodeJobs.get(node);
		if (consumers == null) {
			consumers = Collections.synchronizedList(new LinkedList<>());
			pendingNodeJobs.put(node, consumers);
		}
		consumers.add(consumer);
	}

	protected void addPendingIteratorConsumer(final GTLEdgeIterator gtlItr, Consumer<GTForEachExpression> consumer) {
		List<Consumer<GTForEachExpression>> consumers = pendingIteratorJobs.get(gtlItr);
		if (consumers == null) {
			consumers = Collections.synchronizedList(new LinkedList<>());
			pendingIteratorJobs.put(gtlItr, consumers);
		}
		consumers.add(consumer);
	}

	protected void setEdgeName(IBeXEdge edge) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(edge.getSource().getName());
		sb.append("]--");
		sb.append(edge.getType().getName());
		sb.append("-->[");
		sb.append(edge.getTarget().getName());
		sb.append("]");
		edge.setName(sb.toString());
	}

}

record IBeXNodePair(IBeXNode n1, IBeXNode n2) {
	@Override
	public int hashCode() {
		return Objects.hash(n1, n2) + Objects.hash(n2, n1);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof IBeXNodePair otherPair) {
			if (hashCode() != other.hashCode())
				return false;

			if ((n1.equals(otherPair.n1) && n2.equals(otherPair.n2))
					|| (n1.equals(otherPair.n2) && n2.equals(otherPair.n1))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}
}
