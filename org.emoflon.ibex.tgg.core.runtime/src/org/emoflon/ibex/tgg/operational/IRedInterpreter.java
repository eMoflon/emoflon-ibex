package org.emoflon.ibex.tgg.operational;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

public interface IRedInterpreter {

	void revokeOperationalRule(ITGGMatch match);
	void revoke(final Set<EObject> nodesToRevoke, final Set<EMFEdge> edgesToRevoke);

	int getNumOfDeletedNodes();
	
	int getNumOfDeletedCorrNodes();
}
