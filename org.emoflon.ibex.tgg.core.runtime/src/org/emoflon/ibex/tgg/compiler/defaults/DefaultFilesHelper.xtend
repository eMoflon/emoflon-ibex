
package org.emoflon.ibex.tgg.compiler.defaults

import java.util.Collection
import language.csp.definition.TGGAttributeConstraintDefinition
import org.emoflon.ibex.tgg.compiler.defaults.UserAttrCondHelper

class DefaultFilesHelper {

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
			package org.emoflon.ibex.tgg.run;
			
			import java.io.IOException;
			import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
			
			public class «fileName» extends SYNC {
			
				public «fileName»(String projectName, String workspacePath, boolean debug) throws IOException {
					super(projectName, workspacePath, debug);
				}
			
				@Override
				protected void registerUserMetamodels() throws IOException {
					// TODO Auto-generated method stub
				}
			
				@Override
				public void saveModels() throws IOException {
					// TODO Auto-generated method stub
				}
			
				@Override
				public void loadModels() throws IOException {
					// TODO Auto-generated method stub
				}
			}

		'''
	}
}
