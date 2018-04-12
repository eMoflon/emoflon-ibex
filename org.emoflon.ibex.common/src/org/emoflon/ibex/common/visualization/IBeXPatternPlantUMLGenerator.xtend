package org.emoflon.ibex.common.visualization

import IBeXLanguage.IBeXCreatePattern
import IBeXLanguage.IBeXDeletePattern
import IBeXLanguage.IBeXEdge
import IBeXLanguage.IBeXPattern
import IBeXLanguage.IBeXPatternSet
import IBeXLanguage.IBeXNode

import org.eclipse.emf.common.util.EList

/**
 * Utility methods to generate PlantUML code.
 */
class IBeXPatternPlantUMLGenerator {
	static val ContextColor = 'Black'
	static val CreateColor = 'DarkGreen'
	static val DeleteColor = 'Crimson'

	/**
	 * Visualizes the pattern invocations in the pattern set. 
	 */
	static def String visualizePatternInvocations(IBeXPatternSet patternSet) {
		'''
			«commonLayoutSettings»
			
			«FOR pattern : patternSet.patterns»
				class "«pattern.name»"
			«ENDFOR»
			
			«FOR pattern : patternSet.patterns»
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
	 * Visualizes the pattern.
	 */
	static def String visualizePattern(IBeXPattern pattern) {
		// TODO visualize
		'''
			«commonLayoutSettings»
			
			center footer
				= Pattern «pattern.name»
			end footer
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
			
			«visualizeNodes(pattern.contextNodes, 'CONTEXT')»
			«visualizeNodes(pattern.createdNodes, 'CREATE')»
			«visualizeEdges(pattern.createdEdges, CreateColor)»
			
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
			
			«visualizeNodes(pattern.contextNodes, 'CONTEXT')»
			«visualizeNodes(pattern.deletedNodes, 'DELETE')»
			«visualizeEdges(pattern.deletedEdges, DeleteColor)»
			
			center footer
				= Delete Pattern «pattern.name»
			end footer
		'''
	}

	/**
	 * Visualizes the nodes.
	 */
	static def String visualizeNodes(EList<IBeXNode> nodes, String type) {
		'''
			«FOR node : nodes»
				class "«node.name»" <<«type»>>
			«ENDFOR»
		'''
	}

	/**
	 * Visualizes the edges.
	 */
	static def String visualizeEdges(EList<IBeXEdge> edges, String color) {
		'''
			«FOR edge : edges»
				"«edge.sourceNode.name»" -[#«color»]-> "«edge.targetNode.name»": «» <color:«color»>«edge.type.name»
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
