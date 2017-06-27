package org.benchmarx.classInheritanceHierarchy.core

import ClassInheritanceHierarchy.Attribute
import java.util.List
import ClassInheritanceHierarchy.Clazz
import java.util.Comparator
import java.util.Collections
import java.util.ArrayList

class ClazzNormalizer implements Comparator<Clazz> {
	AttributeNormalizer attributeNormalizer
	
	new (){
		attributeNormalizer = new AttributeNormalizer();
	}

	override compare(Clazz clazz1, Clazz clazz2) {
		val clazzString1 = stringify(clazz1);
		val clazzString2 = stringify(clazz2);
		
		return clazzString1.compareTo(clazzString2);
	}
	
	def String stringify(Clazz clazz) {
		'''
		Clazz {
			name = "clazz",
			superClass = "«IF clazz.superClass !== null»superclass«ENDIF»",
			attributes = [
			«val List<Attribute> sortedList = new ArrayList<Attribute>(clazz.attributes)»
			«attributeNormalizer.normalize(sortedList)»
			«FOR a : sortedList SEPARATOR ", "»
				«attributeNormalizer.stringify(a)»
			«ENDFOR»
			]
		}
		'''
	}

	def normalize(List<Clazz> clazzes){
		Collections.sort(clazzes, this);
	}
}