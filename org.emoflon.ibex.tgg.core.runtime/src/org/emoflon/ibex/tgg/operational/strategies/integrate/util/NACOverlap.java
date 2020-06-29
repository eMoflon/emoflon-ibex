package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

public class NACOverlap {

	private Collection<String> parameters;
	private ITGGMatch match;
	
	private Integer hash = null;
	
	public NACOverlap(ITGGMatch nacMatch) {
		this.match = nacMatch;
		this.parameters = nacMatch.getParameterNames();
	}
	
	public NACOverlap(ITGGMatch match, Collection<String> parameters) {
		this.match = match;
		this.parameters = parameters;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof NACOverlap) {
			NACOverlap otherOverlap = (NACOverlap) obj;
			return parameters.stream().allMatch(p -> match.get(p).equals(otherOverlap.match.get(p)));
		}
		return false;
	}

	@Override
	public int hashCode() {
		if(hash == null) 
			hash = Arrays.hashCode(parameters.stream().map(n -> match.get(n)).collect(Collectors.toList()).toArray());
		return hash;
	}
}
