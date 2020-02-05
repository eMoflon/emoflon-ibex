package org.emoflon.ibex.tgg.operational.defaults.options;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

public class ProjectOptions extends IbexSubOptions {
	
	private String workspacePath;
	private String projectPath;
	private String projectName;

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

}
