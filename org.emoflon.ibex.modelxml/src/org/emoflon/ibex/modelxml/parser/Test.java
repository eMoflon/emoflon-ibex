package org.emoflon.ibex.modelxml.parser;

public class Test {

	public static void main(String[] args) {
		Writer.createXMLFile(Parser.parseXMLFile(), "test.xml");
	}

}
