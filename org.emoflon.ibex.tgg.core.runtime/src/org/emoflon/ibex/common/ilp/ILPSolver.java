package org.emoflon.ibex.common.ilp;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.emoflon.ibex.common.ilp.ILPSolver.ILPTerm;

public abstract class ILPSolver {
	
	private final Set<String> variables = new HashSet<>();
	/**
	 * @return the variables
	 */
	public Set<String> getVariables() {
		return Collections.unmodifiableSet(variables);
	}
	
	public void addVariable(String variable) {
		variables.add(variable);
	}
	
	public abstract ILPLinearExpression createLinearExpression(ILPTerm...terms);
	public ILPTerm createTerm(String variable, double factor) {
		return new ILPTerm(variable, factor);
	}
	public abstract void addConstraint(ILPLinearExpression linearExpression, Operation comparator, double value, String name);
	public abstract void setObjective(ILPLinearExpression linearExpression, Operation operation);
	public abstract ILPSolution solveILP(); 
	
	public enum Operation {
		gt,
		ge,
		eq,
		le,
		lt,
		maximize,
		minimize
	}	
	
	public class ILPTerm {
		protected final String variable;
		protected final double factor;
		
		private ILPTerm(String variable, double factor) {
			if(!variables.contains(variable)) {
				addVariable(variable);
			}
			this.variable = variable;
			this.factor = factor;
		}

		/**
		 * @return the variable
		 */
		public String getVariable() {
			return variable;
		}

		/**
		 * @return the factor
		 */
		public double getFactor() {
			return factor;
		}
	}
	
	public abstract class ILPConstraint {
		
	}
	
	public abstract class ILPLinearExpression {
		public abstract void addTerm(ILPTerm term);
	}
	
	public final class ILPSolution {
		
		protected final Map<String, Integer> solutionVariables;
		protected final boolean optimal;
		
		public ILPSolution(Map<String, Integer> solutionVariables, boolean optimal) {
			super();
			this.solutionVariables = solutionVariables;
			this.optimal = optimal;
		}
		public final int getVariable(String variable) {
			return solutionVariables.get(variable);
		}
		public boolean isOptimal() {
			return optimal;
		}
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