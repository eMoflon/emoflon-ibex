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

public class MODELGEN_App {

	public static void main(String[] args) throws IOException {
		//BasicConfigurator.configure();
				
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
		registerMetamodels(rs);
		
		Resource tggR = rs.getResource(URI.createFileURI("model/MoflonToHenshin.tgg.xmi"), true);
		TGG tgg = (TGG) tggR.getContents().get(0);
		
		// create your resources 
		Resource s = rs.createResource(URI.createFileURI("instances/src_gen.xmi"));
		Resource t = rs.createResource(URI.createFileURI("instances/trg_gen.xmi"));
		Resource c = rs.createResource(URI.createFileURI("instances/corr_gen.xmi"));
		Resource p = rs.createResource(URI.createFileURI("instances/protocol_gen.xmi"));
		
		// load the resources containing your input 
		
		System.out.println("Starting MODELGEN");
		long tic = System.currentTimeMillis();
		MODELGENStopCriterion stop = new MODELGENStopCriterion();
		stop.setMaxSrcCount(1000);
		TGGRuntimeUtil tggRuntime = new MODELGEN(tgg, s, c, t, p, stop);
		tggRuntime.getCSPProvider().registerFactory(new MoflonToHenshinAttrCondDefLibrary());
		
		MoflonToHenshinTransformation transformation = new MoflonToHenshinTransformation(rs, tggRuntime);						
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
		Resource corr = rs.getResource(URI.createFileURI("model/MoflonToHenshin.ecore"), true);
		EPackage pcorr = (EPackage) corr.getContents().get(0);
		Registry.INSTANCE.put(corr.getURI().toString(), corr);
		Registry.INSTANCE.put("platform:/resource/MoflonToHenshin/model/MoflonToHenshin.ecore", pcorr);
		Registry.INSTANCE.put("platform:/plugin/MoflonToHenshin/model/MoflonToHenshin.ecore", pcorr);
		
		// SourcePackage.eInstance.getName();
		// TargetPackage.eInstance.getName();
	}
}
