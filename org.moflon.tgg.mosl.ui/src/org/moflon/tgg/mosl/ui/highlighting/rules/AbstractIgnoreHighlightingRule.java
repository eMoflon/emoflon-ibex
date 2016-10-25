package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public abstract class AbstractIgnoreHighlightingRule extends AbstractHighlightingRule {

	public AbstractIgnoreHighlightingRule(String id, String description) {
		super(id, description, Integer.MAX_VALUE);
	}

	@Override
	protected TextStyle getTextStyle() {
		return null;
	}

	protected void setHighlighting(INode node){
		//do nothing
	}
	
	public void setHighlightingConfiguration(IHighlightingConfigurationAcceptor acceptor){
		//do nothing
	}
	
	@Override
	protected boolean getHighlightingConditions(EObject moslObject, INode node) {
		return getIgnoreConditions(moslObject, node);
	}
	
	protected abstract boolean getIgnoreConditions(EObject moslObject, INode node);

}
