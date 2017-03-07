package org.emoflon.ibex.tgg.ui.ide.visualisation

import org.gervarro.democles.specification.emf.Pattern
import org.gervarro.democles.specification.emf.PatternInvocationConstraint
import org.gervarro.democles.specification.emf.Variable
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable
import org.gervarro.democles.specification.emf.constraint.emf.emf.Reference
import org.moflon.tgg.mosl.tgg.ObjectVariablePattern
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile
import org.moflon.tgg.mosl.tgg.LinkVariablePattern
import org.moflon.tgg.mosl.tgg.Operator
import org.moflon.tgg.mosl.tgg.CorrVariablePattern

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
	
	static def String visualiseTGGFile(TripleGraphGrammarFile file) {
		'''
		@startuml
		«IF file.rules.length != 1»title "I can only visualise exactly one TGG rule in one file"
		«ELSE»
		«var r = file.rules.get(0)»
		digraph root {
			fontname=Monospace
			fontsize=9
			subgraph "cluster_source" {
				 label="";
				 pencolor="invis";
			     «FOR sp : r.sourcePatterns»
			    	«visualisePattern(sp, "LIGHTYELLOW")»
			     «ENDFOR»
			}
			subgraph "cluster_target" {
				 label="";
				 pencolor="invis";
				 «FOR sp : r.targetPatterns»
				 	«visualisePattern(sp, "MISTYROSE")»
				 «ENDFOR»
			}
			subgraph "correspondence" {
				 label="";
				 pencolor="invis";
				 «FOR cp : r.correspondencePatterns»
				 	«visualiseCorrs(cp)»
				 «ENDFOR»
			}
		}
		«ENDIF»
		@enduml
		'''
	}
	
	def static visualiseCorrs(CorrVariablePattern corr) {
		'''
		«idForPattern(corr.source)» -> «idForPattern(corr.target)» [penwidth=7, dir="both", style="tapered", arrowtail="none", arrowhead="none", color=«operatorToColour(corr.op)», constraint=false];
		'''
	}
	
	def static visualisePattern(ObjectVariablePattern p, String domainColour) {
		'''
		 «idForPattern(p)» [fontsize=9, fontname=Monospace, penwidth=1, shape=record, color=«operatorToColour(p.op)», fillcolor=«domainColour», label="{«p.name» : «p.type.name» | }",style=filled];
		 «FOR lv : p.linkVariablePatterns»
		 «visaliseLinkVariable(p, lv)»
		 «ENDFOR»
		'''
	}
	
	def static idForPattern(ObjectVariablePattern p) {
		'''"«p.name» : «p.type.name»"'''
	}
	
	def static visaliseLinkVariable(ObjectVariablePattern src, LinkVariablePattern p) {
		'''«idForPattern(src)» -> «idForPattern(p.target)» [fontname=Monospace, penwidth=1, color=«operatorToColour(p.op)», label="«p.type.name»", fontsize=8, constraint=true];'''
	}
	
	def static operatorToColour(Operator op) {
		if(op != null)
			return "GREEN"
		else
			return "BLACK"
	}
	
}
