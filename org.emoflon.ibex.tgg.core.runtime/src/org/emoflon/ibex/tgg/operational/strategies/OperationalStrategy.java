package org.emoflon.ibex.tgg.operational.strategies;

import static org.emoflon.ibex.tgg.util.MultiAmalgamationUtil.isFusedPatternMatch;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.IGreenInterpreter;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.MatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.GreenFusedPatternFactory;
import org.emoflon.ibex.tgg.operational.patterns.GreenPatternFactory;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;
import org.emoflon.ibex.tgg.operational.updatepolicy.RandomMatchUpdatePolicy;

import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.set.hash.THashSet;
import language.TGG;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;
import language.impl.LanguagePackageImpl;
import runtime.RuntimeFactory;
import runtime.TGGRuleApplication;
import runtime.TempContainer;
import runtime.impl.RuntimePackageImpl;

public abstract class OperationalStrategy {

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

	protected TCustomHashSet<RuntimeEdge> markedAndCreatedEdges = new TCustomHashSet<>(new RuntimeEdgeHashingStrategy());
	protected THashMap<TGGRuleApplication, IMatch> brokenRuleApplications = new THashMap<>();

	protected String workspacePath;
	protected String projectPath;

	protected IbexOptions options;

	protected IBlackInterpreter blackInterpreter;
	protected IGreenInterpreter greenInterpreter;
	protected IRedInterpreter redInterpreter;
	
	private boolean domainsHaveNoSharedTypes;
	
	private boolean isFused;

	private Map<String, IGreenPatternFactory> factories;
	
	public OperationalStrategy(IbexOptions options) {
		this(options, new RandomMatchUpdatePolicy());
	}

	public OperationalStrategy(IbexOptions options,  IUpdatePolicy policy) {
		base = URI.createPlatformResourceURI("/", true);
		this.workspacePath = options.workspacePath();
		this.projectPath = options.projectPath();

		this.options = options;
		
		setModelGen();
		
		this.updatePolicy = policy;
		
		factories = new HashMap<>();
		
		greenInterpreter = new IbexGreenInterpreter(this);
		redInterpreter = new IbexRedInterpreter(this);
	}
	
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

	/**
	 * Decide if matches of this pattern should be watched and notified by the
	 * pattern matcher
	 * 
	 * @param patternName
	 * @return
	 */
	abstract public boolean isPatternRelevantForCompiler(String patternName);

	public boolean isPatternRelevantForInterpreter(String patternName) {
		return isPatternRelevantForCompiler(patternName);
	}
	
	public void addOperationalRuleMatch(String ruleName, IMatch match) {
		if(matchIsDomainConform(ruleName, match) && matchIsValidIsomorphism(ruleName, match)) {
			operationalMatchContainer.addMatch(ruleName, match);
			if (options.debug()) logger.debug("Received and added " + match.patternName());
		} else {
			if (options.debug()) logger.debug("Received but rejected " + match.patternName());
		}
	}

	private boolean matchIsValidIsomorphism(String ruleName, IMatch match) {
		if(match.patternName().endsWith(PatternSuffixes.CONSISTENCY)) {
			// Make sure that node mappings comply to bindings in match
			TGGRuleApplication ruleAppNode = (TGGRuleApplication) match.get(ConsistencyPattern.getProtocolNodeName());
			return ruleAppNode.getNodeMappings().keySet().stream().noneMatch(n -> ruleAppNode.getNodeMappings().get(n) != match.get(n));
		}
		
		return true;
	}

	private boolean matchIsDomainConform(String ruleName, IMatch match) {
		if(domainsHaveNoSharedTypes) return true;
	
		return matchedNodesAreInCorrectResource(s, getGreenFactory(ruleName).getBlackSrcNodesInRule(), match) &&
			   matchedNodesAreInCorrectResource(s, getGreenFactory(ruleName).getGreenSrcNodesInRule(), match) &&
			   matchedNodesAreInCorrectResource(t, getGreenFactory(ruleName).getBlackTrgNodesInRule(), match) &&
			   matchedNodesAreInCorrectResource(t, getGreenFactory(ruleName).getGreenTrgNodesInRule(), match);
	}
	
	private boolean matchedNodesAreInCorrectResource(Resource r, Collection<TGGRuleNode> nodes, IMatch match){
		return nodes.stream().noneMatch(n -> match.isInMatch(n.getName()) && 
											 !nodeIsInResource(match, n.getName(), r));
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
		blackInterpreter.initialise(rs.getPackageRegistry(), this, options);
		blackInterpreter.monitor(rs);
	}

	public void terminate() throws IOException {
		blackInterpreter.terminate();
		rs.getAllContents().forEachRemaining(c -> c.eAdapters().clear());
		rs.eAdapters().clear();
	}

	protected void loadTGG() throws IOException {
		Resource res = loadResource(projectPath + "/model/" + projectPath + ".tgg.xmi");
		Resource flattenedRes = loadResource(projectPath + "/model/" + projectPath + "_flattened.tgg.xmi");

		options.tgg((TGG) res.getContents().get(0));
		options.flattenedTgg((TGG) flattenedRes.getContents().get(0));
		
		// Instantiate runtime constraint provider with loaded constraint definition library
		runtimeConstraintProvider = new RuntimeTGGAttrConstraintProvider(options.tgg().getAttributeConstraintDefinitionLibrary());
		runtimeConstraintProvider.registerFactory(options.userDefinedConstraints());
		options.setConstraintProvider(runtimeConstraintProvider);
		
		rs.getResources().remove(res);
		rs.getResources().remove(flattenedRes);

		this.operationalMatchContainer = new MatchContainer(options.flattenedTGG());
		
		domainsHaveNoSharedTypes = options.tgg().getSrc().stream().noneMatch(options.tgg().getTrg()::contains);
	}

	/**
	 * Set mappings before loading the metamodel resources plugin:/resource/ ->
	 * file:/to/workspace/root/
	 * 
	 * @throws IOException
	 */
	protected void createAndPrepareResourceSet() {
		rs = blackInterpreter.createAndPrepareResourceSet(workspacePath);
	}

	protected void registerInternalMetamodels() {
		// Register internals for engine
		blackInterpreter.registerInternalMetamodels();

		// Register internals for Ibex
		LanguagePackageImpl.init();
		RuntimePackageImpl.init();
	}

	protected void loadAndRegisterMetamodel(String workspaceRelativePath) throws IOException {
		Resource res = loadResource(workspaceRelativePath);
		EPackage pack = (EPackage) res.getContents().get(0);
		rs.getPackageRegistry().put(res.getURI().toString(), pack);
		rs.getResources().remove(res);
	}

	protected Resource loadResource(String workspaceRelativePath) throws IOException {
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
		Optional<IMatch> comatch = processOperationalRuleMatch(ruleName, match);
		comatch.ifPresent(cm -> {
			if (isKernelMatch(ruleName))
				processComplementRuleMatches(cm, ruleName);
		});
		removeOperationalRuleMatch(match);
		return true;
	}

	protected IMatch chooseOneMatch() {
		IMatch match = updatePolicy.chooseOneMatch(new ImmutableMatchContainer(operationalMatchContainer));
		if(match == null)
			throw new IllegalStateException("Update policies should never return null!");
		
		return match;
	}

	protected Optional<IMatch> processOperationalRuleMatch(String ruleName, IMatch match) {
		if(!isPatternRelevantForInterpreter(match.patternName())) {
			return Optional.empty();
		}
		
		IGreenPatternFactory factory = getGreenFactory(ruleName);
		IGreenPattern greenPattern = factory.create(match.patternName());
		Optional<IMatch> comatch = greenInterpreter.apply(greenPattern, ruleName, match);	
		
		comatch.ifPresent(cm -> {
			if (options.debug()) logger.debug("Successfully applied: " + match.patternName());
			markedAndCreatedEdges.addAll(cm.getCreatedEdges());
			greenPattern.getEdgesMarkedByPattern().forEach(e -> markedAndCreatedEdges.add(getRuntimeEdge(cm, e)));
			createMarkers(greenPattern, cm, ruleName);
		});
		
		return comatch;
	}

	protected void createMarkers(IGreenPattern greenPattern, IMatch comatch, String ruleName) {
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
			if (!markedAndCreatedEdges.contains(new RuntimeEdge(src, trg, ref)))
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
						"The match " + match.patternName() + " is invalid for this operational strategy (the edge -"
								+ ref.getName() + "-> appears to be expected but is missing)!  "
								+ "Are you sure you have implemented isPatternRelevant correctly?");

			if (markedAndCreatedEdges.contains(new RuntimeEdge(src, trg, ref)))
				return true;
		}

		return false;
	}
	

	/***** Methods for reacting to broken matches of consistency patterns ******/

	public TGGRuleApplication getRuleApplicationNode(IMatch match) {
		return (TGGRuleApplication) match.get(ConsistencyPattern.getProtocolNodeName());
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
			THashSet<TGGRuleApplication> revoked = new THashSet<>();
			for (TGGRuleApplication ra : brokenRuleApplications.keySet()) {
				redInterpreter.revokeOperationalRule(brokenRuleApplications.get(ra));
				revoked.add(ra);
			}
			for (TGGRuleApplication revokedRA : revoked)
				brokenRuleApplications.remove(revokedRA);
		}
	}

	public RuntimeEdge getRuntimeEdge(IMatch match, TGGRuleEdge specificationEdge) {
		EObject src = (EObject) match.get(specificationEdge.getSrcNode().getName());
		EObject trg = (EObject) match.get(specificationEdge.getTrgNode().getName());
		EReference ref = specificationEdge.getType();
		RuntimeEdge edge = new RuntimeEdge(src, trg, ref);
		return edge;
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

	abstract public List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library);

	public TGG getTGG() {
		return options.tgg();
	}
	
	protected TGGRule getRule(String ruleName) {
		TGGRule rule = getTGG().getRules().stream()
				.filter(r -> r.getName().equals(ruleName)).findFirst().get();
		return rule;
	}
	
	protected boolean isKernelMatch(String kernelName) {
		//add here that fused kernels are kernel matches
		return getKernelRulesNames().contains(kernelName);
	}
	
	protected Set<String> getComplementRulesNames(String kernelName){
		Set<String> complementRulesNames = getTGG().getRules().stream()
												.filter(r -> r instanceof TGGComplementRule && ((TGGComplementRule) r).getKernel().getName().equals(kernelName))
												.map(n -> n.getName())
												.collect(Collectors.toSet());
		return complementRulesNames;
	}
	
	protected Set<String> getComplementRulesNames(){
		Set<String> complementRulesNames = getTGG().getRules().stream()
												.filter(r -> r instanceof TGGComplementRule)
												.map(n -> n.getName())
												.collect(Collectors.toSet());
		return complementRulesNames;
	}
	
	protected Set<String> getKernelRulesNames() {
		Set<String> kernelRulesNames = getTGG().getRules().stream()
				.filter(r -> r instanceof TGGComplementRule)
				.map(n -> ((TGGComplementRule) n).getKernel().getName())
				.distinct()
				.collect(Collectors.toSet());
		return kernelRulesNames;
	}
	
	private void processComplementRuleMatches(IMatch comatch, String kernelName) {
		blackInterpreter.updateMatches();
		//add check to see if it is kernel or if it is fused match!
		Set<IMatch> complementRuleMatches = findAllComplementRuleMatches(kernelName);
			while (! complementRuleMatches.isEmpty()) {
					IMatch match = complementRuleMatches.iterator().next();
					String ruleName = operationalMatchContainer.getRuleName(match);
					processOperationalRuleMatch(ruleName, match);
					complementRuleMatches.remove(match);
					removeOperationalRuleMatch(match);
			}
		
		//close the kernel, so other complement rules cannot find this match anymore
		TGGRuleApplication application = (TGGRuleApplication) comatch.get(ConsistencyPattern.getProtocolNodeName());
		application.setAmalgamated(true);
	}
	
	private Set<IMatch> findAllComplementRuleMatches(String kernelName) {
		Set<IMatch> allComplementRuleMatches = operationalMatchContainer.getMatches().stream()
				.filter(m -> getComplementRulesNames(kernelName).contains(PatternSuffixes.removeSuffix(m.patternName())))
				.collect(Collectors.toSet());

		return allComplementRuleMatches;
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
		if(!factories.containsKey(ruleName)) {
			if (isFusedPatternMatch(ruleName)){
				factories.put(ruleName, new GreenFusedPatternFactory(ruleName, options, this));
			}
			else {
				factories.put(ruleName, new GreenPatternFactory(ruleName, options, this));
			}
		}
		return factories.get(ruleName);
	}

	public void removeCreatedEdge(RuntimeEdge runtimeEdge) {
		markedAndCreatedEdges.remove(runtimeEdge);
	}
	
	public void removeMarkedEdge(RuntimeEdge runtimeEdge) {
		markedAndCreatedEdges.remove(runtimeEdge);
	}

	public void addToTrash(EObject o) {
		TempContainer c = (TempContainer) trash.getContents().get(0);
		c.getObjects().add(EcoreUtil.getRootContainer(o));
	}

	public IGreenPattern revokes(IMatch match) {
		throw new IllegalStateException("Not clear how to revoke a match of " + match.patternName());
	}
}