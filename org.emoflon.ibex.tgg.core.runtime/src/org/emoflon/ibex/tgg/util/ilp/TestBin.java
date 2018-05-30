package org.emoflon.ibex.tgg.util.ilp;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class TestBin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinaryILPProblem bin = new BinaryILPProblem();
		bin.addExclusion(new ObjectOpenHashSet<String>(new String[] {"x1","x2"}), "a");
		bin.addImplication("x1", new ObjectOpenHashSet<String>(new String[] {"x2","x3"}), "b");
		bin.addNegativeImplication(
				new ObjectOpenHashSet<String>(new String[] {"x1","x2"}),
				new ObjectOpenHashSet<String>(new String[] {"y1","y2"}),
				"c"
				);
//		bin.fixVariable("x1", false);
//		bin.fixVariable("y1", true);
//		bin.fixVariable("x2", false);
//		bin.fixVariable("x3", false);
		bin.applyLazyFixedVariables();
		bin.getConstraints();
		System.out.println(bin);
	}

}
