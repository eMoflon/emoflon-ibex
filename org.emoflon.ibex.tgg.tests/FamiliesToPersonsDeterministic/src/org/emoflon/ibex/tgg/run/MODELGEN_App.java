package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.MODELGEN;
import org.emoflon.ibex.tgg.operational.MODELGENStopCriterion;
import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.UserDefinedRuntimeTGGAttrConstraintFactory;
import org.gervarro.democles.specification.emf.SpecificationPackage;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypePackage;
import org.gervarro.democles.specification.emf.constraint.relational.RelationalConstraintPackage;
import org.moflon.core.utilities.eMoflonEMFUtil;

import language.TGG;
import language.impl.LanguagePackageImpl;
import runtime.impl.RuntimePackageImpl;

public class MODELGEN_App {

	public static void main(String[] args) throws IOException {
		//BasicConfigurator.configure();
				
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
		registerMetamodels(rs);
		
		Resource tggR = rs.getResource(URI.createFileURI("model/FamiliesToPersonsDeterministic.tgg.xmi"), true);
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
		tggRuntime.getCSPProvider().registerFactory(new UserDefinedRuntimeTGGAttrConstraintFactory());
		
		FamiliesToPersonsDeterministicTransformation transformation = new FamiliesToPersonsDeterministicTransformation(rs, tggRuntime, tgg);						
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
		LanguagePackageImpl.init();
		RuntimePackageImpl.init();
		SpecificationPackage.init();
		RelationalConstraintPackage.init();
		EMFTypePackage.init();

		
		// Add mapping for correspondence metamodel
		Resource corr = rs.getResource(URI.createFileURI("model/FamiliesToPersonsDeterministic.ecore"), true);
		EPackage pcorr = (EPackage) corr.getContents().get(0);
		Registry.INSTANCE.put(corr.getURI().toString(), corr);
		Registry.INSTANCE.put("platform:/resource/FamiliesToPersonsDeterministic/model/FamiliesToPersonsDeterministic.ecore", pcorr);
		Registry.INSTANCE.put("platform:/plugin/FamiliesToPersonsDeterministic/model/FamiliesToPersonsDeterministic.ecore", pcorr);
		
		// SourcePackage.eInstance.getName();
		// TargetPackage.eInstance.getName();
	}
}
