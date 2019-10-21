package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.ProcessState;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

import language.BindingType;
import language.TGGRuleElement;

public class ConflictDetector {

	private INTEGRATE integrate;

	public ConflictDetector(INTEGRATE integrate) {
		this.integrate = integrate;
	}

	public List<DeleteConflict> detectDeleteConflicts() {
		List<DeleteConflict> conflicts = new ArrayList<>();
		for (Mismatch mismatch : integrate.getMismatches()) {
			DeleteConflict possibleConflict = detectDeleteConflict(mismatch);
			if (possibleConflict != null)
				conflicts.add(possibleConflict);
		}
		return conflicts;
	}

	private DeleteConflict detectDeleteConflict(Mismatch mismatch) {
		Set<ConflictingElement> conflictingElements = new HashSet<>();

		mismatch.getClassifiedElts().forEach((element, state) -> {
			if (state.equals(ProcessState.TO_BE_DELETED)) {
				List<Notification> conflAdditions = getConflictingAdditions(element);
				List<Notification> conflChanges = getConflictingChanges(element);
				List<Notification> conflCrossRefs = getConflictingCrossRefs(element);
				if (!conflAdditions.isEmpty() || !conflChanges.isEmpty() || !conflCrossRefs.isEmpty())
					conflictingElements
							.add(new ConflictingElement(element, conflAdditions, conflChanges, conflCrossRefs));
			}
		});

		if (conflictingElements.isEmpty())
			return null;

		Set<DeletionChain> deletionChains = determineDeletionChains(mismatch.getBrokenMatch());

		return new DeleteConflict(mismatch.getBrokenMatch(), conflictingElements, deletionChains);
	}

	private Set<DeletionChain> determineDeletionChains(IMatch match) {
		AnalysedMatch analysedMatch = integrate.getAnalysedMatches().get(match);
		return analysedMatch.getEObjectToNode().keySet().stream() //
				.filter(e -> {
					TGGRuleElement node = analysedMatch.getEObjectToNode().get(e);
					return analysedMatch.isRuleEltDeleted(node) && node.getBindingType().equals(BindingType.CREATE);
				}) //
				.map(delElt -> new DeletionChain(integrate, new ImmutablePair<>(match, delElt))) //
				.collect(Collectors.toSet());
	}

	private List<Notification> getConflictingAdditions(EObject element) {
		Map<Object, List<Notification>> added = integrate.getModelChangeProtocol().getAdded();
		if (added.containsKey(element))
			return new ArrayList<>(added.get(element));
		return new ArrayList<>();
	}

	private List<Notification> getConflictingChanges(EObject element) {
		Map<Object, List<Notification>> changed = integrate.getModelChangeProtocol().getChanged();
		if (changed.containsKey(element))
			return new ArrayList<>(changed.get(element));
		return new ArrayList<>();
	}

	private List<Notification> getConflictingCrossRefs(EObject element) {
		List<Notification> crossRefs = new ArrayList<>();
		integrate.getModelChangeProtocol().getAdded().forEach((e, l) -> l.forEach(n -> {
			if (element.equals(n.getNewValue()))
				crossRefs.add(n);
		}));
		integrate.getModelChangeProtocol().getChanged().forEach((e, l) -> l.forEach(n -> {
			if (element.equals(n.getNewValue()))
				crossRefs.add(n);
		}));
		return crossRefs;
		// TODO adrianm: include cross refs not captured by eContentAdapter
	}

}
