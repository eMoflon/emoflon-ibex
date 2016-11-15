package org.moflon.ibex.tgg.run;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.operational.RuleInvocationUtil;
import org.moflon.core.utilities.eMoflonEMFUtil;

import language.LanguagePackage;
import language.TGG;

public class Application {

	public static void main(String[] args) throws IOException {

		LanguagePackage.eINSTANCE.getName();
		
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
		
		Resource CD = rs.getResource(URI.createFileURI("domains/CD.ecore"), true);
		CD.setURI(URI.createURI(((EPackage)CD.getContents().get(0)).getNsURI()));
		
		Resource Doc = rs.getResource(URI.createFileURI("domains/Doc.ecore"), true);
		Doc.setURI(URI.createURI(((EPackage)Doc.getContents().get(0)).getNsURI()));

		Resource CorrMM = rs.getResource(URI.createFileURI("model/CDToDoc.ecore"), true);
		CorrMM.setURI(URI.createURI(((EPackage)CorrMM.getContents().get(0)).getNsURI()));
		
		Resource p = rs.createResource(URI.createFileURI("protocol.xmi"));

		Resource s = rs.createResource(URI.createFileURI("src.xmi"));
		s.load(null);

		Resource t = rs.createResource(URI.createFileURI("trg.xmi"));
		Resource c = rs.createResource(URI.createFileURI("corr.xmi"));
		

		Resource tggR = rs.getResource(URI.createFileURI("model/CDToDoc.tgg.xmi"), true);
		
		TGG tgg = (TGG) tggR.getContents().get(0);
		register(tgg.getSrc());
		register(tgg.getTrg());
		register(tgg.getCorr());


		RuleInvocationUtil transformer = new RuleInvocationUtil(tgg, s, c, t, p);
		FWD fwd = new FWD(rs, transformer);

		fwd.dispose();

		s.save(null);
		t.save(null);
		c.save(null);
		p.save(null);

	}
	
	private static void register(Collection<EPackage> packages){
		packages.forEach(p -> register(p));
	}
	
	private static void register(EPackage p){
		EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
	}

}
