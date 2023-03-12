package org.emoflon.ibex.tgg.runtime.strategies.integrate.classification;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;

public class DeletionPattern {

	private List<DomainModification> serialized;
	private final int initMapCapacity = 8;

	private Map<DomainType, Map<BindingType, DomainModification>> pattern = new HashMap<>(initMapCapacity);

	public DeletionPattern(DomainModification initialMod) {
		pattern.put(DomainType.SOURCE, new HashMap<>(initMapCapacity));
		pattern.put(DomainType.CORRESPONDENCE, new HashMap<>(initMapCapacity));
		pattern.put(DomainType.TARGET, new HashMap<>(initMapCapacity));
		pattern.forEach((domain, domainMap) -> {
			domainMap.put(BindingType.CONTEXT, initialMod);
			domainMap.put(BindingType.CREATE, initialMod);
		});
	}

	/**
	 * @param srcCre  Modification of green part in source domain
	 * @param srcCon  Modification of black part in source domain
	 * @param corrCre Modification of green part in correspondence domain
	 * @param corrCon Modification of black part in correspondence domain
	 * @param trgCre  Modification of green part in target domain
	 * @param trgCon  Modification of black part in target domain
	 */
	public DeletionPattern(DomainModification srcCre, DomainModification srcCon, //
			DomainModification corrCre, DomainModification corrCon, //
			DomainModification trgCre, DomainModification trgCon) {
		Map<BindingType, DomainModification> src = new HashMap<>(initMapCapacity);
		Map<BindingType, DomainModification> corr = new HashMap<>(initMapCapacity);
		Map<BindingType, DomainModification> trg = new HashMap<>(initMapCapacity);

		src.put(BindingType.CREATE, srcCre);
		src.put(BindingType.CONTEXT, srcCon);
		corr.put(BindingType.CREATE, corrCre);
		corr.put(BindingType.CONTEXT, corrCon);
		trg.put(BindingType.CREATE, trgCre);
		trg.put(BindingType.CONTEXT, trgCon);

		pattern.put(DomainType.SOURCE, src);
		pattern.put(DomainType.CORRESPONDENCE, corr);
		pattern.put(DomainType.TARGET, trg);
	}

	public void setModType(DomainType domain, BindingType binding, DomainModification mod) {
		pattern.get(domain).put(binding, mod);
	}

	public DomainModification getModType(DomainType domain, BindingType binding) {
		return pattern.get(domain).get(binding);
	}

	public boolean matches(DeletionPattern pattern) {
		List<DomainModification> thisPattern = this.serialize();
		List<DomainModification> otherPattern = pattern.serialize();

		if (thisPattern.size() != otherPattern.size())
			throw new RuntimeException("DeletionPattern matching: patterns must have the same size!");

		for (int i = 0; i < thisPattern.size(); i++) {
			DomainModification thisMod = thisPattern.get(i);
			DomainModification otherMod = otherPattern.get(i);
			if (!thisMod.covers(otherMod) && !otherMod.covers(thisMod))
				return false;
		}
		return true;
	}

	synchronized List<DomainModification> serialize() {
		if(serialized != null)
			return serialized;
		
		serialized = new LinkedList<>();

		List<DomainType> domains = Arrays.asList(DomainType.SOURCE, DomainType.CORRESPONDENCE, DomainType.TARGET);
		List<BindingType> bindings = Arrays.asList(BindingType.CREATE, BindingType.CONTEXT);
		domains.forEach(domain -> {
			bindings.forEach(binding -> {
				serialized.add(getModType(domain, binding));
			});
		});

		return serialized;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MatchModPattern [\n");
		builder.append("  " + print().replace("\n", "\n  "));
		builder.append("\n]");
		return builder.toString();
	}

	private String print() {
		StringBuilder builder = new StringBuilder();
		for (DomainType domain : pattern.keySet()) {
			builder.append(domain.getName() + "\n");
			for (BindingType binding : pattern.get(domain).keySet()) {
				builder.append("  " + binding.getName() + " = ");
				builder.append(pattern.get(domain).get(binding).toString() + "\n");
			}
		}
		return builder.substring(0, builder.length() - 1);
	}
}
