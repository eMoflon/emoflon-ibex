package org.emoflon.ibex.tgg.compiler;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXPatternSet;

/**
 * Transforms TGG models to IBexPatterns
 * 
 * @author aanjorin
 */
public interface IContextPatternTransformation {

	/**
	 * Decide which concrete implementation to use.
	 * 
	 * @param options
	 * @return
	 */
	static IContextPatternTransformation getTransformation(IbexOptions options) {
		return new ContextPatternTransformation(options);
	}

	IbexOptions getOptions();
	IBeXPatternSet transform();
}
