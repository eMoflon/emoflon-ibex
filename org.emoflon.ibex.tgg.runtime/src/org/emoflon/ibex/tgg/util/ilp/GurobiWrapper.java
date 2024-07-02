package org.emoflon.ibex.tgg.util.ilp;

import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.collections.IntToIntMap;
import org.emoflon.ibex.common.collections.IntToObjectMap;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPConstraint;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPObjective;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;

import gurobi.GRB;
import gurobi.GRB.DoubleAttr;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

/**
 * This class is a wrapper around Gurobi allowing the usage of this ILPSolver
 * with the unified API of the {@link ILPSolver} class. To use this wrapper
 * Gurobi has to be installed separately.
 *
 * @author Robin Oppermann
 *
 */
final class GurobiWrapper extends ILPSolver {
	/**
	 * A mapping of the variable identifiers to the variables registered in the
	 * Gurobi model
	 */
	private final IntToObjectMap<GRBVar> gurobiVariables = CollectionFactory.cfactory.createIntToObjectHashMap();
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
	private static final int MAX_TIMEOUT = 60 * 60; // 1 hour

	/**
	 * Creates a new Gurobi ILP solver
	 *
	 * @param onlyBinaryVariables This setting defines the variable range of
	 *                            variables registered at Gurobi
	 */
	GurobiWrapper(final ILPProblem ilpProblem, final boolean onlyBinaryVariables) {
		super(ilpProblem);
		this.onlyBinaryVariables = onlyBinaryVariables;
	}

	/**
	 * Registers the variable with the given name in the gurobi model
	 *
	 * @param variable Name of the variable to register
	 * @throws GRBException
	 */
	private void registerVariable(final int variableId) throws GRBException {
		// add var (lowerBound,upperBound, Objective coefficient, type, name)
		String variable = this.ilpProblem.getVariable(variableId);
		if (this.onlyBinaryVariables) {
			this.gurobiVariables.put(variableId, this.model.addVar(0.0, 1.0, 0.0, GRB.BINARY, variable));
		} else {
			this.gurobiVariables.put(variableId,
					this.model.addVar(Integer.MIN_VALUE, Integer.MAX_VALUE, 0.0, GRB.INTEGER, variable));
		}
	}

	@Override
	public ILPSolution solveILP() throws GRBException {
		ILPSolver.logger.info(this.ilpProblem.getProblemInformation());

		long currentTimeout = this.ilpProblem.getVariableIdsOfUnfixedVariables().size();
		currentTimeout = GurobiWrapper.MIN_TIMEOUT + (long) Math.ceil(Math.pow(1.16, Math.sqrt(currentTimeout)));
		if (currentTimeout < 0) {
			currentTimeout = GurobiWrapper.MAX_TIMEOUT;
		}
		currentTimeout = Math.min(currentTimeout, GurobiWrapper.MAX_TIMEOUT);

		this.prepareModel();
		this.solveModel(currentTimeout);
		return this.retrieveSolution();
	}

	/**
	 * Fills the Gurobi model with all problem details
	 *
	 * @throws GRBException
	 */
	private void prepareModel() throws GRBException {
		this.env = new GRBEnv("Gurobi_ILP.log");
		this.model = new GRBModel(this.env);
		this.model.set(GRB.IntParam.OutputFlag, 0);

		for (int variableId : this.ilpProblem.getVariableIdsOfUnfixedVariables()) {
			this.registerVariable(variableId);
		}
		for (ILPConstraint constraint : this.ilpProblem.getConstraints()) {
			this.registerConstraint(constraint);
		}
		this.registerObjective(this.ilpProblem.getObjective());
	}

	/**
	 * Solves the model using Gurobi
	 *
	 * @param timeout the maximum time to take
	 * @throws GRBException
	 */
	private void solveModel(final long timeout) throws GRBException {
		ILPSolver.logger.debug("Setting time-limit to " + timeout + " seconds.");
		this.model.set(GRB.DoubleParam.TimeLimit, timeout);
		if (this.getSolutionTolerance() != 0) {
			this.model.set(GRB.DoubleParam.MIPGap, this.getSolutionTolerance());
		}
		this.model.optimize();
	}

	/**
	 * Retrieve the solution from Gurobi
	 *
	 * @return
	 * @throws GRBException
	 */
	private ILPSolution retrieveSolution() throws GRBException {
		int status = this.model.get(GRB.IntAttr.Status);
		int solutionCount = this.model.get(GRB.IntAttr.SolCount);
		boolean optimal = status == GRB.Status.OPTIMAL;
		boolean feasible = solutionCount > 0;
		if (!feasible) {
			ILPSolver.logger.error("No optimal or feasible solution found.");
			throw new RuntimeException("No optimal or feasible solution found.");
		}

		double optimum = this.model.get(GRB.DoubleAttr.ObjVal);

		IntToIntMap solutionVariables = CollectionFactory.cfactory.createIntToIntMap();
		for (int variableId : this.ilpProblem.getVariableIdsOfUnfixedVariables()) {
			GRBVar gurobiVar = this.gurobiVariables.get(variableId);
			solutionVariables.put(variableId, (int) gurobiVar.get(DoubleAttr.X));
		}

		ILPSolver.logger.debug("Gurobi found solution: " + optimum + " - Optimal: " + optimal);

		this.env.dispose();
		this.model.dispose();
		ILPSolution solution = this.ilpProblem.createILPSolution(solutionVariables, optimal, optimum);
		ILPSolver.logger.info(solution.getSolutionInformation());
		return solution;
	}

	/**
	 * Creates the internally used Gurobi expression
	 */
	private GRBLinExpr createGurobiExpression(final ILPLinearExpression linearExpression) {
		GRBLinExpr gurobiExpression = new GRBLinExpr();
		for (int variableId : linearExpression.getVariables()) {
			double coefficient = linearExpression.getCoefficient(variableId);
			gurobiExpression.addTerm(coefficient, this.gurobiVariables.get(variableId));
		}
		return gurobiExpression;
	}

	/**
	 * Registers the constraint in the gurobi model
	 *
	 * @throws GRBException
	 */
	private void registerConstraint(final ILPConstraint constraint) throws GRBException {
		char gurobiComparator;
		gurobiComparator = switch (constraint.getComparator()) {
			case eq -> GRB.EQUAL;
			case ge -> GRB.GREATER_EQUAL;
			case le -> GRB.LESS_EQUAL;
			default -> throw new IllegalArgumentException("Unsupported comparator: " + constraint.getComparator().toString());
		};
		this.model.addConstr(this.createGurobiExpression(constraint.getLinearExpression()), gurobiComparator, constraint.getValue(),
				constraint.getName());
	}

	/**
	 * Registers the objective in the gurobi model
	 *
	 * @throws GRBException
	 */
	private void registerObjective(final ILPObjective objective) throws GRBException {
		int gurobiObjectiveSelector;
		gurobiObjectiveSelector = switch (objective.getObjectiveOperation()) {
			case maximize -> GRB.MAXIMIZE;
			case minimize -> GRB.MINIMIZE;
			default -> throw new IllegalArgumentException("Unsupported operation: " + objective.getObjectiveOperation().toString());
		};
		this.model.setObjective(this.createGurobiExpression(objective.getLinearExpression()), gurobiObjectiveSelector);
	}
}
