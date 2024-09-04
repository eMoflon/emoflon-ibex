package org.emoflon.ibex.tgg.runtime.strategies.integrate.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;

public class EltFilter {

	List<DomainType> domainTypes = Arrays.asList(DomainType.SOURCE, DomainType.CORRESPONDENCE, DomainType.TARGET);
	List<BindingType> bindingTypes = Arrays.asList(BindingType.CONTEXT, BindingType.CREATE);

	boolean all = true;
	boolean deleted;

	public EltFilter domains(DomainType... domainTypes) {
		this.domainTypes = Arrays.asList(domainTypes);
		return this;
	}

	public EltFilter src() {
		this.domainTypes = Arrays.asList(DomainType.SOURCE);
		return this;
	}

	public EltFilter trg() {
		this.domainTypes = Arrays.asList(DomainType.TARGET);
		return this;
	}

	public EltFilter corr() {
		this.domainTypes = Arrays.asList(DomainType.CORRESPONDENCE);
		return this;
	}

	public EltFilter srcAndTrg() {
		this.domainTypes = Arrays.asList(DomainType.SOURCE, DomainType.TARGET);
		return this;
	}

	public EltFilter bindings(BindingType... bindingTypes) {
		this.bindingTypes = Arrays.asList(bindingTypes);
		return this;
	}

	public EltFilter create() {
		this.bindingTypes = Arrays.asList(BindingType.CREATE);
		return this;
	}

	public EltFilter context() {
		this.bindingTypes = Arrays.asList(BindingType.CONTEXT);
		return this;
	}

	public EltFilter deleted() {
		this.all = false;
		this.deleted = true;
		return this;
	}

	public EltFilter notDeleted() {
		this.all = false;
		this.deleted = false;
		return this;
	}
	
	public EltFilter copy() {
		EltFilter copy = new EltFilter();
		copy.domainTypes = new LinkedList<>(this.domainTypes);
		copy.bindingTypes = new LinkedList<>(this.bindingTypes);
		copy.all = this.all;
		copy.deleted = this.deleted;
		return copy;
	}
}