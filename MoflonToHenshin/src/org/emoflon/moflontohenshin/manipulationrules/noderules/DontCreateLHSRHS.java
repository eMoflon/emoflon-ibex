package org.emoflon.moflontohenshin.manipulationrules.noderules;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinFactory;

import language.TGGRuleNode;
import language.basic.expressions.TGGExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.inplaceAttributes.TGGInplaceAttributeExpression;

public class DontCreateLHSRHS extends NodeCreationRule{

	public DontCreateLHSRHS() {
		super(HenshinFactory.eINSTANCE.createGraph().eClass());
		
	}

	protected boolean otherConditions(TGGRuleNode node) {
		Optional<TGGInplaceAttributeExpression> attrMonad = node.getAttrExpr().stream().filter(attr -> attr.getAttribute().getName().equals("name")).findFirst(); 
		if(attrMonad.isPresent()){
			TGGExpression valueExpr = attrMonad.get().getValueExpr();
			if(valueExpr instanceof TGGLiteralExpression){
				String value = TGGLiteralExpression.class.cast(valueExpr).getValue();
				return false && value!=null && value.compareTo("LHS")==0 || value.compareTo("RHS")==0;
			}
		}
		return false;
	}
	
	@Override
	public EObject forceCreation(TGGRuleNode node) {
		return null;
	}

}
