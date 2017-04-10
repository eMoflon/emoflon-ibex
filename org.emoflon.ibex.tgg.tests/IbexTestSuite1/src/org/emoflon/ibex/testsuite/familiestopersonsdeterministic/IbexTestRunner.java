package org.emoflon.ibex.testsuite.familiestopersonsdeterministic;

import java.util.function.Consumer;

import org.benchmarx.BXToolForEMF;
import org.benchmarx.Configurator;
import org.benchmarx.families.core.FamilyComparator;
import org.benchmarx.persons.core.PersonComparator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.OperationMode;
import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil;
import org.emoflon.ibex.tgg.run.FamiliesToPersonsDeterministicTransformation;
import org.moflon.core.utilities.eMoflonEMFUtil;

import Families.FamiliesFactory;
import Families.FamiliesPackage;
import Families.FamilyRegister;
import Persons.PersonRegister;
import Persons.PersonsPackage;
import language.LanguagePackage;
import language.TGG;
import runtime.RuntimePackage;

public class IbexTestRunner extends BXToolForEMF<FamilyRegister, PersonRegister, NoDecisions> {

	private static final String MODEL_FOLDER = "../FamiliesToPersonsDeterministic/model";
	private Resource s;
	private Resource t;
	private TGGRuntimeUtil tggRuntime;
	private FamiliesToPersonsDeterministicTransformation transformation;
	
	public IbexTestRunner() {
		super(new FamilyComparator(), new PersonComparator());
		registerMetamodels();
	}

	@Override
	public void initiateSynchronisationDialogue() {
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
		Resource tggR = rs.getResource(URI.createFileURI(MODEL_FOLDER + "/FamiliesToPersonsDeterministic.tgg.xmi"), true);
		TGG tgg = (TGG) tggR.getContents().get(0);
		
		// create your resources 
		s = rs.createResource(URI.createFileURI("src.xmi"));
		t = rs.createResource(URI.createFileURI("trg.xmi"));
		Resource c = rs.createResource(URI.createFileURI("corr.xmi"));
		Resource p = rs.createResource(URI.createFileURI("protocol.xmi"));
		
		FamilyRegister freg = FamiliesFactory.eINSTANCE.createFamilyRegister();
		s.getContents().add(freg);
		
		tggRuntime = new TGGRuntimeUtil(tgg, s, c, t, p);
		tggRuntime.setMode(OperationMode.FWD);
		transformation = new FamiliesToPersonsDeterministicTransformation(rs, tggRuntime);	
		transformation.execute();
		tggRuntime.run();
	}

	@Override
	public void performAndPropagateTargetEdit(Consumer<PersonRegister> edit) {
		tggRuntime.setMode(OperationMode.BWD);
		edit.accept((PersonRegister) t.getContents().get(0));
		tggRuntime.run();
	}

	@Override
	public void performAndPropagateSourceEdit(Consumer<FamilyRegister> edit) {
		tggRuntime.setMode(OperationMode.FWD);
		edit.accept((FamilyRegister) s.getContents().get(0));
		tggRuntime.run();
	}

	@Override
	public void setConfigurator(Configurator<NoDecisions> configurator) {		
	}

	@Override
	public FamilyRegister getSourceModel() {
		return (FamilyRegister) s.getContents().get(0);
	}

	@Override
	public PersonRegister getTargetModel() {
		return (PersonRegister) t.getContents().get(0);
	}


	private void registerMetamodels(){
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();

		// Register internals
		LanguagePackage.eINSTANCE.getName();
		RuntimePackage.eINSTANCE.getName();
		
		// Add mapping for correspondence metamodel
		Resource corr = rs.getResource(URI.createFileURI(MODEL_FOLDER + "/FamiliesToPersonsDeterministic.ecore"), true);
		EPackage pcorr = (EPackage) corr.getContents().get(0);
		Registry.INSTANCE.put(corr.getURI().toString(), corr);
		Registry.INSTANCE.put("platform:/resource/FamiliesToPersonsDeterministic/model/FamiliesToPersonsDeterministic.ecore", pcorr);
		Registry.INSTANCE.put("platform:/plugin/FamiliesToPersonsDeterministic/model/FamiliesToPersonsDeterministic.ecore", pcorr);
		
		// Add mappings for all other required dependencies
		FamiliesPackage.eINSTANCE.getName();
		PersonsPackage.eINSTANCE.getName();
	}

	@Override
	public void performIdleTargetEdit(Consumer<PersonRegister> edit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performIdleSourceEdit(Consumer<FamilyRegister> edit) {
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
