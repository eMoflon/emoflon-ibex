package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util;

import static language.DomainType.CORR;
import static language.DomainType.SRC;
import static language.DomainType.TRG;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util.ConflictEltModification.CREATED;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util.ConflictEltModification.DELETED;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util.ConflictEltModification.INTACT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.Conflict;

import language.DomainType;

/**
 * This class provides a configurable filter used to retrieve the elements involved in a
 * {@link Conflict}.<br>
 * These elements can be filtered by their
 * <ul>
 * <li>types</li>
 * <li>domain (<code>source</code>/<code>correspondence</code>/<code>target</code>)</li>
 * <li>modification (<code>created</code>/<code>deleted</code>/<code>intact</code>)</li>
 * <li>rules they are created by</li>
 * </ul>
 * 
 * @author Adrian M.
 */
public class ConflictEltFilter {

	//// DEFAULTS ////
	static final Set<DomainType> default_domainTypes = new HashSet<>(Arrays.asList(SRC, CORR, TRG));
	static final Set<ConflictEltModification> default_modifications = new HashSet<>(Arrays.asList(CREATED, DELETED, INTACT));

	Set<DomainType> domainTypes;
	Set<ConflictEltModification> modifications;

	Set<String> ruleNames;
	boolean excludeRuleNames;

	Set<EClass> objectTypes;
	boolean excludeObjectTypes;

	Set<EReference> edgeTypes;
	boolean excludeEdgeTypes;

	/**
	 * Creates a filter that includes all elements.
	 */
	public ConflictEltFilter() {
		this.domainTypes = new HashSet<>();
		this.modifications = new HashSet<>();
		this.ruleNames = new HashSet<>();
		this.excludeRuleNames = true;
		this.objectTypes = new HashSet<>();
		this.edgeTypes = new HashSet<>();
	}

	/**
	 * Sets the domains that should be included in the result.
	 * <p>
	 * <i>Overrides any previously defined domain filters.</i>
	 * 
	 * @param domainTypes the domain types
	 * @return the filter object itself
	 */
	public ConflictEltFilter setDomains(DomainType... domainTypes) {
		this.domainTypes = new HashSet<>(Arrays.asList(domainTypes));
		return this;
	}

	/**
	 * Adds the <code>source</code> domain to the domain filter.
	 * 
	 * @return the filter object itself
	 */
	public ConflictEltFilter src() {
		this.domainTypes.add(SRC);
		return this;
	}

	/**
	 * Adds the <code>target</code> domain to the domain filter.
	 * 
	 * @return the filter object itself
	 */
	public ConflictEltFilter trg() {
		this.domainTypes.add(TRG);
		return this;
	}

	/**
	 * Adds the <code>correspondence</code> domain to the domain filter.
	 * 
	 * @return the filter object itself
	 */
	public ConflictEltFilter corr() {
		this.domainTypes.add(CORR);
		return this;
	}

	/**
	 * Sets the modifications that should be included in the result.
	 * <p>
	 * <i>Overrides any previously defined modification filters.</i>
	 * 
	 * @param modifications the modification types
	 * @return the filter object itself
	 */
	public ConflictEltFilter setModifications(ConflictEltModification... modifications) {
		this.modifications = new HashSet<>(Arrays.asList(modifications));
		return this;
	}

	/**
	 * Adds the <code>created</code> modification to the modification filter.
	 * 
	 * @return the filter object itself
	 */
	public ConflictEltFilter created() {
		this.modifications.add(CREATED);
		return this;
	}

	/**
	 * Adds the <code>deleted</code> modification to the modification filter.
	 * 
	 * @return the filter object itself
	 */
	public ConflictEltFilter deleted() {
		this.modifications.add(DELETED);
		return this;
	}

	/**
	 * Adds the <code>intact</code> modification to the modification filter.
	 * 
	 * @return the filter object itself
	 */
	public ConflictEltFilter intact() {
		this.modifications.add(INTACT);
		return this;
	}

	/**
	 * Includes all elements that have been created by the given rules.
	 * <p>
	 * <i>Overrides any previously defined rule filters.</i>
	 * 
	 * @param ruleNames the set of rule names
	 * @return the filter object itself
	 */
	public ConflictEltFilter includeEltsCreatedBy(Set<String> ruleNames) {
		this.ruleNames = ruleNames;
		this.excludeRuleNames = false;
		return this;
	}

	/**
	 * Excludes all elements that have been created by the given rules.
	 * <p>
	 * <i>Overrides any previously defined rule filters.</i>
	 * 
	 * @param ruleNames the set of rule names
	 * @return the filter object itself
	 */
	public ConflictEltFilter excludeEltsCreatedBy(Set<String> ruleNames) {
		this.ruleNames = ruleNames;
		this.excludeRuleNames = true;
		return this;
	}

	/**
	 * Includes all objects of the given class types.
	 * <p>
	 * <i>Overrides any previously defined object type filters.</i>
	 * 
	 * @param objectTypes the set of object types
	 * @return the filter object itself
	 */
	public ConflictEltFilter includeObjectsOf(Set<EClass> objectTypes) {
		this.objectTypes = objectTypes;
		this.excludeObjectTypes = false;
		return this;
	}

	/**
	 * Excludes all objects of the given class types.
	 * <p>
	 * <i>Overrides any previously defined object type filters.</i>
	 * 
	 * @param objectTypes the set of object types
	 * @return the filter object itself
	 */
	public ConflictEltFilter excludeObjectsOf(Set<EClass> objectTypes) {
		this.objectTypes = objectTypes;
		this.excludeObjectTypes = true;
		return this;
	}

	/**
	 * Includes all edges of the given reference types.
	 * <p>
	 * <i>Overrides any previously defined edge type filters.</i>
	 * 
	 * @param edgeTypes the set of edge types
	 * @return the filter object itself
	 */
	public ConflictEltFilter includeEdgesOf(Set<EReference> edgeTypes) {
		this.edgeTypes = edgeTypes;
		this.excludeEdgeTypes = false;
		return this;
	}

	/**
	 * Excludes all edges of the given reference types.
	 * <p>
	 * <i>Overrides any previously defined edge type filters.</i>
	 * 
	 * @param edgeTypes the set of edge types
	 * @return the filter object itself
	 */
	public ConflictEltFilter excludeEdgesOf(Set<EReference> edgeTypes) {
		this.edgeTypes = edgeTypes;
		this.excludeEdgeTypes = true;
		return this;
	}

}
