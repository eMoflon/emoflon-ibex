package org.emoflon.ibex.tgg.runtime.strategies.opt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.engine.IMatch;
import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.StrategyMode;
import org.emoflon.ibex.tgg.runtime.updatepolicy.NextMatchUpdatePolicy;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

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
		TGGRule rule = ruleHandler.getRule(ruleName);

		return super.getDefaultWeightForMatch(comatch, ruleName)
				+ rule.getCreateCorrespondence().getNodes().size();
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
	protected Set<PatternType> getRelevantOperationalPatterns() {
		return new HashSet<>(Arrays.asList(PatternType.CC, PatternType.GENForCO));
	}

	@Override
	public StrategyMode getStrategyMode() {
		return StrategyMode.CHECK_ONLY;
	}
}