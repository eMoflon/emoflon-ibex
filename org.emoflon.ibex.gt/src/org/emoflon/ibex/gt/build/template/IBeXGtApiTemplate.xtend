package org.emoflon.ibex.gt.build.template

import org.emoflon.ibex.gt.build.template.GeneratorTemplate
import org.emoflon.ibex.common.engine.IBeXPMEngineInformation

class IBeXGtApiTemplate extends GeneratorTemplate<IBeXPMEngineInformation> {
	
	new(IBeXGTApiData data, IBeXPMEngineInformation context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiPackage
		className = data.apiClassNames.get(context);
		fqn = packageName + "." + className;
		filePath = data.apiPackagePath + "/" + className
		
		imports.add("org.eclipse.emf.ecore.resource.ResourceSet");
		imports.addAll(context.imports)
		
	}
	
	def String generateClass() {
		return
'''public class «className» extends «data.apiAbstractClassName»<«context.engineClassName»> {
	
	@Override
	protected «context.engineClassName» createPatternMatcher() {
		return new «context.engineClassName»(ibexModel, model);
	}
}
'''
	}
	
	override generate() {
		val clazz = generateClass
		code = '''package «packageName»;
		
«FOR imp : imports.filter[imp | imp !== null]»
import «imp»;
«ENDFOR»

«clazz»
'''
}
	
}