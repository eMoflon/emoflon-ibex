package org.emoflon.ibex.tgg.operational.defaults.options;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.moflon.smartemf.runtime.SmartPackage;

public class ProjectOptions extends IbexSubOptions {
	
	private String workspacePath;
	private String projectPath;
	private String projectName;
	private Boolean usesSmartEMF = null;

	public ProjectOptions(IbexOptions options) {
		super(options);

		projectPath = "/";
		workspacePath = "./../";
	}
	
	public IbexOptions workspacePath(String workspacePath) {
		this.workspacePath = workspacePath;
		return options;
	}

	public String workspacePath() {
		return workspacePath;
	}

	public IbexOptions path(String projectPath) {
		this.projectPath = projectPath;
		return options;
	}

	public String path() {
		return projectPath;
	}

	public IbexOptions name(String projectName) {
		this.projectName = projectName;
		return options;
	}

	public String name() {
		return projectName;
	}

	public boolean usesSmartEMF() {
		if(usesSmartEMF == null) {
			if(options.tgg.corrMetamodel() == null)
				return false;
			else {
				usesSmartEMF = options.tgg.corrMetamodel() instanceof SmartPackage;
			}
		}
		return usesSmartEMF;
	}

}
