package org.emoflon.ibex.tgg.operational.util;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

public interface IMatch {

	EObject get(String name);

	Collection<String> parameterNames();

	String patternName();

}
