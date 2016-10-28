package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLColor;

public class NoOperatorPatternHighlightRule extends AbstractOperatorPatternHighlightRule {

	public NoOperatorPatternHighlightRule() {
		super("NoOperator", "If no operator exist");
	}

	@Override
	public boolean getOperatorCondition(Operator op) {
		return  op== null || op.getValue() == null || op.getValue().equals("");
	}

	@Override
	protected TextStyle getTextStyle() {
	      TextStyle ts = new TextStyle();
	      ts.setColor(MOSLColor.BLACK.getColor());
	      return ts;
	}



}
