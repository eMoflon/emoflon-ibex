package org.emoflon.ibex.common.visualization.tgg

import java.util.List
import language.BindingType
import language.DomainType
import language.TGG
import language.TGGRule
import language.TGGRuleCorr
import language.TGGRuleEdge
import language.TGGRuleElement
import language.TGGRuleNode
import language.repair.ExternalShortcutRule
import language.repair.TGGRuleElementMapping

class IBeXTggXmiPlantUMLGenerator {

	def static CharSequence plantUMLPreamble() {
		'''
			hide empty members
			hide circle
			hide stereotype
			
			skinparam shadowing false
			
			skinparam class {
				BorderColor<<ABSB>> SlateGrey
				BorderColor<<GREEN>> SpringGreen
				BorderColor<<RED>> Red
				BorderColor<<BLACK>> Black
				BackgroundColor<<ABSBG>> White
				BackgroundColor<<TRG>> MistyRose
				BackgroundColor<<SRC>> LightYellow
				ArrowColor Black
			}	
		'''
	}

	def static String visualizeTGG(TGG tgg) {
		'''
				«plantUMLPreamble»
			
				«FOR rule : tgg.rules»
					«IF rule.abstract»abstract «ENDIF»class «rule.name» «IF rule.abstract»<<ABSBG>> <<ABSB>>«ELSE»<<BLACK>>«ENDIF»
				«ENDFOR»
				
				«FOR rule : tgg.rules»
					«FOR superRule : rule.refines»
						«superRule.name» <|-- «rule.name»
					«ENDFOR»
				«ENDFOR»
		'''
	}

	def static String visualizeTGGRule(TGGRule rule) {
		'''
			«plantUMLPreamble»
			
			package "«rule.name»" {
				«visualizeTGGRule(rule.name, rule)»
			}
		'''
	}

	private def static visualizeTGGRule(String namespace, TGGRule rule) {
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
				«visualizeEdge(namespace, edge)»
			«ENDFOR»
		'''
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
			class «idOf(namespace, node)» <<«node.color»>> <<«domain»>>
		'''
	}

	private def static visualizeCorr(String namespace, TGGRuleCorr node) {
		'''
			«idOf(namespace, node.source)» .«IF node.bindingType==BindingType.CREATE»[#SpringGreen]«ENDIF» «idOf(namespace, node.target)» : «node.name»
		'''
	}

	private def static visualizeEdge(String namespace, TGGRuleEdge edge) {
		'''
			«idOf(namespace, edge.srcNode)» -«IF edge.bindingType==BindingType.CREATE»[#SpringGreen]«ENDIF»-> «idOf(namespace, edge.trgNode)» : «edge.type.name»
		'''
	}

	private def static idOf(String namespace, TGGRuleNode node) {
		'''"«namespace».«node.name» : «node.type.name»"'''
	}

	private def static getColor(TGGRuleNode node) {
		switch (node.bindingType) {
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
		'''
			«plantUMLPreamble»
			
			package "«scrule.name»" {
				together {
					«FOR mapping : scrule.mapping.filter[m|m.sourceRuleElement.domainType == DomainType.TRG]»
						«visualizeNodeMapping(mapping, "TRG")»
					«ENDFOR»
					«FOR element : scrule.unboundSrcContext.filter(DomainType.TRG)»
						«visualizeNode(element, "BLACK", "TRG", "S")»
					«ENDFOR»
					«FOR element : scrule.unboundTrgContext.filter(DomainType.TRG)»
						«visualizeNode(element, "BLACK", "TRG", "T")»
					«ENDFOR»
					«FOR element : scrule.creations.filter(DomainType.TRG)»
						«visualizeNode(element, "GREEN", "TRG", "T")»
					«ENDFOR»
					«FOR element : scrule.deletions.filter(DomainType.TRG)»
						«visualizeNode(element, "RED", "TRG", "S")»
					«ENDFOR»
				}
				
				together {
					«FOR mapping : scrule.mapping.filter[m|m.sourceRuleElement.domainType == DomainType.SRC]»
						«visualizeNodeMapping(mapping, "SRC")»
					«ENDFOR»
					«FOR element : scrule.unboundSrcContext.filter(DomainType.SRC)»
						«visualizeNode(element, "BLACK", "SRC", "S")»
					«ENDFOR»
					«FOR element : scrule.unboundTrgContext.filter(DomainType.SRC)»
						«visualizeNode(element, "BLACK", "SRC", "T")»
					«ENDFOR»
					«FOR element : scrule.creations.filter(DomainType.SRC)»
						«visualizeNode(element, "GREEN", "SRC", "T")»
					«ENDFOR»
					«FOR element : scrule.deletions.filter(DomainType.SRC)»
						«visualizeNode(element, "RED", "SRC", "S")»
					«ENDFOR»
				}
				
				together {
					«FOR mapping : scrule.mapping.filter[m|m.sourceRuleElement.domainType == DomainType.CORR]»
						«visualizeCorrMapping(mapping)»
					«ENDFOR»
					«FOR element : scrule.unboundSrcContext.filter(DomainType.CORR)»
						«visualizeCorr(element, "BLACK", "S")»
					«ENDFOR»
					«FOR element : scrule.unboundTrgContext.filter(DomainType.CORR)»
						«visualizeCorr(element, "BLACK", "T")»
					«ENDFOR»
					«FOR element : scrule.creations.filter(DomainType.CORR)»
						«visualizeCorr(element, "GREEN", "T")»
					«ENDFOR»
					«FOR element : scrule.deletions.filter(DomainType.CORR)»
						«visualizeCorr(element, "RED", "S")»
					«ENDFOR»
				}
			}
			
			«FOR mapping : scrule.mapping.filter[m|m.sourceRuleElement.domainType != DomainType.CORR]»
				«visualizeEdgeMapping(mapping)»
			«ENDFOR»
			«FOR element : scrule.unboundSrcContext.filterInverse(DomainType.CORR)»
				«visualizeEdge(element, "BLACK", "S", scrule.mapping)»
			«ENDFOR»
			«FOR element : scrule.unboundTrgContext.filterInverse(DomainType.CORR)»
				«visualizeEdge(element, "BLACK", "T", scrule.mapping)»
			«ENDFOR»
			«FOR element : scrule.creations.filterInverse(DomainType.CORR)»
				«visualizeEdge(element, "GREEN", "T", scrule.mapping)»
			«ENDFOR»
			«FOR element : scrule.deletions.filterInverse(DomainType.CORR)»
				«visualizeEdge(element, "RED", "S", scrule.mapping)»
			«ENDFOR»
		'''
	}

	private def static visualizeNodeMapping(TGGRuleElementMapping mapping, String domain) {
		if (mapping.sourceRuleElement instanceof TGGRuleNode) {
			'''
				class «idOfMapped(mapping.sourceRuleElement as TGGRuleNode)» <<BLACK>> <<«domain»>>
			'''
		}
	}

	private def static visualizeNode(TGGRuleElement elt, String domain, String binding, String origin) {
		if (elt instanceof TGGRuleNode) {
			'''
				class «idOf(elt as TGGRuleNode, origin)» <<«binding»>> <<«domain»>>
			'''
		}
	}

	private def static visualizeCorrMapping(TGGRuleElementMapping mapping) {
		if (mapping.sourceRuleElement instanceof TGGRuleCorr) {
			var srcCorr = mapping.sourceRuleElement as TGGRuleCorr
			'''
				«idOfMapped(srcCorr.source)» . «idOfMapped(srcCorr.target)» : «srcCorr.name»
			'''
		}
	}

	private def static visualizeCorr(TGGRuleElement elt, String binding, String origin) {
		if (elt instanceof TGGRuleCorr) {
			var corr = elt as TGGRuleCorr
			'''
				«idOf(corr.source, origin)» .[#«binding.color»] «idOf(corr.target, origin)» : «corr.name»
			'''
		}
	}

	private def static visualizeEdgeMapping(TGGRuleElementMapping mapping) {
		if (mapping.sourceRuleElement instanceof TGGRuleEdge) {
			var srcEdge = mapping.sourceRuleElement as TGGRuleEdge
			'''
				«idOfMapped(srcEdge.srcNode)» --> «idOfMapped(srcEdge.trgNode)» : «srcEdge.type.name»
			'''
		}
	}

	private def static visualizeEdge(TGGRuleElement elt, String binding, String origin,
		List<TGGRuleElementMapping> mappings) {
		if (elt instanceof TGGRuleEdge) {
			var edge = elt as TGGRuleEdge

			var srcMapping = edge.srcNode.isMapped(mappings)
			var trgMapping = edge.trgNode.isMapped(mappings)
			var srcId = srcMapping === null ? idOf(edge.srcNode, origin) : idOfMapped(
					srcMapping.sourceRuleElement as TGGRuleNode)
			var trgId = trgMapping === null ? idOf(edge.trgNode, origin) : idOfMapped(
					trgMapping.sourceRuleElement as TGGRuleNode)

			'''
				«srcId» -[#«binding.color»]-> «trgId» : «edge.type.name»
			'''
		}
	}

	private def static isMapped(TGGRuleNode node, List<TGGRuleElementMapping> mappings) {
		mappings.findFirst[m|m.sourceRuleElement.equals(node) || m.targetRuleElement.equals(node)]
	}

	private def static idOfMapped(TGGRuleNode srcNode) {
		'''"[ST] «srcNode.name» : «srcNode.type.name»"'''
	}

	private def static idOf(TGGRuleNode node, String origin) {
		'''"[«origin»] «node.name» : «node.type.name»"'''
	}

	private def static getColor(String binding) {
		switch (binding) {
			case "BLACK": {
				"Black"
			}
			case "GREEN": {
				"SpringGreen"
			}
			case "RED": {
				"Red"
			}
			default: {
				"Black"
			}
		}
	}

	private def static filter(List<TGGRuleElement> list, DomainType domainType) {
		list.filter[e|e.domainType == domainType]
	}

	private def static filterInverse(List<TGGRuleElement> list, DomainType domainType) {
		list.filter[e|e.domainType != domainType]
	}

}
