package org.emoflon.ibex.tgg.compiler.codegen

import org.emoflon.ibex.tgg.compiler.builder.AttrCondDefLibraryProvider
import org.emoflon.ibex.tgg.compiler.builder.UserAttrCondHelper
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary
import org.moflon.core.utilities.MoflonUtil
import java.util.Collection

class DefaultFilesGenerator {

	static def String generateUserRuntimeAttrCondContainer(Collection<TGGAttributeConstraintDefinitionLibrary> libraries, String projectName) {
		'''
			package org.emoflon.ibex.tgg.operational.csp.constraints.custom.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase»;
			
			import java.util.Collection;
			import java.util.LinkedList;			
			
			import org.emoflon.ibex.tgg.operational.csp.constraints.custom.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».*;
			import org.emoflon.ibex.tgg.runtime.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;
			
			public class RuntimeTGGAttrConstraintFactoryContainer {
			
				private Collection<RuntimeTGGAttrConstraintFactory> factories = new LinkedList<>();
			
				public RuntimeTGGAttrConstraintFactoryContainer() {
					«FOR library : libraries»
					factories.add(new «library.name»RuntimeTGGAttrConstraintFactory());
					«ENDFOR»
				}
				
				public Collection<RuntimeTGGAttrConstraintFactory> getFactories() {
					return factories;
				}
			}
		'''
	}

	static def String generateUserRuntimeAttrCondFactory(TGGAttributeConstraintDefinitionLibrary library, String projectName) {
		'''
			package org.emoflon.ibex.tgg.operational.csp.constraints.custom.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase»;
			
			import java.util.HashMap;
			import java.util.HashSet;			
			
			import org.emoflon.ibex.tgg.runtime.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;
			
			«FOR constraint : library.tggAttributeConstraintDefinitions»
				import «AttrCondDefLibraryProvider.ATTR_COND_DEF_USERDEFINED_PACKAGE».«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».«library.name.toLowerCase».«UserAttrCondHelper.getFileName(constraint)»;
			«ENDFOR» 
			
			public class «library.name»RuntimeTGGAttrConstraintFactory extends RuntimeTGGAttrConstraintFactory {
			
				public «library.name»RuntimeTGGAttrConstraintFactory() {
					super();
				}
				
				@Override
				protected void initialize() {
					creators = new HashMap<>();
					«FOR constraint : library.tggAttributeConstraintDefinitions»
						creators.put("«constraint.name»", () -> new «UserAttrCondHelper.getFileName(constraint)»());
					«ENDFOR»
			
					constraints = new HashSet<String>();
					constraints.addAll(creators.keySet());
				}
				
				@Override
				public String getLibraryName() {
					return "«library.name»";
				}
			}
		'''
	}

	static def generateUserAttrCondDefStub(TGGAttributeConstraintDefinition tacd, String projectName) {
		var library = tacd.eContainer as TGGAttributeConstraintDefinitionLibrary
		return '''
			package org.emoflon.ibex.tgg.operational.csp.constraints.custom.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».«library.name.toLowerCase»;
			
			import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraint;
			import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraintVariable;
			
			public class «UserAttrCondHelper.getFileName(tacd)» extends RuntimeTGGAttributeConstraint
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

	static def String generateBasicStructure(String additionalImports, String fileName, String strategy, 
		String projectName, String setUpRoutine) {
		'''
			package org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase»;
			
			import java.io.IOException;
			
			import org.apache.log4j.Level;
			import org.apache.log4j.Logger;
			import org.apache.log4j.BasicConfigurator;
			
			import org.emoflon.ibex.tgg.runtime.config.IRegistrationHelper;
			import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
			import org.emoflon.ibex.tgg.runtime.strategies.sync.SYNC;
			import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;

			
			import org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».config.*;
			«additionalImports»
			
			public class «fileName» extends «strategy» {
			
				// eMoflon supports other pattern matching engines. Replace _DefaultRegistrationHelper with one of the other registrationHelpers from the *.config-package to choose between them. Default: Democles 
				public static IRegistrationHelper registrationHelper = new _DefaultRegistrationHelper();
			
				public «fileName»() throws IOException {
					super(registrationHelper.createIbexOptions().resourceHandler(new TGGResourceHandler() {
						@Override
						public void saveModels() throws IOException {
							// Use the commented code below to implement saveModels individually.
							// source.save(null);
							// target.save(null);
							// corr.save(null);
							// protocol.save(null);
							
							super.saveModels();
						}
						
						@Override
						public void loadModels() throws IOException {
							// Use the commented code below to implement loadModels individually.
							// loadResource loads from a file while createResource creates a new resource without content
							// source = loadResource(options.project.path() + "/instances/src.xmi");
							// target = createResource(options.project.path() + "/instances/trg.xmi");
							// corr = createResource(options.project.path() + "/instances/corr.xmi");
							// protocol = createResource(options.project.path() + "/instances/protocol.xmi");
							
							super.loadModels();
						}
					}));
				}
			
				public static void main(String[] args) throws IOException {
					BasicConfigurator.configure();
					Logger.getRootLogger().setLevel(Level.INFO);
			
					«setUpRoutine»
				}
			}
		'''
	}
	
	static def generateModelGenFile(String projectName, String fileName) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGEN;
				import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGENStopCriterion;
			''',
			fileName,
			"MODELGEN",
			projectName,
			'''
				logger.info("Starting MODELGEN");
				long tic = System.currentTimeMillis();
				«fileName» generator = new «fileName»();
				long toc = System.currentTimeMillis();
				logger.info("Completed init for MODELGEN in: " + (toc - tic) + " ms");
				
				MODELGENStopCriterion stop = new MODELGENStopCriterion(generator.getTGG());
				stop.setTimeOutInMS(1000);
				generator.setStopCriterion(stop);
				
				tic = System.currentTimeMillis();
				generator.run();
				toc = System.currentTimeMillis();
				logger.info("Completed MODELGEN in: " + (toc - tic) + " ms");
				
				generator.saveModels();
				generator.terminate();
			'''
		)
	}
	
	static def generateSyncAppFile(String projectName, String fileName) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
			''',
			fileName,
			"SYNC",
			projectName,
			'''
				logger.info("Starting SYNC");
				long tic = System.currentTimeMillis();
				«fileName» sync = new «fileName»();
				long toc = System.currentTimeMillis();
				logger.info("Completed init for SYNC in: " + (toc - tic) + " ms");
				
				tic = System.currentTimeMillis();
				sync.forward();
				toc = System.currentTimeMillis();
				logger.info("Completed SYNC in: " + (toc - tic) + " ms");
				
				sync.saveModels();
				sync.terminate();
			'''
		)
	}

	static def generateCCAppFile(String projectName, String fileName) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.opt.CC;
			''',
			fileName,
			"CC",
			projectName,
			'''
				«fileName» cc = new «fileName»();
				
				logger.info("Starting CC");
				long tic = System.currentTimeMillis();
				cc.run();
				long toc = System.currentTimeMillis();
				logger.info("Completed CC in: " + (toc - tic) + " ms");
				
				cc.saveModels();
				cc.terminate();
				logger.info(cc.generateConsistencyReport());			
			'''
		)
	}

	static def generateCOAppFile(String projectName, String fileName) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.opt.CO;
			''',
			fileName,
			"CO",
			projectName,
			'''
				«fileName» co = new «fileName»();
				
				logger.info("Starting CO");
				long tic = System.currentTimeMillis();
				co.run();
				long toc = System.currentTimeMillis();
				logger.info("Completed CO in: " + (toc - tic) + " ms");
				
				co.saveModels();
				co.terminate();
				logger.info(co.generateConsistencyReport());
			'''
		)
	}

	static def generateFWDOptAppFile(String projectName, String fileName) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.opt.FWD_OPT;
			''',
			fileName,
			"FWD_OPT",
			projectName,
			'''
				«fileName» fwd_opt = new «fileName»();
				
				logger.info("Starting FWD_OPT");
				long tic = System.currentTimeMillis();
				fwd_opt.run();
				long toc = System.currentTimeMillis();
				logger.info("Completed FWD_OPT in: " + (toc - tic) + " ms");
				
				fwd_opt.saveModels();
				fwd_opt.terminate();
			'''
		)
	}

	static def generateBWDOptAppFile(String projectName, String fileName) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.opt.BWD_OPT;
			''',
			fileName,
			"BWD_OPT",
			projectName,
			'''
				«fileName» bwd_opt = new «fileName»();
				
				logger.info("Starting BWD_OPT");
				long tic = System.currentTimeMillis();
				bwd_opt.run();
				long toc = System.currentTimeMillis();
				logger.info("Completed BWD_OPT in: " + (toc - tic) + " ms");
				
				bwd_opt.saveModels();
				bwd_opt.terminate();
			'''
		)
	}

	static def generateInitialFwdAppFile(String projectName, String fileName) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.sync.INITIAL_FWD;
			''',
			fileName,
			"INITIAL_FWD",
			projectName,
			'''
				logger.info("Starting INITIAL FWD");
				long tic = System.currentTimeMillis();
				«fileName» init_fwd = new «fileName»();
				long toc = System.currentTimeMillis();
				logger.info("Completed init for FWD in: " + (toc - tic) + " ms");
				
				tic = System.currentTimeMillis();
				init_fwd.forward();
				toc = System.currentTimeMillis();
				logger.info("Completed INITIAL_FWD in: " + (toc - tic) + " ms");
				
				init_fwd.saveModels();
				init_fwd.terminate();
			'''
		)
	}

	static def generateInitialBwdAppFile(String projectName, String fileName) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.sync.INITIAL_BWD;
			''',
			fileName,
			"INITIAL_BWD",
			projectName,
			'''
				logger.info("Starting INITIAL BWD");
				long tic = System.currentTimeMillis();
				«fileName» init_bwd = new «fileName»();
				long toc = System.currentTimeMillis();
				logger.info("Completed init for BWD in: " + (toc - tic) + " ms");
				
				tic = System.currentTimeMillis();
				init_bwd.backward();
				toc = System.currentTimeMillis();
				logger.info("Completed INITIAL_BWD in: " + (toc - tic) + " ms");
				
				init_bwd.saveModels();
				init_bwd.terminate();
			'''
		)
	}
	
	static def generateIntegrateAppFile(String projectName, String fileName) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
			''',
			fileName,
			"INTEGRATE",
			projectName,
			'''
				logger.info("Starting INTEGRATE");
				long tic = System.currentTimeMillis();
				«fileName» integrate = new «fileName»();
				long toc = System.currentTimeMillis();
				logger.info("Completed init for INTEGRATE in: " + (toc - tic) + " ms");
				
				tic = System.currentTimeMillis();
				integrate.integrate();
				toc = System.currentTimeMillis();
				logger.info("Completed INTEGRATE in: " + (toc - tic) + " ms");
				
				integrate.saveModels();
				integrate.terminate();
			'''
		)
	}

	
	def static String generateDefaultRegHelperFile(String projectName) {
		'''
			package org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».config;
			
			import java.io.IOException;
				
			import org.eclipse.emf.ecore.resource.ResourceSet;
			import org.emoflon.ibex.tgg.runtime.config.IRegistrationHelper;
			import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
			import org.emoflon.ibex.tgg.runtime.strategies.modules.IbexExecutable;

			public class _DefaultRegistrationHelper implements IRegistrationHelper{
			
				/** Load and register source and target metamodels */
				public void registerMetamodels(ResourceSet rs, IbexExecutable executable) throws IOException {
					// Replace to register generated code or handle other URI-related requirements
					new HiPERegistrationHelper().registerMetamodels(rs, executable);
				}
			
				/** Create default options **/
				public IbexOptions createIbexOptions() {
					return new HiPERegistrationHelper().createIbexOptions();
				}
			}
		'''
	}
}
