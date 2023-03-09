package org.emoflon.ibex.tgg.runtime;

import java.util.Collection;

import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.engine.IBeXPMEngineInformation;
import org.emoflon.ibex.common.engine.IMatch;
import org.emoflon.ibex.common.engine.MatchFilter;
import org.emoflon.ibex.common.engine.PatternMatchingEngine;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.SimpleTGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.modules.IMatchObserver;
import org.emoflon.ibex.tgg.runtime.strategies.modules.IbexExecutable;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGPMConfig;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;

/**
 * Interface for bidirectional graph transformations via TGGs
 */
public abstract class BlackInterpreter<EM> extends PatternMatchingEngine<TGGModel, EM, SimpleTGGMatch>  implements IMatchObserver{
	public BlackInterpreter(TGGModel ibexModel, ResourceSet model, TGGPMConfig config) {
		super(ibexModel, model);
	}

	@Override
	protected Collection<SimpleTGGMatch> insertNewMatchCollection(String patternName) {
		return null;
	}

	@Override
	protected Collection<SimpleTGGMatch> insertNewPendingMatchCollection(String patternName) {
		return null;
	}

	@Override
	protected Collection<SimpleTGGMatch> insertNewFilteredMatchCollection(String patternName) {
		return null;
	}

	@Override
	protected Collection<SimpleTGGMatch> insertNewAddedMatchCollection(String patternName) {
		return null;
	}

	@Override
	protected Collection<SimpleTGGMatch> insertNewRemovedMatchCollection(String patternName) {
		return null;
	}

	@Override
	protected void initialize() {
	}
	
	public abstract void initialize(IbexExecutable executable, final IbexOptions options, Registry registry, IMatchObserver matchObserver);

	@Override
	protected void fetchMatches() {
		
	}

	@Override
	public SimpleTGGMatch transformToIMatch(EM match) {
		return null;
	}

	@Override
	protected MatchFilter<?, TGGModel, SimpleTGGMatch> createMatchFilter() {
		return null;
	}

	@Override
	protected IBeXPMEngineInformation createEngineProperties() {
		return null;
	}

	@Override
	public void terminate() {
		
	}

	@Override
	public void addMatch(IMatch match) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMatches(Collection<IMatch> matches) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMatch(IMatch match) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMatches(Collection<IMatch> matches) {
		// TODO Auto-generated method stub
		
	}
}
