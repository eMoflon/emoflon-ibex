package org.emoflon.ibex.tgg.core.compiler

import java.util.Map
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.ConsistencyPattern
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.RulePartPattern
import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs.PatternWithProtocolNACs
import java.util.Collection

class PatternTemplate {
	
	def generateCommonPatterns() {
		return '''
		pattern marked(o: EObject){
			TGGRuleApplication.createdSrc(_,o);
		} or {
			TGGRuleApplication.createdTrg(_,o);
		}
		'''
	}
		
	def generateHeaderAndImports(Map<String, String> aliasedImports, Collection<String> nonAliasedImports, String packageName){
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
		pattern «pattern.getName»(«FOR e : pattern.getSignatureElements SEPARATOR ", "»«e.name»:«pattern.typeOf(e).name»«ENDFOR»){
			«IF pattern.ignored»
			check(false);
			«ENDIF»
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
	
	def generateProtocolNACsPattern(PatternWithProtocolNACs pattern) {
		return '''
		pattern «pattern.getName»(«FOR e : pattern.getSignatureElements SEPARATOR ", "»«e.name»:«pattern.typeOf(e).name»«ENDFOR»){
			«FOR pi : pattern.positiveInvocations»
			find «pi.getName»(«FOR e : pi.signatureElements SEPARATOR ", "»«e.name»«ENDFOR»);
			«ENDFOR»
			«FOR e : pattern.NACrelevantElements»
			neg find marked(«e.name»);
			«ENDFOR»
		}
		'''
	}
	
	def generateConsistencyPattern(ConsistencyPattern pattern) {
		return '''
		pattern «pattern.getName»(«FOR e : pattern.getSignatureElements SEPARATOR ", "»«e.name»:«pattern.typeOf(e).name»«ENDFOR»){
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
}