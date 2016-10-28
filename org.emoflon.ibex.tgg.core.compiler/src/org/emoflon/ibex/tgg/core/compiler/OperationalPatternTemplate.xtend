package org.emoflon.ibex.tgg.core.compiler

import language.TGGRuleElement
import language.TGGRuleNode
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern

class OperationalPatternTemplate {
	

	
	def get(Pattern pattern) {
		return '''
		
		pattern «pattern.name»(«FOR e : pattern.signatureElements SEPARATOR ", " »«e.name»:«getTypeOf(e)»«ENDFOR»){
			
		}
		
		
		'''
	}
	
	def getTypeOf(TGGRuleElement el){
		
		return '''«IF el instanceof TGGRuleNode»«(el as TGGRuleNode).type»«ENDIF»'''
		
	}

	
}