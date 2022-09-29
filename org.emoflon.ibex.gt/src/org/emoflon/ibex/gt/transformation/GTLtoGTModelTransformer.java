package org.emoflon.ibex.gt.transformation;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModelMetadata;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType;
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
import org.emoflon.ibex.common.slimgt.slimGT.BooleanNegation;
import org.emoflon.ibex.common.slimgt.slimGT.BracketExpression;
import org.emoflon.ibex.common.slimgt.slimGT.Constant;
import org.emoflon.ibex.common.slimgt.slimGT.CountExpression;
import org.emoflon.ibex.common.slimgt.slimGT.EnumExpression;
import org.emoflon.ibex.common.slimgt.slimGT.ExpArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.MinMaxArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.NodeAttributeExpression;
import org.emoflon.ibex.common.slimgt.slimGT.NodeExpression;
import org.emoflon.ibex.common.slimgt.slimGT.ProductArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleCondition;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleEdge;
import org.emoflon.ibex.common.slimgt.slimGT.StochasticArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.SumArithmeticExpression;
import org.emoflon.ibex.common.slimgt.slimGT.UnaryArithmeticExpression;
import org.emoflon.ibex.common.slimgt.util.SlimGTEMFUtil;
import org.emoflon.ibex.common.transformation.SlimGtToIBeXCoreTransformer;
import org.emoflon.ibex.gt.gtl.gTL.EditorFile;
import org.emoflon.ibex.gt.gtl.gTL.ExpressionOperand;
import org.emoflon.ibex.gt.gtl.gTL.GTLIteratorAttributeExpression;
import org.emoflon.ibex.gt.gtl.gTL.GTLParameterExpression;
import org.emoflon.ibex.gt.gtl.gTL.GTLRuleNodeDeletion;
import org.emoflon.ibex.gt.gtl.gTL.GTLRuleType;
import org.emoflon.ibex.gt.gtl.gTL.Import;
import org.emoflon.ibex.gt.gtl.gTL.SlimRule;
import org.emoflon.ibex.gt.gtl.gTL.SlimRuleNode;
import org.emoflon.ibex.gt.gtl.gTL.SlimRuleNodeContext;
import org.emoflon.ibex.gt.gtl.gTL.SlimRuleNodeCreation;
import org.emoflon.ibex.gt.gtl.util.GTLResourceManager;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelFactory;

public class GTLtoGTModelTransformer extends SlimGtToIBeXCoreTransformer<EditorFile, GTModel, IBeXGTModelFactory> {

	protected final IProject project;
	protected final GTLResourceManager gtlManager = new GTLResourceManager(xtextResources);

	protected final Map<SlimRule, GTRule> rule2rule = Collections.synchronizedMap(new HashMap<>());
	protected final Map<SlimRule, GTPattern> pattern2pattern = Collections.synchronizedMap(new HashMap<>());
	protected final Map<SlimRuleNode, IBeXNode> node2node = Collections.synchronizedMap(new HashMap<>());
	protected final Map<SlimRuleEdge, IBeXEdge> edge2edge = Collections.synchronizedMap(new HashMap<>());
	protected final Map<SlimRuleNode, List<Consumer<IBeXNode>>> pendingNodeJobs = Collections
			.synchronizedMap(new HashMap<>());

	protected boolean countFlag = false;
	protected boolean arithmeticFlag = false;
	protected boolean booleanFlag = false;

	public GTLtoGTModelTransformer(final EditorFile editorFile, final IProject project) {
		super(editorFile);
		this.project = project;
	}

	@Override
	public GTModel transform() {
		model.setRuleSet(factory.createGTRuleSet());

		for (SlimRule rule : editorFile.getRules()) {
			// Ignore abstract patterns / rules
			if (rule.isAbstract())
				continue;

			// Transform to gt pattern
			if (rule.getType() == GTLRuleType.PATTERN) {
				transformPrecondition(rule);
			} else { // Transform to gt rule
				transformRule(rule);
			}
		}

		return model;
	}

	protected void postProcessing() {
		rule2rule.values().forEach(r -> model.getRuleSet().getRules().add(r));
		pattern2pattern.values().forEach(p -> model.getPatternSet().getPatterns().add(p));
		node2node.values().forEach(n -> model.getNodeSet().getNodes().add(n));
		edge2edge.values().forEach(e -> model.getEdgeSet().getEdges().add(e));
		node2node.forEach((srNode, gtNode) -> pendingNodeJobs.get(srNode).forEach(consumer -> consumer.accept(gtNode)));
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

		GTPattern precondition = transformPrecondition(rule);
		gtRule.setPrecondition(precondition);

		GTPattern postcondition = transformPostcondition(rule);
		gtRule.setPostcondition(postcondition);

		return gtRule;
	}

	protected GTPattern transformPrecondition(SlimRule pattern) {
		if (pattern2pattern.containsKey(pattern)) {
			return pattern2pattern.get(pattern);
		}

		GTPattern gtPattern = factory.createGTPattern();
		gtPattern.setName(pattern.getName());
		pattern2pattern.put(pattern, gtPattern);

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

		for (GTLRuleNodeDeletion deletion : pattern.getDeletedNodes()) {
			IBeXNode gtNode = transformPrecondition(deletion.getDeletion());
			gtPattern.getSignatureNodes().add(gtNode);
			gtNode.setOperationType(IBeXOperationType.DELETION);
		}

		for (SlimRuleCondition condition : pattern.getConditions()) {
			BooleanExpression expression = transform(condition.getExpression());
			gtPattern.getConditions().add(expression);
		}

		// TODO: Invocations

		// TODO: Watch dogs

		if (gtPattern.getSignatureNodes().isEmpty() && gtPattern.getLocalNodes().isEmpty()
				&& gtPattern.getConditions().isEmpty())
			gtPattern.setEmpty(true);

		return gtPattern;
	}

	protected GTPattern transformPostcondition(SlimRule pattern) {
		GTPattern gtPattern = factory.createGTPattern();
		gtPattern.setName(pattern.getName());

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

		for (SlimRuleNodeCreation creation : pattern.getCreatedNodes().stream().map(n -> (SlimRuleNodeCreation) n)
				.collect(Collectors.toList())) {
			IBeXNode gtNode = transformPostcondition((SlimRuleNode) creation.getCreation());
			gtPattern.getSignatureNodes().add(gtNode);
			gtNode.setOperationType(IBeXOperationType.CREATION);
		}

		for (SlimRuleCondition condition : pattern.getConditions()) {
			BooleanExpression expression = transform(condition.getExpression());
			gtPattern.getConditions().add(expression);
		}

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
			setEdgeName(gtEdge);
		} else {
			addPendingNodeConsumer((SlimRuleNode) edge.getTarget(), (node) -> {
				gtEdge.setTarget(node);
				setEdgeName(gtEdge);
			});
		}
		gtEdge.setType(edge.getType());

		return gtEdge;
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

	protected BooleanExpression transform(org.emoflon.ibex.common.slimgt.slimGT.BooleanExpression expression) {
		if (expression instanceof BooleanImplication impl) {
			setBooleanFeatureFlag();
			BooleanBinaryExpression binary = arithmeticFactory.createBooleanBinaryExpression();
			binary.setLhs(transform(impl.getLeft()));
			binary.setRhs(transform(impl.getRight()));
			binary.setOperator(BooleanBinaryOperator.IMPLICATION);
			return binary;
		} else if (expression instanceof BooleanDisjunction disj) {
			setBooleanFeatureFlag();
			BooleanBinaryExpression binary = arithmeticFactory.createBooleanBinaryExpression();
			binary.setLhs(transform(disj.getLeft()));
			binary.setRhs(transform(disj.getRight()));
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
			setBooleanFeatureFlag();
			BooleanBinaryExpression binary = arithmeticFactory.createBooleanBinaryExpression();
			binary.setLhs(transform(conj.getLeft()));
			binary.setRhs(transform(conj.getRight()));
			binary.setOperator(switch (conj.getOperator()) {
			case AND -> {
				yield BooleanBinaryOperator.AND;
			}
			default -> throw new UnsupportedOperationException("Unkown boolean operator: " + conj.getOperator());
			});
			return binary;
		} else if (expression instanceof BooleanNegation neg) {
			setBooleanFeatureFlag();
			BooleanUnaryExpression unary = arithmeticFactory.createBooleanUnaryExpression();
			unary.setOperand(transform(neg.getOperand()));
			unary.setOperator(BooleanUnaryOperator.NEGATION);
			return unary;
		} else if (expression instanceof BooleanBracket brack) {
			setBooleanFeatureFlag();
			BooleanUnaryExpression unary = arithmeticFactory.createBooleanUnaryExpression();
			unary.setOperand(transform(brack.getOperand()));
			unary.setOperator(BooleanUnaryOperator.BRACKET);
			return unary;
		} else if (expression instanceof org.emoflon.ibex.common.slimgt.slimGT.ValueExpression val) {
			setBooleanFeatureFlag();
			ValueExpression gtValue = transform(val);
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
			gtRelation.setLhs(transform(rel.getLhs()));
			gtRelation.setRhs(transform(rel.getRhs()));
			gtRelation.setOperator(switch (rel.getRelation()) {
			case EQUAL -> {
				if (gtRelation.getLhs().getType() instanceof EDataType
						|| gtRelation.getLhs().getType() instanceof EEnumLiteral) {
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
				if (gtRelation.getLhs().getType() instanceof EDataType
						|| gtRelation.getLhs().getType() instanceof EEnumLiteral) {
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

	protected ValueExpression transform(org.emoflon.ibex.common.slimgt.slimGT.ValueExpression value) {
		return transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) value);
	}

	protected ArithmeticExpression transform(org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression expression) {
		if (expression instanceof SumArithmeticExpression sum) {
			BinaryExpression binary = arithmeticFactory.createBinaryExpression();
			binary.setLhs(transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) sum.getLhs()));
			binary.setRhs(transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) sum.getRhs()));
			binary.setType(mergeDataTypes(binary.getLhs(), binary.getRhs()));
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
			BinaryExpression binary = arithmeticFactory.createBinaryExpression();
			binary.setLhs(transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) prod.getLhs()));
			binary.setRhs(transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) prod.getRhs()));
			binary.setType(mergeDataTypes(binary.getLhs(), binary.getRhs()));
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
			BinaryExpression binary = arithmeticFactory.createBinaryExpression();
			binary.setLhs(transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) exp.getLhs()));
			binary.setRhs(transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) exp.getRhs()));
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
			return switch (stoc.getDistribution()) {
			case NORMAL -> {
				BinaryExpression binary = arithmeticFactory.createBinaryExpression();
				binary.setLhs(transform(stoc.getMean()));
				binary.setRhs(transform(stoc.getSd()));
				binary.setType(EcorePackage.Literals.EDOUBLE);
				binary.setOperator(BinaryOperator.NORMAL_DISTRIBUTION);
				yield binary;
			}
			case UNIFORM -> {
				BinaryExpression binary = arithmeticFactory.createBinaryExpression();
				binary.setLhs(transform(stoc.getMean()));
				binary.setRhs(transform(stoc.getSd()));
				binary.setType(EcorePackage.Literals.EDOUBLE);
				binary.setOperator(BinaryOperator.UNIFORM_DISTRIBUTION);
				yield binary;
			}
			case EXPONENTIAL -> {
				UnaryExpression unary = arithmeticFactory.createUnaryExpression();
				unary.setOperand(transform(stoc.getMean()));
				unary.setType(EcorePackage.Literals.EDOUBLE);
				unary.setOperator(
						org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator.EXPONENTIAL_DISTRIBUTION);
				yield unary;
			}
			default ->
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + stoc.getDistribution());
			};
		} else if (expression instanceof MinMaxArithmeticExpression minMax) {
			BinaryExpression binary = arithmeticFactory.createBinaryExpression();
			binary.setLhs(transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) minMax.getLhs()));
			binary.setRhs(transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) minMax.getRhs()));
			binary.setType(mergeDataTypes(binary.getLhs(), binary.getRhs()));
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
			UnaryExpression unary = arithmeticFactory.createUnaryExpression();
			unary.setOperand(
					transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) expression.getLhs()));
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
			UnaryExpression unary = arithmeticFactory.createUnaryExpression();
			unary.setOperand(
					transform((org.emoflon.ibex.common.slimgt.slimGT.ArithmeticExpression) expression.getLhs()));
			unary.setType(unary.getOperand().getType());
			unary.setOperator(org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator.BRACKET);
			return unary;
		} else if (expression instanceof ExpressionOperand op) {
			if (op.getOperand() instanceof NodeExpression ne) {
				// TODO:
				return null;
			} else if (op.getOperand() instanceof NodeAttributeExpression nae) {
				// TODO:
				return null;
			} else if (op.getOperand() instanceof GTLIteratorAttributeExpression nae) {
				// TODO:
				return null;
			} else if (op.getOperand() instanceof GTLParameterExpression param) {
				// TODO:
				return null;
			} else if (op.getOperand() instanceof CountExpression count) {
				// TODO:
				return null;
			} else if (op.getOperand() instanceof ArithmeticLiteral lit) {
				// TODO:
				return null;
			} else if (op.getOperand() instanceof EnumExpression en) {
				// TODO:
				return null;
			} else if (op.getOperand() instanceof Constant con) {
				// TODO:
				return null;
			} else {
				throw new UnsupportedOperationException("Unkown arithmetic operand type: " + op.getOperand());
			}
		} else {
			throw new UnsupportedOperationException("Unkown arithmetic operation type: " + expression);
		}
	}

	protected EClassifier mergeDataTypes(final ValueExpression lhs, final ValueExpression rhs) {
		return null;
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

	protected void addPendingNodeConsumer(final SlimRuleNode node, Consumer<IBeXNode> consumer) {
		List<Consumer<IBeXNode>> consumers = pendingNodeJobs.get(node);
		if (consumer == null) {
			consumers = Collections.synchronizedList(new LinkedList<>());
			pendingNodeJobs.put(node, consumers);
		}
		consumers.add(consumer);
	}

	protected synchronized void setCountFeatureFlag() {
		countFlag = true;
	}

	protected synchronized void setArithmeticFeatureFlag() {
		arithmeticFlag = true;
	}

	protected synchronized void setBooleanFeatureFlag() {
		booleanFlag = true;
	}

}
