package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.*;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.UserDefinedRuntimeTGGAttrConstraintFactory;
import org.moflon.core.utilities.eMoflonEMFUtil;

import language.LanguagePackage;
import language.TGG;
import runtime.RuntimePackage;

public class Application {

	public static void main(String[] args) throws IOException {
				
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
		registerMetamodels(rs);
		
		Resource tggR = rs.getResource(URI.createFileURI("model/CDToDoc.tgg.xmi"), true);
		TGG tgg = (TGG) tggR.getContents().get(0);
		
		// create your resources 
		Resource s = rs.createResource(URI.createFileURI("src.xmi"));
		Resource t = rs.createResource(URI.createFileURI("trg.xmi"));
		Resource c = rs.createResource(URI.createFileURI("corr.xmi"));
		Resource p = rs.createResource(URI.createFileURI("protocol.xmi"));
		
		// load the resources containing your input 
		s.load(null);
		//t.load(null);
		//c.load(null);
		//p.load(null);			
						
		
		TGGRuntimeUtil tggRuntime = null;
		
		// choose your operation type (FWD, BWD, CC, MODELGEN etc.)
		
		tggRuntime = new FWD(tgg, s, c, t, p);
		tggRuntime.getCSPProvider().registerFactory(new UserDefinedRuntimeTGGAttrConstraintFactory());
		
		//tggRuntime = new FWD_ILP(tgg, s, c, t, p);
		
		//tggRuntime = new BWD(tgg, s, c, t, p);
		
		//tggRuntime = new BWD_ILP(tgg, s, c, t, p);
		
		//tggRuntime = new CC(tgg, s, c, t, p);
		
		//MODELGENStopCriterion stop = new MODELGENStopCriterion();
		//stop.setMaxSrcCount(1000);
		//tggRuntime = new MODELGEN(tgg, s, c, t, p, stop);
		
		
		Transformation transformation = new Transformation(rs, tggRuntime);						
		transformation.execute();
		
		// change your input models here if necessary
		
		tggRuntime.run();
		transformation.dispose();
 
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
		
		// TODO: Add mappings for all other required dependencies
		
	}
}

