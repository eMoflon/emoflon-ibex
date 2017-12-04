package org.emoflon.ibex.tgg.operational.strategies.co;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.util.IMatch;

import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;
import runtime.TGGRuleApplication;

public abstract class CO<E> extends OperationalStrategy {
	
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

	}

	@Override
	public void saveModels() throws IOException {

	}

	@Override
	public boolean isPatternRelevant(String patternName) {
		return patternName.endsWith(PatternSuffixes.CO);
	}
	
	@Override
	protected boolean processOneOperationalRuleMatch() {
		return true;
	}

	@Override
	protected void wrapUp() {
		 
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
		return library.getSorted_CO();
	}


}
