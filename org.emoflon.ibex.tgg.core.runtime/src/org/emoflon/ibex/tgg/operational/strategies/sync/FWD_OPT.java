package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.OPT;

import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.set.hash.THashSet;
import gnu.trove.set.hash.TIntHashSet;
import language.TGGRuleCorr;
import language.TGGRuleNode;

public abstract class FWD_OPT extends OPT {
	
	public FWD_OPT(IbexOptions options) throws IOException {
		super(options);
	}
	
	@Override
	public void loadModels() throws IOException {
		s = loadResource(projectPath + "/instances/src.xmi");
		t = createResource(projectPath + "/instances/trg.xmi");
		c = createResource(projectPath + "/instances/corr.xmi");
		p = createResource(projectPath + "/instances/protocol.xmi");

		EcoreUtil.resolveAll(rs);
	}
	
	@Override
	protected void wrapUp() {
		ArrayList<EObject> objectsToDelete = new ArrayList<EObject>();
		
		  for (int v : chooseTGGRuleApplications()) {
			  int id = v < 0 ? -v : v;
			  IMatch comatch = idToMatch.get(id);
			   if (v < 0) {
					for (TGGRuleCorr createdCorr : getGreenFactory(matchIdToRuleName.get(id)).getGreenCorrNodesInRule())
						objectsToDelete.add((EObject) comatch.get(createdCorr.getName()));
					
					for (TGGRuleNode createdTrgNode : getGreenFactory(matchIdToRuleName.get(id)).getGreenTrgNodesInRule())
						objectsToDelete.add((EObject) comatch.get(createdTrgNode.getName()));
					
					objectsToDelete.add(getRuleApplicationNode(comatch));
				}
		  }
		  
		  EcoreUtil.deleteAll(objectsToDelete, true);
		  consistencyReporter.initSrc(this);
	}
	
	@Override
	public boolean isPatternRelevantForCompiler(String patternName) {
		return patternName.endsWith(PatternSuffixes.FWD_OPT);
	}
	
	@Override
	public boolean isPatternRelevantForInterpreter(String patternName) {
		return patternName.endsWith(PatternSuffixes.FWD_OPT);
	}
	
	@Override
	protected void prepareMarkerCreation(IGreenPattern greenPattern, IMatch comatch, String ruleName) {
		
		idToMatch.put(idCounter, comatch);
		matchIdToRuleName.put(idCounter, ruleName);

		int weight = 
				getGreenFactory(ruleName).getGreenSrcEdgesInRule().size() + 
				getGreenFactory(ruleName).getGreenSrcNodesInRule().size();

		weights.put(idCounter, weight);

		getGreenNodes(comatch, ruleName).forEach(e -> {
			if (!nodeToMarkingMatches.containsKey(e))
				nodeToMarkingMatches.put(e, new TIntHashSet());
			nodeToMarkingMatches.get(e).add(idCounter);
		});

		getGreenEdges(comatch, ruleName).forEach(e -> {
			if (!edgeToMarkingMatches.containsKey(e)) {
				edgeToMarkingMatches.put(e, new TIntHashSet());
			}
			edgeToMarkingMatches.get(e).add(idCounter);
		});

		matchToContextNodes.put(idCounter, new THashSet<>());
		matchToContextNodes.get(idCounter).addAll(getBlackNodes(comatch, ruleName));

		matchToContextEdges.put(idCounter, new TCustomHashSet<RuntimeEdge>(new RuntimeEdgeHashingStrategy()));
		matchToContextEdges.get(idCounter).addAll(getBlackEdges(comatch, ruleName));
		
		handleBundles(comatch, ruleName);

		idCounter++;
	}
	
	@Override
	public void saveModels() throws IOException {
	 	p.save(null);
		
	 	// Unrelax the metamodel
		unrelaxReferences(options.tgg().getTrg());
		
		// Remove adapters to avoid problems with notifications
		t.eAdapters().clear();
		t.getAllContents().forEachRemaining(o -> o.eAdapters().clear());
		
		// Copy and fix the model in the process
		FixingCopier.fixAll(t, c, "target");
		
		// Now save fixed models
		t.save(null);
		c.save(null);
	}
	
	public void forward() throws IOException {
		run();
	}
	
	@Override
	public void run() throws IOException {	
 
		do {
			blackInterpreter.updateMatches();
		} while (processOneOperationalRuleMatch());

		wrapUp();
	}
	
	@Override
	public void loadTGG() throws IOException {
		super.loadTGG();
		relaxReferences(options.tgg().getTrg());
	}
}

