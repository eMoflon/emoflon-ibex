package org.emoflon.ibex.tgg.compiler.builder

import java.util.List

class DefaultFilesHelper {
	
	private static def concatAndFormatMetamodelNameList(List<String> metamodelNames) {
		val StringBuilder sb = new StringBuilder();
		if(metamodelNames === null || metamodelNames.size() == 0) {
			return sb.toString();
		}
		
		for(String name : metamodelNames) {
			sb.append(name);
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	static def generateDefaultSchema(String projectName, List<String> importURIs, List<String> sourceMetamodels,
		List<String> targetMetamodels) {
		var String importSection = "// Add imports here" + System.lineSeparator();
		if (importURIs !== null && importURIs.size() > 0) {
			val StringBuilder sb = new StringBuilder();
			for (String modelURI : importURIs) {
				sb.append("#import \"");
				sb.append(modelURI);
				sb.append("\"")
				sb.append(System.lineSeparator());
			}

			importSection = sb.toString();
		}
		
		val String sourceSection = concatAndFormatMetamodelNameList(sourceMetamodels);
		val String targetSection = concatAndFormatMetamodelNameList(targetMetamodels);

		return '''
			«importSection»
			
			#schema «projectName»
				
			#source {
				«sourceSection»
			}
			
			#target { 
				«targetSection»
			} 
			
			#correspondence {
				
			}
			
			#attributeConditions {
				
			}
		'''
	}

	static def generateDefaultRule(String schema, String ruleName) {
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

	static def generateDefaultAttrCondDefLibrary() {
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
				concat(separator:EString, leftWord:EString, rightWord:EString, result:EString) {
					#sync: [B B B B], [B B B F], [B B F B], [B F F B], [B F B B]
					#gen: [B B B B] , [B B B F], [B B F B], [B F F B], [B F B B], [B F B F], [B B F F], [B F F F]
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
				
				// Semantics: Variable a is set to random string.
				// If it already has a value (B) then nothing is done and the condition is still satisfied.
				setRandomString(a:EString) {
					#sync: [B]
					#gen: [F], [B]
				}
			
			}
		'''
	}
}
