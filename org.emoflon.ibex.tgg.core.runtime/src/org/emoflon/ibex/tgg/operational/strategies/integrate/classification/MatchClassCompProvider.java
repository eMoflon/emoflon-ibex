package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.Arrays;
import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent.CREATE_FilterNac;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent.DEL_Complete;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent.DEL_Corr;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent.DEL_OneSideIncompl;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent.DEL_OneSided;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent.DEL_Partly;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent.DEL_PartlyOneSided;

public class MatchClassCompProvider {

	private static final CREATE_FilterNac createFilterNac = new CREATE_FilterNac();
	private static final DEL_Complete delComplete = new DEL_Complete();
	private static final DEL_Corr delCorr = new DEL_Corr();
	private static final DEL_OneSided delOneSided = new DEL_OneSided();
	private static final DEL_OneSideIncompl delOneSideIncompl = new DEL_OneSideIncompl();
	private static final DEL_Partly delPartly = new DEL_Partly();
	private static final DEL_PartlyOneSided delPartlyOneSided = new DEL_PartlyOneSided();

	private static final List<MatchClassificationComponent> defaultMCCs = Arrays.asList( //
			delComplete, //
			delOneSided, //
			delCorr, //
			delOneSideIncompl, //
			delPartly, //
			delPartlyOneSided, //
			createFilterNac //
	);

	public static List<MatchClassificationComponent> getDefaultMCCs() {
		return defaultMCCs;
	}

	public static CREATE_FilterNac get_CREATE_FilterNac() {
		return createFilterNac;
	}

	public static DEL_Complete get_DEL_Complete() {
		return delComplete;
	}

	public static DEL_Corr get_DEL_Corr() {
		return delCorr;
	}

	public static DEL_OneSided get_DEL_OneSided() {
		return delOneSided;
	}

	public static DEL_OneSideIncompl get_DEL_OneSideIncompl() {
		return delOneSideIncompl;
	}

	public static DEL_Partly get_DEL_Partly() {
		return delPartly;
	}

	public static DEL_PartlyOneSided get_DEL_PartlyOneSided() {
		return delPartlyOneSided;
	}

}
