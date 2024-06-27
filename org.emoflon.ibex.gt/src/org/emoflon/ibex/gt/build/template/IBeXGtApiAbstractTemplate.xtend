package org.emoflon.ibex.gt.build.template

import org.emoflon.ibex.gt.build.template.GeneratorTemplate
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel

class IBeXGtApiAbstractTemplate extends GeneratorTemplate<GTModel> {
	
	protected ExpressionHelper exprHelper;
	
	new(IBeXGTApiData data, GTModel context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiPackage
		className = data.apiAbstractClassName
		fqn = packageName + "." + className;
		filePath = data.apiPackagePath + "/" + className
		
		imports.add("org.eclipse.emf.ecore.resource.ResourceSet");
		imports.add("org.emoflon.ibex.gt.api.IBeXGtAPI")
		imports.add("org.emoflon.ibex.gt.engine.IBeXGTPatternMatcher")
		
		data.pattern2patternClassName.filter[pattern, name | !data.pattern2rule.containsKey(pattern)].forEach[pattern, name | imports.add(data.patternPackage + "." + name)]
		data.rule2ruleClassName.forEach[rule, name | imports.add(data.rulePackage + "." + name)]
		
		data.model.metaData.dependencies.forEach[dep | imports.add(dep.fullyQualifiedName + "." + dep.packageClassName)]
		exprHelper = new ExpressionHelper(data, imports)
	}
	
	def String generateClass() {
		return
'''public abstract class «className» <ENGINE extends IBeXGTPatternMatcher<?>> extends IBeXGtAPI<ENGINE, «data.patternFactoryClassName», «data.ruleFactoryClassName»> {
	
	«FOR pattern : data.pattern2patternClassName.keySet.filter[p | !data.pattern2rule.containsKey(p)]»
	protected «data.pattern2patternClassName.get(pattern)» «pattern.name.toFirstLower»;
	«ENDFOR»
	
	«FOR rule : data.rule2ruleClassName.keySet»
	protected «data.rule2ruleClassName.get(rule)» «rule.name.toFirstLower»;
	«ENDFOR»
	
	@Override
	public String getWorkspacePath() {
		return "«data.model.metaData.projectPath»/../";
	}
	
	@Override
	public String getProjectPath() {
		return "«data.model.metaData.projectPath»";
	}
	
	@Override
	public String getIBeXModelPath() {
		return "«data.model.metaData.projectPath»«data.gtModelPath»";
	}
	
	@Override
	public String getProjectName() {
		return "«data.model.metaData.project»";
	}
	
	@Override
	protected «data.patternFactoryClassName» createPatternFactory() {
		return new 	«data.patternFactoryClassName»(this);
	}
	
	@Override
	protected «data.ruleFactoryClassName» createRuleFactory() {
		return new 	«data.ruleFactoryClassName»(this);
	}
	
	@Override
	protected void initializeRules() {
		«FOR rule : data.rule2ruleClassName.keySet»
		«rule.name.toFirstLower» = ruleFactory.create«data.rule2ruleClassName.get(rule)»();
		«ENDFOR»
	}
	
	@Override
	protected void initializePatterns() {
		«FOR pattern : data.pattern2patternClassName.keySet.filter[p | !data.pattern2rule.containsKey(p)]»
		«pattern.name.toFirstLower» = patternFactory.create«data.pattern2patternClassName.get(pattern)»();
		«ENDFOR»
	}
	
	@Override
	protected void registerMetamodels(final ResourceSet rs) {
		«FOR dep : data.model.metaData.dependencies»
		rs.getPackageRegistry().put(«dep.packageClassName».eINSTANCE.getNsURI(), «dep.packageClassName».eINSTANCE);
		«ENDFOR»
	}
	
	«FOR pattern : data.pattern2patternClassName.keySet.filter[p | !data.pattern2rule.containsKey(p)]»
	public «data.pattern2patternClassName.get(pattern)» «pattern.name.toFirstLower»() {
		return 	«pattern.name.toFirstLower»;
	}
	«IF !pattern.parameters.isNullOrEmpty»
	public «data.pattern2patternClassName.get(pattern)» «pattern.name.toFirstLower»(«FOR param : pattern.parameters SEPARATOR ', '»final «exprHelper.EDataType2Java(param.type)» «param.name.toFirstLower»«ENDFOR») {
		return «pattern.name.toFirstLower».setParameters(«FOR param : pattern.parameters SEPARATOR ', '»«param.name.toFirstLower»«ENDFOR»);
	}
	«ENDIF»
	
	«ENDFOR»
	«FOR rule : data.rule2ruleClassName.keySet»
	public «data.rule2ruleClassName.get(rule)» «rule.name.toFirstLower»() {
		return 	«rule.name.toFirstLower»;
	}
	«IF !rule.parameters.isNullOrEmpty»
	public «data.rule2ruleClassName.get(rule)» «rule.name.toFirstLower»(«FOR param : rule.parameters SEPARATOR ', '»final «exprHelper.EDataType2Java(param.type)» «param.name.toFirstLower»«ENDFOR») {
		return «rule.name.toFirstLower».setParameters(«FOR param : rule.parameters SEPARATOR ', '»«param.name.toFirstLower»«ENDFOR»);
	}
	«ENDIF»
	«ENDFOR»
	
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