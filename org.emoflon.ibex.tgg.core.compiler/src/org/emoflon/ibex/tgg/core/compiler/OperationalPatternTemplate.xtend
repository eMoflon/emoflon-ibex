package org.emoflon.ibex.tgg.core.compiler

import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern

class OperationalPatternTemplate {
		
	def get(Pattern pattern) {
		return '''
		pattern «pattern.name»(«FOR e : pattern.signatureElements SEPARATOR ", "»«e.name»:«pattern.typeOf(e).name»«ENDFOR»){
			check(true);
		}
		
		'''
	}
}