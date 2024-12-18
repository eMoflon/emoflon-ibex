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

There are 4 ways how to use eMoflon::IBeX:
1. Use our [pre-built Eclipse download](#pre-built-eclipse) with eMoflon::IBeX installed (or the version without eMoflon::IBeX to develop it).
1. Use our [pre-built virtual machine (VM)](#pre-built-virtual-machine-vm) with eMoflon::IBeX installed.
1. Prepare your Eclipse environment step-by-step with the section [how to develop](#how-to-develop).
1. [Install eMoflon::IBeX](#how-to-install) into your existing Eclipse installation.


## Pre-built Eclipse

1. Install [GraphViz](http://www.graphviz.org/download/).
1. Choose the right pre-built Eclipse version for your need. A comparisson table can be found in the [readme of the emoflon-ibex-eclipse-build repository](https://github.com/eMoflon/emoflon-ibex-eclipse-build). Typically, you want to download:
	+ `eclipse-emoflon-$yourOS-dev.zip` if you want to develop eMoflon::IBeX
	+ `eclipse-emoflon-$yourOS-user.zip` if you want to use eMoflon::IBeX without developing it
1. Download your chosen [Eclipse archive](https://github.com/eMoflon/emoflon-ibex-eclipse-build/releases/latest).
1. Extract the archive to a folder, e.g., to `~/eclipse-apps/emoflon-ibex`.
1. Start your Eclipse (= the binary in the extracted folder) and create a new workspace.

### eclipse-emoflon-user

You can now create eMoflon projects and/or start using it with existing projects, e.g., from the [tutorial](https://github.com/eMoflon/emoflon-ibex-tutorial).

### eclipse-emoflon-dev

Some additional steps are needed:
Complete steps 9, 10, 12-15 from [the next section](#how-to-develop) to clone the code and verify your build.


## Pre-built virtual machine (VM)

You can download the latest version of our pre-built virtual machine image from the [release section](https://github.com/eMoflon/emoflon-ibex-vm/releases).
For detailed installation instructions, please refer to the [README.md file](https://github.com/eMoflon/emoflon-ibex-vm#usageinstallation) within the repository.


## How to develop

1. Install a Java JDK >= 21.
1. Install [GraphViz](http://www.graphviz.org/download/).
1. Get the latest version of the [Eclipse Modeling Tools](https://www.eclipse.org/downloads/packages/). You need at least **Eclipse 2024-06**.
1. Install Xtext from this update site (or use the Eclipse Marketplace):
	http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/
1. Install PlantUML from this update site (or use the Eclipse Marketplace):
	http://hallvard.github.io/plantuml/
1. Install HiPE from this update site:
	https://hipe-devops.github.io/HiPE-Updatesite/hipe.updatesite/
1. Install the EPackage Registration Feature from http://www.kermeta.org/k2/update
1. Check the encoding for Xtend files.
    - In Eclipse: Go to ```Window->Preferences->General->Workspace```.
    - Change the text file encoding to 'Other: UTF-8'.
1. (Important!) Set standard git folder to (workspace) relative path, e.g., (Eclipse ->) Window -> Preferences -> Team (or Version Control) -> Git -> Default Repository Folder = `${workspace_loc}\git` (for Windows) or `${workspace_loc}/git` (for Linux and macOS)
1. Go to ```File/Import.../Team/Team Project Set```, check URL and enter in and import this PSF file:
	- https://raw.githubusercontent.com/eMoflon/emoflon-ibex-deployment/master/devProjectSet.psf
1. Execute MWE2
    - Open package ```org.emoflon.ibex.common/launch```.
    - Right-click on ```Run all eMoflon MWE2 files.mwe2```.
    - Press ```Run As -> Run all eMoflon MWE2 files```.
	- In case any error warning pops up, simply click `Proceed`.
1. Set UTF-8 as file encoding for the development workspace (*Window &rarr; Preferences &rarr; General/Workspace*) and manually build all projects by clicking (*Project &rarr; Clean... &rarr; Clean all projects*) to trigger code generation (and get rid of errors). Make sure to check *Project &rarr; Build Automatically*.
1. Set up your runtime and test workspaces by starting a runtime Eclipse workspace from your development workspace:
	- To start the runtime workspace, do the following steps *inside* your development workspace: *Run &rarr; Run Configurations...;* double click on *Eclipse Application*, give it a name (e.g., *test-workspace*) and click on *Run*.
		- Your development Eclipse should now start another Eclipse instance with all eMoflon plug-ins installed.
	- Inside your runtime workspace:
		- Check and/or apply the git setting of **step 9** again.
		- Import this PSF file:  
		https://raw.githubusercontent.com/eMoflon/emoflon-ibex-tests/master/testProjectSet.psf
1. Execute MWE2
    - Open package ```/org.emoflon.express/src/org.emoflon.express```
    - Right-click on ```GenerateExpress.mwe2```
    - Press ```Run As -> MWE2 Workflow```
	- If you can not find the package, open the `eMoflon` perspective (`Window` -> `Perspective` -> `Open Perspective` -> `Other...` -> `eMoflon`).
1. Inside the runtime workspace, build all projects (*Project &rarr; Clean... &rarr; Clean all projects*) to trigger code generation.
	- Hint: It may be required to trigger a full eMoflon build on all projects. Select all projects in *Package Explorer* and click on the black hammer symbol.
1. Run the JUnit tests to ensure that all is well by right-clicking
	one of the ```Testsuite_*.launch``` in the ```Testsuite``` project
	and ```TestsuiteGT_*.launch``` in the ```TestsuiteGT``` project
	and start the tests by selecting ```Run As/<name-of-the-file>```.
	If everything is set up correctly, all tests should be green.
	- If you get an error `Class not found`, select all projects on the package explorer and click `F5`/`Refresh`.

In a standard scenario, you want to run:
- `TestSuite_HiPE.launch`
- `TestSuiteGT_HiPE.launch`

Other options:
- Running ```Testsuite_GLPK.launch``` requires GLPK (see installation step 5).
- Running ```Testsuite_Gurobi.launch``` requires Gurobi (see installation step 6).
- Running ```Testsuite_CBC.launch``` requires Google OR tools (see installation step 7).
- ```Testsuite_SAT4J.launch``` uses the SAT4J (automatically installed, but the slowest option).  


## How to install

eMoflon::IBeX can be installed via its updatesite: https://github.com/eMoflon/emoflon-ibex-updatesite

Please notice: You need at least Eclipse 2024-06.

Please notice: Ensure that your Eclipse runs with an OpenJDK >= 21.

(Please notice:
This section is for *installation purpose* only.
If you already setup your development workspace as described above, this section is irrelevant for you.
Furthermore, you may need to install some additional dependencies if Eclipse tells you to.)
