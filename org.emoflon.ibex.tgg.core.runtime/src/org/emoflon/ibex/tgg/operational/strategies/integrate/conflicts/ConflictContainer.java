package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;

public class ConflictContainer {

	protected final INTEGRATE integrate;
	private final ITGGMatch match;
	private final Set<Conflict> conflicts;
	private final Set<ConflictContainer> subContainers;

	public ConflictContainer(INTEGRATE integrate, ITGGMatch match) {
		this.integrate = integrate;
		this.match = match;
		this.conflicts = cfactory.createObjectSet();
		this.subContainers = cfactory.createObjectSet();
	}

	public ITGGMatch getMatch() {
		return match;
	}

	public void addConflict(Conflict conflict) {
		conflicts.add(conflict);
	}

//	public ConflictContainer addAllConflicts(Collection<Conflict> conflicts) {
//		conflicts.forEach(this::addConflict);
//		return this;
//	}

	public Set<Conflict> getConflicts() {
		return conflicts;
	}

	public Set<ConflictContainer> getSubContainers() {
		return subContainers;
	}

}