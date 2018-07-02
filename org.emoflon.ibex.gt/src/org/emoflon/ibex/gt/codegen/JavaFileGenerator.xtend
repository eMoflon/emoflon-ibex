package org.emoflon.ibex.gt.codegen

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import java.util.Set

import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EEnum
import org.emoflon.ibex.gt.codegen.EClassifiersManager

import GTLanguage.GTNode
import GTLanguage.GTRule
import GTLanguage.GTRuleSet

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
	 * Creates a new JavaFileGenerator.
	 */
	new(String classNamePrefix, String packageName, EClassifiersManager eClassifiersManager) {
		this.classNamePrefix = classNamePrefix
		this.packageName = packageName
		this.eClassifiersManager = eClassifiersManager
	}

	/**
	 * Generates the Java API class.
	 */
	public def generateAPIClass(IFolder apiPackage, GTRuleSet gtRuleSet, String patternPath) {
		val imports = newHashSet(
			'org.eclipse.emf.common.util.URI',
			'org.eclipse.emf.ecore.resource.Resource',
			'org.eclipse.emf.ecore.resource.ResourceSet',
			'org.emoflon.ibex.common.operational.IContextPatternInterpreter',
			'org.emoflon.ibex.gt.api.GraphTransformationAPI'
		)
		gtRuleSet.rules.forEach [
			imports.add('''«getSubPackageName('api.rules')».«getRuleClassName(it)»''')
			imports.addAll(eClassifiersManager.getImportsForDataTypes(it.parameters))
		]

		val apiSourceCode = '''			
			«printHeader(getSubPackageName('api'), imports)»
			
			/**
			 * The «APIClassName» with «gtRuleSet.rules.size» rules.
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
				}
			«FOR rule : gtRuleSet.rules»
				
					/**
					 * Creates a new instance of the «getRuleType(rule)» «getRuleSignature(rule)» which does the following:
					 * «getRuleDocumentation(rule)»
					 *
					 * @return the new instance of the «getRuleType(rule)»
					 */
					public «getRuleClassName(rule)» «rule.name»(«FOR parameter : rule.parameters SEPARATOR ', '»final «getJavaType(parameter.type)» «parameter.name»Value«ENDFOR») {
						return new «getRuleClassName(rule)»(this, interpreter«FOR parameter : rule.parameters BEFORE ', 'SEPARATOR ', '»«parameter.name»Value«ENDFOR»);
					}
			«ENDFOR»
			}
		'''
		writeFile(apiPackage.getFile(APIClassName + '.java'), apiSourceCode)
	}

	/**
	 * Generates the Java App class.
	 */
	public def generateAppClass(IFolder apiPackage) {
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
	public def generateAppClassForEngine(IFolder apiPackage, GTEngineExtension engine) {
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
	public def generateMatchClass(IFolder apiMatchesPackage, GTRule rule) {
		val imports = eClassifiersManager.getImportsForNodeTypes(rule.nodes.toList)
		imports.addAll(
			'org.emoflon.ibex.common.operational.IMatch',
			'org.emoflon.ibex.gt.api.GraphTransformationMatch',
			'''«getSubPackageName('api.rules')».«getRuleClassName(rule)»'''
		)

		val matchSourceCode = '''
			«printHeader(getSubPackageName('api.matches'), imports)»
			
			/**
			 * A match for the «getRuleType(rule)» «getRuleSignature(rule)».
			 */
			public class «getMatchClassName(rule)» extends GraphTransformationMatch<«getMatchClassName(rule)», «getRuleClassName(rule)»> {
				«FOR node : rule.nodes»
					private «getVariableType(node)» «getVariableName(node)»;
				«ENDFOR»
			
				/**
				 * Creates a new match for the «getRuleType(rule)» «getRuleSignature(rule)».
				 * 
				 * @param pattern
				 *            the pattern
				 * @param match
				 *            the untyped match
				 */
				public «getMatchClassName(rule)»(final «getRuleClassName(rule)» pattern, final IMatch match) {
					super(pattern, match);
					«FOR node : rule.nodes»
						«getVariableName(node)» = («getVariableType(node)») match.get("«node.name»");
					«ENDFOR»
				}
			«FOR node : rule.nodes»
				
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
					«FOR node : rule.nodes»
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
	 * Generates the Java Pattern/Rule class for the given rule.
	 */
	public def generateRuleClass(IFolder rulesPackage, GTRule rule) {
		val ruleType = if(rule.executable) 'rule' else 'pattern'
		val ruleClassType = if(rule.executable) 'GraphTransformationRule' else 'GraphTransformationPattern'
		val imports = eClassifiersManager.getImportsForNodeTypes(rule.ruleNodes)
		imports.addAll(eClassifiersManager.getImportsForDataTypes(rule.parameters))
		imports.addAll(
			'java.util.ArrayList',
			'java.util.List',
			'org.emoflon.ibex.common.operational.IMatch',
			'''org.emoflon.ibex.gt.api.«ruleClassType»''',
			'org.emoflon.ibex.gt.engine.GraphTransformationInterpreter',
			'''«getSubPackageName('api')».«APIClassName»''',
			'''«getSubPackageName('api.matches')».«getMatchClassName(rule)»'''
		)
		if (rule.parameters.size > 0 || rule.ruleNodes.size > 0) {
			imports.add('java.util.Objects');
		}

		val ruleSourceCode = '''
			«printHeader(getSubPackageName('api.rules'), imports)»
			
			/**
			 * The «ruleType» «getRuleSignature(rule)» which does the following:
			 * «getRuleDocumentation(rule)»
			 */
			public class «getRuleClassName(rule)» extends «ruleClassType»<«getMatchClassName(rule)», «getRuleClassName(rule)»> {
				private static String patternName = "«rule.name»";
			
				/**
				 * Creates a new «ruleType» «rule.name»(«FOR parameter : rule.parameters SEPARATOR ', '»«parameter.name»«ENDFOR»).
				 * 
				 * @param api
				 *            the API the «ruleType» belongs to
				 * @param interpreter
				 *            the interpreter
				 «FOR parameter : rule.parameters»
				 	* @param «parameter.name»Value
				 	*            the value for the parameter «parameter.name»
				 «ENDFOR»
				 */
				public «getRuleClassName(rule)»(final «APIClassName» api, final GraphTransformationInterpreter interpreter«IF rule.parameters.size == 0») {«ELSE»,«ENDIF»
						«FOR parameter : rule.parameters SEPARATOR ', ' AFTER ') {'»final «getJavaType(parameter.type)» «parameter.name»Value«ENDFOR»
					super(api, interpreter, patternName);
					«FOR parameter : rule.parameters»
						«getMethodName('set', parameter.name)»(«parameter.name»Value);
					«ENDFOR»
				}
			
				@Override
				protected «getMatchClassName(rule)» convertMatch(final IMatch match) {
					return new «getMatchClassName(rule)»(this, match);
				}
			
				@Override
				protected List<String> getParameterNames() {
					List<String> names = new ArrayList<String>();
					«FOR node : rule.ruleNodes»
						names.add("«node.name»");
					«ENDFOR»
					return names;
				}
			«FOR node : rule.ruleNodes»
				
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
			«ENDFOR»
			«FOR parameter : rule.parameters»
				
					/**
					 * Sets the parameter «parameter.name» to the given value.
					 *
					 * @param value
					 *            the value to set
					 */
					public «getRuleClassName(rule)» «getMethodName('set', parameter.name)»(final «getJavaType(parameter.type)» value) {
						parameters.put("«parameter.name»", Objects.requireNonNull(value, "«parameter.name» must not be null!"));
						return this;
					}
			«ENDFOR»
			
				@Override
				public String toString() {
					String s = "«ruleType» " + patternName + " {" + System.lineSeparator();
					«FOR node : rule.ruleNodes»
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
	private static def getMatchClassName(GTRule rule) {
		return rule.name.toFirstUpper + "Match"
	}

	/**
	 * Returns the name of the rule class for the rule.
	 */
	private static def getRuleClassName(GTRule rule) {
		return rule.name.toFirstUpper + if(rule.executable) "Rule" else "Pattern"
	}

	/**
	 * Returns "pattern" or "rule". 
	 */
	private static def getRuleType(GTRule rule) {
		return if(rule.executable) 'rule' else 'pattern'
	}

	/**
	 * Returns the concatenation of rule name and the list of parameter names.
	 */
	private static def getRuleSignature(GTRule rule) {
		return '''<code>«rule.name»(«FOR parameter : rule.parameters SEPARATOR ', '»«parameter.name»«ENDFOR»)</code>'''
	}

	/**
	 * Returns the documentation for the rule.
	 */
	private static def getRuleDocumentation(GTRule rule) {
		if (rule.documentation.isEmpty) {
			return String.format(
				"If this %s is not self-explaining, you really should add some comment in the specification.",
				getRuleType(rule)
			)
		} else {
			return rule.documentation
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
	private static def getVariableName(GTNode node) {
		return 'var' + node.name.toFirstUpper
	}

	/**
	 * Returns the name of the type of given node.
	 */
	private static def getVariableType(GTNode node) {
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
