package org.emoflon.ibex.tgg.operational.strategies.co;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.util.IMatch;

import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;
import runtime.TGGRuleApplication;

public abstract class CO extends OperationalStrategy {
	
	public CO(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	@Override
	protected boolean manipulateSrc() {
		return false;
	}

	@Override
	protected boolean manipulateTrg() {
		return false;
	}

	@Override
	public void loadModels() throws IOException {
		s = loadResource(projectPath + "/instances/src.xmi");
		t = loadResource(projectPath + "/instances/trg.xmi");
		c = loadResource(projectPath + "/instances/corr.xmi");
		p = createResource(projectPath + "/instances/protocol.xmi");

		EcoreUtil.resolveAll(rs);
	}

	@Override
	public void saveModels() throws IOException {
		p.save(null);		
	}

	@Override
	public boolean isPatternRelevant(String patternName) {
		return patternName.endsWith(PatternSuffixes.WHOLE);
	}
	
	@Override
	protected boolean processOneOperationalRuleMatch() {
		return true;
	}

	@Override
	protected void wrapUp() {
		 
	}
	
	@Override
	public void run() throws IOException {
		engine.updateMatches();
		
		wrapUp();
	}

	@Override
	protected boolean allContextElementsalreadyProcessed(IMatch match, String ruleName) {
		return true;
	}

	@Override
	protected boolean someElementsAlreadyProcessed(String ruleName, IMatch match) {
		return false;
	}

	@Override
	protected void setIsRuleApplicationFinal(TGGRuleApplication ra) {
		ra.setFinal(false);
	}

	@Override
	protected void prepareProtocol(String ruleName, IMatch match, HashMap<String, EObject> comatch) {
		super.prepareProtocol(ruleName, match, comatch);
	}

	@Override
	public List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library) {
		// With respect to attribute constraints, there is no difference between CC and CO!
		return library.getSorted_CC();
	}

}
