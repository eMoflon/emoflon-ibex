package org.emoflon.ibex.ui;
//
//import org.eclipse.swt.*;
//import org.eclipse.swt.layout.*;
//import org.eclipse.swt.widgets.*;
//
//import org.graphstream.graph.Graph;
//import org.graphstream.graph.implementations.SingleGraph;
//import org.graphstream.ui.swing_viewer.SwingViewer;
//import org.graphstream.ui.view.View;
//import org.graphstream.ui.view.Viewer;
//
//public class displayTest {
//	
//	public static void openDisplay() {
//		Display display = new Display();
//		Shell shell = new Shell(display);
//		shell.open();
//	
//		while (!shell.isDisposed()) { 
//		    if (!display.readAndDispatch()) 
//		     { display.sleep();} 
//		}
//	
//		// disposes all associated windows and their components
//		display.dispose();
//	}
//}

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JRootPane;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
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
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StateContainer;
import org.emoflon.ibex.gt.state.ModelStateManager;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodeSet;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
//import org.graphstream.ui.swingViewer.View;
//import org.graphstream.ui.swingViewer.Viewer;
//import org.graphstream.ui.swingViewer.Viewer.CloseFramePolicy;

import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

public class displayTest {
	static Graph graph;
	static ModelStateManager localStateManager;
	static int nodeID = 0;
	static int edgeID = 0;
	private static List<IBeXNode> startNodes;
	
	private static Map<IBeXNode, Node> nodeMap;
	private static Map<IBeXEdge, Edge> edgeMap;
	
	public static void prepareVisualisation(IBeXModel model) {
		graph = new SingleGraph("TestGraph");
		graph.setStrict(false);
		graph.setAutoCreate(false);	
//		
//		//int nodeID = 0;
//		int edgeID = -1;
//		
//		int maxNodes= nodeSet.getNodes().size();
//		System.out.println(maxNodes);
//		for(IBeXNode node : nodeSet.getNodes()) {
//			graph.addNode(Integer.toString(nodeID));
////			int sourceID = nodeID;
////			for(IBeXEdge outEdge : node.getOutgoingEdges()) {
////				nodeID++;
////				graph.addNode(Integer.toString(nodeID));
////				graph.getNode(Integer.toString(nodeID)).setAttribute("ui.label", node.getName());
////				graph.addEdge(Integer.toString(edgeID), sourceID, nodeID);
////				edgeID--;
////			}
//			graph.getNode(Integer.toString(nodeID)).setAttribute("ui.label", node.getName());
//			nodeID++;
//		}
//		System.out.print(nodeID);
		startNodes = new ArrayList<IBeXNode>();
		edgeMap = new HashMap<IBeXEdge, Edge>();
		nodeMap = new HashMap<IBeXNode, Node>();
		for(IBeXEdge edge : model.getEdgeSet().getEdges()) {
			if(!(startNodes.contains(edge.getTargetNode())) && !(startNodes.contains(edge.getSourceNode()))) {
				graph.addNode(Integer.toString(nodeID));
				startNodes.add(edge.getTargetNode());
				nodeMap.put(edge.getTargetNode(), graph.getNode(Integer.toString(nodeID)));
				graph.getNode(Integer.toString(nodeID)).setAttribute("ui.label", edge.getTargetNode().getName());
				nodeID++;
				graph.addNode(Integer.toString(nodeID));
				startNodes.add(edge.getSourceNode());
				nodeMap.put(edge.getSourceNode(), graph.getNode(Integer.toString(nodeID)));
				graph.getNode(Integer.toString(nodeID)).setAttribute("ui.label", edge.getSourceNode().getName());
				nodeID++;
			} else if (startNodes.contains(edge.getTargetNode()) && !(startNodes.contains(edge.getSourceNode()))) {
				graph.addNode(Integer.toString(nodeID));
				startNodes.add(edge.getSourceNode());
				nodeMap.put(edge.getSourceNode(), graph.getNode(Integer.toString(nodeID)));
				graph.getNode(Integer.toString(nodeID)).setAttribute("ui.label", edge.getSourceNode().getName());
				nodeID++;
			} else if (!(startNodes.contains(edge.getTargetNode())) && startNodes.contains(edge.getSourceNode())) {
				graph.addNode(Integer.toString(nodeID));
				startNodes.add(edge.getTargetNode());
				nodeMap.put(edge.getTargetNode(), graph.getNode(Integer.toString(nodeID)));
				graph.getNode(Integer.toString(nodeID)).setAttribute("ui.label", edge.getTargetNode().getName());
				nodeID++;
			}
			
			Edge createdEdge = graph.addEdge(Integer.toString(edgeID), startNodes.indexOf(edge.getSourceNode()), startNodes.indexOf(edge.getTargetNode()), true);
			if(createdEdge!=null) {
				edgeMap.put(edge, createdEdge);
				graph.getEdge(Integer.toString(edgeID)).setAttribute("ui.label", edge.getName());
				edgeID++;
			}else {
				for(IBeXEdge existingEdge : edgeMap.keySet()) {
					if(existingEdge.getSourceNode().equals(edge.getSourceNode()) && existingEdge.getTargetNode().equals(edge.getTargetNode())) {
						//graph.getEdge(edgeMap.get(existingEdge).getId()).setAttribute("ui.label", Integer.toString(  Integer.parseInt(   (String) graph.getEdge(edgeMap.get(existingEdge).getId()).getAttribute("ui.label")    )+1 ));
						//edgeID++; //Je nachdem, ob zwei kanten erlaubt oder nicht //egal?
					}
				}
			}
		}
		System.out.println(edgeID);
		for(IBeXNode node : model.getNodeSet().getNodes()) {
			if(!startNodes.contains(node)) {
				graph.addNode(Integer.toString(nodeID));
				startNodes.add(node);
				graph.getNode(Integer.toString(nodeID)).setAttribute("ui.label", node.getName());
				nodeID++;
			}
		}
		
	}
	
	//public static void main(String[] args) {
	public static void openDisplay(ModelStateManager stateManager) {
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
				localStateManager.moveToState(localStateManager.getCurrentState().getChildren().get(0), false);
				UpdateGraph();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				localStateManager.moveToState(localStateManager.getCurrentState().getChildren().get(0), false);
				UpdateGraph();
			}
			
		});
		
		final Button stepBack = new Button(shell, SWT.PUSH);
		stepBack.setText("StepBackward");
	
		stepBack.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				//localStateManager.revertToPrevious();
				UpdateGraph();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				//localStateManager.revertToPrevious();
				UpdateGraph();
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
	
	private static void generatePatternGraph(Frame frame, StateContainer modelStates) {
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
		
	public static void UpdateGraph() {
		if(localStateManager.getCurrentState().isInitial()) {
			for(IBeXNode newNode : nodeMap.keySet()) {
				if(!startNodes.contains(newNode)) {
					graph.removeNode(nodeMap.get(newNode));
				}
			}
			
			// remove aus den listen und nodeID anpassen
		} else {
			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
			currentRuleState.getStructuralDelta().getCreatedObjects();
			System.out.println(currentRuleState.getRule().getName());
		
		
			
			for(IBeXNode node : currentRuleState.getRule().getCreate().getCreatedNodes()) {
				System.out.println(node.getName());
			}
			
			for(IBeXEdge edge : currentRuleState.getRule().getCreate().getCreatedEdges()) {
				System.out.println(edge.getName());
			}
			
			for(IBeXNode node : currentRuleState.getRule().getCreate().getCreatedNodes()) {
				//IBeXNode newNode = (IBeXNode) newObject;
				graph.addNode(Integer.toString(nodeID));
				graph.getNode(Integer.toString(nodeID)).setAttribute("ui.label", node.getName());
				nodeMap.put(node, graph.getNode(Integer.toString(nodeID)));
				//nodeMap.put(node, graph.getNode(Integer.toString(nodeID)));
				nodeID++;
				//graph.getNode(Integer.toString(nodeID)).setAttribute("ui.label", newNode.getName());
				
			}
		}
	}
	
	
	//gt.ui
	
	//reexport 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void generateGraph(Frame frame, String networkName) {
		Graph graph = new SingleGraph(networkName);
		graph.setStrict(false);
		graph.setAutoCreate(true);

		BarabasiAlbertGenerator gen = new BarabasiAlbertGenerator();
		gen.addSink(graph);
		gen.begin();
		for (int i = 0; i < 1000; i++)
			gen.nextEvents();
		gen.end();
		gen.removeSink(graph);

		for (int i = 0; i < graph.getNodeCount(); i++) {
			Node n = graph.getNode(i);
			String type = "Sensor";

			switch ((int) (Math.random() * 4)) {
			default:
				type = "Sensor";
				break;
			case 1:
				type = "Actuator";
				break;
			case 2:
				type = "Function";
				break;
			case 3:
				type = "Message";
				break;
			}

			n.setAttribute("ui.label", n.getId());
			n.setAttribute("ui.class", type);
			n.setAttribute("x", Math.random() * 20 - 10);
			n.setAttribute("y", Math.random() * 20 - 10);
		}

		for (int i = 0; i < graph.getEdgeCount(); i++) {
			Edge e = graph.getEdge(i);
			e.setAttribute("ui.class", e.getClass().getSimpleName());
			e.setAttribute("ui.label",
					Long.toString((long) Math.random() * Long.MAX_VALUE));
		}

		String styleSheet = "node.Sensor { fill-color: blue; shape: circle; size-mode: dyn-size; size: 0.25gu; stroke-mode: plain; stroke-color: black; }\n";
		styleSheet += "node.Actuator { fill-color: red;  shape: circle; size-mode: dyn-size; size: 0.25gu; stroke-mode: plain; stroke-color: black; }\n";
		styleSheet += "node.Function { fill-color: yellow;  shape: box; size-mode: dyn-size; size: 0.25gu; stroke-mode: plain; stroke-color: black; }\n";
		styleSheet += "node.Message { fill-color: green;  shape: diamond; size-mode: dyn-size; size: 0.25gu; stroke-mode: plain; stroke-color: black; }";
		styleSheet += "edge { arrow-shape: arrow; }";

		graph.setAttribute("ui.stylesheet", styleSheet);
		graph.setAttribute("ui.quality");
		graph.setAttribute("ui.antialias");

		Viewer graphstreamViewer = new SwingViewer(graph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		//graphstreamViewer.setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);

		graphstreamViewer.enableAutoLayout();

		View graphstreamView = graphstreamViewer.addDefaultView(false);
		
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