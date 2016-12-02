package org.emoflon.ibex.tgg.operational.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
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
			comatch.put(n.getName(), createNode(n.getType(), nodeResource));
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
					createCorr(c.getType(), getVariableByName(c.getSource().getName(), comatch, match),
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

	private static EObject createNode(EClass type, Resource resource) {
		EObject newObj = EcoreUtil.create(type);
		resource.getContents().add(newObj);
		return newObj;
	}
	
	private static EObject createCorr(EClass type, EObject src, EObject trg, Resource corrR) {
		EObject corr = createNode(type, corrR);
		corr.eSet(corr.eClass().getEStructuralFeature("source"), src);
		corr.eSet(corr.eClass().getEStructuralFeature("target"), trg);
		return corr;
	}
}
