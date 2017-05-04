
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
	
	
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgeisland.TGGEdgeToContextHenshinEdgeIsland_FWDMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgeisland.TGGEdgeToContextHenshinEdgeIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgeisland.TGGEdgeToContextHenshinEdgeIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgeisland.TGGEdgeToContextHenshinEdgeIsland_BWDMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgeisland.TGGEdgeToContextHenshinEdgeIsland_CCMatcher
	import org.emoflon.ibex.tgg.tggruleedgetocreatehenshinedgebridge.TGGRuleEdgeToCreateHenshinEdgeBridge_FWDMatcher
	import org.emoflon.ibex.tgg.tggruleedgetocreatehenshinedgebridge.TGGRuleEdgeToCreateHenshinEdgeBridge_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggruleedgetocreatehenshinedgebridge.TGGRuleEdgeToCreateHenshinEdgeBridge_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggruleedgetocreatehenshinedgebridge.TGGRuleEdgeToCreateHenshinEdgeBridge_BWDMatcher
	import org.emoflon.ibex.tgg.tggruleedgetocreatehenshinedgebridge.TGGRuleEdgeToCreateHenshinEdgeBridge_CCMatcher
	import org.emoflon.ibex.tgg.tggtomoduleisland.TGGToModuleIsland_FWDMatcher
	import org.emoflon.ibex.tgg.tggtomoduleisland.TGGToModuleIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggtomoduleisland.TGGToModuleIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggtomoduleisland.TGGToModuleIsland_BWDMatcher
	import org.emoflon.ibex.tgg.tggtomoduleisland.TGGToModuleIsland_CCMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodeisland.CreateTGGNodeToCreateHenshinNodeIsland_FWDMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodeisland.CreateTGGNodeToCreateHenshinNodeIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodeisland.CreateTGGNodeToCreateHenshinNodeIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodeisland.CreateTGGNodeToCreateHenshinNodeIsland_BWDMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodeisland.CreateTGGNodeToCreateHenshinNodeIsland_CCMatcher
	import org.emoflon.ibex.tgg.tggruleedgetocreatehenshinedgeisland.TGGRuleEdgeToCreateHenshinEdgeIsland_FWDMatcher
	import org.emoflon.ibex.tgg.tggruleedgetocreatehenshinedgeisland.TGGRuleEdgeToCreateHenshinEdgeIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggruleedgetocreatehenshinedgeisland.TGGRuleEdgeToCreateHenshinEdgeIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggruleedgetocreatehenshinedgeisland.TGGRuleEdgeToCreateHenshinEdgeIsland_BWDMatcher
	import org.emoflon.ibex.tgg.tggruleedgetocreatehenshinedgeisland.TGGRuleEdgeToCreateHenshinEdgeIsland_CCMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgebridge.TGGEdgeToContextHenshinEdgeBridge_FWDMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgebridge.TGGEdgeToContextHenshinEdgeBridge_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgebridge.TGGEdgeToContextHenshinEdgeBridge_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgebridge.TGGEdgeToContextHenshinEdgeBridge_BWDMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgebridge.TGGEdgeToContextHenshinEdgeBridge_CCMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulebridge.TGGRuleToHenshinRuleBridge_FWDMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulebridge.TGGRuleToHenshinRuleBridge_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulebridge.TGGRuleToHenshinRuleBridge_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulebridge.TGGRuleToHenshinRuleBridge_BWDMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulebridge.TGGRuleToHenshinRuleBridge_CCMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinruleisland.TGGRuleToHenshinRuleIsland_FWDMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinruleisland.TGGRuleToHenshinRuleIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinruleisland.TGGRuleToHenshinRuleIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinruleisland.TGGRuleToHenshinRuleIsland_BWDMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinruleisland.TGGRuleToHenshinRuleIsland_CCMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodebridge.ContextTGGNodeToContextHenshinNodeBridge_FWDMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodebridge.ContextTGGNodeToContextHenshinNodeBridge_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodebridge.ContextTGGNodeToContextHenshinNodeBridge_MODELGENMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodebridge.ContextTGGNodeToContextHenshinNodeBridge_BWDMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodebridge.ContextTGGNodeToContextHenshinNodeBridge_CCMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodebridge.CreateTGGNodeToCreateHenshinNodeBridge_FWDMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodebridge.CreateTGGNodeToCreateHenshinNodeBridge_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodebridge.CreateTGGNodeToCreateHenshinNodeBridge_MODELGENMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodebridge.CreateTGGNodeToCreateHenshinNodeBridge_BWDMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodebridge.CreateTGGNodeToCreateHenshinNodeBridge_CCMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodeisland.ContextTGGNodeToContextHenshinNodeIsland_FWDMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodeisland.ContextTGGNodeToContextHenshinNodeIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodeisland.ContextTGGNodeToContextHenshinNodeIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodeisland.ContextTGGNodeToContextHenshinNodeIsland_BWDMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodeisland.ContextTGGNodeToContextHenshinNodeIsland_CCMatcher
	
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
				getTGGEdgeToContextHenshinEdgeIsland_FWD(),
				getTGGEdgeToContextHenshinEdgeIsland_BWD(),
				getTGGEdgeToContextHenshinEdgeIsland_PROTOCOL(), 
				getTGGRuleEdgeToCreateHenshinEdgeBridge_FWD(),
				getTGGRuleEdgeToCreateHenshinEdgeBridge_BWD(),
				getTGGRuleEdgeToCreateHenshinEdgeBridge_PROTOCOL(), 
				getTGGToModuleIsland_FWD(),
				getTGGToModuleIsland_BWD(),
				getTGGToModuleIsland_PROTOCOL(), 
				getCreateTGGNodeToCreateHenshinNodeIsland_FWD(),
				getCreateTGGNodeToCreateHenshinNodeIsland_BWD(),
				getCreateTGGNodeToCreateHenshinNodeIsland_PROTOCOL(), 
				getTGGRuleEdgeToCreateHenshinEdgeIsland_FWD(),
				getTGGRuleEdgeToCreateHenshinEdgeIsland_BWD(),
				getTGGRuleEdgeToCreateHenshinEdgeIsland_PROTOCOL(), 
				getTGGEdgeToContextHenshinEdgeBridge_FWD(),
				getTGGEdgeToContextHenshinEdgeBridge_BWD(),
				getTGGEdgeToContextHenshinEdgeBridge_PROTOCOL(), 
				getTGGRuleToHenshinRuleBridge_FWD(),
				getTGGRuleToHenshinRuleBridge_BWD(),
				getTGGRuleToHenshinRuleBridge_PROTOCOL(), 
				getTGGRuleToHenshinRuleIsland_FWD(),
				getTGGRuleToHenshinRuleIsland_BWD(),
				getTGGRuleToHenshinRuleIsland_PROTOCOL(), 
				getContextTGGNodeToContextHenshinNodeBridge_FWD(),
				getContextTGGNodeToContextHenshinNodeBridge_BWD(),
				getContextTGGNodeToContextHenshinNodeBridge_PROTOCOL(), 
				getCreateTGGNodeToCreateHenshinNodeBridge_FWD(),
				getCreateTGGNodeToCreateHenshinNodeBridge_BWD(),
				getCreateTGGNodeToCreateHenshinNodeBridge_PROTOCOL(), 
				getContextTGGNodeToContextHenshinNodeIsland_FWD(),
				getContextTGGNodeToContextHenshinNodeIsland_BWD(),
				getContextTGGNodeToContextHenshinNodeIsland_PROTOCOL()
				)
		}
		
		private def get_MODELGEN(){
			new EventDrivenTransformationRuleGroup(
				getTGGEdgeToContextHenshinEdgeIsland_MODELGEN(), 
				getTGGRuleEdgeToCreateHenshinEdgeBridge_MODELGEN(), 
				getTGGToModuleIsland_MODELGEN(), 
				getCreateTGGNodeToCreateHenshinNodeIsland_MODELGEN(), 
				getTGGRuleEdgeToCreateHenshinEdgeIsland_MODELGEN(), 
				getTGGEdgeToContextHenshinEdgeBridge_MODELGEN(), 
				getTGGRuleToHenshinRuleBridge_MODELGEN(), 
				getTGGRuleToHenshinRuleIsland_MODELGEN(), 
				getContextTGGNodeToContextHenshinNodeBridge_MODELGEN(), 
				getCreateTGGNodeToCreateHenshinNodeBridge_MODELGEN(), 
				getContextTGGNodeToContextHenshinNodeIsland_MODELGEN()
			 )
		}
		
		private def get_CC(){
			new EventDrivenTransformationRuleGroup(
				getTGGEdgeToContextHenshinEdgeIsland_CC(), 
				getTGGRuleEdgeToCreateHenshinEdgeBridge_CC(), 
				getTGGToModuleIsland_CC(), 
				getCreateTGGNodeToCreateHenshinNodeIsland_CC(), 
				getTGGRuleEdgeToCreateHenshinEdgeIsland_CC(), 
				getTGGEdgeToContextHenshinEdgeBridge_CC(), 
				getTGGRuleToHenshinRuleBridge_CC(), 
				getTGGRuleToHenshinRuleIsland_CC(), 
				getContextTGGNodeToContextHenshinNodeBridge_CC(), 
				getCreateTGGNodeToCreateHenshinNodeBridge_CC(), 
				getContextTGGNodeToContextHenshinNodeIsland_CC()
			)
		}
			
		
		private def getTGGEdgeToContextHenshinEdgeIsland_FWD() {
			createRule.name("TGGEdgeToContextHenshinEdgeIsland_FWD").precondition(TGGEdgeToContextHenshinEdgeIsland_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgeToContextHenshinEdgeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleEdgeToCreateHenshinEdgeBridge_FWD() {
			createRule.name("TGGRuleEdgeToCreateHenshinEdgeBridge_FWD").precondition(TGGRuleEdgeToCreateHenshinEdgeBridge_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleEdgeToCreateHenshinEdgeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGToModuleIsland_FWD() {
			createRule.name("TGGToModuleIsland_FWD").precondition(TGGToModuleIsland_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGToModuleIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getCreateTGGNodeToCreateHenshinNodeIsland_FWD() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeIsland_FWD").precondition(CreateTGGNodeToCreateHenshinNodeIsland_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGNodeToCreateHenshinNodeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleEdgeToCreateHenshinEdgeIsland_FWD() {
			createRule.name("TGGRuleEdgeToCreateHenshinEdgeIsland_FWD").precondition(TGGRuleEdgeToCreateHenshinEdgeIsland_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleEdgeToCreateHenshinEdgeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgeToContextHenshinEdgeBridge_FWD() {
			createRule.name("TGGEdgeToContextHenshinEdgeBridge_FWD").precondition(TGGEdgeToContextHenshinEdgeBridge_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgeToContextHenshinEdgeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleBridge_FWD() {
			createRule.name("TGGRuleToHenshinRuleBridge_FWD").precondition(TGGRuleToHenshinRuleBridge_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleToHenshinRuleBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleIsland_FWD() {
			createRule.name("TGGRuleToHenshinRuleIsland_FWD").precondition(TGGRuleToHenshinRuleIsland_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleToHenshinRuleIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToContextHenshinNodeBridge_FWD() {
			createRule.name("ContextTGGNodeToContextHenshinNodeBridge_FWD").precondition(ContextTGGNodeToContextHenshinNodeBridge_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGNodeToContextHenshinNodeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getCreateTGGNodeToCreateHenshinNodeBridge_FWD() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeBridge_FWD").precondition(CreateTGGNodeToCreateHenshinNodeBridge_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGNodeToCreateHenshinNodeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToContextHenshinNodeIsland_FWD() {
			createRule.name("ContextTGGNodeToContextHenshinNodeIsland_FWD").precondition(ContextTGGNodeToContextHenshinNodeIsland_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGNodeToContextHenshinNodeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getTGGEdgeToContextHenshinEdgeIsland_PROTOCOL() {
			createRule.name("TGGEdgeToContextHenshinEdgeIsland_PROTOCOL").precondition(TGGEdgeToContextHenshinEdgeIsland_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleEdgeToCreateHenshinEdgeBridge_PROTOCOL() {
			createRule.name("TGGRuleEdgeToCreateHenshinEdgeBridge_PROTOCOL").precondition(TGGRuleEdgeToCreateHenshinEdgeBridge_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGToModuleIsland_PROTOCOL() {
			createRule.name("TGGToModuleIsland_PROTOCOL").precondition(TGGToModuleIsland_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getCreateTGGNodeToCreateHenshinNodeIsland_PROTOCOL() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeIsland_PROTOCOL").precondition(CreateTGGNodeToCreateHenshinNodeIsland_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleEdgeToCreateHenshinEdgeIsland_PROTOCOL() {
			createRule.name("TGGRuleEdgeToCreateHenshinEdgeIsland_PROTOCOL").precondition(TGGRuleEdgeToCreateHenshinEdgeIsland_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgeToContextHenshinEdgeBridge_PROTOCOL() {
			createRule.name("TGGEdgeToContextHenshinEdgeBridge_PROTOCOL").precondition(TGGEdgeToContextHenshinEdgeBridge_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleBridge_PROTOCOL() {
			createRule.name("TGGRuleToHenshinRuleBridge_PROTOCOL").precondition(TGGRuleToHenshinRuleBridge_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleIsland_PROTOCOL() {
			createRule.name("TGGRuleToHenshinRuleIsland_PROTOCOL").precondition(TGGRuleToHenshinRuleIsland_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToContextHenshinNodeBridge_PROTOCOL() {
			createRule.name("ContextTGGNodeToContextHenshinNodeBridge_PROTOCOL").precondition(ContextTGGNodeToContextHenshinNodeBridge_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getCreateTGGNodeToCreateHenshinNodeBridge_PROTOCOL() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeBridge_PROTOCOL").precondition(CreateTGGNodeToCreateHenshinNodeBridge_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToContextHenshinNodeIsland_PROTOCOL() {
			createRule.name("ContextTGGNodeToContextHenshinNodeIsland_PROTOCOL").precondition(ContextTGGNodeToContextHenshinNodeIsland_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getTGGEdgeToContextHenshinEdgeIsland_MODELGEN() {
			createRule.name("TGGEdgeToContextHenshinEdgeIsland_MODELGEN").precondition(TGGEdgeToContextHenshinEdgeIsland_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgeToContextHenshinEdgeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleEdgeToCreateHenshinEdgeBridge_MODELGEN() {
			createRule.name("TGGRuleEdgeToCreateHenshinEdgeBridge_MODELGEN").precondition(TGGRuleEdgeToCreateHenshinEdgeBridge_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleEdgeToCreateHenshinEdgeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGToModuleIsland_MODELGEN() {
			createRule.name("TGGToModuleIsland_MODELGEN").precondition(TGGToModuleIsland_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGToModuleIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getCreateTGGNodeToCreateHenshinNodeIsland_MODELGEN() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeIsland_MODELGEN").precondition(CreateTGGNodeToCreateHenshinNodeIsland_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGNodeToCreateHenshinNodeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleEdgeToCreateHenshinEdgeIsland_MODELGEN() {
			createRule.name("TGGRuleEdgeToCreateHenshinEdgeIsland_MODELGEN").precondition(TGGRuleEdgeToCreateHenshinEdgeIsland_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleEdgeToCreateHenshinEdgeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgeToContextHenshinEdgeBridge_MODELGEN() {
			createRule.name("TGGEdgeToContextHenshinEdgeBridge_MODELGEN").precondition(TGGEdgeToContextHenshinEdgeBridge_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgeToContextHenshinEdgeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleBridge_MODELGEN() {
			createRule.name("TGGRuleToHenshinRuleBridge_MODELGEN").precondition(TGGRuleToHenshinRuleBridge_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleToHenshinRuleBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleIsland_MODELGEN() {
			createRule.name("TGGRuleToHenshinRuleIsland_MODELGEN").precondition(TGGRuleToHenshinRuleIsland_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleToHenshinRuleIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToContextHenshinNodeBridge_MODELGEN() {
			createRule.name("ContextTGGNodeToContextHenshinNodeBridge_MODELGEN").precondition(ContextTGGNodeToContextHenshinNodeBridge_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGNodeToContextHenshinNodeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getCreateTGGNodeToCreateHenshinNodeBridge_MODELGEN() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeBridge_MODELGEN").precondition(CreateTGGNodeToCreateHenshinNodeBridge_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGNodeToCreateHenshinNodeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToContextHenshinNodeIsland_MODELGEN() {
			createRule.name("ContextTGGNodeToContextHenshinNodeIsland_MODELGEN").precondition(ContextTGGNodeToContextHenshinNodeIsland_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGNodeToContextHenshinNodeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getTGGEdgeToContextHenshinEdgeIsland_BWD() {
			createRule.name("TGGEdgeToContextHenshinEdgeIsland_BWD").precondition(TGGEdgeToContextHenshinEdgeIsland_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgeToContextHenshinEdgeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleEdgeToCreateHenshinEdgeBridge_BWD() {
			createRule.name("TGGRuleEdgeToCreateHenshinEdgeBridge_BWD").precondition(TGGRuleEdgeToCreateHenshinEdgeBridge_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleEdgeToCreateHenshinEdgeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGToModuleIsland_BWD() {
			createRule.name("TGGToModuleIsland_BWD").precondition(TGGToModuleIsland_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGToModuleIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getCreateTGGNodeToCreateHenshinNodeIsland_BWD() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeIsland_BWD").precondition(CreateTGGNodeToCreateHenshinNodeIsland_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGNodeToCreateHenshinNodeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleEdgeToCreateHenshinEdgeIsland_BWD() {
			createRule.name("TGGRuleEdgeToCreateHenshinEdgeIsland_BWD").precondition(TGGRuleEdgeToCreateHenshinEdgeIsland_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleEdgeToCreateHenshinEdgeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgeToContextHenshinEdgeBridge_BWD() {
			createRule.name("TGGEdgeToContextHenshinEdgeBridge_BWD").precondition(TGGEdgeToContextHenshinEdgeBridge_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgeToContextHenshinEdgeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleBridge_BWD() {
			createRule.name("TGGRuleToHenshinRuleBridge_BWD").precondition(TGGRuleToHenshinRuleBridge_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleToHenshinRuleBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleIsland_BWD() {
			createRule.name("TGGRuleToHenshinRuleIsland_BWD").precondition(TGGRuleToHenshinRuleIsland_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleToHenshinRuleIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToContextHenshinNodeBridge_BWD() {
			createRule.name("ContextTGGNodeToContextHenshinNodeBridge_BWD").precondition(ContextTGGNodeToContextHenshinNodeBridge_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGNodeToContextHenshinNodeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getCreateTGGNodeToCreateHenshinNodeBridge_BWD() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeBridge_BWD").precondition(CreateTGGNodeToCreateHenshinNodeBridge_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGNodeToCreateHenshinNodeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToContextHenshinNodeIsland_BWD() {
			createRule.name("ContextTGGNodeToContextHenshinNodeIsland_BWD").precondition(ContextTGGNodeToContextHenshinNodeIsland_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGNodeToContextHenshinNodeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getTGGEdgeToContextHenshinEdgeIsland_CC() {
			createRule.name("TGGEdgeToContextHenshinEdgeIsland_CC").precondition(TGGEdgeToContextHenshinEdgeIsland_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgeToContextHenshinEdgeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleEdgeToCreateHenshinEdgeBridge_CC() {
			createRule.name("TGGRuleEdgeToCreateHenshinEdgeBridge_CC").precondition(TGGRuleEdgeToCreateHenshinEdgeBridge_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleEdgeToCreateHenshinEdgeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGToModuleIsland_CC() {
			createRule.name("TGGToModuleIsland_CC").precondition(TGGToModuleIsland_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGToModuleIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getCreateTGGNodeToCreateHenshinNodeIsland_CC() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeIsland_CC").precondition(CreateTGGNodeToCreateHenshinNodeIsland_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGNodeToCreateHenshinNodeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleEdgeToCreateHenshinEdgeIsland_CC() {
			createRule.name("TGGRuleEdgeToCreateHenshinEdgeIsland_CC").precondition(TGGRuleEdgeToCreateHenshinEdgeIsland_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleEdgeToCreateHenshinEdgeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGEdgeToContextHenshinEdgeBridge_CC() {
			createRule.name("TGGEdgeToContextHenshinEdgeBridge_CC").precondition(TGGEdgeToContextHenshinEdgeBridge_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGEdgeToContextHenshinEdgeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleBridge_CC() {
			createRule.name("TGGRuleToHenshinRuleBridge_CC").precondition(TGGRuleToHenshinRuleBridge_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleToHenshinRuleBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getTGGRuleToHenshinRuleIsland_CC() {
			createRule.name("TGGRuleToHenshinRuleIsland_CC").precondition(TGGRuleToHenshinRuleIsland_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("TGGRuleToHenshinRuleIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToContextHenshinNodeBridge_CC() {
			createRule.name("ContextTGGNodeToContextHenshinNodeBridge_CC").precondition(ContextTGGNodeToContextHenshinNodeBridge_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGNodeToContextHenshinNodeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getCreateTGGNodeToCreateHenshinNodeBridge_CC() {
			createRule.name("CreateTGGNodeToCreateHenshinNodeBridge_CC").precondition(CreateTGGNodeToCreateHenshinNodeBridge_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGNodeToCreateHenshinNodeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGNodeToContextHenshinNodeIsland_CC() {
			createRule.name("ContextTGGNodeToContextHenshinNodeIsland_CC").precondition(ContextTGGNodeToContextHenshinNodeIsland_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGNodeToContextHenshinNodeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
	}		
	
