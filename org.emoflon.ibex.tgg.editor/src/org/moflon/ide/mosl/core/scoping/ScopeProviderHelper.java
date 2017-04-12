package org.moflon.ide.mosl.core.scoping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.moflon.tgg.mosl.tgg.Schema;

public class ScopeProviderHelper <E extends EObject> {
	private Map<URI, E> existingScopingRoots;
	private Map<Class<EClass>, List<EClass>> oldCandidates;
	private ResourceSet resourceSet;
	
	public ScopeProviderHelper(ResourceSet resSet) {
		init();
		resourceSet = resSet;
	}
	
	public ScopeProviderHelper() {
		init();
		resourceSet = new ResourceSetImpl();
	}
	
	private void init(){
		existingScopingRoots = new HashMap<>();
		oldCandidates = new HashMap<>();
	}
	
	public ResourceSet getResourceSet(){
		return resourceSet;
	}
	
	private E getScopingObject(URI uri, Class<E> clazz){
		if(existingScopingRoots.containsKey(uri)){
			return existingScopingRoots.get(uri);
		}else{
			E scopingRoot = loadScopeObject(uri, clazz);
			existingScopingRoots.put(uri, scopingRoot);
			return scopingRoot;
		}
	}
	
	private <T extends EObject> T loadScopeObject(URI uri, Class<T> clazz){
		Resource res=resourceSet.getResource(uri, true);
		T scopingRoot = clazz.cast(res.getContents().get(0));
		return scopingRoot;
	}
	
	public IScope createScope(List<URI> uris, Class<E> clazz, Class<EClass> type){
		List<EClass> candidates=null;
		if(oldCandidates.containsKey(type)){
			candidates=oldCandidates.get(candidates);
		}
		else {		
			candidates = new ArrayList<>();
			
			for(URI uri : uris){
				E scopingObject=getScopingObject(uri, clazz);
				candidates.addAll(EcoreUtil2.getAllContentsOfType(scopingObject, type));
			}
		}
		return Scopes.scopeFor(candidates);
	}
	
	public Collection<EDataType> getAllEData(Schema schema){		
		return schema.getImports().stream().flatMap(im -> getObjectFromURIString(im.getName()).stream()).collect(Collectors.toList());
	}
	
	private Collection<EDataType> getObjectFromURIString(String uriString){
		EPackage ePackage = loadScopeObject(URI.createURI(uriString, true), EPackage.class);
		return EcoreUtil2.getAllContentsOfType(ePackage, EDataType.class);
	}
}
