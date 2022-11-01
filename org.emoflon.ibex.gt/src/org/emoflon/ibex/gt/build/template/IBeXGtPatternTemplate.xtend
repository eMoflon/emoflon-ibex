package org.emoflon.ibex.gt.build.template

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern

class IBeXGtPatternTemplate extends GeneratorTemplate<GTPattern>{
	
	protected String matchClassName;
	protected ExpressionHelper exprHelper;
	
	new(IBeXGTApiData data, GTPattern context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.patternPackage
		className = data.pattern2patternClassName.get(context)
		matchClassName = data.pattern2matchClassName.get(context)
		
		fqn = packageName + "." + className;
		filePath = data.patternPackagePath + "/" + className
		
		imports.add("java.util.Collection")
		imports.add("java.util.Map")
		imports.add("java.util.List")
		imports.add("org.emoflon.ibex.gt.engine.IBeXGTPattern")
		imports.add("org.emoflon.ibex.gt.api.IBeXGtAPI")
		imports.add("org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern")
		imports.add(data.matchPackage + "." + matchClassName)
		
		exprHelper = new ExpressionHelper(data, imports)
		
		context.signatureNodes.forEach[n | imports.add(data.getFQN(n.type))]
	}
	
	override generate() {
		code = '''package «data.patternPackage»;
		
«FOR imp : imports.filter[imp | imp !== null]»
import «imp»;
«ENDFOR»

public class «className» extends IBeXGTPattern<«className», «matchClassName»> {
	
	«IF !context.parameters.nullOrEmpty»
	protected boolean parametersInitialized = false;
	«ENDIF»
	
	«FOR param : context.parameters»
	protected «exprHelper.EDataType2Java(param.type)» «param.name.toFirstLower»;
	«ENDFOR»
	
	«FOR node : context.signatureNodes»
	protected «node.type.name» «node.name.toFirstLower»Binding = null;
	«ENDFOR»
	
	public «className»(final IBeXGtAPI<?, ?, ?> api, final GTPattern pattern) {
		super(api, pattern);
	}
	
	@Override
	public Collection<String> getParameterNames() {
		return List.of(«FOR param : context.parameters SEPARATOR ', \n'»"«param.name»"«ENDFOR»);
	}
	
	@Override
	public Map<String, Object> getParameters() {
		«IF context.parameters.isNullOrEmpty»
		throw new UnsupportedOperationException("This rule does not have any parameters.");
		«ELSE»
		return Map.of(
			«FOR param : context.parameters SEPARATOR ', '»
			"«param.name»", «param.name.toFirstLower»
			«ENDFOR»
		);
		«ENDIF»
	}
	
	@Override
	public «className» setParameters(final Map<String, Object> parameters) {
		«IF context.parameters.isNullOrEmpty»
		throw new UnsupportedOperationException("This rule does not have any parameters.");
		«ELSE»
		for(String name : parameters.keySet()) {
			switch(name) {
				«FOR param : context.parameters»
				case "«param.name»" : {
					«param.name.toFirstLower» = («exprHelper.EDataType2Java(param.type)») parameters.get("«param.name»");
					break;
				}
				«ENDFOR»
			}
		}
		parametersInitialized = true;
		return this;
		«ENDIF»
	}
	
	«IF !context.parameters.isNullOrEmpty»
	public «className» setParameters(«FOR param : context.parameters SEPARATOR ', '»final «exprHelper.EDataType2Java(param.type)» «param.name.toFirstLower»«ENDFOR») {
		«FOR param : context.parameters»
		this.«param.name.toFirstLower» = «param.name.toFirstLower»;
		«ENDFOR»
		parametersInitialized = true;
		return this;
	}
	«ENDIF»
	
	«FOR node : context.signatureNodes»
	public «className» bind«node.name.toFirstUpper»(final «node.type.name» «node.name.toFirstLower») {
		this.«node.name.toFirstLower»Binding = «node.name.toFirstLower»;
		setBinding("«node.name»", «node.name.toFirstLower»);
		return this;
	}
	
	public «className» unbind«node.name.toFirstUpper»() {
		this.«node.name.toFirstLower»Binding = null;
		unsetBinding("«node.name»");
		return this;
	}
	
	«ENDFOR»
	
	@Override
	public boolean checkBindings(final «matchClassName» match) {
		if(bindings.isEmpty())
			return true;
			
		boolean bound = true;
		«FOR node : context.signatureNodes»
		bound &= «node.name.toFirstLower»Binding == null || match.«node.name.toFirstLower»().equals(«node.name.toFirstLower»Binding);
		«ENDFOR»
		return bound;
	}
	
	@Override
	public boolean checkConditions(final «matchClassName» match) {
		«IF context.conditions === null || context.conditions.isEmpty»
		return true;
		«ELSE»
		«IF !context.parameters.nullOrEmpty»
		if(!parametersInitialized)
			return false;
		«ENDIF»
		return «FOR condition : context.conditions SEPARATOR ' && \n'»(«exprHelper.unparse("match", condition)»)«ENDFOR»;
		«ENDIF»
	}
	
	@Override
	public boolean hasArithmeticExpressions() {
		return «context.usedFeatures.arithmeticExpressions.toString»;
	}
	
	@Override
	public boolean hasBooleanExpressions() {
		return «context.usedFeatures.booleanExpressions.toString»;
	}
	
	@Override
	public boolean hasCountExpressions() {
		return «context.usedFeatures.countExpressions.toString»;
	}
	
	@Override
	public boolean hasParameterExpressions() {
		return «context.usedFeatures.parameterExpressions.toString»;
	}
	
	public «matchClassName» createMatch(final Map<String, Object> nodes,  Object... args) {
		return new «matchClassName»(this, nodes);
	}
	
}'''
	}

	
}