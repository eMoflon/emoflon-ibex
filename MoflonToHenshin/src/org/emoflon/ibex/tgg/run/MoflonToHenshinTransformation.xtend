
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
	
	
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodebridge.ContextTGGNodeToContextHenshinNodeBridge_FWDMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodebridge.ContextTGGNodeToContextHenshinNodeBridge_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodebridge.ContextTGGNodeToContextHenshinNodeBridge_MODELGENMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodebridge.ContextTGGNodeToContextHenshinNodeBridge_BWDMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodebridge.ContextTGGNodeToContextHenshinNodeBridge_CCMatcher
	import org.emoflon.ibex.tgg.tggtomoduleisland.TGGToModuleIsland_FWDMatcher
	import org.emoflon.ibex.tgg.tggtomoduleisland.TGGToModuleIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggtomoduleisland.TGGToModuleIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggtomoduleisland.TGGToModuleIsland_BWDMatcher
	import org.emoflon.ibex.tgg.tggtomoduleisland.TGGToModuleIsland_CCMatcher
	import org.emoflon.ibex.tgg.createtggedgetocreatehenshinedgeisland.CreateTGGEdgeToCreateHenshinEdgeIsland_FWDMatcher
	import org.emoflon.ibex.tgg.createtggedgetocreatehenshinedgeisland.CreateTGGEdgeToCreateHenshinEdgeIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.createtggedgetocreatehenshinedgeisland.CreateTGGEdgeToCreateHenshinEdgeIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.createtggedgetocreatehenshinedgeisland.CreateTGGEdgeToCreateHenshinEdgeIsland_BWDMatcher
	import org.emoflon.ibex.tgg.createtggedgetocreatehenshinedgeisland.CreateTGGEdgeToCreateHenshinEdgeIsland_CCMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocreatehenshinnoderuleplain.ContextTGGNodeToCreateHenshinNodeRulePlain_FWDMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocreatehenshinnoderuleplain.ContextTGGNodeToCreateHenshinNodeRulePlain_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocreatehenshinnoderuleplain.ContextTGGNodeToCreateHenshinNodeRulePlain_MODELGENMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocreatehenshinnoderuleplain.ContextTGGNodeToCreateHenshinNodeRulePlain_BWDMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocreatehenshinnoderuleplain.ContextTGGNodeToCreateHenshinNodeRulePlain_CCMatcher
	import org.emoflon.ibex.tgg.tggedgetocreatehenshinedgerule.TGGEdgetoCreateHenshinEdgeRule_FWDMatcher
	import org.emoflon.ibex.tgg.tggedgetocreatehenshinedgerule.TGGEdgetoCreateHenshinEdgeRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggedgetocreatehenshinedgerule.TGGEdgetoCreateHenshinEdgeRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggedgetocreatehenshinedgerule.TGGEdgetoCreateHenshinEdgeRule_BWDMatcher
	import org.emoflon.ibex.tgg.tggedgetocreatehenshinedgerule.TGGEdgetoCreateHenshinEdgeRule_CCMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodeisland.CreateTGGNodeToCreateHenshinNodeIsland_FWDMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodeisland.CreateTGGNodeToCreateHenshinNodeIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodeisland.CreateTGGNodeToCreateHenshinNodeIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodeisland.CreateTGGNodeToCreateHenshinNodeIsland_BWDMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodeisland.CreateTGGNodeToCreateHenshinNodeIsland_CCMatcher
	import org.emoflon.ibex.tgg.contexttggedgetocontexthenshinedgebridge.ContextTGGEdgeToContextHenshinEdgeBridge_FWDMatcher
	import org.emoflon.ibex.tgg.contexttggedgetocontexthenshinedgebridge.ContextTGGEdgeToContextHenshinEdgeBridge_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.contexttggedgetocontexthenshinedgebridge.ContextTGGEdgeToContextHenshinEdgeBridge_MODELGENMatcher
	import org.emoflon.ibex.tgg.contexttggedgetocontexthenshinedgebridge.ContextTGGEdgeToContextHenshinEdgeBridge_BWDMatcher
	import org.emoflon.ibex.tgg.contexttggedgetocontexthenshinedgebridge.ContextTGGEdgeToContextHenshinEdgeBridge_CCMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgerule.TGGEdgeToContextHenshinEdgeRule_FWDMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgerule.TGGEdgeToContextHenshinEdgeRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgerule.TGGEdgeToContextHenshinEdgeRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgerule.TGGEdgeToContextHenshinEdgeRule_BWDMatcher
	import org.emoflon.ibex.tgg.tggedgetocontexthenshinedgerule.TGGEdgeToContextHenshinEdgeRule_CCMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodeisland.ContextTGGNodeToContextHenshinNodeIsland_FWDMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodeisland.ContextTGGNodeToContextHenshinNodeIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodeisland.ContextTGGNodeToContextHenshinNodeIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodeisland.ContextTGGNodeToContextHenshinNodeIsland_BWDMatcher
	import org.emoflon.ibex.tgg.contexttggnodetocontexthenshinnodeisland.ContextTGGNodeToContextHenshinNodeIsland_CCMatcher
	import org.emoflon.ibex.tgg.createtggedgetocreatehenshinedgebridge.CreateTGGEdgeToCreateHenshinEdgeBridge_FWDMatcher
	import org.emoflon.ibex.tgg.createtggedgetocreatehenshinedgebridge.CreateTGGEdgeToCreateHenshinEdgeBridge_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.createtggedgetocreatehenshinedgebridge.CreateTGGEdgeToCreateHenshinEdgeBridge_MODELGENMatcher
	import org.emoflon.ibex.tgg.createtggedgetocreatehenshinedgebridge.CreateTGGEdgeToCreateHenshinEdgeBridge_BWDMatcher
	import org.emoflon.ibex.tgg.createtggedgetocreatehenshinedgebridge.CreateTGGEdgeToCreateHenshinEdgeBridge_CCMatcher
	import org.emoflon.ibex.tgg.attributeassignmenttosetattributeisland.AttributeAssignmentToSetAttributeIsland_FWDMatcher
	import org.emoflon.ibex.tgg.attributeassignmenttosetattributeisland.AttributeAssignmentToSetAttributeIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.attributeassignmenttosetattributeisland.AttributeAssignmentToSetAttributeIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.attributeassignmenttosetattributeisland.AttributeAssignmentToSetAttributeIsland_BWDMatcher
	import org.emoflon.ibex.tgg.attributeassignmenttosetattributeisland.AttributeAssignmentToSetAttributeIsland_CCMatcher
	import org.emoflon.ibex.tgg.contexttggedgetocontexthenshinedgeisland.ContextTGGEdgeToContextHenshinEdgeIsland_FWDMatcher
	import org.emoflon.ibex.tgg.contexttggedgetocontexthenshinedgeisland.ContextTGGEdgeToContextHenshinEdgeIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.contexttggedgetocontexthenshinedgeisland.ContextTGGEdgeToContextHenshinEdgeIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.contexttggedgetocontexthenshinedgeisland.ContextTGGEdgeToContextHenshinEdgeIsland_BWDMatcher
	import org.emoflon.ibex.tgg.contexttggedgetocontexthenshinedgeisland.ContextTGGEdgeToContextHenshinEdgeIsland_CCMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodebridge.CreateTGGNodeToCreateHenshinNodeBridge_FWDMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodebridge.CreateTGGNodeToCreateHenshinNodeBridge_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodebridge.CreateTGGNodeToCreateHenshinNodeBridge_MODELGENMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodebridge.CreateTGGNodeToCreateHenshinNodeBridge_BWDMatcher
	import org.emoflon.ibex.tgg.createtggnodetocreatehenshinnodebridge.CreateTGGNodeToCreateHenshinNodeBridge_CCMatcher
	import org.emoflon.ibex.tgg.attributeassignmenttosetattributebridge.AttributeAssignmentToSetAttributeBridge_FWDMatcher
	import org.emoflon.ibex.tgg.attributeassignmenttosetattributebridge.AttributeAssignmentToSetAttributeBridge_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.attributeassignmenttosetattributebridge.AttributeAssignmentToSetAttributeBridge_MODELGENMatcher
	import org.emoflon.ibex.tgg.attributeassignmenttosetattributebridge.AttributeAssignmentToSetAttributeBridge_BWDMatcher
	import org.emoflon.ibex.tgg.attributeassignmenttosetattributebridge.AttributeAssignmentToSetAttributeBridge_CCMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinruleisland.TGGRuleToHenshinRuleIsland_FWDMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinruleisland.TGGRuleToHenshinRuleIsland_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinruleisland.TGGRuleToHenshinRuleIsland_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinruleisland.TGGRuleToHenshinRuleIsland_BWDMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinruleisland.TGGRuleToHenshinRuleIsland_CCMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulebridge.TGGRuleToHenshinRuleBridge_FWDMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulebridge.TGGRuleToHenshinRuleBridge_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulebridge.TGGRuleToHenshinRuleBridge_MODELGENMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulebridge.TGGRuleToHenshinRuleBridge_BWDMatcher
	import org.emoflon.ibex.tgg.tggruletohenshinrulebridge.TGGRuleToHenshinRuleBridge_CCMatcher
	
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
				getContextTGGNodeToContextHenshinNodeBridge_FWD(),
				getContextTGGNodeToContextHenshinNodeBridge_BWD(),
				getContextTGGNodeToContextHenshinNodeBridge_PROTOCOL(), 
				getTGGToModuleIsland_FWD(),
				getTGGToModuleIsland_BWD(),
				getTGGToModuleIsland_PROTOCOL(), 
				getCreateTGGEdgeToCreateHenshinEdgeIsland_FWD(),
				getCreateTGGEdgeToCreateHenshinEdgeIsland_BWD(),
				getCreateTGGEdgeToCreateHenshinEdgeIsland_PROTOCOL(), 
				getContextTGGNodeToCreateHenshinNodeRulePlain_FWD(),
				getContextTGGNodeToCreateHenshinNodeRulePlain_BWD(),
				getContextTGGNodeToCreateHenshinNodeRulePlain_PROTOCOL(), 
				getTGGEdgetoCreateHenshinEdgeRule_FWD(),
				getTGGEdgetoCreateHenshinEdgeRule_BWD(),
				getTGGEdgetoCreateHenshinEdgeRule_PROTOCOL(), 
				getCreateTGGNodeToCreateHenshinNodeIsland_FWD(),
				getCreateTGGNodeToCreateHenshinNodeIsland_BWD(),
				getCreateTGGNodeToCreateHenshinNodeIsland_PROTOCOL(), 
				getContextTGGEdgeToContextHenshinEdgeBridge_FWD(),
				getContextTGGEdgeToContextHenshinEdgeBridge_BWD(),
				getContextTGGEdgeToContextHenshinEdgeBridge_PROTOCOL(), 
				getTGGEdgeToContextHenshinEdgeRule_FWD(),
				getTGGEdgeToContextHenshinEdgeRule_BWD(),
				getTGGEdgeToContextHenshinEdgeRule_PROTOCOL(), 
				getContextTGGNodeToContextHenshinNodeIsland_FWD(),
				getContextTGGNodeToContextHenshinNodeIsland_BWD(),
				getContextTGGNodeToContextHenshinNodeIsland_PROTOCOL(), 
				getCreateTGGEdgeToCreateHenshinEdgeBridge_FWD(),
				getCreateTGGEdgeToCreateHenshinEdgeBridge_BWD(),
				getCreateTGGEdgeToCreateHenshinEdgeBridge_PROTOCOL(), 
				getAttributeAssignmentToSetAttributeIsland_FWD(),
				getAttributeAssignmentToSetAttributeIsland_BWD(),
				getAttributeAssignmentToSetAttributeIsland_PROTOCOL(), 
				getContextTGGEdgeToContextHenshinEdgeIsland_FWD(),
				getContextTGGEdgeToContextHenshinEdgeIsland_BWD(),
				getContextTGGEdgeToContextHenshinEdgeIsland_PROTOCOL(), 
				getCreateTGGNodeToCreateHenshinNodeBridge_FWD(),
				getCreateTGGNodeToCreateHenshinNodeBridge_BWD(),
				getCreateTGGNodeToCreateHenshinNodeBridge_PROTOCOL(), 
				getAttributeAssignmentToSetAttributeBridge_FWD(),
				getAttributeAssignmentToSetAttributeBridge_BWD(),
				getAttributeAssignmentToSetAttributeBridge_PROTOCOL(), 
				getTGGRuleToHenshinRuleIsland_FWD(),
				getTGGRuleToHenshinRuleIsland_BWD(),
				getTGGRuleToHenshinRuleIsland_PROTOCOL(), 
				getTGGRuleToHenshinRuleBridge_FWD(),
				getTGGRuleToHenshinRuleBridge_BWD(),
				getTGGRuleToHenshinRuleBridge_PROTOCOL()
				)
		}
		
		private def get_MODELGEN(){
			new EventDrivenTransformationRuleGroup(
				getContextTGGNodeToContextHenshinNodeBridge_MODELGEN(), 
				getTGGToModuleIsland_MODELGEN(), 
				getCreateTGGEdgeToCreateHenshinEdgeIsland_MODELGEN(), 
				getContextTGGNodeToCreateHenshinNodeRulePlain_MODELGEN(), 
				getTGGEdgetoCreateHenshinEdgeRule_MODELGEN(), 
				getCreateTGGNodeToCreateHenshinNodeIsland_MODELGEN(), 
				getContextTGGEdgeToContextHenshinEdgeBridge_MODELGEN(), 
				getTGGEdgeToContextHenshinEdgeRule_MODELGEN(), 
				getContextTGGNodeToContextHenshinNodeIsland_MODELGEN(), 
				getCreateTGGEdgeToCreateHenshinEdgeBridge_MODELGEN(), 
				getAttributeAssignmentToSetAttributeIsland_MODELGEN(), 
				getContextTGGEdgeToContextHenshinEdgeIsland_MODELGEN(), 
				getCreateTGGNodeToCreateHenshinNodeBridge_MODELGEN(), 
				getAttributeAssignmentToSetAttributeBridge_MODELGEN(), 
				getTGGRuleToHenshinRuleIsland_MODELGEN(), 
				getTGGRuleToHenshinRuleBridge_MODELGEN()
			 )
		}
		
		private def get_CC(){
			new EventDrivenTransformationRuleGroup(
				getContextTGGNodeToContextHenshinNodeBridge_CC(), 
				getTGGToModuleIsland_CC(), 
				getCreateTGGEdgeToCreateHenshinEdgeIsland_CC(), 
				getContextTGGNodeToCreateHenshinNodeRulePlain_CC(), 
				getTGGEdgetoCreateHenshinEdgeRule_CC(), 
				getCreateTGGNodeToCreateHenshinNodeIsland_CC(), 
				getContextTGGEdgeToContextHenshinEdgeBridge_CC(), 
				getTGGEdgeToContextHenshinEdgeRule_CC(), 
				getContextTGGNodeToContextHenshinNodeIsland_CC(), 
				getCreateTGGEdgeToCreateHenshinEdgeBridge_CC(), 
				getAttributeAssignmentToSetAttributeIsland_CC(), 
				getContextTGGEdgeToContextHenshinEdgeIsland_CC(), 
				getCreateTGGNodeToCreateHenshinNodeBridge_CC(), 
				getAttributeAssignmentToSetAttributeBridge_CC(), 
				getTGGRuleToHenshinRuleIsland_CC(), 
				getTGGRuleToHenshinRuleBridge_CC()
			)
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
		private def getCreateTGGEdgeToCreateHenshinEdgeIsland_FWD() {
			createRule.name("CreateTGGEdgeToCreateHenshinEdgeIsland_FWD").precondition(CreateTGGEdgeToCreateHenshinEdgeIsland_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGEdgeToCreateHenshinEdgeIsland", it)
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
		private def getContextTGGEdgeToContextHenshinEdgeBridge_FWD() {
			createRule.name("ContextTGGEdgeToContextHenshinEdgeBridge_FWD").precondition(ContextTGGEdgeToContextHenshinEdgeBridge_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGEdgeToContextHenshinEdgeBridge", it)
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
		private def getCreateTGGEdgeToCreateHenshinEdgeBridge_FWD() {
			createRule.name("CreateTGGEdgeToCreateHenshinEdgeBridge_FWD").precondition(CreateTGGEdgeToCreateHenshinEdgeBridge_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGEdgeToCreateHenshinEdgeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getAttributeAssignmentToSetAttributeIsland_FWD() {
			createRule.name("AttributeAssignmentToSetAttributeIsland_FWD").precondition(AttributeAssignmentToSetAttributeIsland_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("AttributeAssignmentToSetAttributeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGEdgeToContextHenshinEdgeIsland_FWD() {
			createRule.name("ContextTGGEdgeToContextHenshinEdgeIsland_FWD").precondition(ContextTGGEdgeToContextHenshinEdgeIsland_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGEdgeToContextHenshinEdgeIsland", it)
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
		private def getAttributeAssignmentToSetAttributeBridge_FWD() {
			createRule.name("AttributeAssignmentToSetAttributeBridge_FWD").precondition(AttributeAssignmentToSetAttributeBridge_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("AttributeAssignmentToSetAttributeBridge", it)
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
		
		private def getContextTGGNodeToContextHenshinNodeBridge_PROTOCOL() {
			createRule.name("ContextTGGNodeToContextHenshinNodeBridge_PROTOCOL").precondition(ContextTGGNodeToContextHenshinNodeBridge_PROTOCOLMatcher.querySpecification).action(
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
		private def getCreateTGGEdgeToCreateHenshinEdgeIsland_PROTOCOL() {
			createRule.name("CreateTGGEdgeToCreateHenshinEdgeIsland_PROTOCOL").precondition(CreateTGGEdgeToCreateHenshinEdgeIsland_PROTOCOLMatcher.querySpecification).action(
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
		private def getTGGEdgetoCreateHenshinEdgeRule_PROTOCOL() {
			createRule.name("TGGEdgetoCreateHenshinEdgeRule_PROTOCOL").precondition(TGGEdgetoCreateHenshinEdgeRule_PROTOCOLMatcher.querySpecification).action(
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
		private def getContextTGGEdgeToContextHenshinEdgeBridge_PROTOCOL() {
			createRule.name("ContextTGGEdgeToContextHenshinEdgeBridge_PROTOCOL").precondition(ContextTGGEdgeToContextHenshinEdgeBridge_PROTOCOLMatcher.querySpecification).action(
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
		private def getContextTGGNodeToContextHenshinNodeIsland_PROTOCOL() {
			createRule.name("ContextTGGNodeToContextHenshinNodeIsland_PROTOCOL").precondition(ContextTGGNodeToContextHenshinNodeIsland_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getCreateTGGEdgeToCreateHenshinEdgeBridge_PROTOCOL() {
			createRule.name("CreateTGGEdgeToCreateHenshinEdgeBridge_PROTOCOL").precondition(CreateTGGEdgeToCreateHenshinEdgeBridge_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getAttributeAssignmentToSetAttributeIsland_PROTOCOL() {
			createRule.name("AttributeAssignmentToSetAttributeIsland_PROTOCOL").precondition(AttributeAssignmentToSetAttributeIsland_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGEdgeToContextHenshinEdgeIsland_PROTOCOL() {
			createRule.name("ContextTGGEdgeToContextHenshinEdgeIsland_PROTOCOL").precondition(ContextTGGEdgeToContextHenshinEdgeIsland_PROTOCOLMatcher.querySpecification).action(
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
		private def getAttributeAssignmentToSetAttributeBridge_PROTOCOL() {
			createRule.name("AttributeAssignmentToSetAttributeBridge_PROTOCOL").precondition(AttributeAssignmentToSetAttributeBridge_PROTOCOLMatcher.querySpecification).action(
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
		private def getTGGRuleToHenshinRuleBridge_PROTOCOL() {
			createRule.name("TGGRuleToHenshinRuleBridge_PROTOCOL").precondition(TGGRuleToHenshinRuleBridge_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
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
		private def getCreateTGGEdgeToCreateHenshinEdgeIsland_MODELGEN() {
			createRule.name("CreateTGGEdgeToCreateHenshinEdgeIsland_MODELGEN").precondition(CreateTGGEdgeToCreateHenshinEdgeIsland_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGEdgeToCreateHenshinEdgeIsland", it)
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
		private def getContextTGGEdgeToContextHenshinEdgeBridge_MODELGEN() {
			createRule.name("ContextTGGEdgeToContextHenshinEdgeBridge_MODELGEN").precondition(ContextTGGEdgeToContextHenshinEdgeBridge_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGEdgeToContextHenshinEdgeBridge", it)
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
		private def getCreateTGGEdgeToCreateHenshinEdgeBridge_MODELGEN() {
			createRule.name("CreateTGGEdgeToCreateHenshinEdgeBridge_MODELGEN").precondition(CreateTGGEdgeToCreateHenshinEdgeBridge_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGEdgeToCreateHenshinEdgeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getAttributeAssignmentToSetAttributeIsland_MODELGEN() {
			createRule.name("AttributeAssignmentToSetAttributeIsland_MODELGEN").precondition(AttributeAssignmentToSetAttributeIsland_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("AttributeAssignmentToSetAttributeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGEdgeToContextHenshinEdgeIsland_MODELGEN() {
			createRule.name("ContextTGGEdgeToContextHenshinEdgeIsland_MODELGEN").precondition(ContextTGGEdgeToContextHenshinEdgeIsland_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGEdgeToContextHenshinEdgeIsland", it)
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
		private def getAttributeAssignmentToSetAttributeBridge_MODELGEN() {
			createRule.name("AttributeAssignmentToSetAttributeBridge_MODELGEN").precondition(AttributeAssignmentToSetAttributeBridge_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("AttributeAssignmentToSetAttributeBridge", it)
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
		private def getCreateTGGEdgeToCreateHenshinEdgeIsland_BWD() {
			createRule.name("CreateTGGEdgeToCreateHenshinEdgeIsland_BWD").precondition(CreateTGGEdgeToCreateHenshinEdgeIsland_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGEdgeToCreateHenshinEdgeIsland", it)
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
		private def getContextTGGEdgeToContextHenshinEdgeBridge_BWD() {
			createRule.name("ContextTGGEdgeToContextHenshinEdgeBridge_BWD").precondition(ContextTGGEdgeToContextHenshinEdgeBridge_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGEdgeToContextHenshinEdgeBridge", it)
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
		private def getCreateTGGEdgeToCreateHenshinEdgeBridge_BWD() {
			createRule.name("CreateTGGEdgeToCreateHenshinEdgeBridge_BWD").precondition(CreateTGGEdgeToCreateHenshinEdgeBridge_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGEdgeToCreateHenshinEdgeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getAttributeAssignmentToSetAttributeIsland_BWD() {
			createRule.name("AttributeAssignmentToSetAttributeIsland_BWD").precondition(AttributeAssignmentToSetAttributeIsland_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("AttributeAssignmentToSetAttributeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGEdgeToContextHenshinEdgeIsland_BWD() {
			createRule.name("ContextTGGEdgeToContextHenshinEdgeIsland_BWD").precondition(ContextTGGEdgeToContextHenshinEdgeIsland_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGEdgeToContextHenshinEdgeIsland", it)
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
		private def getAttributeAssignmentToSetAttributeBridge_BWD() {
			createRule.name("AttributeAssignmentToSetAttributeBridge_BWD").precondition(AttributeAssignmentToSetAttributeBridge_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("AttributeAssignmentToSetAttributeBridge", it)
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
		private def getCreateTGGEdgeToCreateHenshinEdgeIsland_CC() {
			createRule.name("CreateTGGEdgeToCreateHenshinEdgeIsland_CC").precondition(CreateTGGEdgeToCreateHenshinEdgeIsland_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGEdgeToCreateHenshinEdgeIsland", it)
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
		private def getContextTGGEdgeToContextHenshinEdgeBridge_CC() {
			createRule.name("ContextTGGEdgeToContextHenshinEdgeBridge_CC").precondition(ContextTGGEdgeToContextHenshinEdgeBridge_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGEdgeToContextHenshinEdgeBridge", it)
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
		private def getCreateTGGEdgeToCreateHenshinEdgeBridge_CC() {
			createRule.name("CreateTGGEdgeToCreateHenshinEdgeBridge_CC").precondition(CreateTGGEdgeToCreateHenshinEdgeBridge_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("CreateTGGEdgeToCreateHenshinEdgeBridge", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getAttributeAssignmentToSetAttributeIsland_CC() {
			createRule.name("AttributeAssignmentToSetAttributeIsland_CC").precondition(AttributeAssignmentToSetAttributeIsland_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("AttributeAssignmentToSetAttributeIsland", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getContextTGGEdgeToContextHenshinEdgeIsland_CC() {
			createRule.name("ContextTGGEdgeToContextHenshinEdgeIsland_CC").precondition(ContextTGGEdgeToContextHenshinEdgeIsland_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ContextTGGEdgeToContextHenshinEdgeIsland", it)
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
		private def getAttributeAssignmentToSetAttributeBridge_CC() {
			createRule.name("AttributeAssignmentToSetAttributeBridge_CC").precondition(AttributeAssignmentToSetAttributeBridge_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("AttributeAssignmentToSetAttributeBridge", it)
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
		
	}		
	
