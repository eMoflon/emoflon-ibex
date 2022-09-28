package org.emoflon.ibex.gt.transformation;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModelMetadata;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.slimgt.util.SlimGTEMFUtil;
import org.emoflon.ibex.common.transformation.SlimGtToIBeXCoreTransformer;
import org.emoflon.ibex.gt.gtl.gTL.EditorFile;
import org.emoflon.ibex.gt.gtl.gTL.GTLRuleNodeDeletion;
import org.emoflon.ibex.gt.gtl.gTL.GTLRuleType;
import org.emoflon.ibex.gt.gtl.gTL.Import;
import org.emoflon.ibex.gt.gtl.gTL.SlimRule;
import org.emoflon.ibex.gt.gtl.gTL.SlimRuleNode;
import org.emoflon.ibex.gt.gtl.gTL.SlimRuleNodeContext;
import org.emoflon.ibex.gt.gtl.gTL.SlimRuleNodeCreation;
import org.emoflon.ibex.gt.gtl.util.GTLResourceManager;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelFactory;

public class GTLtoGTModelTransformer extends SlimGtToIBeXCoreTransformer<EditorFile, GTModel, IBeXGTModelFactory> {

	protected final IProject project;
	protected final GTLResourceManager gtlManager = new GTLResourceManager(xtextResources);

	protected final Map<SlimRule, IBeXPattern> pattern2pattern = Collections.synchronizedMap(new HashMap<>());
	protected final Map<SlimRuleNode, IBeXNode> node2node = Collections.synchronizedMap(new HashMap<>());

	public GTLtoGTModelTransformer(final EditorFile editorFile, final IProject project) {
		super(editorFile);
		this.project = project;
	}

	@Override
	public GTModel transform() {
		model.setRuleSet(factory.createGTRuleSet());

		for (SlimRule rule : editorFile.getRules()) {
			// Ignore abstract patterns / rules
			if (rule.isAbstract())
				continue;

			// Transform to gt pattern
			if (rule.getType() == GTLRuleType.PATTERN) {
				transformPrecondition(rule);
			} else { // Transform to gt rule
				transformRule(rule);
			}
		}

		return model;
	}

	protected GTRule transformRule(SlimRule rule) {
		if (rule.getType() != GTLRuleType.RULE)
			return null;

		GTRule gtRule = factory.createGTRule();
		gtRule.setName(rule.getName());
		model.getRuleSet().getRules().add(gtRule);

		IBeXPattern precondition = transformPrecondition(rule);
		gtRule.setPrecondition(precondition);

		IBeXPattern postcondition = transformPostcondition(rule);
		gtRule.setPostcondition(postcondition);

		return gtRule;
	}

	protected IBeXPattern transformPrecondition(SlimRule pattern) {
		if (pattern2pattern.containsKey(pattern)) {
			return pattern2pattern.get(pattern);
		}

		IBeXPattern gtPattern = superFactory.createIBeXPattern();
		gtPattern.setName(pattern.getName());
		model.getPatternSet().getPatterns().add(gtPattern);
		pattern2pattern.put(pattern, gtPattern);

		for (SlimRuleNodeContext context : pattern.getContextNodes().stream().map(n -> (SlimRuleNodeContext) n)
				.collect(Collectors.toList())) {
			IBeXNode gtNode = transform((SlimRuleNode) context.getContext());
			if (context.isLocal()) {
				gtPattern.getLocalNodes().add(gtNode);
			} else {
				gtPattern.getSignatureNodes().add(gtNode);
			}
			gtNode.setOperationType(IBeXOperationType.CONTEXT);
		}

		for (GTLRuleNodeDeletion deletion : pattern.getDeletedNodes()) {
			IBeXNode gtNode = transform(deletion.getDeletion());
			gtPattern.getSignatureNodes().add(gtNode);
			gtNode.setOperationType(IBeXOperationType.DELETION);
		}

		// TODO: Conditions

		// TODO: Invocations

		// TODO: Watch dogs

		if (gtPattern.getSignatureNodes().isEmpty() && gtPattern.getLocalNodes().isEmpty()
				&& gtPattern.getAttributeConstraints().isEmpty())
			gtPattern.setEmpty(true);

		return gtPattern;
	}

	protected IBeXPattern transformPostcondition(SlimRule pattern) {
		IBeXPattern gtPattern = superFactory.createIBeXPattern();
		gtPattern.setName(pattern.getName());

		for (SlimRuleNodeContext context : pattern.getContextNodes().stream().map(n -> (SlimRuleNodeContext) n)
				.collect(Collectors.toList())) {
			IBeXNode gtNode = transform((SlimRuleNode) context.getContext());
			if (context.isLocal()) {
				gtPattern.getLocalNodes().add(gtNode);
			} else {
				gtPattern.getSignatureNodes().add(gtNode);
			}
			gtNode.setOperationType(IBeXOperationType.CONTEXT);
		}

		for (SlimRuleNodeCreation creation : pattern.getCreatedNodes().stream().map(n -> (SlimRuleNodeCreation) n)
				.collect(Collectors.toList())) {
			IBeXNode gtNode = transform((SlimRuleNode) creation.getCreation());
			gtPattern.getSignatureNodes().add(gtNode);
			gtNode.setOperationType(IBeXOperationType.CREATION);
		}

		// TODO: Conditions

		if (gtPattern.getSignatureNodes().isEmpty() && gtPattern.getLocalNodes().isEmpty()
				&& gtPattern.getAttributeConstraints().isEmpty())
			gtPattern.setEmpty(true);

		return gtPattern;
	}

	protected IBeXNode transform(SlimRuleNode node) {
		if (node2node.containsKey(node)) {
			return node2node.get(node);
		}

		IBeXNode gtNode = superFactory.createIBeXNode();
		model.getNodeSet().getNodes().add(gtNode);
		node2node.put(node, gtNode);

		// TODO: Edges

		return gtNode;
	}

	@Override
	protected IBeXGTModelFactory initFactory() {
		return IBeXGTModelFactory.eINSTANCE;
	}

	@Override
	protected GTModel createNewModel() {
		return factory.createGTModel();
	}

	@Override
	protected IBeXModelMetadata createModelMetadata() {
		IBeXModelMetadata metadata = superFactory.createIBeXModelMetadata();
		metadata.setProject(project.getName());
		metadata.setProjectPath(project.getLocation().toPortableString());
		metadata.setPackage(editorFile.getPackage().getName());
		metadata.setPackagePath("/src/" + metadata.getPackage().replace(".", "/"));

		for (Import imp : editorFile.getImports().stream().map(imp -> (Import) imp).collect(Collectors.toList())) {
			EPackageDependency dependency = null;
			try {
				dependency = transform(imp);
			} catch (IOException e) {
				continue;
			}
			metadata.getDependencies().add(dependency);
			metadata.getName2package().put(dependency.getSimpleName(), dependency);
		}

		// Add the ecore package if not present
		if (!metadata.getName2package().containsKey("ecore")) {
			try {
				EPackageDependency dependency = transform(EcorePackage.eINSTANCE);
				metadata.getDependencies().add(dependency);
				metadata.getName2package().put(dependency.getSimpleName(), dependency);
			} catch (IOException e) {
			}
		}

		return metadata;
	}

	protected EPackageDependency transform(final Import imp) throws IOException {
		EPackage pkg = SlimGTEMFUtil.loadMetamodel(imp.getName());
		return transform(pkg);
	}

	protected EPackageDependency transform(final EPackage pkg) throws IOException {
		EPackageDependency dependency = superFactory.createEPackageDependency();

		dependency.setSimpleName(pkg.getName());
		dependency.setHasAlias(false);

		// Set package metadata
		GenPackage genPack = SlimGTEMFUtil.getGenPack(pkg);
		dependency.setFullyQualifiedName(SlimGTEMFUtil.getFQName(genPack));
		dependency.setPackage(pkg);
		dependency.setPackageURI(pkg.getNsURI());
		dependency.setFactoryClassName(SlimGTEMFUtil.getFactoryClassName(genPack));
		dependency.setPackageClassName(SlimGTEMFUtil.getPackageClassName(pkg));
		dependency.setEcoreURI(pkg.eResource().getURI().toString());

		// Set package project metadata and check if it has a project, a proper ecore
		// and genmodel file
		// TODO: Finish this -> for now: set to false
		dependency.setPackageHasProject(false);
		dependency.setEcoreHasLocation(false);
		dependency.setGenmodelHasLocation(false);

		// Add classifier name to fqn map
		pkg.getEClassifiers().forEach(cls -> dependency.getClassifierName2FQN().put(cls.getName(),
				dependency.getFullyQualifiedName() + "." + cls.getName()));

		return dependency;
	}

}
