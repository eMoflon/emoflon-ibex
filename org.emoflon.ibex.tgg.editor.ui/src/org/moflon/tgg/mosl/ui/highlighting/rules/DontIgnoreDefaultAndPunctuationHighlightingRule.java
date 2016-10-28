package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.nodemodel.INode;
import org.moflon.tgg.mosl.ui.highlighting.MOSLHighlightingConfiguration;
import org.moflon.tgg.mosl.ui.highlighting.MOSLTokenMapper;

public class DontIgnoreDefaultAndPunctuationHighlightingRule extends AbstractIgnoreHighlightingRule {

	public DontIgnoreDefaultAndPunctuationHighlightingRule() {
		super("DontIgnoreDefault", "An Ignore Highlighting Rule");
	}

	@Override
	protected boolean getIgnoreConditions(EObject moslObject, INode node) {
		if(node != null && node.getGrammarElement() instanceof Keyword){
			return !isDefaultOrPunctuation(Keyword.class.cast(node.getGrammarElement()));
		}
		return false;
	}

	private boolean isDefaultOrPunctuation(Keyword keyword){
		String id = MOSLTokenMapper.getIDFromToken(keyword.getValue());
		return id !=null && (id.equals(MOSLHighlightingConfiguration.DEFAULT_ID) || id.equals(MOSLHighlightingConfiguration.PUNCTUATION_ID));
	}


}
