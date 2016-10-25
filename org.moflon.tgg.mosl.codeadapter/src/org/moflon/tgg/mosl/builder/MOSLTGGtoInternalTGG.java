package org.moflon.tgg.mosl.builder;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

import language.TGG;
import language.LanguageFactory;


public class MOSLTGGtoInternalTGG {
	
	public TGGProject convertXtextTGG(TripleGraphGrammarFile xtextTGG){
		
		EPackage pack = EcoreFactory.eINSTANCE.createEPackage();
		pack.setName("foo");
		
		TGG tgg = LanguageFactory.eINSTANCE.createTGG();
		tgg.setName("bar");
		
		return new TGGProject(pack, tgg);
	}
}
