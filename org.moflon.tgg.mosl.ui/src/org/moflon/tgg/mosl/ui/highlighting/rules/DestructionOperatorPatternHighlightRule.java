package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLColor;

public class DestructionOperatorPatternHighlightRule extends AbstractOperatorPatternHighlightRule {

	public DestructionOperatorPatternHighlightRule() {
		super("DestroyPattern", "Destroy-Operator");
	}

	@Override
	public boolean getOperatorCondition(Operator op) {
		return  op!= null && op.getValue() != null && op.getValue().contains("--");
	}

	@Override
	protected TextStyle getTextStyle() {
	      TextStyle ts = new TextStyle();
	      ts.setColor(MOSLColor.RED.getColor());
	      return ts;
	}

}
