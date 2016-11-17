package org.emoflon.ibex.tgg.core.compiler;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.ConsistencyPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs.BWDwithProtocolNACsPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs.FWDwithProtocolNACsPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs.PatternWithProtocolNACs;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.CCPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.CorrContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.MODELGENPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.RulePartPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.SrcContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.SrcPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.TrgContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.TrgPattern;

import language.LanguageFactory;
import language.TGG;
import language.TGGRule;
import language.TGGRuleEdge;

public class TGGCompiler {

	private HashMap<TGGRule, Collection<Pattern>> ruleToPatterns = new HashMap<>();
	private PatternTemplate patternTemplate = new PatternTemplate();

	public void preparePatterns(TGG tggModel) {
		
		TGG tggModelWithOppositeEdges = addOppositeEdges(tggModel);
		
		for(TGGRule rule : tggModelWithOppositeEdges.getRules()){
			
			Collection<Pattern> patterns = new HashSet<>();
			
			ConsistencyPattern protocol = new ConsistencyPattern(rule);
			patterns.add(protocol);
			
			SrcContextPattern srcContext = new SrcContextPattern(rule);
			patterns.add(srcContext);
			
			SrcPattern src = new SrcPattern(rule);
			patterns.add(src);
			src.getPositiveInvocations().add(srcContext);
			
			TrgContextPattern trgContext= new TrgContextPattern(rule);
			patterns.add(trgContext);
			
			TrgPattern trg = new TrgPattern(rule);
			patterns.add(trg);
			trg.getPositiveInvocations().add(trgContext);
			
			CorrContextPattern corrContext = new CorrContextPattern(rule);
			patterns.add(corrContext);
			
			FWDwithProtocolNACsPattern fwd = new FWDwithProtocolNACsPattern(rule);
			patterns.add(fwd);
			fwd.getPositiveInvocations().add(src);
			fwd.getPositiveInvocations().add(corrContext);
			fwd.getPositiveInvocations().add(trgContext);
			
			BWDwithProtocolNACsPattern bwd = new BWDwithProtocolNACsPattern(rule);
			patterns.add(bwd);
			bwd.getPositiveInvocations().add(trg);
			bwd.getPositiveInvocations().add(corrContext);
			bwd.getPositiveInvocations().add(srcContext);
			
			CCPattern cc = new CCPattern(rule);
			patterns.add(cc);
			cc.getPositiveInvocations().add(src);
			cc.getPositiveInvocations().add(trg);
			cc.getPositiveInvocations().add(corrContext);
			
			MODELGENPattern modelgen = new MODELGENPattern(rule);
			patterns.add(modelgen);
			modelgen.getPositiveInvocations().add(srcContext);
			modelgen.getPositiveInvocations().add(trgContext);
			modelgen.getPositiveInvocations().add(corrContext);
			
			ruleToPatterns.put(rule, patterns);
		}
	}

	private TGG addOppositeEdges(TGG tggModel) {
		tggModel.getRules().stream().flatMap(r -> r.getEdges().stream()).forEach(e -> {
			if(e.getType().getEOpposite() != null){
				TGGRuleEdge oppositeEdge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
				oppositeEdge.setBindingType(e.getBindingType());
				oppositeEdge.setDomainType(e.getDomainType());
				oppositeEdge.setType(e.getType().getEOpposite());
				oppositeEdge.setSrcNode(e.getTrgNode());
				oppositeEdge.setTrgNode(e.getSrcNode());
				oppositeEdge.setName(oppositeEdge.getSrcNode().getName() + "__" + oppositeEdge.getType().getName() + "__" + oppositeEdge.getTrgNode().getName() + "_eMoflonEdge");
				((TGGRule)e.eContainer()).getEdges().add(oppositeEdge);
			}
		});
		return tggModel;
	}

	public String getViatraPatterns(TGGRule rule) {
		
		
		String result = patternTemplate.generateHeaderAndImports(determineAliasedImports(rule), determineNonAliasedImports(rule), rule.getName());
		
		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof RulePartPattern).map(p -> patternTemplate.generateOperationalPattern((RulePartPattern) p)).collect(Collectors.joining());
		
		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof ConsistencyPattern).map(p -> patternTemplate.generateConsistencyPattern((ConsistencyPattern) p)).collect(Collectors.joining());
		
		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof PatternWithProtocolNACs).map(p -> patternTemplate.generateProtocolNACsPattern((PatternWithProtocolNACs) p)).collect(Collectors.joining());
		
		return result;
	}

	private Collection<String> determineNonAliasedImports(TGGRule rule) {
		Collection<String> result = new HashSet<>();
		result.add("org.emoflon.ibex.tgg.common.marked");
		return result;
	}

	private Map<String, String> determineAliasedImports(TGGRule rule) {
		Map<String, String> imports = new HashMap<>();

		imports.put("dep_ibex", "platform:/plugin/org.emoflon.ibex.tgg.core.runtime/model/Runtime.ecore");

		Set<EPackage> packs = rule.getNodes().stream().map(n -> n.getType().getEPackage()).collect(Collectors.toSet());
		Iterator<EPackage> it = packs.iterator();
		for (int i = 0; i < packs.size(); i++) {
			imports.put("dep_" + i, it.next().eResource().getURI().toString());
		}

		return imports;
	}

	public String getCommonViatraPatterns() {
		
		String result = ""; 
		
		
		Map<String, String> aliasedImports = new HashMap<>();
		
		Collection<String> nonAliasedImports = new HashSet<>();
		aliasedImports.put("dep_ecore", "http://www.eclipse.org/emf/2002/Ecore");
		aliasedImports.put("dep_ibex", "platform:/plugin/org.emoflon.ibex.tgg.core.runtime/model/Runtime.ecore");

		result += patternTemplate.generateHeaderAndImports(aliasedImports, nonAliasedImports, "common");
		result += patternTemplate.generateCommonPatterns();
		
		return result;
	}

	public String getCommonPatternFileName() {
		return "_common_eMoflon";
	}
	
	public String getXtendManipulationCode(TGG tggModel){
		ManipulationTemplate mTemplate = new ManipulationTemplate();
		return mTemplate.getManipulationCode(tggModel);
	}

}
