package org.emoflon.ibex.gt.api.generator

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import org.apache.log4j.Logger
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IFolder
import org.eclipse.core.runtime.IPath
import org.emoflon.ibex.gt.editor.ui.builder.GTBuilderExtension
import org.eclipse.core.resources.IFile

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
		this.generateModel
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
	 * Creates the model.
	 */
	private def generateModel() {
		val modelFolder = this.ensureFolderExists(this.sourceGenPackage.getFolder('model'))
		this.writeFile(modelFolder.getFile("gt-model.txt"), this.packageName)
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
