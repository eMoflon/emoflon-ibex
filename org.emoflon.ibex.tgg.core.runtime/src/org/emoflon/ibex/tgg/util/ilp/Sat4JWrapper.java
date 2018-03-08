package org.emoflon.ibex.tgg.util.ilp;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	/**
	 * Set of constraints that have been defined using addConstraint
	 */
	private final Set<SAT4JConstraint> constraints = new HashSet<>();
	/**
	 * The objective function that has been defined using setObjective
	 */
	private SAT4JObjective objective = null;
	
	private static final int DEFAULT_TIMEOUT = 20;
	private static final int MAX_TIMEOUT = 60*60;


	/**
	 * Creates a new SAT4JWrapper
	 */
	Sat4JWrapper() {}

	@Override
	public ILPLinearExpression createLinearExpression(ILPTerm... terms) {
		ILPLinearExpression expr = new SAT4JLinearExpression();
		for (ILPTerm term : terms) {
			expr.addTerm(term);
		}
		return expr;
	}

	@Override
	public void addConstraint(ILPLinearExpression linearExpression, Operation comparator, double value, String name) {
		this.constraints.add(new SAT4JConstraint(linearExpression, comparator, value));
	}

	@Override
	public Collection<ILPConstraint> getConstraints() {
		return Collections.unmodifiableSet(constraints);
	}

	@Override
	public void setObjective(ILPLinearExpression linearExpression, Operation operation) {
		if(objective == null) {
			objective = new SAT4JObjective(linearExpression, operation);
		} else {
			throw new UnsupportedOperationException("The objective function has already been set");
		}
	}

	@Override
	public ILPObjective getObjective() {
		return objective;
	}

	@Override
	public ILPSolution solveILP() throws ContradictionException {
		System.out.println("The ILP to solve has "+constraints.size()+" constraints and "+this.getVariables().size()+ " variables");
		int currentTimeout = DEFAULT_TIMEOUT;
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
	
	private ILPSolution solveILP(int timeout) throws ContradictionException, TimeoutException {
		solver = SolverFactory.newDefaultOptimizer();
		
		for(SAT4JConstraint constraint : constraints) {
			constraint.registerConstraint();
		}
		objective.registerObjective();
		
		OptToPBSATAdapter optimizer = new OptToPBSATAdapter(new PseudoOptDecorator(solver));
		optimizer.setTimeout(30);
		optimizer.setVerbose(true);
		if(optimizer.isSatisfiable()) {
			int[] model = solver.model();
			Map<String, Integer> variableSolutions = new HashMap<>();
			for(int i : model) {
				int solution = i>0? 1 : 0;
				for(String var : this.getVariables()) {
					if(Math.abs(i) == var.hashCode()) {
						variableSolutions.put(var, solution);
						break;
					}
				}
			}
			ILPSolution solution = new ILPSolution(variableSolutions, optimizer.isOptimal());
			for(SAT4JConstraint constraint : constraints) {
				if(!constraint.checkConstraint(solution)) {
					throw new RuntimeException("The ILP is not satisfiable");
				}
			}
			return solution;
		}
		return null;
	}

	/**
	 * SAT4J LinearExpression
	 * @author Robin Oppermann
	 */
	private class SAT4JLinearExpression extends ILPLinearExpression {
		/**
		 * Converts the term representation into the integer vector of variable literals of SAT4J
		 * The string identifiers are replaced by integer identifiers 
		 * @return The integer vector representing the literals of the linear expression
		 */
		private IVecInt getLiterals() {
			IVecInt vec = new VecInt();
			for (ILPTerm term : this.getTerms()) {
				vec.push(term.getVariable().hashCode());
			}
			return vec;
		}

		/**
		 * Converts the term representation into the integer vector of coefficients of SAT4J
		 * @return The BigInteger vector of the coefficients
		 */
		private IVec<BigInteger> getCoefs() {
			IVec<BigInteger> vec = new Vec<>();
			for (ILPTerm term : this.getTerms()) {
				vec.push(BigInteger.valueOf((long)term.getCoefficient()));
			}
			return vec;
		}
	}

	/**
	 * SAT4J Contraint
	 * @author Robin Oppermann
	 *
	 */
	private class SAT4JConstraint extends ILPConstraint {
		/**
		 * Creates a SAT4J constraint
		 * @param linearExpression	The linear expression of the constraint (left side of the inequation)
		 * @param comparator		Comparator (e.g. <=)
		 * @param value				The value on the right side of the inequation
		 */
		private SAT4JConstraint(ILPLinearExpression linearExpression, Operation comparator, double value) {
			super(linearExpression, comparator, value);
			if(!(linearExpression instanceof SAT4JLinearExpression)) {
				throw new IllegalArgumentException("The linear Expression is not a SAT4J Expression");
			}
			switch(comparator) {
			case ge:
			case le:
			case eq:
				break;
			default:
				throw new IllegalArgumentException("Unsupported comparator: "+comparator.toString());
			}
		}

		/**
		 * Registers the constraint for SAT4J
		 * @throws ContradictionException
		 */
		private void registerConstraint() throws ContradictionException {
			for (ILPTerm term : this.linearExpression.getTerms()) {
				while(Math.abs(term.getCoefficient() - ((long)term.getCoefficient())) >= 0.00000000001) {
					this.multiplyBy(10);
				}
			}
			while(Math.abs(value - ((long)value)) >= 0.00000000001) {
				this.multiplyBy(10);
			}
			SAT4JLinearExpression expr = (SAT4JLinearExpression) linearExpression;
			long value = (long) this.value;
			switch(comparator) {
			case ge:
				solver.addPseudoBoolean(expr.getLiterals(), expr.getCoefs(), true, BigInteger.valueOf(value));
				break;
			case le:
				solver.addPseudoBoolean(expr.getLiterals(), expr.getCoefs(), false, BigInteger.valueOf(value));
				break;
			case eq:
				solver.addPseudoBoolean(expr.getLiterals(), expr.getCoefs(), true, BigInteger.valueOf(value));
				solver.addPseudoBoolean(expr.getLiterals(), expr.getCoefs(), false, BigInteger.valueOf(value));
			default:
				throw new IllegalArgumentException("Unsupported comparator: "+comparator.toString());
			}
		}
	}
	
	/**
	 * SAT4J Objective
	 * 
	 * @author Robin Oppermann
	 *
	 */
	private class SAT4JObjective extends ILPObjective {
		/**
		 * Creates a new objective function
		 * 
		 * @param linearExpression		The linear expression to optimize
		 * @param objectiveOperation	The objective: Either minimize or maximize the objective
		 */
		private SAT4JObjective(ILPLinearExpression linearExpression, Operation objectiveOperation) {
			super(linearExpression, objectiveOperation);
		}
		
		/**
		 * Register the objective for SAT4J
		 */
		private void registerObjective() {
			for (ILPTerm term : this.linearExpression.getTerms()) {
				while(Math.abs(term.getCoefficient() - ((long)term.getCoefficient())) >= 0.00000000001) {
					linearExpression.multiplyBy(10);
				}
			}
			SAT4JLinearExpression expr = (SAT4JLinearExpression) this.linearExpression;
			switch(this.objectiveOperation) {
			case maximize:
				SAT4JLinearExpression invertedExpression = (SAT4JLinearExpression) createLinearExpression();
				for(ILPTerm term : expr.getTerms()) {
					invertedExpression.addTerm(createTerm(term.getVariable(), -term.getCoefficient()));
				}
				expr = invertedExpression;
				break;
			case minimize:
				break;
			default:
				throw new IllegalArgumentException("Unsupported comparator: "+objectiveOperation.toString());
			}
			solver.setObjectiveFunction(new ObjectiveFunction(expr.getLiterals(), expr.getCoefs()));
		}
		
	}
}