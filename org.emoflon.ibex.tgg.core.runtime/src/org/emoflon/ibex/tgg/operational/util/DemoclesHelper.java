package org.emoflon.ibex.tgg.operational.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.TGGCompiler;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.operational.OperationalStrategy;
import org.gervarro.democles.common.DataFrame;
import org.gervarro.democles.common.IDataFrame;
import org.gervarro.democles.common.PatternMatcherPlugin;
import org.gervarro.democles.common.runtime.ListOperationBuilder;
import org.gervarro.democles.common.runtime.VariableRuntime;
import org.gervarro.democles.constraint.CoreConstraintModule;
import org.gervarro.democles.constraint.emf.EMFConstraintModule;
import org.gervarro.democles.event.MatchEvent;
import org.gervarro.democles.event.MatchEventListener;
import org.gervarro.democles.interpreter.incremental.rete.RetePattern;
import org.gervarro.democles.interpreter.incremental.rete.RetePatternMatcherModule;
import org.gervarro.democles.notification.emf.NotificationModule;
import org.gervarro.democles.operation.RelationalOperationBuilder;
import org.gervarro.democles.operation.emf.DefaultEMFBatchAdornmentStrategy;
import org.gervarro.democles.operation.emf.DefaultEMFIncrementalAdornmentStrategy;
import org.gervarro.democles.operation.emf.EMFIdentifierProviderBuilder;
import org.gervarro.democles.operation.emf.EMFInterpretableIncrementalOperationBuilder;
import org.gervarro.democles.plan.incremental.builder.AdornedNativeOperationDrivenComponentBuilder;
import org.gervarro.democles.plan.incremental.builder.FilterComponentBuilder;
import org.gervarro.democles.plan.incremental.builder.PatternInvocationComponentBuilder;
import org.gervarro.democles.plan.incremental.leaf.ReteSearchPlanAlgorithm;
import org.gervarro.democles.runtime.AdornedNativeOperationBuilder;
import org.gervarro.democles.runtime.GenericOperationBuilder;
import org.gervarro.democles.runtime.InterpretableAdornedOperation;
import org.gervarro.democles.runtime.JavaIdentifierProvider;
import org.gervarro.democles.specification.emf.Constraint;
import org.gervarro.democles.specification.emf.ConstraintParameter;
import org.gervarro.democles.specification.emf.EMFPatternBuilder;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.PatternInvocationConstraint;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.Variable;
import org.gervarro.democles.specification.emf.constraint.EMFTypeModule;
import org.gervarro.democles.specification.emf.constraint.PatternInvocationTypeModule;
import org.gervarro.democles.specification.emf.constraint.RelationalTypeModule;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypeFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable;
import org.gervarro.democles.specification.emf.constraint.emf.emf.Reference;
import org.gervarro.democles.specification.impl.DefaultPattern;
import org.gervarro.democles.specification.impl.DefaultPatternBody;
import org.gervarro.democles.specification.impl.DefaultPatternFactory;
import org.gervarro.democles.specification.impl.PatternInvocationConstraintModule;

import language.TGG;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class DemoclesHelper implements MatchEventListener {
	private static final Logger logger = Logger.getLogger(DemoclesHelper.class);
	
	private ResourceSet rs;
	private Collection<Pattern> patterns;
	private HashMap<IDataFrame, IMatch> matches;
	private RetePatternMatcherModule retePatternMatcherModule;
	private EMFPatternBuilder<DefaultPattern, DefaultPatternBody> patternBuilder;
	private Collection<RetePattern> patternMatchers;
	protected OperationalStrategy app;
	private TGG tgg;
	private HashMap<IbexPattern, Pattern> patternMap;
	private boolean debug;
	
	// Factories
	private final SpecificationFactory factory = SpecificationFactory.eINSTANCE;
	private final EMFTypeFactory emfTypeFactory = EMFTypeFactory.eINSTANCE;
	// TODO for attributes
	// private final RelationalConstraintFactory relationalFactory = RelationalConstraintFactory.eINSTANCE;				
	
	public DemoclesHelper(ResourceSet rs, OperationalStrategy app, TGG tgg, boolean debug) throws IOException {
		this.rs = rs;
		patterns = new ArrayList<>();
		matches = new HashMap<>();
		patternMatchers = new ArrayList<>();
		this.app = app;
		this.tgg = tgg;
		patternMap = new HashMap<>();
		this.debug = debug;
		 
		init();
	}
	
	private void init() throws IOException {		
		// Create EMF-based pattern specification
		createDemoclesPatterns();
		if(debug)
			saveDemoclesPatterns();
		
		// Democles configuration
		final EMFInterpretableIncrementalOperationBuilder<VariableRuntime> 
			emfNativeOperationModule = configureDemocles();
		
		// Build the pattern matchers in 2 phases
		// 1) EMF-based to EMF-independent transformation
		final Collection<DefaultPattern> internalPatterns = patterns.stream()
				.map(patternBuilder::build)
				.collect(Collectors.toList());
		
		// 2) EMF-independent to pattern matcher runtime (i.e., Rete network) transformation
		internalPatterns.forEach(retePatternMatcherModule::build);
		
		retePatternMatcherModule.getSession().setAutoCommitMode(false);
		
		// Attach match listener to pattern matchers
		retrievePatternMatchers();
		patternMatchers.forEach(pm -> pm.addEventListener(this));
		
		// Install model event listeners on the resource set
		NotificationModule.installNotificationAdapter(rs, emfNativeOperationModule);
	}
	
	private void saveDemoclesPatterns() {
		Resource r = rs.createResource(URI.createPlatformResourceURI(tgg.getName() + "/patterns.xmi", true));
		r.getContents().addAll(patterns);
		try {
			r.save(null);
			rs.getResources().remove(r);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createDemoclesPatterns() {
		TGGCompiler compiler = new TGGCompiler(tgg);
		compiler.preparePatterns();

		for (TGGRule r : compiler.getRuleToPatternMap().keySet()) {
			for (IbexPattern pattern : compiler.getRuleToPatternMap().get(r)) {
				if(patternIsNotEmpty(pattern) && app.isPatternRelevant(pattern.getName()))
					ibexToDemocles(pattern);
			}
		}
	}

	private boolean patternIsNotEmpty(IbexPattern pattern) {
		return !pattern.getSignatureElements().isEmpty();
	}

	private Pattern ibexToDemocles(IbexPattern ibexPattern) {
		if(patternMap.containsKey(ibexPattern))
			return patternMap.get(ibexPattern);
		
		// Root pattern
		Pattern pattern = factory.createPattern();
		pattern.setName(ibexPattern.getName());
		PatternBody body = factory.createPatternBody();
		pattern.getBodies().add(body);
		
		// Parameters
		HashMap<TGGRuleNode, EMFVariable> nodeToVar = new HashMap<>();
		EList<Variable> parameters = pattern.getSymbolicParameters();
		
		// Signature elements
		for (TGGRuleElement element : ibexPattern.getSignatureElements()) {
			if (!nodeToVar.containsKey(element)) {
				if (element instanceof TGGRuleNode) {
					TGGRuleNode node = (TGGRuleNode) element;
					EMFVariable var = emfTypeFactory.createEMFVariable();
					var.setName(node.getName());
					var.setEClassifier(node.getType());
					nodeToVar.put(node, var);
				}
			}
			parameters.add(nodeToVar.get(element));				
		}
		
		// All other nodes
		EList<Variable> locals = body.getLocalVariables();
		for (TGGRuleNode node : ibexPattern.getBodyNodes()) {
			if (!nodeToVar.containsKey(node)) {
				EMFVariable var = emfTypeFactory.createEMFVariable();
				var.setName(node.getName());
				var.setEClassifier(node.getType());
				nodeToVar.put(node, var);
				locals.add(nodeToVar.get(node));
			}
		}
		
		// Constraints
		EList<Constraint> constraints = body.getConstraints();

		// Edges as constraints
		for (TGGRuleEdge edge : ibexPattern.getBodyEdges()) {
			Reference ref = emfTypeFactory.createReference();
			ref.setEModelElement(edge.getType());

			ConstraintParameter from = factory.createConstraintParameter();
			from.setReference(nodeToVar.get(edge.getSrcNode()));
			ref.getParameters().add(from);
			
			ConstraintParameter to = factory.createConstraintParameter();
			to.setReference(nodeToVar.get(edge.getTrgNode()));
			ref.getParameters().add(to);
			
			constraints.add(ref);
		}
		
		// Handle Corrs
		for (TGGRuleCorr corr : ibexPattern.getBodyCorrNodes()) {
			Reference srcRef = emfTypeFactory.createReference();
			srcRef.setEModelElement((EReference) corr.getType().getEStructuralFeature("source"));

			ConstraintParameter from = factory.createConstraintParameter();
			from.setReference(nodeToVar.get(corr));
			srcRef.getParameters().add(from);
			
			ConstraintParameter to = factory.createConstraintParameter();
			to.setReference(nodeToVar.get(corr.getSource()));
			srcRef.getParameters().add(to);
			
			constraints.add(srcRef);
			
			Reference trgRef = emfTypeFactory.createReference();
			trgRef.setEModelElement((EReference) corr.getType().getEStructuralFeature("target"));

			to = factory.createConstraintParameter();
			to.setReference(nodeToVar.get(corr));
			trgRef.getParameters().add(to);
			
			from = factory.createConstraintParameter();
			from.setReference(nodeToVar.get(corr.getTarget()));
			trgRef.getParameters().add(from);
			
			constraints.add(trgRef);

		}
		
		// Pattern invocations
		for (IbexPattern inv : ibexPattern.getPositiveInvocations()) {
			if (patternIsNotEmpty(inv)) {
				PatternInvocationConstraint invCon = createInvocationConstraint(inv, true, nodeToVar);
				if(!invCon.getParameters().isEmpty())
					constraints.add(invCon);
			}
		}
		
		for (IbexPattern inv : ibexPattern.getNegativeInvocations()) {
			if (patternIsNotEmpty(inv)) {
				PatternInvocationConstraint invCon = createInvocationConstraint(inv, false, nodeToVar);
				if(!invCon.getParameters().isEmpty())
					constraints.add(invCon);
			}
		}
		
		patternMap.put(ibexPattern, pattern);
		patterns.add(pattern);
		
		return pattern;
	}

	private PatternInvocationConstraint createInvocationConstraint(IbexPattern inv, boolean isTrue, HashMap<TGGRuleNode, EMFVariable> nodeToVar) {
		PatternInvocationConstraint invCon = factory.createPatternInvocationConstraint();
		invCon.setPositive(isTrue);
		invCon.setInvokedPattern(ibexToDemocles(inv));
		for (TGGRuleElement element : inv.getSignatureElements()) {
			if(nodeToVar.containsKey(element)){
				ConstraintParameter parameter = factory.createConstraintParameter();
				invCon.getParameters().add(parameter);
				parameter.setReference(nodeToVar.get(element));
			}
		}
		
		return invCon;
	}

	private void retrievePatternMatchers() {
		for (Pattern pattern : patterns) {
			if (app.isPatternRelevant(pattern.getName())){
				patternMatchers.add(retePatternMatcherModule.getPatternMatcher(getPatternID(pattern)));
			}
		}
	}

	private String getPatternID(Pattern pattern) {
		return PatternMatcherPlugin.getIdentifier(pattern.getName(), pattern.getSymbolicParameters().size());
	}

	private EMFInterpretableIncrementalOperationBuilder<VariableRuntime> configureDemocles() {
		final EMFConstraintModule emfTypeModule = new EMFConstraintModule(rs);
		final EMFTypeModule internalEMFTypeModule = new EMFTypeModule(emfTypeModule);
		final RelationalTypeModule internalRelationalTypeModule = new RelationalTypeModule(CoreConstraintModule.INSTANCE);
		
		patternBuilder = new EMFPatternBuilder<DefaultPattern, DefaultPatternBody>(new DefaultPatternFactory());
		final PatternInvocationConstraintModule<DefaultPattern, DefaultPatternBody> patternInvocationTypeModule = 
				new PatternInvocationConstraintModule<DefaultPattern, DefaultPatternBody>(patternBuilder);
		final PatternInvocationTypeModule<DefaultPattern, DefaultPatternBody> internalPatternInvocationTypeModule =
				new PatternInvocationTypeModule<DefaultPattern, DefaultPatternBody>(patternInvocationTypeModule);
		patternBuilder.addConstraintTypeSwitch(internalPatternInvocationTypeModule.getConstraintTypeSwitch());
		patternBuilder.addConstraintTypeSwitch(internalRelationalTypeModule.getConstraintTypeSwitch());
		patternBuilder.addConstraintTypeSwitch(internalEMFTypeModule.getConstraintTypeSwitch());
		patternBuilder.addVariableTypeSwitch(internalEMFTypeModule.getVariableTypeSwitch());
	
		retePatternMatcherModule = new RetePatternMatcherModule();
		// EMF native
		final EMFInterpretableIncrementalOperationBuilder<VariableRuntime> emfNativeOperationModule =
				new EMFInterpretableIncrementalOperationBuilder<VariableRuntime>(
						retePatternMatcherModule, emfTypeModule);
		// EMF batch
		final GenericOperationBuilder<VariableRuntime> emfBatchOperationModule =
				new GenericOperationBuilder<VariableRuntime>(emfNativeOperationModule,
						DefaultEMFBatchAdornmentStrategy.INSTANCE);
		final EMFIdentifierProviderBuilder<VariableRuntime> emfIdentifierProviderModule =
				new EMFIdentifierProviderBuilder<VariableRuntime>(
						JavaIdentifierProvider.INSTANCE);
		// Relational
		final ListOperationBuilder<InterpretableAdornedOperation,VariableRuntime> relationalOperationModule =
				new ListOperationBuilder<InterpretableAdornedOperation,VariableRuntime>(
						new RelationalOperationBuilder<VariableRuntime>());
		
		final ReteSearchPlanAlgorithm algorithm = new ReteSearchPlanAlgorithm();
		// EMF incremental
		final AdornedNativeOperationBuilder<VariableRuntime> emfIncrementalOperationModule =
				new AdornedNativeOperationBuilder<VariableRuntime>(
						emfNativeOperationModule,
						DefaultEMFIncrementalAdornmentStrategy.INSTANCE);
		// EMF component
		algorithm.addComponentBuilder(
				new AdornedNativeOperationDrivenComponentBuilder<VariableRuntime>(
						emfIncrementalOperationModule));
		// Relational component
		algorithm.addComponentBuilder(
				new FilterComponentBuilder<VariableRuntime>(relationalOperationModule));
		// Pattern invocation component
		algorithm.addComponentBuilder(
				new PatternInvocationComponentBuilder<VariableRuntime>(retePatternMatcherModule));
		retePatternMatcherModule.setSearchPlanAlgorithm(algorithm);
		retePatternMatcherModule.addOperationBuilder(emfBatchOperationModule);
		retePatternMatcherModule.addOperationBuilder(relationalOperationModule);
		retePatternMatcherModule.addIdentifierProviderBuilder(emfIdentifierProviderModule);
		return emfNativeOperationModule;
	}

	public void updateMatches() {
		// Trigger the Rete network
		retePatternMatcherModule.performIncrementalUpdates();
	}

	public void terminate() throws IOException {
		patternMatchers.forEach(pm -> pm.removeEventListener(this));
	}
	
	public void handleEvent(final MatchEvent event) {
		// React to events
		final String type = event.getEventType();
		final DataFrame frame = event.getMatching();
		
		Optional<Pattern> p = patterns.stream()
							.filter(pattern -> getPatternID(pattern).equals(event.getSource().toString()))
							.findAny();
		
		p.ifPresent(pattern -> {
			// React to create
			if (type.contentEquals(MatchEvent.INSERT)) {
				IMatch match = new IbexMatch(frame, pattern);
				matches.put(frame, match);
				//FIXME [anjorin] Better way of accessing rule name.
				app.addOperationalRuleMatch(PatternSuffixes.removeSuffix(pattern.getName()), match);
			}

			// React to delete
			if (type.equals(MatchEvent.DELETE)){
				app.removeOperationalRuleMatch(matches.get(frame));
				matches.remove(frame);
			}
		});
	}
}