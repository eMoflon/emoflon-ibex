package org.emoflon.ibex.tgg.operational.strategies.integrate.pattern;

import java.util.ArrayList;
import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.fragments.CREATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.fragments.DEL;
import org.emoflon.ibex.tgg.operational.strategies.integrate.fragments.DELAY;
import org.emoflon.ibex.tgg.operational.strategies.integrate.fragments.DELCorr;
import org.emoflon.ibex.tgg.operational.strategies.integrate.fragments.DELDelay;
import org.emoflon.ibex.tgg.operational.strategies.integrate.fragments.DELProp;

public class IntegrationPattern {

	private final List<IPComponent> components;

	public IntegrationPattern(List<IPComponent> components) {
		this.components = components;
	}

	public IntegrationPattern() {
		List<IPComponent> defaultPattern = new ArrayList<>();

		// TODO adrianm: set up default integration pattern
		IFContainer del = new IFContainer(new DefaultIFCStrategy());
		del.add(new CREATE());
		del.add(new DEL());
		del.add(new DELProp());
		del.add(new DELDelay());
		del.add(new DELCorr());
		del.add(new DELAY());
		defaultPattern.add(del);

		this.components = defaultPattern;
	}

	public List<IPComponent> getComponents() {
		return components;
	}
}
