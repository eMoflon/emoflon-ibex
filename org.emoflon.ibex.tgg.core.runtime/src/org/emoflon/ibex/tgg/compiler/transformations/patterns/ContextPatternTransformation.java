package org.emoflon.ibex.tgg.compiler.transformations.patterns;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getBWDBlackPatternName;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getConsistencyPatternName;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getFWDBlackPatternName;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getNACPatternName;
import static org.emoflon.ibex.tgg.compiler.patterns.IBeXPatternOptimiser.optimizeIBeXPattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.patterns.IBeXPatternFactory;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
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
import language.TGGNamedElement;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class ContextPatternTransformation {
	public static final int MAX_NUM_OF_EDGES_IN_PATTERN = 3;

	protected static final Logger logger = Logger.getLogger(ContextPatternTransformation.class);
	private final boolean USE_INVOCATIONS_FOR_REFERENCES;
	private IbexOptions options;
	private List<IBeXContext> ibexContextPatterns = new ArrayList<>();

	/**
	 * Mapping between pattern names and the context patterns.
	 */
	private HashMap<String, IBeXContextPattern> nameToPattern = new HashMap<>();
	private Map<IBeXContextPattern, TGGNamedElement> patternToRuleMap = new HashMap<>();
	private OperationalStrategy strategy;

	public ContextPatternTransformation(IbexOptions options, OperationalStrategy strategy) {
		this.options = options;
		this.strategy = strategy;
		this.USE_INVOCATIONS_FOR_REFERENCES = options.getUseEdgePatterns();
	}

	public IBeXPatternSet transform() {
		for (TGGRule rule : options.getFlattenedConcreteTGGRules()) {
			createPatternIfRelevant(rule, this::createModelGenPattern, PatternSuffixes.GEN);
			createPatternIfRelevant(rule, this::createFWDPattern, PatternSuffixes.FWD);
			createPatternIfRelevant(rule, this::createBWDPattern, PatternSuffixes.BWD);
			createPatternIfRelevant(rule, this::createConsistencyPattern, PatternSuffixes.CONSISTENCY);
			createPatternIfRelevant(rule, this::createCCPattern, PatternSuffixes.CC);
			createPatternIfRelevant(rule, this::createCOPattern, PatternSuffixes.CO);
			createPatternIfRelevant(rule, this::createFWD_OPTPattern, PatternSuffixes.FWD_OPT);
			createPatternIfRelevant(rule, this::createBWD_OPTPattern, PatternSuffixes.BWD_OPT);
			
			optimizeSyncPatterns(rule);
		}

		return createSortedPatternSet();
	}

	private void createPatternIfRelevant(TGGRule rule, Consumer<TGGRule> transformer, String suffix) {
		if(strategy.isPatternRelevantForCompiler(suffix)) {
			transformer.accept(rule);
		}
	}

	public boolean isTransformed(String patternName) {
		return nameToPattern.containsKey(patternName);
	}

	public IBeXContextPattern getPattern(String patternName) {
		return nameToPattern.get(patternName);
	}

	public Map<IBeXContextPattern, TGGNamedElement> getPatternToRuleMap() {
		return patternToRuleMap;
	}

	protected IBeXContextPattern createModelGenPattern(TGGRule rule) {
		GENPatternTransformation genPatternTransformer = new GENPatternTransformation(this, options);
		return genPatternTransformer.transform(rule);
	}

	protected IBeXContextPattern createFWDPattern(TGGRule rule) {
		FWDPatternTransformation fwdPatternTransformer = new FWDPatternTransformation(this, options);
		return fwdPatternTransformer.transform(rule);
	}
	
	protected IBeXContextPattern createBWDPattern(TGGRule rule) {
		BWDPatternTransformation  bwdPatternTransformer = new BWDPatternTransformation(this, options);
		return bwdPatternTransformer.transform(rule);
	}
	
	protected IBeXContextPattern createConsistencyPattern(TGGRule rule) {
		ConsistencyPatternTransformation consistencyPatternTransformer = new ConsistencyPatternTransformation(this, options);
		return consistencyPatternTransformer.transform(rule);
	}

	protected IBeXContextPattern createCCPattern(TGGRule rule) {
		CCPatternTransformation ccPatternTransformer = new CCPatternTransformation(this, options);
		return ccPatternTransformer.transform(rule);
	}

	protected IBeXContextPattern createCOPattern(TGGRule rule) {
		COPatternTransformation coPatternTransformer = new COPatternTransformation(this, options);
		return coPatternTransformer.transform(rule);
	}

	protected IBeXContextPattern createFWD_OPTPattern(TGGRule rule) {
		FWD_OPTPatternTransformation fwd_optPatternTransformer = new FWD_OPTPatternTransformation(this, options);
		return fwd_optPatternTransformer.transform(rule);
	}

	protected IBeXContextPattern createBWD_OPTPattern(TGGRule rule) {
		BWD_OPTPatternTransformation bwd_optPatternTransformer = new BWD_OPTPatternTransformation(this, options);
		return bwd_optPatternTransformer.transform(rule);
	}
	
	protected IBeXContextPattern createProtocolPattern(TGGRule rule) {
		OperationalPatternTransformation transformer = new ProtocolPatternTransformation(this, options);
		return transformer.transform(rule);
	}
	
	protected IBeXContextPattern createProtocolCorePattern(TGGRule rule) {
		OperationalPatternTransformation transformer = new ProtocolCorePatternTransformation(this, options);
		return transformer.transform(rule);
	}

	public IBeXContextPattern transformNac(TGGRule rule, NAC nac, IBeXContextPattern parent) {
		// Root pattern
		IBeXContextPattern nacPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();

		// Transform nodes
		List<TGGRuleNode> nodes = nac.getNodes();
		for (final TGGRuleNode node : nodes) {
			transformNode(nacPattern, node);
		}

		// Transform attributes
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
			nacPattern.setName(TGGPatternUtil.getAxiomNACPatternName(rule.getName(), nac.getName()));
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
	
	public void optimizeSyncPatterns(TGGRule rule) {
		if(!options.optimizeSyncPattern())
			return;
		
		IBeXContextPattern fwdPattern = getPattern(getFWDBlackPatternName(rule.getName()));
		IBeXContextPattern bwdPattern = getPattern(getBWDBlackPatternName(rule.getName()));
		IBeXContextPattern consistencyPattern = getPattern(getConsistencyPatternName(rule.getName()));
		
		boolean fwdPatternPresent = fwdPattern != null;
		boolean bwdPatternPresent = bwdPattern != null;
		boolean consistencyPatternPresent = consistencyPattern != null;

		if(fwdPatternPresent && consistencyPatternPresent)  {
			createInvocation(consistencyPattern, fwdPattern);
			
			optimizeIBeXPattern(consistencyPattern, bwdPattern);
		}
		
		if(bwdPatternPresent && consistencyPatternPresent) {
			createInvocation(consistencyPattern, bwdPattern);	
			
			optimizeIBeXPattern(consistencyPattern, bwdPattern);
		}

		if(fwdPatternPresent && bwdPatternPresent) {
			IBeXContextPattern genPattern = createModelGenPattern(rule);
			if(genPattern != null) {
				createInvocation(fwdPattern, genPattern);
				createInvocation(bwdPattern, genPattern);
				
				optimizeIBeXPattern(fwdPattern, genPattern);
				optimizeIBeXPattern(bwdPattern, genPattern);
			}
		}
		
		if(consistencyPatternPresent) {
			IBeXContextPattern protocolPattern = createProtocolPattern(rule);
			IBeXContextPattern protocolCorePattern = createProtocolCorePattern(rule);
			
			createInvocation(consistencyPattern, protocolPattern);
			createInvocation(protocolPattern, protocolCorePattern);

			optimizeIBeXPattern(consistencyPattern, protocolPattern);
			optimizeIBeXPattern(protocolPattern, protocolCorePattern);
		}
	}
	
	protected void createInvocation(IBeXContextPattern invoker, IBeXContextPattern invokee) {
		IBeXPatternInvocation invocation = IBeXLanguageFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(true);

		// Creating mapping for invocation: missing signature nodes of the invoked
		// pattern are added as local nodes to the invoking pattern
		for (IBeXNode node : invokee.getSignatureNodes()) {
			Optional<IBeXNode> src = IBeXPatternUtils.findIBeXNodeWithName(invoker, node.getName());

			if (src.isPresent())
				invocation.getMapping().put(src.get(), node);
			else {
				IBeXNode newLocalNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
				newLocalNode.setName(node.getName());
				newLocalNode.setType(node.getType());
				invoker.getLocalNodes().add(newLocalNode);

				invocation.getMapping().put(newLocalNode, node);
			}
		}

		invocation.setInvokedPattern(invokee);
		invoker.getInvocations().add(invocation);
	}

	public void transformEdge(EReference type, IBeXNode srcNode, IBeXNode trgNode, IBeXContextPattern ibexPattern,
			boolean tooManyEdges) {
		if (USE_INVOCATIONS_FOR_REFERENCES && tooManyEdges) {
			transformEdgeToPatternInvocation(type, srcNode, trgNode, ibexPattern);
			return;
		}

		IBeXEdge ibexEdge = IBeXLanguageFactory.eINSTANCE.createIBeXEdge();
		ibexEdge.setType(type);
		ibexEdge.setSourceNode(srcNode);
		ibexEdge.setTargetNode(trgNode);
		ibexPattern.getLocalEdges().add(ibexEdge);
	}

	public void transformEdge(Collection<TGGRuleEdge> allEdges, TGGRuleEdge edge, IBeXContextPattern ibexPattern) {
		Objects.requireNonNull(edge, "The edge must not be null!");

		// Skip if "greater" eOpposite exists
		if (allEdges.stream().anyMatch(e -> isGreaterEOpposite(e, edge)))
			return;

		transformEdge(edge.getType(), edge.getSrcNode(), edge.getTrgNode(), ibexPattern,
				allEdges.size() > MAX_NUM_OF_EDGES_IN_PATTERN);
	}

	public void transformInNodeAttributeConditions(IBeXContextPattern ibexPattern, TGGRuleNode node) {
		Objects.requireNonNull(ibexPattern, "ibexContextPattern must not be null!");

		Optional<IBeXNode> ibexNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, node.getName());
		if (!ibexNode.isPresent()) {
			logger.error("Node " + node.getName() + " is missing!");
			return;
		}

		for (TGGInplaceAttributeExpression attrExp : node.getAttrExpr()) {
			IBeXAttributeConstraint ibexAttrConstraint = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeConstraint();
			ibexAttrConstraint.setNode(ibexNode.get());
			ibexAttrConstraint.setType(attrExp.getAttribute());

			IBeXRelation ibexRelation = convertRelation(attrExp.getOperator());
			ibexAttrConstraint.setRelation(ibexRelation);
			convertValue(ibexPattern, attrExp.getValueExpr(), attrExp.getAttribute())
					.ifPresent(value -> ibexAttrConstraint.setValue(value));
			ibexPattern.getAttributeConstraint().add(ibexAttrConstraint);
		}
	}

	protected Optional<IBeXAttributeValue> convertValue(IBeXContextPattern ibexPattern, TGGExpression valueExpr,
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

	protected IBeXRelation convertRelation(TGGAttributeConstraintOperators operator) {
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

	/**
	 * Add the given pattern to the list.
	 * 
	 * @param ibexPattern the context pattern to add, must not be <code>null</code>
	 */
	public void addContextPattern(final IBeXContextPattern ibexPattern, TGGNamedElement tggElement) {
		addContextPattern(ibexPattern);
		patternToRuleMap.put(ibexPattern, tggElement);
	}

	public void addContextPattern(final IBeXContextPattern ibexPattern) {
		Objects.requireNonNull(ibexPattern, "The pattern must not be null!");

		ibexContextPatterns.add(ibexPattern);
		nameToPattern.put(ibexPattern.getName(), ibexPattern);
	}

	private void transformEdgeToPatternInvocation(EReference type, IBeXNode ibexSourceNode, IBeXNode ibexTargetNode,
			IBeXContextPattern ibexPattern) {
		if (ibexSourceNode == ibexTargetNode) {
			transformEdge(type, ibexSourceNode, ibexTargetNode, ibexPattern, false);
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

	public IBeXNode transformNode(IBeXContextPattern ibexPattern, TGGRuleNode node) {
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
		transformEdge(srcType, corr, srcOfCorr, ibexPattern, true);

		EReference trgType = ((EReference) corr.getType().getEStructuralFeature("target"));
		TGGRuleNode trgOfCorr = corr.getTarget();
		transformEdge(trgType, corr, trgOfCorr, ibexPattern, true);
	}

	private void transformEdge(EReference type, TGGRuleNode srcNode, TGGRuleNode trgNode,
			IBeXContextPattern ibexPattern, boolean tooManyEdges) {
		IBeXNode sourceNode = transformNode(ibexPattern, srcNode);
		IBeXNode targetNode = transformNode(ibexPattern, trgNode);

		transformEdge(type, sourceNode, targetNode, ibexPattern, tooManyEdges);
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
	
	public void createAndConnectProtocolNode(TGGRule rule, IBeXContextPattern ibexPattern) {
		IBeXNode protocolNode = createProtocolNode(rule, ibexPattern);
		connectProtocolNode(ibexPattern, protocolNode, rule);
	}

	public IBeXNode createProtocolNode(TGGRule rule, IBeXContextPattern ibexPattern) {
		IBeXNode protocolNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
		protocolNode.setName(TGGPatternUtil.getProtocolNodeName(rule.getName()));
		EClass type = (EClass) options.getCorrMetamodel()
				.getEClassifier(TGGModelUtils.getMarkerTypeName(rule.getName()));
		protocolNode.setType(type);
		ibexPattern.getSignatureNodes().add(protocolNode);
		return protocolNode;
	}

	private void connectProtocolNode(IBeXContextPattern ibexPattern, IBeXNode protocolNode, TGGRule rule) {
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.SRC);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.TRG);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.SRC);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.TRG);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.CORR);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.CORR);
	}

	private void connectProtocolNode(IBeXContextPattern ibexPattern, TGGRule rule, IBeXNode protocolNode,
			BindingType type, DomainType domain) {
		Collection<TGGRuleNode> nodes = TGGModelUtils.getNodesByOperatorAndDomain(rule, type, domain);

		for (TGGRuleNode node : nodes) {
			EReference ref = (EReference) protocolNode.getType()
					.getEStructuralFeature(TGGModelUtils.getMarkerRefName(type, domain, node.getName()));
			transformEdge(ref, protocolNode, transformNode(ibexPattern, node), ibexPattern, false);
		}
	}
}
