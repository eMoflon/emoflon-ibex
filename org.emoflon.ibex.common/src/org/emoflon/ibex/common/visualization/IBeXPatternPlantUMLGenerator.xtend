package org.emoflon.ibex.common.visualization

import IBeXLanguage.IBeXContextPattern
import IBeXLanguage.IBeXCreatePattern
import IBeXLanguage.IBeXDeletePattern
import IBeXLanguage.IBeXEdge
import IBeXLanguage.IBeXPatternSet
import IBeXLanguage.IBeXNode

import org.eclipse.emf.common.util.EList

/**
 * Utility methods to generate PlantUML code.
 */
class IBeXPatternPlantUMLGenerator {
	static val ContextColor = 'Black'
	static val LocalColor = 'DarkSlateGray'
	static val CreateColor = 'DarkGreen'
	static val DeleteColor = 'Crimson'

	/**
	 * Visualizes the pattern invocations in the pattern set. 
	 */
	static def String visualizePatternInvocations(IBeXPatternSet patternSet) {
		'''
			«commonLayoutSettings»
			
			«FOR pattern : patternSet.contextPatterns»
				class "«pattern.name»"
			«ENDFOR»
			
			«FOR pattern : patternSet.contextPatterns»
				«FOR invocation: pattern.invocations»
					"«pattern.name»" --> "«invocation.invokedPattern.name»"
				«ENDFOR»
			«ENDFOR»
			
			center footer
				= Pattern Invocations
			end footer
		'''
	}

	/**
	 * Visualizes the context pattern.
	 */
	static def String visualizeContextPattern(IBeXContextPattern pattern) {
		'''
			«commonLayoutSettings»
			
			skinparam class {
				BorderColor<<SIGNATURE>> «ContextColor»
				BorderColor<<LOCAL>> «LocalColor»
				FontColor<<SIGNATURE>> «ContextColor»
				FontColor<<LOCAL>> «LocalColor»
			}
			
			«printPattern(pattern, '', true)»
			
			center footer
				= Context Pattern «pattern.name»
			end footer
		'''
	}

	/**
	 * Outputs the context pattern.
	 */
	private static def String printPattern(IBeXContextPattern pattern, String prefix, boolean positive) {
		val packageName = prefix + pattern.name
		'''
			package "«packageName»" «IF !positive»#FFCCCC«ENDIF» {}
			«visualizeNodes(pattern.signatureNodes, 'SIGNATURE', packageName)»
			«visualizeNodes(pattern.localNodes, 'LOCAL', packageName)»
			«visualizeEdges(pattern.localEdges, ContextColor, packageName)»
			«var j = 0»
			«FOR invocation : pattern.invocations»
				«val newPrefix = j++ + "\\"»
				«printPattern(invocation.invokedPattern, newPrefix, invocation.positive)»
				
				«FOR mapEntry : invocation.mapping.entrySet»
					"«node(mapEntry.key, packageName)»" #--# "«node(mapEntry.value, newPrefix + invocation.invokedPattern.name)»"
				«ENDFOR»
			«ENDFOR»
		'''
	}

	/**
	 * Visualizes the create pattern.
	 */
	static def String visualizeCreatePattern(IBeXCreatePattern pattern) {
		'''
			«commonLayoutSettings»
			
			skinparam class {
				BorderColor<<CONTEXT>> «ContextColor»
				BorderColor<<CREATE>> «CreateColor»
				FontColor<<CONTEXT>> «ContextColor»
				FontColor<<CREATE>> «CreateColor»
			}
			
			«visualizeNodes(pattern.contextNodes, 'CONTEXT', '')»
			«visualizeNodes(pattern.createdNodes, 'CREATE', '')»
			«visualizeEdges(pattern.createdEdges, CreateColor, '')»
			
			center footer
				= Create Pattern «pattern.name»
			end footer
		'''
	}

	/**
	 * Visualizes the delete pattern.
	 */
	static def String visualizeDeletePattern(IBeXDeletePattern pattern) {
		'''
			«commonLayoutSettings»
			
			skinparam class {
				BorderColor<<CONTEXT>> «ContextColor»
				BorderColor<<DELETE>> «DeleteColor»
				FontColor<<CONTEXT>> «ContextColor»
				FontColor<<DELETE>> «DeleteColor»
			}
			
			«visualizeNodes(pattern.contextNodes, 'CONTEXT', '')»
			«visualizeNodes(pattern.deletedNodes, 'DELETE', '')»
			«visualizeEdges(pattern.deletedEdges, DeleteColor, '')»
			
			center footer
				= Delete Pattern «pattern.name»
			end footer
		'''
	}

	/**
	 * Outputs the nodes.
	 */
	private static def String visualizeNodes(EList<IBeXNode> nodes, String type, String nodePrefix) {
		'''
			«FOR node : nodes»
				class "«node(node, nodePrefix)»" <<«type»>>
			«ENDFOR»
		'''
	}

	/**
	 * Outputs the node name and type with the prefix.
	 */
	private static def String node(IBeXNode node, String prefix) {
		'''«prefix».«node.name» : «node.type.name»'''
	}

	/**
	 * Outputs the edges.
	 */
	private static def String visualizeEdges(EList<IBeXEdge> edges, String color, String nodePrefix) {
		'''
			«FOR edge : edges»
				"«node(edge.sourceNode, nodePrefix)»" -[#«color»]-> "«node(edge.targetNode, nodePrefix)»": «» <color:«color»>«edge.type.name»
			«ENDFOR»
		'''
	}

	/**
	 * Prints the common settings for all visualizations.
	 */
	private static def String commonLayoutSettings() {
		'''
			hide empty members
			hide circle
			hide stereotype
			
			skinparam padding 2
			skinparam shadowing false
		'''
	}
}
