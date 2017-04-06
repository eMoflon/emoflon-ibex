package org.benchmarx.cd.core;

import org.benchmarx.compare.EMFOrderedStringComparator;

import CD.CDPackage;


public class CDComparator extends EMFOrderedStringComparator<CD.Package> {

	@Override
	public void registerEClasses() {
		registerEClass(CDPackage.Literals.PACKAGE);
		registerEClass(CDPackage.Literals.CLAZZ);
	}


	@Override
	public void registerAttributes() {
		registerEAttribute(CDPackage.Literals.PACKAGE__INDEX);
		registerEAttribute(CDPackage.Literals.PACKAGE__NAME);
		registerEAttribute(CDPackage.Literals.CLAZZ__GENERATE_DOC);
		registerEAttribute(CDPackage.Literals.CLAZZ__NAME);
	}

	@Override
	public void registerEDataTypeToStringConverter() {
	}

	@Override
	public void registerEReferences() {
		registerEReference(CDPackage.Literals.PACKAGE__PACKAGES);
		registerEReference(CDPackage.Literals.PACKAGE__CLAZZS);
		registerEReference(CDPackage.Literals.CLAZZ__SUPER_CLAZZ);
	}
}
