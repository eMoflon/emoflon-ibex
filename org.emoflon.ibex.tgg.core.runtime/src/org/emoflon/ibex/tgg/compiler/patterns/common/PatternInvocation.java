package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.Map;

import language.TGGRuleNode;

public class PatternInvocation {
	private IPattern invoking;
	private IPattern invoked;
	private Map<TGGRuleNode, TGGRuleNode> mapping;
	
	public PatternInvocation(IPattern invoking, IPattern invoked, Map<TGGRuleNode, TGGRuleNode> mappings) {
		this.invoking = invoking;
		this.invoked = invoked;
		this.mapping = mappings;
	}

	public IPattern getInvokedPattern() {
		return invoked;
	}

	public TGGRuleNode getPreImage(TGGRuleNode image) {
		for (TGGRuleNode preImage : mapping.keySet()) {
			if(mapping.get(preImage).equals(image))
				return preImage;
		}
		
		throw new IllegalArgumentException("The invocation [" + invoking.getName() + " -> " + invoked.getName() + "] does not have a preimage for " + image);
	}
}
