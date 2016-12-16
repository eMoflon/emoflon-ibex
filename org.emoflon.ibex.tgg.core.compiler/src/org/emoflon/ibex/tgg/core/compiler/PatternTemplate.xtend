package org.emoflon.ibex.tgg.core.compiler

import java.util.Collection
import language.TGGRuleElement
import org.eclipse.emf.ecore.EReference
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.ConsistencyPattern
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.RulePartPattern
import runtime.RuntimePackage
import language.TGGRuleNode
import language.TGGRuleEdge
import org.eclipse.emf.ecore.EPackage
import java.util.LinkedHashMap

class PatternTemplate {
	
	private LinkedHashMap<EPackage, String> importAliases;
	new(LinkedHashMap<EPackage,String> importAliases){
		this.importAliases = importAliases;
	}
	
	def generateCommonPatterns(Collection<EReference> edgeTypes) {
		
		return '''	
		«FOR et : edgeTypes»
		pattern «EdgePatternNaming.getEdgePatternNaming(et)»(s:«importAliases.get(et.EContainingClass.EPackage)»::«et.EContainingClass.name», t:«importAliases.get(et.EType.EPackage)»::«et.EType.name»){
			«et.EContainingClass.name».«et.name»(s,t);
		}
		
		«ENDFOR»
		
		'''
	}
		
	def generateHeaderAndImports(LinkedHashMap<String, String> aliasedImports, Collection<String> nonAliasedImports, String packageName){
		return '''
		package org.emoflon.ibex.tgg.«packageName.toLowerCase»
		
		«FOR p : aliasedImports.keySet»
			import "«aliasedImports.get(p)»" as «p»
		«ENDFOR»
		
		«FOR i : nonAliasedImports»
		    import «i»
		«ENDFOR»
		
		'''
	}
	
		
	def generateOperationalPattern(RulePartPattern pattern) {
	
		return '''
		pattern «pattern.getName»(«FOR e : pattern.signatureElements SEPARATOR ", "»«e.name»:«typeOf(e)»«ENDFOR»){
			«IF pattern.ignored»
			check(false);
			«ENDIF»
			«FOR injectivityCheckPair : pattern.injectivityChecks»
			«injectivityCheckPair.left.name» != «injectivityCheckPair.right.name»;
			«ENDFOR»
			«FOR edge : pattern.getBodyEdges»
			find «EdgePatternNaming.getEdgePatternNaming(edge.type)»(«edge.srcNode.name», «edge.trgNode.name»);
			«ENDFOR»			
			«FOR node : pattern.bodySrcTrgNodes»
			«node.type.name»(«node.name»);
			«FOR attrExpr : node.attrExpr»
			«IF InplaceAttribute2ViatraCheck.simpleExpression(attrExpr)»
			«node.type.name».«attrExpr.attribute.name»(«node.name», «InplaceAttribute2ViatraCheck.extractViatraEqualCheck(attrExpr)»);
			«ELSE»
			«node.type.name».«attrExpr.attribute.name»(«node.name», «node.name»_«attrExpr.attribute.name»_emoflonAttr);
			«IF !InplaceAttribute2ViatraCheck.isENUMExpr(attrExpr)»
			check («InplaceAttribute2ViatraCheck.extractViatraCheck(node.name + "_" + attrExpr.attribute.name + "_emoflonAttr", attrExpr)»);
			«ELSE»
			«InplaceAttribute2ViatraCheck.extractViatraCheck(node.name + "_" + attrExpr.attribute.name + "_emoflonAttr", attrExpr)»;
			«ENDIF»
			«ENDIF»
			«ENDFOR»	
			«ENDFOR»			
			«FOR corr : pattern.bodyCorrNodes»
			«corr.type.name».source(«corr.name»,«corr.source.name»);
			«corr.type.name».target(«corr.name»,«corr.target.name»);
			«ENDFOR»
			«FOR pi : pattern.positiveInvocations»
			find «pi.getName»(«FOR e : pi.signatureElements SEPARATOR ", "»«e.name»«ENDFOR»);
			«ENDFOR»
			«FOR ni : pattern.negativeInvocations»
			neg find «ni.getName»(«FOR e : ni.signatureElements SEPARATOR ", "»«e.name»«ENDFOR»);
			«ENDFOR»
		    check(true);
		}
		
		'''
	}
	
	
	def generateConsistencyPattern(ConsistencyPattern pattern) {
		return '''
		pattern «pattern.getName»(«FOR e : pattern.getSignatureElements SEPARATOR ", "»«e.name»:«typeOf(e)»«ENDFOR»){
			«FOR pi : pattern.positiveInvocations»
			find «pi.getName»(«FOR e : pi.signatureElements SEPARATOR ", "»«e.name»«ENDFOR»);
			«ENDFOR»
			TGGRuleApplication.final(«pattern.protocolNodeName», true);
			TGGRuleApplication.name(«pattern.protocolNodeName», "«pattern.ruleName»");
			«FOR e : pattern.contextSrc»
			TGGRuleApplication.contextSrc(«pattern.protocolNodeName», «e.name»);
			«ENDFOR»
			«FOR e : pattern.createdSrc»
			TGGRuleApplication.createdSrc(«pattern.protocolNodeName», «e.name»);
			«ENDFOR»
			«FOR e : pattern.contextTrg»
			TGGRuleApplication.contextTrg(«pattern.protocolNodeName», «e.name»);
			«ENDFOR»
			«FOR e : pattern.createdTrg»
			TGGRuleApplication.createdTrg(«pattern.protocolNodeName», «e.name»);
			«ENDFOR»
			«FOR e : pattern.contextCorr»
			TGGRuleApplication.contextCorr(«pattern.protocolNodeName», «e.name»);
		    «ENDFOR»
		    «FOR e : pattern.createdCorr»
		    TGGRuleApplication.createdCorr(«pattern.protocolNodeName», «e.name»);
		    «ENDFOR»
		}
		'''
	}
	
	
	def typeOf(TGGRuleElement e){
		return'''«importAliases.get((e as TGGRuleNode).type.EPackage)»::«(e as TGGRuleNode).type.name»'''
	}
	
}