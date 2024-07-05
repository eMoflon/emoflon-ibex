package org.emoflon.ibex.tgg.codegen

import java.util.Collection
import language.TGGAttributeConstraintDefinition
import org.emoflon.ibex.tgg.editor.tgg.TripleGraphGrammarFile
import org.moflon.core.utilities.MoflonUtil

class DefaultFilesGenerator {

	static def String generateUserRuntimeAttrCondFactory(Collection<String> userDefConstraints, String projectName) {
		'''
			package org.emoflon.ibex.tgg.operational.csp.constraints.factories.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase»;
			
			import java.util.HashMap;
			import java.util.HashSet;			
			
			import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;			
			
			«FOR constraint : userDefConstraints»
				import org.emoflon.ibex.tgg.operational.csp.constraints.custom.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».«UserAttrCondHelper.getFileName(constraint)»;
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

	static def generateUserAttrCondDefStub(TGGAttributeConstraintDefinition tacd, String projectName) {
		return '''
			package org.emoflon.ibex.tgg.operational.csp.constraints.custom.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase»;
			
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

	static def String generateBasicStructure(String additionalImports, String fileName, String strategy, 
		String projectName, String setUpRoutine) {
		'''
			package org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase»;
			
			import java.io.IOException;
			
			import org.apache.log4j.Level;
			import org.apache.log4j.Logger;
			import org.apache.log4j.BasicConfigurator;
			
			import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;
			import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;
			
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
	
	static def String generateDebugStructure(String additionalImports, String fileName, String strategy,
		String projectName, String setUpRoutine, String body) {
		'''
			package org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».debug;
			
			import java.io.IOException;
			
			import org.apache.log4j.Level;
			import org.apache.log4j.Logger;
			import org.apache.log4j.BasicConfigurator;
			
			import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;
			
			import org.emoflon.ibex.tgg.ui.debug.adapter.TGGAdapter.IBeXOperation;
			import org.emoflon.ibex.tgg.ui.debug.adapter.TGGAdapter.VictoryIBeXAdapter;
			
			import org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».config.*;
			
			«additionalImports»
			
			public class «fileName» extends «strategy» {
			
				private static IRegistrationHelper registrationHelper = new _DefaultRegistrationHelper();
			
				public «fileName»() throws IOException {
					super(registrationHelper.createIbexOptions());
				}
			
				public static void main(String[] args) throws IOException {
					BasicConfigurator.configure();
					Logger.getRootLogger().setLevel(Level.INFO);
			
					«setUpRoutine»
				}
				
				«body»
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
	
	static def generateModelGenDebugFile(String projectName, String fileName) {
		return generateDebugStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGEN;
				import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGENStopCriterion;
			''',
			fileName,
			"MODELGEN",
			projectName,
			'''
				boolean restart = true;
				while (restart) {
				    restart = false;
				    
				    logger.info("Starting MODELGEN_Debug");
				    long tic = System.currentTimeMillis();
				    «fileName» generator = new «fileName»();
				    long toc = System.currentTimeMillis();
				    logger.info("Completed init for MODELGEN_Debug in: " + (toc - tic) + " ms");
				
				    MODELGENStopCriterion stop = new MODELGENStopCriterion(generator.getTGG());
				    generator.setStopCriterion(stop);
				    
				    VictoryIBeXAdapter adapter = VictoryIBeXAdapter.create(generator, IBeXOperation.MODELGEN);
					restart = adapter.run(() -> {
					       adapter.register(generator);
					       try {
						    logger.info("Starting MODELGEN_Debug");
						    long runTic = System.currentTimeMillis();
						    generator.run();
						    long runToc = System.currentTimeMillis();
						    logger.info("Completed MODELGEN_Debug in: " + (runToc - runTic) + " ms");
				
						    generator.saveModels();
						    generator.terminate();
						      } catch (IOException pIOE) {
						    logger.error("MODELGEN_Debug threw an IOException", pIOE);
						      }
				    });
				}
			''',
			""
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

	static def generateInitialFwdDebugAppFile(String projectName, String fileName) {
		return generateDebugStructure(
			'''
				import org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».INITIAL_FWD_App;
			''',
			fileName,
			"INITIAL_FWD_App",
			projectName,
			'''
				boolean restart = true;
				while (restart) {
				    restart = false;
				
				    logger.info("Starting INITIAL_FWD_Debug");
				    long tic = System.currentTimeMillis();
				    «fileName» init_fwd = new «fileName»();
				    long toc = System.currentTimeMillis();
				    logger.info("Completed init for INITIAL_FWD_Debug in: " + (toc - tic) + " ms");
				
				    VictoryIBeXAdapter adapter = VictoryIBeXAdapter.create(init_fwd, IBeXOperation.FWD);
				    restart = adapter.run(() -> {
				        adapter.register(init_fwd);
				        try {
				      logger.info("Starting INITIAL_FWD_Debug");
				      long runTic = System.currentTimeMillis();
				      init_fwd.forward();
				      long runToc = System.currentTimeMillis();
				      logger.info("Completed INITIAL_FWD_Debug in: " + (runToc - runTic) + " ms");
				
						    init_fwd.saveModels();
						    init_fwd.terminate();
					    } catch (IOException pIOE) {
					     logger.error("INITIAL_FWD_Debug threw an IOException", pIOE);
					       }
				    });
				}
			''',
			""
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

	static def generateInitialBwdDebugAppFile(String projectName, String fileName) {
		return generateDebugStructure(
			'''
				import org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».INITIAL_BWD_App;
			''',
			fileName,
			"INITIAL_BWD_App",
			projectName,
			'''
				boolean restart = true;
				while (restart) {
				    restart = false;
				
				    logger.info("Starting INITIAL_BWD_Debug");
				    long tic = System.currentTimeMillis();
				    «fileName» init_bwd = new «fileName»();
				    long toc = System.currentTimeMillis();
				    logger.info("Completed init for INITIAL_BWD_Debug in: " + (toc - tic) + " ms");
				
				    VictoryIBeXAdapter adapter = VictoryIBeXAdapter.create(init_bwd, IBeXOperation.FWD);
				    restart = adapter.run(() -> {
				        adapter.register(init_bwd);
				        try {
				      logger.info("Starting INITIAL_BWD_Debug");
				      long runTic = System.currentTimeMillis();
				      init_bwd.backward();
				      long runToc = System.currentTimeMillis();
				      logger.info("Completed INITIAL_BWD_Debug in: " + (runToc - runTic) + " ms");
				
						    init_bwd.saveModels();
						    init_bwd.terminate();
					    } catch (IOException pIOE) {
					     logger.error("INITIAL_BWD_Debug threw an IOException", pIOE);
					       }
				    });
				}
			''',
			""
		)
	}
	
	def static String generateDefaultRegHelperFile(String projectName) {
		'''
			package org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».config;
			
			import java.io.IOException;
				
			import org.eclipse.emf.ecore.resource.ResourceSet;
			import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;
			import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
			import org.emoflon.ibex.tgg.operational.strategies.modules.IbexExecutable;
			
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

	def static String generateRegHelperFile(String projectName, TripleGraphGrammarFile tgg) {
		'''
			package org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».config;
			
			import java.io.IOException;
			
			import org.eclipse.emf.ecore.resource.ResourceSet;
			import org.emoflon.ibex.tgg.operational.csp.constraints.factories.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».UserDefinedRuntimeTGGAttrConstraintFactory;
			import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
			import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;
			import org.emoflon.ibex.tgg.operational.strategies.modules.IbexExecutable;
			import org.emoflon.ibex.tgg.runtime.democles.DemoclesTGGEngine;
			
			public class DemoclesRegistrationHelper implements IRegistrationHelper {
			
				/** Load and register source and target metamodels */
				public void registerMetamodels(ResourceSet rs, IbexExecutable executable) throws IOException {
					// Replace to register generated code or handle other URI-related requirements
					«FOR imp : tgg.imports»
						executable.getResourceHandler().loadAndRegisterMetamodel("«imp.name»");
					«ENDFOR»
				}
			
				/** Create default options **/
				public IbexOptions createIbexOptions() {
					IbexOptions options = new IbexOptions();
					options.blackInterpreter(new DemoclesTGGEngine());
					options.project.name("«MoflonUtil.lastCapitalizedSegmentOf(projectName)»");
					options.project.path("«projectName»");
					options.debug.ibexDebug(false);
					options.csp.userDefinedConstraints(new UserDefinedRuntimeTGGAttrConstraintFactory());
					options.registrationHelper(this);
					return options;
				}
			}
		'''
	}
}
