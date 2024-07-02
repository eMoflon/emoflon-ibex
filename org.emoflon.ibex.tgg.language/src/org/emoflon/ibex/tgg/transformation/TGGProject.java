package org.emoflon.ibex.tgg.transformation;

import org.eclipse.emf.ecore.EPackage;

import language.TGG;

public class TGGProject {

	private EPackage corrPackage;
	private TGG tggModel;
	private TGG flattenedTggModel;
	
	public TGGProject(EPackage corrPackage, TGG tggModel, TGG flattenedTggModel){
		this.corrPackage = corrPackage;
		this.tggModel = tggModel;
		this.flattenedTggModel = flattenedTggModel;
	}
	
	public EPackage getCorrPackage() {
		return corrPackage;
	}

	public TGG getTggModel() {
		return tggModel;
	}

	public TGG getFlattenedTggModel() {
		return flattenedTggModel;
	}

}
