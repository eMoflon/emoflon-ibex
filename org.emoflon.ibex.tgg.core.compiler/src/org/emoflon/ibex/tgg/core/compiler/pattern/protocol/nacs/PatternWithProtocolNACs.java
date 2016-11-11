package org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleElement;

public abstract class PatternWithProtocolNACs extends Pattern {

	public PatternWithProtocolNACs(TGGRule rule) {
		super(rule);
	}
	
	@Override
	protected Collection<TGGRuleElement> getSignatureElements(TGGRule rule) {
		return Stream.concat(rule.getNodes().stream(), rule.getEdges().stream())
				.filter(e -> e.getBindingType() != BindingType.CREATE || e.getDomainType() == getInputDomainType())
				.collect(Collectors.toSet());
	}
	
	public Collection<TGGRuleElement> getNACrelevantElements(){
		return Stream.concat(rule.getNodes().stream(), rule.getEdges().stream())
				.filter(e -> e.getBindingType() == BindingType.CREATE && e.getDomainType() == getInputDomainType())
				.collect(Collectors.toSet());
	}

	abstract protected DomainType getInputDomainType();

}
