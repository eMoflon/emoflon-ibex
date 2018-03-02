package org.emoflon.ibex.tgg.operational;

import org.emoflon.ibex.common.operational.IContextPatternInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

/**
 * Interface for bidirectional graph transformations via TGGs
 */
public interface IBlackInterpreter extends IContextPatternInterpreter {
	/**
	 * Sets the IBeXOptions.
	 * 
	 * @param options
	 *            the options to set
	 */
	public void setOptions(IbexOptions options);
}
