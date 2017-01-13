package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.*;
import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.UserDefinedRuntimeTGGAttrConstraintFactory;
import org.moflon.core.utilities.eMoflonEMFUtil;

import language.LanguagePackage;
import language.TGG;
import runtime.RuntimePackage;

public class MODELGEN_App {

	public static void main(String[] args) throws IOException {
			//BasicConfigurator.configure();
					
			ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
			registerMetamodels(rs);
			
			Resource tggR = rs.getResource(URI.createFileURI("model/CDToDoc.tgg.xmi"), true);
			TGG tgg = (TGG) tggR.getContents().get(0);
			
			// create your resources 
			Resource s = rs.createResource(URI.createFileURI("instances/src_gen.xmi"));
			Resource t = rs.createResource(URI.createFileURI("instances/trg_gen.xmi"));
			Resource c = rs.createResource(URI.createFileURI("instances/corr_gen.xmi"));
			Resource p = rs.createResource(URI.createFileURI("instances/protocol_gen.xmi"));
			
			// load the resources containing your input 
			t.save(null);
			
			System.out.println("Starting MODELGEN");
			long tic = System.currentTimeMillis();
			MODELGENStopCriterion stop = new MODELGENStopCriterion();
			stop.setMaxSrcCount(1000);
			TGGRuntimeUtil tggRuntime = new MODELGEN(tgg, s, c, t, p, stop);
			tggRuntime.getCSPProvider().registerFactory(new UserDefinedRuntimeTGGAttrConstraintFactory());
			
			Transformation transformation = new Transformation(rs, tggRuntime);						
			transformation.execute();
			
			tggRuntime.run();
			
			transformation.dispose();
			
			long toc = System.currentTimeMillis();
			System.out.println("Completed MODELGEN in: " + (toc-tic) + " ms");
	 
	 	s.save(null);
	 	t.save(null);
	 	c.save(null);
	 	p.save(null);
	}
		
	
	private static void registerMetamodels(ResourceSet rs){
		 // Register internals
		 LanguagePackage.eINSTANCE.getName();
		 RuntimePackage.eINSTANCE.getName();
		 
		 
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
