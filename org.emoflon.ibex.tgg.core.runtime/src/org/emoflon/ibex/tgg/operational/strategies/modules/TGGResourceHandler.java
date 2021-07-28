package org.emoflon.ibex.tgg.operational.strategies.modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.common.emf.EMFEdge;
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
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.smartemf.persistence.SmartEMFResourceFactoryImpl;
import org.moflon.smartemf.runtime.util.SmartEMFUtil;

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
	protected ResourceSet specificationRS;
	protected ResourceSet rs;

	protected Resource source;
	protected Resource target;
	protected Resource corr;
	protected Resource protocol;

	private MetamodelRelaxer relaxer = new MetamodelRelaxer();

	private boolean initialized = false;

	private Resource trash;

	private Map<EObject, Collection<EObject>> node2corrs;

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
		if (initialized)
			return;

		try {
			createAndPrepareResourceSets();
			registerInternalMetamodels();
			registerUserMetamodels();
			loadTGG();
			loadRelevantModels();

			if (options.blackInterpreter() != null && options.blackInterpreter().getClass().getName().contains("Democles")) {
				trash = createResource("instances/trash.xmi");
				trash.getContents().add(RuntimeFactory.eINSTANCE.createTempContainer());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		initialized = true;
	}

	public Map<EObject, Collection<EObject>> getCorrCaching() {
		if (node2corrs != null)
			return node2corrs;

		initializeCorrCaching();
		return node2corrs;
	}

	private void initializeCorrCaching() {
		node2corrs = Collections.synchronizedMap(new HashMap<>());
		corr.getContents().parallelStream().forEach(corr -> doAddCorrCachingNode(corr));
	}

	public void addCorrCachingNode(EObject corr) {
		if (node2corrs == null)
			initializeCorrCaching();

		doAddCorrCachingNode(corr);
	}

	public void addCorrCachingEdge(EMFEdge corrEdge) {
		if (node2corrs == null)
			initializeCorrCaching();

		EObject corrNode = null;
		EObject node = null;
		if (Objects.equals(corr, corrEdge.getSource().eResource())) {
			corrNode = corrEdge.getSource();
			node = corrEdge.getTarget();
		} else if (Objects.equals(corr, corrEdge.getTarget().eResource())) {
			corrNode = corrEdge.getTarget();
			node = corrEdge.getSource();
		} else
			throw new RuntimeException("Inconsistent correnspondence edge found!");

		doAddCorrCachingNode(corrNode, node);
	}

	private void doAddCorrCachingNode(EObject corr) {
		for (EObject node : corr.eCrossReferences())
			doAddCorrCachingNode(corr, node);
	}

	private void doAddCorrCachingNode(EObject corr, EObject node) {
		Collection<EObject> corrNodes = null;
		if (!node2corrs.containsKey(node)) {
			corrNodes = Collections.synchronizedSet(new HashSet<>());
			node2corrs.put(node, corrNodes);
		} else
			corrNodes = node2corrs.get(node);
		corrNodes.add(corr);
	}

	public void removeCorrCachingNode(EObject corr) {
		if (node2corrs == null)
			initializeCorrCaching();

		for (EObject node : corr.eCrossReferences()) {
			if (node2corrs.containsKey(node)) {
				node2corrs.get(node).remove(corr);
			} else
				throw new RuntimeException("Unkown correspondence node detected!");
		}
	}

	public void saveRelevantModels() throws IOException {
		if (executable instanceof FWD_OPT) {
			// Unrelax the metamodel
			relaxer.unrelaxReferences(options.tgg.tgg().getTrg());
		}

		if (executable instanceof BWD_OPT) {
			// Unrelax the metamodel
			relaxer.unrelaxReferences(options.tgg.tgg().getSrc());
		}

		saveModels();
	}

	public void saveModels() throws IOException {
		if (executable == null || executable instanceof MODELGEN) {
			source.save(null);
			target.save(null);
			corr.save(null);
			protocol.save(null);
		}

		if (executable instanceof FWD_OPT) {
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

		if (executable instanceof BWD_OPT) {
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

		if (executable instanceof SYNC) {
			if (executable instanceof INITIAL_FWD) {
				target.save(null);
				corr.save(null);
				protocol.save(null);
			} else if (executable instanceof INITIAL_BWD) {
				source.save(null);
				corr.save(null);
				protocol.save(null);
			} else {
				source.save(null);
				target.save(null);
				corr.save(null);
				protocol.save(null);
			}
		}

		if (executable instanceof CC) {
			corr.save(null);
			protocol.save(null);
		}

		if (executable instanceof CO) {
			protocol.save(null);
		}

		if (executable instanceof INTEGRATE) {
			source.save(null);
			target.save(null);
			corr.save(null);
			protocol.save(null);
		}
	}

	public void loadRelevantModels() throws IOException {
		if (executable instanceof FWD_OPT) {
			relaxer.relaxReferences(options.tgg.tgg().getTrg());
		}

		if (executable instanceof BWD_OPT) {
			relaxer.relaxReferences(options.tgg.tgg().getSrc());
		}

		loadModels();
		SmartEMFUtil.resolveAll(rs);
	}

	public void loadModels() throws IOException {
		if (executable == null || executable instanceof MODELGEN) {
			source = createResource(options.project.path() + "/instances/src.xmi");
			target = createResource(options.project.path() + "/instances/trg.xmi");
			corr = createResource(options.project.path() + "/instances/corr.xmi");
			protocol = createResource(options.project.path() + "/instances/protocol.xmi");
			return;
		}

		if (executable instanceof FWD_OPT) {
			source = loadResource(options.project.path() + "/instances/src.xmi");
			target = createResource(options.project.path() + "/instances/trg.xmi");
			corr = createResource(options.project.path() + "/instances/corr.xmi");
			protocol = createResource(options.project.path() + "/instances/protocol.xmi");
		}

		if (executable instanceof BWD_OPT) {
			source = createResource(options.project.path() + "/instances/src.xmi");
			target = loadResource(options.project.path() + "/instances/trg.xmi");
			corr = createResource(options.project.path() + "/instances/corr.xmi");
			protocol = createResource(options.project.path() + "/instances/protocol.xmi");
		}

		if (executable instanceof SYNC) {
			if (executable instanceof INITIAL_FWD) {
				source = loadResource(options.project.path() + "/instances/src.xmi");
				target = createResource(options.project.path() + "/instances/trg.xmi");
				corr = createResource(options.project.path() + "/instances/corr.xmi");
				protocol = createResource(options.project.path() + "/instances/protocol.xmi");
			} else if (executable instanceof INITIAL_BWD) {
				source = createResource(options.project.path() + "/instances/src.xmi");
				target = loadResource(options.project.path() + "/instances/trg.xmi");
				corr = createResource(options.project.path() + "/instances/corr.xmi");
				protocol = createResource(options.project.path() + "/instances/protocol.xmi");
			} else {
				source = loadResource(options.project.path() + "/instances/src.xmi");
				target = loadResource(options.project.path() + "/instances/trg.xmi");
				corr = loadResource(options.project.path() + "/instances/corr.xmi");
				protocol = loadResource(options.project.path() + "/instances/protocol.xmi");
			}
		}

		if (executable instanceof CC) {
			source = loadResource(options.project.path() + "/instances/src.xmi");
			target = loadResource(options.project.path() + "/instances/trg.xmi");
			corr = createResource(options.project.path() + "/instances/corr.xmi");
			protocol = createResource(options.project.path() + "/instances/protocol.xmi");
		}

		if (executable instanceof CO) {
			source = loadResource(options.project.path() + "/instances/src.xmi");
			target = loadResource(options.project.path() + "/instances/trg.xmi");
			corr = loadResource(options.project.path() + "/instances/corr.xmi");
			protocol = createResource(options.project.path() + "/instances/protocol.xmi");
		}

		if (executable instanceof INTEGRATE) {
			source = loadResource(options.project.path() + "/instances/src.xmi");
			target = loadResource(options.project.path() + "/instances/trg.xmi");
			corr = loadResource(options.project.path() + "/instances/corr.xmi");
			protocol = loadResource(options.project.path() + "/instances/protocol.xmi");
		}

		SmartEMFUtil.resolveAll(rs);
	}

	protected void createAndPrepareResourceSets() {
		rs = options.blackInterpreter().createAndPrepareResourceSet(options.project.workspacePath());
		specificationRS = options.blackInterpreter().createAndPrepareResourceSet(options.project.workspacePath());
		
		// set the factory of metamodelRs to EMFResourceFactory as SmartEMF does not supported loading metamodels alongside models within the same resourceset
		specificationRS.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
	}

	public ResourceSet getModelResourceSet() {
		return rs;
	}
	
	public ResourceSet getSpecificationResourceSet() {
		return specificationRS;
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
		if (specificationRS.getPackageRegistry().containsKey(uri)) {
			return specificationRS.getPackageRegistry().getEPackage(uri);
		}
		Resource res = loadResource(specificationRS, workspaceRelativePath);
		EPackage pack = (EPackage) res.getContents().get(0);
		pack = (EPackage) specificationRS.getPackageRegistry().getOrDefault(res.getURI().toString(), pack);
		specificationRS.getPackageRegistry().put(res.getURI().toString(), pack);
		specificationRS.getPackageRegistry().put(pack.getNsURI(), pack);
		specificationRS.getResources().remove(res);
		return pack;
	}

	public EPackage loadAndRegisterCorrMetamodel() throws IOException {
		String relativePath = MoflonUtil.lastCapitalizedSegmentOf(options.project.name()) + "/model/"
				+ MoflonUtil.lastCapitalizedSegmentOf(options.project.name()) + ".ecore";

		EPackage pack = loadAndRegisterMetamodel("platform:/resource/" + relativePath);
		options.tgg.corrMetamodel(pack);
		return pack;
	}

	public Resource loadResource(String workspaceRelativePath) throws IOException {
		Resource res = createResource(workspaceRelativePath);
		try {
			res.load(null);
		} catch (FileNotFoundException e) {
			throw new TGGFileNotFoundException(e, res.getURI());
		}
		SmartEMFUtil.resolveAll(res);
		return res;
	}

	public Resource createResource(String workspaceRelativePath) {
		URI uri = URI.createURI(workspaceRelativePath);
		Resource res = rs.createResource(uri.resolve(base), ContentHandler.UNSPECIFIED_CONTENT_TYPE);
		return res;
	}
	
	private Resource loadResource(ResourceSet rs, String workspaceRelativePath) throws IOException {
		Resource res = createResource(rs, workspaceRelativePath);
		try {
			res.load(null);
		} catch (FileNotFoundException e) {
			throw new TGGFileNotFoundException(e, res.getURI());
		}
		SmartEMFUtil.resolveAll(res);
		return res;
	}
	
	public Resource createResource(ResourceSet rs, String workspaceRelativePath) {
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

		options.tgg.tgg((TGG) res.getContents().get(0));
		options.tgg.flattenedTgg((TGG) flattenedRes.getContents().get(0));

		RuntimeTGGAttrConstraintProvider runtimeConstraintProvider = new RuntimeTGGAttrConstraintProvider(
				options.tgg.tgg().getAttributeConstraintDefinitionLibrary());
		runtimeConstraintProvider.registerFactory(options.csp.userDefinedConstraints());
		options.csp.constraintProvider(runtimeConstraintProvider);

		rs.getResources().remove(res);
		rs.getResources().remove(flattenedRes);
	}

	protected Resource loadFlattenedTGGResource() throws IOException {
		return loadTGGResource("_flattened.tgg.xmi");
	}

	protected Resource loadTGGResource() throws IOException {
		return loadTGGResource(".tgg.xmi");
	}

	protected Resource loadTGGResource(String fileEnding) throws IOException {
		String projectRelativePath = "model/" + MoflonUtil.lastCapitalizedSegmentOf(options.project.name()) + fileEnding;
		Resource res = null;
		// first, try to load TGG resource via workspace relative path
		try {
			String workspaceRelativePath = options.project.path() + "/" + projectRelativePath;
			res = loadResource(specificationRS, workspaceRelativePath);
		} catch (TGGFileNotFoundException e1) {
			// if file could not be found, try to load TGG resource via protection domain
			try {
				res = loadResourceViaProtectionDomain(executable.getClass(), projectRelativePath);
			} catch (TGGFileNotFoundException e2) {
				// if it also did not work, throw an exception
				throw new IOException("We looked for the TGG file at the following paths, but did not find it:\n" //
						+ "  " + e1.getUri() + "\n" //
						+ "  " + e2.getUri() + "\n" //
						+ "This may have two reasons:\n" //
						+ "1. in the IbexOptions your TGG project path is incomplete\n" //
						+ "2. your IbexExecutable stub is located out of your TGG project", //
						e2);
			}
		}

		EcoreUtil.resolveAll(res);
		return res;
	}

	protected Resource loadResourceViaProtectionDomain(Class<?> clazz, String projectRelativePath) throws IOException {
		String path = clazz.getProtectionDomain().getCodeSource().getLocation().getPath().toString();
		// fix that is necessary if executed in an eclipse plugin context
		if (path.endsWith("bin/"))
			path = path.substring(0, path.length() - 4);
		path += projectRelativePath;

		File file = new File(path);
		String canonicalPath = file.getCanonicalPath();
		canonicalPath = canonicalPath.replace("%20", " ");

		URI uri = URI.createFileURI(canonicalPath);
		Resource res = specificationRS.createResource(uri);
		try {
			res.load(null);
		} catch (FileNotFoundException e) {
			throw new TGGFileNotFoundException(e, res.getURI());
		}
		EcoreUtil.resolveAll(res);

		return res;
	}

	private void registerInternalMetamodels() {
		// Register internals for Ibex
		LanguagePackageImpl.init();
		RuntimePackageImpl.init();
	}

	protected void registerUserMetamodels() throws IOException {
		options.registrationHelper().registerMetamodels(specificationRS, options.executable());

		// Register correspondence metamodel last
		loadAndRegisterCorrMetamodel();
	}

	public void addToTrash(EObject o) {
		if (trash != null) {
			TempContainer c = (TempContainer) trash.getContents().get(0);
			c.getObjects().add(EcoreUtil.getRootContainer(o));
		}
	}

	public Resource getTrashResource() {
		return trash;
	}

	public static class TGGFileNotFoundException extends IOException {

		private static final long serialVersionUID = 1L;

		private URI uri;

		public TGGFileNotFoundException(Throwable e, URI uri) {
			super(e);
			this.uri = uri;
		}

		public TGGFileNotFoundException(String message, Throwable e, URI uri) {
			super(message, e);
			this.uri = uri;
		}

		public URI getUri() {
			return uri;
		}

	}
}
