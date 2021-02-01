package org.emoflon.ibex.gt.transformations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorReference;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

public class TransformationData {
	
	/**
	 * All flattened editor patterns.
	 */
	public Map<EditorPattern, EditorPattern> pattern2flattened = new HashMap<>();
	
	/**
	 * All generated ibex nodes.
	 */
	public List<IBeXNode> ibexNodes = new LinkedList<>();
	
	/**
	 * All generated ibex edges.
	 */
	public List<IBeXEdge> ibexEdges = new LinkedList<>();
	
	/**
	 * Maps pattern names to a collection mapping editor nodes to ibex nodes.
	 */
	public Map<String, Map<EditorNode, IBeXNode>> node2ibexNode = new HashMap<>();
	
	/**
	 * Maps pattern names to a collection mapping editor references to ibex references.
	 */
	public Map<String, Map<EditorReference, IBeXEdge>> reference2ibexEdge = new HashMap<>();
	
	/**
	 * Maps pattern names to a all context patterns.
	 */
	public Map<String, IBeXContext> nameToPattern = new HashMap<>();
	
	/**
	 * Maps disjoint patterns names to a all context patterns.
	 */
	public Map<String, IBeXDisjointContextPattern> nameToDisjointPattern = new HashMap<>();
	
	/**
	 * Maps disjoint patterns to its transformation information
	 */
	public Map<IBeXDisjointContextPattern, IBeXDisjointPatternTransformation> disjointPatternToTransformation = new HashMap<>();
	
	/**
	 * All context patterns.
	 */
	public Map<IBeXContext, EditorPattern> ibexContextPatterns = new HashMap<>();

	/**
	 * All create patterns.
	 */
	public List<IBeXCreatePattern> ibexCreatePatterns = new LinkedList<>();;

	/**
	 * All delete patterns.
	 */
	public List<IBeXDeletePattern> ibexDeletePatterns = new LinkedList<>();
	
	/**
	 * All rules.
	 */
	public Map<IBeXRule, EditorPattern> ibexRules = new HashMap<>();
	
	public void clear() {
		pattern2flattened.clear();
		ibexNodes.clear();
		ibexEdges.clear();
		node2ibexNode.clear();
		reference2ibexEdge.clear();
		nameToPattern.clear();
		ibexContextPatterns.clear();
		ibexCreatePatterns.clear();
		ibexDeletePatterns.clear();
		ibexRules.clear();
	}

}
