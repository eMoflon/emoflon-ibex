package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.SimpleMatch;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXForEachExpression;

public class GraphTransformationIterationInterpreter {

	/**
	 * The default resource.
	 */
	private final Resource defaultResource;
	/**
	 * The trash resource.
	 */
	private final Resource trashResource;

	private final GraphTransformationInterpreter contextInterpreter;

	public GraphTransformationIterationInterpreter(final Resource defaultResource, final Resource trashResource,
			final GraphTransformationInterpreter contextInterpreter) {
		this.defaultResource = defaultResource;
		this.trashResource = trashResource;
		this.contextInterpreter = contextInterpreter;
	}
	
	@SuppressWarnings("unchecked")
	public Optional<IMatch> apply(final Collection<IBeXForEachExpression> iteratorPatterns, final IMatch match,
			final Map<String, Object> parameters) {
		Map<EReference, Map<EObject, List<EObject>>> deletedEdges = new HashMap<>();
		
		for(IBeXForEachExpression iterator : iteratorPatterns) {
			iterator.getCreate().getCreatedEdges().forEach(edge -> {
				EObject itrSrc = (EObject) match.get(iterator.getSource().getName());
				EObject src = (EObject) match.get(edge.getSourceNode().getName());
				EReference itrRef = iterator.getEdge().getType();
				EReference ref = edge.getType();
				if(itrRef.isMany()) {
					List<EObject> trgs = (List<EObject>) itrSrc.eGet(itrRef);
					for(EObject trg : trgs) {
						EMFManipulationUtils.createEdge(src, trg, ref);
					}
				}else {
					EObject trg = (EObject) itrSrc.eGet(itrRef);
					EMFManipulationUtils.createEdge(src, trg, ref);
				}
			});
			
			iterator.getCreate().getAttributeAssignments().forEach(asgn -> {
				EObject itrSrc = (EObject) match.get(iterator.getSource().getName());
				EReference itrRef = iterator.getEdge().getType();
				if(itrRef.isMany()) {
					List<EObject> trgs = (List<EObject>) itrSrc.eGet(itrRef);
					for(EObject trg : trgs) {
						// Calculate attribute values.
						SimpleMatch dynamicMatch = new SimpleMatch(match);
						dynamicMatch.put(iterator.getTrgIterator().getName(), trg);
						AssignmentTriple assignment = GraphTransformationCreateInterpreter.calculateAssignment(asgn, dynamicMatch, parameters, contextInterpreter);
						assignment.getObject().eSet(assignment.getAttribute(), assignment.getValue());
					}
				}else {
					EObject trg = (EObject) itrSrc.eGet(itrRef);
					// Calculate attribute values.
					SimpleMatch dynamicMatch = new SimpleMatch(match);
					dynamicMatch.put(iterator.getTrgIterator().getName(), trg);
					AssignmentTriple assignment = GraphTransformationCreateInterpreter.calculateAssignment(asgn, dynamicMatch, parameters, contextInterpreter);
					assignment.getObject().eSet(assignment.getAttribute(), assignment.getValue());
				}
			});
			
			iterator.getDelete().getDeletedEdges().forEach(edge -> {
				EObject itrSrc = (EObject) match.get(iterator.getSource().getName());
				EObject src = (EObject) match.get(edge.getSourceNode().getName());
				EReference itrRef = iterator.getEdge().getType();
				EReference ref = edge.getType();
				if(itrRef.isMany()) {
					List<EObject> trgs = (List<EObject>) itrSrc.eGet(itrRef);
					for(EObject trg : trgs) {
						Map<EObject, List<EObject>> src2deleted = deletedEdges.get(ref);
						if(src2deleted == null) {
							src2deleted = new HashMap<>();
							deletedEdges.put(ref, src2deleted);
						}
						List<EObject> deleted = src2deleted.get(src);
						if(deleted == null) {
							deleted = new LinkedList<>();
							src2deleted.put(src, deleted);
						}
						deleted.add(trg);
					}
				}else {
					EObject trg = (EObject) itrSrc.eGet(itrRef);
					Map<EObject, List<EObject>> src2deleted = deletedEdges.get(ref);
					if(src2deleted == null) {
						src2deleted = new HashMap<>();
						deletedEdges.put(ref, src2deleted);
					}
					List<EObject> deleted = src2deleted.get(src);
					if(deleted == null) {
						deleted = new LinkedList<>();
						src2deleted.put(src, deleted);
					}
					deleted.add(trg);
				}
			});
		}
		
		for(EReference ref : deletedEdges.keySet()) {
			Map<EObject, List<EObject>> src2deleted = deletedEdges.get(ref);
			for(EObject src : src2deleted.keySet()) {
				if(ref.isMany()) {
					List<EObject> trgs = (List<EObject>) src.eGet(ref);
					trgs.removeAll(src2deleted.get(src));
				} else {
					src.eSet(ref, null);
				}
			}
		}

		return Optional.of(match);
	}
	
}
