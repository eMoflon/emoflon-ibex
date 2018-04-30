package org.emoflon.ibex.tgg.util.ilp;

import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPConstraint;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPObjective;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;

import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gurobi.GRB;
import gurobi.GRB.DoubleAttr;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

/**
 * This class is a wrapper around Gurobi allowing the usage of this ILPSolver with the unified API of the {@link ILPSolver} class.
 * To use this wrapper Gurobi has to be installed separately.
 * 
 * @author Robin Oppermann
 *
 */
final class GurobiWrapper extends ILPSolver {
	/**
	 * A mapping of the variable identifiers to the variables registered in the Gurobi model
	 */
	private final TIntObjectHashMap<GRBVar> gurobiVariables = new TIntObjectHashMap<GRBVar>();
	/**
	 * The Gurobi model
	 */
	private GRBModel model;
	/**
	 * The Gurobi environment
	 */
	private GRBEnv env;
	/**
	 * This setting defines the variable range of variables registered at Gurobi
	 */
	private final boolean onlyBinaryVariables;
	
	private static final int MIN_TIMEOUT = 30;
	private static final int MAX_TIMEOUT = 60*60; //1 hour

	/**
	 * Creates a new Gurobi ILP solver
	 * @param onlyBinaryVariables This setting defines the variable range of variables registered at Gurobi
	 */
	GurobiWrapper(ILPProblem ilpProblem, boolean onlyBinaryVariables){
		super(ilpProblem);
		this.onlyBinaryVariables = onlyBinaryVariables;
	}

	/**
	 * Registers the variable with the given name in the gurobi model
	 * @param variable		Name of the variable to register
	 * @throws GRBException	
	 */
	private void registerVariable(int variableId) throws GRBException {
		//add var (lowerBound,upperBound, Objective coefficient, type, name)
		String variable = this.ilpProblem.getVariable(variableId);
		if(onlyBinaryVariables) {
			gurobiVariables.put(variableId, model.addVar(0.0, 1.0, 0.0, GRB.BINARY, variable));
		} else {
			gurobiVariables.put(variableId, model.addVar(Integer.MIN_VALUE, Integer.MAX_VALUE, 0.0, GRB.INTEGER, variable));
		}
	}

	@Override
	public ILPSolution solveILP() throws GRBException {
		System.out.println("The ILP to solve has "+this.ilpProblem.getConstraints().size()+" constraints and "+this.ilpProblem.getVariableIdsOfUnfixedVariables().length+ " variables");
		
		long currentTimeout = this.ilpProblem.getVariableIdsOfUnfixedVariables().length;
		currentTimeout = MIN_TIMEOUT + (long) Math.ceil(Math.pow(1.16, Math.sqrt(currentTimeout)));
		if(currentTimeout < 0) {
			currentTimeout = MAX_TIMEOUT;
		}
		currentTimeout = Math.min(currentTimeout, MAX_TIMEOUT);
		
		this.prepareModel();
		this.solveModel(currentTimeout);
		return this.retrieveSolution();
	}
	
	/**
	 * Fills the Gurobi model with all problem details
	 * @throws GRBException
	 */
	private void prepareModel() throws GRBException {
		env = new GRBEnv("Gurobi_ILP.log");
		model = new GRBModel(env);

		for(int variableId : this.ilpProblem.getVariableIdsOfUnfixedVariables()) {
			this.registerVariable(variableId);
		}
		for(ILPConstraint constraint : this.ilpProblem.getConstraints()) {
			registerConstraint(constraint);
		}
		registerObjective(this.ilpProblem.getObjective());
	}
	
	/**
	 * Solves the model using Gurobi
	 * @param timeout the maximum time to take
	 * @throws GRBException
	 */
	private void solveModel(long timeout) throws GRBException {
		System.out.println("Setting time-limit to "+timeout+ " seconds.");
		this.model.set(GRB.DoubleParam.TimeLimit, timeout);
		this.model.optimize();
	}
	
	/**
	 * Retrieve the solution from Gurobi
	 * @return
	 * @throws GRBException
	 */
	private ILPSolution retrieveSolution() throws GRBException {
		int status = model.get(GRB.IntAttr.Status);
		int solutionCount = model.get(GRB.IntAttr.SolCount);
		boolean optimal = status == GRB.Status.OPTIMAL;
		boolean feasible = solutionCount > 0;
		if (!feasible) {
			System.err.println("No optimal or feasible solution found.");
			throw new RuntimeException("No optimal or feasible solution found.");
		}
		
		double optimum = model.get(GRB.DoubleAttr.ObjVal);
		
		TIntIntHashMap solutionVariables = new TIntIntHashMap();
		for (int variableId : this.ilpProblem.getVariableIdsOfUnfixedVariables()) {
			GRBVar gurobiVar = gurobiVariables.get(variableId);
			solutionVariables.put(variableId, (int) gurobiVar.get(DoubleAttr.X));
		}
		
		System.out.println("Solution found: "+optimum);
		
		env.dispose();
		model.dispose();
		return ilpProblem.createILPSolution(solutionVariables, optimal, optimum);
	}
	
	/**
	 * Creates the internally used Gurobi expression
	 */
	private GRBLinExpr createGurobiExpression(ILPLinearExpression linearExpression) {
		GRBLinExpr gurobiExpression = new GRBLinExpr();
		for (int variableId : linearExpression.getVariables()) {
			double coefficient = linearExpression.getCoefficient(variableId);
			gurobiExpression.addTerm(coefficient, gurobiVariables.get(variableId));
		}
		return gurobiExpression;
	}
	
	/**
	 * Registers the constraint in the gurobi model
	 * @throws GRBException
	 */
	private void registerConstraint(ILPConstraint constraint) throws GRBException {
		char gurobiComparator;
		switch(constraint.getComparator()) {
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
			throw new IllegalArgumentException("Unsupported comparator: "+constraint.getComparator().toString());
		}
		model.addConstr(createGurobiExpression(constraint.getLinearExpression()), gurobiComparator, constraint.getValue(), constraint.getName());
	}

	/**
	 * Registers the objective in the gurobi model
	 * @throws GRBException
	 */
	private void registerObjective(ILPObjective objective) throws GRBException {
		int gurobiObjectiveSelector;
		switch(objective.getObjectiveOperation()) {
		case maximize:
			gurobiObjectiveSelector = GRB.MAXIMIZE;
			break;
		case minimize:
			gurobiObjectiveSelector = GRB.MINIMIZE;
			break;
		default:
			throw new IllegalArgumentException("Unsupported operation: "+objective.getObjectiveOperation().toString());
		}
		model.setObjective(createGurobiExpression(objective.getLinearExpression()), gurobiObjectiveSelector);
	}	
}
