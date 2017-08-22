package org.emoflon.ibex.tgg.compiler.patterns.gen;

import java.util.Collection;
import java.util.HashSet;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.RulePartPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgContextPattern;

import language.BindingType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class MODELGENPattern extends RulePartPattern {
	
	protected PatternFactory factory;
	private Collection<TGGRuleElement> signatureElements = new HashSet<TGGRuleElement>();

	public MODELGENPattern(PatternFactory factory) {
		this(factory.getRule(), factory); 
	}
	
	protected MODELGENPattern(TGGRule rule, PatternFactory factory) {
		super(rule);
		this.factory = factory;
		signatureElements = getSignatureElements(getRule());
		
		createPatternNetwork();
	}

	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(SrcContextPattern.class));
		addTGGPositiveInvocation(factory.create(CorrContextPattern.class));
		addTGGPositiveInvocation(factory.create(TrgContextPattern.class));
		
		addTGGNegativeInvocations(factory.createPatternsForMultiplicityConstraints());
		addTGGNegativeInvocations(factory.createPatternsForContainmentReferenceConstraints());
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
		return PatternSuffixes.MODELGEN;
	}

	@Override
	public Collection<TGGRuleElement> getSignatureElements() {
		return signatureElements;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}

}
