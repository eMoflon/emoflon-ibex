package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.eclipse.core.internal.runtime.Activator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.emoflon.ibex.tgg.operational.*;
import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.MoflonToHenshinAttrCondDefLibrary;
import org.emoflon.moflontohenshin.utils.ManipulationRulesFactory;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;

import language.LanguagePackage;
import language.TGG;
import runtime.RuntimePackage;

public class SYNCH_App {

	public static void main(String[] args) throws IOException {
		//BasicConfigurator.configure();
				
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
		String pluginID = registerMetamodels(rs);
		ManipulationRulesFactory.createInstances();
		Resource tggR = rs.getResource(URI.createFileURI("model/MoflonToHenshin.tgg.xmi"), true);
		rs = eMoflonEMFUtil.createDefaultResourceSet();
		registerMetamodels(rs);
		EcoreUtil.resolveAll(tggR);
		TGG tgg = (TGG) tggR.getContents().get(0);
		EcoreUtil.resolveAll(tgg);
		
		
		
		// create your resources 
		Resource s = rs.createResource(URI.createFileURI("instances/testTGG.xmi"));
		Resource t = rs.createResource(URI.createFileURI("instances/trg_gen.xmi"));
		Resource c = rs.createResource(URI.createFileURI("instances/corr_gen.xmi"));
		Resource p = rs.createResource(URI.createFileURI("instances/protocol_gen.xmi"));
		

		

		// load the resources containing your input 
		s.load(null);
		
		EcoreUtil.resolveAll(s);
		
		TGG source = (TGG) tggR.getContents().get(0);
		EClassifier testUnresolve = EClass.class.cast(source.getCorr().getEClassifiers().get(0)).getEReferences().get(0).getEType();
		EcoreUtil.resolveAll(testUnresolve);
		
		System.out.println("Starting SYNCH");
		long tic = System.currentTimeMillis();
		TGGRuntimeUtil tggRuntime = new TGGRuntimeUtil(tgg, s, c, t, p, pluginID);
		tggRuntime.setMode(OperationMode.FWD);
		tggRuntime.getCSPProvider().registerFactory(new MoflonToHenshinAttrCondDefLibrary());
		
		
		
		MoflonToHenshinTransformation transformation = new MoflonToHenshinTransformation(rs, tggRuntime);						
		transformation.execute();
		
		tggRuntime.run();
		
		transformation.dispose();
		
		long toc = System.currentTimeMillis();
		System.out.println("Completed SYNCH in: " + (toc-tic) + " ms");
	 
	 	c.save(null);
	 	p.save(null);
	 	t.save(null);
	}
		
	
	private static String registerMetamodels(ResourceSet rs){
		// Register internals
		LanguagePackage.eINSTANCE.getName();
		RuntimePackage.eINSTANCE.getName();
		HenshinPackage.eINSTANCE.getName();

		
		// Add mapping for correspondence metamodel
		Resource corr = rs.getResource(URI.createFileURI("model/MoflonToHenshin.ecore"), true);
		EPackage pcorr = (EPackage) corr.getContents().get(0);
		Registry.INSTANCE.put(corr.getURI().toString(), corr);
		Registry.INSTANCE.put("platform:/resource/MoflonToHenshin/model/MoflonToHenshin.ecore", pcorr);
		Registry.INSTANCE.put("platform:/plugin/MoflonToHenshin/model/MoflonToHenshin.ecore", pcorr);
//		EPackage henshinPackage = HenshinPackage.eINSTANCE;
//		Registry.INSTANCE.put(henshinPackage.eResource().getURI().toFileString(), henshinPackage);
		EPackage tggEPackage = LanguagePackage.eINSTANCE;
		Registry.INSTANCE.put("platform:/plugin/org.emoflon.ibex.tgg.core.language/model/Language.ecore", tggEPackage);		
		EcoreUtil.resolveAll(rs);
		return pcorr.getNsPrefix();
	}
}
