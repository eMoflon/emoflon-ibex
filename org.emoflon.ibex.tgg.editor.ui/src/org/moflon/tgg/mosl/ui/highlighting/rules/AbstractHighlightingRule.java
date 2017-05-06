package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.ui.highlighting.exceptions.IDAlreadyExistException;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLHighlightProviderHelper;
import org.moflon.util.LogUtils;


public abstract class AbstractHighlightingRule{

	protected Logger logger;
	
	protected String id;
	
	protected String description;
	
	private int prio=50;
	
	private IHighlightedPositionAcceptor acceptor;
	
	public AbstractHighlightingRule(String id, String description){
		init(id, description);
	}
	
	public AbstractHighlightingRule(String id, String description, int prio){
		this.prio = prio;
		init(id, description);
	}
	
	private void init(String id, String description){
		logger = Logger.getLogger(this.getClass());
		this.id = id;
		this.description = description;
		try {
			MOSLHighlightProviderHelper.addHighlightRule(this);
		} catch (IDAlreadyExistException e) {
         LogUtils.error(logger, e);
		}
	}
	
	protected void setHighlighting(INode node){
		acceptor.addPosition(node.getOffset(), node.getLength() , id);
	}
	
	protected abstract TextStyle getTextStyle();
	
	public void setHighlightingConfiguration(IHighlightingConfigurationAcceptor acceptor){
		acceptor.
		acceptDefaultHighlighting(id, description, getTextStyle());
	}
	
	public boolean canProvideHighlighting(EObject moslObject, INode node, IHighlightedPositionAcceptor acceptor){
		boolean provide = getHighlightingConditions(moslObject, node);
		if(provide){
			this.acceptor = acceptor;
			setHighlighting(node);
		}
		return provide;
	}
	
	public int getPriority(){
		return prio;
	}
	
	protected abstract boolean getHighlightingConditions(EObject moslObject, INode node);
	
	public String getID(){
		return id;
	}
}
