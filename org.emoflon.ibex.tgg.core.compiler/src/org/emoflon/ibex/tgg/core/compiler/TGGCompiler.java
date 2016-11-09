package org.emoflon.ibex.tgg.core.compiler;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.operational.BWDPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.operational.CCPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.operational.CorrContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.operational.FWDPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.operational.OperationalPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.operational.SrcContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.operational.SrcPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.operational.TrgContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.operational.TrgPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.ProtocolPattern;

import language.TGG;
import language.TGGRule;

public class TGGCompiler {

	HashMap<TGGRule, Collection<Pattern>> ruleToPatterns = new HashMap<>();

	public void preparePatterns(TGG tggModel) {
		
		for(TGGRule rule : tggModel.getRules()){
			
			Collection<Pattern> patterns = new HashSet<>();
			
			ProtocolPattern protocol = new ProtocolPattern(rule);
			patterns.add(protocol);
			
			SrcContextPattern srcContext = new SrcContextPattern(rule);
			patterns.add(srcContext);
			
			SrcPattern src = new SrcPattern(rule);
			patterns.add(src);
			
			TrgContextPattern trgContext= new TrgContextPattern(rule);
			patterns.add(trgContext);
			
			TrgPattern trg = new TrgPattern(rule);
			patterns.add(trg);
			
			CorrContextPattern corrContext = new CorrContextPattern(rule);
			patterns.add(corrContext);
			
			FWDPattern fwd = new FWDPattern(rule);
			patterns.add(fwd);
			fwd.getPositiveInvocations().add(src);
			fwd.getPositiveInvocations().add(corrContext);
			fwd.getPositiveInvocations().add(trgContext);
			fwd.getNegativeInvocations().add(protocol);
			
			BWDPattern bwd = new BWDPattern(rule);
			patterns.add(bwd);
			bwd.getPositiveInvocations().add(trg);
			bwd.getPositiveInvocations().add(corrContext);
			bwd.getPositiveInvocations().add(srcContext);
			bwd.getNegativeInvocations().add(protocol);
			
			CCPattern cc = new CCPattern(rule);
			patterns.add(cc);
			cc.getPositiveInvocations().add(src);
			cc.getPositiveInvocations().add(trg);
			cc.getPositiveInvocations().add(corrContext);
			cc.getNegativeInvocations().add(protocol);
			
			ruleToPatterns.put(rule, patterns);
		}
	}

	public String getViatraPatterns(TGGRule rule) {
		
		PatternTemplate patternTemplate = new PatternTemplate();
		
		String result = patternTemplate.generateHeaderAndImports(determineImports(rule), rule.getName());
		
		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof OperationalPattern).map(p -> patternTemplate.generateOperationalPattern((OperationalPattern) p)).collect(Collectors.joining());
		
		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof ProtocolPattern).map(p -> patternTemplate.generateProtocolPattern((ProtocolPattern) p)).collect(Collectors.joining());
		
		return result;
	}

	private Map<String, String> determineImports(TGGRule rule) {
		Map<String, String> imports = new HashMap<>();

		imports.put("dep_ibex", "platform:/plugin/org.emoflon.ibex.tgg.core.runtime/model/Runtime.ecore");

		Set<EPackage> packs = rule.getNodes().stream().map(n -> n.getType().getEPackage()).collect(Collectors.toSet());
		Iterator<EPackage> it = packs.iterator();
		for (int i = 0; i < packs.size(); i++) {
			imports.put("dep_" + i, it.next().eResource().getURI().toString());
		}

		return imports;
	}

}
