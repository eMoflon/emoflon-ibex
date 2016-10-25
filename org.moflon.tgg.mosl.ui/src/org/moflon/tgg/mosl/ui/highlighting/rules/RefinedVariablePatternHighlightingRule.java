package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.swt.SWT;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLColor;

public class RefinedVariablePatternHighlightingRule extends AbstractRefinedVariablePatternHighlightingRule {

	public RefinedVariablePatternHighlightingRule() {
		super("RefinedPattern", "Refined Pattern", 100);
	}

	@Override
	protected TextStyle getTextStyle() {
	      TextStyle ts = new TextStyle();
	      ts.setColor(MOSLColor.BLACK.getColor());
	      ts.setStyle(SWT.BOLD);
	      return ts;
	}

	@Override
	public boolean getOperatorCondition(Operator op) {
		return op == null || op.getValue() == null || "".equals(op.getValue());
	}





}
