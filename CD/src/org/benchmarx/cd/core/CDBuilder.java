package org.benchmarx.cd.core;

import java.util.Date;

import CD.CDFactory;
import CD.Package;

public class CDBuilder {

	private Package root;
	private CDFactory f = CDFactory.eINSTANCE;
	
	public CDBuilder() {
		root = f.createPackage();
	}
	
	public CDBuilder(Package pac) {
		root = pac;
	}
	
//	public CDBuilder male(String name)  {
//		Male m = f.createMale();
//		m.setName(name);
//		register.getPersons().add(m);
//		return this;
//	}
//	
//	public CDBuilder female(String name) {
//		Female fm = f.createFemale();
//		fm.setName(name);
//		register.getPersons().add(fm);
//		return this;
//	}
//	
//	public CDBuilder female(String name, Date date) {
//		Female fm = f.createFemale();
//		fm.setName(name);
//		fm.setBirthday(date);
//		register.getPersons().add(fm);
//		return this;
//	}
}
