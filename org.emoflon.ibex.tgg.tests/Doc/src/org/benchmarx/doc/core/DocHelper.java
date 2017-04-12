package org.benchmarx.doc.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import Doc.DocFactory;
import Doc.DocFile;
import Doc.File;
import Doc.Folder;


public class DocHelper {
	
	private DocBuilder builder = null;
	
	public DocHelper() {
		
	}
	
	private Folder createFolder(String name) {
		Folder folder = DocFactory.eINSTANCE.createFolder();
		folder.setName(name);
		return folder;
	}
	
	public Folder createSubFolder(String name, Folder superFolder) {
		Folder sub = createFolder(name);
		superFolder.getFolders().add(sub);
		return sub;
	}

	public File createFile(String name, Folder folder) {
		File file = DocFactory.eINSTANCE.createFile();
		file.setName(name);
		folder.getFiles().add(file);
		return file;
	}
	
	public DocFile createDocFile(String name, Folder folder) {
		DocFile file = DocFactory.eINSTANCE.createDocFile();
		file.setName(name);
		folder.getFiles().add(file);
		return file;
	}
	
	public DocFile createSubDoc(String name, Folder folder, DocFile superDoc) {
		DocFile file = createDocFile(name, folder);
		createSuperLinksForDocs(file, superDoc);
		return file;
	}
	
	public DocFile createSuperDoc(String name, Folder folder, DocFile subDoc) {
		DocFile file = createDocFile(name, folder);
		createSuperLinksForDocs(subDoc, file);
		return file;
	}
	
	public DocFile createMiddleDoc(String name, Folder folder, DocFile superDoc, DocFile subDoc) {
		DocFile file = createDocFile(name, folder);
		createSuperLinksForDocs(file, superDoc);
		createSuperLinksForDocs(subDoc, file);
		return file;
	}
	
	public void createAllSuperLinks(Folder folder) {
		List<DocFile> docs = new ArrayList<>();
		collectDocs(folder, docs);
		docs.stream().filter(d -> d.getSuperRef() != null).forEach(d -> createSuperLinksForDocsRecursive(d, d.getSuperRef()));
	}
	
	public void collectDocs(Folder folder, List<DocFile> docs) {
		docs.addAll(folder.getFiles().stream().filter(f -> f instanceof DocFile).map(f -> (DocFile) f).collect(Collectors.toList()));
		folder.getFolders().stream().forEach(f -> collectDocs(f, docs));
	}
	
	public void createSuperLinksForDocs(DocFile subDoc, DocFile superDoc) {
		subDoc.setSuperRef(superDoc);
	}
	
	public void createSuperLinksForDocsRecursive(DocFile subDoc, DocFile superDoc) {
		if(!superDoc.getAllSuperRefs().contains(superDoc)) {
			subDoc.getAllSuperRefs().add(superDoc);
		}
		superDoc.getAllSuperRefs().stream().forEach(sup -> createSuperLinksForDocsRecursive(subDoc, sup));
	}

	public void createComplexExample(Folder rootFolder) {
		createFile("rootClazz", rootFolder);
		
		Folder sub1 = createSubFolder("sub1", rootFolder);
		Folder subsub1 = createSubFolder("subsub1", sub1);
		
		createFile("subsubClazz1", subsub1);
		createFile("subsubClazz2", subsub1);
		
		Folder sub2 = createSubFolder("sub2", rootFolder);
		
		createFile("subClazz1", sub2);
		createFile("subClazz2", sub2);
		createFile("subClazz3", sub2);
	}
	
	public Folder deleteElement(Folder root, String toBeDeleted) {
		if(root.getName().equals(toBeDeleted)) 
			EcoreUtil.delete(root);
		else {
			for(Folder f : root.getFolders().stream().collect(Collectors.toList())) 
				deleteElement(f, toBeDeleted);
			for(File f : root.getFiles().stream().collect(Collectors.toList())) 
				deleteElement(f, toBeDeleted);
		}
		return root;
	}
	
	private void deleteElement(File file, String toBeDeleted) {
		if(file.getName().equals(toBeDeleted)) 
			EcoreUtil.delete(file);
	}
}
