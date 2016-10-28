package org.emoflon.ibex.tgg.core.compiler;

import org.emoflon.ibex.tgg.core.compiler.pattern.CorrContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.SrcContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.SrcPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.TrgContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.TrgPattern;

import language.TGG;
import language.TGGRule;

public class TGGCompiler {
	
	public static String getViatraPatterns(TGG tgg){
		
		String result = "";
		for(TGGRule rule : tgg.getRules()){
			OperationalPatternTemplate template = new OperationalPatternTemplate();
			result += template.get(new SrcContextPattern(rule));
			result += template.get(new SrcPattern(rule));
			result += template.get(new TrgContextPattern(rule));
			result += template.get(new TrgPattern(rule));
			result += template.get(new CorrContextPattern(rule));
		}
		return result;
	}
	
	

}

