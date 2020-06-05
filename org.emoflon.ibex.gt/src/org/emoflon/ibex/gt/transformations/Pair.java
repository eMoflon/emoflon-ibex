package org.emoflon.ibex.gt.transformations;

/**
 * Helper class to save multiple values under the same key in the graph map; only used in the IBeXDisjunctPatternTransformation classes
 */
class Pair<K, V>{
	K left;
	V right;
	
	public Pair(K left, V right){
		this.left = left;
		this.right = right;
	}
	public K getLeft() {
		return left;
	}
	public V getRight() {
		return right;
	}
	public void setLeft(K newLeft) {
		left = newLeft;
	}
	public void setRight(V newRight) {
		right = newRight;
	}
}
