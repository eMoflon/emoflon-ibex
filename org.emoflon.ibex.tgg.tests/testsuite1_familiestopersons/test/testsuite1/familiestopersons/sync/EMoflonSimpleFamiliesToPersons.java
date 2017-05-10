package testsuite1.familiestopersons.sync;

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

import SimpleFamilies.FamilyRegister;
import SimplePersons.PersonRegister;

/**
 * This class implements the bx tool interface for eMoflon::Ibex, which is
 * structural-delta-based and corr-based.
 * 
 * 
 * @author anthony anjorin
 */
public class EMoflonSimpleFamiliesToPersons extends BXToolForEMF<FamilyRegister, PersonRegister, Decisions>   {
	
	private static final String RESULTPATH = "results/emoflon";
	
	public EMoflonSimpleFamiliesToPersons() {
		super(new FamiliesComparator(), new PersonsComparator());
	}
	
	@Override
	public String getName() {
		return "eMoflon::Ibex";
	}
	
	@Override
	public void initiateSynchronisationDialogue() {
		// TODO
	}

	@Override
	public void performAndPropagateTargetEdit(Consumer<PersonRegister> edit) {
		// TODO
	}

	@Override
	public void performAndPropagateSourceEdit(Consumer<FamilyRegister> edit) {
		// TODO
	}

	@Override
	public FamilyRegister getSourceModel() {
		// TODO
		return null;
	} 

	@Override
	public PersonRegister getTargetModel() {
		// TODO
		return null;
	}

	@Override
	public void setConfigurator(Configurator<Decisions> configurator) {
		// TODO
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
}
