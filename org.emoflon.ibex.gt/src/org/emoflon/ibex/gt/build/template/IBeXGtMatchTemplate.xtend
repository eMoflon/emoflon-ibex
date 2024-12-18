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
		patternClassName = data.pattern2patternClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.matchPackagePath + "/" + className
		
		imports.add("java.util.Collection")
		imports.add("java.util.List")
		imports.add("java.util.Map")
		imports.add("org.emoflon.ibex.gt.engine.IBeXGTMatch")
		if(data.pattern2rule.containsKey(context)) {
			val rule = data.pattern2rule.get(context)
			patternClassName = data.rule2ruleClassName.get(rule);
			imports.add(data.rulePackage + "." + patternClassName)
		} else {
			patternClassName = data.pattern2patternClassName.get(context)
			imports.add(data.patternPackage + "." + data.pattern2patternClassName.get(context))
		}
		
		context.signatureNodes.forEach[n | imports.add(data.getFQN(n.type))]
	}
	
	override generate() {
		code = '''package «data.matchPackage»;
		
«FOR imp : imports.filter[imp | imp !== null]»
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
		return "«context.name»";
	}
	
	@Override
	public Object get(String name) {
		«IF context.signatureNodes.isNullOrEmpty»
		throw new UnsupportedOperationException("This rule does not have any parameters.");
		«ELSE»
		return switch(name) {
			«FOR node : context.signatureNodes»
			case "«node.name»" -> {yield «node.name.toFirstLower»;}
			«ENDFOR»
			default -> throw new NullPointerException("Unknown parameter name: " + name);
		};
		«ENDIF»
	}
	
	@Override
	public Collection<String> getParameterNames() {
		return List.of(«FOR node : context.signatureNodes SEPARATOR ', \n'»"«node.name»"«ENDFOR»);
	}
	
	@Override
	public Collection<Object> getObjects() {
		return List.of(«FOR node : context.signatureNodes SEPARATOR ', \n'»«node.name.toFirstLower»«ENDFOR»);
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
	public boolean checkBindings() {
		return typedPattern.checkBindings(this);
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