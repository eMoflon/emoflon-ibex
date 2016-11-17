package org.emoflon.ibex.tgg.core.compiler

import language.TGG

class ManipulationTemplate {
	
	def getManipulationCode(TGG tgg){
		
		val suffixes = #{PatternSuffixes.FWD, PatternSuffixes.BWD, PatternSuffixes.CC, PatternSuffixes.MODELGEN}
		return '''
	
		package org.emoflon.ibex.tgg.run
		
		import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine
		import org.eclipse.viatra.query.runtime.emf.EMFScope
		import org.eclipse.viatra.transformation.runtime.emf.modelmanipulation.IModelManipulations
		import org.eclipse.viatra.transformation.runtime.emf.rules.eventdriven.EventDrivenTransformationRuleFactory
		import org.eclipse.viatra.transformation.runtime.emf.transformation.eventdriven.EventDrivenTransformation
		import org.eclipse.viatra.transformation.runtime.emf.modelmanipulation.SimpleModelManipulations
		import org.eclipse.viatra.transformation.evm.specific.Lifecycles
		import org.eclipse.viatra.transformation.evm.specific.crud.CRUDActivationStateEnum
		import org.eclipse.viatra.transformation.runtime.emf.rules.EventDrivenTransformationRuleGroup
		import org.eclipse.emf.ecore.resource.Resource	
		import org.eclipse.emf.ecore.resource.ResourceSet
		import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil
		import org.emoflon.ibex.tgg.operational.OperationMode
		import org.emoflon.ibex.tgg.operational.OperationStrategy
		
		«FOR rule : tgg.rules»
		«FOR suffix : suffixes»
		import org.emoflon.ibex.tgg.«rule.name.toLowerCase()».«rule.name»«suffix»Matcher
		«ENDFOR»
		«ENDFOR»
		
		class Transformation{	
			
			
			/* Transformation-related extensions */
			extension EventDrivenTransformation transformation
		
			/* Transformation rule-related extensions */
			extension EventDrivenTransformationRuleFactory = new EventDrivenTransformationRuleFactory
			extension IModelManipulations manipulation
		
			protected ViatraQueryEngine engine
			protected Resource resource
		
			private TGGRuntimeUtil tggRuntimeUtil;
			
			// protected EventDrivenTransformationRule<?,?> exampleRule
			new(ResourceSet set, TGGRuntimeUtil tggRuntimeUtil) {
				this.resource = resource
				this.tggRuntimeUtil = tggRuntimeUtil
				// Create EMF scope and EMF IncQuery engine based on the resource
				val scope = new EMFScope(set)
				engine = ViatraQueryEngine.on(scope);
			
				createTransformation
			
			}
			
			public def execute() {
				transformation.executionSchema.startUnscheduledExecution
			}
			
			def dispose() {
				if (transformation != null) {
					transformation.dispose
				}
				transformation = null
				tggRuntimeUtil.finalize
				return
			}
			
			private def createTransformation() {
				// Initialize model manipulation API
				this.manipulation = new SimpleModelManipulations(engine)
				this.tggRuntimeUtil.setModelManipulation(this.manipulation)
				// Initialize event-driven transformation
				transformation = EventDrivenTransformation.forEngine(engine).addRules(getTransformationRuleGroup).build
			}
			
			private def getTransformationRuleGroup() {
				if (tggRuntimeUtil.mode == OperationMode.FWD)
					return get«PatternSuffixes.FWD»
				else if (tggRuntimeUtil.mode == OperationMode.BWD)
					return get«PatternSuffixes.BWD»
				else if (tggRuntimeUtil.mode == OperationMode.CC)
					return get«PatternSuffixes.CC»
				else if (tggRuntimeUtil.mode == OperationMode.MODELGEN)
					return get«PatternSuffixes.MODELGEN»
			}
			
			«FOR suffix : suffixes»
			private def get«suffix»() {
				new EventDrivenTransformationRuleGroup(
				«FOR rule : tgg.rules SEPARATOR ", "»
				get«rule.name»«suffix»()
				«ENDFOR»
				)
			}
			
			«FOR rule : tgg.rules»
			private def get«rule.name»«suffix»() {
				createRule.name("«rule.name»«suffix»").precondition(«rule.name»«suffix»Matcher.querySpecification).action(
					CRUDActivationStateEnum.CREATED) [
					tggRuntimeUtil.apply("«rule.name»", it)
				].action(CRUDActivationStateEnum.UPDATED)[].action(CRUDActivationStateEnum.DELETED)[].addLifeCycle(
					Lifecycles.getDefault(true, true)).build
			}
			«ENDFOR»
			
			«ENDFOR»
		}		
		
		'''
	}
}