package org.emoflon.ibex.gt.codegen

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import java.util.Set

import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EEnum
import org.emoflon.ibex.gt.codegen.EClassifiersManager

import GTLanguage.GTBindingType
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
	 * The graph transformation rules as instance of the internal GT model.
	 */
	GTRuleSet gtRuleSet

	/**
	 * Utility to handle the mapping between EClassifier names to meta-model names.
	 */
	EClassifiersManager eClassifiersManager

	/**
	 * Creates a new JavaFileGenerator.
	 */
	new(String classNamePrefix, String packageName, GTRuleSet gtRuleSet, EClassifiersManager eClassifiersManager) {
		this.classNamePrefix = classNamePrefix
		this.packageName = packageName
		this.gtRuleSet = gtRuleSet
		this.eClassifiersManager = eClassifiersManager
	}

	/**
	 * Generates the Java API class.
	 */
	public def generateAPIClass(IFolder apiPackage, String patternPath) {
		val rules = this.gtRuleSet.rules.filter[!it.abstract]
		val imports = newHashSet(
			'org.eclipse.emf.common.util.URI',
			'org.eclipse.emf.ecore.resource.ResourceSet',
			'org.emoflon.ibex.common.operational.IContextPatternInterpreter',
			'org.emoflon.ibex.gt.api.GraphTransformationAPI'
		)
		rules.forEach [
			imports.add('''«this.getSubPackageName('api.rules')».«getRuleClassName(it)»''')
			imports.addAll(this.eClassifiersManager.getImportsForDataTypes(it.parameters))
		]

		val apiSourceCode = '''			
			«printHeader(this.getSubPackageName('api'), imports)»
			
			/**
			 * The «APIClassName» with «gtRuleSet.rules.size» rules.
			 */
			public class «APIClassName» extends GraphTransformationAPI {
				public static String patternPath = "«patternPath»";
			
				/**
				 * Creates a new «APIClassName».
				 *
				 * The are loaded from the default pattern path.
				 *
				 * @param engine
				 *            the engine to use for queries and transformations
				 * @param model
				 *            the resource set containing the model file
				 */
				public «APIClassName»(final IContextPatternInterpreter engine, final ResourceSet model) {
					super(engine, model);
					URI uri = URI.createURI("../" + patternPath);
					this.interpreter.loadPatternSet(uri);
				}
			
				/**
				 * Creates a new «APIClassName».
				 *
				 * The are loaded from the pattern path (the given workspace path concatenated
				 * with the project relative path to the pattern file).
				 *
				 * @param engine
				 *            the engine to use for queries and transformations
				 * @param model
				 *            the resource set containing the model file
				 * @param workspacePath
				 *            the path to the workspace
				 */
				public «APIClassName»(final IContextPatternInterpreter engine, final ResourceSet model,
						final String workspacePath) {
					super(engine, model);
					URI uri = URI.createURI(workspacePath + patternPath);
					this.interpreter.loadPatternSet(uri);
				}
			«FOR rule : rules»
				
					/**
					 * Creates a new rule «getRuleSignature(rule)».
					 * 
					 * @return the created rule
					 */
					public «getRuleClassName(rule)» «rule.name»(«FOR parameter : rule.parameters SEPARATOR ', '»final «getJavaType(parameter.type)» «parameter.name»Value«ENDFOR») {
						return new «getRuleClassName(rule)»(this, this.interpreter«FOR parameter : rule.parameters BEFORE ', 'SEPARATOR ', '»«parameter.name»Value«ENDFOR»);
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
		val imports = this.eClassifiersManager.importsForPackages
		imports.addAll(
			'org.emoflon.ibex.common.operational.IContextPatternInterpreter',
			'org.emoflon.ibex.gt.api.GraphTransformationApp'
		)
		val appClassName = this.classNamePrefix + 'App'
		val appSourceCode = '''
			«printHeader(this.getSubPackageName('api'), imports)»
			
			/**
			 * An application using the «this.APIClassName».
			 */
			public abstract class «appClassName» extends GraphTransformationApp<«this.APIClassName»> {
			
				@Override
				protected void registerMetaModels() {
					«FOR p : this.eClassifiersManager.packages»
						registerMetaModel(«p».eINSTANCE);
					«ENDFOR»
				}
			
				@Override
				protected «this.APIClassName» initAPI(final IContextPatternInterpreter engine) {
					return new «this.APIClassName»(engine, resourceSet, workspacePath);
				}
			}
		'''
		writeFile(apiPackage.getFile(appClassName + '.java'), appSourceCode)
	}

	/**
	 * Generates the Java Match class for the given rule.
	 */
	public def generateMatchClass(IFolder apiMatchesPackage, GTRule rule) {
		val imports = this.eClassifiersManager.getImportsForNodeTypes(rule.graph.nodes.filter[!it.local].toList)
		imports.addAll(
			'org.emoflon.ibex.common.operational.IMatch',
			'org.emoflon.ibex.gt.api.GraphTransformationMatch',
			'''«this.getSubPackageName('api.rules')».«getRuleClassName(rule)»'''
		)

		val signatureNodes = rule.graph.nodes.filter[!it.local]
		val matchSourceCode = '''
			«printHeader(this.getSubPackageName('api.matches'), imports)»
			
			/**
			 * A match for the rule «getRuleSignature(rule)».
			 */
			public class «getMatchClassName(rule)» extends GraphTransformationMatch<«getMatchClassName(rule)», «getRuleClassName(rule)»> {
				«FOR node : signatureNodes»
					private «getVariableType(node)» «getVariableName(node)»;
				«ENDFOR»
			
				/**
				 * Creates a new match for the rule «rule.name»().
				 * 
				 * @param rule
				 *            the rule
				 * @param match
				 *            the untyped match
				 */
				public «getMatchClassName(rule)»(final «getRuleClassName(rule)» rule, final IMatch match) {
					super(rule, match);
					«FOR node : signatureNodes»
						this.«getVariableName(node)» = («getVariableType(node)») match.get("«node.name»");
					«ENDFOR»
				}
			«FOR node : signatureNodes»
				
					/**
					 * Returns the «node.name».
					 *
					 * @return the «node.name»
					 */
					public «getVariableType(node)» «getMethodName('get', node.name)»() {
						return this.«getVariableName(node)»;
					}
			«ENDFOR»
			
				@Override
				public String toString() {
					String s = "match {" + System.lineSeparator();
					«FOR node : signatureNodes»
						s += "	«node.name» --> " + this.«getVariableName(node)» + System.lineSeparator();
					«ENDFOR»
					s += "} for " + this.getPattern();
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
		val parameterNodes = rule.graph.nodes.filter[it.bindingType != GTBindingType.CREATE && !it.local].toList
		val imports = this.eClassifiersManager.getImportsForNodeTypes(parameterNodes)
		imports.addAll(this.eClassifiersManager.getImportsForDataTypes(rule.parameters))
		imports.addAll(
			'java.util.ArrayList',
			'java.util.List',
			'org.emoflon.ibex.common.operational.IMatch',
			'''org.emoflon.ibex.gt.api.«ruleClassType»''',
			'org.emoflon.ibex.gt.engine.GraphTransformationInterpreter',
			'''«this.getSubPackageName('api')».«APIClassName»''',
			'''«this.getSubPackageName('api.matches')».«getMatchClassName(rule)»'''
		)
		if (rule.parameters.size > 0 || parameterNodes.size > 0) {
			imports.add('java.util.Objects');
		}

		val ruleSourceCode = '''
			«printHeader(this.getSubPackageName('api.rules'), imports)»
			
			/**
			 * The «ruleType» «getRuleSignature(rule)».
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
						this.«getMethodName('set', parameter.name)»(«parameter.name»Value);
					«ENDFOR»
				}
			
				@Override
				protected «getMatchClassName(rule)» convertMatch(final IMatch match) {
					return new «getMatchClassName(rule)»(this, match);
				}
			
				@Override
				protected List<String> getParameterNames() {
					List<String> names = new ArrayList<String>();
					«FOR node : parameterNodes»
						names.add("«node.name»");
					«ENDFOR»
					return names;
				}
			«FOR node : parameterNodes»
				
					/**
					 * Binds the parameter «node.name» to the given object.
					 *
					 * @param object
					 *            the object to set
					 */
					public «getRuleClassName(rule)» «getMethodName('bind', node.name)»(final «getVariableType(node)» object) {
						this.parameters.put("«node.name»", Objects.requireNonNull(object, "«node.name» must not be null!"));
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
						this.parameters.put("«parameter.name»", Objects.requireNonNull(value, "«parameter.name» must not be null!"));
						return this;
					}
			«ENDFOR»
			
				@Override
				public String toString() {
					String s = "«ruleType» " + patternName + " {" + System.lineSeparator();
					«FOR node : parameterNodes»
						s += "	«node.name» --> " + this.parameters.get("«node.name»") + System.lineSeparator();
					«ENDFOR»
					«FOR parameter : rule.parameters»
						s += "	«parameter.name» --> " + this.parameters.get("«parameter.name»") + System.lineSeparator();
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
		return this.classNamePrefix + "API"
	}

	/**
	 * Returns the name of the package.
	 */
	private def getSubPackageName(String subPackage) {
		val dot = if(this.packageName.equals("")) "" else "."
		return '''«this.packageName»«dot»«subPackage»'''
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
	 * Returns the concatenation of rule name and the list of parameter names.
	 */
	private static def getRuleSignature(GTRule rule) {
		return '''«rule.name»(«FOR parameter : rule.parameters SEPARATOR ', '»«parameter.name»«ENDFOR»)'''
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
