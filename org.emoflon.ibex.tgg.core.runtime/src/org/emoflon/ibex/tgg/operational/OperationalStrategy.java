package org.emoflon.ibex.tgg.operational;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
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
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;
import org.emoflon.ibex.tgg.operational.util.IMatch;
import org.emoflon.ibex.tgg.operational.util.IbexOptions;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;
import org.emoflon.ibex.tgg.operational.util.MatchContainer;
import org.emoflon.ibex.tgg.operational.util.NextMatchUpdatePolicy;
import org.emoflon.ibex.tgg.operational.util.RuleInfos;
import org.emoflon.ibex.tgg.operational.util.UpdatePolicy;

import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.set.hash.THashSet;
import language.TGG;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import language.basic.expressions.TGGAttributeExpression;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;
import language.impl.LanguagePackageImpl;
import runtime.RuntimePackage;
import runtime.TGGRuleApplication;
import runtime.impl.RuntimePackageImpl;

public abstract class OperationalStrategy {

	protected final static Logger logger = Logger.getLogger(OperationalStrategy.class);
	protected final URI base;

	protected ResourceSet rs;
	protected Resource s;
	protected Resource t;
	protected Resource c;
	protected Resource p;

	protected RuleInfos ruleInfos;
	protected MatchContainer operationalMatchContainer;
	protected UpdatePolicy updatePolicy;

	private RuntimeTGGAttrConstraintProvider runtimeConstraintProvider = new RuntimeTGGAttrConstraintProvider();

	protected TCustomHashSet<RuntimeEdge> markedEdges = new TCustomHashSet<>(new RuntimeEdgeHashingStrategy());
	protected THashMap<TGGRuleApplication, IMatch> brokenRuleApplications = new THashMap<>();

	protected String workspacePath;
	protected String projectPath;

	protected IbexOptions options;

	private PatternMatchingEngine engine;
	private boolean domainsHaveNoSharedTypes;

	public OperationalStrategy(String projectPath, String workspacePath, boolean flatten, boolean debug) {
		this(projectPath, workspacePath, flatten, debug, new NextMatchUpdatePolicy());
	}

	public OperationalStrategy(String projectPath, String workspacePath, boolean flatten, boolean debug, UpdatePolicy policy) {
		base = URI.createPlatformResourceURI("/", true);
		this.workspacePath = workspacePath;
		this.projectPath = projectPath;

		options = new IbexOptions();
		options.debug(debug);
		options.useFlattenedTGG(flatten);
		options.projectPath(projectPath);
		
		this.updatePolicy = policy;
	}

	public void registerPatternMatchingEngine(PatternMatchingEngine engine) throws IOException {
		this.engine = engine;
		createAndPrepareResourceSet();
		registerInternalMetamodels();
		registerUserMetamodels();
		loadTGG();
		initialiseEngine();
		loadModels();
	}

	protected abstract void registerUserMetamodels() throws IOException;

	/**
	 * Decide if matches of this pattern should be watched and notified by the
	 * pattern matcher
	 * 
	 * @param patternName
	 * @return
	 */
	abstract public boolean isPatternRelevant(String patternName);

	public void addOperationalRuleMatch(String ruleName, IMatch match) {
		if(matchIsDomainConform(ruleName, match))
			operationalMatchContainer.addMatch(ruleName, match);
	}

	private boolean matchIsDomainConform(String ruleName, IMatch match) {
		if(domainsHaveNoSharedTypes) return true;
	
		return matchedNodesAreInCorrectResource(s, ruleInfos.getBlackSrcNodes(ruleName), match) &&
			   matchedNodesAreInCorrectResource(s, ruleInfos.getGreenSrcNodes(ruleName), match) &&
			   matchedNodesAreInCorrectResource(t, ruleInfos.getBlackTrgNodes(ruleName), match) &&
			   matchedNodesAreInCorrectResource(t, ruleInfos.getGreenTrgNodes(ruleName), match);
	}
	
	private boolean matchedNodesAreInCorrectResource(Resource r, Collection<TGGRuleNode> nodes, IMatch match){
		return nodes.stream().noneMatch(n -> match.isInMatch(n.getName()) && 
											 !nodeIsInResource(match, n.getName(), r));
	}

	private boolean nodeIsInResource(IMatch match, String name, Resource r) {
		return match.get(name).eResource().equals(r);
	}

	public void removeOperationalRuleMatch(IMatch match) {
		operationalMatchContainer.removeMatch(match);
	}

	abstract public void saveModels() throws IOException;

	abstract public void loadModels() throws IOException;

	protected void initialiseEngine() throws IOException {
		engine.initialise(rs, this, options);
	}

	public void terminate() throws IOException {
		engine.terminate();
	}

	protected void loadTGG() throws IOException {
		Resource res = loadResource(projectPath + "/model/" + projectPath + ".tgg.xmi");
		Resource flattenedRes = loadResource(projectPath + "/model/" + projectPath + "_flattened.tgg.xmi");

		options.tgg((TGG) res.getContents().get(0));
		options.flattenedTgg((TGG) flattenedRes.getContents().get(0));

		rs.getResources().remove(res);
		rs.getResources().remove(flattenedRes);

		ruleInfos = new RuleInfos(options.flattenedTGG());
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
		rs = engine.createAndPrepareResourceSet(workspacePath);
	}

	protected void registerInternalMetamodels() {
		// Register internals for engine
		engine.registerInternalMetamodels();

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
			engine.updateMatches();
		} while (processBrokenMatches());

		do {
			engine.updateMatches();
		} while (processOneOperationalRuleMatch());

		wrapUp();
	}

	protected boolean processOneOperationalRuleMatch() {
		if (operationalMatchContainer.isEmpty())
			return false;

		IMatch match = this.updatePolicy.chooseOneMatch(operationalMatchContainer);
		String ruleName = operationalMatchContainer.getRuleName(match);
		processOperationalRuleMatch(ruleName, match);
		removeOperationalRuleMatch(match);

		return true;
	}

	public boolean processOperationalRuleMatch(String ruleName, IMatch match) {
		if (match.patternName().endsWith(PatternSuffixes.CONSISTENCY))
			return false;

		if (match.patternName().endsWith(PatternSuffixes.FWD) && !markingSrc())
			return false;

		if (match.patternName().endsWith(PatternSuffixes.BWD) && !markingTrg())
			return false;

		if (match.patternName().endsWith(PatternSuffixes.FWD) && ruleInfos.getGreenSrcNodes(ruleName).isEmpty()
															  && ruleInfos.getGreenSrcEdges(ruleName).isEmpty())
			return false;

		if (match.patternName().endsWith(PatternSuffixes.BWD) && ruleInfos.getGreenTrgNodes(ruleName).isEmpty()
															  && ruleInfos.getGreenTrgEdges(ruleName).isEmpty())
			return false;

		if (someElementsAlreadyProcessed(ruleName, match))
			return false;

		RuntimeTGGAttributeConstraintContainer cspContainer = new RuntimeTGGAttributeConstraintContainer(
				ruleInfos.getRuleCSPConstraintLibrary(ruleName), match, this, runtimeConstraintProvider);
		if (!cspContainer.solve())
			return false;

		if (!conformTypesOfGreenNodes(match, ruleName))
			return false;

		// TODO [Leblebici]: what if some context elements are not processed?
		// the match is then "pending" (and waiting for its context)
		if (!allContextElementsalreadyProcessed(match, ruleName))
			return false;

		/*
		 * this hash map complements the match to a comatch of an original
		 * triple rule application
		 */
		HashMap<String, EObject> comatch = new HashMap<>();

		if (manipulateSrc()) {
			ManipulationUtil.createNonCorrNodes(match, comatch, ruleInfos.getGreenSrcNodes(ruleName), s);
		}
		Collection<RuntimeEdge> srcEdges = ManipulationUtil.createEdges(match, comatch,
				ruleInfos.getGreenSrcEdges(ruleName), manipulateSrc());

		if (manipulateTrg()) {
			ManipulationUtil.createNonCorrNodes(match, comatch, ruleInfos.getGreenTrgNodes(ruleName), t);
		}
		Collection<RuntimeEdge> trgEdges = ManipulationUtil.createEdges(match, comatch,
				ruleInfos.getGreenTrgEdges(ruleName), manipulateTrg());

		Collection<Pair<TGGAttributeExpression, Object>> cspValues = cspContainer.getBoundAttributeExpValues();
		applyCSPValues(comatch, cspValues);

		ManipulationUtil.createCorrs(match, comatch, ruleInfos.getGreenCorrNodes(ruleName), c);

		markedEdges.addAll(srcEdges);
		markedEdges.addAll(trgEdges);

		if (protocol()) {
			prepareProtocol(ruleName, match, comatch);
		}

		if (options.debug())
			logger.debug("Successfully applied: " + match.patternName());

		return true;
	}

	protected void prepareProtocol(String ruleName, IMatch match, HashMap<String, EObject> createdElements) {
		RuntimePackage runtimePackage = RuntimePackage.eINSTANCE;

		TGGRuleApplication ra = (TGGRuleApplication) EcoreUtil.create(runtimePackage.getTGGRuleApplication());
		p.getContents().add(ra);

		ra.setName(ruleName);

		fillProtocolInfo(ruleInfos.getGreenSrcNodes(ruleName), ra, runtimePackage.getTGGRuleApplication_CreatedSrc(),
				createdElements, match);
		fillProtocolInfo(ruleInfos.getGreenTrgNodes(ruleName), ra, runtimePackage.getTGGRuleApplication_CreatedTrg(),
				createdElements, match);
		fillProtocolInfo(ruleInfos.getGreenCorrNodes(ruleName), ra, runtimePackage.getTGGRuleApplication_CreatedCorr(),
				createdElements, match);

		ra.getNodeMappings().putAll(createdElements);
		match.parameterNames().forEach(n -> {
			ra.getNodeMappings().put(n, (EObject) match.get(n));
		});

		setIsRuleApplicationFinal(ra);
	}

	protected void setIsRuleApplicationFinal(TGGRuleApplication ra) {
		ra.setFinal(true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fillProtocolInfo(Collection<? extends TGGRuleElement> ruleInfos, TGGRuleApplication protocol,
			EStructuralFeature feature, HashMap<String, EObject> createdElements, IMatch match) {
		ruleInfos.forEach(e -> {
			((EList) protocol.eGet(feature))
					.add(ManipulationUtil.getVariableByName(e.getName(), createdElements, match));
		});
	}

	protected boolean allContextElementsalreadyProcessed(IMatch match, String ruleName) {
		if (markingSrc()) {
			if (!allEdgesAlreadyProcessed(ruleInfos.getBlackSrcEdges(ruleName), match))
				return false;
		}

		if (markingTrg()) {
			if (!allEdgesAlreadyProcessed(ruleInfos.getBlackTrgEdges(ruleName), match))
				return false;
		}

		return true;
	}

	protected boolean someElementsAlreadyProcessed(String ruleName, IMatch match) {
		if (markingSrc()) {
			if (someEdgesAlreadyProcessed(ruleInfos.getGreenSrcEdges(ruleName), match))
				return true;
		}

		if (markingTrg()) {
			if (someEdgesAlreadyProcessed(ruleInfos.getGreenTrgEdges(ruleName), match))
				return true;
		}

		return false;
	}

	private boolean someEdgesAlreadyProcessed(Collection<TGGRuleEdge> specificationEdges, IMatch match) {
		for (TGGRuleEdge edge : specificationEdges) {
			EObject src = (EObject) match.get(edge.getSrcNode().getName());
			EObject trg = (EObject) match.get(edge.getTrgNode().getName());
			EReference ref = edge.getType();

			if (src == null | trg == null | ref == null)
				throw new IllegalStateException(
						"The match " + match.patternName() + " is invalid for this operational strategy (the edge -"
								+ ref.getName() + "-> appears to be expected but is missing)!  "
								+ "Are you sure you have implemented isPatternRelevant correctly?");

			if (markedEdges.contains(new RuntimeEdge(src, trg, ref)))
				return true;
		}

		return false;
	}

	private boolean allEdgesAlreadyProcessed(Collection<TGGRuleEdge> specificationEdges, IMatch match) {
		for (TGGRuleEdge edge : specificationEdges) {
			EObject src = (EObject) match.get(edge.getSrcNode().getName());
			EObject trg = (EObject) match.get(edge.getTrgNode().getName());
			EReference ref = edge.getType();
			if (!markedEdges.contains(new RuntimeEdge(src, trg, ref)))
				return false;
		}
		return true;
	}

	protected void applyCSPValues(HashMap<String, EObject> comatch,
			Collection<Pair<TGGAttributeExpression, Object>> cspValues) {
		for (Pair<TGGAttributeExpression, Object> cspVal : cspValues) {
			EObject entry = comatch.get(cspVal.getLeft().getObjectVar().getName());
			if (entry != null) {
				entry.eSet(cspVal.getLeft().getAttribute(), cspVal.getRight());
			}
		}
	}

	protected boolean conformTypesOfGreenNodes(IMatch match, String ruleName) {
		if (markingSrc()) {
			for (TGGRuleNode gsn : ruleInfos.getGreenSrcNodes(ruleName)) {
				if (gsn.getType() != ((EObject) match.get(gsn.getName())).eClass())
					return false;
			}
		}
		if (markingTrg()) {
			for (TGGRuleNode gtn : ruleInfos.getGreenTrgNodes(ruleName)) {
				if (gtn.getType() != ((EObject) match.get(gtn.getName())).eClass())
					return false;
			}
		}
		return true;
	}

	/***** Methods for reacting to broken matches of consistency patterns ******/

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
		revokeEdges(ruleApplication, match);
		revokeNodes(ruleApplication);
		EcoreUtil.delete(ruleApplication);
	}

	private void revokeEdges(TGGRuleApplication ruleApplication, IMatch match) {
		revokeEdges(ruleInfos.getGreenSrcEdges(ruleApplication.getName()), match, manipulateSrc());
		revokeEdges(ruleInfos.getGreenTrgEdges(ruleApplication.getName()), match, manipulateTrg());
	}

	private void revokeEdges(Collection<TGGRuleEdge> specificationEdges, IMatch match, boolean delete) {
		specificationEdges.forEach(se -> {
			RuntimeEdge runtimeEdge = getRuntimeEdge(match, se);
			markedEdges.remove(runtimeEdge);
			if (delete)
				ManipulationUtil.deleteEdge(runtimeEdge.getSrc(), runtimeEdge.getTrg(), runtimeEdge.getRef());
		});
	}

	protected void revokeNodes(TGGRuleApplication ra) {
		revokeNodes(ra.getCreatedSrc(), manipulateSrc());
		revokeNodes(ra.getCreatedTrg(), manipulateTrg());
		revokeNodes(ra.getCreatedCorr(), manipulateCorr());
	}

	private void revokeNodes(Collection<EObject> nodes, boolean delete) {
		if (delete)
			ManipulationUtil.deleteNodes(new THashSet<>(nodes));
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
	
	public Resource getProtocolResource() {
		return p;
	}
	
	public Resource getCorrespondenceResource() {
		return c;
	}

	public RuntimeTGGAttrConstraintProvider getCSPProvider() {
		return runtimeConstraintProvider;
	}

	protected boolean protocol() {
		return true;
	}

	abstract protected boolean manipulateSrc();

	abstract protected boolean manipulateTrg();

	protected boolean manipulateCorr() {
		return true;
	}

	protected boolean markingSrc() {
		return !manipulateSrc();
	}

	protected boolean markingTrg() {
		return !manipulateTrg();
	}

	protected boolean markingCorr() {
		return !manipulateCorr();
	}

	abstract protected void wrapUp();

	abstract public List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library);

	public TGG getTGG() {
		return options.tgg();
	}
	
	public void setUpdatePolicy(UpdatePolicy updatePolicy) {
		if (updatePolicy == null)
			throw new NullPointerException("UpdatePolicy must not be set to null.");
		else
			this.updatePolicy = updatePolicy;
	}
}