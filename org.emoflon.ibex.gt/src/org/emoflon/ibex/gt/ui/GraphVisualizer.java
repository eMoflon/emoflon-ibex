package org.emoflon.ibex.gt.ui;

//AWT imports
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
import org.eclipse.swt.widgets.Slider;
//IBeX imports
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.Link;
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StateModelFactory;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.gt.state.ModelStateManager;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdgeSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet;

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
	private boolean freeze = false;
	
	private Map<EObject, Node> nodeMap;
	private Map<Link, Edge> edgeMap;
	private EList<IBeXRule> ruleSet;
	private EList<EObject> initialResourceContents;
	private Map<Integer,IMatch> listedMatches;
	private Map<EObject, Node> infoNodes;
	private Node matchHiglightNode;
	private int matchCount;
	
	// GUI
	private Button stepForward;
	private Button stepBackward;
	private Button setInitial;
	private Button toggleFreeze;
	
	private Button jumpRule;
	private Button jumpMatchChange;
	
	private Button showCurrentApply;
	private Button showFutureApply;
	private Button showNoApply;
	
	private Button help;
	
	private Label currentApplyLabel;
	private Label futureApplyLabel;
	private Label nodeInfoLabel;
	private Label jumpRuleInfo;
	private Label jumpRuleHead;
	private Label jumpMatchChangeInfo;
	private Label info;
	private String selectedRuleInList;
	private int oldSelectionRule = -1;
	
	private Slider slider;
	
	private org.eclipse.swt.widgets.List matchList;
	private org.eclipse.swt.widgets.List ruleList;
	
	// Graphstream variables
	private SingleGraph graph;
	private MouseEvent last;	//Used for camera movement -> not implemented
	private Viewer graphstreamViewer;

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
	 * @param iBeXRuleSet 
	 * @param edgeTypes 
	 */
	public GraphVisualizer(Resource resource, ModelStateManager stateManager, GraphTransformationInterpreter graphTransformationInterpreter, IBeXRuleSet iBeXRuleSet) {
		initialResourceContents = new BasicEList<EObject>(resource.getContents());
		localStateManager = stateManager;
		localGraphTransformationInterpreter = graphTransformationInterpreter;
		localStateManager.moveToState(localStateManager.modelStates.getInitialState(), false);
		ruleSet = iBeXRuleSet.getRules();
		
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
		gridLayout.numColumns = 3;
		shell.setLayout(gridLayout);
		shell.setSize(1600, 850);
		
		GridData gd;
		
		// Rules
		Composite rules = new Composite(shell, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.widthHint = 250;
		gd.heightHint = 600;
		rules.setLayoutData(gd);
		
		rules.setLayout(new GridLayout(1, true));
		
		jumpRuleHead = new Label(rules, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 50;
		jumpRuleHead.setLayoutData(gd);
		jumpRuleHead.setText("Rules:");
 		
		ruleList = new org.eclipse.swt.widgets.List (rules, SWT.FILL | SWT.SINGLE | SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 450;
		ruleList.setLayoutData(gd);
		//Add rules to gui list
		for(IBeXRule rule : ruleSet) {
			ruleList.add(rule.getName());
		}
		jumpRule = new Button(rules, SWT.PUSH);
		jumpRule.setText("Jump To Selected Rule");
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 50;
		jumpRule.setLayoutData(gd);
		
		jumpRuleInfo = new Label(rules, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 50;
		jumpRuleInfo.setLayoutData(gd);
	
		// Graphstream
		Composite composite = new Composite(shell, SWT.NO_BACKGROUND | SWT.EMBEDDED);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.widthHint = 1080;
		gd.heightHint = 600;
		composite.setLayoutData(gd);
		Frame frame = SWT_AWT.new_Frame(composite);
		frame.add(generateGraphPanel());
		frame.setVisible(true);
		    
		// Matches and apply
		Composite textsAndList = new Composite(shell, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.widthHint = 250;
		gd.heightHint = 600;
		textsAndList.setLayoutData(gd);
		
		textsAndList.setLayout(new GridLayout(1, true));

		currentApplyLabel = new Label(textsAndList, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 50;
		currentApplyLabel.setLayoutData(gd);
		futureApplyLabel = new Label(textsAndList, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 60;
		futureApplyLabel.setLayoutData(gd);
		 	
		matchList = new org.eclipse.swt.widgets.List (textsAndList, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 375;
		matchList.setLayoutData(gd);
		
		jumpMatchChange = new Button(textsAndList, SWT.PUSH);
		jumpMatchChange.setText("Jump To Match Count Change");
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 50;
		jumpMatchChange.setLayoutData(gd);
		
		jumpMatchChangeInfo = new Label(textsAndList, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 50;
		jumpMatchChangeInfo.setLayoutData(gd);
		
		// Information
		Composite infoComp = new Composite(shell, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.widthHint = 250;
		gd.heightHint = 250;
		infoComp.setLayoutData(gd);
		
		info = new Label(infoComp, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 250;
		info.setLayoutData(gd);

		
		// Slider and Buttons
		Composite sliderAndButtons = new Composite(shell, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.widthHint = 1080;
		gd.heightHint = 250;
		sliderAndButtons.setLayoutData(gd);
		sliderAndButtons.setLayout(new GridLayout(1, true));
		 	
		slider = new Slider(sliderAndButtons, SWT.HORIZONTAL);
		slider.setValues(0, 0, localStateManager.modelStates.getStates().size()-1, 1, 1, 1);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 20;
		slider.setLayoutData(gd);
	
		Composite buttons = new Composite(sliderAndButtons, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 20;
		buttons.setLayoutData(gd);
		buttons.setLayout(new RowLayout(SWT.HORIZONTAL));	 	
		 	
		stepForward = new Button(buttons, SWT.PUSH);
		stepForward.setText("StepForward");
			
		stepBackward = new Button(buttons, SWT.PUSH);
		stepBackward.setText("StepBackward");
			
		setInitial = new Button(buttons, SWT.PUSH);
		setInitial.setText("Set Initial");
			
		toggleFreeze = new Button(buttons, SWT.CHECK);
		toggleFreeze.setText("Freeze Graph");
		    
		Group radioButtons = new Group(sliderAndButtons, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 30;
		radioButtons.setLayoutData(gd);
		radioButtons.setLayout(new RowLayout(SWT.HORIZONTAL));
			
		showNoApply = new Button(radioButtons, SWT.RADIO);
		showNoApply.setSelection(true);
		showNoApply.setText("Show No Apply");
			
		showCurrentApply = new Button(radioButtons, SWT.RADIO);
		showCurrentApply.setText("Show Current Apply");
			
		showFutureApply = new Button(radioButtons, SWT.RADIO);
		showFutureApply.setText("Show Future");
		
		// Init help shell
		Shell helpShell = new Shell(shell);
		helpShell.setText("Help");
		helpShell.setSize(400, 200);
	    Label helpLabel = new Label(helpShell,SWT.BORDER);
	    helpLabel.setText(
	    		"Green/++: Creation or change"
	    		+ "\nRed: Deletion"
	    		);
	    
	    GridLayout helpShellLayout = new GridLayout();
	    helpShellLayout.numColumns = 1;
		helpShell.setLayout(helpShellLayout);
	    
		help = new Button(sliderAndButtons, SWT.PUSH);
		help.setText("Help");
		gd = new GridData(SWT.NONE, SWT.FILL, true, true);
		gd.heightHint = 10;
		help.setLayoutData(gd);
		
		// Node Information
		Composite nodeInfoComp = new Composite(shell, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.widthHint = 250;
		gd.heightHint = 250;
		nodeInfoComp.setLayoutData(gd);
		nodeInfoComp.setLayout(new GridLayout(1, true));
		nodeInfoLabel = new Label(nodeInfoComp, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 80;
		nodeInfoLabel.setLayoutData(gd);
		
		// Initialize listeners
		setListeners(helpShell);
		
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
	private void setListeners(Shell helpShell) {
		
		help.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
					helpShell.open();
			}
	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
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
		
		toggleFreeze.addSelectionListener(new SelectionListener() {
	
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(freeze) {
					defreezeGraph();
					freeze = false;
				} else {
					freezeGraph();
					freeze = true;
				}
			}
	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
		jumpRule.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				jumpRuleApply(selectedRuleInList);
			}
	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
		jumpMatchChange.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				jumpMatchCountChanged();
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
				stateChanged(slider.getSelection());
			}
			
		});
		
		matchList.addSelectionListener(new SelectionListener() {
	
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Check if something is selected and it´s not just a click on the list
				if(matchList.getSelectionIndex() != -1) {
					resetHighlightVis();
					if(oldSelection == matchList.getSelectionIndex()) {
						oldSelection = -1;
						matchList.deselectAll();
					} else {
						highlightMatch(listedMatches.get(matchList.getSelectionIndex()));
						// Remember old selection
						oldSelection = matchList.getSelectionIndex();
					}
				}
			}
	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Auto-generated method stub
			}
			
		});
		
		ruleList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Check if something is selected and it´s not just a click on the list
				if(ruleList.getSelectionIndex() != -1) {
					jumpRuleInfo.setText("");
					if(oldSelectionRule == ruleList.getSelectionIndex()) {
						ruleList.deselect(oldSelectionRule);
						selectedRuleInList = null;
						oldSelectionRule = -1;
					} else {
						selectedRuleInList = ruleList.getItem(ruleList.getSelectionIndex());
						// Remember old selection
						oldSelectionRule = ruleList.getSelectionIndex();
					}
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
		graphstreamViewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
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
		
//		 Enabbles to move camera by drag & drop -> not implemented
		
//		((Component) graphstreamView).addMouseMotionListener(new MouseMotionListener() {
//			@Override
//			public void mouseDragged(MouseEvent event) {
//			
//				if(last!=null) {
//					Camera camera = graphstreamView.getCamera();
//					Point3 p1 = camera.getViewCenter();
//					Point3 p2=camera.transformGuToPx(p1.x,p1.y,0);
//					int xdelta=event.getX()-last.getX();	//determine direction
//					int ydelta=event.getY()-last.getY();	//determine direction
//					//sysout("xdelta "+xdelta+" ydelta "+ydelta);
//					p2.x-=xdelta;
//					p2.y-=ydelta;
//					Point3 p3=camera.transformPxToGu(p2.x,p2.y);
//					camera.setViewCenter(p3.x,p3.y, 0);
//				}
//				last=event;
//				
//			}
//
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				// Auto-generated method stub
//				
//			}
//			
//		});
		
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
		
		// Enables to click on nodes 
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
							link.setType(ref);
							createGraphEdge(link);
							
//							TODO Delete notes or save somewhere else
//							node.eClass().getEAllContainments()
//							node.eClass().getEAllStructuralFeatures()
//							node.eGet(ref)
							
							
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
			//Defreeze graph
			defreezeGraph();
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
			//Defreeze graph
			defreezeGraph();
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
		//Defreeze graph
		defreezeGraph();
		
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
			matchCount = matchStream.size();
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
		//Defreeze graph
		defreezeGraph();
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
			//Defreeze graph
			defreezeGraph();
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
				Link toColor = deleteLink;
				for(Link link : edgeMap.keySet()) {
					if(equalEdge(deleteLink, link)) {
						toColor = link;
						break;
					}
				}
				edgeMap.get(toColor).setAttribute("ui.style", "fill-color: rgba(150,0,0,255);");
				edgeMap.get(toColor).setAttribute("ui.label", deleteLink.getType().getName() + " --");
				
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
		matchHiglightNode = graph.addNode(Integer.toString(nodeID));
		matchHiglightNode.setAttribute("ui.style", 
				"fill-color: orange;"
				+ "fill-mode: dyn-plain;"
				+ "size: 2px;"
				+ "shape: box;"
//				+ "shadow-mode: gradient-radial;"
//				+ "shadow-width: 5px;"
//				+ "shadow-color: orange;"
//				+ "stroke-mode: plain;"
//				+ "stroke-width: 2.0px;"
);
		matchHiglightNode.setAttribute("ui.label", "");
		nodeID++;
		
		for(Object matchNode : selectedMatch.getObjects()) {
			try {
				nodeMap.get((EObject)matchNode).setAttribute("ui.style", "shadow-mode: gradient-radial; shadow-width: 10px; shadow-color: orange;");
				Edge matchHighlightEdge = graph.addEdge(Integer.toString(edgeID), matchHiglightNode, nodeMap.get((EObject)matchNode));
				matchHighlightEdge.setAttribute("ui.label", "");
				matchHighlightEdge.setAttribute("ui.style", 
						"fill-color: white;"
						+ "stroke-mode: dashes;"
						+ "stroke-color: black;"
						+ "stroke-width: 2.0px;");
				
				edgeID++;
			} catch (Exception ex) {
				System.out.println("Error when highlighting matches");
			}
			
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
		if(matchHiglightNode != null) {
			graph.removeNode(matchHiglightNode);
			matchHiglightNode = null;
		}
		
		List<Node> test = graph.nodes().collect(Collectors.toList());
		for(Node n : graph.nodes().collect(Collectors.toList())) {
			n.setAttribute("ui.style", "shadow-mode: none;");
		}
		
		for(Edge e : graph.edges().collect(Collectors.toList())) {
			e.setAttribute("ui.style", "shadow-mode: none;");
		}
		
		
	}
	
	/**
	 * Disables auto layout function of the graph and freezes positions of nodes
	 */
	private void freezeGraph() {
		graphstreamViewer.disableAutoLayout();
		for(Node n : graph.nodes().toList()) {
			n.setAttribute("layout.frozen");
		}
	}
	
	/**
	 * Enables auto layout function of the graph and defreezes positions of nodes
	 */
	private void defreezeGraph() {
		graphstreamViewer.enableAutoLayout();
		for(Node n : graph.nodes().toList()) {
			n.removeAttribute("layout.frozen");
		}
		toggleFreeze.setSelection(false);
		freeze = false;
	}
	
	/**
	 * Changes the state to state with given index
	 * @param index index of new state
	 */
	private void stateChanged(int index) {
		int indexOfCurrentState = localStateManager.modelStates.getStates().indexOf(localStateManager.getCurrentState());
		while(index != indexOfCurrentState) {
			if(index > indexOfCurrentState) {
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
		Edge createdEdge = graph.addEdge(Integer.toString(edgeID), nodeMap.get(newLink.getSrc()), nodeMap.get(newLink.getTrg()), true);
		if(createdEdge!=null) {
			
			// Map graph edge to EObject edge
			edgeMap.put(newLink, createdEdge);
			
			// Label edge
			if(newLink.getType()!=null)
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
		Link toDelete = deletedLink;
		for(Link link : edgeMap.keySet()) {
			if(equalEdge(deletedLink, link)) {
				toDelete = link;
				break;
			}
		}
		graph.removeEdge(edgeMap.remove(toDelete));
		
	}
	
	private boolean equalEdge(Link one, Link two) {
		return(one.getSrc().equals(two.getSrc()) && one.getTrg().equals(two.getTrg()) && one.getType().equals(two.getType()));
	}

	/**
	 * Jumps to next state where match count in comparison to current match count changes
	 */
	private void jumpMatchCountChanged() {
		State curr = localStateManager.getCurrentState();
		boolean found = false;
		while(!localStateManager.getCurrentState().getChildren().isEmpty()) {
			localStateManager.moveToState(localStateManager.getCurrentState().getChildren().get(0), false);
			RuleState movedState = (RuleState) localStateManager.getCurrentState();
			@SuppressWarnings("unchecked")
			List<IMatch> matchStream = localGraphTransformationInterpreter.matchStream(movedState.getRule().getName(), (Map<String, Object>) movedState.getParameter() , true).collect(Collectors.toList());
			if(matchCount != matchStream.size()) {
				int index = localStateManager.modelStates.getStates().indexOf(movedState);
				found = true;
				localStateManager.moveToState(curr, false);
				stateChanged(index);
				break;
			}
		}
		if(!found) {
			localStateManager.moveToState(curr, false);
			jumpMatchChangeInfo.setText("No further change in match count!");
		}
	}
	
	/**
	 * Jumps to state where give rule is applied
	 * @param selectedRule rule of searched state
	 */
	private void jumpRuleApply(String selectedRule) {
		if(selectedRule != null) {
			State curr = localStateManager.getCurrentState();
			boolean found = false;
			while(!localStateManager.getCurrentState().getChildren().isEmpty()) {
				localStateManager.moveToState(localStateManager.getCurrentState().getChildren().get(0), false);
				RuleState movedState = (RuleState) localStateManager.getCurrentState();
				if(movedState.getRule().getName().equals(selectedRule)) {
					int index = localStateManager.modelStates.getStates().indexOf(movedState);
					found = true;
					localStateManager.moveToState(curr, false);
					stateChanged(index);
					ruleList.deselectAll();
					oldSelectionRule = -1;
					selectedRuleInList = null;
					break;
				}
			}
			if(!found) {
				localStateManager.moveToState(curr, false);
				jumpRuleInfo.setText("No further apply!");
			}
		}	
	}
}

