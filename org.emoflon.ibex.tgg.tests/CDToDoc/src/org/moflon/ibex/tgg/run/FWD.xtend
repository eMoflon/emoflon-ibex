package org.moflon.ibex.tgg.run

import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine
import org.eclipse.viatra.query.runtime.emf.EMFScope
import org.eclipse.viatra.transformation.runtime.emf.modelmanipulation.IModelManipulations
import org.eclipse.viatra.transformation.runtime.emf.rules.eventdriven.EventDrivenTransformationRuleFactory
import org.eclipse.viatra.transformation.runtime.emf.transformation.eventdriven.EventDrivenTransformation
import org.eclipse.viatra.transformation.runtime.emf.modelmanipulation.SimpleModelManipulations
import org.eclipse.emf.ecore.resource.Resource
import org.emoflon.ibex.tgg.operational.RuleInvocationUtil
import org.eclipse.emf.ecore.resource.ResourceSet
import org.emoflon.ibex.tgg.axiom.Axiom_FWD_ProtocolNacsMatcher

import org.eclipse.viatra.transformation.evm.specific.Lifecycles
import org.eclipse.viatra.transformation.evm.specific.crud.CRUDActivationStateEnum

class FWD {

    /* Transformation-related extensions */
    extension EventDrivenTransformation transformation
    
    /* Transformation rule-related extensions */
    extension EventDrivenTransformationRuleFactory = new EventDrivenTransformationRuleFactory
    extension IModelManipulations manipulation

    protected ViatraQueryEngine engine
    protected Resource resource
    
    private RuleInvocationUtil ruleInvocationUtil;
    //protected EventDrivenTransformationRule<?,?> exampleRule

    new(ResourceSet set, RuleInvocationUtil ruleInvocationUtil) {
        this.resource = resource
        this.ruleInvocationUtil = ruleInvocationUtil
        // Create EMF scope and EMF IncQuery engine based on the resource
        val scope = new EMFScope(set)
        engine = ViatraQueryEngine.on(scope);
        
        createTransformation

    }

    public def execute() {
        transformation.executionSchema.startUnscheduledExecution
    }

    private def createTransformation() {
        //Initialize model manipulation API
        this.manipulation = new SimpleModelManipulations(engine)
        this.ruleInvocationUtil.setModelManipulation(this.manipulation)
        //Initialize event-driven transformation
        transformation = EventDrivenTransformation.forEngine(engine)
            .addRule(getAxiom())
            .build
    }
    
  private def getAxiom() {
       createRule.name("Axiom").precondition(Axiom_FWD_ProtocolNacsMatcher.querySpecification).
       action(CRUDActivationStateEnum.CREATED) [
            	ruleInvocationUtil.applyFWD("Axiom", it)
       ].action(CRUDActivationStateEnum.UPDATED) [
       ].action(CRUDActivationStateEnum.DELETED) [
       ].addLifeCycle(Lifecycles.getDefault(true, true)).build
  }

  def dispose() {
        if (transformation != null) {
            transformation.dispose
        }
        transformation = null
        return
  }
}
