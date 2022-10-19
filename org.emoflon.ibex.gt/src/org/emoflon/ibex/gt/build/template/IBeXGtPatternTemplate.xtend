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
		imports.add("org.emoflon.ibex.gt.engine.IBeXGTPattern")
		imports.add("org.emoflon.ibex.gt.api.IBeXGtAPI")
		imports.add("org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern")
		
		exprHelper = new ExpressionHelper(data, imports)
	}
	
	override generate() {
		code = '''package «data.patternPackage»
		
«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends IBeXGTPattern<«className», «matchClassName»> {
	
	«FOR node : context.signatureNodes»
	protected «node.type.name» «node.name.toFirstLower»Binding = null;
	«ENDFOR»
	
	public «className»(final IBeXGtAPI<?, ?, ?> api, final GTPattern pattern) {
		super(api, pattern);
	}
	
	@Override
	protected Collection<String> getParameterNames() {
		throw new UnsupportedOperationException("Patterns do not have any parameters.");
	}
	
	@Override
	protected Map<String, Object> getParameters() {
		throw new UnsupportedOperationException("Patterns do not have any parameters.");
	}
	
	@Override
	public void setParameters(final Map<String, Object> parameters) {
		throw new UnsupportedOperationException("Patterns do not have any parameters.");
	}
	
	«FOR node : context.signatureNodes»
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
		«FOR node : context.signatureNodes»
		bound &= «node.name.toFirstLower»Binding == null || match.«node.name.toFirstLower»().equals(«node.name.toFirstLower»Binding);
		«ENDFOR»
	}
	
	@Override
	protected boolean checkConditions(final «matchClassName» match) {
		«IF context.conditions === null || context.conditions.isEmpty»
		return true;
		«ELSE»
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
	
	protected «matchClassName» createMatch(final Map<String, Object> nodes) {
		return new «matchClassName»(this, nodes);
	}
	
}'''
	}

	
}