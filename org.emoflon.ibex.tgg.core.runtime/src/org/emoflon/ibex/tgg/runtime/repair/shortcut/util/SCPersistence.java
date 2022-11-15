package org.emoflon.ibex.tgg.runtime.repair.shortcut.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.util.config.IbexOptions;

import language.TGGRuleElement;
import language.repair.ExternalShortcutRule;
import language.repair.RepairFactory;
import language.repair.TGGRuleElementMapping;

public class SCPersistence {

	private final TGGResourceHandler resourceHandler;

	private final Resource scResource;
	private final Resource oscFWDResource;
	private final Resource oscBWDResource;

	private final Supplier<Resource> hoResCreator;
	private Resource higherOrderResource;

	public SCPersistence(IbexOptions options) {
		resourceHandler = options.resourceHandler();

		scResource = resourceHandler.createResource(resourceHandler.getSpecificationResourceSet(),
				options.project.path() + "/model/" + options.project.name() + ".sc.tgg.xmi");
		oscFWDResource = resourceHandler.createResource(resourceHandler.getSpecificationResourceSet(),
				options.project.path() + "/model/" + options.project.name() + ".osc.fwd.tgg.xmi");
		oscBWDResource = resourceHandler.createResource(resourceHandler.getSpecificationResourceSet(),
				options.project.path() + "/model/" + options.project.name() + ".osc.bwd.tgg.xmi");

		hoResCreator = () -> resourceHandler.createResource(resourceHandler.getSpecificationResourceSet(),
				options.project.path() + "/model/" + options.project.name() + ".ho.tgg.xmi");
	}

	public void saveOperationalFWDSCRules(Collection<OperationalShortcutRule> oscRule) {
		oscRule.forEach(this::saveFWDOSCRule);
		try {
			oscFWDResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveFWDOSCRule(OperationalShortcutRule oscRule) {
		ExternalShortcutRule esc = convertToEMF(oscRule.getOperationalizedSCR(), oscRule.getName());
		oscFWDResource.getContents().add(esc);
	}

	public void saveOperationalBWDSCRules(Collection<OperationalShortcutRule> oscRule) {
		oscRule.forEach(this::saveBWDOSCRule);
		try {
			oscBWDResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveBWDOSCRule(OperationalShortcutRule oscRule) {
		ExternalShortcutRule esc = convertToEMF(oscRule.getOperationalizedSCR(), oscRule.getName());
		oscBWDResource.getContents().add(esc);
	}

	public void saveSCRules(Collection<ShortcutRule> scRule) {
		scRule.forEach(this::save);
		try {
			scResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (higherOrderResource != null) {
			try {
				higherOrderResource.save(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void save(ShortcutRule scRule) {
		if (scRule.getOriginalRule() instanceof HigherOrderTGGRule hoOriginalRule)
			save(hoOriginalRule);
		if (scRule.getReplacingRule() instanceof HigherOrderTGGRule hoReplacingRule)
			save(hoReplacingRule);

		ExternalShortcutRule esc = convertToEMF(scRule, scRule.getName());
		scResource.getContents().add(esc);
	}

	private void save(HigherOrderTGGRule hoRule) {
		if (higherOrderResource == null)
			higherOrderResource = hoResCreator.get();

		higherOrderResource.getContents().add(hoRule);
	}

	public ExternalShortcutRule convertToEMF(ShortcutRule scRule, String name) {
		ExternalShortcutRule esc = createESCRule(name);

		esc.setSourceRule(scRule.getOriginalRule());
		esc.setTargetRule(scRule.getReplacingRule());

		esc.getCreations().addAll(scRule.getOverlap().creations);
		esc.getDeletions().addAll(scRule.getOverlap().deletions);
		esc.getUnboundSrcContext().addAll(scRule.getOverlap().unboundOriginalContext);
		esc.getUnboundTrgContext().addAll(scRule.getOverlap().unboundReplacingContext);

		esc.getMapping().addAll(convertToEMF(scRule.getOverlap().mappings));

		return esc;
	}

	public Collection<TGGRuleElementMapping> convertToEMF(Map<TGGRuleElement, TGGRuleElement> mappings) {
		Collection<TGGRuleElementMapping> emfMappings = new ArrayList<>();
		for (TGGRuleElement elt : mappings.keySet()) {
			TGGRuleElement coElt = mappings.get(elt);

			TGGRuleElementMapping mapping = RepairFactory.eINSTANCE.createTGGRuleElementMapping();
			mapping.setSourceRuleElement(elt);
			mapping.setTargetRuleElement(coElt);
			emfMappings.add(mapping);
		}
		return emfMappings;
	}

	public ExternalShortcutRule createESCRule(String name) {
		ExternalShortcutRule esc = RepairFactory.eINSTANCE.createExternalShortcutRule();
		esc.setName(name);
		return esc;
	}
}
