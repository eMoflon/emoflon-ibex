package org.emoflon.ibex.common.visualization.tgg

import java.util.List
import language.BindingType
import language.DomainType
import language.TGG
import language.TGGAttributeConstraint
import language.TGGAttributeConstraintLibrary
import language.TGGAttributeConstraintOperators
import language.TGGAttributeExpression
import language.TGGEnumExpression
import language.TGGInplaceAttributeExpression
import language.TGGLiteralExpression
import language.TGGParamValue
import language.TGGRule
import language.TGGRuleCorr
import language.TGGRuleEdge
import language.TGGRuleElement
import language.TGGRuleNode
import language.impl.TGGAttributeExpressionImpl
import language.impl.TGGEnumExpressionImpl
import language.impl.TGGLiteralExpressionImpl
import language.repair.ExternalShortcutRule
import language.repair.TGGRuleElementMapping
import java.util.Set
import java.util.HashSet

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

	def static String visualizeTGG(TGG tgg) {
		'''
				«plantUMLPreamble»
			
				«FOR rule : tgg.rules»
					«IF rule.abstract»abstract «ENDIF»class «rule.name» «IF rule.abstract»<<WHITE>> <<GREY>>«ELSE»<<BLACK>>«ENDIF»
				«ENDFOR»
				
				«FOR rule : tgg.rules»
					«FOR superRule : rule.refines»
						«superRule.name» <|-- «rule.name»
					«ENDFOR»
				«ENDFOR»
		'''
	}

	def static String visualizeTGGRule(TGGRule rule) {
		var namespaceRule = "Rule: " + rule.name
		var namespaceConstr = "Attribute Constraints"

		'''
			«plantUMLPreamble»
			
			«visualizeTGGRule(namespaceRule, rule)»
			
			«visualizeContraints(namespaceRule, namespaceConstr, rule.attributeConditionLibrary)»
		'''
	}

	private def static visualizeTGGRule(String namespace, TGGRule rule) {
		var Set<TGGRuleEdge> processedOpposites = new HashSet();
		
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
					«visualizeCorr(namespace, node as TGGRuleCorr)»
				«ENDFOR»
			}
			
			«FOR edge : rule.srcTrgEdges»
				«visualizeEdge(namespace, edge, processedOpposites)»
			«ENDFOR»
		'''
	}

	private def static visualizeContraints(String namespaceRule, String namespaceConstr,
		TGGAttributeConstraintLibrary library) {
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
				«IF param instanceof TGGAttributeExpression»
					«idOf(namespaceRule, (param as TGGAttributeExpression).objectVar)» ..«"LIGHT".color» «constrId»
				«ENDIF»
			«ENDFOR»
		'''
	}

	private def static visualizeParameter(TGGParamValue value) {
		switch (value.class) {
			case TGGLiteralExpressionImpl: {
				var litExpr = value as TGGLiteralExpression
				'''«litExpr.value»'''
			}
			case TGGAttributeExpressionImpl: {
				var attrExpr = value as TGGAttributeExpression
				'''«attrExpr.objectVar.name».«attrExpr.attribute.name»'''
			}
			case TGGEnumExpressionImpl: {
				var enumExpr = value as TGGEnumExpression
				'''«enumExpr.literal.literal»'''
			}
		}
	}

	private def static getSrcNodes(TGGRule rule) {
		rule.nodes.filter[n|n.domainType == DomainType.SRC]
	}

	private def static getTrgNodes(TGGRule rule) {
		rule.nodes.filter[n|n.domainType == DomainType.TRG]
	}

	private def static getCorrNodes(TGGRule rule) {
		rule.nodes.filter[n|n.domainType == DomainType.CORR]
	}

	private def static getSrcTrgEdges(TGGRule rule) {
		rule.edges.filter[e|e.domainType != DomainType.CORR]
	}

	private def static visualizeNode(String namespace, TGGRuleNode node, String domain) {
		'''
			class «idOf(namespace, node)» <<«node.bindingType.color»>> <<«domain»>> {
				«FOR expr : node.attrExpr»
					«expr.visualizeAttrExpr(node.bindingType)»
				«ENDFOR»
			}
		'''
	}

	private def static visualizeAttrExpr(TGGInplaceAttributeExpression expr, BindingType binding) {
		'''«expr.attribute.name» «expr.operator.getSign(binding)» «expr.valueExpr.visualizeParameter»'''
	}

	private def static getSign(TGGAttributeConstraintOperators operator, BindingType binding) {
		switch (operator) {
			case EQUAL: {
				'''«IF binding === BindingType.CREATE»:=«ELSE»==«ENDIF»'''
			}
			case GR_EQUAL: {
				'''>='''
			}
			case GREATER: {
				'''>'''
			}
			case LESSER: {
				'''<'''
			}
			case LE_EQUAL: {
				'''<='''
			}
			case UNEQUAL: {
				'''!='''
			}
		}
	}

	private def static visualizeCorr(String namespace, TGGRuleCorr node) {
		'''
			«idOf(namespace, node.source)» .«node.bindingType.color.color» «idOf(namespace, node.target)» : «node.name»
		'''
	}

	private def static visualizeEdge(String namespace, TGGRuleEdge edge, Set<TGGRuleEdge> processedOpposites) {
		if (edge.type.EOpposite !== null) {
			if (processedOpposites.contains(edge) || edge.type.name.endsWith("Inverse"))
				return null
			processedOpposites.add(getOppositeEdge(edge))
			return '''
				«idOf(namespace, edge.srcNode)» "«edge.type.EOpposite.name»" <-«edge.bindingType.color.color»-> "«edge.type.name»" «idOf(namespace, edge.trgNode)»
			'''
		}

		return '''
			«idOf(namespace, edge.srcNode)» -«edge.bindingType.color.color»-> «idOf(namespace, edge.trgNode)» : «edge.type.name»
		'''
	}
	
	private def static getOppositeEdge(TGGRuleEdge edge) {
		val rule = edge.eContainer as TGGRule
		val oppositeType = edge.type.EOpposite
		return rule.edges.filter[e|e.type === oppositeType]
				.filter[e|e.srcNode === edge.trgNode]
				.filter[e|e.trgNode === edge.srcNode]
				.findFirst[e|true]
	}

	private def static idOf(String namespace, TGGRuleNode node) {
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

	def static String visualizeSCRule(ExternalShortcutRule scrule) {
		var srcNamespace = scrule.sourceRule.name + "_source"
		var trgNamespace = scrule.targetRule.name + "_target"

		'''
			«plantUMLPreamble»
			
			«visualizeTGGRule(srcNamespace, scrule.sourceRule)»
			«visualizeTGGRule(trgNamespace, scrule.targetRule)»
			
			«FOR mapping : scrule.mapping»
				«visualizeMapping(srcNamespace, trgNamespace, mapping)»
			«ENDFOR»
			
		'''
	}

	private def static visualizeMapping(String srcNamespace, String trgNamespace, TGGRuleElementMapping mapping) {
		if (mapping.sourceRuleElement instanceof TGGRuleCorr) {
			'''
			
			'''
		} else if (mapping.sourceRuleElement instanceof TGGRuleNode) {
			'''
				«idOf(srcNamespace, mapping.sourceRuleElement as TGGRuleNode)» --[#RoyalBlue] «idOf(trgNamespace, mapping.targetRuleElement as TGGRuleNode)»
			'''
		} else if (mapping.sourceRuleElement instanceof TGGRuleEdge) {
			'''
				
			'''
		}
	}

	def static String visualizeSCRuleMerged(ExternalShortcutRule scrule) {
		var namespace = scrule.name
		var namespaceSrc = "[S] " + scrule.sourceRule.name
		var namespaceTrg = "[T] " + scrule.targetRule.name
		
		var Set<TGGRuleEdge> processedOpposites = new HashSet();

		'''
			«plantUMLPreamble»
			
			together {
				«FOR mapping : scrule.mapping.filter[m|m.sourceRuleElement.domainType == DomainType.TRG]»
					«visualizeNodeMapping(namespace, mapping, "TRG")»
				«ENDFOR»
				«FOR element : scrule.unboundSrcContext.filter(DomainType.TRG)»
					«visualizeNode(namespace, element, "TRG", "BLACK", "S")»
				«ENDFOR»
				«FOR element : scrule.unboundTrgContext.filter(DomainType.TRG)»
					«visualizeNode(namespace, element, "TRG", "BLACK", "T")»
				«ENDFOR»
				«FOR element : scrule.creations.filter(DomainType.TRG)»
					«visualizeNode(namespace, element, "TRG", "GREEN", "T")»
				«ENDFOR»
				«FOR element : scrule.deletions.filter(DomainType.TRG)»
					«visualizeNode(namespace, element, "TRG", "RED", "S")»
				«ENDFOR»
			}
			
			together {
				«FOR mapping : scrule.mapping.filter[m|m.sourceRuleElement.domainType == DomainType.SRC]»
					«visualizeNodeMapping(namespace, mapping, "SRC")»
				«ENDFOR»
				«FOR element : scrule.unboundSrcContext.filter(DomainType.SRC)»
					«visualizeNode(namespace, element, "SRC", "BLACK", "S")»
				«ENDFOR»
				«FOR element : scrule.unboundTrgContext.filter(DomainType.SRC)»
					«visualizeNode(namespace, element, "SRC", "BLACK", "T")»
				«ENDFOR»
				«FOR element : scrule.creations.filter(DomainType.SRC)»
					«visualizeNode(namespace, element, "SRC", "GREEN", "T")»
				«ENDFOR»
				«FOR element : scrule.deletions.filter(DomainType.SRC)»
					«visualizeNode(namespace, element, "SRC", "RED", "S")»
				«ENDFOR»
			}
			
			together {
				«FOR mapping : scrule.mapping.filter[m|m.sourceRuleElement.domainType == DomainType.CORR]»
					«visualizeCorrMapping(namespace, mapping)»
				«ENDFOR»
				«FOR element : scrule.unboundSrcContext.filter(DomainType.CORR)»
					«visualizeCorr(namespace, element, "BLACK", "S")»
				«ENDFOR»
				«FOR element : scrule.unboundTrgContext.filter(DomainType.CORR)»
					«visualizeCorr(namespace, element, "BLACK", "T")»
				«ENDFOR»
				«FOR element : scrule.creations.filter(DomainType.CORR)»
					«visualizeCorr(namespace, element, "GREEN", "T")»
				«ENDFOR»
				«FOR element : scrule.deletions.filter(DomainType.CORR)»
					«visualizeCorr(namespace, element, "RED", "S")»
				«ENDFOR»
			}
			
			«FOR mapping : scrule.mapping.filter[m|m.sourceRuleElement.domainType != DomainType.CORR]»
				«visualizeEdgeMapping(namespace, mapping, processedOpposites)»
			«ENDFOR»
			«FOR element : scrule.unboundSrcContext.filterInverse(DomainType.CORR)»
				«visualizeEdge(namespace, element, "BLACK", "S", scrule.mapping, processedOpposites)»
			«ENDFOR»
			«FOR element : scrule.unboundTrgContext.filterInverse(DomainType.CORR)»
				«visualizeEdge(namespace, element, "BLACK", "T", scrule.mapping, processedOpposites)»
			«ENDFOR»
			«FOR element : scrule.creations.filterInverse(DomainType.CORR)»
				«visualizeEdge(namespace, element, "GREEN", "T", scrule.mapping, processedOpposites)»
			«ENDFOR»
			«FOR element : scrule.deletions.filterInverse(DomainType.CORR)»
				«visualizeEdge(namespace, element, "RED", "S", scrule.mapping, processedOpposites)»
			«ENDFOR»
			
			«visualizeTGGRule(namespaceSrc, scrule.sourceRule)»
			«visualizeTGGRule(namespaceTrg, scrule.targetRule)»
			
			"«namespaceSrc»" -down-> "«namespace»"
			"«namespaceTrg»" -up-> "«namespace»" 
		'''
	}

	private def static visualizeNodeMapping(String namespace, TGGRuleElementMapping mapping, String domain) {
		if (mapping.sourceRuleElement instanceof TGGRuleNode) {
			var srcNode = mapping.sourceRuleElement as TGGRuleNode
			var trgNode = mapping.targetRuleElement as TGGRuleNode
			'''
				class «idOfMapped(namespace, srcNode)» <<BLUE>> <<«domain»>> {
					«FOR expr : trgNode.attrExpr»
						«expr.visualizeAttrExpr(trgNode.bindingType)»
					«ENDFOR»
				}
			'''
		}
	}

	private def static visualizeNode(String namespace, TGGRuleElement elt, String domain, String binding,
		String origin) {
		if (elt instanceof TGGRuleNode) {
			var node = elt as TGGRuleNode
			'''
				class «idOf(namespace, node, origin)» <<«binding»>> <<«domain»>> {
					«FOR expr : node.attrExpr»
						«expr.visualizeAttrExpr(binding == "RED" ? BindingType.DELETE : node.bindingType)»
					«ENDFOR»
				}
			'''
		}
	}

	private def static visualizeCorrMapping(String namespace, TGGRuleElementMapping mapping) {
		if (mapping.sourceRuleElement instanceof TGGRuleCorr) {
			var srcCorr = mapping.sourceRuleElement as TGGRuleCorr
			'''
				«idOfMapped(namespace, srcCorr.source)» .«"BLUE".color» «idOfMapped(namespace, srcCorr.target)» : «srcCorr.name»
			'''
		}
	}

	private def static visualizeCorr(String namespace, TGGRuleElement elt, String binding, String origin) {
		if (elt instanceof TGGRuleCorr) {
			var corr = elt as TGGRuleCorr
			'''
				«idOf(namespace, corr.source, origin)» .«binding.color» «idOf(namespace, corr.target, origin)» : «corr.name»
			'''
		}
	}

	private def static visualizeEdgeMapping(String namespace, TGGRuleElementMapping mapping, Set<TGGRuleEdge> processedOpposites) {
		if (mapping.sourceRuleElement instanceof TGGRuleEdge) {
			var srcEdge = mapping.sourceRuleElement as TGGRuleEdge
			
			if (srcEdge.type.EOpposite !== null) {
				if (processedOpposites.contains(srcEdge) || srcEdge.type.name.endsWith("Inverse"))
					return null
				processedOpposites.add(getOppositeEdge(srcEdge))
				return '''
					«idOfMapped(namespace, srcEdge.srcNode)» "«srcEdge.type.EOpposite.name»" <-«"BLUE".color»-> "«srcEdge.type.name»" «idOfMapped(namespace, srcEdge.trgNode)»
				'''
			}
			
			return '''
				«idOfMapped(namespace, srcEdge.srcNode)» -«"BLUE".color»-> «idOfMapped(namespace, srcEdge.trgNode)» : «srcEdge.type.name»
			'''
		}
	}

	private def static visualizeEdge(String namespace, TGGRuleElement elt, String binding, String origin,
		List<TGGRuleElementMapping> mappings, Set<TGGRuleEdge> processedOpposites) {
		if (elt instanceof TGGRuleEdge) {
			var edge = elt as TGGRuleEdge
			
			var srcMapping = edge.srcNode.isMapped(mappings)
			var trgMapping = edge.trgNode.isMapped(mappings)
			var srcId = srcMapping === null
					? idOf(namespace, edge.srcNode, origin)
					: idOfMapped(namespace, srcMapping.sourceRuleElement as TGGRuleNode)
			var trgId = trgMapping === null
					? idOf(namespace, edge.trgNode, origin)
					: idOfMapped(namespace, trgMapping.sourceRuleElement as TGGRuleNode)

			if (edge.type.EOpposite !== null) {
				if (processedOpposites.contains(edge) || edge.type.name.endsWith("Inverse"))
					return null
				processedOpposites.add(getOppositeEdge(edge))
				return '''
					«srcId» "«edge.type.EOpposite.name»" <-«binding.color»-> "«edge.type.name»" «trgId»
				'''
			}
			
			return '''
				«srcId» -«binding.color»-> «trgId» : «edge.type.name»
			'''
		}
	}

	private def static isMapped(TGGRuleNode node, List<TGGRuleElementMapping> mappings) {
		mappings.findFirst[m|m.sourceRuleElement.equals(node) || m.targetRuleElement.equals(node)]
	}

	private def static idOfMapped(String namespace, TGGRuleNode srcNode) {
		'''"«namespace».[ST] «srcNode.name» : «srcNode.type.name»"'''
	}

	private def static idOf(String namespace, TGGRuleNode node, String origin) {
		'''"«namespace».[«origin»] «node.name» : «node.type.name»"'''
	}

	private def static filter(List<TGGRuleElement> list, DomainType domainType) {
		list.filter[e|e.domainType == domainType]
	}

	private def static filterInverse(List<TGGRuleElement> list, DomainType domainType) {
		list.filter[e|e.domainType != domainType]
	}

	def static String visualizeMapping(TGGRuleElementMapping mapping) {
		var srcRule = mapping.sourceRuleElement.eContainer as TGGRule
		var trgRule = mapping.targetRuleElement.eContainer as TGGRule
		var namespaceSrc = "[S] " + srcRule.name
		var namespaceTrg = "[T] " + trgRule.name

		'''
			«plantUMLPreamble»
			
			«visualizeTGGRule(namespaceSrc, srcRule)»
			
			«visualizeMappingAndEdges(mapping, namespaceSrc, namespaceTrg, 0)»
			
			«visualizeTGGRule(namespaceTrg, trgRule)»
		'''
	}

	def static String visualizeMappings(List<TGGRuleElementMapping> mappings) {
		var srcRule = mappings.get(0).sourceRuleElement.eContainer as TGGRule
		var trgRule = mappings.get(0).targetRuleElement.eContainer as TGGRule
		var namespaceSrc = "[S] " + srcRule.name
		var namespaceTrg = "[T] " + trgRule.name

		'''
			«plantUMLPreamble»
			
			«visualizeTGGRule(namespaceSrc, srcRule)»
			
			«var count = 0»
			«FOR mapping : mappings»
				«visualizeMappingAndEdges(mapping, namespaceSrc, namespaceTrg, count++)»
			«ENDFOR»
			
			«visualizeTGGRule(namespaceTrg, trgRule)»
		'''
	}

	private def static visualizeMappingAndEdges(TGGRuleElementMapping mapping, String namespaceSrc, String namespaceTrg,
		int count) {
		'''
			«visualizeMappingEdges(mapping, mapping.mappingName, namespaceSrc, namespaceTrg, count)»
			class «mapping.mappingName» <<BLUE>> <<WHITE>>
		'''
	}

	private def static visualizeMappingEdges(TGGRuleElementMapping mapping, String mappingName, String namespaceSrc,
		String namespaceTrg, int count) {
		var srcName = '''"src«count»"'''
		var trgName = '''"trg«count»"'''
		if (mapping.sourceRuleElement instanceof TGGRuleCorr) {
			var srcCorr = mapping.sourceRuleElement as TGGRuleCorr
			var trgCorr = mapping.targetRuleElement as TGGRuleCorr
			'''
				class «srcName» <<BLUE>> <<WHITE>>
				«idOf(namespaceSrc, srcCorr.source)» -«"BLUE".color»# «srcName»
				«srcName» #-«"BLUE".color» «idOf(namespaceSrc, srcCorr.target)»
				class «trgName» <<BLUE>> <<WHITE>>
				«idOf(namespaceTrg, trgCorr.source)» -«"BLUE".color»# «trgName»
				«trgName» #-«"BLUE".color» «idOf(namespaceTrg, trgCorr.target)»
				«mappingName» -left«"BLUE".color»> «srcName»
				«mappingName» -right«"BLUE".color»> «trgName»
			'''
		} else if (mapping.sourceRuleElement instanceof TGGRuleNode) {
			var srcNode = mapping.sourceRuleElement as TGGRuleNode
			var trgNode = mapping.targetRuleElement as TGGRuleNode
			'''
				«mappingName» -left«"BLUE".color»> «idOf(namespaceSrc, srcNode)»
				«mappingName» -right«"BLUE".color»> «idOf(namespaceTrg, trgNode)»
			'''
		} else if (mapping.sourceRuleElement instanceof TGGRuleEdge) {
			var srcEdge = mapping.sourceRuleElement as TGGRuleEdge
			var trgEdge = mapping.targetRuleElement as TGGRuleEdge

			if (srcEdge.domainType != DomainType.CORR) {
				'''
					class «srcName» <<BLUE>> <<WHITE>>
					«idOf(namespaceSrc, srcEdge.srcNode)» -«"BLUE".color»-# «srcName»
					«srcName» #-«"BLUE".color»- «idOf(namespaceSrc, srcEdge.trgNode)»
					class «trgName» <<BLUE>> <<WHITE>>
					«idOf(namespaceTrg, trgEdge.srcNode)» -«"BLUE".color»-# «trgName»
					«trgName» #-«"BLUE".color»- «idOf(namespaceTrg, trgEdge.trgNode)»
					«mappingName» -left«"BLUE".color»> «srcName»
					«mappingName» -right«"BLUE".color»> «trgName»
				'''
			}
		}
	}

	private def static String getMappingName(TGGRuleElementMapping mapping) {
		if (mapping.sourceRuleElement instanceof TGGRuleNode) {
			'''"«mapping.sourceRuleElement.name»<->«mapping.targetRuleElement.name» : Mapping"'''
		} else if (mapping.sourceRuleElement instanceof TGGRuleEdge) {
			var srcEdge = mapping.sourceRuleElement as TGGRuleEdge
			var trgEdge = mapping.targetRuleElement as TGGRuleEdge
			'''"«srcEdge.type.name»<->«trgEdge.type.name» : Mapping"'''
		}
	}

}
