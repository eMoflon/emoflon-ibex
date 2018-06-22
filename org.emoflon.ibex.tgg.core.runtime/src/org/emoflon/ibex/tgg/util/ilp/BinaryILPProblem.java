package org.emoflon.ibex.tgg.util.ilp;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

/**
 * This class is an extension of the generic {@link ILPProblem} for the more
 * specific binary ILP problems in which variables are either 1 or 0. <br>
 * 
 * The class contains basic constructs that are often used in boolean
 * expression. Therefore when defining the ILP it is not necessary to think
 * about how to transform the boolean expressions into constraints, as these are
 * automatically translated into suitable ILP constraints. When fixing variables
 * these boolean constructs help determining other variables that can already be
 * fixed as well, which simplifies the ILP problem and reduces the time needed
 * for computing a valid solution.
 *
 */
public final class BinaryILPProblem extends ILPProblem {
	/**
	 * This map contains all defined implications, sorted by the left side variable.
	 */
	private Int2ObjectOpenHashMap<LinkedList<Implication>> implications = new Int2ObjectOpenHashMap<>();
	/**
	 * A list containing all negative implications
	 */
	private LinkedList<NegativeImplication> negativeImplications = new LinkedList<>();
	/**
	 * A list containing all exclusions
	 */
	private LinkedList<Exclusion> exclusions = new LinkedList<>();
	/**
	 * A set of all constraints that were generated to express the boolean
	 * constructs.
	 */
	private ObjectLinkedOpenHashSet<ILPConstraint> generatedConstraints = new ObjectLinkedOpenHashSet<>();

	/**
	 * All fixed variables that have been set to 1
	 */
	private IntLinkedOpenHashSet positiveChoices = new IntLinkedOpenHashSet();
	/**
	 * All fixed variables that have been set to 0
	 */
	private IntLinkedOpenHashSet negativeChoices = new IntLinkedOpenHashSet();
	/**
	 * Fixed but not yet applied variables set to 1
	 */
	private IntLinkedOpenHashSet lazyPositiveChoices = new IntLinkedOpenHashSet();
	/**
	 * Fixed but not yet applied variables set to 0
	 */
	private IntLinkedOpenHashSet lazyNegativeChoices = new IntLinkedOpenHashSet();

	/**
	 * Contains for each variable the list of constraints the variable is contained
	 * in. This makes fixing variables very efficient, but costs memory
	 */
	private Int2ObjectOpenHashMap<LinkedList<BinaryConstraint>> variableIdsToContainingConstraints = new Int2ObjectOpenHashMap<>();

	/**
	 * Creates a new binary ILP problem
	 */
	BinaryILPProblem() {
	}

	/**
	 * Superclass for all binary expressions that can be transformed into ILP
	 * constraints
	 */
	private abstract class BinaryConstraint {
		/**
		 * If the constraint is still needed or is superfluous
		 */
		private boolean isRelevant = true;
		/**
		 * The name of the constraint
		 */
		private final String name;

		/**
		 * Creates a new binary constraint
		 * 
		 * @param name The name of the constraint
		 */
		private BinaryConstraint(String name) {
			this.name = name;
		}

		/**
		 * @return whether the expression is still relevant, or superfluous
		 */
		final boolean isRelevant() {
			return isRelevant;
		}

		/**
		 * @param Sets the relevance status
		 */
		final void setRelevant(boolean isRelevant) {
			this.isRelevant = isRelevant;
		}

		/**
		 * @return the name
		 */
		final String getName() {
			return name;
		}

		/**
		 * Fixes the given variable. If the value of other variables is obvious due to
		 * the fixes, they are fixed as well.
		 * 
		 * @param variableId ID of the variable
		 * @param choice     value of the variable
		 * @return whether the constraint is still relevant
		 */
		abstract boolean fixVariable(int variableId, boolean choice);

		/**
		 * Fixes all given variables. If the value of other variables is obvious due to
		 * the fixes, they are fixed as well.
		 * 
		 * @param positiveChoices Variables that have been set to 1
		 * @param negativeChoices Variables that have been set to 0
		 * @return whether the constraint is still relevant
		 */
		abstract boolean fixVariables(IntCollection positiveChoices, IntCollection negativeChoices);

		/**
		 * Generates an ILP constraint for the binary constraint.
		 */
		abstract void generateILPConstraint();

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + (isRelevant ? 1231 : 1237);
			return result;
		}

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
			BinaryConstraint other = (BinaryConstraint) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (isRelevant != other.isRelevant) {
				return false;
			}
			return true;
		}

		private BinaryILPProblem getOuterType() {
			return BinaryILPProblem.this;
		}
	}

	/**
	 * A constraint of the form x -> a v b <br>
	 * If the variable on the left side is chosen, one of the variables on the right
	 * side has to be chosen as well.
	 */
	public final class Implication extends BinaryConstraint {
		/**
		 * Variable on the left side of the implication
		 */
		private final int leftVariable;
		/**
		 * OR-connected variables on the right side of the implication
		 */
		private final IntOpenHashSet rightVariables;

		/**
		 * Creates a new implication from the given variables
		 * 
		 * @param leftVariable   variable on the left side of the implication
		 * @param rightVariables variables on the right side of the implication
		 * @param name           the name of the implication
		 */
		private Implication(int leftVariable, IntOpenHashSet rightVariables, String name) {
			super(name);
			this.leftVariable = leftVariable;
			this.rightVariables = rightVariables;

			this.fixVariables(new IntArraySet(positiveChoices), new IntArraySet(negativeChoices));

			if (this.isRelevant()) {
				implications.get(this.leftVariable).add(this);
				variableIdsToContainingConstraints.get(this.leftVariable).add(this);
				for (int id : this.rightVariables) {
					variableIdsToContainingConstraints.get(id).add(this);
				}
			}
		}

		/**
		 * Adds the constraint to the already existing expression. This is used to fuse
		 * all implications over the same variable into one expression.
		 * 
		 * @param expr The expression to add the constraint to
		 */
		private void generateILPConstraint(ILPLinearExpression expr) {
			expr.addTerm(leftVariable, 1);
			rightVariables.stream().forEach(v -> {
				expr.addTerm(v, -1);
			});
		}

		@Override
		boolean fixVariable(int id, boolean choice) {
			if (leftVariable == id) {
				if (!choice) { // left side is false
					this.setRelevant(false);
					return false;
				} else {
					// left side of the implication is true -> one of the right variables has to be
					// true
					new Exclusion(rightVariables, Integer.MAX_VALUE, 1, this.getName());
					this.setRelevant(false);
					return false;
				}
			}
			if (rightVariables.remove(id)) {
				// remove yielded true, so it was contained
				if (choice) {
					// implication is fulfilled -> remove
					this.setRelevant(false);
					return false;
				} else {
					if (rightVariables.isEmpty()) {
						// Implication cannot be fulfilled
						BinaryILPProblem.this.fixVariable(leftVariable, false);
						this.setRelevant(false);
						return false;
					}
				}
			}
			return true;
		}

		@Override
		boolean fixVariables(IntCollection positiveChoices, IntCollection negativeChoices) {
			if (negativeChoices.contains(leftVariable)) {
				this.setRelevant(false);
				return false;
			}

			for (int id : positiveChoices) {
				if (rightVariables.contains(id)) {
					this.setRelevant(false);
					return false;
				}
			}

			if (rightVariables.removeAll(negativeChoices)) {
				if (rightVariables.isEmpty()) {
					// Implication cannot be fulfilled
					BinaryILPProblem.this.fixVariable(leftVariable, false);
					this.setRelevant(false);
					return false;
				}
			}

			if (positiveChoices.contains(leftVariable)) {
				// left side of the implication is true -> one of the right variables has to be
				// true
				new Exclusion(rightVariables, Integer.MAX_VALUE, 1, this.getName());
				this.setRelevant(false);
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			List<String> termStrings = new LinkedList<String>();
			this.rightVariables.stream().forEach((variableId) -> {
				termStrings.add(getVariable(variableId));
			});
			return "Implication(" + getName() + ")" + getVariable(leftVariable) + " -> "
					+ String.join(" V ", termStrings);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + leftVariable;
			result = prime * result + ((rightVariables == null) ? 0 : rightVariables.hashCode());
			return result;
		}

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

		@Override
		void generateILPConstraint() {
			ILPLinearExpression expr = createLinearExpression();
			expr.addTerm(leftVariable, 1);
			rightVariables.stream().forEach(v -> {
				expr.addTerm(v, -1);
			});
			ILPConstraint constr = new ILPConstraint(expr, Comparator.le, 0.0, getName());
			addConstraint(constr);
			generatedConstraints.add(constr);

		}
	}

	/**
	 * A constraint of the form not(x V y) -> not(a) ^ not(b) <br>
	 * If none of the variables on the left side is chosen, none of the variables on
	 * the right side can be chosen
	 */
	public final class NegativeImplication extends BinaryConstraint {
		/**
		 * Variables on the left side of the implication
		 */
		private final IntOpenHashSet leftVariables;
		/**
		 * Variables on the right side of the implication
		 */
		private final IntOpenHashSet rightVariables;

		/**
		 * Adds a negative implication
		 * 
		 * @param leftVariables  Variables on the left side of the implication
		 * @param rightVariables Variables on the right side of the implication
		 * @param name           Name of the implication
		 */
		private NegativeImplication(IntOpenHashSet leftVariables, IntOpenHashSet rightVariables, String name) {
			super(name);

			this.leftVariables = leftVariables;
			this.rightVariables = rightVariables;

			this.fixVariables(new IntArraySet(positiveChoices), new IntArraySet(negativeChoices));

			if (this.isRelevant()) {
				negativeImplications.add(this);
				for (int id : this.leftVariables) {
					variableIdsToContainingConstraints.get(id).add(this);
				}
				for (int id : this.rightVariables) {
					variableIdsToContainingConstraints.get(id).add(this);
				}
			}
		}

		@Override
		void generateILPConstraint() {
			ILPLinearExpression expr = createLinearExpression();
			leftVariables.stream().forEach(v -> {
				expr.addTerm(v, -rightVariables.size());
			});
			rightVariables.stream().forEach(v -> {
				expr.addTerm(v, 1);
			});
			ILPConstraint constr = new ILPConstraint(expr, Comparator.le, 0.0, getName());
			addConstraint(constr);
			generatedConstraints.add(constr);
		}

		@Override
		boolean fixVariable(int id, boolean choice) {
			if (leftVariables.remove(id)) {
				// remove yielded true, so it was contained
				if (choice) {
					// irrelevant impl
					this.setRelevant(false);
					return false;
				} else {
					if (leftVariables.isEmpty()) {
						for (int id2 : rightVariables) {
							BinaryILPProblem.this.fixVariable(id2, false);
						}
						this.setRelevant(false);
						return false;
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
						this.setRelevant(false);
						return false;
					}
				}
			}
			return true;
		}

		@Override
		boolean fixVariables(IntCollection positiveChoices, IntCollection negativeChoices) {
			if (leftVariables.removeAll(negativeChoices)) {
				if (leftVariables.isEmpty()) {
					for (int id2 : rightVariables) {
						BinaryILPProblem.this.fixVariable(id2, false);
					}
					this.setRelevant(false);
					return false;
				}
			}
			if (rightVariables.removeAll(negativeChoices)) {
				if (rightVariables.isEmpty()) {
					// Implication is fulfilled -> remove
					this.setRelevant(false);
					return false;
				}
			}
			for (int id : positiveChoices) {
				if (leftVariables.contains(id)) {
					this.setRelevant(false);
					return false;
				}
				if (rightVariables.contains(id)) {
					rightVariables.clear();
					rightVariables.add(id);
					break;
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
			return "NegativeImplication(" + getName() + ")" + "-(" + String.join(" V ", termStringsLeft) + ") -> "
					+ String.join(" ^ ", termStringsRight);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((leftVariables == null) ? 0 : leftVariables.hashCode());
			result = prime * result + ((rightVariables == null) ? 0 : rightVariables.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!super.equals(obj)) {
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
	 * A set of variables of which only a certain number can be chosen.
	 */
	public final class Exclusion extends BinaryConstraint {
		/**
		 * Set of variables
		 */
		private final IntOpenHashSet variableGroup;
		/**
		 * Number of variables that can be chosen
		 */
		private int allowed;

		/**
		 * Number of variables that have to be chosen
		 */
		private int required = 0;

		/**
		 * Creates a new Exclusion from the given variables. Applies already fixed
		 * variables and adds the exclusion to the collections
		 * 
		 * @param variableGroup Variables in this exclusion
		 * @param allowed       Maximum number of variables to choose
		 * @param required      Required number of variables to choose
		 * @param name          Name of the exclusion
		 */
		private Exclusion(IntOpenHashSet variableGroup, int allowed, int required, String name) {
			super(name);
			if (required > allowed || required > variableGroup.size()) {
				throw new RuntimeException("Cannot fulfill number of required choices for this exclusion");
			}
			this.variableGroup = variableGroup;
			this.allowed = allowed;
			this.required = required;
			this.fixVariables(new IntArraySet(positiveChoices), new IntArraySet(negativeChoices));
			if (this.isRelevant()) {
				exclusions.add(this);
				for (int id : this.variableGroup) {
					variableIdsToContainingConstraints.get(id).add(this);
				}
			}
		}

		@Override
		void generateILPConstraint() {
			if (variableGroup.size() > allowed) {
				// generate constraint for choosing at most allowed vars
				ILPLinearExpression expr = createLinearExpression();
				variableGroup.stream().forEach(v -> {
					expr.addTerm(v, 1);
				});
				ILPConstraint constr = new ILPConstraint(expr, Comparator.le, allowed, getName());
				addConstraint(constr);
				generatedConstraints.add(constr);
			}

			if (required > 0) {
				// generate constraint for choosing at least required vars
				ILPLinearExpression expr2 = createLinearExpression();
				variableGroup.stream().forEach(v -> {
					expr2.addTerm(v, -1);
				});
				ILPConstraint constr2 = new ILPConstraint(expr2, Comparator.le, -required, getName());
				addConstraint(constr2);
				generatedConstraints.add(constr2);
			}
		}

		@Override
		boolean fixVariable(int id, boolean choice) {
			if (variableGroup.remove(id)) {
				// remove yielded true, so it was contained
				if (choice) {
					--required;
					if (--allowed == 0) {
						for (int id2 : variableGroup) {
							// all other vars cannot be chosen
							BinaryILPProblem.this.fixVariable(id2, false);
						}
						this.setRelevant(false);
						return false;
					}
				} else if (variableGroup.size() < required) {
					throw new RuntimeException("Cannot fulfill number of required choices for this exclusion");
				}
			}

			if (variableGroup.isEmpty() || (required <= 0 && variableGroup.size() <= allowed)) {
				// no further variable choices required and all other vars can be chosen
				this.setRelevant(false);
				return false;
			}

			return true;
		}

		@Override
		boolean fixVariables(IntCollection positiveChoices, IntCollection negativeChoices) {
			if (variableGroup.removeAll(negativeChoices)) {
				if (variableGroup.size() < required) {
					throw new RuntimeException("Cannot fulfill number of required choices for this exclusion");
				}
			}

			for (int id : positiveChoices) {
				if (variableGroup.remove(id)) {
					required--;
					if (--allowed == 0) {
						for (int id2 : variableGroup) {
							// all other vars cannot be chosen
							BinaryILPProblem.this.fixVariable(id2, false);
						}
						this.setRelevant(false);
						return false;
					}
				}
			}

			if (variableGroup.isEmpty() || (required <= 0 && variableGroup.size() <= allowed)) {
				// no further variable choices required and all other vars can be chosen
				this.setRelevant(false);
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			List<String> termStrings = new LinkedList<String>();
			this.variableGroup.stream().forEach((variableId) -> {
				termStrings.add(getVariable(variableId));
			});
			return "Exclusion(" + getName() + ", [" + required + ", " + allowed + "]" + ") {"
					+ String.join(", ", termStrings) + "}";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + getOuterType().hashCode();
			result = prime * result + allowed;
			result = prime * result + ((variableGroup == null) ? 0 : variableGroup.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!super.equals(obj)) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Exclusion other = (Exclusion) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (allowed != other.allowed) {
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

	/**
	 * Generates ILP constraints for the defined boolean expressions
	 */
	private void generateContraints() {
		ILPSolver.logger.debug("Generating ILP constraints");
		this.removeConstraints(generatedConstraints);
		generatedConstraints.clear();

		for (it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<LinkedList<Implication>> implOfSameVar : implications
				.int2ObjectEntrySet()) {
			ILPLinearExpression expr = createLinearExpression();
			String name = "IMPL" + this.getVariable(implOfSameVar.getIntKey());
			for (Implication impl : implOfSameVar.getValue()) {
				if (impl.isRelevant()) {
					impl.generateILPConstraint(expr);
					name = impl.getName();
				}
			}
			ILPConstraint constr = new ILPConstraint(expr, Comparator.le, 0.0, name);
			addConstraint(constr);
			generatedConstraints.add(constr);
		}
		for (NegativeImplication negImpl : negativeImplications) {
			if (negImpl.isRelevant())
				negImpl.generateILPConstraint();
		}
		for (Exclusion excl : exclusions) {
			if (excl.isRelevant())
				excl.generateILPConstraint();
		}
	}

	@Override
	protected void applyLazyFixedVariables() {
		if (getLazyFixedVariables().isEmpty())
			return;
		this.removeConstraints(generatedConstraints);
		generatedConstraints.clear();
		while (!(lazyPositiveChoices.isEmpty() && lazyNegativeChoices.isEmpty())) {
			IntArrayList positiveChoices = new IntArrayList(lazyPositiveChoices);
			IntArrayList negativeChoices = new IntArrayList(lazyNegativeChoices);
			lazyPositiveChoices.clear();
			lazyNegativeChoices.clear();
			for (int id : positiveChoices) {
				for (BinaryConstraint constraint : this.variableIdsToContainingConstraints.remove(id)) {
					if (constraint.isRelevant) {
						constraint.isRelevant = constraint.fixVariable(id, true);
					}
				}
			}
			for (int id : negativeChoices) {
				this.implications.remove(id);
				for (BinaryConstraint constraint : this.variableIdsToContainingConstraints.remove(id)) {
					if (constraint.isRelevant) {
						constraint.isRelevant = constraint.fixVariable(id, false);
					}
				}
			}
		}
		super.applyLazyFixedVariables();
	}

	@Override
	int createNewVariable(String variableName) {
		int variableId = super.createNewVariable(variableName);
		this.variableIdsToContainingConstraints.put(variableId, new LinkedList<>());
		return variableId;
	}

	/**
	 * Sets the variable to the given value
	 * 
	 * @param variableName Name of the variable to fix
	 * @param choice       Value of the variable
	 */
	public void fixVariable(String variableName, boolean choice) {
		this.fixVariable(this.getVariableId(variableName), choice);
	}

	/**
	 * Sets the variable to the given value
	 * 
	 * @param variableId ID of the variable to fix
	 * @param choice     Value of the variable
	 */
	protected void fixVariable(int variableId, boolean choice) {
		super.fixVariable(variableId, choice ? 1 : 0);
		if (choice) {
			if (!positiveChoices.contains(variableId)) {
				positiveChoices.add(variableId);
				lazyPositiveChoices.add(variableId);
			}
		} else {
			if (!negativeChoices.contains(variableId)) {
				negativeChoices.add(variableId);
				lazyNegativeChoices.add(variableId);
			}
		}
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

	public Boolean getFixedBooleanVariable(String variableName) {
		return this.getFixedBooleanVariable(this.getVariableId(variableName));
	}

	protected Boolean getFixedBooleanVariable(int variable) {
		Integer value = this.getFixedVariable(variable);
		if (value == null)
			return null;
		switch (value) {
		case 1:
			return true;
		case 0:
			return false;
		default:
			throw new IllegalArgumentException("Only 0 or 1 are supported in binary ILP problems");
		}
	}

	/**
	 * Adds a constraint of the form x -> a v b <br>
	 * If the variable on the left side is chosen, one of the variables on the right
	 * side has to be chosen as well.
	 * 
	 * @param leftSide  The name of the variable on the left side of the implication
	 * @param rightSide The names of the variables on the right side of the
	 *                  implication
	 * @param name      The name of the implication
	 * @return The implication that has been created
	 */
	public Implication addImplication(String leftSide, Collection<String> rightSide, String name) {
		return new Implication(getVariableId(leftSide),
				new IntOpenHashSet(rightSide.stream().map(s -> getVariableId(s)).collect(Collectors.toList())), name);
	}

	/**
	 * Adds a constraint for a set of variables of which only a certain number can
	 * be chosen.
	 * 
	 * @param variables The variables contained in the exclusion
	 * @param name      The name of the exclusion
	 * @param allowed   The number of variables that can be chosen
	 * @return The created exclusion
	 */
	public Exclusion addExclusion(Collection<String> variables, String name, int allowed) {
		return this.addExclusion(variables, name, allowed, 0);
	}

	/**
	 * Adds a constraint for a set of variables of which only a certain number can
	 * be chosen.
	 * 
	 * @param variables The variables contained in the exclusion
	 * @param name      The name of the exclusion
	 * @param allowed   The number of variables that can be chosen
	 * @param required  The minimum number of variables that have to be chosen
	 * @return The created exclusion
	 */
	public Exclusion addExclusion(Collection<String> variables, String name, int allowed, int required) {
		return new Exclusion(
				new IntOpenHashSet(variables.stream().map(s -> getVariableId(s)).collect(Collectors.toList())), allowed,
				required, name);
	}

	/**
	 * Adds a constraint for a set of variables of which only one can be chosen
	 * 
	 * @param variables The variables contained in the exclusion
	 * @param name      The name of the exclusion
	 * @return The created exclusion
	 */
	public Exclusion addExclusion(Collection<String> variables, String name) {
		return addExclusion(variables, name, 1);
	}

	/**
	 * Adds a negative implication: A constraint of the form not(x V y) -> not(a) ^
	 * not(b) <br>
	 * If none of the variables on the left side is chosen, none of the variables on
	 * the right side can be chosen
	 * 
	 * @param leftSide  Variables on the left side of the implication
	 * @param rightSide Variables on the right side of the implication
	 * @param name      The name of the implication
	 * @return the created implication
	 */
	public NegativeImplication addNegativeImplication(Collection<String> leftSide, Collection<String> rightSide,
			String name) {
		return new NegativeImplication(
				new IntOpenHashSet(leftSide.stream().map(s -> getVariableId(s)).collect(Collectors.toList())),
				new IntOpenHashSet(rightSide.stream().map(s -> getVariableId(s)).collect(Collectors.toList())), name);
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
			if (excl.isRelevant())
				b.append("\n" + excl);
		}
		for (it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<LinkedList<Implication>> implOfSameVar : implications
				.int2ObjectEntrySet()) {
			for (Implication impl : implOfSameVar.getValue()) {
				if (impl.isRelevant())
					b.append("\n" + impl);
			}
		}
		for (NegativeImplication impl : negativeImplications) {
			if (impl.isRelevant())
				b.append("\n" + impl);
		}

		return super.toString() + b.toString();
	}
}
