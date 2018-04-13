/**
 * 
 */
package org.emoflon.ibex.tgg.util.ilp;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.set.hash.THashSet;

/**
 * This class is used to define ILPProblems that can be given to {@link ILPSolver} to be solved.
 * An instance can be obtained using the {@link ILPFactory}. Afterwards constraints and the objective can be defined and added.
 * 
 * @author Robin Oppermann
 *
 */
public final class ILPProblem {
	
	private int variableCounter = 1;

	/**
	 * Contains all variables that have been defined
	 */
	private final TObjectIntHashMap<String> variables = new TObjectIntHashMap<String>();
	private final TIntObjectHashMap<String> variableIDsToVariables = new TIntObjectHashMap<String>();
	/**
	 * Set of constraints that have been defined using addConstraint
	 */
	private final THashSet<ILPConstraint> constraints = new THashSet<>();
	/**
	 * The objective function that has been defined using setObjective
	 */
	private ILPObjective objective = null;

	/**
	 * Creates a new ILPProblem. Instances can be obtained using the {@link ILPFactory}
	 */
	ILPProblem() {}

	/**
	 * Returns the variables that have been defined. New variables can be defined by using them within a term.
	 * 
	 * @return the variables that have been defined
	 */
	public Collection<String> getVariables() {
		return Collections.unmodifiableCollection(variables.keySet());
	}
	
	public int[] getVariableIds() {
		return variableIDsToVariables.keys();
	}

	/**
	 * Adds a variable to the set of defined variables. This method is called when using a new variable within a term.
	 * This method should be overwritten if a special handling of variables within for the concrete ILP Solver is required.
	 * @param	variable The (unique) name of the variable.
	 */
	int addVariable(String variable) {
		if(!variables.contains(variable)) {
			variables.put(variable, variableCounter);
			variableIDsToVariables.put(variableCounter, variable);
			return variableCounter++;
		}
		return variables.get(variable);
	}
	
	int getVariableId(String variable) {
		return this.variables.get(variable);
	}
	
	String getVariable(int variableId) {
		return this.variableIDsToVariables.get(variableId);
	}

	/**
	 * Creates a new linear expression using the sum of the given terms. <br>
	 * The linear expression is formed as follows: (t1 + t2 + t3 + ...) where ti is one of the terms. 
	 * 
	 * @param	terms	The terms that are used in the linear expression.
	 * @return	The linear expression that has been created.
	 */
	public ILPLinearExpression createLinearExpression(ILPTerm...terms) {
		ILPLinearExpression expr = new ILPLinearExpression();
		for (ILPTerm term : terms) {
			expr.addTerm(term);
		}
		return expr;
	}

	/**
	 * Creates an ILP Term that can be used within linear expressions.
	 * The term is of the form (c*x) where x is the variable and c is the coefficient.
	 * 
	 * @param	variable	The variable used within the term.
	 * @param	coefficient	The coefficient of the term.
	 * @return	The ILPTerm that has been created.
	 */
	public ILPTerm createTerm(String variable, double coefficient) {
		return new ILPTerm(variable, coefficient);
	}
	
	ILPSolution createILPSolution(TIntIntHashMap variableAllocations, boolean optimal, double solutionValue) {
		return new ILPSolution(variableAllocations, optimal, solutionValue);
	}
	
	/**
	 * Creates an ILP Term that can be used within linear expressions.
	 * The term is of the form (c*x) where x is the variable and c is the coefficient.
	 * 
	 * @param	variableId	The ID of the variable used within the term.
	 * @param	coefficient	The coefficient of the term.
	 * @return	The ILPTerm that has been created.
	 */
	ILPTerm createTerm(int variableId, double coefficient) {
		return new ILPTerm(variableId, coefficient);
	}

	/**
	 * Adds a constraint to the ILP. <br>
	 * The constraint is formed as follows:  (LE &lt;= V) if LE is the linear Expression, &lt;= is the comparator and V is the value. 
	 * @param	linearExpression	The linear expression containing the sum of the terms
	 * @param	comparator			The comparator (e.g. &lt;=, &gt;=)
	 * @param	value				The value 
	 * @param	name				The name of the constraint. Naming constraints is not supported by all solvers.
	 */
	public ILPConstraint addConstraint(ILPLinearExpression linearExpression, Comparator comparator, double value, String name) {
		ILPConstraint constr = new ILPConstraint(linearExpression, comparator, value, name);
		this.addConstraint(constr);
		return constr;
	}

	/**
	 * Adds the constraint the set of constraints
	 * @param constraint the constraint to add
	 */
	void addConstraint(ILPConstraint constraint) {
		if(!this.constraints.contains(constraint)) {
			System.out.println("Constraint added ("+constraints.size()+")");
			this.constraints.add(constraint);
		}
	}

	/**
	 * Retrieve all defined constraints
	 * @return the constraints that have been defined
	 */
	public final Collection<ILPConstraint> getConstraints() {
		return Collections.unmodifiableCollection(this.constraints);
	}

	/**
	 * Retrieve the objective function
	 * @return The objective function
	 */
	public final ILPObjective getObjective() {
		return this.objective;
	}

	/**
	 * Sets the objective of the ILP that has to be either maximized or minimized.
	 * @param 	linearExpression	The linear expression that has to be optimized
	 * @param 	operation			Whether the operation should be maximized or minimized.
	 */
	public ILPObjective setObjective(ILPLinearExpression linearExpression, Objective operation) {
		ILPObjective objective = new ILPObjective(linearExpression, operation);
		this.setObjective(objective);
		return objective;
	}

	/**
	 * Sets the objective of the ILP
	 * @param objective the objective
	 */
	void setObjective(ILPObjective objective) {
		this.objective = objective;
	}
	
	/**
	 * Checks whether the given solution is valid according to the constraints of this problem
	 * @param solution The solution to check
	 * @return true iff all constraints are fulfilled
	 */
	public boolean checkValidity(ILPSolution solution) {
		return this.constraints.stream().allMatch(c -> c.checkConstraint(solution));
	}
	
	/**
	 * Calculates the objective value generated by the given solution
	 * @param solution The solution to check
	 * @return the value of the objective function for the given solution
	 */
	public double getSolutionValue(ILPSolution solution) {
		return this.objective.getSolutionValue(solution);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(objective);
		for(ILPConstraint constraint : constraints) {
			b.append("\n" + constraint);
		}
		return b.toString();
	}

	/**
	 * Defines the comparators that are available for constraints
	 * @author Robin Oppermann
	 *
	 */
	public enum Comparator {
		/**
		 * &gt; (constraint)
		 */
		gt(">="),
		/**
		 * &gt;= (constraint)
		 */
		ge(">"),
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

		private Comparator(String stringRepresentation) {
			this.stringRepresentation = stringRepresentation;
		}

		@Override
		public String toString() {
			return stringRepresentation;
		}
	}
	
	/**
	 * Defines the operations that are available for objectives
	 * @author Robin Oppermann
	 *
	 */
	public enum Objective {
		maximize("MAX"),
		/**
		 * minimize objective
		 */
		minimize("MIN");

		private final String stringRepresentation;

		private Objective(String stringRepresentation) {
			this.stringRepresentation = stringRepresentation;
		}

		@Override
		public String toString() {
			return stringRepresentation;
		}
	}

	/**
	 * A linear term of the form (c*x) where x is the variable and c is the coefficient.
	 * 
	 * @author Robin Oppermann
	 *
	 */
	public final class ILPTerm {
		/**
		 * The variable identifier
		 */
		private final int variableId;
		/**
		 * The coefficient
		 */
		private double coefficient;

		/**
		 * Creates a new term
		 * @param variable The variable
		 * @param coefficient The coefficient
		 */
		private ILPTerm(String variable, double coefficient) {
			this(addVariable(variable), coefficient);
		}
		
		/**
		 * Creates a new term
		 * @param variableId The id of the variable
		 * @param coefficient The coefficient
		 */
		private ILPTerm(int variableId, double coefficient) {
			this.variableId = variableId;
			this.coefficient = coefficient;
		}

		/**
		 * Multiplies the term by the given factor. This can be used to get rid of non-integer coefficients.
		 * @param factor The factor to multiply the term by
		 */
		void multiplyBy(double factor) {
			this.coefficient *= factor; 
		}

		/**
		 * @return the variable
		 */
		public String getVariable() {
			return variableIDsToVariables.get(variableId);
		}
		
		/**
		 * @return the id of the variable
		 */
		int getVariableId() {
			return this.variableId;
		}

		/**
		 * @return the coefficient
		 */
		public double getCoefficient() {
			return coefficient;
		}

		/**
		 * Returns the value of the term when calculated with the variables of the given solution
		 * @param ilpSolution	The solution to use
		 * @return The calculated value
		 */
		final double getSolutionValue(ILPSolution ilpSolution) {
			return coefficient * ilpSolution.getVariable(variableId);
		}

		@Override
		public String toString() {
			if(Double.doubleToLongBits(coefficient) == Double.doubleToLongBits(1.0)) {
				return this.getVariable();
			}
			if(Double.doubleToLongBits(coefficient) == Double.doubleToLongBits(-1.0)) {
				return "-" + this.getVariable();
			}
			return "("+this.coefficient + " * " + this.getVariable()+")";
		}
		
		

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			long temp;
			temp = Double.doubleToLongBits(coefficient);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + variableId;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ILPTerm other = (ILPTerm) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (Double.doubleToLongBits(coefficient) != Double.doubleToLongBits(other.coefficient))
				return false;
			if (variableId != other.variableId)
				return false;
			return true;
		}

		private ILPProblem getOuterType() {
			return ILPProblem.this;
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
		 * @param linearExpression	The linear expression of the constraint (left side of the inequation)
		 * @param comparator		Comparator (e.g. <=)
		 * @param value				The value on the right side of the inequation
		 */
		ILPConstraint(ILPLinearExpression linearExpression, Comparator comparator, double value, String name) {
			this.linearExpression = linearExpression;
			this.comparator = comparator;
			this.value = value;
			this.name = name;
		}

		@Override
		public String toString() {
			return "CONSTRAINT ("+name+"): "+linearExpression.toString() + " "+ comparator.toString() +" " + value;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((comparator == null) ? 0 : comparator.hashCode());
			result = prime * result + ((linearExpression == null) ? 0 : linearExpression.hashCode());
			long temp;
			temp = Double.doubleToLongBits(value);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ILPConstraint other = (ILPConstraint) obj;
			if (comparator != other.comparator) {
				return false;
			}
			if (linearExpression == null) {
				if (other.linearExpression != null) {
					return false;
				}
			} else if (!linearExpression.equals(other.linearExpression)) {
				return false;
			}
			if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value)) {
				return false;
			}
			return true;
		}

		/**
		 * Multiplies the inequation by the given factor
		 * @param factor
		 */
		void multiplyBy(double factor) {
			this.linearExpression.multiplyBy(factor);
			this.value *= factor;
		}

		/**
		 * Checks whether the constraint is fulfilled by the given solution
		 * @param ilpSolution The solution to test
		 * @return
		 */
		public final boolean checkConstraint(ILPSolution ilpSolution) {
			double solution = linearExpression.getSolutionValue(ilpSolution);
			switch(comparator) {
			case ge:
				return solution >= value;
			case le:
				return solution <= value;
			case eq:
				return solution == value;
			default:
				throw new IllegalArgumentException("Unsupported comparator: "+comparator.toString());
			}
		}
		
		/**
		 * @return the linearExpression
		 */
		ILPLinearExpression getLinearExpression() {
			return linearExpression;
		}

		/**
		 * @return the comparator
		 */
		Comparator getComparator() {
			return comparator;
		}

		/**
		 * @return the value
		 */
		double getValue() {
			return value;
		}

		/**
		 * @return the name
		 */
		String getName() {
			return name;
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
		 * Creates a new objective function
		 * 
		 * @param linearExpression		The linear expression to optimize
		 * @param objectiveOperation	The objective: Either minimize or maximize the objective
		 */
		ILPObjective(ILPLinearExpression linearExpression, Objective objectiveOperation) {
			switch(objectiveOperation) {
			case maximize:
			case minimize:
				break;
			default:
				throw new IllegalArgumentException("Unsupported objectiveOperation: "+objectiveOperation.toString());
			}
			this.linearExpression = linearExpression;
			this.objectiveOperation = objectiveOperation;
		}

		/**
		 * Gets the optimized value the solution has reached
		 * @param ilpSolution	The solution to use
		 * @return	The value of the solution
		 */
		double getSolutionValue(ILPSolution ilpSolution) {
			return linearExpression.getSolutionValue(ilpSolution);
		}
		
		/**
		 * @return the linearExpression
		 */
		ILPLinearExpression getLinearExpression() {
			return linearExpression;
		}

		/**
		 * @return the objectiveOperation
		 */
		Objective getObjectiveOperation() {
			return objectiveOperation;
		}

		@Override
		public String toString() {
			return "OBJECTIVE: "+this.objectiveOperation.toString() + ": "+ this.linearExpression.toString();
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((linearExpression == null) ? 0 : linearExpression.hashCode());
			result = prime * result + ((objectiveOperation == null) ? 0 : objectiveOperation.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ILPObjective other = (ILPObjective) obj;
			if (linearExpression == null) {
				if (other.linearExpression != null) {
					return false;
				}
			} else if (!linearExpression.equals(other.linearExpression)) {
				return false;
			}
			if (objectiveOperation != other.objectiveOperation) {
				return false;
			}
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
		private final TIntObjectHashMap<ILPTerm> terms = new TIntObjectHashMap<ILPProblem.ILPTerm>();

		/**
		 * Adds a term to the linear expression (additional summand)
		 * @param term	The term to add
		 */
		public void addTerm(ILPTerm term) {
			//check existing terms if there is one with the same coef
			if(terms.containsKey(term.variableId)) {
				ILPTerm existingTerm = terms.get(term.variableId);
				//update coefficient
				existingTerm.coefficient += term.coefficient;
				//check if term not 0
				if(Double.doubleToLongBits(existingTerm.coefficient) == Double.doubleToLongBits(0)) {
					terms.remove(term.variableId);
				}
			} else {
				terms.put(term.variableId, term);
			}
		}

		/**
		 * Multiplies the linear expression by the given factor
		 * @param factor	The factor to multiply by
		 */
		void multiplyBy(double factor) {
			for (ILPTerm term : terms.valueCollection()) {
				term.multiplyBy(factor);
			}
		}

		/**
		 * Gets the value of the linear expression using the variable set of the given solution
		 * @param ilpSolution	The solution to use
		 * @return	The value of the linear expression
		 */
		final double getSolutionValue(ILPSolution ilpSolution) {
			double solution = 0;
			for(ILPTerm term : terms.valueCollection()) {
				solution += term.getSolutionValue(ilpSolution);
			}
			return solution;
		}

		@Override
		public String toString() {
			return String.join(" + ", terms.valueCollection().stream().map(t -> t.toString()).collect(Collectors.toList()));
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result;
			result = prime * result + ((terms == null) ? 0 : terms.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ILPLinearExpression other = (ILPLinearExpression) obj;
			if (terms == null) {
				if (other.terms != null) {
					return false;
				}
			} else if (!terms.equals(other.terms)) {
				return false;
			}
			return true;
		}

		/**
		 * @return the terms
		 */
		Collection<ILPTerm> getTerms() {
			return Collections.unmodifiableCollection(terms.valueCollection());
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
		private final TIntIntHashMap variableAllocations;
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
		 * @param variableAllocations	Mapping of variables to the found solutions
		 * @param optimal			Whether the found solution is optimal 
		 */
		private ILPSolution(TIntIntHashMap variableAllocations, boolean optimal, double solutionValue) {
			super();
			this.variableAllocations = variableAllocations;
			this.optimal = optimal;
			this.solutionValue = solutionValue;
		}

		/**
		 * Returns the value of a variable 
		 * @param 	variable	The variable identifier
		 * @return	The value of the variable in the solution
		 */
		public int getVariable(String variable) {
			return getVariable(getVariableId(variable));
		}
		
		int getVariable(int variableId) {
			return variableAllocations.get(variableId);
		}

		/**
		 * @return the solutionValue
		 */
		public double getSolutionValue() {
			return solutionValue;
		}

		/**
		 * @return	Whether the found solution is optimal
		 */
		public boolean isOptimal() {
			return optimal;
		}
		/**
		 * Creates a string representation of the found solution
		 */
		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append("Solution value: "+solutionValue+"\n");
			for (int variableId : variableAllocations.keys()) {
				s.append("("+getVariable(variableId)+","+variableAllocations.get(variableId)+")\n");
			}
			return s.toString();
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(solutionValue);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + ((variableAllocations == null) ? 0 : variableAllocations.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ILPSolution other = (ILPSolution) obj;
			if (Double.doubleToLongBits(solutionValue) != Double.doubleToLongBits(other.solutionValue)) {
				return false;
			}
			if (variableAllocations == null) {
				if (other.variableAllocations != null) {
					return false;
				}
			} else if (!variableAllocations.equals(other.variableAllocations)) {
				return false;
			}
			return true;
		}
	}
}