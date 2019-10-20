package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.ProcessState;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

import language.TGGRuleNode;
import precedencegraph.Node;

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
		List<ConflictingElement> conflictingElements = new ArrayList<>();

		mismatch.getClassifiedElts().forEach((element, state) -> {
			if (state.equals(ProcessState.TO_BE_DELETED)) {
				List<Notification> conflictingAdditions = getConflictingAdditions(element);
				List<Notification> conflictingChanges = getConflictingChanges(element);
				if (!conflictingAdditions.isEmpty() || !conflictingChanges.isEmpty())
					conflictingElements.add(new ConflictingElement(element, conflictingAdditions, conflictingChanges));
			}
		});

		if (conflictingElements.isEmpty())
			return null;

		Set<TreeMap<IMatch, EObject>> deletionChains = determineDeletionChains(mismatch.getBrokenMatch());
		// TODO adrianm: continue here...

		return null; // return conflict
	}

	private Set<TreeMap<IMatch, EObject>> determineDeletionChains(IMatch match) {
		Set<TreeMap<IMatch, EObject>> deletionChains = new HashSet<>();

		AnalysedMatch analysedMatch = integrate.getAnalysedMatches().get(match);
		analysedMatch.getEObjectToNode().keySet().stream() //
				.filter(e -> analysedMatch.getAreRuleEltsDeleted().get(analysedMatch.getEObjectToNode().get(e))) //
				.forEach(delElt -> {
					TreeMap<IMatch, EObject> deletionChain = new TreeMap<>();
					deletionChain.put(match, delElt);
					deletionChains.add(concludeDeletionChain(deletionChain));
				});

		return deletionChains;
	}

	private TreeMap<IMatch, EObject> concludeDeletionChain(TreeMap<IMatch, EObject> deletionChain) {
		EObject nextElement = deletionChain.lastEntry().getValue().eContainer();
		if (nextElement == null)
			return deletionChain;

		Node lastNode = integrate.getEPG().getNode(deletionChain.lastKey());
		for (Node subNode : lastNode.getBasedOn()) {
			IMatch subMatch = integrate.getEPG().getMatch(subNode);
			if (!integrate.getAnalysedMatches().containsKey(subMatch))
				continue;

			AnalysedMatch analysedMatch = integrate.getAnalysedMatches().get(subMatch);
			TGGRuleNode ruleNode = analysedMatch.getEObjectToNode().get(nextElement);
			if (ruleNode == null)
				continue;

			if (analysedMatch.getAreRuleEltsDeleted().get(ruleNode)) {
				deletionChain.put(subMatch, nextElement);
				return concludeDeletionChain(deletionChain);
			}
		}

		return deletionChain;
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

}
