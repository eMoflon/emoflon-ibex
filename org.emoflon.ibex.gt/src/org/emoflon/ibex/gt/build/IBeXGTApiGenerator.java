package org.emoflon.ibex.gt.build;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.gt.build.template.GeneratorTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGTApiData;
import org.emoflon.ibex.gt.build.template.IBeXGtApiAbstractTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGtApiTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGtCoMatchTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGtCoPatternTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGtMatchTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGtPatternFactoryTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGtPatternTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGtRuleFactoryTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGtRuleTemplate;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage;
import org.moflon.core.utilities.LogUtils;

public class IBeXGTApiGenerator {
	private Logger logger = Logger.getLogger(IBeXGTApiGenerator.class);

	final protected IBeXGTApiData data;
	protected List<GeneratorTemplate<?>> templates = Collections.synchronizedList(new LinkedList<>());

	public IBeXGTApiGenerator(final GTModel model) {
		data = new IBeXGTApiData(model);
	}

	public void generate() {
		data.engines.values().forEach(ext -> templates.add(new IBeXGtApiTemplate(data, ext)));
		templates.add(new IBeXGtApiAbstractTemplate(data, data.model));
		templates.add(new IBeXGtRuleFactoryTemplate(data, data.model));
		templates.add(new IBeXGtPatternFactoryTemplate(data, data.model));

		data.pattern2matchClassName.keySet().parallelStream()
				.forEach(pattern -> templates.add(new IBeXGtMatchTemplate(data, pattern)));

		data.pattern2patternClassName.keySet().parallelStream()
				.filter(pattern -> !data.pattern2rule.containsKey(pattern))
				.forEach(pattern -> templates.add(new IBeXGtPatternTemplate(data, pattern)));

		data.rule2ruleClassName.keySet().parallelStream().forEach(rule -> {
			templates.add(new IBeXGtRuleTemplate(data, rule));
			templates.add(new IBeXGtCoPatternTemplate(data, rule));
			templates.add(new IBeXGtCoMatchTemplate(data, rule));
		});

		templates.parallelStream().forEach(template -> {
			try {
				template.init();
				template.generate();
				template.writeToFile();
			} catch (Exception e) {
				LogUtils.error(logger, e);
			}
		});

	}

	public void saveModel() {
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getPackageRegistry().put(IBeXGTModelPackage.eINSTANCE.getNsURI(), IBeXGTModelPackage.eINSTANCE);
		URI uri = URI.createFileURI(data.model.getMetaData().getProjectPath() + "/" + data.gtModelPath);
		Resource r = rs.createResource(uri);
		r.getContents().add(data.model);
		try {
			r.save(null);
		} catch (IOException e) {
			LogUtils.error(logger, e);
		}
	}
}
