package org.emoflon.ibex.gt.ui;

//AWT imports
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

//Graphstream imports
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

//Util imports
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//SWING imports
import javax.swing.JRootPane;

//emf imports
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
//SWT imports
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
//IBeX imports
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.Link;
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.StateModelFactory;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.gt.state.ModelStateManager;

/**
* 
* @author Thomas
*
*/
public class GraphVisualizer {
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
	 * @param edgeTypes 
	 */
	public GraphVisualizer(Resource resource, ModelStateManager stateManager, GraphTransformationInterpreter graphTransformationInterpreter) {
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
	 *  Generates the UI and embeds the graph from graphstream
	 */
	private void generateUI() {
		//Display
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
		resetSelection.setText("Reset Selection");
		    
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
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		// Free resources after closing
		display.dispose();
		}

	/**
	 * Initializes listeners for gui components like buttons, slider and graph
	 */
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
				// Check if something is selected and it´s not just a click on the list
				if(matchList.getSelection().length != 0) {
					// At first selection just highlight the match
					// else: check if new match is selected or deselect the old one
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
					// Remember old selection
					oldSelection = matchList.getSelectionIndex();
				}
			}
	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
	}

	/**
	 * Crates the panel to embed the graph from graphstream into swt
	 * @return panel with the graph from graphstream
	 */
	private Panel generateGraphPanel() {
		Viewer graphstreamViewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
	
		graphstreamViewer.enableAutoLayout();
		View graphstreamView = graphstreamViewer.addDefaultView(false);
		
		// Initialize graph listeners to enable zoom and clicks on nodes
		addGraphListeners(graphstreamView);
		
		@SuppressWarnings("serial")
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

	/**
	 * Initializes graph listeners
	 * @param graphstreamView
	 */
	private void addGraphListeners(View graphstreamView) {
		// Enables zoom function
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
		
		// mouse listener enables to click on nodes 
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
						// If there is only one sub-node its not saved as list 
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
	 * Changes to the next state and calls updates for the gui
	 */
	private void UpdateGraphForwards() {
		// Checks if next state exists
		if(!localStateManager.getCurrentState().getChildren().isEmpty()) {
			// Reset info label
			nodeInfoLabel.setText("");
			// Reset coloring and highlighting
			resetApplyVis();
			resetHighlightVis();
			// Move to next state and change slider value
			localStateManager.moveToState(localStateManager.getCurrentState().getChildren().get(0), false);
			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
			slider.setSelection(localStateManager.modelStates.getStates().indexOf(currentRuleState));
			
			// Create new nodes
			for(EObject newNode : currentRuleState.getStructuralDelta().getCreatedObjects()) {
				createGraphNode(newNode);
			}
			// Change attributes
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
			// Create new links	
			for(Link newLink : currentRuleState.getStructuralDelta().getCreatedLinks()) {
				createGraphEdge(newLink);
			}
			// Delete links
			for(Link deleteLink : currentRuleState.getStructuralDelta().getDeletedLinks()) {
				deleteGraphEdge(deleteLink);
			}
			// Delete nodes
			for(EObject deleteNode : currentRuleState.getStructuralDelta().getDeletedObjects()) {
				deleteGraphNode(deleteNode);
			}
				
			// Set rule and match label
			setMatchRuleInfo();
			
			// If coloring was activated apply it on new state
			if(showCurrent) 
				showCurrentObjects();
			else if(showFuture) 
				showFutureObjects();
		}
			
	}

	/**
	 * Changes to the previous state and calls updates for the gui
	 */
	private void UpdateGraphBackwards() {
		// Check if current state is initial
		if(!localStateManager.getCurrentState().isInitial()) {
			// Reset info label
			nodeInfoLabel.setText("");
			// Reset coloring and highlighting
			resetApplyVis();
			resetHighlightVis();
			// Delete objects created for visualization of future rule apply
			revertFutureObjects();	
			
			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
			// Adjust slider value
			slider.setSelection(localStateManager.modelStates.getStates().indexOf(currentRuleState)-1);
			// Delete links created in current state
			for(Link newLink : currentRuleState.getStructuralDelta().getCreatedLinks()) {
				deleteGraphEdge(newLink);
			}
			// Delete nodes created in current state
			for(EObject newNode : currentRuleState.getStructuralDelta().getCreatedObjects()) {
				deleteGraphNode(newNode);
			}
			// Revert attribute changes
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
			// Recreate deleted nodes
			for(EObject deleteNode : currentRuleState.getStructuralDelta().getDeletedObjects()) {
				createGraphNode(deleteNode);
			}
			// Recreate deleted links
			for(Link deleteLink : currentRuleState.getStructuralDelta().getDeletedLinks()) {
				createGraphEdge(deleteLink);
			}	
			// Change to previous state
			localStateManager.revertToPrevious();
			
			// Set rule and match label
			setMatchRuleInfo();
			
			// If coloring was activated apply it on new state
			if(showCurrent) 
				showCurrentObjects();
			else if(showFuture) 
				showFutureObjects();
		}
	}

	/**
	 * Sets the graph to its initial state
	 */
	private void setInitial() {
		// Reset coloring and highlighting
		resetApplyVis();
		resetHighlightVis();
		
		// Clear and re-initialize graph
		graph.clear();
		graph.setAttribute("ui.stylesheet", styleSheet);
		
		// Clear variables
		nodeMap.clear();
		edgeMap.clear();
		nodeID = 0;
		edgeID = 0;
			
		// Move to initial state and create initial nodes and edges
		localStateManager.moveToState(localStateManager.modelStates.getInitialState(),false);
		createNodesFromList(initialResourceContents);
		
		// Clean info label
		nodeInfoLabel.setText("");
	
		// Reset radio button selection
		showNoApply.setSelection(true);
		showFutureApply.setSelection(false);
		showCurrentApply.setSelection(false);
		showCurrent = false;
		showFuture = false;
		
		// Reset slider
		slider.setSelection(0);
		
		// Reset rule and match labels
		setMatchRuleInfo();
			
	}

	/**
	 * Sets the rule and match labels
	 */
	private void setMatchRuleInfo() {
		// Clear listes matches 
		matchList.removeAll();
		listedMatches.clear();
		
		//Check if in initial state
		if(!localStateManager.getCurrentState().isInitial()) {
			RuleState currentRuleState = (RuleState)localStateManager.getCurrentState();
			currentApplyLabel.setText("Current Apply: \n" + currentRuleState.getRule().getName());		
		} else {
			currentApplyLabel.setText("Current Apply: \nNo apply in initial state!");
		}
		
		// Check if there is a next state
		if(!localStateManager.getCurrentState().getChildren().isEmpty()) {
			RuleState nextRuleState = (RuleState)localStateManager.getCurrentState().getChildren().get(0);
			// Use pattern matcher to find matches and list them in gui
			@SuppressWarnings("unchecked")
			List<IMatch> matchStream = localGraphTransformationInterpreter.matchStream(nextRuleState.getRule().getName(), (Map<String, Object>) nextRuleState.getParameter() , true).collect(Collectors.toList());
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

	/**
	 * Prints the attributes of node with given ID in the gui 
	 * @param nodeID ID of the node which attributes should be shown
	 */
	private void showAttributes(String nodeID) {
		EObject node = null;
		boolean infoNode = true;
		// Check if normal node or info node
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
		
		// If node exists show print out attributes
		if(node!=null) {
			String info = node.eClass().getInstanceClassName();
			for(EAttribute attribute : node.eClass().getEAllAttributes()) {
				info = info + "\n" + attribute.getName() + ": " + node.eGet(attribute);
				// If node is info node also print out attribute changes
				// No need to check if next state is available because if info node exist this is already true
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
	 * Highlights parameter of given match with orange shadow
	 * @param selectedMatch Match which should be highlighted
	 */
	private void highlightMatch(IMatch selectedMatch) {
		// TODO: Differenzierung zwischen Edge und Node
		for(Object matchNode : selectedMatch.getObjects()) {
			nodeMap.get((EObject)matchNode).setAttribute("ui.style", "shadow-mode: gradient-radial; shadow-width: 10px; shadow-color: orange;");
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
		
		//for(Node n : graph.nodes().toList()) {
		for(Node n : graph.nodes().collect(Collectors.toList())) {
			n.setAttribute("ui.style", "fill-color: grey; text-background-color: grey;");
			String label = (String) n.getAttribute("ui.label");
			if(label.contains("++")) {
				n.setAttribute("ui.label", label.subSequence(0, label.length()-2));
			}
		}
		
		for(Edge e : graph.edges().collect(Collectors.toList())) {
			e.setAttribute("ui.style", "fill-color: black;");
			String label = (String) e.getAttribute("ui.label");
			if(label.contains("++")) {
				e.setAttribute("ui.label", label.subSequence(0, label.length()-2));
			}
		}
	}
	
	/**
	 * Reverts highlighting of all graph objects (disables shadow)
	 */
	private void resetHighlightVis() {
		
		for(Node n : graph.nodes().collect(Collectors.toList())) {
			n.setAttribute("ui.style", "shadow-mode: none;");
		}
		
		for(Edge e : graph.edges().collect(Collectors.toList())) {
			e.setAttribute("ui.style", "shadow-mode: none;");
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
	 * Adds node to the graph and map graph node to EObject node
	 * @param node Node to add
	 */
	private void createGraphNode(EObject node) {
		if(!nodeMap.keySet().contains(node)) {
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

	/**
	 * Deletes graph node and its mapping
	 * @param node Node to delete
	 */
	private void deleteGraphNode(EObject node) {
		graph.removeNode(nodeMap.remove(node));
	}

	/**
	 * Creates new graph edge and its mapping
	 * @param newLink Edge to create
	 * @return created edge
	 */
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

	/**
	 * Deletes graph edge and its mapping
	 * @param deletedLink edge to delete
	 */
	private void deleteGraphEdge(Link deletedLink) {
		graph.removeEdge(edgeMap.remove(deletedLink));
	}
}

