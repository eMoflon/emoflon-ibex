package org.emoflon.ibex.tgg.operational.strategies.cc;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;
import org.emoflon.ibex.tgg.operational.util.RuleInfos;

import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.set.hash.THashSet;
import language.TGGRuleEdge;
import runtime.TGGRuleApplication;

public class ConsistencyReporter {

	private RuleInfos ruleInfos;
	
	private Collection<EObject> inconsistentSrcNodes;
	private Collection<EObject> inconsistentTrgNodes;
	private Collection<EObject> inconsistentCorrNodes;
	
	private Collection<RuntimeEdge> inconsistentSrcEdges;
	private Collection<RuntimeEdge> inconsistentTrgEdges;

	public void init(Resource src, Resource trg, Resource protocol, RuleInfos ruleInfos) {
		this.ruleInfos = ruleInfos;
		inconsistentSrcNodes = extractInconsistentNodes(src, protocol, Domain.SRC);
		inconsistentTrgNodes = extractInconsistentNodes(trg, protocol, Domain.TRG);
		inconsistentSrcEdges = extractInconsistentEdges(src, protocol, Domain.SRC);
		inconsistentTrgEdges = extractInconsistentEdges(trg, protocol, Domain.TRG);
	}
	
	public void init(Resource src, Resource trg, Resource corr, Resource protocol, RuleInfos ruleInfos) {
		this.ruleInfos = ruleInfos;
		inconsistentSrcNodes = extractInconsistentNodes(src, protocol, Domain.SRC);
		inconsistentTrgNodes = extractInconsistentNodes(trg, protocol, Domain.TRG);
		inconsistentCorrNodes = extractInconsistentNodes(corr, protocol, Domain.CORR);
		inconsistentSrcEdges = extractInconsistentEdges(src, protocol, Domain.SRC);
		inconsistentTrgEdges = extractInconsistentEdges(trg, protocol, Domain.TRG);
		
	}
	
	public Collection<EObject> getInconsistentSrcNodes() {
		return inconsistentSrcNodes;
	}

	public Collection<EObject> getInconsistentTrgNodes() {
		return inconsistentTrgNodes;
	}

	public Collection<RuntimeEdge> getInconsistentSrcEdges() {
		return inconsistentSrcEdges;
	}

	public Collection<RuntimeEdge> getInconsistentTrgEdges() {
		return inconsistentTrgEdges;
	}
	
	public Collection<EObject> getInconsistentCorrNodes() {
		return inconsistentCorrNodes;
	}

	private Collection<EObject> extractInconsistentNodes(Resource resource, Resource protocol, Domain domain) {
		Iterator<EObject> it = resource.getAllContents();

		Collection<EObject> nodes = new THashSet<>();

		while (it.hasNext()) {
			nodes.add(it.next());
		}
		protocol.getContents().forEach(c -> {
			if (c instanceof TGGRuleApplication) {
				TGGRuleApplication ra = (TGGRuleApplication) c;
				Collection<EObject> createdNodes;
				
				switch(domain) {
				case SRC: createdNodes = ra.getCreatedSrc(); break;
				case TRG: createdNodes = ra.getCreatedTrg(); break;
				case CORR: createdNodes = ra.getCreatedCorr(); break;
				default: createdNodes = null; break;
				}

				nodes.removeAll(createdNodes);
			}

		});
		return nodes;
	}

	private Collection<RuntimeEdge> extractInconsistentEdges(Resource resource, Resource protocol, Domain domain) {

		Iterator<EObject> it = resource.getAllContents();

		Collection<RuntimeEdge> edges = new TCustomHashSet<>(new RuntimeEdgeHashingStrategy());

		while (it.hasNext()) {
			EObject node = it.next();
			for (EReference ref : node.eClass().getEAllReferences()) {
				if (!ref.isDerived()) {
					Object getterResult = node.eGet(ref);
					if (getterResult instanceof EList) {
						@SuppressWarnings("unchecked")
						EList<EObject> getResultCollection = (EList<EObject>) getterResult;
						for (EObject edgeTrg : getResultCollection) {
							edges.add(new RuntimeEdge(node, edgeTrg, ref));
						}
					} else if (getterResult instanceof EObject) {
						edges.add(new RuntimeEdge(node, (EObject) getterResult, ref));
					}
				}

			}
		}

		protocol.getContents().forEach(c -> {
			if (c instanceof TGGRuleApplication) {
				TGGRuleApplication ra = (TGGRuleApplication) c;
				Collection<TGGRuleEdge> specificationEdges = domain == Domain.SRC
						? ruleInfos.getGreenSrcEdges(ra.getName()) : ruleInfos.getGreenTrgEdges(ra.getName());
				for(TGGRuleEdge specificationEdge : specificationEdges){
					EObject srcOfEdge = ra.getNodeMappings().get(specificationEdge.getSrcNode().getName());
					EObject trgOfEdge = ra.getNodeMappings().get(specificationEdge.getTrgNode().getName());
					EReference refOfEdge = specificationEdge.getType();
					RuntimeEdge edge = new RuntimeEdge(srcOfEdge, trgOfEdge, refOfEdge);
					edges.remove(edge);
				}
			}
		});
		return edges;
	}
	
	

	private enum Domain {
		SRC, TRG, CORR
	}

}
