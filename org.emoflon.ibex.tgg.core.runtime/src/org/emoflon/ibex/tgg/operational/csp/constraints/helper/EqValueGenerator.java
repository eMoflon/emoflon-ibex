package org.emoflon.ibex.tgg.operational.csp.constraints.helper;

import java.util.Random;

import org.emoflon.ibex.tgg.operational.csp.generator.Generator;

public interface EqValueGenerator {
	
	default Object generateValue(String type) {
		switch (type) {
		case "String":
			return Generator.getNewRandomString(null);
		case "Integer":
			return Integer.valueOf((int) (Math.random() * 1000.0));
		case "Long":
			return Long.valueOf((long) (Math.random() * 1000));
		case "Double":
			return Double.valueOf((Math.random() * 1000.0));
		case "Float":
			return Float.valueOf((float) (Math.random() * 1000.0));
		case "Char":
			return (char) ((new Random()).nextInt(26) + 'a');
		case "Boolean": 
			return Math.random() > 0.5;
		default:
			throw new RuntimeException("The type " + type + " is not supported for random value generation in Eq-constraints");
		}
	}

}
