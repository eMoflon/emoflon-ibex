# eMoflon-ibex

## Installation
1. Get current version of eclipse modeling tools
2. Install
  - Xtext  (Eclipse Marketplace)
  - Xtend  (Should have been installed with Xtext)
  - eMoflon IDE Core (do not install the rest!) Link:[eMoflon-builds](https://emoflon.github.io/installation.html)
  - PlantUML (e.g. http://plantuml.sourceforge.net/updatesitejuno/)
3. Check encoding for Xtend Files
  - In Eclipse: Go to Window->Preferences->General->Workspace 
  - Change Text file encoding to 'Other: UTF-8'
4. Execute MWE2
  - Open project org.moflon.ibex.editor. 
  - Go to package src/org.moflon.tgg.mosl 
  - Right-Click on GenerateTGG.mwe2 
  - Press Run As -> MWE2 Workflow
5. Install registration feature of Kermata: http://www.kermeta.org/k2/update/  
6. Import Democles
  - Import the workspace_configuration project into eclipse
  - In Eclipse: Go to Window->Preferences->Plugin Development->Target Platform
		- Select "democlesEnabledPlatform"
		- Press Apply
