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
		
		imports.add("org.emoflon.ibex.gt.api.IBeXGtAPI")
		imports.addAll(context.imports)
		
		data.pattern2patternClassName.forEach[pattern, name | imports.add(data.patternPackage + "." + name)]
		data.rule2ruleClassName.forEach[rule, name | imports.add(data.rulePackage + "." + name)]
		
		data.model.metaData.dependencies.forEach[dep | imports.add(dep.fullyQualifiedName + "." + dep.packageClassName)]
	}
	
	override generate() {
		code = '''package «packageName»
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends IBeXGtAPI<«context.engineClassName», «data.patternFactoryClassName», «data.ruleFactoryClassName»> {
	
	«FOR pattern : data.pattern2patternClassName.keySet»
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
		return "«data.gtModelPath»";
	}
	
	@Override
	public String getProjectName() {
		return "«data.model.metaData.project»";
	}
	
	@Override
	protected «context.engineClassName» createPatternMatcher() {
		return new «context.engineClassName»(ibexModel, model);
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
		«rule.name.toFirstLower» = ruleFactory.create«data.rule2ruleClassName.get(rule)»;
		«ENDFOR»
	}
	
	@Override
	protected void initializePatterns() {
		«FOR pattern : data.pattern2patternClassName.keySet»
		«pattern.name.toFirstLower» = patternFactory.create«data.pattern2patternClassName.get(pattern)»;
		«ENDFOR»
	}
	
	@Override
	protected void registerModelMetamodels() {
		«FOR dep : data.model.metaData.dependencies»
		model.getPackageRegistry().put(«dep.packageClassName».eINSTANCE.getNsURI(), «dep.packageClassName».eINSTANCE);
		«ENDFOR»
	}
	
	«FOR pattern : data.pattern2patternClassName.keySet»
	public «data.pattern2patternClassName.get(pattern)» «pattern.name.toFirstLower»() {
		return 	«pattern.name.toFirstLower»;
	}
	
	«ENDFOR»
	«FOR rule : data.rule2ruleClassName.keySet»
	public «data.rule2ruleClassName.get(rule)» «rule.name.toFirstLower»() {
		return 	«rule.name.toFirstLower»;
	}
	
	«ENDFOR»
	
}'''
}
	
}