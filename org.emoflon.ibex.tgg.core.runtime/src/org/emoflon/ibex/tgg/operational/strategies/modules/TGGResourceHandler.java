package org.emoflon.ibex.tgg.operational.strategies.modules;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGEN;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.opt.BWD_OPT;
import org.emoflon.ibex.tgg.operational.strategies.opt.CC;
import org.emoflon.ibex.tgg.operational.strategies.opt.CO;
import org.emoflon.ibex.tgg.operational.strategies.opt.FWD_OPT;
import org.emoflon.ibex.tgg.operational.strategies.opt.FixingCopier;
import org.emoflon.ibex.tgg.operational.strategies.opt.MetamodelRelaxer;
import org.emoflon.ibex.tgg.operational.strategies.sync.INITIAL_BWD;
import org.emoflon.ibex.tgg.operational.strategies.sync.INITIAL_FWD;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

import language.TGG;
import language.impl.LanguagePackageImpl;
import runtime.RuntimeFactory;
import runtime.TempContainer;
import runtime.impl.RuntimePackageImpl;

public class TGGResourceHandler {
	
	private final static Logger logger = Logger.getLogger(OperationalStrategy.class);

	protected IbexOptions options;
	protected IbexExecutable executable;
	protected IRegistrationHelper registrationHelper;

	protected final URI base;
	protected ResourceSet rs;
	
	protected Resource source;
	protected Resource target;
	protected Resource corr;
	protected Resource protocol;
	protected Resource precedence;
	
	private MetamodelRelaxer relaxer = new MetamodelRelaxer();
	
	private boolean initialized = false;
	
	private Resource trash;
	
	public TGGResourceHandler() throws IOException {
		base = URI.createPlatformResourceURI("/", true);
	}
	
	public void setOptions(IbexOptions options) {
		this.options = options;
	}
	
	public void setExecutable(IbexExecutable executable) {
		this.executable = executable;
	}
	
	public void initialize() {
		if(initialized)
			return;
		
		try {
			createAndPrepareResourceSet();
			registerInternalMetamodels();
			registerUserMetamodels();
			loadTGG();
			loadRelevantModels();
			
			trash = createResource("instances/trash.xmi");
			trash.getContents().add(RuntimeFactory.eINSTANCE.createTempContainer());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		initialized = true;
	}
	
	public void saveRelevantModels() throws IOException {
		if(executable instanceof FWD_OPT) {
			// Unrelax the metamodel
			relaxer.unrelaxReferences(options.tgg().getTrg());
		}
		
		if(executable instanceof BWD_OPT) {
			// Unrelax the metamodel
			relaxer.unrelaxReferences(options.tgg().getSrc());
		}
		
		saveModels();
	}
	
	public void saveModels() throws IOException {
		if(executable == null || executable instanceof MODELGEN) {
			source.save(null);
			target.save(null);
			corr.save(null);
			protocol.save(null);
		}
		
		if(executable instanceof FWD_OPT) {
			protocol.save(null);

			// Remove adapters to avoid problems with notifications
			target.eAdapters().clear();
			target.getAllContents().forEachRemaining(o -> o.eAdapters().clear());
			corr.eAdapters().clear();
			corr.getAllContents().forEachRemaining(o -> o.eAdapters().clear());

			// Copy and fix the model in the process
			FixingCopier.fixAll(target, corr, "target");

			// Now save fixed models
			target.save(null);
			corr.save(null);
		}
		
		if(executable instanceof BWD_OPT) {
			protocol.save(null);

			// Remove adapters to avoid problems with notifications
			source.eAdapters().clear();
			source.getAllContents().forEachRemaining(o -> o.eAdapters().clear());
			corr.eAdapters().clear();
			corr.getAllContents().forEachRemaining(o -> o.eAdapters().clear());

			// Copy and fix the model in the process
			FixingCopier.fixAll(source, corr, "source");

			// Now save fixed models
			source.save(null);
			corr.save(null);
		}
		
		if(executable instanceof SYNC) {
			if(executable instanceof INITIAL_FWD) {
				target.save(null);
				corr.save(null);
				protocol.save(null);
			} else
			if(executable instanceof INITIAL_BWD) {
				source.save(null);
				corr.save(null);
				protocol.save(null);
			}
			else {
				source.save(null);
				target.save(null);
				corr.save(null);
				protocol.save(null);
			}
		}
		
		if(executable instanceof CC) {
			corr.save(null);
			protocol.save(null);
		}
		
		if(executable instanceof CO) {
			protocol.save(null);
		}
		
		if(executable instanceof INTEGRATE) {
			source.save(null);
			target.save(null);
			corr.save(null);
			protocol.save(null);
			precedence.save(null);
		}
	}
	
	public void loadRelevantModels() throws IOException {
		if(executable instanceof FWD_OPT) {
			relaxer.relaxReferences(options.tgg().getTrg());
		}
		
		if(executable instanceof BWD_OPT) {
			relaxer.relaxReferences(options.tgg().getSrc());
		}
		
		loadModels();
		EcoreUtil.resolveAll(rs);
	}

	public void loadModels() throws IOException {
		if(executable == null || executable instanceof MODELGEN) {
			source = createResource(options.projectPath() + "/instances/src.xmi");
			target = createResource(options.projectPath() + "/instances/trg.xmi");
			corr = createResource(options.projectPath() + "/instances/corr.xmi");
			protocol = createResource(options.projectPath() + "/instances/protocol.xmi");
			return;
		}
		
		if(executable instanceof FWD_OPT) {
			source = loadResource(options.projectPath() + "/instances/src.xmi");
			target = createResource(options.projectPath() + "/instances/trg.xmi");
			corr = createResource(options.projectPath() + "/instances/corr.xmi");
			protocol = createResource(options.projectPath() + "/instances/protocol.xmi");
		}
		
		if(executable instanceof BWD_OPT) {
			source = createResource(options.projectPath() + "/instances/src.xmi");
			target = loadResource(options.projectPath() + "/instances/trg.xmi");
			corr = createResource(options.projectPath() + "/instances/corr.xmi");
			protocol = createResource(options.projectPath() + "/instances/protocol.xmi");
		}
		
		if(executable instanceof SYNC) {
			if(executable instanceof INITIAL_FWD) {
				source = loadResource(options.projectPath() + "/instances/src.xmi");
				target = createResource(options.projectPath() + "/instances/trg.xmi");
				corr = createResource(options.projectPath() + "/instances/corr.xmi");
				protocol = createResource(options.projectPath() + "/instances/protocol.xmi");
			} else
			if(executable instanceof INITIAL_BWD) {
				source = createResource(options.projectPath() + "/instances/src.xmi");
				target = loadResource(options.projectPath() + "/instances/trg.xmi");
				corr = createResource(options.projectPath() + "/instances/corr.xmi");
				protocol = createResource(options.projectPath() + "/instances/protocol.xmi");
			}
			else {
				source = loadResource(options.projectPath() + "/instances/src.xmi");
				target = loadResource(options.projectPath() + "/instances/trg.xmi");
				corr = loadResource(options.projectPath() + "/instances/corr.xmi");
				protocol = loadResource(options.projectPath() + "/instances/protocol.xmi");
			}
		}
		
		if(executable instanceof CC) {
			source = loadResource(options.projectPath() + "/instances/src.xmi");
			target = loadResource(options.projectPath() + "/instances/trg.xmi");
			corr = createResource(options.projectPath() + "/instances/corr.xmi");
			protocol = createResource(options.projectPath() + "/instances/protocol.xmi");
		}
		
		if(executable instanceof CO) {
			source = loadResource(options.projectPath() + "/instances/src.xmi");
			target = loadResource(options.projectPath() + "/instances/trg.xmi");
			corr = loadResource(options.projectPath() + "/instances/corr.xmi");
			protocol = createResource(options.projectPath() + "/instances/protocol.xmi");
		}
		
		if(executable instanceof INTEGRATE) {
			source = loadResource(options.projectPath() + "/instances/src.xmi");
			target = loadResource(options.projectPath() + "/instances/trg.xmi");
			corr = loadResource(options.projectPath() + "/instances/corr.xmi");
			protocol = loadResource(options.projectPath() + "/instances/protocol.xmi");
			precedence = createResource(options.projectPath() + "/instances/epg.xmi");
		}

		EcoreUtil.resolveAll(rs);
	}
	
	protected void createAndPrepareResourceSet() {
		rs = options.getBlackInterpreter().createAndPrepareResourceSet(options.workspacePath());
	}

	public ResourceSet getResourceSet() {
		return rs;
	}

	public Resource getSourceResource() {
		return source;
	}

	public Resource getTargetResource() {
		return target;
	}

	public Resource getCorrResource() {
		return corr;
	}

	public Resource getProtocolResource() {
		return protocol;
	}
	
	public EPackage loadAndRegisterMetamodel(String workspaceRelativePath) throws IOException {
		String uri = URI.createURI(workspaceRelativePath).toString();
		if(rs.getPackageRegistry().containsKey(uri)) {
			return rs.getPackageRegistry().getEPackage(uri);
		}
		Resource res = loadResource(workspaceRelativePath);
		EPackage pack = (EPackage) res.getContents().get(0);
		pack = (EPackage) rs.getPackageRegistry().getOrDefault(res.getURI().toString(), pack);
		rs.getPackageRegistry().put(res.getURI().toString(), pack);
		rs.getPackageRegistry().put(pack.getNsURI(), pack);
		rs.getResources().remove(res);
		return pack;
	}

	public EPackage loadAndRegisterCorrMetamodel(String workspaceRelativePath) throws IOException {
		EPackage pack = loadAndRegisterMetamodel(workspaceRelativePath);
		options.setCorrMetamodel(pack);
		return pack;
	}

	public Resource loadResource(String workspaceRelativePath) throws IOException {
		Resource res = createResource(workspaceRelativePath);
		res.load(null);
		EcoreUtil.resolveAll(res);
		return res;
	}

	public Resource createResource(String workspaceRelativePath) {
		URI uri = URI.createURI(workspaceRelativePath);
		Resource res = rs.createResource(uri.resolve(base), ContentHandler.UNSPECIFIED_CONTENT_TYPE);
		return res;
	}

	private void loadTGG() throws IOException {
		Resource res = loadTGGResource();
		Resource flattenedRes = loadFlattenedTGGResource();

		EcoreUtil.resolveAll(rs);
		EcoreUtil.UnresolvedProxyCrossReferencer//
				.find(rs)//
				.forEach((eob, settings) -> logger.error("Problems resolving: " + eob));

		options.tgg((TGG) res.getContents().get(0));
		options.flattenedTgg((TGG) flattenedRes.getContents().get(0));

		RuntimeTGGAttrConstraintProvider runtimeConstraintProvider = new RuntimeTGGAttrConstraintProvider(
				options.tgg().getAttributeConstraintDefinitionLibrary());
		runtimeConstraintProvider.registerFactory(options.userDefinedConstraints());
		options.setConstraintProvider(runtimeConstraintProvider);

		rs.getResources().remove(res);
		rs.getResources().remove(flattenedRes);
	}
	
	protected Resource loadFlattenedTGGResource() throws IOException {
		return loadResource(options.projectPath() + "/model/" + options.projectName() + "_flattened.tgg.xmi");
	}

	protected Resource loadTGGResource() throws IOException {
		return loadResource(options.projectPath() + "/model/" + options.projectName() + ".tgg.xmi");
	}
	
	private void registerInternalMetamodels() {
		// Register internals for Ibex
		LanguagePackageImpl.init();
		RuntimePackageImpl.init();
	}
	
	protected void registerUserMetamodels() throws IOException {
		options.registrationHelper().registerMetamodels(rs, options.getExecutable());
		
		// Register correspondence metamodel last
		loadAndRegisterCorrMetamodel(options.projectPath() + "/model/" + options.projectName() + ".ecore");
	}
	
	public void addToTrash(EObject o) {
		TempContainer c = (TempContainer) trash.getContents().get(0);
		c.getObjects().add(EcoreUtil.getRootContainer(o));
	}

	public Resource getPrecedenceResource() {
		return precedence;
	}
}
