package org.emoflon.ibex.tgg.runtime.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNullValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.DoubleLiteral;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IntegerLiteral;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;
import org.emoflon.ibex.tgg.runtime.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGLocalVariable;
import org.moflon.core.utilities.EcoreUtils;


public class RuntimeTGGAttributeConstraintContainer implements IRuntimeTGGAttrConstrContainer {

	private List<RuntimeTGGAttributeConstraint> constraints;
	private RuntimeTGGAttrConstraintProvider constraintProvider;
	protected Map<TGGAttributeConstraintParameterValue, RuntimeTGGAttributeConstraintVariable> params2runtimeVariable = new HashMap<>();

	private ITGGMatch match;
	private Collection<String> boundObjectNames;

	public RuntimeTGGAttributeConstraintContainer(
			List<TGGAttributeConstraintParameterValue> variables,
			List<TGGAttributeConstraint> sortedConstraints, ITGGMatch match,
			RuntimeTGGAttrConstraintProvider runtimeConstraintProvider) {
		this.match = match;
		this.boundObjectNames = match.getParameterNames();
		this.constraintProvider = runtimeConstraintProvider;

		extractRuntimeParameters(variables);
		extractRuntimeConstraints(sortedConstraints);
	}

	private void extractRuntimeConstraints(List<TGGAttributeConstraint> sortedConstraints) {
		constraints = sortedConstraints.stream().map(c -> extractRuntimeConstraint(c)).collect(Collectors.toList());
	}

	private RuntimeTGGAttributeConstraint extractRuntimeConstraint(TGGAttributeConstraint c) {
		RuntimeTGGAttributeConstraint runtimeConstraint = constraintProvider.createRuntimeTGGAttributeConstraint(c.getDefinition());
		runtimeConstraint.initialize(this, c);
		return runtimeConstraint;
	}

	private void extractRuntimeParameters(List<TGGAttributeConstraintParameterValue> parameterValues) {
		for(var paramValue : parameterValues) {
			var variable = new RuntimeTGGAttributeConstraintVariable( //
					calculateBoundState(paramValue), //
					calculateValue(paramValue.getExpression()), //
					calculateType(paramValue));			
			params2runtimeVariable.put(paramValue, variable);
		}
	}

	private boolean calculateBoundState(TGGAttributeConstraintParameterValue value) {
		if (value.isDerived())
			return false;

		if (value.getExpression() instanceof IBeXAttributeValue tae) {
			return boundObjectNames.contains(tae.getNode().getName());
		}
		
		// TODO: check why TGGAtributeVariable were also unbound
//		if (value instanceof TGGAttributeVariable) {
//			return false;
//		}
		
		if (value.getExpression() instanceof TGGLocalVariable lv)
			return false;
		return true;
	}

	private Object calculateValue(ValueExpression value) {
		if (value instanceof IBeXAttributeValue ae) {
			String varName = ae.getNode().getName();
			if (match.isInMatch(varName)) {
				EObject obj = (EObject) match.get(varName);
				return obj.eGet(ae.getAttribute());
			}
			return null;
		}
		if (value instanceof IBeXEnumValue ev) {
			return ev.getLiteral().getInstance();
		}
		if (value instanceof IBeXStringValue sv) {
			return sv.getValue();
		}
		if (value instanceof IBeXBooleanValue bv) {
			return bv.isValue();
		}
		if (value instanceof IBeXNullValue nv) {
			return null;			
		}
		if(value instanceof DoubleLiteral dl) {
			return dl.getValue();
		}
		if(value instanceof IntegerLiteral il) {
			return il.getValue();
		}
		
		throw new RuntimeException("TGGAttributeConstraintVariable value could not be recognized.");
	}

	private String calculateType(TGGAttributeConstraintParameterValue value) {
		return value.getParameterDefinition().getType().getInstanceTypeName();
	}

	public void addConstraint(RuntimeTGGAttributeConstraint constraint) {
		constraints.add(constraint);
	}

	public boolean solve() {
		for (RuntimeTGGAttributeConstraint constraint : constraints) {
			constraint.solve();
			if (!constraint.isSatisfied())
				return false;
		}

		return true;
	}

	private Collection<Pair<IBeXAttributeValue, Object>> getBoundAttributeExpValues() {
		Collection<Pair<IBeXAttributeValue, Object>> col = constraints.stream()
				.map(constraint -> constraint.getBoundAttrExprValues())
				.reduce(new ArrayList<Pair<IBeXAttributeValue, Object>>(), (a, b) -> a.addAll(b) ? a : a);
		return col == null ? new ArrayList<Pair<IBeXAttributeValue, Object>>() : col;
	}

	@Override
	public void applyCSPValues(ITGGMatch comatch) {
		Collection<Pair<IBeXAttributeValue, Object>> cspValues = getBoundAttributeExpValues();

		for (Pair<IBeXAttributeValue, Object> cspVal : cspValues) {
			EObject entry = (EObject) comatch.get(cspVal.getLeft().getNode().getName());
			EAttribute attr = cspVal.getLeft().getAttribute();
			EDataType type = attr.getEAttributeType();
			Object value = cspVal.getRight();

			if(value != null && type != null && attr != null) {
				Object coercedType = coerceToType(type, value);
				if(!coercedType.equals(entry.eGet(attr))) {
					entry.eSet(attr, coercedType);
				}
			}
		}
	}

	private Object coerceToType(EDataType type, Object o) {
		assert (o != null);
		assert (type != null);

		// Try to handle dynamic EENums
		if (type.getInstanceClass() == null) {
			if (type instanceof EEnum en) {
				if (o instanceof EEnumLiteral enumLiteral) {
					if (en.getEEnumLiteralByLiteral(enumLiteral.getLiteral()) != null) {
						return en.getEEnumLiteralByLiteral(enumLiteral.getLiteral());
					}
				} else if (o instanceof Enumerator e) {
					if (e.getClass().getName().equals(EcoreUtils.getFQN(en))
							&& en.getEEnumLiteralByLiteral(e.getLiteral()) != null)
						return en.getEEnumLiteralByLiteral(e.getLiteral());
				}
			}
		} else {
			// Handle generated EEnums
			if (EcoreUtil.wrapperClassFor(type.getInstanceClass()).isInstance(o))
				return o;

			// Try type conversion as final attempt
			if (type.getInstanceClass().equals(int.class) && o.getClass().equals(Double.class))
				// Approximate result and squeeze into an int
				return ((Double) o).intValue();
			else if (o instanceof String s && type.getInstanceClass().equals(int.class))
				return Integer.parseInt(s);
			else if (o instanceof String s && type.getInstanceClass().equals(double.class))
				return Double.parseDouble(s);
			else if (o instanceof BasicEList<?> list)
				// Shallow copy
				return list.clone();
			else if (o instanceof Collection)
				// Currently no handling for collections
				return o;
			else
				throw new IllegalStateException("Cannot coerce " + o.getClass() + " to " + type.getInstanceClassName());
		}
		
		throw new IllegalStateException("Cannot coerce " + o + " to " + type);
	}
}
