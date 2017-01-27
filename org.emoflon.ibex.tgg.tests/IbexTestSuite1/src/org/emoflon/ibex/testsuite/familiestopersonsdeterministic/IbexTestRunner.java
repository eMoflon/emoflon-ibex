package org.emoflon.ibex.testsuite.familiestopersonsdeterministic;

import java.util.function.Consumer;

import org.benchmarx.BXToolForEMF;
import org.benchmarx.Configurator;
import org.benchmarx.families.core.FamiliesComparator;
import org.benchmarx.persons.core.PersonsComparator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.FWD;
import org.emoflon.ibex.tgg.operational.OperationMode;
import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.UserDefinedRuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.run.Transformation;
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
	
	public IbexTestRunner() {
		super(new FamiliesComparator(), new PersonsComparator());
	}

	@Override
	public void initiateSynchronisationDialogue() {
		ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
		registerMetamodels(rs);
		
		Resource tggR = rs.getResource(URI.createFileURI(MODEL_FOLDER + "/FamiliesToPersonsDeterministic.tgg.xmi"), true);
		TGG tgg = (TGG) tggR.getContents().get(0);
		
		// create your resources 
		s = rs.createResource(URI.createFileURI("src.xmi"));
		t = rs.createResource(URI.createFileURI("trg.xmi"));
		Resource c = rs.createResource(URI.createFileURI("corr.xmi"));
		Resource p = rs.createResource(URI.createFileURI("protocol.xmi"));
		
		FamilyRegister freg = FamiliesFactory.eINSTANCE.createFamilyRegister();
		s.getContents().add(freg);
		
		TGGRuntimeUtil tggRuntime = new TGGRuntimeUtil(tgg, s, c, t, p);
		tggRuntime.setMode(OperationMode.FWD);
		Transformation transformation = new Transformation(rs, tggRuntime);						
		transformation.execute();		
		tggRuntime.run();
		transformation.dispose();
	}

	@Override
	public void performAndPropagateTargetEdit(Consumer<PersonRegister> edit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performAndPropagateSourceEdit(Consumer<FamilyRegister> edit) {
		// TODO Auto-generated method stub
		
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


	private void registerMetamodels(ResourceSet rs){
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
}
