package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.RulePartPattern;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class ComplementBWDPattern extends RulePartPattern {
	protected PatternFactory factory;
	private Collection<TGGRuleNode> signatureElements;

	public ComplementBWDPattern(PatternFactory factory) {
		this(factory.getFlattenedVersionOfRule(), factory);
	}

	private ComplementBWDPattern(TGGRule rule, PatternFactory factory) {
		super(rule);
		this.factory = factory;
		
		createPatternNetwork();
	}
	
	protected void createPatternNetwork() {
		// Rule Patterns
		if (rule instanceof TGGComplementRule)
			addTGGPositiveInvocation(factory.getFactory(((TGGComplementRule) rule).getKernel()).create(BWDPattern.class));
		
		// Marked Patterns
		createMarkedInvocations();
	}

	protected void createMarkedInvocations() {
		for (TGGRuleElement e : getSignatureNodes()) {
			TGGRuleNode node = (TGGRuleNode) e;
			if (nodeIsNotInKernel(node) && node.getDomainType().equals(DomainType.TRG)) {
				IbexPattern markedPattern = PatternFactory.getMarkedPattern(node.getDomainType(), true, false);
				TGGRuleNode invokedObject = (TGGRuleNode) markedPattern.getSignatureNodes().stream().findAny().get();

				Map<TGGRuleElement, TGGRuleElement> mapping = new HashMap<>();
				mapping.put(node, invokedObject);

				if (node.getBindingType() == BindingType.CREATE)
					addCustomNegativeInvocation(markedPattern, mapping);
				
				else if (node.getBindingType() == BindingType.CONTEXT) {
					addCustomPositiveInvocation(markedPattern, mapping);
				}
					
			}
		}
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleNode e) {
		throw new IllegalStateException();
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
		return "_" + ((TGGComplementRule)rule).getKernel().getName() + PatternSuffixes.BWD;
	}
	
	@Override
	public boolean ignored() {
		return Stream.concat(rule.getNodes().stream(), rule.getEdges().stream())
				.noneMatch(e -> e.getDomainType() == DomainType.TRG && e.getBindingType() == BindingType.CREATE);
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getDomainType() == node2.getDomainType();
	}

	
	@Override
	protected void initialize() {
		super.initialize();
		
		if (rule instanceof TGGComplementRule) {
			createSignatureNodes();
		}
	}

	private void createSignatureNodes() {
		
		addKernelTargetAndContextNodes();
		addComplementTargetAndContextNodes();
		
	}
	
	private void addKernelTargetAndContextNodes() {
		Collection<TGGRuleNode> kernelNodes = ((TGGComplementRule) rule).getKernel().getNodes();
		for (TGGRuleNode n : kernelNodes) {
			if(n.getDomainType() == DomainType.TRG || n.getBindingType() == BindingType.CONTEXT)
				getSignatureNodes().add(createProxyNode(n));
			}
	}

	private void addComplementTargetAndContextNodes() {
		for (TGGRuleNode n : rule.getNodes()) {
			if(nodeIsNotInKernel(n) && (n.getDomainType() == DomainType.TRG || n.getBindingType() == BindingType.CONTEXT))
				getSignatureNodes().add(createProxyNode(n));
		}
	}
	
	private boolean nodeIsNotInKernel(TGGRuleElement node) {
		return ((TGGComplementRule) rule).getKernel().getNodes().stream().noneMatch(re -> re.getName().equals(node.getName()));
	}

	private TGGRuleNode createProxyNode(TGGRuleNode node) {
		TGGRuleNode copiedNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		copiedNode.setName(node.getName());
		copiedNode.setType(node.getType());
		copiedNode.setBindingType(node.getBindingType());
		copiedNode.setDomainType(node.getDomainType());
		return copiedNode;
	}
	
	@Override
	public Collection<TGGRuleNode> getSignatureNodes() {
		if (signatureElements == null) {
			signatureElements = new HashSet<TGGRuleNode>();
		}
		return signatureElements;
	}

}
