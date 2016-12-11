package org.moflon.tgg.mosl.defaults

import org.moflon.core.utilities.MoflonUtil

class DefaultFilesHelper {

	static def generateDefaultEPackageForProject(String projectName) {
		return '''
		<?xml version="1.0" encoding="ASCII"?>
		<ecore:EPackage xmi:version="2.0" 
						xmlns:xmi="http://www.omg.org/XMI" 
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
						xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore" 
						name="«MoflonUtil.lastSegmentOf(projectName)»" 
						nsURI="«MoflonUtil.getDefaultURIToEcoreFileInPlugin(projectName)»" 
						nsPrefix="«projectName»">
		</ecore:EPackage>		
		'''
	}

	static def generateDefaultSchema(String projectName){
		return '''
		// Add imports here
		
		#schema «projectName»
			
		#source {
			
		}
		
		#target { 
			
		} 
		
		#correspondence {
			
		}
		
		#attributeConditions {
			
		}
		'''
	}
	
	static def generateDefaultRule(String schema, String ruleName){
		return '''
		#using «schema».*
		#using AttrCondDefLibrary.*
		
		#rule «ruleName» #with «schema»
		
		#source { 
			
		}
		
		#target {
			
		}
		
		#correspondence {
			
		}
		
		#attributeConditions {
			
		}
		'''
	}
	
	static def generateDefaultAttrCondDefLibrary(){
		return '''
		#library AttrCondDefLibrary {
		
			// Semantics:  a:EString == b:EString
			eq_string(a: EString, b: EString) {
				#sync: [B B], [B F], [F B]
				#gen:  [B B], [B F], [F B], [F F]
			}
		
			// Semantics:  a:EInt == b:EInt
			eq_int(a: EInt, b: EInt) {
				#sync: [B B], [B F], [F B]
				#gen:  [B B], [B F], [F B], [F F]
			}
		
			// Semantics:  a:EFloat == b:EFloat
			eq_float(a: EFloat, b: EFloat) {
				#sync: [B B], [B F], [F B]
				#gen:  [B B], [B F], [F B], [F F]
			}
			
			// Semantics:  a:EDouble == b:EDouble
			eq_double(a: EDouble, b: EDouble) {
				#sync: [B B], [B F], [F B]
				#gen:  [B B], [B F], [F B], [F F]
			}
			
			// Semantics:  a:ELong == b:ELong
			eq_long(a: ELong, b: ELong) {
				#sync: [B B], [B F], [F B]
				#gen:  [B B], [B F], [F B], [F F]
			}

			// Semantics:  a:EChar == b:EChar
			eq_char(a: EChar, b: EChar) {
				#sync: [B B], [B F], [F B]
				#gen:  [B B], [B F], [F B], [F F]
			}
						
			// Semantics:  a:EBoolean == b:EBoolean
			eq_boolean(a: EBoolean, b: EBoolean) {
				#sync: [B B], [B F], [F B]
				#gen:  [B B], [B F], [F B], [F F]
			}
								
			// Semantics: Prefix + Word = Result (where + is string concatenation)
			addPrefix(prefix:EString, word:EString, result:EString) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B], [B F F], [F B F]
			}
			
			// Semantics: Suffix + Word = Result (where + is string concatenation)
			addSuffix(suffix:EString, word:EString, result:EString) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B], [B F F], [F F F], [F B F]
			}
		
			// Semantics: LeftWord + Separator + RightWord = Result (where + is string concatenation)
			// Note:  Separator should be occur only once in Result
			concat(leftWord:EString, separator:EString, rightWord:EString, result:EString) {
				#sync: [B B B B], [B B B F], [B B F B], [B F F B], [B F B B]
				#gen: [B B B B] , [B B B F], [B B F B], [B F F B], [B F B B], [B F F F], [B F B F], [B B F F]
			}
		
			// Semantics: VariableString is set to DefaultString if it is free (FB).
			//            If it already has a value (BB) then nothing is done and the condition is still satisfied.
			//            The case (_F) does not make sense for #sync as this should be a fixed default string.
			setDefaultString(variableString:EString, defaultString:EString) {
				#sync: [B B], [F B]
				#gen: [B B], [F B], [F F]
			}
		
			// Semantics: VariableNumber is set to DefaultNumber if it is free (FB).
			//            If it already has a value (BB) then nothing is done and the condition is still satisfied.
			//            The case (_F) does not make sense for #sync as this should be a fixed default number.
			setDefaultNumber(variableNumber:EDouble, defaultNumber:EDouble) {
				#sync: [B B], [F B]
				#gen: [B B], [F B], [F F]
			}
		
			// Semantics:  new Double(stringValue) == doubleValue (where == is equality for doubles)
			stringToDouble(stringValue:EString, doubleValue:EDouble) {
				#sync: [B B], [B F], [F B]
				#gen: [B B], [B F], [F B], [F F]
			}
		
			// Semantics:  new Double(stringValue) == intValue (where == is equality for ints)
			stringToInt(stringValue:EString, intValue:EInt) {
				#sync: [B B], [B F], [F B]
				#gen: [B B], [B F], [F B], [F F]
			}
		
			// Semantics:  operand1 * operand2 == result
			multiply(operand1:EDouble, operand2:EDouble, result:EDouble) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B]
			}
		
			// Semantics: Numerator / Denominator == Result (/ is division for doubles)
			divide(numerator:EDouble, denominator:EDouble, result:EDouble) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B]
			}
		
			// Semantics: summand1 + summand2 == result (where + is addition for Numbers)
			add(summand1:EDouble, summand2:EDouble, result:EDouble) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B], [F F B], [F B F], [B F F]
			}
		
			// Semantics: minuend - subtrahend == result
			sub(minuend:EDouble, subtrahend:EDouble, result:EDouble) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B], [F F B], [B F F], [F B F], [F F F]
			}
		
			// Semantics: max == max(a, b)
			max(a:EDouble, b:EDouble, max:EDouble) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B]
			}
		
			// Semantics: a <= b
			// Note: For FB, BF, and FF, both a and b are set to the same value.
			smallerOrEqual(a:EDouble, b:EDouble) {
				#sync: [B B], [B F], [F B]
				#gen: [B B], [B F], [F B], [F F]
			}
		
		}
		'''
	}
}
