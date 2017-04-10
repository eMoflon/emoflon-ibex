package org.emoflon.ibex.testsuite.cdtodoc;

import java.util.function.Consumer;

import org.benchmarx.BXToolForEMF;
import org.benchmarx.Configurator;
import org.benchmarx.cd.core.CDComparator;
import org.benchmarx.doc.core.DocComparator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.OperationMode;
import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil;
import org.emoflon.ibex.tgg.run.CDToDocTransformation;
import org.moflon.core.utilities.eMoflonEMFUtil;

import CD.CDFactory;
import CD.CDPackage;
import Doc.DocFactory;
import Doc.DocPackage;
import Doc.Folder;
import language.LanguagePackage;
import language.TGG;
import runtime.RuntimePackage;

public class IbexTestRunner extends BXToolForEMF<CD.Package, Folder, NoDecisions> {

	private static final String MODEL_FOLDER = "../CDToDoc/model";
	private Resource s;
	private Resource t;
	private TGGRuntimeUtil tggRuntime;
	private CDToDocTransformation transformation;
	
	public IbexTestRunner() {
		super(new CDComparator(), new DocComparator());
		registerMetamodels();
	}

	@Override
	public void initiateSynchronisationDialogue() {
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
		Resource tggR = rs.getResource(URI.createFileURI(MODEL_FOLDER + "/CDToDoc.tgg.xmi"), true);
		TGG tgg = (TGG) tggR.getContents().get(0);
		
		// create your resources 
		s = rs.createResource(URI.createFileURI("src.xmi"));
		t = rs.createResource(URI.createFileURI("trg.xmi"));
		Resource c = rs.createResource(URI.createFileURI("corr.xmi"));
		Resource p = rs.createResource(URI.createFileURI("protocol.xmi"));
		
		Folder f = DocFactory.eINSTANCE.createFolder();
		f.setName("root");
		t.getContents().add(f);
		
		tggRuntime = new TGGRuntimeUtil(tgg, s, c, t, p);
		tggRuntime.setMode(OperationMode.BWD);
		transformation = new CDToDocTransformation(rs, tggRuntime);	
		transformation.execute();
		tggRuntime.run();
	}

	@Override
	public void performAndPropagateTargetEdit(Consumer<Folder> edit) {
		tggRuntime.setMode(OperationMode.BWD);
		edit.accept((Folder) t.getContents().get(0));
		tggRuntime.run();
	}

	@Override
	public void performAndPropagateSourceEdit(Consumer<CD.Package> edit) {
		tggRuntime.setMode(OperationMode.FWD);
		edit.accept((CD.Package) s.getContents().get(0));
		tggRuntime.run();
	}

	@Override
	public void setConfigurator(Configurator<NoDecisions> configurator) {		
	}

	@Override
	public CD.Package getSourceModel() {
		return (CD.Package) s.getContents().get(0);
	}

	@Override
	public Folder getTargetModel() {
		return (Folder) t.getContents().get(0);
	}


	private void registerMetamodels(){
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();

		// Register internals
		LanguagePackage.eINSTANCE.getName();
		RuntimePackage.eINSTANCE.getName();
		
		// Add mapping for correspondence metamodel
		Resource corr = rs.getResource(URI.createFileURI(MODEL_FOLDER + "/CDToDoc.ecore"), true);
		EPackage pcorr = (EPackage) corr.getContents().get(0);
		Registry.INSTANCE.put(corr.getURI().toString(), corr);
		Registry.INSTANCE.put("platform:/resource/CDToDoc/model/CDToDoc.ecore", pcorr);
		Registry.INSTANCE.put("platform:/plugin/CDToDoc/model/CDToDoc.ecore", pcorr);
		
		// Add mappings for all other required dependencies
		CDPackage.eINSTANCE.getName();
		DocPackage.eINSTANCE.getName();
	}

	@Override
	public void performIdleTargetEdit(Consumer<Folder> edit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performIdleSourceEdit(Consumer<CD.Package> edit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveModels(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disposeSynchronisationDialogue() {
		transformation.dispose();
	}
}
