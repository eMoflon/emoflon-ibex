package org.emoflon.ibex.tgg.operational.strategies.sync_opt;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.operational.PatternMatchingEngine;

public class MetamodelUtil {

	public static void simplifyMetamodel(String relativePath, PatternMatchingEngine engine) throws IOException {
		
		Resource res = loadResource(relativePath, engine);
	}
	
	protected static Resource loadResource(String workspaceRelativePath, PatternMatchingEngine engine) throws IOException {
		Resource res = createResource(workspaceRelativePath, engine);
		res.load(null);
		EcoreUtil.resolveAll(res);
		return res;
	}

	protected static Resource createResource(String workspaceRelativePath, PatternMatchingEngine engine) {
		ResourceSet rs = engine.createAndPrepareResourceSet(workspaceRelativePath);
		URI uri = URI.createURI(workspaceRelativePath);
		URI base = URI.createPlatformResourceURI("/", true);
		Resource res = rs.createResource(uri.resolve(base), ContentHandler.UNSPECIFIED_CONTENT_TYPE);
		return res;
	}
}
