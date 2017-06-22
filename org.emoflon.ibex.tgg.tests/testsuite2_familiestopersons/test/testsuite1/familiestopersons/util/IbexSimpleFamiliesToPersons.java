package testsuite1.familiestopersons.util;

import java.io.IOException;
import java.util.function.Consumer;

import org.benchmarx.Configurator;
import org.benchmarx.emf.BXToolForEMF;
import org.benchmarx.families.core.FamiliesComparator;
import org.benchmarx.persons.core.PersonsComparator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
import org.emoflon.ibex.tgg.run.SYNC_App;
import org.emoflon.ibex.tgg.runtime.engine.DemoclesEngine;

import SimpleFamilies.FamilyRegister;
import SimpleFamilies.SimpleFamiliesFactory;
import SimplePersons.PersonRegister;

/**
 * This class implements the bx tool interface for eMoflon::Ibex, which is
 * structural-delta-based and corr-based.
 * 
 * 
 * @author anthony anjorin
 */
public class IbexSimpleFamiliesToPersons extends BXToolForEMF<FamilyRegister, PersonRegister, Decisions>   {
	
	private static final String RESULTPATH = "results/ibex";
	
	private SYNC synchroniser;
	
	public IbexSimpleFamiliesToPersons() {
		super(new FamiliesComparator(), new PersonsComparator());
	}
	
	@Override
	public String getName() {
		return "eMoflon::Ibex";
	}
	
	@Override
	public void initiateSynchronisationDialogue() {
		try {
			synchroniser = new SYNC_App("testsuite2_familiestopersons", "./../", true);
			FamilyRegister reg = SimpleFamiliesFactory.eINSTANCE.createFamilyRegister();
			synchroniser.getSourceResource().getContents().add(reg);
			synchroniser.forward();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void performAndPropagateTargetEdit(Consumer<PersonRegister> edit) {
		edit.accept(getPersonRegister());
		try {
			synchroniser.backward();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private PersonRegister getPersonRegister() {
		return (PersonRegister) synchroniser.getTargetResource().getContents().get(0);
	}

	@Override
	public void performAndPropagateSourceEdit(Consumer<FamilyRegister> edit) {
		edit.accept(getFamilyRegister());
		try {
			synchroniser.forward();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private FamilyRegister getFamilyRegister() {
		return (FamilyRegister) synchroniser.getSourceResource().getContents().get(0);
	}

	@Override
	public FamilyRegister getSourceModel() {
		return getFamilyRegister();
	} 

	@Override
	public PersonRegister getTargetModel() {
		return getPersonRegister();
	}

	@Override
	public void setConfigurator(Configurator<Decisions> configurator) {
		// No configuration for now
	}
	
	@Override
	public void saveModels(String name) {
		ResourceSet set = new ResourceSetImpl();
		set.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		URI srcURI = URI.createFileURI(RESULTPATH + "/" + name + "Family.xmi");
		URI trgURI = URI.createFileURI(RESULTPATH + "/" + name + "Person.xmi");
		Resource resSource = set.createResource(srcURI);
		Resource resTarget = set.createResource(trgURI);
		
		EObject colSource = EcoreUtil.copy(getSourceModel());
		EObject colTarget = EcoreUtil.copy(getTargetModel());
		
		resSource.getContents().add(colSource);
		resTarget.getContents().add(colTarget);
		
		try {
			resSource.save(null);
			resTarget.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void performIdleTargetEdit(Consumer<PersonRegister> edit) {
		performAndPropagateTargetEdit(edit);
	}

	@Override
	public void performIdleSourceEdit(Consumer<FamilyRegister> edit) {
		performAndPropagateSourceEdit(edit);
	}
	
	@Override
	public void terminateSynchronisationDialogue() {
		try {
			synchroniser.saveModels();
			saveModels("results");
			synchroniser.terminate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
