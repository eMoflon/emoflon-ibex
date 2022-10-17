package org.emoflon.ibex.gt.build.template

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule

class IBeXGtCoMatchTemplate extends GeneratorTemplate<GTRule>{
	
	protected String ruleClassName;
	protected String matchClassName;
	protected String coPatternClassName;
	protected GTPattern coPattern;
	
	new(IBeXGTApiData data, GTRule context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.matchPackage
		className = data.rule2CoMatchClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.matchPackagePath + "/" + className
		
		imports.add("java.util.List")
		imports.add("java.util.Map")
		imports.add("org.emoflon.ibex.common.operational.HashUtil")
		imports.add("org.emoflon.ibex.gt.engine.IBeXGTCoMatch")
		ruleClassName = data.rule2ruleClassName.get(context);
		matchClassName = data.pattern2matchClassName.get(context.precondition)
		coPatternClassName = data.rule2CoPatternClassName.get(context)
		
		imports.add(data.rulePackage + "." + ruleClassName)
		imports.add(data.patternPackage + "." + coPatternClassName)
		imports.add(data.matchPackage + "." + matchClassName)
		
		coPattern = context.postcondition as GTPattern
		coPattern.signatureNodes
			.map[node | data.model.metaData.name2package.get(node.eClass.EPackage.name).classifierName2FQN.get(node.type)]
			.forEach[fqn | imports.add(fqn)]
	}
	
	override generate() {
		code = '''package «data.matchPackage»
		
«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends IBeXGTCoMatch<«className», «coPatternClassName», «ruleClassName», «ruleClassName», «matchClassName»> {
	
	«FOR node : coPattern.signatureNodes»
	protected «node.type.name» «node.name.toFirstLower»;
	«ENDFOR»
	
	public «className»(final «ruleClassName» typedRule, final «coPatternClassName» typedCoPattern, final Map<String, Object> nodes) {
		super(typedRule, typedCoPattern, nodes);
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
			«FOR node : coPattern.signatureNodes»
			case "«node.name»" -> yield «node.name.toFirstLower»
			«ENDFOR»
		};
	}
	
	@Override
	public Collection<String> getParameterNames() {
		return List.of(
			«FOR node : coPattern.signatureNodes SEPARATOR ', '» 
			"«node.name»"
			«ENDFOR»
		);
	}
	
	@Override
	public Collection<Object> getObjects() {
		return List.of(
			«FOR node : coPattern.signatureNodes SEPARATOR ', '» 
			«node.name.toFirstLower»
			«ENDFOR»
		);
	}

	@Override
	protected void initialize(final Map<String, Object> nodes) {
		«FOR node : coPattern.signatureNodes»
		«node.name.toFirstLower» = («node.type.name») nodes.get("«node.name»");
		«ENDFOR»
	}
	
	@Override
	protected void initialize(final «className» other) {
		«FOR node : coPattern.signatureNodes»
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
	
	«FOR node : coPattern.signatureNodes»
	public «node.type.name» «node.name.toFirstLower»() {
		return «node.name.toFirstLower»;
	}
	
	«ENDFOR»
}'''
	}

	
}