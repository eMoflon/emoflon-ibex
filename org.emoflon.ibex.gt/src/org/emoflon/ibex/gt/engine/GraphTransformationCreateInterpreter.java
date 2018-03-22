package org.emoflon.ibex.gt.engine;

import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.ICreatePatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.utils.EMFManipulationUtils;

import IBeXLanguage.IBeXAttributeParameter;
import IBeXLanguage.IBeXAttributeValue;
import IBeXLanguage.IBeXConstant;
import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXEnumLiteral;

/**
 * Interpreter applying creation of elements for graph transformation.
 */
public class GraphTransformationCreateInterpreter implements ICreatePatternInterpreter {
	private Resource defaultResource;

	/**
	 * Creates a new GraphTransformationCreateInterpreter.
	 * 
	 * @param defaultResource
	 *            the default resource
	 */
	public GraphTransformationCreateInterpreter(final Resource defaultResource) {
		this.defaultResource = defaultResource;
	}

	@Override
	public Optional<IMatch> apply(final IBeXCreatePattern createPattern, final IMatch match,
			Map<String, Object> parameters) {
		// Create nodes and edges.
		createPattern.getCreatedNodes().forEach(node -> {
			EObject newNode = EcoreUtil.create(node.getType());
			this.defaultResource.getContents().add(newNode);
			match.put(node.getName(), newNode);
		});
		createPattern.getCreatedEdges().forEach(edge -> {
			EObject src = (EObject) match.get(edge.getSourceNode().getName());
			EObject trg = (EObject) match.get(edge.getTargetNode().getName());
			EMFManipulationUtils.createEdge(src, trg, edge.getType());
		});

		// Assign attributes.
		createPattern.getAttributeAssignments().forEach(assignment -> {
			EObject object = (EObject) match.get(assignment.getNode().getName());
			EAttribute attribute = assignment.getType();
			IBeXAttributeValue value = assignment.getValue();
			if (value instanceof IBeXConstant) {
				object.eSet(attribute, ((IBeXConstant) value).getValue());
			}
			if (value instanceof IBeXEnumLiteral) {
				IBeXEnumLiteral enumLiteral = ((IBeXEnumLiteral) value);
				// TODO eSet causes CastException on runtime
				object.eSet(attribute, enumLiteral.getLiteral());
			}
			if (value instanceof IBeXAttributeParameter) {
				String parameterName = ((IBeXAttributeParameter) value).getName();
				if (!parameters.containsKey(parameterName)) {
					throw new IllegalArgumentException("Missing required parameter " + parameterName);
				}
				object.eSet(attribute, parameters.get(parameterName));
			}
		});
		return Optional.of(match);
	}
}
