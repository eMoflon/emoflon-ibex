# eMoflon::IBeX

eMoflon::IBeX is an Eclipse-based incremental interpreter for
	bidirectional graph transformations based on Triple Graph Grammars (TGGs)
	and unidirectional graph transformations.
It uses [eMoflon::IBeX UI](https://github.com/eMoflon/emoflon-ibex-ui)
	for the textual rule specification.

This repository contains only the part which is independent from a concrete pattern matcher.
- [eMoflon::IBeX Democles](https://github.com/eMoflon/emoflon-ibex-democles)
	uses eMoflon::IBeX with the Democles pattern matching engine.
- [eMoflon::IBeX HiPE](https://github.com/eMoflon/emoflon-ibex-hipe)
	uses eMoflon::IBeX with our new HiPE parallel pattern matching engine.

## How to develop
1. Install [GraphViz](http://www.graphviz.org/download/).
2. Get the latest version of the [Eclipse Modeling Tools](https://www.eclipse.org/downloads/packages/).
3. Install Xtext from this update site (or use the Eclipse Marketplace):
	http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/
4. Install PlantUML from this update site (or use the Eclipse Marketplace):
	http://hallvard.github.io/plantuml/
5. Install HiPE from this update site:
	https://hipe-devops.github.io/HiPE-Updatesite/hipe.updatesite/
6. Install Viatra from this update site:
	http://download.eclipse.org/viatra/updates/release/latest
7. Check the encoding for Xtend files.
    - In Eclipse: Go to ```Window->Preferences->General->Workspace```.
    - Change the text file encoding to 'Other: UTF-8'.
8. Go to ```File/Import.../Team/Team Project Set```, check URL and enter in and import one of these PSF files:<br/>
   For eMoflon including everything:	<br/>
	- https://raw.githubusercontent.com/eMoflon/emoflon-ibex-deployment/master/devProjectSet.psf <br/>
9. Execute MWE2
    - Open packages ```org.emoflon.ibex.gt.editor/src/org.emoflon.ibex.gt.editor``` and ```org.emoflon.ibex.tgg.editor/src/org.moflon.tgg.mosl```
    - Right-click on ```GenerateGT.mwe2``` in the first package and ```GenerateTGG.mwe2``` in the second.
    - Press ```Run As -> MWE2 Workflow```.
10. Set UTF-8 as file encoding for the development workspace (*Window &rarr; Preferences &rarr; General/Workspace*) and build all projects (*Project &rarr; Build All*) to trigger code generation (and get rid of errors).
11. Set up your runtime and test workspaces by starting a runtime Eclipse workspace
	from your development workspace, and importing this PSF file:<br/>
	https://raw.githubusercontent.com/eMoflon/emoflon-ibex-tests/master/testProjectSet.psf
12. Inside the runtime workspace, build all projects (*Project &rarr; Build All*) to trigger code generation.
13. Run the JUnit tests to ensure that all is well by right-clicking
	one of the ```Testsuite_*.launch``` in the ```Testsuite``` project
	and ```TestsuiteGT.launch``` in the ```TestsuiteGT``` project
	and start the tests by selecting ```Run As/JUnit```.
	If everything is set up correctly, all tests should be green.

Running ```Testsuite_GLPK.launch``` requires GLPK (see installation step 5).
	
Running ```Testsuite_Gurobi.launch``` requires Gurobi (see installation step 6).

Running ```Testsuite_CBC.launch``` requires Google OR tools (see installation step 7).

```Testsuite_SAT4J.launch``` uses the SAT4J (automatically installed, but the slowest option).  

## How to install
1. Install [GraphViz](http://www.graphviz.org/download/).
2. Get the latest version of the [Eclipse Modeling Tools](https://www.eclipse.org/downloads/packages/).
3. Install eMoflon::IBeX from this update site:
	https://emoflon.org/emoflon-ibex-updatesite/snapshot/updatesite/
    - Select the eMoflon::IBeX (Democles) feature to install eMoflon::IBeX along with the Democles pattern matching tool.
    - Select the eMoflon::IBeX (HiPE) feature to install eMoflon::IBeX along with the HiPE, our new parallel pattern matching tool.
    - Select the eMoflon::IBeX (Viatra) feature to install eMoflon::IBeX along with the [Viatra](https://www.eclipse.org/viatra/) pattern matching tool.
4. If dependencies were not found, go to Eclipse->Help->Install New Software...->Manage... and activate the following update sites:
    - Xtext: http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/
    - PlantUML: http://hallvard.github.io/plantuml/
    - HiPE: https://hipe-devops.github.io/HiPE-Updatesite/hipe.updatesite/
    - Viatra: http://download.eclipse.org/viatra/updates/release/latest
    
Now you are ready to use eMoflon::IBeX.

6. (Optional) Install [GLPK for Windows](https://sourceforge.net/projects/winglpk/)
	or install GLPK via your package manager (Linux).
7. (Optional) Install [Gurobi](http://www.gurobi.com/downloads/gurobi-optimizer) 7.0.2
	(make sure it is exactly this version!)
8. (Optional) Install [Google OR](https://developers.google.com/optimization/introduction/installing/binary) (not necessary on 64-bit Windows)

Note that Gurobi is only free for academical use (but not for commercial).
