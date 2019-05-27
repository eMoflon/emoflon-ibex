package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import language.BindingType;
import language.DomainType;

public class MatchModPattern {

	private final int initMapCapacity = 8;

	private Map<DomainType, Map<BindingType, Modification>> pattern = new HashMap<>(initMapCapacity);

	public MatchModPattern(Modification initialMod) {
		pattern.put(DomainType.SRC, new HashMap<>(initMapCapacity));
		pattern.put(DomainType.TRG, new HashMap<>(initMapCapacity));
		pattern.put(DomainType.CORR, new HashMap<>(initMapCapacity));
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
	public MatchModPattern(Modification srcCre, Modification srcCon, //
			Modification corrCre, Modification corrCon, //
			Modification trgCre, Modification trgCon) {
		Map<BindingType, Modification> src = new HashMap<>(initMapCapacity);
		Map<BindingType, Modification> corr = new HashMap<>(initMapCapacity);
		Map<BindingType, Modification> trg = new HashMap<>(initMapCapacity);

		src.put(BindingType.CREATE, srcCre);
		src.put(BindingType.CONTEXT, srcCon);
		corr.put(BindingType.CREATE, corrCre);
		corr.put(BindingType.CONTEXT, corrCon);
		trg.put(BindingType.CREATE, trgCre);
		trg.put(BindingType.CONTEXT, trgCon);

		pattern.put(DomainType.SRC, src);
		pattern.put(DomainType.CORR, corr);
		pattern.put(DomainType.TRG, trg);
	}

	public void setModType(DomainType domain, BindingType binding, Modification mod) {
		pattern.get(domain).put(binding, mod);
	}

	public Modification getModType(DomainType domain, BindingType binding) {
		return pattern.get(domain).get(binding);
	}

	protected List<Modification> serialise() {
		List<Modification> result = new LinkedList<>();

		List<DomainType> domains = Arrays.asList(DomainType.SRC, DomainType.CORR, DomainType.TRG);
		List<BindingType> bindings = Arrays.asList(BindingType.CREATE, BindingType.CONTEXT);
		domains.forEach(domain -> {
			bindings.forEach(binding -> {
				result.add(getModType(domain, binding));
			});
		});

		return result;
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
