package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.Map;

import language.TGGRuleElement;

public class PatternInvocation {
	private IbexPattern invoking;
	private IbexPattern invoked;
	private Map<TGGRuleElement, TGGRuleElement> mapping;
	
	public PatternInvocation(IbexPattern invoking, IbexPattern invoked, Map<TGGRuleElement, TGGRuleElement> mappings) {
		this.invoking = invoking;
		this.invoked = invoked;
		this.mapping = mappings;
	}

	public IbexPattern getInvokedPattern() {
		return invoked;
	}

	public TGGRuleElement getPreImage(TGGRuleElement image) {
		for (TGGRuleElement preImage : mapping.keySet()) {
			if(mapping.get(preImage).equals(image))
				return preImage;
		}
		
		throw new IllegalArgumentException("The invocation [" + invoking.getName() + " -> " + invoked.getName() + "] does not have a preimage for " + image);
	}
}
