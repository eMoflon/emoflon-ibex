package org.emoflon.ibex.tgg.operational.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
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

	//TODO is there a reason why this attribute is not used?
	private static RuntimePackage runtimePackage = RuntimePackage.eINSTANCE;
	
	/**
	 * this is the function which will create the Nodes of a TGG, if you want another node creation, this function must be changed
	 */
	private static Function<TGGRuleNode, EObject> nodeCreationFun = getDefaultNodeCreationFun();

	/**
	 * this is the function which will create the Edges of a TGG, if you want another edge creation, this function must be changed
	 */
	private static Function<EObject, Function<EObject, Consumer<EReference>>> edgeCreationFun = getDefaultEdgeCreationFun();
	
	/**
	 * this is the function which will crate the Correspondence of a TGG, if you want another correspondence creation, this function must be changed
	 */
	private static Function<TGGRuleNode, Function<EObject, Function<EObject, Function<Resource, EObject>>>> corrCreationFun = getDefaultCorrCreationFun();
	
	
	/**
	 * This will change the creation of nodes
	 * @param fun the function which is changing the Creation
	 */
	public static void setNodeCreationFun(Function<TGGRuleNode, EObject> fun){
		nodeCreationFun = fun;
	}
	
	/**
	 * This will change the creation of edges
	 * @param fun the function which is changing the Creation
	 */
	public static void setEdgeCreationFun(Function<EObject, Function<EObject, Consumer<EReference>>> fun){
		edgeCreationFun = fun;
	}
	
	/**
	 * This will change the creation of nodes
	 * @param fun the function which is changing the Creation
	 */
	public static void setCorrCreationFun(Function<TGGRuleNode, Function<EObject, Function<EObject, Function<Resource, EObject>>>> fun){
		corrCreationFun = fun;
	}
	
	/**
	 * Returns the default node creation function
	 * @return the default node creation function
	 */
	public static Function<TGGRuleNode, EObject> getDefaultNodeCreationFun(){
		return node -> {return EcoreUtil.create(node.getType());};
	}
	
	/**
	 * Returns the default Correspondence creation function
	 * @return the default Correspondence creation function
	 */
	public static Function<TGGRuleNode, Function<EObject, Function<EObject, Function<Resource, EObject>>>> getDefaultCorrCreationFun(){
		return node -> src -> trg -> corrR -> createCorr(node, src, trg, corrR); 
	}
	
	/**
	 * This is the creation of a node, if a new creation function is set and fails it uses the default creation function
	 * @param node the Type of the Node
	 * @return a new Node
	 */
	private static EObject createNodeByTGGRuleNode(TGGRuleNode node){
		EObject obj = nodeCreationFun.apply(node);
		if(obj == null){
			return getDefaultNodeCreationFun().apply(node);
		} else return obj;
	}
	
	/**
	 * Returns the default edge creation function
	 * @return the default edge creation function
	 */
	public static Function<EObject, Function<EObject, Consumer<EReference>>> getDefaultEdgeCreationFun(){
		return src -> trg -> ref -> {createDefaultEdge(src, trg, ref);};
	}
	
	//changed from for each construct to collection.stream() construct for consistency
	public static void createNonCorrNodes(IPatternMatch match, HashMap<String, EObject> comatch,
			Collection<TGGRuleNode> greenNodes, Resource nodeResource) {
		greenNodes.stream().forEach(n -> {comatch.put(n.getName(), createNode(match, n, nodeResource));});
	}

	public static Collection<RuntimeEdge> createEdges(IPatternMatch match, HashMap<String, EObject> comatch,
			Collection<TGGRuleEdge> greenEdges, boolean createEMFEdgesAsWell) {
		return greenEdges.stream().map(e -> {
			return mapToRuntimeEdge(e, match, comatch, createEMFEdgesAsWell);
		}).collect(Collectors.toList());
	}
	
	private static RuntimeEdge mapToRuntimeEdge(TGGRuleEdge edge, IPatternMatch match, HashMap<String, EObject> comatch, boolean createEMFEdgesAsWell){
		EObject src = getVariableByName(edge.getSrcNode().getName(), comatch, match);
		EObject trg = getVariableByName(edge.getTrgNode().getName(), comatch, match);
		if (createEMFEdgesAsWell)
			createEMFEdge(edge, src,trg);
		return new RuntimeEdge(src, trg, edge.getType());
	}

	//changed from for each construct to collection.stream() construct for consistency
	public static void createCorrs(IPatternMatch match, HashMap<String, EObject> comatch,
			Collection<TGGRuleCorr> greenCorrs, Resource corrR) {
		greenCorrs.stream().forEach(c -> {
			comatch.put(c.getName(), corrCreationFun.apply(c).apply(getVariableByName(c.getSource().getName(), comatch, match))
					.apply(getVariableByName(c.getTarget().getName(), comatch, match)).apply(corrR));
		});
	}

	public static void deleteNodes(Collection<EObject> elements) {
		elements.stream().forEach(EcoreUtil::delete);
	}

	public static EObject getVariableByName(String name, HashMap<String, EObject> comatch, IPatternMatch match) {
		if (comatch.containsKey(name))
			return comatch.get(name);
		return (EObject) match.get(name);
	}

	// changed to edgeCreationFun
	private static void createEMFEdge(TGGRuleEdge e, EObject src, EObject trg) {
		EReference ref = e.getType();
		edgeCreationFun.apply(src).apply(trg).accept(ref);
	}
	
	/**
	 * The default Edge creation
	 * @param src source Object
	 * @param trg target Object
	 * @param ref the reference which connects source and target
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void createDefaultEdge(EObject src, EObject trg, EReference ref){
		if (ref.isMany())
			((EList) src.eGet(ref)).add(trg);
		else
			src.eSet(ref, trg);
		if (ref.isContainment() && trg.eResource() != null) {
			trg.eResource().getContents().remove(trg);
		}
	}

	@SuppressWarnings({"rawtypes" })
	public static void deleteEdge(EObject src, EObject trg, EReference ref) {
		if (ref.isMany())
			((EList) src.eGet(ref)).remove(trg);
		else
			src.eUnset(ref);
	}

	//changed from for each construct to collection.stream() construct for consistency and changed Node creation
	private static EObject createNode(IPatternMatch match, TGGRuleNode node, Resource resource) {
		EObject newObj = createNodeByTGGRuleNode(node);

		node.getAttrExpr().stream().filter(attrExpr -> findCorrectAttributeAssignment(attrExpr, newObj))
			.forEach(attrExpr -> {
			applyInplaceAttributeAssignments(attrExpr, newObj, match);
		});
		resource.getContents().add(newObj);
		return newObj;
	}
	
	private static boolean findCorrectAttributeAssignment(TGGInplaceAttributeExpression attrExpr, EObject eObject){
		TGGAttributeConstraintOperators equalOP = TGGAttributeConstraintOperators.EQUAL;
		EAttribute eAttribute = attrExpr.getAttribute();
		EClass type = eObject.eClass();
		return attrExpr.getOperator().equals(equalOP) && type.getEAllAttributes().contains(eAttribute);
	}

	private static void applyInplaceAttributeAssignments(TGGInplaceAttributeExpression attrExpr, EObject eObject, IPatternMatch match){
		if (attrExpr.getValueExpr() instanceof TGGLiteralExpression) {
			TGGLiteralExpression tle = (TGGLiteralExpression) attrExpr.getValueExpr();
			eObject.eSet(attrExpr.getAttribute(),
					String2EPrimitive.convertString(attrExpr.getAttribute().getEType(), tle.getValue()));
		}
		else if (attrExpr.getValueExpr() instanceof TGGEnumExpression) {
			TGGEnumExpression tee = TGGEnumExpression.class.cast(attrExpr.getValueExpr());
			applyTGGEnumExpression(tee, tee.getEenum(), tee.getLiteral(), eObject, attrExpr.getAttribute());
			
		}
		else if (attrExpr.getValueExpr() instanceof TGGAttributeExpression) {
			TGGAttributeExpression tae = (TGGAttributeExpression) attrExpr.getValueExpr();
			EObject obj = (EObject) match.get(tae.getObjectVar().getName());
			eObject.eSet(attrExpr.getAttribute(), obj.eGet(tae.getAttribute()));
		}
	}
	
	private static void applyTGGEnumExpression(TGGEnumExpression tee, EEnum eEnum, EEnumLiteral literal, EObject eObject, EAttribute eAttribute) {
		Enumerator enumerator = literal.getInstance();		
		eObject.eSet(eAttribute, enumerator);		
	}
	
	private static EObject createCorr(TGGRuleNode node, EObject src, EObject trg, Resource corrR) {
		EObject corr = createNode(null, node, corrR);
		corr.eSet(corr.eClass().getEStructuralFeature("source"), src);
		corr.eSet(corr.eClass().getEStructuralFeature("target"), trg);
		return corr;
	}
}
