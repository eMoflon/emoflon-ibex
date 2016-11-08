package org.emoflon.ibex.tgg.operational;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.viatra.transformation.runtime.emf.modelmanipulation.IModelManipulations;
import org.eclipse.viatra.transformation.runtime.emf.modelmanipulation.ModelManipulationException;
import org.emoflon.ibex.tgg.axiom.Axiom_CORR_CONTEXTMatch;
import org.emoflon.ibex.tgg.axiom.Axiom_SRCMatch;
import org.emoflon.ibex.tgg.axiom.Axiom_SRC_CONTEXTMatch;
import org.emoflon.ibex.tgg.axiom.Axiom_TRGMatch;
import org.emoflon.ibex.tgg.axiom.Axiom_TRG_CONTEXTMatch;

public class ClazzToFileRuleOperations extends RuleOperations {
	public ClazzToFileRuleOperations(IModelManipulations manipulator, Resource src, Resource corr, Resource trg, Resource edges) throws IOException{
		super(manipulator, src, corr, trg, edges);
		register("platform:/resource/CDToDoc/domains/CD.ecore", "dep_0");
		register("platform:/resource/CDToDoc/model/CDToDoc.ecore", "dep_1");
		register("platform:/resource/CDToDoc/domains/Doc.ecore", "dep_2");
	}
	
	public void forward(Axiom_SRCMatch _src_, Axiom_CORR_CONTEXTMatch _corrContext_, Axiom_TRG_CONTEXTMatch _trgContext_) throws IOException, ModelManipulationException{
		// Create objects
		EObject f2 = createTrg("Folder", "dep_2");
		
		// Create links
		linkWithEdge(_trgContext_.get("f1"), "files", f2);
		
		// Create corrs
		createCorr("ClazzToFile", "dep_1", _src_.get("c1"), f2);
	}


	public void backward(Axiom_TRGMatch _trg_, Axiom_CORR_CONTEXTMatch _corrContext_, Axiom_SRC_CONTEXTMatch _srcContext_) throws ModelManipulationException{
		// Create objects
		EObject c1 = createTrg("Clazz", "dep_0");

		// Create links
		linkWithEdge(_srcContext_.get("p1"), "clazzs", c1);

		// Create corrs
		createCorr("ClazzToFile", "dep_1", c1, _trg_.get("f2"));
	}
}
