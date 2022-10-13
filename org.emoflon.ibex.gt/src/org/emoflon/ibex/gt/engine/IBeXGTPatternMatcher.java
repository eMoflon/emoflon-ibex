package org.emoflon.ibex.gt.engine;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.engine.MatchFilter;
import org.emoflon.ibex.common.engine.PatternMatchingEngine;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;

public abstract class IBeXGTPatternMatcher<E extends IBeXGTPatternMatcher<E, ENGINE_MATCH>, ENGINE_MATCH extends Object>
		extends PatternMatchingEngine<GTModel, ENGINE_MATCH> {

	protected Map<String, IBeXGTPattern<?, ?>> name2typedPattern = Collections.synchronizedMap(new LinkedHashMap<>());

	public IBeXGTPatternMatcher(GTModel ibexModel, ResourceSet model) {
		super(ibexModel, model);
	}

	protected void registerTypedPattern(IBeXGTPattern<?, ?> typedPattern) {
		name2typedPattern.put(typedPattern.patternName, typedPattern);
	}

	@Override
	protected MatchFilter<GTModel> createMatchFilter() {
		// TODO Auto-generated method stub
		return null;
	}

}
