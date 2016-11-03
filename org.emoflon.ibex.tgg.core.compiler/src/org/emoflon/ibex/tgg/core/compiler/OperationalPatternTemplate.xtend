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
			check(true);
		}
		
		'''
	}
}