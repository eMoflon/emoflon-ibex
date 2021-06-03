package org.emoflon.ibex.ui;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JRootPane;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.Link;
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StateContainer;
import org.emoflon.ibex.gt.StateModel.StateModelFactory;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.gt.state.ModelStateManager;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Color;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.camera.Camera;
import org.graphstream.ui.view.util.InteractiveElement;

public class SimVis {
	private StateModelFactory factory = StateModelFactory.eINSTANCE;
	
	private SingleGraph graph;
	private ModelStateManager localStateManager;
	private GraphTransformationInterpreter localGraphTransformationInterpreter;
	
	private int nodeID = 0;
	private int edgeID = 0;
	
	private Map<EObject, Node> nodeMap;
	private Map<Link, Edge> edgeMap;
	
	private EList<EObject> initialResourceContents;
	
	private boolean showFuture = false;
	private boolean showCurrent = false;
	
	private Map<EObject, Node> infoNodes;
	
	private Label nodeInfo;
	private Label matchRuleInfo;
	
    protected static String styleSheet =
		"edge {"
		+ "size: 2px;"
		+ "fill-color: black;"
//		+ "stroke-mode: dashes;"				Ermöglicht dashed edges
//		+ "stroke-color: black;"
//		+ "stroke-width: 10px;"
		+ "fill-mode: dyn-plain;"
		+ "text-alignment: under;"
		+ "text-background-mode: plain;"
		+ "}"
		
		+ "node {"
		+ "size-mode: fit;"
		+ "shape: box;"
		//+ "size: 12px;"
		+ "fill-color: grey, red, blue, green;"
		+ "stroke-mode: plain;"
		+ "stroke-color: black;"
		+ "stroke-width: 1.5px;"
		+ "text-mode: normal;"
		+ "text-background-color: grey;"
		+ "padding: 3px, 3px;"
		+ "text-background-mode: plain;"
		+ "fill-mode: dyn-plain;"
		//+ "text-alignment: under;"
		+ "}";
	
	
	public SimVis(Resource resource) {
		nodeMap = new HashMap<EObject, Node>();
		edgeMap = new HashMap<Link, Edge>();
		infoNodes = new HashMap<EObject, Node>();
		
		graph = new SingleGraph("");
		graph.setStrict(false);
		graph.setAutoCreate(false);
		graph.setAttribute("ui.stylesheet", styleSheet);	

		initialResourceContents = new BasicEList<EObject>(resource.getContents());
		createNodesFromList(resource.getContents());	
	}
	
	
	@SuppressWarnings("unchecked")
	private List<EObject> createNodesFromList(EList<EObject> list) {
		List<EObject> createdOrOldNodes = new ArrayList<EObject>();
		for(EObject node : list) {
			createdOrOldNodes.add(node);
			if(!nodeMap.keySet().contains(node)) {
				createGraphNode(node);
				if(!node.eClass().getEReferences().isEmpty()) {
					for(EReference ref : node.eClass().getEReferences()) {
						EList<EObject> newList = new BasicEList<EObject>();
						if(!(node.eGet(ref) instanceof EList)) {
							if(node.eGet(ref) != null)
								newList.add((EObject) node.eGet(ref));
						} else {
							newList = (EList<EObject>)(node.eGet(ref));
						}
						List<EObject> createdSubNodes = createNodesFromList(newList);
						for(EObject createdSubNode : createdSubNodes) {
							Link link = factory.createLink();
							link.setSrc(node);
							link.setTrg(createdSubNode);
							// TODO: Link-Typ zuweisen
							createGraphEdge(link);
						}
					}
				}
			}	
		}
		return createdOrOldNodes;
	}
	
	private void createInfoNode(EObject targetNode) {
		if(!infoNodes.keySet().contains(targetNode)) {
			Node addedNode = graph.addNode(Integer.toString(nodeID));
			Edge addedEdge = graph.addEdge(Integer.toString(edgeID), addedNode, nodeMap.get(targetNode));
			nodeID++;
			edgeID++;
			
			addedNode.setAttribute("ui.style", 
					"fill-color: grey;"
					+ "shape: circle;"
					+ "text-background-color: grey;"
					+ "stroke-mode: plain;"
					+ "stroke-color: rgba(0,150,0,255);"
					+ "stroke-width: 2.0px;");
			addedNode.setAttribute("ui.label", "++");
			
			addedEdge.setAttribute("ui.style", 
					"fill-color: white;"
					+ "stroke-mode: dashes;"
					+ "stroke-color: black;"
					+ "stroke-width: 2.0px;");
			
			infoNodes.put(targetNode, addedNode);
		}
	}
	
	private void showCurrentObjects() {
		if(localStateManager.getCurrentState().isInitial()) {
			resetApplyVis();
		} else {
			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
			
			for(EObject newNode : currentRuleState.getStructuralDelta().getCreatedObjects()) {
				nodeMap.get(newNode).setAttribute("ui.style", "fill-color: rgba(0,150,0,255); text-background-color: rgba(0,150,0,255);");
				nodeMap.get(newNode).setAttribute("ui.label", nodeMap.get(newNode).getAttribute("ui.label") + " ++");
			}
	
			for(AttributeDelta attributeDelta : currentRuleState.getAttributeDeltas()) {
				createInfoNode(attributeDelta.getObject());
			}
			
			for(Link newLink : currentRuleState.getStructuralDelta().getCreatedLinks()) {
				if(edgeMap.containsKey(newLink)) {
					edgeMap.get(newLink).setAttribute("ui.style", "fill-color: rgba(0,150,0,255);");
					edgeMap.get(newLink).setAttribute("ui.label", newLink.getType().getName() + " ++");
				}
			}
		}
	}
	
	private void showFutureObjects() {
		if(!localStateManager.getCurrentState().getChildren().isEmpty()) {
			RuleState nextRuleState = (RuleState)localStateManager.getCurrentState().getChildren().get(0);
			
			for(EObject newNode : nextRuleState.getStructuralDelta().getCreatedObjects()) {
				createGraphNode(newNode);
				nodeMap.get(newNode).setAttribute("ui.style", "fill-color: rgba(0,150,0,255); text-background-color: rgba(0,150,0,255);");
				nodeMap.get(newNode).setAttribute("ui.label", nodeMap.get(newNode).getAttribute("ui.label") + " ++");
			}
			
			for(EObject deletedNode : nextRuleState.getStructuralDelta().getDeletedObjects()) {
				nodeMap.get(deletedNode).setAttribute("ui.style", "fill-color: rgba(150,0,0,255); text-background-color: rgba(255,0,0,128);");
				nodeMap.get(deletedNode).setAttribute("ui.label", nodeMap.get(deletedNode).getAttribute("ui.label") + " --");
			}
			
			for(Link createdLink : nextRuleState.getStructuralDelta().getCreatedLinks()) {
				Edge edge = createGraphEdge(createdLink);
				if(edge!=null) {
					edge.setAttribute("ui.style", "fill-color: rgba(0,150,0,255);");
					edge.setAttribute("ui.label", createdLink.getType().getName() + " ++");
				}
			}
			
			for(Link deleteLink : nextRuleState.getStructuralDelta().getDeletedLinks()) {
				edgeMap.get(deleteLink).setAttribute("ui.style", "fill-color: rgba(150,0,0,255);");
				edgeMap.get(deleteLink).setAttribute("ui.label", deleteLink.getType().getName() + " --");
			}
			
			for(AttributeDelta attributeDelta : nextRuleState.getAttributeDeltas()) {
				createInfoNode(attributeDelta.getObject());
			}
		}
	}
	
	private void revertFutureObjects() {
		if(!localStateManager.getCurrentState().getChildren().isEmpty()) {
			RuleState nextRuleState = (RuleState)localStateManager.getCurrentState().getChildren().get(0);
			
			for(Link newLink : nextRuleState.getStructuralDelta().getCreatedLinks()) {
				deleteGraphEdge(newLink);
			}
			
			for(EObject newNode : nextRuleState.getStructuralDelta().getCreatedObjects()) {
				deleteGraphNode(newNode);
			}
		
			for(EObject deleteNode : nextRuleState.getStructuralDelta().getDeletedObjects()) {
				createGraphNode(deleteNode);
			}
			
			for(Link deleteLink : nextRuleState.getStructuralDelta().getDeletedLinks()) {
				createGraphEdge(deleteLink);
			}
			
			resetApplyVis();
		}
	}
	
	private void resetApplyVis() {
		for(Node node : infoNodes.values()) {
			graph.removeNode(node);
		}
		infoNodes.clear();
		
		for(Node n : graph.nodes().toList()) {
			n.setAttribute("ui.style", "fill-color: grey; text-background-color: grey; shadow-mode: none;");
			String label = (String) n.getAttribute("ui.label");
			if(label.contains("++")) {
				n.setAttribute("ui.label", label.subSequence(0, label.length()-2));
			}
		}
		
		for(Edge e : graph.edges().toList()) {
			e.setAttribute("ui.style", "fill-color: black; shadow-mode: none;");
			String label = (String) e.getAttribute("ui.label");
			if(label.contains("++")) {
				e.setAttribute("ui.label", label.subSequence(0, label.length()-2));
			}
		}
	}
	
	private void UpdateGraphForwards() {
		if(!localStateManager.getCurrentState().getChildren().isEmpty()) {
			nodeInfo.setText("");
			resetApplyVis();
	
			localStateManager.moveToState(localStateManager.getCurrentState().getChildren().get(0), false);
			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
			
			for(EObject newNode : currentRuleState.getStructuralDelta().getCreatedObjects()) {
				createGraphNode(newNode);
			}
	
			for(AttributeDelta attributeDelta : currentRuleState.getAttributeDeltas()) {
				for(EObject node : nodeMap.keySet()) {
					if(node.equals(attributeDelta.getObject())) {
						for(EAttribute attribute : node.eClass().getEAttributes()) {
							if(attributeDelta.getAttribute().equals(attribute)) {
								node.eSet(attribute, attributeDelta.getNewValue());
							}
							
						}
					}
				}
			}
				
			for(Link newLink : currentRuleState.getStructuralDelta().getCreatedLinks()) {
				createGraphEdge(newLink);
			}
			
			for(Link deleteLink : currentRuleState.getStructuralDelta().getDeletedLinks()) {
				deleteGraphEdge(deleteLink);
			}
				
			for(EObject deleteNode : currentRuleState.getStructuralDelta().getDeletedObjects()) {
				deleteGraphNode(deleteNode);
			}
				
			setMatchRuleInfo();
			
			if(showCurrent) 
				showCurrentObjects();
			else if(showFuture) 
				showFutureObjects();
		}
			
	}
	
	private void UpdateGraphBackwards() {
		if(!localStateManager.getCurrentState().isInitial()) {
			nodeInfo.setText("");
			resetApplyVis();
			revertFutureObjects();	
			
			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
	
			for(Link newLink : currentRuleState.getStructuralDelta().getCreatedLinks()) {
				deleteGraphEdge(newLink);
			}
				
			for(EObject newNode : currentRuleState.getStructuralDelta().getCreatedObjects()) {
				deleteGraphNode(newNode);
			}
				
			for(AttributeDelta attributeDelta : currentRuleState.getAttributeDeltas()) {
				for(EObject node : nodeMap.keySet()) {
					if(node.equals(attributeDelta.getObject())) {
						for(EAttribute attribute : node.eClass().getEAttributes()) {
							if(attributeDelta.getAttribute().equals(attribute)) {
								node.eSet(attribute, attributeDelta.getNewValue());
							}
								
						}
					}
				}
			}
			
			for(EObject deleteNode : currentRuleState.getStructuralDelta().getDeletedObjects()) {
				createGraphNode(deleteNode);
			}
				
			for(Link deleteLink : currentRuleState.getStructuralDelta().getDeletedLinks()) {
				createGraphEdge(deleteLink);
			}	
			
			localStateManager.revertToPrevious();
			
			setMatchRuleInfo();
			
			if(showCurrent) 
				showCurrentObjects();
			else if(showFuture) 
				showFutureObjects();
		}
	}
	
	// TODO: Add List and beautiful text with new UI
	private void setMatchRuleInfo() {
		if(!localStateManager.getCurrentState().isInitial()) {
			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
			matchRuleInfo.setText("Actual Rule:\n" +  currentRuleState.getRule().getName());			
		} else {
			matchRuleInfo.setText("");
		}
		
		if(!localStateManager.getCurrentState().getChildren().isEmpty()) {
				
			RuleState nextRuleState = (RuleState)localStateManager.getCurrentState().getChildren().get(0);
			List<IMatch> matchStream = localGraphTransformationInterpreter.matchStream(nextRuleState.getRule().getName(), (Map<String, Object>) nextRuleState.getParameter() , true).toList();
			// TODO: Use stream to put matches in ui list and make them visuable
			matchRuleInfo.setText(matchRuleInfo.getText() + "\n\nNext Rule:\n" + nextRuleState.getRule().getName() + "\nMatches: " + matchStream.size());
		}
		
	}
	
	private void setInitial() {
			graph.clear();
			graph.setAttribute("ui.stylesheet", styleSheet);
			
			nodeMap.clear();
			edgeMap.clear();
			nodeID = 0;
			edgeID = 0;
			
			localStateManager.moveToState(localStateManager.modelStates.getInitialState(),false);
			createNodesFromList(initialResourceContents);
			
			// TODO: Reset Buttons so nothing is checked
	}
	
	private void createGraphNode(EObject node) {
		if(!nodeMap.keySet().contains(node)) {
			// Add node to the graph and map graph node to EObject node
			nodeMap.put(node, graph.addNode(Integer.toString(nodeID)));
			
			// Label node
			if(node.eClass().getInstanceClassName().indexOf(".") != -1)
				graph.getNode(Integer.toString(nodeID)).setAttribute("ui.label", node.eClass().getInstanceClassName().substring(node.eClass().getInstanceClassName().indexOf(".") + 1));
			else
				graph.getNode(Integer.toString(nodeID)).setAttribute("ui.label", node.eClass().getInstanceClassName());
			
			// Increment nodeID
			nodeID++;
		}	
	}
	
	private void deleteGraphNode(EObject node) {
		// Delete graph node and mapping
		graph.removeNode(nodeMap.remove(node));
	}
	
	private Edge createGraphEdge(Link newLink) {
		// No check with edgeMap because different Link-objects can have same target and source
		// Instead check if link is already in graph
		Edge createdEdge = graph.addEdge(Integer.toString(edgeID), nodeMap.get(newLink.getSrc()),  nodeMap.get(newLink.getTrg()), true);
		if(createdEdge!=null) {
			
			// Map graph edge to EObject edge
			edgeMap.put(newLink, createdEdge);
			
			// Label edge
			createdEdge.setAttribute("ui.label", newLink.getType().getName());
			
			// Increment edgeID;
			edgeID++;	
			
			return createdEdge;
		}
		return null;
	}

	private void deleteGraphEdge(Link deletedLink) {
		// Delete graph edge and mapping
		graph.removeEdge(edgeMap.remove(deletedLink));
	}
	
	private void showAttributes(String nodeID) {
		EObject node = null;
		boolean infoNode = true;
		for(EObject n : nodeMap.keySet()) {
			if(nodeMap.get(n).getId()==nodeID) {
				node = n;
				infoNode = false;
			}
		}
		if(infoNode)
			for(EObject n : infoNodes.keySet()) {
				if(infoNodes.get(n).getId()==nodeID)
					node = n;
			}
		
		if(node!=null) {
			String info = node.eClass().getInstanceClassName();
			for(EAttribute attribute : node.eClass().getEAllAttributes()) {
				info = info + "\n" + attribute.getName() + ": " + node.eGet(attribute);
				if(infoNode) {
					for(AttributeDelta delta : ((RuleState)localStateManager.getCurrentState().getChildren().get(0)).getAttributeDeltas()) {
						if(delta.getObject().equals(node) && delta.getAttribute().equals(attribute)) {
							info = info + " --> " + delta.getNewValue();
						}
					}
				}
			}
			nodeInfo.setText(info);
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void openDisplay(ModelStateManager stateManager, GraphTransformationInterpreter graphTransformationInterpreter) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		
		FillLayout fillLayout = new FillLayout();
        fillLayout.type = SWT.VERTICAL;
        shell.setLayout(fillLayout);
        
//        GridLayout gridLayout = new GridLayout(2, true);
//
//        shell.setLayout(gridLayout);
        
		Composite composite = new Composite(shell, SWT.NO_BACKGROUND | SWT.EMBEDDED);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Composite buttons = new Composite(shell, SWT.BORDER);
		buttons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		buttons.setLayout(new GridLayout(2, true));
	
		final Button setInitial = new Button(buttons, SWT.PUSH);
		setInitial.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setInitial.setText("Set Initial");
		setInitial.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				setInitial();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
//		NOT USED ATM		
		
		final Button rootNode = new Button(buttons, SWT.PUSH);
		rootNode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		rootNode.setText("Toggle Root Node");
		rootNode.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// Auto-generated method stub
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
		final Button stepForward = new Button(buttons, SWT.PUSH);
		stepForward.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		stepForward.setText("StepForward");
		stepForward.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
					UpdateGraphForwards();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
		final Button stepBack = new Button(buttons, SWT.PUSH);
		stepBack.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		stepBack.setText("StepBackward");
		stepBack.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
					UpdateGraphBackwards();	
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
		final Button showActualCheck = new Button(buttons, SWT.CHECK);
		showActualCheck.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		showActualCheck.setText("Show Actual Apply");
		showActualCheck.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(showActualCheck.getSelection()) {
					showCurrentObjects();
					showCurrent = true;
				}
				else { 
					resetApplyVis();
					showCurrent = false;
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
		final Button showFutureCheck = new Button(buttons, SWT.CHECK);
		showFutureCheck.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		showFutureCheck.setText("Show Future");
		showFutureCheck.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(showFutureCheck.getSelection()) {
					showFutureObjects();
					showFuture = true;
				}
				else { 
					revertFutureObjects();
					showFuture = false;
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
		nodeInfo = new Label(buttons, SWT.EMBEDDED);
		nodeInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		nodeInfo.setText("");
		
		matchRuleInfo = new Label(buttons, SWT.EMBEDDED);
		matchRuleInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		matchRuleInfo.setText("");
		
        try {
			System.setProperty("sun.awt.noerasebackground","true");
		} catch (NoSuchMethodError error) {}
        
		Frame f = SWT_AWT.new_Frame(composite);

		//generateGraph(f,"");
		localStateManager = stateManager;
		localGraphTransformationInterpreter = graphTransformationInterpreter;
		localStateManager.moveToState(localStateManager.modelStates.getInitialState(), false);
		generatePatternGraph(f, stateManager.modelStates);
		setMatchRuleInfo();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
	}
	
	private void generatePatternGraph(Frame frame, StateContainer modelStates) {
		for(State state : modelStates.getStates()) {
			if(!state.isInitial()) {
				RuleState ruleState = (RuleState) state;
				System.out.println(ruleState.getRule().getName());
			}
		}
		
		Viewer graphstreamViewer = new SwingViewer(graph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		//graphstreamViewer.setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);

		graphstreamViewer.enableAutoLayout();

		View graphstreamView = graphstreamViewer.addDefaultView(false);
		
		((Component) graphstreamView).addMouseWheelListener(new MouseWheelListener() {
		    @Override
		    public void mouseWheelMoved(MouseWheelEvent e) {
		        e.consume();
		        int i = e.getWheelRotation();
		        double factor = Math.pow(1.25, i);
		        Camera cam = graphstreamView.getCamera();
		        double zoom = cam.getViewPercent() * factor;
		        Point2 pxCenter  = cam.transformGuToPx(cam.getViewCenter().x, cam.getViewCenter().y, 0);
		        Point3 guClicked = cam.transformPxToGu(e.getX(), e.getY());
		        double newRatioPx2Gu = cam.getMetrics().ratioPx2Gu/factor;
		        double x = guClicked.x + (pxCenter.x - e.getX())/newRatioPx2Gu;
		        double y = guClicked.y - (pxCenter.y - e.getY())/newRatioPx2Gu;
		        cam.setViewCenter(x, y, 0);
		        cam.setViewPercent(zoom);
		    }
		});
		((Component) graphstreamView).addMouseListener(new MouseListener() {

			EnumSet<InteractiveElement> types;
			@Override
			public void mouseClicked(MouseEvent e) {
				types = EnumSet.of(InteractiveElement.NODE);
				GraphicElement element = graphstreamView.findGraphicElementAt(types, e.getX(), e.getY());
		        if(element != null){
		        	Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							showAttributes(element.getId());	
						}
	        		});
		        }
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Panel panel = new Panel(new BorderLayout()) {
			public void update(java.awt.Graphics g) {
				/* Do not erase the background */ 
				paint(g);
			}
		};
		JRootPane root = new JRootPane();
		panel.add(root);
		root.getContentPane().add((Component) graphstreamView);
		
		frame.add(panel);
		frame.setVisible(true);
		
	}

}
/*
 * Step Forward bis Edge da ist -> set initial -> step forward bis edge da ist --> root node --> absturz (Fixed)
 */

