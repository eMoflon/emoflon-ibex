package org.emoflon.ibex.tgg.core.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.ConsistencyPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs.ProtocolNACsPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs.SrcProtocolNACsPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs.TrgProtocolNACsPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.BWDPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.CCPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.FWDPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.MODELGENPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.RulePartPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.WholeRulePattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.CorrContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.SrcContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.SrcPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.SrcProtocolNACsAndDECPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.TrgContextPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.TrgPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.TrgProtocolNACsAndDECPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC.DECHelper;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC.DECPattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC.DECTrackingContainer;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC.NoDECsPatterns;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC.SearchEdgePattern;

import language.DomainType;
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
	
	private DECTrackingContainer decTC;

	public TGGCompiler(TGG tgg) {
		this.tgg = tgg;
		fillImportAliasTables(tgg);
		patternTemplate = new PatternTemplate(epackageToAlias);
		
		// initialise DECTrackingContainer which is responsible to hold information needed for DEC generation such as which patterns belongs to which rule
		// or what's the mapping of signature elements for the calls to external patterns
		decTC = new DECTrackingContainer(ruleToPatterns);
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

			SrcContextPattern srcContext = new SrcContextPattern(rule);
			patterns.add(srcContext);

			SrcPattern src = new SrcPattern(rule);
			patterns.add(src);
			src.getPositiveInvocations().add(srcContext);

			SrcProtocolNACsPattern srcProtocolNACs = new SrcProtocolNACsPattern(rule);
			patterns.add(srcProtocolNACs);
			srcProtocolNACs.getPositiveInvocations().add(src);
			
			SrcProtocolNACsAndDECPattern srcProtocolDECs = new SrcProtocolNACsAndDECPattern(rule);
			patterns.add(srcProtocolDECs);
			srcProtocolDECs.getPositiveInvocations().add(srcProtocolNACs);

			TrgContextPattern trgContext = new TrgContextPattern(rule);
			patterns.add(trgContext);

			TrgPattern trg = new TrgPattern(rule);
			patterns.add(trg);
			trg.getPositiveInvocations().add(trgContext);

			TrgProtocolNACsPattern trgProtocolNACs = new TrgProtocolNACsPattern(rule);
			patterns.add(trgProtocolNACs);
			trgProtocolNACs.getPositiveInvocations().add(trg);

			TrgProtocolNACsAndDECPattern trgProtocolDECs = new TrgProtocolNACsAndDECPattern(rule);
			patterns.add(trgProtocolDECs);
			trgProtocolDECs.getPositiveInvocations().add(trgProtocolNACs);
			
			CorrContextPattern corrContext = new CorrContextPattern(rule);
			patterns.add(corrContext);

			CCPattern cc = new CCPattern(rule);
			patterns.add(cc);
			cc.getPositiveInvocations().add(src);
			cc.getPositiveInvocations().add(trg);
			cc.getPositiveInvocations().add(corrContext);

			FWDPattern fwd = new FWDPattern(rule);
			patterns.add(fwd);
			fwd.getPositiveInvocations().add(srcProtocolDECs);
			fwd.getPositiveInvocations().add(corrContext);
			fwd.getPositiveInvocations().add(trgContext);

			BWDPattern bwd = new BWDPattern(rule);
			patterns.add(bwd);
			bwd.getPositiveInvocations().add(trgProtocolDECs);
			bwd.getPositiveInvocations().add(corrContext);
			bwd.getPositiveInvocations().add(srcContext);

			MODELGENPattern modelgen = new MODELGENPattern(rule);
			patterns.add(modelgen);
			modelgen.getPositiveInvocations().add(srcContext);
			modelgen.getPositiveInvocations().add(trgContext);
			modelgen.getPositiveInvocations().add(corrContext);

			WholeRulePattern whole = new WholeRulePattern(rule);
			patterns.add(whole);
			whole.getPositiveInvocations().add(src);
			whole.getPositiveInvocations().add(trg);
			whole.getPositiveInvocations().add(corrContext);

			ConsistencyPattern protocol = new ConsistencyPattern(rule);
			patterns.add(protocol);
			protocol.getPositiveInvocations().add(whole);

			ruleToPatterns.put(rule, patterns);
		}


		// add no DEC patterns to Src- and TrgPattern, respectively and register them
		for (TGGRule rule : tgg.getRules()) {
			NoDECsPatterns srcNoDecPatterns = new NoDECsPatterns(rule, decTC, DomainType.SRC);
			NoDECsPatterns trgNoDecPatterns = new NoDECsPatterns(rule, decTC, DomainType.TRG);

			if (!srcNoDecPatterns.isEmpty()) {
				ruleToPatterns.get(rule).add(srcNoDecPatterns);
				ruleToPatterns.get(rule).stream().filter(r -> r instanceof SrcProtocolNACsAndDECPattern).forEach(r -> r.getPositiveInvocations().add(srcNoDecPatterns));
			}
			if (!trgNoDecPatterns.isEmpty()) {
				ruleToPatterns.get(rule).add(trgNoDecPatterns);
				ruleToPatterns.get(rule).stream().filter(r -> r instanceof TrgProtocolNACsAndDECPattern).forEach(r -> r.getPositiveInvocations().add(trgNoDecPatterns));
			}
		}
	}

	public String getViatraPatterns(TGGRule rule) {

		String result = patternTemplate.generateHeaderAndImports(aliasToEPackageUri, determineNonAliasedImports(rule), rule.getName());

		result += patternTemplate
				.generateExternalPatternImports(DECHelper.determineImports(rule, ruleToPatterns.get(rule).stream().filter(p -> (p instanceof DECPattern)).collect(Collectors.toList())));

		result += ruleToPatterns.get(rule).stream().filter(p -> isOperationalPattern(p))
				.map(p -> patternTemplate.generateOperationalPattern((RulePartPattern) p)).collect(Collectors.joining());

		result += ruleToPatterns.get(rule).stream().filter(p -> isRootPattern(p)).map(p -> patternTemplate.generateProtocolDECPattern((Pattern) p)).collect(Collectors.joining());
		
		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof DECPattern).map(p -> patternTemplate.generateDECPattern((DECPattern) p, decTC)).collect(Collectors.joining());
		
		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof SearchEdgePattern).map(p -> patternTemplate.generateSearchEdgePattern((SearchEdgePattern) p))
				.collect(Collectors.joining());

		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof ProtocolNACsPattern && !isRootPattern(p)).map(p -> patternTemplate.generateProtocolNACsPattern((ProtocolNACsPattern) p))
				.collect(Collectors.joining());
		
		result += ruleToPatterns.get(rule).stream().filter(p -> p instanceof ConsistencyPattern).map(p -> patternTemplate.generateConsistencyPattern((ConsistencyPattern) p))
				.collect(Collectors.joining());

		return result;
	}
	
	private boolean isOperationalPattern(Pattern p) {
		return p instanceof RulePartPattern && !(p instanceof SearchEdgePattern) && !(p instanceof DECPattern) && !isRootPattern(p);
	}
	
	private boolean isRootPattern(Pattern p) {
		return p instanceof SrcProtocolNACsAndDECPattern || p instanceof TrgProtocolNACsAndDECPattern;
	}

	private Collection<String> determineNonAliasedImports(TGGRule rule) {
		Collection<String> result = new LinkedHashSet<>();
		result.add("org.emoflon.ibex.tgg.common.marked");
		result.add("org.emoflon.ibex.tgg.common.context");
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
		return tgg.getRules().stream().flatMap(r -> r.getEdges().stream()).map(e -> e.getType()).collect(Collectors.toCollection(LinkedHashSet::new));
	}

}
