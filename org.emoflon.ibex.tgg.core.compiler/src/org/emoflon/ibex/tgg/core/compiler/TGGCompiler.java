package org.emoflon.ibex.tgg.core.compiler;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.core.compiler.pattern.CorrContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.SrcContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.SrcPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.TrgContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.TrgPattern;

import language.TGG;

public class TGGCompiler {

	private TGG tgg;
	
	public TGGCompiler(TGG tgg) {
		this.tgg = tgg;
	}
	
	public String getViatraPatterns(){
		OperationalPatternTemplate template = new OperationalPatternTemplate();
		return tgg.getRules().stream().flatMap(rule -> 
			Arrays.asList(	
					template.get(new SrcContextPattern(rule)),
					template.get(new SrcPattern(rule)),
					template.get(new TrgContextPattern(rule)),
					template.get(new TrgPattern(rule)),
					template.get(new CorrContextPattern(rule))
			).stream()).collect(Collectors.joining());
	}
}

