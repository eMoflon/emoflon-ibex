package org.emoflon.ibex.common.utils;

import org.moflon.core.preferences.PlatformUriType;

public class URIPreferenceExtension implements org.moflon.emf.codegen.URIPreferenceExtension {
	@Override
	public PlatformUriType getPlatformURIType() {
		return PlatformUriType.RESOURCE;
	}
}
