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
import org.emoflon.ibex.tgg.runtime.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.DeletionPattern;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.DomainModification;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.util.TGGInplaceAttrExprUtil;

import language.TGGAttributeExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGParamValue;
import language.TGGRuleEdge;
import language.TGGRuleNode;

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
		if (value instanceof List<?> list && !list.contains(emfEdge.getTarget()))
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
		return util.integrate.filterNACMatchCollector().getFilterNACMatches(util.match).stream() //
				.collect(Collectors.toMap( //
						fnm -> fnm, //
						fnm -> fnm.getType() == PatternType.FILTER_NAC_SRC ? DomainType.SRC : DomainType.TRG) //
				);
	}

	public Set<ConstrainedAttributeChanges> analyzeConstrainedAttributeChanges() {
		Set<ConstrainedAttributeChanges> constrainedAttrChanges = new HashSet<>();

		for (TGGAttributeConstraint constr : util.rule.getAttributeConditionLibrary().getTggAttributeConstraints()) {
			IRuntimeTGGAttrConstrContainer runtimeAttrConstr = getRuntimeAttrConstraint(constr, util.match);
			if (runtimeAttrConstr.solve())
				continue;

			Map<TGGAttributeExpression, AttributeChange> affectedParams = new HashMap<>();

			for (TGGParamValue param : constr.getParameters()) {
				if (param instanceof TGGAttributeExpression attrExpr) {
					EObject obj = util.getEObject(attrExpr.getObjectVar());
					Set<AttributeChange> attrChanges = util.integrate.generalModelChanges().getAttributeChanges(obj);
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

	public Map<InplAttributeChange, DomainType> analyzeInplaceAttributeChanges() {
		Set<InplAttributeChange> inplAttrChanges = new HashSet<>();

		Map<String, EObject> nodeName2eObject = util.getNodeToEObject().entrySet().stream() //
				.collect(Collectors.toMap(e -> e.getKey().getName(), e -> e.getValue()));
		for (TGGRuleNode node : util.rule.getNodes()) {
			EObject eObject = util.getEObject(node);
			for (TGGInplaceAttributeExpression attrExpr : node.getAttrExpr()) {
				if (!TGGInplaceAttrExprUtil.checkInplaceAttributeCondition(attrExpr, eObject, nodeName2eObject)) {
					Set<AttributeChange> attrChanges = util.integrate.generalModelChanges().getAttributeChanges(eObject);
					for (AttributeChange attrChange : attrChanges) {
						if (attrChange.getAttribute().equals(attrExpr.getAttribute())) {
							inplAttrChanges.add(new InplAttributeChange(node, attrExpr, attrChange));
							break;
						}
					}
				}
			}
		}

		return inplAttrChanges.stream() //
				.collect(Collectors.toMap( //
						ac -> ac, //
						ac -> ac.node.getDomainType() //
				));
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

	public class InplAttributeChange {
		public final TGGRuleNode node;
		public final TGGInplaceAttributeExpression attrExpr;
		public final AttributeChange attrChange;

		public InplAttributeChange(TGGRuleNode node, TGGInplaceAttributeExpression attrExpr, AttributeChange attrChange) {
			this.node = node;
			this.attrExpr = attrExpr;
			this.attrChange = attrChange;
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append(node.getName());
			b.append(":");
			b.append(node.getType().getName());
			b.append("#");
			b.append(attrExpr.getAttribute().getName());
			b.append(", '");
			b.append(attrChange.getOldValue());
			b.append("'->'");
			b.append(attrChange.getNewValue());
			b.append("'");
			return b.toString();
		}
	}

}
