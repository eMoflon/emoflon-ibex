package org.emoflon.ibex.gt.transformations;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.emoflon.ibex.common.patterns.IBeXPatternFactory;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.ArithmeticCalculationExpression;
import org.emoflon.ibex.gt.editor.gT.EditorApplicationCondition;
import org.emoflon.ibex.gt.editor.gT.EditorApplicationConditionType;
import org.emoflon.ibex.gt.editor.gT.EditorAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeExpression;
import org.emoflon.ibex.gt.editor.gT.EditorCondition;
import org.emoflon.ibex.gt.editor.gT.EditorEnumExpression;
import org.emoflon.ibex.gt.editor.gT.EditorExpression;
import org.emoflon.ibex.gt.editor.gT.EditorGTFile;
import org.emoflon.ibex.gt.editor.gT.EditorLiteralExpression;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorParameterExpression;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorPatternType;
import org.emoflon.ibex.gt.editor.gT.EditorReference;
import org.emoflon.ibex.gt.editor.gT.StochasticFunctionExpression;
import org.emoflon.ibex.gt.editor.utils.GTCommentExtractor;
import org.emoflon.ibex.gt.editor.utils.GTEditorModelUtils;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdgeSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodeSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelFactoryImpl;

import GTLanguage.GTLanguageFactory;
import GTLanguage.GTParameter;

/**
 * Transformation from the editor model to IBeX Patterns.
 */
public class EditorToIBeXPatternTransformation extends AbstractEditorModelTransformation<IBeXModel> {

	private List<IBeXNode> ibexNodes = new LinkedList<>();
	
	private List<IBeXEdge> ibexEdges = new LinkedList<>();
	
	private Map<String, Map<EditorNode, IBeXNode>> node2ibexNode = new HashMap<>();
	
	private Map<String, Map<EditorReference, IBeXEdge>> reference2ibexEdge = new HashMap<>();
	
	/**
	 * All context patterns.
	 */
	private Set<IBeXContext> ibexContextPatterns = new LinkedHashSet<>();

	/**
	 * All create patterns.
	 */
	private List<IBeXCreatePattern> ibexCreatePatterns = new LinkedList<>();;

	/**
	 * All delete patterns.
	 */
	private List<IBeXDeletePattern> ibexDeletePatterns = new LinkedList<>();
	
	private List<IBeXRule> ibexRules = new LinkedList<>();

	/**
	 * Mapping between pattern names and the context patterns.
	 */
	private Map<String, IBeXContext> nameToPattern = new HashMap<>();
		

	public Map<String, Map<EditorNode, IBeXNode>> getNode2ibexNode() {
		return node2ibexNode;
	}

	public Map<String, Map<EditorReference, IBeXEdge>> getReference2ibexEdge() {
		return reference2ibexEdge;
	}


	public Map<String, IBeXContext> getNameToPattern() {
		return nameToPattern;
	}

	@Override
	public IBeXModel transform(final EditorGTFile file) {
		Objects.requireNonNull(file, "The editor file must not be null!");
		file.getPatterns().stream() //
			.forEach(editorPattern -> calcFlattenedPattern(editorPattern));
		
		file.getPatterns().stream() //
			.filter(editorPattern -> !editorPattern.isAbstract())
			.forEach(editorPattern -> transformPattern(pattern2flattened.get(editorPattern)));

		return createSortedIBeXSets();
	}

	/**
	 * Creates a pattern set with pattern lists sorted alphabetically.
	 * 
	 * @return the IBeXPatternSet
	 */
	private IBeXModel createSortedIBeXSets() {
		List<IBeXContext> sortList = new LinkedList<>(ibexContextPatterns);
		ibexContextPatterns.clear();
		sortList.sort(IBeXPatternUtils.sortByName);
		ibexContextPatterns.addAll(sortList);
		
		ibexRules.sort(IBeXPatternUtils.sortByName);
		ibexNodes.addAll(node2ibexNode.values().stream()
				.flatMap(map -> map.values().stream())
				.collect(Collectors.toList()));
		ibexNodes.sort(IBeXPatternUtils.sortByName);
		ibexEdges.addAll(reference2ibexEdge.values().stream()
				.flatMap(map -> map.values().stream())
				.collect(Collectors.toList()));
		ibexEdges.sort(IBeXPatternUtils.sortByName);
		
		IBeXModel ibexModel = IBeXPatternModelFactory.eINSTANCE.createIBeXModel();
		
		IBeXPatternSet ibexPatternSet = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternSet();
		ibexModel.setPatternSet(ibexPatternSet);
		ibexPatternSet.getContextPatterns().addAll(ibexContextPatterns);
		
		IBeXRuleSet ibexRuleSet = IBeXPatternModelFactoryImpl.eINSTANCE.createIBeXRuleSet();
		ibexModel.setRuleSet(ibexRuleSet);
		ibexRuleSet.getRules().addAll(ibexRules);
		
		IBeXNodeSet ibexNodeSet = IBeXPatternModelFactoryImpl.eINSTANCE.createIBeXNodeSet();
		ibexNodeSet.getNodes().addAll(ibexNodes);
		ibexModel.setNodeSet(ibexNodeSet);
		
		IBeXEdgeSet ibexEdgeSet = IBeXPatternModelFactoryImpl.eINSTANCE.createIBeXEdgeSet();
		ibexEdgeSet.getEdges().addAll(ibexEdges);
		ibexModel.setEdgeSet(ibexEdgeSet);
		
		return ibexModel;
	}

	/**chrome
	 * Transforms the given pattern to a context, a create and a delete pattern.
	 * 
	 * @param editorPattern
	 *            the editor pattern to transform
	 */
	private void transformPattern(final EditorPattern editorPattern) {
		Objects.requireNonNull(editorPattern, "The pattern must not be null!");

		if (nameToPattern.containsKey(editorPattern.getName())) {
			// Already transformed.
			return;
		}
		
		transformToContextPattern(editorPattern);
		if (editorPattern.getType() == EditorPatternType.RULE) {
			transformToRule(editorPattern);
		}

	}

	/**
	 * Add the given pattern to the list.
	 * 
	 * @param ibexPattern
	 *            the context pattern to add, must not be <code>null</code>
	 */
	private void addContextPattern(final EditorPattern editorPattern, final IBeXContext ibexPattern) {
		Objects.requireNonNull(ibexPattern, "The pattern must not be null!");

		ibexContextPatterns.add(ibexPattern);
		if (ibexPattern instanceof IBeXContextAlternatives) {
			ibexContextPatterns.removeAll(((IBeXContextAlternatives) ibexPattern).getAlternativePatterns());
		}

		nameToPattern.put(ibexPattern.getName(), ibexPattern);
	}

	/**
	 * Returns the IBeXContextPattern for the given editor pattern. If it does not
	 * exist, it is created.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @return the IBeXContextPattern
	 */
	public IBeXContext getContextPattern(final EditorPattern editorPattern) {
		if (!nameToPattern.containsKey(editorPattern.getName())) {
			transformPattern(editorPattern);
		}
		return nameToPattern.get(editorPattern.getName());
	}

	/**
	 * Transforms an editor pattern to an IBeXContextPattern.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 */
	private void transformToContextPattern(final EditorPattern editorPattern) {
		if (editorPattern.getConditions().size() > 1) {
			transformToAlternatives(editorPattern);
		} else {
			IBeXContextPattern ibexPattern = transformToContextPattern(editorPattern, editorPattern.getName(),
					editorNode -> GTEditorModelUtils.isLocal(editorNode));
			if (editorPattern.getConditions().size() == 1) {
				EditorCondition editorCondition = editorPattern.getConditions().get(0);
				transformCondition(ibexPattern, editorCondition);
			}
		}
	}

	/**
	 * Transforms an editor pattern with more than one condition to an
	 * {@link IBeXContextAlternatives}.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 */
	private void transformToAlternatives(final EditorPattern editorPattern) {
		IBeXContextAlternatives ibexAlternatives = IBeXPatternModelFactory.eINSTANCE.createIBeXContextAlternatives();
		ibexAlternatives.setName(editorPattern.getName());
		ibexAlternatives.setContext(transformToContextPattern(editorPattern, editorPattern.getName(), editorNode -> GTEditorModelUtils.isLocal(editorNode)));
		nameToPattern.remove(editorPattern.getName());
		node2ibexNode.remove(editorPattern.getName());
		ibexNodes.addAll(ibexAlternatives.getContext().getSignatureNodes());
		ibexNodes.addAll(ibexAlternatives.getContext().getLocalNodes());
		ibexContextPatterns.remove(ibexAlternatives.getContext());
		
		for (final EditorCondition editorCondition : editorPattern.getConditions()) {
			String name = editorPattern.getName() + "-ALTERNATIVE-" + editorCondition.getName();
			IBeXContextPattern ibexPattern = transformToContextPattern(editorPattern, name,
					editorNode -> GTEditorModelUtils.isLocal(editorNode));
			transformCondition(ibexPattern, editorCondition);
			ibexAlternatives.getAlternativePatterns().add(ibexPattern);
		}

		addContextPattern(editorPattern, ibexAlternatives);
	}

	/**
	 * Transforms an editor pattern to an IBeXContextPattern.
	 * 
	 * @param editorPattern
	 *            the editor pattern, must not be <code>null</code>
	 * @param name
	 *            the name of the added pattern
	 * @param isLocalCheck
	 *            the condition which nodes shall be local nodes
	 */
	private IBeXContextPattern transformToContextPattern(final EditorPattern editorPattern, final String name,
			final Predicate<EditorNode> isLocalCheck) {
		if (nameToPattern.containsKey(name)) {
			return (IBeXContextPattern) nameToPattern.get(name);
		}
		
		if(!node2ibexNode.containsKey(name)) {
			node2ibexNode.put(name, new HashMap<>());
		}
		
		if(!reference2ibexEdge.containsKey(name)) {
			reference2ibexEdge.put(name, new HashMap<>());
		}
		
		IBeXContextPattern ibexPattern = IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(name);
		addContextPattern(editorPattern, ibexPattern);
		
		// Transform nodes.
		List<EditorNode> editorNodes = GTEditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.CONTEXT,
				EditorOperator.DELETE);
		for (final EditorNode editorNode : editorNodes) {
			IBeXNode ibexNode = node2ibexNode.get(name).get(editorNode);
			if(ibexNode == null) {
				Objects.requireNonNull(editorNode, "Node must not be null!");
				ibexNode = IBeXPatternFactory.createNode(editorNode.getName(), editorNode.getType());
				node2ibexNode.get(name).put(editorNode, ibexNode);
			}

			if (isLocalCheck.test(editorNode)) {
				ibexPattern.getLocalNodes().add(ibexNode);
			} else {
				ibexPattern.getSignatureNodes().add(ibexNode);
			}			
			
		}

		// Transform attributes.
		for (final EditorNode editorNode : editorNodes) {
			transformAttributeConditions(editorPattern, ibexPattern, editorNode);
		}

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(ibexPattern);

		// Transform edges.
		List<EditorReference> edges = GTEditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.CONTEXT,
				EditorOperator.DELETE);

		edges.stream()
			.filter(editorReference -> !reference2ibexEdge.get(name).containsKey(editorReference))
			.forEach(editorReference -> {
				IBeXEdge edge = transformEdge(editorPattern, editorReference, ibexPattern);
				ibexPattern.getLocalEdges().add(edge);
				reference2ibexEdge.get(name).put(editorReference, edge);
		});
		
		ibexPattern.getLocalEdges().addAll(edges.stream()
				.filter(editorReference -> reference2ibexEdge.get(name).containsKey(editorReference))
				.map(editorReference -> reference2ibexEdge.get(name).get(editorReference))
				.collect(Collectors.toList()));
		
		// Transform parameters
		editorPattern.getParameters().forEach(param -> {
			IBeXParameter ibexParameter = IBeXPatternModelFactory.eINSTANCE.createIBeXParameter();
			ibexParameter.setName(param.getName());
			ibexParameter.setType(param.getType());
			ibexPattern.getParameters().add(ibexParameter);
		});
		// Add documentation
		try {
			ibexPattern.setDocumentation(GTCommentExtractor.getComment(editorPattern));
		} catch(Exception e) {
			ibexPattern.setDocumentation("");
		}
		
		
		return ibexPattern;
	}

	/**
	 * Transforms a GTEdge into an IBeXEdge.
	 * 
	 * @param editorReference
	 *            the editor reference
	 * @param ibexPattern
	 *            the pattern the edge belongs to.
	 * @return the IBeXEdge
	 */
	private IBeXEdge transformEdge(final EditorPattern editorPattern, final EditorReference editorReference, final IBeXContextPattern ibexPattern) {
		Objects.requireNonNull(editorReference, "The editor reference must not be null!");

		IBeXEdge ibexEdge = IBeXPatternModelFactory.eINSTANCE.createIBeXEdge();
		ibexEdge.setType(editorReference.getType());
		ibexEdge.setSourceNode(node2ibexNode.get(ibexPattern.getName()).get(GTEditorModelUtils.getSourceNode(editorReference)));
		ibexEdge.setTargetNode(node2ibexNode.get(ibexPattern.getName()).get(editorReference.getTarget()));
		ibexEdge.setName(ibexEdge.getSourceNode().getName()+"->"+ibexEdge.getTargetNode().getName());
		return ibexEdge;
	}
	
	/**
	 * Transforms the condition of the editor pattern.
	 * 
	 * @param condition
	 *            the condition
	 */
	
	public void transformCondition(final IBeXContextPattern invokingPattern, final EditorCondition condition) {
		Objects.requireNonNull(condition, "The condition must not be null!");

		for (EditorApplicationCondition applicationCondition : EditorToIBeXPatternHelper.getApplicationConditions(condition)) {
			transformPattern(invokingPattern, getFlattenedPattern(applicationCondition.getPattern()),
					applicationCondition.getType() == EditorApplicationConditionType.POSITIVE);
		}
	}
	
	/**
	 * Creates a pattern invocation for the given editor pattern mapping nodes of
	 * the same name.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @param invocationType
	 *            <code>true</code> for positive invocation, <code>false</code> for
	 *            negative invocation
	 */
	private void transformPattern(final IBeXContextPattern invokingPattern, final EditorPattern editorPattern, final boolean invocationType) {
		IBeXContext contextPattern = getContextPattern(editorPattern);
		if (!(contextPattern instanceof IBeXContextPattern)) {
			logError("%s not allowed in condition.", editorPattern.getName());
			return;
		}
		IBeXContextPattern invokedPattern = (IBeXContextPattern) contextPattern;

		IBeXPatternInvocation invocation = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(invocationType);
		invokingPattern.getInvocations().add(invocation);

		Map<IBeXNode, IBeXNode> nodeMap = EditorToIBeXPatternHelper.determineNodeMapping(invokingPattern, invokedPattern);
		if (nodeMap.size() == invokedPattern.getSignatureNodes().size()) {
			invocation.setInvokedPattern(invokedPattern);
			EditorToIBeXPatternHelper.addNodeMapping(invocation, nodeMap);
		} else { // not all signature nodes are mapped.
			IBeXContextPattern subContext = transformContextPatternForSignature(editorPattern, nodeMap);
			invocation.setInvokedPattern(subContext);
			EditorToIBeXPatternHelper.addNodeMapping(invocation, EditorToIBeXPatternHelper.determineNodeMapping(invokingPattern, subContext));
		}
	}
	
	/**
	 * Creates a context pattern for the given editor pattern which has the
	 * signature nodes of the given map. All other nodes will become local nodes.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @param nodeMap
	 *            the node mapping
	 * @return the IBeXContextPattern if it exists
	 */
	private IBeXContextPattern transformContextPatternForSignature(final EditorPattern editorPattern,
			final Map<IBeXNode, IBeXNode> nodeMap) {
		List<String> signatureNodeNames = nodeMap.values().stream() //
				.map(n -> n.getName()) //
				.collect(Collectors.toList());
		String patternName = editorPattern.getName() + "-CONDITION-"
				+ signatureNodeNames.stream().collect(Collectors.joining(","));
		return  transformToContextPattern(editorPattern, patternName, 
				editorNode -> !signatureNodeNames.contains(editorNode.getName()));
		
	}
	
	/**
	 * Transforms each attribute condition of the editor node to an
	 * {@link IBeXAttributeConstraint} and adds them to the given context pattern.
	 * 
	 * @param ibexContextPattern
	 *            the context pattern
	 */
	public void transformAttributeConditions(final EditorPattern editorPattern, final IBeXContextPattern ibexContextPattern, final EditorNode editorNode) {
		Objects.requireNonNull(ibexContextPattern, "ibexContextPattern must not be null!");

		Optional<IBeXNode> ibexNode = IBeXPatternUtils.findIBeXNodeWithName(ibexContextPattern, editorNode.getName());
		if (!ibexNode.isPresent()) {
			logError("Node %s missing!", editorNode.getName());
			return;
		}

		for (final EditorAttribute editorAttribute : EditorToIBeXPatternHelper.filterAttributes(editorNode, EditorToIBeXPatternHelper.isAssignment.negate())) {
			transformAttributeCondition(editorPattern, editorAttribute, ibexNode.get(), ibexContextPattern);
		}
	}
	
	/**
	 * Transforms an attribute condition to an {@link IBeXAttributeConstraint}.
	 * 
	 * @param editorAttribute
	 *            the editor attribute to transform
	 * @param ibexNode
	 *            the IBeXNode
	 * @param ibexContextPattern
	 *            the context pattern
	 */
	private void transformAttributeCondition(final EditorPattern editorPattern, final EditorAttribute editorAttribute, final IBeXNode ibexNode,
			final IBeXContextPattern ibexContextPattern) {
		IBeXAttributeConstraint ibexAttrConstraint = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeConstraint();
		ibexAttrConstraint.setNode(ibexNode);
		ibexAttrConstraint.setType(editorAttribute.getAttribute());

		IBeXRelation ibexRelation = EditorToIBeXPatternHelper.convertRelation(editorAttribute.getRelation());
		ibexAttrConstraint.setRelation(ibexRelation);
		convertAttributeValue(editorPattern, editorAttribute, ibexContextPattern).ifPresent(v -> ibexAttrConstraint.setValue(v));
		ibexContextPattern.getAttributeConstraint().add(ibexAttrConstraint);
	}
	
	/**
	 * Convert the value of the editor attribute.
	 * 
	 * @param editorAttribute
	 *            the editor attribute
	 * @param ibexPattern
	 *            the IBeXPattern
	 * @return an {@link Optional} for the IBeXAttributeValue
	 */
	private Optional<IBeXAttributeValue> convertAttributeValue(final EditorPattern editorPattern, final EditorAttribute editorAttribute,
			final IBeXPattern ibexPattern) {
		EditorExpression value = editorAttribute.getValue();
		if (value instanceof EditorAttributeExpression) {
			return convertAttributeValue(editorPattern, (EditorAttributeExpression) value, ibexPattern);
		} else if (value instanceof EditorEnumExpression) {
			return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue((EditorEnumExpression) value));
		} else if (value instanceof EditorLiteralExpression) {
			return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue((EditorLiteralExpression) value,
					editorAttribute.getAttribute().getEAttributeType()));
		} else if (value instanceof EditorParameterExpression) {
			return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue((EditorParameterExpression) value));
		} else if(value instanceof StochasticFunctionExpression) {
			return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue((StochasticFunctionExpression) value));
		} else if(value instanceof ArithmeticCalculationExpression) {
			return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue((ArithmeticCalculationExpression) value));
		}
		else {
			logError("Invalid attribute value: %s", editorAttribute.getValue());
			return Optional.empty();
		}
	}
	
	/**
	 * Sets the attribute value for the given attribute to the attribute expression.
	 * 
	 * @param editorExpression
	 *            the attribute expression
	 * @param ibexPattern
	 *            the IBeXPattern
	 * @return the IBeXAttributeExpression
	 */
	private Optional<IBeXAttributeValue> convertAttributeValue(final EditorPattern editorPattern, final EditorAttributeExpression editorExpression,
			final IBeXPattern ibexPattern) {
		IBeXAttributeExpression ibexAttributeExpression = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeExpression();
		ibexAttributeExpression.setAttribute(editorExpression.getAttribute());
		Optional<IBeXNode> ibexExistingNode = Optional.of(getNode2ibexNode().get(ibexPattern.getName()).get(editorExpression.getNode()));

		if (!ibexExistingNode.isPresent() && ibexPattern instanceof IBeXCreatePattern) {
			Objects.requireNonNull(editorExpression.getNode(), "Node must not be null!");
			IBeXNode ibexNode = IBeXPatternFactory.createNode(editorExpression.getNode().getName(), editorExpression.getNode().getType());
			getNode2ibexNode().get(ibexPattern.getName()).put(editorExpression.getNode(), ibexNode);
			((IBeXCreatePattern) ibexPattern).getContextNodes().add(ibexNode);
			Optional.of(ibexNode);
		}

		return ibexExistingNode.map(ibexNode -> {
			ibexAttributeExpression.setNode(ibexNode);
			return ibexAttributeExpression;
		});
	}
	private void transformToRule(final EditorPattern editorPattern) {
		IBeXCreatePattern ibexCreatePattern = transformToCreatePattern(editorPattern);
		IBeXDeletePattern ibexDeletePattern = transformToDeletePattern(editorPattern);
		
		IBeXRule ibexRule = IBeXPatternModelFactory.eINSTANCE.createIBeXRule();
		ibexRule.setName(editorPattern.getName());
		if(nameToPattern.containsKey(editorPattern.getName())) {
			ibexRule.setLhs(nameToPattern.get(editorPattern.getName()));
		}
		
		if(ibexCreatePattern != null) {
			ibexRule.setCreate(ibexCreatePattern);
		}
		
		if(ibexDeletePattern != null) {
			ibexRule.setDelete(ibexDeletePattern);
		}
		IBeXContextPattern lhs = null;
		if(ibexRule.getLhs() instanceof IBeXContextPattern) {
			lhs = (IBeXContextPattern) ibexRule.getLhs();
		} else {
			lhs = ((IBeXContextAlternatives) ibexRule.getLhs()).getContext();
		}
		
		ibexRule.getParameters().addAll(lhs.getParameters());
		ibexRule.setDocumentation(lhs.getDocumentation());
		
		IBeXContextPattern rhs = IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();
		rhs.setName(lhs.getName()+"_rhs");
		rhs.getSignatureNodes().addAll(lhs.getSignatureNodes().stream()
				.filter(lhsNode -> !ibexDeletePattern.getDeletedNodes().contains(lhsNode))
				.collect(Collectors.toList()));
		rhs.getLocalNodes().addAll(lhs.getLocalNodes().stream()
				.filter(lhsNode -> !ibexDeletePattern.getDeletedNodes().contains(lhsNode))
				.collect(Collectors.toList()));
		rhs.getLocalEdges().addAll(lhs.getLocalEdges().stream()
				.filter(lhsEdge -> !ibexDeletePattern.getDeletedEdges().contains(lhsEdge))
				.collect(Collectors.toList()));
		rhs.getSignatureNodes().addAll(ibexCreatePattern.getCreatedNodes());
		rhs.getLocalEdges().addAll(ibexCreatePattern.getCreatedEdges());
		ibexRule.setRhs(rhs);
		
		ibexRules.add(ibexRule);
	}

	/**
	 * Transforms an editor pattern to an IBeXCreatePattern.
	 * 
	 * @param editorPattern
	 *            the rule, must not be <code>null</code>
	 */
	private IBeXCreatePattern transformToCreatePattern(final EditorPattern editorPattern) {
		IBeXCreatePattern ibexCreatePattern = IBeXPatternModelFactory.eINSTANCE.createIBeXCreatePattern();
		ibexCreatePattern.setName(editorPattern.getName());
		
		GTEditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.CREATE).forEach(editorNode -> {
			IBeXNode ibexNode = node2ibexNode.get(editorPattern.getName()).get(editorNode);
			if(ibexNode == null) {
				Objects.requireNonNull(editorNode, "Node must not be null!");
				ibexNode = IBeXPatternFactory.createNode(editorNode.getName(), editorNode.getType());
				node2ibexNode.get(editorPattern.getName()).put(editorNode, ibexNode);
			}
			ibexCreatePattern.getCreatedNodes().add(ibexNode);
		});
		
		List<IBeXNode> context = new LinkedList<>();
		GTEditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.CREATE).forEach(editorReference -> {
			IBeXEdge ibexEdge = reference2ibexEdge.get(editorPattern.getName()).get(editorReference);
			if(ibexEdge == null) {
				EditorNode editorSourceNode = GTEditorModelUtils.getSourceNode(editorReference);
				EditorNode editorTargetNode = editorReference.getTarget();
				
				IBeXNode srcNode = node2ibexNode.get(editorPattern.getName()).get(editorSourceNode);
				IBeXNode trgNode = node2ibexNode.get(editorPattern.getName()).get(editorTargetNode);
				
				if(srcNode == null) {
					Objects.requireNonNull(editorSourceNode, "Node must not be null!");
					srcNode = IBeXPatternFactory.createNode(editorSourceNode.getName(), editorSourceNode.getType());
					node2ibexNode.get(editorPattern.getName()).put(editorSourceNode, srcNode);
					context.add(srcNode);
				}
				if(trgNode == null) {
					Objects.requireNonNull(editorTargetNode, "Node must not be null!");
					trgNode = IBeXPatternFactory.createNode(editorTargetNode.getName(), editorTargetNode.getType());
					node2ibexNode.get(editorPattern.getName()).put(editorTargetNode, trgNode);
					context.add(trgNode);
				}
				
				ibexEdge = IBeXPatternFactory.createEdge(srcNode, trgNode, editorReference.getType());
				ibexEdge.setName(ibexEdge.getSourceNode().getName()+"->"+ibexEdge.getTargetNode().getName());
				reference2ibexEdge.get(editorPattern.getName()).put(editorReference, ibexEdge);
			} 
			ibexCreatePattern.getCreatedEdges().add(ibexEdge);
		});
		context.sort(IBeXPatternUtils.sortByName);
		ibexCreatePattern.getContextNodes().addAll(context);

		List<EditorNode> editorNodes = GTEditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.CONTEXT,
				EditorOperator.CREATE);
		for (final EditorNode editorNode : editorNodes) {
			transformAttributeAssignments(editorPattern, ibexCreatePattern, editorNode);
		}

		ibexCreatePatterns.add(ibexCreatePattern);
		return ibexCreatePattern;
	}

	/**
	 * Transforms an editor pattern into an IBeXDeletePattern.
	 * 
	 * @param editorPattern
	 *            the rule, must not be <code>null</code>
	 */
	private IBeXDeletePattern transformToDeletePattern(final EditorPattern editorPattern) {
		IBeXDeletePattern ibexDeletePattern = IBeXPatternModelFactory.eINSTANCE.createIBeXDeletePattern();
		ibexDeletePattern.setName(editorPattern.getName());

		GTEditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.DELETE).forEach(editorNode -> {
			IBeXNode ibexNode = node2ibexNode.get(editorPattern.getName()).get(editorNode);
			if(ibexNode == null) {
				Objects.requireNonNull(editorNode, "Node must not be null!");
				ibexNode = IBeXPatternFactory.createNode(editorNode.getName(), editorNode.getType());
				node2ibexNode.get(editorPattern.getName()).put(editorNode, ibexNode);
			}
			ibexDeletePattern.getDeletedNodes().add(ibexNode);
		});
		
		List<IBeXNode> context = new LinkedList<>();
		GTEditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.DELETE).forEach(editorReference -> {
			IBeXEdge ibexEdge = reference2ibexEdge.get(editorPattern.getName()).get(editorReference);
			if(ibexEdge == null) {
				EditorNode editorSourceNode = GTEditorModelUtils.getSourceNode(editorReference);
				EditorNode editorTargetNode = editorReference.getTarget();
				
				IBeXNode srcNode = node2ibexNode.get(editorPattern.getName()).get(editorSourceNode);
				IBeXNode trgNode = node2ibexNode.get(editorPattern.getName()).get(editorTargetNode);
				
				if(srcNode == null) {
					Objects.requireNonNull(editorSourceNode, "Node must not be null!");
					srcNode = IBeXPatternFactory.createNode(editorSourceNode.getName(), editorSourceNode.getType());
					node2ibexNode.get(editorPattern.getName()).put(editorSourceNode, srcNode);
					context.add(srcNode);
				}
				if(trgNode == null) {
					Objects.requireNonNull(editorTargetNode, "Node must not be null!");
					trgNode = IBeXPatternFactory.createNode(editorTargetNode.getName(), editorTargetNode.getType());
					node2ibexNode.get(editorPattern.getName()).put(editorTargetNode, trgNode);
					context.add(trgNode);
				}
				
				ibexEdge = IBeXPatternFactory.createEdge(srcNode, trgNode, editorReference.getType());
				ibexEdge.setName(ibexEdge.getSourceNode().getName()+"->"+ibexEdge.getTargetNode().getName());
				reference2ibexEdge.get(editorPattern.getName()).put(editorReference, ibexEdge);
			} 
			ibexDeletePattern.getDeletedEdges().add(ibexEdge);
		});
		context.sort(IBeXPatternUtils.sortByName);
		ibexDeletePattern.getContextNodes().addAll(context);

		ibexDeletePatterns.add(ibexDeletePattern);
		return ibexDeletePattern;
	}
	
	/**
	 * Transforms each attribute assignment of the editor node to an
	 * {@link IBeXAttributeAssignment} and adds them to the given create pattern.
	 *
	 * @param ibexCreatePattern
	 *            the create pattern
	 */
	private void transformAttributeAssignments(final EditorPattern editorPattern, final IBeXCreatePattern ibexCreatePattern, final EditorNode editorNode) {
		List<EditorAttribute> attributeAssignments = EditorToIBeXPatternHelper.filterAttributes(editorNode, EditorToIBeXPatternHelper.isAssignment);
		if (attributeAssignments.size() == 0) {
			return;
		}

		IBeXNode ibexNode = getNode2ibexNode().get(editorPattern.getName()).get(editorNode);
		if(ibexNode == null) {
			Objects.requireNonNull(editorNode, "Node must not be null!");
			ibexNode = IBeXPatternFactory.createNode(editorNode.getName(), editorNode.getType());
			getNode2ibexNode().get(editorPattern.getName()).put(editorNode, ibexNode);
			ibexCreatePattern.getContextNodes().add(ibexNode);
		}
		for (EditorAttribute editorAttribute : attributeAssignments) {
			transformAttributeAssignment(editorPattern, editorAttribute, ibexNode, ibexCreatePattern);
		}
	}
	
	/**
	 * Transforms an attribute assignment to an {@link IBeXAttributeAssignment}.
	 * 
	 * @param editorAttribute
	 *            the editor attribute to transform
	 * @param ibexNode
	 *            the IBeXNode
	 * @param ibexCreatePattern
	 *            the create pattern
	 */
	private void transformAttributeAssignment(final EditorPattern editorPattern, final EditorAttribute editorAttribute, final IBeXNode ibexNode,
			final IBeXCreatePattern ibexCreatePattern) {
		IBeXAttributeAssignment ibexAssignment = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeAssignment();
		ibexAssignment.setNode(ibexNode);
		ibexAssignment.setType(editorAttribute.getAttribute());
		convertAttributeValue(editorPattern, editorAttribute, ibexCreatePattern).ifPresent(v -> ibexAssignment.setValue(v));
		ibexCreatePattern.getAttributeAssignments().add(ibexAssignment);
	}
}
