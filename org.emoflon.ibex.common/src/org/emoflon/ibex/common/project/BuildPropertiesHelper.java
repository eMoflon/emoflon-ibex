package org.emoflon.ibex.common.project;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;

public class BuildPropertiesHelper {

	private String properties;
	private ArrayList<String> lines;
	private Map<Integer, Section> idex2sections = new LinkedHashMap<>();
	private Map<String, Section> sections = new LinkedHashMap<>();
	private int eof = -1;

	private boolean touched = false;

	public void loadProperties(File buildProps) throws CoreException, IOException {
		FileInputStream fi = new FileInputStream(buildProps);
		InputStreamReader ir = new InputStreamReader(fi);
		BufferedReader br = new BufferedReader(ir);
		lines = br.lines().map(line -> line + "\n").collect(Collectors.toCollection(ArrayList::new));

		this.properties = lines.stream().collect(Collectors.joining());

		br.close();
		ir.close();
		fi.close();

		findSections();
	}

	private void findSections() {
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).contains("=")) {

				String[] lineSplit = lines.get(i).split("=");
				if (lineSplit.length == 0)
					continue;

				Section section = new Section();
				section.title = lineSplit[0].trim().replace("\n", "");
				section.begin = i;
				idex2sections.put(i, section);
				sections.put(section.title, section);

				if (lineSplit.length > 1) {

					String[] subSplit = lineSplit[1].split(",\\\\");
					for (String subLine : subSplit) {
						String content = subLine.trim().replace("\n", "");
						if (content.length() != 0) {
							section.content.add(content);
						}
					}
				}

				if (i == lines.size() - 1) {
					section.end = i;
					eof = i;
					break;
				}

				for (int j = i + 1; j < lines.size(); j++) {
					if (lines.get(j).contains("=")) {
						i = j - 1;
						section.end = i;
						break;
					}

					String[] subSplit = lines.get(j).split(",\\\\");
					for (String subLine : subSplit) {
						String content = subLine.trim().replace("\n", "");
						if (content.length() != 0) {
							section.content.add(content);
						}
					}

					if (j == lines.size() - 1) {
						section.end = j;
						i = j;
						break;
					}

					i = j;
				}
			}

			if (i == lines.size() - 1) {
				eof = i;
				break;
			}

		}
	}

	public boolean containsSection(String section) {
		return sections.containsKey(section);
	}

	public boolean sectionContainsContent(String section, String content) {
		return sections.get(section).content.contains(content);
	}

	public void appendSection(String section) {
		Section sec = new Section();
		sec.title = section;
		sec.begin = eof;
		eof++;

		sections.put(section, sec);
		idex2sections.put(sec.begin, sec);

		touched = true;
	}

	public void addContentToSection(String section, String content) {
		Section sec = sections.get(section);
		sec.content.add(content);
		sec.end++;
		eof++;

		int movingIdx = sec.end;
		while (idex2sections.containsKey(movingIdx)) {
			Section current = idex2sections.get(movingIdx);
			current.begin++;
			current.end++;
			movingIdx = current.end;
		}

		touched = true;
	}

	public void updateProperties(File output) throws IOException {
		if (!touched) {
			return;
		}
		sectionsToManifest();
		InputStream is = new ByteArrayInputStream(properties.getBytes());
		OutputStream os = new FileOutputStream(output);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}
		os.close();
		is.close();
	}

	private void sectionsToManifest() {
		StringBuilder sb = new StringBuilder();
		for (Section section : sections.values()) {
			sb.append(section.title + " =");
			Queue<String> content = new LinkedList<>();
			content.addAll(section.content);

			if (content.size() <= 0) {
				sb.append("\n");
				continue;
			}

			sb.append(" " + content.poll() + (!content.isEmpty() ? ",\\" : "") + "\n");

			while (!content.isEmpty()) {
				sb.append("\t\t" + content.poll() + (!content.isEmpty() ? ",\\" : "") + "\n");
			}
		}
		properties = sb.toString();
	}

}