package org.emoflon.ibex.tgg.operational.strategies.co;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.strategies.cc.CC;
import org.emoflon.ibex.tgg.operational.util.IMatch;
import org.emoflon.ibex.tgg.operational.util.IUpdatePolicy;
import org.emoflon.ibex.tgg.operational.util.RandomKernelMatchUpdatePolicy;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.set.hash.THashSet;
import gnu.trove.set.hash.TIntHashSet;
import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;
import language.TGGRuleCorr;
import language.TGGRuleNode;

public abstract class CO extends CC {

	public CO(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug, new RandomKernelMatchUpdatePolicy());
		RandomKernelMatchUpdatePolicy policy = (RandomKernelMatchUpdatePolicy)getUpdatePolicy();
		policy.setOptions(options);
		BasicConfigurator.configure();
	}

	@Override
	public boolean isPatternRelevant(String patternName) {
		return patternName.endsWith(PatternSuffixes.WHOLE);
	}
	
	@Override
	protected boolean manipulateCorr() {
		return false;
	}
	
	@Override
	public void run() throws IOException {
		engine.updateMatches();
		
		while (processOneOperationalRuleMatch());
		
		wrapUp();
	}
	
	@Override
	protected void wrapUp() {
		int[] ruleApplications = chooseTGGRuleApplications();
		
		  for (int v : ruleApplications) {
			   IMatch match = idToMatch.get(v < 0 ? -v : v);
			   HashMap<String, EObject> comatch = matchToCoMatch.get(match);
			   if (v < 0)
			    comatch.values().forEach(EcoreUtil::delete);
		  }
		  consistencyReporter.init(s, t, c, p, ruleInfos);
	}
		
		private int[] chooseTGGRuleApplications() {

		try {
			GRBEnv env = new GRBEnv("Gurobi_ILP.log");
			GRBModel model = new GRBModel(env);

			TIntObjectHashMap<GRBVar> gurobiVariables = defineGurobiVariables(model);
			
			defineGurobiImplications(model, gurobiVariables);
			
			defineGurobiExclusions(model, gurobiVariables);

			defineGurobiObjective(model, gurobiVariables);

			model.optimize();

			int[] result = new int[idToMatch.size()];

			idToMatch.keySet().forEach(v -> {
				try {
					if (gurobiVariables.get(v).get(GRB.DoubleAttr.X) > 0)
						result[v - 1] = v;
					else
						result[v - 1] = -v;
				} catch (GRBException e) {
					e.printStackTrace();
				}
				return true;
			});

			env.dispose();
			model.dispose();

			return result;

		} catch (GRBException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	public void loadModels() throws IOException {
		s = loadResource(projectPath + "/instances/src.xmi");
		t = loadResource(projectPath + "/instances/trg.xmi");
		c = loadResource(projectPath + "/instances/corr.xmi");
		p = createResource(projectPath + "/instances/protocol.xmi");

		EcoreUtil.resolveAll(rs);
	}
	
	@Override
	public boolean modelsAreConsistent() {
		return getInconsistentSrcNodes().size() + getInconsistentTrgNodes().size() + getInconsistentSrcEdges().size()
				+ getInconsistentTrgEdges().size() + getInconsistentCorrNodes().size() == 0;
	}
	
	public Collection<EObject> getInconsistentCorrNodes() {
		return consistencyReporter.getInconsistentCorrNodes();
	}
}


