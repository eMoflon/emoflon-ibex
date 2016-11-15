package org.emoflon.ibex.tgg.ui.ide.transformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.moflon.tgg.mosl.tgg.CorrType;
import org.moflon.tgg.mosl.tgg.CorrVariablePattern;
import org.moflon.tgg.mosl.tgg.LinkVariablePattern;
import org.moflon.tgg.mosl.tgg.ObjectVariablePattern;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGG;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;


public class EditorTGGtoInternalTGG {
	
	private EcoreFactory ecoreFactory = EcoreFactory.eINSTANCE;
	private LanguageFactory tggFactory = LanguageFactory.eINSTANCE;
	private HashMap<EObject,EObject> xtextToTGG = new HashMap<>();
	private HashMap<EObject,EObject> tggToXtext = new HashMap<>();
	
	public TGGProject convertXtextTGG(TripleGraphGrammarFile xtextTGG){
		EPackage corrPackage = createCorrModel(xtextTGG);
		TGG tgg = createTGG(xtextTGG);
		tgg.setCorr(corrPackage);
		return new TGGProject(corrPackage, tgg);
	}
	
	private TGG createTGG(TripleGraphGrammarFile xtextTGG) {
		
		TGG tgg = tggFactory.createTGG();
		tgg.setName(xtextTGG.getSchema().getName());
		tgg.getSrc().addAll(xtextTGG.getSchema().getSourceTypes());
		tgg.getTrg().addAll(xtextTGG.getSchema().getTargetTypes());
		
		map(xtextTGG, tgg);
		
		for(Rule xtextRule : xtextTGG.getRules()){
			TGGRule tggRule = tggFactory.createTGGRule();
			tggRule.setName(xtextRule.getName());
			tggRule.setAbstract(xtextRule.isAbstractRule());
			tgg.getRules().add(tggRule);
			map(xtextRule,tggRule);
			
			tggRule.getNodes().addAll(createTGGRuleNodes(xtextRule.getSourcePatterns(), DomainType.SRC));
			tggRule.getNodes().addAll(createTGGRuleNodes(xtextRule.getTargetPatterns(), DomainType.TRG));
			tggRule.getNodes().addAll(createTGGRuleNodesFromCorrOVs(xtextRule.getCorrespondencePatterns()));
		
			tggRule.getEdges().addAll(createTGGRuleEdges(tggRule));
		}
		
		return tgg;
	}
	
	private Collection<TGGRuleEdge> createTGGRuleEdges(TGGRule tggRule) {
		
		ArrayList<TGGRuleEdge> result = new ArrayList<>();
		
		for(TGGRuleNode node : tggRule.getNodes()){
			if(node.getDomainType() == DomainType.SRC || node.getDomainType() == DomainType.TRG){
				ObjectVariablePattern ov = (ObjectVariablePattern) tggToXtext.get(node);
				for(LinkVariablePattern lv : ov.getLinkVariablePatterns()){
					TGGRuleEdge tggEdge = tggFactory.createTGGRuleEdge();
					tggEdge.setType(lv.getType());
					tggEdge.setSrcNode(node);
					tggEdge.setTrgNode((TGGRuleNode) xtextToTGG.get(lv.getTarget()));
					tggEdge.setBindingType(getBindingType(lv.getOp()));
					tggEdge.setDomainType(node.getDomainType());
					tggEdge.setName(tggEdge.getSrcNode().getName() + "__" + tggEdge.getType().getName() + "__" + tggEdge.getTrgNode().getName() + "_eMoflonEdge");
					map(lv, tggEdge);
					result.add(tggEdge);
				}
			}
		}
		
		return result;
	}

	private Collection<TGGRuleNode> createTGGRuleNodesFromCorrOVs(Collection<CorrVariablePattern> corrOVs) {
		ArrayList<TGGRuleNode> result = new ArrayList<>();
		for(CorrVariablePattern cv : corrOVs){
			TGGRuleCorr corrNode = tggFactory.createTGGRuleCorr();
			corrNode.setName(cv.getName());
			corrNode.setType((EClass) xtextToTGG.get(cv.getType()));
			corrNode.setBindingType(getBindingType(cv.getOp()));
			corrNode.setDomainType(DomainType.CORR);
			corrNode.setSource((TGGRuleNode) xtextToTGG.get(cv.getSource()));
			corrNode.setTarget((TGGRuleNode) xtextToTGG.get(cv.getTarget()));
			result.add(corrNode);
			map(cv, corrNode);
		}
		return result;
	}

	private Collection<TGGRuleNode> createTGGRuleNodes(Collection<ObjectVariablePattern> ovs, DomainType domainType){
		ArrayList<TGGRuleNode> result = new ArrayList<>();
		for(ObjectVariablePattern ov : ovs){
			TGGRuleNode tggNode = getTGGRuleNode(ov);
			tggNode.setDomainType(domainType);
			result.add(tggNode);
		}
		return result;
	}

	private TGGRuleNode getTGGRuleNode(ObjectVariablePattern ov) {
		TGGRuleNode tggNode = tggFactory.createTGGRuleNode();
		tggNode.setName(ov.getName());
		tggNode.setType(ov.getType());
		tggNode.setBindingType(getBindingType(ov.getOp()));
		map(ov,tggNode);
		return tggNode;
	}

	private BindingType getBindingType(Operator op) {
		if(op == null)
			return BindingType.CONTEXT;
		String value = op.getValue();
		if("++".equals(value))
			return BindingType.CREATE;
		if("!".equals(value))
			return BindingType.NEGATIVE;
		return null;
	}

	private EPackage createCorrModel(TripleGraphGrammarFile xtextTGG){
		
		EPackage corrModel = ecoreFactory.createEPackage();
		
		corrModel.setName(xtextTGG.getSchema().getName());
		corrModel.setNsPrefix(xtextTGG.getSchema().getName());
		corrModel.setNsURI("platform:/plugin/" + corrModel.getName() + "/model/" + corrModel.getName() + ".ecore");
		
		for(CorrType ct : xtextTGG.getSchema().getCorrespondenceTypes()){	
			corrModel.getEClassifiers().add(createEClass(ct));
		}
		
		for(CorrType ct : xtextTGG.getSchema().getCorrespondenceTypes()){

			if(ct.getSuper() != null){
				((EClass) xtextToTGG.get(ct)).getESuperTypes().add((EClass) xtextToTGG.get(ct.getSuper()));
			}
		}
		
		return corrModel;
	}

	private EClass createEClass(CorrType ct) {
		EClass corrClass = ecoreFactory.createEClass();
		corrClass.setName(ct.getName());
		
		EReference srcRef = ecoreFactory.createEReference();
		srcRef.setName("source");
		srcRef.setLowerBound(0);
		srcRef.setUpperBound(1);
		srcRef.setEType(ct.getSource());
		corrClass.getEStructuralFeatures().add(srcRef);
		
		EReference trgRef = ecoreFactory.createEReference();
		trgRef.setName("target");
		trgRef.setLowerBound(0);
		trgRef.setUpperBound(1);
		trgRef.setEType(ct.getTarget());
		corrClass.getEStructuralFeatures().add(trgRef);
		
		map(ct, corrClass);
		
		return corrClass;
	}
	
	private void map(EObject xtextObject, EObject tggObject){
		xtextToTGG.put(xtextObject, tggObject);
		tggToXtext.put(tggObject, xtextObject);
	}
}
