package org.emoflon.ibex.tgg.core.compiler.pattern.protocol;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.tgg.core.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRule;
import language.TGGRuleElement;
import language.TGGRuleNode;
import runtime.RuntimePackage;

public class ConsistencyPattern extends Pattern {

	public ConsistencyPattern(TGGRule rule){
		super(rule);
	}

	@Override
	protected Collection<TGGRuleElement> getSignatureElements(TGGRule rule) {
		Collection<TGGRuleElement> result = Stream.concat(rule.getNodes().stream(), rule.getEdges().stream())
				.collect(Collectors.toCollection(LinkedHashSet::new));
		
		TGGRuleNode protocolNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		protocolNode.setName(getProtocolNodeName());
		protocolNode.setType(RuntimePackage.eINSTANCE.getTGGRuleApplication());
		result.add(protocolNode);
		
		return result;
	}
	
	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.PROTOCOL;
	}
	
	public String getProtocolNodeName(){
		return "eMoflon_ProtocolNode";
	}
	
	public String getRuleName(){
		return rule.getName();
	}
	
	
	public Collection<TGGRuleElement> getContextSrc(){
		return getAllRuleElements().filter(e -> isConform(e, BindingType.CONTEXT, DomainType.SRC)).collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	public Collection<TGGRuleElement> getCreatedSrc(){
		return getAllRuleElements().filter(e -> isConform(e, BindingType.CREATE, DomainType.SRC)).collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	public Collection<TGGRuleElement> getContextTrg(){
		return getAllRuleElements().filter(e -> isConform(e, BindingType.CONTEXT, DomainType.TRG)).collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	public Collection<TGGRuleElement> getCreatedTrg(){
		return getAllRuleElements().filter(e -> isConform(e, BindingType.CREATE, DomainType.TRG)).collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	public Collection<TGGRuleElement> getContextCorr(){
		return getAllRuleElements().filter(e -> isConform(e, BindingType.CONTEXT, DomainType.CORR)).collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	public Collection<TGGRuleElement> getCreatedCorr(){
		return getAllRuleElements().filter(e -> isConform(e, BindingType.CREATE, DomainType.CORR)).collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	
	private Stream<TGGRuleElement> getAllRuleElements(){
		return Stream.concat(rule.getNodes().stream(), rule.getEdges().stream());
	}
	
	private boolean isConform(TGGRuleElement e, BindingType bindingType, DomainType domainType){
		return e.getBindingType() == bindingType && e.getDomainType() == domainType;
	}

	
}
