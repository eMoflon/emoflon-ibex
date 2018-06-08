package org.emoflon.ibex.gt.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.common.operational.ICreatePatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;

import IBeXLanguage.IBeXAttributeAssignment;
import IBeXLanguage.IBeXAttributeExpression;
import IBeXLanguage.IBeXAttributeParameter;
import IBeXLanguage.IBeXAttributeValue;
import IBeXLanguage.IBeXConstant;
import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXEnumLiteral;

/**
 * Interpreter applying creation of elements for graph transformation.
 */
public class GraphTransformationCreateInterpreter implements ICreatePatternInterpreter {
	/**
	 * The default resource.
	 */
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
			final Map<String, Object> parameters) {
		createNodes(createPattern, match);
		createEdges(createPattern, match);
		applyAttributeAssignments(createPattern, match, parameters);
		return Optional.of(match);
	}

	/**
	 * Create nodes.
	 * 
	 * @param createPattern
	 *            the pattern defining which elements are created
	 * @param match
	 *            the match
	 */
	private void createNodes(final IBeXCreatePattern createPattern, final IMatch match) {
		createPattern.getCreatedNodes().forEach(node -> {
			EObject newNode = EcoreUtil.create(node.getType());
			this.defaultResource.getContents().add(newNode);
			match.put(node.getName(), newNode);
		});
	}

	/**
	 * Create edges.
	 * 
	 * @param createPattern
	 *            the pattern defining which elements are created
	 * @param match
	 *            the match
	 */
	private void createEdges(final IBeXCreatePattern createPattern, final IMatch match) {
		createPattern.getCreatedEdges().forEach(edge -> {
			EObject src = (EObject) match.get(edge.getSourceNode().getName());
			EObject trg = (EObject) match.get(edge.getTargetNode().getName());
			EMFManipulationUtils.createEdge(src, trg, edge.getType());
		});
	}

	/**
	 * Execute the attribute assignments.
	 * 
	 * @param createPattern
	 *            the pattern defining which elements are created
	 * @param match
	 *            the match
	 * @param parameters
	 *            the parameters
	 */
	private void applyAttributeAssignments(final IBeXCreatePattern createPattern, final IMatch match,
			final Map<String, Object> parameters) {
		// Calculate attribute values.
		List<AssignmentTriple> assignments = new ArrayList<AssignmentTriple>();
		createPattern.getAttributeAssignments().forEach(assignment -> {
			assignments.add(calculateAssignment(assignment, match, parameters));
		});

		// Execute assignments.
		assignments.forEach(assignment -> {
			assignment.getObject().eSet(assignment.getAttribute(), assignment.getValue());
		});
	}

	/**
	 * Calculates the new value for the attributes.
	 * 
	 * @param assignment
	 *            the attribute assignments
	 * @param match
	 *            the match
	 * @param parameters
	 *            the parameters
	 * @return the calculated assignment
	 */
	private AssignmentTriple calculateAssignment(final IBeXAttributeAssignment assignment, final IMatch match,
			final Map<String, Object> parameters) {
		IBeXAttributeValue value = assignment.getValue();
		Object calculatedValue = null;
		if (value instanceof IBeXConstant) {
			calculatedValue = ((IBeXConstant) value).getValue();
		} else if (value instanceof IBeXAttributeExpression) {
			IBeXAttributeExpression attributeExpression = (IBeXAttributeExpression) value;
			EObject node = (EObject) match.get(attributeExpression.getNode().getName());
			calculatedValue = node.eGet(attributeExpression.getAttribute());
		} else if (value instanceof IBeXEnumLiteral) {
			EEnumLiteral enumLiteral = ((IBeXEnumLiteral) value).getLiteral();
			// Need to get actual Java instance. Cannot use EnumLiteral here!
			calculatedValue = enumLiteral.getInstance();
			if (calculatedValue == null) {
				throw new IllegalArgumentException("Missing object for " + enumLiteral);
			}
		} else if (value instanceof IBeXAttributeParameter) {
			String parameterName = ((IBeXAttributeParameter) value).getName();
			if (!parameters.containsKey(parameterName)) {
				throw new IllegalArgumentException("Missing required parameter " + parameterName);
			}
			calculatedValue = parameters.get(parameterName);
		}

		EObject object = (EObject) match.get(assignment.getNode().getName());
		return new AssignmentTriple(object, assignment.getType(), calculatedValue);
	}

	/**
	 * An AssignmentTriple consists of the object, the attribute type and the new
	 * value for the attribute.
	 */
	class AssignmentTriple {
		private final EObject object;
		private final EAttribute attribute;
		private final Object value;

		public AssignmentTriple(final EObject object, final EAttribute attribute, final Object value) {
			this.object = object;
			this.attribute = attribute;
			this.value = value;
		}

		public EObject getObject() {
			return object;
		}

		public EAttribute getAttribute() {
			return attribute;
		}

		public Object getValue() {
			return value;
		}
	}
}
