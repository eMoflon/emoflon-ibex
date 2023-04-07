package org.emoflon.ibex.common.visualization.tgg

import java.util.HashSet
import java.util.Set
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.DoubleLiteral
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IntegerLiteral
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanValue
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression

class IBeXTggXmiPlantUMLGenerator {

	private def static CharSequence plantUMLPreamble() {
		'''
			hide empty members
			hide circle
			hide stereotype
			
			skinparam shadowing false
			
			skinparam class {
				BorderColor<<GREY>> SlateGrey
				BorderColor<<GREEN>> SpringGreen
				BorderColor<<RED>> Red
				BorderColor<<BLACK>> Black
				BorderColor<<BLUE>> RoyalBlue
				BackgroundColor<<WHITE>> White
				BackgroundColor<<TRG>> MistyRose
				BackgroundColor<<SRC>> LightYellow
				ArrowColor Black
			}	
		'''
	}

	private def static getColor(String binding) {
		'''[#«switch (binding) {
			case "BLACK": {
				"Black"
			}
			case "GREEN": {
				"SpringGreen"
			}
			case "RED": {
				"Red"
			}
			case "BLUE": {
				"RoyalBlue"
			}
			case "GREY": {
				"SlateGrey"
			}
			case "LIGHT": {
				"Gainsboro"
			}
			default: {
				"Black"
			}
		}»]'''
	}

	def static String visualizeTGG(TGGModel tgg) {
		'''
				«plantUMLPreamble»
			
				«FOR rule : tgg.ruleSet.rules»
					«IF rule.abstract»abstract «ENDIF»class «rule.name» «IF rule.abstract»<<WHITE>> <<GREY>>«ELSE»<<BLACK>>«ENDIF»
				«ENDFOR»
				
		'''
	}

	def static String visualizeTGGRule(TGGRule rule) {
		var namespaceRule = "Rule: " + rule.name
		var namespaceConstr = "Attribute Constraints"

		'''
			«plantUMLPreamble»
			
			«visualizeTGGRule(namespaceRule, rule)»
			
			«visualizeContraints(namespaceRule, namespaceConstr, rule.attributeConstraints)»
		'''
	}

	private def static visualizeTGGRule(String namespace, TGGRule rule) {
		var Set<TGGEdge> processedOpposites = new HashSet();

		'''
			together {
				«FOR node : rule.trgNodes»
					«visualizeNode(namespace, node, "TRG")»
				«ENDFOR»
			}
			
			together {
				«FOR node : rule.srcNodes»
					«visualizeNode(namespace, node, "SRC")»
				«ENDFOR»
			}
			
			together {
				«FOR node : rule.corrNodes»
					«visualizeCorr(namespace, node as TGGCorrespondence)»
				«ENDFOR»
			}
			
			«FOR edge : rule.srcTrgEdges»
				«visualizeEdge(namespace, edge, processedOpposites)»
			«ENDFOR»
		'''
	}

	private def static visualizeContraints(String namespaceRule, String namespaceConstr, TGGAttributeConstraintSet library) {
		'''
			«FOR constr : library.tggAttributeConstraints»
				«visualizeAttrConstr(namespaceRule, namespaceConstr, constr)»
			«ENDFOR»
		'''
	}

	private def static visualizeAttrConstr(String namespaceRule, String namespaceConstr,
		TGGAttributeConstraint constraint) {
		var paramString = '''«FOR parameter : constraint.parameters»«visualizeParameter(parameter)», «ENDFOR»'''
		var constrId = '''"«namespaceConstr».«constraint.definition.name»"'''

		'''
			class «constrId» <<WHITE>> <<BLACK>> {
				«paramString.substring(0, paramString.length - 2)»
			}
			
			«FOR param : constraint.parameters»
				«IF param.expression instanceof IBeXAttributeValue»
					«idOf(namespaceRule, (param.expression as IBeXAttributeValue).node)» ..«"LIGHT".color» «constrId»
				«ENDIF»
			«ENDFOR»
		'''
	}
	
	private def static visualizeExpression(ValueExpression valueExpression) {
		switch (valueExpression.class) {
			case IBeXStringValue: {
				var litExpr = valueExpression as IBeXStringValue
				'''«litExpr.value»'''
			}
			case IBeXBooleanValue: {
				var litExpr = valueExpression as IBeXBooleanValue
				'''«litExpr.value»'''
			}
			case DoubleLiteral: {
				var litExpr = valueExpression as DoubleLiteral
				'''«litExpr.value»'''
			}
			case IntegerLiteral: {
				var litExpr = valueExpression as IntegerLiteral
				'''«litExpr.value»'''
			}
			case BooleanValue: {
				var litExpr = valueExpression as IntegerLiteral
				'''«litExpr.value»'''
			}
			case IBeXAttributeValue: {
				var attrExpr = valueExpression as IBeXAttributeValue
				'''«attrExpr.node.name».«attrExpr.attribute.name»'''
			}
			case IBeXEnumValue: {
				var enumExpr = valueExpression as IBeXEnumValue
				'''«enumExpr.literal.literal»'''
			}
		}
	}

	private def static visualizeParameter(TGGAttributeConstraintParameterValue value) {
		return visualizeExpression(value.expression)
	}

	private def static getSrcNodes(TGGRule rule) {
		rule.nodes.filter[n|n.domainType == DomainType.SOURCE]
	}

	private def static getTrgNodes(TGGRule rule) {
		rule.nodes.filter[n|n.domainType == DomainType.TARGET]
	}

	private def static getCorrNodes(TGGRule rule) {
		rule.nodes.filter[n|n.domainType == DomainType.CORRESPONDENCE]
	}

	private def static getSrcTrgEdges(TGGRule rule) {
		rule.edges.filter[e|e.domainType != DomainType.CORRESPONDENCE]
	}

	private def static visualizeNode(String namespace, TGGNode node, String domain) {
		'''
			class «idOf(namespace, node)» <<«node.bindingType.color»>> <<«domain»>> {
				«FOR expr : node.attributeAssignments»
					«expr.visualizeAttrExpr(node.bindingType)»
				«ENDFOR»
			}
		'''
	}

	private def static visualizeAttrExpr(IBeXAttributeAssignment expr, BindingType binding) {
		'''«expr.attribute.name» «getSign(RelationalOperator.EQUAL, BindingType.CREATE)» «visualizeExpression(expr.value)»'''
	}

	private def static getSign(RelationalOperator operator, BindingType binding) {
		switch (operator) {
			case EQUAL: {
				'''«IF binding === BindingType.CREATE»:=«ELSE»==«ENDIF»'''
			}
			case GREATER_OR_EQUAL: {
				'''>='''
			}
			case GREATER: {
				'''>'''
			}
			case SMALLER: {
				'''<'''
			}
			case SMALLER_OR_EQUAL: {
				'''<='''
			}
			case UNEQUAL: {
				'''!='''
			}
		}
	}

	private def static visualizeCorr(String namespace, TGGCorrespondence node) {
		'''
			«idOf(namespace, node.source)» .«node.bindingType.color.color» «idOf(namespace, node.target)» : «node.name»
		'''
	}

	private def static visualizeEdge(String namespace, TGGEdge edge, Set<TGGEdge> processedOpposites) {
		if (edge.type.EOpposite !== null) {
			if (processedOpposites.contains(edge) || edge.type.name.endsWith("Inverse"))
				return null
			processedOpposites.add(getOppositeEdge(edge))
			return '''
				«idOf(namespace, edge.source)» "«edge.type.EOpposite.name»" <-«edge.bindingType.color.color»-> "«edge.type.name»" «idOf(namespace, edge.target)»
			'''
		}

		return '''
			«idOf(namespace, edge.source)» -«edge.bindingType.color.color»-> «idOf(namespace, edge.target)» : «edge.type.name»
		'''
	}

	private def static getOppositeEdge(TGGEdge edge) {
		val rule = edge.eContainer as TGGRule
		val oppositeType = edge.type.EOpposite
		return rule.edges.filter[e|e.type === oppositeType].filter[e|e.source === edge.target].filter [ e |
			e.target === edge.source
		].findFirst[e|true]
	}

	private def static idOf(String namespace, IBeXNode node) {
		'''"«namespace».«node.name» : «node.type.name»"'''
	}

	private def static getColor(BindingType binding) {
		switch (binding) {
			case CREATE: {
				return "GREEN";
			}
			case CONTEXT: {
				return "BLACK";
			}
			default: {
				return "BLACK"
			}
		}
	}

//	def static String visualizeSCRule(ExternalShortcutRule scrule) {
//		var srcNamespace = scrule.sourceRule.name + "_source"
//		var trgNamespace = scrule.targetRule.name + "_target"
//
//		'''
//			«plantUMLPreamble»
//			
//			«visualizeTGGRule(srcNamespace, scrule.sourceRule)»
//			«visualizeTGGRule(trgNamespace, scrule.targetRule)»
//			
//			«FOR mapping : scrule.mapping»
//				«visualizeMapping(srcNamespace, trgNamespace, mapping)»
//			«ENDFOR»
//			
//		'''
//	}
//
//	private def static visualizeMapping(String srcNamespace, String trgNamespace, TGGRuleElementMapping mapping) {
//		if (mapping.sourceRuleElement instanceof TGGCorrespondence) {
//			'''
//			
//			'''
//		} else if (mapping.sourceRuleElement instanceof TGGNode) {
//			'''
//				«idOf(srcNamespace, mapping.sourceRuleElement as TGGNode)» --[#RoyalBlue] «idOf(trgNamespace, mapping.targetRuleElement as TGGNode)»
//			'''
//		} else if (mapping.sourceRuleElement instanceof TGGEdge) {
//			'''
//				
//			'''
//		}
//	}
//
//	def static String visualizeSCRuleMerged(ExternalShortcutRule scrule) {
//		var namespace = scrule.name
//		var namespaceSrc = "[O] " + scrule.sourceRule.name
//		var namespaceTrg = "[R] " + scrule.targetRule.name
//
//		var Set<TGGEdge> processedOpposites = new HashSet();
//
//		'''
//			«plantUMLPreamble»
//			
//			«««			together {
//				«FOR mapping : scrule.mapping.filter[m|m.sourceRuleElement.domainType == DomainType.SOURCE]»
//				«visualizeNodeMapping(namespace, mapping, "SRC")»
//				«ENDFOR»
//				«FOR element : scrule.unboundSrcContext.filter(DomainType.SOURCE)»
//				«visualizeNode(namespace, element, "SRC", "BLACK", "O")»
//				«ENDFOR»
//				«FOR element : scrule.unboundTrgContext.filter(DomainType.SOURCE)»
//				«visualizeNode(namespace, element, "SRC", "BLACK", "R")»
//				«ENDFOR»
//				«FOR element : scrule.creations.filter(DomainType.SOURCE)»
//				«visualizeNode(namespace, element, "SRC", "GREEN", "R")»
//				«ENDFOR»
//				«FOR element : scrule.deletions.filter(DomainType.SOURCE)»
//				«visualizeNode(namespace, element, "SRC", "RED", "O")»
//				«ENDFOR»
//			«««			}
//			
//«««			together {
//				«FOR mapping : scrule.mapping.filter[m|m.sourceRuleElement.domainType == DomainType.TARGET]»
//			
//	«visualizeNodeMapping(namespace, mapping, "TRG")»
//			
//«ENDFOR»
//			
//«FOR element : scrule.unboundSrcContext.filter(DomainType.TARGET)»
//			
//	«visualizeNode(namespace, element, "TRG", "BLACK", "O")»
//			
//«ENDFOR»
//			
//«FOR element : scrule.unboundTrgContext.filter(DomainType.TARGET)»
//			
//	«visualizeNode(namespace, element, "TRG", "BLACK", "R")»
//			
//«ENDFOR»
//			
//«FOR element : scrule.creations.filter(DomainType.TARGET)»
//			
//	«visualizeNode(namespace, element, "TRG", "GREEN", "R")»
//			
//«ENDFOR»
//			
//«FOR element : scrule.deletions.filter(DomainType.TARGET)»
//			
//	«visualizeNode(namespace, element, "TRG", "RED", "O")»
//			
//«ENDFOR»
//			«««			}
//			
//«««			together {
//				«FOR mapping : scrule.mapping.filter[m|m.sourceRuleElement.domainType == DomainType.CORRESPONDENCE]»
//			
//	«visualizeCorrMapping(namespace, mapping)»
//			
//«ENDFOR»
//			
//«FOR corr : scrule.unboundSrcContext.filterCorrNodes»
//			
//	«visualizeCorr(namespace, corr, "BLACK", "O", scrule.mapping)»
//			
//«ENDFOR»
//			
//«FOR corr : scrule.unboundTrgContext.filterCorrNodes»
//			
//	«visualizeCorr(namespace, corr, "BLACK", "R", scrule.mapping)»
//			
//«ENDFOR»
//			
//«FOR corr : scrule.creations.filterCorrNodes»
//			
//	«visualizeCorr(namespace, corr, "GREEN", "R", scrule.mapping)»
//			
//«ENDFOR»
//			
//«FOR corr : scrule.deletions.filterCorrNodes»
//			
//	«visualizeCorr(namespace, corr, "RED", "O", scrule.mapping)»
//			
//«ENDFOR»
//			«««			}
//			
//			«FOR mapping : scrule.mapping.filter[m|m.sourceRuleElement.domainType != DomainType.CORRESPONDENCE]»
//				«visualizeEdgeMapping(namespace, mapping, processedOpposites)»
//			«ENDFOR»
//			«FOR element : scrule.unboundSrcContext.filterInverse(DomainType.CORRESPONDENCE)»
//				«visualizeEdge(namespace, element, "BLACK", "O", scrule.mapping, processedOpposites)»
//			«ENDFOR»
//			«FOR element : scrule.unboundTrgContext.filterInverse(DomainType.CORRESPONDENCE)»
//				«visualizeEdge(namespace, element, "BLACK", "R", scrule.mapping, processedOpposites)»
//			«ENDFOR»
//			«FOR element : scrule.creations.filterInverse(DomainType.CORRESPONDENCE)»
//				«visualizeEdge(namespace, element, "GREEN", "R", scrule.mapping, processedOpposites)»
//			«ENDFOR»
//			«FOR element : scrule.deletions.filterInverse(DomainType.CORRESPONDENCE)»
//				«visualizeEdge(namespace, element, "RED", "O", scrule.mapping, processedOpposites)»
//			«ENDFOR»
//			
//			«visualizeTGGRule(namespaceSrc, scrule.sourceRule)»
//			«visualizeTGGRule(namespaceTrg, scrule.targetRule)»
//			
//			"«namespaceSrc»" -down-> "«namespace»"
//			"«namespaceTrg»" -up-> "«namespace»" 
//		'''
//	}
//
//	private def static visualizeNodeMapping(String namespace, TGGRuleElementMapping mapping, String domain) {
//		if (mapping.sourceRuleElement instanceof TGGNode) {
//			var srcNode = mapping.sourceRuleElement as TGGNode
//			var trgNode = mapping.targetRuleElement as TGGNode
//			'''
//				class «idOfMapped(namespace, srcNode)» <<BLUE>> <<«domain»>> {
//					«FOR expr : trgNode.attrExpr»
//						«expr.visualizeAttrExpr(trgNode.bindingType)»
//					«ENDFOR»
//				}
//			'''
//		}
//	}
//
//	private def static visualizeNode(String namespace, TGGRuleElement elt, String domain, String binding,
//		String origin) {
//		if (elt instanceof TGGNode) {
//			var node = elt as TGGNode
//			'''
//				class «idOf(namespace, node, origin)» <<«binding»>> <<«domain»>> {
//					«FOR expr : node.attrExpr»
//						«expr.visualizeAttrExpr(binding == "RED" ? BindingType.DELETE : node.bindingType)»
//					«ENDFOR»
//				}
//			'''
//		}
//	}
//
//	private def static visualizeCorrMapping(String namespace, TGGRuleElementMapping mapping) {
//		if (mapping.sourceRuleElement instanceof TGGCorrespondence) {
//			var srcCorr = mapping.sourceRuleElement as TGGCorrespondence
//			'''
//				«idOfMapped(namespace, srcCorr.source)» .«"BLUE".color» «idOfMapped(namespace, srcCorr.target)» : «srcCorr.name»
//			'''
//		}
//	}
//
//	private def static visualizeCorr(String namespace, TGGCorrespondence corr, String binding, String origin,
//		List<TGGRuleElementMapping> mappings) {
//		var srcMapping = corr.source.isMapped(mappings)
//		var trgMapping = corr.target.isMapped(mappings)
//		var srcId = srcMapping !== null
//				? idOfMapped(namespace, srcMapping.sourceRuleElement as TGGNode)
//				: idOf(namespace, corr.source, origin)
//		var trgId = trgMapping !== null
//				? idOfMapped(namespace, trgMapping.sourceRuleElement as TGGNode)
//				: idOf(namespace, corr.target, origin)
//		'''
//			«srcId» .«binding.color» «trgId» : «corr.name»
//		'''
//	}
//
//	private def static visualizeEdgeMapping(String namespace, TGGRuleElementMapping mapping,
//		Set<TGGEdge> processedOpposites) {
//		if (mapping.sourceRuleElement instanceof TGGEdge) {
//			var srcEdge = mapping.sourceRuleElement as TGGEdge
//
//			if (srcEdge.type.EOpposite !== null) {
//				if (processedOpposites.contains(srcEdge) || srcEdge.type.name.endsWith("Inverse"))
//					return null
//				processedOpposites.add(getOppositeEdge(srcEdge))
//				return '''
//					«idOfMapped(namespace, srcEdge.srcNode)» "«srcEdge.type.EOpposite.name»" <-«"BLUE".color»-> "«srcEdge.type.name»" «idOfMapped(namespace, srcEdge.trgNode)»
//				'''
//			}
//
//			return '''
//				«idOfMapped(namespace, srcEdge.srcNode)» -«"BLUE".color»-> «idOfMapped(namespace, srcEdge.trgNode)» : «srcEdge.type.name»
//			'''
//		}
//	}
//
//	private def static visualizeEdge(String namespace, TGGRuleElement elt, String binding, String origin,
//		List<TGGRuleElementMapping> mappings, Set<TGGEdge> processedOpposites) {
//		if (elt instanceof TGGEdge) {
//			var edge = elt as TGGEdge
//
//			var srcMapping = edge.srcNode.isMapped(mappings)
//			var trgMapping = edge.trgNode.isMapped(mappings)
//			var srcId = srcMapping === null
//					? idOf(namespace, edge.srcNode, origin)
//					: idOfMapped(namespace, srcMapping.sourceRuleElement as TGGNode)
//			var trgId = trgMapping === null
//					? idOf(namespace, edge.trgNode, origin)
//					: idOfMapped(namespace, trgMapping.sourceRuleElement as TGGNode)
//
//			if (edge.type.EOpposite !== null) {
//				if (processedOpposites.contains(edge) || edge.type.name.endsWith("Inverse"))
//					return null
//				processedOpposites.add(getOppositeEdge(edge))
//				return '''
//					«srcId» "«edge.type.EOpposite.name»" <-«binding.color»-> "«edge.type.name»" «trgId»
//				'''
//			}
//
//			return '''
//				«srcId» -«binding.color»-> «trgId» : «edge.type.name»
//			'''
//		}
//	}
//
//	private def static isMapped(TGGNode node, List<TGGRuleElementMapping> mappings) {
//		mappings.findFirst[m|m.sourceRuleElement.equals(node) || m.targetRuleElement.equals(node)]
//	}
//
//	private def static idOfMapped(String namespace, TGGNode srcNode) {
//		'''"«namespace».[M] «srcNode.name» : «srcNode.type.name»"'''
//	}
//
//	private def static idOf(String namespace, TGGNode node, String origin) {
//		'''"«namespace».[«origin»] «node.name» : «node.type.name»"'''
//	}
//
//	private def static filter(List<TGGRuleElement> list, DomainType domainType) {
//		list.filter[e|e.domainType == domainType]
//	}
//
//	private def static filterInverse(List<TGGRuleElement> list, DomainType domainType) {
//		list.filter[e|e.domainType != domainType]
//	}
//
//	private def static filterCorrNodes(List<TGGRuleElement> list) {
//		list.filter(TGGCorrespondence)
//	}
//
//	def static String visualizeMapping(TGGRuleElementMapping mapping) {
//		var srcRule = mapping.sourceRuleElement.eContainer as TGGRule
//		var trgRule = mapping.targetRuleElement.eContainer as TGGRule
//		var namespaceSrc = "[O] " + srcRule.name
//		var namespaceTrg = "[R] " + trgRule.name
//
//		'''
//			«plantUMLPreamble»
//			
//			«visualizeTGGRule(namespaceSrc, srcRule)»
//			
//			«visualizeMappingAndEdges(mapping, namespaceSrc, namespaceTrg, 0)»
//			
//			«visualizeTGGRule(namespaceTrg, trgRule)»
//		'''
//	}
//
//	def static String visualizeMappings(List<TGGRuleElementMapping> mappings) {
//		var srcRule = mappings.get(0).sourceRuleElement.eContainer as TGGRule
//		var trgRule = mappings.get(0).targetRuleElement.eContainer as TGGRule
//		var namespaceSrc = "[O] " + srcRule.name
//		var namespaceTrg = "[R] " + trgRule.name
//
//		'''
//			«plantUMLPreamble»
//			
//			«visualizeTGGRule(namespaceSrc, srcRule)»
//			
//			«var count = 0»
//			«FOR mapping : mappings»
//				«visualizeMappingAndEdges(mapping, namespaceSrc, namespaceTrg, count++)»
//			«ENDFOR»
//			
//			«visualizeTGGRule(namespaceTrg, trgRule)»
//		'''
//	}
//
//	private def static visualizeMappingAndEdges(TGGRuleElementMapping mapping, String namespaceSrc, String namespaceTrg,
//		int count) {
//		'''
//			«visualizeMappingEdges(mapping, mapping.mappingName, namespaceSrc, namespaceTrg, count)»
//			class «mapping.mappingName» <<BLUE>> <<WHITE>>
//		'''
//	}
//
//	private def static visualizeMappingEdges(TGGRuleElementMapping mapping, String mappingName, String namespaceSrc,
//		String namespaceTrg, int count) {
//		var srcName = '''"src«count»"'''
//		var trgName = '''"trg«count»"'''
//		if (mapping.sourceRuleElement instanceof TGGCorrespondence) {
//			var srcCorr = mapping.sourceRuleElement as TGGCorrespondence
//			var trgCorr = mapping.targetRuleElement as TGGCorrespondence
//			'''
//				class «srcName» <<BLUE>> <<WHITE>>
//				«idOf(namespaceSrc, srcCorr.source)» -«"BLUE".color»# «srcName»
//				«srcName» #-«"BLUE".color» «idOf(namespaceSrc, srcCorr.target)»
//				class «trgName» <<BLUE>> <<WHITE>>
//				«idOf(namespaceTrg, trgCorr.source)» -«"BLUE".color»# «trgName»
//				«trgName» #-«"BLUE".color» «idOf(namespaceTrg, trgCorr.target)»
//				«mappingName» -left«"BLUE".color»> «srcName»
//				«mappingName» -right«"BLUE".color»> «trgName»
//			'''
//		} else if (mapping.sourceRuleElement instanceof TGGNode) {
//			var srcNode = mapping.sourceRuleElement as TGGNode
//			var trgNode = mapping.targetRuleElement as TGGNode
//			'''
//				«mappingName» -left«"BLUE".color»> «idOf(namespaceSrc, srcNode)»
//				«mappingName» -right«"BLUE".color»> «idOf(namespaceTrg, trgNode)»
//			'''
//		} else if (mapping.sourceRuleElement instanceof TGGEdge) {
//			var srcEdge = mapping.sourceRuleElement as TGGEdge
//			var trgEdge = mapping.targetRuleElement as TGGEdge
//
//			if (srcEdge.domainType != DomainType.CORRESPONDENCE) {
//				'''
//					class «srcName» <<BLUE>> <<WHITE>>
//					«idOf(namespaceSrc, srcEdge.srcNode)» -«"BLUE".color»-# «srcName»
//					«srcName» #-«"BLUE".color»- «idOf(namespaceSrc, srcEdge.target)»
//					class «trgName» <<BLUE>> <<WHITE>>
//					«idOf(namespaceTrg, trgEdge.srcNode)» -«"BLUE".color»-# «trgName»
//					«trgName» #-«"BLUE".color»- «idOf(namespaceTrg, trgEdge.target)»
//					«mappingName» -left«"BLUE".color»> «srcName»
//					«mappingName» -right«"BLUE".color»> «trgName»
//				'''
//			}
//		}
//	}
//
//	private def static String getMappingName(TGGRuleElementMapping mapping) {
//		if (mapping.sourceRuleElement instanceof TGGNode) {
//			'''"«mapping.sourceRuleElement.name»<->«mapping.targetRuleElement.name» : Mapping"'''
//		} else if (mapping.sourceRuleElement instanceof TGGEdge) {
//			var srcEdge = mapping.sourceRuleElement as TGGEdge
//			var trgEdge = mapping.targetRuleElement as TGGEdge
//			'''"«srcEdge.type.name»<->«trgEdge.type.name» : Mapping"'''
//		}
//	}

}
