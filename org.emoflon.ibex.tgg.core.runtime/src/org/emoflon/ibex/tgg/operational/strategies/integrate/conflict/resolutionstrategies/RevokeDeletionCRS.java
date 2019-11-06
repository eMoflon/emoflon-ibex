package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph.ExtPrecedenceGraph;

public class RevokeDeletionCRS extends DeleteConflictResStrategy {

	protected Set<ITGGMatch> restored;

	public RevokeDeletionCRS(DeleteConflict conflict, ConflResStratToken token) {
		super(conflict, token);
	}

	@Override
	public List<Notification> apply(INTEGRATE integrate) {
		restored = new HashSet<>();
		List<Notification> undos = new ArrayList<>();

		conflict.getDeletionChain().foreach(m -> {
			undos.addAll(restoreMatch(integrate, m));
			restored.add(m);
			undos.addAll(restoreMatchesBasedOn(integrate, m));
		});

		return undos;
	}

	protected List<Notification> restoreMatchesBasedOn(INTEGRATE integrate, ITGGMatch match) {
		List<Notification> undos = new ArrayList<>();

		ExtPrecedenceGraph epg = integrate.getEPG();
		epg.getNode(match).getBaseFor().forEach(n -> {
			if (n.isBroken()) {
				ITGGMatch m = epg.getMatch(n);
				if (!restored.contains(m)) {
					undos.addAll(restoreMatch(integrate, m));
					restored.add(m);
					undos.addAll(restoreMatchesBasedOn(integrate, m));
				}
			}
		});

		return undos;
	}

}
