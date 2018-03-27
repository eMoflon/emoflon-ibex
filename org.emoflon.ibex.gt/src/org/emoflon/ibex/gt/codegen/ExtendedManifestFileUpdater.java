package org.emoflon.ibex.gt.codegen;

import java.util.List;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

import org.moflon.core.plugins.manifest.ManifestFileUpdater;
import org.moflon.core.plugins.manifest.PluginManifestConstants;
import org.moflon.core.plugins.manifest.ManifestFileUpdater.AttributeUpdatePolicy;

/**
 * Extensions for the {@link ManifestFileUpdater}.
 */
public class ExtendedManifestFileUpdater {

	/**
	 * Sets the required properties of the manifest if not set already.
	 * 
	 * @param manifest
	 *            the manifest to update
	 * @param projectName
	 *            the name of the project
	 * @return whether the property was changed
	 */
	public static boolean setBasics(final Manifest manifest, final String projectName) {
		boolean changed = false;
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.MANIFEST_VERSION, "1.0",
				AttributeUpdatePolicy.KEEP);
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_NAME, projectName,
				AttributeUpdatePolicy.KEEP);
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_MANIFEST_VERSION, "2",
				AttributeUpdatePolicy.KEEP);
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VERSION, "0.0.1",
				AttributeUpdatePolicy.KEEP);
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_SYMBOLIC_NAME,
				projectName + ";singleton:=true", AttributeUpdatePolicy.KEEP);
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_ACTIVATION_POLICY,
				"lazy", AttributeUpdatePolicy.KEEP);
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_EXECUTION_ENVIRONMENT,
				"JavaSE-1.8", AttributeUpdatePolicy.KEEP);
		return changed;
	}

	/**
	 * Updates the Export-Package property of the manifest.
	 * 
	 * @param manifest
	 *            the manifest to update
	 * @param newExports
	 *            the exports to add
	 * @return whether the property was changed
	 */
	public static boolean updateExports(final Manifest manifest, final List<String> newExports) {
		List<String> exportsList = ManifestFileUpdater
				.extractDependencies((String) manifest.getMainAttributes().get(PluginManifestConstants.EXPORT_PACKAGE));
		boolean updated = false;
		for (String newExport : newExports) {
			if (!exportsList.contains(newExport)) {
				exportsList.add(newExport);
				updated = true;
			}
		}
		if (updated) {
			manifest.getMainAttributes().put(PluginManifestConstants.EXPORT_PACKAGE,
					exportsList.stream().filter(e -> !e.isEmpty()).collect(Collectors.joining(",")));
		}
		return updated;
	}
}
