
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
	import org.emoflon.ibex.tgg.operational.csp.constraints.factories.MoflonToHenshinAttrCondDefLibrary
	
	
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnoderule.CreateTGGNodeToCreateHenshinNodeRule_FWDMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnoderule.CreateTGGNodeToCreateHenshinNodeRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnoderule.CreateTGGNodeToCreateHenshinNodeRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnoderule.CreateTGGNodeToCreateHenshinNodeRule_BWDMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnoderule.CreateTGGNodeToCreateHenshinNodeRule_CCMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocreatehenshinnoderuleplain.ContextTGGNodeToCreateHenshinNodeRulePlain_FWDMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocreatehenshinnoderuleplain.ContextTGGNodeToCreateHenshinNodeRulePlain_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocreatehenshinnoderuleplain.ContextTGGNodeToCreateHenshinNodeRulePlain_MODELGENMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocreatehenshinnoderuleplain.ContextTGGNodeToCreateHenshinNodeRulePlain_BWDMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocreatehenshinnoderuleplain.ContextTGGNodeToCreateHenshinNodeRulePlain_CCMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulerule.TGGRuleToHenshinRuleRule_FWDMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulerule.TGGRuleToHenshinRuleRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulerule.TGGRuleToHenshinRuleRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulerule.TGGRuleToHenshinRuleRule_BWDMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulerule.TGGRuleToHenshinRuleRule_CCMatcher
	import org.emoflon.ibex.tgg.tggtomodulerule.TGGToModuleRule_FWDMatcher
	import org.emoflon.ibex.tgg.tggtomodulerule.TGGToModuleRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggtomodulerule.TGGToModuleRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggtomodulerule.TGGToModuleRule_BWDMatcher
	import org.emoflon.ibex.tgg.tggtomodulerule.TGGToModuleRule_CCMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgerule.TGGEdgeToContextHenshinEdgeRule_FWDMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgerule.TGGEdgeToContextHenshinEdgeRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgerule.TGGEdgeToContextHenshinEdgeRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgerule.TGGEdgeToContextHenshinEdgeRule_BWDMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgerule.TGGEdgeToContextHenshinEdgeRule_CCMatcher
	import org.emoflon.ibex.tgg.tggedgetocreatehenshinedgerule.TGGEdgetoCreateHenshinEdgeRule_FWDMatcher
	import org.emoflon.ibex.tgg.tggedgetocreatehenshinedgerule.TGGEdgetoCreateHenshinEdgeRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggedgetocreatehenshinedgerule.TGGEdgetoCreateHenshinEdgeRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggedgetocreatehenshinedgerule.TGGEdgetoCreateHenshinEdgeRule_BWDMatcher
	import org.emoflon.ibex.tgg.tggedgetocreatehenshinedgerule.TGGEdgetoCreateHenshinEdgeRule_CCMatcher
	import org.emoflon.ibex.tgg.nactggnodetonachenshinnodewithoutotherformularule.NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_FWDMatcher
	import org.emoflon.ibex.tgg.nactggnodetonachenshinnodewithoutotherformularule.NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.nactggnodetonachenshinnodewithoutotherformularule.NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.nactggnodetonachenshinnodewithoutotherformularule.NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_BWDMatcher
	import org.emoflon.ibex.tgg.nactggnodetonachenshinnodewithoutotherformularule.NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_CCMatcher
	
	class MoflonToHenshinTransformation{	
		
		
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
			tggRuntimeUtil.getCSPProvider().registerFactory(new MoflonToHenshinAttrCondDefLibrary());
			
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
				getCreateTGGNodeToCreateHenshinNodeRule_FWD(),
				getCreateTGGNodeToCreateHenshinNodeRule_BWD(),
				getCreateTGGNodeToCreateHenshinNodeRule_PROTOCOL(), 
				getContextTGGNodeToCreateHenshinNodeRulePlain_FWD(),
				getContextTGGNodeToCreateHenshinNodeRulePlain_BWD(),
				getContextTGGNodeToCreateHenshinNodeRulePlain_PROTOCOL(), 
				getTGGRuleToHenshinRuleRule_FWD(),
				getTGGRuleToHenshinRuleRule_BWD(),
				getTGGRuleToHenshinRuleRule_PROTOCOL(), 
				getTGGToModuleRule_FWD(),
				getTGGToModuleRule_BWD(),
				getTGGToModuleRule_PROTOCOL(), 
				getTGGEdgeToContextHenshinEdgeRule_FWD(),
				getTGGEdgeToContextHenshinEdgeRule_BWD(),
				getTGGEdgeToContextHenshinEdgeRule_PROTOCOL(), 
				getTGGEdgetoCreateHenshinEdgeRule_FWD(),
				getTGGEdgetoCreateHenshinEdgeRule_BWD(),
				getTGGEdgetoCreateHenshinEdgeRule_PROTOCOL(), 
				getNACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_FWD(),
				getNACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_BWD(),
				getNACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_PROTOCOL()
				)
		}
		
		private def get_MODELGEN(){
			new EventDrivenTransformationRuleGroup(
				getCreateTGGNodeToCreateHenshinNodeRule_MODELGEN(), 
				getContextTGGNodeToCreateHenshinNodeRulePlain_MODELGEN(), 
				getTGGRuleToHenshinRuleRule_MODELGEN(), 
				getTGGToModuleRule_MODELGEN(), 
				getTGGEdgeToContextHenshinEdgeRule_MODELGEN(), 
				getTGGEdgetoCreateHenshinEdgeRule_MODELGEN(), 
				getNACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_MODELGEN()
			 )
		}
		
		private def get_CC(){
			new EventDrivenTransformationRuleGroup(
				getCreateTGGNodeToCreateHenshinNodeRule_CC(), 
				getContextTGGNodeToCreateHenshinNodeRulePlain_CC(), 
				getTGGRuleToHenshinRuleRule_CC(), 
				getTGGToModuleRule_CC(), 
				getTGGEdgeToContextHenshinEdgeRule_CC(), 
				getTGGEdgetoCreateHenshinEdgeRule_CC(), 
				getNACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_CC()
			)
		}
			
		
		private def getCreateTGGNodeToCreateHenshinNodeRule_FWD() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeRule_FWD").precondition(CreateTGGNodeToCreateHenshinNodeRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGNodeToCreateHenshinNodeRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToCreateHenshinNodeRulePlain_FWD() {
			createRule.name("ContextTGGNodeToCreateHenshinNodeRulePlain_FWD").precondition(ContextTGGNodeToCreateHenshinNodeRulePlain_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGNodeToCreateHenshinNodeRulePlain", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleRule_FWD() {
			createRule.name("TGGRuleToHenshinRuleRule_FWD").precondition(TGGRuleToHenshinRuleRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleToHenshinRuleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGToModuleRule_FWD() {
			createRule.name("TGGToModuleRule_FWD").precondition(TGGToModuleRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGToModuleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgeToContextHenshinEdgeRule_FWD() {
			createRule.name("TGGEdgeToContextHenshinEdgeRule_FWD").precondition(TGGEdgeToContextHenshinEdgeRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgeToContextHenshinEdgeRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgetoCreateHenshinEdgeRule_FWD() {
			createRule.name("TGGEdgetoCreateHenshinEdgeRule_FWD").precondition(TGGEdgetoCreateHenshinEdgeRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgetoCreateHenshinEdgeRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getNACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_FWD() {
			createRule.name("NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_FWD").precondition(NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getCreateTGGNodeToCreateHenshinNodeRule_PROTOCOL() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeRule_PROTOCOL").precondition(CreateTGGNodeToCreateHenshinNodeRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToCreateHenshinNodeRulePlain_PROTOCOL() {
			createRule.name("ContextTGGNodeToCreateHenshinNodeRulePlain_PROTOCOL").precondition(ContextTGGNodeToCreateHenshinNodeRulePlain_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleRule_PROTOCOL() {
			createRule.name("TGGRuleToHenshinRuleRule_PROTOCOL").precondition(TGGRuleToHenshinRuleRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGToModuleRule_PROTOCOL() {
			createRule.name("TGGToModuleRule_PROTOCOL").precondition(TGGToModuleRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgeToContextHenshinEdgeRule_PROTOCOL() {
			createRule.name("TGGEdgeToContextHenshinEdgeRule_PROTOCOL").precondition(TGGEdgeToContextHenshinEdgeRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgetoCreateHenshinEdgeRule_PROTOCOL() {
			createRule.name("TGGEdgetoCreateHenshinEdgeRule_PROTOCOL").precondition(TGGEdgetoCreateHenshinEdgeRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getNACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_PROTOCOL() {
			createRule.name("NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_PROTOCOL").precondition(NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getCreateTGGNodeToCreateHenshinNodeRule_MODELGEN() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeRule_MODELGEN").precondition(CreateTGGNodeToCreateHenshinNodeRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGNodeToCreateHenshinNodeRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToCreateHenshinNodeRulePlain_MODELGEN() {
			createRule.name("ContextTGGNodeToCreateHenshinNodeRulePlain_MODELGEN").precondition(ContextTGGNodeToCreateHenshinNodeRulePlain_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGNodeToCreateHenshinNodeRulePlain", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleRule_MODELGEN() {
			createRule.name("TGGRuleToHenshinRuleRule_MODELGEN").precondition(TGGRuleToHenshinRuleRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleToHenshinRuleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGToModuleRule_MODELGEN() {
			createRule.name("TGGToModuleRule_MODELGEN").precondition(TGGToModuleRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGToModuleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgeToContextHenshinEdgeRule_MODELGEN() {
			createRule.name("TGGEdgeToContextHenshinEdgeRule_MODELGEN").precondition(TGGEdgeToContextHenshinEdgeRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgeToContextHenshinEdgeRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgetoCreateHenshinEdgeRule_MODELGEN() {
			createRule.name("TGGEdgetoCreateHenshinEdgeRule_MODELGEN").precondition(TGGEdgetoCreateHenshinEdgeRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgetoCreateHenshinEdgeRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getNACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_MODELGEN() {
			createRule.name("NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_MODELGEN").precondition(NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getCreateTGGNodeToCreateHenshinNodeRule_BWD() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeRule_BWD").precondition(CreateTGGNodeToCreateHenshinNodeRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGNodeToCreateHenshinNodeRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToCreateHenshinNodeRulePlain_BWD() {
			createRule.name("ContextTGGNodeToCreateHenshinNodeRulePlain_BWD").precondition(ContextTGGNodeToCreateHenshinNodeRulePlain_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGNodeToCreateHenshinNodeRulePlain", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleRule_BWD() {
			createRule.name("TGGRuleToHenshinRuleRule_BWD").precondition(TGGRuleToHenshinRuleRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleToHenshinRuleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGToModuleRule_BWD() {
			createRule.name("TGGToModuleRule_BWD").precondition(TGGToModuleRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGToModuleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgeToContextHenshinEdgeRule_BWD() {
			createRule.name("TGGEdgeToContextHenshinEdgeRule_BWD").precondition(TGGEdgeToContextHenshinEdgeRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgeToContextHenshinEdgeRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgetoCreateHenshinEdgeRule_BWD() {
			createRule.name("TGGEdgetoCreateHenshinEdgeRule_BWD").precondition(TGGEdgetoCreateHenshinEdgeRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgetoCreateHenshinEdgeRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getNACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_BWD() {
			createRule.name("NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_BWD").precondition(NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getCreateTGGNodeToCreateHenshinNodeRule_CC() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeRule_CC").precondition(CreateTGGNodeToCreateHenshinNodeRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGNodeToCreateHenshinNodeRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToCreateHenshinNodeRulePlain_CC() {
			createRule.name("ContextTGGNodeToCreateHenshinNodeRulePlain_CC").precondition(ContextTGGNodeToCreateHenshinNodeRulePlain_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGNodeToCreateHenshinNodeRulePlain", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleRule_CC() {
			createRule.name("TGGRuleToHenshinRuleRule_CC").precondition(TGGRuleToHenshinRuleRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleToHenshinRuleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGToModuleRule_CC() {
			createRule.name("TGGToModuleRule_CC").precondition(TGGToModuleRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGToModuleRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgeToContextHenshinEdgeRule_CC() {
			createRule.name("TGGEdgeToContextHenshinEdgeRule_CC").precondition(TGGEdgeToContextHenshinEdgeRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgeToContextHenshinEdgeRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgetoCreateHenshinEdgeRule_CC() {
			createRule.name("TGGEdgetoCreateHenshinEdgeRule_CC").precondition(TGGEdgetoCreateHenshinEdgeRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgetoCreateHenshinEdgeRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getNACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_CC() {
			createRule.name("NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_CC").precondition(NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("NACTGGNodetoNACHenshinNodeWithoutOtherFormulaRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
	}		
	
