package org.moflon.tgg.mosl.scoping

import java.util.ArrayList
import java.util.Collection
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EEnum
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider
import org.eclipse.xtext.scoping.impl.FilteringScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.moflon.tgg.mosl.tgg.AttributeAssignment
import org.moflon.tgg.mosl.tgg.AttributeConstraint
import org.moflon.tgg.mosl.tgg.AttributeExpression
import org.moflon.tgg.mosl.tgg.CorrType
import org.moflon.tgg.mosl.tgg.CorrVariablePattern
import org.moflon.tgg.mosl.tgg.EnumExpression
import org.moflon.tgg.mosl.tgg.LinkVariablePattern
import org.moflon.tgg.mosl.tgg.ObjectVariablePattern
import org.moflon.tgg.mosl.tgg.Param
import org.moflon.tgg.mosl.tgg.Rule
import org.moflon.tgg.mosl.tgg.Schema
import org.moflon.tgg.mosl.tgg.TggPackage
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile
import org.moflon.codegen.eclipse.CodeGeneratorPlugin
import org.moflon.ide.mosl.core.scoping.MoflonScope

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 * 
 */
class TGGScopeProvider extends AbstractDeclarativeScopeProvider {
	

	override getScope(EObject context, EReference reference) {
		
		/* Scopes in Rule */
		try{
			if (is_attr_of_ov(context, reference))
				return attr_must_be_of_ov(context)
			
			if(is_attr_of_attr_assignment(context, reference))
				return attr_must_be_of_assigned_ov(context)
				
			if(is_attr_of_attr_constraint(context, reference))
				return attr_must_be_of_constrained_ov(context)
			
			if(is_attr_of_attr_expression(context, reference))
				return attr_must_be_from_attr_exp_ov(context)
			
			if (is_type_of_lv(context, reference)) 
				return type_of_lv_must_be_a_reference_of_enclosing_ov(context)
			
			if(is_target_of_lv(context, reference))
				return target_of_lv_must_be_compatible_with_its_type(context)			
			
			if (is_attr_cond(context, reference))
				return attr_in_cond_must_be_an_attr_of_the_ref_ov(context)
			
			if(is_type_of_ov(context, reference))
				return type_of_ov_must_be_from_correct_domain(context)
			
			if(is_src_of_corr_ov(context, reference))
				return src_of_corr_ov_must_be_in_src_domain(context)
			
			if(is_trg_of_corr_ov(context, reference))
				return trg_of_corr_ov_must_be_in_trg_domain(context)
				
			if(is_enum_exp(context, reference))
				return potential_enums(context)
				
			if(is_enum_literal(context, reference))
				return potential_enum_literals(context)
			
			/* Scopes in Schema */
			
			if (is_src_of_corr_type(context, reference))
				return src_of_corr_type_must_be_a_src_type(context)
			
			if (is_trg_of_corr_type(context, reference))
				return trg_of_corr_type_must_be_a_trg_type(context)
				
			if (is_type_of_param(context, reference))
				return type_of_param_must_be_edatatype(context)
				
			if(is_source_or_target_type_of_schema(context, reference))
				return potential_packages(context)
		} catch(Exception e){
			// Specific handling didn't work so delegate to base class
		}

		super.getScope(context, reference)
	}
	
	def is_enum_literal(EObject context, EReference reference) {
		context instanceof EnumExpression && reference == TggPackage.Literals.ENUM_EXPRESSION__LITERAL	
	}
	
	def potential_enum_literals(EObject context) {
		val enumExp = context as EnumExpression
		val eenum = enumExp.eenum;
		val literals = eenum.ELiterals
		
		Scopes.scopeFor(literals)	
	}
	
	def potential_enums(EObject context) {
		val enumExp = context as EnumExpression
		
		// Crawl up eContainers to Rule
		var EObject current = enumExp
		while(!(current instanceof Rule))
			current = current.eContainer
		
		// Get imports from schema
		val ResourceSet set = new ResourceSetImpl
		CodeGeneratorPlugin.createPluginToResourceMapping(set);
		val schema = (current as Rule).schema
		val packages = schema.imports.map[u | set.getResource(URI.createURI(u.name), true).contents.get(0) as EPackage]
		
		val literals = packages.map[p | EcoreUtil2.getAllContentsOfType(p, EEnum)].flatten
				
		Scopes.scopeFor(literals)
	}
	
	def is_enum_exp(EObject context, EReference reference) {
		context instanceof EnumExpression && reference == TggPackage.Literals.ENUM_EXPRESSION__EENUM
	}
	
	def is_attr_of_ov(EObject context, EReference reference) {
		context instanceof ObjectVariablePattern && (reference == TggPackage.Literals.ATTRIBUTE_ASSIGNMENT__ATTRIBUTE 
			|| reference == TggPackage.Literals.ATTRIBUTE_CONSTRAINT__ATTRIBUTE
		)
	}
	def is_attr_of_attr_assignment(EObject context, EReference reference) {
		context instanceof AttributeAssignment && reference == TggPackage.Literals.ATTRIBUTE_ASSIGNMENT__ATTRIBUTE
	}
	def is_attr_of_attr_constraint(EObject context, EReference reference) {
		context instanceof AttributeConstraint && reference == TggPackage.Literals.ATTRIBUTE_CONSTRAINT__ATTRIBUTE
	}
	def is_attr_of_attr_expression(EObject context, EReference reference) {
		context instanceof AttributeExpression && reference == TggPackage.Literals.ATTRIBUTE_EXPRESSION__ATTRIBUTE
	}
	
	def attr_must_be_of_ov(EObject context) {
		var ovPattern = context as ObjectVariablePattern
		return Scopes.scopeFor(ovPattern.type.EAllAttributes)
	}
	def attr_must_be_of_assigned_ov(EObject context) {
		var attr = context as AttributeAssignment
		var ovPattern = attr.eContainer as ObjectVariablePattern
		return Scopes.scopeFor(ovPattern.type.EAllAttributes)
	}
	def attr_must_be_of_constrained_ov(EObject context) {
		var attr = context as AttributeConstraint
		var ovPattern = attr.eContainer as ObjectVariablePattern
		return Scopes.scopeFor(ovPattern.type.EAllAttributes)
	}
	def attr_must_be_from_attr_exp_ov(EObject context) {
		var exp = context as AttributeExpression
		var ovPattern = exp.objectVar as ObjectVariablePattern
		return Scopes.scopeFor(ovPattern.type.EAllAttributes)
	}
	
	def potential_packages(EObject context) {
		val set = new ResourceSetImpl()
		CodeGeneratorPlugin.createPluginToResourceMapping(set);
		var schema = context as Schema
		var resources = schema.imports.map[u | set.getResource(URI.createURI(u.name), true)]
		return new MoflonScope(resources.map[r | r.contents.get(0)])
	}
	
	def is_source_or_target_type_of_schema(EObject context, EReference reference) {
		context instanceof Schema && (reference == TggPackage.Literals.SCHEMA__SOURCE_TYPES || reference == TggPackage.Literals.SCHEMA__TARGET_TYPES)
	}
	
	def type_of_corr_ov_must_be_a_corr_type(EObject context) {
		var rule = context.eContainer as Rule
		var tgg = rule.eContainer as TripleGraphGrammarFile
		var schema = tgg.schema as Schema
		return Scopes.scopeFor(schema.correspondenceTypes)
	}
	
	
	def type_of_param_must_be_edatatype(EObject object) {
		var eClassifiers = EcorePackage.eINSTANCE.getEClassifiers()
		var edata = (EcoreUtil.getObjectsByType(eClassifiers, EcorePackage.Literals.EDATA_TYPE) as Object) as Collection<EDataType>
		return Scopes.scopeFor(edata)
	}
	
	def is_type_of_corr_ov(EObject context, EReference reference) {
		context instanceof CorrVariablePattern && reference == TggPackage.Literals.CORR_VARIABLE_PATTERN__TYPE
	}
	
	def is_type_of_param(EObject context, EReference reference) {
		context instanceof Param && reference == TggPackage.Literals.PARAM__TYPE
	}
	
	def is_trg_types_of_schema(EObject context, EReference reference) {
		context instanceof Schema && reference == TggPackage.Literals.SCHEMA__TARGET_TYPES
	}
	
	def is_src_types_of_schema(EObject context, EReference reference) {
		context instanceof Schema && reference == TggPackage.Literals.SCHEMA__SOURCE_TYPES
	}

	def is_trg_of_corr_ov(EObject context, EReference reference) {
		context instanceof CorrVariablePattern && reference == TggPackage.Literals.CORR_VARIABLE_PATTERN__TARGET
	}
	
	def is_src_of_corr_ov(EObject context, EReference reference) {
		context instanceof CorrVariablePattern && reference == TggPackage.Literals.CORR_VARIABLE_PATTERN__SOURCE
	}
	
	def is_equal_or_super_type_of_ov(EClass sup, IEObjectDescription desc){
		var sub = (desc.EObjectOrProxy as ObjectVariablePattern).type
		return sub.equals(sup) || sub.EAllSuperTypes.contains(sup) || EcorePackage.eINSTANCE.EObject.equals(sup)
	}

	def is_type_of_ov(EObject context, EReference reference) {
		context instanceof ObjectVariablePattern && reference == TggPackage.Literals.OBJECT_VARIABLE_PATTERN__TYPE
	}

	def is_attr_cond(EObject context, EReference reference) {
		context instanceof AttributeExpression && reference == TggPackage.Literals.ATTRIBUTE_EXPRESSION__ATTRIBUTE
	}

	def is_type_of_lv(EObject context, EReference reference) {
		context instanceof LinkVariablePattern && reference == TggPackage.Literals.LINK_VARIABLE_PATTERN__TYPE
	}
	
	def is_target_of_lv(EObject context, EReference reference) {
		context instanceof LinkVariablePattern && reference == TggPackage.Literals.LINK_VARIABLE_PATTERN__TARGET
	}
	
	def is_trg_of_corr_type(EObject context, EReference reference) {
		context instanceof CorrType && reference == TggPackage.Literals.CORR_TYPE__TARGET
	}

	def is_src_of_corr_type(EObject context, EReference reference) {
		context instanceof CorrType && reference == TggPackage.Literals.CORR_TYPE__SOURCE
	}

	def trg_of_corr_ov_must_be_in_trg_domain(EObject context) {
		val typeDef = determineTypeDef(context)
		var rule = context.eContainer as Rule
		var IScope allCandidates = Scopes.scopeFor(rule.targetPatterns)		
		return new FilteringScope(allCandidates, [c | is_equal_or_super_type_of_ov(typeDef.target, c)])
	}
	
	def determineTypeDef(EObject context){
		var corrOv = context as CorrVariablePattern
		var type = corrOv.type
		if(type.super == (null))
			return type as CorrType
		else if(type.super instanceof CorrType)
			return determineTypeDefFromExtension(type) as CorrType
		else
			throw new IllegalStateException("Unable to determine type def from " + type)
	}
	
	def determineTypeDefFromExtension(CorrType typeExtension) {
		if(typeExtension.getSuper.super == (null))
			return typeExtension.super as CorrType
		else if(typeExtension.getSuper.super instanceof CorrType)
			return determineTypeDefFromExtension(typeExtension.super as CorrType)
		else
			throw new IllegalStateException("This should never be the case!") 
	}                  
	
	def src_of_corr_ov_must_be_in_src_domain(EObject context) {
		val typeDef = determineTypeDef(context)
		var rule = context.eContainer as Rule
		var IScope allCandidates = Scopes.scopeFor(rule.sourcePatterns)		
		return new FilteringScope(allCandidates, [c | is_equal_or_super_type_of_ov(typeDef.source, c)])
	}
	
	def target_of_lv_must_be_compatible_with_its_type(EObject context) {
		val lvPattern = context as LinkVariablePattern
		var ovPattern = lvPattern.eContainer as ObjectVariablePattern
		var rule = ovPattern.eContainer as Rule
		
		// Must be in same domain as ov
		var IScope allCandidates = null;
		if(rule.sourcePatterns.contains(ovPattern))
			allCandidates = Scopes.scopeFor(rule.sourcePatterns)
		else {
			allCandidates = Scopes.scopeFor(rule.targetPatterns)		
		}
		
		// Must be compatible to lv
		return new FilteringScope(allCandidates, [c | is_equal_or_super_type_of_ov(lvPattern.type.EType as EClass, c)])
	}
	
	
	def type_of_ov_must_be_from_correct_domain(EObject context) {
		var ov = context as ObjectVariablePattern
		var rule = ov.eContainer as Rule
		var schema = rule.schema
		
		if(rule.sourcePatterns.contains(ov)){
			// typeOfOv must be in source domain
			allTypes(schema.sourceTypes, schema)
		}else{
			// typeOfOv must be in target domain
			allTypes(schema.targetTypes, schema)
		}
	}
	

	def src_of_corr_type_must_be_a_src_type(EObject context) {
		handleCorrTypeDef(context, [Schema s | s.sourceTypes])
	}

	def trg_of_corr_type_must_be_a_trg_type(EObject context) {
		handleCorrTypeDef(context, [Schema s | s.targetTypes])
	}

	def handleCorrTypeDef(EObject context, (Schema)=>List<EPackage> types) {
		var corrTypeDef = context as CorrType
		var schema = corrTypeDef.eContainer as Schema
		var sources = types.apply(schema)
		return allTypes(sources, schema)
	}
	
	def allTypes(List<EPackage> types, Schema schema) {
		val allPackages = new ArrayList
		allPackages.addAll(types)
		allPackages.addAll(types.map[p | EcoreUtil2.getAllContentsOfType(p, EPackage)].flatten)		
		
		val elements = allPackages.map[EPackage p | EcoreUtil2.getAllContentsOfType(p, EClassifier)].flatten
		
		new SimpleScope(
			Scopes.scopeFor(elements), 
			Scopes.scopedElementsFor(elements, new DefaultDeclarativeQualifiedNameProvider)
		)
	}

	def attr_in_cond_must_be_an_attr_of_the_ref_ov(EObject context) {
		var paramVal = context as AttributeExpression
		var ovPattern = paramVal.objectVar as ObjectVariablePattern
		return Scopes.scopeFor(ovPattern.type.EAllAttributes)
	}

	def type_of_lv_must_be_a_reference_of_enclosing_ov(EObject context) {
		var lvPattern = context as LinkVariablePattern
		var ovPattern = lvPattern.eContainer as ObjectVariablePattern
		return Scopes.scopeFor(ovPattern.type.EAllReferences)
	}
}
