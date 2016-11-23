package org.emoflon.ibex.tgg.core.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.ConsistencyPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.BWDPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.CCPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.CorrContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.FWDPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.MODELGENPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.RulePartPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.SrcContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.SrcPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.TrgContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.TrgPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.ilp.BWDILPAllMarkedPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.ilp.BWDILPPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.ilp.CCILPAllMarkedPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.ilp.CCILPPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.ilp.FWDILPAllMarkedPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.ilp.FWDILPPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.ilp.ILPAllMarkedPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.ilp.ILPPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.protocolnacs.BWDProtocolNACsPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.protocolnacs.FWDProtocolNACsPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.protocolnacs.ProtocolNACsPattern;

import language.TGG;
import language.TGGRule;

public class TGGCompiler {

	private HashMap<TGGRule, Collection<Pattern>> ruleToPatterns = new HashMap<>();
	private PatternTemplate patternTemplate = new PatternTemplate();

	private TGG tgg;

	public TGGCompiler(TGG tgg) {
		this.tgg = tgg;
	}

	public void preparePatterns() {

		for (TGGRule rule : tgg.getRules()) {

			Collection<Pattern> patterns = new ArrayList<>();

			ConsistencyPattern protocol = new ConsistencyPattern(rule);
			patterns.add(protocol);

			SrcContextPattern srcContext = new SrcContextPattern(rule);
			patterns.add(srcContext);

			SrcPattern src = new SrcPattern(rule);
			patterns.add(src);
			src.getPositiveInvocations().add(srcContext);

			TrgContextPattern trgContext = new TrgContextPattern(rule);
			patterns.add(trgContext);

			TrgPattern trg = new TrgPattern(rule);
			patterns.add(trg);
			trg.getPositiveInvocations().add(trgContext);

			CorrContextPattern corrContext = new CorrContextPattern(rule);
			patterns.add(corrContext);
			
			CCPattern cc = new CCPattern(rule);
			patterns.add(cc);
			cc.getPositiveInvocations().add(src);
			cc.getPositiveInvocations().add(trg);
			cc.getPositiveInvocations().add(corrContext);
			
			FWDPattern fwd = new FWDPattern(rule);
			patterns.add(fwd);
			fwd.getPositiveInvocations().add(src);
			fwd.getPositiveInvocations().add(corrContext);
			fwd.getPositiveInvocations().add(trgContext);
			
			BWDPattern bwd = new BWDPattern(rule);
			patterns.add(bwd);
			bwd.getPositiveInvocations().add(trg);
			bwd.getPositiveInvocations().add(corrContext);
			bwd.getPositiveInvocations().add(srcContext);
			
			MODELGENPattern modelgen = new MODELGENPattern(rule);
			patterns.add(modelgen);
			modelgen.getPositiveInvocations().add(srcContext);
			modelgen.getPositiveInvocations().add(trgContext);
			modelgen.getPositiveInvocations().add(corrContext);
			
			FWDProtocolNACsPattern fwdWithProtocolNACs = new FWDProtocolNACsPattern(rule);
			patterns.add(fwdWithProtocolNACs);
			fwdWithProtocolNACs.getPositiveInvocations().add(fwd);

			BWDProtocolNACsPattern bwdWithProtocolNACs = new BWDProtocolNACsPattern(rule);
			patterns.add(bwdWithProtocolNACs);
			bwdWithProtocolNACs.getPositiveInvocations().add(bwd);

			FWDILPAllMarkedPattern fwdILPAllMarked = new FWDILPAllMarkedPattern(rule);
			patterns.add(fwdILPAllMarked);
			fwdILPAllMarked.getPositiveInvocations().add(src);
			
			BWDILPAllMarkedPattern bwdILPAllMarked = new BWDILPAllMarkedPattern(rule);
			patterns.add(bwdILPAllMarked);
			bwdILPAllMarked.getPositiveInvocations().add(trg);
			
			CCILPAllMarkedPattern ccILPAllMarked = new CCILPAllMarkedPattern(rule);
			patterns.add(ccILPAllMarked);
			ccILPAllMarked.getPositiveInvocations().add(src);
			ccILPAllMarked.getPositiveInvocations().add(trg);
			
			FWDILPPattern fwdILP = new FWDILPPattern(rule);
			patterns.add(fwdILP);
			fwdILP.getPositiveInvocations().add(fwd);
			fwdILP.getNegativeInvocations().add(fwdILPAllMarked);
			
			BWDILPPattern bwdILP = new BWDILPPattern(rule);
			patterns.add(bwdILP);
			bwdILP.getPositiveInvocations().add(bwd);
			bwdILP.getNegativeInvocations().add(bwdILPAllMarked);
			
			CCILPPattern ccILP = new CCILPPattern(rule);
			patterns.add(ccILP);
			ccILP.getPositiveInvocations().add(cc);
			ccILP.getNegativeInvocations().add(ccILPAllMarked);
			

			ruleToPatterns.put(rule, patterns);
		}
	}

	public String getViatraPatterns(TGGRule rule) {

		String result = patternTemplate.generateHeaderAndImports(determineAliasedImports(rule),
				determineNonAliasedImports(rule), rule.getName());

		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof RulePartPattern)
				.map(p -> patternTemplate.generateOperationalPattern((RulePartPattern) p))
				.collect(Collectors.joining());

		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof ConsistencyPattern)
				.map(p -> patternTemplate.generateConsistencyPattern((ConsistencyPattern) p))
				.collect(Collectors.joining());

		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof ProtocolNACsPattern)
				.map(p -> patternTemplate.generateProtocolNACsPattern((ProtocolNACsPattern) p))
				.collect(Collectors.joining());
		
		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof ILPAllMarkedPattern)
				.map(p -> patternTemplate.generateILPAllMarkedPattern((ILPAllMarkedPattern) p))
				.collect(Collectors.joining());
		
		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof ILPPattern)
				.map(p -> patternTemplate.generateILPPattern((ILPPattern) p))
				.collect(Collectors.joining());

		return result;
	}

	private Collection<String> determineNonAliasedImports(TGGRule rule) {
		Collection<String> result = new HashSet<>();
		result.add("org.emoflon.ibex.tgg.common.marked");
		return result;
	}

	private Map<String, String> determineAliasedImports(TGGRule rule) {
		Map<String, String> imports = new HashMap<>();

		addDefaultEPackagesAsImport(imports);

		Set<EPackage> packs = rule.getNodes().stream().map(n -> n.getType().getEPackage()).collect(Collectors.toSet());
		addEPackagesToImport(packs, imports);

		return imports;
	}

	private Map<String, String> determineAliasedImports(TGG tgg) {
		Map<String, String> imports = new HashMap<>();

		addDefaultEPackagesAsImport(imports);

		Set<EPackage> packs = tgg.getRules().stream().flatMap(r -> r.getNodes().stream())
				.map(n -> n.getType().getEPackage()).collect(Collectors.toSet());
		addEPackagesToImport(packs, imports);

		return imports;
	}

	private void addDefaultEPackagesAsImport(Map<String, String> imports) {
		imports.put("dep_ibex", "platform:/plugin/org.emoflon.ibex.tgg.core.runtime/model/Runtime.ecore");
	}

	private void addEPackagesToImport(Set<EPackage> packs, Map<String, String> imports) {
		Iterator<EPackage> it = packs.iterator();
		for (int i = 0; i < packs.size(); i++) {
			imports.put("dep_" + i, it.next().eResource().getURI().toString());
		}
	}

	public String getCommonViatraPatterns() {

		String result = "";

		Map<String, String> aliasedImports = determineAliasedImports(tgg);

		Collection<String> nonAliasedImports = new HashSet<>();
		aliasedImports.put("dep_ecore", "http://www.eclipse.org/emf/2002/Ecore");
		addDefaultEPackagesAsImport(aliasedImports);

		result += patternTemplate.generateHeaderAndImports(aliasedImports, nonAliasedImports, "common");
		result += patternTemplate.generateCommonPatterns(getEdgeTypes(tgg));

		return result;
	}

	public String getCommonPatternFileName() {
		return "_common_eMoflon";
	}

	public String getXtendManipulationCode() {
		ManipulationTemplate mTemplate = new ManipulationTemplate();
		return mTemplate.getManipulationCode(tgg);
	}

	public static Set<EReference> getEdgeTypes(TGG tgg) {
		return tgg.getRules().stream().flatMap(r -> r.getEdges().stream()).map(e -> e.getType())
				.collect(Collectors.toSet());
	}

}
