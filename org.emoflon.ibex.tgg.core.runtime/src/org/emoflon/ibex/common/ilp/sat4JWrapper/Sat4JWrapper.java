package org.emoflon.ibex.common.ilp.sat4JWrapper;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.common.ilp.ILPSolver;
import org.emoflon.ibex.common.ilp.ILPSolver.ILPSolution;
import org.sat4j.core.Vec;
import org.sat4j.core.VecInt;
import org.sat4j.pb.IPBSolver;
import org.sat4j.pb.ObjectiveFunction;
import org.sat4j.pb.OptToPBSATAdapter;
import org.sat4j.pb.PseudoOptDecorator;
import org.sat4j.pb.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IOptimizationProblem;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;

public class Sat4JWrapper extends ILPSolver {
	
	private final IPBSolver solver;
	
	
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
		// TODO Auto-generated method stub
		SAT4JLinearExpression expr = (SAT4JLinearExpression) linearExpression;
		boolean moreThan;
		switch(comparator) {
		case ge:
			moreThan = true;
			break;
		case le:
			moreThan = false;
			break;
		default:
			throw new IllegalArgumentException("Unsupported comparator: "+comparator.toString());
		}
		try {
			solver.addPseudoBoolean(expr.getLiterals(), expr.getCoefs(), moreThan, BigInteger.valueOf((long) value));
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
				invertedExpression.addTerm(this.createTerm(term.getVariable(), -term.getFactor()));
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
		// TODO Auto-generated method stub
		OptToPBSATAdapter optimizer = new OptToPBSATAdapter(new PseudoOptDecorator(solver));
		optimizer.setTimeout(600);
		optimizer.setVerbose(true);
		try {
			if(optimizer.isSatisfiable()) {
				int[] model = optimizer.model();
				for(int i : model) {
					System.out.println(i);
				}
				
			}
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	private class SAT4JLinearExpression extends ILPLinearExpression {
		
		private final Set<ILPTerm> terms = new HashSet<>();

		@Override
		public void addTerm(ILPTerm term) {
			terms.add(term);
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
				vec.push(BigInteger.valueOf((long)term.getFactor()));
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
}
