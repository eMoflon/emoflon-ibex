package org.benchmarx.database.core

import org.benchmarx.emf.Comparator
import java.util.List
import java.util.ArrayList

import static org.junit.Assert.*
import Database.DB
import Database.Table

class DatabaseComparator implements Comparator<DB> {
		TableNormalizer tableNormalizer
	
	new (){
		tableNormalizer = new TableNormalizer();
	}
	
	override assertEquals(DB expected, DB actual) {
		assertTrue(stringify(expected).startsWith("DB"))
		assertEquals(stringify(expected), stringify(actual))
	}
	
	def stringify(DB db) {
		return '''
		DB {
			title = "«db.title»",
			tables = [
			«val List<Table> sortedList = new ArrayList<Table>(db.tables)»
			«tableNormalizer.normalize(sortedList)»
			«FOR c : sortedList SEPARATOR ", "»
				«tableNormalizer.stringify(c)»
			«ENDFOR»
			]
		}
		'''
	}
}