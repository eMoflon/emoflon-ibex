package org.emoflon.ibex.tgg.operational.strategies;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.IGreenInterpreter;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.operational.defaults.GreenInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.MatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.GreenPatternFactory;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;
import org.emoflon.ibex.tgg.operational.updatepolicy.RandomMatchUpdatePolicy;

import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.set.hash.THashSet;
import language.TGG;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;
import language.impl.LanguagePackageImpl;
import runtime.RuntimeFactory;
import runtime.RuntimePackage;
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

	private Map<String, GreenPatternFactory> factories;
	
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
		
		greenInterpreter = new GreenInterpreter(this);
	}
	
	protected void setModelGen() {
		options.setModelGen(false);
	}

	public void registerPatternMatchingEngine(IBlackInterpreter blackInterpreter) throws IOException {
		//FIXME[Anjorin] Remove this wrapper method eventually and refactor tests
		registerBlackInterpreter(blackInterpreter);
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
		if(matchIsDomainConform(ruleName, match) && matchIsValidIsomorphism(ruleName, match))
			operationalMatchContainer.addMatch(ruleName, match);
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
	
		return matchedNodesAreInCorrectResource(s, getFactory(ruleName).getBlackSrcNodesInRule(), match) &&
			   matchedNodesAreInCorrectResource(s, getFactory(ruleName).getGreenSrcNodesInRule(), match) &&
			   matchedNodesAreInCorrectResource(t, getFactory(ruleName).getBlackTrgNodesInRule(), match) &&
			   matchedNodesAreInCorrectResource(t, getFactory(ruleName).getGreenTrgNodesInRule(), match);
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
		processOperationalRuleMatch(ruleName, match);
		removeOperationalRuleMatch(match);

		return true;
	}

	protected IMatch chooseOneMatch() {
		IMatch match = updatePolicy.chooseOneMatch(new ImmutableMatchContainer(operationalMatchContainer));
		if(match == null)
			throw new IllegalStateException("Update policies should never return null!");
		
		return match;
	}

	public Optional<IMatch> processOperationalRuleMatch(String ruleName, IMatch match) {
		if(!isPatternRelevantForInterpreter(match.patternName())) {
			return Optional.empty();
		}
		
		GreenPatternFactory factory = getFactory(ruleName);
		IGreenPattern greenPattern = factory.create(match.patternName());
		Optional<IMatch> comatch = greenInterpreter.apply(greenPattern, ruleName, match);	
		
		comatch.ifPresent(cm -> {
			if (options.debug()) logger.debug("Successfully applied: " + match.patternName());
			markedAndCreatedEdges.addAll(cm.getCreatedEdges());
			greenPattern.getEdgesToBeMarked().forEach(e -> markedAndCreatedEdges.add(getRuntimeEdge(cm, e)));
			if(protocol()) prepareProtocol(ruleName, cm);
		});
		
		return comatch;
	}

	protected void prepareProtocol(String ruleName, IMatch comatch) {
		RuntimePackage runtimePackage = RuntimePackage.eINSTANCE;

		TGGRuleApplication ra = (TGGRuleApplication) EcoreUtil.create(runtimePackage.getTGGRuleApplication());
		p.getContents().add(ra);

		ra.setName(ruleName);

		fillProtocolInfo(getFactory(ruleName).getGreenSrcNodesInRule(), ra, runtimePackage.getTGGRuleApplication_CreatedSrc(), comatch);
		fillProtocolInfo(getFactory(ruleName).getGreenTrgNodesInRule(), ra, runtimePackage.getTGGRuleApplication_CreatedTrg(), comatch);
		fillProtocolInfo(getFactory(ruleName).getGreenCorrNodesInRule(), ra, runtimePackage.getTGGRuleApplication_CreatedCorr(), comatch);

		addProtocolContextEdges(getFactory(ruleName).getBlackSrcNodesInRule(), ra, runtimePackage.getTGGRuleApplication_ContextSrc(), comatch);
		addProtocolContextEdges(getFactory(ruleName).getBlackTrgNodesInRule(), ra, runtimePackage.getTGGRuleApplication_ContextTrg(), comatch);

		comatch.parameterNames().stream()
			.filter(n -> !IbexBasePattern.isAttrNode(n))
			.forEach(n -> ra.getNodeMappings().put(n, (EObject) comatch.get(n)));

		setIsRuleApplicationFinal(ra);
		comatch.put(ConsistencyPattern.getProtocolNodeName(), ra);
	}

	protected void setIsRuleApplicationFinal(TGGRuleApplication ra) {
		ra.setFinal(true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fillProtocolInfo(Collection<? extends TGGRuleElement> ruleInfos, TGGRuleApplication protocol, EStructuralFeature feature, IMatch comatch) {
		ruleInfos.forEach(e -> {
			((EList) protocol.eGet(feature)).add(comatch.get(e.getName()));
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addProtocolContextEdges(Collection<? extends TGGRuleElement> ruleInfos, TGGRuleApplication protocol, EStructuralFeature feature, IMatch match) {
		ruleInfos.forEach(e -> {
			((EList) protocol.eGet(feature)).add(match.get(e.getName()));
		});
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

	protected TGGRuleApplication getRuleApplicationNode(IMatch match) {
		return (TGGRuleApplication) match.get("eMoflon_ProtocolNode");
	}
	
	public void addBrokenMatch(IMatch match) {
		TGGRuleApplication ra = (TGGRuleApplication) match.get("eMoflon_ProtocolNode");
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
				revokeOperationalRule(ra, brokenRuleApplications.get(ra));
				revoked.add(ra);
			}
			for (TGGRuleApplication revokedRA : revoked)
				brokenRuleApplications.remove(revokedRA);
		}
	}

	protected void revokeOperationalRule(TGGRuleApplication ruleApplication, IMatch match) {
		revokeCorrs(ruleApplication);
		revokeNodes(ruleApplication);
		revokeEdges(ruleApplication, match);
		
		EcoreUtil.delete(ruleApplication);
	}

	private void revokeEdges(TGGRuleApplication ruleApplication, IMatch match) {
		revokeEdges(getFactory(ruleApplication.getName()).getGreenSrcEdgesInRule(), match, manipulateSrc());
		revokeEdges(getFactory(ruleApplication.getName()).getGreenTrgEdgesInRule(), match, manipulateTrg());
	}

	private void revokeEdges(Collection<TGGRuleEdge> specificationEdges, IMatch match, boolean delete) {
		specificationEdges.forEach(se -> {
			RuntimeEdge runtimeEdge = getRuntimeEdge(match, se);
			markedAndCreatedEdges.remove(runtimeEdge);
			if (delete)
				GreenInterpreter.deleteEdge(runtimeEdge.getSrc(), runtimeEdge.getTrg(), runtimeEdge.getRef());
		});
	}

	protected void revokeNodes(TGGRuleApplication ra) {
		revokeNodes(ra.getCreatedSrc(), manipulateSrc());
		revokeNodes(ra.getCreatedTrg(), manipulateTrg());
	}

	private void revokeCorrs(TGGRuleApplication ra) {
		EList<EObject> createdCorr = ra.getCreatedCorr();
		if(manipulateCorr()) {
			for(EObject corr : createdCorr) {
				EStructuralFeature srcFeature = corr.eClass().getEStructuralFeature("source");
				EStructuralFeature trgFeature = corr.eClass().getEStructuralFeature("target");
				
				EObject src = (EObject) corr.eGet(srcFeature);
				EObject trg = (EObject) corr.eGet(trgFeature);
				
				if(isDanglingNode(Optional.ofNullable(src))) addToTrash(src);
				if(isDanglingNode(Optional.ofNullable(trg))) addToTrash(trg);
				
				corr.eUnset(srcFeature);
				corr.eUnset(trgFeature);
			}
		}
		
		revokeNodes(createdCorr, manipulateCorr());
	}

	private void addToTrash(EObject o) {
		TempContainer c = (TempContainer) trash.getContents().get(0);
		c.getObjects().add(EcoreUtil.getRootContainer(o));
	}

	private boolean isDanglingNode(EObject o) {
		return o.eResource() == null;
	}
	
	private boolean isDanglingNode(Optional<EObject> o) {
		return o.map(this::isDanglingNode).orElse(false);
	}

	private void revokeNodes(Collection<EObject> nodes, boolean delete) {
		if (delete) {
			for (EObject n : nodes) if(isDanglingNode(n)) addToTrash(n);
			
			// Now safe to delete
			GreenInterpreter.deleteNodes(new THashSet<>(nodes));
		}
	}

	private RuntimeEdge getRuntimeEdge(IMatch match, TGGRuleEdge specificationEdge) {
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

	protected boolean protocol() {
		return true;
	}

	//FIXME[Anjorin] Git rid of these methods as patterns used by their interpreter decide what is to be done
	@Deprecated
	abstract protected boolean manipulateSrc();
	@Deprecated
	abstract protected boolean manipulateTrg();
	@Deprecated
	abstract protected boolean manipulateCorr(); 

	@Deprecated
	protected boolean markingSrc() {
		return !manipulateSrc();
	}

	@Deprecated
	protected boolean markingTrg() {
		return !manipulateTrg();
	}

	@Deprecated
	protected boolean markingCorr() {
		return !manipulateCorr();
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
		return getKernelRulesNames().contains(kernelName);
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
	
	public void setUpdatePolicy(IUpdatePolicy updatePolicy) {
		if (updatePolicy == null)
			throw new NullPointerException("UpdatePolicy must not be set to null.");
		else
			this.updatePolicy = updatePolicy;
	}
	
	public IUpdatePolicy getUpdatePolicy() {
		return updatePolicy;
	}
	
	public GreenPatternFactory getFactory(String ruleName) {
		if(!factories.containsKey(ruleName))
			factories.put(ruleName, new GreenPatternFactory(ruleName, options, this));

		return factories.get(ruleName);
	}
}