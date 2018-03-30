package org.emoflon.ibex.tgg.util.ilp;

import java.util.Map;
import java.util.Map.Entry;

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

	
	/**
	 * The value of the objective function generated for the current solution
	 */
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
		s.append("Solution value: "+solutionValue+"\n");
		for (Entry<String, Integer> solution : solutionVariables.entrySet()) {
			s.append("("+solution.getKey()+","+solution.getValue()+")\n");
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
		result = prime * result + ((solutionVariables == null) ? 0 : solutionVariables.hashCode());
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
		if (solutionVariables == null) {
			if (other.solutionVariables != null) {
				return false;
			}
		} else if (!solutionVariables.equals(other.solutionVariables)) {
			return false;
		}
		return true;
	}
}