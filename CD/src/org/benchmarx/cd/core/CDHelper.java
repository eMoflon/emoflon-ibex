package org.benchmarx.cd.core;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import CD.CDFactory;
import CD.CDPackage;
import CD.Clazz;


public class CDHelper {
	
	private CDBuilder builder = null;
	
	public CDHelper() {
		
	}
	
	private CD.Package createPackage(String name, int index) {
		CD.Package pack = CDFactory.eINSTANCE.createPackage();
		pack.setName(name);
		pack.setIndex(index);
		return pack;
	}
	
	public CD.Package createSubPackage(String name, int index, CD.Package superPackage) {
		CD.Package sub = createPackage(name, index);
		superPackage.getPackages().add(sub);
		return sub;
	}

	public Clazz createClazz(String name, CD.Package pack) {
		Clazz clazz = CDFactory.eINSTANCE.createClazz();
		clazz.setName(name);
		pack.getClazzs().add(clazz);
		return clazz;
	}

	public Clazz createClazz(String name, CD.Package pack, boolean generateDoc) {
		Clazz clazz = createClazz(name, pack);
		clazz.setGenerateDoc(generateDoc);
		return clazz;
	}
	
	
	public Clazz createSubClazz(String name, CD.Package pack, Clazz superClazz) {
		Clazz subClazz = createClazz(name, pack);
		subClazz.getSuperClazz().add(superClazz);
		subClazz.setGenerateDoc(true);
		return subClazz;
	}
	
	public Clazz createSuperClazz(String name, CD.Package pack, Clazz subClazz) {
		Clazz superClazz = createClazz(name, pack);
		subClazz.getSuperClazz().add(superClazz);
		superClazz.setGenerateDoc(true);
		return superClazz;
	}
	
	public void createComplexExample(CD.Package rootPackage) {
		createClazz("rootClazz", rootPackage);
		
		CD.Package sub1 = createSubPackage("sub1", 2, rootPackage);
		CD.Package subsub1 = createSubPackage("subsub1", 3, sub1);
		
		createClazz("subsubClazz1", subsub1);
		createClazz("subsubClazz2", subsub1);
		
		CD.Package sub2 = createSubPackage("sub2", 2, rootPackage);
		
		createClazz("subClazz1", sub2);
		createClazz("subClazz2", sub2);
		createClazz("subClazz3", sub2);
	}
	
	public CD.Package deleteElement(CD.Package root, String toBeDeleted) {
		if(root.getName().equals(toBeDeleted)) 
			EcoreUtil.delete(root);
		else {
			for(CD.Package p : root.getPackages().stream().collect(Collectors.toList())) 
				deleteElement(p, toBeDeleted);
			for(Clazz c : root.getClazzs().stream().collect(Collectors.toList())) 
				deleteElement(c, toBeDeleted);
			}
		return root;
	}
	
	private void deleteElement(Clazz clazz, String toBeDeleted) {
		if(clazz.getName().equals(toBeDeleted)) 
			EcoreUtil.delete(clazz);
	}
}
