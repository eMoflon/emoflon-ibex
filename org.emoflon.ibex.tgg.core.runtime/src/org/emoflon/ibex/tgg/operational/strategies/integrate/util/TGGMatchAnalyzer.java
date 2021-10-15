package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionPattern;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;

import language.DomainType;
import language.TGGAttributeConstraint;
import language.TGGAttributeExpression;
import language.TGGParamValue;
import language.TGGRuleEdge;
import language.TGGRuleElement;

public class TGGMatchAnalyzer {

	private final TGGMatchUtil util;

	TGGMatchAnalyzer(TGGMatchUtil util) {
		this.util = util;
	}

	public Set<TGGRuleElement> getAllDeletedElements() {
		Set<TGGRuleElement> deletedElements = new HashSet<>();

		util.node2eObject.forEach((node, obj) -> {
			Resource res = obj.eResource();
			if (res == null || !isValidResource(res))
				deletedElements.add(node);
		});
		util.edge2emfEdge.forEach((edge, emfEdge) -> {
			if (isEdgeDeleted(edge, emfEdge, deletedElements))
				deletedElements.add(edge);
		});

		return deletedElements;
	}

	private boolean isValidResource(Resource resource) {
		TGGResourceHandler resourceHandler = util.integrate.getOptions().resourceHandler();
		if (resource.equals(resourceHandler.getSourceResource()))
			return true;
		if (resource.equals(resourceHandler.getTargetResource()))
			return true;
		if (resource.equals(resourceHandler.getCorrResource()))
			return true;
		return false;
	}

	private boolean isEdgeDeleted(TGGRuleEdge edge, EMFEdge emfEdge, Set<TGGRuleElement> deletedElements) {
		if (deletedElements.contains(edge.getSrcNode()) || deletedElements.contains(edge.getTrgNode()))
			return true;
		Object value = emfEdge.getSource().eGet(emfEdge.getType());
		if (value == null)
			return true;
		if (value instanceof List && !((List<?>) value).contains(emfEdge.getTarget()))
			return true;
		return false;
	}

	public DeletionPattern createDelPattern() {
		Set<TGGRuleElement> deletedElements = getAllDeletedElements();

		DeletionPattern pattern = new DeletionPattern(DomainModification.UNCHANGED);
		Predicate<TGGRuleElement> isDel = e -> deletedElements.contains(e);
		util.groupedElements.forEach((domain, bindingMap) -> {
			bindingMap.forEach((binding, elements) -> {
				DomainModification mod;
				if (elements.stream().allMatch(isDel))
					mod = DomainModification.COMPL_DEL;
				else if (elements.stream().anyMatch(isDel))
					mod = DomainModification.PART_DEL;
				else
					mod = DomainModification.UNCHANGED;
				pattern.setModType(domain, binding, mod);
			});
		});
		return pattern;
	}

	public Map<ITGGMatch, DomainType> analyzeFilterNACViolations() {
		return util.integrate.getFilterNacMatches(util.match).stream() //
				.collect(Collectors.toMap( //
						fnm -> fnm, //
						fnm -> fnm.getType() == PatternType.FILTER_NAC_SRC ? DomainType.SRC : DomainType.TRG) //
				);
	}

	public Set<ConstrainedAttributeChanges> analyzeAttributeChanges() {
		Set<ConstrainedAttributeChanges> constrainedAttrChanges = new HashSet<>();

		for (TGGAttributeConstraint constr : util.rule.getAttributeConditionLibrary().getTggAttributeConstraints()) {
			IRuntimeTGGAttrConstrContainer runtimeAttrConstr = getRuntimeAttrConstraint(constr, util.match);
			if (runtimeAttrConstr.solve())
				continue;

			Map<TGGAttributeExpression, AttributeChange> affectedParams = new HashMap<>();

			for (TGGParamValue param : constr.getParameters()) {
				if (param instanceof TGGAttributeExpression) {
					TGGAttributeExpression attrExpr = (TGGAttributeExpression) param;
					EObject obj = util.getObject(attrExpr.getObjectVar());
					Set<AttributeChange> attrChanges = util.integrate.getGeneralModelChanges().getAttributeChanges(obj);
					for (AttributeChange attrChange : attrChanges) {
						if (attrChange.getAttribute().equals(attrExpr.getAttribute())) {
							affectedParams.put(attrExpr, attrChange);
							break;
						}
					}
				}
			}

			if (!affectedParams.isEmpty())
				constrainedAttrChanges.add(new ConstrainedAttributeChanges(constr, affectedParams));
		}

		return constrainedAttrChanges;
	}

	private IRuntimeTGGAttrConstrContainer getRuntimeAttrConstraint(TGGAttributeConstraint constraint, ITGGMatch match) {
		List<TGGAttributeConstraint> constraints = new LinkedList<>();
		constraints.add(constraint);
		return new RuntimeTGGAttributeConstraintContainer(constraint.getParameters(), //
				constraints, match, util.integrate.getOptions().csp.constraintProvider());
	}

	public class ConstrainedAttributeChanges {
		public final TGGAttributeConstraint constraint;
		public final Map<TGGAttributeExpression, AttributeChange> affectedParams;

		public ConstrainedAttributeChanges(TGGAttributeConstraint constraint, Map<TGGAttributeExpression, AttributeChange> affectedParams) {
			this.constraint = constraint;
			this.affectedParams = affectedParams;
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append(constraint.getDefinition().getName());
			b.append(" ");
			affectedParams.forEach((p, ac) -> {
				b.append("(");
				b.append(p.getObjectVar().getName());
				b.append(":");
				b.append(p.getObjectVar().getType().getName());
				b.append("#");
				b.append(p.getAttribute().getName());
				b.append(", '");
				b.append(ac.getOldValue());
				b.append("'->'");
				b.append(ac.getNewValue());
				b.append("') ");
			});
			return b.toString();
		}
	}

}
