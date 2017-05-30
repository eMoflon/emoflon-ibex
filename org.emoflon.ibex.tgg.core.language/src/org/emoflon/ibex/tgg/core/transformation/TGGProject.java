package org.emoflon.ibex.tgg.core.transformation;

import org.eclipse.emf.ecore.EPackage;

import language.TGG;

public class TGGProject {

	private EPackage corrPackage;
	private TGG tggModel;
	
	public TGGProject(EPackage corrPackage, TGG tggModel){
		this.corrPackage = corrPackage;
		this.tggModel = tggModel;
	}
	
	public EPackage getCorrPackage() {
		return corrPackage;
	}

	public TGG getTggModel() {
		return tggModel;
	}

}
