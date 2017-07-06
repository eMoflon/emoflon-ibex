package org.emoflon.ibex.tgg.operational.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.basic.expressions.TGGAttributeExpression;
import language.basic.expressions.TGGEnumExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;
import runtime.RuntimePackage;

/**
 * @author leblebici Util class for creating EObjects, Edges, and
 *         Correspondences for a given set of green TGGRuleElement
 */
public class ManipulationUtil {

	private static ManipulationUtil instance;

	private ManipulationUtil() {
		this.manipulatedCorrCreations = new HashMap<>();
		this.nodeCreations = new HashMap<>();
		this.edgeCreations = new HashMap<>();
		getRuntimePackageSingletonInstance();
	}

	public static ManipulationUtil getInstance() {
		if (instance == null)
			instance = new ManipulationUtil();
		return instance;
	}

	private RuntimePackage getRuntimePackageSingletonInstance(){
		return RuntimePackage.eINSTANCE;
	}
	// TODO is there a reason why this attribute is not used?
//	@SuppressWarnings("unused")
//	private RuntimePackage runtimePackage = RuntimePackage.eINSTANCE;

	private Map<String, Function<TGGRuleNode, EObject>> nodeCreations;
	
	private Map<String, Function<EObject, Function<EObject, Consumer<EReference>>>> edgeCreations;

	/**
	 * This will change the creation of nodes
	 * 
	 * @param fun
	 *            the function which is changing the Creation
	 */
	public void addNodeCreationFun(Function<TGGRuleNode, EObject> fun, String pluginID) {
		nodeCreations.put(pluginID,fun);
	}

	/**
	 * This will change the creation of edges
	 * 
	 * @param fun
	 *            the function which is changing the Creation
	 */
	public void addEdgeCreationFun(Function<EObject, Function<EObject, Consumer<EReference>>> fun, String pluginID) {
		this.edgeCreations.put(pluginID, fun);
	}

	/**
	 * This will change the creation of nodes
	 * 
	 * @param fun
	 *            the function which is changing the Creation
	 */
	public void addCorrCreationFun(Function<TGGRuleNode, Function<EObject, Function<EObject, Function<Resource, EObject>>>> fun, String pluginID) {
		manipulatedCorrCreations.put(pluginID, fun);
	}

	public EObject defaultCreateNode(TGGRuleNode node){
		return EcoreUtil.create(node.getType());
	}
	
	/**
	 * Container for all CorrCreationFunctions which are changed for a specific plug-in
	 */
	private Map<String, Function<TGGRuleNode, Function<EObject, Function<EObject, Function<Resource, EObject>>>>> manipulatedCorrCreations;

	/**
	 * This is the creation of a node, if a new creation function is set and
	 * fails it uses the default creation function
	 * 
	 * @param node
	 *            the Type of the Node
	 * @return a new Node
	 */
	private EObject createNodeByTGGRuleNode(TGGRuleNode node, boolean isManipulated, String pluginID) {
		Function<TGGRuleNode, EObject> nodeCreationFun = nodeCreations.get(pluginID);
		
		if(isManipulated && nodeCreationFun != null){
			return nodeCreationFun.apply(node);
		}else{
			return defaultCreateNode(node);
		}
	}

	/**
	 * Returns the default edge creation function
	 * 
	 * @return the default edge creation function
	 */
	public Function<EObject, Function<EObject, Consumer<EReference>>> getDefaultEdgeCreationFun() {
		return src -> trg -> ref -> {
			defaultCreateEdge(src, trg, ref);
		};
	}

	// changed from for each construct to collection.stream() construct for
	// consistency
	public void createNonCorrNodes(IPatternMatch match, HashMap<String, EObject> comatch,
			Collection<TGGRuleNode> greenNodes, Resource nodeResource, boolean isManipulated, String pluginID) {
		greenNodes.stream().forEach(n -> {
			comatch.put(n.getName(), createNode(match, n, nodeResource, isManipulated, pluginID));
		});
	}

	public Collection<RuntimeEdge> createEdges(IPatternMatch match, HashMap<String, EObject> comatch,
			Collection<TGGRuleEdge> greenEdges, boolean createEMFEdgesAsWell, boolean isManipulated, String pluginID) {
		return greenEdges.stream().map(e -> {
			return mapToRuntimeEdge(e, match, comatch, createEMFEdgesAsWell, isManipulated, pluginID);
		}).collect(Collectors.toList());
	}

	private RuntimeEdge mapToRuntimeEdge(TGGRuleEdge edge, IPatternMatch match, HashMap<String, EObject> comatch,
			boolean createEMFEdgesAsWell, boolean isManipulated, String pluginID) {
		EObject src = getVariableByName(edge.getSrcNode().getName(), comatch, match);
		EObject trg = getVariableByName(edge.getTrgNode().getName(), comatch, match);
		if (createEMFEdgesAsWell)
			createEMFEdge(edge, src, trg, isManipulated, pluginID);
		return new RuntimeEdge(src, trg, edge.getType());
	}

	// changed from for each construct to collection.stream() construct for
	// consistency
	public void createCorrs(IPatternMatch match, HashMap<String, EObject> comatch, Collection<TGGRuleCorr> greenCorrs,
			Resource corrR, boolean isManipulated, String pluginID) {
		greenCorrs.stream().forEach(c -> {
			comatch.put(c.getName(),
					applyCorrCreation(c, getVariableByName(c.getSource().getName(), comatch, match), 
							getVariableByName(c.getTarget().getName(), comatch, match), corrR, isManipulated, pluginID));
		});
	}
	
	private EObject applyCorrCreation(TGGRuleNode node, EObject src, EObject trg, Resource corrR, boolean isManipulated, String pluginID){
		Function<TGGRuleNode, Function<EObject, Function<EObject, Function<Resource, EObject>>>> manipultedCorrCreationFun = manipulatedCorrCreations.get(pluginID);
		if(isManipulated && manipultedCorrCreationFun != null){
			return manipultedCorrCreationFun.apply(node).apply(src).apply(trg).apply(corrR);
		}
			
		return defaultCreateCorr(node, src, trg, corrR);
	}

	public void deleteNodes(Collection<EObject> elements) {
		elements.stream().forEach(EcoreUtil::delete);
	}

	public EObject getVariableByName(String name, HashMap<String, EObject> comatch, IPatternMatch match) {
		if (comatch.containsKey(name))
			return comatch.get(name);
		return (EObject) match.get(name);
	}

	// changed to edgeCreationFun
	private void createEMFEdge(TGGRuleEdge e, EObject src, EObject trg, boolean isManipulated, String pluginID) {
		EReference ref = e.getType();
		Function<EObject, Function<EObject, Consumer<EReference>>> edgeCreationFun = edgeCreations.get(pluginID);
		if(isManipulated && edgeCreationFun!=null){
			edgeCreationFun.apply(src).apply(trg).accept(ref);
		}else{
			defaultCreateEdge(src, trg, ref);
		}
	}

	/**
	 * The default Edge creation
	 * 
	 * @param src
	 *            source Object
	 * @param trg
	 *            target Object
	 * @param ref
	 *            the reference which connects source and target
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void defaultCreateEdge(EObject src, EObject trg, EReference ref) {
		if (ref.isMany())
			((EList) src.eGet(ref)).add(trg);
		else
			src.eSet(ref, trg);
		if (ref.isContainment() && trg.eResource() != null) {
			trg.eResource().getContents().remove(trg);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public void deleteEdge(EObject src, EObject trg, EReference ref) {
		if (ref.isMany())
			((EList) src.eGet(ref)).remove(trg);
		else
			src.eUnset(ref);
	}

	// changed from for each construct to collection.stream() construct for
	// consistency and changed Node creation
	private EObject createNode(IPatternMatch match, TGGRuleNode node, Resource resource, boolean isManipulated, String pluginID) {
		EObject newObj = createNodeByTGGRuleNode(node, isManipulated, pluginID);

		node.getAttrExpr().stream().filter(attrExpr -> findCorrectAttributeAssignment(attrExpr, newObj))
				.forEach(attrExpr -> {
					applyInplaceAttributeAssignments(attrExpr, newObj, match);
				});
		resource.getContents().add(newObj);
		return newObj;
	}
	
	private EObject defaultCreateNode(IPatternMatch match, TGGRuleNode node, Resource resource){
		return this.createNode(match, node, resource, false, "");
	}

	private boolean findCorrectAttributeAssignment(TGGInplaceAttributeExpression attrExpr, EObject eObject) {
		TGGAttributeConstraintOperators equalOP = TGGAttributeConstraintOperators.EQUAL;
		EAttribute eAttribute = attrExpr.getAttribute();
		EClass type = eObject.eClass();
		return attrExpr.getOperator().equals(equalOP) && type.getEAllAttributes().contains(eAttribute);
	}

	private void applyInplaceAttributeAssignments(TGGInplaceAttributeExpression attrExpr, EObject eObject,
			IPatternMatch match) {
		if (attrExpr.getValueExpr() instanceof TGGLiteralExpression) {
			TGGLiteralExpression tle = (TGGLiteralExpression) attrExpr.getValueExpr();
			eObject.eSet(attrExpr.getAttribute(),
					String2EPrimitive.convertString(attrExpr.getAttribute().getEType(), tle.getValue()));
		} else if (attrExpr.getValueExpr() instanceof TGGEnumExpression) {
			TGGEnumExpression tee = TGGEnumExpression.class.cast(attrExpr.getValueExpr());
			applyTGGEnumExpression(tee, tee.getEenum(), tee.getLiteral(), eObject, attrExpr.getAttribute());

		} else if (attrExpr.getValueExpr() instanceof TGGAttributeExpression) {
			TGGAttributeExpression tae = (TGGAttributeExpression) attrExpr.getValueExpr();
			EObject obj = (EObject) match.get(tae.getObjectVar().getName());
			eObject.eSet(attrExpr.getAttribute(), obj.eGet(tae.getAttribute()));
		}
	}

	private void applyTGGEnumExpression(TGGEnumExpression tee, EEnum eEnum, EEnumLiteral literal, EObject eObject,
			EAttribute eAttribute) {
		Enumerator enumerator = literal.getInstance();
		eObject.eSet(eAttribute, enumerator);
	}

	public EObject defaultCreateCorr(TGGRuleNode node, EObject src, EObject trg, Resource corrR) {
		EObject corr = defaultCreateNode(null, node, corrR);
		corr.eSet(corr.eClass().getEStructuralFeature("source"), src);
		corr.eSet(corr.eClass().getEStructuralFeature("target"), trg);
		return corr;
	}
}
