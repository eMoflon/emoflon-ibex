package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;
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
	private Map<TGGRule, Collection<Pattern>> ruleToPatterns;
	private DomainType domain;
	
	public NoDECsPatterns(TGGRule rule, Map<TGGRule, Collection<Pattern>> ruleToPatterns, DomainType domain) {
		super(rule);
		this.ruleToPatterns = ruleToPatterns;
		this.domain = domain;
		createDECEntries(rule, domain);
		initialize();
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

					// check type
					if (!DECHelper.getType(eType, eDirection).equals(n.getType()))
						continue;

					int numOfEdges = DECHelper.countEdgeInRule(rule, n, eType, eDirection);
					// if the edge has any multiplicity or there is no edge representation at all in this rule
					if (eType.getUpperBound() == 1 && numOfEdges == 1)
						continue;
					
					// initialise DECpattern 
					DECPattern decPattern = new DECPattern(rule, n, eType, eDirection, ruleToPatterns);

					// check if the edges are represented and translated elsewhere
					TGG tgg = (TGG) rule.eContainer();
					List<TGGRule> rules = tgg.getRules().stream().filter(r -> DECHelper.countEdgeInRule(r, eType, eDirection) > numOfEdges).collect(Collectors.toList());

					// find src or trg pattern already generated for the found rules
					for (TGGRule filteredRule : rules) {
						Collection<Pattern> patterns = ruleToPatterns.get(filteredRule);

						Pattern pattern = null;
						switch (n.getDomainType()) {
						case SRC:
							pattern = patterns.stream().filter(p -> p instanceof SrcPattern).findFirst().get();
							break;
						case TRG:
							pattern = patterns.stream().filter(p -> p instanceof TrgPattern).findFirst().get();
							break;
						default:
							throw new RuntimeException("DECPatterns: Not defined for anything else than SRC or TRG patterns!");
						}

						decPattern.getNegativeInvocations().add(pattern);
					}

					// if any decpattern was created which has at least one negative invocation, we'll add it to this pattern
					// a negative invocation means here that we were able to find another rule that translated the edge
					// which might remain untranslated if we apply the current rule
					// we also register the pattern so that it is found when we generate the code and initialize the search pattern
					if (!decPattern.isEmpty() || DECHelper.isEdgeInTGG(tgg, eType, eDirection)) {
						getNegativeInvocations().add(decPattern);
						decPattern.createSearchEdgePattern(rule, n, eType, eDirection, ruleToPatterns);
						ruleToPatterns.get(rule).add(decPattern);
					}
				}

			}
		}
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return false;
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
		return getPositiveInvocations().isEmpty() && getNegativeInvocations().isEmpty();
	}
}
