package org.moflon.tgg.mosl.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.ui.highlighting.rules.AbstractHighlightingRule;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLColor;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLHighlightProviderHelper;

public class MOSLHighlightingConfiguration extends DefaultHighlightingConfiguration
{
	public final static String BOOL_ID = "bool";
	public final static String ENUM_ID = "enum";
	public final static String SOURCE_ID = "source";
	public final static String TARGET_ID = "target";
	
	
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		acceptor.acceptDefaultHighlighting(BOOL_ID, "Boolean", boolTextStyle());
		acceptor.acceptDefaultHighlighting(ENUM_ID, "Enumeration", stringTextStyle());
		acceptor.acceptDefaultHighlighting(SOURCE_ID, "Source Design", sourceTextStyle());
		acceptor.acceptDefaultHighlighting(TARGET_ID, "Target Design", targetTextStyle());		
		for(AbstractHighlightingRule rule : MOSLHighlightProviderHelper.getHighlightRules())
			rule.setHighlightingConfiguration(acceptor);
	}
	
   @Override
   public TextStyle keywordTextStyle()
   {
      TextStyle ts = super.keywordTextStyle();
      ts.setStyle(SWT.ITALIC);
      return ts;
   }
   
   @Override
   public TextStyle commentTextStyle(){
	   TextStyle ts = super.commentTextStyle();
	   ts.setColor(MOSLColor.GRAY.getColor());
	   return ts;
   }
   
   @Override
	public TextStyle stringTextStyle() {
		TextStyle textStyle = super.stringTextStyle();
		textStyle.setColor(MOSLColor.DARK_ORANGE.getColor());
		return textStyle;
	}
   
   public TextStyle boolTextStyle(){
	   TextStyle ts = super.keywordTextStyle();
	   ts.setStyle(SWT.BOLD);
	   return ts;
   }
   
   public TextStyle sourceTextStyle(){
	   TextStyle ts = keywordTextStyle();
	   ts.setBackgroundColor(MOSLColor.LIGHT_YELLOW.getColor());
	   ts.setColor(MOSLColor.BLACK.getColor());
	   return ts;
   }
   
   public TextStyle targetTextStyle(){
	   TextStyle ts = keywordTextStyle();
	   ts.setBackgroundColor(MOSLColor.MISTY_ROSE.getColor());
	   ts.setColor(MOSLColor.BLACK.getColor());
	   return ts;
   }
   
}
