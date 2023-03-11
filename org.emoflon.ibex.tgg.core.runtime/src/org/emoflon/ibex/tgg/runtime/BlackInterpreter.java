package org.emoflon.ibex.tgg.runtime;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.engine.MatchFilter;
import org.emoflon.ibex.common.engine.PatternMatchingEngine;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.SimpleTGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.modules.IMatchObserver;
import org.emoflon.ibex.tgg.runtime.strategies.modules.IbexExecutable;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;


/**
 * Interface for bidirectional graph transformations via TGGs
 */
public abstract class BlackInterpreter<EM> extends PatternMatchingEngine<TGGModel, EM, SimpleTGGMatch>  implements IMatchObserver{
	
	protected IbexExecutable executable;
	protected IbexOptions options;
	protected Registry registry;
	protected IMatchObserver matchObserver;
	protected Collection<Resource> observedResources = new LinkedList<>();
	
	public BlackInterpreter(TGGModel ibexModel, ResourceSet model) {
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
	protected MatchFilter<?, TGGModel, SimpleTGGMatch> createMatchFilter() {
		return null;
	}
	
	public void monitor(ResourceSet resourceSet) {
		monitor(resourceSet.getResources());
	}

	public void monitor(Collection<Resource> resources) {
		resources.forEach(this::monitor);
	}
	
	public abstract void monitor(Resource r);
	
	@Override
	public void terminate() {
		observedResources.clear();
	}
	
	public abstract ResourceSet createAndPrepareResourceSet(String path);
}
