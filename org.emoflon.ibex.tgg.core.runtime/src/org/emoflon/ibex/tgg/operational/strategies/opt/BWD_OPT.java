package org.emoflon.ibex.tgg.operational.strategies.opt;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRuleCorr;
import language.TGGRuleNode;

public abstract class BWD_OPT extends OPT {

	public BWD_OPT(IbexOptions options) throws IOException {
		super(options);
	}

	@Override
	public void loadModels() throws IOException {
		s = createResource(options.projectPath() + "/instances/src.xmi");
		t = loadResource(options.projectPath() + "/instances/trg.xmi");
		c = createResource(options.projectPath() + "/instances/corr.xmi");
		p = createResource(options.projectPath() + "/instances/protocol.xmi");

		EcoreUtil.resolveAll(rs);
	}
	
	@Override
	protected void addObjectsToDelete(List<EObject> objectsToDelete, IMatch comatch, int id) {
		for (TGGRuleCorr createdCorr : getGreenFactory(matchIdToRuleName.get(id)).getGreenCorrNodesInRule())
			objectsToDelete.add((EObject) comatch.get(createdCorr.getName()));

		for (TGGRuleNode createdSrcNode : getGreenFactory(matchIdToRuleName.get(id)).getGreenSrcNodesInRule())
			objectsToDelete.add((EObject) comatch.get(createdSrcNode.getName()));

		objectsToDelete.addAll(getRuleApplicationNodes(comatch));
	}

	@Override
	public boolean isPatternRelevantForCompiler(String patternName) {
		return patternName.endsWith(PatternSuffixes.BWD_OPT) || patternName.endsWith(PatternSuffixes.USER_NAC);
	}
	
	@Override
	public void saveModels() throws IOException {
		p.save(null);

		// Unrelax the metamodel
		unrelaxReferences(options.tgg().getSrc());

		// Remove adapters to avoid problems with notifications
		s.eAdapters().clear();
		s.getAllContents().forEachRemaining(o -> o.eAdapters().clear());
		c.eAdapters().clear();
		c.getAllContents().forEachRemaining(o -> o.eAdapters().clear());

		// Copy and fix the model in the process
		FixingCopier.fixAll(s, c, "source");

		// Now save fixed models
		s.save(null);
		c.save(null);
	}

	@Override
	public double getDefaultWeightForMatch(IMatch comatch, String ruleName) {
		return getGreenFactory(ruleName).getGreenTrgEdgesInRule().size()
				+ getGreenFactory(ruleName).getGreenTrgNodesInRule().size();
	}

	public void backward() throws IOException {
		run();
	}

	@Override
	public void loadTGG() throws IOException {
		super.loadTGG();
		relaxReferences(options.tgg().getSrc());
	}
}
