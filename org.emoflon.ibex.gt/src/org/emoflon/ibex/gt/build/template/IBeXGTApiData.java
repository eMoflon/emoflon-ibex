package org.emoflon.ibex.gt.build.template;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.emoflon.ibex.common.engine.IBeXPMEngineInformation;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;
import org.moflon.core.utilities.ExtensionsUtil;

public class IBeXGTApiData {
	final public GTModel model;

	final public String apiPrefix;
	final public String apiPackage;
	final public String apiPackagePath;
	final public String apiAbstractClassName;
	final public String gtModelPath;
	final public String matchPackage;
	final public String matchPackagePath;
	final public String patternPackage;
	final public String patternPackagePath;
	final public String rulePackage;
	final public String rulePackagePath;

	final public Map<String, IBeXPMEngineInformation> engines = new LinkedHashMap<>();
	final public Map<IBeXPMEngineInformation, String> apiClassNames = new LinkedHashMap<>();
	final public String patternFactoryClassName;
	final public String ruleFactoryClassName;

	final public Map<GTPattern, String> pattern2matchClassName = new LinkedHashMap<>();
	final public Map<GTPattern, String> pattern2patternClassName = new LinkedHashMap<>();
	final public Map<GTRule, String> rule2ruleClassName = new LinkedHashMap<>();
	final public Map<GTRule, String> rule2CoPatternClassName = new LinkedHashMap<>();
	final public Map<GTRule, String> rule2CoMatchClassName = new LinkedHashMap<>();
	final public Map<GTPattern, GTRule> pattern2rule = new LinkedHashMap<>();

	public IBeXGTApiData(final GTModel model) {
		this.model = model;

		apiPrefix = apiPrefixFromPackage(model.getMetaData().getPackage());
		apiPackage = model.getMetaData().getPackage() + ".api";
		apiPackagePath = model.getMetaData().getPackagePath().replace("src", "src-gen") + "/api";
		apiAbstractClassName = apiPrefix + "GtApi";
		gtModelPath = model.getMetaData().getPackagePath().replace("src", "src-gen") + "/api/ibex_gt_model.xmi";
		matchPackage = apiPackage + ".match";
		matchPackagePath = apiPackagePath + "/match";
		patternPackage = apiPackage + ".pattern";
		patternPackagePath = apiPackagePath + "/pattern";
		rulePackage = apiPackage + ".rule";
		rulePackagePath = apiPackagePath + "/rule";

		ExtensionsUtil.collectExtensions(IBeXPMEngineInformation.PLUGIN_EXTENSON_ID, "engine_information",
				IBeXPMEngineInformation.class).forEach(ext -> engines.put(ext.getEngineName(), ext));
		engines.forEach((extName, ext) -> apiClassNames.put(ext, apiPrefix + firstToUpper(extName) + "GtApi"));
		patternFactoryClassName = apiPrefix + "GtPatternFactory";
		ruleFactoryClassName = apiPrefix + "GtRuleFactory";

		init();
	}

	protected void init() {
		Set<GTPattern> rulePatterns = new HashSet<>();
		model.getRuleSet().getRules().forEach(rule -> {
			rulePatterns.add((GTPattern) rule.getPrecondition());
			rule2ruleClassName.put(rule, firstToUpper(rule.getName()) + "Rule");
			pattern2matchClassName.put((GTPattern) rule.getPrecondition(), firstToUpper(rule.getName()) + "Match");
			rule2CoPatternClassName.put(rule, firstToUpper(rule.getPostcondition().getName()));
			rule2CoMatchClassName.put(rule, firstToUpper(rule.getName()) + "CoMatch");
			pattern2rule.put((GTPattern) rule.getPrecondition(), rule);
			pattern2patternClassName.put((GTPattern) rule.getPrecondition(), firstToUpper(rule.getName()) + "Rule");
		});
		model.getPatternSet().getPatterns().stream().map(pattern -> (GTPattern) pattern)
				.filter(pattern -> !rulePatterns.contains(pattern)).forEach(pattern -> {
					pattern2patternClassName.put(pattern, firstToUpper(pattern.getName()) + "Pattern");
					pattern2matchClassName.put(pattern, firstToUpper(pattern.getName()) + "Match");
				});

	}

	public static String apiPrefixFromPackage(final String pkg) {
		String[] segments = pkg.split("\\.");
		String finalSegment = segments[segments.length - 1];
		return firstToUpper(finalSegment);
	}

	public static String firstToUpper(final String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}

	public String getFQN(EClassifier cls) {
		if (cls.eContainer() != null && cls.eContainer() instanceof EPackage epk) {
			return model.getMetaData().getName2package().get(epk.getName()).getClassifierName2FQN().get(cls.getName());
		}
		throw new NullPointerException("EPackage of classifier " + cls + " is null.");
	}

	public String getPackageFQN(EClassifier cls) {
		if (cls.eContainer() != null && cls.eContainer() instanceof EPackage epk) {
			return model.getMetaData().getName2package().get(epk.getName()).getFullyQualifiedName();
		}
		throw new NullPointerException("EPackage of classifier " + cls + " is null.");
	}

	public String getPackageClass(EClassifier cls) {
		if (cls.eContainer() != null && cls.eContainer() instanceof EPackage epk) {
			return model.getMetaData().getName2package().get(epk.getName()).getPackageClassName();
		}
		throw new NullPointerException("EPackage of classifier " + cls + " is null.");
	}

	public String getSimplePackageClass(EClassifier cls) {
		if (cls.eContainer() != null && cls.eContainer() instanceof EPackage epk) {
			String fqn = model.getMetaData().getName2package().get(epk.getName()).getPackageClassName();
			String[] segments = fqn.split("\\.");
			return segments[segments.length - 1];
		}
		throw new NullPointerException("EPackage of classifier " + cls + " is null.");
	}

	public String getPackageFactoryClass(EClassifier cls) {
		if (cls.eContainer() != null && cls.eContainer() instanceof EPackage epk) {
			return model.getMetaData().getName2package().get(epk.getName()).getFactoryClassName();
		}
		throw new NullPointerException("EPackage of classifier " + cls + " is null.");
	}

	public String getSimplePackageFactoryClass(EClassifier cls) {
		if (cls.eContainer() != null && cls.eContainer() instanceof EPackage epk) {
			String fqn = model.getMetaData().getName2package().get(epk.getName()).getFactoryClassName();
			String[] segments = fqn.split("\\.");
			return segments[segments.length - 1];
		}
		throw new NullPointerException("EPackage of classifier " + cls + " is null.");
	}
}
