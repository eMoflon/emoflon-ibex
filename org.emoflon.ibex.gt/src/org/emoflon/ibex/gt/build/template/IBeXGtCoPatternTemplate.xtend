package org.emoflon.ibex.gt.build.template

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern

class IBeXGtCoPatternTemplate extends GeneratorTemplate<GTRule>{
	
	protected String ruleClassName;
	
	protected String coMatchClassName;
	protected String matchClassName;
	
	protected String patternClassName;
	
	protected ExpressionHelper exprHelper;
	
	new(IBeXGTApiData data, GTRule context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.patternPackage
		className = data.rule2CoPatternClassName.get(context)
		ruleClassName = data.rule2ruleClassName.get(context)
		coMatchClassName = data.rule2CoMatchClassName.get(context)
		matchClassName = data.pattern2matchClassName.get(context.precondition)
		patternClassName = ruleClassName
		
		fqn = packageName + "." + className;
		filePath = data.patternPackagePath + "/" + className
		
		imports.add("java.util.Collection")
		imports.add("java.util.Map")
		imports.add("org.emoflon.ibex.gt.engine.IBeXGTCoPattern")
		imports.add("org.emoflon.ibex.gt.api.IBeXGtAPI")
		imports.add("org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern")
		imports.add(data.matchPackage + "." + coMatchClassName);
		imports.add(data.matchPackage + "." + matchClassName);
		imports.add(data.rulePackage + "." + ruleClassName);
		
		exprHelper = new ExpressionHelper(data, imports)
	}
	
	override generate() {
		code = '''package «data.patternPackage»;
		
«FOR imp : imports.filter[imp | imp !== null]»
import «imp»;
«ENDFOR»

public class «className» extends IBeXGTCoPattern<«className», «coMatchClassName», «ruleClassName», «patternClassName», «matchClassName»> {
		
	public «className»(final IBeXGtAPI<?, ?, ?> api, final «ruleClassName» typedRule, final GTPattern coPattern) {
		super(api, typedRule, coPattern);
	}
	
	@Override
	public Collection<String> getParameterNames() {
		throw new UnsupportedOperationException("Patterns do not have any parameters.");
	}
	
	@Override
	public Map<String, Object> getParameters() {
		throw new UnsupportedOperationException("Patterns do not have any parameters.");
	}
	
	@Override
	public void setParameters(final Map<String, Object> parameters) {
		throw new UnsupportedOperationException("Patterns do not have any parameters.");
	}
	
	@Override
	public boolean checkBindings(final «coMatchClassName» match) {
		return true;
	}
		
	@Override
	public boolean checkConditions(final «coMatchClassName» match) {
		return true;
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
	
	public «coMatchClassName» createMatch(final Map<String, Object> nodes) {
		return new «coMatchClassName»(typedRule, this, nodes);
	}
}'''
	}

}