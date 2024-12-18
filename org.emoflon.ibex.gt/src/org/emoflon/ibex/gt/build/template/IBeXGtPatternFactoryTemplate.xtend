package org.emoflon.ibex.gt.build.template

import org.emoflon.ibex.gt.build.template.GeneratorTemplate
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel

class IBeXGtPatternFactoryTemplate extends GeneratorTemplate<GTModel> {
	
	new(IBeXGTApiData data, GTModel context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiPackage
		className = data.patternFactoryClassName;
		fqn = packageName + "." + className;
		filePath = data.apiPackagePath + "/" + className
		
		imports.add("org.emoflon.ibex.gt.api.IBeXGtAPI")
		imports.add("org.emoflon.ibex.gt.engine.IBeXGTPatternFactory")
		
		data.pattern2patternClassName.filter[pattern, name | !data.pattern2rule.containsKey(pattern)].forEach[pattern, name | imports.add(data.patternPackage + "." + name)]
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends IBeXGTPatternFactory {	
	
	public «className»(IBeXGtAPI<?, ?, ?> api) {
			super(api);
	}
	
	«FOR pattern : data.pattern2patternClassName.keySet.filter[p | !data.pattern2rule.containsKey(p)]»
	protected «data.pattern2patternClassName.get(pattern)» create«data.pattern2patternClassName.get(pattern)»() {
		«data.pattern2patternClassName.get(pattern)» pattern = new «data.pattern2patternClassName.get(pattern)»(api, api.getGTEngine().getPattern("«pattern.name»"));
		return pattern;
	}
	
	«ENDFOR»
}'''
	}
	
}