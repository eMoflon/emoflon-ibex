package org.emoflon.ibex.tgg.operational.patterns;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGUtil;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.BindingType;
import language.DomainType;
import language.NAC;
import language.TGGAttributeConstraint;
import language.TGGAttributeConstraintLibrary;
import language.TGGParamValue;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class GreenPatternFactory implements IGreenPatternFactory {
	protected IbexOptions options;
	protected List<TGGParamValue> variables = new ArrayList<>();
	protected List<TGGAttributeConstraint> constraints = new ArrayList<>();
	protected final static Logger logger = Logger.getLogger(GreenPatternFactory.class);

	private Map<String, IGreenPattern> patterns;
	private OperationalStrategy strategy;
	private TGGRule rule;
	private NAC nac;

	protected Collection<TGGRuleNode> greenSrcNodesInRule = new ArrayList<>();
	protected Collection<TGGRuleNode> greenTrgNodesInRule = new ArrayList<>();;
	protected Collection<TGGRuleCorr> greenCorrNodesInRule = new ArrayList<>();;
	protected Collection<TGGRuleEdge> greenSrcEdgesInRule = new ArrayList<>();;
	protected Collection<TGGRuleEdge> greenTrgEdgesInRule = new ArrayList<>();;
	protected Collection<TGGRuleEdge> greenCorrEdgesInRule = new ArrayList<>();;

	protected Collection<TGGRuleNode> blackSrcNodesInRule = new ArrayList<>();;
	protected Collection<TGGRuleNode> blackTrgNodesInRule = new ArrayList<>();;
	protected Collection<TGGRuleCorr> blackCorrNodesInRule = new ArrayList<>();;
	protected Collection<TGGRuleEdge> blackSrcEdgesInRule = new ArrayList<>();;
	protected Collection<TGGRuleEdge> blackTrgEdgesInRule = new ArrayList<>();;
	protected Collection<TGGRuleEdge> blackCorrEdgesInRule = new ArrayList<>();;
	
	//usability-team
		//declaring -ve or nac src trg n corr nodes n src n trg edges
	protected Collection<TGGRuleNode> negativeSrcNodesInNac = new ArrayList<>();
	protected Collection<TGGRuleNode> negativeTrgNodesInNac = new ArrayList<>();
	protected Collection<TGGRuleCorr> negativeCorrNodesInNac = new ArrayList<>();
	protected Collection<TGGRuleEdge> negativeSrcEdgesInNac = new ArrayList<>();
	protected Collection<TGGRuleEdge> negativeTrgEdgesInNac = new ArrayList<>();
	

	public GreenPatternFactory(String ruleName, IbexOptions options, OperationalStrategy strategy) {
		this(options, strategy);  
		
		if (TGGUtil.isNac(ruleName, options)) {
			for (TGGRule r : options.flattenedTGG().getRules())
				for (NAC n : r.getNacs())
					if (n.getName().contentEquals(ruleName)) {
						rule = r;
						nac = n;
						break;
					}
			
			 //usability-team
			//defining -ve or nac nodes n edges for binding type NEGATIVE
			negativeSrcNodesInNac.addAll(getNacNodes(DomainType.SRC));
			negativeTrgNodesInNac.addAll(getNacNodes(DomainType.TRG));
			negativeCorrNodesInNac.addAll(getNacNodes(DomainType.CORR).stream().map(TGGRuleCorr.class::cast)
					.collect(Collectors.toList()));

			negativeSrcEdgesInNac.addAll(validate(getNacEdges(DomainType.SRC)));
			negativeTrgEdgesInNac.addAll(validate(getNacEdges(DomainType.TRG)));
			
		}
		else {
			for (TGGRule r : options.flattenedTGG().getRules())
				if (r.getName().equals(ruleName))
					rule = r;
			
			greenSrcNodesInRule.addAll(getNodes(BindingType.CREATE, DomainType.SRC));
			greenTrgNodesInRule.addAll(getNodes(BindingType.CREATE, DomainType.TRG));
			greenCorrNodesInRule.addAll(getNodes(BindingType.CREATE, DomainType.CORR).stream().map(TGGRuleCorr.class::cast)
					.collect(Collectors.toList()));

			greenSrcEdgesInRule.addAll(validate(getEdges(BindingType.CREATE, DomainType.SRC)));
			greenTrgEdgesInRule.addAll(validate(getEdges(BindingType.CREATE, DomainType.TRG)));
			greenCorrEdgesInRule.addAll(validate(getEdges(BindingType.CREATE, DomainType.CORR)));

		}
		
		blackSrcNodesInRule.addAll(getNodes(BindingType.CONTEXT, DomainType.SRC));
		blackTrgNodesInRule.addAll(getNodes(BindingType.CONTEXT, DomainType.TRG));
		blackCorrNodesInRule.addAll(getNodes(BindingType.CONTEXT, DomainType.CORR).stream().map(TGGRuleCorr.class::cast)
				.collect(Collectors.toList()));

		blackSrcEdgesInRule.addAll(validate(getEdges(BindingType.CONTEXT, DomainType.SRC)));
		blackTrgEdgesInRule.addAll(validate(getEdges(BindingType.CONTEXT, DomainType.TRG)));
		blackCorrEdgesInRule.addAll(validate(getEdges(BindingType.CONTEXT, DomainType.CORR)));   
		
		constraints.addAll(rule.getAttributeConditionLibrary().getTggAttributeConstraints());
		variables.addAll(rule.getAttributeConditionLibrary().getParameterValues());
	}

	public GreenPatternFactory(IbexOptions options, OperationalStrategy strategy) {
		this.options = options;
		this.strategy = strategy;
		patterns = new HashMap<>();
	}

	private Collection<TGGRuleEdge> validate(Collection<TGGRuleEdge> edges) {
		for (TGGRuleEdge e : edges) {
			assert (e.getSrcNode() != null);
			assert (e.getTrgNode() != null);
		}

		return edges;
	}

	private Collection<TGGRuleNode> getNodes(BindingType bt, DomainType dt) {
		return rule.getNodes().stream().filter(n -> n.getBindingType() == bt && n.getDomainType() == dt)
				.collect(Collectors.toList());
	}

	private Collection<TGGRuleEdge> getEdges(BindingType bt, DomainType dt) {
		return rule.getEdges().stream().filter(e -> e.getBindingType() == bt && e.getDomainType() == dt)
				.collect(Collectors.toList());
	}
	
	private Collection<TGGRuleNode> getNacNodes(DomainType dt) {
		return nac.getNodes().stream().filter(n -> n.getBindingType() == BindingType.CONTEXT && n.getDomainType() == dt)
				.collect(Collectors.toList());
	}
	
	private Collection<TGGRuleEdge> getNacEdges(DomainType dt) {
		return nac.getEdges().stream().filter(n -> n.getBindingType() == BindingType.CONTEXT && n.getDomainType() == dt)
				.collect(Collectors.toList());
	}

	public IGreenPattern create(String patternName) {
		if (isGENBlackPattern(patternName))
			return createGreenPattern(GenGreenPattern.class);

		if (isCCBlackPattern(patternName))
			return createGreenPattern(CCGreenPattern.class);

		if (isCOBlackPattern(patternName))
			return createGreenPattern(COGreenPattern.class);

		if (isFWDBlackPattern(patternName))
			return createGreenPattern(FWDGreenPattern.class);

		if (isBWDBlackPattern(patternName))
			return createGreenPattern(BWDGreenPattern.class);

		if (isFWDOptBlackPattern(patternName))
			return createGreenPattern(FWDOptGreenPattern.class);

		if (isBWDOptBlackPattern(patternName))
			return createGreenPattern(BWDOptGreenPattern.class);
		
		if (isUserNACPattern(patternName))
			return createGreenPattern(UserNACGreenPattern.class);
		
		return createGreenPattern(EmptyGreenPattern.class);
	}

	private boolean isBWDBlackPattern(String patternName) {
		return patternName.equals(TGGPatternUtil.getBWDBlackPatternName(rule.getName()));
	}

	private boolean isFWDBlackPattern(String patternName) {
		return patternName.equals(TGGPatternUtil.getFWDBlackPatternName(rule.getName()));
	}

	private boolean isCOBlackPattern(String patternName) {
		return patternName.equals(TGGPatternUtil.getCOBlackPatternName(rule.getName()));
	}

	private boolean isCCBlackPattern(String patternName) {
		return patternName.equals(TGGPatternUtil.getCCBlackPatternName(rule.getName()));
	}

	private boolean isGENBlackPattern(String patternName) {
		return patternName.equals(TGGPatternUtil.getGENBlackPatternName(rule.getName()));
	}

	private boolean isFWDOptBlackPattern(String patternName) {
		return patternName.equals(TGGPatternUtil.getFWDOptBlackPatternName(rule.getName()));
	}

	private boolean isBWDOptBlackPattern(String patternName) {
		return patternName.equals(TGGPatternUtil.getBWDOptBlackPatternName(rule.getName()));
	}
	
	private boolean isUserNACPattern(String patternName) {
		return patternName.equals(TGGPatternUtil.getNACPatternName(nac.getName()));
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

	protected IGreenPattern createPattern(String key, Supplier<IGreenPattern> creator) {
		if (!patterns.containsKey(key)) {
			IGreenPattern newValue = creator.get();
			if (newValue != null)
				patterns.put(key, newValue);
		}

		if (!patterns.containsKey(key))
			throw new IllegalStateException("Pattern could not be added: " + key + " => " + patterns.get(key));

		return patterns.get(key);
	}

	@Override
	public IbexOptions getOptions() {
		return options;
	}

	@Override
	public OperationalStrategy getStrategy() {
		return strategy;
	}

	@Override
	public Collection<TGGRuleNode> getGreenSrcNodesInRule() {
		return greenSrcNodesInRule;
	}

	@Override
	public Collection<TGGRuleNode> getGreenTrgNodesInRule() {
		return greenTrgNodesInRule;
	}

	@Override
	public Collection<TGGRuleCorr> getGreenCorrNodesInRule() {
		return greenCorrNodesInRule;
	}

	@Override
	public Collection<TGGRuleEdge> getGreenCorrEdgesInRule() {
		return greenCorrEdgesInRule;
	}

	@Override
	public Collection<TGGRuleEdge> getGreenSrcEdgesInRule() {
		return greenSrcEdgesInRule;
	}

	@Override
	public Collection<TGGRuleEdge> getGreenTrgEdgesInRule() {
		return greenTrgEdgesInRule;
	}

	@Override
	public Collection<TGGRuleNode> getBlackSrcNodesInRule() {
		return blackSrcNodesInRule;
	}

	@Override
	public Collection<TGGRuleNode> getBlackTrgNodesInRule() {
		return blackTrgNodesInRule;
	}

	@Override
	public Collection<TGGRuleCorr> getBlackCorrNodesInRule() {
		return blackCorrNodesInRule;
	}

	@Override
	public Collection<TGGRuleEdge> getBlackSrcEdgesInRule() {
		return blackSrcEdgesInRule;
	}

	@Override
	public Collection<TGGRuleEdge> getBlackTrgEdgesInRule() {
		return blackTrgEdgesInRule;
	}
	
	@Override
	public Collection<TGGRuleEdge> getBlackCorrEdgesInRule() {
		return blackCorrEdgesInRule;
	}
	
	//usability-team
	//implementing -ve or nac src trg n corr nodes n src n trg edges
	@Override
	public Collection<TGGRuleNode> getNegativeSrcNodesInNac() {
		return negativeSrcNodesInNac;
	}

	@Override
	public Collection<TGGRuleNode> getNegativeTrgNodesInNac() {
		return negativeTrgNodesInNac;
	}

	@Override
	public Collection<TGGRuleCorr> getNegativeCorrNodesInNac() {
		return negativeCorrNodesInNac;
	}

	@Override
	public Collection<TGGRuleEdge> getNegativeSrcEdgesInNac() {
		return negativeSrcEdgesInNac;
	}

	@Override
	public Collection<TGGRuleEdge> getNegativeTrgEdgesInNac() {
		return negativeTrgEdgesInNac;
	}

	@Override
	public boolean isAxiom() {
		return blackSrcNodesInRule.isEmpty() && blackTrgNodesInRule.isEmpty() && blackCorrNodesInRule.isEmpty();
	}

	@Override
	public List<TGGAttributeConstraint> getAttributeConstraints() {
		return constraints;
	}

	@Override
	public List<TGGParamValue> getAttributeCSPVariables() {
		return variables;
	}

	@Override
	public TGGAttributeConstraintLibrary getAttributeLibrary() {
		return rule.getAttributeConditionLibrary();
	}
}
