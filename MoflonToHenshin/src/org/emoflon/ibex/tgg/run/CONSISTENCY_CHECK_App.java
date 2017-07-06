package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.*;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.MoflonToHenshinAttrCondDefLibrary;
import org.moflon.core.utilities.eMoflonEMFUtil;

import language.LanguagePackage;
import language.TGG;
import runtime.RuntimePackage;

public class CONSISTENCY_CHECK_App {

	public static void main(String[] args) throws IOException {
		//BasicConfigurator.configure();
				
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
		registerMetamodels(rs);
		
		Resource tggR = rs.getResource(URI.createFileURI("model/MoflonToHenshin.tgg.xmi"), true);
		TGG tgg = (TGG) tggR.getContents().get(0);
		
		// create your resources 
		Resource s = rs.createResource(URI.createFileURI("instances/testTGG.xmi"));
		Resource t = rs.createResource(URI.createFileURI("instances/trg_gen.xmi"));
		Resource c = rs.createResource(URI.createFileURI("instances/corr_gen.xmi"));
		Resource p = rs.createResource(URI.createFileURI("instances/protocol_gen.xmi"));
		
		// load the resources containing your input 
		s.load(null);
		//t.load(null);
		
		System.out.println("Starting CONSISTENCY_CHECK");
		long tic = System.currentTimeMillis();
		TGGRuntimeUtil tggRuntime = new CC(tgg, s, c, t, p);
		tggRuntime.getCSPProvider().registerFactory(new MoflonToHenshinAttrCondDefLibrary());
		
		MoflonToHenshinTransformation transformation = new MoflonToHenshinTransformation(rs, tggRuntime);						
		transformation.execute();
		
		tggRuntime.run();
		
		transformation.dispose();
		
		long toc = System.currentTimeMillis();
		System.out.println("Completed CONSISTENCY_CHECK in: " + (toc-tic) + " ms");
	 
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
		Resource corr = rs.getResource(URI.createFileURI("model/MoflonToHenshin.ecore"), true);
		EPackage pcorr = (EPackage) corr.getContents().get(0);
		Registry.INSTANCE.put(corr.getURI().toString(), corr);
		Registry.INSTANCE.put("platform:/resource/MoflonToHenshin/model/MoflonToHenshin.ecore", pcorr);
		Registry.INSTANCE.put("platform:/plugin/MoflonToHenshin/model/MoflonToHenshin.ecore", pcorr);
		
		// SourcePackage.eInstance.getName();
		// TargetPackage.eInstance.getName();
	}
}
