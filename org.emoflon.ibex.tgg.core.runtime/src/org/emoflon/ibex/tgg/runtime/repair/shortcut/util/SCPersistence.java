package org.emoflon.ibex.tgg.runtime.repair.shortcut.util;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.RuntimeShortcutRule;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;

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
		oscFWDResource.getContents().add(oscRule.getOperationalizedSCR().getShortcutRule());
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
		oscBWDResource.getContents().add(oscRule.getOperationalizedSCR().getShortcutRule());
	}

	public void saveSCRules(Collection<RuntimeShortcutRule> scRule) {
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

	private void save(RuntimeShortcutRule scRule) {
		if (scRule.getOriginalRule() instanceof HigherOrderTGGRule hoOriginalRule)
			save(hoOriginalRule);
		if (scRule.getReplacingRule() instanceof HigherOrderTGGRule hoReplacingRule)
			save(hoReplacingRule);

		scResource.getContents().add(scRule.getShortcutRule());
	}

	private void save(HigherOrderTGGRule hoRule) {
		if (higherOrderResource == null)
			higherOrderResource = hoResCreator.get();

		higherOrderResource.getContents().add(hoRule);
	}

}
