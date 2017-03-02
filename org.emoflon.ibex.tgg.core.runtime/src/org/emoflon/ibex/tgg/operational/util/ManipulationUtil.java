package org.emoflon.ibex.tgg.operational.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
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

	private static RuntimePackage runtimePackage = RuntimePackage.eINSTANCE;
	
	public static void createNonCorrNodes(IMatch match, HashMap<String, EObject> comatch,
			Collection<TGGRuleNode> greenNodes, Resource nodeResource) {
		for (TGGRuleNode n : greenNodes) {
			comatch.put(n.getName(), createNode(match, n, nodeResource));
		}
	}

	public static Collection<RuntimeEdge> createEdges(IMatch match, HashMap<String, EObject> comatch,
			Collection<TGGRuleEdge> greenEdges, boolean createEMFEdgesAsWell) {
		Collection<RuntimeEdge> result = new ArrayList<>();
		for (TGGRuleEdge e : greenEdges) {
			EObject src = getVariableByName(e.getSrcNode().getName(), comatch, match);
			EObject trg = getVariableByName(e.getTrgNode().getName(), comatch, match);
			if (createEMFEdgesAsWell)
				createEMFEdge(e, src,trg);
			result.add(new RuntimeEdge(src, trg, e.getType()));
		}
		return result;
	}

	public static void createCorrs(IMatch match, HashMap<String, EObject> comatch,
			Collection<TGGRuleCorr> greenCorrs, Resource corrR) {
		for (TGGRuleCorr c : greenCorrs) {
			comatch.put(c.getName(), createCorr(c, getVariableByName(c.getSource().getName(), comatch, match),
					getVariableByName(c.getTarget().getName(), comatch, match), corrR));
		}
	}

	public static void deleteNodes(Collection<EObject> elements) {
		elements.stream().forEach(EcoreUtil::delete);
	}

	public static EObject getVariableByName(String name, HashMap<String, EObject> comatch, IMatch match) {
		if (comatch.containsKey(name))
			return comatch.get(name);
		return (EObject) match.get(name);
	}

	private static void createEMFEdge(TGGRuleEdge e, EObject src, EObject trg) {
		EReference ref = e.getType();
		if (ref.isMany())
			((EList) src.eGet(ref)).add(trg);
		else
			src.eSet(ref, trg);
		
		if (ref.isContainment() && trg.eResource() != null) {
			trg.eResource().getContents().remove(trg);
		}
	}

	public static void deleteEdge(EObject src, EObject trg, EReference ref) {
		if (ref.isMany())
			((EList) src.eGet(ref)).remove(trg);
		else
			src.eUnset(ref);
	}

	private static EObject createNode(IMatch match, TGGRuleNode node, Resource resource) {
		EObject newObj = EcoreUtil.create(node.getType());

		// apply inplace attribute assignments
		for (TGGInplaceAttributeExpression attrExpr : node.getAttrExpr()) {
			if (attrExpr.getOperator().equals(TGGAttributeConstraintOperators.EQUAL)) {
				if (attrExpr.getValueExpr() instanceof TGGLiteralExpression) {
					TGGLiteralExpression tle = (TGGLiteralExpression) attrExpr.getValueExpr();
					newObj.eSet(attrExpr.getAttribute(),
							String2EPrimitive.convertString(attrExpr.getAttribute().getEType(), tle.getValue()));
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
		
		handlePlacementInResource(node, resource, newObj);
		return newObj;
	}

	private static void handlePlacementInResource(TGGRuleNode node, Resource resource, EObject newObj) {
		// TODO[anjorin] For now check carefully if object will be added as a child of another object in this co-match
		//              If yes, do not add to the resource
		// Consider simplifying this later (so simply always add to the resource) as soon as Democles accepts this
		if(node.getIncomingEdges().stream()
				.map(TGGRuleEdge::getType)
				.noneMatch(EReference::isContainment))
			resource.getContents().add(newObj);
	}

	private static EObject createCorr(TGGRuleNode node, EObject src, EObject trg, Resource corrR) {
		EObject corr = createNode(null, node, corrR);
		corr.eSet(corr.eClass().getEStructuralFeature("source"), src);
		corr.eSet(corr.eClass().getEStructuralFeature("target"), trg);
		return corr;
	}
}
