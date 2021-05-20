package org.emoflon.ibex.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JRootPane;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
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
import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.Link;
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StateContainer;
import org.emoflon.ibex.gt.StateModel.StateModelFactory;
import org.emoflon.ibex.gt.state.ModelStateManager;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.camera.Camera;
import org.graphstream.ui.view.util.InteractiveElement;

public class SimVis {
	private StateModelFactory factory = StateModelFactory.eINSTANCE;
	
	private SingleGraph graph;
	private ModelStateManager localStateManager;
	
	private SingleGraph initialGraph;
	
	private int nodeID = 0;
	private int edgeID = 0;
	
	private Map<EObject, Node> nodeMap;
	private Map<Link, Edge> edgeMap;
	
	private EList<EObject> initialResourceContents;
	
	private boolean rootActivated = true;
	
	private EObject rootNode;
	private EList<Link> rootEdges;
	
	private Label nodeInfo;
	
	public SimVis(Resource resource) {
		nodeMap = new HashMap<EObject, Node>();
		edgeMap = new HashMap<Link, Edge>();
		rootEdges = new BasicEList<Link>();
		
		graph = new SingleGraph("");
		graph.setStrict(false);
		graph.setAutoCreate(false);
		
		initialResourceContents = new BasicEList<EObject>(resource.getContents());
		createNodesFromList(resource.getContents());
		
	}
	
	
	public List<EObject> createNodesFromList(EList<EObject> list) {
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
						//List<EObject> createdSubNodes = createNodesFromList((EList<EObject>)(node.eGet(ref)));
						List<EObject> createdSubNodes = createNodesFromList(newList);
						for(EObject createdSubNode : createdSubNodes) {
							Link link = factory.createLink();
							link.setSrc(node);
							link.setTrg(createdSubNode);
							createGraphEdge(link);
						}
					}
				}
			}	
		}
		return createdOrOldNodes;
	}
	
	public void UpdateGraphForwards() {
		if(rootActivated) {
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
		}	
	}
	

	
	public void UpdateGraphBackwards() {
		if(rootActivated) {
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
		}
	}
	
//	public void UpdateGraph() {
//		if(localStateManager.getCurrentState().isInitial()) {
//			setInitial();	
//		} else {
//			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
//
//			for(EObject newNode : currentRuleState.getStructuralDelta().getCreatedObjects()) {
//				createGraphNode(newNode);
//			}
//			
//			for(AttributeDelta attributeDelta : currentRuleState.getAttributeDeltas()) {
//				for(EObject node : nodeMap.keySet()) {
//					if(node.equals(attributeDelta.getObject())) {
//						for(EAttribute attribute : node.eClass().getEAttributes()) {
//							if(attributeDelta.getAttribute().equals(attribute)) {
//								node.eSet(attribute, attributeDelta.getNewValue());
//							}
//							
//						}
//					}
//				}
//			}
//			
//			for(Link newLink : currentRuleState.getStructuralDelta().getCreatedLinks()) {
//				createGraphEdge(newLink);
//			}
//			
//			for(EObject deleteNode : currentRuleState.getStructuralDelta().getDeletedObjects()) {
//				deleteGraphNode(deleteNode);
//			}
//			
//			for(Link deleteLink : currentRuleState.getStructuralDelta().getDeletedLinks()) {
//				deleteGraphEdge(deleteLink);
//			}
//		}
//	}
	
	private void setInitial() {
		if(rootActivated) {
			graph.clear();
			nodeMap.clear();
			edgeMap.clear();
			rootNode = null;
			rootEdges.clear();
			nodeID = 0;
			edgeID = 0;
			localStateManager.moveToState(localStateManager.modelStates.getInitialState(),false);
			createNodesFromList(initialResourceContents);
		}
	}
	
	private void createGraphNode(EObject node) {
		if(rootNode == null) {
			rootNode = node;
		}
		graph.addNode(Integer.toString(nodeID));
		nodeMap.put(node, graph.getNode(Integer.toString(nodeID)));
		graph.getNode(Integer.toString(nodeID)).setAttribute("ui.label", node.eClass().getInstanceClassName());
		nodeID++;
		//TODO : Attribute zuweisen?
	}
	
	private void deleteGraphNode(EObject node) {
		graph.removeNode(nodeMap.remove(node));
	}
	
	private void createGraphEdge(Link newLink) {
		Edge createdEdge = graph.addEdge(Integer.toString(edgeID), nodeMap.get(newLink.getSrc()),  nodeMap.get(newLink.getTrg()), true);
		if(createdEdge!=null) {
			edgeMap.put(newLink, createdEdge);
			if(newLink.getSrc().equals(rootNode) || newLink.getTrg().equals(rootNode)) {
				rootEdges.add(newLink);
			}
			edgeID++;
		}
	}

	private void deleteGraphEdge(Link deletedLink) {
		graph.removeEdge(edgeMap.remove(deletedLink));
		if(deletedLink.getSrc().equals(rootNode) || deletedLink.getTrg().equals(rootNode)) {
			rootEdges.remove(deletedLink);
		}
	}

	private void deactivateRootNode() {
		for(Link rootLink : rootEdges) {
			graph.removeEdge(edgeMap.get(rootLink));
		}
		graph.removeNode(nodeMap.get(rootNode));
		rootActivated = false;
	}
	
	private void activateRootNode() {
//		graph.addNode("0");
//		graph.getNode(0).setAttribute("ui.label", rootNode.eClass().getInstanceClassName());
////		for(Link rootLink : rootEdges) {
////			graph.addE
////		}
////		deleteGraphNode(rootNode);
		createGraphNode(rootNode);
		for(Link rootLink : rootEdges) {
			Edge createdEdge = graph.addEdge(Integer.toString(edgeID), nodeMap.get(rootLink.getSrc()),  nodeMap.get(rootLink.getTrg()), true);
			if(createdEdge!=null) {
				edgeMap.put(rootLink, createdEdge);
				edgeID++;
			}
		}
		
		rootActivated = true;
	}
	

	public void openDisplay(ModelStateManager stateManager) {
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
		
		
		
		final Button rootNode = new Button(buttons, SWT.PUSH);
		rootNode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		rootNode.setText("Root Node");
		rootNode.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(rootActivated) {
					deactivateRootNode();
				}
				else { 
					activateRootNode();
				}
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
				if(!localStateManager.getCurrentState().getChildren().isEmpty()) {
					UpdateGraphForwards();
				}
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
				if(!localStateManager.getCurrentState().isInitial()) {
					UpdateGraphBackwards();	
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
		
        try {
			System.setProperty("sun.awt.noerasebackground","true");
		} catch (NoSuchMethodError error) {}
        
		Frame f = SWT_AWT.new_Frame(composite);
		
		//generateGraph(f,"");
		localStateManager = stateManager;
		localStateManager.moveToState(localStateManager.modelStates.getInitialState(), false);
		generatePatternGraph(f, stateManager.modelStates);

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
		        	for(EObject node : nodeMap.keySet()) {	
		        		if(nodeMap.get(node).getId() == element.getId()) {
		        			Display.getDefault().asyncExec(new Runnable() {
								@Override
								public void run() {
									nodeInfo.setText("---------------------------\n" + "NodeID: " + element.getId());
									for(EAttribute attribute : node.eClass().getEAllAttributes()) {
										nodeInfo.setText(nodeInfo.getText() + "\n" + attribute.getName() + ": " + node.eGet(attribute));
				 
				        			}
									nodeInfo.setText(nodeInfo.getText() + "\n" + "---------------------------");
								}
			        		});
//		        			System.out.println("---------------------------");
//	        				System.out.println("NodeID: " + element.getId());
//		        			for(EAttribute attribute : node.eClass().getEAllAttributes()) {
//		        				System.out.println(attribute.getName() + ": " + node.eGet(attribute));
//		        			}
//		        			System.out.println("---------------------------");
		        		}
		        	}
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

