package org.emoflon.ibex.gt.transformations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.patterns.IBeXPatternFactory;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.EditorCondition;
import org.emoflon.ibex.gt.editor.gT.EditorGTFile;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorPatternType;
import org.emoflon.ibex.gt.editor.gT.EditorReference;
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
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelFactoryImpl;
import org.moflon.core.utilities.EcoreUtils;

/**
 * Transformation from the editor model to IBeX Patterns.
 */
public class EditorToIBeXPatternTransformation extends AbstractEditorModelTransformation<IBeXModel> {
	/**
	 * Setting: usage of invocations for references.
	 */
	static final boolean USE_INVOCATIONS_FOR_REFERENCES = false;

	private List<IBeXNode> ibexNodes = new LinkedList<>();
	
	private List<IBeXEdge> ibexEdges = new LinkedList<>();
	
	private Map<EditorNode, IBeXNode> node2ibexNode = new HashMap<>();
	
	private Map<EditorReference, IBeXEdge> reference2ibexEdge = new HashMap<>();
	
	/**
	 * All context patterns.
	 */
	private List<IBeXContext> ibexContextPatterns = new LinkedList<>();

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
		

	public Map<EditorNode, IBeXNode> getNode2ibexNode() {
		return node2ibexNode;
	}

	public Map<EditorReference, IBeXEdge> getReference2ibexEdge() {
		return reference2ibexEdge;
	}


	public Map<String, IBeXContext> getNameToPattern() {
		return nameToPattern;
	}

	@Override
	public IBeXModel transform(final EditorGTFile file) {
		Objects.requireNonNull(file, "The editor file must not be null!");
		file.getPatterns().stream() //
				.filter(p -> !p.isAbstract()) //
				.forEach(editorPattern -> transformPattern(editorPattern));
		return createSortedIBeXSets();
	}

	/**
	 * Creates a pattern set with pattern lists sorted alphabetically.
	 * 
	 * @return the IBeXPatternSet
	 */
	private IBeXModel createSortedIBeXSets() {
		ibexContextPatterns.sort(IBeXPatternUtils.sortByName);
		ibexRules.sort(IBeXPatternUtils.sortByName);
		ibexNodes.addAll(node2ibexNode.values());
		ibexNodes.sort(IBeXPatternUtils.sortByName);
		ibexEdges.addAll(reference2ibexEdge.values());
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

		getFlattenedPattern(editorPattern).ifPresent(flattenedPattern -> {
			transformToContextPattern(flattenedPattern);
			if (editorPattern.getType() == EditorPatternType.RULE) {
				transformToRule(editorPattern);
			}
		});
	}

	/**
	 * Add the given pattern to the list.
	 * 
	 * @param ibexPattern
	 *            the context pattern to add, must not be <code>null</code>
	 */
	private void addContextPattern(final IBeXContext ibexPattern) {
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
					editorNode -> EditorModelUtils.isLocal(editorNode));
			if (editorPattern.getConditions().size() == 1) {
				EditorCondition editorCondition = editorPattern.getConditions().get(0);
				new EditorToIBeXConditionHelper(this, ibexPattern).transformCondition(editorCondition);
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

		for (final EditorCondition editorCondition : editorPattern.getConditions()) {
			String name = editorPattern.getName() + "-ALTERNATIVE-" + editorCondition.getName();
			IBeXContextPattern ibexPattern = transformToContextPattern(editorPattern, name,
					editorNode -> EditorModelUtils.isLocal(editorNode));
			new EditorToIBeXConditionHelper(this, ibexPattern).transformCondition(editorCondition);
			ibexAlternatives.getAlternativePatterns().add(ibexPattern);
		}

		addContextPattern(ibexAlternatives);
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
	public IBeXContextPattern transformToContextPattern(final EditorPattern editorPattern, final String name,
			final Predicate<EditorNode> isLocalCheck) {
		if (nameToPattern.containsKey(name)) {
			return (IBeXContextPattern) nameToPattern.get(name);
		}

		IBeXContextPattern ibexPattern = IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(name);

		// Transform nodes.
		List<EditorNode> editorNodes = EditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.CONTEXT,
				EditorOperator.DELETE);
		for (final EditorNode editorNode : editorNodes) {
			IBeXNode ibexNode = node2ibexNode.get(editorNode);
			if(ibexNode == null) {
				Objects.requireNonNull(editorNode, "Node must not be null!");
				ibexNode = IBeXPatternFactory.createNode(editorNode.getName(), editorNode.getType());
				node2ibexNode.put(editorNode, ibexNode);
			}

			if (isLocalCheck.test(editorNode)) {
				ibexPattern.getLocalNodes().add(ibexNode);
			} else {
				ibexPattern.getSignatureNodes().add(ibexNode);
			}			
			
		}

		// Transform attributes.
		for (final EditorNode editorNode : editorNodes) {
			new EditorToIBeXAttributeHelper(this, editorNode).transformAttributeConditions(ibexPattern);
		}

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(ibexPattern);

		// Transform edges.
		List<EditorReference> edges = EditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.CONTEXT,
				EditorOperator.DELETE);
		if (USE_INVOCATIONS_FOR_REFERENCES) {
			edges.forEach(gtEdge -> transformEdgeToPatternInvocation(gtEdge, ibexPattern));
		} else {
			// No invocations, so include all edges as well.
			edges.stream()
				.filter(editorReference -> !reference2ibexEdge.containsKey(editorReference))
				.forEach(editorReference -> {
					IBeXEdge edge = transformEdge(editorReference, ibexPattern);
					ibexPattern.getLocalEdges().add(edge);
					reference2ibexEdge.put(editorReference, edge);
			});
		}

		addContextPattern(ibexPattern);
		return ibexPattern;
	}

	/**
	 * Transforms an edge to a pattern invocation.
	 * 
	 * @param editorReference
	 *            the edge to transform
	 * @param ibexPattern
	 *            the pattern where the pattern invocation shall be added
	 */
	private void transformEdgeToPatternInvocation(final EditorReference editorReference,
			final IBeXContextPattern ibexPattern) {
		if(reference2ibexEdge.containsKey(editorReference))
			return;
		
		EditorNode sourceNode = EditorModelUtils.getSourceNode(editorReference);
		EditorNode targetNode = editorReference.getTarget();
		if (sourceNode == null || sourceNode.getName() == null) {
			logError("Cannot resolve reference to source node.");
			return;
		}
		if (targetNode == null || targetNode.getName() == null) {
			logError("Cannot resolve reference to target node.");
			return;
		}

		Optional<IBeXNode> ibexSourceNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, sourceNode.getName());
		Optional<IBeXNode> ibexTargetNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, targetNode.getName());

		if (!ibexSourceNode.isPresent()) {
			logError("Could not find node %s!", sourceNode.getName());
			return;
		}
		if (!ibexTargetNode.isPresent()) {
			logError("Could not find node %s!", targetNode.getName());
			return;
		}

		if (ibexSourceNode.get() == ibexTargetNode.get() && !reference2ibexEdge.containsKey(editorReference)) {
			IBeXEdge edge = transformEdge(editorReference, ibexPattern);
			ibexPattern.getLocalEdges().add(edge);
			reference2ibexEdge.put(editorReference, edge);
			return;
		}
		
		Optional<IBeXContextPattern> edgePatternOptional = createEdgePattern(editorReference.getType(), ibexSourceNode.get(), ibexTargetNode.get());
		if (!edgePatternOptional.isPresent()) {
			logError("Cannot create edge pattern for type " + editorReference.getType().getName());
			return;
		}
		IBeXContextPattern edgePattern = edgePatternOptional.get();
		addContextPattern(edgePattern);

		IBeXPatternInvocation invocation = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(true);
		// Get Src at idx 0
		invocation.getMapping().put(ibexSourceNode.get(), edgePattern.getSignatureNodes().get(0));
		// Get Trg at idx 1
		invocation.getMapping().put(ibexTargetNode.get(), edgePattern.getSignatureNodes().get(1));
		invocation.setInvokedPattern(edgePattern);
		ibexPattern.getInvocations().add(invocation);
		// Get Edge at idx 0
		reference2ibexEdge.put(editorReference, edgePattern.getLocalEdges().get(0));
	}
	
	/**
	 * Create an {@link IBeXPattern} for the given edge. If an {@link IBeXPattern}
	 * for the given {@link EReference} exists already, the existing pattern is
	 * returned.
	 * 
	 * @param edgeType the EReference to create a pattern for
	 * @return the created IBeXPattern
	 */
	public Optional<IBeXContextPattern> createEdgePattern(final EReference edgeType, final IBeXNode ibexSrcNode, final IBeXNode ibexTrgNode) {
		Objects.requireNonNull(edgeType, "Edge type must not be null!");

		EClass sourceType = edgeType.getEContainingClass();
		EClass targetType = edgeType.getEReferenceType();

		if (sourceType == null || targetType == null) {
			logError("Cannot resolve reference source or target type.");
			return Optional.empty();
		}

		String name = String.format("edge-%s-%s-%s", EcoreUtils.getFQN(sourceType).replace(".", "_"),
				edgeType.getName(), EcoreUtils.getFQN(targetType).replace(".", "_"));

		if (nameToPattern.containsKey(name)) {
			return Optional.of((IBeXContextPattern) nameToPattern.get(name));
		}

		IBeXContextPattern edgePattern = IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();
		edgePattern.setName(name);
		// Put Src at idx 0
		edgePattern.getSignatureNodes().add(ibexSrcNode);
		// Put Trg at idx 1
		edgePattern.getSignatureNodes().add(ibexTrgNode);

		IBeXEdge ibexEdge = IBeXPatternFactory.createEdge(ibexSrcNode, ibexTrgNode, edgeType);
		ibexEdge.setName(ibexEdge.getSourceNode()+"->"+ibexEdge.getTargetNode());
		// Put Edge at idx 0
		edgePattern.getLocalEdges().add(ibexEdge);

		return Optional.of(edgePattern);
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
	private IBeXEdge transformEdge(final EditorReference editorReference, final IBeXContextPattern ibexPattern) {
		Objects.requireNonNull(editorReference, "The editor reference must not be null!");

		IBeXEdge ibexEdge = IBeXPatternModelFactory.eINSTANCE.createIBeXEdge();
		ibexEdge.setType(editorReference.getType());
		IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, EditorModelUtils.getSourceNode(editorReference).getName())
				.ifPresent(sourceNode -> ibexEdge.setSourceNode(sourceNode));
		IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, editorReference.getTarget().getName())
				.ifPresent(targetNode -> ibexEdge.setTargetNode(targetNode));
		ibexEdge.setName(ibexEdge.getSourceNode()+"->"+ibexEdge.getTargetNode());
		return ibexEdge;
	}
	
	private void transformToRule(final EditorPattern editorPattern) {
		IBeXCreatePattern ibexCreatePattern = transformToCreatePattern(editorPattern);
		IBeXDeletePattern ibexDeletePattern = transformToDeletePattern(editorPattern);
		
		IBeXRule ibexRule = IBeXPatternModelFactory.eINSTANCE.createIBeXRule();
		ibexRule.setName(editorPattern.getName()+"_rule");
		if(nameToPattern.containsKey(editorPattern.getName())) {
			ibexRule.setLhs(nameToPattern.get(editorPattern.getName()));
		}
		
		if(ibexCreatePattern != null) {
			ibexRule.setCreate(ibexCreatePattern);
		}
		
		if(ibexDeletePattern != null) {
			ibexRule.setDelete(ibexDeletePattern);
		}
		
		if(ibexRule.getLhs() instanceof IBeXContextPattern) {
			IBeXContextPattern lhs = (IBeXContextPattern) ibexRule.getLhs();
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
		}
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
		
		EditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.CREATE).forEach(editorNode -> {
			IBeXNode ibexNode = node2ibexNode.get(editorNode);
			if(ibexNode == null) {
				Objects.requireNonNull(editorNode, "Node must not be null!");
				ibexNode = IBeXPatternFactory.createNode(editorNode.getName(), editorNode.getType());
				node2ibexNode.put(editorNode, ibexNode);
			}
			ibexCreatePattern.getCreatedNodes().add(ibexNode);
		});
		
		List<IBeXNode> context = new LinkedList<>();
		EditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.CREATE).forEach(editorReference -> {
			IBeXEdge ibexEdge = reference2ibexEdge.get(editorReference);
			if(ibexEdge == null) {
				EditorNode editorSourceNode = EditorModelUtils.getSourceNode(editorReference);
				EditorNode editorTargetNode = editorReference.getTarget();
				
				IBeXNode srcNode = node2ibexNode.get(editorSourceNode);
				IBeXNode trgNode = node2ibexNode.get(editorTargetNode);
				
				if(srcNode == null) {
					Objects.requireNonNull(editorSourceNode, "Node must not be null!");
					srcNode = IBeXPatternFactory.createNode(editorSourceNode.getName(), editorSourceNode.getType());
					node2ibexNode.put(editorSourceNode, srcNode);
					context.add(srcNode);
				}
				if(trgNode == null) {
					Objects.requireNonNull(editorTargetNode, "Node must not be null!");
					trgNode = IBeXPatternFactory.createNode(editorTargetNode.getName(), editorTargetNode.getType());
					node2ibexNode.put(editorTargetNode, trgNode);
					context.add(trgNode);
				}
				
				ibexEdge = IBeXPatternFactory.createEdge(srcNode, trgNode, editorReference.getType());
				ibexEdge.setName(ibexEdge.getSourceNode()+"->"+ibexEdge.getTargetNode());
				reference2ibexEdge.put(editorReference, ibexEdge);
			} 
			ibexCreatePattern.getCreatedEdges().add(ibexEdge);
		});
		context.sort(IBeXPatternUtils.sortByName);
		ibexCreatePattern.getContextNodes().addAll(context);

		List<EditorNode> editorNodes = EditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.CONTEXT,
				EditorOperator.CREATE);
		for (final EditorNode editorNode : editorNodes) {
			new EditorToIBeXAttributeHelper(this, editorNode).transformAttributeAssignments(ibexCreatePattern);
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

		EditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.DELETE).forEach(editorNode -> {
			IBeXNode ibexNode = node2ibexNode.get(editorNode);
			if(ibexNode == null) {
				Objects.requireNonNull(editorNode, "Node must not be null!");
				ibexNode = IBeXPatternFactory.createNode(editorNode.getName(), editorNode.getType());
				node2ibexNode.put(editorNode, ibexNode);
			}
			ibexDeletePattern.getDeletedNodes().add(ibexNode);
		});
		
		List<IBeXNode> context = new LinkedList<>();
		EditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.DELETE).forEach(editorReference -> {
			IBeXEdge ibexEdge = reference2ibexEdge.get(editorReference);
			if(ibexEdge == null) {
				EditorNode editorSourceNode = EditorModelUtils.getSourceNode(editorReference);
				EditorNode editorTargetNode = editorReference.getTarget();
				
				IBeXNode srcNode = node2ibexNode.get(editorSourceNode);
				IBeXNode trgNode = node2ibexNode.get(editorTargetNode);
				
				if(srcNode == null) {
					Objects.requireNonNull(editorSourceNode, "Node must not be null!");
					srcNode = IBeXPatternFactory.createNode(editorSourceNode.getName(), editorSourceNode.getType());
					node2ibexNode.put(editorSourceNode, srcNode);
					context.add(srcNode);
				}
				if(trgNode == null) {
					Objects.requireNonNull(editorTargetNode, "Node must not be null!");
					trgNode = IBeXPatternFactory.createNode(editorTargetNode.getName(), editorTargetNode.getType());
					node2ibexNode.put(editorTargetNode, trgNode);
					context.add(trgNode);
				}
				
				ibexEdge = IBeXPatternFactory.createEdge(srcNode, trgNode, editorReference.getType());
				ibexEdge.setName(ibexEdge.getSourceNode()+"->"+ibexEdge.getTargetNode());
				reference2ibexEdge.put(editorReference, ibexEdge);
			} 
			ibexDeletePattern.getDeletedEdges().add(ibexEdge);
		});
		context.sort(IBeXPatternUtils.sortByName);
		ibexDeletePattern.getContextNodes().addAll(context);

		ibexDeletePatterns.add(ibexDeletePattern);
		return ibexDeletePattern;
	}
}
