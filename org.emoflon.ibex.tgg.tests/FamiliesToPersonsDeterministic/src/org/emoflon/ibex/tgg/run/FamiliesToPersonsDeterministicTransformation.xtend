
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
	import org.emoflon.ibex.tgg.operational.csp.constraints.factories.FamiliesToPersonsDeterministicAttrCondDefLibrary
	
	
	import org.emoflon.ibex.tgg.fathertomalerule.FatherToMaleRule_FWDMatcher
	import org.emoflon.ibex.tgg.fathertomalerule.FatherToMaleRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.fathertomalerule.FatherToMaleRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.fathertomalerule.FatherToMaleRule_BWDMatcher
	import org.emoflon.ibex.tgg.fathertomalerule.FatherToMaleRule_CCMatcher
	import org.emoflon.ibex.tgg.handleregisters.HandleRegisters_FWDMatcher
	import org.emoflon.ibex.tgg.handleregisters.HandleRegisters_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.handleregisters.HandleRegisters_MODELGENMatcher
	import org.emoflon.ibex.tgg.handleregisters.HandleRegisters_BWDMatcher
	import org.emoflon.ibex.tgg.handleregisters.HandleRegisters_CCMatcher
	import org.emoflon.ibex.tgg.ignorefamily.IgnoreFamily_FWDMatcher
	import org.emoflon.ibex.tgg.ignorefamily.IgnoreFamily_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.ignorefamily.IgnoreFamily_MODELGENMatcher
	import org.emoflon.ibex.tgg.ignorefamily.IgnoreFamily_BWDMatcher
	import org.emoflon.ibex.tgg.ignorefamily.IgnoreFamily_CCMatcher
	import org.emoflon.ibex.tgg.mothertofemale.MotherToFemale_FWDMatcher
	import org.emoflon.ibex.tgg.mothertofemale.MotherToFemale_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.mothertofemale.MotherToFemale_MODELGENMatcher
	import org.emoflon.ibex.tgg.mothertofemale.MotherToFemale_BWDMatcher
	import org.emoflon.ibex.tgg.mothertofemale.MotherToFemale_CCMatcher
	import org.emoflon.ibex.tgg.sontomalerule.SonToMaleRule_FWDMatcher
	import org.emoflon.ibex.tgg.sontomalerule.SonToMaleRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.sontomalerule.SonToMaleRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.sontomalerule.SonToMaleRule_BWDMatcher
	import org.emoflon.ibex.tgg.sontomalerule.SonToMaleRule_CCMatcher
	
	class FamiliesToPersonsDeterministicTransformation{	
		
		
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
			tggRuntimeUtil.getCSPProvider().registerFactory(new FamiliesToPersonsDeterministicAttrCondDefLibrary());
			
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
			transformation = EventDrivenTransformation.forEngine(engine).addRules(getTransformationRuleGroup).build
		}
		
		private def getTransformationRuleGroup() {
			if (tggRuntimeUtil.mode == OperationMode.FWD || tggRuntimeUtil.mode == OperationMode.BWD)
				return getSynch
			else if (tggRuntimeUtil.mode == OperationMode.MODELGEN)
				return get_MODELGEN
			else if (tggRuntimeUtil.mode == OperationMode.CC)
				return get_CC
		}
		
		private def getSynch() {
			new EventDrivenTransformationRuleGroup(
				getFatherToMaleRule_FWD(),
				getFatherToMaleRule_BWD(),
				getFatherToMaleRule_PROTOCOL(), 
				getHandleRegisters_FWD(),
				getHandleRegisters_BWD(),
				getHandleRegisters_PROTOCOL(), 
				getIgnoreFamily_FWD(),
				getIgnoreFamily_BWD(),
				getIgnoreFamily_PROTOCOL(), 
				getMotherToFemale_FWD(),
				getMotherToFemale_BWD(),
				getMotherToFemale_PROTOCOL(), 
				getSonToMaleRule_FWD(),
				getSonToMaleRule_BWD(),
				getSonToMaleRule_PROTOCOL()
				)
		}
		
		private def get_MODELGEN(){
			new EventDrivenTransformationRuleGroup(
				getFatherToMaleRule_MODELGEN(), 
				getHandleRegisters_MODELGEN(), 
				getIgnoreFamily_MODELGEN(), 
				getMotherToFemale_MODELGEN(), 
				getSonToMaleRule_MODELGEN()
			 )
		}
		
		private def get_CC(){
			new EventDrivenTransformationRuleGroup(
				getFatherToMaleRule_CC(), 
				getHandleRegisters_CC(), 
				getIgnoreFamily_CC(), 
				getMotherToFemale_CC(), 
				getSonToMaleRule_CC()
			)
		}
			
		
		private def getFatherToMaleRule_FWD() {
			createRule.name("FatherToMaleRule_FWD").precondition(FatherToMaleRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("FatherToMaleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getHandleRegisters_FWD() {
			createRule.name("HandleRegisters_FWD").precondition(HandleRegisters_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("HandleRegisters", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getIgnoreFamily_FWD() {
			createRule.name("IgnoreFamily_FWD").precondition(IgnoreFamily_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("IgnoreFamily", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getMotherToFemale_FWD() {
			createRule.name("MotherToFemale_FWD").precondition(MotherToFemale_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("MotherToFemale", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getSonToMaleRule_FWD() {
			createRule.name("SonToMaleRule_FWD").precondition(SonToMaleRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("SonToMaleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getFatherToMaleRule_PROTOCOL() {
			createRule.name("FatherToMaleRule_PROTOCOL").precondition(FatherToMaleRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getHandleRegisters_PROTOCOL() {
			createRule.name("HandleRegisters_PROTOCOL").precondition(HandleRegisters_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getIgnoreFamily_PROTOCOL() {
			createRule.name("IgnoreFamily_PROTOCOL").precondition(IgnoreFamily_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getMotherToFemale_PROTOCOL() {
			createRule.name("MotherToFemale_PROTOCOL").precondition(MotherToFemale_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getSonToMaleRule_PROTOCOL() {
			createRule.name("SonToMaleRule_PROTOCOL").precondition(SonToMaleRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getFatherToMaleRule_MODELGEN() {
			createRule.name("FatherToMaleRule_MODELGEN").precondition(FatherToMaleRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("FatherToMaleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getHandleRegisters_MODELGEN() {
			createRule.name("HandleRegisters_MODELGEN").precondition(HandleRegisters_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("HandleRegisters", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getIgnoreFamily_MODELGEN() {
			createRule.name("IgnoreFamily_MODELGEN").precondition(IgnoreFamily_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("IgnoreFamily", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getMotherToFemale_MODELGEN() {
			createRule.name("MotherToFemale_MODELGEN").precondition(MotherToFemale_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("MotherToFemale", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getSonToMaleRule_MODELGEN() {
			createRule.name("SonToMaleRule_MODELGEN").precondition(SonToMaleRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("SonToMaleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getFatherToMaleRule_BWD() {
			createRule.name("FatherToMaleRule_BWD").precondition(FatherToMaleRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("FatherToMaleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getHandleRegisters_BWD() {
			createRule.name("HandleRegisters_BWD").precondition(HandleRegisters_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("HandleRegisters", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getIgnoreFamily_BWD() {
			createRule.name("IgnoreFamily_BWD").precondition(IgnoreFamily_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("IgnoreFamily", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getMotherToFemale_BWD() {
			createRule.name("MotherToFemale_BWD").precondition(MotherToFemale_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("MotherToFemale", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getSonToMaleRule_BWD() {
			createRule.name("SonToMaleRule_BWD").precondition(SonToMaleRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("SonToMaleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getFatherToMaleRule_CC() {
			createRule.name("FatherToMaleRule_CC").precondition(FatherToMaleRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("FatherToMaleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getHandleRegisters_CC() {
			createRule.name("HandleRegisters_CC").precondition(HandleRegisters_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("HandleRegisters", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getIgnoreFamily_CC() {
			createRule.name("IgnoreFamily_CC").precondition(IgnoreFamily_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("IgnoreFamily", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getMotherToFemale_CC() {
			createRule.name("MotherToFemale_CC").precondition(MotherToFemale_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("MotherToFemale", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getSonToMaleRule_CC() {
			createRule.name("SonToMaleRule_CC").precondition(SonToMaleRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("SonToMaleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
	}		
	
