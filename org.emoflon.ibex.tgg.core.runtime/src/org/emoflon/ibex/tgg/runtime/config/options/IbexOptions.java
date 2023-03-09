package org.emoflon.ibex.tgg.runtime.config.options;

import java.io.IOException;

import org.emoflon.ibex.tgg.runtime.BlackInterpreter;
import org.emoflon.ibex.tgg.runtime.config.IRegistrationHelper;
import org.emoflon.ibex.tgg.runtime.strategies.modules.IbexExecutable;
import org.emoflon.ibex.tgg.runtime.strategies.modules.MatchDistributor;
import org.emoflon.ibex.tgg.runtime.strategies.modules.MatchHandler;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;

public class IbexOptions {

	private IbexExecutable executable;
	private TGGResourceHandler resourceHandler;
	private MatchHandler matchHandler;

	private MatchDistributor matchDistributor;
	private IRegistrationHelper registrationHelper;
	private BlackInterpreter<?> blackInterpreter;
	private SupportedILPSolver ilpSolver;

	public final PatternOptions patterns = new PatternOptions(this);
	public final DebugOptions debug = new DebugOptions(this);
	public final ProjectOptions project = new ProjectOptions(this);
	public final TGGOptions tgg = new TGGOptions(this);
	public final RepairOptions repair = new RepairOptions(this);
	public final CSPOptions csp = new CSPOptions(this);
	public final PropagateOptions propagate = new PropagateOptions(this);
	public final IntegrationOptions integration = new IntegrationOptions(this);

	public IbexOptions() {
		try {
			resourceHandler(new TGGResourceHandler());
		} catch (IOException e) {
			e.printStackTrace();
		}
		matchDistributor = new MatchDistributor(this);
		matchHandler = new MatchHandler(this);
		this.ilpSolver = SupportedILPSolver.Sat4J;
	}

	public IbexExecutable executable() {
		return executable;
	}

	public IbexOptions executable(IbexExecutable executable) {
		this.executable = executable;
		if (resourceHandler != null)
			resourceHandler.setExecutable(executable);
		return this;
	}

	public TGGResourceHandler resourceHandler() {
		return resourceHandler;
	}

	public IbexOptions resourceHandler(TGGResourceHandler resourceHandler) {
		this.resourceHandler = resourceHandler;
		resourceHandler.setOptions(this);
		if (executable != null)
			executable.setResourceHandler(resourceHandler);
		return this;
	}

	public MatchDistributor matchDistributor() {
		return matchDistributor;
	}

	public MatchHandler matchHandler() {
		return matchHandler;
	}

	public IbexOptions registrationHelper(IRegistrationHelper registrationHelper) {
		this.registrationHelper = registrationHelper;
		return this;
	}

	public IRegistrationHelper registrationHelper() {
		return registrationHelper;
	}

	public BlackInterpreter<?> blackInterpreter() {
		return blackInterpreter;
	}

	public IbexOptions blackInterpreter(BlackInterpreter<?> blackInterpreter) {
		this.blackInterpreter = blackInterpreter;
		return this;
	}

	public SupportedILPSolver ilpSolver() {
		return ilpSolver;
	}

	public IbexOptions ilpSolver(SupportedILPSolver ilpSolver) {
		this.ilpSolver = ilpSolver;
		return this;
	}

}