package org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.resolution.util;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.Conflict;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;

public class ConflictElements {

	private final INTEGRATE integrate;

	private final ConflictEltFilter filter;

	private final boolean created;
	private final boolean deleted;
	private final boolean intact;
	private final EltFilter consistencyFilter;
	private final EltFilter srcTrgFilter;

	private Stream<ITGGMatch> matches;

	/**
	 * Created an object that can collect and filter the elements involved in the given conflict.
	 * 
	 * @param conflict     the conflict
	 * @param filter       used to filter the elements by different criteria described in
	 *                     {@link ConflictEltFilter}
	 * @param includeScope if <code>true</code>, the result includes the elements from the conflict
	 *                     scope
	 */
	public ConflictElements(Conflict conflict, ConflictEltFilter filter, boolean includeScope) {
		this.integrate = conflict.integrate();
		this.filter = filter;
		this.matches = getMatches(conflict, includeScope).stream();

		if (filter.domainTypes.isEmpty())
			filter.domainTypes = ConflictEltFilter.default_domainTypes;
		if (filter.modifications.isEmpty()) {
			this.created = true;
			this.deleted = true;
			this.intact = true;
		} else {
			this.created = filter.modifications.contains(ConflictEltModification.CREATED);
			this.deleted = filter.modifications.contains(ConflictEltModification.DELETED);
			this.intact = filter.modifications.contains(ConflictEltModification.INTACT);
		}

		this.consistencyFilter = new EltFilter().create().domains(filter.domainTypes.toArray(new DomainType[] {}));
		this.srcTrgFilter = this.consistencyFilter.copy();
		if (deleted && !intact)
			this.consistencyFilter.deleted();
		else if (!deleted && intact)
			this.consistencyFilter.notDeleted();

		if (!filter.ruleNames.isEmpty())
			this.matches = this.matches.filter(m -> filter.ruleNames.contains(m.getRuleName()) != filter.excludeRuleNames);
		if (!created)
			this.matches = this.matches.filter(m -> m.getType() == PatternType.CONSISTENCY);
		else if (!deleted && !intact)
			this.matches = this.matches.filter(m -> m.getType() != PatternType.CONSISTENCY);
	}

	private Set<ITGGMatch> getMatches(Conflict conflict, boolean includeScope) {
		Set<ITGGMatch> matches = new HashSet<>(conflict.getConflictMatches());
		if (includeScope)
			matches.addAll(conflict.getScopeMatches());
		return matches;
	}

	/**
	 * Returns the filtered objects.
	 * <p>
	 * <i>Performance note:</i> as this method performs the most filter steps, it is recommended to only
	 * execute it once.
	 * 
	 * @return a set of filtered objects
	 */
	public Set<EObject> getObjects() {
		Stream<EObject> objects;

		if (!created) {
			objects = this.matches.flatMap(m -> integrate.matchUtils().get(m).getEObjectStream(consistencyFilter));
		} else if (!deleted && !intact) {
			objects = this.matches.flatMap(m -> integrate.matchUtils().get(m).getEObjectStream(srcTrgFilter));
		} else {
			objects = this.matches.flatMap(m -> {
				if (created && m.getType() == PatternType.CONSISTENCY)
					return integrate.matchUtils().get(m).getEObjectStream(consistencyFilter);
				return integrate.matchUtils().get(m).getEObjectStream(srcTrgFilter);
			});
		}

		if (!filter.objectTypes.isEmpty())
			objects = objects.filter(o -> filter.objectTypes.contains(o.eClass()) != filter.excludeObjectTypes);

		return objects.collect(Collectors.toSet());
	}

	/**
	 * Returns the filtered edges.
	 * <p>
	 * <i>Performance note:</i> as this method performs the most filter steps, it is recommended to only
	 * execute it once.
	 * 
	 * @return a set of filtered edges
	 */
	public Set<EMFEdge> getEdges() {
		Stream<EMFEdge> edges;

		if (!created) {
			edges = this.matches.flatMap(m -> integrate.matchUtils().get(m).getEMFEdgeStream(consistencyFilter));
		} else if (!deleted && !intact) {
			edges = this.matches.flatMap(m -> integrate.matchUtils().get(m).getEMFEdgeStream(srcTrgFilter));
		} else {
			edges = this.matches.flatMap(m -> {
				if (created && m.getType() == PatternType.CONSISTENCY)
					return integrate.matchUtils().get(m).getEMFEdgeStream(consistencyFilter);
				return integrate.matchUtils().get(m).getEMFEdgeStream(srcTrgFilter);
			});
		}

		if (!filter.objectTypes.isEmpty())
			edges = edges.filter(e -> filter.edgeTypes.contains(e.getType()) != filter.excludeEdgeTypes);

		return edges.collect(Collectors.toSet());
	}

}
