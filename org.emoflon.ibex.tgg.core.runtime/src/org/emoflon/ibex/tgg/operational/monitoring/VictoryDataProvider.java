package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

import language.DomainType;
import language.TGGRule;
import language.TGGRuleNode;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

public class VictoryDataProvider implements IVictoryDataProvider {

	private final static Logger logger = Logger.getLogger(VictoryDataProvider.class);

	OperationalStrategy op;

	public VictoryDataProvider(OperationalStrategy pOperationalStrategy) {
		this.op = pOperationalStrategy;
	}

	@Override
	public TGGRule getRule(String pRuleName) {
		try {
			return op.getOptions().flattenedTGG().getRules().stream().filter(r -> r.getName().equals(pRuleName))
					.findFirst().get();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public Set<EObject> getMatchNeighbourhood(IMatch match, int k) {

		Resource srcR = op.getSourceResource();
		Resource trgR = op.getTargetResource();

		EObject trgCnt = trgR.getContents().get(0);
		EObject srcCnt = srcR.getContents().get(0);

		Set<EObject> list = new HashSet<EObject>();

		TGGRule rule = getRule(match.getRuleName());
		for (String p : match.getParameterNames()) {
			for (TGGRuleNode node : rule.getNodes()) {
				String nodeName = node.getName();
				if (nodeName.equals(p) && !node.getDomainType().equals(DomainType.CORR)) {
					if (k > 0) {
						if (srcCnt.toString().indexOf(node.getType().getName()) > 0) {
							getList(list, srcR.getContents(), k, 0);
						}

						if (trgCnt.toString().indexOf(node.getType().getName()) > 0) {
							getList(list, trgR.getContents(), k, 0);
						}
					} else {
						list.add((EObject) match.get(p));
					}
				}
			}
		}
		return list;
	}

	protected static void getList(Set<EObject> list, EList<EObject> content, int k, int i) {
		if (content.size() > 0) {
			i++;
			for (EObject x : content) {
				if (k >= i) {
					list.addAll(x.eContents());
				}
				getList(list, x.eContents(), k, i);
			}
		}
	}

	@Override
	public Set<IMatch> getMatches() {
		try {
			return op.getMatchContainer().getMatches();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public Set<IMatch> getMatches(String pRuleName) {
		try {
			return this.getMatches().stream().filter(r -> r.getRuleName().equals(pRuleName))
					.collect(Collectors.toSet());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public Set<IMatch> getMatches(IMatch match) {
		try {
			return this.getMatches(match.getRuleName());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
}
