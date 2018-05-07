package org.emoflon.ibex.common.utils;

import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.moflon.core.utilities.MoflonUtil;

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

	/**
	 * Checks whether the given EClasses are equal based on their fully qualified
	 * names (determined with
	 * {@link MoflonUtil#getFQN(org.eclipse.emf.ecore.ENamedElement)}. This is
	 * sometimes necessary if a weaker equality is desirable (classes are loaded
	 * from different resources).
	 * 
	 * @param class1
	 *            an EClass
	 * @param class2
	 *            another EClass
	 * @return true if and only if both classes have the same fully qualified name.
	 */
	public static boolean equalsFQN(final EClass class1, final EClass class2) {
		if(class1 != null && class2 != null)
			return MoflonUtil.getFQN(class1).equals(MoflonUtil.getFQN(class2));
		else
			return false;
	}
}
