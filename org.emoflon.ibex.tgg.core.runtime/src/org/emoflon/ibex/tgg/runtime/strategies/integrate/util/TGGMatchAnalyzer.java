package org.emoflon.ibex.tgg.runtime.strategies.integrate.util;

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
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.DeletionPattern;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.DomainModification;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGPattern;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.util.TGGAttributeCheckUtil;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

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

	private boolean isEdgeDeleted(TGGEdge edge, EMFEdge emfEdge, Set<TGGRuleElement> deletedElements) {
		if (deletedElements.contains((TGGNode) edge.getSource()) || deletedElements.contains((TGGNode) edge.getTarget()))
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
						fnm -> fnm.getType() == PatternType.FILTER_NAC_SRC ? DomainType.SOURCE : DomainType.TARGET) //
				);
	}

	public Set<ConstrainedAttributeChanges> analyzeConstrainedAttributeChanges() {
		Set<ConstrainedAttributeChanges> constrainedAttrChanges = new HashSet<>();

		for (TGGAttributeConstraint constr : ((TGGPattern) util.rule.getPrecondition()).getAttributeConstraints().getTggAttributeConstraints()) {
			IRuntimeTGGAttrConstrContainer runtimeAttrConstr = getRuntimeAttrConstraint(constr, util.match);
			if (runtimeAttrConstr.solve())
				continue;

			Map<IBeXAttributeValue, AttributeChange> affectedParams = new HashMap<>();

			for (var param : constr.getParameters()) {
				if (param.getExpression() instanceof IBeXAttributeValue attributeValue) {
					EObject obj = util.getEObject((TGGNode) attributeValue.getNode());
					Set<AttributeChange> attrChanges = util.integrate.generalModelChanges().getAttributeChanges(obj);
					for (AttributeChange attrChange : attrChanges) {
						if (attrChange.getAttribute().equals(attributeValue.getAttribute())) {
							affectedParams.put(attributeValue, attrChange);
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
		for (TGGNode node : util.rule.getNodes()) {
			EObject eObject = util.getEObject(node);
			for (var attributeCondition : node.getReferencedByConditions()) {
				if (!(attributeCondition instanceof RelationalExpression relationalExpression))
					continue;
				
				if (!TGGAttributeCheckUtil.checkAttributeCondition(attributeCondition, nodeName2eObject)) {
					Set<AttributeChange> attrChanges = util.integrate.generalModelChanges().getAttributeChanges(eObject);
					for (AttributeChange attrChange : attrChanges) {
						IBeXAttributeValue attributeValue = TGGModelUtils.getOperandWithAttributeValue(attributeCondition, node);
						
						if (attrChange.getAttribute().equals(attributeValue.getAttribute())) {
							inplAttrChanges.add(new InplAttributeChange(node, relationalExpression, attributeValue, attrChange));
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
		public final Map<IBeXAttributeValue, AttributeChange> affectedParams;

		public ConstrainedAttributeChanges(TGGAttributeConstraint constraint, Map<IBeXAttributeValue, AttributeChange> affectedParams) {
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
				b.append(p.getNode().getName());
				b.append(":");
				b.append(p.getNode().getType().getName());
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
		public final TGGNode node;
		public final RelationalExpression attributeCondition;
		public final IBeXAttributeValue affectedSide;
		public final AttributeChange attributeChange;

		public InplAttributeChange(TGGNode node, RelationalExpression attributeCondition, IBeXAttributeValue affectedSide,
				AttributeChange attributeChange) {
			this.node = node;
			this.attributeCondition = attributeCondition;
			this.affectedSide = affectedSide;
			this.attributeChange = attributeChange;
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append(node.getName());
			b.append(":");
			b.append(node.getType().getName());
			b.append("#");
			b.append(affectedSide.getAttribute().getName());
			b.append(", '");
			b.append(attributeChange.getOldValue());
			b.append("'->'");
			b.append(attributeChange.getNewValue());
			b.append("'");
			return b.toString();
		}
	}

}
