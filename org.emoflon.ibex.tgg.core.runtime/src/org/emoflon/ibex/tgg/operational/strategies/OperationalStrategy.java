package org.emoflon.ibex.tgg.operational.strategies;

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
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFEdgeHashingStrategy;
import org.emoflon.ibex.common.operational.IMatchObserver;
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
import org.emoflon.ibex.tgg.util.MAUtil;

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

	private final Map<String, IGreenPatternFactory> factories;

	public OperationalStrategy(final IbexOptions options) {
		this(options, new RandomMatchUpdatePolicy());
	}

	public OperationalStrategy(final IbexOptions options, final IUpdatePolicy policy) {
		this.base = URI.createPlatformResourceURI("/", true);

		this.options = options;

		this.setModelGen();

		this.updatePolicy = policy;

		this.factories = new HashMap<>();

		this.greenInterpreter = new IbexGreenInterpreter(this);
		this.redInterpreter = new IbexRedInterpreter(this);
	}

	@Override
	public void addMatch(final org.emoflon.ibex.common.operational.IMatch match) {
		this.addOperationalRuleMatch(PatternSuffixes.removeSuffix(match.getPatternName()), (IMatch) match);
	}

	@Override
	public void removeMatch(final org.emoflon.ibex.common.operational.IMatch match) {
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
		this.options.setModelGen(false);
	}

	public void registerBlackInterpreter(final IBlackInterpreter blackInterpreter) throws IOException {
		this.blackInterpreter = blackInterpreter;

		this.createAndPrepareResourceSet();
		this.registerInternalMetamodels();
		this.registerUserMetamodels();
		this.loadTGG();
		this.initialiseBlackInterpreter();
		this.loadModels();

		this.trash = this.createResource("instances/trash.xmi");
		this.trash.getContents().add(RuntimeFactory.eINSTANCE.createTempContainer());
	}

	public void registerGreenInterpeter(final IGreenInterpreter greenInterpreter) {
		this.greenInterpreter = greenInterpreter;
	}

	public void registerRedInterpeter(final IRedInterpreter redInterpreter) {
		this.redInterpreter = redInterpreter;
	}

	protected abstract void registerUserMetamodels() throws IOException;

	public boolean isPatternRelevantForInterpreter(final String patternName) {
		return this.isPatternRelevantForCompiler(patternName);
	}

	public void addOperationalRuleMatch(final String ruleName, final IMatch match) {
		if (this.isPatternRelevantForInterpreter(match.getPatternName()) && this.matchIsDomainConform(ruleName, match)
				&& this.matchIsValidIsomorphism(ruleName, match)) {
			this.operationalMatchContainer.addMatch(ruleName, match);
			OperationalStrategy.logger.debug("Received and added " + match.getPatternName());
		} else
			OperationalStrategy.logger.debug("Received but rejected " + match.getPatternName());
	}

	private boolean matchIsValidIsomorphism(final String ruleName, final IMatch match) {
		if (match.getPatternName().endsWith(PatternSuffixes.CONSISTENCY)) {
			// Make sure that node mappings comply to bindings in match
			TGGRuleApplication ruleAppNode = (TGGRuleApplication) match
					.get(ConsistencyPattern.getProtocolNodeName(ruleName));
			return ruleAppNode.getNodeMappings().keySet().stream()
					.noneMatch(n -> ruleAppNode.getNodeMappings().get(n) != match.get(n));
		}

		return true;
	}

	private boolean matchIsDomainConform(final String ruleName, final IMatch match) {
		if (this.domainsHaveNoSharedTypes)
			return true;

		return this.matchedNodesAreInCorrectResource(this.s, this.getGreenFactory(ruleName).getBlackSrcNodesInRule(), match)
				&& this.matchedNodesAreInCorrectResource(this.s, this.getGreenFactory(ruleName).getGreenSrcNodesInRule(), match)
				&& this.matchedNodesAreInCorrectResource(this.t, this.getGreenFactory(ruleName).getBlackTrgNodesInRule(), match)
				&& this.matchedNodesAreInCorrectResource(this.t, this.getGreenFactory(ruleName).getGreenTrgNodesInRule(), match);
	}

	private boolean matchedNodesAreInCorrectResource(final Resource r, final Collection<TGGRuleNode> nodes, final IMatch match) {
		return nodes.stream().noneMatch(n -> match.isInMatch(n.getName()) && !this.nodeIsInResource(match, n.getName(), r));
	}

	private boolean nodeIsInResource(final IMatch match, final String name, final Resource r) {
		return ((EObject) match.get(name)).eResource().equals(r);
	}

	public void removeOperationalRuleMatch(final IMatch match) {
		this.operationalMatchContainer.removeMatch(match);
	}

	abstract public void saveModels() throws IOException;

	abstract public void loadModels() throws IOException;

	protected void initialiseBlackInterpreter() throws IOException {
		// Attempt initialisation
		Optional<RuntimeException> initExcep = Optional.empty();
		try {
			this.blackInterpreter.initialise(this.rs.getPackageRegistry(), this);
			this.blackInterpreter.setOptions(this.options);
		} catch (RuntimeException e) {
			initExcep = Optional.of(e);
		}

		// Even if init failed still attempt to monitor before failing
		try {
			// Ensure that the resource set is empty
			this.rs.getResources().clear();
			this.blackInterpreter.monitor(this.rs);
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
		this.removeBlackInterpreter();
	}

	/**
	 * Removes the black interpreter and all references to the black interpreter
	 * from the strategy and its resources
	 */
	protected void removeBlackInterpreter() {
		if (this.blackInterpreter == null)
			return;

		this.blackInterpreter.terminate();
		this.blackInterpreter = null;
		this.rs.getAllContents().forEachRemaining(c -> c.eAdapters().clear());
		this.rs.eAdapters().clear();
		Object[] matches = this.operationalMatchContainer.getMatches().toArray();
		for (Object m : matches)
			this.operationalMatchContainer.removeMatch((IMatch) m);

		OperationalStrategy.logger.debug("Removed black interpreter");
	}

	/**
	 * Replaces the black interpreter and initializes the new black interpreter
	 *
	 * @param newBlackInterpreter
	 *            The black interpreter to replace the existing black interpreter
	 */
	protected void reinitializeBlackInterpreter(final IBlackInterpreter newBlackInterpreter) {
		if (this.blackInterpreter != null) {
			this.removeBlackInterpreter();
		}
		this.blackInterpreter = newBlackInterpreter;
		this.blackInterpreter.initialise(this.rs.getPackageRegistry(), this);
		this.blackInterpreter.setOptions(this.options);
		this.blackInterpreter.monitor(this.rs);
	}

	protected void loadTGG() throws IOException {
		Resource res = this.loadTGGResource();
		Resource flattenedRes = this.loadFlattenedTGGResource();

		this.options.tgg((TGG) res.getContents().get(0));
		this.options.flattenedTgg((TGG) flattenedRes.getContents().get(0));

		// Instantiate runtime constraint provider with loaded constraint definition
		// library
		this.runtimeConstraintProvider = new RuntimeTGGAttrConstraintProvider(
				this.options.tgg().getAttributeConstraintDefinitionLibrary());
		this.runtimeConstraintProvider.registerFactory(this.options.userDefinedConstraints());
		this.options.setConstraintProvider(this.runtimeConstraintProvider);

		this.rs.getResources().remove(res);
		this.rs.getResources().remove(flattenedRes);

		this.operationalMatchContainer = new MatchContainer(this.options.flattenedTGG());

		this.domainsHaveNoSharedTypes = this.options.tgg().getSrc().stream().noneMatch(this.options.tgg().getTrg()::contains);
	}

	protected Resource loadFlattenedTGGResource() throws IOException {
		return this.loadResource(this.options.projectPath() + "/model/" + this.options.projectName() + "_flattened.tgg.xmi");
	}

	protected Resource loadTGGResource() throws IOException {
		return this.loadResource(this.options.projectPath() + "/model/" + this.options.projectName() + ".tgg.xmi");
	}

	/**
	 * Set mappings before loading the metamodel resources plugin:/resource/ ->
	 * file:/to/workspace/root/
	 *
	 * @throws IOException
	 */
	protected void createAndPrepareResourceSet() {
		this.rs = this.blackInterpreter.createAndPrepareResourceSet(this.options.workspacePath());
	}

	protected void registerInternalMetamodels() {
		// Register internals for Ibex
		LanguagePackageImpl.init();
		RuntimePackageImpl.init();
	}

	public EPackage loadAndRegisterMetamodel(final String workspaceRelativePath) throws IOException {
		Resource res = this.loadResource(workspaceRelativePath);
		EPackage pack = (EPackage) res.getContents().get(0);
		this.rs.getPackageRegistry().put(res.getURI().toString(), pack);
		this.rs.getPackageRegistry().put(pack.getNsURI(), pack);
		this.rs.getResources().remove(res);
		return pack;
	}

	public Resource loadResource(final String workspaceRelativePath) throws IOException {
		Resource res = this.createResource(workspaceRelativePath);
		res.load(null);
		EcoreUtil.resolveAll(res);
		return res;
	}

	protected Resource createResource(final String workspaceRelativePath) {
		URI uri = URI.createURI(workspaceRelativePath);
		Resource res = this.rs.createResource(uri.resolve(this.base), ContentHandler.UNSPECIFIED_CONTENT_TYPE);
		return res;
	}

	public void run() throws IOException {
		do {
			this.blackInterpreter.updateMatches();
		} while (this.processBrokenMatches());

		do {
			this.blackInterpreter.updateMatches();
		} while (this.processOneOperationalRuleMatch());

		this.wrapUp();
	}

	protected boolean processOneOperationalRuleMatch() {
		if (this.operationalMatchContainer.isEmpty())
			return false;

		IMatch match = this.chooseOneMatch();
		String ruleName = this.operationalMatchContainer.getRuleName(match);

		this.processOperationalRuleMatch(ruleName, match);
		this.removeOperationalRuleMatch(match);
		return true;
	}

	protected IMatch chooseOneMatch() {
		IMatch match = this.updatePolicy.chooseOneMatch(new ImmutableMatchContainer(this.operationalMatchContainer));
		if (match == null)
			throw new IllegalStateException("Update policies should never return null!");

		return match;
	}

	protected Optional<IMatch> processOperationalRuleMatch(final String ruleName, final IMatch match) {
		if (!this.updatePolicy.matchShouldBeApplied(match, ruleName)) {
			return Optional.empty();
		}

		IGreenPatternFactory factory = this.getGreenFactory(ruleName);
		IGreenPattern greenPattern = factory.create(match.getPatternName());
		Optional<IMatch> comatch = this.greenInterpreter.apply(greenPattern, ruleName, match);

		comatch.ifPresent(cm -> {
			OperationalStrategy.logger.debug("Successfully applied: " + match.getPatternName());
			this.markedAndCreatedEdges.addAll(cm.getCreatedEdges());
			greenPattern.getEdgesMarkedByPattern().forEach(e -> this.markedAndCreatedEdges.add(this.getRuntimeEdge(cm, e)));
			this.createMarkers(greenPattern, cm, ruleName);
			this.updatePolicy.notifyMatchHasBeenApplied(cm, ruleName);
		});

		return comatch;
	}

	protected void prepareMarkerCreation(final IGreenPattern greenPattern, final IMatch comatch, final String ruleName) {

	}

	private void createMarkers(final IGreenPattern greenPattern, final IMatch comatch, final String ruleName) {
		this.prepareMarkerCreation(greenPattern, comatch, ruleName);
		greenPattern.createMarkers(ruleName, comatch);
	}

	public void setIsRuleApplicationFinal(final TGGRuleApplication ra) {
		ra.setFinal(true);
	}

	public boolean allEdgesAlreadyProcessed(final Collection<TGGRuleEdge> specificationEdges, final IMatch match) {
		for (TGGRuleEdge edge : specificationEdges) {
			EObject src = (EObject) match.get(edge.getSrcNode().getName());
			EObject trg = (EObject) match.get(edge.getTrgNode().getName());
			EReference ref = edge.getType();
			if (!this.markedAndCreatedEdges.contains(new EMFEdge(src, trg, ref)))
				return false;
		}

		return true;
	}

	public boolean someEdgesAlreadyProcessed(final Collection<TGGRuleEdge> specificationEdges, final IMatch match) {
		for (TGGRuleEdge edge : specificationEdges) {
			EObject src = (EObject) match.get(edge.getSrcNode().getName());
			EObject trg = (EObject) match.get(edge.getTrgNode().getName());
			EReference ref = edge.getType();

			if (src == null | trg == null | ref == null)
				throw new IllegalStateException(
						"The match " + match.getPatternName() + " is invalid for this operational strategy (the edge -"
								+ ref.getName() + "-> appears to be expected but is missing)!  "
								+ "Are you sure you have implemented isPatternRelevant correctly?");

			if (this.markedAndCreatedEdges.contains(new EMFEdge(src, trg, ref)))
				return true;
		}

		return false;
	}

	/***** Methods for reacting to broken matches of consistency patterns ******/

	public TGGRuleApplication getRuleApplicationNode(final IMatch match) {
		return (TGGRuleApplication) match
				.get(ConsistencyPattern.getProtocolNodeName(PatternSuffixes.removeSuffix(match.getPatternName())));
	}

	public void addBrokenMatch(final IMatch match) {
		TGGRuleApplication ra = this.getRuleApplicationNode(match);
		this.brokenRuleApplications.put(ra, match);
	}

	protected boolean processBrokenMatches() {
		if (this.brokenRuleApplications.isEmpty())
			return false;

		this.revokeAllMatches();

		return true;
	}

	private void revokeAllMatches() {
		while (!this.brokenRuleApplications.isEmpty()) {
			ObjectOpenHashSet<TGGRuleApplication> revoked = new ObjectOpenHashSet<>();
			for (TGGRuleApplication ra : this.brokenRuleApplications.keySet()) {
				this.redInterpreter.revokeOperationalRule(this.brokenRuleApplications.get(ra));
				revoked.add(ra);
			}
			for (TGGRuleApplication revokedRA : revoked)
				this.brokenRuleApplications.remove(revokedRA);
		}
	}

	public EMFEdge getRuntimeEdge(final IMatch match, final TGGRuleEdge specificationEdge) {
		EObject src = (EObject) match.get(specificationEdge.getSrcNode().getName());
		EObject trg = (EObject) match.get(specificationEdge.getTrgNode().getName());
		EReference ref = specificationEdge.getType();
		return new EMFEdge(src, trg, ref);
	}

	public ResourceSet getResourceSet() {
		return this.rs;
	}

	public Resource getSourceResource() {
		return this.s;
	}

	public Resource getTargetResource() {
		return this.t;
	}

	public Resource getCorrResource() {
		return this.c;
	}

	public Resource getProtocolResource() {
		return this.p;
	}

	public RuntimeTGGAttrConstraintProvider getCSPProvider() {
		return this.runtimeConstraintProvider;
	}

	abstract protected void wrapUp();

	public TGG getTGG() {
		return this.options.tgg();
	}

	protected Optional<TGGRule> getRule(final String ruleName) {
		return this.getTGG().getRules().stream().filter(r -> r.getName().equals(ruleName)).findFirst();
	}

	protected Optional<TGGComplementRule> getComplementRule(final String ruleName) {
		return this.getRule(ruleName).filter(TGGComplementRule.class::isInstance).map(TGGComplementRule.class::cast);
	}

	protected boolean isKernelMatch(final String kernelName) {
		return this.getKernelRulesNames().contains(kernelName);
	}

	protected boolean isComplementMatch(final String complementName) {
		return this.getComplementRulesNames().contains(complementName);
	}

	protected Set<String> getComplementRulesNames() {
		Set<String> complementRulesNames = this.getTGG().getRules().stream().filter(r -> r instanceof TGGComplementRule)
				.map(n -> n.getName()).collect(Collectors.toSet());
		return complementRulesNames;
	}

	protected Set<String> getKernelRulesNames() {
		Set<String> kernelRulesNames = this.getTGG().getRules().stream().filter(r -> r instanceof TGGComplementRule)
				.map(n -> ((TGGComplementRule) n).getKernel().getName()).distinct().collect(Collectors.toSet());
		return kernelRulesNames;
	}

	protected boolean tggContainsComplementRules() {
		return !this.getComplementRulesNames().isEmpty();
	}

	public void setUpdatePolicy(final IUpdatePolicy updatePolicy) {
		if (updatePolicy == null)
			throw new NullPointerException("UpdatePolicy must not be set to null.");
		else
			this.updatePolicy = updatePolicy;
	}

	public IUpdatePolicy getUpdatePolicy() {
		return this.updatePolicy;
	}

	public IGreenPatternFactory getGreenFactory(final String ruleName) {
		assert (ruleName != null);
		if (!this.factories.containsKey(ruleName)) {
			if (MAUtil.isFusedPatternMatch(ruleName)) {
				this.factories.put(ruleName, new GreenFusedPatternFactory(ruleName, this.options, this));
			} else {
				this.factories.put(ruleName, new GreenPatternFactory(ruleName, this.options, this));
			}
		}

		return this.factories.get(ruleName);
	}

	public void removeCreatedEdge(final EMFEdge runtimeEdge) {
		this.markedAndCreatedEdges.remove(runtimeEdge);
	}

	public void removeMarkedEdge(final EMFEdge runtimeEdge) {
		this.markedAndCreatedEdges.remove(runtimeEdge);
	}

	public void addToTrash(final EObject o) {
		TempContainer c = (TempContainer) this.trash.getContents().get(0);
		c.getObjects().add(EcoreUtil.getRootContainer(o));
	}

	public IGreenPattern revokes(final IMatch match) {
		throw new IllegalStateException("Not clear how to revoke a match of " + match.getPatternName());
	}

	public IbexOptions getOptions() {
		return this.options;
	}
}