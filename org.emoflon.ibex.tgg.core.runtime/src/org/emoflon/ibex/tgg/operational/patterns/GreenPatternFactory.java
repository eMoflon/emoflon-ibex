package org.emoflon.ibex.tgg.operational.patterns;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.BindingType;
import language.DomainType;
import language.TGGAttributeConstraint;
import language.TGGAttributeConstraintLibrary;
import language.TGGParamValue;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class GreenPatternFactory implements IGreenPatternFactory {
	protected String ruleName;
	protected IbexOptions options;
	protected List<TGGParamValue> variables = new LinkedList<>();
	protected List<TGGAttributeConstraint> constraints = new LinkedList<>();
	protected final static Logger logger = Logger.getLogger(GreenPatternFactory.class);

	private Map<String, IGreenPattern> patterns;
	private OperationalStrategy strategy;
	private TGGRule rule;

	protected List<TGGRuleNode> greenSrcNodesInRule = new LinkedList<>();
	protected List<TGGRuleNode> greenTrgNodesInRule = new LinkedList<>();;
	protected List<TGGRuleCorr> greenCorrNodesInRule = new LinkedList<>();;
	protected List<TGGRuleEdge> greenSrcEdgesInRule = new LinkedList<>();;
	protected List<TGGRuleEdge> greenTrgEdgesInRule = new LinkedList<>();;
	protected List<TGGRuleEdge> greenCorrEdgesInRule = new LinkedList<>();;

	protected List<TGGRuleNode> blackSrcNodesInRule = new LinkedList<>();;
	protected List<TGGRuleNode> blackTrgNodesInRule = new LinkedList<>();;
	protected List<TGGRuleCorr> blackCorrNodesInRule = new LinkedList<>();;
	protected List<TGGRuleEdge> blackSrcEdgesInRule = new LinkedList<>();;
	protected List<TGGRuleEdge> blackTrgEdgesInRule = new LinkedList<>();;
	protected List<TGGRuleEdge> blackCorrEdgesInRule = new LinkedList<>();;

	public GreenPatternFactory(IbexOptions options, String ruleName) {
		this(options);
		this.ruleName = ruleName;

		rule = options.tgg.flattenedTGG().getRules().stream().filter(r -> r.getName().equals(ruleName)).findAny()
				.orElseThrow(() -> new IllegalStateException("Could not find " + ruleName + " in the TGG."));

		greenSrcNodesInRule.addAll(getNodes(BindingType.CREATE, DomainType.SRC));
		greenTrgNodesInRule.addAll(getNodes(BindingType.CREATE, DomainType.TRG));
		greenCorrNodesInRule.addAll(getNodes(BindingType.CREATE, DomainType.CORR).stream().map(TGGRuleCorr.class::cast)
				.collect(Collectors.toList()));

		greenSrcEdgesInRule.addAll(validate(getEdges(BindingType.CREATE, DomainType.SRC)));
		greenTrgEdgesInRule.addAll(validate(getEdges(BindingType.CREATE, DomainType.TRG)));
		greenCorrEdgesInRule.addAll(validate(getEdges(BindingType.CREATE, DomainType.CORR)));

		blackSrcNodesInRule.addAll(getNodes(BindingType.CONTEXT, DomainType.SRC));
		blackTrgNodesInRule.addAll(getNodes(BindingType.CONTEXT, DomainType.TRG));
		blackCorrNodesInRule.addAll(getNodes(BindingType.CONTEXT, DomainType.CORR).stream().map(TGGRuleCorr.class::cast)
				.collect(Collectors.toList()));

		blackSrcEdgesInRule.addAll(validate(getEdges(BindingType.CONTEXT, DomainType.SRC)));
		blackTrgEdgesInRule.addAll(validate(getEdges(BindingType.CONTEXT, DomainType.TRG)));
		blackCorrEdgesInRule.addAll(validate(getEdges(BindingType.CONTEXT, DomainType.CORR)));

		constraints.addAll(rule.getAttributeConditionLibrary().getTggAttributeConstraints());
		variables.addAll(rule.getAttributeConditionLibrary().getParameterValues());
		
		greenSrcEdgesInRule.sort((a, b) -> compareEdges(a, b));
		greenTrgEdgesInRule.sort((a, b) -> compareEdges(a, b));
	}
	
	private int compareEdges(TGGRuleEdge a, TGGRuleEdge b) {
		boolean a_val = a.getSrcNode().getBindingType() == BindingType.CONTEXT && a.getTrgNode().getBindingType() == BindingType.CREATE;
		boolean b_val = b.getSrcNode().getBindingType() == BindingType.CONTEXT && b.getTrgNode().getBindingType() == BindingType.CREATE;
		if(a_val && !b_val) return 1;
		if(!a_val && b_val) return -1;
		return 0;
	}

	public GreenPatternFactory(IbexOptions options) {
		patterns = new HashMap<>();
		this.options = options;
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

	public IGreenPattern create(PatternType type) {
		switch(type) {
		case GEN: return createGreenPattern(GenGreenPattern.class);
		case CC: return createGreenPattern(CCGreenPattern.class);
		case CO: return createGreenPattern(COGreenPattern.class);
		case FWD: return createGreenPattern(FWDGreenPattern.class);
		case BWD: return createGreenPattern(BWDGreenPattern.class);
		case FWD_OPT: return createGreenPattern(FWDOptGreenPattern.class);
		case BWD_OPT: return createGreenPattern(BWDOptGreenPattern.class);
		default: return createGreenPattern(EmptyGreenPattern.class);
		}
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
