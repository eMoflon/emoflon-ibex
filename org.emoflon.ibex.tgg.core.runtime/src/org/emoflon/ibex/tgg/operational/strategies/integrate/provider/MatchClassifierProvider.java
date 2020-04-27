package org.emoflon.ibex.tgg.operational.strategies.integrate.provider;

import java.util.Arrays;
import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier.CREATE_FilterNac;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier.DEL_Complete;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier.DEL_Corr;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier.DEL_OneSideIncompl;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier.DEL_OneSided;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier.DEL_Partly;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier.DEL_PartlyOneSided;

public class MatchClassifierProvider {

	private static final CREATE_FilterNac CREATE_FILTER_NAC = new CREATE_FilterNac();
	private static final DEL_Complete DEL_COMPLETE = new DEL_Complete();
	private static final DEL_Corr DEL_CORR = new DEL_Corr();
	private static final DEL_OneSided DEL_ONESIDED = new DEL_OneSided();
	private static final DEL_OneSideIncompl DEL_ONESIDE_INCOMPL = new DEL_OneSideIncompl();
	private static final DEL_Partly DEL_PARTLY = new DEL_Partly();
	private static final DEL_PartlyOneSided DEL_PARTLY_ONESIDED = new DEL_PartlyOneSided();

	private static final List<MatchClassifier> DEFAULT_MATCH_CLASSIFIER = Arrays.asList( //
			DEL_COMPLETE, //
			DEL_ONESIDED, //
			DEL_CORR, //
			DEL_ONESIDE_INCOMPL, //
			DEL_PARTLY, //
			DEL_PARTLY_ONESIDED, //
			CREATE_FILTER_NAC //
	);

	public static List<MatchClassifier> getDefaultMatchClassifier() {
		return DEFAULT_MATCH_CLASSIFIER;
	}

	public static CREATE_FilterNac get_CREATE_FilterNac() {
		return CREATE_FILTER_NAC;
	}

	public static DEL_Complete get_DEL_Complete() {
		return DEL_COMPLETE;
	}

	public static DEL_Corr get_DEL_Corr() {
		return DEL_CORR;
	}

	public static DEL_OneSided get_DEL_OneSided() {
		return DEL_ONESIDED;
	}

	public static DEL_OneSideIncompl get_DEL_OneSideIncompl() {
		return DEL_ONESIDE_INCOMPL;
	}

	public static DEL_Partly get_DEL_Partly() {
		return DEL_PARTLY;
	}

	public static DEL_PartlyOneSided get_DEL_PartlyOneSided() {
		return DEL_PARTLY_ONESIDED;
	}

}
