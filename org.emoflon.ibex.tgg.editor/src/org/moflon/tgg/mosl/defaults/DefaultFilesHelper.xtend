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
				#sync: BB, BF, FB
				#gen: BB, BF, FB, FF
			}
		
			// Semantics: 0:Prefix + 1:Word = 2:Result (where + is string concatenation)
			addPrefix(0:EString, 1:EString, 2:EString) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB, BFF, FBF
			}
			
			// Semantics: 0:Suffix + 1:Word = 2:Result (where + is string concatenation)
			addSuffix(0:EString, 1:EString, 2:EString) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB, BFF, FFF, FBF
			}
		
			// Semantics: 1:LeftWord + 0:Separator + 2:RightWord = 3:Result (where + is string concatenation)
			// Note:  0:Separator should be occur only once in 3:Result
			concat(0:EString, 1:EString, 2:EString, 3:EString) {
				#sync: BBBB, BBBF, BBFB, BFFB, BFBB
				#gen: BBBB, BBBF, BBFB, BFFB, BFBB, BFFF, BFBF, BBFF
			}
		
			// Semantics: 0:VariableString is set to 1:DefaultString if it is free (FB).
			//            If it already has a value (BB) then nothing is done and the condition is still satisfied.
			//            The case (_F) does not make sense for #sync as this should be a fixed default string.
			setDefaultString(0:EString, 1:EString) {
				#sync: BB, FB
				#gen: BB, FB, FF
			}
		
			// Semantics: 0:VariableNumber is set to 1:DefaultNumber if it is free (FB).
			//            If it already has a value (BB) then nothing is done and the condition is still satisfied.
			//            The case (_F) does not make sense for #sync as this should be a fixed default number.
			setDefaultNumber(0:Number, 1:Number) {
				#sync: BB, FB
				#gen: BB, FB, FF
			}
		
			// Semantics:  new Double(0:String) == 1:Double (where == is equality for doubles)
			stringToDouble(0:EString, 1:EDouble) {
				#sync: BB, BF, FB
				#gen: BB, BF, FB, FF
			}
		
			// Semantics:  new Double(0:String) == 1:Int (where == is equality for ints)
			stringToInt(0:EString, 1:EInt) {
				#sync: BB, BF, FB
				#gen: BB, BF, FB, FF
			}
		
			// Semantics:  0:Operand * 1:Operand == 2:Result
			multiply(0:Number, 1:Number, 2:Number) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB
			}
		
			// Semantics: 0:Numerator / 1:Denominator == 2:Result (/ is division for doubles)
			divide(0:Number, 1:Number, 2:Number) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB
			}
		
			// Semantics: 0:a + 1:b == 2:c (where + is addition for Numbers)
			add(0:Number, 1:Number, 2:Number) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB, FFB, FBF, BFF
			}
		
			// Semantics: 0:a - 1:b == 2:c
			sub(0:Number, 1:Number, 2:Number) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB, FFB, BFF, FBF, FFF
			}
		
			// Semantics: 2:c == max(0:a, 1:b)
			max(0:Number, 1:Number, 2:Number) {
				#sync: BBB, BBF, BFB, FBB
				#gen: BBB, BBF, BFB, FBB
			}
		
			// Semantics: 0:a <= 1:b
			// Note: For FB, BF, and FF, both a and b are set to the same value.
			smallerOrEqual(0:Number, 1:Number) {
				#sync: BB, BF, FB
				#gen: BB, BF, FB, FF
			}
		
		}
		'''
	}
}
