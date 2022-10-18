package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.emoflon.ibex.gt.api.IBeXGtAPI;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;

public abstract class IBeXGTCoPattern<CP extends IBeXGTCoPattern<CP, CM, R, P, M>, CM extends IBeXGTCoMatch<CM, CP, R, P, M>, R extends IBeXGTRule<R, P, M, CP, CM>, P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>>
		extends IBeXGTPattern<CP, CM> {

	final protected R typedRule;

	public IBeXGTCoPattern(final IBeXGtAPI<?, ?, ?> api, final R typedRule, final GTPattern pattern) {
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
	public boolean hasCountExpressions() {
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
	public void subscribeAppearing(final Consumer<CM> action) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	public void unsubscribeAppearing(final Consumer<CM> action) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	public void subscribeDisappearing(final Consumer<CM> action) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	public void unsubscribeDisappearing(final Consumer<CM> action) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	public void subscribeMatchDisappears(final CM match, final Consumer<CM> action) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	public void unsubscribeMatchDisappears(final CM match, final Consumer<CM> action) {
		throw new UnsupportedOperationException("Co patterns do not support the use of callbacks.");
	}

	@Override
	protected Collection<CM> getMatches() {
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

}
