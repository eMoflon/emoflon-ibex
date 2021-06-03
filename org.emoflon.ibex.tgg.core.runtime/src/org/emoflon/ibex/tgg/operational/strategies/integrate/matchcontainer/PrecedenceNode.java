package org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.util.ConsoleUtil;

public class PrecedenceNode {

	private final ITGGMatch match;
	private boolean broken = false;

	private Set<PrecedenceNode> requires = Collections.synchronizedSet(cfactory.createObjectSet());
	private Set<PrecedenceNode> requiredBy = Collections.synchronizedSet(cfactory.createObjectSet());

	private Set<PrecedenceNode> toBeRolledBackBy = Collections.synchronizedSet(cfactory.createObjectSet());

	public PrecedenceNode(ITGGMatch match) {
		this.match = match;
	}

	public boolean isBroken() {
		return broken;
	}

	public void setBroken(boolean broken) {
		this.broken = broken;
	}

	public ITGGMatch getMatch() {
		return match;
	}

	public Set<PrecedenceNode> getRequires() {
		return requires;
	}

	public Set<PrecedenceNode> getRequiredBy() {
		return requiredBy;
	}

	public Set<PrecedenceNode> getToBeRolledBackBy() {
		return toBeRolledBackBy;
	}

	public void addRequires(PrecedenceNode node) {
		this.requires.add(node);
		node.requiredBy.add(this);
	}

	public void addRequiredBy(PrecedenceNode node) {
		this.requiredBy.add(node);
		node.requires.add(this);
	}

	public void addToBeRolledBackBy(PrecedenceNode node) {
		this.toBeRolledBackBy.add(node);
	}

	public void removeRequires(PrecedenceNode node) {
		this.requires.remove(node);
		node.requiredBy.remove(this);
	}

	public void removeRequiredBy(PrecedenceNode node) {
		this.requiredBy.remove(node);
		node.requires.remove(this);
	}

	public void removeToBeRolledBackBy(PrecedenceNode node) {
		this.toBeRolledBackBy.remove(node);
	}

	public void clearRequires() {
		for (PrecedenceNode node : this.requires)
			node.requiredBy.remove(this);
		this.requires.clear();
	}

	public void clearRequiredBy() {
		for (PrecedenceNode node : this.requiredBy)
			node.requires.remove(this);
		this.requiredBy.clear();
	}

	public void clearToBeRolledBackBy() {
		this.toBeRolledBackBy.clear();
	}

	//// UTILS ////

	/**
	 * Executes the specified <code>action</code> for all nodes that are (transitively)
	 * required by this node.<br>
	 * The first predicate of the <code>action</code> is the actual node, while the second one
	 * is the hierarchically previously processed node. If the <code>action</code> returns
	 * <code>false</code>, the transition of the current branch stops at this point.
	 * 
	 * @param action the action
	 */
	public void forAllRequires(BiPredicate<? super PrecedenceNode, ? super PrecedenceNode> action) {
		Set<PrecedenceNode> processed = cfactory.createObjectSet();
		forAllRequires(this, action, processed);
	}

	private void forAllRequires(PrecedenceNode node, BiPredicate<? super PrecedenceNode, ? super PrecedenceNode> action, Set<PrecedenceNode> processed) {
		for (PrecedenceNode n : node.getRequires()) {
			if (!processed.contains(n)) {
				processed.add(n);
				if (action.test(n, node))
					forAllRequires(n, action, processed);
			}
		}
	}

	/**
	 * Executes the specified <code>action</code> for all nodes that (transitively) requires
	 * this node.<br>
	 * The first predicate of the <code>action</code> is the actual node, while the second one
	 * is the hierarchically previously processed node. If the <code>action</code> returns
	 * <code>false</code>, the transition of the current branch stops at this point.
	 * 
	 * @param action the action
	 */
	public void forAllRequiredBy(BiPredicate<? super PrecedenceNode, ? super PrecedenceNode> action) {
		Set<PrecedenceNode> processed = cfactory.createObjectSet();
		forAllRequiredBy(this, action, processed);
	}

	private void forAllRequiredBy(PrecedenceNode node, BiPredicate<? super PrecedenceNode, ? super PrecedenceNode> action, Set<PrecedenceNode> processed) {
		for (PrecedenceNode n : node.getRequiredBy()) {
			if (!processed.contains(n)) {
				processed.add(n);
				if (action.test(n, node))
					forAllRequiredBy(n, action, processed);
			}
		}
	}

	/**
	 * Executes the specified <code>action</code> for all nodes that (transitively) requires
	 * this node.<br>
	 * <b>Important note:</b> this implementation may visit some nodes multiple times. For one
	 * time visitation use {@link PrecedenceNode#forAllRequiredBy(BiPredicate)}.<br>
	 * The first predicate of the <code>action</code> is the actual node, while the second one
	 * is the hierarchically previously processed node. If the <code>action</code> returns
	 * <code>false</code>, the transition of the current branch stops at this point.
	 * 
	 * @param action the action
	 */
	public void forAllRequiredByMultiVisit(BiPredicate<? super PrecedenceNode, ? super PrecedenceNode> action) {
		forAllRequiredBy(this, action);
	}

	private void forAllRequiredBy(PrecedenceNode node, BiPredicate<? super PrecedenceNode, ? super PrecedenceNode> action) {
		for (PrecedenceNode n : node.getRequiredBy()) {
			if (action.test(n, node))
				forAllRequiredBy(n, action);
		}
	}

	/**
	 * Executes the specified <code>action</code> for all nodes that directly or indirectly
	 * are going to roll back this node.<br>
	 * The first predicate of the <code>action</code> is the actual node, while the second one
	 * is the hierarchically previously processed node. If the <code>action</code> returns
	 * <code>false</code>, the transition of the current branch stops at this point.
	 * 
	 * @param action the action
	 */
	public void forAllToBeRolledBackBy(BiPredicate<? super PrecedenceNode, ? super PrecedenceNode> action) {
		Set<PrecedenceNode> processed = cfactory.createObjectSet();
		forAllToBeRolledBackBy(this, action, processed);
	}

	private void forAllToBeRolledBackBy(PrecedenceNode node, BiPredicate<? super PrecedenceNode, ? super PrecedenceNode> action,
			Set<PrecedenceNode> processed) {
		for (PrecedenceNode n : node.getToBeRolledBackBy()) {
			if (!processed.contains(n)) {
				processed.add(n);
				if (action.test(n, node))
					forAllToBeRolledBackBy(n, action, processed);
			}
		}
	}

	public List<PrecedenceNode> computeSortedRollBackCauses() {
		List<PrecedenceNode> rollBackCauses = new LinkedList<>();

		rollBackCauses.add(this);
		this.forAllToBeRolledBackBy((act, pre) -> {
			if (act.isBroken())
				rollBackCauses.add(act);
			return true;
		});

		return rollBackCauses;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("PrecedenceNode [\n");
		b.append(ConsoleUtil.indent(print(), 2, true));
		b.append("\n]");
		return b.toString();
	}

	private String print() {
		StringBuilder b = new StringBuilder();

		b.append("match: ");
		b.append(ConsoleUtil.indent(match.toString(), 7, false));

		b.append("\nbroken: ");
		b.append(broken);

		b.append("\ntoBeRolledBackBy: ");
		b.append(toBeRolledBackBy.size());
		b.append(" node" + (toBeRolledBackBy.size() == 1 ? "" : "s"));

		b.append("\nrequires: ");
		b.append(requires.size());
		b.append(" node" + (requires.size() == 1 ? "" : "s"));

		b.append("\nrequiredBy: ");
		b.append(requiredBy.size());
		b.append(" node" + (requiredBy.size() == 1 ? "" : "s"));

		return b.toString();
	}

}
