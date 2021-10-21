package org.emoflon.ibex.tgg.operational.strategies.opt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.updatepolicy.NextMatchUpdatePolicy;

public class CO extends CC {

	public CO(IbexOptions options) throws IOException {
		super(options, new NextMatchUpdatePolicy());
	}

	@Override
	protected void wrapUp() {
		ArrayList<EObject> objectsToDelete = new ArrayList<EObject>();

		for (int v : chooseTGGRuleApplications()) {
			int id = v < 0 ? -v : v;
			ITGGMatch comatch = idToMatch.get(id);
			if (v < 0)
				objectsToDelete.add(comatch.getRuleApplicationNode());
		}

		EcoreUtil.deleteAll(objectsToDelete, true);
		consistencyReporter.initWithCorr();
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
	public Collection<PatternType> getPatternRelevantForCompiler() {
		return PatternType.getCOTypes();
	}
}