package org.emoflon.ibex.tgg.runtime.repair.shortcut.util;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.RuntimeShortcutRule;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleSet;

public class SCPersistence {

	private final TGGResourceHandler resourceHandler;

	private final Resource scResource;
	private TGGModel scTggModel;
	private final Resource oscFWDResource;
	private final Resource oscBWDResource;

	private final Supplier<Resource> hoResCreator;
	private Resource higherOrderResource;

	public SCPersistence(IbexOptions options) {
		resourceHandler = options.resourceHandler();

		scResource = resourceHandler.createResource(resourceHandler.getSpecificationResourceSet(),
				options.project.path() + "/model/" + options.project.name() + ".sc.tgg.xmi");
		scTggModel = initContainer(scResource);
		oscFWDResource = resourceHandler.createResource(resourceHandler.getSpecificationResourceSet(),
				options.project.path() + "/model/" + options.project.name() + ".osc.fwd.tgg.xmi");
		oscBWDResource = resourceHandler.createResource(resourceHandler.getSpecificationResourceSet(),
				options.project.path() + "/model/" + options.project.name() + ".osc.bwd.tgg.xmi");

		hoResCreator = () -> {
			Resource hoResource = resourceHandler.createResource(resourceHandler.getSpecificationResourceSet(),
				options.project.path() + "/model/" + options.project.name() + ".ho.tgg.xmi");
			initContainer(hoResource);
			return hoResource;
		};
	}

	private TGGModel initContainer(Resource res) {
		TGGModel tggModel = IBeXTGGModelFactory.eINSTANCE.createTGGModel();
		res.getContents().add(tggModel);
		
		IBeXPatternSet patternSet = IBeXCoreModelFactory.eINSTANCE.createIBeXPatternSet();
		tggModel.setPatternSet(patternSet);
		TGGRuleSet ruleSet = IBeXTGGModelFactory.eINSTANCE.createTGGRuleSet();
		tggModel.setRuleSet(ruleSet);
		
		return tggModel;
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
		
		if (higherOrderResource != null) {
			try {
				higherOrderResource.save(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			scResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void save(RuntimeShortcutRule scRule) {
		if (scRule.getOriginalRule() instanceof HigherOrderTGGRule hoOriginalRule)
			save(hoOriginalRule);
		if (scRule.getReplacingRule() instanceof HigherOrderTGGRule hoReplacingRule)
			save(hoReplacingRule);
		
		scTggModel.getRuleSet().getRules().add(scRule.getShortcutRule());
		scTggModel.getPatternSet().getPatterns().add(scRule.getShortcutRule().getPrecondition());
		containerPatternsOfOperationalizations(scRule.getShortcutRule());
	}

	private void containerPatternsOfOperationalizations(TGGOperationalRule opRule) {
		for (var operationalization : opRule.getOperationalisations()) {
			scTggModel.getPatternSet().getPatterns().add(operationalization.getPrecondition());
			containerPatternsOfOperationalizations(operationalization);
		}
	}

	private void save(HigherOrderTGGRule hoRule) {
		if (higherOrderResource == null)
			higherOrderResource = hoResCreator.get();

		TGGModel hoTggModel = (TGGModel) higherOrderResource.getContents().get(0);
		hoTggModel.getRuleSet().getRules().add(hoRule);
		hoTggModel.getPatternSet().getPatterns().add(hoRule.getPrecondition());
	}

}
