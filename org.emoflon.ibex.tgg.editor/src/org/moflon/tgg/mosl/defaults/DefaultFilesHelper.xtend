package org.moflon.tgg.mosl.defaults

import org.moflon.core.utilities.MoflonUtil
import org.moflon.tgg.mosl.tgg.AttrCondDef
import java.util.Collection
import language.csp.definition.TGGAttributeConstraintDefinition
import org.moflon.tgg.language.algorithm.ApplicationTypes

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

	static def generateUserRuntimeAttrCondFactory(String projectName, Collection<String> userDefConstraints) {
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
			
			public class «projectName»AttrCondDefLibrary extends RuntimeTGGAttrConstraintFactory {
			
				private Collection<String> constraints; 
				private Map<String, Supplier<RuntimeTGGAttributeConstraint>> creators;
				
				public «projectName»AttrCondDefLibrary() {
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

	static def generateRunFile(String projectName, String fileName, RunFileType mode) {
		return '''
			package org.emoflon.ibex.tgg.run;
			
			import java.io.File;
			import java.io.IOException;
			
			import org.eclipse.emf.common.util.URI;
			import org.eclipse.emf.ecore.EPackage;
			import org.eclipse.emf.ecore.EPackage.Registry;
			import org.eclipse.emf.ecore.resource.Resource;
			import org.eclipse.emf.ecore.resource.ResourceSet;
			import org.emoflon.ibex.tgg.operational.*;
			import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil;
			import org.moflon.core.utilities.eMoflonEMFUtil;
			
			import language.LanguagePackage;
			import language.TGG;
			import runtime.RuntimePackage;
			
			import org.emoflon.ibex.tgg.operational.csp.constraints.factories.«projectName»AttrCondDefLibrary;
			
			public class «fileName» {
			
				public static void main(String[] args) throws IOException {
					//BasicConfigurator.configure();
							
					ResourceSet rs = eMoflonEMFUtil.createDefaultResourceSet();
					registerMetamodels(rs);
					
					Resource tggR = rs.getResource(URI.createFileURI("model/«projectName».tgg.xmi"), true);
					TGG tgg = (TGG) tggR.getContents().get(0);
					
					// create your resources 
					Resource s = rs.createResource(URI.createFileURI(new File("instances/src_gen.xmi").getAbsolutePath()));
					Resource t = rs.createResource(URI.createFileURI(new File("instances/trg_gen.xmi").getAbsolutePath()));
					Resource c = rs.createResource(URI.createFileURI(new File("instances/corr_gen.xmi").getAbsolutePath()));
					Resource p = rs.createResource(URI.createFileURI(new File("instances/protocol_gen.xmi").getAbsolutePath()));
					
					// load the resources containing your input 
					«RunFileHelper.getLoadCall(mode)»
					
					System.out.println("Starting «mode.name»");
					long tic = System.currentTimeMillis();
					«RunFileHelper.getCreator(mode)»
					tggRuntime.getCSPProvider().registerFactory(new «projectName»AttrCondDefLibrary());
					
					«projectName»Transformation transformation = new «projectName»Transformation(rs, tggRuntime);						
					transformation.execute();
					
					tggRuntime.run();
					
					transformation.dispose();
					
					long toc = System.currentTimeMillis();
					System.out.println("Completed «mode.name» in: " + (toc-tic) + " ms");
				 
				 	«RunFileHelper.getSaveCall(mode)»
				}
					
				
				private static void registerMetamodels(ResourceSet rs){
					// Register internals
					LanguagePackage.eINSTANCE.getName();
					RuntimePackage.eINSTANCE.getName();
			
					
					// Add mapping for correspondence metamodel
					Resource corr = rs.getResource(URI.createFileURI("model/«projectName».ecore"), true);
					EPackage pcorr = (EPackage) corr.getContents().get(0);
					Registry.INSTANCE.put(corr.getURI().toString(), corr);
					Registry.INSTANCE.put("platform:/resource/«projectName»/model/«projectName».ecore", pcorr);
					Registry.INSTANCE.put("platform:/plugin/«projectName»/model/«projectName».ecore", pcorr);
					
					// SourcePackage.eInstance.getName();
					// TargetPackage.eInstance.getName();
				}
			}
		'''
	}
}
