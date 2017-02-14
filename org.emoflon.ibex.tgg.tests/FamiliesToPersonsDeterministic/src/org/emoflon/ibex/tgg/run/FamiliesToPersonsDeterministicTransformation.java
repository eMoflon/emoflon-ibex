package org.emoflon.ibex.tgg.run;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.core.compiler.DemoclesDriver;
import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil;

import language.TGG;

public class FamiliesToPersonsDeterministicTransformation extends DemoclesDriver {

	public FamiliesToPersonsDeterministicTransformation(ResourceSet rs, TGGRuntimeUtil tggRuntime, TGG tgg) throws IOException {
		super(rs, tggRuntime, new File("./../").getCanonicalPath(), tgg, false);
	}
}
