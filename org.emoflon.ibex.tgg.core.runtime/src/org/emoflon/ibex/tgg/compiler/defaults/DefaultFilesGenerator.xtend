
package org.emoflon.ibex.tgg.compiler.defaults

import java.util.Collection
import language.csp.definition.TGGAttributeConstraintDefinition
import org.emoflon.ibex.tgg.compiler.defaults.UserAttrCondHelper

class DefaultFilesGenerator {

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

	static def String generateBasicStructure(String additionalImports, String fileName, String strategy, String engine, String projectName, String setUpRoutine) {
		'''
			package org.emoflon.ibex.tgg.run.«projectName.toLowerCase»;
			
			import java.io.IOException;
			
			import org.apache.log4j.BasicConfigurator;
			«additionalImports»
			
			public class «fileName» extends «strategy» {
			
				public «fileName»(String projectName, String workspacePath, boolean flatten, boolean debug) throws IOException {
					super(projectName, workspacePath, flatten, debug);
					registerPatternMatchingEngine(new «engine»());
				}
			
				public static void main(String[] args) throws IOException {
					BasicConfigurator.configure();
			
					«setUpRoutine»
				}
			
				«generateMetamodelRegistration()»
			}
		'''
	}

	static def generateModelGenFile(String projectName, String fileName, String engine, String additionalImports) {
		return generateBasicStructure(
			'''
			import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGEN;
			import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGENStopCriterion;
			«additionalImports»
			''',
			fileName,
			"MODELGEN",
			engine,
			projectName,
			'''
			«fileName» generator = new «fileName»("«projectName»", "./../", false, false);
			
			MODELGENStopCriterion stop = new MODELGENStopCriterion(generator.getTGG());
			stop.setTimeOutInMS(1000);
			generator.setStopCriterion(stop);
			
			logger.info("Starting MODELGEN");
			long tic = System.currentTimeMillis();
			generator.run();
			long toc = System.currentTimeMillis();
			logger.info("Completed MODELGEN in: " + (toc - tic) + " ms");
			
			generator.saveModels();
			generator.terminate();
			'''
		)
	}
	
	static def generateSyncAppFile(String projectName, String fileName, String engine, String additionalImports){
		return generateBasicStructure(
			'''
			import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
			«additionalImports»
			''',
			fileName,
			"SYNC",
			engine,
			projectName,
			'''
			«fileName» sync = new «fileName»("«projectName»", "./../", false, false);
			
			logger.info("Starting SYNC");
			long tic = System.currentTimeMillis();
			sync.forward();
			long toc = System.currentTimeMillis();
			logger.info("Completed SYNC in: " + (toc - tic) + " ms");
			
			sync.saveModels();
			sync.terminate();
			'''
		)
	}
	
	static def generateCCAppFile(String projectName, String fileName, String engine, String additionalImports){
		return generateBasicStructure(
			'''
			import org.emoflon.ibex.tgg.operational.strategies.cc.CC;
			«additionalImports»
			''',
			fileName,
			"CC",
			engine,
			projectName,
			'''
			«fileName» cc = new «fileName»("«projectName»", "./../", false, false);
			
			logger.info("Starting CC");
			long tic = System.currentTimeMillis();
			cc.run();
			long toc = System.currentTimeMillis();
			logger.info("Completed CC in: " + (toc - tic) + " ms");

			cc.saveModels();
			cc.terminate();
			'''
		)	
	}
	
	def static generateMetamodelRegistration() {
		'''
		protected void registerUserMetamodels() throws IOException {
			//FIXME load and register source and target metamodels
			
			// Register correspondence metamodel last
			loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
		}
		'''
	}
}
