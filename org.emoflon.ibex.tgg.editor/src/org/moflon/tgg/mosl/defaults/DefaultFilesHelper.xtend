
package org.moflon.tgg.mosl.defaults

import org.moflon.core.utilities.MoflonUtil
import java.util.Collection
import language.csp.definition.TGGAttributeConstraintDefinition

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

	static def generateDefaultSchema(String projectName) {
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
					#gen: [B B B B] , [B B B F], [B B F B], [B F F B], [B F B B], [B F B F], [B B F F]
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

	static def generateUserRuntimeAttrCondFactory(Collection<String> userDefConstraints) {
		return '''
			package org.emoflon.ibex.tgg.operational.csp.constraints.factories;
			
			import java.util.Collection;
			import java.util.HashMap;
			import java.util.HashSet;
			import java.util.Map;
			import java.util.function.Supplier;
			import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
			
			«FOR constraint : userDefConstraints»
				import org.emoflon.ibex.tgg.operational.csp.constraints.custom.«UserAttrCondHelper.getFileName(constraint)»;
			«ENDFOR»
			
			public class UserDefinedRuntimeTGGAttrConstraintFactory extends RuntimeTGGAttrConstraintFactory {
			
				private Collection<String> constraints; 
				private Map<String, Supplier<RuntimeTGGAttributeConstraint>> creators;
				
				public UserDefinedRuntimeTGGAttrConstraintFactory() {
					initialize();
				}
				
				private void initialize() {
					creators = new HashMap<>();
					«FOR constraint : userDefConstraints»
						creators.put("«constraint»", () -> new «UserAttrCondHelper.getFileName(constraint)»());
					«ENDFOR»
					
					constraints = new HashSet<String>();
					constraints.addAll(creators.keySet());
				}
				
				@Override
				public RuntimeTGGAttributeConstraint createRuntimeTGGAttributeConstraint(String name) {
					Supplier<RuntimeTGGAttributeConstraint> creator = creators.get(name);
					if(creator == null)
						throw new RuntimeException("CSP not implemented");
					return creator.get();
				}
				
				@Override
				public boolean containsRuntimeTGGAttributeConstraint(String name) {
					return constraints.contains(name);
				}
			}
		'''
	}

	static def generateUserAttrCondDefStub(
		TGGAttributeConstraintDefinition tacd) {
		return '''
			package org.emoflon.ibex.tgg.operational.csp.constraints.custom;
			
			import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
			import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;
			
			public class «UserAttrCondHelper.getFileName(tacd.name)» extends RuntimeTGGAttributeConstraint
			{
			
			   /**
			    * Constraint «tacd.name»(«UserAttrCondHelper.getParameterString(tacd)»)
			    * 
			    * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
			    */
				@Override
				public void solve() {
					if (variables.size() != «tacd.parameterDefinitions.size»)
						throw new RuntimeException("The CSP -«tacd.name.toUpperCase»- needs exactly «tacd.parameterDefinitions.size» variables");
			
					«FOR param : tacd.parameterDefinitions»
						RuntimeTGGAttributeConstraintVariable v«tacd.parameterDefinitions.indexOf(param)» = variables.get(«tacd.parameterDefinitions.indexOf(param)»);
					«ENDFOR»
					String bindingStates = getBindingStates(«UserAttrCondHelper.getParameterString(tacd)»);
			
				  	switch(bindingStates) {
				  		«FOR adornment : UserAttrCondHelper.getAdorments(tacd)»
				  			case "«adornment»": 
				  		«ENDFOR»
				  		default:  throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
				  		 	}
				  	}
			}
			
		'''
	}

	static def generateModelGenFile(String projectName, String fileName) {
		return '''
		package org.emoflon.ibex.tgg.run;
		
		import java.io.IOException;
		
		import org.apache.log4j.BasicConfigurator;
		import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGEN;
		import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGENStopCriterion;
		
		public class «fileName» extends MODELGEN {
		
			public «fileName»(String projectName, String workspacePath, boolean debug) throws IOException {
				super(projectName, workspacePath, debug);
			}
		
			public static void main(String[] args) throws IOException {
				BasicConfigurator.configure();
		
				«fileName» generator = new «fileName»("«projectName»", "./../", false);
		
				MODELGENStopCriterion stop = new MODELGENStopCriterion(generator.tgg);
				stop.setTimeOutInMS(1000);
				generator.setStopCriterion(stop);
		
				logger.info("Starting MODELGEN");
				long tic = System.currentTimeMillis();
				generator.run();
				long toc = System.currentTimeMillis();
				logger.info("Completed MODELGEN in: " + (toc - tic) + " ms");
		
				generator.saveModels();
				generator.terminate();
			}
		
			protected void registerUserMetamodels() throws IOException {
				loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
				//FIXME load and register source and target metamodels
			}
		}
		'''
	}
	
	static def generateSyncAppFile(String projectName, String fileName){
		return '''
			TODO
		'''
	}
}
