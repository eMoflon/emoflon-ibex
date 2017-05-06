package org.emoflon.ibex.tgg.ui.ide.admin.plugins;

import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;

/**
 * Contains constants for the various attributes that may appear in MANIFEST.MF
 */
public final class PluginManifestConstants extends Manifest {


	public static final Name BUNDLE_MANIFEST_VERSION = new Attributes.Name("Bundle-ManifestVersion");

	public static final Name BUNDLE_NAME = new Attributes.Name("Bundle-Name");

	public static final Name BUNDLE_SYMBOLIC_NAME = new Attributes.Name("Bundle-SymbolicName");

	public static final Name BUNDLE_VERSION = new Attributes.Name("Bundle-Version");

	public static final Name BUNDLE_VENDOR = new Attributes.Name("Bundle-Vendor");

	public static final Name BUNDLE_ACTIVATION_POLICY = new Attributes.Name("Bundle-ActivationPolicy");

	public static final Name BUNDLE_CLASSPATH = new Attributes.Name("Bundle-ClassPath");

	public static final Name BUNDLE_EXECUTION_ENVIRONMENT = new Attributes.Name("Bundle-RequiredExecutionEnvironment");
	
	public static final String REQUIRE_BUNDLE_KEY = "Require-Bundle";

	public static final Name REQUIRE_BUNDLE = new Attributes.Name(REQUIRE_BUNDLE_KEY);

	public static final Name MANIFEST_VERSION = Attributes.Name.MANIFEST_VERSION;

	public static final String EXPORT_PACKAGE_KEY = "Export-Package";
	
	public static final Name EXPORT_PACKAGE = new Attributes.Name(EXPORT_PACKAGE_KEY);



}
