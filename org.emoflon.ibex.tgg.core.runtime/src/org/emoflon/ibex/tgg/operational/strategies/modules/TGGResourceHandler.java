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
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

import language.TGG;
import language.impl.LanguagePackageImpl;
import runtime.RuntimeFactory;
import runtime.TempContainer;
import runtime.impl.RuntimePackageImpl;

public class TGGResourceHandler {
	
	private final static Logger logger = Logger.getLogger(OperationalStrategy.class);

	private IbexOptions options;
	private IbexExecutable executable;
	private IRegistrationHelper registrationHelper;

	private final URI base;
	protected ResourceSet rs;
	
	protected Resource source;
	protected Resource target;
	protected Resource corr;
	protected Resource protocol;
	protected Resource precedence;
	
	private boolean initialized = false;
	
	private Resource trash;
	
	public TGGResourceHandler(IbexOptions options) throws IOException {
		this.options = options;
		base = URI.createPlatformResourceURI("/", true);
	}
	
	public void setExecutable(IbexExecutable executable) {
		this.executable = executable;
	}
	
	public void initialize() {
		if(initialized)
			return;
		
		try {
			registerInternalMetamodels();
			createAndPrepareResourceSet();
			loadTGG();
			loadModels();
			
			trash = createResource("instances/trash.xmi");
			trash.getContents().add(RuntimeFactory.eINSTANCE.createTempContainer());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		initialized = true;
	}
	
	public void saveModels() throws IOException {
		if(executable == null || executable instanceof MODELGEN || executable instanceof SYNC) {
			source.save(null);
			target.save(null);
			corr.save(null);
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

	public void loadModels() throws IOException {
		if(executable == null || executable instanceof MODELGEN || executable instanceof SYNC) {
			source = createResource(options.projectPath() + "/instances/src.xmi");
			target = createResource(options.projectPath() + "/instances/trg.xmi");
			corr = createResource(options.projectPath() + "/instances/corr.xmi");
			protocol = createResource(options.projectPath() + "/instances/protocol.xmi");
		}
		
		if(executable instanceof INTEGRATE) {
			source = createResource(options.projectPath() + "/instances/src.xmi");
			target = createResource(options.projectPath() + "/instances/trg.xmi");
			corr = createResource(options.projectPath() + "/instances/corr.xmi");
			protocol = createResource(options.projectPath() + "/instances/protocol.xmi");
			precedence = createResource(options.projectPath() + "/instances/epg.xmi");
		}

		EcoreUtil.resolveAll(rs);
	}
	
	private void createAndPrepareResourceSet() {
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
	
	private Resource loadFlattenedTGGResource() throws IOException {
		return loadResource(options.projectPath() + "/model/" + options.projectName() + "_flattened.tgg.xmi");
	}

	private Resource loadTGGResource() throws IOException {
		return loadResource(options.projectPath() + "/model/" + options.projectName() + ".tgg.xmi");
	}
	
	private void registerInternalMetamodels() {
		// Register internals for Ibex
		LanguagePackageImpl.init();
		RuntimePackageImpl.init();
	}

	protected void registerUserMetamodels() throws IOException {
		registrationHelper.registerMetamodels(rs, this);
		
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
