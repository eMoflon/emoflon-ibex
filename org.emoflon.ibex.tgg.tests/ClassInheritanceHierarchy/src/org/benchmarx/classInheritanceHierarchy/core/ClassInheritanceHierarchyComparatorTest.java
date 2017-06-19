package org.benchmarx.classInheritanceHierarchy.core;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.benchmarx.classInheritanceHierarchy.core.ClassInheritanceHierarchyComparator;
import org.junit.*;

import ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage;
import ClassInheritanceHierarchy.ClassPackage;

public class ClassInheritanceHierarchyComparatorTest {
	private ClassInheritanceHierarchyComparator comp;
	private static ResourceSet rs;
	
	@BeforeClass
	public static void init() {
		rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		rs.getPackageRegistry().put(ClassInheritanceHierarchyPackage.eNS_URI, ClassInheritanceHierarchyPackage.eINSTANCE);
	}
	
	@Before
	public void createComparator() {
		comp = new ClassInheritanceHierarchyComparator();
	}
	
	@Test
	public void testPositive() throws IOException {
		URI uri = URI.createURI("./instances/ClassPackage.xmi");
		Resource res = rs.createResource(uri);
		res.load(null);
		ClassPackage classPackage = (ClassPackage)(res.getContents().get(0));
		

		uri = URI.createURI("./instances/TheSameClassPackage.xmi");
		res = rs.createResource(uri);
		res.load(null);
		ClassPackage theSameClassPackage = (ClassPackage)(res.getContents().get(0));
		
		comp.assertEquals(classPackage, theSameClassPackage);
	}
	
	@Test
	public void testNegative() throws IOException {
		URI uri = URI.createURI("./instances/ClassPackage.xmi");
		Resource res = rs.createResource(uri);
		res.load(null);
		ClassPackage classPackage = (ClassPackage)(res.getContents().get(0));
		
		uri = URI.createURI("./instances/DifferentClassPackage.xmi");
		res = rs.createResource(uri);
		res.load(null);
		ClassPackage differentClassPackage = (ClassPackage)(res.getContents().get(0));
		
		boolean equal = false;
		try {
			comp.assertEquals(classPackage, differentClassPackage);
			equal = true;
		} catch (AssertionError e) {}
		
		if (equal)
			throw new AssertionError();
	}
}
