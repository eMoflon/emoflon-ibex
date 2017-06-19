package org.benchmarx.classInheritanceHierarchy.core

import ClassInheritanceHierarchy.ClassPackage
import org.benchmarx.emf.Comparator
import ClassInheritanceHierarchy.Clazz
import java.util.List
import java.util.ArrayList

import static org.junit.Assert.*

class ClassInheritanceHierarchyComparator implements Comparator<ClassPackage> {
	ClazzNormalizer clazzNormalizer
	
	new (){
		clazzNormalizer = new ClazzNormalizer();
	}
	
	override assertEquals(ClassPackage expected, ClassPackage actual) {
		assertTrue(stringify(expected).startsWith("ClassPackage"))
		assertEquals(stringify(expected), stringify(actual))
	}
	
	def stringify(ClassPackage classPackage) {
		return '''
		ClassPackage {
			name = "«classPackage.name»",
			classes = [
			«val List<Clazz> sortedList = new ArrayList<Clazz>(classPackage.classes)»
			«clazzNormalizer.normalize(sortedList)»
			«FOR c : sortedList SEPARATOR ", "»
				«clazzNormalizer.stringify(c)»
			«ENDFOR»
			]
		}
		'''
	}
	
}