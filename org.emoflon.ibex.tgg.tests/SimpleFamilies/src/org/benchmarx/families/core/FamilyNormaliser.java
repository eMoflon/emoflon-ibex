package org.benchmarx.families.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import SimpleFamilies.Family;
import SimpleFamilies.FamilyMember;

public class FamilyNormaliser implements Comparator<Family>{
	
	@Override
	public int compare(Family family1, Family family2) {
		if(!(family1.getName().equals(family2.getName())))
		{
			return family1.getName().compareTo(family2.getName());
		}
		else
		{
			int f_mother = createStringFromFamilyMember(family1.getMother()).compareTo(createStringFromFamilyMember(family2.getMother()));
			if(f_mother!=0)
			{
				return f_mother;
			}
			else
			{
				int f_father = createStringFromFamilyMember(family1.getFather()).compareTo(createStringFromFamilyMember(family2.getFather()));
				if(f_father!=0)
				{
					return f_father;
				}
				else
				{
					int f_daughters = createStringFromMembers(family1.getDaughters()).compareTo(createStringFromMembers(family2.getDaughters()));
					if(f_daughters!=0)
					{
						return f_daughters;
					}
					else
					{
						int f_sons = createStringFromMembers(family1.getSons()).compareTo(createStringFromMembers(family2.getSons()));
						if(f_sons!=0)
						{
							return f_sons;
						}
						else
						{
							return -1;
						}
					}
				}
			}
			
		}
	}

	public void normalize(List<Family> families){
		Comparator<Family> comparator = new FamilyNormaliser();
		Collections.sort(families, comparator);
	}
	
	private String createStringFromFamilyMember(FamilyMember m)
	{
		if(m == null)
		{
			return "";
		}
		else {
			return m.getName();
		}
		     
	}
	
	private String createStringFromMembers(List<FamilyMember> members)
	{
		Comparator<FamilyMember> comparator = new FamilyMemberNormaliser();
		Collections.sort(members, comparator);
		return members.stream().map(FamilyMember::getName).collect(Collectors.joining("-"));
	}
	
}