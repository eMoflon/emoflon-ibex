package org.emoflon.ibex.tgg.operational.strategies;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.IMatchObserver;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.IGreenInterpreter;
import org.emoflon.ibex.tgg.operational.benchmark.BenchmarkLogger;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.MatchContainer;
import org.emoflon.ibex.tgg.operational.monitoring.AbstractIbexObservable;
import org.emoflon.ibex.tgg.operational.patterns.GreenPatternFactory;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;
import org.emoflon.ibex.tgg.operational.updatepolicy.NextMatchUpdatePolicy;

import language.TGG;
import language.TGGRule;
import language.TGGRuleNode;
import language.impl.LanguagePackageImpl;
import runtime.RuntimeFactory;
import runtime.TGGRuleApplication;
import runtime.TempContainer;
import runtime.impl.RuntimePackageImpl;

public abstract class OperationalStrategy extends AbstractIbexObservable implements IMatchObserver {
	private long currentIntervalStart = -1;
	private final long INTERVAL_LENGTH = 5000;
	private long matchCounter = 0;

	protected final static Logger logger = Logger.getLogger(OperationalStrategy.class);

	// Resource management
	protected final URI base;
	protected ResourceSet rs;
	protected Resource trash;
	protected Resource s;
	protected Resource t;
	protected Resource c;
	protected Resource p;

	// Match and pattern management
	protected IMatchContainer operationalMatchContainer;
	protected Map<TGGRuleApplication, IMatch> consistencyMatches;
	private boolean domainsHaveNoSharedTypes;
	private Map<String, IGreenPatternFactory> factories;
	
	protected Map<IMatch, String> blockedMatches = cfactory.createObjectToObjectHashMap();

	// Configuration
	protected final IbexOptions options;

	// Model manipulation
	private RuntimeTGGAttrConstraintProvider runtimeConstraintProvider;
	protected IBlackInterpreter blackInterpreter;
	protected IGreenInterpreter greenInterpreter;

	/***** Constructors *****/

	public OperationalStrategy(IbexOptions options) {
		this(options, new NextMatchUpdatePolicy());
	}

	protected OperationalStrategy(IbexOptions options, IUpdatePolicy policy) {
		BenchmarkLogger.startTimer();
		
		this.options = options;
		this.setUpdatePolicy(policy);

		base = URI.createPlatformResourceURI("/", true);
		factories = new HashMap<>();

		greenInterpreter = new IbexGreenInterpreter(this);

		consistencyMatches = cfactory.createObjectToObjectHashMap();
		
		options.getBenchmarkLogger().addToInitTime(BenchmarkLogger.stopTimer());
	}

	/***** Resource management *****/

	public ResourceSet getResourceSet() {
		return rs;
	}

	public Resource getSourceResource() {
		return s;
	}

	public Resource getTargetResource() {
		return t;
	}

	public Resource getCorrResource() {
		return c;
	}

	public Resource getProtocolResource() {
		return p;
	}

	public TGG getTGG() {
		return options.tgg();
	}

	abstract public void saveModels() throws IOException;

	abstract public void loadModels() throws IOException;

	protected void createAndPrepareResourceSet() {
		rs = blackInterpreter.createAndPrepareResourceSet(options.workspacePath());
	}

	protected void registerInternalMetamodels() {
		// Register internals for Ibex
		LanguagePackageImpl.init();
		RuntimePackageImpl.init();
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

	protected void loadTGG() throws IOException {
		Resource res = loadTGGResource();
		Resource flattenedRes = loadFlattenedTGGResource();

		EcoreUtil.resolveAll(rs);
		EcoreUtil.UnresolvedProxyCrossReferencer//
				.find(rs)//
				.forEach((eob, settings) -> logger.error("Problems resolving: " + eob));

		options.tgg((TGG) res.getContents().get(0));
		options.flattenedTgg((TGG) flattenedRes.getContents().get(0));

		runtimeConstraintProvider = new RuntimeTGGAttrConstraintProvider(
				options.tgg().getAttributeConstraintDefinitionLibrary());
		runtimeConstraintProvider.registerFactory(options.userDefinedConstraints());
		options.setConstraintProvider(runtimeConstraintProvider);

		rs.getResources().remove(res);
		rs.getResources().remove(flattenedRes);

		this.operationalMatchContainer = createMatchContainer();

		domainsHaveNoSharedTypes = options.tgg().getSrc().stream().noneMatch(options.tgg().getTrg()::contains);
	}

	protected IMatchContainer createMatchContainer() {
		return new MatchContainer(options.flattenedTGG());
	}

	protected Resource loadFlattenedTGGResource() throws IOException {
		return loadResource(options.projectPath() + "/model/" + options.projectName() + "_flattened.tgg.xmi");
	}

	protected Resource loadTGGResource() throws IOException {
		return loadResource(options.projectPath() + "/model/" + options.projectName() + ".tgg.xmi");
	}

	public void addToTrash(EObject o) {
		TempContainer c = (TempContainer) trash.getContents().get(0);
		c.getObjects().add(EcoreUtil.getRootContainer(o));
	}

	/***** Match and pattern management *****/
	
	@Override
	public void notifySubscriptions() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void addMatch(org.emoflon.ibex.common.operational.IMatch match) {
		matchCounter++;
		if (currentIntervalStart == -1) {
			logger.info("Now collecting matches...");
			currentIntervalStart = System.currentTimeMillis();
		} else if (System.currentTimeMillis() - currentIntervalStart > INTERVAL_LENGTH) {
			logger.info("Collected " + matchCounter + " matches...");
			currentIntervalStart = System.currentTimeMillis();
		}

		addOperationalRuleMatch((IMatch) match);
	}

	protected void addOperationalRuleMatch(IMatch match) {
		if (match.getPatternName().endsWith(PatternSuffixes.CONSISTENCY))
			addConsistencyMatch(match);

		if (isPatternRelevantForInterpreter(match.getPatternName()) && matchIsDomainConform(match)) {
			operationalMatchContainer.addMatch(match);
			logger.debug("Received and added " + match.getPatternName());
		} else
			logger.debug("Received but rejected " + match.getPatternName());
	}

	protected void addConsistencyMatch(IMatch match) {
		TGGRuleApplication ruleAppNode = getRuleApplicationNode(match);
		consistencyMatches.put(ruleAppNode, match);
		logger.debug("Received and added consistency match: " + match.getPatternName() + "(" + match.hashCode() + ")");
	}

	@Override
	public void removeMatch(org.emoflon.ibex.common.operational.IMatch match) {
		if (removeOperationalRuleMatch((IMatch) match)) {
			logger.debug("Removed due to delete event from pattern matcher: ");
			logger.debug(match.getPatternName());
		}
	}

	protected boolean removeOperationalRuleMatch(IMatch match) {
		return operationalMatchContainer.removeMatch(match);
	}

	public boolean isPatternRelevantForInterpreter(String patternName) {
		return isPatternRelevantForCompiler(patternName);
	}
	
	private boolean matchIsDomainConform(IMatch match) {
		if (domainsHaveNoSharedTypes || options.ignoreDomainConformity())
			return true;

		return matchedNodesAreInCorrectResource(s, //
				getGreenFactory(match.getRuleName()).getBlackSrcNodesInRule(), match)
				&& matchedNodesAreInCorrectResource(s, //
						getGreenFactory(match.getRuleName()).getGreenSrcNodesInRule(), match)
				&& matchedNodesAreInCorrectResource(t, //
						getGreenFactory(match.getRuleName()).getBlackTrgNodesInRule(), match)
				&& matchedNodesAreInCorrectResource(t, //
						getGreenFactory(match.getRuleName()).getGreenTrgNodesInRule(), match);
	}

	private boolean matchedNodesAreInCorrectResource(Resource r, Collection<TGGRuleNode> nodes, IMatch match) {
		return nodes.stream().noneMatch(n -> match.isInMatch(n.getName()) && !nodeIsInResource(match, n.getName(), r));
	}

	private Map<EObject, Resource> cacheObjectToResource = cfactory.createObjectToObjectHashMap();

	private boolean nodeIsInResource(IMatch match, String name, Resource r) {
		EObject root = (EObject) match.get(name);
		if (!cacheObjectToResource.containsKey(root))
			cacheObjectToResource.put(root, root.eResource());

		return cacheObjectToResource.get(root).equals(r);
	}

	public IGreenPattern revokes(IMatch match) {
		throw new IllegalStateException("Not clear how to revoke a match of " + match.getPatternName());
	}

	public abstract void run() throws IOException;

	protected boolean processOneOperationalRuleMatch() {
		this.updateBlockedMatches();
		if (operationalMatchContainer.isEmpty())
			return false;

		IMatch match = chooseOneMatch();
		String ruleName = operationalMatchContainer.getRuleName(match);

		Optional<IMatch> result = processOperationalRuleMatch(ruleName, match);
		removeOperationalRuleMatch(match);

		if (result.isPresent()) {
			options.getBenchmarkLogger().addToNumOfMatchesApplied(1);
			logger.debug("Removed as it has just been applied: ");
		} else
			logger.debug("Removed as application failed: ");

		logger.debug(match);

		return true;
	}

	protected IMatch chooseOneMatch() {
		IMatch match = this.notifyChooseMatch(new ImmutableMatchContainer(operationalMatchContainer));
		
		if (match == null)
			throw new IllegalStateException("Update policies should never return null!");

		return match;
	}

	protected Optional<IMatch> processOperationalRuleMatch(String ruleName, IMatch match) {
		//generatedPatternsSizeObserver.setNodes(match);
		if (this.getBlockedMatches().containsKey(match)) { 
			logger.debug("Application blocked by update policy.");
			return Optional.empty();
		}

		IGreenPatternFactory factory = getGreenFactory(ruleName);
		IGreenPattern greenPattern = factory.create(match.getPatternName());

		logger.debug("Attempting to apply: " + match.getPatternName() + "(" + match.hashCode() + ") with " + greenPattern);

		Optional<IMatch> comatch = greenInterpreter.apply(greenPattern, ruleName, match);

		comatch.ifPresent(cm -> {
			logger.debug("Successfully applied: " + match.getPatternName());
			this.notifyMatchApplied(match, ruleName);
			operationalMatchContainer.matchApplied(match);
			handleSuccessfulRuleApplication(cm, ruleName, greenPattern);
		});

		return comatch;
	}

	protected void handleSuccessfulRuleApplication(IMatch cm, String ruleName, IGreenPattern greenPattern) {
		createMarkers(greenPattern, cm, ruleName);
	}

	protected void prepareMarkerCreation(IGreenPattern greenPattern, IMatch comatch, String ruleName) {

	}

	protected void createMarkers(IGreenPattern greenPattern, IMatch comatch, String ruleName) {
		prepareMarkerCreation(greenPattern, comatch, ruleName);
		greenPattern.createMarkers(ruleName, comatch);
	}

	public TGGRuleApplication getRuleApplicationNode(IMatch match) {
		return (TGGRuleApplication) match.get(TGGPatternUtil.getProtocolNodeName(//
				PatternSuffixes.removeSuffix(match.getPatternName())));
	}

	public RuntimeTGGAttrConstraintProvider getCSPProvider() {
		return runtimeConstraintProvider;
	}

	protected Optional<TGGRule> getRule(String ruleName) {
		return options.tgg().getRules().stream()//
				.filter(r -> r.getName().equals(ruleName))//
				.findFirst();
	}
	
	protected void updateBlockedMatches() {
		for(IMatch match : operationalMatchContainer.getMatches().toArray(new IMatch[0])) {
			if(!this.getUpdatePolicy().matchShouldBeApplied(match, operationalMatchContainer.getRuleName(match))) {
				if(!blockedMatches.containsKey(match))
					blockedMatches.put(match, "Match is blocked by the update policy");
				this.operationalMatchContainer.removeMatch(match);
			}
		}
	}

	public Map<IMatch, String> getBlockedMatches() {
	    return this.blockedMatches;
	}
	
	/****** Initialisation, termination *****/

	public void registerBlackInterpreter(IBlackInterpreter blackInterpreter) throws IOException {
		BenchmarkLogger.startTimer();
		
		this.notifyStartInit();
		this.blackInterpreter = blackInterpreter;

		createAndPrepareResourceSet();
		registerInternalMetamodels();
		registerUserMetamodels();
		
		this.notifyStartLoading();
		loadTGG();
		loadModels();
		this.notifyLoadingFinished();
		
		initialiseBlackInterpreter();

		this.trash = createResource("instances/trash.xmi");
		this.trash.getContents().add(RuntimeFactory.eINSTANCE.createTempContainer());
		
		this.notifyDoneInit();
		
		options.getBenchmarkLogger().addToInitTime(BenchmarkLogger.stopTimer());
	}

	public void registerGreenInterpeter(IGreenInterpreter greenInterpreter) {
		this.greenInterpreter = greenInterpreter;
	}

	public IGreenInterpreter getGreenInterpreter() {
		return greenInterpreter;
	}

	protected abstract void registerUserMetamodels() throws IOException;

	protected void initialiseBlackInterpreter() throws IOException {		
		Optional<RuntimeException> initExcep = Optional.empty();
		try {
			blackInterpreter.initialise(options, rs.getPackageRegistry(), this);
		} catch (RuntimeException e) {
			initExcep = Optional.of(e);
		}

		try {
			blackInterpreter.monitor(rs);
		} finally {
			if (initExcep.isPresent())
				throw initExcep.get();
		}
	}

	public void terminate() throws IOException {
		removeBlackInterpreter();
	}

	/**
	 * Removes the black interpreter and all references to the black interpreter
	 * from the strategy and its resources
	 */
	protected void removeBlackInterpreter() {
		if (blackInterpreter == null)
			return;

		blackInterpreter.terminate();
		blackInterpreter = null;
		rs.getAllContents().forEachRemaining(c -> c.eAdapters().clear());
		rs.eAdapters().clear();
		operationalMatchContainer.removeAllMatches();
		cacheObjectToResource.clear();

		logger.debug("Removed black interpreter");
	}

	/**
	 * Replaces the black interpreter and initialises the new black interpreter
	 * 
	 * @param newBlackInterpreter The black interpreter to replace the existing
	 *                            black interpreter
	 */
	protected void reinitializeBlackInterpreter(IBlackInterpreter newBlackInterpreter) {
		this.removeBlackInterpreter();
		this.blackInterpreter = newBlackInterpreter;
		this.blackInterpreter.initialise(options, rs.getPackageRegistry(), this);
		this.blackInterpreter.monitor(rs);
	}

	public IGreenPatternFactory getGreenFactory(String ruleName) {
		assert (ruleName != null);
		if (!factories.containsKey(ruleName)) {
			factories.put(ruleName, new GreenPatternFactory(ruleName, options, this));
		}

		return factories.get(ruleName);
	}

	/***** Configuration *****/

	public Map<String, IGreenPatternFactory> getFactories() {
		return factories;
	}

	public IbexOptions getOptions() {
		return options;
	}
	
	public IMatchContainer getMatchContainer() {
		return operationalMatchContainer;
	}
	
	/***** Benchmark Logging *****/
	
	protected void collectDataToBeLogged() {
		options.getBenchmarkLogger().setNumOfMatchesFound(matchCounter);
	}
}