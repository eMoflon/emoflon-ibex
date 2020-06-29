package org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

public class PrecedenceNode {

	private final ITGGMatch match;
	private boolean broken = false;

	private Set<PrecedenceNode> requires = cfactory.createObjectSet();
	private Set<PrecedenceNode> requiredBy = cfactory.createObjectSet();

	private Set<PrecedenceNode> rollbackCauses = cfactory.createObjectSet();
	private Set<PrecedenceNode> rollsBack = cfactory.createObjectSet();

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

	public Set<PrecedenceNode> getRollbackCauses() {
		return rollbackCauses;
	}

	public Set<PrecedenceNode> getRollsBack() {
		return rollsBack;
	}

	public void addRequires(PrecedenceNode node) {
		this.requires.add(node);
		node.requiredBy.add(this);
	}

	public void addRequiredBy(PrecedenceNode node) {
		this.requiredBy.add(node);
		node.requires.add(this);
	}
	
	public void addRollbackCause(PrecedenceNode node) {
		this.rollbackCauses.add(node);
		node.rollsBack.add(this);
	}
	
	public void removeRequires(PrecedenceNode node) {
		this.requires.remove(node);
		node.requiredBy.remove(this);
	}

	public void removeRequiredBy(PrecedenceNode node) {
		this.requiredBy.remove(node);
		node.requires.remove(this);
	}
	
	public void removeRollbackCause(PrecedenceNode node) {
		this.rollbackCauses.remove(node);
		node.rollsBack.remove(this);
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
	
	public void clearRollbackCauses() {
		for (PrecedenceNode node : this.rollbackCauses)
			node.rollsBack.remove(this);
		this.rollbackCauses.clear();
	}
	
	public void clearRollsBack() {
		for (PrecedenceNode node : this.rollsBack)
			node.rollbackCauses.remove(this);
		this.rollsBack.clear();
	}

}
