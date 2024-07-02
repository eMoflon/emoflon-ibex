package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.EPackage.Registry;
import org.emoflon.ibex.common.operational.IContextPatternInterpreter;
import org.emoflon.ibex.common.operational.IMatchObserver;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.modules.IbexExecutable;

/**
 * Interface for bidirectional graph transformations via TGGs
 */
public interface IBlackInterpreter extends IContextPatternInterpreter {
	public void initialise(IbexExecutable executable, IbexOptions options, Registry registry, IMatchObserver matchObserver);
}
