package org.emoflon.ibex.tgg.util.ilp;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.naming.directory.InvalidAttributeValueException;

import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPConstraint;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPObjective;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPSolver.ResultStatus;
import com.google.ortools.linearsolver.MPVariable;

import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class CBCWrapper extends ILPSolver{
	
	private MPSolver solver;
	
	private final boolean onlyBinaryVariables;
	private final TIntObjectHashMap<MPVariable> variableIdToCBCVar = new TIntObjectHashMap<MPVariable>();
	private static final int MIN_TIMEOUT = 30;
	private static final int MAX_TIMEOUT = 60*60; //1 hour
	
	static { 
		final URL classResource = MPSolver.class.getResource(MPSolver.class.getSimpleName() + ".class");

	    final String url = classResource.toString();
	    final String suffix = MPSolver.class.getCanonicalName().replace('.', '/') + ".class";

	    // strip the class's path from the URL string
	    final String base = url.substring(0, url.length() - suffix.length());

	    String path = base;

	    // remove the "jar:" prefix and "!/" suffix, if present
	    if (path.startsWith("jar:")) path = path.substring(4, path.length() - 2);
	    try {
			File jar = new File(new URI(path));
			System.load(jar.getParent() + "/jniortools.dll");;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	CBCWrapper(ILPProblem ilpProblem, boolean onlyBinaryVariables) {
		super(ilpProblem);
		this.onlyBinaryVariables = onlyBinaryVariables;
	}

	@Override
	public ILPSolution solveILP() throws Exception {
		System.out.println("The ILP to solve has "+this.ilpProblem.getConstraints().size()+" constraints and "+this.ilpProblem.getVariableIdsOfUnfixedVariables().length+ " variables");
		long currentTimeout = this.ilpProblem.getVariableIdsOfUnfixedVariables().length;
		currentTimeout = MIN_TIMEOUT + (long) Math.ceil(Math.pow(1.16, Math.sqrt(currentTimeout)));
		if(currentTimeout < 0) {
			currentTimeout = MAX_TIMEOUT;
		}
		currentTimeout = Math.min(currentTimeout, MAX_TIMEOUT);
		
		this.prepareModel();
		ResultStatus result = this.solveModel(currentTimeout);
		ILPSolution solution = this.retrieveSolution(result);
		return solution;
	}
	
	private void prepareModel() {
		this.solver = new MPSolver("ILPSolver", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);
		
		for(int variableId : this.ilpProblem.getVariableIdsOfUnfixedVariables()) {
			this.registerVariable(this.ilpProblem.getVariable(variableId), variableId);
		}
		for(ILPConstraint constraint : this.ilpProblem.getConstraints()) {
			registerConstraint(constraint);
		}
		registerObjective(this.ilpProblem.getObjective());
	}
	
	private ResultStatus solveModel(long timeout) {
		this.solver.enableOutput();
		System.out.println("Setting time-limit for each step to "+timeout+ " seconds.");
		timeout *= 1000;
		this.solver.setTimeLimit(timeout);
		return this.solver.solve();
	}
	
	private ILPSolution retrieveSolution(ResultStatus result) throws InvalidAttributeValueException {
		boolean optimal = result == ResultStatus.OPTIMAL;
		boolean feasible = optimal || result == ResultStatus.FEASIBLE;
		if (!feasible) { 
			System.err.println("No optimal or feasible solution found.");
			throw new RuntimeException("No optimal or feasible solution found.");
		}
		
		TIntIntHashMap variableSolutions = new TIntIntHashMap();
		double optimum = solver.objective().value();
		System.out.println("Solution found: "+optimum + " - Optimal: "+optimal);
		for(int variable : ilpProblem.getVariableIdsOfUnfixedVariables()) {
			double value = this.variableIdToCBCVar.get(variable).solutionValue();
			if(value != 0 && value != 1) {
				throw new InvalidAttributeValueException("Solution may only be 0 or 1");
			}
			variableSolutions.put(variable, (int) value);
		}
		return this.ilpProblem.createILPSolution(variableSolutions, optimal, optimum);
	}
	
	private void registerVariable(String variable, int variableID) {
		MPVariable var;
		if(this.onlyBinaryVariables) {
			var = solver.makeBoolVar(variable);
		} else {
			var = solver.makeIntVar(-MPSolver.infinity(), MPSolver.infinity(), variable);
		}
		this.variableIdToCBCVar.put(variableID, var);
	}
	
	private void registerConstraint(ILPConstraint constraint){
		MPConstraint constr = solver.makeConstraint(constraint.getName());

		for(int variableId : constraint.getLinearExpression().getVariables()) {
			double coefficient = constraint.getLinearExpression().getCoefficient(variableId);
			constr.setCoefficient(variableIdToCBCVar.get(variableId), coefficient);
		}
		
		switch(constraint.getComparator()) {
		case ge:
			constr.setBounds(constraint.getValue(), MPSolver.infinity());
			break;
		case le:
			constr.setBounds(-MPSolver.infinity(), constraint.getValue());
			break;
		case eq:
			constr.setBounds(constraint.getValue(), constraint.getValue());
			break;
		default:
			throw new IllegalArgumentException("Unsupported comparator: "+constraint.getComparator().toString());
		}
	}
	
	private void registerObjective(ILPObjective objective) {
		MPObjective obj = solver.objective();
		switch(objective.getObjectiveOperation()) {
		case maximize:
			obj.setMaximization();
			break;
		case minimize:
			obj.setMinimization();
			break;
		default:
			throw new IllegalArgumentException("Unsupported operation: "+objective.getObjectiveOperation().toString());
		}

		//define terms
		for (int variableId : objective.getLinearExpression().getVariables()) {
			double coefficient = objective.getLinearExpression().getCoefficient(variableId);
			obj.setCoefficient(variableIdToCBCVar.get(variableId), coefficient);
		}
	}

}
