package org.emoflon.ibex.tgg.util.ilp;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import it.unimi.dsi.fastutil.ints.Int2IntMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

public final class BinaryILPProblem extends ILPProblem {
	private Int2ObjectOpenHashMap<LinkedList<Implication>> implications = new Int2ObjectOpenHashMap<>();
	private LinkedList<NegativeImplication> negativeImplications = new LinkedList<>();
	private LinkedList<Exclusion> exclusions = new LinkedList<>();
	private ObjectLinkedOpenHashSet<ILPConstraint> generatedConstraints = new ObjectLinkedOpenHashSet<>();
	private IntLinkedOpenHashSet resultingFixedVariables = null;

	/**
	 * 
	 */
	BinaryILPProblem() {
	}

	/**
	 * A constraint of the form x -> a v b
	 */
	private final class Implication {
		private int leftVariable;
		private IntOpenHashSet rightVariables;
		private String name = "";

		private void generateILPConstraint(ILPLinearExpression expr) {
			expr.addTerm(leftVariable, 1);
			rightVariables.stream().forEach(v -> {
				expr.addTerm(v, -1);
			});
		}

		private boolean fixImplVariable(int id, boolean choice) {
			if (leftVariable == id) {
				if (!choice) { // left side is false
					return false;
				}
			}
			if (rightVariables.remove(id)) {
				// remove yielded true, so it was contained
				if (choice) {
					// implication is fulfilled -> remove
					return false;
				} else {
					if (rightVariables.isEmpty()) {
						// Implication cannot be fulfilled
						fixVariable(leftVariable, false);
					}
				}
			}
			return true;
		}

		@Override
		public String toString() {
			List<String> termStrings = new LinkedList<String>();
			this.rightVariables.stream().forEach((variableId) -> {
				termStrings.add(getVariable(variableId));
			});
			return "Implication(" + name + ")" + getVariable(leftVariable) + " -> " + String.join(" V ", termStrings);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + leftVariable;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((rightVariables == null) ? 0 : rightVariables.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Implication other = (Implication) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (leftVariable != other.leftVariable) {
				return false;
			}
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			if (rightVariables == null) {
				if (other.rightVariables != null) {
					return false;
				}
			} else if (!rightVariables.equals(other.rightVariables)) {
				return false;
			}
			return true;
		}

		private BinaryILPProblem getOuterType() {
			return BinaryILPProblem.this;
		}
	}

	/**
	 * A constraint of the form not(x V y) -> not(a) ^ not(b)
	 */
	private final class NegativeImplication {
		private IntOpenHashSet leftVariables;
		private IntOpenHashSet rightVariables;
		private String name = "";

		private void generateILPConstraint() {
			ILPLinearExpression expr = createLinearExpression();
			leftVariables.stream().forEach(v -> {
				expr.addTerm(v, -rightVariables.size());
			});
			rightVariables.stream().forEach(v -> {
				expr.addTerm(v, 1);
			});
			ILPConstraint constr = new ILPConstraint(expr, Comparator.le, 0.0, name);
			addConstraint(constr);
			generatedConstraints.add(constr);
		}

		private boolean fixImplVariable(int id, boolean choice) {
			if (leftVariables.remove(id)) {
				// remove yielded true, so it was contained
				if (choice) {
					// irrelevant impl
					return false;
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
						return false;
					}
				}
			}
			return true;
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

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((leftVariables == null) ? 0 : leftVariables.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((rightVariables == null) ? 0 : rightVariables.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			NegativeImplication other = (NegativeImplication) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (leftVariables == null) {
				if (other.leftVariables != null) {
					return false;
				}
			} else if (!leftVariables.equals(other.leftVariables)) {
				return false;
			}
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			if (rightVariables == null) {
				if (other.rightVariables != null) {
					return false;
				}
			} else if (!rightVariables.equals(other.rightVariables)) {
				return false;
			}
			return true;
		}

		private BinaryILPProblem getOuterType() {
			return BinaryILPProblem.this;
		}
	}

	private final class Exclusion {
		private IntOpenHashSet variableGroup;
		private String name = "";

		private void generateILPConstraint() {
			ILPLinearExpression expr = createLinearExpression();
			variableGroup.stream().forEach(v -> {
				expr.addTerm(v, 1);
			});
			ILPConstraint constr = new ILPConstraint(expr, Comparator.le, 1.0, name);
			addConstraint(constr);
			generatedConstraints.add(constr);
		}

		private boolean fixExclVariable(int id, boolean choice) {
			if (variableGroup.remove(id)) {
				// remove yielded true, so it was contained
				if (choice) {
					for (int id2 : variableGroup) {
						// all other vars cannot be chosen
						fixVariable(id2, false);
					}
					return false;
				} else {
					if (variableGroup.isEmpty()) {
						return false;
					}
				}
			}
			return true;
		}

		@Override
		public String toString() {
			List<String> termStrings = new LinkedList<String>();
			this.variableGroup.stream().forEach((variableId) -> {
				termStrings.add(getVariable(variableId));
			});
			return "Exclusion(" + name + ") " + String.join(" XOR ", termStrings);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((variableGroup == null) ? 0 : variableGroup.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Exclusion other = (Exclusion) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			if (variableGroup == null) {
				if (other.variableGroup != null) {
					return false;
				}
			} else if (!variableGroup.equals(other.variableGroup)) {
				return false;
			}
			return true;
		}

		private BinaryILPProblem getOuterType() {
			return BinaryILPProblem.this;
		}
		
		
	}

	private void generateContraints() {
		System.out.println("Generating ILP constraints");
		this.removeConstraints(generatedConstraints);
		generatedConstraints.clear();

		for (it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<LinkedList<Implication>> implOfSameVar : implications
				.int2ObjectEntrySet()) {
			ILPLinearExpression expr = createLinearExpression();
			String name = "IMPL" + this.getVariable(implOfSameVar.getIntKey());
			for (Implication impl : implOfSameVar.getValue()) {
				impl.generateILPConstraint(expr);
				name = impl.name;
			}
			ILPConstraint constr = new ILPConstraint(expr, Comparator.le, 0.0, name);
			addConstraint(constr);
			generatedConstraints.add(constr);
		}
		for (NegativeImplication negImpl : negativeImplications) {
			negImpl.generateILPConstraint();
		}
		for (Exclusion excl : exclusions) {
			excl.generateILPConstraint();
		}
	}

	private void applyFixedVariablesToImplications(int[] fixedVariables) {
		ObjectIterator<it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<LinkedList<Implication>>> implMapIt = implications
				.int2ObjectEntrySet().iterator();
		while (implMapIt.hasNext()) {
			it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<LinkedList<Implication>> entry = implMapIt
					.next();
			Iterator<Implication> implIt = entry.getValue().iterator();
			while (implIt.hasNext()) {
				Implication impl = implIt.next();
				for (int id : fixedVariables) {
					boolean choice = getFixedVariableValues().get(id) == 1;
					if(!impl.fixImplVariable(id, choice)) {
						implIt.remove();
						break;
					}
				}
			}
			if (entry.getValue().isEmpty())
				implMapIt.remove();
		}
	}

	private void applyFixedVariablesToNegativeImplications(int[] fixedVariables) {
		Iterator<NegativeImplication> negImplIt = negativeImplications.iterator();
		while (negImplIt.hasNext()) {
			NegativeImplication impl = negImplIt.next();
			for (int id : fixedVariables) {
				boolean choice = getFixedVariableValues().get(id) == 1;
				if(!impl.fixImplVariable(id, choice)) {
					negImplIt.remove();
					break;
				}
			}
		}
	}

	private void applyFixedVariablesToExclusions(int[] fixedVariables) {
		Iterator<Exclusion> exclIt = exclusions.iterator();
		while (exclIt.hasNext()) {
			Exclusion excl = exclIt.next();
			for (int id : fixedVariables) {
				boolean choice = getFixedVariableValues().get(id) == 1;
				if(!excl.fixExclVariable(id, choice)) {
					exclIt.remove();
					break;
				}
			}
		}
	}

	@Override
	protected void applyLazyFixedVariables() {
		if (getLazyFixedVariables().isEmpty())
			return;
		this.removeConstraints(generatedConstraints);
		generatedConstraints.clear();
		resultingFixedVariables = getLazyFixedVariables();
		while (!resultingFixedVariables.isEmpty()) {
			int[] ids = resultingFixedVariables.toIntArray();
			resultingFixedVariables = new IntLinkedOpenHashSet(resultingFixedVariables.size());
			applyFixedVariablesToExclusions(ids);
			applyFixedVariablesToImplications(ids);
			applyFixedVariablesToNegativeImplications(ids);
		}
		super.applyLazyFixedVariables();
	}

	public void fixVariable(String variableName, boolean choice) {
		this.fixVariable(this.getVariableId(variableName), choice);
		// this.applyLazyFixedVariables();
	}

	protected void fixVariable(int variableId, boolean choice) {
		if(resultingFixedVariables != null) {
			resultingFixedVariables.add(variableId);
		}
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
		implications.putIfAbsent(impl.leftVariable, new LinkedList<>());
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
		excl.variableGroup = new IntOpenHashSet(
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
		this.applyLazyFixedVariables();
		if (generatedConstraints.isEmpty())
			this.generateContraints();
		return super.getConstraints();
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (Exclusion excl : exclusions) {
			b.append("\n" + excl);
		}
		for (it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<LinkedList<Implication>> implOfSameVar : implications
				.int2ObjectEntrySet()) {
			for (Implication impl : implOfSameVar.getValue()) {
					b.append("\n" + impl);
			}
		}
		for (NegativeImplication impl : negativeImplications) {
			b.append("\n" + impl);
		}

		return super.toString() + b.toString();
	}

}
