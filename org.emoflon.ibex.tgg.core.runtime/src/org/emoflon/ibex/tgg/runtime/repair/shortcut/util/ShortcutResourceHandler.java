package org.emoflon.ibex.tgg.runtime.repair.shortcut.util;

import java.io.IOException;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleSet;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule;

public class ShortcutResourceHandler {

	private final TGGResourceHandler resourceHandler;

	private final Resource scResource;
	private TGGModel scTggModel;

	private final Supplier<Resource> hoResCreator;
	private Resource higherOrderResource;

	public ShortcutResourceHandler(IbexOptions options) {
		resourceHandler = options.resourceHandler();

		scResource = resourceHandler.createResource(resourceHandler.getSpecificationResourceSet(),
				options.project.path() + "/model/" + options.project.name() + ".sc.tgg.xmi");
		scTggModel = initContainer(scResource);

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

	public void save() {		
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

	public void add(TGGShortcutRule shortcutRule) {
		if (shortcutRule.getOriginalRule() instanceof HigherOrderTGGRule hoOriginalRule)
			addHigherOrderRule(hoOriginalRule);
		if (shortcutRule.getReplacingRule() instanceof HigherOrderTGGRule hoReplacingRule)
			addHigherOrderRule(hoReplacingRule);
		
		scTggModel.getRuleSet().getRules().add(shortcutRule);
		scTggModel.getPatternSet().getPatterns().add(shortcutRule.getPrecondition());
	}

	private void addHigherOrderRule(HigherOrderTGGRule hoRule) {
		if (higherOrderResource == null)
			higherOrderResource = hoResCreator.get();

		TGGModel hoTggModel = (TGGModel) higherOrderResource.getContents().get(0);
		hoTggModel.getRuleSet().getRules().add(hoRule);
		hoTggModel.getPatternSet().getPatterns().add(hoRule.getPrecondition());
	}

}