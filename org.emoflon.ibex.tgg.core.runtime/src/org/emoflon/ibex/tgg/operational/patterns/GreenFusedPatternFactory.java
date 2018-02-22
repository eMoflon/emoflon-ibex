package org.emoflon.ibex.tgg.operational.patterns;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.util.MAUtil;

import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.basic.expressions.TGGParamValue;
import language.csp.TGGAttributeConstraint;

public class GreenFusedPatternFactory extends GreenPatternFactory {
	private IGreenPatternFactory kernelFactory;
	private IGreenPatternFactory complementFactory;
	
	public GreenFusedPatternFactory(String fusedName, IbexOptions options, OperationalStrategy strategy) {
		super(options, strategy);
		this.ruleName = fusedName;
		
		String kernelName = MAUtil.getKernelName(fusedName);
		String complementName = MAUtil.getComplementName(fusedName);
		kernelFactory = strategy.getGreenFactory(kernelName);
		complementFactory = strategy.getGreenFactory(complementName);
		
		compileBookkeepingData();
		computeVariablesAndConstraints();
	}

	private void compileBookkeepingData() {
		greenSrcNodesInRule.addAll(kernelFactory.getGreenSrcNodesInRule());
		greenSrcNodesInRule.addAll(complementFactory.getGreenSrcNodesInRule());
		
		greenTrgNodesInRule.addAll(kernelFactory.getGreenTrgNodesInRule());
		greenTrgNodesInRule.addAll(complementFactory.getGreenTrgNodesInRule());
		
		greenCorrNodesInRule.addAll(kernelFactory.getGreenCorrNodesInRule());
		greenCorrNodesInRule.addAll(complementFactory.getGreenCorrNodesInRule());
		
		greenSrcEdgesInRule.addAll(kernelFactory.getGreenSrcEdgesInRule());
		greenSrcEdgesInRule.addAll(complementFactory.getGreenSrcEdgesInRule());
		
		greenTrgEdgesInRule.addAll(kernelFactory.getGreenTrgEdgesInRule());
		greenTrgEdgesInRule.addAll(complementFactory.getGreenTrgEdgesInRule());
		
		blackSrcNodesInRule.addAll(kernelFactory.getBlackSrcNodesInRule());
		blackSrcNodesInRule.addAll(
				complementFactory.getBlackSrcNodesInRule().stream()
					.filter(n -> !containedViaName(n, kernelFactory.getGreenSrcNodesInRule()))
					.collect(Collectors.toList()));
		
		blackTrgNodesInRule.addAll(kernelFactory.getBlackTrgNodesInRule());
		blackTrgNodesInRule.addAll(
				complementFactory.getBlackTrgNodesInRule().stream()
					.filter(n -> !containedViaName(n, kernelFactory.getGreenTrgNodesInRule()))
					.collect(Collectors.toList()));
		
		blackCorrNodesInRule.addAll(kernelFactory.getBlackCorrNodesInRule());
		blackCorrNodesInRule.addAll(
				complementFactory.getBlackCorrNodesInRule().stream()
					.filter(c -> !containedViaName(c, kernelFactory.getGreenCorrNodesInRule()))
					.collect(Collectors.toList()));
		
		blackSrcEdgesInRule.addAll(kernelFactory.getBlackSrcEdgesInRule());
		blackSrcEdgesInRule.addAll(
				complementFactory.getBlackSrcEdgesInRule().stream()
					.filter(e -> !containedViaName(e, kernelFactory.getGreenSrcEdgesInRule()))
					.collect(Collectors.toList()));
		
		blackTrgEdgesInRule.addAll(kernelFactory.getBlackTrgEdgesInRule());
		blackTrgEdgesInRule.addAll(
				complementFactory.getBlackTrgEdgesInRule().stream()
					.filter(e -> !containedViaName(e, kernelFactory.getGreenTrgEdgesInRule()))
					.collect(Collectors.toList()));
	}
	
	private boolean containedViaName(TGGRuleNode n, Collection<? extends TGGRuleNode> nodes) {
		return nodes.stream().noneMatch(on -> on.getName().contentEquals(n.getName()));
	}
	
	private boolean containedViaName(TGGRuleEdge e, Collection<? extends TGGRuleEdge> edges) {
		return edges.stream().noneMatch(oe -> oe.getName().contentEquals(e.getName()));
	}

	@Override
	public IGreenPattern create(String patternName) {
		if(isBWDFusedPattern(patternName))
			return createGreenPattern(BWDFusedGreenPattern.class);
		
		if(isFWDFusedPattern(patternName))
			return createGreenPattern(FWDFusedGreenPattern.class);
		
		if(isBWDOptFusedPattern(patternName))
			return createGreenPattern(BWDOptFusedGreenPattern.class);
		
		if(isFWDOptFusedPattern(patternName))
			return createGreenPattern(FWDOptFusedGreenPattern.class);
		
		return createGreenPattern(EmptyGreenPattern.class);
	}
	
	private boolean isFWDOptFusedPattern(String patternName) {
		return MAUtil.isFusedPatternMatch(patternName) && patternName.endsWith(PatternSuffixes.FWD_OPT);
	}

	private boolean isBWDOptFusedPattern(String patternName) {
		return MAUtil.isFusedPatternMatch(patternName) && patternName.endsWith(PatternSuffixes.BWD_OPT);
	}

	private boolean isFWDFusedPattern(String patternName) {
		return MAUtil.isFusedPatternMatch(patternName) && patternName.endsWith(PatternSuffixes.FWD);
	}

	private boolean isBWDFusedPattern(String patternName) {
		return MAUtil.isFusedPatternMatch(patternName) && patternName.endsWith(PatternSuffixes.BWD);
	}
	
	private void computeVariablesAndConstraints() {
		variables.addAll(kernelFactory.getAttributeCSPVariables());
		constraints.addAll(kernelFactory.getAttributeConstraints());
		
		// Carefully merge in constraints and their parameters from complement
		for (TGGAttributeConstraint c : complementFactory.getAttributeConstraints()) {
			TGGAttributeConstraint constraint = EcoreUtil.copy(c);
			constraints.add(constraint);
			
			// Collect parameters to be replaced by values from constraints in kernel
			Collection<Pair<TGGParamValue, TGGParamValue>> replace = new ArrayList<>();
			for (TGGParamValue param : constraint.getParameters()) {
				Optional<TGGParamValue> alreadyPresent = variables.stream()
						.filter(p -> MAUtil.equal(p, param))
						.findAny();
				
				if(alreadyPresent.isPresent())
					replace.add(Pair.of(param, alreadyPresent.get()));
				else
					variables.add(param);
			}
			
			// Replace them here to avoid concurrent modification exceptions
			replace.forEach(p -> MAUtil.replaceWith(constraint, p.getLeft(), p.getRight()));
		}
	}

	public IGreenPatternFactory getKernelFactory() {
		return kernelFactory;
	}
	
	public IGreenPatternFactory getComplementFactory() {
		return complementFactory;
	}
	
	public IGreenPattern createGreenPattern(Class<? extends IGreenPattern> c) {
		return createPattern(c.getName(), () -> {
			try {
				return c.getConstructor(GreenFusedPatternFactory.class).newInstance(this);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				return null;
			}
		});
	}
}