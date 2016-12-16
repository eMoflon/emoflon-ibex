
	package org.emoflon.ibex.tgg.run
	
	import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine
	import org.eclipse.viatra.query.runtime.emf.EMFScope
	import org.eclipse.viatra.transformation.runtime.emf.rules.eventdriven.EventDrivenTransformationRuleFactory
	import org.eclipse.viatra.transformation.runtime.emf.transformation.eventdriven.EventDrivenTransformation
	import org.eclipse.viatra.transformation.evm.specific.Lifecycles
	import org.eclipse.viatra.transformation.evm.specific.crud.CRUDActivationStateEnum
	import org.eclipse.viatra.transformation.runtime.emf.rules.EventDrivenTransformationRuleGroup
	import org.eclipse.emf.ecore.resource.Resource	
	import org.eclipse.emf.ecore.resource.ResourceSet
	import org.emoflon.ibex.tgg.operational.TGGRuntimeUtil
	import org.emoflon.ibex.tgg.operational.MODELGEN
	import org.emoflon.ibex.tgg.operational.OperationMode
	import org.emoflon.ibex.tgg.operational.OperationStrategy
	import org.apache.log4j.Logger
	import org.apache.log4j.LogManager
	import java.util.Collections
	import org.apache.log4j.Level
	import org.emoflon.ibex.tgg.common.*
	
	import org.emoflon.ibex.tgg.handleregisters.HandleRegisters_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.handleregisters.HandleRegisters_MODELGENMatcher
	import org.emoflon.ibex.tgg.handleregisters.HandleRegisters_BWD_ILPMatcher
	import org.emoflon.ibex.tgg.handleregisters.HandleRegisters_FWD_ILPMatcher
	import org.emoflon.ibex.tgg.handleregisters.HandleRegisters_BWD_PROTOCOLNACSMatcher
	import org.emoflon.ibex.tgg.handleregisters.HandleRegisters_FWD_PROTOCOLNACSMatcher
	import org.emoflon.ibex.tgg.handleregisters.HandleRegisters_CC_ILPMatcher
	
	class Transformation{	
		
		
		/* Transformation-related extensions */
		extension EventDrivenTransformation transformation
	
		/* Transformation rule-related extensions */
		extension EventDrivenTransformationRuleFactory = new EventDrivenTransformationRuleFactory
	
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
			
			val loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
			loggers.add(LogManager.getRootLogger());
			for ( Logger logger : loggers ) {
			   logger.setLevel(Level.OFF);
			}
		
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
			return
		}
		
		private def createTransformation() {
			// Initialize event-driven transformation
			transformation = EventDrivenTransformation.forEngine(engine).addRules(getTransformationRuleGroup).addRules(get_EdgePatterns).addRules(get_PROTOCOL).build
		}
		
		private def getTransformationRuleGroup() {
			if (tggRuntimeUtil.mode == OperationMode.FWD && tggRuntimeUtil.strategy == OperationStrategy.PROTOCOL_NACS)
				return get_FWD_PROTOCOLNACS
			else if (tggRuntimeUtil.mode == OperationMode.BWD && tggRuntimeUtil.strategy == OperationStrategy.PROTOCOL_NACS)
				return get_BWD_PROTOCOLNACS
			else if (tggRuntimeUtil.mode == OperationMode.FWD && tggRuntimeUtil.strategy == OperationStrategy.ILP)
				return get_FWD_ILP
			else if (tggRuntimeUtil.mode == OperationMode.BWD && tggRuntimeUtil.strategy == OperationStrategy.ILP)
				return get_BWD_ILP	
			else if (tggRuntimeUtil.mode == OperationMode.CC && tggRuntimeUtil.strategy == OperationStrategy.ILP)
				return get_CC_ILP
			else if (tggRuntimeUtil.mode == OperationMode.MODELGEN)
				return get_MODELGEN
		}
		
		private def get_PROTOCOL() {
			new EventDrivenTransformationRuleGroup(
			getHandleRegisters_PROTOCOL()
			)
		}
		
		private def getHandleRegisters_PROTOCOL() {
			createRule.name("HandleRegisters_PROTOCOL").precondition(HandleRegisters_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.revokeOperationalRule(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(true, true)
				).build
		}
		
		private def get_MODELGEN() {
			new EventDrivenTransformationRuleGroup(
			getHandleRegisters_MODELGEN()
			)
		}
		
		private def getHandleRegisters_MODELGEN() {
			createRule.name("HandleRegisters_MODELGEN").precondition(HandleRegisters_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("HandleRegisters", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(true, true)
				).build
		}
		
		private def get_BWD_ILP() {
			new EventDrivenTransformationRuleGroup(
			getHandleRegisters_BWD_ILP()
			)
		}
		
		private def getHandleRegisters_BWD_ILP() {
			createRule.name("HandleRegisters_BWD_ILP").precondition(HandleRegisters_BWD_ILPMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("HandleRegisters", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(true, true)
				).build
		}
		
		private def get_FWD_ILP() {
			new EventDrivenTransformationRuleGroup(
			getHandleRegisters_FWD_ILP()
			)
		}
		
		private def getHandleRegisters_FWD_ILP() {
			createRule.name("HandleRegisters_FWD_ILP").precondition(HandleRegisters_FWD_ILPMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("HandleRegisters", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(true, true)
				).build
		}
		
		private def get_BWD_PROTOCOLNACS() {
			new EventDrivenTransformationRuleGroup(
			getHandleRegisters_BWD_PROTOCOLNACS()
			)
		}
		
		private def getHandleRegisters_BWD_PROTOCOLNACS() {
			createRule.name("HandleRegisters_BWD_PROTOCOLNACS").precondition(HandleRegisters_BWD_PROTOCOLNACSMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("HandleRegisters", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(true, true)
				).build
		}
		
		private def get_FWD_PROTOCOLNACS() {
			new EventDrivenTransformationRuleGroup(
			getHandleRegisters_FWD_PROTOCOLNACS()
			)
		}
		
		private def getHandleRegisters_FWD_PROTOCOLNACS() {
			createRule.name("HandleRegisters_FWD_PROTOCOLNACS").precondition(HandleRegisters_FWD_PROTOCOLNACSMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("HandleRegisters", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(true, true)
				).build
		}
		
		private def get_CC_ILP() {
			new EventDrivenTransformationRuleGroup(
			getHandleRegisters_CC_ILP()
			)
		}
		
		private def getHandleRegisters_CC_ILP() {
			createRule.name("HandleRegisters_CC_ILP").precondition(HandleRegisters_CC_ILPMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("HandleRegisters", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(true, true)
				).build
		}
		
		
		
		private def get_EdgePatterns(){
			new EventDrivenTransformationRuleGroup(
			)
		}
	}		
	
