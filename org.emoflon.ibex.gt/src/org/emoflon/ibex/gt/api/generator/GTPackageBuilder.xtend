package org.emoflon.ibex.gt.api.generator

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import org.apache.log4j.Logger
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.IPath
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl
import org.eclipse.emf.ecore.xmi.XMIResource
import org.eclipse.emf.ecore.xmi.XMLResource
import org.emoflon.ibex.gt.editor.gT.GraphTransformationFile
import org.emoflon.ibex.gt.editor.ui.builder.GTBuilderExtension
import org.emoflon.ibex.gt.editor.ui.builder.GTBuilder
import org.emoflon.ibex.gt.engine.transformations.EditorToInternalGT
import org.emoflon.ibex.gt.engine.transformations.InternalGTToIBeX
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.resource.XtextResourceSet

import GTLanguage.GTLanguageFactory
import IBeXLanguage.IBeXLanguageFactory

/**
 * Code generation for the API.
 */
class GTPackageBuilder implements GTBuilderExtension {
	static val SOURCE_GEN_FOLDER = 'src-gen'

	IProject project
	IPath path
	String packageName
	IFolder sourceGenPackage

	override void run(IProject project, IPath packagePath) {
		this.project = project
		this.path = packagePath
		this.packageName = this.path.toString.replace('/', '.')

		this.log("Start generation")
		this.ensureSourceGenPackageExists
		this.generateModels
		this.generateAPI
		this.log("Finished generation")
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
		this.sourceGenPackage = folder
	}

	/**
	 * Creates the models.
	 */
	private def generateModels() {
		val modelFolder = this.ensureFolderExists(this.sourceGenPackage.getFolder('model'))
		val allFiles = this.project.getFolder(GTBuilder.SOURCE_FOLDER).getFolder(this.path).members
		val gtFiles = allFiles.filter[it instanceof IFile].map[it as IFile].filter["gt" == it.fileExtension].toList

		// Load files into editor models.
		val editorModels = newHashMap()
		val resourceSet = new XtextResourceSet();
		gtFiles.forEach [
			val file = resourceSet.createResource(URI.createPlatformResourceURI(it.getFullPath().toString(), false))
			file.load(null);
			EcoreUtil2.resolveLazyCrossReferences(file, [false]);
			EcoreUtil.resolveAll(resourceSet);

			val editorModel = file.contents.get(0) as GraphTransformationFile
			editorModels.put(it, editorModel)
		]

		// Transform Editor models to rules of the internal GT model.
		val gtRules = newHashSet()
		editorModels.forEach [ gtFile, editorModel |
			val internalModel = EditorToInternalGT.transformRuleSet(editorModel)
			gtRules.addAll(internalModel.rules)
		]

		val gtRulesSet = GTLanguageFactory.eINSTANCE.createGTRuleSet
		gtRulesSet.rules.addAll(gtRules.sortBy[it.name])
		this.saveModelFile(modelFolder.getFile("gt-rules.xmi"), resourceSet, gtRulesSet)

		// Transform rules rules from internal models to IBeXPatterns.
		val ibexPatterns = newHashSet()
		val ruleToPatternCount = newHashMap()
		gtRules.forEach [ internalRule, index |
			val patterns = InternalGTToIBeX.transformRule(internalRule).getPatterns()
			ibexPatterns.addAll(patterns)
			ruleToPatternCount.put(internalRule.name, patterns.size)
		]

		val ibexPatternSet = IBeXLanguageFactory.eINSTANCE.createIBeXPatternSet
		ibexPatternSet.patterns.addAll(ibexPatterns.sortBy[it.name])
		this.saveModelFile(modelFolder.getFile("ibex-patterns.xmi"), resourceSet, ibexPatternSet)

		// Write debug file.
		val debugFileContent = '''
			# «this.packageName»
			«gtRules.size» rules from «gtFiles.size» files
			«ibexPatterns.size» patterns
			
			«FOR file : gtFiles»
				## «file.name» (with «editorModels.get(file).rules.size» rules)
				«FOR rule : editorModels.get(file).rules»
					- «IF rule.abstract»abstract «ENDIF»rule «rule.name» (transformed to «ruleToPatternCount.get(rule.name)» patterns)
				«ENDFOR»
				
			«ENDFOR»
		'''
		this.writeFile(modelFolder.getFile("log.md"), debugFileContent)
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
	 * Generates the API.
	 */
	private def generateAPI() {
		val apiPackage = this.ensureFolderExists(this.sourceGenPackage.getFolder('api'))
		this.ensureFolderExists(apiPackage.getFolder('matches'))
		this.ensureFolderExists(apiPackage.getFolder('rules'))
	}

	/**
	 * Logs the message on the console.
	 */
	private def log(String message) {
		Logger.rootLogger.info(this.project.name + ", package " + this.packageName + ": " + message)
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
