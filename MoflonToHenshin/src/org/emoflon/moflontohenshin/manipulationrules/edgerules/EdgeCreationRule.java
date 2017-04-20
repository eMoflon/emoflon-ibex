package org.emoflon.moflontohenshin.manipulationrules.edgerules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.moflontohenshin.ManipulationHelper;

public abstract class EdgeCreationRule {
	
	public EdgeCreationRule(){
		ManipulationHelper.addEdgeCreationRule(this);
	}
	
	public abstract boolean needsForcedCreation(EObject src, EObject trg, EReference ref);
	public abstract void forceCreation(EObject src, EObject trg, EReference ref);
}
