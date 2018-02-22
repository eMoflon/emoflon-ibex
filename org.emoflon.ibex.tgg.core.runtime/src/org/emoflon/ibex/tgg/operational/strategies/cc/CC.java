package org.emoflon.ibex.tgg.operational.strategies.cc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.OPT;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.set.hash.THashSet;
import gnu.trove.set.hash.TIntHashSet;
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
		s = loadResource(projectPath + "/instances/src.xmi");
		t = loadResource(projectPath + "/instances/trg.xmi");
		c = createResource(projectPath + "/instances/corr.xmi");
		p = createResource(projectPath + "/instances/protocol.xmi");

		EcoreUtil.resolveAll(rs);
	}

	@Override
	public void saveModels() throws IOException {
		c.save(null);
		p.save(null);
	}

	@Override
	public boolean isPatternRelevantForCompiler(String patternName) {
		return patternName.endsWith(PatternSuffixes.CC);
	}
	
	@Override
	protected boolean processOneOperationalRuleMatch() {
		if (operationalMatchContainer.isEmpty())
			return false;
		
		if(operationalMatchContainer.getMatches().stream()
				.allMatch(m -> m.getPatternName().contains(PatternSuffixes.GENForCC))) {
			//FIXME[Anjorin]:  Find an elegant way of properly discarding GENForCC matches!
			return false;
		}

		IMatch match = chooseOneMatch();
		String ruleName = operationalMatchContainer.getRuleName(match);
		
		if(ruleName == null)
			return true;  //FIXME[Anjorin]:  This should be avoided (all matches that do not correspond to rules should be filtered)
		
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
		int kernelMatchID = idToMatch.size();
		Set<IMatch> contextRuleMatches = findAllComplementRuleContextMatches();
		Set<IMatch> complementRuleMatches = findAllComplementRuleMatches();
		
		THashMap<Integer, THashSet<EObject>> crMatchToContextNodes = new THashMap<>();
		
		while (complementRuleMatches.iterator().hasNext()) {
			IMatch match = complementRuleMatches.iterator().next();
			applyMatchAndHandleUniqueness(match, crMatchToContextNodes);
			complementRuleMatches.remove(match);
			removeOperationalRuleMatch(match);
		}
		
		//check if all found CR matches are really applied
		while (contextRuleMatches.iterator().hasNext()) {
			IMatch match = contextRuleMatches.iterator().next();
			handleMaximality(match, contextRuleMatches, kernelMatchID);
			contextRuleMatches.remove(match);
		}
		
		//FIXME:[Milica] Check if this is really needed
		TGGRuleApplication application = (TGGRuleApplication) comatch.get(ConsistencyPattern.getProtocolNodeName(PatternSuffixes.removeSuffix(comatch.getPatternName())));
		application.setAmalgamated(true);
	}
	
	//FIXME:[Milica] Check if maximality need to be done for edges as well
	private void handleMaximality(IMatch match, Set<IMatch> contextRuleMatches, int kernelMatchID) {
		String ruleName = removeAllSuffixes(match.getPatternName());
		TGGComplementRule rule = getComplementRule(ruleName).get();
		if(rule.isBounded()) {
		//check if the complement rule was applied. If not, mark its kernel as invalid.
			THashSet<EObject> contextNodes = getGenContextNodes(match);
			if (!matchToContextNodes.containsValue(contextNodes))
				invalidKernels.add(kernelMatchID);
		}
	}

	//FIXME:[Milica] Check if uniqueness need to be done for edges as well
	private void applyMatchAndHandleUniqueness(IMatch match, THashMap<Integer, THashSet<EObject>> contextNodesMatches) {
		String ruleName = operationalMatchContainer.getRuleName(match);
		if (processOperationalRuleMatch(ruleName, match) != null) {
			TGGComplementRule rule = getComplementRule(ruleName).get();
			if(rule.isBounded())
				findDuplicatedMatches(idToMatch.size(), contextNodesMatches);
		}
	}

	private void findDuplicatedMatches(int matchID, THashMap<Integer, THashSet<EObject>> contextNodesMatches) { 
		THashSet<EObject> contextNodesForMatchID = matchToContextNodes.get(matchID);
		for (Integer id : contextNodesMatches.keySet()) {
		//check if matches belong to the same complement rule
			if (matchIdToRuleName.get(matchID).equals(matchIdToRuleName.get(id))) {
				if(matchToContextNodes.get(id).equals(contextNodesForMatchID)) {
					if (!sameCRmatches.containsKey(matchID)) {
						sameCRmatches.put(matchID, new TIntHashSet());
						sameCRmatches.get(matchID).add(matchID);
						sameCRmatches.get(matchID).add(id);
					}
					else {
					sameCRmatches.get(matchID).add(id);
					}
				}
			}
		}
		contextNodesMatches.put(matchID, contextNodesForMatchID);
	}
	
	private String removeAllSuffixes(String name) {
		if(name.indexOf(PatternSuffixes.GENForCC) == -1)
			return name;
		return name.substring(0, name.indexOf(PatternSuffixes.GENForCC));
	}

	private THashSet<EObject> getGenContextNodes(IMatch match){
		THashSet<EObject> contextNodes = match.getParameterNames().stream()
				.map(n -> match.get(n)).collect(Collectors.toCollection(THashSet<EObject>::new));
		return contextNodes;
	}
	
	/**
	 * @return Collection of all matches that has to be applied.
	 */
	private Set<IMatch> findAllComplementRuleContextMatches() {
		Set<IMatch> allComplementRuleMatches = operationalMatchContainer.getMatches().stream()
				.filter(m -> m.getPatternName().contains(PatternSuffixes.GENForCC))
				.collect(Collectors.toSet());
		return allComplementRuleMatches;
	}
	
	private Set<IMatch> findAllComplementRuleMatches() {
		Set<IMatch> allComplementRuleMatches = operationalMatchContainer.getMatches().stream()
				.filter(m -> getComplementRulesNames().contains(PatternSuffixes.removeSuffix(m.getPatternName())))
				.collect(Collectors.toSet());
		return allComplementRuleMatches;
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
		matchIdToRuleName.put(idCounter, ruleName);

		int weight = 
				getGreenFactory(ruleName).getGreenSrcEdgesInRule().size() + 
				getGreenFactory(ruleName).getGreenSrcNodesInRule().size() + 
				getGreenFactory(ruleName).getGreenTrgEdgesInRule().size() + 
				getGreenFactory(ruleName).getGreenTrgNodesInRule().size();

		weights.put(idCounter, weight);

		getGreenNodes(comatch, ruleName).forEach(e -> {
			if (!nodeToMarkingMatches.containsKey(e))
				nodeToMarkingMatches.put(e, new TIntHashSet());
			nodeToMarkingMatches.get(e).add(idCounter);
		});

		getGreenEdges(comatch, ruleName).forEach(e -> {
			if (!edgeToMarkingMatches.containsKey(e)) {
				edgeToMarkingMatches.put(e, new TIntHashSet());
			}
			edgeToMarkingMatches.get(e).add(idCounter);
		});

		matchToContextNodes.put(idCounter, new THashSet<>());
		matchToContextNodes.get(idCounter).addAll(getBlackNodes(comatch, ruleName));

		matchToContextEdges.put(idCounter, new TCustomHashSet<RuntimeEdge>(new RuntimeEdgeHashingStrategy()));
		matchToContextEdges.get(idCounter).addAll(getBlackEdges(comatch, ruleName));
		
		handleBundles(comatch, ruleName);

		idCounter++;
	}

	public boolean modelsAreConsistent() {
		return getInconsistentSrcNodes().size() + getInconsistentTrgNodes().size() + getInconsistentSrcEdges().size()
				+ getInconsistentTrgEdges().size() == 0;
	}

	public Collection<EObject> getInconsistentSrcNodes() {
		return consistencyReporter.getInconsistentSrcNodes();
	}

	public Collection<EObject> getInconsistentTrgNodes() {
		return consistencyReporter.getInconsistentTrgNodes();
	}

	public Collection<RuntimeEdge> getInconsistentSrcEdges() {
		return consistencyReporter.getInconsistentSrcEdges();
	}

	public Collection<RuntimeEdge> getInconsistentTrgEdges() {
		return consistencyReporter.getInconsistentTrgEdges();
	}

	public String generateConsistencyReport() {
		String result = "";
		if (modelsAreConsistent())
			result += "Your models are consistent";
		else {
			result += "Your models are inconsistent. Following elements are not part of a consistent triple:";
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

}
