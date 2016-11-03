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
		
			// Semantics:  0:Object == 1:Object
			eq(0: , 1: ) {
				#sync: [B B], [B F], [F B]
				#gen:  [B B], [B F], [F B], [F F]
			}
		
			// Semantics: 0:Prefix + 1:Word = 2:Result (where + is string concatenation)
			addPrefix(0:EString, 1:EString, 2:EString) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B], [B F F], [F B F]
			}
			
			// Semantics: 0:Suffix + 1:Word = 2:Result (where + is string concatenation)
			addSuffix(0:EString, 1:EString, 2:EString) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B], [B F F], [F F F], [F B F]
			}
		
			// Semantics: 1:LeftWord + 0:Separator + 2:RightWord = 3:Result (where + is string concatenation)
			// Note:  0:Separator should be occur only once in 3:Result
			concat(0:EString, 1:EString, 2:EString, 3:EString) {
				#sync: [B B B B], [B B B F], [B B F B], [B F F B], [B F B B]
				#gen: [B B B B] , [B B B F], [B B F B], [B F F B], [B F B B], [B F F F], [B F B F], [B B F F]
			}
		
			// Semantics: 0:VariableString is set to 1:DefaultString if it is free (FB).
			//            If it already has a value (BB) then nothing is done and the condition is still satisfied.
			//            The case (_F) does not make sense for #sync as this should be a fixed default string.
			setDefaultString(0:EString, 1:EString) {
				#sync: [B B], [F B]
				#gen: [B B], [F B], [F F]
			}
		
			// Semantics: 0:VariableNumber is set to 1:DefaultNumber if it is free (FB).
			//            If it already has a value (BB) then nothing is done and the condition is still satisfied.
			//            The case (_F) does not make sense for #sync as this should be a fixed default number.
			setDefaultNumber(0:EDouble, 1:EDouble) {
				#sync: [B B], [F B]
				#gen: [B B], [F B], [F F]
			}
		
			// Semantics:  new Double(0:String) == 1:Double (where == is equality for doubles)
			stringToDouble(0:EString, 1:EDouble) {
				#sync: [B B], [B F], [F B]
				#gen: [B B], [B F], [F B], [F F]
			}
		
			// Semantics:  new Double(0:String) == 1:Int (where == is equality for ints)
			stringToInt(0:EString, 1:EInt) {
				#sync: [B B], [B F], [F B]
				#gen: [B B], [B F], [F B], [F F]
			}
		
			// Semantics:  0:Operand * 1:Operand == 2:Result
			multiply(0:EDouble, 1:EDouble, 2:EDouble) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B]
			}
		
			// Semantics: 0:Numerator / 1:Denominator == 2:Result (/ is division for doubles)
			divide(0:EDouble, 1:EDouble, 2:EDouble) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B]
			}
		
			// Semantics: 0:a + 1:b == 2:c (where + is addition for Numbers)
			add(0:EDouble, 1:EDouble, 2:EDouble) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B], [F F B], [F B F], [B F F]
			}
		
			// Semantics: 0:a - 1:b == 2:c
			sub(0:EDouble, 1:EDouble, 2:EDouble) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B], [F F B], [B F F], [F B F], [F F F]
			}
		
			// Semantics: 2:c == max(0:a, 1:b)
			max(0:EDouble, 1:EDouble, 2:EDouble) {
				#sync: [B B B], [B B F], [B F B], [F B B]
				#gen: [B B B], [B B F], [B F B], [F B B]
			}
		
			// Semantics: 0:a <= 1:b
			// Note: For FB, BF, and FF, both a and b are set to the same value.
			smallerOrEqual(0:EDouble, 1:EDouble) {
				#sync: [B B], [B F], [F B]
				#gen: [B B], [B F], [F B], [F F]
			}
		
		}
		'''
	}
}
