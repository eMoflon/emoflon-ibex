package org.moflon.tgg.mosl.postvalidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.moflon.tgg.mosl.tgg.AbstractAttribute;
import org.moflon.tgg.mosl.tgg.CorrVariablePattern;
import org.moflon.tgg.mosl.tgg.LinkVariablePattern;
import org.moflon.tgg.mosl.tgg.NamePattern;
import org.moflon.tgg.mosl.tgg.ObjectVariablePattern;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.tgg.OperatorPattern;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

public class TGGPostValidator {

	private static TGGPostValidator instance;
	
	private Map<String, List<Consumer<ObjectVariablePattern>>> setUnfinishedLinks;

	private TGGPostValidator() {
		setUnfinishedLinks = new HashMap<>();
	}

	public static TGGPostValidator getInstance() {
		if (instance == null)
			instance = new TGGPostValidator();
		return instance;
	}

	private void addUnfinishedLink(String name, LinkVariablePattern lv){
		List<Consumer<ObjectVariablePattern>> unfinishedLinkFuns = setUnfinishedLinks.get(name);
		if(unfinishedLinkFuns == null){
			unfinishedLinkFuns = new ArrayList<>();
		}
		unfinishedLinkFuns.add(lv::setTarget);
	}
	
	private void setUnfinishedLinks(ObjectVariablePattern ov){
		List<Consumer<ObjectVariablePattern>> unfinishedLinkFuns = setUnfinishedLinks.remove(ov.getName());
		if(unfinishedLinkFuns != null){
			unfinishedLinkFuns.stream().forEach(f -> f.accept(ov));
		}
	}
	
	public List<Rule> getResolvedRules(XtextResourceSet resourceSet, EObject tggFile) {
		return getResolvedRules(resourceSet, TripleGraphGrammarFile.class.cast(tggFile)).stream()
				.filter(rule -> !rule.isAbstractRule()).collect(Collectors.toList());
	}

	private List<Rule> getResolvedRules(XtextResourceSet resourceSet, TripleGraphGrammarFile tggFile) {
		return tggFile.getRules().stream().map(rule -> resolveRefinment(resourceSet, rule))
				.collect(Collectors.toList());
	}

	private Rule resolveRefinment(XtextResourceSet resourceSet, Rule rule) {
		EcoreUtil.resolveAll(rule);
		if (rule.getSupertypes().size() > 0) {
			for (Rule superRule : rule.getSupertypes()) {
				Rule resolvedSuperRule = resolveRefinment(resourceSet, superRule);
				mergeRules(rule, resolvedSuperRule);
			}
		}
		return rule;
	}

	private void mergeRules(Rule subRule, Rule superRule) {
		mergeNamePatterns(subRule.getSourcePatterns(), superRule.getSourcePatterns(), subRule);
		mergeNamePatterns(subRule.getTargetPatterns(), superRule.getTargetPatterns(), subRule);
		mergeNamePatterns(subRule.getCorrespondencePatterns(), superRule.getCorrespondencePatterns(), subRule);
	}

	private <NP extends NamePattern> void mergeNamePatterns(List<NP> subNPLst, List<NP> superNPList, Rule contextRule) {
		for (NP superNamePattern : new ArrayList<>(superNPList)) {
			Optional<NP> subNPMonad = subNPLst.stream()
					.filter(n -> n.getName().compareTo(superNamePattern.getName()) == 0).findFirst();
			if (subNPMonad.isPresent())
				mergeNamePattern(subNPMonad.get(), superNamePattern, subNPLst);
			else {
				subNPLst.add(superNamePattern);
				if(superNamePattern instanceof ObjectVariablePattern){
					ObjectVariablePattern ov = ObjectVariablePattern.class.cast(superNamePattern);
					mergeLinkVariables(ov.getLinkVariablePatterns(), ov.getLinkVariablePatterns(), subNPLst.stream().map(ovp -> ObjectVariablePattern.class.cast(ovp)).collect(Collectors.toList()));
					setUnfinishedLinks(ov);
				}
				if(superNamePattern instanceof CorrVariablePattern)
					updateCorrespondence(CorrVariablePattern.class.cast(superNamePattern), contextRule);
			}
		}
	}
	
	private void updateCorrespondence(CorrVariablePattern corr, Rule context){
		ObjectVariablePattern src = corr.getSource();
		ObjectVariablePattern trg = corr.getTarget();
		corr.setSource(context.getSourcePatterns().stream().filter(ov -> ov.getName().equals(src.getName())).findFirst().get());
		corr.setTarget(context.getTargetPatterns().stream().filter(ov -> ov.getName().equals(trg.getName())).findFirst().get());
	}

	private boolean isContext(Operator op) {
		return op == null || op.getValue() == null || op.getValue().equals("");
	}

	private <O extends OperatorPattern> void fixOperation(O subOpPattern, O superOpPattern) {
		if (isContext(subOpPattern.getOp()) || isContext(subOpPattern.getOp())) {
			subOpPattern.setOp(null);
		}
	}

	private <NP extends NamePattern> void mergeNamePattern(NP subNP, NP superNP, List<NP> context) {
		fixOperation(subNP, superNP);

		if (subNP instanceof ObjectVariablePattern)
			mergeObjectVariable(ObjectVariablePattern.class.cast(subNP), ObjectVariablePattern.class.cast(superNP),
					context.stream().map(np -> ObjectVariablePattern.class.cast(np)).collect(Collectors.toList()));
	}

	private void mergeObjectVariable(ObjectVariablePattern subOV, ObjectVariablePattern superOV,
			List<ObjectVariablePattern> context) {
		mergeAttributes(subOV.getAttributeAssignments(), superOV.getAttributeAssignments());
		mergeAttributes(subOV.getAttributeConstraints(), superOV.getAttributeConstraints());
		mergeLinkVariables(subOV.getLinkVariablePatterns(), superOV.getLinkVariablePatterns(), context);
	}

	private <A extends AbstractAttribute> void mergeAttributes(List<A> subAttrLst, List<A> superAttrList) {
		subAttrLst.addAll(getDisjunctAttributes(subAttrLst, superAttrList));
	}

	private <A extends AbstractAttribute> List<A> getDisjunctAttributes(List<A> subAttrLst, List<A> superAttrList) {
		return superAttrList.stream().filter(aa -> isAttributeSet(subAttrLst, aa)).collect(Collectors.toList());
	}

	private <A extends AbstractAttribute> boolean isAttributeSet(List<A> attrLst, A aa) {
		return attrLst.stream().filter(anotherAA -> anotherAA.getAttribute().equals(aa.getAttribute())).findFirst()
				.isPresent();
	}
	
	private void mergeLinkVariables(List<LinkVariablePattern> subLVs, List<LinkVariablePattern> superLVs,
			List<ObjectVariablePattern> context) {
		for (LinkVariablePattern superLV : getDisjunctLinkVariables(subLVs, superLVs)) {
			Optional<ObjectVariablePattern> contextMonad = context.stream()
					.filter(ov -> ov.getName().equals(superLV.getTarget().getName())).findFirst();
			if (superLV.getType().getUpperBound() != -1) {
				if (contextMonad.isPresent()) {
					superLV.setTarget(contextMonad.get());
				}
				else{
					addUnfinishedLink(superLV.getTarget().getName(), superLV);
				}
			}else{
				if (contextMonad.isPresent()) {
					superLV.setTarget(contextMonad.get());
				}
				else{
					addUnfinishedLink(superLV.getTarget().getName(), superLV);
				}
			}

			subLVs.add(superLV);
		}
	}

	private List<LinkVariablePattern> getDisjunctLinkVariables(List<LinkVariablePattern> subLVs,
			List<LinkVariablePattern> superLVs) {
		return superLVs.stream().filter(lv -> isLinkVariableSet(subLVs, lv)).collect(Collectors.toList());
	}

	private boolean isLinkVariableSet(List<LinkVariablePattern> subLVs, LinkVariablePattern lv) {
		EReference superRef = lv.getType();
		Optional<LinkVariablePattern> subLVMonad = subLVs.stream().filter(subLV -> subLV.getType().equals(superRef))
				.findFirst();
		if (!subLVMonad.isPresent()) {
			return true;
		}

		if (superRef.getUpperBound() != -1)
			return false;

		return true;
	}
}
