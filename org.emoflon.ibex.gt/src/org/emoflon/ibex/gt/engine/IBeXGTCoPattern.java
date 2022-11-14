package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.gt.api.IBeXGtAPI;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog;

public abstract class IBeXGTCoPattern<CP extends IBeXGTCoPattern<CP, CM, R, P, M>, CM extends IBeXGTCoMatch<CM, CP, R, P, M>, R extends IBeXGTRule<R, P, M, CP, CM>, P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>>
		extends IBeXGTPattern<CP, CM> {

	final public R typedRule;

	public IBeXGTCoPattern(final IBeXGtAPI<? extends IBeXGTPatternMatcher<?>, ?, ?> api, final R typedRule,
			final GTPattern pattern) {
		super(api, pattern);
		this.typedRule = typedRule;
		patternMatcher.unRegisterTypedPattern(this);
	}

	@Override
	public boolean checkBindings(final CM match) {
		return true;
	}

	@Override
	public boolean checkConditions(final CM match) {
		return true;
	}

	@Override
	public boolean hasArithmeticExpressions() {
		return false;
	}

	@Override
	public boolean hasBooleanExpressions() {
		return false;
	}

	@Override
	public boolean hasCountExpressions() {
		return false;
	}

	@Override
	public boolean hasParameterExpressions() {
		return false;
	}

	@Override
	public Collection<CM> getMatches(boolean doUpdate) {
		throw new UnsupportedOperationException("Co patterns do not have any matches.");
	}

	@Override
	public Stream<CM> matchStream(boolean doUpdate) {
		throw new UnsupportedOperationException("Co patterns do not have any matches.");
	}

	@Override
	public boolean hasMatches(boolean doUpdate) {
		throw new UnsupportedOperationException("Co patterns do not have any matches.");
	}

	@Override
	public long countMatches(boolean doUpdate) {
		throw new UnsupportedOperationException("Co patterns do not support the count match operation.");
	}

	@Override
	public IBeXGTPattern<CP, CM> subscribeAppearing(final Consumer<CM> action) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	public IBeXGTPattern<CP, CM> unsubscribeAppearing(final Consumer<CM> action) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	public IBeXGTPattern<CP, CM> subscribeDisappearing(final Consumer<CM> action) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	public IBeXGTPattern<CP, CM> unsubscribeDisappearing(final Consumer<CM> action) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	public IBeXGTPattern<CP, CM> subscribeMatchDisappears(final CM match, final Consumer<CM> action) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	public IBeXGTPattern<CP, CM> unsubscribeMatchDisappears(final CM match, final Consumer<CM> action) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	protected Collection<CM> getUnfilteredMatches() {
		throw new UnsupportedOperationException("Co patterns do not have any matches.");
	}

	@Override
	protected Collection<CM> getPendingMatches() {
		throw new UnsupportedOperationException("Co patterns do not have any matches.");
	}

	@Override
	protected Collection<CM> getFilteredMatches() {
		throw new UnsupportedOperationException("Co patterns do not have any matches.");
	}

	@Override
	protected Collection<CM> getAddedMatches() {
		throw new UnsupportedOperationException("Co patterns do not have any matches.");
	}

	@Override
	protected Collection<CM> getRemovedMatches() {
		throw new UnsupportedOperationException("Co patterns do not have any matches.");
	}

	@Override
	public Consumer<IBeXGTMatch<?, ?>> toIMatchConsumer(final Consumer<CM> consumer) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	protected void registerWatchDogs(Collection<GTWatchDog> watchDogs) {
		return; // Do nothing
	}

	@Override
	protected void terminate() {
		return; // Do nothing
	}

	@Override
	protected Set<EObject> insertNodesAndMatch(final CM match) {
		throw new UnsupportedOperationException("Co patterns do not support the use of watch-dogs.");
	}

}
