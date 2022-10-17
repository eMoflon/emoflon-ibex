package org.emoflon.ibex.gt.build.template

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern

class IBeXGtMatchTemplate extends GeneratorTemplate<GTPattern>{
	
	protected String patternClassName;
	
	new(IBeXGTApiData data, GTPattern context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.matchPackage
		className = data.pattern2matchClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.matchPackagePath + "/" + className
		
		imports.add("java.util.List")
		imports.add("java.util.Map")
		imports.add("org.emoflon.ibex.common.operational.HashUtil")
		imports.add("org.emoflon.ibex.gt.engine.IBeXGTMatch")
		if(data.pattern2rule.containsKey(context)) {
			val rule = data.pattern2rule.get(context)
			patternClassName = data.rule2ruleClassName.get(rule);
			imports.add(data.rulePackage + "." + patternClassName)
		} else {
			patternClassName = data.pattern2patternClassName.get(context)
			imports.add(data.patternPackage + "." + data.pattern2patternClassName.get(context))
		}
		
		context.signatureNodes
			.map[node | data.model.metaData.name2package.get(node.eClass.EPackage.name).classifierName2FQN.get(node.type)]
			.forEach[fqn | imports.add(fqn)]
	}
	
	override generate() {
		code = '''package data.matchPackage
		
«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends IBeXGTMatch<«className», «patternClassName»> {
	
	«FOR node : context.signatureNodes»
	protected «node.type.name» «node.name.toFirstLower»;
	«ENDFOR»
	
	public «className»(final «patternClassName» typedPattern, final Map<String, Object> nodes) {
		super(typedPattern, nodes);
	}
	
	public «className»(final «className» other) {
		super(other);
	}
	
	@Override
	public String getPatternName() {
		return "«context.name»"	
	}
	
	@Override
	public Object get(String name) {
		return switch(name) -> {
			«FOR node : context.signatureNodes»
			case "«node.name»" -> yield «node.name.toFirstLower»
			«ENDFOR»
		};
	}
	
	@Override
	public Collection<String> getParameterNames() {
		return List.of(
			«FOR node : context.signatureNodes SEPARATOR ', '» 
			"«node.name»"
			«ENDFOR»
		);
	}
	
	@Override
	public Collection<Object> getObjects() {
		return List.of(
			«FOR node : context.signatureNodes SEPARATOR ', '» 
			«node.name.toFirstLower»
			«ENDFOR»
		);
	}

	@Override
	protected void initialize(final Map<String, Object> nodes) {
		«FOR node : context.signatureNodes»
		«node.name.toFirstLower» = («node.type.name») nodes.get("«node.name»");
		«ENDFOR»
	}
	
	@Override
	protected void initialize(final «className» other) {
		«FOR node : context.signatureNodes»
		«node.name.toFirstLower» = other.«node.name.toFirstLower»;
		«ENDFOR»
	}
	
	@Override
	public boolean checkConditions() {
		return typedPattern.checkConditions(this);
	}
	
	@Override
	public «className» copy() {
		return new «className»(this);
	}
	
	«FOR node : context.signatureNodes»
	public «node.type.name» «node.name.toFirstLower»() {
		return «node.name.toFirstLower»;
	}
	
	«ENDFOR»
}'''
	}

	
}