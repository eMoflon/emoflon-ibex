package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

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
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;

/**
 * Transformation from the editor model to IBeX Patterns.
 */
public class EditorToIBeXPatternTransformation extends AbstractEditorModelTransformation<IBeXPatternSet> {
	/**
	 * Setting: usage of invocations for references.
	 */
	static final boolean USE_INVOCATIONS_FOR_REFERENCES = false;

	/**
	 * All context patterns.
	 */
	private List<IBeXContext> ibexContextPatterns = new ArrayList<IBeXContext>();

	/**
	 * All create patterns.
	 */
	private List<IBeXCreatePattern> ibexCreatePatterns = new ArrayList<IBeXCreatePattern>();;

	/**
	 * All delete patterns.
	 */
	private List<IBeXDeletePattern> ibexDeletePatterns = new ArrayList<IBeXDeletePattern>();

	/**
	 * Mapping between pattern names and the context patterns.
	 */
	private HashMap<String, IBeXContext> nameToPattern = new HashMap<String, IBeXContext>();
	private HashMap<IBeXContext, EditorPattern> pattern2EditorPattern = new HashMap<>();
	
	public IBeXContext getPattern(final String patternName) {
		return nameToPattern.get(patternName);
	}

	@Override
	public IBeXPatternSet transform(final EditorGTFile file) {
		Objects.requireNonNull(file, "The editor file must not be null!");
		file.getPatterns().stream() //
				.filter(p -> !p.isAbstract()) //
				.forEach(editorPattern -> transformPattern(editorPattern));

		nameToPattern.forEach((name, pattern) -> transformToContextPattern(pattern2EditorPattern.get(pattern), name));
		return createSortedPatternSet();
	}

	/**
	 * Creates a pattern set with pattern lists sorted alphabetically.
	 * 
	 * @return the IBeXPatternSet
	 */
	private IBeXPatternSet createSortedPatternSet() {
		ibexContextPatterns.sort(IBeXPatternUtils.sortByName);
		ibexCreatePatterns.sort(IBeXPatternUtils.sortByName);
		ibexDeletePatterns.sort(IBeXPatternUtils.sortByName);

		IBeXPatternSet ibexPatternSet = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternSet();
		ibexPatternSet.getContextPatterns().addAll(ibexContextPatterns);
		ibexPatternSet.getCreatePatterns().addAll(ibexCreatePatterns);
		ibexPatternSet.getDeletePatterns().addAll(ibexDeletePatterns);
		return ibexPatternSet;
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
				transformToCreatePattern(flattenedPattern);
				transformToDeletePattern(flattenedPattern);
			}
		});
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
		pattern2EditorPattern.put(ibexPattern, editorPattern);
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
			IBeXNode ibexNode = EditorToIBeXPatternHelper.transformNode(editorNode);
			if (isLocalCheck.test(editorNode)) {
				ibexPattern.getLocalNodes().add(ibexNode);
			} else {
				ibexPattern.getSignatureNodes().add(ibexNode);
			}
		}

//		// Transform attributes.
//		for (final EditorNode editorNode : editorNodes) {
//			new EditorToIBeXAttributeHelper(this, editorNode).transformAttributeConditions(ibexPattern);
//		}
//
//		// Ensure that all nodes must be disjoint even if they have the same type.
//		EditorToIBeXPatternHelper.addInjectivityConstraints(ibexPattern);
//
//		// Transform edges.
//		List<EditorReference> edges = EditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.CONTEXT,
//				EditorOperator.DELETE);
//		if (USE_INVOCATIONS_FOR_REFERENCES) {
//			edges.forEach(gtEdge -> transformEdgeToPatternInvocation(gtEdge, ibexPattern));
//		} else {
//			// No invocations, so include all edges as well.
//			edges.forEach(editorReference -> {
//				ibexPattern.getLocalEdges().add(transformEdge(editorReference, ibexPattern));
//			});
//		}
//
		addContextPattern(editorPattern, ibexPattern);
		return ibexPattern;
	}
	
	public IBeXContextPattern transformToContextPattern(final EditorPattern editorPattern, final String name) {
		IBeXContextPattern pattern = (IBeXContextPattern) nameToPattern.get(name);

		// Transform attributes.
		List<EditorNode> editorNodes = EditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.CONTEXT,
				EditorOperator.DELETE);
		for (final EditorNode editorNode : editorNodes) {
			new EditorToIBeXAttributeHelper(this, editorNode).transformAttributeConditions(pattern);
		}

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(pattern);

		// Transform edges.
		List<EditorReference> edges = EditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.CONTEXT,
				EditorOperator.DELETE);
		if (USE_INVOCATIONS_FOR_REFERENCES) {
			edges.forEach(gtEdge -> transformEdgeToPatternInvocation(gtEdge, pattern));
		} else {
			// No invocations, so include all edges as well.
			edges.forEach(editorReference -> {
				pattern.getLocalEdges().add(transformEdge(editorReference, pattern));
			});
		}
		return pattern;
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

		if (ibexSourceNode.get() == ibexTargetNode.get()) {
			ibexPattern.getLocalEdges().add(transformEdge(editorReference, ibexPattern));
			return;
		}

		Optional<IBeXContextPattern> edgePatternOptional = IBeXPatternUtils.createEdgePattern(editorReference.getType(), nameToPattern, s -> logError(s));
		if (!edgePatternOptional.isPresent()) {
			logError("Cannot create edge pattern for type " + editorReference.getType().getName());
			return;
		}
		IBeXContextPattern edgePattern = edgePatternOptional.get();
		addContextPattern(null, edgePattern);

		Optional<IBeXNode> ibexSignatureSourceNode = //
				IBeXPatternUtils.findIBeXNodeWithName(edgePattern.getSignatureNodes(), "src");
		Optional<IBeXNode> ibexSignatureTargetNode = //
				IBeXPatternUtils.findIBeXNodeWithName(edgePattern.getSignatureNodes(), "trg");
		if (!ibexSignatureSourceNode.isPresent() || !ibexSignatureTargetNode.isPresent()) {
			logError("Invalid signature nodes for edge pattern!");
			return;
		}

		IBeXPatternInvocation invocation = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(true);
		invocation.getMapping().put(ibexSourceNode.get(), ibexSignatureSourceNode.get());
		invocation.getMapping().put(ibexTargetNode.get(), ibexSignatureTargetNode.get());
		invocation.setInvokedPattern(edgePattern);
		ibexPattern.getInvocations().add(invocation);
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
		return ibexEdge;
	}

	/**
	 * Transforms an editor pattern to an IBeXCreatePattern.
	 * 
	 * @param editorPattern
	 *            the rule, must not be <code>null</code>
	 */
	private void transformToCreatePattern(final EditorPattern editorPattern) {
		IBeXCreatePattern ibexCreatePattern = IBeXPatternModelFactory.eINSTANCE.createIBeXCreatePattern();
		ibexCreatePattern.setName(editorPattern.getName());
		new EditorToIBeXPatternHelper(this).transformNodesAndEdgesOfOperator(editorPattern, EditorOperator.CREATE,
				ibexCreatePattern.getCreatedNodes(), ibexCreatePattern.getContextNodes(),
				ibexCreatePattern.getCreatedEdges());

		List<EditorNode> editorNodes = EditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.CONTEXT,
				EditorOperator.CREATE);
		for (final EditorNode editorNode : editorNodes) {
			new EditorToIBeXAttributeHelper(this, editorNode).transformAttributeAssignments(ibexCreatePattern);
		}

		ibexCreatePatterns.add(ibexCreatePattern);
	}

	/**
	 * Transforms an editor pattern into an IBeXDeletePattern.
	 * 
	 * @param editorPattern
	 *            the rule, must not be <code>null</code>
	 */
	private void transformToDeletePattern(final EditorPattern editorPattern) {
		IBeXDeletePattern ibexDeletePattern = IBeXPatternModelFactory.eINSTANCE.createIBeXDeletePattern();
		ibexDeletePattern.setName(editorPattern.getName());

		new EditorToIBeXPatternHelper(this).transformNodesAndEdgesOfOperator(editorPattern, EditorOperator.DELETE,
				ibexDeletePattern.getDeletedNodes(), ibexDeletePattern.getContextNodes(),
				ibexDeletePattern.getDeletedEdges());

		ibexDeletePatterns.add(ibexDeletePattern);
	}
}
