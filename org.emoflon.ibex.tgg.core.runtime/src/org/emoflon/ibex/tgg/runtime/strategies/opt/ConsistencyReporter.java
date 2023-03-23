package org.emoflon.ibex.tgg.runtime.strategies.opt;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.patterns.TGGPatternUtil.getNodes;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.runtime.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.runtime.strategies.modules.RuleHandler;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuleApplication;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.smartemf.runtime.util.SmartEMFUtil;

public class ConsistencyReporter {
	private OperationalStrategy strategy;

	private Collection<EObject> inconsistentSrcNodes;
	private Collection<EObject> inconsistentTrgNodes;
	private Collection<EObject> inconsistentCorrNodes;

	private Collection<EMFEdge> inconsistentSrcEdges;
	private Collection<EMFEdge> inconsistentTrgEdges;

	private TGGResourceHandler resourceHandler;
	private RuleHandler ruleHandler;

	public ConsistencyReporter(OperationalStrategy strategy) {
		this.strategy = strategy;
		resourceHandler = strategy.getOptions().resourceHandler();
		ruleHandler = strategy.getOptions().tgg.ruleHandler();
	}

	public void init(OperationalStrategy strategy) {
		inconsistentSrcNodes = extractInconsistentNodes(resourceHandler.getSourceResource(), resourceHandler.getProtocolResource(), DomainType.SOURCE);
		inconsistentTrgNodes = extractInconsistentNodes(resourceHandler.getTargetResource(), resourceHandler.getProtocolResource(), DomainType.TARGET);
		inconsistentSrcEdges = extractInconsistentEdges(resourceHandler.getSourceResource(), resourceHandler.getProtocolResource(), DomainType.SOURCE);
		inconsistentTrgEdges = extractInconsistentEdges(resourceHandler.getTargetResource(), resourceHandler.getProtocolResource(), DomainType.TARGET);
	}

	public void initWithCorr() {
		init(strategy);
		inconsistentCorrNodes = //
				extractInconsistentNodes(resourceHandler.getCorrResource(), resourceHandler.getProtocolResource(), DomainType.CORRESPONDENCE);
	}

	public void initSrc() {
		inconsistentSrcNodes = extractInconsistentNodes(resourceHandler.getSourceResource(), resourceHandler.getProtocolResource(), DomainType.SOURCE);
		inconsistentSrcEdges = extractInconsistentEdges(resourceHandler.getSourceResource(), resourceHandler.getProtocolResource(), DomainType.SOURCE);
	}

	public void initTrg() {
		inconsistentTrgNodes = extractInconsistentNodes(resourceHandler.getTargetResource(), resourceHandler.getProtocolResource(), DomainType.TARGET);
		inconsistentTrgEdges = extractInconsistentEdges(resourceHandler.getTargetResource(), resourceHandler.getProtocolResource(), DomainType.TARGET);
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

			for (EReference ref : SmartEMFUtil.getEAllNonDynamicReferences(node.eClass())) {
				if (!ref.isDerived()) {
					Object getterResult = node.eGet(ref);
					if (getterResult instanceof EList) {
						@SuppressWarnings("unchecked")
						EList<EObject> getResultCollection = (EList<EObject>) getterResult;
						for (EObject edgeTrg : getResultCollection) {
							edges.add(new EMFEdge(node, edgeTrg, ref));
						}
					} else if (getterResult instanceof EObject eObjRes) {
						edges.add(new EMFEdge(node, eObjRes, ref));
					}
				}
			}
		}

		protocol.getContents().forEach(c -> {
			if (c instanceof TGGRuleApplication ra) {

				String ruleName = ra.eClass().getName().substring(0, ra.eClass().getName().length() - 8);
				TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(ruleName);

				Collection<IBeXEdge> specificationEdges = domain == DomainType.SOURCE ? operationalRule.getCreateSource().getEdges()
						: operationalRule.getCreateTarget().getEdges();
				
				for (IBeXEdge specificationEdge : specificationEdges) {
					EObject srcOfEdge = TGGPatternUtil.getNode(ra, ((TGGNode) specificationEdge.getSource()).getBindingType(), domain,
							specificationEdge.getSource().getName());
					EObject trgOfEdge = TGGPatternUtil.getNode(ra, ((TGGNode) specificationEdge.getTarget()).getBindingType(), domain,
							specificationEdge.getTarget().getName());
					if (srcOfEdge == null || trgOfEdge == null)
						throw new NullPointerException("Source and/or target of tggRuleEdge<" + specificationEdge + "> must not be null!");

					EReference refOfEdge = specificationEdge.getType();
					EMFEdge edge = new EMFEdge(srcOfEdge, trgOfEdge, refOfEdge);
					edges.remove(edge);

					if (refOfEdge.getEOpposite() != null) {
						EMFEdge opposite = new EMFEdge(trgOfEdge, srcOfEdge, refOfEdge.getEOpposite());
						edges.remove(opposite);
					}
				}
			}
		});
		return edges;
	}
}
