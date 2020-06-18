package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.BrokenMatch;

public class ConflictContainer {

	protected final INTEGRATE integrate;
	private final BrokenMatch brokenMatch;
	private final Set<Conflict> conflicts;
	private final Set<ConflictContainer> subContainers;

	public ConflictContainer(INTEGRATE integrate, BrokenMatch match) {
		this.integrate = integrate;
		this.brokenMatch = match;
		this.conflicts = cfactory.createObjectSet();
		this.subContainers = cfactory.createObjectSet();
	}

	public BrokenMatch getBrokenMatch() {
		return brokenMatch;
	}

	public void addConflict(Conflict conflict) {
		conflicts.add(conflict);
	}

	public Set<Conflict> getConflicts() {
		return conflicts;
	}

	public Set<ConflictContainer> getSubContainers() {
		return subContainers;
	}

}