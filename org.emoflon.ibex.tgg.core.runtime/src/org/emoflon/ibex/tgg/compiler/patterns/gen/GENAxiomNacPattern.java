/**
 * 
 */
package org.emoflon.ibex.tgg.compiler.patterns.gen;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;

import language.BindingType;
import language.NAC;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

/**
 * Pattern used for matching the NACs of Axioms for model generators. The NAC pattern is just a regular all black pattern. If the pattern matches
 * the axiom must not be applied.
 */
public class GENAxiomNacPattern extends IbexBasePattern{

	/**
	 * Creates a new NAC pattern for an axiom rule
	 * @param factory 	The pattern factory
	 * @param rule		The axiom rule
	 * @param nac		The nac for the axiom
	 */
	public GENAxiomNacPattern(BlackPatternFactory factory, TGGRule rule, NAC nac) {
		super(factory);
		this.initialise(rule, nac);
	}
	
	/**
	 * Initializes the NAC pattern
	 * @param rule	The axiom rule
	 * @param nac	The nac for the axiom
	 */
	protected void initialise(TGGRule rule, NAC nac) {		
		String name = rule.getName() + PatternSuffixes.SEP + nac.getName() + PatternSuffixes.GEN_AXIOM_NAC;
		Collection<TGGRuleNode> signatureNodes = nac.getNodes().stream()
				   .filter(n -> n.getBindingType() == BindingType.CONTEXT)
				   .collect(Collectors.toList());
	
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		//Root pattern
		return true;
	}
	
	/**
	 * Checks if the given name matches the naming structure of a {@link GENAxiomNacPattern} by checking if the suffix matches.
	 * @param name	The name of the pattern to check
	 * @return	true if the name has the suffix {@link PatternSuffixes}.GEN_AXIOM_NAC
	 */
	public static boolean isGENAxiomNacPattern(String name) {
		return name.endsWith(PatternSuffixes.GEN_AXIOM_NAC);
	}
	
	/**
	 * Returns the axiom name encoded in the name of the rule
	 * @param name	The name of the pattern
	 * @return	The part of the name representing the axiom name
	 */
	public static String getAxiomName(String name) {
		return name.substring(0, name.indexOf(PatternSuffixes.SEP));
	}

}
