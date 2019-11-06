package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.opt.FixingCopier;
import org.emoflon.ibex.tgg.operational.strategies.opt.OPT;

import language.TGGRuleCorr;
import language.TGGRuleNode;

public class INTEGRATE_OPT extends OPT {

	private INTEGRATE integrate;
	
	private Collection<ITGGMatch> fwdMatches = new HashSet<>();
	private Collection<ITGGMatch> bwdMatches = new HashSet<>();
	private Collection<ITGGMatch> ccMatches = new HashSet<>();
	
	public INTEGRATE_OPT(INTEGRATE integrate, IbexOptions options) throws IOException {
		super(options);
		this.integrate = integrate;
	}

	@Override
	protected void wrapUp() {
		ArrayList<EObject> objectsToDelete = new ArrayList<EObject>();
		for (int v : chooseTGGRuleApplications()) {
			int id = v < 0 ? -v : v;
			ITGGMatch comatch = idToMatch.get(id);
			if (v < 0) {
				for (TGGRuleNode createdSrcNode : getGreenFactory(matchIdToRuleName.get(id)).getGreenSrcNodesInRule())
					objectsToDelete.add((EObject) comatch.get(createdSrcNode.getName()));

				for (TGGRuleCorr createdCorr : getGreenFactory(matchIdToRuleName.get(id)).getGreenCorrNodesInRule())
					objectsToDelete.add((EObject) comatch.get(createdCorr.getName()));

				for (TGGRuleNode createdTrgNode : getGreenFactory(matchIdToRuleName.get(id)).getGreenTrgNodesInRule())
					objectsToDelete.add((EObject) comatch.get(createdTrgNode.getName()));

				objectsToDelete.addAll(getRuleApplicationNodes(comatch));
			}
		}

		EcoreUtil.deleteAll(objectsToDelete, true);
		consistencyReporter.initSrc(this);
	}
	
	@Override
	public void addMatch(org.emoflon.ibex.common.operational.IMatch match) {
		if(!(match instanceof ITGGMatch))
			return;
		
		if(match.getPatternName().endsWith(PatternSuffixes.FWD_OPT)) {
			fwdMatches.add((ITGGMatch) match);
		} else 
		if(match.getPatternName().endsWith(PatternSuffixes.BWD_OPT)) {
			bwdMatches.add((ITGGMatch) match);
		} else
		if(match.getPatternName().endsWith(PatternSuffixes.CC)) {
			ccMatches.add((ITGGMatch) match);
		}
	}

	@Override
	public double getDefaultWeightForMatch(ITGGMatch comatch, String ruleName) {
		return getGreenFactory(ruleName).getGreenSrcEdgesInRule().size()
				+ getGreenFactory(ruleName).getGreenSrcNodesInRule().size();
	}

	@Override
	public void saveModels() throws IOException {
		// Unrelax the metamodel
		unrelaxReferences(options.tgg().getSrc());
		unrelaxReferences(options.tgg().getTrg());

		// lfritsche: what's this code doing?
		// Remove adapters to avoid problems with notifications
		t.eAdapters().clear();
		t.getAllContents().forEachRemaining(o -> o.eAdapters().clear());
		c.eAdapters().clear();
		c.getAllContents().forEachRemaining(o -> o.eAdapters().clear());

		// Copy and fix the model in the process
		FixingCopier.fixAll(s, c, "source");
		FixingCopier.fixAll(t, c, "target");
	}

	@Override
	public void loadModels() throws IOException {
		s = integrate.getSourceResource();
		t = integrate.getTargetResource();
		c = integrate.getCorrResource();
		p = integrate.getProtocolResource();
	}

	@Override
	protected void registerUserMetamodels() throws IOException {
		
	}

	public void unrelaxReferences() {
		relaxReferences(options.tgg().getSrc());
		relaxReferences(options.tgg().getTrg());
	}

}
