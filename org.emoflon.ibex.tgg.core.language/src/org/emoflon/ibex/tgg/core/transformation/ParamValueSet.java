package org.emoflon.ibex.tgg.core.transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import language.TGGAttributeExpression;
import language.TGGAttributeVariable;
import language.TGGEnumExpression;
import language.TGGLiteralExpression;
import language.TGGParamValue;

public class ParamValueSet {
	List<TGGParamValue> collection = new ArrayList<TGGParamValue>();

	public TGGParamValue putIfAbsent(TGGParamValue entry) {
		TGGParamValue value = getDuplicate(entry);
		if (value == null) {
			collection.add(entry);
			return entry;
		}
		return value;
	}

	public TGGParamValue getDuplicate(TGGParamValue entry) {
		if (entry instanceof TGGLiteralExpression) {
			Optional<TGGParamValue> duplicate = collection.stream()
					.filter(element -> element instanceof TGGLiteralExpression)
					.filter(element -> ((TGGLiteralExpression) element).getValue()
							.equals(((TGGLiteralExpression) entry).getValue()))
					.findFirst();
			return duplicate.isPresent() ? duplicate.get() : null;
		}
		if (entry instanceof TGGEnumExpression) {
			Optional<TGGParamValue> duplicate = collection.stream()
					.filter(element -> element instanceof TGGEnumExpression)
					.filter(element -> ((TGGEnumExpression) element).getEenum()
							.equals(((TGGEnumExpression) entry).getEenum())
							&& ((TGGEnumExpression) element).getLiteral()
									.equals(((TGGEnumExpression) entry).getLiteral()))
					.findFirst();
			return duplicate.isPresent() ? duplicate.get() : null;
		}
		if (entry instanceof TGGAttributeExpression) {
			Optional<TGGParamValue> duplicate = collection.stream()
					.filter(element -> element instanceof TGGAttributeExpression)
					.filter(element -> ((TGGAttributeExpression) element).getAttribute()
							.equals(((TGGAttributeExpression) entry).getAttribute())
							&& ((TGGAttributeExpression) element).getObjectVar()
									.equals(((TGGAttributeExpression) entry).getObjectVar()))
					.findFirst();
			return duplicate.isPresent() ? duplicate.get() : null;
		}
		if (entry instanceof TGGAttributeVariable) {
			Optional<TGGParamValue> duplicate = collection.stream()
					.filter(element -> element instanceof TGGAttributeVariable)
					.filter(element -> ((TGGAttributeVariable) element).getName()
							.equals(((TGGAttributeVariable) entry).getName()))
					.findFirst();
			return duplicate.isPresent() ? duplicate.get() : null;
		}
		return null;
	}

	public List<TGGParamValue> getCollection() {
		return collection;
	}
}