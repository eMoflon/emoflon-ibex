package org.emoflon.moflontohenshin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;
import org.emoflon.moflontohenshin.manipulationrules.corrrules.CorrCreationRule;
import org.emoflon.moflontohenshin.manipulationrules.edgerules.EdgeCreationRule;
import org.emoflon.moflontohenshin.manipulationrules.noderules.NodeCreationRule;

import language.TGGRuleNode;

public class ManipulationHelper {

	private static List<NodeCreationRule> nodeCreationRules = new ArrayList<>();
	private static List<EdgeCreationRule> edgeCreationRules = new ArrayList<>();
	private static List<CorrCreationRule> corrCreationRules = new ArrayList<>();
	
	public static void addNodeCreationRule(NodeCreationRule rule){
		nodeCreationRules.add(rule);
	}
	
	public static void addEdgeCreationRule(EdgeCreationRule rule){
		edgeCreationRules.add(rule);
	}
	
	public static void addCorrCreationRule(CorrCreationRule rule){
		corrCreationRules.add(rule);
	}
	
	public ManipulationHelper(){
		ManipulationUtil.setNodeCreationFun(this::createNode);
		ManipulationUtil.setEdgeCreationFun(src -> trg -> ref -> createEdge(src, trg, ref));
		ManipulationUtil.setCorrCreationFun(node -> src -> trg -> corrR -> createCorrNode(node, src, trg, corrR));
	}
	
	private EObject createNode(TGGRuleNode node){
		for(NodeCreationRule rule : nodeCreationRules){
			if(rule.needsForcedCreation(node))
				return rule.forceCreation(node);
		}
		
		return ManipulationUtil.getDefaultNodeCreationFun().apply(node);
	}
	
	private void createEdge(EObject src, EObject trg, EReference ref){
		for(EdgeCreationRule rule : edgeCreationRules){
			if(rule.needsForcedCreation(src, trg, ref)){
				rule.forceCreation(src, trg, ref);
				return;
			}
		}
		
		ManipulationUtil.getDefaultEdgeCreationFun().apply(src).apply(trg).accept(ref);
	}
	
	private EObject createCorrNode(TGGRuleNode node, EObject src, EObject trg, Resource corrR){
		for(CorrCreationRule rule : corrCreationRules){
			if(rule.needsForcedCreation(node, src, trg, corrR))
				return rule.forceCreation(node, src, trg, corrR);
		}
		return ManipulationUtil.getDefaultCorrCreationFun().apply(node).apply(src).apply(trg).apply(corrR);
	}
	
	
}
