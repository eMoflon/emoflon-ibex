
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
	import org.emoflon.ibex.tgg.operational.csp.constraints.factories.CDToDocAttrCondDefLibrary
	
	
	import org.emoflon.ibex.tgg.axiom.Axiom_FWDMatcher
	import org.emoflon.ibex.tgg.axiom.Axiom_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.axiom.Axiom_MODELGENMatcher
	import org.emoflon.ibex.tgg.axiom.Axiom_BWDMatcher
	import org.emoflon.ibex.tgg.axiom.Axiom_CCMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_FWDMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_BWDMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationrule.ClazzToDocAmalgamationRule_CCMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivemultirule.ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirectbackwardmultirule.ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher
	import org.emoflon.ibex.tgg.clazztodocamalgamationdirecttransitivebackwardmultirule.ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher
	import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_FWDMatcher
	import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_BWDMatcher
	import org.emoflon.ibex.tgg.clazztodocfilerule.ClazzToDocFileRule_CCMatcher
	import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_FWDMatcher
	import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_MODELGENMatcher
	import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_BWDMatcher
	import org.emoflon.ibex.tgg.clazztofilerule.ClazzToFileRule_CCMatcher
	import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_FWDMatcher
	import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_PROTOCOLMatcher
	import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_MODELGENMatcher
	import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_BWDMatcher
	import org.emoflon.ibex.tgg.packagetofolderrule_recursive.PackageToFolderRule_Recursive_CCMatcher
	
	class CDToDocTransformation{	
		
		
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
			tggRuntimeUtil.getCSPProvider().registerFactory(new CDToDocAttrCondDefLibrary());
			
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
				getAxiom_FWD(),
				getAxiom_BWD(),
				getAxiom_PROTOCOL(), 
				getClazzToDocAmalgamationRule_FWD(),
				getClazzToDocAmalgamationRule_BWD(),
				getClazzToDocAmalgamationRule_PROTOCOL(), 
				getClazzToDocAmalgamationDirectTransitiveMultiRule_FWD(),
				getClazzToDocAmalgamationDirectTransitiveMultiRule_BWD(),
				getClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOL(), 
				getClazzToDocAmalgamationDirectBackwardMultiRule_FWD(),
				getClazzToDocAmalgamationDirectBackwardMultiRule_BWD(),
				getClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOL(), 
				getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWD(),
				getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWD(),
				getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOL(), 
				getClazzToDocFileRule_FWD(),
				getClazzToDocFileRule_BWD(),
				getClazzToDocFileRule_PROTOCOL(), 
				getClazzToFileRule_FWD(),
				getClazzToFileRule_BWD(),
				getClazzToFileRule_PROTOCOL(), 
				getPackageToFolderRule_Recursive_FWD(),
				getPackageToFolderRule_Recursive_BWD(),
				getPackageToFolderRule_Recursive_PROTOCOL()
				)
		}
		
		private def get_MODELGEN(){
			new EventDrivenTransformationRuleGroup(
				getAxiom_MODELGEN(), 
				getClazzToDocAmalgamationRule_MODELGEN(), 
				getClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGEN(), 
				getClazzToDocAmalgamationDirectBackwardMultiRule_MODELGEN(), 
				getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGEN(), 
				getClazzToDocFileRule_MODELGEN(), 
				getClazzToFileRule_MODELGEN(), 
				getPackageToFolderRule_Recursive_MODELGEN()
			 )
		}
		
		private def get_CC(){
			new EventDrivenTransformationRuleGroup(
				getAxiom_CC(), 
				getClazzToDocAmalgamationRule_CC(), 
				getClazzToDocAmalgamationDirectTransitiveMultiRule_CC(), 
				getClazzToDocAmalgamationDirectBackwardMultiRule_CC(), 
				getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CC(), 
				getClazzToDocFileRule_CC(), 
				getClazzToFileRule_CC(), 
				getPackageToFolderRule_Recursive_CC()
			)
		}
			
		
		private def getAxiom_FWD() {
			createRule.name("Axiom_FWD").precondition(Axiom_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("Axiom", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationRule_FWD() {
			createRule.name("ClazzToDocAmalgamationRule_FWD").precondition(ClazzToDocAmalgamationRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectTransitiveMultiRule_FWD() {
			createRule.name("ClazzToDocAmalgamationDirectTransitiveMultiRule_FWD").precondition(ClazzToDocAmalgamationDirectTransitiveMultiRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveMultiRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectBackwardMultiRule_FWD() {
			createRule.name("ClazzToDocAmalgamationDirectBackwardMultiRule_FWD").precondition(ClazzToDocAmalgamationDirectBackwardMultiRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectBackwardMultiRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWD() {
			createRule.name("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWD").precondition(ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocFileRule_FWD() {
			createRule.name("ClazzToDocFileRule_FWD").precondition(ClazzToDocFileRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocFileRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToFileRule_FWD() {
			createRule.name("ClazzToFileRule_FWD").precondition(ClazzToFileRule_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToFileRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getPackageToFolderRule_Recursive_FWD() {
			createRule.name("PackageToFolderRule_Recursive_FWD").precondition(PackageToFolderRule_Recursive_FWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("PackageToFolderRule_Recursive", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getAxiom_PROTOCOL() {
			createRule.name("Axiom_PROTOCOL").precondition(Axiom_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationRule_PROTOCOL() {
			createRule.name("ClazzToDocAmalgamationRule_PROTOCOL").precondition(ClazzToDocAmalgamationRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOL() {
			createRule.name("ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOL").precondition(ClazzToDocAmalgamationDirectTransitiveMultiRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOL() {
			createRule.name("ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOL").precondition(ClazzToDocAmalgamationDirectBackwardMultiRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOL() {
			createRule.name("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOL").precondition(ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocFileRule_PROTOCOL() {
			createRule.name("ClazzToDocFileRule_PROTOCOL").precondition(ClazzToDocFileRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToFileRule_PROTOCOL() {
			createRule.name("ClazzToFileRule_PROTOCOL").precondition(ClazzToFileRule_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getPackageToFolderRule_Recursive_PROTOCOL() {
			createRule.name("PackageToFolderRule_Recursive_PROTOCOL").precondition(PackageToFolderRule_Recursive_PROTOCOLMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) []
				.action(CRUDActivationStateEnum.DELETED)[
				tggRuntimeUtil.addBrokenMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getAxiom_MODELGEN() {
			createRule.name("Axiom_MODELGEN").precondition(Axiom_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("Axiom", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationRule_MODELGEN() {
			createRule.name("ClazzToDocAmalgamationRule_MODELGEN").precondition(ClazzToDocAmalgamationRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGEN() {
			createRule.name("ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGEN").precondition(ClazzToDocAmalgamationDirectTransitiveMultiRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveMultiRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectBackwardMultiRule_MODELGEN() {
			createRule.name("ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGEN").precondition(ClazzToDocAmalgamationDirectBackwardMultiRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectBackwardMultiRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGEN() {
			createRule.name("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGEN").precondition(ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocFileRule_MODELGEN() {
			createRule.name("ClazzToDocFileRule_MODELGEN").precondition(ClazzToDocFileRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocFileRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToFileRule_MODELGEN() {
			createRule.name("ClazzToFileRule_MODELGEN").precondition(ClazzToFileRule_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToFileRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getPackageToFolderRule_Recursive_MODELGEN() {
			createRule.name("PackageToFolderRule_Recursive_MODELGEN").precondition(PackageToFolderRule_Recursive_MODELGENMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("PackageToFolderRule_Recursive", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getAxiom_BWD() {
			createRule.name("Axiom_BWD").precondition(Axiom_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("Axiom", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationRule_BWD() {
			createRule.name("ClazzToDocAmalgamationRule_BWD").precondition(ClazzToDocAmalgamationRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectTransitiveMultiRule_BWD() {
			createRule.name("ClazzToDocAmalgamationDirectTransitiveMultiRule_BWD").precondition(ClazzToDocAmalgamationDirectTransitiveMultiRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveMultiRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectBackwardMultiRule_BWD() {
			createRule.name("ClazzToDocAmalgamationDirectBackwardMultiRule_BWD").precondition(ClazzToDocAmalgamationDirectBackwardMultiRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectBackwardMultiRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWD() {
			createRule.name("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWD").precondition(ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocFileRule_BWD() {
			createRule.name("ClazzToDocFileRule_BWD").precondition(ClazzToDocFileRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocFileRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToFileRule_BWD() {
			createRule.name("ClazzToFileRule_BWD").precondition(ClazzToFileRule_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToFileRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getPackageToFolderRule_Recursive_BWD() {
			createRule.name("PackageToFolderRule_Recursive_BWD").precondition(PackageToFolderRule_Recursive_BWDMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("PackageToFolderRule_Recursive", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
		private def getAxiom_CC() {
			createRule.name("Axiom_CC").precondition(Axiom_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("Axiom", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationRule_CC() {
			createRule.name("ClazzToDocAmalgamationRule_CC").precondition(ClazzToDocAmalgamationRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectTransitiveMultiRule_CC() {
			createRule.name("ClazzToDocAmalgamationDirectTransitiveMultiRule_CC").precondition(ClazzToDocAmalgamationDirectTransitiveMultiRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveMultiRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectBackwardMultiRule_CC() {
			createRule.name("ClazzToDocAmalgamationDirectBackwardMultiRule_CC").precondition(ClazzToDocAmalgamationDirectBackwardMultiRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectBackwardMultiRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CC() {
			createRule.name("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CC").precondition(ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocAmalgamationDirectTransitiveBackwardMultiRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToDocFileRule_CC() {
			createRule.name("ClazzToDocFileRule_CC").precondition(ClazzToDocFileRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToDocFileRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getClazzToFileRule_CC() {
			createRule.name("ClazzToFileRule_CC").precondition(ClazzToFileRule_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("ClazzToFileRule", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		private def getPackageToFolderRule_Recursive_CC() {
			createRule.name("PackageToFolderRule_Recursive_CC").precondition(PackageToFolderRule_Recursive_CCMatcher.querySpecification).action(
				CRUDActivationStateEnum.CREATED) [
				         tggRuntimeUtil.addOperationalRuleMatch("PackageToFolderRule_Recursive", it)
				].action(CRUDActivationStateEnum.DELETED)[
				         tggRuntimeUtil.removeOperationalRuleMatch(it)]
			.addLifeCycle(				
				Lifecycles.getDefault(false, true)
				).build
		}
		
	}		
	
