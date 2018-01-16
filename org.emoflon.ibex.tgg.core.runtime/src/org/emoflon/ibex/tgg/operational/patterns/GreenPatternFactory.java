package org.emoflon.ibex.tgg.operational.patterns;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.cc.CCBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.gen.GENBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.BWDBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.FWDBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.WholeRulePattern;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.csp.TGGAttributeConstraintLibrary;

public class GreenPatternFactory {
	private String ruleName;
	private Map<String, IGreenPattern> patterns;
	private IbexOptions options;
	private OperationalStrategy strategy;
	private TGGRule rule;
	
	private Collection<TGGRuleNode> greenSrcNodesInRule;
	private Collection<TGGRuleNode> greenTrgNodesInRule;
	private Collection<TGGRuleCorr> greenCorrNodesInRule;
	private Collection<TGGRuleEdge> greenSrcEdgesInRule;
	private Collection<TGGRuleEdge> greenTrgEdgesInRule;

	private Collection<TGGRuleNode> blackSrcNodesInRule;
	private Collection<TGGRuleNode> blackTrgNodesInRule;
	private Collection<TGGRuleCorr> blackCorrNodesInRule;
	private Collection<TGGRuleEdge> blackSrcEdgesInRule;
	private Collection<TGGRuleEdge> blackTrgEdgesInRule;
	
	public GreenPatternFactory(String ruleName, IbexOptions options, OperationalStrategy strategy) {
		this.ruleName = ruleName;
		
		rule = options.flattenedTGG().getRules().stream()
			.filter(r -> r.getName().equals(ruleName))
			.findAny()
			.orElseThrow(() -> new IllegalStateException("Could not find " + ruleName + " in the TGG."));
		
		greenSrcNodesInRule = getNodes(BindingType.CREATE, DomainType.SRC);
		greenTrgNodesInRule = getNodes(BindingType.CREATE, DomainType.TRG);
		greenCorrNodesInRule = getNodes(BindingType.CREATE, DomainType.CORR)
				.stream()
				.map(TGGRuleCorr.class::cast)
				.collect(Collectors.toList());
		
		greenSrcEdgesInRule = validate(getEdges(BindingType.CREATE, DomainType.SRC));
		greenTrgEdgesInRule = validate(getEdges(BindingType.CREATE, DomainType.TRG));
		
		blackSrcNodesInRule = getNodes(BindingType.CONTEXT, DomainType.SRC);
		blackTrgNodesInRule = getNodes(BindingType.CONTEXT, DomainType.TRG);
		blackCorrNodesInRule = getNodes(BindingType.CONTEXT, DomainType.CORR)
				.stream()
				.map(TGGRuleCorr.class::cast)
				.collect(Collectors.toList());
		
		blackSrcEdgesInRule = validate(getEdges(BindingType.CONTEXT, DomainType.SRC));
		blackTrgEdgesInRule = validate(getEdges(BindingType.CONTEXT, DomainType.TRG));
		
		this.options = options;
		this.strategy = strategy;
		
		patterns = new HashMap<>();
	}

	private Collection<TGGRuleEdge> validate(Collection<TGGRuleEdge> edges) {
		for(TGGRuleEdge e : edges) {
			assert(e.getSrcNode() != null);
			assert(e.getTrgNode() != null);
		}
		
		return edges;
	}

	private Collection<TGGRuleNode> getNodes(BindingType bt, DomainType dt) {
		return rule.getNodes().stream()
				.filter(n -> n.getBindingType() == bt && n.getDomainType() == dt)
				.collect(Collectors.toList());
	}
	
	private Collection<TGGRuleEdge> getEdges(BindingType bt, DomainType dt) {
		return rule.getEdges().stream()
				.filter(e -> e.getBindingType() == bt && e.getDomainType() == dt)
				.collect(Collectors.toList());
	}

	public IGreenPattern create(String patternName) {
		if(isGENBlackPattern(patternName))
			return createGreenPattern(GenGreenPattern.class);
		
		if(isCCBlackPattern(patternName))
			return createGreenPattern(CCGreenPattern.class);
		
		if(isCOBlackPattern(patternName))
			return createGreenPattern(COGreenPattern.class);
		
		if(isFWDBlackPattern(patternName))
			return createGreenPattern(FWDGreenPattern.class);
		
		if(isBWDBlackPattern(patternName))
			return createGreenPattern(BWDGreenPattern.class);
		
		return createGreenPattern(EmptyGreenPattern.class);
	}

	private boolean isBWDBlackPattern(String patternName) {
		return patternName.equals(BWDBlackPattern.getName(ruleName));
	}

	private boolean isFWDBlackPattern(String patternName) {
		return patternName.equals(FWDBlackPattern.getName(ruleName));
	}

	private boolean isCOBlackPattern(String patternName) {
		return patternName.equals(WholeRulePattern.getName(ruleName));
	}

	private boolean isCCBlackPattern(String patternName) {
		return patternName.equals(CCBlackPattern.getName(ruleName));
	}

	private boolean isGENBlackPattern(String patternName) {
		return patternName.equals(GENBlackPattern.getName(ruleName));
	}
	
	public IGreenPattern createGreenPattern(Class<? extends IGreenPattern> c) {
		return createPattern(c.getName(), () -> {
			try {
				return c.getConstructor(GreenPatternFactory.class).newInstance(this);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				return null;
			}
		});
	}
	
	private IGreenPattern createPattern(String key, Supplier<IGreenPattern> creator){		
		if (!patterns.containsKey(key)) {
			IGreenPattern newValue = creator.get();
			if (newValue != null)
				patterns.put(key, newValue);
		}
		
		if(!patterns.containsKey(key))
			throw new IllegalStateException("Pattern could not be added: " + key + " => " + patterns.get(key));
		
		return patterns.get(key);
	}
	
	public IbexOptions getOptions() {
		return options;
	}
	
	public OperationalStrategy getStrategy() {
		return strategy;
	}

	public boolean blackInterpSupportsAttrConstrs() {
		return options.blackInterpSupportsAttrConstrs();
	}

	public TGGAttributeConstraintLibrary getRuleCSPConstraintLibrary() {
		return rule.getAttributeConditionLibrary();
	}
	
	public Collection<TGGRuleNode> getGreenSrcNodesInRule() {
		return greenSrcNodesInRule;
	}
	
	public Collection<TGGRuleNode> getGreenTrgNodesInRule() {
		return greenTrgNodesInRule;
	}
	
	public Collection<TGGRuleCorr> getGreenCorrNodesInRule() {
		return greenCorrNodesInRule;
	}
	
	public Collection<TGGRuleEdge> getGreenSrcEdgesInRule() {
		return greenSrcEdgesInRule;
	}
	
	public Collection<TGGRuleEdge> getGreenTrgEdgesInRule() {
		return greenTrgEdgesInRule;
	}
	
	public Collection<TGGRuleNode> getBlackSrcNodesInRule() {
		return blackSrcNodesInRule;
	}

	public Collection<TGGRuleNode> getBlackTrgNodesInRule() {
		return blackTrgNodesInRule;
	}

	public Collection<TGGRuleCorr> getBlackCorrNodesInRule() {
		return blackCorrNodesInRule;
	}

	public Collection<TGGRuleEdge> getBlackSrcEdgesInRule() {
		return blackSrcEdgesInRule;
	}

	public Collection<TGGRuleEdge> getBlackTrgEdgesInRule() {
		return blackTrgEdgesInRule;
	}

	public boolean isAxiom() {
		return blackSrcNodesInRule.isEmpty() &&
			   blackTrgNodesInRule.isEmpty() && 
			   blackCorrNodesInRule.isEmpty();
	}
}
