package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC;

public class Triple<L, M, R> {

	public L left;
	public M middle;
	public R right;
	
	public static <L, M, R> Triple<L, M, R> of(L left, M middle, R right) {
		return new Triple<L, M, R>(left, middle, right);
	}
	
	public Triple(L left, M middle, R right) {
		this.left = left;
		this.middle = middle;
		this.right = right;
	}
	
	public L getLeft() {
		return left;
	}
	
	public M getMiddle() {
		return middle;
	}
	
	public R getRight() {
		return right;
	}
	
}
