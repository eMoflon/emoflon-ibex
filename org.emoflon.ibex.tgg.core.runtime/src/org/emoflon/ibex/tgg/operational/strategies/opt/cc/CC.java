package org.emoflon.ibex.tgg.operational.strategies.opt.cc;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

import org.emoflon.ibex.tgg.operational.strategies.opt.OPT;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

import language.TGGRuleCorr;

public abstract class CC extends OPT {

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
		return patternName.endsWith(PatternSuffixes.CC) || patternName.endsWith(PatternSuffixes.GENForCC)
				|| patternName.endsWith(PatternSuffixes.USER_NAC);
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

		processOperationalRuleMatch(ruleName, match);
		removeOperationalRuleMatch(match);

		return true;
	}
	
	@Override
	protected void addObjectsToDelete(List<EObject> objectsToDelete, IMatch comatch, int id) {
		for (TGGRuleCorr createdCorr : getGreenFactory(matchIdToRuleName.get(id)).getGreenCorrNodesInRule())
			objectsToDelete.add((EObject) comatch.get(createdCorr.getName()));
	
		objectsToDelete.add(getRuleApplicationNode(comatch));
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
