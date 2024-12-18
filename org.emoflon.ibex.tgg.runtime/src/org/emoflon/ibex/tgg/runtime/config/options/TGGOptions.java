package org.emoflon.ibex.tgg.runtime.config.options;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.emoflon.ibex.tgg.runtime.strategies.modules.RuleHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

public class TGGOptions extends IbexSubOptions {

	private TGGModel tgg;
	private EPackage corrMetamodel;
	private RuleHandler ruleHandler = new RuleHandler();
	
	public TGGOptions(IbexOptions options) {
		super(options);
	}

	public IbexOptions tgg(TGGModel tgg) {
		this.tgg = tgg;
		return options;
	}

	public TGGModel tgg() {
		return tgg;
	}

	public Collection<TGGRule> getConcreteTGGRules() {
		return tgg.getRuleSet().getRules() //
				.stream() //
				.filter(r -> !r.isAbstract()) //
				.collect(Collectors.toList());
	}

	public void corrMetamodel(EPackage pack) {
		this.corrMetamodel = pack;
	}

	public EPackage corrMetamodel() {
		return this.corrMetamodel;
	}
	
	public RuleHandler ruleHandler() {
		return ruleHandler;
	}
}
