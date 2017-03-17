# eMoflon-ibex

## Installation
1. Get current version of eclipse modeling tools
2. Install
  - Xtext  (Eclipse Marketplace)
  - eMoflon IDE Core (do not install the rest!) Link:[eMoflon-builds](https://emoflon.github.io/installation.html)
  - PlantUML (make sure you use: http://plantuml.sourceforge.net/updatesitejuno/) and GraphViz
3. Check encoding for Xtend Files
  - In Eclipse: Go to Window->Preferences->General->Workspace 
  - Change Text file encoding to 'Other: UTF-8'
4. Install registration feature of Kermata: http://www.kermeta.org/k2/update/  
5. Install Democles
  - Import the workspace_configuration project into Eclipse
  - In Eclipse: Go to Window->Preferences->Plugin Development->Target Platform
  - Select "democlesEnabledPlatform"
  - Press Apply
  - Open the file ```democlesEnabledPlatform.target``` in org.emoflon.ibex.tgg.workspace_configuration and perform updates on the individual components
6. Set up your developer workspace
  - Open the workspace_configuration project, choose the PSF file ```devProjectSet.psf```, right-click and select ```Import Project Set...```.  After this you should have exactly 6 projects in your workspace.
7. Execute MWE2
  - Open project org.moflon.ibex.tgg.editor 
  - Go to package src/org.moflon.tgg.mosl 
  - Right-Click on GenerateTGG.mwe2 
  - Press Run As -> MWE2 Workflow
8. Set up your runtime workspace
  - Open the workspace_configuration project, choose ```ibex_runtime_workspace_run_configuration.launch```, right-click and select ```Run As``` and choose to start a new Eclipse application.
  - In the runtime workspace import the PSF file ```runtimeProjectSet.psf```
