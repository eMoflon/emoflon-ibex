package org.emoflon.ibex.common.utils;

import java.util.Objects;

import org.eclipse.emf.ecore.EClass;

/**
 * Utility methods working with Ecore.
 */
public class EcoreUtils {
	
	/**
	 * Checks whether the given EClasses are the same or one is a super class of the
	 * other.
	 * 
	 * @param class1
	 *            an EClass
	 * @param class2
	 *            another EClass
	 * @return true if and only if an object could be an instance of both classes
	 */
	public static boolean areTypesCompatible(final EClass class1, final EClass class2) {
		Objects.requireNonNull(class1);
		Objects.requireNonNull(class2);
		return class1.equals(class2) || class1.getEAllSuperTypes().contains(class2)
				|| class2.getEAllSuperTypes().contains(class1);
	}
}
