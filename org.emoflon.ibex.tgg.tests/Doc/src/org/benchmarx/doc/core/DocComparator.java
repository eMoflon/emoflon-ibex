package org.benchmarx.doc.core;


import org.benchmarx.compare.EMFOrderedStringComparator;

import Doc.DocPackage;
import Doc.Folder;



public class DocComparator extends EMFOrderedStringComparator<Folder> {

	@Override
	public void registerEClasses() {
		registerEClass(DocPackage.Literals.FOLDER);
		registerEClass(DocPackage.Literals.FILE);
		registerEClass(DocPackage.Literals.BINARY_FILE);
		registerEClass(DocPackage.Literals.DOC_FILE);
	}


	@Override
	public void registerAttributes() {
		registerEAttribute(DocPackage.Literals.FOLDER__NAME);
		registerEAttribute(DocPackage.Literals.FILE__NAME);
	}

	@Override
	public void registerEDataTypeToStringConverter() {
	}

	@Override
	public void registerEReferences() {
		registerEReference(DocPackage.Literals.FOLDER__FOLDERS);
		registerEReference(DocPackage.Literals.FOLDER__FILES);
		registerEReference(DocPackage.Literals.DOC_FILE__ALL_SUPER_REFS);
		registerEReference(DocPackage.Literals.DOC_FILE__SUPER_REF);
	}
}
