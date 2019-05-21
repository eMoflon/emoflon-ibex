package org.emoflon.ibex.tgg.operational.strategies.opt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.opt.cc.CC;
import org.emoflon.ibex.tgg.operational.updatepolicy.NextMatchUpdatePolicy;

public abstract class CO extends CC {

	public CO(IbexOptions options) throws IOException {
		super(options, new NextMatchUpdatePolicy());
	}

	@Override
	public boolean isPatternRelevantForCompiler(String patternName) {
		return patternName.endsWith(PatternSuffixes.CO) || patternName.endsWith(PatternSuffixes.GENForCO);
	}

	@Override
	protected void wrapUp() {
		ArrayList<EObject> objectsToDelete = new ArrayList<EObject>();

		for (int v : chooseTGGRuleApplications()) {
			int id = v < 0 ? -v : v;
			IMatch comatch = idToMatch.get(id);
			if (v < 0)
				objectsToDelete.add(getRuleApplicationNode(comatch));
		}

		EcoreUtil.deleteAll(objectsToDelete, true);
		consistencyReporter.initWithCorr(this);
	}

	@Override
	public void loadModels() throws IOException {
		s = loadResource(options.projectPath() + "/instances/src.xmi");
		t = loadResource(options.projectPath() + "/instances/trg.xmi");
		c = loadResource(options.projectPath() + "/instances/corr.xmi");
		p = createResource(options.projectPath() + "/instances/protocol.xmi");

		EcoreUtil.resolveAll(rs);
	}

	@Override
	public void saveModels() throws IOException {
		p.save(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.emoflon.ibex.tgg.operational.strategies.OPT#getWeightForMatch(org.emoflon
	 * .ibex.tgg.operational.matches.IMatch, java.lang.String)
	 */
	@Override
	public double getDefaultWeightForMatch(IMatch comatch, String ruleName) {
		return super.getDefaultWeightForMatch(comatch, ruleName)
				+ getGreenFactory(ruleName).getGreenCorrNodesInRule().size();
	}

	@Override
	public boolean modelsAreConsistent() {
		return getInconsistentSrcNodes().size() + getInconsistentTrgNodes().size() + getInconsistentSrcEdges().size()
				+ getInconsistentTrgEdges().size() + getInconsistentCorrNodes().size() == 0;
	}

	public Collection<EObject> getInconsistentCorrNodes() {
		return consistencyReporter.getInconsistentCorrNodes();
	}

	@Override
	public String getGENPatternForMaximality() {
		return PatternSuffixes.GENForCO;
	}
}
