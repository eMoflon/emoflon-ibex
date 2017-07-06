package org.emoflon.moflontohenshin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;
import org.emoflon.moflontohenshin.manipulationrules.corrrules.CorrCreationRule;
import org.emoflon.moflontohenshin.manipulationrules.edgerules.EdgeCreationRule;
import org.emoflon.moflontohenshin.manipulationrules.noderules.NodeCreationRule;
import org.moflon.core.utilities.eMoflonEMFUtil;

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
	
	private String getPluginID(ResourceSet rs){
		Resource corr = rs.getResource(URI.createFileURI("model/MoflonToHenshin.ecore"), true);
		EPackage pcorr = (EPackage) corr.getContents().get(0);
		return pcorr.getNsPrefix();
	}
	
	public ManipulationHelper(){
		String pluginID = getPluginID(eMoflonEMFUtil.createDefaultResourceSet());
		
		ManipulationUtil.getInstance().addNodeCreationFun(this::createNode, pluginID);
		ManipulationUtil.getInstance().addEdgeCreationFun(src -> trg -> ref -> createEdge(src, trg, ref), pluginID);
		ManipulationUtil.getInstance().addCorrCreationFun(node -> src -> trg -> corrR -> createCorrNode(node, src, trg, corrR), pluginID);
	}
	
	private EObject createNode(TGGRuleNode node){
		for(NodeCreationRule rule : nodeCreationRules){
			if(rule.needsForcedCreation(node))
				return rule.forceCreation(node);
		}
		
		return ManipulationUtil.getInstance().defaultCreateNode(node);
	}
	
	private void createEdge(EObject src, EObject trg, EReference ref){
		for(EdgeCreationRule rule : edgeCreationRules){
			if(rule.needsForcedCreation(src, trg, ref)){
				rule.forceCreation(src, trg, ref);
				return;
			}
		}
		ManipulationUtil.getInstance().defaultCreateEdge(src, trg, ref);
	}
	
	private EObject createCorrNode(TGGRuleNode node, EObject src, EObject trg, Resource corrR){
		for(CorrCreationRule rule : corrCreationRules){
			if(rule.needsForcedCreation(node, src, trg, corrR))
				return rule.forceCreation(node, src, trg, corrR);
		}
		return ManipulationUtil.getInstance().defaultCreateCorr(node, src, trg, corrR);
		//return ManipulationUtil.getDefaultCorrCreationFun().apply(node).apply(src).apply(trg).apply(corrR);
	}
	
	
}
