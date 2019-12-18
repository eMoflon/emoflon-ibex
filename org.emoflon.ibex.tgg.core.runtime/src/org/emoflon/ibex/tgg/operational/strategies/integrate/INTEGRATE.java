package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.delta.validation.DeltaValidator;
import org.emoflon.delta.validation.InvalidDeltaException;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.benchmark.BenchmarkLogger;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.AttributeRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.ShortcutRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.ExtOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.EltClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.ConflictDetector;
import org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph.ExtPrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol.GroupKey;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChanges;
import org.emoflon.ibex.tgg.operational.strategies.integrate.pattern.IntegrationPattern;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.BrokenMatchAnalyser;
import org.emoflon.ibex.tgg.operational.strategies.modules.IbexExecutable;

import delta.DeltaContainer;
import language.BindingType;
import language.TGGRuleCorr;
import runtime.TGGRuleApplication;

public final class INTEGRATE extends IbexExecutable {
	public INTEGRATE(IbexOptions options) throws IOException {
		strategy = new INTEGRATE_Op(this, options);
	}
	
	public class INTEGRATE_Op extends ExtOperationalStrategy {
		private IntegrationPattern pattern;
		private BrokenMatchAnalyser matchAnalyser;
		private ModelChangeProtocol modelChangeProtocol;
		private ConflictDetector conflictDetector;

		protected Resource epg;

		protected DeltaContainer userDeltaContainer;
		protected BiConsumer<EObject, EObject> userDeltaBiConsumer;
		protected GroupKey userDeltaKey;

		// Element classification
		private Map<EObject, EltClassifier> classifiedNodes;
		private Map<EMFEdge, EltClassifier> classifiedEdges;

		private Set<ITGGMatch> filterNacMatches;
		private Map<ITGGMatch, AnalysedMatch> analysedMatches;
		private Set<Mismatch> mismatches;

		public INTEGRATE_Op(IbexExecutable integrate, IbexOptions options) throws IOException {
			super(integrate, options);
			pattern = new IntegrationPattern();
			classifiedNodes = new HashMap<>();
			classifiedEdges = new HashMap<>();
			filterNacMatches = new HashSet<>();
			analysedMatches = new HashMap<>();
			mismatches = new HashSet<>();
			
			initIntegrateDependantTools();
		}

		public void integrate() throws IOException {
			run();
		}

		@Override
		public void run() throws IOException {
			matchDistributor.updateMatches();
			getEPG().update();
			modelChangeProtocol.attachAdapter();
			userDeltaKey = modelChangeProtocol.new GroupKey();
			modelChangeProtocol.setGroupKey(userDeltaKey);

			applyUserDelta();

			matchDistributor.updateMatches();

//			repair();
			deleteCorrsOfBrokenMatches();
			analyseAndClassifyMatches();
			detectAndResolveConflicts();

			modelChangeProtocol.unsetGroupKey();

			fillClassificationMaps();
			calculateIntegrationSolution();
			cleanUp();
		}

		protected void applyUserDelta() {
			if (userDeltaContainer != null) {
				modelChangeProtocol.util.applyUserDelta(userDeltaContainer);
				userDeltaContainer = null;
			} else if (userDeltaBiConsumer != null) {
				userDeltaBiConsumer.accept(resourceHandler.getSourceResource().getContents().get(0),
						resourceHandler.getTargetResource().getContents().get(0));
				userDeltaBiConsumer = null;
			}

		}

		protected void deleteCorrsOfBrokenMatches() {
			Map<TGGRuleApplication, ITGGMatch> processed = new HashMap<>();
			do {
				matchDistributor.updateMatches();
			} while (deleteCorrs(processed));
			brokenRuleApplications.putAll(processed);
		}

		protected void analyseAndClassifyMatches() {
			matchDistributor.updateMatches();
			getEPG().update();

			analysedMatches.clear();
			for (ITGGMatch brokenMatch : getBrokenMatches()) {
				AnalysedMatch analysedMatch = matchAnalyser.analyse(brokenMatch);
				analysedMatches.put(brokenMatch, analysedMatch);
			}

			mismatches.clear();
			for (AnalysedMatch am : getAnalysedMatches().values()) {
				for (MatchClassificationComponent mcc : pattern.getMCComponents()) {
					if (mcc.isApplicable(am)) {
						getMismatches().add(mcc.classify(this, am));
						break;
					}
				}
			}
		}

		protected void detectAndResolveConflicts() {
			conflictDetector.detectDeleteConflicts().forEach(conflict -> {
				options.getConflictSolver().resolveDeleteConflict(conflict).apply(this);
			});
		}

		protected void fillClassificationMaps() {
			mismatches.forEach(mm -> {
				classifiedNodes.putAll(mm.getClassifiedNodes());
				classifiedEdges.putAll(mm.getClassifiedEdges());
			});
		}

		protected void calculateIntegrationSolution() {
			// TODO adrianm: implement
		}

		private boolean deleteCorrs(Map<TGGRuleApplication, ITGGMatch> processed) {
			if (brokenRuleApplications.isEmpty())
				return false;
			getBrokenMatches().forEach(m -> deleteGreenCorr(m));
			processed.putAll(brokenRuleApplications);
			brokenRuleApplications.clear();
			return true;
		}

		/**
		 * Deletes all green correspondence elements.
		 * 
		 * @param match
		 */
		private void deleteGreenCorr(ITGGMatch match) {
			Set<EObject> nodesToRevoke = new HashSet<EObject>();
			Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();
			prepareGreenCorrDeletion(match, nodesToRevoke, edgesToRevoke);
			getIbexRedInterpreter().revoke(nodesToRevoke, edgesToRevoke);
		}

		/**
		 * <p>
		 * Determines green correspondence elements and adds them to the passed sets
		 * (nodesToRevoke & edgesToRevoke).
		 * </p>
		 * <p>
		 * To delete this elements call
		 * {@link IbexRedInterpreter#revoke(nodesToRevoke, edgesToRevoke)}.
		 * </p>
		 * 
		 * @param match
		 * @param nodesToRevoke
		 * @param edgesToRevoke
		 */
		private void prepareGreenCorrDeletion(ITGGMatch match, Set<EObject> nodesToRevoke, Set<EMFEdge> edgesToRevoke) {
			matchAnalyser.getRule(match.getRuleName()).getNodes().stream() //
					.filter(n -> (n instanceof TGGRuleCorr) && n.getBindingType().equals(BindingType.CREATE)) //
					.map(c -> (EObject) match.get(c.getName())) //
					.forEach(c -> getIbexRedInterpreter().revokeCorr(c, nodesToRevoke, edgesToRevoke));
		}

		protected void cleanUp() {
			modelChangeProtocol.detachAdapter();
			modelChangeProtocol = new ModelChangeProtocol(resourceHandler.getSourceResource(),
					resourceHandler.getTargetResource(), resourceHandler.getCorrResource());
			classifiedNodes = new HashMap<>();
			classifiedEdges = new HashMap<>();
			analysedMatches = new HashMap<>();
			mismatches = new HashSet<>();
		}

		@Override
		protected void initializeRepairStrategy(IbexOptions options) {
			if (!repairStrategies.isEmpty())
				return;

			if (options.repairUsingShortcutRules()) {
				repairStrategies.add(new ShortcutRepairStrategy(this));
			}
			if (options.repairAttributes()) {
				repairStrategies.add(new AttributeRepairStrategy(this));
			}
		}

		@Override
		protected IMatchContainer createMatchContainer() {
			return new ExtPrecedenceGraph(this);
		}

		@Override
		public Collection<PatternType> getPatternRelevantForCompiler() {
			Collection<PatternType> types = new LinkedList<>();
			types.add(PatternType.FWD);
			types.add(PatternType.BWD);
			types.add(PatternType.CONSISTENCY);
			types.add(PatternType.CC);
			types.add(PatternType.FILTER_NAC);
			return types;
		}

		@Override
		public boolean isPatternRelevantForInterpreter(PatternType type) {
			switch (type) {
			case FWD:
				return true;
			case BWD:
				return true;
			case CONSISTENCY:
				return true;
			case CC:
				return true;
			case FILTER_NAC:
				return true;
			default:
				return false;
			}
		}

		@Override
		public IGreenPattern revokes(ITGGMatch match) {
			// TODO adrianm: implement
			return super.revokes(match);
		}

		@Override
		public void addOperationalRuleMatch(ITGGMatch match) {
			if (match.getType() == PatternType.FILTER_NAC)
				filterNacMatches.add((ITGGMatch) match);
			else
				super.addOperationalRuleMatch(match);
		}

		@Override
		public boolean removeOperationalRuleMatch(ITGGMatch match) {
			if (match.getType() == PatternType.FILTER_NAC)
				return filterNacMatches.remove(match);
			else
				return super.removeOperationalRuleMatch(match);
		}

		public BrokenMatchAnalyser getMatchAnalyser() {
			return matchAnalyser;
		}

		public ExtPrecedenceGraph getEPG() {
			try {
				return (ExtPrecedenceGraph) operationalMatchContainer;
			} catch (Exception e) {
				throw new RuntimeException("ExtPrecedenceGraph implementation is needed", e);
			}
		}

		public ModelChangeProtocol getModelChangeProtocol() {
			return modelChangeProtocol;
		}

		public IRedInterpreter getRedInterpreter() {
			return redInterpreter;
		}

		public IbexRedInterpreter getIbexRedInterpreter() {
			try {
				return (IbexRedInterpreter) getRedInterpreter();
			} catch (Exception e) {
				throw new RuntimeException("IbexRedInterpreter implementation is needed", e);
			}
		}

		public Collection<ITGGMatch> getBrokenMatches() {
			return brokenRuleApplications.values();
		}

		public Map<EObject, EltClassifier> getClassifiedNodes() {
			return classifiedNodes;
		}

		public Map<EMFEdge, EltClassifier> getClassifiedEdges() {
			return classifiedEdges;
		}

		public Set<ITGGMatch> getFilterNacMatches() {
			return filterNacMatches;
		}

		public Map<ITGGMatch, AnalysedMatch> getAnalysedMatches() {
			return analysedMatches;
		}

		public Set<Mismatch> getMismatches() {
			return mismatches;
		}

		public ModelChanges getUserModelChanges() {
			return modelChangeProtocol.getModelChanges(userDeltaKey);
		}

		/**
		 * Applies a given delta to source and target models specified by a
		 * {@link BiConsumer} providing the source and target root elements.
		 * <p>
		 * Alternatively use {@link INTEGRATE#applyDelta(DeltaContainer)}.
		 * </p>
		 * 
		 * @param delta delta to be applied
		 */
		public void applyDelta(BiConsumer<EObject, EObject> delta) {
			this.userDeltaBiConsumer = delta;
		}

		/**
		 * Applies a given delta to source and target models specified by a
		 * {@link delta.DeltaContainer DeltaContainer}.
		 * <p>
		 * Alternatively use {@link INTEGRATE#applyDelta(BiConsumer)}.
		 * </p>
		 * 
		 * @param delta delta to be applied
		 * @throws InvalidDeltaException if a <code>Delta</code> of the given
		 *                               <code>DeltaContainer</code> has an invalid
		 *                               structure or invalid components
		 */
		public void applyDelta(DeltaContainer delta) throws InvalidDeltaException {
			DeltaValidator.validate(delta);
			this.userDeltaContainer = delta;
		}

		private void initIntegrateDependantTools() {
			matchAnalyser = new BrokenMatchAnalyser(this);
			modelChangeProtocol = new ModelChangeProtocol(resourceHandler.getSourceResource(), resourceHandler.getTargetResource(), resourceHandler.getCorrResource());
			conflictDetector = new ConflictDetector(this);
		}
	}
}


