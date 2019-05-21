package org.emoflon.ibex.gt.codegen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.moflon.core.utilities.EcoreUtils;

import GTLanguage.GTNode;
import GTLanguage.GTParameter;

/**
 * This class manages the mapping between names of EClasses/EDataTypes to the
 * name of the meta-model.
 */
public class EClassifiersManager {
	/**
	 * The mapping eClassifier name to the name of the package containing the
	 * meta-model code.
	 */
	private Map<String, String> eClassifierNameToPath = new HashMap<String, String>();

	/**
	 * The mapping name of the meta-model Java Package to the name of the package
	 * containing it.
	 */
	private Map<String, String> packageNameToPath = new HashMap<String, String>();

	/**
	 * The mappings of URIs to package paths set via the properties file.
	 */
	private final Map<String, String> mappings;

	/**
	 * Creates a new EClassifiersManager.
	 * 
	 * @param mappings the mappings of URIs to package paths set via the properties
	 *                 file
	 */
	public EClassifiersManager(final Map<String, String> mappings) {
		this.mappings = mappings;
	}

	/**
	 * Loads the EClasses from the given meta-model resource.
	 * 
	 * @param ecoreFile the resource
	 */
	public void loadMetaModelClasses(final Resource ecoreFile) {
		EObject rootElement = ecoreFile.getContents().get(0);
		if (rootElement instanceof EPackage) {
			loadMetaModelClasses((EPackage) rootElement);
		}
	}

	/**
	 * Loads the EClasses from the given package.
	 * 
	 * @param ePackage the package
	 */
	private void loadMetaModelClasses(final EPackage ePackage) {
		boolean isEcore = "ecore".equals(ePackage.getName());
		String name = isEcore ? "org.eclipse.emf.ecore" : getPackagePath(ePackage);
		ePackage.getEClassifiers().stream() //
				.filter(c -> !isEcore || c instanceof EClass) //
				.forEach(c -> eClassifierNameToPath.put(c.getName(), name));
		if (!isEcore) {
			addPackage(ePackage);
		}

		ePackage.getESubpackages().forEach(p -> loadMetaModelClasses(p));
	}

	/**
	 * Returns the import path for the Java package containing the code for the
	 * given Ecore package.
	 * 
	 * @param ePackage the package
	 * @return the import path for package
	 */
	private String getPackagePath(final EPackage ePackage) {
		String uriString = ePackage.eResource().getURI().toString();
		if (mappings.containsKey(uriString)) {
			return mappings.get(uriString);
		} else {
			return EcoreUtils.getFQNIfPossible(ePackage).orElse(ePackage.getName());
		}
	}

	/**
	 * Adds the EPackage to the meta-models.
	 * 
	 * @param ePackage the package to add
	 */
	private void addPackage(final EPackage ePackage) {
		String packageClassName = getPackageClassName(ePackage.getName());
		String packageImport = getPackagePath(ePackage) + "." + packageClassName;
		packageImport = correctPackageImportWithMapping(packageImport);
		packageNameToPath.put(packageClassName, packageImport);
	}

	/**
	 * Give the user the chance to correct any package mapping with values in the
	 * property file.
	 * 
	 * @param packageImport initial import
	 * @return fixed import
	 */
	private String correctPackageImportWithMapping(String packageImport) {
		if (mappings.containsKey(packageImport))
			return mappings.get(packageImport);
		return packageImport;
	}

	/**
	 * Return the name of the Package class.
	 * 
	 * @param modelName the package of the meta-model
	 */
	private static String getPackageClassName(String modelName) {
		return Character.toUpperCase(modelName.charAt(0)) + modelName.substring(1) + "Package";
	}

	/**
	 * Determines the set of necessary imports for the given EClassifiers.
	 * 
	 * @param types the EClassifiers to import
	 * @return the types for Java import statements
	 */
	private Set<String> getImportsForTypes(final Set<? extends EClassifier> types) {
		Set<String> imports = new TreeSet<String>();
		types.stream().distinct().forEach(eClassifier -> {
			String typePackageName = eClassifierNameToPath.get(eClassifier.getName());
			if (typePackageName != null) {
				imports.add(typePackageName + '.' + eClassifier.getName());
			}
		});
		return imports;
	}

	/**
	 * Determines the set of necessary type imports for a set of nodes.
	 * 
	 * @param nodes the nodes
	 * @return the types for Java import statements
	 */
	public Set<String> getImportsForNodeTypes(final List<GTNode> nodes) {
		return getImportsForTypes(nodes.stream().map(n -> n.getType()).collect(Collectors.toSet()));
	}

	/**
	 * Determines the set of necessary type imports for the parameters.
	 * 
	 * @param parameters the parameters
	 * @return the types for Java import statements
	 */
	public Set<String> getImportsForDataTypes(final List<GTParameter> parameters) {
		return getImportsForTypes(parameters.stream().map(p -> p.getType()).collect(Collectors.toSet()));
	}

	/**
	 * Returns the names of the meta-model packages.
	 * 
	 * @return names of the meta-model packages
	 */
	public Set<String> getPackages() {
		return packageNameToPath.keySet();
	}

	/**
	 * Determines the set of necessary imports for the meta-models packages.
	 * 
	 * @return the types for Java import statements
	 */
	public Set<String> getImportsForPackages() {
		return new HashSet<String>(packageNameToPath.values());
	}
}
