package org.emoflon.ibex.tgg.runtime.strategies.integrate.classification;

import static org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.DomainModification.COMPL_DEL;
import static org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.DomainModification.PART_DEL;
import static org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.DomainModification.UNCHANGED;
import static org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.DomainModification.UNSPECIFIED;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum DeletionType {

	/**
	 * No created elements are deleted.
	 */
	NOTHING(new DeletionPattern(UNCHANGED, UNSPECIFIED, UNCHANGED, UNSPECIFIED, UNCHANGED, UNSPECIFIED)),

	/**
	 * Created elements are completely deleted in source <i>and</i> target domain.
	 */
	COMPLETELY(new DeletionPattern(COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED)),

	/**
	 * Context correspondence elements are completely deleted.
	 */
	CORR_FULL(new DeletionPattern(UNCHANGED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNCHANGED, UNSPECIFIED)),

	/**
	 * Created elements are completely deleted in source domain and not deleted in target domain.
	 */
	SRC_FULL_TRG_NOT(new DeletionPattern(COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNCHANGED, UNSPECIFIED)),

	/**
	 * Created elements are not deleted in source domain and completely deleted in target domain.
	 */
	SRC_NOT_TRG_FULL(new DeletionPattern(UNCHANGED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED)),

	/**
	 * Created elements are completely deleted in source domain and partly deleted in target domain.
	 */
	SRC_FULL_TRG_PARTLY(new DeletionPattern(COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED)),

	/**
	 * Created elements are partly deleted in source domain and completely deleted in target domain.
	 */
	SRC_PARTLY_TRG_FULL(new DeletionPattern(PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED)),

	/**
	 * Created elements are partly deleted in source domain and not deleted in target domain.
	 */
	SRC_PARTLY_TRG_NOT(new DeletionPattern(PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNCHANGED, UNSPECIFIED)),

	/**
	 * Created elements are not deleted in source domain and partly deleted in target domain.
	 */
	SRC_NOT_TRG_PARTLY(new DeletionPattern(UNCHANGED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED)),

	/**
	 * Created elements are partly deleted in source <i>and</i> target domain.
	 */
	SRC_PARTLY_TRG_PARTLY(new DeletionPattern(PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED));

	private DeletionPattern pattern;

	private DeletionType(DeletionPattern pattern) {
		this.pattern = pattern;
	}

	public boolean isType(ClassifiedMatch brokenMatch) {
		return pattern.matches(brokenMatch.getDeletionPattern());
	}

	public static final List<DeletionType> defaultTypes = Collections.unmodifiableList(Arrays.asList( //
			NOTHING, COMPLETELY, SRC_FULL_TRG_NOT, SRC_NOT_TRG_FULL, SRC_FULL_TRG_PARTLY, //
			SRC_PARTLY_TRG_FULL, SRC_PARTLY_TRG_NOT, SRC_NOT_TRG_PARTLY, SRC_PARTLY_TRG_PARTLY));

	public static final Set<DeletionType> shortcutCCCandidates = Collections.unmodifiableSet(new HashSet<>(Arrays.asList( //
			SRC_PARTLY_TRG_PARTLY, SRC_PARTLY_TRG_FULL, SRC_FULL_TRG_PARTLY)));

	public static final Set<DeletionType> shortcutPropCandidates = Collections.unmodifiableSet(new HashSet<>(Arrays.asList( //
			SRC_PARTLY_TRG_NOT, SRC_NOT_TRG_PARTLY)));

	public static final Set<DeletionType> propFWDCandidates = Collections.unmodifiableSet(new HashSet<>(Arrays.asList( //
			SRC_PARTLY_TRG_NOT, SRC_FULL_TRG_NOT)));

	public static final Set<DeletionType> propBWDCandidates = Collections.unmodifiableSet(new HashSet<>(Arrays.asList( //
			SRC_NOT_TRG_PARTLY, SRC_NOT_TRG_FULL)));

	public static final Set<DeletionType> inconsDelsCandidates = Collections.unmodifiableSet(new HashSet<>(Arrays.asList( //
			SRC_FULL_TRG_PARTLY, SRC_PARTLY_TRG_FULL, SRC_PARTLY_TRG_NOT, SRC_NOT_TRG_PARTLY, SRC_PARTLY_TRG_PARTLY)));

	public static final Set<DeletionType> srcDelCandidates = Collections.unmodifiableSet(new HashSet<>(Arrays.asList( //
			COMPLETELY, SRC_FULL_TRG_NOT, SRC_FULL_TRG_PARTLY, //
			SRC_PARTLY_TRG_FULL, SRC_PARTLY_TRG_NOT, SRC_PARTLY_TRG_PARTLY)));

	public static final Set<DeletionType> trgDelCandidates = Collections.unmodifiableSet(new HashSet<>(Arrays.asList( //
			COMPLETELY, SRC_NOT_TRG_FULL, SRC_FULL_TRG_PARTLY, //
			SRC_PARTLY_TRG_FULL, SRC_NOT_TRG_PARTLY, SRC_PARTLY_TRG_PARTLY)));

}
