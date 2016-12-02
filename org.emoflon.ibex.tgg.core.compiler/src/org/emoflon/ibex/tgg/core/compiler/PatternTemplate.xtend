package org.emoflon.ibex.tgg.core.compiler

import java.util.Collection
import language.TGGRuleElement
import org.eclipse.emf.ecore.EReference
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.ConsistencyPattern
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.RulePartPattern
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.ilp.ILPAllMarkedPattern
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.ilp.ILPPattern
import org.emoflon.ibex.tgg.core.compiler.pattern.strategy.protocolnacs.ProtocolNACsPattern
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
		pattern marked(o: EObject){
			TGGRuleApplication.createdSrc(p,o);
			TGGRuleApplication.final(p, true);
		} or {
			TGGRuleApplication.createdTrg(p,o);
			TGGRuleApplication.final(p, true);
		}
		
		«FOR et : edgeTypes»
		pattern «EdgePatternNaming.getEMFEdge(et)»(s:«importAliases.get(et.EContainingClass.EPackage)»::«et.EContainingClass.name», t:«importAliases.get(et.EType.EPackage)»::«et.EType.name»){
			«et.EContainingClass.name».«et.name»(s,t);
		}
		
		pattern «EdgePatternNaming.getEdgeWrapper(et)»(s:«importAliases.get(et.EContainingClass.EPackage)»::«et.EContainingClass.name», t:«importAliases.get(et.EType.EPackage)»::«et.EType.name», e:Edge){
			Edge.src(e,s);
			Edge.trg(e,t);
			Edge.name(e, "«et.name»");
		}
		
		pattern «EdgePatternNaming.getMissingEdgeWrapper(et)»(s:«importAliases.get(et.EContainingClass.EPackage)»::«et.EContainingClass.name», t:«importAliases.get(et.EType.EPackage)»::«et.EType.name»){
			find «EdgePatternNaming.getEMFEdge(et)»(s,t);
			neg find «EdgePatternNaming.getEdgeWrapper(et)»(s,t,_);
		}
		
		pattern «EdgePatternNaming.getExistingEdgeWrapper(et)»(s:«importAliases.get(et.EContainingClass.EPackage)»::«et.EContainingClass.name», t:«importAliases.get(et.EType.EPackage)»::«et.EType.name», e:Edge){
			find «EdgePatternNaming.getEMFEdge(et)»(s,t);
			find «EdgePatternNaming.getEdgeWrapper(et)»(s,t,e);
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
			Edge.src(«edge.name»,«edge.srcNode.name»);
			Edge.trg(«edge.name»,«edge.trgNode.name»);
			Edge.name(«edge.name»,"«edge.type.name»");
			«ENDFOR»			
			«FOR node : pattern.bodySrcTrgNodes»
			«node.type.name»(«node.name»);
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
	
	def generateProtocolNACsPattern(ProtocolNACsPattern pattern) {
		return '''
		pattern «pattern.getName»(«FOR e : pattern.getSignatureElements SEPARATOR ", "»«e.name»:«typeOf(e)»«ENDFOR»){
			«FOR pi : pattern.positiveInvocations»
			find «pi.getName»(«FOR e : pi.signatureElements SEPARATOR ", "»«e.name»«ENDFOR»);
			«ENDFOR»
			«FOR e : pattern.markingNACs»
			neg find marked(«e.name»);
			«ENDFOR»
			«FOR e : pattern.marked»
			find marked(«e.name»);
			«ENDFOR»
		}
		'''
	}
	
	def generateConsistencyPattern(ConsistencyPattern pattern) {
		return '''
		pattern «pattern.getName»(«FOR e : pattern.getSignatureElements SEPARATOR ", "»«e.name»:«typeOf(e)»«ENDFOR»){
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
	
	def generateILPAllMarkedPattern(ILPAllMarkedPattern pattern){
		return '''
		pattern «pattern.getName»(«FOR e : pattern.getSignatureElements SEPARATOR ", "»«e.name»:«typeOf(e)»«ENDFOR»){
		    «FOR e : pattern.signatureElements»
		    find marked(«e.name»);
		    «ENDFOR»
		    «FOR pi : pattern.positiveInvocations»
		    find «pi.getName»(«FOR e : pi.signatureElements SEPARATOR ", "»«e.name»«ENDFOR»);
		    «ENDFOR»
		    check(true);
		}
		'''
	}
	
	def generateILPPattern(ILPPattern pattern){
		return '''
	    pattern «pattern.getName»(«FOR e : pattern.getSignatureElements SEPARATOR ", "»«e.name»:«typeOf(e)»«ENDFOR»){
	    	«FOR pi : pattern.positiveInvocations»
	    		find «pi.getName»(«FOR e : pi.signatureElements SEPARATOR ", "»«e.name»«ENDFOR»);
	    	«ENDFOR»
	    	«FOR ni : pattern.negativeInvocations»
	    		neg find «ni.getName»(«FOR e : ni.signatureElements SEPARATOR ", "»«e.name»«ENDFOR»);
	    	«ENDFOR»	
	    }		
		'''
	}
	
	def typeOf(TGGRuleElement e){
		return'''«IF e instanceof TGGRuleEdge»«importAliases.get(RuntimePackage.eINSTANCE)»::Edge«ELSE»«importAliases.get((e as TGGRuleNode).type.EPackage)»::«(e as TGGRuleNode).type.name»«ENDIF»'''
	}
	
}