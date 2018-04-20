package org.emoflon.ibex.tgg.util.ilp;

import java.math.BigInteger;

import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPConstraint;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPObjective;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.sat4j.core.Vec;
import org.sat4j.core.VecInt;
import org.sat4j.pb.IPBSolver;
import org.sat4j.pb.ObjectiveFunction;
import org.sat4j.pb.OptToPBSATAdapter;
import org.sat4j.pb.PseudoOptDecorator;
import org.sat4j.pb.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;

import gnu.trove.map.hash.TIntIntHashMap;

/**
 * This class is a wrapper around SAT4J allowing the usage of this ILPSolver with the unified API of the {@link ILPSolver} class.
 * SAT4J comes with eclipse but is only able to solve pseudo-boolean ILP problems. To use SAT4J your project has to have Sat4J as Plugin Dependencies.
 * 
 *  The SAT4J Javadocs can be found at:
 *  <li> http://www.sat4j.org/maven234/org.ow2.sat4j.pb/apidocs/index.html </li>
 *	<li> http://www.sat4j.org/maven234/org.ow2.sat4j.core/apidocs/index.html </li>
 * 
 * @author Robin Oppermann
 *
 */
final class Sat4JWrapper extends ILPSolver {
	/**
	 * The SAT4J pseudo-boolean solver
	 */
	private IPBSolver solver;
	
	private static final int MIN_TIMEOUT = 3;
	private static final int MAX_TIMEOUT = 60*60;

	/**
	 * Creates a new SAT4JWrapper
	 */
	Sat4JWrapper(ILPProblem ilpProblem) {
		super(ilpProblem);
	}

	@Override
	public ILPSolution solveILP() throws ContradictionException {
		System.out.println("The ILP to solve has "+this.ilpProblem.getConstraints().size()+" constraints and "+this.ilpProblem.getVariables().size()+ " variables");
		int currentTimeout = this.ilpProblem.getVariables().size();
		currentTimeout = MIN_TIMEOUT + (int) Math.ceil(Math.pow(1.16, Math.sqrt(currentTimeout)));
		currentTimeout = Math.min(currentTimeout, MAX_TIMEOUT);
		ILPSolution solution = null;
		while(solution == null && currentTimeout <= MAX_TIMEOUT) {
			System.out.println("Attempting to solve ILP. Timeout="+currentTimeout+" seconds.");
			try {
				solution = solveILP(currentTimeout);
			} catch(TimeoutException e) {
				System.err.println("Could not solve ILP within "+currentTimeout+" seconds");
				currentTimeout*=2;
			}
		}
		return solution;
	}
	
	/**
	 * Starts the solver with the specified timeout.
	 * @param timeout	The timeout for the solver. If the timeout is too low it might happen that
	 * 			<li>	the solver does not find a solution even though there is one </li>
	 * 			<li>	the solver finds a solution but it is not the optimal solution yet </li>
	 * @return
	 * @throws ContradictionException
	 * @throws TimeoutException
	 */
	private ILPSolution solveILP(int timeout) throws ContradictionException, TimeoutException {
		solver = SolverFactory.newDefaultOptimizer();
		
		for(ILPConstraint constraint : this.ilpProblem.getConstraints()) {
			this.registerConstraint(constraint);
		}
		this.registerObjective(this.ilpProblem.getObjective());
		
		OptToPBSATAdapter optimizer = new OptToPBSATAdapter(new PseudoOptDecorator(solver));
		optimizer.setTimeout(timeout);
		optimizer.setVerbose(true);
		if(optimizer.isSatisfiable()) {
			int[] model = solver.model();
			TIntIntHashMap variableSolutions = new TIntIntHashMap();
			for(int i : model) {
				int solution = i>0? 1 : 0;
				for(int var : this.ilpProblem.getVariableIds()) {
					if(Math.abs(i) == var) {
						variableSolutions.put(var, solution);
						break;
					}
				}
			}
			boolean optimal = optimizer.isOptimal();
			ILPSolution solution = this.ilpProblem.createILPSolution(variableSolutions, optimal, -1);
			double optimum = this.ilpProblem.getObjective().getSolutionValue(solution);
			System.out.println("Solution found: "+optimum + " - Optimal: "+optimal);
			return this.ilpProblem.createILPSolution(variableSolutions, optimal, optimum);
		}
		return null;
	}

	/**
	 * Converts the term representation into the integer vector of variable literals of SAT4J
	 * The string identifiers are replaced by integer identifiers 
	 * @return The integer vector representing the literals of the linear expression
	 */
	private IVecInt getLiterals(ILPLinearExpression linearExpression) {
		IVecInt vec = new VecInt();
		for (int variableId : linearExpression.getVariables()) {
			vec.push(variableId);
		}
		return vec;
	}

	/**
	 * Converts the term representation into the integer vector of coefficients of SAT4J
	 * @return The BigInteger vector of the coefficients
	 */
	private IVec<BigInteger> getCoefs(ILPLinearExpression linearExpression) {
		IVec<BigInteger> vec = new Vec<>();
		for (int variableId : linearExpression.getVariables()) {
			double coefficient = linearExpression.getCoefficient(variableId);
			vec.push(BigInteger.valueOf((long)coefficient));
		}
		return vec;
	}
	
	/**
	 * Registers the constraint for SAT4J
	 * @throws ContradictionException
	 */
	private void registerConstraint(ILPConstraint constraint) throws ContradictionException {
		if(constraint.getLinearExpression().getVariables().length < 1) {
			//Do not register empty clauses
			return;
		}
		for (int variableId : constraint.getLinearExpression().getVariables()) {
			double coefficient = constraint.getLinearExpression().getCoefficient(variableId);
			while(Math.abs(coefficient - ((long)coefficient)) >= 0.00000000001) {
				constraint.multiplyBy(10);
				coefficient = constraint.getLinearExpression().getCoefficient(variableId);
			}
		}
		while(Math.abs(constraint.getValue() - ((long)constraint.getValue())) >= 0.00000000001) {
			constraint.multiplyBy(10);
		}
		long value = (long) constraint.getValue();
		IVecInt literals = getLiterals(constraint.getLinearExpression());
		IVec<BigInteger> coeffs = getCoefs(constraint.getLinearExpression());
		switch(constraint.getComparator()) {
		case ge:
			solver.addPseudoBoolean(literals, coeffs, true, BigInteger.valueOf(value));
			break;
		case le:
			solver.addPseudoBoolean(literals, coeffs, false, BigInteger.valueOf(value));
			break;
		case eq:
			solver.addPseudoBoolean(literals, coeffs, true, BigInteger.valueOf(value));
			solver.addPseudoBoolean(literals, coeffs, false, BigInteger.valueOf(value));
		default:
			throw new IllegalArgumentException("Unsupported comparator: "+constraint.getComparator().toString());
		}
	}
	
	/**
	 * Register the objective for SAT4J
	 */
	private void registerObjective(ILPObjective objective) {
		for (int variableId : objective.getLinearExpression().getVariables()) {
			double coefficient = objective.getLinearExpression().getCoefficient(variableId);
			while(Math.abs(coefficient - ((long)coefficient)) >= 0.00000000001) {
				objective.getLinearExpression().multiplyBy(10);
				coefficient = objective.getLinearExpression().getCoefficient(variableId);
			}
		}
		ILPLinearExpression expr = objective.getLinearExpression();
		switch(objective.getObjectiveOperation()) {
		case maximize:
			ILPLinearExpression invertedExpression = ilpProblem.createLinearExpression();
			for (int variableId : objective.getLinearExpression().getVariables()) {
				double coefficient = objective.getLinearExpression().getCoefficient(variableId);
				invertedExpression.addTerm(variableId, -coefficient);
			}
			expr = invertedExpression;
			break;
		case minimize:
			break;
		default:
			throw new IllegalArgumentException("Unsupported comparator: "+objective.getObjectiveOperation().toString());
		}
		IVecInt literals = getLiterals(expr);
		IVec<BigInteger> coeffs = getCoefs(expr);
		solver.setObjectiveFunction(new ObjectiveFunction(literals, coeffs));
	}
}