package org.emoflon.ibex.gt.transformations;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import java.util.Queue;
import java.util.Set;

import org.emoflon.ibex.common.patterns.IBeXPatternFactory;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.ArithmeticCalculationExpression;
import org.emoflon.ibex.gt.editor.gT.EditorApplicationCondition;
import org.emoflon.ibex.gt.editor.gT.EditorApplicationConditionType;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeAssignment;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeConstraint;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeExpression;
import org.emoflon.ibex.gt.editor.gT.EditorCondition;
import org.emoflon.ibex.gt.editor.gT.EditorEnumExpression;
import org.emoflon.ibex.gt.editor.gT.EditorExpression;
import org.emoflon.ibex.gt.editor.gT.EditorGTFile;
import org.emoflon.ibex.gt.editor.gT.EditorIteratorAttributeAssignment;
import org.emoflon.ibex.gt.editor.gT.EditorIteratorAttributeAssignmentItr;
import org.emoflon.ibex.gt.editor.gT.EditorIteratorAttributeAssignmentNode;
import org.emoflon.ibex.gt.editor.gT.EditorIteratorAttributeExpression;
import org.emoflon.ibex.gt.editor.gT.EditorIteratorReference;
import org.emoflon.ibex.gt.editor.gT.EditorLiteralExpression;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorParameterExpression;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorPatternType;
import org.emoflon.ibex.gt.editor.gT.EditorReference;
import org.emoflon.ibex.gt.editor.gT.EditorReferenceIterator;
import org.emoflon.ibex.gt.editor.gT.StochasticFunctionExpression;
import org.emoflon.ibex.gt.editor.utils.GTCommentExtractor;
import org.emoflon.ibex.gt.editor.utils.GTEditorModelUtils;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdgeSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEnumLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXForEachExpression;
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


/**
 * Transformation from the editor model to IBeX Patterns.
 */
public class EditorToIBeXPatternTransformation extends AbstractEditorModelTransformation<IBeXModel> {
	
	private Queue<EditorPattern> rulesToBeTransformed = new LinkedList<>();

	@Override
	public IBeXModel transform(Collection<EditorGTFile> sourceModels) {

		sourceModels.forEach(file -> {
			Objects.requireNonNull(file, "The editor file must not be null!");
			// flattend all editor patterns
			file.getPatterns().stream() //
				.forEach(editorPattern -> calcFlattenedPattern(editorPattern));
			// transfrom non-abstract editor patterns to ibex patterns
			file.getPatterns().stream() //
				.filter(editorPattern -> !editorPattern.isAbstract())
				.forEach(editorPattern -> transformPattern(data.pattern2flattened.get(editorPattern)));
		});
		
		// transform attribute conditions and complex arithmetic expressions for each editor pattern (may require other ibex patterns)
		data.ibexContextPatterns.forEach((ibexPattern, editorPattern) -> {
			if(data.nameToPattern.get(editorPattern.getName()) instanceof IBeXContextPattern) {
				transformAttributeConditions(editorPattern, (IBeXContextPattern)ibexPattern);
				transformArithmeticConstraints(editorPattern, (IBeXContextPattern)ibexPattern);
			} else {
				IBeXContextAlternatives ibexAltPattern = (IBeXContextAlternatives) data.nameToPattern.get(editorPattern.getName());
				transformAttributeConditions(editorPattern, ibexAltPattern.getContext());
				transformArithmeticConstraints(editorPattern, ibexAltPattern.getContext());
				
				// do the same for all nested alternative patterns
				ibexAltPattern.getAlternativePatterns().forEach(pattern -> {
					transformAttributeConditions(editorPattern, pattern);
					transformArithmeticConstraints(editorPattern, pattern);
				});
			}
		});
		
		//transfrom the disjoint attribute constraints
		data.disjointPatternToTransformation.forEach((disjointPattern, transformation) -> {
			transformation.transformAttributeConstraint(disjointPattern);
		});
		
		while(!rulesToBeTransformed.isEmpty())
			transformToRule(rulesToBeTransformed.poll());
		
		// add arithmetic expressions and probability annotations to rules
		data.ibexRules.forEach((rule, editorPattern) -> {
			// fetch lhs
			IBeXContextPattern lhs = null;
			if(rule.getLhs() instanceof IBeXContextPattern) {
				lhs = (IBeXContextPattern) rule.getLhs();
			}else if (rule.getLhs() instanceof IBeXContextAlternatives) {
				lhs = ((IBeXContextAlternatives) rule.getLhs()).getContext();
			} else {
				lhs = ((IBeXDisjointContextPattern) rule.getLhs()).getNonOptimizedPattern();
			}
			
			rule.getArithmeticConstraints().addAll(lhs.getArithmeticConstraints());
			rule.setProbability(EditorToStochasticExtensionHelper
					.transformToIBeXProbability(data, lhs, editorPattern));
		});
		
		IBeXModel model = createSortedIBeXSets();
		data.clear();
		
		return model;
	}

	@Override
	public IBeXModel transform(final EditorGTFile file) {
		Objects.requireNonNull(file, "The editor file must not be null!");
		
		// flattend all editor patterns
		file.getPatterns().stream() //
			.forEach(editorPattern -> calcFlattenedPattern(editorPattern));
		// transfrom non-abstract editor patterns to ibex patterns
		file.getPatterns().stream() //
			.filter(editorPattern -> !editorPattern.isAbstract())
			.forEach(editorPattern -> transformPattern(data.pattern2flattened.get(editorPattern)));
		// transform attribute conditions and complex arithmetic expressions for each editor pattern (may require other ibex patterns)
		data.ibexContextPatterns.forEach((ibexPattern, editorPattern) -> {
			if(data.nameToPattern.get(editorPattern.getName()) instanceof IBeXContextPattern) {
				transformAttributeConditions(editorPattern, (IBeXContextPattern)ibexPattern);
				transformArithmeticConstraints(editorPattern, (IBeXContextPattern)ibexPattern);
			} else {
				IBeXContextAlternatives ibexAltPattern = (IBeXContextAlternatives) data.nameToPattern.get(editorPattern.getName());
				transformAttributeConditions(editorPattern, ibexAltPattern.getContext());
				transformArithmeticConstraints(editorPattern, ibexAltPattern.getContext());
				
				// do the same for all nested alternative patterns
				ibexAltPattern.getAlternativePatterns().forEach(pattern -> {
					transformAttributeConditions(editorPattern, pattern);
					transformArithmeticConstraints(editorPattern, pattern);
				});
			}
		});
		
		while(!rulesToBeTransformed.isEmpty())
			transformToRule(rulesToBeTransformed.poll());
		
		// add arithmetic expressions and probability annotations to rules
		data.ibexRules.forEach((rule, editorPattern) -> {
			// fetch lhs
			IBeXContextPattern lhs = null;
			if(rule.getLhs() instanceof IBeXContextPattern) {
				lhs = (IBeXContextPattern) rule.getLhs();
			}else if (rule.getLhs() instanceof IBeXContextAlternatives) {
				lhs = ((IBeXContextAlternatives) rule.getLhs()).getContext();
			} else {
				lhs = ((IBeXDisjointContextPattern) rule.getLhs()).getNonOptimizedPattern();
			}
			
			rule.getArithmeticConstraints().addAll(lhs.getArithmeticConstraints());
			rule.setProbability(EditorToStochasticExtensionHelper
					.transformToIBeXProbability(data, lhs, editorPattern));
		});
		
		IBeXModel model = createSortedIBeXSets();
		data.clear();
		
		return model;
	}

	/**
	 * Creates a pattern set with pattern lists sorted alphabetically.
	 * 
	 * @return the IBeXPatternSet
	 */
	private IBeXModel createSortedIBeXSets() {
		List<IBeXContext> sortPatterns = new LinkedList<>(data.ibexContextPatterns.keySet());
		
		//remove the context patterns of the disjoint patterns that are optimized
		data.nameToDisjointPattern.values().forEach(pattern -> sortPatterns.remove(pattern.getNonOptimizedPattern()));
		sortPatterns.addAll(data.nameToDisjointPattern.values());	
		sortPatterns.addAll(data.nameToDisjointPattern.values().stream().flatMap(pattern -> pattern.getSubpatterns().stream()).collect(Collectors.toList()));
		sortPatterns.sort(IBeXPatternUtils.sortByName);
			
		List<IBeXRule> sortRules = new LinkedList<>(data.ibexRules.keySet());
		sortRules.sort(IBeXPatternUtils.sortByName);
		
		data.ibexNodes.addAll(data.node2ibexNode.values().stream()
				.flatMap(map -> map.values().stream())
				.collect(Collectors.toList()));
		data.ibexNodes.sort(IBeXPatternUtils.sortByName);
		data.ibexEdges.addAll(data.reference2ibexEdge.values().stream()
				.flatMap(map -> map.values().stream())
				.collect(Collectors.toList()));
		data.ibexEdges.sort(IBeXPatternUtils.sortByName);
		
		IBeXModel ibexModel = IBeXPatternModelFactory.eINSTANCE.createIBeXModel();

		IBeXPatternSet ibexPatternSet = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternSet();
		ibexModel.setPatternSet(ibexPatternSet);
		ibexPatternSet.getContextPatterns().addAll(sortPatterns);
		
		IBeXRuleSet ibexRuleSet = IBeXPatternModelFactoryImpl.eINSTANCE.createIBeXRuleSet();
		ibexModel.setRuleSet(ibexRuleSet);
		ibexRuleSet.getRules().addAll(sortRules);
		
		IBeXNodeSet ibexNodeSet = IBeXPatternModelFactoryImpl.eINSTANCE.createIBeXNodeSet();
		ibexNodeSet.getNodes().addAll(data.ibexNodes);
		ibexModel.setNodeSet(ibexNodeSet);
		
		IBeXEdgeSet ibexEdgeSet = IBeXPatternModelFactoryImpl.eINSTANCE.createIBeXEdgeSet();
		ibexEdgeSet.getEdges().addAll(data.ibexEdges);
		ibexModel.setEdgeSet(ibexEdgeSet);
		
		return ibexModel;
	}

	/**
	 * Transforms the given pattern to a context, a create and a delete pattern.
	 * 
	 * @param editorPattern
	 *            the editor pattern to transform
	 * @param canBeDisjoint if the pattern can be a ibexDisjointContextPattern
	 */
	private void transformPattern(EditorPattern editorPattern) {
		Objects.requireNonNull(editorPattern, "The pattern must not be null!");

		if (data.nameToPattern.containsKey(editorPattern.getName())) {
			// Already transformed.
			return;
		}
		
		transformToContextPattern(editorPattern);
		if (editorPattern.getType() == EditorPatternType.RULE) {
//			transformToRule(editorPattern);
			rulesToBeTransformed.add(editorPattern);
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

		data.ibexContextPatterns.put(ibexPattern, editorPattern);
		if (ibexPattern instanceof IBeXContextAlternatives) {
			((IBeXContextAlternatives) ibexPattern).getAlternativePatterns().forEach(pattern -> data.ibexContextPatterns.remove(pattern));
		}
		data.nameToPattern.put(ibexPattern.getName(), ibexPattern);
	}

	/**
	 * Returns the IBeXContextPattern for the given editor pattern. If it does not
	 * exist, it is created.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @param canBeDisjoint if the pattern can be disjoint; only used for editorConditions
	 * @return the IBeXContextPattern
	 */

	public IBeXContext getOrCreateContextPattern(final EditorPattern editorPattern) {
		if (!data.nameToPattern.containsKey(editorPattern.getName())) {
			transformPattern(editorPattern);
		}
		return data.nameToPattern.get(editorPattern.getName());
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
			IBeXContextPattern ibexPattern = transformToContextPattern(editorPattern, editorPattern.getName(), null);
			if (editorPattern.getConditions().size() == 1) {
				EditorCondition editorCondition = editorPattern.getConditions().get(0);
				transformCondition(ibexPattern, editorCondition);
			}
			
			//copy the IBeXPattern to not destroy the non optimized pattern
			IBeXContextPattern possibleDisjointPattern = EcoreUtil.copy(ibexPattern);
			IBeXDisjointPatternFinder patternFinder = new IBeXDisjointPatternFinder(possibleDisjointPattern);
			//transform if the pattern is disjoint and the pattern has the disjoint flag (which says that the pattern can be optimized)
			if(editorPattern.isOptimize() && patternFinder.isDisjoint()) {
				try {
					IBeXDisjointPatternTransformation transformation = new IBeXDisjointPatternTransformation(possibleDisjointPattern, patternFinder.getSubgraphs());
					IBeXDisjointContextPattern disjointPattern = transformation.transformToContextPattern();
					disjointPattern.setNonOptimizedPattern(ibexPattern);
					//add them to the disjoint pattern set; the used context pattern will be removed at the end
					data.nameToDisjointPattern.put(disjointPattern.getName(), disjointPattern);
					data.disjointPatternToTransformation.put(disjointPattern, transformation);

				}
				catch(IllegalArgumentException e) {
					//when something goes wrong proceed normally
					// already transformed patterns do not need to be removed
					ibexPattern.setOptimizedDisjoint(false);
				}
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
		ibexAlternatives.setContext(transformToContextPattern(editorPattern, editorPattern.getName(), null));
		data.nameToPattern.remove(editorPattern.getName());
		data.node2ibexNode.remove(editorPattern.getName());
		data.ibexNodes.addAll(ibexAlternatives.getContext().getSignatureNodes());
		data.ibexNodes.addAll(ibexAlternatives.getContext().getLocalNodes());
		data.ibexContextPatterns.remove(ibexAlternatives.getContext());
		
		for (final EditorCondition editorCondition : editorPattern.getConditions()) {
			String name = editorPattern.getName() + "-ALTERNATIVE-" + editorCondition.getName();
			IBeXContextPattern ibexPattern = transformToContextPattern(editorPattern, name, null);
			transformCondition(ibexPattern, editorCondition);
			ibexAlternatives.getAlternativePatterns().add(ibexPattern);
			ibexAlternatives.getApiPatternDependencies().add(ibexPattern);
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
	private IBeXContextPattern transformToContextPattern(final EditorPattern editorPattern, final String name, final Map<IBeXNode, IBeXNode> nodeMap) {
		if (data.nameToPattern.containsKey(name)) {
			return (IBeXContextPattern) data.nameToPattern.get(name);
		}
		
		if(!data.node2ibexNode.containsKey(name)) {
			data.node2ibexNode.put(name, new HashMap<>());
		}
		
		if(!data.reference2ibexEdge.containsKey(name)) {
			data.reference2ibexEdge.put(name, new HashMap<>());
		}
		
		IBeXContextPattern ibexPattern = IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(name);
		//is the pattern optimized or not
		ibexPattern.setOptimizedDisjoint(editorPattern.isOptimize());
		ibexPattern.setSubpattern(false);
		addContextPattern(editorPattern, ibexPattern);
		
		// Add documentation
		try {
			ibexPattern.setDocumentation(GTCommentExtractor.getComment(editorPattern));
		} catch(Exception e) {
			ibexPattern.setDocumentation("");
		}
		
		// Transform parameters
		editorPattern.getParameters().forEach(param -> {
			IBeXParameter ibexParameter = IBeXPatternModelFactory.eINSTANCE.createIBeXParameter();
			ibexParameter.setName(param.getName());
			ibexParameter.setType(param.getType());
			ibexPattern.getParameters().add(ibexParameter);
		});
		
		// Transform nodes.
		List<EditorNode> editorNodes = GTEditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.CONTEXT,
				EditorOperator.DELETE);
		Set<String> signatureNodeNames = null;
		if(nodeMap != null) {
			signatureNodeNames = nodeMap.values().stream() //
					.map(n -> n.getName()) //
					.collect(Collectors.toSet());
		}
		
		for (final EditorNode editorNode : editorNodes) {
			IBeXNode ibexNode = data.node2ibexNode.get(name).get(editorNode);
			if(ibexNode == null) {
				Objects.requireNonNull(editorNode, "Node must not be null!");
				ibexNode = IBeXPatternFactory.createNode(editorNode.getName(), editorNode.getType());
				data.node2ibexNode.get(name).put(editorNode, ibexNode);
			}
			if(nodeMap == null) {
				if (editorNode.isLocal()) {
					ibexPattern.getLocalNodes().add(ibexNode);
				} else {
					ibexPattern.getSignatureNodes().add(ibexNode);
				}
			} else {
				if(!signatureNodeNames.contains(editorNode.getName())) {
					ibexPattern.getLocalNodes().add(ibexNode);
				} else {
					ibexPattern.getSignatureNodes().add(ibexNode);
				}
			}
						
			
		}

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(ibexPattern);

		// Transform edges.
		List<EditorReference> edges = GTEditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.CONTEXT,
				EditorOperator.DELETE);

		edges.stream()
			.filter(editorReference -> !data.reference2ibexEdge.get(name).containsKey(editorReference))
			.forEach(editorReference -> {
				IBeXEdge edge = transformEdge(editorPattern, editorReference, ibexPattern);
				ibexPattern.getLocalEdges().add(edge);
				data.reference2ibexEdge.get(name).put(editorReference, edge);
		});
		
		ibexPattern.getLocalEdges().addAll(edges.stream()
				.filter(editorReference -> data.reference2ibexEdge.get(name).containsKey(editorReference))
				.map(editorReference -> data.reference2ibexEdge.get(name).get(editorReference))
				.collect(Collectors.toList()));
		
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
		ibexEdge.setSourceNode(data.node2ibexNode.get(ibexPattern.getName()).get(GTEditorModelUtils.getSourceNode(editorReference)));
		ibexEdge.setTargetNode(data.node2ibexNode.get(ibexPattern.getName()).get(editorReference.getTarget()));
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
		IBeXContext contextPattern = getOrCreateContextPattern(editorPattern);
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
		Set<String> signatureNodeNames = nodeMap.values().stream() //
				.map(n -> n.getName()) //
				.collect(Collectors.toSet());
		String patternName = editorPattern.getName() + "-CONDITION-"
				+ signatureNodeNames.stream().collect(Collectors.joining("_"));
		return  transformToContextPattern(editorPattern, patternName, nodeMap);
		
	}
	
	/**
	 * Transforms each attribute condition of the editor node to an
	 * {@link IBeXAttributeConstraint} and adds them to the given context pattern.
	 * 
	 * @param ibexContextPattern
	 *            the context pattern
	 */
	public void transformAttributeConditions(final EditorPattern editorPattern, final IBeXContextPattern ibexContextPattern) {
		Objects.requireNonNull(ibexContextPattern, "ibexContextPattern must not be null!");

		for (final EditorAttributeConstraint editorAttribute : editorPattern.getAttributeConstraints()) {
			transformAttributeCondition(editorPattern, editorAttribute, ibexContextPattern);
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
	private void transformAttributeCondition(final EditorPattern editorPattern, final EditorAttributeConstraint editorAttribute, 
			final IBeXContextPattern ibexContextPattern) {
		if(!(isSimpleAttributeValue(editorAttribute.getLhs()) && isSimpleAttributeValue(editorAttribute.getRhs()))) {
			return;
		}
		IBeXAttributeConstraint ibexAttrConstraint = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeConstraint();
		IBeXRelation ibexRelation = EditorToIBeXPatternHelper.convertRelation(editorAttribute.getRelation());
		ibexAttrConstraint.setRelation(ibexRelation);
		convertAttributeValue(editorPattern, editorAttribute.getLhs(), ibexContextPattern).ifPresent(v -> ibexAttrConstraint.setLhs(v));
		convertAttributeValue(editorPattern, editorAttribute.getRhs(), ibexContextPattern).ifPresent(v -> ibexAttrConstraint.setRhs(v));
		ibexContextPattern.getAttributeConstraint().add(ibexAttrConstraint);
	}
	
	public boolean isSimpleAttributeValue(EditorExpression value) {
		if(value instanceof EditorAttributeExpression)
			return true;
		else if(value instanceof EditorEnumExpression)
			return true;
		else if(value instanceof EditorLiteralExpression)
			return true;
		else if(value instanceof EditorParameterExpression)
			return true;
		else if(value instanceof ArithmeticCalculationExpression) {
			ArithmeticCalculationExpression ace = (ArithmeticCalculationExpression)value;
			if(ace.getExpression() instanceof EditorAttributeExpression) {
				return true;
			} else if(ace.getExpression() instanceof EditorLiteralExpression) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
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
	private Optional<IBeXAttributeValue> convertAttributeValue(final EditorPattern editorPattern, final EditorExpression value,
			final IBeXPattern ibexPattern) {
		if (value instanceof EditorAttributeExpression) {
			return convertAttributeValue(editorPattern, (EditorAttributeExpression) value, ibexPattern);
		} else if (value instanceof EditorEnumExpression) {
			return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue((EditorEnumExpression) value));
		} else if (value instanceof EditorLiteralExpression) {
			return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue((EditorLiteralExpression) value));
		} else if (value instanceof EditorParameterExpression) {
			return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue((EditorParameterExpression) value));
		} else if(value instanceof StochasticFunctionExpression) {
			return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue(data, ibexPattern, (StochasticFunctionExpression) value));
		} else if(value instanceof ArithmeticCalculationExpression) {
			ArithmeticCalculationExpression ace = (ArithmeticCalculationExpression)value;
			if(ace.getExpression() instanceof EditorAttributeExpression) {
				return convertAttributeValue(editorPattern, (EditorAttributeExpression) ace.getExpression(), ibexPattern);
			} else if(ace.getExpression() instanceof EditorLiteralExpression) {
				return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue((EditorLiteralExpression) ace.getExpression()));
			} else if(ace.getExpression() instanceof EditorIteratorAttributeExpression) {
				return convertIteratorAttributeValue(editorPattern, (EditorIteratorAttributeExpression) ace.getExpression(), ibexPattern);
			} else {
				return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue(data, ibexPattern, (ArithmeticCalculationExpression) value));
			}
		}
		else {
			logError("Invalid attribute value: %s", value);
			return Optional.empty();
		}
	}
	
	private Optional<IBeXArithmeticExpression> convertAttributeValue(final IBeXAttributeValue value) {
		if (value instanceof IBeXConstant) {
			IBeXConstant constant = (IBeXConstant)value;
			IBeXArithmeticValueLiteral literal = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticValueLiteral();
			try {
				literal.setValue(Double.parseDouble(constant.getStringValue()));
			} catch(Exception e) {
				logError("Invalid attribute value: %s", value);
				return Optional.empty();
			}
			return Optional.of(literal);
		} else if (value instanceof IBeXAttributeExpression) {
			IBeXAttributeExpression attributeExpression = (IBeXAttributeExpression)value;
			IBeXArithmeticAttribute out = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticAttribute();
			out.setType(attributeExpression.getNode().getType());
			out.setName(attributeExpression.getNode().getName());
			out.setAttribute(attributeExpression.getAttribute());
			return Optional.of(out);
		} else {
			logError("Invalid attribute value: %s", value);
			return Optional.empty();
		}
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
	private Optional<IBeXAttributeValue> convertAttributeValue(final EditorPattern editorPattern, final EditorAttributeAssignment editorAttribute,
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
			return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue(data, ibexPattern, (StochasticFunctionExpression) value));
		} else if(value instanceof ArithmeticCalculationExpression) {
			ArithmeticCalculationExpression ace = (ArithmeticCalculationExpression)value;
			if(ace.getExpression() instanceof EditorAttributeExpression) {
				return convertAttributeValue(editorPattern, (EditorAttributeExpression) ace.getExpression(), ibexPattern);
			} else if(ace.getExpression() instanceof EditorLiteralExpression) {
				return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue((EditorLiteralExpression) ace.getExpression()));
			} else {
				return Optional.of(EditorToIBeXPatternHelper.convertAttributeValue(data, ibexPattern, (ArithmeticCalculationExpression) value));
			}
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
		Optional<IBeXNode> ibexExistingNode = Optional.of(data.node2ibexNode.get(ibexPattern.getName()).get(editorExpression.getNode()));

		if (!ibexExistingNode.isPresent() && ibexPattern instanceof IBeXCreatePattern) {
			Objects.requireNonNull(editorExpression.getNode(), "Node must not be null!");
			IBeXNode ibexNode = IBeXPatternFactory.createNode(editorExpression.getNode().getName(), editorExpression.getNode().getType());
			data.node2ibexNode.get(ibexPattern.getName()).put(editorExpression.getNode(), ibexNode);
			((IBeXCreatePattern) ibexPattern).getContextNodes().add(ibexNode);
			ibexExistingNode = Optional.of(ibexNode);
		}

		return ibexExistingNode.map(ibexNode -> {
			ibexAttributeExpression.setNode(ibexNode);
			return ibexAttributeExpression;
		});
	}
	
	private Optional<IBeXAttributeValue> convertIteratorAttributeValue(final EditorPattern editorPattern, final EditorIteratorAttributeExpression editorExpression,
			final IBeXPattern ibexPattern) {
		IBeXAttributeExpression ibexAttributeExpression = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeExpression();
		ibexAttributeExpression.setAttribute(editorExpression.getAttribute());
		Optional<IBeXNode> ibexExistingNode = Optional.of(data.iterator2ibexNode.get(ibexPattern.getName()).get(editorExpression.getIterator()));

		if (!ibexExistingNode.isPresent() && ibexPattern instanceof IBeXCreatePattern) {
			IBeXNode ibexNode = null;
			if(editorExpression.getIterator().getSubType() != null) {
				ibexNode = IBeXPatternFactory.createNode(editorExpression.getIterator().getName(), editorExpression.getIterator().getSubType());
			} else {
				ibexNode = IBeXPatternFactory.createNode(editorExpression.getIterator().getName(), (EClass)editorExpression.getIterator().getType().getEType());
			}
			data.iterator2ibexNode.get(ibexPattern.getName()).put(editorExpression.getIterator(), ibexNode);
			((IBeXCreatePattern) ibexPattern).getContextNodes().add(ibexNode);
			ibexExistingNode = Optional.of(ibexNode);
		}

		return ibexExistingNode.map(ibexNode -> {
			ibexAttributeExpression.setNode(ibexNode);
			return ibexAttributeExpression;
		});
	}
	
	/**
	 * Transforms the arithmetic expressions to pattern constraints; only used on context patterns
	 * @param editorPattern
	 * @param ibexPattern
	 */
	private void transformArithmeticConstraints(final EditorPattern editorPattern, final IBeXContextPattern ibexPattern) {
		for(final EditorAttributeConstraint attribute: editorPattern.getAttributeConstraints()) {
			if(attribute.getLhs() instanceof ArithmeticCalculationExpression && attribute.getRhs() instanceof ArithmeticCalculationExpression) {
				ArithmeticCalculationExpression lhs = (ArithmeticCalculationExpression)attribute.getLhs();
				ArithmeticCalculationExpression rhs = (ArithmeticCalculationExpression)attribute.getRhs();
				if(lhs.getExpression() instanceof EditorLiteralExpression && rhs.getExpression() instanceof EditorLiteralExpression) {
					continue;
				} else if(lhs.getExpression() instanceof EditorLiteralExpression && rhs.getExpression() instanceof EditorAttributeExpression) {
					continue;
				} else if(lhs.getExpression() instanceof EditorAttributeExpression && rhs.getExpression() instanceof EditorLiteralExpression) {
					continue;
				} else if(lhs.getExpression() instanceof EditorAttributeExpression && rhs.getExpression() instanceof EditorAttributeExpression) {
					continue;
				}
				
				IBeXArithmeticConstraint constraint = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticConstraint();
				constraint.setLhs(EditorToArithmeticExtensionHelper
						.transformToIBeXArithmeticExpression(data, ibexPattern, ((ArithmeticCalculationExpression) attribute.getLhs()).getExpression()));
				constraint.setRelation(EditorToIBeXPatternHelper.convertRelation(attribute.getRelation()));
				constraint.setRhs(EditorToArithmeticExtensionHelper
						.transformToIBeXArithmeticExpression(data, ibexPattern, ((ArithmeticCalculationExpression) attribute.getRhs()).getExpression()));
				ibexPattern.getArithmeticConstraints().add(constraint);
			} else if(attribute.getLhs() instanceof ArithmeticCalculationExpression && !(attribute.getRhs() instanceof ArithmeticCalculationExpression)) {
				ArithmeticCalculationExpression lhs = (ArithmeticCalculationExpression)attribute.getLhs();
				if(lhs.getExpression() instanceof EditorLiteralExpression) {
					continue;
				} else if(lhs.getExpression() instanceof EditorAttributeExpression) {
					continue;
				} 
				
				IBeXArithmeticConstraint constraint = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticConstraint();
				constraint.setLhs(EditorToArithmeticExtensionHelper
						.transformToIBeXArithmeticExpression(data, ibexPattern, ((ArithmeticCalculationExpression) attribute.getLhs()).getExpression()));
				constraint.setRelation(EditorToIBeXPatternHelper.convertRelation(attribute.getRelation()));
				convertAttributeValue(editorPattern, attribute.getRhs(), ibexPattern).ifPresent(val -> convertAttributeValue(val).ifPresent(val2 -> constraint.setRhs(val2)));
				ibexPattern.getArithmeticConstraints().add(constraint);
			} else if(!(attribute.getLhs() instanceof ArithmeticCalculationExpression) && attribute.getRhs() instanceof ArithmeticCalculationExpression) {
				ArithmeticCalculationExpression rhs = (ArithmeticCalculationExpression)attribute.getRhs();
				if(rhs.getExpression() instanceof EditorLiteralExpression) {
					continue;
				} else if(rhs.getExpression() instanceof EditorAttributeExpression) {
					continue;
				}
				
				IBeXArithmeticConstraint constraint = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticConstraint();
				convertAttributeValue(editorPattern, attribute.getLhs(), ibexPattern).ifPresent(val -> convertAttributeValue(val).ifPresent(val2 -> constraint.setLhs(val2)));
				constraint.setRelation(EditorToIBeXPatternHelper.convertRelation(attribute.getRelation()));
				constraint.setRhs(EditorToArithmeticExtensionHelper
						.transformToIBeXArithmeticExpression(data, ibexPattern, ((ArithmeticCalculationExpression) attribute.getRhs()).getExpression()));
				ibexPattern.getArithmeticConstraints().add(constraint);
			}
		}
	}
	
	private void transformToRule(final EditorPattern editorPattern) {
		IBeXCreatePattern ibexCreatePattern = transformToCreatePattern(editorPattern);
		IBeXDeletePattern ibexDeletePattern = transformToDeletePattern(editorPattern);
		List<IBeXForEachExpression> forEachExpressions = new LinkedList<>();
		for(EditorReferenceIterator iterator : editorPattern.getNodes().stream()
				.flatMap(node -> node.getIterators().stream())
				.collect(Collectors.toList()) ) {
			forEachExpressions.add(transformToForEachExpression(editorPattern, iterator));
		}
		
		IBeXRule ibexRule = IBeXPatternModelFactory.eINSTANCE.createIBeXRule();
		ibexRule.setName(editorPattern.getName());
		if(data.nameToPattern.containsKey(editorPattern.getName())) {
			ibexRule.setLhs(data.nameToPattern.get(editorPattern.getName()));
		}
		
		if(ibexCreatePattern != null) {
			ibexRule.setCreate(ibexCreatePattern);
		}
		
		if(ibexDeletePattern != null) {
			ibexRule.setDelete(ibexDeletePattern);
		}
		
		ibexRule.getForEach().addAll(forEachExpressions);
		
		// fetch lhs
		IBeXContextPattern lhs = null;
		if(ibexRule.getLhs() instanceof IBeXContextPattern) {
			lhs = (IBeXContextPattern) ibexRule.getLhs();
		} else {
			lhs = ((IBeXContextAlternatives) ibexRule.getLhs()).getContext();
		}
		
		ibexRule.getParameters().addAll(lhs.getParameters());
		ibexRule.setDocumentation(lhs.getDocumentation());
		
		// create RHS from LHS, create and delete patterns
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
		
		data.ibexRules.put(ibexRule, editorPattern);
	}

	private IBeXForEachExpression transformToForEachExpression(EditorPattern editorPattern, EditorReferenceIterator iterator) {
		IBeXForEachExpression forEachExpr = IBeXPatternModelFactory.eINSTANCE.createIBeXForEachExpression();
		forEachExpr.setSource(data.node2ibexNode.get(editorPattern.getName()).get(iterator.eContainer()));
		
		IBeXNode trgIterator = null;
		if(iterator.getSubType() != null) {
			trgIterator = IBeXPatternFactory.createNode(iterator.getName(), iterator.getSubType());
		} else {
			trgIterator = IBeXPatternFactory.createNode(iterator.getName(), (EClass)iterator.getType().getEType());
		}
		forEachExpr.setTrgIterator(trgIterator);
		Map<EditorReferenceIterator, IBeXNode> itr2node = data.iterator2ibexNode.get(editorPattern.getName());
		if(itr2node == null) {
			itr2node = new HashMap<>();
			data.iterator2ibexNode.put(editorPattern.getName(), itr2node);
		}
		itr2node.put(iterator, trgIterator);
		
		IBeXEdge edge = IBeXPatternFactory.createEdge(forEachExpr.getSource(), trgIterator, iterator.getType());
		forEachExpr.setEdge(edge);
		
		IBeXCreatePattern ibexCreatePattern = IBeXPatternModelFactory.eINSTANCE.createIBeXCreatePattern();
		ibexCreatePattern.setName(editorPattern.getName()+"_ForEachCreate("+edge.getName()+")");
		
		IBeXDeletePattern ibexDeletePattern = IBeXPatternModelFactory.eINSTANCE.createIBeXDeletePattern();
		ibexDeletePattern.setName(editorPattern.getName()+"_ForEachDelete("+edge.getName()+")");
		
		for(EditorIteratorReference refItr : iterator.getReferences()) {
			if(refItr.getOperator() == EditorOperator.CREATE) {
				ibexCreatePattern.getContextNodes().add(data.node2ibexNode.get(editorPattern.getName()).get(refItr.getSource()));
				ibexCreatePattern.getContextNodes().add(trgIterator);
				IBeXEdge createdEdge = IBeXPatternFactory.createEdge(data.node2ibexNode.get(editorPattern.getName()).get(refItr.getSource()), trgIterator, refItr.getType());
				ibexCreatePattern.getCreatedEdges().add(createdEdge);
				forEachExpr.getCreatedEdges().add(createdEdge);
			} else {
				ibexDeletePattern.getContextNodes().add(data.node2ibexNode.get(editorPattern.getName()).get(refItr.getSource()));
				ibexDeletePattern.getContextNodes().add(trgIterator);
				IBeXEdge deletedEdge = IBeXPatternFactory.createEdge(data.node2ibexNode.get(editorPattern.getName()).get(refItr.getSource()), trgIterator, refItr.getType());
				ibexDeletePattern.getDeletedEdges().add(deletedEdge);
				forEachExpr.getDeletedEdges().add(deletedEdge);
			}
		}
		
		for(EditorIteratorAttributeAssignment atrItr : iterator.getIteratorAttributes()) {
			if(atrItr instanceof EditorIteratorAttributeAssignmentItr) {
				EditorIteratorAttributeAssignmentItr toIterator = (EditorIteratorAttributeAssignmentItr)atrItr;
				IBeXAttributeAssignment ibexAssignment = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeAssignment();
				ibexAssignment.setNode(trgIterator);
				ibexAssignment.setType(toIterator.getIteratorAttribute().getAttribute());
				convertAttributeValue(editorPattern, toIterator.getValue(), ibexCreatePattern).ifPresent(v -> ibexAssignment.setValue(v));
				ibexCreatePattern.getAttributeAssignments().add(ibexAssignment);
			} else {
				EditorIteratorAttributeAssignmentNode toNode = (EditorIteratorAttributeAssignmentNode)atrItr;
				IBeXAttributeAssignment ibexAssignment = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeAssignment();
				ibexAssignment.setNode(data.node2ibexNode.get(editorPattern.getName()).get(toNode.getNodeAttribute().getNode()));
				ibexAssignment.setType(toNode.getNodeAttribute().getAttribute());
				convertAttributeValue(editorPattern, toNode.getValue(), ibexCreatePattern).ifPresent(v -> ibexAssignment.setValue(v));
				ibexCreatePattern.getAttributeAssignments().add(ibexAssignment);
			}
		}
		
		if(!ibexCreatePattern.getCreatedEdges().isEmpty() || !ibexCreatePattern.getAttributeAssignments().isEmpty()) {
			forEachExpr.setCreate(ibexCreatePattern);
		}
		if(!ibexDeletePattern.getDeletedEdges().isEmpty()) {
			forEachExpr.setDelete(ibexDeletePattern);
		}
		
		return forEachExpr;
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
			IBeXNode ibexNode = data.node2ibexNode.get(editorPattern.getName()).get(editorNode);
			if(ibexNode == null) {
				Objects.requireNonNull(editorNode, "Node must not be null!");
				ibexNode = IBeXPatternFactory.createNode(editorNode.getName(), editorNode.getType());
				data.node2ibexNode.get(editorPattern.getName()).put(editorNode, ibexNode);
			}
			ibexCreatePattern.getCreatedNodes().add(ibexNode);
		});
		
		List<IBeXNode> context = new LinkedList<>();
		GTEditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.CREATE).forEach(editorReference -> {
			IBeXEdge ibexEdge = data.reference2ibexEdge.get(editorPattern.getName()).get(editorReference);
			if(ibexEdge == null) {
				EditorNode editorSourceNode = GTEditorModelUtils.getSourceNode(editorReference);
				EditorNode editorTargetNode = editorReference.getTarget();
				
				IBeXNode srcNode = data.node2ibexNode.get(editorPattern.getName()).get(editorSourceNode);
				IBeXNode trgNode = data.node2ibexNode.get(editorPattern.getName()).get(editorTargetNode);
				
				if(srcNode == null) {
					Objects.requireNonNull(editorSourceNode, "Node must not be null!");
					srcNode = IBeXPatternFactory.createNode(editorSourceNode.getName(), editorSourceNode.getType());
					data.node2ibexNode.get(editorPattern.getName()).put(editorSourceNode, srcNode);
					context.add(srcNode);
				}
				if(trgNode == null) {
					Objects.requireNonNull(editorTargetNode, "Node must not be null!");
					trgNode = IBeXPatternFactory.createNode(editorTargetNode.getName(), editorTargetNode.getType());
					data.node2ibexNode.get(editorPattern.getName()).put(editorTargetNode, trgNode);
					context.add(trgNode);
				}
				
				ibexEdge = IBeXPatternFactory.createEdge(srcNode, trgNode, editorReference.getType());
				ibexEdge.setName(ibexEdge.getSourceNode().getName()+"->"+ibexEdge.getTargetNode().getName());
				data.reference2ibexEdge.get(editorPattern.getName()).put(editorReference, ibexEdge);
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

		data.ibexCreatePatterns.add(ibexCreatePattern);
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
			IBeXNode ibexNode = data.node2ibexNode.get(editorPattern.getName()).get(editorNode);
			if(ibexNode == null) {
				Objects.requireNonNull(editorNode, "Node must not be null!");
				ibexNode = IBeXPatternFactory.createNode(editorNode.getName(), editorNode.getType());
				data.node2ibexNode.get(editorPattern.getName()).put(editorNode, ibexNode);
			}
			ibexDeletePattern.getDeletedNodes().add(ibexNode);
		});
		
		List<IBeXNode> context = new LinkedList<>();
		GTEditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.DELETE).forEach(editorReference -> {
			IBeXEdge ibexEdge = data.reference2ibexEdge.get(editorPattern.getName()).get(editorReference);
			if(ibexEdge == null) {
				EditorNode editorSourceNode = GTEditorModelUtils.getSourceNode(editorReference);
				EditorNode editorTargetNode = editorReference.getTarget();
				
				IBeXNode srcNode = data.node2ibexNode.get(editorPattern.getName()).get(editorSourceNode);
				IBeXNode trgNode = data.node2ibexNode.get(editorPattern.getName()).get(editorTargetNode);
				
				if(srcNode == null) {
					Objects.requireNonNull(editorSourceNode, "Node must not be null!");
					srcNode = IBeXPatternFactory.createNode(editorSourceNode.getName(), editorSourceNode.getType());
					data.node2ibexNode.get(editorPattern.getName()).put(editorSourceNode, srcNode);
					context.add(srcNode);
				}
				if(trgNode == null) {
					Objects.requireNonNull(editorTargetNode, "Node must not be null!");
					trgNode = IBeXPatternFactory.createNode(editorTargetNode.getName(), editorTargetNode.getType());
					data.node2ibexNode.get(editorPattern.getName()).put(editorTargetNode, trgNode);
					context.add(trgNode);
				}
				
				ibexEdge = IBeXPatternFactory.createEdge(srcNode, trgNode, editorReference.getType());
				ibexEdge.setName(ibexEdge.getSourceNode().getName()+"->"+ibexEdge.getTargetNode().getName());
				data.reference2ibexEdge.get(editorPattern.getName()).put(editorReference, ibexEdge);
			} 
			ibexDeletePattern.getDeletedEdges().add(ibexEdge);
		});
		context.sort(IBeXPatternUtils.sortByName);
		ibexDeletePattern.getContextNodes().addAll(context);

		data.ibexDeletePatterns.add(ibexDeletePattern);
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
		List<EditorAttributeAssignment> attributeAssignments = editorNode.getAttributes();
		if (attributeAssignments.size() == 0) {
			return;
		}

		IBeXNode ibexNode = data.node2ibexNode.get(editorPattern.getName()).get(editorNode);
		if(ibexNode == null) {
			Objects.requireNonNull(editorNode, "Node must not be null!");
			ibexNode = IBeXPatternFactory.createNode(editorNode.getName(), editorNode.getType());
			data.node2ibexNode.get(editorPattern.getName()).put(editorNode, ibexNode);
			ibexCreatePattern.getContextNodes().add(ibexNode);
		}
		for (EditorAttributeAssignment editorAttribute : attributeAssignments) {
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
	private void transformAttributeAssignment(final EditorPattern editorPattern, final EditorAttributeAssignment editorAttribute, final IBeXNode ibexNode,
			final IBeXCreatePattern ibexCreatePattern) {
		IBeXAttributeAssignment ibexAssignment = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeAssignment();
		ibexAssignment.setNode(ibexNode);
		ibexAssignment.setType(editorAttribute.getAttribute());
		convertAttributeValue(editorPattern, editorAttribute, ibexCreatePattern).ifPresent(v -> ibexAssignment.setValue(v));
		ibexCreatePattern.getAttributeAssignments().add(ibexAssignment);
	}

}
