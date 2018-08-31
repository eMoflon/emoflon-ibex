package org.emoflon.ibex.tgg.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.common.patterns.IBeXPatternFactory;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.util.String2EPrimitive;

import IBeXLanguage.IBeXAttributeConstraint;
import IBeXLanguage.IBeXAttributeValue;
import IBeXLanguage.IBeXConstant;
import IBeXLanguage.IBeXContext;
import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXEnumLiteral;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPatternInvocation;
import IBeXLanguage.IBeXPatternSet;
import IBeXLanguage.IBeXRelation;
import language.BindingType;
import language.DomainType;
import language.NAC;
import language.TGGAttributeConstraintOperators;
import language.TGGEnumExpression;
import language.TGGExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGLiteralExpression;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.RuntimePackage;

public class ContextPatternTransformation implements IContextPatternTransformation {
	private static final Logger logger = Logger.getLogger(ContextPatternTransformation.class);
	private static final boolean USE_INVOCATIONS_FOR_REFERENCES = true;
	private IbexOptions options;
	private List<IBeXContext> ibexContextPatterns = new ArrayList<>();

	/**
	 * Mapping between pattern names and the context patterns.
	 */
	private HashMap<String, IBeXContext> nameToPattern = new HashMap<>();

	public ContextPatternTransformation(IbexOptions options) {
		this.options = options;
	}

	@Override
	public IbexOptions getOptions() {
		return options;
	}

	@Override
	public IBeXPatternSet transform() {
		createModelGenPatterns();
		createConsistencyPatterns();

		// TODO: Handle other operationalisations

		return createSortedPatternSet();
	}

	private void createConsistencyPatterns() {
		for (TGGRule rule : options.getFlattenedConcreteTGGRules())
			createConsistencyPattern(rule);
	}

	private void createConsistencyPattern(TGGRule rule) {
		String patternName = getConsistencyPatternName(rule.getName());

		if (nameToPattern.containsKey(patternName)) {
			return;
		}

		// Root pattern
		IBeXContextPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(patternName);

		// Transform nodes
		List<TGGRuleNode> contextNodes = rule.getNodes();
		for (final TGGRuleNode node : contextNodes) {
			transformNode(ibexPattern, node);
		}

		// Transform attributes
		transformAttributeConditions(ibexPattern);
		for (final TGGRuleNode node : contextNodes) {
			transformInNodeAttributeConditions(ibexPattern, node);
		}

		// Ensure that all nodes must be disjoint even if they have the same type
		EditorToIBeXPatternHelper.addInjectivityConstraints(ibexPattern);

		// Transform edges
		List<TGGRuleEdge> edges = rule.getEdges();
		for (TGGRuleEdge edge : edges)
			transformEdge(edges, edge, ibexPattern);

		// Create protocol node and connections to nodes in pattern
		createAndConnectProtocolNode(rule, ibexPattern);

		addContextPattern(ibexPattern);
	}

	private void createAndConnectProtocolNode(TGGRule rule, IBeXContextPattern ibexPattern) {
		IBeXNode protocolNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
		protocolNode.setName(getProtocolNodeName(rule.getName()));
		protocolNode.setType(RuntimePackage.eINSTANCE.getTGGRuleApplication());
		ibexPattern.getSignatureNodes().add(protocolNode);

		IBeXAttributeConstraint ibexAttrConstraint = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeConstraint();
		ibexAttrConstraint.setNode(protocolNode);
		ibexAttrConstraint.setType(RuntimePackage.Literals.TGG_RULE_APPLICATION__NAME);

		ibexAttrConstraint.setRelation(IBeXRelation.EQUAL);
		String s = "\"" + rule.getName() + "\"";
		Object object = String2EPrimitive.convertLiteral(s, EcorePackage.Literals.ESTRING);
		IBeXConstant ibexConstant = IBeXLanguageFactory.eINSTANCE.createIBeXConstant();
		ibexConstant.setValue(object);
		ibexConstant.setStringValue(object.toString());
		ibexAttrConstraint.setValue(ibexConstant);

		ibexPattern.getAttributeConstraint().add(ibexAttrConstraint);

		connectProtocolNode(ibexPattern, protocolNode, rule);
	}

	private void connectProtocolNode(IBeXContextPattern ibexPattern, IBeXNode protocolNode, TGGRule rule) {
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.SRC, RuntimePackage.Literals.TGG_RULE_APPLICATION__CONTEXT_SRC);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.TRG, RuntimePackage.Literals.TGG_RULE_APPLICATION__CONTEXT_TRG);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.SRC, RuntimePackage.Literals.TGG_RULE_APPLICATION__CREATED_SRC);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.TRG, RuntimePackage.Literals.TGG_RULE_APPLICATION__CREATED_TRG);
	}

	private void connectProtocolNode(IBeXContextPattern ibexPattern, TGGRule rule, IBeXNode protocolNode,
			BindingType type, DomainType domain, EReference connectionType) {
		Collection<TGGRuleNode> nodes = TGGModelUtils.getNodesByOperatorAndDomain(rule, type, domain);
		for (TGGRuleNode node : nodes)
			transformEdge(connectionType, protocolNode, transformNode(ibexPattern, node), ibexPattern);
	}

	public static String getProtocolNodeName(String ruleName) {
		return ruleName + protocolNodeSuffix;
	}

	public static final String protocolNodeSuffix = "_eMoflon_ProtocolNode";

	private void createModelGenPatterns() {
		for (TGGRule rule : options.getFlattenedConcreteTGGRules())
			createModelGenPattern(rule);
	}

	private void createModelGenPattern(TGGRule rule) {
		String patternName = getGENPatternName(rule.getName());

		if (nameToPattern.containsKey(patternName)) {
			return;
		}

		// Root pattern
		IBeXContextPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(patternName);

		// Transform nodes.
		List<TGGRuleNode> contextNodes = TGGModelUtils.getNodesByOperator(rule, BindingType.CONTEXT);
		for (final TGGRuleNode node : contextNodes) {
			transformNode(ibexPattern, node);
		}

		// Transform attributes.
		transformAttributeConditions(ibexPattern);
		for (final TGGRuleNode node : contextNodes) {
			transformInNodeAttributeConditions(ibexPattern, node);
		}

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(ibexPattern);

		// Transform edges.
		List<TGGRuleEdge> edges = TGGModelUtils.getReferencesByOperator(rule, BindingType.CONTEXT);
		for (TGGRuleEdge edge : edges)
			transformEdge(edges, edge, ibexPattern);

		addContextPattern(ibexPattern);

		// NACs
		for (NAC nac : rule.getNacs())
			addContextPattern(transformNac(rule, nac, ibexPattern));
		
		// TODO:  If this is a complement rule, add a positive invocation to the consistency pattern of its kernel rule
	}

	private IBeXContext transformNac(TGGRule rule, NAC nac, IBeXContextPattern parent) {
		// Root pattern
		IBeXContextPattern nacPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();

		// Transform nodes
		List<TGGRuleNode> nodes = nac.getNodes();
		for (final TGGRuleNode node : nodes) {
			transformNode(nacPattern, node);
		}

		// Transform attributes
		transformAttributeConditions(nacPattern);
		for (final TGGRuleNode node : nodes) {
			transformInNodeAttributeConditions(nacPattern, node);
		}

		// Ensure that all nodes must be disjoint even if they have the same type
		EditorToIBeXPatternHelper.addInjectivityConstraints(nacPattern);

		// Transform edges
		List<TGGRuleEdge> edges = nac.getEdges();
		for (TGGRuleEdge edge : edges)
			transformEdge(edges, edge, nacPattern);

		if (TGGModelUtils.ruleIsAxiom(rule)) {
			nacPattern.setName(getAxiomNACPatternName(rule.getName(), nac.getName()));
		} else {
			nacPattern.setName(getNACPatternName(nac.getName()));

			// Invoke NAC from parent: nodes with/without pre-image are signature/local
			IBeXPatternInvocation invocation = IBeXLanguageFactory.eINSTANCE.createIBeXPatternInvocation();
			invocation.setPositive(false);
			ArrayList<IBeXNode> localNodes = new ArrayList<>();

			for (IBeXNode node : nacPattern.getSignatureNodes()) {
				Optional<IBeXNode> src = IBeXPatternUtils.findIBeXNodeWithName(parent, node.getName());

				if (src.isPresent())
					invocation.getMapping().put(src.get(), node);
				else
					localNodes.add(node);
			}

			nacPattern.getLocalNodes().addAll(localNodes);

			invocation.setInvokedPattern(nacPattern);
			parent.getInvocations().add(invocation);
		}

		return nacPattern;
	}

	private String getAxiomNACPatternName(String ruleName, String nacName) {
		return ruleName + PatternSuffixes.SEP + nacName + PatternSuffixes.GEN_AXIOM_NAC;
	}

	private void transformInNodeAttributeConditions(IBeXContextPattern ibexPattern, TGGRuleNode node) {
		Objects.requireNonNull(ibexPattern, "ibexContextPattern must not be null!");

		Optional<IBeXNode> ibexNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, node.getName());
		if (!ibexNode.isPresent()) {
			logger.error("Node " + node.getName() + " is missing!");
			return;
		}

		for (TGGInplaceAttributeExpression attrExp : node.getAttrExpr()) {
			if (node.getBindingType().equals(BindingType.CONTEXT)) {
				IBeXAttributeConstraint ibexAttrConstraint = IBeXLanguageFactory.eINSTANCE
						.createIBeXAttributeConstraint();
				ibexAttrConstraint.setNode(ibexNode.get());
				ibexAttrConstraint.setType(attrExp.getAttribute());

				IBeXRelation ibexRelation = convertRelation(attrExp.getOperator());
				ibexAttrConstraint.setRelation(ibexRelation);
				convertValue(ibexPattern, attrExp.getValueExpr(), attrExp.getAttribute())
						.ifPresent(value -> ibexAttrConstraint.setValue(value));
				ibexPattern.getAttributeConstraint().add(ibexAttrConstraint);
			}
		}
	}

	private Optional<IBeXAttributeValue> convertValue(IBeXContextPattern ibexPattern, TGGExpression valueExpr,
			EAttribute eAttribute) {
		if (valueExpr instanceof TGGEnumExpression) {
			return Optional.of(convertAttributeValue((TGGEnumExpression) valueExpr));
		} else if (valueExpr instanceof TGGLiteralExpression) {
			return Optional.of(convertAttributeValue((TGGLiteralExpression) valueExpr, eAttribute.getEAttributeType()));
		} else {
			logger.error("Invalid attribute value: " + valueExpr);
			return Optional.empty();
		}
	}

	private IBeXEnumLiteral convertAttributeValue(TGGEnumExpression valueExpr) {
		IBeXEnumLiteral ibexEnumLiteral = IBeXLanguageFactory.eINSTANCE.createIBeXEnumLiteral();
		ibexEnumLiteral.setLiteral(valueExpr.getLiteral());
		return ibexEnumLiteral;
	}

	private static IBeXConstant convertAttributeValue(final TGGLiteralExpression valueExp, final EDataType type) {
		String s = valueExp.getValue();
		Object object = String2EPrimitive.convertLiteral(s, type);
		IBeXConstant ibexConstant = IBeXLanguageFactory.eINSTANCE.createIBeXConstant();
		ibexConstant.setValue(object);
		ibexConstant.setStringValue(object.toString());
		return ibexConstant;
	}

	private IBeXRelation convertRelation(TGGAttributeConstraintOperators operator) {
		switch (operator) {
		case GREATER:
			return IBeXRelation.GREATER;
		case GR_EQUAL:
			return IBeXRelation.GREATER_OR_EQUAL;
		case EQUAL:
			return IBeXRelation.EQUAL;
		case UNEQUAL:
			return IBeXRelation.UNEQUAL;
		case LESSER:
			return IBeXRelation.SMALLER;
		case LE_EQUAL:
			return IBeXRelation.SMALLER_OR_EQUAL;
		default:
			throw new IllegalArgumentException("Cannot convert operator: " + operator);
		}
	}

	private void transformAttributeConditions(IBeXContextPattern ibexPattern) {
		// TODO Auto-generated method stub

	}

	/**
	 * Add the given pattern to the list.
	 * 
	 * @param ibexPattern the context pattern to add, must not be <code>null</code>
	 */
	private void addContextPattern(final IBeXContext ibexPattern) {
		Objects.requireNonNull(ibexPattern, "The pattern must not be null!");

		ibexContextPatterns.add(ibexPattern);
		nameToPattern.put(ibexPattern.getName(), ibexPattern);
	}

	private void transformEdgeToPatternInvocation(EReference type, IBeXNode ibexSourceNode, IBeXNode ibexTargetNode,
			IBeXContextPattern ibexPattern) {
		if (ibexSourceNode == ibexTargetNode) {
			transformEdge(type, ibexSourceNode, ibexTargetNode, ibexPattern);
			return;
		}

		Optional<IBeXContextPattern> edgePatternOptional = IBeXPatternUtils.createEdgePattern(type, nameToPattern,
				(s) -> logger.error(s));
		if (!edgePatternOptional.isPresent()) {
			logger.error("Cannot create edge pattern for type " + type.getName());
			return;
		}
		IBeXContextPattern edgePattern = edgePatternOptional.get();
		addContextPattern(edgePattern);

		Optional<IBeXNode> ibexSignatureSourceNode = //
				IBeXPatternUtils.findIBeXNodeWithName(edgePattern.getSignatureNodes(), "src");
		Optional<IBeXNode> ibexSignatureTargetNode = //
				IBeXPatternUtils.findIBeXNodeWithName(edgePattern.getSignatureNodes(), "trg");
		if (!ibexSignatureSourceNode.isPresent() || !ibexSignatureTargetNode.isPresent()) {
			logger.error("Invalid signature nodes for edge pattern!");
			return;
		}

		IBeXPatternInvocation invocation = IBeXLanguageFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(true);
		invocation.getMapping().put(ibexSourceNode, ibexSignatureSourceNode.get());
		invocation.getMapping().put(ibexTargetNode, ibexSignatureTargetNode.get());
		invocation.setInvokedPattern(edgePattern);
		ibexPattern.getInvocations().add(invocation);
	}

	private IBeXNode transformNode(IBeXContextPattern ibexPattern, TGGRuleNode node) {
		Objects.requireNonNull(node, "Node must not be null!");

		return IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, node.getName())
				.orElseGet(() -> handleNode(ibexPattern, node));
	}

	private IBeXNode handleNode(IBeXContextPattern ibexPattern, TGGRuleNode node) {
		IBeXNode ibexNode = IBeXPatternFactory.createNode(node.getName(), node.getType());

		ibexPattern.getSignatureNodes().add(ibexNode);

		if (node instanceof TGGRuleCorr) {
			TGGRuleCorr corr = (TGGRuleCorr) node;
			transformCorr(ibexPattern, corr);
		}

		return ibexNode;
	}

	private void transformCorr(IBeXContextPattern ibexPattern, TGGRuleCorr corr) {
		EReference srcType = ((EReference) corr.getType().getEStructuralFeature("source"));
		TGGRuleNode srcOfCorr = corr.getSource();
		transformEdge(srcType, corr, srcOfCorr, ibexPattern);

		EReference trgType = ((EReference) corr.getType().getEStructuralFeature("target"));
		TGGRuleNode trgOfCorr = corr.getTarget();
		transformEdge(trgType, corr, trgOfCorr, ibexPattern);
	}

	private void transformEdge(Collection<TGGRuleEdge> allEdges, TGGRuleEdge edge, IBeXContextPattern ibexPattern) {
		Objects.requireNonNull(edge, "The edge must not be null!");

		// Skip if "greater" eOpposite exists
		if (allEdges.stream().anyMatch(e -> isGreaterEOpposite(e, edge)))
			return;

		transformEdge(edge.getType(), edge.getSrcNode(), edge.getTrgNode(), ibexPattern);
	}

	private boolean isGreaterEOpposite(TGGRuleEdge e1, TGGRuleEdge e2) {
		if (e2.getType().equals(e1.getType().getEOpposite())) {
			if (e1.getSrcNode().getName().equals(e2.getTrgNode().getName())
					&& e1.getTrgNode().getName().equals(e2.getSrcNode().getName())) {
				return e1.getType().getName().compareTo(e2.getType().getName()) >= 0;
			}
		}

		return false;
	}

	private void transformEdge(EReference type, TGGRuleNode srcNode, TGGRuleNode trgNode,
			IBeXContextPattern ibexPattern) {
		IBeXNode sourceNode = transformNode(ibexPattern, srcNode);
		IBeXNode targetNode = transformNode(ibexPattern, trgNode);

		transformEdge(type, sourceNode, targetNode, ibexPattern);
	}

	private void transformEdge(EReference type, IBeXNode srcNode, IBeXNode trgNode, IBeXContextPattern ibexPattern) {
		if (USE_INVOCATIONS_FOR_REFERENCES) {
			transformEdgeToPatternInvocation(type, srcNode, trgNode, ibexPattern);
			return;
		}

		IBeXEdge ibexEdge = IBeXLanguageFactory.eINSTANCE.createIBeXEdge();
		ibexEdge.setType(type);
		ibexEdge.setSourceNode(srcNode);
		ibexEdge.setTargetNode(trgNode);
		ibexPattern.getLocalEdges().add(ibexEdge);
	}

	/**
	 * Creates a pattern set with pattern lists sorted alphabetically.
	 * 
	 * @return the IBeXPatternSet
	 */
	private IBeXPatternSet createSortedPatternSet() {
		ibexContextPatterns.sort(IBeXPatternUtils.sortByName);

		IBeXPatternSet ibexPatternSet = IBeXLanguageFactory.eINSTANCE.createIBeXPatternSet();
		ibexPatternSet.getContextPatterns().addAll(ibexContextPatterns);

		return ibexPatternSet;
	}

	private String getGENPatternName(String ruleName) {
		return ruleName + PatternSuffixes.GEN;
	}

	private String getNACPatternName(String nacName) {
		return nacName + PatternSuffixes.NAC;
	}

	private String getConsistencyPatternName(String ruleName) {
		return ruleName + PatternSuffixes.CONSISTENCY;
	}
}
