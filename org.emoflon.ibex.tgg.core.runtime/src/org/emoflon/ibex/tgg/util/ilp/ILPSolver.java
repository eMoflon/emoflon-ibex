package org.emoflon.ibex.tgg.util.ilp;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import gnu.trove.set.hash.THashSet;

/**
 * This class is used to abstract from the usage of a concrete ILP solver. It provides common methods to define ILP problems, 
 * start finding the solution and access to the found solution.
 * 
 * @author Robin Oppermann
 */
public abstract class ILPSolver {
	
	/**
	 * 
	 */
	protected ILPSolver() {
		
	}
	
	/**
	 * Contains all variables that have been defined
	 */
	private final THashSet<String> variables = new THashSet<>();
	
	/**
	 * Set of constraints that have been defined using addConstraint
	 */
	private final THashSet<ILPConstraint> constraints = new THashSet<>();
	/**
	 * The objective function that has been defined using setObjective
	 */
	private ILPObjective objective = null;
	
	/**
	 * Returns the variables that have been defined. New variables can be defined by using them within a term.
	 * 
	 * @return the variables that have been defined
	 */
	public Set<String> getVariables() {
		return Collections.unmodifiableSet(variables);
	}
	
	/**
	 * Adds a variable to the set of defined variables. This method is called when using a new variable within a term.
	 * This method should be overwritten if a special handling of variables within for the concrete ILP Solver is required.
	 * @param	variable The (unique) name of the variable.
	 */
	protected void addVariable(String variable) {
		variables.add(variable);
	}
	
	/**
	 * Creates a new linear expression using the sum of the given terms. <br>
	 * The linear expression is formed as follows: (t1 + t2 + t3 + ...) where ti is one of the terms. 
	 * 
	 * @param	terms	The terms that are used in the linear expression.
	 * @return	The linear expression that has been created.
	 */
	public abstract ILPLinearExpression createLinearExpression(ILPTerm...terms);
	
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
	
	/**
	 * Adds a constraint to the ILP. <br>
	 * The constraint is formed as follows:  (LE &lt;= V) if LE is the linear Expression, &lt;= is the comparator and V is the value. 
	 * @param	linearExpression	The linear expression containing the sum of the terms
	 * @param	comparator			The comparator (e.g. &lt;=, &gt;=)
	 * @param	value				The value 
	 * @param	name				The name of the constraint. Naming constraints is not supported by all solvers.
	 */
	public abstract ILPConstraint addConstraint(ILPLinearExpression linearExpression, Operation comparator, double value, String name);
	
	/**
	 * Adds the constraint the set of constraints
	 * @param constraint the constraint to add
	 */
	protected void addConstraint(ILPConstraint constraint) {
		if(!this.constraints.contains(constraint)) {
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
	public abstract ILPObjective setObjective(ILPLinearExpression linearExpression, Operation operation);
	
	/**
	 * Sets the objective of the ILP
	 * @param objective the objective
	 */
	protected void setObjective(ILPObjective objective) {
		this.objective = objective;
	}
	
	/**
	 * Starts solving the ILP
	 * @return The solution of the ILP (if found)
	 * @throws Exception 
	 */
	public abstract ILPSolution solveILP() throws Exception;
	
	
	
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
	 * Defines the operations that are available for constraints and objectives
	 * @author RobinO
	 *
	 */
	public enum Operation {
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
		lt("<"),
		/**
		 * maximize objective
		 */
		maximize("MAX"),
		/**
		 * minimize objective
		 */
		minimize("MIN");
		
		private final String stringRepresentation;
		
		private Operation(String stringRepresentation) {
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
	public class ILPTerm {
		/**
		 * The variable identifier
		 */
		private final String variable;
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
			if(!variables.contains(variable)) {
				addVariable(variable);
			}
			this.variable = variable;
			this.coefficient = coefficient;
		}
		
		/**
		 * Multiplies the term by the given factor. This can be used to get rid of non-integer coefficients.
		 * @param factor The factor to multiply the term by
		 */
		protected void multiplyBy(double factor) {
			this.coefficient *= factor; 
		}

		/**
		 * @return the variable
		 */
		public String getVariable() {
			return variable;
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
		public final double getSolutionValue(ILPSolution ilpSolution) {
			return coefficient * ilpSolution.getVariable(variable);
		}

		@Override
		public String toString() {
			if(Double.doubleToLongBits(coefficient) == Double.doubleToLongBits(1.0)) {
				return this.variable;
			}
			if(Double.doubleToLongBits(coefficient) == Double.doubleToLongBits(-1.0)) {
				return "-" + this.variable;
			}
			return "("+this.coefficient + " * " + this.variable+")";
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			long temp;
			temp = Double.doubleToLongBits(coefficient);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + ((variable == null) ? 0 : variable.hashCode());
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
			ILPTerm other = (ILPTerm) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (Double.doubleToLongBits(coefficient) != Double.doubleToLongBits(other.coefficient)) {
				return false;
			}
			if (variable == null) {
				if (other.variable != null) {
					return false;
				}
			} else if (!variable.equals(other.variable)) {
				return false;
			}
			return true;
		}

		private ILPSolver getOuterType() {
			return ILPSolver.this;
		}

		
	}
	
	/**
	 * Abstract representation of ILP Constraints
	 * 
	 * @author Robin Oppermann
	 *
	 */
	protected abstract class ILPConstraint {
		/**
		 * The linear expression of the constraint (left side of the inequation)
		 */
		protected final ILPLinearExpression linearExpression;
		/**
		 * Comparator (e.g. <=)
		 */
		protected final Operation comparator;
		/**
		 * The value on the right side of the inequation
		 */
		protected double value;
		
		/**
		 * Name of the constraint
		 */
		protected final String name;
		
		/**
		 * Create a new ILP constraint
		 * @param linearExpression	The linear expression of the constraint (left side of the inequation)
		 * @param comparator		Comparator (e.g. <=)
		 * @param value				The value on the right side of the inequation
		 */
		protected ILPConstraint(ILPLinearExpression linearExpression, Operation comparator, double value, String name) {
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
		protected void multiplyBy(double factor) {
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
	}
	
	/**
	 * Abstract class representing the objective function of the ILP
	 * 
	 * @author Robin Oppermann
	 *
	 */
	protected abstract class ILPObjective {
		/**
		 * The linear expression to optimize
		 */
		protected final ILPLinearExpression linearExpression;
		/**
		 * Either minimize or maximize the function
		 */
		protected final Operation objectiveOperation;
		
		/**
		 * Creates a new objective function
		 * 
		 * @param linearExpression		The linear expression to optimize
		 * @param objectiveOperation	The objective: Either minimize or maximize the objective
		 */
		protected ILPObjective(ILPLinearExpression linearExpression, Operation objectiveOperation) {
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
		public double getSolutionValue(ILPSolution ilpSolution) {
			return linearExpression.getSolutionValue(ilpSolution);
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
	public abstract class ILPLinearExpression {
		/**
		 * The terms the linear expression uses
		 */
		private final Set<ILPTerm> terms = new HashSet<>();
		
		/**
		 * Adds a term to the linear expression (additional summand)
		 * @param term	The term to add
		 */
		public void addTerm(ILPTerm term) {
			//check existing terms if there is one with the same coef
			Iterator<ILPTerm> it = terms.iterator();
			while(it.hasNext()) {
				ILPTerm existingTerm = it.next();
				if(existingTerm.variable.equals(term.variable)) {
					//update coefficient
					existingTerm.coefficient += term.coefficient;
					//check if term not 0
					if(Double.doubleToLongBits(existingTerm.coefficient) == Double.doubleToLongBits(0)) {
						it.remove();
					}
					return;
				}
			}
			
			terms.add(term);
		}
		
		/**
		 * Multiplies the linear expression by the given factor
		 * @param factor	The factor to multiply by
		 */
		protected void multiplyBy(double factor) {
			for (ILPTerm term : terms) {
				term.multiplyBy(factor);
			}
		}
		
		/**
		 * Gets the value of the linear expression using the variable set of the given solution
		 * @param ilpSolution	The solution to use
		 * @return	The value of the linear expression
		 */
		public final double getSolutionValue(ILPSolution ilpSolution) {
			double solution = 0;
			for(ILPTerm term : terms) {
				solution += term.getSolutionValue(ilpSolution);
			}
			return solution;
		}
		
		
		
		@Override
		public String toString() {
			return String.join(" + ", terms.stream().map(t -> t.toString()).collect(Collectors.toList()));
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
		protected Set<ILPTerm> getTerms() {
			return Collections.unmodifiableSet(terms);
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
		private final Map<String, Integer> solutionVariables;
		/**
		 * Whether the found solution is optimal 
		 */
		private final boolean optimal;
		
		private final double solutionValue;
		
		/**
		 * Initializes a new ILPSolution
		 * @param solutionVariables	Mapping of variables to the found solutions
		 * @param optimal			Whether the found solution is optimal 
		 */
		protected ILPSolution(Map<String, Integer> solutionVariables, boolean optimal, double solutionValue) {
			super();
			this.solutionVariables = solutionVariables;
			this.optimal = optimal;
			this.solutionValue = solutionValue;
		}
		
		/**
		 * Returns the value of a variable 
		 * @param 	variable	The variable identifier
		 * @return	The value of the variable in the solution
		 */
		public final int getVariable(String variable) {
			return solutionVariables.get(variable);
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
			for (Entry<String, Integer> solution : solutionVariables.entrySet()) {
				s.append("("+solution.getKey()+","+solution.getValue()+")\n");
			}
			return s.toString();
		}
	}
}