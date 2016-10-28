package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.swt.SWT;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLColor;

public class CreatedAndRefinedVariablePatternHighlightingRule extends AbstractRefinedVariablePatternHighlightingRule {

	public CreatedAndRefinedVariablePatternHighlightingRule() {
		super("CreatedAndRefined", "Created and Refined VariablePattern", 150);
	}

	@Override
	public boolean getOperatorCondition(Operator op) {
		return op!= null && op.getValue() != null && op.getValue().contains("++");
	}

	@Override
	protected TextStyle getTextStyle() {
	      TextStyle ts = new TextStyle();
	      ts.setColor(MOSLColor.DARK_GREEN.getColor());
	      ts.setStyle(SWT.BOLD);
	      return ts;
	}

}
