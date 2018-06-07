package org.emoflon.ibex.tgg.operational.strategies;

import static org.emoflon.ibex.tgg.util.MAUtil.isFusedPatternMatch;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.IMatchObserver;
import org.emoflon.ibex.common.utils.EMFEdge;
import org.emoflon.ibex.common.utils.EMFEdgeHashingStrategy;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.IGreenInterpreter;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.MatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.GreenFusedPatternFactory;
import org.emoflon.ibex.tgg.operational.patterns.GreenPatternFactory;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;
import org.emoflon.ibex.tgg.operational.updatepolicy.RandomMatchUpdatePolicy;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import language.TGG;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.impl.LanguagePackageImpl;
import runtime.RuntimeFactory;
import runtime.TGGRuleApplication;
import runtime.TempContainer;
import runtime.impl.RuntimePackageImpl;

public abstract class OperationalStrategy implements IMatchObserver {

	protected final static Logger logger = Logger.getLogger(OperationalStrategy.class);
	protected final URI base;

	protected ResourceSet rs;

	protected Resource trash;
	protected Resource s;
	protected Resource t;
	protected Resource c;
	protected Resource p;

	protected MatchContainer operationalMatchContainer;
	protected IUpdatePolicy updatePolicy;

	private RuntimeTGGAttrConstraintProvider runtimeConstraintProvider;

	protected ObjectOpenCustomHashSet<EMFEdge> markedAndCreatedEdges = new ObjectOpenCustomHashSet<>(
			new EMFEdgeHashingStrategy());
	protected Object2ObjectOpenHashMap<TGGRuleApplication, IMatch> brokenRuleApplications = new Object2ObjectOpenHashMap<>();

	protected final IbexOptions options;

	protected IBlackInterpreter blackInterpreter;
	protected IGreenInterpreter greenInterpreter;
	protected IRedInterpreter redInterpreter;

	private boolean domainsHaveNoSharedTypes;

	private Map<String, IGreenPatternFactory> factories;

	public OperationalStrategy(IbexOptions options) {
		this(options, new RandomMatchUpdatePolicy());
	}

	public OperationalStrategy(IbexOptions options, IUpdatePolicy policy) {
		base = URI.createPlatformResourceURI("/", true);

		this.options = options;

		setModelGen();

		this.updatePolicy = policy;

		factories = new HashMap<>();

		greenInterpreter = new IbexGreenInterpreter(this);
		redInterpreter = new IbexRedInterpreter(this);
	}

	@Override
	public void addMatch(org.emoflon.ibex.common.operational.IMatch match) {
		this.addOperationalRuleMatch(PatternSuffixes.removeSuffix(match.getPatternName()), (IMatch) match);
	}

	@Override
	public void removeMatch(org.emoflon.ibex.common.operational.IMatch match) {
		if (match.getPatternName().endsWith(PatternSuffixes.CONSISTENCY)) {
			this.addBrokenMatch((IMatch) match);
		}
		this.removeOperationalRuleMatch((IMatch) match);
	}

	/**
	 * Decide if matches of this pattern should be watched and notified by the
	 * pattern matcher
	 * 
	 * @param patternName
	 * @return
	 */
	@Override
	abstract public boolean isPatternRelevantForCompiler(String patternName);

	protected void setModelGen() {
		options.setModelGen(false);
	}

	public void registerBlackInterpreter(IBlackInterpreter blackInterpreter) throws IOException {
		this.blackInterpreter = blackInterpreter;

		createAndPrepareResourceSet();
		registerInternalMetamodels();
		registerUserMetamodels();
		loadTGG();
		initialiseBlackInterpreter();
		loadModels();

		this.trash = createResource("instances/trash.xmi");
		this.trash.getContents().add(RuntimeFactory.eINSTANCE.createTempContainer());
	}

	public void registerGreenInterpeter(IGreenInterpreter greenInterpreter) {
		this.greenInterpreter = greenInterpreter;
	}

	public void registerRedInterpeter(IRedInterpreter redInterpreter) {
		this.redInterpreter = redInterpreter;
	}

	protected abstract void registerUserMetamodels() throws IOException;

	public boolean isPatternRelevantForInterpreter(String patternName) {
		return isPatternRelevantForCompiler(patternName);
	}

	public void addOperationalRuleMatch(String ruleName, IMatch match) {
		if (this.isPatternRelevantForInterpreter(match.getPatternName()) && matchIsDomainConform(ruleName, match)
				&& matchIsValidIsomorphism(ruleName, match)) {
			operationalMatchContainer.addMatch(ruleName, match);
			logger.debug("Received and added " + match.getPatternName());
		} else
			logger.debug("Received but rejected " + match.getPatternName());
	}

	private boolean matchIsValidIsomorphism(String ruleName, IMatch match) {
		if (match.getPatternName().endsWith(PatternSuffixes.CONSISTENCY)) {
			// Make sure that node mappings comply to bindings in match
			TGGRuleApplication ruleAppNode = (TGGRuleApplication) match
					.get(ConsistencyPattern.getProtocolNodeName(ruleName));
			return ruleAppNode.getNodeMappings().keySet().stream()
					.noneMatch(n -> ruleAppNode.getNodeMappings().get(n) != match.get(n));
		}

		return true;
	}

	private boolean matchIsDomainConform(String ruleName, IMatch match) {
		if (domainsHaveNoSharedTypes)
			return true;

		return matchedNodesAreInCorrectResource(s, getGreenFactory(ruleName).getBlackSrcNodesInRule(), match)
				&& matchedNodesAreInCorrectResource(s, getGreenFactory(ruleName).getGreenSrcNodesInRule(), match)
				&& matchedNodesAreInCorrectResource(t, getGreenFactory(ruleName).getBlackTrgNodesInRule(), match)
				&& matchedNodesAreInCorrectResource(t, getGreenFactory(ruleName).getGreenTrgNodesInRule(), match);
	}

	private boolean matchedNodesAreInCorrectResource(Resource r, Collection<TGGRuleNode> nodes, IMatch match) {
		return nodes.stream().noneMatch(n -> match.isInMatch(n.getName()) && !nodeIsInResource(match, n.getName(), r));
	}

	private boolean nodeIsInResource(IMatch match, String name, Resource r) {
		return ((EObject) match.get(name)).eResource().equals(r);
	}

	public void removeOperationalRuleMatch(IMatch match) {
		operationalMatchContainer.removeMatch(match);
	}

	abstract public void saveModels() throws IOException;

	abstract public void loadModels() throws IOException;

	protected void initialiseBlackInterpreter() throws IOException {
		// Attempt initialisation
		Optional<RuntimeException> initExcep = Optional.empty();
		try {
			blackInterpreter.initialise(rs.getPackageRegistry(), this);
			blackInterpreter.setOptions(options);
		} catch (RuntimeException e) {
			initExcep = Optional.of(e);
		}

		// Even if init failed still attempt to monitor before failing
		try {
			// Ensure that the resource set is empty
			rs.getResources().clear();
			blackInterpreter.monitor(rs);
		} finally {
			if (initExcep.isPresent())
				throw initExcep.get();
		}
	}

	/**
	 * Terminates the strategy
	 * 
	 * @throws IOException
	 */
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
		Object[] matches = operationalMatchContainer.getMatches().toArray();
		for (Object m : matches)
			this.operationalMatchContainer.removeMatch((IMatch) m);

		logger.debug("Removed black interpreter");
	}

	/**
	 * Replaces the black interpreter and initializes the new black interpreter
	 * 
	 * @param newBlackInterpreter
	 *            The black interpreter to replace the existing black interpreter
	 */
	protected void reinitializeBlackInterpreter(IBlackInterpreter newBlackInterpreter) {
		this.removeBlackInterpreter();
		this.blackInterpreter = newBlackInterpreter;
		this.blackInterpreter.initialise(rs.getPackageRegistry(), this);
		this.blackInterpreter.setOptions(options);
		this.blackInterpreter.monitor(rs);
	}

	protected void loadTGG() throws IOException {
		Resource res = loadTGGResource();
		Resource flattenedRes = loadFlattenedTGGResource();

		options.tgg((TGG) res.getContents().get(0));
		options.flattenedTgg((TGG) flattenedRes.getContents().get(0));

		// Instantiate runtime constraint provider with loaded constraint definition
		// library
		runtimeConstraintProvider = new RuntimeTGGAttrConstraintProvider(
				options.tgg().getAttributeConstraintDefinitionLibrary());
		runtimeConstraintProvider.registerFactory(options.userDefinedConstraints());
		options.setConstraintProvider(runtimeConstraintProvider);

		rs.getResources().remove(res);
		rs.getResources().remove(flattenedRes);

		this.operationalMatchContainer = new MatchContainer(options.flattenedTGG());

		domainsHaveNoSharedTypes = options.tgg().getSrc().stream().noneMatch(options.tgg().getTrg()::contains);
	}

	protected Resource loadFlattenedTGGResource() throws IOException {
		return loadResource(options.projectPath() + "/model/" + options.projectName() + "_flattened.tgg.xmi");
	}

	protected Resource loadTGGResource() throws IOException {
		return loadResource(options.projectPath() + "/model/" + options.projectName() + ".tgg.xmi");
	}

	/**
	 * Set mappings before loading the metamodel resources plugin:/resource/ ->
	 * file:/to/workspace/root/
	 * 
	 * @throws IOException
	 */
	protected void createAndPrepareResourceSet() {
		rs = blackInterpreter.createAndPrepareResourceSet(options.workspacePath());
	}

	protected void registerInternalMetamodels() {
		// Register internals for Ibex
		LanguagePackageImpl.init();
		RuntimePackageImpl.init();
	}

	public void loadAndRegisterMetamodel(String workspaceRelativePath) throws IOException {
		Resource res = loadResource(workspaceRelativePath);
		EPackage pack = (EPackage) res.getContents().get(0);
		rs.getPackageRegistry().put(res.getURI().toString(), pack);
		rs.getResources().remove(res);
	}

	public Resource loadResource(String workspaceRelativePath) throws IOException {
		Resource res = createResource(workspaceRelativePath);
		res.load(null);
		EcoreUtil.resolveAll(res);
		return res;
	}

	protected Resource createResource(String workspaceRelativePath) {
		URI uri = URI.createURI(workspaceRelativePath);
		Resource res = rs.createResource(uri.resolve(base), ContentHandler.UNSPECIFIED_CONTENT_TYPE);
		return res;
	}

	public void run() throws IOException {
		do {
			blackInterpreter.updateMatches();
		} while (processBrokenMatches());

		do {
			blackInterpreter.updateMatches();
		} while (processOneOperationalRuleMatch());

		wrapUp();
	}

	protected boolean processOneOperationalRuleMatch() {
		if (operationalMatchContainer.isEmpty())
			return false;

		IMatch match = chooseOneMatch();
		String ruleName = operationalMatchContainer.getRuleName(match);

		processOperationalRuleMatch(ruleName, match);
		removeOperationalRuleMatch(match);
		return true;
	}

	protected IMatch chooseOneMatch() {
		IMatch match = updatePolicy.chooseOneMatch(new ImmutableMatchContainer(operationalMatchContainer));
		if (match == null)
			throw new IllegalStateException("Update policies should never return null!");

		return match;
	}

	protected Optional<IMatch> processOperationalRuleMatch(String ruleName, IMatch match) {
		if (!updatePolicy.matchShouldBeApplied(match, ruleName)) {
			return Optional.empty();
		}

		IGreenPatternFactory factory = getGreenFactory(ruleName);
		IGreenPattern greenPattern = factory.create(match.getPatternName());
		Optional<IMatch> comatch = greenInterpreter.apply(greenPattern, ruleName, match);

		comatch.ifPresent(cm -> {
			logger.debug("Successfully applied: " + match.getPatternName());
			markedAndCreatedEdges.addAll(cm.getCreatedEdges());
			greenPattern.getEdgesMarkedByPattern().forEach(e -> markedAndCreatedEdges.add(getRuntimeEdge(cm, e)));
			createMarkers(greenPattern, cm, ruleName);
			updatePolicy.notifyMatchHasBeenApplied(cm, ruleName);
		});

		return comatch;
	}

	protected void prepareMarkerCreation(IGreenPattern greenPattern, IMatch comatch, String ruleName) {

	}

	private void createMarkers(IGreenPattern greenPattern, IMatch comatch, String ruleName) {
		prepareMarkerCreation(greenPattern, comatch, ruleName);
		greenPattern.createMarkers(ruleName, comatch);
	}

	public void setIsRuleApplicationFinal(TGGRuleApplication ra) {
		ra.setFinal(true);
	}

	public boolean allEdgesAlreadyProcessed(Collection<TGGRuleEdge> specificationEdges, IMatch match) {
		for (TGGRuleEdge edge : specificationEdges) {
			EObject src = (EObject) match.get(edge.getSrcNode().getName());
			EObject trg = (EObject) match.get(edge.getTrgNode().getName());
			EReference ref = edge.getType();
			if (!markedAndCreatedEdges.contains(new EMFEdge(src, trg, ref)))
				return false;
		}

		return true;
	}

	public boolean someEdgesAlreadyProcessed(Collection<TGGRuleEdge> specificationEdges, IMatch match) {
		for (TGGRuleEdge edge : specificationEdges) {
			EObject src = (EObject) match.get(edge.getSrcNode().getName());
			EObject trg = (EObject) match.get(edge.getTrgNode().getName());
			EReference ref = edge.getType();

			if (src == null | trg == null | ref == null)
				throw new IllegalStateException(
						"The match " + match.getPatternName() + " is invalid for this operational strategy (the edge -"
								+ ref.getName() + "-> appears to be expected but is missing)!  "
								+ "Are you sure you have implemented isPatternRelevant correctly?");

			if (markedAndCreatedEdges.contains(new EMFEdge(src, trg, ref)))
				return true;
		}

		return false;
	}

	/***** Methods for reacting to broken matches of consistency patterns ******/

	public TGGRuleApplication getRuleApplicationNode(IMatch match) {
		return (TGGRuleApplication) match
				.get(ConsistencyPattern.getProtocolNodeName(PatternSuffixes.removeSuffix(match.getPatternName())));
	}

	public void addBrokenMatch(IMatch match) {
		TGGRuleApplication ra = getRuleApplicationNode(match);
		brokenRuleApplications.put(ra, match);
	}

	protected boolean processBrokenMatches() {
		if (brokenRuleApplications.isEmpty())
			return false;

		revokeAllMatches();

		return true;
	}

	private void revokeAllMatches() {
		while (!brokenRuleApplications.isEmpty()) {
			ObjectOpenHashSet<TGGRuleApplication> revoked = new ObjectOpenHashSet<>();
			for (TGGRuleApplication ra : brokenRuleApplications.keySet()) {
				redInterpreter.revokeOperationalRule(brokenRuleApplications.get(ra));
				revoked.add(ra);
			}
			for (TGGRuleApplication revokedRA : revoked)
				brokenRuleApplications.remove(revokedRA);
		}
	}

	public EMFEdge getRuntimeEdge(IMatch match, TGGRuleEdge specificationEdge) {
		EObject src = (EObject) match.get(specificationEdge.getSrcNode().getName());
		EObject trg = (EObject) match.get(specificationEdge.getTrgNode().getName());
		EReference ref = specificationEdge.getType();
		return new EMFEdge(src, trg, ref);
	}

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

	public RuntimeTGGAttrConstraintProvider getCSPProvider() {
		return runtimeConstraintProvider;
	}

	abstract protected void wrapUp();

	public TGG getTGG() {
		return options.tgg();
	}

	protected Optional<TGGRule> getRule(String ruleName) {
		return getTGG().getRules().stream().filter(r -> r.getName().equals(ruleName)).findFirst();
	}

	protected Optional<TGGComplementRule> getComplementRule(String ruleName) {
		return getRule(ruleName).filter(TGGComplementRule.class::isInstance).map(TGGComplementRule.class::cast);
	}

	protected boolean isKernelMatch(String kernelName) {
		return getKernelRulesNames().contains(kernelName);
	}

	protected boolean isComplementMatch(String complementName) {
		return getComplementRulesNames().contains(complementName);
	}

	protected Set<String> getComplementRulesNames() {
		Set<String> complementRulesNames = getTGG().getRules().stream().filter(r -> r instanceof TGGComplementRule)
				.map(n -> n.getName()).collect(Collectors.toSet());
		return complementRulesNames;
	}

	protected Set<String> getKernelRulesNames() {
		Set<String> kernelRulesNames = getTGG().getRules().stream().filter(r -> r instanceof TGGComplementRule)
				.map(n -> ((TGGComplementRule) n).getKernel().getName()).distinct().collect(Collectors.toSet());
		return kernelRulesNames;
	}

	protected boolean tggContainsComplementRules() {
		return !getComplementRulesNames().isEmpty();
	}

	public void setUpdatePolicy(IUpdatePolicy updatePolicy) {
		if (updatePolicy == null)
			throw new NullPointerException("UpdatePolicy must not be set to null.");
		else
			this.updatePolicy = updatePolicy;
	}

	public IUpdatePolicy getUpdatePolicy() {
		return updatePolicy;
	}

	public IGreenPatternFactory getGreenFactory(String ruleName) {
		assert (ruleName != null);
		if (!factories.containsKey(ruleName)) {
			if (isFusedPatternMatch(ruleName)) {
				factories.put(ruleName, new GreenFusedPatternFactory(ruleName, options, this));
			} else {
				factories.put(ruleName, new GreenPatternFactory(ruleName, options, this));
			}
		}

		return factories.get(ruleName);
	}

	public void removeCreatedEdge(EMFEdge runtimeEdge) {
		markedAndCreatedEdges.remove(runtimeEdge);
	}

	public void removeMarkedEdge(EMFEdge runtimeEdge) {
		markedAndCreatedEdges.remove(runtimeEdge);
	}

	public void addToTrash(EObject o) {
		TempContainer c = (TempContainer) trash.getContents().get(0);
		c.getObjects().add(EcoreUtil.getRootContainer(o));
	}

	public IGreenPattern revokes(IMatch match) {
		throw new IllegalStateException("Not clear how to revoke a match of " + match.getPatternName());
	}

	public IbexOptions getOptions() {
		return options;
	}
}