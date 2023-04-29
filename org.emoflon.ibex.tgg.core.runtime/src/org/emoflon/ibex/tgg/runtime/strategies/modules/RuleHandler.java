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

	public void registerRule(TGGRule rule) {
		name2rule.put(rule.getName(), rule);
	}
	
	public void registerOperationalRule(TGGOperationalRule operationalRule) {
		name2operationalRule.put(operationalRule.getName(), operationalRule);
	}

	public void registerRules(TGGModel model) {
		for (var tggRule : model.getRuleSet().getRules()) {
			registerRule(tggRule);
			for (var operationRule : tggRule.getOperationalisations())
				registerOperationalRule(operationRule);
		}
	}

	public TGGRule getRule(String name) {
		return name2rule.get(name);
	}

	public TGGOperationalRule getOperationalRule(String name) {
		return name2operationalRule.get(name);
	}

	public TGGOperationalRule getOperationalRule(String name, OperationalisationMode operationalization) {
		TGGRule rule = null;
		
		if(name2operationalRule.containsKey(name)) {
			var operationalRule = name2operationalRule.get(name);
			if(operationalRule.getOperationalisationMode() == operationalization)
				return operationalRule;
			
			rule = operationalRule.getTggRule();
		}
		
		if(rule == null)
			rule = getRule(name);
		
		for (var operationalRule : rule.getOperationalisations()) {
			if (operationalRule.getOperationalisationMode() == operationalization)
				return operationalRule;
		}
		return null;
	}
}
