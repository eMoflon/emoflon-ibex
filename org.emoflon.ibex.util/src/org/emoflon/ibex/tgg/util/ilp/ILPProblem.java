/**
 *
 */
package org.emoflon.ibex.tgg.util.ilp;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.collections.IntSet;
import org.emoflon.ibex.common.collections.IntToDoubleMap;
import org.emoflon.ibex.common.collections.IntToIntMap;
import org.emoflon.ibex.common.collections.IntToObjectMap;
import org.emoflon.ibex.common.collections.ObjectToIntMap;

/**
 * This class is used to define ILPProblems that can be given to
 * {@link ILPSolver} to be solved. An instance can be obtained using the
 * {@link ILPFactory}. Afterwards constraints and the objective can be defined
 * and added.
 *
 * @author Robin Oppermann
 *
 */
public class ILPProblem {

	/**
	 * Counter variable used for assigning IDs to variables
	 */
	private int variableCounter = 1;

	/**
	 * Contains all variables that have been defined and the mapping to their names
	 */
	private final ObjectToIntMap<String> variables = CollectionFactory.cfactory.createObjectToIntHashMap();
	/**
	 * Contains the mapping of variable names to variable IDs The additional map is
	 * used for efficiency reasons
	 */
	private final IntToObjectMap<String> variableIDsToVariables = CollectionFactory.cfactory.createIntToObjectHashMap();
	/**
	 * Set of constraints that have been defined using addConstraint
	 */
	private final Set<ILPConstraint> constraints = CollectionFactory.cfactory.createLinkedObjectSet();
	/**
	 * The objective function that has been defined using setObjective
	 */
	private ILPObjective objective = null;

	/**
	 * Contains pre-fixed variables the solver does not need to care about
	 */
	private final IntToIntMap fixedVariableValues = CollectionFactory.cfactory.createIntToIntLinkedMap();

	/**
	 * Contains the IDs of the unassigned variables
	 */
	private final IntSet unfixedVariables = CollectionFactory.cfactory.createIntSet();

	/**
	 * Contains the IDs of the variables that have been fixed but have not yet been
	 * removed from the constraints and objective
	 */
	private final IntSet lazyFixedVariables = CollectionFactory.cfactory.createLinkedIntSet();

	/**
	 * Contains for each variable the list of constraints the variable is contained
	 * in. This makes fixing variables very efficient, but costs memory
	 */
	private final IntToObjectMap<LinkedList<ILPConstraint>> variableIdsToContainingConstraints = CollectionFactory.cfactory
			.createIntToObjectHashMap();

	/**
	 * Creates a new ILPProblem. Instances can be obtained using the
	 * {@link ILPFactory}
	 */
	ILPProblem() {
	}

	/**
	 * Returns the variables that have been defined. New variables can be defined by
	 * using them within a term.
	 *
	 * @return the variables that have been defined
	 */
	public Collection<String> getVariables() {
		return Collections.unmodifiableSet(this.variables.keySet());
	}

	/**
	 * Returns the mapping of variable IDs of fixed variables to their assigned
	 * values
	 *
	 * @return
	 *
	 * @return a HashMap containing the variable mapping of fixed variables.
	 */
	protected IntToIntMap getInternalFixedVariableValues() {
		return this.fixedVariableValues;
	}

	/**
	 * Returns the internal IDs of variables that have been fixed but the fixes have
	 * not yet been applied to the constraints
	 *
	 * @return the HashSet of fixed bu not yet applied variables
	 */
	protected IntSet getLazyFixedVariables() {
		return this.lazyFixedVariables;
	}

	/**
	 * Gets all variable IDs of registered variables
	 *
	 * @return the variable IDs
	 */
	Set<Integer> getVariableIdsOfUnfixedVariables() {
		return Collections.unmodifiableSet(this.unfixedVariables);
	}

	/**
	 * Adapts the constraints and objectives to no longer contain fixed variables.
	 * Adapting constraints is a costly operation so it should only be done before
	 * accessing constraints or objective
	 */
	protected void applyLazyFixedVariables() {
		if (this.lazyFixedVariables.isEmpty())
			return;

		LinkedList<ILPConstraint> modifiedConstraints = new LinkedList<>();
		for (int id : this.lazyFixedVariables) {
			LinkedList<ILPConstraint> constraintsToModify = this.variableIdsToContainingConstraints.remove(id);
			int value = this.fixedVariableValues.get(id);
			for (ILPConstraint constraint : constraintsToModify) {
				if (!this.constraints.remove(constraint)) {
					continue;
				}
				constraint.fixVariable(id, value);
				modifiedConstraints.add(constraint);
			}
			if (this.objective != null) {
				this.objective.fixVariable(id, value);
			}
		}

		modifiedConstraints.stream().forEach(c -> this.addConstraint(c));
		this.lazyFixedVariables.clear();
	}

	/**
	 * Sets one of the variables to a fixed value
	 *
	 * @param variableName The name of the variable
	 * @param value        Value to set the variable to
	 */
	public void fixVariable(final String variableName, final int value) {
		this.fixVariable(this.getVariableId(variableName), value);
	}

	public Integer getFixedVariable(final String variableName) {
		return this.getFixedVariable(this.getVariableId(variableName));
	}

	protected Integer getFixedVariable(final int variable) {
		this.applyLazyFixedVariables();
		return this.fixedVariableValues.containsKey(variable) ? this.fixedVariableValues.get(variable) : null;
	}

	/**
	 * Fixes the variable with the given variable ID to the given value
	 *
	 * @param variableId The ID of the variable
	 * @param value      The value of the variable
	 */
	protected void fixVariable(final int variableId, final int value) {
		if (this.fixedVariableValues.containsKey(variableId)) {
			if (this.fixedVariableValues.get(variableId) == value)
				// unchanged
				return;
			else
				throw new RuntimeException("The variable " + this.getVariable(variableId) + "cannot be fixed to value "
						+ value + " as it already been fixed to a different value: "
						+ this.fixedVariableValues.get(variableId));
		}
		this.unfixedVariables.remove(variableId);
		this.fixedVariableValues.put(variableId, value);
		// variable is not directly removed from the constraints or objective (to reduce
		// overhead), but only just before the first time we
		// access the constraints/objective
		this.lazyFixedVariables.add(variableId);
	}

	/**
	 * Returns the ID of the variable with the given name
	 *
	 * @param variable The (unique) variable name
	 * @return The ID of the variable. If the variable is not yet contained, it will
	 *         be registered with a new ID
	 */
	int getVariableId(final String variable) {
		if (!this.variables.containsKey(variable))
			return this.createNewVariable(variable);
		return this.variables.getInt(variable);
	}

	/**
	 * Creates a new variable with the given name
	 *
	 * @param variableName The Name of the variable
	 * @return The id of the created variable
	 */
	int createNewVariable(final String variableName) {
		this.variables.put(variableName, this.variableCounter);
		this.variableIDsToVariables.put(this.variableCounter, variableName);
		this.unfixedVariables.add(this.variableCounter);
		this.variableIdsToContainingConstraints.put(this.variableCounter, new LinkedList<>());
		return this.variableCounter++;
	}

	/**
	 * Gets the variable name for the given variable ID
	 *
	 * @param variableId The variable ID to look for
	 * @return
	 */
	String getVariable(final int variableId) {
		return this.variableIDsToVariables.get(variableId);
	}

	/**
	 * Creates a new linear expression using the sum of the given terms. <br>
	 * The linear expression is formed as follows: (t1 + t2 + t3 + ...) where ti is
	 * one of the terms.
	 *
	 * @param terms The terms that are used in the linear expression.
	 * @return The linear expression that has been created.
	 */
	public ILPLinearExpression createLinearExpression() {
		ILPLinearExpression expr = new ILPLinearExpression();
		return expr;
	}

	/**
	 * Creates a new Solution object for this problem
	 *
	 * @param variableAllocations mapping of variable IDs to the assigning values of
	 *                            the solution
	 * @param optimal             identificator if the solution is optimal
	 * @param solutionValue       Value of the objective function in the given
	 *                            solution
	 * @return the created solution
	 */
	ILPSolution createILPSolution(final IntToIntMap variableAllocations, final boolean optimal,
			final double solutionValue) {
		return new ILPSolution(variableAllocations, optimal, solutionValue);
	}

	/**
	 * Adds a constraint to the ILP. <br>
	 * The constraint is formed as follows: (LE &lt;= V) if LE is the linear
	 * Expression, &lt;= is the comparator and V is the value.
	 *
	 * @param linearExpression The linear expression containing the sum of the terms
	 * @param comparator       The comparator (e.g. &lt;=, &gt;=)
	 * @param value            The value
	 * @param name             The name of the constraint. Naming constraints is not
	 *                         supported by all solvers.
	 */
	public ILPConstraint addConstraint(final ILPLinearExpression linearExpression, final Comparator comparator,
			final double value, final String name) {
		ILPConstraint constr = new ILPConstraint(linearExpression, comparator, value, name);
		constr.removeFixedVariables();
		for (int id : constr.linearExpression.terms.keySet()) {
			this.variableIdsToContainingConstraints.get(id).add(constr);
		}
		this.addConstraint(constr);
		return constr;
	}

	/**
	 * Adds the constraint the set of constraints
	 *
	 * @param constraint the constraint to add
	 */
	void addConstraint(final ILPConstraint constraint) {
		if (!constraint.isEmpty()) {
			this.constraints.add(constraint);
		}
	}

	/**
	 * Removes the given constraints from the Set of constraints
	 *
	 * @param constraints The constraints to remove
	 */
	void removeConstraints(final Collection<ILPConstraint> constraints) {
		this.constraints.removeAll(constraints);
	}

	/**
	 * Retrieve all defined constraints
	 *
	 * @return the constraints that have been defined
	 */
	public Collection<ILPConstraint> getConstraints() {
		this.applyLazyFixedVariables();
		return Collections.unmodifiableCollection(this.constraints);
	}

	/**
	 * Retrieve the objective function
	 *
	 * @return The objective function
	 */
	public final ILPObjective getObjective() {
		this.applyLazyFixedVariables();
		return this.objective;
	}

	/**
	 * Sets the objective of the ILP that has to be either maximized or minimized.
	 *
	 * @param linearExpression The linear expression that has to be optimized
	 * @param operation        Whether the operation should be maximized or
	 *                         minimized.
	 */
	public ILPObjective setObjective(final ILPLinearExpression linearExpression, final Objective operation) {
		ILPObjective objective = new ILPObjective(linearExpression, operation);
		this.setObjective(objective);
		return objective;
	}

	/**
	 * Sets the objective of the ILP
	 *
	 * @param objective the objective
	 */
	void setObjective(final ILPObjective objective) {
		objective.removeFixedVariables();
		this.objective = objective;
	}

	/**
	 * Checks whether the given solution is valid according to the constraints of
	 * this problem
	 *
	 * @param solution The solution to check
	 * @return true iff all constraints are fulfilled
	 */
	public boolean checkValidity(final ILPSolution solution) {
		return this.constraints.stream().allMatch(c -> c.checkConstraint(solution));
	}

	/**
	 * Calculates the objective value generated by the given solution
	 *
	 * @param solution The solution to check
	 * @return the value of the objective function for the given solution
	 */
	public double getSolutionValue(final ILPSolution solution) {
		this.applyLazyFixedVariables();
		return this.objective.getSolutionValue(solution);
	}

	@Override
	public String toString() {
		this.applyLazyFixedVariables();
		StringBuilder b = new StringBuilder();
		b.append(this.objective);
		for (ILPConstraint constraint : this.constraints) {
			b.append("\n" + constraint);
		}
		for (java.util.Map.Entry<Integer, Integer> entry : this.fixedVariableValues.entrySet()) {
			b.append("\n" + this.getVariable(entry.getKey()) + " = " + entry.getValue());
		}
		return b.toString();
	}

	/**
	 * Returns a String representation of basic problem information
	 *
	 * @return A String with information about the problem size
	 */
	public String getProblemInformation() {
		int fixed = this.getVariables().size() - this.unfixedVariables.size();
		return ("The ILP to solve has " + this.getConstraints().size() + " constraints and "
				+ this.getVariables().size() + " variables (" + fixed + " prefixed variables, "
				+ this.unfixedVariables.size() + " to find)");
	}

	/**
	 * Defines the comparators that are available for constraints
	 *
	 * @author Robin Oppermann
	 *
	 */
	public enum Comparator {
		/**
		 * &gt; (constraint)
		 */
		gt(">"),
		/**
		 * &gt;= (constraint)
		 */
		ge(">="),
		/**
		 * = (constraint)
		 */
		eq("="),
		/**
		 * &lt;= (constraint)
		 */
		le("<="),
		/**
		 * &lt; (constraint)
		 */
		lt("<");

		private final String stringRepresentation;

		private Comparator(final String stringRepresentation) {
			this.stringRepresentation = stringRepresentation;
		}

		@Override
		public String toString() {
			return this.stringRepresentation;
		}
	}

	/**
	 * Defines the operations that are available for objectives
	 *
	 * @author Robin Oppermann
	 *
	 */
	public enum Objective {
		/**
		 * maximize objective function
		 */
		maximize("MAX"),
		/**
		 * minimize objective function
		 */
		minimize("MIN");

		private final String stringRepresentation;

		private Objective(final String stringRepresentation) {
			this.stringRepresentation = stringRepresentation;
		}

		@Override
		public String toString() {
			return this.stringRepresentation;
		}
	}

	/**
	 * Abstract representation of ILP Constraints
	 *
	 * @author Robin Oppermann
	 *
	 */
	final class ILPConstraint {
		/**
		 * The linear expression of the constraint (left side of the inequation)
		 */
		private final ILPLinearExpression linearExpression;
		/**
		 * Comparator (e.g. <=)
		 */
		private final Comparator comparator;
		/**
		 * The value on the right side of the inequation
		 */
		private double value;

		/**
		 * Name of the constraint
		 */
		private final String name;

		/**
		 * Create a new ILP constraint
		 *
		 * @param linearExpression The linear expression of the constraint (left side of
		 *                         the inequation)
		 * @param comparator       Comparator (e.g. <=)
		 * @param value            The value on the right side of the inequation
		 */
		ILPConstraint(final ILPLinearExpression linearExpression, final Comparator comparator, final double value,
				final String name) {
			this.linearExpression = linearExpression;
			this.comparator = comparator;
			this.value = value;
			this.name = name;
		}

		@Override
		public String toString() {
			return "CONSTRAINT (" + this.name + "): " + this.linearExpression.toString() + " "
					+ this.comparator.toString() + " " + this.value;
		}

		/**
		 * Multiplies the inequation by the given factor
		 *
		 * @param factor
		 */
		void multiplyBy(final double factor) {
			this.linearExpression.multiplyBy(factor);
			this.value *= factor;
		}

		private void removeFixedVariables() {
			ILPProblem.this.fixedVariableValues.forEach((variableID, value) -> {
				double termValue = this.linearExpression.removeTerm(variableID) * value;
				this.value -= termValue;
			});
			this.checkFeasibility();
		}

		/**
		 * Checks if the constraint can still be fulfilled after fixing variables
		 */
		private void checkFeasibility() {
			if (this.isEmpty()) {
				boolean feasible = true;
				feasible = switch (this.comparator) {
					case eq -> 0 == this.value;
					case ge -> 0 >= this.value;
					case gt -> 0 > this.value;
					case lt -> 0 < this.value;
					case le -> 0 <= this.value;
				};
				if (!feasible)
					throw new RuntimeException("The problem is infeasible: " + this.toString());
			}
		}

		/**
		 * Sets the variable to the given fixed value
		 *
		 * @param variableID variable ID
		 * @param value      fixed value of the variable
		 * @return true if the constraint has been changed by this action
		 */
		private boolean fixVariable(final int variableID, final int value) {
			double termValue = this.linearExpression.removeTerm(variableID) * value;
			this.value -= termValue;

			this.checkFeasibility();
			return termValue != 0;
		}

		/**
		 * Checks whether the constraint is fulfilled by the given solution
		 *
		 * @param ilpSolution The solution to test
		 * @return
		 */
		public boolean checkConstraint(final ILPSolution ilpSolution) {
			double solution = this.linearExpression.getSolutionValue(ilpSolution);
			return switch (this.comparator) {
				case ge -> solution >= this.value;
				case le -> solution <= this.value;
				case eq -> solution == this.value;
				default -> throw new IllegalArgumentException("Unsupported comparator: " + this.comparator.toString());
			};
		}

		/**
		 * @return the linearExpression
		 */
		ILPLinearExpression getLinearExpression() {
			return this.linearExpression;
		}

		/**
		 * Checks whether the constraint is empty (i.e. the expression contains no
		 * terms)
		 *
		 * @return
		 */
		private boolean isEmpty() {
			return this.linearExpression.isEmpty();
		}

		/**
		 * @return the comparator
		 */
		Comparator getComparator() {
			return this.comparator;
		}

		/**
		 * @return the value
		 */
		double getValue() {
			return this.value;
		}

		/**
		 * @return the name
		 */
		String getName() {
			return this.name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + this.getOuterType().hashCode();
			result = prime * result + ((this.comparator == null) ? 0 : this.comparator.hashCode());
			result = prime * result + ((this.linearExpression == null) ? 0 : this.linearExpression.hashCode());
			long temp;
			temp = Double.doubleToLongBits(this.value);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (this.getClass() != obj.getClass())
				return false;
			ILPConstraint other = (ILPConstraint) obj;
			if (!this.getOuterType().equals(other.getOuterType()))
				return false;
			if (this.comparator != other.comparator)
				return false;
			if (this.linearExpression == null) {
				if (other.linearExpression != null)
					return false;
			} else if (!this.linearExpression.equals(other.linearExpression))
				return false;
			if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value))
				return false;
			return true;
		}

		private ILPProblem getOuterType() {
			return ILPProblem.this;
		}
	}

	/**
	 * Abstract class representing the objective function of the ILP
	 *
	 * @author Robin Oppermann
	 *
	 */
	final class ILPObjective {
		/**
		 * The linear expression to optimize
		 */
		private final ILPLinearExpression linearExpression;

		/**
		 * Either minimize or maximize the function
		 */
		private final Objective objectiveOperation;

		/**
		 * Value of the already fixed variables multiplied by their coefficients
		 */
		private double fixedVariablesValue = 0;

		/**
		 * Creates a new objective function
		 *
		 * @param linearExpression   The linear expression to optimize
		 * @param objectiveOperation The objective: Either minimize or maximize the
		 *                           objective
		 */
		ILPObjective(final ILPLinearExpression linearExpression, final Objective objectiveOperation) {
			switch (objectiveOperation) {
				case maximize, minimize -> {
				}
				default -> throw new IllegalArgumentException("Unsupported objectiveOperation: " + objectiveOperation.toString());
			}
			this.linearExpression = linearExpression;
			this.objectiveOperation = objectiveOperation;
		}

		/**
		 * Gets the optimized value the solution has reached
		 *
		 * @param ilpSolution The solution to use
		 * @return The value of the solution
		 */
		double getSolutionValue(final ILPSolution ilpSolution) {
			return this.linearExpression.getSolutionValue(ilpSolution) + this.fixedVariablesValue;
		}

		/**
		 * @return the linearExpression
		 */
		ILPLinearExpression getLinearExpression() {
			return this.linearExpression;
		}

		/**
		 * @return the objectiveOperation
		 */
		Objective getObjectiveOperation() {
			return this.objectiveOperation;
		}

		@Override
		public String toString() {
			return "OBJECTIVE: " + this.objectiveOperation.toString() + ": " + this.linearExpression.toString();
		}

		/**
		 * removes variables that have been fixed from the objective
		 */
		private void removeFixedVariables() {
			ILPProblem.this.fixedVariableValues.forEach((variableID, value) -> {
				this.fixVariable(variableID, value);
			});
		}

		/**
		 * Sets the variable to the given fixed value
		 *
		 * @param variableID variable ID
		 * @param value      fixed value of the variable
		 * @return true if the objective has been changed by this action
		 */
		private boolean fixVariable(final int variableID, final int value) {
			double termValue = this.linearExpression.removeTerm(variableID) * value;
			this.fixedVariablesValue += termValue;
			return termValue != 0;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.linearExpression == null) ? 0 : this.linearExpression.hashCode());
			result = prime * result + ((this.objectiveOperation == null) ? 0 : this.objectiveOperation.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (this.getClass() != obj.getClass())
				return false;
			ILPObjective other = (ILPObjective) obj;
			if (this.linearExpression == null) {
				if (other.linearExpression != null)
					return false;
			} else if (!this.linearExpression.equals(other.linearExpression))
				return false;
			if (this.objectiveOperation != other.objectiveOperation)
				return false;
			return true;
		}
	}

	/**
	 * Abstract representation of linear expressions
	 *
	 * @author Robin Oppermann
	 */
	public final class ILPLinearExpression {
		/**
		 * The terms the linear expression uses
		 */
		private final IntToDoubleMap terms = CollectionFactory.cfactory.createIntToDoubleMap();

		/**
		 * Adds a term (variable * coefficient) to the linear expression
		 *
		 * @param variable    The name of the variable
		 * @param coefficient The coefficient of the variable
		 */
		public void addTerm(final String variable, final double coefficient) {
			this.addTerm(ILPProblem.this.getVariableId(variable), coefficient);
		}

		/**
		 * Adds a term (variable * coefficient) to the linear expression
		 *
		 * @param variableID  The id of the variable
		 * @param coefficient The coefficient of the variable
		 */
		void addTerm(final int variableID, final double coefficient) {
			double result = this.terms.addTo(variableID, coefficient);
			if (Double.doubleToLongBits(result) == Double.doubleToLongBits(-coefficient)) {
				this.terms.remove(variableID);
			}
		}

		/**
		 * Multiplies the linear expression by the given factor
		 *
		 * @param factor The factor to multiply by
		 */
		void multiplyBy(final double factor) {
			this.terms.replaceAll((variableID, coefficient) -> coefficient * factor);
		}

		/**
		 * Gets the value of the linear expression using the variable set of the given
		 * solution
		 *
		 * @param ilpSolution The solution to use
		 * @return The value of the linear expression
		 */
		double getSolutionValue(final ILPSolution ilpSolution) {
			double solution = 0;
			for (int variableId : this.terms.keySet()) {
				double coefficient = this.terms.get(variableId);
				solution += coefficient * ilpSolution.getVariable(variableId);
			}
			return solution;
		}

		/**
		 * Builds a String representation of the term
		 *
		 * @param variableId The variable ID of the term's variable
		 * @return
		 */
		private String getTermString(final int variableId) {
			double coefficient = this.terms.get(variableId);
			if (Double.doubleToLongBits(coefficient) == Double.doubleToLongBits(1.0))
				return ILPProblem.this.getVariable(variableId);
			if (Double.doubleToLongBits(coefficient) == Double.doubleToLongBits(-1.0))
				return "-" + ILPProblem.this.getVariable(variableId);
			return "(" + coefficient + " * " + ILPProblem.this.getVariable(variableId) + ")";
		}

		@Override
		public String toString() {
			List<String> termStrings = new LinkedList<>();
			this.terms.keySet().stream().forEach((variableId) -> {
				termStrings.add(this.getTermString(variableId));
			});
			return String.join(" + ", termStrings);
		}

		/**
		 * Returns all variable IDs of variables contained in this expression
		 *
		 * @return
		 */
		Set<Integer> getVariables() {
			return Collections.unmodifiableSet(this.terms.keySet());
		}

		/**
		 * Gets the coefficient of a term in this expression
		 *
		 * @param variableId the id of the variable
		 * @return the coefficient, or 0 if no term for this variable has been defined
		 */
		double getCoefficient(final int variableId) {
			if (this.terms.containsKey(variableId))
				return this.terms.get(variableId);
			return 0;
		}

		/**
		 * @param variableId
		 * @return the coefficient of the variable that was removed
		 */
		double removeTerm(final int variableId) {
			double coefficient = this.getCoefficient(variableId);
			this.terms.remove(variableId);
			return coefficient;
		}

		/**
		 * Returns true if the expression is empty, i.e. contains no terms
		 *
		 * @return true if no terms are contained
		 */
		private boolean isEmpty() {
			return this.terms.isEmpty();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + this.getOuterType().hashCode();
			result = prime * result + ((this.terms == null) ? 0 : this.terms.hashCode());
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (this.getClass() != obj.getClass())
				return false;
			ILPLinearExpression other = (ILPLinearExpression) obj;
			if (!this.getOuterType().equals(other.getOuterType()))
				return false;
			if (this.terms == null) {
				if (other.terms != null)
					return false;
			} else {
				if (other.terms == null)
					return false;
				if (this.terms.size() != other.terms.size())
					return false;
				for (int variableID : this.terms.keySet()) {
					if (!other.terms.containsKey(variableID))
						return false;
					if (this.terms.get(variableID) != other.terms.get(variableID))
						return false;
				}
			}
			return true;
		}

		private ILPProblem getOuterType() {
			return ILPProblem.this;
		}
	}

	/**
	 * This class is used to make the solution found by the ILP Solver accessible.
	 *
	 * @author Robin Oppermann
	 */
	public final class ILPSolution {
		/**
		 * Mapping of variables to the found solutions
		 */
		private final IntToIntMap variableAllocations;
		/**
		 * Whether the found solution is optimal
		 */
		private final boolean optimal;

		/**
		 * The value of the objective function generated for the current solution
		 */
		private final double solutionValue;

		/**
		 * Initializes a new ILPSolution
		 *
		 * @param variableAllocations Mapping of variables to the found solutions
		 * @param optimal             Whether the found solution is optimal
		 */
		private ILPSolution(final IntToIntMap variableAllocations, final boolean optimal, final double solutionValue) {
			super();
			this.variableAllocations = variableAllocations;
			this.optimal = optimal;
			this.solutionValue = solutionValue + ILPProblem.this.objective.fixedVariablesValue;
		}

		/**
		 * Returns the value of a variable
		 *
		 * @param variable The variable identifier
		 * @return The value of the variable in the solution
		 */
		public int getVariable(final String variable) {
			return this.getVariable(ILPProblem.this.getVariableId(variable));
		}

		/**
		 * Returns the value of the solution for the variable
		 *
		 * @param variableId the id of the variable
		 * @return
		 */
		int getVariable(final int variableId) {
			if (ILPProblem.this.fixedVariableValues.containsKey(variableId))
				return ILPProblem.this.fixedVariableValues.get(variableId);
			return this.variableAllocations.get(variableId);
		}

		/**
		 * @return the solutionValue
		 */
		public double getSolutionValue() {
			return this.solutionValue;
		}

		/**
		 * @return Whether the found solution is optimal
		 */
		public boolean isOptimal() {
			return this.optimal;
		}

		/**
		 * Creates a string representation of the found solution
		 */
		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append("Solution value: " + this.solutionValue + "\n");
			for (int variableId : this.variableAllocations.keySet()) {
				s.append("(" + this.getVariable(variableId) + "," + this.variableAllocations.get(variableId) + ")\n");
			}
			return s.toString();
		}

		/**
		 * Returns a string representing basic information about the found solution
		 *
		 * @return A string containing information about the solution size and value.
		 */
		String getSolutionInformation() {
			int fixed = ILPProblem.this.getVariables().size()
					- ILPProblem.this.getVariableIdsOfUnfixedVariables().size();
			return "Found solution for " + ILPProblem.this.getVariables().size() + " variables (" + fixed
					+ " prefixed). Solution value = " + this.solutionValue;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(this.solutionValue);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + ((this.variableAllocations == null) ? 0 : this.variableAllocations.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (this.getClass() != obj.getClass())
				return false;
			ILPSolution other = (ILPSolution) obj;
			if (Double.doubleToLongBits(this.solutionValue) != Double.doubleToLongBits(other.solutionValue))
				return false;
			if (this.variableAllocations == null) {
				if (other.variableAllocations != null)
					return false;
			} else if (!this.variableAllocations.equals(other.variableAllocations))
				return false;
			return true;
		}
	}
}