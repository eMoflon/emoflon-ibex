package org.emoflon.ibex.gt.build.template

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern

class IBeXGtRuleTemplate extends GeneratorTemplate<GTRule>{
	
	protected String coMatchClassName;
	protected String matchClassName;
	
	protected String coPatternClassName;
	protected String patternClassName;
	
	protected ExpressionHelper exprHelper;
	
	new(IBeXGTApiData data, GTRule context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.rulePackage
		className = data.rule2ruleClassName.get(context)
		coMatchClassName = data.rule2CoMatchClassName.get(context)
		matchClassName = data.pattern2matchClassName.get(context.precondition)
		coPatternClassName = data.rule2CoPatternClassName.get(context)
		patternClassName = data.pattern2patternClassName.get(context.precondition)
		
		fqn = packageName + "." + className;
		filePath = data.rulePackagePath + "/" + className
		
		imports.add("java.util.Collection")
		imports.add("java.util.List")
		imports.add("java.util.Map")
		imports.add("java.util.Optional")
		imports.add("org.emoflon.ibex.gt.engine.IBeXGTRule")
		imports.add("org.emoflon.ibex.gt.api.IBeXGtAPI")
		imports.add("org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule")
		
		context.parameters
			.map[param | data.model.metaData.name2package.get(param.type.EPackage).classifierName2FQN.get(param.type.name)]
			.forEach[fqn | imports.add(fqn)]
		
		exprHelper = new ExpressionHelper(data, imports)
	}
	
	override generate() {
		code = '''package «data.rulePackage»
		
«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends IBeXGTRule<«className», «patternClassName», «matchClassName», «coPatternClassName», «coMatchClassName»> {
	
	«FOR param : context.parameters»
	protected «param.type.name» «param.name.toFirstLower»;
	«ENDFOR»
	
	«FOR node : context.precondition.signatureNodes»
	protected «node.type.name» «node.name.toFirstLower»Binding = null;
	«ENDFOR»
	
	public «className»(final IBeXGtAPI<?, ?, ?> api, final GTRule rule) {
		super(api, rule);
	}
	
	@Override
	protected Collection<String> getParameterNames() {
		return List.of(
			«FOR param : context.parameters SEPARATOR ', '»
			"«param.name»"
			«ENDFOR»
		);
	}
	
	@Override
	protected Map<String, Object> getParameters() {
		return Map.of(
			«FOR param : context.parameters SEPARATOR ', '»
			"«param.name»", «param.name.toFirstLower»
			«ENDFOR»
		);
	}
	
	@Override
	public void setParameters(final Map<String, Object> parameters) {
		for(String name : parameters.keySet()) {
			switch(name) {
				«FOR param : context.parameters»
				case "«param.name»" : {
					«param.name.toFirstLower» = parameters.get("«param.name»");
					break;
				}
				«ENDFOR»
			}
		}
	}
	
	public void setParameters(«FOR param : context.parameters SEPARATOR ', '»final «param.type.name» «param.name.toFirstLower»«ENDFOR») {
		«FOR param : context.parameters»
		this.«param.name.toFirstLower» = «param.name.toFirstLower»;
		«ENDFOR»
	}
	
	«FOR node : context.precondition.signatureNodes»
	public void bind«node.name.toFirstUpper»(final «node.type.name» «node.name.toFirstLower») {
		this.«node.name.toFirstLower» = «node.name.toFirstLower»;
		setBinding("«node.name»", «node.name.toFirstLower»);
	}
	
	public void unbind«node.name.toFirstUpper»() {
		this.«node.name.toFirstLower» = null;
		unsetBinding("«node.name»");
	}
	
	«ENDFOR»
	
	@Override
	public boolean checkBindings(final «matchClassName» match) {
		if(bindings.isEmpty())
			return true;
			
		boolean bound = true;
		«FOR node : context.precondition.signatureNodes»
		bound &= «node.name.toFirstLower»Binding == null || match.«node.name.toFirstLower»().equals(«node.name.toFirstLower»Binding);
		«ENDFOR»
	}
		
	@Override
	public boolean checkConditions(final «matchClassName» match) {
		«IF context.precondition.conditions === null || context.precondition.conditions.isEmpty»
		return true;
		«ELSE»
		return «FOR condition : context.precondition.conditions SEPARATOR ' && \n'»(«exprHelper.unparse("match", condition)»)«ENDFOR»;
		«ENDIF»
	}

	
	@Override
	public boolean hasArithmeticExpressions() {
		return «(context.precondition as GTPattern).usedFeatures.arithmeticExpressions.toString»;
	}
	
	@Override
	public boolean hasBooleanExpressions() {
		return «(context.precondition as GTPattern).usedFeatures.booleanExpressions.toString»;
	}
	
	@Override
	public boolean hasCountExpressions() {
		return «(context.precondition as GTPattern).usedFeatures.countExpressions.toString»;
	}
	
	@Override
	public boolean hasParameterExpressions() {
		return «(context.precondition as GTPattern).usedFeatures.parameterExpressions.toString»;
	}
	
	protected «matchClassName» createMatch(final Map<String, Object> nodes) {
		return new «matchClassName»(this, nodes);
	}
	
	protected «coPatternClassName» createCoPattern() {
		return new «coPatternClassName»(api, this, rule);
	}
	
	public boolean hasProbability() {
		«IF context.probability === null»
		return false;
		«ELSE»
		return true;
		«ENDIF»
	}
	
	public double getProbability(final «matchClassName» match) {
		«IF context.probability === null»
		return 0.0;
		«ELSE»
		return «exprHelper.unparse("match", context.probability)»;
		«ENDIF»
	}
	
	public Optional<«coMatchClassName»> apply(final «matchClassName» match) {
		// TODO: !
		return Optional.empty();
	}
«««	TODO: Future works!
«««	public Optional<«matchClassName»> applyReverse(final «coMatchClassName» coMatch) {
«««		throw new UnsupportedOperationException("Reverse application is currently not implemented!");
«««	}
	
}'''
	}

}