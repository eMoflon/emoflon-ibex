package org.emoflon.ibex.tgg.runtime;

import org.eclipse.emf.ecore.EPackage.Registry;
import org.emoflon.ibex.common.operational.IContextPatternInterpreter;
import org.emoflon.ibex.common.operational.IMatchObserver;
import org.emoflon.ibex.tgg.runtime.strategies.modules.IbexExecutable;
import org.emoflon.ibex.util.config.IbexOptions;

/**
 * Interface for bidirectional graph transformations via TGGs
 */
public interface IBlackInterpreter extends IContextPatternInterpreter {
	public void initialise(IbexExecutable executable, IbexOptions options, Registry registry, IMatchObserver matchObserver);
}
