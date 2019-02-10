package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.OperationalShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.ShortcutRule;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.TGGRuleElement;
import language.repair.ExternalShortcutRule;
import language.repair.RepairFactory;
import language.repair.TGGRuleElementMapping;

public class SCPersistence {
	
	private IbexOptions options;
	
	private Resource scResource;
	private Resource oscFWDResource;
	private Resource oscBWDResource;
	
	public SCPersistence(OperationalStrategy strategy) {
		this.options = strategy.getOptions();
		
		scResource = strategy.createResource(options.projectPath() + "/model/" + options.projectName() + ".sc.tgg.xmi");
		oscFWDResource = strategy.createResource(options.projectPath() + "/model/" + options.projectName() + ".osc.fwd.tgg.xmi");
		oscBWDResource = strategy.createResource(options.projectPath() + "/model/" + options.projectName() + ".osc.bwd.tgg.xmi");
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
		ExternalShortcutRule esc = convertToEMF(oscRule.getScRule(), oscRule.getName());
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
		ExternalShortcutRule esc = convertToEMF(oscRule.getScRule(), oscRule.getName());
		oscBWDResource.getContents().add(esc);
	}

	public void saveSCRules(Collection<ShortcutRule> scRule) {
		scRule.forEach(this::save);
		try {
			scResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void save(ShortcutRule scRule) {
		ExternalShortcutRule esc = convertToEMF(scRule, scRule.getName());
		scResource.getContents().add(esc);
	}
	
	public ExternalShortcutRule convertToEMF(ShortcutRule scRule, String name) {
		ExternalShortcutRule esc = createESCRule(name);
		
		esc.setSourceRule(scRule.getSourceRule());
		esc.setTargetRule(scRule.getTargetRule());

		esc.getCreations().addAll(scRule.getOverlap().creations);
		esc.getDeletions().addAll(scRule.getOverlap().deletions);
		esc.getUnboundSrcContext().addAll(scRule.getOverlap().unboundSrcContext);
		esc.getUnboundTrgContext().addAll(scRule.getOverlap().unboundTrgContext);
		
		esc.getMapping().addAll(convertToEMF(scRule.getOverlap().mappings));
		
		return esc;
	}
	
	public Collection<TGGRuleElementMapping> convertToEMF(Map<TGGRuleElement, TGGRuleElement> mappings) {
		Collection<TGGRuleElementMapping> emfMappings = new ArrayList<>();
		for(TGGRuleElement elt : mappings.keySet()) {
			TGGRuleElement coElt = mappings.get(elt);
			
			TGGRuleElementMapping mapping = RepairFactory.eINSTANCE.createTGGRuleElementMapping();
			mapping.setSourceRuleElement(elt);
			mapping.setTargetRuleElement(coElt);
			emfMappings.add(mapping);
		}
		return emfMappings;
	}
	
	public ExternalShortcutRule createESCRule(String name) {
		ExternalShortcutRule esc =  RepairFactory.eINSTANCE.createExternalShortcutRule();
		esc.setName(name);
		return esc;
	}
}
