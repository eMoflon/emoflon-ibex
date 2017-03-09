package org.emoflon.ibex.tgg.operational.util;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.gervarro.democles.common.IDataFrame;
import org.gervarro.democles.specification.emf.Pattern;

public class IbexMatch implements IMatch {
	private IDataFrame frame;
	private Pattern pattern;

	public IbexMatch(IDataFrame frame, Pattern pattern) {
		this.frame = frame;
		this.pattern = pattern;
	}
	
	public Collection<String> parameterNames() {
		return pattern.getSymbolicParameters()
				.stream()
				.map(p -> p.getName())
				.collect(Collectors.toList());
	}

	public EObject get(String name) {
		int index = varNameToIndex(name);
		return index == -1 ? null : (EObject) frame.getValue(index);
	}

	public String patternName() {
		return pattern.getName();
	}
	
	private int varNameToIndex(String varName) {
		for(int i = 0; i < pattern.getSymbolicParameters().size(); i++){
			if(varName.equals(pattern.getSymbolicParameters().get(i).getName()))
				return i;
		}
		return -1;
//		throw new IllegalArgumentException("Not able to find variable " + varName + " in match of " + pattern.getName());
	}
}
