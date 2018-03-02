package org.emoflon.ibex.common.ilp.gurobiWrapper;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.ibex.common.ilp.ILPSolver;

import gurobi.GRB;
import gurobi.GRB.DoubleAttr;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;
public class GurobiWrapper extends ILPSolver {
	
	private final Map<String, GRBVar> gurobiVariables = new HashMap<>();
	private GRBModel model;
	private GRBEnv env;
	private ILPSolution solution;

	public GurobiWrapper() {
		super();
		try {
			env = new GRBEnv("Gurobi_ILP.log");
			model = new GRBModel(env);
		} catch (GRBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addVariable(String variable) {
		if(solution != null) {
			throw new UnsupportedOperationException("The ILP has already been solved");
		}
		super.addVariable(variable);
		try {
			//add var (lowerBound,upperBound, Objective coefficient, type, name)
			gurobiVariables.put(variable, model.addVar(0.0, 1.0, 0.0, GRB.BINARY, variable));
		} catch (GRBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ILPLinearExpression createLinearExpression(ILPTerm... terms) {
		if(solution != null) {
			throw new UnsupportedOperationException("The ILP has already been solved");
		}
		GurobiLinearExpressionWrapper expr = new GurobiLinearExpressionWrapper();
		for (ILPTerm ilpTerm : terms) {
			expr.addTerm(ilpTerm);
		}
		return expr;
	}

	@Override
	public void addConstraint(ILPLinearExpression linearExpression, Operation comparator, double value, String name) {
		char gurobiComparator;
		switch(comparator) {
		case eq:
			gurobiComparator = GRB.EQUAL;
			break;
		case ge:
			gurobiComparator = GRB.GREATER_EQUAL;
			break;
		case le:
			gurobiComparator = GRB.LESS_EQUAL;
			break;
		default:
			throw new IllegalArgumentException("Unsupported comparator: "+comparator.toString());
		}
		try {
			model.addConstr(((GurobiLinearExpressionWrapper)linearExpression).gurobiExpression, gurobiComparator, value, name);
		} catch (GRBException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setObjective(ILPLinearExpression linearExpression, Operation operation) {
		if(solution != null) {
			throw new UnsupportedOperationException("The ILP has already been solved");
		}
		int gurobiObjectiveSelector;
		switch(operation) {
		case maximize:
			gurobiObjectiveSelector = GRB.MAXIMIZE;
			break;
		case minimize:
			gurobiObjectiveSelector = GRB.MINIMIZE;
			break;
		default:
			throw new IllegalArgumentException("Unsupported operation: "+operation.toString());
		}
		try {
			model.setObjective(((GurobiLinearExpressionWrapper)linearExpression).gurobiExpression, gurobiObjectiveSelector);
		} catch (GRBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ILPSolution solveILP() {
		if(solution != null) {
			return solution;
		}
		try {
			model.optimize();
			Map<String, Integer> solutionVariables = new HashMap<>();
			for (String variableName : getVariables()) {
				GRBVar gurobiVar = gurobiVariables.get(variableName);
				solutionVariables.put(variableName, (int) gurobiVar.get(DoubleAttr.X));
			}
			env.dispose();
			model.dispose();
			this.solution = new ILPSolution(solutionVariables, true);
		} catch (GRBException e) {
			e.printStackTrace();
		}
		return solution;
	}

	private class GurobiLinearExpressionWrapper extends ILPLinearExpression {
		private final GRBLinExpr gurobiExpression;
		
		private GurobiLinearExpressionWrapper() {
			gurobiExpression = new GRBLinExpr();
		}
		
		@Override
		public void addTerm(ILPTerm term) {
			gurobiExpression.addTerm(term.getFactor(), gurobiVariables.get(term.getVariable()));
		}
	}
}
