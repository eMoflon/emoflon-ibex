package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.moflon.tgg.mosl.tgg.NamePattern;


public abstract class AbstractNamePatternHighlightRule extends AbstractHighlightingRule {

	public AbstractNamePatternHighlightRule(String id, String description) {
		super(id, description);
	}
	
	public AbstractNamePatternHighlightRule(String id, String description, int prio) {
		super(id, description, prio);
	}

	@Override
	protected boolean getHighlightingConditions(EObject moslObject, INode node) {
		if(moslObject instanceof NamePattern)
			return getNameCondition(NamePattern.class.cast(moslObject));
		return false;
	}

	
	protected abstract boolean getNameCondition(NamePattern namePattern);

}
