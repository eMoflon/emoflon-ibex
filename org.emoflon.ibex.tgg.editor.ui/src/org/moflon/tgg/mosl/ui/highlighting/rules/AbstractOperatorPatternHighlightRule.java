package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.tgg.OperatorPattern;

public abstract class AbstractOperatorPatternHighlightRule extends AbstractHighlightingRule implements IOperatorRelated{

	public AbstractOperatorPatternHighlightRule(String id, String description) {
		super(id, description);
	}

	@Override
	protected boolean getHighlightingConditions(EObject moslObject, INode node) {
		if(moslObject instanceof OperatorPattern)
			return getOperatorCondition(OperatorPattern.class.cast(moslObject).getOp());
		if(moslObject instanceof Operator)
			return getOperatorCondition(Operator.class.cast(moslObject));
		return false;
	}
	
	


}
