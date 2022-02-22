package org.emoflon.ibex.modlexml.test;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.emoflon.ibex.modelxml.Attribute;
import org.emoflon.ibex.modelxml.Node;
import org.emoflon.ibex.modelxml.Value;
import org.emoflon.ibex.modelxml.XMLModel;
import org.emoflon.ibex.modelxml.parser.Parser;
import org.emoflon.ibex.modelxml.parser.Writer;
import org.junit.Test;

public class IntegrationTests {
	
	@Test
	public void loadTestXML() {
		XMLModel expectModel = Parser.parseXMLFile("misc/test.xml");
		XMLModel actualModel = Parser.parseXMLFile("test.xml");
		assertEquals(expectModel.getHeader(), actualModel.getHeader());
		assertTrue(compareNodes(expectModel.getRoot(),actualModel.getRoot()));
	}
	private boolean compareNodes(Node expect, Node actual) {
		//TODO Compare Children
		if(expect.getName() != actual.getName()) return false;
		if((expect == null) && (actual == null)) return true;
		if(!(compareValues(expect.getValue(), actual.getValue())))return false;
		for(var attr : expect.getAttributes())
			if(!(actual.getAttributes().contains(attr))) return false;
		
//		for(var child: expect.getChildren()) {
//			
//		}
		return true;
	}
	private boolean compareAttributes(Attribute expect, Attribute actual) {
		if((expect == null) && (actual == null) )return true;
		if((expect.getValue().equals(actual.getValue()) && expect.getName().equals(actual.getName()) && expect.isId() == actual.isId())) {
			return true;
		}
		return false;
	}
	private boolean compareValues(Value expect, Value actual) {
		if((expect == null) && (actual == null) )return true;
		if(expect.getText().equals(expect.getText()))return true;
		return false;
	}
}
