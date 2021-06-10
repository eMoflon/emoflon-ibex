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
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionPattern;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.util.TGGEdgeUtil;

import language.BindingType;
import language.DomainType;
import language.TGGAttributeConstraint;
import language.TGGAttributeExpression;
import language.TGGParamValue;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class MatchAnalysis {

	private final INTEGRATE integrate;
	private final ITGGMatch match;
	private final TGGRule rule;

	Map<TGGRuleNode, EObject> node2eObject;
	Map<EObject, TGGRuleNode> eObject2node;

	Map<TGGRuleEdge, EMFEdge> edge2emfEdge;
	Map<EMFEdge, TGGRuleEdge> emfEdge2edge;

	Map<DomainType, Map<BindingType, List<TGGRuleElement>>> groupedElements;

	private Set<TGGRuleElement> deletedElements;

	MatchAnalysis(INTEGRATE integrate, ITGGMatch match, TGGRule rule) {
		this.integrate = integrate;
		this.match = match;
		this.rule = rule;
		init();
	}

	private void init() {
		this.node2eObject = rule.getNodes().stream() //
				.filter(n -> match.get(n.getName()) != null) //
				.collect(Collectors.toMap(n -> n, n -> (EObject) match.get(n.getName())));
		this.eObject2node = new HashMap<>();
		node2eObject.forEach((n, o) -> eObject2node.put(o, n));

		this.edge2emfEdge = rule.getEdges().stream() //
				.filter(e -> node2eObject.containsKey(e.getSrcNode()) && node2eObject.containsKey(e.getTrgNode())) //
				.collect(Collectors.toMap(e -> e, e -> TGGEdgeUtil.getRuntimeEdge(match, e)));
		this.emfEdge2edge = new HashMap<>();
		edge2emfEdge.forEach((e, f) -> emfEdge2edge.put(f, e));

		Set<TGGRuleElement> elements = new HashSet<>();
		elements.addAll(node2eObject.keySet());
		elements.addAll(edge2emfEdge.keySet());
		groupedElements = elements.stream().collect(Collectors.groupingBy( //
				elt -> elt.getDomainType(), //
				Collectors.groupingBy(elt -> elt.getBindingType()) //
		));

		this.deletedElements = new HashSet<>();
	}

	MatchAnalysis update() {
		getDeletions();
		return this;
	}

	private void getDeletions() {
		deletedElements.clear();
		node2eObject.forEach((node, obj) -> {
			Resource res = obj.eResource();
			if (res == null || !isValidResource(res))
				deletedElements.add(node);
		});
		edge2emfEdge.forEach((edge, emfEdge) -> {
			if (edgeIsDeleted(edge, emfEdge))
				deletedElements.add(edge);
		});
	}

	private boolean isValidResource(Resource resource) {
		TGGResourceHandler resourceHandler = integrate.getOptions().resourceHandler();
		if (resource.equals(resourceHandler.getSourceResource()))
			return true;
		if (resource.equals(resourceHandler.getTargetResource()))
			return true;
		if (resource.equals(resourceHandler.getCorrResource()))
			return true;
		return false;
	}

	private boolean edgeIsDeleted(TGGRuleEdge edge, EMFEdge emfEdge) {
		if (deletedElements.contains(edge.getSrcNode()) || deletedElements.contains(edge.getTrgNode()))
			return true;
		Object value = emfEdge.getSource().eGet(emfEdge.getType());
		if (value == null)
			return true;
		if (value instanceof List && !((List<?>) value).contains(emfEdge.getTarget()))
			return true;
		return false;
	}

	boolean isElementDeleted(TGGRuleElement element) {
		return deletedElements.contains(element);
	}

	public DeletionPattern createDelPattern() {
		getDeletions();
		DeletionPattern pattern = new DeletionPattern(DomainModification.UNCHANGED);
		Predicate<TGGRuleElement> isDel = e -> isElementDeleted(e);
		groupedElements.forEach((domain, bindingMap) -> {
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
		return integrate.getFilterNacMatches(match).stream() //
				.collect(Collectors.toMap(fnm -> fnm, fnm -> fnm.getType() == PatternType.FILTER_NAC_SRC ? DomainType.SRC : DomainType.TRG));
	}

	public Set<ConstrainedAttributeChanges> analyzeAttributeChanges() {
		Set<ConstrainedAttributeChanges> constrainedAttrChanges = new HashSet<>();

		for (TGGAttributeConstraint constr : rule.getAttributeConditionLibrary().getTggAttributeConstraints()) {
			IRuntimeTGGAttrConstrContainer runtimeAttrConstr = getRuntimeAttrConstraint(constr, match);
			if (runtimeAttrConstr.solve())
				continue;

			Map<TGGAttributeExpression, AttributeChange> affectedParams = new HashMap<>();

			for (TGGParamValue param : constr.getParameters()) {
				if (param instanceof TGGAttributeExpression) {
					TGGAttributeExpression attrExpr = (TGGAttributeExpression) param;
					EObject obj = getObject(attrExpr.getObjectVar());
					Set<AttributeChange> attrChanges = integrate.getGeneralModelChanges().getAttributeChanges(obj);
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
				constraints, match, integrate.getOptions().csp.constraintProvider());
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

	public Set<TGGRuleElement> getElts(EltFilter filter) {
		return integrate.getMatchUtil().getElts(this, filter);
	}

	public Set<TGGRuleNode> getNodes() {
		return node2eObject.keySet();
	}

	public Set<TGGRuleEdge> getEdges() {
		return edge2emfEdge.keySet();
	}

	public Set<EObject> getObjects() {
		return eObject2node.keySet();
	}

	public Set<EMFEdge> getEMFEdges() {
		return emfEdge2edge.keySet();
	}

	public Map<TGGRuleNode, EObject> getNodeToEObject() {
		return node2eObject;
	}

	public Map<EObject, TGGRuleNode> getEObjectToNode() {
		return eObject2node;
	}

	public Map<TGGRuleEdge, EMFEdge> getEdgeToEMFEdge() {
		return edge2emfEdge;
	}

	public Map<EMFEdge, TGGRuleEdge> getEmfEdgeToEdge() {
		return emfEdge2edge;
	}

	public TGGRuleNode getNode(EObject object) {
		return eObject2node.get(object);
	}

	public EObject getObject(TGGRuleNode node) {
		return node2eObject.get(node);
	}

	public TGGRuleEdge getEdge(EMFEdge emfEdge) {
		return emfEdge2edge.get(emfEdge);
	}

	public EMFEdge getEMFEdge(TGGRuleEdge edge) {
		return edge2emfEdge.get(edge);
	}

}
