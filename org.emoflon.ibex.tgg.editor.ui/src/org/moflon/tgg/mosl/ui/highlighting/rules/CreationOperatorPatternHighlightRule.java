package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLColor;

public class CreationOperatorPatternHighlightRule extends AbstractOperatorPatternHighlightRule {

	public CreationOperatorPatternHighlightRule() {
		super("CreatePattern", "Create-Operator");
	}

	@Override
	public boolean getOperatorCondition(Operator op) {
		return op!= null && op.getValue() != null && op.getValue().contains("++");
	}

	@Override
	protected TextStyle getTextStyle() {
	      TextStyle ts = new TextStyle();
	      ts.setColor(MOSLColor.DARK_GREEN.getColor());
	      return ts;
	}

}
