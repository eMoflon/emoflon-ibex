package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Pair;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs.SrcProtocolNACsPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs.TrgProtocolNACsPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.RulePartPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.SrcPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.TrgPattern;

import language.BindingType;
import language.DomainType;
import language.TGG;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class NoDECsPatterns extends RulePartPattern {

	private boolean decDetected = false;
	private DECTrackingContainer decTC;
	private DomainType domain;

	public static final DECStrategy decStrategy = DECStrategy.DYNAMIC;

	public NoDECsPatterns(TGGRule rule, DECTrackingContainer decTC, DomainType domain) {
		super(rule);
		this.decTC = decTC;
		this.domain = domain;
		if (decStrategy != DECStrategy.NONE)
			createDECEntries(rule, domain);
		initialize();
		getPositiveInvocations().add(decTC.getRuleToPatternsMap().get(rule).stream()
				.filter(p -> domain == DomainType.SRC ? p instanceof SrcPattern : p instanceof TrgPattern).findFirst()
				.get());
	}

	/**
	 * This method creates DEC entries for each rule node if some edge has been
	 * detected that may have to be translated by some other rule application
	 * 
	 * @param rule
	 */
	private void createDECEntries(TGGRule rule, DomainType domain) {
		for (TGGRuleNode n : rule.getNodes()) {
			EClass nodeClass = n.getType();

			// entry nodes have to have create binding type
			if (!n.getBindingType().equals(BindingType.CREATE))
				continue;

			// we don't care about correspondence nodes and generate DECs only
			// for the current domain (SRC or TRG)
			if (!n.getDomainType().equals(domain) || n.getDomainType().equals(DomainType.CORR))
				continue;

			// check if edge type occurs in our current rule for the given entry
			// point,
			// if not -> search if some other rule might translate such an edge
			for (EReference eType : DECHelper.extractEReferences(nodeClass)) {
				for (EdgeDirection eDirection : EdgeDirection.values()) {
					TGG tgg = (TGG) rule.eContainer();

					// check type
					if (!DECHelper.getType(eType, eDirection).equals(n.getType()))
						continue;

					int numOfEdges = DECHelper.countEdgeInRule(rule, n, eType, eDirection, false, domain).getLeft();
					// if the edge has any multiplicity or there is no edge
					// representation at all in this rule, else continue
					if (eType.getUpperBound() == 1 && numOfEdges == 1)
						continue;

					// if edge is handled in no rule at all
					if (!DECHelper.isEdgeInTGG(tgg, eType, eDirection, false, domain))
						continue;

					// initialise DECpattern and register the entryNodeName and
					// DECNodeName for the signature mapping
					DECPattern decPattern = new DECPattern(rule, n, eType, eDirection, decTC);
					SearchEdgePattern sep = decPattern.createSearchEdgePattern(rule, n, eType, eDirection, decTC);
					decTC.addEntryAndDec(decPattern, n.getName(), DECHelper.getDECNode(sep.getRule()).getName());

					// check if the edges are represented and translated
					// elsewhere
					List<TGGRule> rules = tgg.getRules().stream()
							.filter(r -> DECHelper.countEdgeInRule(r, eType, eDirection, true, domain).getLeft() > 0)
							.collect(Collectors.toList());

					// generate pattern invocations to rules which could
					// translate the edge (only needed for the dynamic DEC-check)
					if (decStrategy == DECStrategy.DYNAMIC) {
						generatePatternInvocationsToCandidateRules(domain, n, eType, eDirection, decPattern, rules);
					}

					// a decPattern is relevant if 
					// (i) we check everything dynamically
					// or (ii) we only check statically and there was no other rule which would translate the edge
					if (decStrategy == DECStrategy.DYNAMIC
							|| (decStrategy == DECStrategy.STATIC && rules.size() == 0)) {
						getNegativeInvocations().add(decPattern);
						decTC.getRuleToPatternsMap().get(rule).add(decPattern);
						decDetected = true;
					}
				}
			}
		}
	}

	private void generatePatternInvocationsToCandidateRules(DomainType domain, TGGRuleNode n, EReference eType,
			EdgeDirection eDirection, DECPattern decPattern, List<TGGRule> rules) {

		// TODO: clean up this code and comment on important parts
		rules.stream().map(r -> Pair.of(r, DECHelper.countEdgeInRule(r, eType, eDirection, true, domain)))
				.forEach(p -> decTC.addToSignatureMapping(decPattern, p.getKey().getName(),
						p.getValue().getMiddle().getName(), p.getValue().getRight().getName()));

		// find src or trg pattern already generated for the
		// found
		// rules
		for (TGGRule filteredRule : rules) {
			Collection<Pattern> patterns = decTC.getRuleToPatternsMap().get(filteredRule);

			Pattern pattern = null;
			switch (n.getDomainType()) {
			case SRC:
				pattern = patterns.stream().filter(p -> p instanceof SrcProtocolNACsPattern).findFirst().get();
				break;
			case TRG:
				pattern = patterns.stream().filter(p -> p instanceof TrgProtocolNACsPattern).findFirst().get();
				break;
			default:
				throw new RuntimeException("DECPatterns: Not defined for anything else than SRC or TRG patterns!");
			}

			decPattern.getNegativeInvocations().add(pattern);
		}
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
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
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getDomainType() == domain;
	}

	@Override
	protected String getPatternNameSuffix() {
		return "_NO_DECs_" + domain.getName();
	}

	public boolean isEmpty() {
		return !decDetected;
	}
}
