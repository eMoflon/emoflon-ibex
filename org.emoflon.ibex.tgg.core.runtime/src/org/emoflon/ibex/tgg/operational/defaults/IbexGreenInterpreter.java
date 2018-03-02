package org.emoflon.ibex.tgg.operational.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.utils.EMFManipulationUtils;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.operational.IGreenInterpreter;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.util.String2EPrimitive;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.basic.expressions.TGGAttributeExpression;
import language.basic.expressions.TGGEnumExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;

/**
 * @author leblebici Util class for creating EObjects, Edges, and
 *         Correspondences for a given set of green TGGRuleElement
 */
public class IbexGreenInterpreter implements IGreenInterpreter {
	private OperationalStrategy operationalStrategy;

	public IbexGreenInterpreter(OperationalStrategy operationalStrategy) {
		this.operationalStrategy = operationalStrategy;
	}

	public void createNonCorrNodes(IMatch comatch, Collection<TGGRuleNode> greenNodes, Resource nodeResource) {
		for (TGGRuleNode n : greenNodes)
			comatch.put(n.getName(), createNode(comatch, n, nodeResource));
	}

	public Collection<RuntimeEdge> createEdges(IMatch comatch, Collection<TGGRuleEdge> greenEdges, boolean createEMFEdge) {
		Collection<RuntimeEdge> result = new ArrayList<>();
		for (TGGRuleEdge e : greenEdges) {
			EObject src = (EObject) comatch.get(e.getSrcNode().getName());
			EObject trg = (EObject) comatch.get(e.getTrgNode().getName());
			if (createEMFEdge) {
				EMFManipulationUtils.createEdge(src, trg, e.getType());
			}
			result.add(new RuntimeEdge(src, trg, e.getType()));
		}
		
		comatch.getCreatedEdges().addAll(result);
		
		return result;
	}

	public void createCorrs(IMatch comatch, Collection<TGGRuleCorr> greenCorrs, Resource corrR) {
		for (TGGRuleCorr c : greenCorrs) {
			comatch.put(c.getName(), createCorr(comatch, c, comatch.get(c.getSource().getName()), comatch.get(c.getTarget().getName()), corrR));
		}
	}

	private EObject createNode(IMatch match, TGGRuleNode node, Resource resource) {
		EObject newObj = EcoreUtil.create(node.getType());
		handlePlacementInResource(node, resource, newObj);
		
		applyInPlaceAttributeAssignments(match, node, newObj);
		applyAttributeAssignments(match, node, newObj);

		return newObj;
	}

	private void applyAttributeAssignments(IMatch match, TGGRuleNode node, EObject newObj) {
		Collection<String> attributeNames = match.getParameterNames().stream()
			.filter(pname -> { 
				Optional<Pair<String, String>> o = IbexBasePattern.getNodeAndAttrFromVarName(pname);
				Optional<Boolean> check = o.map(node_attr -> node_attr.getLeft().equals(node.getName()));
				return check.orElse(false);
			})
			.collect(Collectors.toList());
		
		for (String node_attr : attributeNames) {
			Object attributeValue = match.get(node_attr);
			Pair<String, String> node_attr_pair = IbexBasePattern.getNodeAndAttrFromVarName(node_attr).orElseThrow(() -> new IllegalStateException("Missing attribute value"));
			String attributeName = node_attr_pair.getRight();
			
			EStructuralFeature feature = node.getType().getEStructuralFeature(attributeName);
			newObj.eSet(feature, attributeValue);
		}
	}

	private void applyInPlaceAttributeAssignments(IMatch match, TGGRuleNode node, EObject newObj) {
		for (TGGInplaceAttributeExpression attrExpr : node.getAttrExpr()) {
			if (attrExpr.getOperator().equals(TGGAttributeConstraintOperators.EQUAL)) {
				if (attrExpr.getValueExpr() instanceof TGGLiteralExpression) {
					TGGLiteralExpression tle = (TGGLiteralExpression) attrExpr.getValueExpr();
					newObj.eSet(attrExpr.getAttribute(),
							String2EPrimitive.convertString(attrExpr.getAttribute().getEType(), tle.getValue()));
					continue;
				}
				if (attrExpr.getValueExpr() instanceof TGGEnumExpression) {
					TGGEnumExpression tee = (TGGEnumExpression) attrExpr.getValueExpr();
					newObj.eSet(attrExpr.getAttribute(), tee.getLiteral());
					continue;
				}
				if (attrExpr.getValueExpr() instanceof TGGAttributeExpression) {
					TGGAttributeExpression tae = (TGGAttributeExpression) attrExpr.getValueExpr();
					EObject obj = (EObject) match.get(tae.getObjectVar().getName());
					newObj.eSet(attrExpr.getAttribute(), obj.eGet(tae.getAttribute()));
					continue;
				}

			}
		}
	}

	private void handlePlacementInResource(TGGRuleNode node, Resource resource, EObject newObj) {
		resource.getContents().add(newObj);
	}

	private EObject createCorr(IMatch comatch, TGGRuleNode node, Object src, Object trg, Resource corrR) {
		EObject corr = createNode(comatch, node, corrR);
		corr.eSet(corr.eClass().getEStructuralFeature("source"), src);
		corr.eSet(corr.eClass().getEStructuralFeature("target"), trg);
		return corr;
	}
	
	@Override
	public Optional<IMatch> apply(IGreenPattern greenPattern, String ruleName, IMatch match) {
		// Check if match is valid
		if(matchIsInvalid(ruleName, greenPattern, match))
			return Optional.empty();
		
		// Check if pattern should be ignored
		if(greenPattern.isToBeIgnored(match))
			return Optional.empty();
		
		// Check if all attribute values provided match are as expected
		IRuntimeTGGAttrConstrContainer cspContainer = greenPattern.getAttributeConstraintContainer(match);
		if(!cspContainer.solve())
			return Optional.empty();
	
		IMatch comatch = match.copy();
		
		createNonCorrNodes(comatch, greenPattern.getSrcNodes(), operationalStrategy.getSourceResource());
		createEdges(comatch, greenPattern.getSrcEdges(), true);
		
		createNonCorrNodes(comatch, greenPattern.getTrgNodes(), operationalStrategy.getTargetResource());
		createEdges(comatch, greenPattern.getTrgEdges(), true);

		cspContainer.applyCSPValues(comatch);

		createCorrs(comatch, greenPattern.getCorrNodes(), operationalStrategy.getCorrResource());
		
		return Optional.of(comatch);
	}
	
	private boolean matchIsInvalid(String ruleName, IGreenPattern greenPattern, IMatch match) {
		return someElementsAlreadyProcessed(ruleName, greenPattern, match)  ||
				!conformTypesOfGreenNodes(match, greenPattern, ruleName)    ||
				!allContextElementsAlreadyProcessed(match, greenPattern, ruleName);
	}
	
	protected boolean someElementsAlreadyProcessed(String ruleName, IGreenPattern greenPattern, IMatch match) {
		return operationalStrategy.someEdgesAlreadyProcessed(greenPattern.getEdgesMarkedByPattern(), match);
	}

	protected boolean conformTypesOfGreenNodes(IMatch match, IGreenPattern greenPattern, String ruleName) {
		for (TGGRuleNode gsn : greenPattern.getNodesMarkedByPattern()) {
			if (gsn.getType() != ((EObject) match.get(gsn.getName())).eClass())
				return false;
		}

		return true;
	}
	
	protected boolean allContextElementsAlreadyProcessed(IMatch match, IGreenPattern greenPattern, String ruleName) {
		return operationalStrategy.allEdgesAlreadyProcessed(greenPattern.getMarkedContextEdges(), match);
	}
}
