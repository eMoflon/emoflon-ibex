package org.emoflon.ibex.tgg.runtime.strategies.modules;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

public class RuleHandler {
	private Map<String, TGGRule> name2rule = new HashMap<>();
	private Map<String, TGGOperationalRule> name2operationalRule = new HashMap<>();

	public void registerRules(TGGModel model) {
		for (var tggRule : model.getRuleSet().getRules()) {
			name2rule.put(tggRule.getName(), tggRule);
			for (var operationRule : tggRule.getOperationalisations()) {
				name2operationalRule.put(operationRule.getName(), operationRule);
			}
		}
	}

	public TGGRule getRule(String name) {
		return name2rule.get(name);
	}

	public TGGOperationalRule getOperationalRule(String name) {
		return name2operationalRule.get(name);
	}

	public TGGOperationalRule getOperationalRule(String name, OperationalisationMode operationalization) {
		for (var operationalRule : getRule(name).getOperationalisations()) {
			if (operationalRule.getOperationalisationMode() == operationalization)
				return operationalRule;
		}
		return null;
	}
}
