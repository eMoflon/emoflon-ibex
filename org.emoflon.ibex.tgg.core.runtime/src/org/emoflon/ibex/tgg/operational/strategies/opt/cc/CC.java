package org.emoflon.ibex.tgg.operational.strategies.opt.cc;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.collections.IntToObjectMap;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.opt.OPT;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

import language.TGGComplementRule;
import language.TGGRuleCorr;
import runtime.TGGRuleApplication;

public abstract class CC extends OPT {

	protected ConsistencyReporter consistencyReporter = new ConsistencyReporter();

	public CC(IbexOptions options) throws IOException {
		super(options);
	}

	public CC(IbexOptions options, IUpdatePolicy policy) {
		super(options, policy);
	}

	@Override
	public void loadModels() throws IOException {
		s = loadResource(options.projectPath() + "/instances/src.xmi");
		t = loadResource(options.projectPath() + "/instances/trg.xmi");
		c = createResource(options.projectPath() + "/instances/corr.xmi");
		p = createResource(options.projectPath() + "/instances/protocol.xmi");

		EcoreUtil.resolveAll(rs);
	}

	@Override
	public void saveModels() throws IOException {
		c.save(null);
		p.save(null);
	}

	@Override
	public double getDefaultWeightForMatch(IMatch comatch, String ruleName) {
		return getGreenFactory(ruleName).getGreenSrcEdgesInRule().size()
				+ getGreenFactory(ruleName).getGreenSrcNodesInRule().size()
				+ getGreenFactory(ruleName).getGreenTrgEdgesInRule().size()
				+ getGreenFactory(ruleName).getGreenTrgNodesInRule().size();
	}

	@Override
	public boolean isPatternRelevantForCompiler(String patternName) {
		return patternName.endsWith(PatternSuffixes.CC);
	}

	@Override
	protected boolean processOneOperationalRuleMatch() {
		if (operationalMatchContainer.isEmpty())
			return false;

		if (operationalMatchContainer.getMatches().stream()
				.allMatch(m -> m.getPatternName().contains(getGENPatternForMaximality()))) {
			return false;
		}

		IMatch match = chooseOneMatch();
		String ruleName = operationalMatchContainer.getRuleName(match);

		if (ruleName == null) {
			removeOperationalRuleMatch(match);
			return true;
		}

		Optional<IMatch> comatch = processOperationalRuleMatch(ruleName, match);
		comatch.ifPresent(cm -> {
			if (isKernelMatch(ruleName))
				processComplementRuleMatches(cm);
		});

		removeOperationalRuleMatch(match);
		return true;
	}

	private void processComplementRuleMatches(IMatch comatch) {
		blackInterpreter.updateMatches();
		// last applied match was kernel
		int kernelMatchID = this.idToMatch.size();

		// collection needed to handle maximality
		Set<IMatch> complementRuleContextMatches = findAllComplementRuleContextMatches();

		Set<IMatch> complementRuleMatches = findAllComplementRuleMatches();

		// collection needed to handle uniqueness
		IntToObjectMap<Set<EObject>> complementMatchToContextNodes = cfactory.createIntToObjectHashMap();

		while (complementRuleMatches.iterator().hasNext()) {
			IMatch match = complementRuleMatches.iterator().next();
			applyMatchAndHandleUniqueness(match, complementMatchToContextNodes);
			complementRuleMatches.remove(match);
			removeOperationalRuleMatch(match);
		}

		// check if all found CR matches are really applied
		while (complementRuleContextMatches.iterator().hasNext()) {
			IMatch match = complementRuleContextMatches.iterator().next();
			handleMaximality(match, kernelMatchID);
			complementRuleContextMatches.remove(match);
		}

		TGGRuleApplication application = (TGGRuleApplication) comatch
				.get(ConsistencyPattern.getProtocolNodeName(PatternSuffixes.removeSuffix(comatch.getPatternName())));
		application.setAmalgamated(true);
	}

	/**
	 * Maximality check that assures complement rule is applied, is only done for
	 * nodes. It might be in the future that some tests show that this is also
	 * needed for edges.
	 */
	private void handleMaximality(IMatch match, int kernelMatchID) {
		String ruleName = removeAllSuffixes(match.getPatternName());
		TGGComplementRule rule = getComplementRule(ruleName).get();
		if (rule.isBounded()) {
			// check if the complement rule was applied. If not, mark its kernel as invalid.
			Set<EObject> contextNodes = getGenContextNodes(match);
			if (!matchToContextNodes.containsValue(contextNodes))
				invalidKernels.add(kernelMatchID);
		}
	}

	private void applyMatchAndHandleUniqueness(IMatch match,
			IntToObjectMap<Set<EObject>> complementMatchToContextNodes) {
		String ruleName = operationalMatchContainer.getRuleName(match);
		if (processOperationalRuleMatch(ruleName, match) != null) {
			TGGComplementRule rule = getComplementRule(ruleName).get();
			if (rule.isBounded())
				findDuplicatedMatches(idToMatch.size(), complementMatchToContextNodes);
		}
	}

	/**
	 * Uniqueness check among same CR matches of the same CR is only done for nodes.
	 * It might be in the future that some tests show that this is also needed for
	 * edges.
	 */
	private void findDuplicatedMatches(int currentComplementMatch,
			IntToObjectMap<Set<EObject>> complementMatchToContextNodes) {

		Set<EObject> contextNodesForCurrentComplementMatch = matchToContextNodes.get(currentComplementMatch);
		for (int previousComplementMatch : complementMatchToContextNodes.keySet()) {
			// check if matches belong to the same complement rule
			if (matchIdToRuleName.get(currentComplementMatch).equals(matchIdToRuleName.get(previousComplementMatch))) {
				if (matchToContextNodes.get(previousComplementMatch).equals(contextNodesForCurrentComplementMatch)) {
					if (!sameComplementMatches.containsKey(currentComplementMatch)) {
						sameComplementMatches.put(currentComplementMatch, cfactory.createIntSet());
						sameComplementMatches.get(currentComplementMatch).add(currentComplementMatch);
						sameComplementMatches.get(currentComplementMatch).add(previousComplementMatch);
					} else {
						sameComplementMatches.get(currentComplementMatch).add(previousComplementMatch);
					}
				}
			}
		}
		complementMatchToContextNodes.put(currentComplementMatch, contextNodesForCurrentComplementMatch);
	}

	private String removeAllSuffixes(String name) {
		if (name.indexOf(getGENPatternForMaximality()) == -1)
			return name;
		return name.substring(0, name.indexOf(getGENPatternForMaximality()));
	}

	private Set<EObject> getGenContextNodes(IMatch match) {
		Set<EObject> contextNodes = match.getParameterNames().stream()//
				.map(n -> (EObject) match.get(n))//
				.collect(Collectors.toSet());
		return contextNodes;
	}

	/**
	 * @return Collection of all complement matches that has to be applied.
	 */
	private Set<IMatch> findAllComplementRuleContextMatches() {
		return operationalMatchContainer.getMatches().stream()
				.filter(m -> m.getPatternName().contains(getGENPatternForMaximality())).collect(Collectors.toSet());
	}

	/**
	 * @return Collection of all complement matches existing in the match container
	 */
	private Set<IMatch> findAllComplementRuleMatches() {
		return operationalMatchContainer.getMatches().stream()
				.filter(m -> getComplementRulesNames().contains(PatternSuffixes.removeSuffix(m.getPatternName())))
				.collect(Collectors.toSet());
	}

	@Override
	protected void wrapUp() {
		ArrayList<EObject> objectsToDelete = new ArrayList<EObject>();

		for (int v : chooseTGGRuleApplications()) {
			int id = v < 0 ? -v : v;
			IMatch comatch = idToMatch.get(id);
			if (v < 0) {
				for (TGGRuleCorr createdCorr : getGreenFactory(matchIdToRuleName.get(id)).getGreenCorrNodesInRule())
					objectsToDelete.add((EObject) comatch.get(createdCorr.getName()));

				objectsToDelete.add(getRuleApplicationNode(comatch));
			}
		}

		EcoreUtil.deleteAll(objectsToDelete, true);
		consistencyReporter.init(this);
	}

	@Override
	public void setIsRuleApplicationFinal(TGGRuleApplication ra) {
		ra.setFinal(false);
	}

	@Override
	protected void prepareMarkerCreation(IGreenPattern greenPattern, IMatch comatch, String ruleName) {
		idToMatch.put(idCounter, comatch);
		matchToWeight.put(idCounter, this.getWeightForMatch(comatch, ruleName));
		matchIdToRuleName.put(idCounter, ruleName);

		getGreenNodes(comatch, ruleName).forEach(e -> {
			if (!nodeToMarkingMatches.containsKey(e))
				nodeToMarkingMatches.put(e, cfactory.createIntSet());
			nodeToMarkingMatches.get(e).add(idCounter);
		});

		getGreenEdges(comatch, ruleName).forEach(e -> {
			if (!edgeToMarkingMatches.containsKey(e)) {
				edgeToMarkingMatches.put(e, cfactory.createIntSet());
			}
			edgeToMarkingMatches.get(e).add(idCounter);
		});

		getBlackNodes(comatch, ruleName).forEach(e -> {
			if (!contextNodeToNeedingMatches.containsKey(e))
				contextNodeToNeedingMatches.put(e, cfactory.createIntSet());
			contextNodeToNeedingMatches.get(e).add(idCounter);
		});

		getBlackEdges(comatch, ruleName).forEach(e -> {
			if (!contextEdgeToNeedingMatches.containsKey(e)) {
				contextEdgeToNeedingMatches.put(e, cfactory.createIntSet());
			}
			contextEdgeToNeedingMatches.get(e).add(idCounter);
		});

		matchToContextNodes.put(idCounter, cfactory.createObjectSet());
		matchToContextNodes.get(idCounter).addAll(getBlackNodes(comatch, ruleName));

		matchToContextEdges.put(idCounter, cfactory.createEMFEdgeHashSet());
		matchToContextEdges.get(idCounter).addAll(getBlackEdges(comatch, ruleName));

		handleBundles(comatch, ruleName);

		idCounter++;
	}

	public boolean modelsAreConsistent() {
		return getInconsistentSrcNodes().size() + //
				getInconsistentTrgNodes().size() + //
				getInconsistentSrcEdges().size() + //
				getInconsistentTrgEdges().size() == 0;
	}

	public Collection<EObject> getInconsistentSrcNodes() {
		return consistencyReporter.getInconsistentSrcNodes();
	}

	public Collection<EObject> getInconsistentTrgNodes() {
		return consistencyReporter.getInconsistentTrgNodes();
	}

	public Collection<EMFEdge> getInconsistentSrcEdges() {
		return consistencyReporter.getInconsistentSrcEdges();
	}

	public Collection<EMFEdge> getInconsistentTrgEdges() {
		return consistencyReporter.getInconsistentTrgEdges();
	}

	public String generateConsistencyReport() {
		String result = "";
		if (modelsAreConsistent())
			result += "Your models are consistent";
		else {
			result += "Your models are inconsistent. The following elements are not part of a consistent triple:";
			result += "\n" + "Source nodes:" + "\n";
			result += String.join("\n",
					getInconsistentSrcNodes().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Source edges:" + "\n";
			result += String.join("\n",
					getInconsistentSrcEdges().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Target nodes:" + "\n";
			result += String.join("\n",
					getInconsistentTrgNodes().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Target edges:" + "\n";
			result += String.join("\n",
					getInconsistentTrgEdges().stream().map(n -> n.toString()).collect(Collectors.toSet()));
		}
		return result;
	}

	/**
	 * Specifies, which GEN pattern is relevant to handle maximality with respect to
	 * multi-amalgamation
	 * 
	 * @return name of the GEN pattern
	 */
	public String getGENPatternForMaximality() {
		return PatternSuffixes.GENForCC;
	}
}
