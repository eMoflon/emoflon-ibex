package org.emoflon.ibex.tgg.compiler.defaults

import java.util.Collection
import language.TGGAttributeConstraintDefinition
import org.moflon.core.utilities.MoflonUtil
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile
 
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

	static def String generateBasicStructure(String additionalImports, String fileName, String strategy, String engine,
		String projectName, String setUpRoutine, String body) {
		'''
			package org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase»;
			
			import java.io.IOException;
			
			import org.apache.log4j.Level;
			import org.apache.log4j.Logger;
			import org.apache.log4j.BasicConfigurator;
			
			import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
			«additionalImports»
			
			public class «fileName» extends «strategy» {
			
				public «fileName»() throws IOException {
					super(createIbexOptions());
					registerBlackInterpreter(new «engine»());
				}
			
				public static void main(String[] args) throws IOException {
					BasicConfigurator.configure();
					Logger.getRootLogger().setLevel(Level.INFO);
			
					«setUpRoutine»
				}
				
				«body»
				
				«generateMetamodelRegistration()»
				
				private static IbexOptions createIbexOptions() {
					return _RegistrationHelper.createIbexOptions();
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
			
			import org.emoflon.ibex.tgg.ui.debug.adapter.TGGAdpater.IBeXOperation;
			import org.emoflon.ibex.tgg.ui.debug.adapter.TGGAdpater.VictoryIBeXAdapter;
			
			«additionalImports»
			
			public class «fileName» extends «strategy» {
			
				public «fileName»() throws IOException {
					super();
				}
			
			    @SuppressWarnings("deprecation")
				public static void main(String[] args) throws IOException {
					BasicConfigurator.configure();
					Logger.getRootLogger().setLevel(Level.INFO);
			
					«setUpRoutine»
				}
				
				«body»
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
			''',
			""
		)
	}
	
	static def generateModelGenDebugFile(String projectName, String fileName) {
		return generateDebugStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGENStopCriterion;
				import org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».MODELGEN_App;
			''',
			fileName,
			"MODELGEN_App",
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
				
				    Thread ibex = new Thread(() -> {
				
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
				    }, "IBeX main thread");
				    ibex.start();
				
				    restart = adapter.runUI();
				        if (ibex.isAlive())
				            try {
				                ibex.join(500);
				            } catch (InterruptedException pIE) {
				            } finally {
				                if (ibex.isAlive())
				                    ibex.stop();
				            }
				}
			''',
			""
		)
	}

	static def generateSyncAppFile(String projectName, String fileName, String engine, String additionalImports) {
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
			''',
			""
		)
	}

	static def generateCCAppFile(String projectName, String fileName, String engine, String additionalImports) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.opt.cc.CC;
				«additionalImports»
			''',
			fileName,
			"CC",
			engine,
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
			''',
			""
		)
	}

	static def generateCOAppFile(String projectName, String fileName, String engine, String additionalImports) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.opt.CO;
				«additionalImports»
			''',
			fileName,
			"CO",
			engine,
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
			''',
			""
		)
	}

	static def generateFWDOptAppFile(String projectName, String fileName, String engine, String additionalImports) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.opt.FWD_OPT;
				«additionalImports»
			''',
			fileName,
			"FWD_OPT",
			engine,
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
			''',
			""
		)
	}

	static def generateBWDOptAppFile(String projectName, String fileName, String engine, String additionalImports) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.opt.BWD_OPT;
				«additionalImports»
			''',
			fileName,
			"BWD_OPT",
			engine,
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
			''',
			""
		)
	}

	static def generateInitialFwdAppFile(String projectName, String fileName, String engine, String additionalImports) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
				import org.eclipse.emf.ecore.util.EcoreUtil;
				import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
				«additionalImports»
			''',
			fileName,
			"SYNC",
			engine,
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
			''',
			'''
				@Override
				public boolean isPatternRelevantForCompiler(String patternName) {
					return patternName.endsWith(PatternSuffixes.FWD);
				}
				
				@Override
				public void loadModels() throws IOException {
					s = loadResource(options.projectPath() + "/instances/src.xmi");
					t = createResource(options.projectPath() + "/instances/trg.xmi");
					c = createResource(options.projectPath() + "/instances/corr.xmi");
					p = createResource(options.projectPath() + "/instances/protocol.xmi");
					
					EcoreUtil.resolveAll(rs);
				}
				
				@Override
				public void saveModels() throws IOException {
					t.save(null);
					c.save(null);
					p.save(null);
				}
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

				    Thread ibex = new Thread(() -> {
				
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
				    }, "IBeX main thread");
				    ibex.start();

				    restart = adapter.runUI();
				        if (ibex.isAlive())
				            try {
				                ibex.join(500);
				            } catch (InterruptedException pIE) {
				            } finally {
				                if (ibex.isAlive())
				                    ibex.stop();
				            }
				}
			''',
			""
		)
	}

	static def generateInitialBwdAppFile(String projectName, String fileName, String engine, String additionalImports) {
		return generateBasicStructure(
			'''
				import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
				import org.eclipse.emf.ecore.util.EcoreUtil;
				import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
				«additionalImports»
			''',
			fileName,
			"SYNC",
			engine,
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
			''',
			'''
				@Override
				public boolean isPatternRelevantForCompiler(String patternName) {
					return patternName.endsWith(PatternSuffixes.BWD);
				}
				
				@Override
				public void loadModels() throws IOException {
					t = loadResource(options.projectPath() + "/instances/trg.xmi");
					s = createResource(options.projectPath() + "/instances/src.xmi");
					c = createResource(options.projectPath() + "/instances/corr.xmi");
					p = createResource(options.projectPath() + "/instances/protocol.xmi");
					
					EcoreUtil.resolveAll(rs);
				}
				
				@Override
				public void saveModels() throws IOException {
					s.save(null);
					c.save(null);
					p.save(null);
				}
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

				    Thread ibex = new Thread(() -> {
				
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
				    }, "IBeX main thread");
				    ibex.start();

				    restart = adapter.runUI();
				        if (ibex.isAlive())
				            try {
				                ibex.join(500);
				            } catch (InterruptedException pIE) {
				            } finally {
				                if (ibex.isAlive())
				                    ibex.stop();
				            }
				}
			''',
			""
		)
	}
	
	def static generateMetamodelRegistration() {
		'''
			@Override
			protected void registerUserMetamodels() throws IOException {
				_RegistrationHelper.registerMetamodels(rs, this);
					
				// Register correspondence metamodel last
				loadAndRegisterCorrMetamodel(options.projectPath() + "/model/" + options.projectName() + ".ecore");
			}
		'''
	}

	def static String generateRegHelperFile(String projectName) {
		'''
			package org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase»;
			
			import java.io.IOException;
			
			import org.eclipse.emf.ecore.resource.ResourceSet;
			import org.emoflon.ibex.tgg.operational.csp.constraints.factories.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase».UserDefinedRuntimeTGGAttrConstraintFactory;
			import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
			import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
			
			public class _RegistrationHelper {
			
				/** Load and register source and target metamodels */
				public static void registerMetamodels(ResourceSet rs, OperationalStrategy strategy) throws IOException {
					// Replace to register generated code or handle other URI-related requirements
					_SchemaBasedAutoRegistration.register(strategy);
				}
			
				/** Create default options **/
				public static IbexOptions createIbexOptions() {
					IbexOptions options = new IbexOptions();
					options.projectName("«MoflonUtil.lastCapitalizedSegmentOf(projectName)»");
					options.projectPath("«projectName»");
					options.debug(false);
					options.userDefinedConstraints(new UserDefinedRuntimeTGGAttrConstraintFactory());
					return options;
				}
			}
		'''
	}
	
	def static String generateSchemaAutoRegFile(String projectName, TripleGraphGrammarFile tgg) {
		'''
			package org.emoflon.ibex.tgg.run.«MoflonUtil.lastCapitalizedSegmentOf(projectName).toLowerCase»;
			
			import java.io.IOException;
			
			import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
			
			/**
			 * Generated by eMoflon::IBeX.
			 * 
			 * Do not edit this class. It is automatically generated and is kept in sync
			 * with the imports in your Schema.tgg file.
			 */
			public class _SchemaBasedAutoRegistration {
				
				public static void register(OperationalStrategy strategy) throws IOException {
					«FOR imp : tgg.imports»
					strategy.loadAndRegisterMetamodel("«imp.name»");
					«ENDFOR»
				}
				
			}
		'''
	}
}
