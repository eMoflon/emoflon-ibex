package org.emoflon.ibex.tgg.core.compiler

import java.util.Map
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern

class OperationalPatternTemplate {
		
	def generateHeaderAndImports(Map<String, String> imports, String ruleName){
		return '''
		package org.emoflon.ibex.tgg.«ruleName.toLowerCase»
		
		«FOR p : imports.keySet»
			import "«imports.get(p)»" as «p»
		«ENDFOR»
		
		'''
	}
		
	def generatePattern(Pattern pattern) {
		return '''
		pattern «pattern.name»(«FOR e : pattern.signatureElements SEPARATOR ", "»«e.name»:«pattern.typeOf(e).name»«ENDFOR»){
			«FOR edge : pattern.bodyEdges»
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
			find «pi.name»(«FOR e : pi.signatureElements SEPARATOR ", "»«e.name»«ENDFOR»);
			«ENDFOR»
		    check(true);
		}
		
		'''
	}
}