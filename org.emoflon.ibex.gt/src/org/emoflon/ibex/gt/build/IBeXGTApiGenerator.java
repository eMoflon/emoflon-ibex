package org.emoflon.ibex.gt.build;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.emoflon.ibex.gt.build.template.GeneratorTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGTApiData;
import org.emoflon.ibex.gt.build.template.IBeXGtApiTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGtMatchTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGtPatternFactoryTemplate;
import org.emoflon.ibex.gt.build.template.IBeXGtRuleFactoryTemplate;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;

public class IBeXGTApiGenerator {
	final protected IBeXGTApiData data;
	protected List<GeneratorTemplate<?>> templates = Collections.synchronizedList(new LinkedList<>());

	public IBeXGTApiGenerator(final GTModel model) {
		data = new IBeXGTApiData(model);
	}

	public void generate() {
		data.engines.values().forEach(ext -> templates.add(new IBeXGtApiTemplate(data, ext)));
		templates.add(new IBeXGtRuleFactoryTemplate(data, data.model));
		templates.add(new IBeXGtPatternFactoryTemplate(data, data.model));

		data.pattern2matchClassName.keySet().parallelStream()
				.forEach(pattern -> templates.add(new IBeXGtMatchTemplate(data, pattern)));

		templates.parallelStream().forEach(template -> {
			try {
				template.init();
				template.generate();
				template.writeToFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
