package org.emoflon.ibex.tgg.operational.util;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;

import language.TGGRule;

public class EmptyMatch implements IMatch {

	private TGGRule r;
	
	public EmptyMatch(TGGRule r) {
		this.r = r;
	}

	@Override
	public EObject get(String name) {
//		throw new IllegalArgumentException("This is an empty match!");
		return null; //FIXME Use an Optional
	}

	@Override
	public Collection<String> parameterNames() {
		return Collections.emptyList();
	}
	
	@Override
	public String patternName() {
		return r.getName();
	}
}
