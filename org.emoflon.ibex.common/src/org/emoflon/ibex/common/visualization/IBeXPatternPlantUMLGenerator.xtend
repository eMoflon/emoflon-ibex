package org.emoflon.ibex.common.visualization

import IBeXLanguage.IBeXCreatePattern
import IBeXLanguage.IBeXDeletePattern
import IBeXLanguage.IBeXPatternSet
import IBeXLanguage.IBeXPattern

/**
 * Utility methods to generate PlantUML code.
 */
class IBeXPatternPlantUMLGenerator {
	static val ContextColor = 'Black'
	static val CreateColor = 'DarkGreen'
	static val DeleteColor = 'Crimson'

	static def String visualizePatternInvocations(IBeXPatternSet patternSet) {
		'''
			title IBeXPatternSet
		'''
	}

	static def String visualizePattern(IBeXPattern pattern) {
		'''
			title IBeXPattern
		'''
	}

	static def String visualizeCreatePattern(IBeXCreatePattern pattern) {
		'''
			title IBeXCreatePattern
		'''
	}

	static def String visualizeDeletePattern(IBeXDeletePattern pattern) {
		'''
			title IBeXDeletePattern
		'''
	}
}
