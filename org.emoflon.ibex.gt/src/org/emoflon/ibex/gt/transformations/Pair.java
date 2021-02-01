package org.emoflon.ibex.gt.transformations;

/**
 * Helper class to save multiple values under the same key in the graph map; only used in the IBeXDisjunctPatternTransformation classes
 */
public class Pair<K, V>{
	K left;
	V right;
	
	public Pair(final K left, final V right){
		this.left = left;
		this.right = right;
	}
	public final K getLeft() {
		return left;
	}
	public final V getRight() {
		return right;
	}
	public void setLeft(final K newLeft) {
		left = newLeft;
	}
	public void setRight(final V newRight) {
		right = newRight;
	}
}
