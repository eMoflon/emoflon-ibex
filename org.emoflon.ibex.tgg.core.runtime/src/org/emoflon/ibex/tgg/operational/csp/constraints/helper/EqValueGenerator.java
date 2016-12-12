package org.emoflon.ibex.tgg.operational.csp.constraints.helper;

import java.util.Random;

import org.emoflon.ibex.tgg.operational.csp.generator.Generator;

public interface EqValueGenerator {
	
	default Object generateValue(String type) {
		switch (type) {
		case "java.lang.String":
			return Generator.getNewRandomString(null);
		case "java.lang.Integer":
			return Integer.valueOf((int) (Math.random() * 1000.0));
		case "java.lang.Long":
			return Long.valueOf((long) (Math.random() * 1000));
		case "java.lang.Double":
			return Double.valueOf((Math.random() * 1000.0));
		case "java.lang.Float":
			return Float.valueOf((float) (Math.random() * 1000.0));
		case "java.lang.Char":
			return (char) ((new Random()).nextInt(26) + 'a');
		case "java.lang.Boolean": 
			return Math.random() > 0.5;
		default:
			throw new RuntimeException("The type " + type + " is not supported for random value generation in Eq-constraints");
		}
	}

}
