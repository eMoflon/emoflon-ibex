package org.emoflon.ibex.common.ilp.sat4JWrapper;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.common.ilp.ILPSolver;
import org.sat4j.core.Vec;
import org.sat4j.core.VecInt;
import org.sat4j.pb.IPBSolver;
import org.sat4j.pb.ObjectiveFunction;
import org.sat4j.pb.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;

public class Sat4JWrapper extends ILPSolver {
	
	private final IPBSolver solver;
	private final Set<SAT4JConstraint> constraints = new HashSet<>();
	
	
	//http://www.sat4j.org/maven234/org.ow2.sat4j.pb/apidocs/index.html
	//http://www.sat4j.org/maven234/org.ow2.sat4j.core/apidocs/index.html
	public Sat4JWrapper() {
		solver = SolverFactory.newDefaultOptimizer();
	}

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
		try {
			this.constraints.add(new SAT4JConstraint(linearExpression, comparator, value));
		} catch (ContradictionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setObjective(ILPLinearExpression linearExpression, Operation operation) {
		SAT4JLinearExpression expr = (SAT4JLinearExpression) linearExpression;
		switch(operation) {
		case maximize:
			SAT4JLinearExpression invertedExpression = (SAT4JLinearExpression) this.createLinearExpression();
			for(ILPTerm term : expr.getTerms()) {
				invertedExpression.addTerm(this.createTerm(term.getVariable(), -term.getCoefficient()));
			}
			expr = invertedExpression;
			break;
		case minimize:
			break;
		default:
			throw new IllegalArgumentException("Unsupported comparator: "+operation.toString());
		}
		solver.setObjectiveFunction(new ObjectiveFunction(expr.getLiterals(), expr.getCoefs()));
	}

	@Override
	public ILPSolution solveILP() {
//		OptToPBSATAdapter optimizer = new OptToPBSATAdapter(new PseudoOptDecorator(solver));
		solver.setTimeout(600);
		solver.setVerbose(true);
		try {
			if(solver.isSatisfiable()) { //TODO check all constraints -> satisfied?
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
				for(SAT4JConstraint constraint : constraints) {
					if(!constraint.checkConstraint(variableSolutions)) {
						throw new RuntimeException("The ILP is not satisfiable");
					}
				}
				return new ILPSolution(variableSolutions, true);//optimizer.isOptimal());
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private class SAT4JLinearExpression extends ILPLinearExpression {
		
		private final Set<ILPTerm> terms = new HashSet<>();
		private int factor = 1;

		@Override
		public void addTerm(ILPTerm term) {
			term.multiplyBy(this.factor);
			terms.add(term);
			while(Math.abs(term.getCoefficient() - ((long)term.getCoefficient())) >= 0.00000000001) {
				this.multiplyBy(10);
			}
		}
		
		private void multiplyBy(double factor) {
			this.factor *= factor;
			for (ILPTerm term : terms) {
				term.multiplyBy(factor);
			}
		}
		
		private IVecInt getLiterals() {
			IVecInt vec = new VecInt();
			for (ILPTerm term : terms) {
				vec.push(term.getVariable().hashCode());
			}
			return vec;
		}
		
		private IVec<BigInteger> getCoefs() {
			IVec<BigInteger> vec = new Vec<>();
			for (ILPTerm term : terms) {
				vec.push(BigInteger.valueOf((long)term.getCoefficient()));
			}
			return vec;
		}

		/**
		 * @return the terms
		 */
		private Set<ILPTerm> getTerms() {
			return terms;
		}
		
	}
	
	private class SAT4JConstraint extends ILPConstraint {
		private final SAT4JLinearExpression linearExpression;
		private final long value;
		private final Operation comparator;
		
		private SAT4JConstraint(ILPLinearExpression linearExpression, Operation comparator, double value) throws ContradictionException {
			SAT4JLinearExpression expr = (SAT4JLinearExpression) linearExpression;
			this.comparator = comparator;
			value *= expr.factor;
			while(Math.abs(value - ((long)value)) >= 0.00000000001) {
				value *= 10;
				expr.multiplyBy(10);
			}
			this.linearExpression = expr;
			this.value = (long) value;
			switch(comparator) {
				case ge:
					solver.addPseudoBoolean(expr.getLiterals(), expr.getCoefs(), true, BigInteger.valueOf(this.value));
					break;
				case le:
					solver.addPseudoBoolean(expr.getLiterals(), expr.getCoefs(), false, BigInteger.valueOf(this.value));
					break;
				case eq:
					solver.addPseudoBoolean(expr.getLiterals(), expr.getCoefs(), true, BigInteger.valueOf(this.value));
					solver.addPseudoBoolean(expr.getLiterals(), expr.getCoefs(), false, BigInteger.valueOf(this.value));
				default:
					throw new IllegalArgumentException("Unsupported comparator: "+comparator.toString());
			}
		}
		
		private boolean checkConstraint(Map<String, Integer> variableSolutions) {
			double solution = 0;
			for(ILPTerm term : linearExpression.terms) {
				solution += term.getCoefficient() * variableSolutions.get(term.getVariable());
			}
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
}
