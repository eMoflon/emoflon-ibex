package org.emoflon.ibex.tgg.compiler;

import java.util.Collection;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType.CONTEXT;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType.CREATE;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType.DELETE;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType.NEGATIVE;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType.RELAXED;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType.SOURCE;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType.CORRESPONDENCE;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType.TARGET;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

import static org.emoflon.ibex.tgg.util.TGGModelUtils.*;

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
		fillDerivedTGGRuleFields(op);
		
		switch(op.getOperationalisationMode()) {
		case GENERATE:
			// nothing to mark for generate
			break;
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
		// nothing to mark for source and target, whose created elements must be marked by forward, backward or consistency check
		case SOURCE:
			break;
		case TARGET:
			break;
		default:
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
}

record DomainBinding(DomainType domain, BindingType binding) {
	
}
