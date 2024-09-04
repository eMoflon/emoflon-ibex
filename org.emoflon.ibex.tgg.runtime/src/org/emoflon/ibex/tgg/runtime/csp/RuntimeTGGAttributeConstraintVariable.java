package org.emoflon.ibex.tgg.runtime.csp;

public class RuntimeTGGAttributeConstraintVariable {

	private boolean bound;
	private Object value;
	private String type;

	public RuntimeTGGAttributeConstraintVariable(boolean bound, Object value, String type) {
		this.bound = bound;
		this.type = type;
		this.value = value;
	}

	public boolean isBound() {
		return bound;
	}

	public Object getValue() {
		return value;
	}

	public void bindToValue(Object value) {
		setBound(true);
		setValue(value);
	}

	private void setValue(Object value) {
		this.value = value;
	}

	private void setBound(boolean b) {
		bound = b;
	}

	public String getType() {
		return type;
	}

}
