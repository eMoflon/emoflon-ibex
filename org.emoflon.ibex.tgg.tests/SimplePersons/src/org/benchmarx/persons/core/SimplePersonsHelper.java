package org.benchmarx.persons.core;

import SimplePersons.Person;
import SimplePersons.PersonRegister;
import SimplePersons.SimplePersonsFactory;

public class SimplePersonsHelper {
	public void createMotherAsFemale(PersonRegister register){
		Person p = SimplePersonsFactory.eINSTANCE.createFemale();
		p.setName("mother");
		register.getPersons().add(p);
	}
}
