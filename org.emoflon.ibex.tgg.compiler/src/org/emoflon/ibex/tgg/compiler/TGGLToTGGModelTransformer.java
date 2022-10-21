package org.emoflon.ibex.tgg.compiler;

import static org.emoflon.ibex.common.slimgt.util.SlimGTModelUtil.getContainer;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModelMetadata;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleEdgeContext;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleEdgeCreation;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleNodeContext;
import org.emoflon.ibex.common.slimgt.slimGT.SlimRuleNodeCreation;
import org.emoflon.ibex.common.slimgt.util.SlimGTEMFUtil;
import org.emoflon.ibex.common.transformation.SlimGtToIBeXCoreTransformer;
import org.emoflon.ibex.tgg.tggl.scoping.TGGLScopeProvider;
import org.emoflon.ibex.tgg.tggl.tGGL.EditorFile;
import org.emoflon.ibex.tgg.tggl.tGGL.SlimRule;
import org.emoflon.ibex.tgg.tggl.tGGL.SlimRuleNode;
import org.emoflon.ibex.tgg.tggl.tGGL.TGGCorrRule;
import org.emoflon.ibex.tgg.tggl.tGGL.TGGCorrespondenceNode;
import org.emoflon.ibex.tgg.tggl.tGGL.TGGCorrespondenceNodeContext;
import org.emoflon.ibex.tgg.tggl.tGGL.TGGCorrespondenceNodeCreation;
import org.emoflon.ibex.tgg.tggl.tGGL.TGGDomainRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

record EdgeSignature(EObject source, EObject target, EReference type) {
	
}

public class TGGLToTGGModelTransformer extends SlimGtToIBeXCoreTransformer<EditorFile, TGGModel, IBeXTGGModelFactory> {
	
	private Map<Object, EObject> tggl2tggModel = new ConcurrentHashMap<>();
	private Map<SlimRule, Collection<IBeXPattern>> pattern2ibexPatterns = new ConcurrentHashMap<>();
	
	public TGGLToTGGModelTransformer(EditorFile editorFile, final IProject project) {
		super(editorFile, project);
	}

	@Override
	protected IBeXTGGModelFactory initFactory() {
		return IBeXTGGModelFactory.eINSTANCE;
	}

	@Override
	protected TGGModel createNewModel() {
		return factory.createTGGModel();
	}

	@Override
	protected IBeXModelMetadata createModelMetadata() {
		IBeXModelMetadata metadata = superFactory.createIBeXModelMetadata();
		metadata.setProject(project.getName());
		metadata.setProjectPath(project.getLocation().toPortableString());

		var usedPackages = getUsedPackages();
		for (EPackage pkg : usedPackages) {
			EPackageDependency dependency = null;
			try {
				dependency = transform(pkg);
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
	
	private Collection<EPackage> getUsedPackages() {
		var schema = editorFile.getSchema();
		var packages = new HashSet<EPackage>();
		packages.addAll(TGGLScopeProvider.getReferencedPackages(schema.getSourceTypes()));
		packages.addAll(TGGLScopeProvider.getReferencedPackages(schema.getTargetTypes()));
		return packages;
	}

	@Override
	public TGGModel transform() {
		model.setRuleSet(factory.createTGGRuleSet());
		
		// Pattern are only transformed if actually called by a tgg rule
		editorFile.getRules().parallelStream().filter(rule -> !rule.isAbstract()).forEach(this::transformRule);
		
		postProcessing();
		
		return model;
	}
	
	protected TGGRule transformRule(org.emoflon.ibex.tgg.tggl.tGGL.TGGRule rule) {
		if(tggl2tggModel.containsKey(rule))
			return (TGGRule) tggl2tggModel.get(rule);
		
		var internalRule = factory.createTGGRule();
		tggl2tggModel.put(rule, internalRule);
		
		internalRule.setName(rule.getName());
		for(var node : rule.getSourceRule().getContextNodes()) {
			internalRule.getNodes().add(transformTGGNode((SlimRuleNode) node.getContext(), BindingType.CONTEXT, DomainType.SOURCE));
		}
		for(var node : rule.getSourceRule().getCreatedNodes()) {
			internalRule.getNodes().add(transformTGGNode((SlimRuleNode) node.getCreation(), BindingType.CREATE, DomainType.SOURCE));
		}
		
		for(var node : rule.getCorrRule().getContextCorrespondenceNodes()) {
			internalRule.getNodes().add(transformTGGCorrespondenceNode((TGGCorrespondenceNode) node.getContext(), BindingType.CONTEXT, DomainType.CORRESPONDENCE));
		}
		for(var node : rule.getCorrRule().getCreatedCorrespondenceNodes()) {
			internalRule.getNodes().add(transformTGGCorrespondenceNode((TGGCorrespondenceNode) node.getCreation(), BindingType.CREATE, DomainType.CORRESPONDENCE));
		}
		
		for(var node : rule.getTargetRule().getContextNodes()) {
			internalRule.getNodes().add(transformTGGNode((SlimRuleNode) node.getContext(), BindingType.CONTEXT, DomainType.TARGET));
		}
		for(var node : rule.getTargetRule().getCreatedNodes()) {
			internalRule.getNodes().add(transformTGGNode((SlimRuleNode) node.getCreation(), BindingType.CREATE, DomainType.TARGET));
		}
		
		
		
		return internalRule;
	}
	
	protected TGGCorrespondence transformTGGCorrespondenceNode(TGGCorrespondenceNode node, BindingType binding, DomainType domain) {
		if(tggl2tggModel.containsKey(node))
			return (TGGCorrespondence) tggl2tggModel.get(node);
		
		var corrNode = factory.createTGGCorrespondence();
		tggl2tggModel.put(node, corrNode);
		
		corrNode.setBindingType(binding);
		corrNode.setDomainType(domain);
		corrNode.setOperationType(
		 switch(binding) {
				case CREATE -> IBeXOperationType.CREATION;
				case DELETE -> IBeXOperationType.DELETION;
				default -> IBeXOperationType.CONTEXT;
				}
		);
		
		var source = transformTGGNode(node.getSource(), getBindingType(node.getSource()), DomainType.SOURCE);
		var target = transformTGGNode(node.getTarget(), getBindingType(node.getTarget()), DomainType.TARGET);
		
		corrNode.setSource(source);
		corrNode.setTarget(target);
		
		transformTGGEdge(new EdgeSignature(corrNode, source, null), binding, domain);
		transformTGGEdge(new EdgeSignature(corrNode, target, null), binding, domain);
		
		return corrNode;
	}
	
	private TGGEdge transformTGGEdge(EdgeSignature edgeSignature, BindingType binding, DomainType domain) {
		if(tggl2tggModel.containsKey(edgeSignature))
			return (TGGEdge) tggl2tggModel.get(edgeSignature);
		
		var edge = factory.createTGGEdge();
		edge.setOperationType(switch(binding) {
				case CREATE -> IBeXOperationType.CREATION;
				case DELETE -> IBeXOperationType.DELETION;
				default -> IBeXOperationType.CONTEXT;
				}
		);
		edge.setType(edgeSignature.type());
		edge.setBindingType(binding);
		edge.setDomainType(domain);
		
		var source = transformNode(edgeSignature.source(), getBindingType(edgeSignature.source()), getDomainType(edgeSignature.source()));
		var target = transformNode(edgeSignature.target(), getBindingType(edgeSignature.target()), getDomainType(edgeSignature.target()));
		edge.setName(source.getName() + " -" + edgeSignature.type().getName()+"-> " + target.getName());
		
		return edge;
	}
	
	private IBeXNode transformNode(EObject input, BindingType bindingType, DomainType domainType) {
		if(input instanceof TGGCorrespondenceNode corrNode)
			return transformTGGCorrespondenceNode(corrNode, bindingType, domainType);
		
		if(input instanceof SlimRuleNode slimNode) 
			return transformTGGNode(slimNode, bindingType, domainType);
		
		throw new RuntimeException("Element could not be transformed to an ibex node: " + input );
	}

	private BindingType getBindingType(EObject obj) {
		var contextEdge = getContainer(obj, SlimRuleEdgeContext.class);
		var creationEdge = getContainer(obj, SlimRuleEdgeCreation.class);
		
		var context = getContainer(obj, SlimRuleNodeContext.class);
		var creation = getContainer(obj, SlimRuleNodeCreation.class);
		
		var corrContext = getContainer(obj, TGGCorrespondenceNodeContext.class);
		var corrCreation = getContainer(obj, TGGCorrespondenceNodeCreation.class);

		if(contextEdge != null)
			return BindingType.CONTEXT;
		if(corrContext != null)
			return BindingType.CONTEXT;
		if(context != null)
			return BindingType.CONTEXT;

		if(creationEdge != null)
			return BindingType.CREATE;
		if(corrCreation != null)
			return BindingType.CREATE;
		if(creation != null)
			return BindingType.CREATE;
		
		throw new RuntimeException("Cannot determine bindingtype for " + obj);
	}
	
	public static DomainType getDomainType(EObject context) {
		var tggRule = getContainer(context, org.emoflon.ibex.tgg.tggl.tGGL.TGGRule.class);
		var rule = getContainer(context, TGGDomainRule.class);
		if (rule != null) {
			if (tggRule.getSourceRule().equals(rule))
				return DomainType.SOURCE;
			if (tggRule.getTargetRule().equals(rule))
				return DomainType.TARGET;
		}

		// else it must be a correspondence node
		var corrRule = getContainer(context, TGGCorrRule.class);
		if (corrRule != null) {
			return DomainType.CORRESPONDENCE;
		}

		throw new RuntimeException("Could not identify domain of element " + context);
	}
	
	protected TGGNode transformTGGNode(SlimRuleNode node, BindingType binding, DomainType domain) {
		if(tggl2tggModel.containsKey(node))
			return (TGGNode) tggl2tggModel.get(node);
		
		var tggNode = factory.createTGGNode();
		tggl2tggModel.put(node, tggNode);
		
		
		
		return tggNode;
	}

	protected IBeXPattern transformPattern(SlimRule pattern) {
		if(tggl2tggModel.containsKey(pattern)) {
			return (IBeXPattern) tggl2tggModel.get(pattern);
		}
			
		var internPattern = superFactory.createIBeXPattern();
		internPattern.setName(pattern.getName());
		
		internPattern.getSignatureNodes()
		
		internPattern.setEmpty(!internPattern.getSignatureNodes().isEmpty() && !internPattern.getLocalNodes().isEmpty());
		return internPattern;
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
	
	protected void postProcessing() {
		Comparator<IBeXNamedElement> comparator = new Comparator<IBeXNamedElement>() {
			@Override
			public int compare(IBeXNamedElement o1, IBeXNamedElement o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
	}
	
}
