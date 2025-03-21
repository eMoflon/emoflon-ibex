package org.emoflon.ibex.gt.build.template

import org.emoflon.ibex.gt.build.template.GeneratorTemplate
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel

class IBeXGtRuleFactoryTemplate extends GeneratorTemplate<GTModel> {
	
	new(IBeXGTApiData data, GTModel context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiPackage
		className = data.ruleFactoryClassName;
		fqn = packageName + "." + className;
		filePath = data.apiPackagePath + "/" + className
		
		imports.add("org.emoflon.ibex.gt.api.IBeXGtAPI")
		imports.add("org.emoflon.ibex.gt.engine.IBeXGTRuleFactory")
		
		data.rule2ruleClassName.forEach[rule, name | imports.add(data.rulePackage + "." + name)]
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends IBeXGTRuleFactory {	
	
	public «className»(IBeXGtAPI<?, ?, ?> api) {
			super(api);
	}
	
	«FOR rule : data.rule2ruleClassName.keySet»
	protected «data.rule2ruleClassName.get(rule)» create«data.rule2ruleClassName.get(rule)»() {
		«data.rule2ruleClassName.get(rule)» rule = new «data.rule2ruleClassName.get(rule)»(api, api.getGTEngine().getRule("«rule.name»"));
		return rule;
	}
	
	«ENDFOR»
}'''
	}
	
}