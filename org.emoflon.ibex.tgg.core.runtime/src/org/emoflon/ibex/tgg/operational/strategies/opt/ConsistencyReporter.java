package org.emoflon.ibex.tgg.operational.strategies.opt;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getNodes;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;

import language.BindingType;
import language.DomainType;
import language.TGGRuleEdge;
import runtime.TGGRuleApplication;

public class ConsistencyReporter {
	private OperationalStrategy strategy;

	private Collection<EObject> inconsistentSrcNodes;
	private Collection<EObject> inconsistentTrgNodes;
	private Collection<EObject> inconsistentCorrNodes;

	private Collection<EMFEdge> inconsistentSrcEdges;
	private Collection<EMFEdge> inconsistentTrgEdges;

	private TGGResourceHandler resourceHandler;

	public ConsistencyReporter(OperationalStrategy strategy) {
		this.strategy = strategy;
		resourceHandler = strategy.getOptions().resourceHandler();
	}
	
	public void init(OperationalStrategy strategy) {
		inconsistentSrcNodes = extractInconsistentNodes(resourceHandler.getSourceResource(), resourceHandler.getProtocolResource(),
				DomainType.SRC);
		inconsistentTrgNodes = extractInconsistentNodes(resourceHandler.getTargetResource(), resourceHandler.getProtocolResource(),
				DomainType.TRG);
		inconsistentSrcEdges = extractInconsistentEdges(resourceHandler.getSourceResource(), resourceHandler.getProtocolResource(),
				DomainType.SRC);
		inconsistentTrgEdges = extractInconsistentEdges(resourceHandler.getTargetResource(), resourceHandler.getProtocolResource(),
				DomainType.TRG);
	}

	public void initWithCorr() {
		init(strategy);
		inconsistentCorrNodes = //
				extractInconsistentNodes(resourceHandler.getCorrResource(), resourceHandler.getProtocolResource(), DomainType.CORR);
	}

	public void initSrc() {
		inconsistentSrcNodes = extractInconsistentNodes(resourceHandler.getSourceResource(), resourceHandler.getProtocolResource(),
				DomainType.SRC);
		inconsistentSrcEdges = extractInconsistentEdges(resourceHandler.getSourceResource(), resourceHandler.getProtocolResource(),
				DomainType.SRC);
	}

	public void initTrg() {
		inconsistentTrgNodes = extractInconsistentNodes(resourceHandler.getTargetResource(), resourceHandler.getProtocolResource(),
				DomainType.TRG);
		inconsistentTrgEdges = extractInconsistentEdges(resourceHandler.getTargetResource(), resourceHandler.getProtocolResource(),
				DomainType.TRG);
	}

	public Collection<EObject> getInconsistentSrcNodes() {
		return inconsistentSrcNodes;
	}

	public Collection<EObject> getInconsistentTrgNodes() {
		return inconsistentTrgNodes;
	}

	public Collection<EMFEdge> getInconsistentSrcEdges() {
		return inconsistentSrcEdges;
	}

	public Collection<EMFEdge> getInconsistentTrgEdges() {
		return inconsistentTrgEdges;
	}

	public Collection<EObject> getInconsistentCorrNodes() {
		return inconsistentCorrNodes;
	}

	private Collection<EObject> extractInconsistentNodes(Resource resource, Resource protocol, DomainType domain) {
		Iterator<EObject> it = resource.getAllContents();

		Set<EObject> nodes = cfactory.createObjectSet();

		while (it.hasNext()) {
			nodes.add(it.next());
		}
		protocol.getContents().forEach(c -> {
			if (c instanceof TGGRuleApplication) {
				Collection<EObject> createdNodes = getNodes(c, BindingType.CREATE, domain);
				nodes.removeAll(createdNodes);
			}
		});
		return nodes;
	}

	private Collection<EMFEdge> extractInconsistentEdges(Resource resource, Resource protocol, DomainType domain) {
		Iterator<EObject> it = resource.getAllContents();
		Collection<EMFEdge> edges = cfactory.createEMFEdgeHashSet();

		while (it.hasNext()) {
			EObject node = it.next();
			for (EReference ref : node.eClass().getEAllReferences()) {
				if (!ref.isDerived()) {
					Object getterResult = node.eGet(ref);
					if (getterResult instanceof EList) {
						@SuppressWarnings("unchecked")
						EList<EObject> getResultCollection = (EList<EObject>) getterResult;
						for (EObject edgeTrg : getResultCollection) {
							edges.add(new EMFEdge(node, edgeTrg, ref));
						}
					} else if (getterResult instanceof EObject) {
						edges.add(new EMFEdge(node, (EObject) getterResult, ref));
					}
				}
			}
		}

		protocol.getContents().forEach(c -> {
			if (c instanceof TGGRuleApplication) {
				TGGRuleApplication ra = (TGGRuleApplication) c;
				String ruleName = ra.eClass().getName().substring(0, ra.eClass().getName().length() - 8);
				Collection<TGGRuleEdge> specificationEdges = domain == DomainType.SRC
						? strategy.getGreenFactory(ruleName).getGreenSrcEdgesInRule()
						: strategy.getGreenFactory(ruleName).getGreenTrgEdgesInRule();
				for (TGGRuleEdge specificationEdge : specificationEdges) {
					EObject srcOfEdge = TGGPatternUtil.getNode(ra, specificationEdge.getSrcNode().getBindingType(),
							domain, specificationEdge.getSrcNode().getName());
					EObject trgOfEdge = TGGPatternUtil.getNode(ra, specificationEdge.getTrgNode().getBindingType(),
							domain, specificationEdge.getTrgNode().getName());
					EReference refOfEdge = specificationEdge.getType();
					EMFEdge edge = new EMFEdge(srcOfEdge, trgOfEdge, refOfEdge);
					edges.remove(edge);
					
					if(refOfEdge.getEOpposite() != null) {
						EMFEdge opposite = new EMFEdge(trgOfEdge, srcOfEdge, refOfEdge.getEOpposite());
						edges.remove(opposite);
					}
				}
			}
		});
		return edges;
	}
}
