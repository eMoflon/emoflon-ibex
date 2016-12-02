package org.emoflon.ibex.tgg.core.compiler.pattern;

import java.util.ArrayList;
import java.util.Collection;

import language.TGGRule;
import language.TGGRuleElement;

public abstract class Pattern {

	protected TGGRule rule;
	
	/**
	 * each positive pattern invocation for a pattern pat corresponds to the following text
	 * find pat(<<signature elements of pat separated with ",">>);
	 */
	protected Collection<Pattern> positiveInvocations = new ArrayList<>();
	
	/**
	 * each negative pattern invocation for a pattern pat corresponds to the following text
	 * neg find pat(<<signature elements of pat separated with ",">>);
	 */
	protected Collection<Pattern> negativeInvocations = new ArrayList<>();
	
	/**
	 * each signature element e of a pattern corresponds to a parameter:
	 * 
	 *  pattern(e1 : <type of e1>, e2: <type of e2,...)
	 *  
	 *  <type of e> is Edge if e is a TGGRuleEdge
	 */
	protected Collection<TGGRuleElement> signatureElements;
	
	
	public Pattern(TGGRule rule){
		this.rule = rule;
		signatureElements = getSignatureElements(rule);
	}
	
	protected abstract Collection<TGGRuleElement> getSignatureElements(TGGRule rule);

	public String getName(){
		return rule.getName() + getPatternNameSuffix();
	}
	
	abstract protected String getPatternNameSuffix();
	
	public Collection<TGGRuleElement> getSignatureElements() {
		return signatureElements;
	}
	
	public Collection<Pattern> getPositiveInvocations() {
		return positiveInvocations;
	}

	public Collection<Pattern> getNegativeInvocations() {
		return negativeInvocations;
	}
	
	public boolean ignored(){
		return false;
	}
	

	
}
