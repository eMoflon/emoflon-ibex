package org.benchmarx.database.core;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.benchmarx.database.core.DatabaseComparator;
import org.junit.*;

import Database.DB;
import Database.DatabasePackage;

public class DatabaseComparatorTest {
	private DatabaseComparator comp;
	private static ResourceSet rs;
	
	@BeforeClass
	public static void init() {
		rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		rs.getPackageRegistry().put(DatabasePackage.eNS_URI, DatabasePackage.eINSTANCE);
	}
	
	@Before
	public void createComparator() {
		comp = new DatabaseComparator();
	}
	
	@Test
	public void testPositive() throws IOException {
		URI uri = URI.createURI("./instances/Database.xmi");
		Resource res = rs.createResource(uri);
		res.load(null);
		DB classPackage = (DB)(res.getContents().get(0));
		

		uri = URI.createURI("./instances/TheSameDatabase.xmi");
		res = rs.createResource(uri);
		res.load(null);
		DB theSameClassPackage = (DB)(res.getContents().get(0));
		
		comp.assertEquals(classPackage, theSameClassPackage);
	}
	
	@Test
	public void testNegative() throws IOException {
		URI uri = URI.createURI("./instances/Database.xmi");
		Resource res = rs.createResource(uri);
		res.load(null);
		DB classPackage = (DB)(res.getContents().get(0));
		
		uri = URI.createURI("./instances/DifferentDatabase.xmi");
		res = rs.createResource(uri);
		res.load(null);
		DB differentClassPackage = (DB)(res.getContents().get(0));
		
		boolean equal = false;
		try {
			comp.assertEquals(classPackage, differentClassPackage);
			equal = true;
		} catch (AssertionError e) {}
		
		if (equal)
			throw new AssertionError();
	}
}
