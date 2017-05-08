package org.benchmarx.families.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import SimpleFamilies.FamilyMember;

public class FamilyMemberNormaliser implements Comparator<FamilyMember>{

	@Override
	public int compare(FamilyMember member1, FamilyMember member2) {
		return member1.getName().compareTo(member2.getName());
	}

	public void normalize(List<FamilyMember> familyMember){
		Comparator<FamilyMember> comparator = new FamilyMemberNormaliser();
		Collections.sort(familyMember, comparator);
	}
}
