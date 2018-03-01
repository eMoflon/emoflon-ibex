package org.emoflon.ibex.tgg.operational.defaults;

import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.emoflon.ibex.common.utils.EMFManipulationUtils;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.TGGRuleNode;
import runtime.TGGRuleApplication;

public class IbexRedInterpreter implements IRedInterpreter {

	private OperationalStrategy strategy;

	public IbexRedInterpreter(OperationalStrategy operationalStrategy) {
		this.strategy = operationalStrategy;
	}
	
	@Override
	public void revokeOperationalRule(IMatch match) {
		TGGRuleApplication ra = strategy.getRuleApplicationNode(match);
		IGreenPattern pattern = strategy.revokes(match);
		
		revokeCorrs(match, pattern);
		revokeNodes(match, pattern);
		revokeEdges(match, pattern);
		
		EcoreUtil.delete(ra);
	}

	private void revokeEdges(IMatch match, IGreenPattern pattern) {
		pattern.getSrcTrgEdgesCreatedByPattern().forEach(e -> {
			RuntimeEdge runtimeEdge = strategy.getRuntimeEdge(match, e);
			strategy.removeCreatedEdge(runtimeEdge);
			EMFManipulationUtils.deleteEdge(runtimeEdge.getSrc(), runtimeEdge.getTrg(), runtimeEdge.getRef());
		});
		
		pattern.getEdgesMarkedByPattern().forEach(e -> {
			RuntimeEdge runtimeEdge = strategy.getRuntimeEdge(match, e);
			strategy.removeMarkedEdge(runtimeEdge);
		});
	}

	private void revokeNodes(IMatch match, IGreenPattern pattern) {
		pattern.getSrcTrgNodesCreatedByPattern().stream()
			.map(TGGRuleNode::getName)
			.map(match::get)
			.map(EObject.class::cast)
			.forEach(this::revokeNode);
	}
	
	private void revokeNode(EObject n) {
		if (EMFManipulationUtils.isDanglingNode(n)) {
			strategy.addToTrash(n);
		}

		// Now safe to delete
		delete(n);
	}

	private void revokeCorrs(IMatch match, IGreenPattern pattern) {
		pattern.getCorrNodes().stream()
			.map(TGGRuleNode::getName)
			.map(match::get)
			.map(EObject.class::cast)
			.forEach(corr -> {
				EStructuralFeature srcFeature = corr.eClass().getEStructuralFeature("source");
				EStructuralFeature trgFeature = corr.eClass().getEStructuralFeature("target");

				EObject src = (EObject) corr.eGet(srcFeature);
				EObject trg = (EObject) corr.eGet(trgFeature);

				if (EMFManipulationUtils.isDanglingNode(Optional.ofNullable(src))) {
					strategy.addToTrash(src);
				}
				if (EMFManipulationUtils.isDanglingNode(Optional.ofNullable(trg))) {
					strategy.addToTrash(trg);
				}

				corr.eUnset(srcFeature);
				corr.eUnset(trgFeature);			

				revokeNode(corr);
			});
		
	}
	
	// This method is exactly what is in EcoreUtil.delete (apart from the Fixme below)
	private void delete(EObject eObject)
	  {
	    EObject rootEObject = EcoreUtil.getRootContainer(eObject);
	    Resource resource = rootEObject.eResource();

	    Collection<EStructuralFeature.Setting> usages;
	    if (resource == null)
	    {
	      usages = UsageCrossReferencer.find(eObject, rootEObject);
	    }
	    else
	    {
	      ResourceSet resourceSet = resource.getResourceSet();
	      if (resourceSet == null)
	      {
	        usages = UsageCrossReferencer.find(eObject, resource);
	      }
	      else
	      {
	        usages = UsageCrossReferencer.find(eObject, resourceSet);
	      }
	    }

	    for (EStructuralFeature.Setting setting : usages)
	    {
	      if (setting.getEStructuralFeature().isChangeable())
	      {
	        EcoreUtil.remove(setting, eObject);
	      }
	    }

	    //FIXME [Greg] Why doesn't this work?
	    //EcoreUtil.remove(eObject);
	  }
}
