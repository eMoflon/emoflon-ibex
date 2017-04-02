package org.emoflon.ibex.tgg.ui.ide.visualisation

import java.util.Collection
import org.apache.commons.lang3.tuple.Pair
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.gervarro.democles.specification.emf.Pattern
import org.gervarro.democles.specification.emf.PatternInvocationConstraint
import org.gervarro.democles.specification.emf.Variable
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable
import org.gervarro.democles.specification.emf.constraint.emf.emf.Reference
import org.moflon.tgg.mosl.tgg.CorrVariablePattern
import org.moflon.tgg.mosl.tgg.LinkVariablePattern
import org.moflon.tgg.mosl.tgg.ObjectVariablePattern
import org.moflon.tgg.mosl.tgg.Operator
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile
import org.gervarro.democles.specification.emf.PatternBody

class PlantUMLGenerator {
	
	public static def String emptyDiagram(){
		'''
		@startuml
		title Choose an element that can be visualised
		@enduml
		'''
	}
	
	public static def String visualisePatternBody(PatternBody b){
		'''
		@startuml
		«visualiseIsolatedPatternBody(b)»
		«var j = 0»
		«FOR pi : patternInvocations(b)»
			«var prefix = j++ + "\\"»
			«visualiseSymbolicParametersWithPrefix(pi.invokedPattern, prefix)»
			«var i = 0»
			«FOR param : pi.parameters»
			«IF pi.positive»
				«identifierFor(param.reference as Variable, b.header)» #--# «identifierForWithPrefix(pi.invokedPattern.symbolicParameters.get(i++), pi.invokedPattern, prefix)»
			«ELSE»
				namespace «prefix»«pi.invokedPattern.name» #DDDDDD {
				«identifierFor(param.reference as Variable, b.header)» #..# «identifierForWithPrefix(pi.invokedPattern.symbolicParameters.get(i++), pi.invokedPattern, prefix)»
				}
			«ENDIF»
			«ENDFOR»
		«ENDFOR»
		@enduml
		'''
	}
	
	private static def String visualiseSymbolicParameters(Pattern p){
		visualiseSymbolicParametersWithPrefix(p, "");
	}
	
	private static def String visualiseSymbolicParametersWithPrefix(Pattern p, String prefix){
		'''
		«FOR v : patternVariables(p) SEPARATOR "\n"»
			class «identifierForWithPrefix(v, p, prefix)»<< (V,#FF7700)>>
		«ENDFOR»
		'''
	}
	
	private static def patternInvocations(PatternBody body){
		body.constraints.filter(PatternInvocationConstraint)
	}
	
	private static def String visualiseIsolatedPatternBody(PatternBody b) {
		'''
		«visualiseSymbolicParameters(b.header)»
		«FOR v : localVariables(b) SEPARATOR "\n"»
			class «identifierFor(v, b.header)»<< (L,#B0D8F0)>>
		«ENDFOR»
		«FOR ref : referenceConstraints(b)»
			«identifierFor(extractSrc(ref), b.header)» --> "«ref.EModelElement.name»" «identifierFor(extractTrg(ref), b.header)»
		«ENDFOR»
		'''
	}
	
	private def static localVariables(PatternBody body) {
		body.localVariables
	}
	
	private static def identifierFor(Variable v, Pattern pattern){
		identifierForWithPrefix(v, pattern, "")
	}
	
	private static def identifierForWithPrefix(Variable v, Pattern pattern, String prefix){
		'''"«prefix»«pattern.name».«v.name»:«(v as EMFVariable).EClassifier.name»"'''
	}
	
	private static def extractVar(Reference ref, int i){
		ref.parameters.get(i).reference as Variable
	}
	
	private static def extractSrc(Reference ref){
		extractVar(ref, 0)	
	}
	
	private static def extractTrg(Reference ref){
		extractVar(ref, 1)	
	}
	
	private static def referenceConstraints(PatternBody body){
		body.constraints.filter(Reference)
	}
	
	private static def patternVariables(Pattern p) {
		p.symbolicParameters
	}
	
	public static def String visualiseTGGFile(TripleGraphGrammarFile file) {
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
	
	private def static visualiseCorrs(CorrVariablePattern corr) {
		'''
		«idForPattern(corr.source)» -> «idForPattern(corr.target)» [penwidth=7, dir="both", style="tapered", arrowtail="none", arrowhead="none", color=«operatorToColour(corr.op)», constraint=false];
		'''
	}
	
	private def static visualisePattern(ObjectVariablePattern p, String domainColour) {
		'''
		 «idForPattern(p)» [fontsize=9, fontname=Monospace, penwidth=1, shape=record, color=«operatorToColour(p.op)», fillcolor=«domainColour», label="{«p.name» : «p.type.name» | }",style=filled];
		 «FOR lv : p.linkVariablePatterns»
		 «visaliseLinkVariable(p, lv)»
		 «ENDFOR»
		'''
	}
	
	private def static idForPattern(ObjectVariablePattern p) {
		'''"«p.name» : «p.type.name»"'''
	}
	
	private def static visaliseLinkVariable(ObjectVariablePattern src, LinkVariablePattern p) {
		'''«idForPattern(src)» -> «idForPattern(p.target)» [fontname=Monospace, penwidth=1, color=«operatorToColour(p.op)», label="«p.type.name»", fontsize=8, constraint=true];'''
	}
	
	private def static operatorToColour(Operator op) {
		if(op != null)
			return "GREEN"
		else
			return "BLACK"
	}
	
	public def static String visualiseEcoreElements(Collection<EClass> eclasses, Collection<EReference> refs){
		'''
		@startuml
		«FOR c : eclasses»
		class «identifierFor(c)»
			«FOR s : c.ESuperTypes»
			«identifierFor(c)»--|>«identifierFor(s)»
			«ENDFOR»
		«ENDFOR»
		«FOR r : refs»
		«identifierFor(r.EContainingClass)»«IF r.isContainment» *«ENDIF»--> «multiplicityFor(r)» «identifierFor(r.EReferenceType)» : "«r.name»"
		«ENDFOR»
		@enduml
		'''
	}
	
	private def static multiplicityFor(EReference r) {
		'''"«IF r.lowerBound == -1»*«ELSE»«r.lowerBound»«ENDIF»..«IF r.upperBound == -1»*«ELSE»«r.upperBound»«ENDIF»"'''
	}
	
	private def static String identifierFor(EClass c)
		'''"«c.EPackage.name».«c.name»"'''
		
	public def static String visualiseModelElements(Collection<EObject> objects, Collection<Pair<String, Pair<EObject, EObject>>> links){
		'''
		@startuml
		«FOR o : objects»
		object «identifierFor(o)»
		«ENDFOR»
		«FOR l : links»
		«identifierFor(l.right.left)» --> «identifierFor(l.right.right)» : "«l.left»"
		«ENDFOR»
		@enduml
		'''
	}
	
	private def static Object identifierFor(EObject o)
		'''«o.hashCode».«o.eClass.name»'''
	
}
