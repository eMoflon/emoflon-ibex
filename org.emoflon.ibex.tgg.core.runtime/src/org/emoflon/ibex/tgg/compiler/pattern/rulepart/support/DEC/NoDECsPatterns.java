package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Pair;
import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.SrcProtocolNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.TrgProtocolNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.RulePartPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.SrcPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.TrgPattern;

import language.BindingType;
import language.DomainType;
import language.TGG;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class NoDECsPatterns extends RulePartPattern {

	private boolean decDetected = false;
	private DECTrackingHelper decTC;
	private DomainType domain;

	public NoDECsPatterns(TGGRule rule, DECTrackingHelper decTC, DomainType domain) {
		super(rule);
		this.decTC = decTC;
		this.domain = domain;
		createDECEntries(rule, domain);
		initialize();
		addTGGPositiveInvocation(decTC.getRuleToPatternsMap().get(rule).stream().filter(p -> domain == DomainType.SRC ? p instanceof SrcPattern : p instanceof TrgPattern).findFirst().get());
	}

	/**
	 * This method creates DEC entries for each rule node if some edge has been detected that may have to be translated by some other rule application
	 * 
	 * @param rule
	 */
	private void createDECEntries(TGGRule rule, DomainType domain) {
		for (TGGRuleNode n : rule.getNodes()) {
			EClass nodeClass = n.getType();

			// entry nodes have to have create binding type
			if (!n.getBindingType().equals(BindingType.CREATE))
				continue;

			// we don't care about correspondence nodes and generate DECs only for the current domain (SRC or TRG)
			if (!n.getDomainType().equals(domain) || n.getDomainType().equals(DomainType.CORR))
				continue;

			// check if edge type occurs in our current rule for the given entry point,
			// if not -> search if some other rule might translate such an edge
			for (EReference eType : DECHelper.extractEReferences(nodeClass)) {
				for (EdgeDirection eDirection : EdgeDirection.values()) {
					TGG tgg = (TGG) rule.eContainer();

					// check type
					if (!DECHelper.getType(eType, eDirection).equals(n.getType()))
						continue;

					int numOfEdges = DECHelper.countEdgeInRule(rule, n, eType, eDirection, false, domain).getLeft();
					// if the edge has any multiplicity or there is no edge representation at all in this rule, else continue
					if (eType.getUpperBound() == 1 && numOfEdges == 1)
						continue;

					// if edge is handled in no rule at all
					if (!DECHelper.isEdgeInTGG(tgg, eType, eDirection, false, domain))
						continue;

					// initialise DECpattern and register the entryNodeName and DECNodeName for the signature mapping
					DECPattern decPattern = new DECPattern(rule, n, eType, eDirection, decTC);

					// check if the edges are represented and translated elsewhere and register signature mapping (todo: clean this up a bit)
					List<TGGRule> rules = tgg.getRules().stream().filter(r -> DECHelper.countEdgeInRule(r, eType, eDirection, true, domain).getLeft() > 0).collect(Collectors.toList());
					rules.stream().map(r -> Pair.of(r, DECHelper.countEdgeInRule(r, eType, eDirection, true, domain)))
							.forEach(p -> decTC.addToSignatureMapping(decPattern, p.getKey().getName(), p.getValue().getMiddle().getName(), p.getValue().getRight().getName()));

					// find src or trg pattern already generated for the found rules
					for (TGGRule filteredRule : rules) {
						Collection<IbexPattern> patterns = decTC.getRuleToPatternsMap().get(filteredRule);

						IbexPattern pattern = null;
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
						
						IbexPattern localProtocolPattern = decTC.getLocalProtocolPattern(decPattern, pattern, n.getName(), eType.getName(), eDirection);
						decPattern.addCustomNegativeInvocation(localProtocolPattern, decTC.getMapping(decPattern, localProtocolPattern));
					}

					// if any decpattern was created which has at least one negative invocation, we'll add it to this pattern
					// a negative invocation means here that we were able to find another rule that translated the edge
					// which might remain untranslated if we apply the current rule
					// we also register the pattern so that it is found when we generate the code and initialize the search pattern
					addTGGNegativeInvocation(decPattern);
					decTC.getRuleToPatternsMap().get(rule).add(decPattern);
				}
			}
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
		return PatternSuffixes.NO_DEC(domain);
	}

	public boolean isEmpty() {
		return getPositiveInvocations().isEmpty() && getNegativeInvocations().isEmpty();
	}
}
