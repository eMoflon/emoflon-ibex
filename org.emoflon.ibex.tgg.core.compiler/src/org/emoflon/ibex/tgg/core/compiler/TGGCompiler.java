package org.emoflon.ibex.tgg.core.compiler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.emoflon.ibex.tgg.core.compiler.pattern.CorrContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.SrcContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.SrcPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.TrgContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.TrgPattern;

import language.TGGRule;

public class TGGCompiler {
	
	public String getViatraPatterns(TGGRule rule){
		OperationalPatternTemplate template = new OperationalPatternTemplate();
		return Arrays.asList(	
					template.generateHeaderAndImports(determineImports(rule), rule.getName()),
					template.generatePattern(new SrcContextPattern(rule)),
					template.generatePattern(new SrcPattern(rule)),
					template.generatePattern(new TrgContextPattern(rule)),
					template.generatePattern(new TrgPattern(rule)),
					template.generatePattern(new CorrContextPattern(rule)))
				.stream().collect(Collectors.joining());
	}

	private Map<String, String> determineImports(TGGRule rule) {
		Map<String, String> imports = new HashMap<>();

		imports.put("dep_ibex", "platform:/plugin/org.emoflon.ibex.tgg.core.runtime/model/Runtime.ecore");
		
		Set<EPackage> packs = rule.getNodes().stream().map(n -> n.getType().getEPackage()).collect(Collectors.toSet());
		Iterator<EPackage> it = packs.iterator();
		for (int i = 0; i < packs.size(); i++) {
			imports.put("dep_" + i, it.next().getNsURI());
		}
		
		
		return imports;
	}
}

