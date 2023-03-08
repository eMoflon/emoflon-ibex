package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.analysis.FilterNACCandidate;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuleApplication;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

public class TGGPatternUtil {
	public static final String protocolNodeSuffix = "_eMoflon_ProtocolNode";

	public static String getProtocolNodeName(String ruleName) {
		return ruleName + protocolNodeSuffix;
	}

	public final static boolean isAttrNode(String nodeName) {
		return nodeName.split("__").length == 3;
	}

	public static String generateFWDBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.FWD;
		PatternUtil.registerPattern(patternName, PatternType.FWD);
		return patternName;
	}
	
	public static String generateSRCBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.SRC;
		PatternUtil.registerPattern(patternName, PatternType.SRC);
		return patternName;
	}
	
	public static String generateTRGBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.TRG;
		PatternUtil.registerPattern(patternName, PatternType.TRG);
		return patternName;
	}
	
	public static String generateGREENCORRBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.GREENCORR;
		PatternUtil.registerPattern(patternName, PatternType.GREENCORR);
		return patternName;
	}
	
	public static String generateFWD_GREENCORRBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.FWD_GREENCORR;
		PatternUtil.registerPattern(patternName, PatternType.FWD_GREENCORR);
		return patternName;
	}
	
	public static String generateBWD_GREENCORRBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.BWD_GREENCORR;
		PatternUtil.registerPattern(patternName, PatternType.BWD_GREENCORR);
		return patternName;
	}

	public static String generateBWDBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.BWD;
		PatternUtil.registerPattern(patternName, PatternType.BWD);
		return patternName;
	}

	public static String generateCOBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.CO;
		PatternUtil.registerPattern(patternName, PatternType.CO);
		return patternName;
	}

	public static String generateCCBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.CC;
		PatternUtil.registerPattern(patternName, PatternType.CC);
		return patternName;
	}

	public static String generateGENBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.GEN;
		PatternUtil.registerPattern(patternName, PatternType.GEN);
		return patternName;
	}

	public static String generateFWDOptBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.FWD_OPT;
		PatternUtil.registerPattern(patternName, PatternType.FWD_OPT);
		return patternName;
	}

	public static String generateBWDOptBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.BWD_OPT;
		PatternUtil.registerPattern(patternName, PatternType.BWD_OPT);
		return patternName;
	}
	
	public static String generateGenForCCBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.GENForCC;
		PatternUtil.registerPattern(patternName, PatternType.GENForCC);
		return patternName;
	}
	
	public static String generateGenForCOBlackPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.GENForCO;
		PatternUtil.registerPattern(patternName, PatternType.GENForCO);
		return patternName;
	}

	public static String getAxiomNACPatternName(String ruleName, String nacName) {
		String patternName = ruleName + PatternSuffixes.SEP + nacName + PatternSuffixes.GEN_AXIOM_NAC;
		PatternUtil.registerPattern(patternName, PatternType.GEN_AXIOM_NAC);
		return patternName;
	}

	public static String getConsistencyPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.CONSISTENCY;
		PatternUtil.registerPattern(patternName, PatternType.CONSISTENCY);
		return patternName;
	}
	
	public static String getProtocolPatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.PROTOCOL;
		PatternUtil.registerPattern(patternName, PatternType.PROTOCOL);
		return patternName;
	}
	
	public static String getProtocolCorePatternName(String ruleName) {
		String patternName = ruleName + PatternSuffixes.PROTOCOL_CORE;
		PatternUtil.registerPattern(patternName, PatternType.PROTOCOL_CORE);
		return patternName;
	}
	
	public static String getNACPatternName(String nacName) {
		String patternName = nacName + PatternSuffixes.USER_NAC;
		PatternUtil.registerPattern(patternName, PatternType.USER_NAC);
		return patternName;
	}

	/**
	 * Returns the axiom name encoded in the name of the rule
	 * 
	 * @param name The name of the pattern
	 * @return The part of the name representing the axiom name
	 */
	public static String extractGENAxiomNacName(String name) {
		return name.substring(0, name.indexOf(PatternSuffixes.SEP));
	}
	
	public static String getFilterNACSRCPatternName(FilterNACCandidate candidate, TGGRule rule) {
		return rule.getName() + "_" + candidate + PatternSuffixes.FILTER_NAC_SRC;  
	}
	
	public static String getFilterNACTRGPatternName(FilterNACCandidate candidate, TGGRule rule) {
		return rule.getName() + "_" + candidate + PatternSuffixes.FILTER_NAC_TRG;  
	}
	
	public static Collection<EObject> getNodes(EObject ruleAppNode, BindingType binding, DomainType type) {
		TGGRuleApplication ra = (TGGRuleApplication) ruleAppNode;
		Collection<EObject> nodes = new ArrayList<>();
		String refNamePrefix = TGGModelUtils.getMarkerRefNamePrefix(binding, type);
		for (EReference ref : ruleAppNode.eClass().getEAllReferences()) {
			if(ref.getName().startsWith(refNamePrefix)) {							
				nodes.add((EObject)ra.eGet(ref));			
			}
		}
		return nodes;
	}
	
	public static Collection<EObject> getAllNodes(EObject ruleAppNode){
		Collection<EObject> allNodes = new ArrayList<>();
		
		allNodes.addAll(getNodes(ruleAppNode, BindingType.CREATE, DomainType.SOURCE));
		allNodes.addAll(getNodes(ruleAppNode, BindingType.CONTEXT, DomainType.SOURCE));
		allNodes.addAll(getNodes(ruleAppNode, BindingType.CREATE, DomainType.TARGET));
		allNodes.addAll(getNodes(ruleAppNode, BindingType.CONTEXT, DomainType.TARGET));
		
		return allNodes;
	}

	public static EObject getNode(EObject ruleAppNode, BindingType binding, DomainType domain, String ovName) {
		TGGRuleApplication ra = (TGGRuleApplication) ruleAppNode;
		String refName = TGGModelUtils.getMarkerRefName(binding, domain, ovName);
		return (EObject) ra.eGet(ruleAppNode.eClass().getEStructuralFeature(refName));	
	}
}