package org.moflon.ibex.tgg.run;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.RuleInvocationUtil;
import org.emoflon.ibex.tgg.core.language.TGG;
import org.moflon.core.utilities.eMoflonEMFUtil;

import language.TGG;

public class Application {

	public static void main(String[] args) throws IOException {
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
		registerMetamodels(rs);
		
		Resource p = rs.createResource(URI.createFileURI("protocol.xmi"));
		Resource t = rs.createResource(URI.createFileURI("trg.xmi"));
		Resource c = rs.createResource(URI.createFileURI("corr.xmi"));
		
		Resource s = rs.createResource(URI.createFileURI("src.xmi"));
		s.load(null);		

		Resource tggR = rs.getResource(URI.createFileURI("model/CDToDoc.tgg.xmi"), true);
		TGG tgg = (TGG) tggR.getContents().get(0);

		RuleInvocationUtil transformer = new RuleInvocationUtil(tgg, s, c, t, p);
		FWD fwd = new FWD(rs, transformer);
		fwd.dispose();

		s.save(null);
		t.save(null);
		c.save(null);
		p.save(null);
	}
	
	private static void registerMetamodels(ResourceSet rs){
		// Add mapping for correspondence metamodel
		rs.getURIConverter().getURIMap().put(
				URI.createURI("platform:/resource/CDToDoc/model/CDToDoc.ecore"), 
				URI.createFileURI("model/CDToDoc.ecore"));
		
		// TODO: Add mappings for all other required dependencies
		rs.getURIConverter().getURIMap().put(
				URI.createURI("platform:/resource/CDToDoc/domains/CD.ecore"), 
				URI.createFileURI("domains/CD.ecore"));
		
		rs.getURIConverter().getURIMap().put(
				URI.createURI("platform:/resource/CDToDoc/domains/Doc.ecore"), 
				URI.createFileURI("domains/Doc.ecore"));	
	}
}
