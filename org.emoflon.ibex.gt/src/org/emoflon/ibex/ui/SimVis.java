package org.emoflon.ibex.ui;

// AWT imports
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

// Util imports
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// SWING imports
import javax.swing.JRootPane;

// emf imports
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;

// SWT imports
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;

// IBeX imports
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.Link;
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.StateModelFactory;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.gt.state.ModelStateManager;

// Graphstream imports
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

/**
 * 
 * @author Thomas
 *
 */
public class SimVis {
	// IBeX variables
	private StateModelFactory factory = StateModelFactory.eINSTANCE;
	private ModelStateManager localStateManager;
	private GraphTransformationInterpreter localGraphTransformationInterpreter;
	
	// Intern variables
	private int nodeID = 0;
	private int edgeID = 0;
	private int oldSelection = -1;
	
	private boolean showFuture = false;
	private boolean showCurrent = false;
	
	private Map<EObject, Node> nodeMap;
	private Map<Link, Edge> edgeMap;
	private EList<EObject> initialResourceContents;
	private Map<Integer,IMatch> listedMatches;
	private Map<EObject, Node> infoNodes;
	
	// GUI
	private Button stepForward;
	private Button stepBackward;
	private Button setInitial;
	private Button resetSelection;
	
	private Button showCurrentApply;
	private Button showFutureApply;
	private Button showNoApply;
	
	private Label currentApplyLabel;
	private Label futureApplyLabel;
	private Label nodeInfoLabel;
	
	private Scale slider;
	
	private org.eclipse.swt.widgets.List matchList;
	
	// Graphstream variables
	private SingleGraph graph;
    protected static String styleSheet =
		"edge {"
		+ "size: 2px;"
		+ "fill-color: black;"
		+ "fill-mode: dyn-plain;"
		+ "text-alignment: under;"
		+ "text-background-mode: plain;"
		+ "}"
		
		+ "node {"
		+ "size-mode: fit;"
		+ "shape: box;"
		+ "fill-color: grey, red, blue, green;"
		+ "stroke-mode: plain;"
		+ "stroke-color: black;"
		+ "stroke-width: 1.5px;"
		+ "text-mode: normal;"
		+ "text-background-color: grey;"
		+ "padding: 3px, 3px;"
		+ "text-background-mode: plain;"
		+ "fill-mode: dyn-plain;"
		+ "}";
	
	/**
	 * Constructor, initializes variables and creates initial graph and gui
	 * @param resource	Model resources with initial nodes
	 * @param stateManager Statemanager with all states and helper functions
	 * @param graphTransformationInterpreter Interpreter used to find matches
	 */
	public SimVis(Resource resource, ModelStateManager stateManager, GraphTransformationInterpreter graphTransformationInterpreter) {
		initialResourceContents = new BasicEList<EObject>(resource.getContents());
		localStateManager = stateManager;
		localGraphTransformationInterpreter = graphTransformationInterpreter;
		localStateManager.moveToState(localStateManager.modelStates.getInitialState(), false);
		
		nodeMap = new HashMap<EObject, Node>();
		edgeMap = new HashMap<Link, Edge>();
		infoNodes = new HashMap<EObject, Node>();
		listedMatches = new HashMap<Integer, IMatch>();
		graph = new SingleGraph("");
		graph.setStrict(false);
		graph.setAutoCreate(false);
		graph.setAttribute("ui.stylesheet", styleSheet);	

		// Create initial graph with resources
		createNodesFromList(resource.getContents());
		
		// Generate gui
		generateUI();
		
	}
	
	/**
	 * Creates the nodes of the graph. Nodes parenting other nodes indicate edges between these objects
	 * recursive call to start at the bottom of the parenting hierarchy
	 * @param list Contains all nodes to create
	 * @return created and existing nodes as list
	 */
	@SuppressWarnings("unchecked")
	private List<EObject> createNodesFromList(EList<EObject> list) {
		List<EObject> createdOrOldNodes = new ArrayList<EObject>();
		for(EObject node : list) {
			createdOrOldNodes.add(node);
			if(!nodeMap.keySet().contains(node)) {
				// If node does not exist in graph create it
				createGraphNode(node);
				// If node has children add all of them to a list and call createNodesFromList recursive
				if(!node.eClass().getEReferences().isEmpty()) {
					for(EReference ref : node.eClass().getEReferences()) {
						EList<EObject> newList = new BasicEList<EObject>();
						if(!(node.eGet(ref) instanceof EList)) {
							if(node.eGet(ref) != null)
								newList.add((EObject) node.eGet(ref));
						} else {
							newList = (EList<EObject>)(node.eGet(ref));
						}
						// Create links between children and their parents
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
	
	/**
	 * Creates an info node and connects it to the respective node
	 * @param targetNode Node to create an info node for
	 */
	private void createInfoNode(EObject targetNode) {
		// Check if target node already has an info node
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
	
	/**
	 * Changes the state depending of the slider value
	 * @param stateValue value of the slider indicating the new state
	 */
	private void stateChangeBySlider(int stateValue) {
		int indexOfCurrentState = localStateManager.modelStates.getStates().indexOf(localStateManager.getCurrentState());
		while(stateValue != indexOfCurrentState) {
			if(stateValue > indexOfCurrentState) {
				UpdateGraphForwards();
			} else {
				UpdateGraphBackwards();
			}
			indexOfCurrentState = localStateManager.modelStates.getStates().indexOf(localStateManager.getCurrentState());
		}
	}
	
	/**
	 * Colors and labels all nodes and edges effected by the apply of the current rule
	 * Structural changes are indicates by coloring the whole node / edge
	 * Changes of attributes are indicated by info nodes
	 * Green / ++: creation or change
	 * Red / --: Deletions in current state are not displayed because the objects do no exist anymore
	 */
	private void showCurrentObjects() {
		if(localStateManager.getCurrentState().isInitial()) {
			resetApplyVis();
		} else {
			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
			// Color new nodes green and add ++ to label
			for(EObject newNode : currentRuleState.getStructuralDelta().getCreatedObjects()) {
				nodeMap.get(newNode).setAttribute("ui.style", "fill-color: rgba(0,150,0,255); text-background-color: rgba(0,150,0,255);");
				nodeMap.get(newNode).setAttribute("ui.label", nodeMap.get(newNode).getAttribute("ui.label") + " ++");
			}
			// create info nodes for nodes whose attributes have changed
			for(AttributeDelta attributeDelta : currentRuleState.getAttributeDeltas()) {
				createInfoNode(attributeDelta.getObject());
			}
			// Color new edges green and add ++ to label
			for(Link newLink : currentRuleState.getStructuralDelta().getCreatedLinks()) {
				if(edgeMap.containsKey(newLink)) {
					edgeMap.get(newLink).setAttribute("ui.style", "fill-color: rgba(0,150,0,255);");
					edgeMap.get(newLink).setAttribute("ui.label", newLink.getType().getName() + " ++");
				}
			}
		}
	}
	
	/**
	 * Colors and labels all nodes and edges effected by the apply of the next rule
	 * Structural changes are indicates by coloring the whole node / egde
	 * Changes of attributes are indicated by info nodes
	 * Green / ++: creation or change
	 * Red / --: deletion
	 */
	private void showFutureObjects() {
		if(!localStateManager.getCurrentState().getChildren().isEmpty()) {
			RuleState nextRuleState = (RuleState)localStateManager.getCurrentState().getChildren().get(0);
			// Create and color new nodes green and add ++ to label
			for(EObject newNode : nextRuleState.getStructuralDelta().getCreatedObjects()) {
				createGraphNode(newNode);
				nodeMap.get(newNode).setAttribute("ui.style", "fill-color: rgba(0,150,0,255); text-background-color: rgba(0,150,0,255);");
				nodeMap.get(newNode).setAttribute("ui.label", nodeMap.get(newNode).getAttribute("ui.label") + " ++");
			}
			// Color nodes which will be deleted red and add -- to label
			for(EObject deletedNode : nextRuleState.getStructuralDelta().getDeletedObjects()) {
				nodeMap.get(deletedNode).setAttribute("ui.style", "fill-color: rgba(150,0,0,255); text-background-color: rgba(255,0,0,128);");
				nodeMap.get(deletedNode).setAttribute("ui.label", nodeMap.get(deletedNode).getAttribute("ui.label") + " --");
			}
			// Create and color new edges green and add ++ to label
			for(Link createdLink : nextRuleState.getStructuralDelta().getCreatedLinks()) {
				Edge edge = createGraphEdge(createdLink);
				if(edge!=null) {
					edge.setAttribute("ui.style", "fill-color: rgba(0,150,0,255);");
					edge.setAttribute("ui.label", createdLink.getType().getName() + " ++");
				}
			}
			
			// Color edges which will be deleted red and add -- to label
			for(Link deleteLink : nextRuleState.getStructuralDelta().getDeletedLinks()) {
				edgeMap.get(deleteLink).setAttribute("ui.style", "fill-color: rgba(150,0,0,255);");
				edgeMap.get(deleteLink).setAttribute("ui.label", deleteLink.getType().getName() + " --");
			}
			// create info nodes for nodes whose attributes have changed
			for(AttributeDelta attributeDelta : nextRuleState.getAttributeDeltas()) {
				createInfoNode(attributeDelta.getObject());
			}
		}
	}
	
	/**
	 * Deletes all objects created to show the apply of the next rule and reverts the coloring
	 */
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
	
	/**
	 * Reverts coloring of all graph objects 
	 * Does not revert highlighting of matches (shadow of nodes)
	 */
	private void resetApplyVis() {
		for(Node node : infoNodes.values()) {
			graph.removeNode(node);
		}
		infoNodes.clear();
		
		for(Node n : graph.nodes().toList()) {
			n.setAttribute("ui.style", "fill-color: grey; text-background-color: grey;");
			String label = (String) n.getAttribute("ui.label");
			if(label.contains("++")) {
				n.setAttribute("ui.label", label.subSequence(0, label.length()-2));
			}
		}
		
		for(Edge e : graph.edges().toList()) {
			e.setAttribute("ui.style", "fill-color: black;");
			String label = (String) e.getAttribute("ui.label");
			if(label.contains("++")) {
				e.setAttribute("ui.label", label.subSequence(0, label.length()-2));
			}
		}
	}
	
	/**
	 * Reverts highlighting of all graph objects 
	 */
	private void resetHighlightVis() {
		
		for(Node n : graph.nodes().toList()) {
			n.setAttribute("ui.style", "shadow-mode: none;");
		}
		
		for(Edge e : graph.edges().toList()) {
			e.setAttribute("ui.style", "shadow-mode: none;");
		}
	}
	
	/**
	 * 
	 */
	private void UpdateGraphForwards() {
		if(!localStateManager.getCurrentState().getChildren().isEmpty()) {
			nodeInfoLabel.setText("");
			resetApplyVis();
			resetHighlightVis();
			localStateManager.moveToState(localStateManager.getCurrentState().getChildren().get(0), false);
			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
			slider.setSelection(localStateManager.modelStates.getStates().indexOf(currentRuleState));
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
			nodeInfoLabel.setText("");
			resetApplyVis();
			resetHighlightVis();
			revertFutureObjects();	
			
			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
			slider.setSelection(localStateManager.modelStates.getStates().indexOf(currentRuleState)-1);
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
	
	private void setMatchRuleInfo() {
		matchList.removeAll();
		listedMatches.clear();
		if(!localStateManager.getCurrentState().isInitial()) {
			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
			currentApplyLabel.setText("Current Apply: \n" + currentRuleState.getRule().getName());		
		} else {
			currentApplyLabel.setText("Current Apply: \nNo apply in initial state!");
		}
		
		if(!localStateManager.getCurrentState().getChildren().isEmpty()) {
				
			RuleState nextRuleState = (RuleState)localStateManager.getCurrentState().getChildren().get(0);
			@SuppressWarnings("unchecked")
			List<IMatch> matchStream = localGraphTransformationInterpreter.matchStream(nextRuleState.getRule().getName(), (Map<String, Object>) nextRuleState.getParameter() , true).toList();
			int index = 0;
			for(IMatch match : matchStream) {
				if(match.equals(nextRuleState.getMatch())) {
					matchList.add(match.getPatternName() + " (Next)");
				} else {
					matchList.add(match.getPatternName());
				}
				
				listedMatches.put(index, match);
				index++;
			}
			futureApplyLabel.setText("Next Apply: \n" + nextRuleState.getRule().getName() + "\nMatches: " + matchStream.size());
		} else {
			futureApplyLabel.setText("Next Apply: \nNo further state available!");
		}
		
	}
	
	private void setInitial() {
			resetApplyVis();
			resetHighlightVis();
			graph.clear();
			graph.setAttribute("ui.stylesheet", styleSheet);
			
			nodeMap.clear();
			edgeMap.clear();
			nodeID = 0;
			edgeID = 0;
			
			localStateManager.moveToState(localStateManager.modelStates.getInitialState(),false);
			createNodesFromList(initialResourceContents);
			
			nodeInfoLabel.setText("");

			showNoApply.setSelection(true);
			showFutureApply.setSelection(false);
			showCurrentApply.setSelection(false);
			showCurrent = false;
			showFuture = false;
			
			slider.setSelection(0);
			setMatchRuleInfo();
			
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
			nodeInfoLabel.setText(info);
		}
		
	}

	
	

	
	private Panel generateGraphPanel() {
		Viewer graphstreamViewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);

		graphstreamViewer.enableAutoLayout();
		View graphstreamView = graphstreamViewer.addDefaultView(false);
		
		addGraphListeners(graphstreamView);

		
		Panel panel = new Panel(new BorderLayout()) {
			public void update(java.awt.Graphics g) {
				/* Do not erase the background */ 
				paint(g);
			}
		};
		JRootPane root = new JRootPane();
		panel.add(root);
		root.getContentPane().add((Component) graphstreamView);
		return panel;
		
	}
	
	
	
	private void generateUI() {
		Display display = new Display();
	    Shell shell = new Shell(display);
	    GridLayout gridLayout = new GridLayout();
	    gridLayout.numColumns = 2;
	    shell.setLayout(gridLayout);
	    shell.setSize(1550, 800);
	     
	    // Graphstream
	    Composite composite = new Composite(shell, SWT.NO_BACKGROUND | SWT.EMBEDDED);
	    GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
	    gd.widthHint = 1180;
	    gd.heightHint = 720;
	    composite.setLayoutData(gd);
	 	Frame frame = SWT_AWT.new_Frame(composite);
	 	frame.add(generateGraphPanel());
	 	frame.setVisible(true);
	    
	 	// Matches and Rules
	 	Composite textsAndList = new Composite(shell, SWT.BORDER);
	 	gd = new GridData(SWT.FILL, SWT.FILL, true, true);
	 	gd.widthHint = 300;
		gd.heightHint = 720;
	 	textsAndList.setLayoutData(gd);
	 	RowData rd = new RowData();
	 	rd.width = 300;
	 	rd.height = 50;
	 	textsAndList.setLayout(new RowLayout(SWT.VERTICAL));
//	 	
	 	currentApplyLabel = new Label(textsAndList, SWT.BORDER);
	 	currentApplyLabel.setLayoutData(rd);
	 	futureApplyLabel = new Label(textsAndList, SWT.BORDER);
	 	futureApplyLabel.setLayoutData(rd);
	 	
	 	
	 	matchList = new org.eclipse.swt.widgets.List (textsAndList, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
	 	rd = new RowData();
	 	rd.width = 275;
	 	rd.height = 350;
	 	matchList.setLayoutData(rd);
	 	
	 	// Slider and Buttons
	 	Composite sliderAndButtons = new Composite(shell, SWT.BORDER);
	 	gd = new GridData(SWT.FILL, SWT.FILL, true, true);
	 	gd.widthHint = 1180;
		gd.heightHint = 300;
	 	sliderAndButtons.setLayoutData(gd);
	 	sliderAndButtons.setLayout(new RowLayout(SWT.VERTICAL));
	 	
	 	slider = new Scale(sliderAndButtons, SWT.HORIZONTAL);
	 	slider.setMaximum(localStateManager.modelStates.getStates().size()-1);
		slider.setMinimum(0);
		slider.setIncrement(1);
	 	slider.setSelection(0);
	 	slider.setPageIncrement(1);
	 	rd = new RowData();
	 	rd.width = 1180;
	 	rd.height = 50;
	 	slider.setLayoutData(rd);
		
//	    RowLayout buttonRowLayout = new RowLayout();
//	    buttonRowLayout.wrap = false;
//	    buttonRowLayout.pack = false;
//	    buttonRowLayout.marginLeft = 5;
//	    buttonRowLayout.marginTop = 5;
//	    buttonRowLayout.marginRight = 5;
//	    buttonRowLayout.marginBottom = 5;
//	    buttonRowLayout.spacing = 0;

	    Composite buttons = new Composite(sliderAndButtons, SWT.NONE);
	    rd = new RowData();
	 	rd.width = 1180;
	 	rd.height = 40;
	 	buttons.setLayoutData(rd);
	 	buttons.setLayout(new RowLayout(SWT.HORIZONTAL));	 	
	 	
		stepForward = new Button(buttons, SWT.PUSH);
		stepForward.setText("StepForward");
		
		stepBackward = new Button(buttons, SWT.PUSH);
		stepBackward.setText("StepBackward");
		
	    setInitial = new Button(buttons, SWT.PUSH);
		setInitial.setText("Set Initial");
		
		resetSelection = new Button(buttons, SWT.PUSH);
		resetSelection.setText("Toggle Root Node");
	    
		Group radioButtons = new Group(sliderAndButtons, SWT.NONE);
		rd = new RowData();
	 	rd.width = 1180;
	 	rd.height = 40;
	 	radioButtons.setLayoutData(rd);
		radioButtons.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		showNoApply = new Button(radioButtons, SWT.RADIO);
		showNoApply.setSelection(true);
		showNoApply.setText("Show No Apply");
		
		showCurrentApply = new Button(radioButtons, SWT.RADIO);
		showCurrentApply.setText("Show Current Apply");
		
		showFutureApply = new Button(radioButtons, SWT.RADIO);
		showFutureApply.setText("Show Future");
	
		// Node Information
		Composite nodeInfoComp = new Composite(shell, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.widthHint = 300;
		gd.heightHint = 300;
		nodeInfoComp.setLayoutData(gd);
		nodeInfoComp.setLayout(new RowLayout(SWT.VERTICAL));
	    nodeInfoLabel = new Label(nodeInfoComp, SWT.BORDER);
	    rd = new RowData();
	 	rd.width = 300;
	 	rd.height = 300;
	 	nodeInfoLabel.setLayoutData(rd);
	    
		// Initialize listeners
		setListeners();
		
		// Initialize labels
		setMatchRuleInfo();
		
		// Open shell and keep open while program is running
		// After closing, free resources
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	private void setListeners() {
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
		
		stepBackward.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
					UpdateGraphBackwards();	
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
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
		
		resetSelection.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// Auto-generated method stub
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
		showNoApply.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(showNoApply.getSelection()) {
					if(showCurrent) {
						resetApplyVis();
						showCurrent = false;
					}
					if(showFuture) {
						revertFutureObjects();
						showFuture = false;
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
		showCurrentApply.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(showCurrentApply.getSelection()) {
					if(!showCurrent) {
						if(showFuture) {
							revertFutureObjects();
							showFuture = false;
						}
						showCurrent = true;
						showCurrentObjects();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
		showFutureApply.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(showFutureApply.getSelection()) {
					if(!showFuture) {
						if(showCurrent) {
							resetApplyVis();
							showCurrent = false;
						}
						showFuture = true;
						showFutureObjects();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
		slider.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				stateChangeBySlider(slider.getSelection());
				
			}
			
		});
		
		matchList.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(matchList.getSelection().length != 0) {
					if(oldSelection==-1) {
						highlightMatch(listedMatches.get(matchList.getSelectionIndex()));
					} else {
						resetHighlightVis();
						if(!(matchList.getSelectionIndex() == oldSelection)) {
							highlightMatch(listedMatches.get(matchList.getSelectionIndex()));
						} else {
							matchList.deselectAll();
						}
					}
					oldSelection = matchList.getSelectionIndex();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
	}
	
	private void highlightMatch(IMatch selectedMatch) {
		// TODO: Differenzierung zwischen Edge und Node
		for(Object matchNode : selectedMatch.getObjects()) {
			nodeMap.get((EObject)matchNode).setAttribute("ui.style", "shadow-mode: gradient-radial; shadow-width: 10px; shadow-color: orange;");
		}
	}

	private void addGraphListeners(View graphstreamView) {
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
		        	Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							showAttributes(element.getId());	
						}
	        		});
		        }
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Auto-generated method stub
				
			}
		});
	}
}



