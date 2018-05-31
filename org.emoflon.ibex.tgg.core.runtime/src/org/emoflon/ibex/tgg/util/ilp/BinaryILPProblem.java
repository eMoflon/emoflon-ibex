package org.emoflon.ibex.tgg.util.ilp;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import it.unimi.dsi.fastutil.ints.Int2IntMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;

public class BinaryILPProblem extends ILPProblem {
	private Int2ObjectOpenHashMap<ObjectLinkedOpenHashSet<Implication>> implications = new Int2ObjectOpenHashMap<>();
	private ObjectLinkedOpenHashSet<NegativeImplication> negativeImplications = new ObjectLinkedOpenHashSet<>();
	private ObjectLinkedOpenHashSet<Exclusion> exclusions = new ObjectLinkedOpenHashSet<>();
	private ObjectLinkedOpenHashSet<ILPConstraint> generatedConstraints = new ObjectLinkedOpenHashSet<>();

	/**
	 * 
	 */
	BinaryILPProblem() {
	}

	/**
	 * A constraint of the form x -> a v b
	 */
	private class Implication {
		private int leftVariable;
		private IntOpenHashSet rightVariables;
		private boolean isRelevant = true;
		private String name = "";

		private void generateILPConstraint(ILPLinearExpression expr) {
			expr.addTerm(leftVariable, 1);
			rightVariables.stream().forEach(v -> {
				expr.addTerm(v, -1);
			});
		}

		private void fixImplVariable(int id, boolean choice) {
			if (leftVariable == id) {
				if (!choice) { // left side is false
					isRelevant = false;
				}
			}
			if (rightVariables.remove(id)) {
				// remove yielded true, so it was contained
				if (choice) {
					// implication is fulfilled -> remove
					isRelevant = false;
				} else {
					if (rightVariables.isEmpty()) {
						// Implication cannot be fulfilled
						fixVariable(leftVariable, false);
					}
				}
			}
		}

		@Override
		public String toString() {
			List<String> termStrings = new LinkedList<String>();
			this.rightVariables.stream().forEach((variableId) -> {
				termStrings.add(getVariable(variableId));
			});
			return "Implication(" + name + ")" + getVariable(leftVariable) + " -> " + String.join(" V ", termStrings);
		}
	}

	/**
	 * A constraint of the form not(x V y) -> not(a) ^ not(b)
	 */
	private class NegativeImplication {
		private IntOpenHashSet leftVariables;
		private IntOpenHashSet rightVariables;
		private boolean isRelevant = true;
		private String name = "";

		private void generateILPConstraint() {
			ILPLinearExpression expr = createLinearExpression();
			leftVariables.stream().forEach(v -> {
				expr.addTerm(v, -rightVariables.size());
			});
			rightVariables.stream().forEach(v -> {
				expr.addTerm(v, 1);
			});
			ILPConstraint constr = addConstraint(expr, Comparator.le, 0.0, name);
			generatedConstraints.add(constr);
		}

		private void fixImplVariable(int id, boolean choice) {
			if (leftVariables.remove(id)) {
				// remove yielded true, so it was contained
				if (choice) {
					// irrelevant impl
					isRelevant = false;
				} else {
					if (leftVariables.isEmpty()) {
						for (int id2 : rightVariables) {
							fixVariable(id2, false);
						}
					}
				}
			}
			if (rightVariables.remove(id)) {
				// was contained
				if (choice) {
					// other vars are irrelevant -> remove
					rightVariables.clear();
					rightVariables.add(id);
				} else {
					if (rightVariables.isEmpty()) {
						// Implication is fulfilled -> remove
						isRelevant = false;
					}
				}
			}
		}

		@Override
		public String toString() {
			List<String> termStringsLeft = new LinkedList<String>();
			this.leftVariables.stream().forEach((variableId) -> {
				termStringsLeft.add(getVariable(variableId));
			});
			List<String> termStringsRight = new LinkedList<String>();
			this.rightVariables.stream().forEach((variableId) -> {
				termStringsRight.add("-" + getVariable(variableId));
			});
			return "NegativeImplication(" + name + ")" + "-(" + String.join(" V ", termStringsLeft) + ") -> "
					+ String.join(" ^ ", termStringsRight);
		}
	}

	private class Exclusion {
		private IntLinkedOpenHashSet variableGroup;
		private boolean isRelevant = true;
		private String name = "";

		private void generateILPConstraint() {
			ILPLinearExpression expr = createLinearExpression();
			variableGroup.stream().forEach(v -> {
				expr.addTerm(v, 1);
			});
			ILPConstraint constr = addConstraint(expr, Comparator.le, 1.0, name);
			generatedConstraints.add(constr);
		}

		private void fixExclVariable(int id, boolean choice) {
			if (variableGroup.remove(id)) {
				// remove yielded true, so it was contained
				if (choice) {
					for (int id2 : variableGroup) {
						// all other vars cannot be chosen
						fixVariable(id2, false);
					}
					isRelevant = false;
				} else {
					if (variableGroup.isEmpty()) {
						isRelevant = false;
					}
				}
			}
		}

		@Override
		public String toString() {
			List<String> termStrings = new LinkedList<String>();
			this.variableGroup.stream().forEach((variableId) -> {
				termStrings.add(getVariable(variableId));
			});
			return "Exclusion(" + name + ") " + String.join(" XOR ", termStrings);
		}
	}

	private void generateContraints() {
		System.out.println("Generating ILP constraints");
		this.removeConstraints(generatedConstraints);
		generatedConstraints.clear();

		for (it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<ObjectLinkedOpenHashSet<Implication>> implOfSameVar : implications
				.int2ObjectEntrySet()) {
			ILPLinearExpression expr = createLinearExpression();
			String name = "IMPL" + this.getVariable(implOfSameVar.getIntKey());
			for (Implication impl : implOfSameVar.getValue()) {
				if (impl.isRelevant) {
					impl.generateILPConstraint(expr);
					name = impl.name;
				}
			}
			ILPConstraint constr = addConstraint(expr, Comparator.le, 0.0, name);
			generatedConstraints.add(constr);
		}
		for (NegativeImplication negImpl : negativeImplications) {
			if (negImpl.isRelevant)
				negImpl.generateILPConstraint();
		}
		for (Exclusion excl : exclusions) {
			if (excl.isRelevant)
				excl.generateILPConstraint();
		}
	}

	private void applyFixedVariablesToImplications(int[] fixedVariables) {
		ObjectIterator<it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<ObjectLinkedOpenHashSet<Implication>>> implMapIt = implications
				.int2ObjectEntrySet().iterator();
		while (implMapIt.hasNext()) {
			it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<ObjectLinkedOpenHashSet<Implication>> entry = implMapIt
					.next();
			ObjectListIterator<Implication> implIt = entry.getValue().iterator();
			while (implIt.hasNext()) {
				Implication impl = implIt.next();
				for (int id : fixedVariables) {
					boolean choice = getFixedVariableValues().get(id) == 1;
					impl.fixImplVariable(id, choice);
				}
				if (!impl.isRelevant)
					implIt.remove();
			}
			if (entry.getValue().isEmpty())
				implMapIt.remove();
		}
	}

	private void applyFixedVariablesToNegativeImplications(int[] fixedVariables) {
		ObjectListIterator<NegativeImplication> negImplIt = negativeImplications.iterator();
		while (negImplIt.hasNext()) {
			NegativeImplication impl = negImplIt.next();
			for (int id : fixedVariables) {
				boolean choice = getFixedVariableValues().get(id) == 1;
				impl.fixImplVariable(id, choice);
			}
			if (!impl.isRelevant)
				negImplIt.remove();
		}
	}

	private void applyFixedVariablesToExclusions(int[] fixedVariables) {
		ObjectListIterator<Exclusion> exclIt = exclusions.iterator();
		while (exclIt.hasNext()) {
			Exclusion excl = exclIt.next();
			for (int id : fixedVariables) {
				boolean choice = getFixedVariableValues().get(id) == 1;
				excl.fixExclVariable(id, choice);
			}
			if (!excl.isRelevant)
				exclIt.remove();
		}
	}

	@Override
	protected void applyLazyFixedVariables() {
		if (getLazyFixedVariables().isEmpty())
			return;
		this.removeConstraints(generatedConstraints);
		generatedConstraints.clear();
		while (!getLazyFixedVariables().isEmpty()) {
			int[] ids = getLazyFixedVariables().toIntArray();
			super.applyLazyFixedVariables();
			applyFixedVariablesToExclusions(ids);
			applyFixedVariablesToImplications(ids);
			applyFixedVariablesToNegativeImplications(ids);
		}
		this.generateContraints();
	}

	public void fixVariable(String variableName, boolean choice) {
		this.fixVariable(this.getVariableId(variableName), choice);
	}

	protected void fixVariable(int variableId, boolean choice) {
		super.fixVariable(variableId, choice ? 1 : 0);
	}

	@Override
	public void fixVariable(String variableName, int value) {
		switch (value) {
		case 1:
			fixVariable(variableName, true);
			break;
		case 0:
			fixVariable(variableName, false);
			break;
		default:
			throw new IllegalArgumentException("Only 0 or 1 are supported in binary ILP problems");
		}
	}

	public void addImplication(String leftSide, Collection<String> rightSide, String name) {
		Implication impl = new Implication();
		impl.name = name;
		impl.leftVariable = getVariableId(leftSide);
		implications.putIfAbsent(impl.leftVariable, new ObjectLinkedOpenHashSet<>());
		implications.get(impl.leftVariable).add(impl);
		impl.rightVariables = new IntOpenHashSet(
				rightSide.stream().map(s -> getVariableId(s)).collect(Collectors.toList()));
		for (Entry id : this.getFixedVariableValues().int2IntEntrySet()) {
			impl.fixImplVariable(id.getIntKey(), id.getIntValue() == 1);
		}
	}

	public void addExclusion(Collection<String> variables, String name) {
		Exclusion excl = new Exclusion();
		excl.name = name;
		exclusions.add(excl);
		excl.variableGroup = new IntLinkedOpenHashSet(
				variables.stream().map(s -> getVariableId(s)).collect(Collectors.toList()));
		for (Entry id : this.getFixedVariableValues().int2IntEntrySet()) {
			excl.fixExclVariable(id.getIntKey(), id.getIntValue() == 1);
		}
	}

	public void addNegativeImplication(Collection<String> leftSide, Collection<String> rightSide, String name) {
		NegativeImplication impl = new NegativeImplication();
		impl.name = name;
		negativeImplications.add(impl);
		impl.leftVariables = new IntOpenHashSet(
				leftSide.stream().map(s -> getVariableId(s)).collect(Collectors.toList()));
		impl.rightVariables = new IntOpenHashSet(
				rightSide.stream().map(s -> getVariableId(s)).collect(Collectors.toList()));
		for (Entry id : this.getFixedVariableValues().int2IntEntrySet()) {
			impl.fixImplVariable(id.getIntKey(), id.getIntValue() == 1);
		}
	}

	@Override
	public Collection<ILPConstraint> getConstraints() {
		if (generatedConstraints.isEmpty() && getLazyFixedVariables().isEmpty())
			this.generateContraints();
		return super.getConstraints();
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (Exclusion excl : exclusions) {
			b.append("\n" + excl);
		}
		for (it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<ObjectLinkedOpenHashSet<Implication>> implOfSameVar : implications
				.int2ObjectEntrySet()) {
			for (Implication impl : implOfSameVar.getValue()) {
				if (impl.isRelevant) {
					b.append("\n" + impl);
				}
			}
		}
		for (NegativeImplication impl : negativeImplications) {
			b.append("\n" + impl);
		}

		return super.toString() + b.toString();
	}

}
