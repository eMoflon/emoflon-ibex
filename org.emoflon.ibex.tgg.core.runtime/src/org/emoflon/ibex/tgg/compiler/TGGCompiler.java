package org.emoflon.ibex.tgg.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.common.MarkedPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.ConsistencyPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.SrcProtocolNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.TrgProtocolNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.BWDPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.CCPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.FWDPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.MODELGENPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.WholeRulePattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.SrcContextPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.SrcPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.SrcProtocolAndDECPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.TrgContextPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.TrgPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.TrgProtocolAndDECPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC.DECTrackingContainer;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC.NoDECsPatterns;

import language.DomainType;
import language.TGG;
import language.TGGRule;

public class TGGCompiler {

	private List<IbexPattern> markedPattern = new ArrayList<>();
	private Map<TGGRule, Collection<IbexPattern>> ruleToPatterns = new LinkedHashMap<>();

	private TGG tgg;
	
	private DECTrackingContainer decTC;

	public TGGCompiler(TGG tgg) {
		this.tgg = tgg;
		
		// initialise DECTrackingContainer that contains information for DEC
		// generation such as which patterns belongs to which rule or the
		// mapping of signature elements for the calls to external patterns
		decTC = new DECTrackingContainer(ruleToPatterns);
	}
	
	public Map<TGGRule, Collection<IbexPattern>> getRuleToPatternMap(){
		return ruleToPatterns;
	}

	public void preparePatterns() {
		createMarkedPatterns();
		
		for (TGGRule rule : tgg.getRules()) {
			Collection<IbexPattern> patterns = new ArrayList<>();
			
			SrcContextPattern srcContext = new SrcContextPattern(rule);
			patterns.add(srcContext);

			SrcPattern src = new SrcPattern(rule);
			patterns.add(src);
			src.getPositiveInvocations().add(srcContext);

			SrcProtocolNACsPattern srcProtocolNACs = new SrcProtocolNACsPattern(rule);
			patterns.add(srcProtocolNACs);
			srcProtocolNACs.getPositiveInvocations().add(src);
			
			SrcProtocolAndDECPattern srcProtocolDECs = new SrcProtocolAndDECPattern(rule);
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

			TrgProtocolAndDECPattern trgProtocolDECs = new TrgProtocolAndDECPattern(rule);
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
				ruleToPatterns.get(rule).stream().filter(r -> r instanceof SrcProtocolAndDECPattern).forEach(r -> r.getPositiveInvocations().add(srcNoDecPatterns));
			}
			if (!trgNoDecPatterns.isEmpty()) {
				ruleToPatterns.get(rule).add(trgNoDecPatterns);
				ruleToPatterns.get(rule).stream().filter(r -> r instanceof TrgProtocolAndDECPattern).forEach(r -> r.getPositiveInvocations().add(trgNoDecPatterns));
			}
		}
	}
	
	public List<IbexPattern> getMarkedPatterns() {
		return markedPattern;
	}
	
	public static LinkedHashSet<EReference> getEdgeTypes(TGG tgg) {
		return tgg.getRules().stream().flatMap(r -> r.getEdges().stream()).map(e -> e.getType()).collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	public void createMarkedPatterns() {
		MarkedPattern signProtocolSrcMarkedPattern = new MarkedPattern(DomainType.SRC, false);
		MarkedPattern signProtocolTrgMarkedPattern = new MarkedPattern(DomainType.TRG, false);
		MarkedPattern localProtocolSrcMarkedPattern = new MarkedPattern(signProtocolSrcMarkedPattern, DomainType.SRC, true);
		MarkedPattern localProtocolTrgMarkedPattern = new MarkedPattern(signProtocolTrgMarkedPattern, DomainType.TRG, true);
		
		localProtocolSrcMarkedPattern.getPositiveInvocations().add(signProtocolSrcMarkedPattern);
		localProtocolTrgMarkedPattern.getPositiveInvocations().add(signProtocolTrgMarkedPattern);
		
		markedPattern.add(localProtocolSrcMarkedPattern);
		markedPattern.add(localProtocolTrgMarkedPattern);
		markedPattern.add(signProtocolSrcMarkedPattern);
		markedPattern.add(signProtocolTrgMarkedPattern);
	}

}
