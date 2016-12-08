package org.emoflon.ibex.tgg.operational.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;

import com.sun.javafx.fxml.expression.LiteralExpression;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.basic.expressions.TGGAttributeExpression;
import language.basic.expressions.TGGEnumExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;
import runtime.Edge;
import runtime.RuntimePackage;

/**
 * @author leblebici
 * Util class for creating EObjects, Edges, and Correspondences for a given set of green TGGRuleElement 
 */
public class ManipulationUtil {

	private static RuntimePackage runtimePackage = RuntimePackage.eINSTANCE;

	public static void createNonCorrNodes(IPatternMatch match, HashMap<String, EObject> comatch,
			Collection<TGGRuleNode> greenNodes, Resource nodeResource) {
		for (TGGRuleNode n : greenNodes) {
			comatch.put(n.getName(), createNode(match, n, nodeResource));
		}
	}

	public static Collection<Edge> createEdges(IPatternMatch match, HashMap<String, EObject> comatch,
			Collection<TGGRuleEdge> greenEdges, Resource edgeResource) {
		ArrayList<Edge> result = new ArrayList<>();
		for (TGGRuleEdge e : greenEdges) {
			Edge edge = createEdge(e, getVariableByName(e.getSrcNode().getName(), comatch, match),
					getVariableByName(e.getTrgNode().getName(), comatch, match), edgeResource);
			comatch.put(e.getName(), edge);
			result.add(edge);
		}
		return result;
	}
	
	public static void createCorrs(IPatternMatch match, HashMap<String, EObject> comatch,
			Collection<TGGRuleCorr> greenCorrs, Resource corrR) {
		for (TGGRuleCorr c : greenCorrs) {
			comatch.put(c.getName(),
					createCorr(c, getVariableByName(c.getSource().getName(), comatch, match),
							getVariableByName(c.getTarget().getName(), comatch, match), corrR));
		}
	}
	
	public static void deleteElements(Collection<EObject> elements) {
		elements.stream().filter(e -> e instanceof Edge).forEach(e -> FromEdgeWrapperToEMFEdgeUtil.revokeEdge((Edge) e));
		elements.stream().forEach(EcoreUtil::delete);
	}

	public static EObject getVariableByName(String name, HashMap<String, EObject> comatch,
			IPatternMatch match) {
		if (comatch.containsKey(name))
			return comatch.get(name);
		return (EObject) match.get(name);
	}

	private static Edge createEdge(TGGRuleEdge e, EObject src, EObject trg, Resource edgeResource) {
		Edge edge = (Edge) EcoreUtil.create(runtimePackage.getEdge());
		edgeResource.getContents().add(edge);
		edge.setName(e.getType().getName());
		edge.setSrc(src);
		edge.setTrg(trg);
		return edge;
	}

	private static EObject createNode(IPatternMatch match, TGGRuleNode node, Resource resource) {
		EObject newObj = EcoreUtil.create(node.getType());
				
		// apply inplace attribute assignments
		for (TGGInplaceAttributeExpression attrExpr : node.getAttrExpr()) {
			if(attrExpr.getOperator().equals(TGGAttributeConstraintOperators.EQUAL)) {
				if (attrExpr.getValueExpr() instanceof TGGLiteralExpression) {
					TGGLiteralExpression tle = (TGGLiteralExpression) attrExpr.getValueExpr();
					newObj.eSet(attrExpr.getAttribute(), convertString(attrExpr.getAttribute(), tle.getValue()));		
					continue;
				} 
				if (attrExpr.getValueExpr() instanceof TGGEnumExpression) {
					TGGEnumExpression tee = (TGGEnumExpression) attrExpr.getValueExpr();
					newObj.eSet(attrExpr.getAttribute(), tee.getLiteral());
					continue;
				}
				if (attrExpr.getValueExpr() instanceof TGGAttributeExpression) {
					TGGAttributeExpression tae = (TGGAttributeExpression) attrExpr.getValueExpr();
					EObject obj = (EObject) match.get(tae.getObjectVar().getName());
					newObj.eSet(attrExpr.getAttribute(), obj.eGet(tae.getAttribute()));		
					continue;
				}
				
			}
		}
		resource.getContents().add(newObj);
		return newObj;
	}
	
	private static Object convertString(EAttribute attr, String value) {
		if (attr.getEType().equals(EcorePackage.Literals.EINT)) 
			return Integer.parseInt(value);
		if (attr.getEType().equals(EcorePackage.Literals.EDOUBLE))
			return Double.parseDouble(value);
		if (attr.getEType().equals(EcorePackage.Literals.EFLOAT))
			return Float.parseFloat(value);
		if (attr.getEType().equals(EcorePackage.Literals.ECHAR))
			return value.length() == 0 ? null : value.charAt(0);
		if (attr.getEType().equals(EcorePackage.Literals.ESTRING))
			return value.substring(1, value.length() - 1);
		if (attr.getEType().equals(EcorePackage.Literals.EBOOLEAN))
			return Boolean.parseBoolean(value);
		
		return null;

	}
	
	private static EObject createCorr(TGGRuleNode node, EObject src, EObject trg, Resource corrR) {
		EObject corr = createNode(null, node, corrR);
		corr.eSet(corr.eClass().getEStructuralFeature("source"), src);
		corr.eSet(corr.eClass().getEStructuralFeature("target"), trg);
		return corr;
	}
}
