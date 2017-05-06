# eMoflon-ibex

## Installation
0. Install GraphViz
1. Get current version of the Eclipse Modeling Tools:  http://www.eclipse.org/downloads/packages/eclipse-modeling-tools/neon3
2. From this repository, import the ```org.emoflon.ibex.tgg.workspace_configuration``` project into your workspace.  Let's refer to this project as our workspace-config from now on.
3. Go to ```Help->Install New Software->Available Software Sites->Import``` and choose the pluginsToInstall.xml``` file the workspace-config.  This should import all the update sites you'll need.
4. Install from the update sites:
  - Xtext
  - PlantUML Feature, PlantUML Library Feature
  - Kermeta EPackage registrationFeature
  - Democles Pattern Matcher Interpreter
  - Democles Pattern Specification
  - Democles Advanced Features
5. Check encoding for Xtend Files
  - In Eclipse: Go to ```Window->Preferences->General->Workspace```
  - Change the text file encoding to 'Other: UTF-8'
6. Set up your developer workspace
  - Choose the PSF file ```devProjectSet.psf``` in the , right-click and select ```Import Project Set...```.
7. Execute MWE2
  - Open project ```org.moflon.ibex.tgg.editor```
  - Go to package ```src/org.moflon.tgg.mosl```
  - Right-click on GenerateTGG.mwe2
  - Press ```Run As -> MWE2 Workflow```
8. Set up your runtime and test workspaces by starting a runtime Eclipse workspace from your dev workspace, and importing the other PSF files in the workspace-config.  Run the JUnit tests to ensure that all is well.
