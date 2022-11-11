package org.emoflon.ibex.tgg.util.options;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.emoflon.ibex.util.config.IbexOptions;

import language.TGG;
import language.TGGRule;

public class TGGOptions extends IbexSubOptions {

	private TGG tgg;
	private TGG flattenedTGG;
	private EPackage corrMetamodel;

	public TGGOptions(IbexOptions options) {
		super(options);
	}

	public IbexOptions tgg(TGG tgg) {
		this.tgg = tgg;
		return options;
	}

	public TGG tgg() {
		return tgg;
	}

	public IbexOptions flattenedTgg(TGG flattenedTGG) {
		this.flattenedTGG = flattenedTGG;
		return options;
	}

	public TGG flattenedTGG() {
		return flattenedTGG;
	}

	public Collection<TGGRule> getFlattenedConcreteTGGRules() {
		return flattenedTGG.getRules() //
				.stream() //
				.filter(r -> !r.isAbstract()) //
				.collect(Collectors.toList());
	}

	public Collection<TGGRule> getConcreteTGGRules() {
		return tgg.getRules() //
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

}
