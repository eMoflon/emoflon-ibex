package org.emoflon.ibex.tgg.operational.csp;

public class RuntimeTGGAttributeConstraintVariable {

	private boolean bound;
	private Object value;
	private String type;

	public RuntimeTGGAttributeConstraintVariable(boolean bound, Object value) {
		this.bound = bound;
		this.value = value;
	}
	
	public RuntimeTGGAttributeConstraintVariable(boolean bound, Object value, String type) {
		this.bound = bound;
		this.type = type;
		this.value = value;
	}

	public boolean isBound() {
		return false;
	}

	public Object getValue() {
		return value;
	}

	public void bindToValue(Object value) {
		setBound(true);
//
//		switch (type.toLowerCase()) {
//		case "int":
//			setValue(((Number) value).intValue());
//			break;
//		case "double":
//			setValue(((Number) value).doubleValue());
//			break;
//		case "float":
//			setValue(((Number) value).floatValue());
//			break;
//		case "number":
//			if (value instanceof Number) {
//				double val = ((Number) value).doubleValue();
//				if (val == Math.round(val))
//					setValue(((Number) value).intValue());
//				else
//					setValue(value);
//			}
//			break;
//		default:
			setValue(value);
//			break;
//		}
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
