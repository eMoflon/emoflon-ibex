package org.emoflon.ibex.gt.api.generator

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import java.util.HashMap
import java.util.HashSet

import org.apache.log4j.Logger
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.IPath
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl
import org.eclipse.emf.ecore.xmi.XMIResource
import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.resource.XtextResourceSet
import org.emoflon.ibex.gt.editor.gT.GraphTransformationFile
import org.emoflon.ibex.gt.editor.ui.builder.GTBuilder
import org.emoflon.ibex.gt.editor.ui.builder.GTBuilderExtension
import org.emoflon.ibex.gt.transformations.EditorToInternalGTModelTransformation
import org.emoflon.ibex.gt.transformations.InternalGTModelToIBeXPatternTransformation

import GTLanguage.GTLanguageFactory
import GTLanguage.GTNode
import GTLanguage.GTRule
import GTLanguage.GTRuleSet
import IBeXLanguage.IBeXPatternSet
import java.util.Set

/**
 * This GTPackageBuilder implements
 * <ul>
 * <li>transforms the editor files into the internal model and IBeXPatterns</li>
 * <li>and generates code for the API.</li>
 * </ul>
 * 
 * Each package is considered as an rule module with an API.
 */
class GTPackageBuilder implements GTBuilderExtension {
	static val SOURCE_GEN_FOLDER = 'src-gen'

	// The package information.
	IProject project
	IPath path
	String packageName
	IFolder apiPackage

	// The model.
	GTRuleSet gtRuleSet
	IBeXPatternSet ibexPatternSet
	HashMap<String, String> eClassNameToMetaModelName = newHashMap()

	override void run(IProject project, IPath packagePath) {
		this.project = project
		this.path = packagePath
		this.packageName = this.path.toString.replace('/', '.')

		this.log("Started build")
		this.ensureSourceGenPackageExists
		this.generateModels
		this.generateAPI
		this.log("Finished build")
	}

	/**
	 * Creates the target package.
	 */
	private def ensureSourceGenPackageExists() {
		var folder = this.ensureFolderExists(this.project.getFolder(SOURCE_GEN_FOLDER))
		for (var i = 0; i < this.path.segmentCount; i++) {
			val s = this.path.segment(i)
			folder = this.ensureFolderExists(folder.getFolder(s))
		}
		folder = folder.getFolder('api')
		if (folder.exists) {
			folder.delete(true, null)
		}
		this.apiPackage = this.ensureFolderExists(folder)
	}

	/**
	 * Creates the models.
	 */
	private def generateModels() {
		val allFiles = this.project.getFolder(GTBuilder.SOURCE_FOLDER).getFolder(this.path).members
		val gtFiles = allFiles.filter[it instanceof IFile].map[it as IFile].filter["gt" == it.fileExtension].toList

		// Load files into editor models.
		val editorModels = newHashMap()
		val resourceSet = new XtextResourceSet();
		val HashSet<String> metaModels = newHashSet()
		gtFiles.filter[it.exists].forEach [
			val file = resourceSet.getResource(URI.createPlatformResourceURI(it.getFullPath().toString(), true), true)
			EcoreUtil2.resolveLazyCrossReferences(file, [false]);

			val editorModel = file.contents.get(0) as GraphTransformationFile
			editorModels.put(it, editorModel)
			metaModels.addAll(editorModel.imports.map[it.name])
		]
		EcoreUtil.resolveAll(resourceSet);

		// Transform Editor models to rules of the internal GT model.
		val gtRules = newHashSet()
		editorModels.forEach [ gtFile, editorModel |
			val transformation = new EditorToInternalGTModelTransformation
			val internalModel = transformation.transform(editorModel)
			gtRules.addAll(internalModel.rules)
		]

		// Save internal GT model (rules ordered alphabetically by their name).
		this.gtRuleSet = GTLanguageFactory.eINSTANCE.createGTRuleSet
		this.gtRuleSet.rules.addAll(gtRules.sortBy[it.name])
		this.saveModelFile(apiPackage.getFile("gt-rules.xmi"), resourceSet, this.gtRuleSet)

		// Transform rules rules from internal models to IBeXPatterns.
		val transformation = new InternalGTModelToIBeXPatternTransformation
		this.ibexPatternSet = transformation.transform(gtRuleSet)

		// Save IBexPatterns (patterns ordered alphabetically by their name).
		this.saveModelFile(apiPackage.getFile("ibex-patterns.xmi"), resourceSet, this.ibexPatternSet)

		// Load meta-models.
		val metaModelPackages = newHashMap()
		metaModels.forEach [
			metaModelPackages.put(it, this.loadMetaModelClasses(it, resourceSet))
		]

		// Write debug file.
		val debugFileContent = '''
			# «this.packageName»
			You specified «gtRuleSet.rules.size» rules in «gtFiles.size» files
				which were transformed into «ibexPatternSet.patterns.size» patterns.
			
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
	 * Saves the model in the file.
	 */
	private def saveModelFile(IFile file, ResourceSet rs, EObject model) {
		val uri = URI.createPlatformResourceURI(this.project.name + "/" + file.projectRelativePath.toString, true);
		val resource = rs.createResource(uri);
		resource.getContents().add(model);

		val options = (resource as XMLResource).getDefaultSaveOptions();
		options.put(XMIResource.OPTION_SAVE_ONLY_IF_CHANGED, XMIResource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		options.put(XMLResource.OPTION_URI_HANDLER, new URIHandlerImpl() {
			override deresolve(URI uri) {
				return uri
			}
		});
		resource.save(options);
	}

	/**
	 * Loads the EClasses from the meta-model.
	 */
	private def loadMetaModelClasses(String metaModelUri, ResourceSet resourceSet) {
		val ecoreFile = resourceSet.getResource(URI.createURI(metaModelUri), true)
		ecoreFile.load(null)
		EcoreUtil2.resolveLazyCrossReferences(ecoreFile, [false]);
		EcoreUtil.resolveAll(resourceSet);

		val rootElement = ecoreFile.contents.get(0)
		if (rootElement instanceof EPackage) {
			val ePackage = rootElement as EPackage
			val metaModelPackageName = if('ecore'.equals(ePackage.name)) 'org.eclipse.emf.ecore' else ePackage.name
			val eClassesFromMetaModel = ePackage.EClassifiers.filter[it instanceof EClass].map[it as EClass].toSet
			eClassesFromMetaModel.forEach [
				eClassNameToMetaModelName.put(it.name, metaModelPackageName)
			]
			return metaModelPackageName
		}
		return null
	}

	/**
	 * Generates the API.
	 */
	private def generateAPI() {
		val matchesPackage = this.ensureFolderExists(this.apiPackage.getFolder('matches'))
		val rulesPackage = this.ensureFolderExists(this.apiPackage.getFolder('rules'))
		this.gtRuleSet.rules.forEach [
			this.generateMatchJavaFile(matchesPackage, it)
			this.generateRuleJavaFile(rulesPackage, it)
		]
		this.generateAPIJavaFile(apiPackage)
	}

	/**
	 * Generates the Java API class.
	 */
	private def generateAPIJavaFile(IFolder apiPackage) {
		val imports = newHashSet(
			'org.eclipse.emf.common.util.URI',
			'org.eclipse.emf.ecore.resource.ResourceSet',
			'org.emoflon.ibex.gt.api.GraphTransformationAPI',
			'org.emoflon.ibex.gt.engine.GTEngine'
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
				private static URI defaultPatternURI = URI.createFileURI("../«this.project.name»/src-gen/«this.path.toString»/api/ibex-patterns.xmi");
			
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
				public «apiClassName»(final GTEngine engine, final ResourceSet model) {
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
	 * Generates the Java Rule class for the given rule.
	 */
	private def generateMatchJavaFile(IFolder apiMatchesPackage, GTRule rule) {
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
	private def generateRuleJavaFile(IFolder rulesPackage, GTRule rule) {
		val imports = newHashSet(
			'java.util.Collection',
			'java.util.Map',
			'java.util.Optional',
			'java.util.stream.Collectors',
			'org.eclipse.emf.ecore.EObject',
			'org.emoflon.ibex.gt.api.GraphTransformationInterpreter',
			'org.emoflon.ibex.gt.api.GraphTransformationRule',
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

	// class names
	private def getAPIClassName() {
		return this.packageName.replace('.', '').toFirstUpper + "API"
	}

	private static def getMatchClassName(GTRule rule) {
		return rule.name.toFirstUpper + "Match"
	}

	private static def getRuleClassName(GTRule rule) {
		return rule.name.toFirstUpper + "Rule"
	}

	// method names
	private static def getGetterMethodName(GTNode node) {
		return 'get' + node.name.toFirstUpper
	}

	// variables
	private static def getVariableName(GTNode node) {
		return 'var' + node.name.toFirstUpper
	}

	private static def getVariableType(GTNode node) {
		return node.type.name
	}

	/**
	 * Logs the message on the console.
	 */
	private def log(String message) {
		Logger.rootLogger.info(this.project.name + " - package " + this.packageName + ": " + message)
	}

	/**
	 * Creates the given folder if the folder does not exist yet.
	 */
	private def ensureFolderExists(IFolder folder) {
		if (!folder.exists) {
			folder.create(true, true, null)
		}
		return folder
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
