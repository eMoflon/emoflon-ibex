package org.emoflon.ibex.gt.build.template

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern
import java.util.Map
import java.util.HashMap
import java.util.Set
import java.util.HashSet
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression
import java.util.List
import org.eclipse.emf.ecore.EPackage

class IBeXGtRuleTemplate extends GeneratorTemplate<GTRule>{
	
	protected String coMatchClassName;
	protected String matchClassName;
	
	protected String coPatternClassName;
	protected String patternClassName;
	
	protected GTPattern precondition;
	protected ExpressionHelper exprHelper;
	
	protected Set<String> factoryClasses = new HashSet
	protected Map<String, String> nodeName2FactoryClass = new HashMap
	
	new(IBeXGTApiData data, GTRule context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.rulePackage
		className = data.rule2ruleClassName.get(context)
		coMatchClassName = data.rule2CoMatchClassName.get(context)
		matchClassName = data.pattern2matchClassName.get(context.precondition)
		coPatternClassName = data.rule2CoPatternClassName.get(context)
		patternClassName = className
		
		fqn = packageName + "." + className;
		filePath = data.rulePackagePath + "/" + className
		
		precondition = context.precondition as GTPattern
		
		imports.add("java.util.Collection")
		imports.add("java.util.stream.Collectors")
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("java.util.Map")
		imports.add("java.util.HashMap")
		imports.add("org.emoflon.ibex.gt.engine.IBeXGTRule")
		imports.add("org.emoflon.ibex.gt.api.IBeXGtAPI")
		imports.add("org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule")
		imports.add("org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern")
		imports.add(data.matchPackage + "." + coMatchClassName);
		imports.add(data.matchPackage + "." + matchClassName);
		imports.add(data.patternPackage + "." + coPatternClassName);
			
		imports.addAll(context.allNodes.map[node | data.getPackageFQN(node.type) + "." 
			+ data.getPackageClass(node.type)
		])
		imports.addAll(context.allNodes.filter(node | node.type.eContainer() !== null && node.type.eContainer() instanceof EPackage)
			.filter[node | (node.type.eContainer() as EPackage).name.equals("ecore")].map[node | data.getPackageFQN(node.type) + "." + data.getPackageFactoryClass(node.type)]
		)
		imports.addAll(context.allNodes.filter(node | node.type.eContainer() !== null && node.type.eContainer() instanceof EPackage)
			.filter[node | !(node.type.eContainer() as EPackage).name.equals("ecore")].map[node | data.getPackageFactoryClass(node.type)]
		)
		
		context.allNodes.forEach[node | factoryClasses.add(data.getSimplePackageFactoryClass(node.type))]
		context.allNodes.forEach[node | nodeName2FactoryClass.put(node.name, data.getSimplePackageFactoryClass(node.type))]
			
		context.allNodes.forEach[node | imports.add(data.getFQN(node.type))]
		
		exprHelper = new ExpressionHelper(data, imports)
	}
	
	def String generateClass() {
		return 
'''
@SuppressWarnings("unused")
public class «className» extends IBeXGTRule<«className», «patternClassName», «matchClassName», «coPatternClassName», «coMatchClassName»> {
	
	«FOR fac : factoryClasses»
	protected «fac» «fac.toFirstLower» = «fac».eINSTANCE;
	«ENDFOR»
	
	«IF !context.parameters.nullOrEmpty»
	protected boolean parametersInitialized = false;
	«ENDIF»
	
	«FOR param : context.parameters»
	protected «exprHelper.EDataType2Java(param.type)» «param.name.toFirstLower»;
	«ENDFOR»
	
	«FOR node : context.precondition.signatureNodes»
	protected «node.type.name» «node.name.toFirstLower»Binding = null;
	«ENDFOR»
	
	public «className»(final IBeXGtAPI<?, ?, ?> api, final GTRule rule) {
		super(api, rule);
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
	
	«FOR node : context.precondition.signatureNodes»
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
		«FOR node : context.precondition.signatureNodes»
		bound &= «node.name.toFirstLower»Binding == null || match.«node.name.toFirstLower»().equals(«node.name.toFirstLower»Binding);
		«ENDFOR»
		return bound;
	}
		
	@Override
	public boolean checkConditions(final «matchClassName» match) {
		«IF context.precondition.conditions === null || context.precondition.conditions.isEmpty»
		return true;
		«ELSE»
		«IF !context.parameters.nullOrEmpty && (context.precondition as GTPattern).usedFeatures.parameterExpressions»
		if(!parametersInitialized)
			return false;
		«ENDIF»
«««		How to check local nodes for injectivity violations externally? If pattern contains local nodes -> deactivate this
		«IF context.precondition.localNodes.nullOrEmpty»
		return «FOR condition : context.precondition.conditions SEPARATOR ' && \n'»(«exprHelper.unparse("match", condition)»)«ENDFOR»;
		«ELSE»
		return true;
		«ENDIF»
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
	
	public «matchClassName» createMatch(final Map<String, Object> nodes, Object... args) {
		return new «matchClassName»(this, nodes);
	}
	«generateWatchDogs»
	protected «coPatternClassName» createCoPattern() {
		return new «coPatternClassName»(api, this, (GTPattern) rule.getPostcondition());
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
	
	protected «coMatchClassName» applyInternal(final «matchClassName» match) {
		Map<String, Object> coMatchNodes = new HashMap<>();
		«FOR node : context.allNodes.filter[node | context.precondition.signatureNodes.contains(node) && context.postcondition.signatureNodes.contains(node)]»
		coMatchNodes.put("«node.name»", match.«node.name.toFirstLower»());
		«ENDFOR»
		«IF !context.deletion.empty»
		
		// Delete elements
		«FOR edge : context.deletion.edges.filter[edge | !(edge.type.isContainment || edge.type.isContainer)]»
		gtEngine.deleteEdge(«getNode("match", edge.source)», «getNode("match", edge.target)», rule.getDeletion().getEdges().get(«context.deletion.edges.indexOf(edge)»));
		«ENDFOR»
		«FOR edge : context.deletion.edges.filter[edge | edge.type.isContainment || edge.type.isContainer]»
		gtEngine.deleteEdge(«getNode("match", edge.source)», «getNode("match", edge.target)», rule.getDeletion().getEdges().get(«context.deletion.edges.indexOf(edge)»));
		«ENDFOR»
		«FOR node : context.deletion.nodes»
		gtEngine.delete(«getNode("match", node)»);
		«ENDFOR»
		«ENDIF»
		«IF !context.creation.empty»
		
		// Create new elements
		«FOR node : context.creation.nodes»
		«node.type.name» «node.name.toFirstLower» = «nodeName2FactoryClass.get(node.name).toFirstLower».create«node.type.name»();
		«IF !placedIntoContainment(node)»
		«IF !context.precondition.signatureNodes.isEmpty»
		if(«getNode("match",context.precondition.signatureNodes.filter[n | context.postcondition.signatureNodes.contains(n)].get(0))».eResource() != null) {
			«getNode("match",context.precondition.signatureNodes.filter[n | context.postcondition.signatureNodes.contains(n)].get(0))».eResource().getContents().add(«node.name.toFirstLower»);
		} else {
			gtEngine.getModel().getResources().get(0).getContents().add(«node.name.toFirstLower»);
		}
		«ELSE»
		gtEngine.getModel().getResources().get(0).getContents().add(«node.name.toFirstLower»);
		«ENDIF»
		«ENDIF»
		coMatchNodes.put("«node.name»", «node.name.toFirstLower»);
		«ENDFOR»
		«IF !context.creation.edges.isNullOrEmpty»
		
		// Create new edges
		«ENDIF»
		«FOR edge : context.creation.edges»
		«IF edge.type.isMany»
		«getNode("match", edge.source)».get«edge.type.name.toFirstUpper»().add(«getNode("match", edge.target)»);
		«ELSE»
		«getNode("match", edge.source)».set«edge.type.name.toFirstUpper»(«getNode("match", edge.target)»);
		«ENDIF»
		«ENDFOR»
		«ENDIF»
		«IF context.attributeAssignments !== null && !context.attributeAssignments.empty»
		
		// Assign attribute values
		«FOR asgn : context.attributeAssignments»
		«getNode("match", asgn.node)».set«asgn.attribute.name.toFirstUpper»((«exprHelper.EDataType2ExactJava(asgn.attribute.EType)»)(«exprHelper.unparse("match", asgn.value)»));
		«ENDFOR»
		«ENDIF»
		«IF context.forEachOperations !== null && !context.forEachOperations.isEmpty»
		
		// Execute forEach-Operations
		«FOR iterator : context.forEachOperations»
		Collection<«getIteratorType(iterator)»> elements = «getIteratorSet("match", iterator)»;
		for(«getIteratorType(iterator)» «iterator.iterator.name» : elements) {
			«IF iterator.deleted !== null && !iterator.deleted.isEmpty»
			// Delete elements
			«FOR edge : iterator.deleted.filter[edge | !(edge.type.isContainment || edge.type.isContainer)]»
			gtEngine.deleteEdge(«getNode("match", iterator, edge.source)», «getNode("match", iterator, edge.target)», rule.getForEachOperations().get(«context.forEachOperations.indexOf(iterator)»).getDeleted().get(«iterator.deleted.indexOf(edge)»));
			«ENDFOR»
			«FOR edge : context.deletion.edges.filter[edge | edge.type.isContainment || edge.type.isContainer]»
			gtEngine.deleteEdge(«getNode("match", iterator, edge.source)», «getNode("match", iterator, edge.target)», rule.getForEachOperations().get(«context.forEachOperations.indexOf(iterator)»).getDeleted().get(«iterator.deleted.indexOf(edge)»));
			«ENDFOR»
			«ENDIF»
			«IF iterator.created !== null && !iterator.created.isEmpty»
			// Create elements
			«FOR edge : iterator.created»
			«IF edge.type.isMany»
			«getNode("match", iterator, edge.source)».get«edge.type.name.toFirstUpper»().add(«getNode("match", iterator, edge.target)»);
			«ELSE»
			«getNode("match", iterator, edge.source)».set«edge.type.name.toFirstUpper»(«getNode("match", iterator, edge.target)»);
			«ENDIF»
			«ENDFOR»
			«ENDIF»
			«IF iterator.attributeAssignments !== null && !iterator.attributeAssignments.isEmpty»
			// Assign attribute values
			«FOR asgn : iterator.attributeAssignments»
			«getNode("match", iterator, asgn.node)».set«asgn.attribute.name.toFirstUpper»((«exprHelper.EDataType2ExactJava(asgn.attribute.EType)»)(«exprHelper.unparse("match", asgn.value)»));
			«ENDFOR»
			«ENDIF»
		}
		«ENDFOR»
		«ENDIF»
		
		ruleApplicationCount++;
		return coPattern.createMatch(coMatchNodes, match);
	}
«««	TODO: Future works!
«««	public Optional<«matchClassName»> applyReverse(final «coMatchClassName» coMatch) {
«««		throw new UnsupportedOperationException("Reverse application is currently not implemented!");
«««	}
	
}'''
	}
	
	override generate() {
		val clazz = generateClass
		code = 
'''package «data.rulePackage»;
		
«FOR imp : imports.filter[imp | imp !== null]»
import «imp»;
«ENDFOR»

«clazz»
'''
	}
	
	def String generateWatchDogs() {
		if(!precondition.watchDogs.isNullOrEmpty) {
			imports.addAll(List.of("org.eclipse.emf.ecore.EObject", "java.util.Collections", "java.util.LinkedHashSet", "java.util.Set"))
		} else {
			imports.addAll(List.of("org.eclipse.emf.ecore.EObject", "java.util.Set"))
		}
		return
'''«IF !precondition.watchDogs.isNullOrEmpty»
	
	@Override
	protected Set<EObject> insertNodesAndMatch(final «matchClassName» match) {
		Set<EObject> addedNodes = Collections.synchronizedSet(new LinkedHashSet<>());
		«FOR wd : precondition.watchDogs»
		«wd.node.type.name» «wd.node.name.toFirstLower» = match.«wd.node.name.toFirstLower»();
		Set<«matchClassName»> «wd.node.name.toFirstLower»Matches = node2matches.get(«wd.node.name.toFirstLower»);
		if(«wd.node.name.toFirstLower»Matches == null) {
			«wd.node.name.toFirstLower»Matches = Collections.synchronizedSet(new LinkedHashSet<>());
			node2matches.put(«wd.node.name.toFirstLower», «wd.node.name.toFirstLower»Matches);
		}
		«wd.node.name.toFirstLower»Matches.add(match);
		addedNodes.add(«wd.node.name.toFirstLower»);
		
		«ENDFOR»
		
		match2nodes.put(match, addedNodes);
		return addedNodes;
	}
	
	«ELSE»
	
	@Override
	protected Set<EObject> insertNodesAndMatch(final «matchClassName» match) {
		throw new UnsupportedOperationException("The pattern <«context.name»> does not define any attributes to watch.");
	}
	
	«ENDIF»
'''
	}
	
	def String getNode(String methodContext, IBeXNode node) {
		if(context.precondition.signatureNodes.contains(node)) {
			return '''«methodContext».«node.name.toFirstLower»()'''
		} else {
			return node.name.toFirstLower
		}
	}
	
	def String getNode(String methodContext, GTForEachExpression itrContext, IBeXNode node) {
		if(context.precondition.signatureNodes.contains(node)) {
			return '''«methodContext».«node.name.toFirstLower»()'''
		} else if(itrContext.iterator.equals(node)) {
			return '''«itrContext.iterator.name»'''
		} else {
			return node.name.toFirstLower
		}
	}
	
	def String getIteratorType(GTForEachExpression itr) {
		if(itr.iterator.type.equals(itr.reference.EType)) {
			imports.add(data.model.metaData.name2package.get(itr.reference.EType.EPackage.name).classifierName2FQN.get(itr.reference.EType.name))
			return itr.reference.EType.name
		} else {
			imports.add(data.model.metaData.name2package.get(itr.iterator.type.EPackage.name).classifierName2FQN.get(itr.iterator.type.name))
			return itr.iterator.type.name
		}
	}
	
	def String getIteratorSet(String methodContext, GTForEachExpression itr) {
		if(itr.iterator.type.equals(itr.reference.EType)) {
			return '''new LinkedList<>(«getNode(methodContext, itr.source)».get«itr.reference.name.toFirstUpper»())'''
		} else {
			return '''«getNode(methodContext, itr.source)».get«itr.reference.name.toFirstUpper»().stream()
			.filter(n -> (n instanceof «itr.iterator.type.name»))
			.map(n -> («itr.iterator.type.name») n).collect(Collectors.toList())'''
		}
	}
	
	def boolean placedIntoContainment(IBeXNode node) {
		val containmentEdge = context.deletion.edges.filter[e | e.type.isContainment || e.type.isContainer].findFirst[e | e.target.equals(node)]
		return containmentEdge !== null;
	}

}