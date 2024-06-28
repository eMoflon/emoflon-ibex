package org.emoflon.ibex.tgg.operational.strategies.integrate.pattern;

import java.io.IOException;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;

public interface IntegrationFragment {

	void apply(INTEGRATE integrate) throws IOException;

}
