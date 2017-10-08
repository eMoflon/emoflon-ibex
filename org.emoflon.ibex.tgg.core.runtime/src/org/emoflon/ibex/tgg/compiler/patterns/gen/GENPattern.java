package org.emoflon.ibex.tgg.compiler.patterns.gen;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.RulePartPattern;

import language.BindingType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class GENPattern extends RulePartPattern {
	
	protected PatternFactory factory;
	private Collection<TGGRuleElement> signatureElements = new HashSet<TGGRuleElement>();

	public GENPattern(PatternFactory factory) {
		this(factory.getFlattenedVersionOfRule(), factory); 
	}
	
	private GENPattern(TGGRule rule, PatternFactory factory) {
		super(rule);
		this.factory = factory;
		signatureElements = getSignatureElements(getRule());
		
		createPatternNetwork();
	}

	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(GENForRefinementInvocationsPattern.class));
		
		addTGGNegativeInvocations(factory.createPatternsForMultiplicityConstraints());
		addTGGNegativeInvocations(factory.createPatternsForContainmentReferenceConstraints());
		
		addTGGNegativeInvocations(factory.createPatternsForUserDefinedSourceNACs());
		addTGGNegativeInvocations(factory.createPatternsForUserDefinedTargetNACs());
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getBindingType() == BindingType.CONTEXT;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return false;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.GEN;
	}

	@Override
	public Collection<TGGRuleElement> getSignatureElements() {
		return signatureElements;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		
		if (rule.getKernel() != null) {
			// TODO:  "flatten kernel into complement rule"
			Collection<TGGRuleNode> kernelContextNodes = rule.getKernel().getNodes()
					.stream()
					.filter(e -> isRelevantForSignature(e))
					.collect(Collectors.toSet());
			
			for (TGGRuleNode kernelContextNode : kernelContextNodes) {
				if ( ! rule.getNodes().contains(kernelContextNode)) {
					//rule.getNodes().add(kernelContextNode);
					this.getBodyNodes().add(kernelContextNode); 
				}
			}
		}
	}

}
