package org.benchmarx.database.core

import java.util.Comparator
import java.util.List
import Database.Table
import java.util.Collections
import Database.Column
import java.util.ArrayList

class TableNormalizer implements Comparator<Table>{
		ColumnNormalizer columnNormalizer
	
	new (){
		columnNormalizer = new ColumnNormalizer();
	}
	
	override compare(Table table1, Table table2) {
		val tableString1 = stringify(table1)
		val tableString2 = stringify(table2)
		
		return tableString1.compareTo(tableString2);
	}
	
	def normalize(List<Table> tables) {
		Collections.sort(tables, this);
	}
	
	def stringify(Table tables) {
		return '''
		Table {
			heading = "table",
			columns = [
			«val List<Column> sortedList = new ArrayList<Column>(tables.columns)»
			«columnNormalizer.normalize(sortedList)»
			«FOR c : sortedList SEPARATOR ", "»
				«columnNormalizer.stringify(c)»
			«ENDFOR»
			]
		}
		'''
	}
	
}