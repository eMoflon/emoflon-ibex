package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.FWDTGGRuntimeUtil;
import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil;
import org.moflon.core.utilities.eMoflonEMFUtil;

import language.LanguagePackage;
import language.TGG;

public class Application {

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();
		
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
		registerMetamodels(rs);
		
		Resource p = rs.createResource(URI.createFileURI("protocol.xmi"));
		Resource t = rs.createResource(URI.createFileURI("trg.xmi"));
		Resource c = rs.createResource(URI.createFileURI("corr.xmi"));
		
		Resource s = rs.createResource(URI.createFileURI("src.xmi"));
		s.load(null);		

		Resource tggR = rs.getResource(URI.createFileURI("model/CDToDoc.tgg.xmi"), true);
		TGG tgg = (TGG) tggR.getContents().get(0);

		TGGRuntimeUtil transformer = new FWDTGGRuntimeUtil(tgg, s, c, t, p);
		Transformation fwd = new Transformation(rs, transformer);
		fwd.execute();
		fwd.dispose();

		s.save(null);
		t.save(null);
		c.save(null);
		p.save(null);
	}
	
	private static void registerMetamodels(ResourceSet rs){
		// Register internals
		LanguagePackage.eINSTANCE.getName();
		
		// Add mapping for correspondence metamodel
		Resource corr = rs.getResource(URI.createFileURI("model/CDToDoc.ecore"), true);
		EPackage pcorr = (EPackage) corr.getContents().get(0);
		Registry.INSTANCE.put(corr.getURI().toString(), corr);
		Registry.INSTANCE.put("platform:/resource/CDToDoc/model/CDToDoc.ecore", pcorr);
		Registry.INSTANCE.put("platform:/plugin/CDToDoc/model/CDToDoc.ecore", pcorr);
		
		// Add mappings for all other required dependencies
		Resource cd = rs.getResource(URI.createFileURI("domains/CD.ecore"), true);
		EPackage pcd = (EPackage) cd.getContents().get(0);
		Registry.INSTANCE.put(cd.getURI().toString(), pcd);
		Registry.INSTANCE.put("platform:/resource/CDToDoc/domains/CD.ecore", pcd);
		Registry.INSTANCE.put("platform:/plugin/CD/model/CD.ecore", pcd);
		
		Resource doc = rs.getResource(URI.createFileURI("domains/Doc.ecore"), true);
		EPackage pdoc = (EPackage) doc.getContents().get(0);
		Registry.INSTANCE.put(doc.getURI().toString(), pdoc);
		Registry.INSTANCE.put("platform:/resource/CDToDoc/domains/Doc.ecore", pdoc);	
		Registry.INSTANCE.put("platform:/plugin/Doc/model/Doc.ecore", pdoc);	
	}
}
