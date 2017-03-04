package org.emoflon.ibex.tgg.ui.ide.visualisation

import org.gervarro.democles.specification.emf.Pattern
import org.gervarro.democles.specification.emf.Variable
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable
import org.gervarro.democles.specification.emf.constraint.emf.emf.Reference
import org.gervarro.democles.specification.emf.PatternInvocationConstraint

class PlantUMLGenerator {
	
	static def String emptyDiagram(){
		'''
		@startuml
		title Choose an element that can be visualised
		@enduml
		'''
	}
	
	static def String visualisePattern(Pattern p){
		'''
		@startuml
		«visualisePatternBody(p)»
		@enduml
		'''
	}
	
	static def String visualisePatternBody(Pattern p){
		'''
		«visualiseIsolatedPattern(p)»
		«FOR pi : patternInvocations(p)»
			«visualisePatternBody(pi.invokedPattern)»
			«p.name»«IF pi.positive»--+«ELSE»--x«ENDIF» «pi.invokedPattern.name»
		«ENDFOR»
		'''
	}
	
	static def patternInvocations(Pattern p){
		p.bodies.get(0).constraints.filter(PatternInvocationConstraint)
	}
	
	static def String visualiseIsolatedPattern(Pattern p) {
		'''
		«FOR v : patternVariables(p) SEPARATOR "\n"»
			class «identifierFor(v, p)»<< (V,#FF7700)>>
		«ENDFOR»
		
		«FOR ref : referenceConstraints(p)»
			«identifierFor(extractSrc(ref), p)» --> "«ref.EModelElement.name»" «identifierFor(extractTrg(ref), p)»
		«ENDFOR»
		'''
	}	
	
	static def identifierFor(Variable v, Pattern pattern){
		'''"«pattern.name».«v.name»:«(v as EMFVariable).EClassifier.name»"'''
	}
	
	static def extractVar(Reference ref, int i){
		ref.parameters.get(i).reference as Variable
	}
	
	static def extractSrc(Reference ref){
		extractVar(ref, 0)	
	}
	
	static def extractTrg(Reference ref){
		extractVar(ref, 1)	
	}
	
	static def referenceConstraints(Pattern pattern){
		pattern.bodies.get(0).constraints.filter(Reference)
	}
	
	static def patternVariables(Pattern pattern) {
		pattern.bodies.get(0).header.symbolicParameters
	}
}
