package org.emoflon.ibex.gt.build.template;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

public abstract class GeneratorTemplate<CONTEXT> {
	final protected IBeXGTApiData data;
	final protected CONTEXT context;

	protected String packageName;
	protected String className;
	protected String fqn;
	protected String filePath;

	protected String code;
	protected Set<String> imports = new HashSet<>();

	public GeneratorTemplate(final IBeXGTApiData data, final CONTEXT context) {
		this.data = data;
		this.context = context;
	}

	public abstract void init();

	public abstract void generate();

	public void writeToFile() throws Exception {
		File file = new File(filePath);
		Files.write(file.toPath(), code.getBytes());
	}
}
