package org.emoflon.ibex.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Event;
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
import java.util.Random;
import java.util.Set;

import javax.swing.JRootPane;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.Link;
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StateContainer;
import org.emoflon.ibex.gt.StateModel.StateModelFactory;
import org.emoflon.ibex.gt.state.ModelStateManager;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodeSet;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
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
	private Map<EObject, Edge> edgeMap;
	
	private Map<EObject, Node> initialNodeMap;
	private Map<EObject, Edge>  initialEdgeMap;
	
	public SimVis(Resource resource) {
		nodeMap = new HashMap<EObject, Node>();
		edgeMap = new HashMap<EObject, Edge>();
		
		graph = new SingleGraph("");
		graph.setStrict(false);
		graph.setAutoCreate(false);
		
	
		createNodesFromList(resource.getContents());
//		for(EObject node : resource.getContents()) {
//			if(!nodeMap.keySet().contains(node)) {
//				createGraphNode(node);
//			}
//			 //EObjectContainmentEList.class;
//			EReference subNodes;
//			while(!node.eClass().getEReferences().isEmpty()) {
//				
//			}
//			
//			
//			for(EReference ref : node.eClass().getEReferences()) {
//				System.out.println(ref.getName());
//				EObjectContainmentEList<EObject> test = (EObjectContainmentEList<EObject>) node.eGet(ref);
//				for(EObject obj : test) {
//					obj.eClass().getEReferences().isEmpty();
//					for(EReference ref2 : obj.eClass().getEReferences()) {
//						System.out.println(ref2.getName());
//					}
//				}
////				EList<EReference> ref2 = ref.eClass().getEReferences();
//				System.out.print("stop");
//			}
//			
//			for(EReference ref : node.eClass().getEAllContainments()) {
//				System.out.println(ref.getName());
//			}
//			
//			
//			//EReferenceImpl
////			for(EObject nodes : node.eClass().eContents()) {
////				System.out.println(nodes.eClass().getInstanceClassName());
////			}
////			TreeIterator<EObject> it = node.eClass().eAllContents();
////			while(it.hasNext()) {
////				EObject s = it.next();
////				System.out.println(s.eClass().getInstanceTypeName());
////			}
//
//		}
////		for(IBeXEdge edge : resource.getEdgeSet().getEdges()) {
////			if(!(nodeMap.keySet().contains(edge.getTargetNode())) && !(nodeMap.keySet().contains(edge.getSourceNode()))) {
////				newGraphNode(edge.getTargetNode());
////				newGraphNode(edge.getSourceNode());
////			} else if (nodeMap.keySet().contains(edge.getTargetNode()) && !(nodeMap.keySet().contains(edge.getSourceNode()))) {
////				newGraphNode(edge.getSourceNode());
////			} else if (!(nodeMap.keySet().contains(edge.getTargetNode())) && nodeMap.keySet().contains(edge.getSourceNode())) {
////				newGraphNode(edge.getTargetNode());
////			}
////			createGraphEdge(edge);
////		}
//		for(EObject node : resource.getContents()) {
//			if(!nodeMap.keySet().contains(node)) {
//				System.out.println(node.eClass().getInstanceClassName());
//				createGraphNode(node);
//			}
//		}
		
		initialNodeMap = new HashMap<EObject, Node>(nodeMap);
		initialEdgeMap = new HashMap<EObject, Edge>(edgeMap);
		
	}
	
	
	public List<EObject> createNodesFromList(EList<EObject> list) {
		List<EObject> createdOrOldNodes = new ArrayList<EObject>();
		for(EObject node : list) {
			if(!nodeMap.keySet().contains(node)) {
				createGraphNode(node);
			}
			createdOrOldNodes.add(node);
			if(!node.eClass().getEReferences().isEmpty()) {
				for(EReference ref : node.eClass().getEReferences()) {
					List<EObject> createdSubNodes = createNodesFromList((EList<EObject>)node.eGet(ref));
					for(EObject createdSubNode : createdSubNodes) {
						Link link = factory.createLink();
						link.setSrc(node);
						link.setTrg(createdSubNode);
						createGraphEdge(link);
					}
				}
			}
		}
		return createdOrOldNodes;
	}
	
	public void UpdateGraphForwards() {
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
	

	
	public void UpdateGraphBackwards() {
//		if(localStateManager.getCurrentState().isInitial()) {
//			setInitial();	
//		} else {
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
//		}
		localStateManager.revertToPrevious();
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

		List<EObject> deleteList = new ArrayList<EObject>();
		for(EObject node : nodeMap.keySet()) {
			if(!initialNodeMap.containsKey(node))
				deleteList.add(node);
		}
		for(EObject node : deleteList) {
			deleteGraphNode(node);
		}
		
		deleteList = new ArrayList<EObject>();
		for(EObject edge : edgeMap.keySet()) {
			if(!initialEdgeMap.containsKey(edge));
				deleteList.add(edge);
		}
		for(EObject edge : deleteList) {
			deleteGraphEdge(edge);
		}
		
		nodeMap = new HashMap<EObject, Node>(initialNodeMap);
		edgeMap = new HashMap<EObject, Edge>(initialEdgeMap);

	}
	
	private void createGraphNode(EObject node) {
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
			edgeID++;
		}
	}

	private void deleteGraphEdge(EObject deletedEdge) {
		graph.removeEdge(edgeMap.remove(deletedEdge));	
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void openDisplay(ModelStateManager stateManager) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		
		FillLayout fillLayout = new FillLayout();
        fillLayout.type = SWT.VERTICAL;
        shell.setLayout(fillLayout);
        
		Composite composite = new Composite(shell, SWT.NO_BACKGROUND | SWT.EMBEDDED);

		final Button stepForward = new Button(shell, SWT.PUSH);
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
//				if(!localStateManager.getCurrentState().getChildren().isEmpty()) {
//					localStateManager.moveToState(localStateManager.getCurrentState().getChildren().get(0), false);
//					UpdateGraphForwards();
//				}
			}
			
		});
		
		final Button stepBack = new Button(shell, SWT.PUSH);
		stepBack.setText("StepBackward");
	
		stepBack.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!localStateManager.getCurrentState().isInitial()) {
					//localStateManager.revertToPrevious();
					UpdateGraphBackwards();	
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
//				if(!localStateManager.getCurrentState().isInitial()) {
//					//localStateManager.revertToPrevious();
//					UpdateGraphBackwards();	
//				}
			}
			
		});
		
		
        try {
			System.setProperty("sun.awt.noerasebackground","true");
		} catch (NoSuchMethodError error) {}
        
		Frame f = SWT_AWT.new_Frame(composite);

		//generateGraph(f,"");
		localStateManager = stateManager;
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
		        			System.out.println("---------------------------");
	        				System.out.println("NodeID: " + element.getId());
		        			for(EAttribute attribute : node.eClass().getEAllAttributes()) {
		        				System.out.println(attribute.getName() + ": " + node.eGet(attribute));
		        			}
		        			System.out.println("---------------------------");
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


