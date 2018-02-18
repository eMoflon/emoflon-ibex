package org.emoflon.ibex.gt.api.generator

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import java.util.HashMap
import java.util.HashSet

import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.emoflon.ibex.gt.editor.gT.GraphTransformationFile

import GTLanguage.GTNode
import GTLanguage.GTRule
import GTLanguage.GTRuleSet
import java.util.Set
import java.util.List

/**
 * This GTPackageBuilder implements
 * <ul>
 * <li>transforms the editor files into the internal model and IBeXPatterns</li>
 * <li>and generates code for the API.</li>
 * </ul>
 * 
 * Each package is considered as an rule module with an API.
 */
class JavaFileGenerator {
	/**
	 * The name of the package.
	 */
	String packageName

	/**
	 * The graph transformation rules as instance of the internal GT model.
	 */
	GTRuleSet gtRuleSet

	/**
	 * The mapping between EClassNames to MetaModelNames
	 */
	HashMap<String, String> eClassNameToMetaModelName

	/**
	 * Creates a new JavaFileGenerator.
	 */
	new(String packageName, GTRuleSet gtRuleSet, HashMap<String, String> eClassNameToMetaModelName) {
		this.packageName = packageName
		this.gtRuleSet = gtRuleSet
		this.eClassNameToMetaModelName = eClassNameToMetaModelName
	}

	/**
	 * Generates the README.md file.
	 */
	public def generateREADME(IFolder apiPackage, List<IFile> gtFiles, HashSet<String> metaModels,
		HashMap<String, String> metaModelPackages, HashMap<IFile, GraphTransformationFile> editorModels) {
		val debugFileContent = '''
			# «this.packageName»
			You specified «gtRuleSet.rules.size» rules in «gtFiles.size» files.
			
			The generated API is located in `src-gen/«this.packageName».api`.
			
			## Meta-Models
			«FOR metaModel : metaModels»
				- `«metaModel»` (package `«metaModelPackages.get(metaModel)»`)
			«ENDFOR»
			
			## Rules
			«FOR file : gtFiles»
				- File `«file.name»`
					«FOR rule : editorModels.get(file).rules.filter[!it.abstract]»
						- rule `«rule.name»`
					«ENDFOR»
			«ENDFOR»
			
			Note that abstract rules are not included in this list
				because they cannot be applied directly.
			
			## How to specify rules
			1. Add a meta-model reference.
			2. Add the meta-model project(s) as dependency to the `META-INF/MANIFEST.MF`,
				if not done yet (tab *Dependencies* via the button *Add*).
			3. Define your rules by adding `.gt` files into the package.
			
			If there are errors in the specification, you will see this in the editor
				and the generated API may contain errors as well.
			
			### How to use the API in another project
			1. Add the generated packages `«this.packageName».api`, 
				`«this.packageName».api.matches` and `«this.packageName».api.rules`
				to the exported packages of this project
				(tab *Runtime* > *Exported packages* via the button *Add*).
			2. Add this project as a dependency of the project in which you want to use the API.
			3. Create a new API object.
				 ```
				 ResourceSet resourceSet = new ResourceSetImpl();
				 resourceSet.createResource(URI.createFileURI("your-model.xmi"));
				 return new «this.APIClassName»(new DemoclesGTEngine(), resourceSet);
				 ```
		'''
		this.writeFile(apiPackage.getFile("README.md"), debugFileContent)
	}

	/**
	 * Generates the Java API class.
	 */
	public def generateAPIJavaFile(IFolder apiPackage, String patternUri) {
		val imports = newHashSet(
			'org.eclipse.emf.common.util.URI',
			'org.eclipse.emf.ecore.resource.ResourceSet',
			'org.emoflon.ibex.common.operational.IPatternInterpreter',
			'org.emoflon.ibex.gt.api.GraphTransformationAPI'
		)
		this.gtRuleSet.rules.forall [
			imports.add('''«this.packageName».api.rules.«getRuleClassName(it)»''')
		]

		val apiClassName = this.APIClassName
		val apiSourceCode = '''
			package «this.packageName».api;
			
			«printImports(imports)»
			
			/**
			 * The «apiClassName».
			 */
			public class «apiClassName» extends GraphTransformationAPI {
				private static URI defaultPatternURI = URI.createFileURI("«patternUri»");
			
				/**
				 * Create a new «apiClassName».
				 *
				 * If the patterns for the engine are not loaded yet, they are loaded from the
				 * default pattern URI.
				 *
				 * @param engine
				 *            the engine to use for queries and transformations
				 * @param model
				 *            the resource set containing the model file
				 */
				public «apiClassName»(final IPatternInterpreter engine, final ResourceSet model) {
					super(engine, model);
					if (!this.interpreter.isPatternSetLoaded()) {
						this.interpreter.loadPatternSet(defaultPatternURI);
					}
				}
			«FOR rule : this.gtRuleSet.rules»
				
					/**
					 * Create a new rule «rule.name»().
					 * 
					 * @return the created rule
					 */
					public «getRuleClassName(rule)» «rule.name»() {
						return new «getRuleClassName(rule)»(this.interpreter);
					}
			«ENDFOR»
			}
		'''
		this.writeFile(apiPackage.getFile(apiClassName + '.java'), apiSourceCode)
	}

	/**
	 * Generates the Java Match class for the given rule.
	 */
	public def generateMatchJavaFile(IFolder apiMatchesPackage, GTRule rule) {
		val imports = getImportsForTypes(rule)
		imports.add('java.util.HashMap')
		imports.add('java.util.Map')
		imports.add('org.eclipse.emf.ecore.EObject')
		imports.add('org.emoflon.ibex.gt.api.GraphTransformationMatch')
		imports.add('''«this.packageName».api.rules.«getRuleClassName(rule)»''')

		val matchSourceCode = '''
			package «this.packageName».api.matches;
			
			«printImports(imports)»
			
			/**
			 * A match for the rule «rule.name»().
			 */
			public class «getMatchClassName(rule)» extends GraphTransformationMatch<«getMatchClassName(rule)», «getRuleClassName(rule)»> {
				«FOR node : rule.graph.nodes»
					private «getVariableType(node)» «getVariableName(node)»;
				«ENDFOR»
			
				/**
				 * Create a new match for a match for the rule «rule.name»().
				 * 
				 * @param rule
				 *            the rule
				 */
				public «getMatchClassName(rule)»(final «getRuleClassName(rule)» rule, final Map<String, EObject> map) {
					super(rule);
					«FOR node : rule.graph.nodes»
						this.«getVariableName(node)» = («getVariableType(node)») map.get("«node.name»");
					«ENDFOR»
				}
			«FOR node : rule.graph.nodes»
				
					/**
					 * Returns the «node.name».
					 *
					 * @return the «node.name»
					 */
					public «getVariableType(node)» «getGetterMethodName(node)»() {
						return this.«getVariableName(node)»;
					}
			«ENDFOR»
			
				@Override
				public Map<String, EObject> toMap() {
					Map<String, EObject> map = new HashMap<String, EObject>();
					«FOR node : rule.graph.nodes»
						map.put("«node.name»", this.«getVariableName(node)»);
					«ENDFOR»
					return map;
				}
			}
		'''
		this.writeFile(apiMatchesPackage.getFile(getMatchClassName(rule) + ".java"), matchSourceCode)
	}

	/**
	 * Generates the Java Rule class for the given rule.
	 */
	public def generateRuleJavaFile(IFolder rulesPackage, GTRule rule) {
		val imports = newHashSet(
			'java.util.Collection',
			'java.util.Map',
			'java.util.Optional',
			'java.util.stream.Collectors',
			'org.eclipse.emf.ecore.EObject',
			'org.emoflon.ibex.gt.api.GraphTransformationRule',
			'org.emoflon.ibex.gt.engine.GraphTransformationInterpreter',
			'''«this.packageName».api.matches.«getMatchClassName(rule)»'''
		)

		val ruleSourceCode = '''
			package «this.packageName».api.rules;
			
			«printImports(imports)»
			
			/**
			 * The rule «rule.name»().
			 */
			public class «getRuleClassName(rule)» extends GraphTransformationRule<«getMatchClassName(rule)», «getRuleClassName(rule)»> {
				private static String ruleName = "«rule.name»";
			
				/**
				 * Create a new rule «rule.name»().
				 * 
				 * @param interpreter
				 *            the interpreter
				 */
				public «getRuleClassName(rule)»(final GraphTransformationInterpreter interpreter) {
					super(interpreter);
				}
			
				@Override
				public void execute(final «getMatchClassName(rule)» match) {
					this.interpreter.execute(ruleName, match.toMap());
				}
			
				@Override
				public Optional<«getMatchClassName(rule)»> findAnyMatch() {
					Optional<Map<String, EObject>> match = this.interpreter.findAnyMatch(ruleName);
					if (match.isPresent()) {
						return Optional.of(new «getMatchClassName(rule)»(this, match.get()));
					}
					return Optional.empty();
				}
			
				@Override
				public Collection<«getMatchClassName(rule)»> findMatches() {
					return this.interpreter.findMatches(ruleName).stream() //
							.map(m -> new «getMatchClassName(rule)»(this, m)) //
							.collect(Collectors.toList());
				}
			}
		'''
		this.writeFile(rulesPackage.getFile(getRuleClassName(rule) + ".java"), ruleSourceCode)
	}

	/**
	 * Determines the set of necessary imports for a rule.
	 */
	private def getImportsForTypes(GTRule rule) {
		val imports = newHashSet()
		val types = rule.graph.nodes.map[it.type].toSet
		types.forEach [
			val typePackageName = this.eClassNameToMetaModelName.get(it.name)
			if (typePackageName !== null) {
				imports.add(typePackageName + '.' + it.name)
			}
		]
		return imports.sortBy[it].toSet
	}

	/**
	 * Sub template for Java import statements
	 */
	private static def printImports(Set<String> imports) {
		return '''
			«FOR importClass : imports.sortBy[it.toLowerCase]»
				import «importClass»;
			«ENDFOR»
		'''
	}

	/**
	 * Returns the name of the API class.
	 */
	private def getAPIClassName() {
		return this.packageName.replace('.', '').toFirstUpper + "API"
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
		return rule.name.toFirstUpper + "Rule"
	}

	/**
	 * Returns the getter method name for the given node.
	 */
	private static def getGetterMethodName(GTNode node) {
		return 'get' + node.name.toFirstUpper
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
	 * Creates the file containing the content.
	 */
	private def writeFile(IFile file, String content) {
		val contentStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))
		if (file.exists) {
			file.setContents(contentStream, true, true, null)
		} else {
			file.create(contentStream, true, null)
		}
	}
}
