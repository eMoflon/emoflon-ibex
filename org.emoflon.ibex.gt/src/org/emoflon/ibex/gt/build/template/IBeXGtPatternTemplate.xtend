package org.emoflon.ibex.gt.build.template

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern

class IBeXGtPatternTemplate extends GeneratorTemplate<GTPattern>{
	
	protected String matchClassName;
	
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
		
	}
	
	override generate() {
		code = '''package «data.patternPackage»
		
«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends IBeXGTPattern<«className», «matchClassName»> {
	
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
	public boolean checkBindings(final «matchClassName» match) {
		//TODO: !
		return false;
	}
	
	@Override
	protected boolean checkConditions(final «matchClassName» match) {
		//TODO: !
		return false;
	}
	
	@Override
	public boolean hasArithmeticExpressions() {
		//TODO: !
		return false;
	}
	
	@Override
	public abstract boolean hasCountExpressions() {
		//TODO: !
		return false;
	}
	
	protected «matchClassName» createMatch(final Map<String, Object> nodes) {
		return new «matchClassName»(this, nodes);
	}
	
}'''
	}

	
}