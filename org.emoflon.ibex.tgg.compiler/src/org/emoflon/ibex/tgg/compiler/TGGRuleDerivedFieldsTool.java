package org.emoflon.ibex.tgg.compiler;

import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType.CONTEXT;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType.CREATE;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType.CORRESPONDENCE;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType.SOURCE;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType.TARGET;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByOperatorAndDomain;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByOperatorAndDomain;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.ruleIsAxiom;

import java.util.Collection;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGPattern;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

public class TGGRuleDerivedFieldsTool {
	public static void fillDerivedTGGRuleFields(TGGRule rule) {
		rule.setCreate(createRuleDelta(rule, new DomainBinding(SOURCE, CREATE),new DomainBinding(CORRESPONDENCE, CREATE), new DomainBinding(TARGET, CREATE) ));
		rule.setContext(createRuleDelta(rule, new DomainBinding(SOURCE, CONTEXT),new DomainBinding(CORRESPONDENCE, CONTEXT), new DomainBinding(TARGET, CONTEXT)));

		rule.setCreateSource(createRuleDelta(rule, new DomainBinding(SOURCE, CREATE)));
		rule.setCreateCorrespondence(createRuleDelta(rule, new DomainBinding(CORRESPONDENCE, CREATE)));
		rule.setCreateTarget(createRuleDelta(rule, new DomainBinding(TARGET, CREATE)));
		rule.setCreateSourceAndTarget(createRuleDelta(rule, new DomainBinding(SOURCE, CREATE), new DomainBinding(TARGET, CREATE)));
		
		rule.setContextSource(createRuleDelta(rule, new DomainBinding(SOURCE, CONTEXT)));
		rule.setContextCorrespondence(createRuleDelta(rule, new DomainBinding(CORRESPONDENCE, CONTEXT)));
		rule.setContextTarget(createRuleDelta(rule, new DomainBinding(TARGET, CONTEXT)));
		
		rule.setAxiom(ruleIsAxiom(rule));
	}

	public static void fillDerivedTGGOperationalRuleFields(TGGOperationalRule op) {
		switch(op.getOperationalisationMode()) {
		case FORWARD:
			op.setAlreadyMarked(createRuleDelta(op, new DomainBinding(SOURCE, CONTEXT)));
			op.setToBeMarked(createRuleDelta(op, new DomainBinding(SOURCE, CREATE)));
			break;
		case BACKWARD:
			op.setAlreadyMarked(createRuleDelta(op, new DomainBinding(TARGET, CONTEXT)));
			op.setToBeMarked(createRuleDelta(op, new DomainBinding(TARGET, CREATE)));
			break;
		case CONSISTENCY_CHECK:
			op.setAlreadyMarked(createRuleDelta(op, new DomainBinding(SOURCE, CONTEXT), new DomainBinding(TARGET, CONTEXT)));
			op.setToBeMarked(createRuleDelta(op, new DomainBinding(SOURCE, CREATE), new DomainBinding(TARGET, CREATE)));
			break;
		case CHECK_ONLY:
			// not sure if this is needed for consistency check
			op.setAlreadyMarked(createRuleDelta(op, new DomainBinding(SOURCE, CONTEXT), new DomainBinding(TARGET, CONTEXT)));
			op.setToBeMarked(createRuleDelta(op, new DomainBinding(SOURCE, CREATE), new DomainBinding(TARGET, CREATE)));
			break;
		// nothing to mark for the other operationalizations, whose created elements must be marked by forward, backward or consistency check
		default:
			op.setAlreadyMarked(IBeXCoreModelFactory.eINSTANCE.createIBeXRuleDelta());
			op.setToBeMarked(IBeXCoreModelFactory.eINSTANCE.createIBeXRuleDelta());
			break;
		
		}
	}
	
	public static IBeXRuleDelta createRuleDelta(TGGRule rule, DomainBinding... bindings) {
		var delta = IBeXCoreModelFactory.eINSTANCE.createIBeXRuleDelta();
		for(var binding : bindings) {
			delta.getNodes().addAll(getNodesByOperatorAndDomain(rule, binding.binding(), binding.domain()));
			delta.getEdges().addAll(getEdgesByOperatorAndDomain(rule, binding.binding(), binding.domain()));
		}
		delta.setEmpty(delta.getNodes().isEmpty());
		return delta;
	}
	
	public static void fillDerivedTGGRulePreCondition(TGGRule rule, Collection<BooleanExpression> attributeConditions,
			TGGAttributeConstraintSet attributeConstraints) {
		TGGPattern precondition = IBeXTGGModelFactory.eINSTANCE.createTGGPattern();
		precondition.getSignatureNodes().addAll(TGGModelUtils.getNodesByOperator(rule, CONTEXT));
		precondition.getEdges().addAll(TGGModelUtils.getEdgesByOperator(rule, CONTEXT));

		precondition.getConditions().addAll(attributeConditions);
		precondition.setAttributeConstraints(attributeConstraints);
	}
}

record DomainBinding(DomainType domain, BindingType binding) {	
}
