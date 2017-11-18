package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

import org.antlr.grammar.v3.ANTLRv3Parser.throwsSpec_return;
import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.RulePartPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcContextPattern;

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
	private Collection<TGGRuleElement> signatureElements;

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
		//createMarkedInvocations(false);

	}

	protected void createMarkedInvocations(boolean positive) {
		for (TGGRuleElement el : getSignatureElements()) {
			TGGRuleNode node = (TGGRuleNode) el;
			if (node.getBindingType().equals(positive ? BindingType.CONTEXT : BindingType.CREATE) && node.getDomainType().equals(DomainType.TRG)) {
				IbexPattern markedPattern = PatternFactory.getMarkedPattern(node.getDomainType(), true, false);
				TGGRuleNode invokedObject = (TGGRuleNode) markedPattern.getSignatureElements().stream().findFirst().get();

				Map<TGGRuleElement, TGGRuleElement> mapping = new HashMap<>();
				mapping.put(node, invokedObject);

				if (positive)
					addCustomPositiveInvocation(markedPattern, mapping);
				else
					addCustomNegativeInvocation(markedPattern, mapping);
			}
		}
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
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
		return "_" + "------------COMPLEMET------------------" + PatternSuffixes.BWD;
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
			embedAdditonalContextNodes();
		}
	}

	private void embedAdditonalContextNodes() {
		Collection<TGGRuleNode> kernelNodes = ((TGGComplementRule) rule).getKernel().getNodes();
		
		addKernelTargetAndContextNodes(kernelNodes);
		addRestOfTargetAndContextNodes(kernelNodes);
		}
	
	private void addKernelTargetAndContextNodes(Collection<TGGRuleNode> kernelNodes) {
		for (TGGRuleNode n : kernelNodes) {
			if(n.getDomainType() == DomainType.TRG || n.getBindingType() == BindingType.CONTEXT)
				getSignatureElements().add(createProxyNode(n));
			}
	}

	private void addRestOfTargetAndContextNodes(Collection<TGGRuleNode> kernelNodes) {
		Collection<TGGRuleNode> complementNodes = rule.getNodes();
		for (TGGRuleNode n : complementNodes) {
			if(!kernelNodes.contains(n) && (n.getDomainType() == DomainType.TRG || n.getBindingType() == BindingType.CONTEXT))
				getSignatureElements().add(createProxyNode(n));
		}
	}

	/* Creates a simple node with just the same name and type of kernelNode */
	private TGGRuleNode createProxyNode(TGGRuleNode kernelNode) {
		TGGRuleNode node = LanguageFactory.eINSTANCE.createTGGRuleNode();
		node.setName(kernelNode.getName());
		node.setType(kernelNode.getType());

		return node;
	}
	
	@Override
	public Collection<TGGRuleElement> getSignatureElements() {
		if (signatureElements == null) {
			signatureElements = new HashSet<TGGRuleElement>();
		}
		return signatureElements;
	}

}
