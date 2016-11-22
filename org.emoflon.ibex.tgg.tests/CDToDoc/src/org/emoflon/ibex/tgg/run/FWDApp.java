package org.emoflon.ibex.tgg.run;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.FWD;
import org.emoflon.ibex.tgg.operational.FWD_ILP;
import org.emoflon.ibex.tgg.operational.OperationMode;
import org.emoflon.ibex.tgg.operational.OperationStrategy;
import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil;
import org.moflon.core.utilities.eMoflonEMFUtil;

import language.LanguagePackage;
import language.TGG;

public class FWDApp {

	public static void main(String[] args) throws IOException {
		//BasicConfigurator.configure();
				
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
		registerMetamodels(rs);
		
		Resource tggR = rs.getResource(URI.createFileURI("model/CDToDoc.tgg.xmi"), true);
		TGG tgg = (TGG) tggR.getContents().get(0);
		
		// create your resources 
		Resource s = rs.createResource(URI.createFileURI("src_gen.xmi"));
		Resource t = rs.createResource(URI.createFileURI("trg.xmi"));
		Resource c = rs.createResource(URI.createFileURI("corr.xmi"));
		Resource p = rs.createResource(URI.createFileURI("protocol.xmi"));
		
		// load the resources containing your input 
		s.load(null);	
		
		System.out.println("Starting FWD");
		long tic = System.currentTimeMillis();
		TGGRuntimeUtil transformer = new FWD_ILP(tgg, s, c, t, p);
		
		Transformation trafo = new Transformation(rs, transformer);
		
		trafo.execute();
		trafo.dispose();
		
		transformer.finalize();
		long toc = System.currentTimeMillis();
		System.out.println("Completed FWD in: " + (toc-tic) + " ms");
 
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
