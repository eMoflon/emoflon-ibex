package org.emoflon.ibex.tgg.core.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
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
import runtime.RuntimePackage;

public class TGGCompiler {

	private LinkedHashMap<TGGRule, Collection<Pattern>> ruleToPatterns = new LinkedHashMap<>();
	private PatternTemplate patternTemplate;

	private TGG tgg;

	private LinkedHashMap<String, String> aliasToEPackageUri = new LinkedHashMap<>();
	private LinkedHashMap<EPackage, String> epackageToAlias = new LinkedHashMap<>();
	private int packageCounter = 0;

	public TGGCompiler(TGG tgg) {
		this.tgg = tgg;
		fillImportAliasTables(tgg);
		patternTemplate = new PatternTemplate(epackageToAlias);
	}

	private void fillImportAliasTables(TGG tgg) {
		aliasToEPackageUri.put("dep_ibex", "platform:/plugin/org.emoflon.ibex.tgg.core.runtime/model/Runtime.ecore");
		epackageToAlias.put(RuntimePackage.eINSTANCE, "dep_ibex");

		aliasToEPackageUri.put("dep_ecore", "http://www.eclipse.org/emf/2002/Ecore");
		epackageToAlias.put(EcorePackage.eINSTANCE, "dep_ecore");

		tgg.getRules().stream().flatMap(r -> r.getNodes().stream()).map(n -> n.getType().getEPackage()).forEachOrdered(p -> {
			if (!epackageToAlias.containsKey(p)) {
				String alias = "dep_" + ++packageCounter;
				aliasToEPackageUri.put(alias, p.eResource().getURI().toString());
				epackageToAlias.put(p, alias);
			}
		});
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

		String result = patternTemplate.generateHeaderAndImports(aliasToEPackageUri,
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
		Collection<String> result = new LinkedHashSet<>();
		result.add("org.emoflon.ibex.tgg.common.marked");
		return result;
	}

	public String getCommonViatraPatterns() {
		String result = "";

		LinkedHashMap<String, String> aliasedImports = aliasToEPackageUri;

		Collection<String> nonAliasedImports = new LinkedHashSet<>();

		result += patternTemplate.generateHeaderAndImports(aliasedImports, nonAliasedImports, "common");
		result += patternTemplate.generateCommonPatterns(getEdgeTypes(tgg));

		return result;
	}

	public static String getCommonPatternFileName() {
		return "_common_eMoflon";
	}

	public String getXtendManipulationCode() {
		ManipulationTemplate mTemplate = new ManipulationTemplate();
		return mTemplate.getManipulationCode(tgg);
	}

	public static LinkedHashSet<EReference> getEdgeTypes(TGG tgg) {
		return tgg.getRules().stream().flatMap(r -> r.getEdges().stream()).map(e -> e.getType())
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

}
