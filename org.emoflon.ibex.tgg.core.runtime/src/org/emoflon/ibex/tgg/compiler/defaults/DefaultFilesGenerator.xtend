package org.emoflon.ibex.tgg.compiler.defaults

import java.util.Collection
import language.csp.definition.TGGAttributeConstraintDefinition
import org.emoflon.ibex.tgg.compiler.defaults.UserAttrCondHelper

class DefaultFilesGenerator {

	static def generateUserRuntimeAttrCondFactory(Collection<String> userDefConstraints) {
		return '''
			package org.emoflon.ibex.tgg.operational.csp.constraints.factories;
			
			import java.util.HashMap;
			import java.util.HashSet;			
	
			«FOR constraint : userDefConstraints»
				import org.emoflon.ibex.tgg.operational.csp.constraints.custom.«UserAttrCondHelper.getFileName(constraint)»;
			«ENDFOR»
			
			public class UserDefinedRuntimeTGGAttrConstraintFactory extends RuntimeTGGAttrConstraintFactory {
			
				public UserDefinedRuntimeTGGAttrConstraintFactory() {
					super();
				}
				
				@Override
				protected void initialize() {
					creators = new HashMap<>();
					«FOR constraint : userDefConstraints»
						creators.put("«constraint»", () -> new «UserAttrCondHelper.getFileName(constraint)»());
					«ENDFOR»
					
					constraints = new HashSet<String>();
					constraints.addAll(creators.keySet());
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
			import org.apache.commons.lang3.NotImplementedException;
			
			import org.emoflon.ibex.tgg.operational.csp.constraints.factories.UserDefinedRuntimeTGGAttrConstraintFactory;
			import org.emoflon.ibex.tgg.operational.util.IbexOptions;
			«additionalImports»
			
			public class «fileName» extends «strategy» {
			
				public «fileName»(String projectName, String workspacePath, boolean debug) throws IOException {
					super(createIbexOptions().projectName(projectName).workspacePath(workspacePath).debug(debug));
					registerPatternMatchingEngine(new «engine»());
				}
			
				public static void main(String[] args) throws IOException {
					BasicConfigurator.configure();
			
					«setUpRoutine»
				}
			
				«generateMetamodelRegistration()»
				
				private static IbexOptions createIbexOptions() {
						IbexOptions options = new IbexOptions();
						options.projectName("«projectName»");
						options.debug(true);
						options.userDefinedConstraints(new UserDefinedRuntimeTGGAttrConstraintFactory());
						return options;
				}
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
			«fileName» generator = new «fileName»();
			
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
			«fileName» sync = new «fileName»(createIbexOptions());
			
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
			«fileName» cc = new «fileName»(createIbexOptions());
			
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

	static def generateCOAppFile(String projectName, String fileName, String engine, String additionalImports){
		return generateBasicStructure(
			'''
			import org.emoflon.ibex.tgg.operational.strategies.co.CO;
			«additionalImports»
			''',
			fileName,
			"CO",
			engine,
			projectName,
			'''
			«fileName» co = new «fileName»("«projectName»", "./../", false);
			
			logger.info("Starting CO");
			long tic = System.currentTimeMillis();
			co.run();
			long toc = System.currentTimeMillis();
			logger.info("Completed CO in: " + (toc - tic) + " ms");

			co.saveModels();
			co.terminate();
			'''
		)	
	}
		
	def static generateMetamodelRegistration() {
		'''
		protected void registerUserMetamodels() throws IOException {
			// Load and register source and target metamodels
			throw new NotImplementedException("Please check that your source and target metamodels are loaded and registered.");
			
			// Register correspondence metamodel last
			loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
		}
		'''
	}
}
