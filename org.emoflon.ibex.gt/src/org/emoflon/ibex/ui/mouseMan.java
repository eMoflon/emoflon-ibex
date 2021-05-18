package org.emoflon.ibex.ui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.EnumSet;
import java.util.Random;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.util.InteractiveElement;
import org.graphstream.ui.view.util.MouseManager;

public class mouseMan implements MouseManager{


	    /**
	     * The view this manager operates upon. 
	     */ 
	    protected View view; 


	    /**
	     * The graph to modify according to the view actions. 
	     */ 
	    protected GraphicGraph graph;


	    protected GraphicElement element; 

	    @Override
	    public void init(GraphicGraph gg, View view) {
	        this.graph = gg;
	        this.view = view;
	        ((Component)view).addMouseListener((MouseListener) this);
	        ((Component)view).addMouseMotionListener((MouseMotionListener) this);
	    }

	    @Override
	    public void release() {
	    	((Component)view).removeMouseListener((MouseListener) this);
	        ((Component)view).removeMouseMotionListener((MouseMotionListener) this);
	    }

	    public void mouseClicked(MouseEvent me) {
	    	element = view.findGraphicElementAt(getManagedTypes(), me.getX(), me.getX());
//	        element = view.findNodeOrSpriteAt(me.getX(), me.getY());
	        if(element != null){
	            Random r = new Random();
	            element.setAttribute("ui.style", "fill-color: rgb("+r.nextInt(256)+","+r.nextInt(256)+","+r.nextInt(256)+");");
	        }

	    }

	    
	    public void mousePressed(MouseEvent me) {
	    }

	    
	    public void mouseReleased(MouseEvent me) {
	    }

	    
	    public void mouseEntered(MouseEvent me) {
	    }

	    
	    public void mouseExited(MouseEvent me) {
	    }

	    
	    public void mouseDragged(MouseEvent me) {
	    }

	    
	    public void mouseMoved(MouseEvent me) {
	    }

		@Override
		public EnumSet<InteractiveElement> getManagedTypes() {
			// TODO Auto-generated method stub
			return null;
		}


}
