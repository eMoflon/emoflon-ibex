package org.moflon.tgg.mosl.ui.highlighting.rules;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.mosl.tgg.CorrVariablePattern;
import org.moflon.tgg.mosl.tgg.NamePattern;
import org.moflon.tgg.mosl.tgg.Rule;

public abstract class AbstractRefinedVariablePatternHighlightingRule extends AbstractNamePatternHighlightRule implements IOperatorRelated {

	public AbstractRefinedVariablePatternHighlightingRule(String id, String description) {
		super(id, description);
	}

	public AbstractRefinedVariablePatternHighlightingRule(String id, String description, int prio) {
		super(id, description, prio);
	}

	@Override
	protected boolean getNameCondition(NamePattern namePattern) {
		Rule rule = null;
		EObject eContainer = namePattern.eContainer();
		if (!operatorCondition(namePattern) || !(eContainer instanceof Rule))
			return false;
		else {
			rule = Rule.class.cast(eContainer);
			return isRefined(rule, rule.getSourcePatterns().contains(namePattern), namePattern);
		}
	}

	private boolean operatorCondition(NamePattern namePattern) {
		return namePattern != null && getOperatorCondition(namePattern.getOp());
	}

	private boolean isRefined(Rule rule, boolean isSource, NamePattern np) {
		boolean tmp = false;
		for (Rule supertype : rule.getSupertypes()) {
			if (tmp)
				return tmp;
			tmp = tmp || containsRuleVariablePatternName(supertype, isSource, np);

			if (tmp)
				return tmp;
			tmp = tmp || isRefined(supertype, isSource, np);
		}
		return tmp;
	}

	private boolean containsRuleVariablePatternName(Rule rule,boolean isSource, NamePattern origin) {
		return origin instanceof CorrVariablePattern
				? containsRuleVariablePatternName(rule.getCorrespondencePatterns(), origin)
				: (isSource?
						containsRuleVariablePatternName(rule.getSourcePatterns(), origin)
						: containsRuleVariablePatternName(rule.getTargetPatterns(), origin));
	}

	private boolean containsRuleVariablePatternName(Collection<? extends NamePattern> patterns, NamePattern origin) {
		for (NamePattern np : patterns) {
			if (np != null && origin != null && origin.getName() != null
					&& origin.getName().compareTo(np.getName()) == 0)
				return true;
		}
		return false;
	}

}
