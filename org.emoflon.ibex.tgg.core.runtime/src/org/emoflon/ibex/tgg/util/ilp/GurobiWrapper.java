package org.emoflon.ibex.tgg.util.ilp;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	private final Map<String, GRBVar> gurobiVariables = new HashMap<>();
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
	
	/**
	 * Set of constraints that have been defined using addConstraint
	 */
	private final Set<GurobiConstraint> constraints = new HashSet<>();
	/**
	 * The objective function that has been defined using setObjective
	 */
	private GurobiObjective objective = null;

	/**
	 * Creates a new Gurobi ILP solver
	 * @param onlyBinaryVariables This setting defines the variable range of variables registered at Gurobi
	 */
	GurobiWrapper(boolean onlyBinaryVariables){
		super();
		this.onlyBinaryVariables = onlyBinaryVariables;
	}

	/**
	 * Registers the variable with the given name in the gurobi model
	 * @param variable		Name of the variable to register
	 * @throws GRBException	
	 */
	private void registerVariable(String variable) throws GRBException {
		//add var (lowerBound,upperBound, Objective coefficient, type, name)
		if(onlyBinaryVariables) {
			gurobiVariables.put(variable, model.addVar(0.0, 1.0, 0.0, GRB.BINARY, variable));
		} else {
			gurobiVariables.put(variable, model.addVar(Integer.MIN_VALUE, Integer.MAX_VALUE, 0.0, GRB.INTEGER, variable));
		}
	}

	@Override
	public ILPLinearExpression createLinearExpression(ILPTerm... terms) {
		GurobiLinearExpression expr = new GurobiLinearExpression();
		for (ILPTerm ilpTerm : terms) {
			expr.addTerm(ilpTerm);
		}
		return expr;
	}

	@Override
	public void addConstraint(ILPLinearExpression linearExpression, Operation comparator, double value, String name) {
		this.constraints.add(new GurobiConstraint(linearExpression, comparator, value, name));
	}
	
	@Override
	public Collection<ILPConstraint> getConstraints() {
		return Collections.unmodifiableSet(constraints);
	}
	
	@Override
	public void setObjective(ILPLinearExpression linearExpression, Operation operation) {
		if(objective == null) {
			objective = new GurobiObjective(linearExpression, operation);
		} else {
			throw new UnsupportedOperationException("The objective function has already been set");
		}
	}

	@Override
	public ILPObjective getObjective() {
		return objective;
	}

	@Override
	public ILPSolution solveILP() throws GRBException {
		env = new GRBEnv("Gurobi_ILP.log");
		model = new GRBModel(env);
		
		for(String variableName : this.getVariables()) {
			this.registerVariable(variableName);
		}
		for(GurobiConstraint constraint : this.constraints) {
			constraint.registerConstraint();
		}
		objective.registerObjective();
	
		model.optimize();
		Map<String, Integer> solutionVariables = new HashMap<>();
		for (String variableName : getVariables()) {
			GRBVar gurobiVar = gurobiVariables.get(variableName);
			solutionVariables.put(variableName, (int) gurobiVar.get(DoubleAttr.X));
		}
		env.dispose();
		model.dispose();
		return new ILPSolution(solutionVariables, true);
	}

	/**
	 * 
	 * @author Robin Oppermann
	 *
	 */
	private class GurobiLinearExpression extends ILPLinearExpression {
		private GRBLinExpr createGurobiExpression() {
			GRBLinExpr gurobiExpression = new GRBLinExpr();
			for(ILPTerm term : this.getTerms()) {
				gurobiExpression.addTerm(term.getCoefficient(), gurobiVariables.get(term.getVariable()));
			}
			return gurobiExpression;
		}
	}
	
	/**
	 * ILPConstraint for Gurobi
	 * 
	 * @author Robin Oppermann
	 */
	private class GurobiConstraint extends ILPConstraint {
		/**
		 * Gurobi identifier of the comparator
		 */
		private final char gurobiComparator;
		/**
		 * Name of the constraint
		 */
		private final String name;
		/**
		 * Creates a Gurobi constraint
		 * @param linearExpression	The linear expression of the constraint (left side of the inequation)
		 * @param comparator		Comparator (e.g. <=)
		 * @param value				The value on the right side of the inequation
		 * @param name				Hte name of the constraint
		 */
		private GurobiConstraint(ILPLinearExpression linearExpression, Operation comparator, double value, String name) {
			super(linearExpression, comparator, value);
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
			this.name = name;
		}
		
		/**
		 * Registers the constraint in the gurobi model
		 * @throws GRBException
		 */
		private void registerConstraint() throws GRBException {
			model.addConstr(((GurobiLinearExpression)linearExpression).createGurobiExpression(), gurobiComparator, value, name);
		}
	}
	
	/**
	 * ILP Objective for Gurobi
	 * 
	 * @author Robin Oppermann
	 */
	private class GurobiObjective extends ILPObjective {
		/**
		 * Gurobi identifier of the objective
		 */
		private final int gurobiObjectiveSelector;
		
		/**
		 * Creates a new objective function
		 * 
		 * @param linearExpression		The linear expression to optimize
		 * @param objectiveOperation	The objective: Either minimize or maximize the objective
		 */
		private GurobiObjective(ILPLinearExpression linearExpression, Operation objectiveOperation) {
			super(linearExpression, objectiveOperation);
			switch(objectiveOperation) {
			case maximize:
				gurobiObjectiveSelector = GRB.MAXIMIZE;
				break;
			case minimize:
				gurobiObjectiveSelector = GRB.MINIMIZE;
				break;
			default:
				throw new IllegalArgumentException("Unsupported operation: "+objectiveOperation.toString());
			}
		}
		
		/**
		 * Registers the objective in the gurobi model
		 * @throws GRBException
		 */
		private void registerObjective() throws GRBException {
			model.setObjective(((GurobiLinearExpression)linearExpression).createGurobiExpression(), gurobiObjectiveSelector);
		}		
	}
}
