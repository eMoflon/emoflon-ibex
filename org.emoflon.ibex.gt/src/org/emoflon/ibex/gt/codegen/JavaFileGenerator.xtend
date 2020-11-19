package org.emoflon.ibex.gt.codegen

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import java.util.Set

import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EEnum
import org.emoflon.ibex.gt.codegen.EClassifiersManager

import java.util.HashSet
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern

/**
 * This class contains the templates for the API Java classes.
 */
class JavaFileGenerator {
	/**
	 * The name of the package.
	 */
	String packageName

	/**
	 * The prefix for the API class name.
	 */
	String classNamePrefix

	/**
	 * Utility to handle the mapping between EClassifier names to meta-model names.
	 */
	EClassifiersManager eClassifiersManager
	
	/**
	 * Utility class for probability class generation
	 */
	 ArithmeticExtensionGenerator probabilityGenerator;
	 
	/**
	 * Creates a new JavaFileGenerator.
	 */
	new(String classNamePrefix, String packageName, EClassifiersManager eClassifiersManager) {
		this.classNamePrefix = classNamePrefix
		this.packageName = packageName
		this.eClassifiersManager = eClassifiersManager
		probabilityGenerator = new ArithmeticExtensionGenerator(packageName);
	}

	/**
	 * Generates the Java API class.
	 */
	def generateAPIClass(IFolder apiPackage, IBeXModel ibexModel, String patternPath) {
		val imports = newHashSet(
			'org.eclipse.emf.common.util.URI',
			'org.eclipse.emf.ecore.resource.Resource',
			'org.eclipse.emf.ecore.resource.ResourceSet',
			'org.emoflon.ibex.common.operational.IContextPatternInterpreter',
			'org.emoflon.ibex.gt.api.GraphTransformationAPI',
			'org.emoflon.ibex.gt.api.GraphTransformationPattern',
			'java.util.Map',
			'java.util.function.Supplier',
			'org.emoflon.ibex.gt.api.GraphTransformationRule',
			'java.util.HashMap'
		)
		
		val rulePreconditions = new HashSet
		
		ibexModel.ruleSet.rules.forEach [ rule |
			imports.add('''«getSubPackageName('api.rules')».«getRuleClassName(rule)»''')
			imports.addAll(eClassifiersManager.getImportsForDataTypes(rule.parameters))
			rulePreconditions.add(rule.lhs)]
		
		ibexModel.patternSet.contextPatterns
			.filter [ pattern | !rulePreconditions.contains(pattern)]
			.filter [ pattern | !pattern.name.contains("CONDITION")]
			.filter [pattern  | !((pattern instanceof IBeXContextPattern) && (pattern as IBeXContextPattern).isSubpattern())]
			.filter [pattern   | !((pattern instanceof IBeXContextPattern) && (pattern as IBeXContextPattern).isOptimizedDisjoint())]
			.forEach [ pattern |
				imports.add('''«getSubPackageName('api.rules')».«getPatternClassName(pattern)»''')
				if(pattern instanceof IBeXContextPattern) {
					val context = pattern as IBeXContextPattern
					imports.addAll(eClassifiersManager.getImportsForDataTypes(context.parameters))
				} else if (pattern instanceof IBeXContextAlternatives) {
					val context = (pattern as IBeXContextAlternatives).context
					imports.addAll(eClassifiersManager.getImportsForDataTypes(context.parameters))
				} else {
					val context = (pattern as IBeXDisjunctContextPattern).nonOptimizedPattern
					imports.addAll(eClassifiersManager.getImportsForDataTypes(context.parameters))
				}]
				

		val apiSourceCode = '''			
			«printHeader(getSubPackageName('api'), imports)»
			
			/**
			 * The «APIClassName» with «ibexModel.ruleSet.rules.size» rules and «ibexModel.patternSet.contextPatterns.size» patterns.
			 */
			public class «APIClassName» extends GraphTransformationAPI {
				
				public static String patternPath = "«patternPath»";
			
				/**
				 * Creates a new «APIClassName».
				 *
				 * @param engine
				 *            the engine to use for queries and transformations
				 * @param model
				 *            the resource set containing the model file
				 * @param workspacePath
				 *            the path to the workspace which is concatenated with the project
				 *            relative path to the patterns
				 */
				public «APIClassName»(final IContextPatternInterpreter engine, final ResourceSet model, final String workspacePath) {
					super(engine, model);
					URI uri = URI.createFileURI(workspacePath + patternPath);
					interpreter.loadPatternSet(uri);
					patternMap = initiatePatternMap();
					gillespieMap = initiateGillespieMap();
				}
			
				/**
				 * Creates a new «APIClassName».
				 *
				 * @param engine
				 *            the engine to use for queries and transformations
				 * @param model
				 *            the resource set containing the model file
				 * @param defaultResource
				 *            the default resource
				 * @param workspacePath
				 *            the path to the workspace which is concatenated with the project
				 *            relative path to the patterns
				 */
				public «APIClassName»(final IContextPatternInterpreter engine, final ResourceSet model, final Resource defaultResource,
						final String workspacePath) {
					super(engine, model, defaultResource);
					URI uri = URI.createFileURI(workspacePath + patternPath);
					interpreter.loadPatternSet(uri);
					patternMap = initiatePatternMap();
					gillespieMap = initiateGillespieMap();
				}
				
				@Override
				protected Map<String, Supplier<? extends GraphTransformationPattern<?,?>>> initiatePatternMap(){
					Map<String, Supplier<? extends GraphTransformationPattern<?,?>>> map = new HashMap<>();
					«FOR rule: ibexModel.ruleSet.rules»
					«IF rule.parameters.empty»
					map.put("«rule.name»", () -> «rule.name»());
					«ENDIF»
					«ENDFOR»
					«FOR pattern: ibexModel.patternSet.contextPatterns
						.filter [ pattern | !rulePreconditions.contains(pattern)]
						.filter [ pattern | !pattern.name.contains("CONDITION")]
						.filter [pattern | !((pattern instanceof IBeXContextPattern) && (pattern as IBeXContextPattern).isSubpattern())]
						.filter [pattern   | !((pattern instanceof IBeXContextPattern) && (pattern as IBeXContextPattern).isOptimizedDisjoint())]»
					«IF getPatternParameter(pattern).empty»
					map.put("«pattern.name»", () -> «pattern.name»());
					«ENDIF»
					«ENDFOR»
					return map;
				}
				
				@Override
				protected Map<GraphTransformationRule<?,?>, double[]> initiateGillespieMap(){
					Map<GraphTransformationRule<?,?>, double[]> map = 
						new HashMap<>();
					«FOR rule: ibexModel.ruleSet.rules»
					«IF rule.parameters.empty && rule.probability!== null»
					«IF probabilityGenerator.isStatic(rule.probability)»
					map.put(«rule.name»(), new double[]{
					«rule.name»().getProbability().get().getProbability(), 0.0});					
					«ENDIF»
					«ENDIF»
					«ENDFOR»
					return map;
				}
								
				«FOR rule : ibexModel.ruleSet.rules»
						/**
						* Creates a new instance of the rule «getRuleSignature(rule)» which does the following:
						* «getRuleDocumentation(rule)»
						*
						* @return the new instance of the rule»
						*/
						public «getRuleClassName(rule)» «rule.name»(«FOR parameter : rule.parameters SEPARATOR ', '»final «getJavaType(parameter.type)» «parameter.name»Value«ENDFOR») {
							try{
								«getRuleClassName(rule)» rule = («getRuleClassName(rule)») interpreter.getRegisteredGraphTransformationPattern("«rule.name»");
								«FOR parameter : rule.parameters»
								rule.«getMethodName('set', parameter.name)»(«parameter.name»Value);
								«ENDFOR»
								return rule;
							} catch(Exception e) {
								return new «getRuleClassName(rule)»(this, interpreter«FOR parameter : rule.parameters BEFORE ', 'SEPARATOR ', '»«parameter.name»Value«ENDFOR»);
							}
						}
			«ENDFOR»
				«FOR pattern : ibexModel.patternSet.contextPatterns
						.filter [ pattern | !rulePreconditions.contains(pattern)]
						.filter [ pattern | !pattern.name.contains("CONDITION")]
						.filter [pattern | !((pattern instanceof IBeXContextPattern) && (pattern as IBeXContextPattern).isSubpattern())]
						.filter [pattern   | !((pattern instanceof IBeXContextPattern) && (pattern as IBeXContextPattern).isOptimizedDisjoint())]»
						/**
						* Creates a new instance of the pattern «getPatternSignature(pattern)» which does the following:
						* «getPatternDocumentation(pattern)»
						*
						* @return the new instance of the pattern»
						*/
						public «getPatternClassName(pattern)» «pattern.name»(«FOR parameter : getPatternParameter(pattern) SEPARATOR ', '»final «getJavaType(parameter.type)» «parameter.name»Value«ENDFOR») {
							try{
								«getPatternClassName(pattern)» pattern = («getPatternClassName(pattern)») interpreter.getRegisteredGraphTransformationPattern("«pattern.name»");
								«FOR parameter : getPatternParameter(pattern)»
								pattern.«getMethodName('set', parameter.name)»(«parameter.name»Value);
								«ENDFOR»
								return pattern;
							} catch(Exception e) {
								return new «getPatternClassName(pattern)»(this, interpreter«FOR parameter : getPatternParameter(pattern) BEFORE ', 'SEPARATOR ', '»«parameter.name»Value«ENDFOR»);
							}
						}
						«ENDFOR»
			}
		'''
		writeFile(apiPackage.getFile(APIClassName + '.java'), apiSourceCode)
	}

	/**
	 * Generates the Java App class.
	 */
	def generateAppClass(IFolder apiPackage) {
		val imports = eClassifiersManager.importsForPackages
		imports.addAll(
			'org.emoflon.ibex.common.operational.IContextPatternInterpreter',
			'org.emoflon.ibex.gt.api.GraphTransformationApp'
		)
		val appClassName = classNamePrefix + 'App'
		val appSourceCode = '''
			«printHeader(getSubPackageName('api'), imports)»
			
			/**
			 * An application using the «APIClassName».
			 */
			public class «appClassName» extends GraphTransformationApp<«APIClassName»> {
			
				/**
				 * Creates the application with the given engine.
				 * 
				 * @param engine
				 *            the pattern matching engine
				 */
				public «appClassName»(final IContextPatternInterpreter engine) {
					super(engine);
				}
			
				/**
				 * Creates the application with the given engine.
				 * 
				 * @param engine
				 *            the pattern matching engine
				 * @param workspacePath
				 *            the workspace path
				 */
				public «appClassName»(final IContextPatternInterpreter engine, final String workspacePath) {
					super(engine, workspacePath);
				}
			
				@Override
				public void registerMetaModels() {
					«FOR p : eClassifiersManager.packages»
						registerMetaModel(«p».eINSTANCE);
					«ENDFOR»
				}
			
				@Override
				public «APIClassName» initAPI() {
					if (defaultResource.isPresent()) {
						return new «APIClassName»(engine, resourceSet, defaultResource.get(), workspacePath);
					}
					return new «APIClassName»(engine, resourceSet, workspacePath);
				}
			}
		'''
		writeFile(apiPackage.getFile(appClassName + '.java'), appSourceCode)
	}

	/**
	 * Generates the App class for the concrete engine. 
	 */
	def generateAppClassForEngine(IFolder apiPackage, GTEngineExtension engine) {
		val appClassName = classNamePrefix + 'App'
		val engineAppClassName = classNamePrefix + engine.engineName + 'App'
		val concreteAppSourceCode = '''
			«printHeader(getSubPackageName('api'), engine.imports)»
			
			/**
			 * An application using the «APIClassName» with «engine.engineName».
			 */
			public class «engineAppClassName» extends «appClassName» {
			
				/**
				 * Creates the application with «engine.engineName».
				 */
				public «engineAppClassName»() {
					super(new «engine.engineClassName»());
				}
			
				/**
				 * Creates the application with «engine.engineName».
				 * 
				 * @param workspacePath
				 *            the workspace path
				 */
				public «engineAppClassName»(final String workspacePath) {
					super(new «engine.engineClassName»(), workspacePath);
				}
			}
		'''
		writeFile(apiPackage.getFile(engineAppClassName + '.java'), concreteAppSourceCode)
	}

	/**
	 * Generates the Java Match class for the given rule.
	 */
	def generateMatchClass(IFolder apiMatchesPackage, IBeXRule rule) {
		val imports = eClassifiersManager.getImportsForNodeTypes(EditorToIBeXPatternHelper.getAllRuleNodes(rule))
		imports.addAll(
			'org.emoflon.ibex.common.operational.IMatch',
			'org.emoflon.ibex.gt.api.GraphTransformationMatch',
			'''«getSubPackageName('api.rules')».«getRuleClassName(rule)»'''
		)

		val matchSourceCode = '''
			«printHeader(getSubPackageName('api.matches'), imports)»
			
			/**
			 * A match for the rule «getRuleSignature(rule)».
			 */
			public class «getMatchClassName(rule)» extends GraphTransformationMatch<«getMatchClassName(rule)», «getRuleClassName(rule)»> {
				«FOR node : EditorToIBeXPatternHelper.getAllRuleNodes(rule)»
					private «getVariableType(node)» «getVariableName(node)»;
				«ENDFOR»
			
				/**
				 * Creates a new match for the rule «getRuleSignature(rule)».
				 * 
				 * @param pattern
				 *            the pattern
				 * @param match
				 *            the untyped match
				 */
				public «getMatchClassName(rule)»(final «getRuleClassName(rule)» pattern, final IMatch match) {
					super(pattern, match);
					«FOR node : EditorToIBeXPatternHelper.getAllRuleNodes(rule)»
						«getVariableName(node)» = («getVariableType(node)») match.get("«node.name»");
					«ENDFOR»
				}
			«FOR node : EditorToIBeXPatternHelper.getAllRuleNodes(rule)»
				
					/**
					 * Returns the «node.name».
					 *
					 * @return the «node.name»
					 */
					public «getVariableType(node)» «getMethodName('get', node.name)»() {
						return «getVariableName(node)»;
					}
			«ENDFOR»
			
				@Override
				public String toString() {
					String s = "match {" + System.lineSeparator();
					«FOR node : EditorToIBeXPatternHelper.getAllRuleNodes(rule)»
						s += "	«node.name» --> " + «getVariableName(node)» + System.lineSeparator();
					«ENDFOR»
					s += "} for " + getPattern();
					return s;
				}
			}
		'''
		writeFile(apiMatchesPackage.getFile(getMatchClassName(rule) + ".java"), matchSourceCode)
	}
	
	/**
	 * Generates the Java Match class for the given pattern.
	 */
	def generateMatchClass(IFolder apiMatchesPackage, IBeXContext pattern) {
		val imports = eClassifiersManager.getImportsForNodeTypes(EditorToIBeXPatternHelper.getPatternNodes(pattern))
		imports.addAll(
			'org.emoflon.ibex.common.operational.IMatch',
			'org.emoflon.ibex.gt.api.GraphTransformationMatch',
			'''«getSubPackageName('api.rules')».«getPatternClassName(pattern)»'''
		)

		val matchSourceCode = '''
			«printHeader(getSubPackageName('api.matches'), imports)»
			
			/**
			 * A match for the pattern «getPatternSignature(pattern)».
			 */
			public class «getMatchClassName(pattern)» extends GraphTransformationMatch<«getMatchClassName(pattern)», «getPatternClassName(pattern)»> {
				«FOR node : EditorToIBeXPatternHelper.getPatternNodes(pattern)»
					private «getVariableType(node)» «getVariableName(node)»;
				«ENDFOR»
			
				/**
				 * Creates a new match for the pattern «getPatternSignature(pattern)».
				 * 
				 * @param pattern
				 *            the pattern
				 * @param match
				 *            the untyped match
				 */
				public «getMatchClassName(pattern)»(final «getPatternClassName(pattern)» pattern, final IMatch match) {
					super(pattern, match);
					«FOR node : EditorToIBeXPatternHelper.getPatternNodes(pattern)»
						«getVariableName(node)» = («getVariableType(node)») match.get("«node.name»");
					«ENDFOR»
				}
			«FOR node : EditorToIBeXPatternHelper.getPatternNodes(pattern)»
				
					/**
					 * Returns the «node.name».
					 *
					 * @return the «node.name»
					 */
					public «getVariableType(node)» «getMethodName('get', node.name)»() {
						return «getVariableName(node)»;
					}
			«ENDFOR»
			
				@Override
				public String toString() {
					String s = "match {" + System.lineSeparator();
					«FOR node : EditorToIBeXPatternHelper.getPatternNodes(pattern)»
						s += "	«node.name» --> " + «getVariableName(node)» + System.lineSeparator();
					«ENDFOR»
					s += "} for " + getPattern();
					return s;
				}
			}
		'''
		writeFile(apiMatchesPackage.getFile(getMatchClassName(pattern) + ".java"), matchSourceCode)
	}

	/**
	 * Generates the Java Pattern/Rule class for the given rule.
	 */
	def generateRuleClass(IFolder rulesPackage, IBeXRule rule) {
		val imports = eClassifiersManager.getImportsForNodeTypes(EditorToIBeXPatternHelper.getRuleContextNodes(rule))
		imports.addAll(eClassifiersManager.getImportsForDataTypes(rule.parameters))
		imports.addAll(
			'java.util.ArrayList',
			'java.util.List',
			'java.util.HashMap',
			'java.util.stream.Stream',
			'org.emoflon.ibex.common.operational.IMatch',
			'org.emoflon.ibex.gt.api.GraphTransformationRule',
			'org.emoflon.ibex.gt.engine.GraphTransformationInterpreter',
			'java.util.Optional',
			'''«getSubPackageName('api')».«APIClassName»''',
			'''«getSubPackageName('api.matches')».«getMatchClassName(rule)»'''
		)
		if (rule.parameters.size > 0 || EditorToIBeXPatternHelper.getRuleContextNodes(rule).size > 0) {
			imports.add('java.util.Objects');
		}
		
		imports.addAll(getProbabilityImports(rule))

		val ruleSourceCode = '''
			«printHeader(getSubPackageName('api.rules'), imports)»
			
			/**
			 * The rule «getRuleSignature(rule)» which does the following:
			 * «getRuleDocumentation(rule)»
			 */
			@SuppressWarnings("unused")
			public class «getRuleClassName(rule)» extends GraphTransformationRule<«getMatchClassName(rule)», «getRuleClassName(rule)»> {
				private static String patternName = "«rule.name»";
			
				/**
				 * Creates a new rule «rule.name»(«FOR parameter : rule.parameters SEPARATOR ', '»«parameter.name»«ENDFOR»).
				 * 
				 * @param api
				 *            the API the rule belongs to
				 * @param interpreter
				 *            the interpreter
				 «FOR parameter : rule.parameters»
				 	* @param «parameter.name»Value
				 	*            the value for the parameter «parameter.name»
				 «ENDFOR»
				 */
				 
				/**
				 * The probability that the rule will be applied; if the rule has no probability,
				 * then the Optional will be empty
				 */
			
				public «getRuleClassName(rule)»(final «APIClassName» api, final GraphTransformationInterpreter interpreter«IF rule.parameters.size == 0») {«ELSE»,«ENDIF»
						«FOR parameter : rule.parameters SEPARATOR ', ' AFTER ') {'»final «getJavaType(parameter.type)» «parameter.name»Value«ENDFOR»
					super(api, interpreter, patternName, «probabilityGenerator.getProbabilityInitialization(rule)»);
					«FOR parameter : rule.parameters»
						«getMethodName('set', parameter.name)»(«parameter.name»Value);
					«ENDFOR»
				}
			
				@Override
				public «getMatchClassName(rule)» convertMatch(final IMatch match) {
					return new «getMatchClassName(rule)»(this, match);
				}
			
				@Override
				protected List<String> getParameterNames() {
					List<String> names = new ArrayList<String>();
					«FOR node : EditorToIBeXPatternHelper.getRuleContextNodes(rule)»
						names.add("«node.name»");
					«ENDFOR»
					return names;
				}
			«FOR node : EditorToIBeXPatternHelper.getRuleContextNodes(rule)»
				
					/**
					 * Binds the node «node.name» to the given object.
					 *
					 * @param object
					 *            the object to set
					 */
					public «getRuleClassName(rule)» «getMethodName('bind', node.name)»(final «getVariableType(node)» object) {
						parameters.put("«node.name»", Objects.requireNonNull(object, "«node.name» must not be null!"));
						return this;
					}
					
					/**
					 * Unbinds the node «node.name» to the given object.
					 *
					 * @param object
					 *            the object to set
					 */
					public «getRuleClassName(rule)» «getMethodName('unbind', node.name)»() {
						parameters.remove("«node.name»");
						return this;
					}
			«ENDFOR»
			«FOR parameter : rule.parameters»
					/**
					 * Sets the parameter «parameter.name».
					 *
					 * @param value
					 *            the value to set
					 */
					public «getRuleClassName(rule)» «getMethodName('set', parameter.name)»(final «getJavaType(parameter.type)» value) {
						parameters.put("«parameter.name»", Objects.requireNonNull(value, "«parameter.name» must not be null!"));
						return this;
					}
			«ENDFOR»
				«IF !rule.arithmeticConstraints.empty»
				@Override
				public boolean isMatchValid(IMatch match){
					return «FOR constraint: rule.arithmeticConstraints SEPARATOR '&&'»
«««						Protect against div/0
						«FOR arithmeticConstraint: ArithmeticExtensionGenerator::getArithmeticConstraint(constraint.lhs, constraint.rhs, true)»
						«arithmeticConstraint» &&
						«ENDFOR»
						«ArithmeticExtensionGenerator.transformExpression(constraint.lhs, true)»«getRelation(constraint.relation)»«ArithmeticExtensionGenerator.transformExpression(constraint.rhs, true)»
						«ENDFOR»;
				}
				«ENDIF»
				@Override
				public String toString() {
					String s = "rule " + patternName + " {" + System.lineSeparator();
					«FOR node : EditorToIBeXPatternHelper.getRuleContextNodes(rule)»
						s += "	«node.name» --> " + parameters.get("«node.name»") + System.lineSeparator();
					«ENDFOR»
					«FOR parameter : rule.parameters»
						s += "	«parameter.name» --> " + parameters.get("«parameter.name»") + System.lineSeparator();
					«ENDFOR»
					s += "}";
					return s;
				}
			}
		'''
		writeFile(rulesPackage.getFile(getRuleClassName(rule) + ".java"), ruleSourceCode)
	}
	
	/**
	 * Generates the Java Pattern/Rule class for the given rule.
	 */
	def generatePatternClass(IFolder rulesPackage, IBeXContext pattern) {
		val imports = eClassifiersManager.getImportsForNodeTypes(EditorToIBeXPatternHelper.getPatternNodes(pattern))
		imports.addAll(eClassifiersManager.getImportsForDataTypes(getPatternParameter(pattern)))
		imports.addAll(
			'java.util.ArrayList',
			'java.util.List',
			'java.util.HashMap',
			'java.util.stream.Stream',
			'java.util.Optional',
			'org.emoflon.ibex.common.operational.IMatch',
			'org.emoflon.ibex.gt.api.GraphTransformationPattern',
			'org.emoflon.ibex.gt.engine.GraphTransformationInterpreter',
			'''«getSubPackageName('api')».«APIClassName»''',
			'''«getSubPackageName('api.matches')».«getMatchClassName(pattern)»'''
		)
		if (getPatternParameter(pattern).size > 0 || EditorToIBeXPatternHelper.getPatternNodes(pattern).size > 0) {
			imports.add('java.util.Objects');
		}

		val ruleSourceCode = '''
			«printHeader(getSubPackageName('api.rules'), imports)»
			
			/**
			 * The pattern «EditorToIBeXPatternHelper.getPatternNodes(pattern)» which does the following:
			 * «getPatternDocumentation(pattern)»
			 */
			@SuppressWarnings("unused")
			public class «getPatternClassName(pattern)» extends GraphTransformationPattern<«getMatchClassName(pattern)», «getPatternClassName(pattern)»> {
				private static String patternName = "«pattern.name»";
			
				/**
				 * Creates a new pattern «pattern.name»(«FOR parameter : getPatternParameter(pattern) SEPARATOR ', '»«parameter.name»«ENDFOR»).
				 * 
				 * @param api
				 *            the API the pattern belongs to
				 * @param interpreter
				 *            the interpreter
				 «FOR parameter : getPatternParameter(pattern)»
				 	* @param «parameter.name»Value
				 	*            the value for the parameter «parameter.name»
				 «ENDFOR»
				 */
				
				public «getPatternClassName(pattern)»(final «APIClassName» api, final GraphTransformationInterpreter interpreter«IF getPatternParameter(pattern).size == 0») {«ELSE»,«ENDIF»
						«FOR parameter : getPatternParameter(pattern) SEPARATOR ', ' AFTER ') {'»final «getJavaType(parameter.type)» «parameter.name»Value«ENDFOR»
					super(api, interpreter, patternName);
					«FOR parameter : getPatternParameter(pattern)»
						«getMethodName('set', parameter.name)»(«parameter.name»Value);
					«ENDFOR»
				}
			
				@Override
				public «getMatchClassName(pattern)» convertMatch(final IMatch match) {
					return new «getMatchClassName(pattern)»(this, match);
				}
			
				@Override
				protected List<String> getParameterNames() {
					List<String> names = new ArrayList<String>();
					«FOR node : EditorToIBeXPatternHelper.getPatternNodes(pattern)»
						names.add("«node.name»");
					«ENDFOR»
					return names;
				}
			«FOR node : EditorToIBeXPatternHelper.getPatternNodes(pattern)»
				
					/**
					 * Binds the node «node.name» to the given object.
					 *
					 * @param object
					 *            the object to set
					 */
					public «getPatternClassName(pattern)» «getMethodName('bind', node.name)»(final «getVariableType(node)» object) {
						parameters.put("«node.name»", Objects.requireNonNull(object, "«node.name» must not be null!"));
						return this;
					}
					
					/**
					 * Unbinds the node «node.name» to the given object.
					 *
					 * @param object
					 *            the object to set
					 */
					public «getPatternClassName(pattern)» «getMethodName('unbind', node.name)»() {
						parameters.remove("«node.name»");
						return this;
					}
			«ENDFOR»
			«FOR parameter : getPatternParameter(pattern)»
				
					/**
					 * Sets the parameter «parameter.name» to the given value.
					 *
					 * @param value
					 *            the value to set
					 */
					public «getPatternClassName(pattern)» «getMethodName('set', parameter.name)»(final «getJavaType(parameter.type)» value) {
						parameters.put("«parameter.name»", Objects.requireNonNull(value, "«parameter.name» must not be null!"));
						return this;
					}
			«ENDFOR»
				
				«IF !EditorToIBeXPatternHelper.getArithmeticConstraints(pattern).empty»
				@Override
				public boolean isMatchValid(IMatch match){
					return «FOR constraint: EditorToIBeXPatternHelper.getArithmeticConstraints(pattern) SEPARATOR '&&'» 
«««						Protection from div/0
						«FOR arithmeticConstraint: ArithmeticExtensionGenerator::getArithmeticConstraint(constraint.lhs, constraint.rhs, true)»
						«arithmeticConstraint» && «ENDFOR»
							«ArithmeticExtensionGenerator.transformExpression(constraint.lhs, true)»«getRelation(constraint.relation)»«ArithmeticExtensionGenerator.transformExpression(constraint.rhs, true)»«ENDFOR»;				
				}
				«ENDIF»
				@Override
				public String toString() {
					String s = "pattern " + patternName + " {" + System.lineSeparator();
					«FOR node : EditorToIBeXPatternHelper.getPatternNodes(pattern)»
						s += "	«node.name» --> " + parameters.get("«node.name»") + System.lineSeparator();
					«ENDFOR»
					«FOR parameter : getPatternParameter(pattern)»
						s += "	«parameter.name» --> " + parameters.get("«parameter.name»") + System.lineSeparator();
					«ENDFOR»
					s += "}";
					return s;
				}
			}
		'''
		writeFile(rulesPackage.getFile(getPatternClassName(pattern) + ".java"), ruleSourceCode)
	}
	
	/**
	 * Generates a probability class for the given rule; only generates a class if the rule is 
	 * depended of a node attribute
	 */
	def generateProbabilityClass(IFolder probabilitiesPackage, IBeXRule rule){
		if(rule.probability === null){
			return;
		}
		if(!probabilityGenerator.isStatic(rule.probability)){
			var probabilitySourceCode = probabilityGenerator.generateProbabilityClass(rule)
			var imports = probabilityGenerator.getProbabilityImports(rule)
			probabilitySourceCode = printHeader(getSubPackageName('api.probabilities'), imports) + probabilitySourceCode 
			//a probability class is only generated if the probability is not static
			writeFile(probabilitiesPackage.getFile(probabilityGenerator.getProbabilityClassName(rule) + ".java")
				, probabilitySourceCode)			
		}
	}
	
	/**
	 * Returns the necessary imports for the rule class for the stochastic extension
	 */
	def getProbabilityImports(IBeXRule rule){
		val imports = new HashSet<String>()
		imports.addAll(
			'org.emoflon.ibex.gt.arithmetic.Probability'
		)
		if(rule.probability === null){
			return imports;
		}
		if(probabilityGenerator.isStatic(rule.probability)){
			imports.addAll(
				'org.emoflon.ibex.gt.arithmetic.StaticProbability',
				'org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType',
				'java.util.OptionalDouble'
			)
		}
		else{
			imports.add('''«getSubPackageName('api.probabilities')».«probabilityGenerator.getProbabilityClassName(rule)»''')
		}
		return imports	
	}
	/**
	 * returns the relation between attribute and expression for the patternConstraints
	 */
	private def getRelation(IBeXRelation relation){
		switch(relation){
			case EQUAL: return '=='
			case GREATER: return '>'
			case GREATER_OR_EQUAL: return '>='
			case SMALLER: return '<'
			case SMALLER_OR_EQUAL: return '<='
			case UNEQUAL: return '!='
		}
	}
	/**
	 * Sub template for the package declaration and import statements.
	 */
	private static def printHeader(String packageDeclaration, Set<String> imports) {
		return '''
			package «packageDeclaration»;
			
			«FOR importClass : imports.sortBy[it.toLowerCase]»
				import «importClass»;
			«ENDFOR»
		'''
	}
	
	/**
	 * Returns the name of the API class.
	 */
	private def getAPIClassName() {
		return classNamePrefix + "API"
	}

	/**
	 * Returns the name of the package.
	 */
	private def getSubPackageName(String subPackage) {
		val dot = if(packageName.equals("")) "" else "."
		return '''«packageName»«dot»«subPackage»'''
	}

	/**
	 * Returns the name of the match class for the rule.
	 */
	private static def getMatchClassName(IBeXRule rule) {
		return rule.name.toFirstUpper + "Match"
	}
	
	/**
	 * Returns the name of the match class for the rule.
	 */
	private static def getMatchClassName(IBeXPattern pattern) {
		return pattern.name.toFirstUpper + "Match"
	}

	/**
	 * Returns the name of the rule class for the rule.
	 */
	private static def getRuleClassName(IBeXRule rule) {
		return rule.name.toFirstUpper + "Rule"
	}
	
		/**
	 * Returns the name of the rule class for the rule.
	 */
	private static def getPatternClassName(IBeXPattern pattern) {
		return pattern.name.toFirstUpper + "Pattern"
	}

	/**
	 * Returns the concatenation of rule name and the list of parameter names.
	 */
	private static def getRuleSignature(IBeXRule rule) {
		return '''<code>«rule.name»(«FOR parameter : rule.parameters SEPARATOR ', '»«parameter.name»«ENDFOR»)</code>'''
	}
	
	/**
	 * Returns the concatenation of rule name and the list of parameter names.
	 */
	private static def getPatternSignature(IBeXPattern pattern) {
		var context = null as IBeXContextPattern
		if(pattern instanceof IBeXContextPattern) {
			context = pattern as IBeXContextPattern	
		} else if (pattern instanceof IBeXContextAlternatives)  {
			context = (pattern as IBeXContextAlternatives).context
		} else {
			context = (pattern as IBeXDisjunctContextPattern).nonOptimizedPattern
		
		}
		return '''<code>«context.name»(«FOR parameter : context.parameters SEPARATOR ', '»«parameter.name»«ENDFOR»)</code>'''
		
	}
	
	private static def getPatternParameter(IBeXPattern pattern) {
		if(pattern instanceof IBeXContextPattern) {
			return (pattern as IBeXContextPattern).parameters
		} else if (pattern instanceof IBeXContextAlternatives) {
			return (pattern as IBeXContextAlternatives).context.parameters
		} else {
			return (pattern as IBeXDisjunctContextPattern).nonOptimizedPattern.parameters
		}
	}

	/**
	 * Returns the documentation for the rule.
	 */
	private static def getRuleDocumentation(IBeXRule rule) {
		if(rule.documentation === null)
			return String.format(
					"If this rule is not self-explaining, you really should add some comment in the specification.")
		if (rule.documentation.isEmpty) {
			return String.format(
				"If this rule is not self-explaining, you really should add some comment in the specification.")
		} else {
			return rule.documentation
		}
	}
	
	/**
	 * Returns the documentation for the rule.
	 */
	private static def getPatternDocumentation(IBeXPattern pattern) {
		var ibexPattern = null as IBeXContextPattern
		if(pattern instanceof IBeXContextPattern)
			ibexPattern = pattern as IBeXContextPattern
		else if (pattern instanceof IBeXContextAlternatives)
			ibexPattern = (pattern as IBeXContextAlternatives).context	
		else
			ibexPattern = (pattern as IBeXDisjunctContextPattern).nonOptimizedPattern
		
		if (ibexPattern.documentation === null) {
			return String.format(
				"If this pattern is not self-explaining, you really should add some comment in the specification."
			)
		} else if (ibexPattern.documentation.isEmpty) {
			return String.format(
				"If this pattern is not self-explaining, you really should add some comment in the specification."
			)
		} else {
			return ibexPattern.documentation
		}
	}

	/**
	 * Returns the getter method name for the given name.
	 */
	private static def getMethodName(String prefix, String name) {
		return prefix + name.toFirstUpper
	}

	/**
	 * Returns the variable name for the given node.
	 */
	private static def getVariableName(IBeXNode node) {
		return 'var' + node.name.toFirstUpper
	}

	/**
	 * Returns the name of the type of given node.
	 */
	private static def getVariableType(IBeXNode node) {
		return node.type.name
	}

	/**
	 * Returns the equivalent Java type for the EDataType.
	 */
	private static def getJavaType(EDataType dataType) {
		return if(dataType instanceof EEnum) dataType.name else dataType.instanceTypeName
	}

	/**
	 * Creates the file containing the content.
	 */
	private static def writeFile(IFile file, String content) {
		val contentStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))
		if (file.exists) {
			file.setContents(contentStream, true, true, null)
		} else {
			file.create(contentStream, true, null)
		}
	}
}
