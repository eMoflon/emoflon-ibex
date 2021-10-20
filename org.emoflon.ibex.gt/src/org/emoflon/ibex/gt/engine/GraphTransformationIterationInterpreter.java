package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.SimpleMatch;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
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
		
		Map<EReference, Map<EObject, List<EObject>>> deletedContainments = new HashMap<>();
		
		for(IBeXForEachExpression iterator : iteratorPatterns) {
			boolean hasExplicitSubtype = iterator.getEdge().getType() != iterator.getTrgIterator().getType();
			
			if(iterator.getCreate() != null) {
				for(IBeXEdge edge : iterator.getCreate().getCreatedEdges()) {
					EObject itrSrc = (EObject) match.get(iterator.getSource().getName());
					EObject src = (EObject) match.get(edge.getSourceNode().getName());
					EReference itrRef = iterator.getEdge().getType();
					EReference ref = edge.getType();
					if(itrRef.isMany()) {
						// Make copy of list to prevent concurrent modification exceptions in case of containment edges
						List<EObject> trgs = new LinkedList<>();
						// Check if the reference was explicitly typed and ignore invalid types
						if(!hasExplicitSubtype) {
							trgs.addAll((List<EObject>) itrSrc.eGet(itrRef));
						} else {
							trgs.addAll(((List<EObject>) itrSrc.eGet(itrRef)).stream()
									.filter(trg -> trg.eClass() == iterator.getTrgIterator().getType() ||
									trg.eClass().getEAllSuperTypes().contains(iterator.getTrgIterator().getType()))
									.collect(Collectors.toList()));
						}
						for(EObject trg : trgs) {
							EMFManipulationUtils.createEdge(src, trg, ref);
						}
						
						if(ref.isContainment()) {
							Map<EObject, List<EObject>> containments = deletedContainments.get(ref);
							if(containments == null) {
								containments = new HashMap<>();
								deletedContainments.put(ref, containments);
							}
							if(containments.containsKey(itrSrc)) {
								containments.get(itrSrc).addAll(trgs);
							} else {
								containments.put(itrSrc, trgs);
							}
						}
					}else {
						EObject trg = (EObject) itrSrc.eGet(itrRef);
						// Check if the reference was explicitly typed and ignore invalid types
						if(hasExplicitSubtype && trg.eClass() != iterator.getTrgIterator().getType() &&
								!trg.eClass().getEAllSuperTypes().contains(iterator.getTrgIterator().getType())){
							continue;
						}

						EMFManipulationUtils.createEdge(src, trg, ref);
						
						if(ref.isContainment()) {
							Map<EObject, List<EObject>> containments = deletedContainments.get(ref);
							if(containments == null) {
								containments = new HashMap<>();
								deletedContainments.put(ref, containments);
							}
							if(containments.containsKey(itrSrc)) {
								containments.get(itrSrc).add(trg);
							} else {
								containments.put(itrSrc, new LinkedList<>());
								containments.get(itrSrc).add(trg);
							}
						}
					}
				}
				
				for(IBeXAttributeAssignment asgn : iterator.getCreate().getAttributeAssignments()) {
					EObject itrSrc = (EObject) match.get(iterator.getSource().getName());
					EReference itrRef = iterator.getEdge().getType();
					if(itrRef.isMany()) {
						List<EObject> trgs = null;
						if(itrRef.isContainment() && deletedContainments.containsKey(itrRef) && deletedContainments.get(itrRef).containsKey(itrSrc)) {
							// Check if the reference was explicitly typed and ignore invalid types
							if(!hasExplicitSubtype) {
								trgs = deletedContainments.get(itrRef).get(itrSrc);
							} else {
								trgs = deletedContainments.get(itrRef).get(itrSrc).stream()
										.filter(trg -> trg.eClass() == iterator.getTrgIterator().getType() ||
											trg.eClass().getEAllSuperTypes().contains(iterator.getTrgIterator().getType()))
										.collect(Collectors.toList());
							}
						} else {
							// Check if the reference was explicitly typed and ignore invalid types
							if(!hasExplicitSubtype) {
								trgs = (List<EObject>) itrSrc.eGet(itrRef);
							} else {
								trgs = ((List<EObject>) itrSrc.eGet(itrRef)).stream()
										.filter(trg -> trg.eClass() == iterator.getTrgIterator().getType() ||
											trg.eClass().getEAllSuperTypes().contains(iterator.getTrgIterator().getType()))
										.collect(Collectors.toList());
							}
						}
						
						for(EObject trg : trgs) {
							// Calculate attribute values.
							SimpleMatch dynamicMatch = new SimpleMatch(match);
							dynamicMatch.put(iterator.getTrgIterator().getName(), trg);
							AssignmentTriple assignment = GraphTransformationCreateInterpreter.calculateAssignment(asgn, dynamicMatch, parameters, contextInterpreter);
							assignment.getObject().eSet(assignment.getAttribute(), assignment.getValue());
						}
					}else {
						EObject trg = null;
						if(itrRef.isContainment() && deletedContainments.containsKey(itrRef) && deletedContainments.get(itrRef).containsKey(itrSrc)) {
							trg = deletedContainments.get(itrRef).get(itrSrc).get(0);
						} else {
							trg = (EObject) itrSrc.eGet(itrRef);
						}
						
						// Check if the reference was explicitly typed and ignore invalid types
						if(hasExplicitSubtype && trg.eClass() != iterator.getTrgIterator().getType() &&
								!trg.eClass().getEAllSuperTypes().contains(iterator.getTrgIterator().getType())){
							continue;
						}
						
						// Calculate attribute values.
						SimpleMatch dynamicMatch = new SimpleMatch(match);
						dynamicMatch.put(iterator.getTrgIterator().getName(), trg);
						AssignmentTriple assignment = GraphTransformationCreateInterpreter.calculateAssignment(asgn, dynamicMatch, parameters, contextInterpreter);
						assignment.getObject().eSet(assignment.getAttribute(), assignment.getValue());
					}
				}
			}
			
			if(iterator.getDelete() != null) {
				for(IBeXEdge edge : iterator.getDelete().getDeletedEdges()) {
					EObject itrSrc = (EObject) match.get(iterator.getSource().getName());
					EObject src = (EObject) match.get(edge.getSourceNode().getName());
					EReference itrRef = iterator.getEdge().getType();
					EReference ref = edge.getType();
					if(itrRef.isMany()) {
						// Check if the reference was explicitly typed and ignore invalid types
						List<EObject> trgs = null;
						if(!hasExplicitSubtype) {
							trgs = (List<EObject>) itrSrc.eGet(itrRef);
						} else {
							trgs = ((List<EObject>) itrSrc.eGet(itrRef)).stream()
									.filter(trg -> trg.eClass() == iterator.getTrgIterator().getType() ||
									trg.eClass().getEAllSuperTypes().contains(iterator.getTrgIterator().getType()))
									.collect(Collectors.toList());
						}
						
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
						// Check if the reference was explicitly typed and ignore invalid types
						if(hasExplicitSubtype && trg.eClass() != iterator.getTrgIterator().getType() &&
								!trg.eClass().getEAllSuperTypes().contains(iterator.getTrgIterator().getType())){
							continue;
						}
						
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
				}
			}	
			
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
