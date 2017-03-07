package org.moflon.tgg.mosl.ui.wizards;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public abstract class AbstractMoflonProjectInfoPage extends WizardPage
{
   private String projectName;

   private boolean useDefaultLocation;

   private String projectLocation;

   private Button defaultLocationCheckbox;

   private Label projectLocationLabel;

   private Text projectLocationTextfield;

   private Button browseTargetDirectoryButton;

   private Text projectNameTextfield;

   public AbstractMoflonProjectInfoPage(String name, String title, String desc)
   {
      super(name);
      projectName = "";
      useDefaultLocation = true;
      setProjectLocation(null);

      // Set information on the page
      setTitle(title);
      setDescription(desc);
      setImageDescriptor(AbstractMoflonWizard.getImage("resources/icons/metamodelProjectWizard.gif"));
   }

   @Override
   public void createControl(final Composite parent)
   {
      // Create root container
      Composite container = new Composite(parent, SWT.NULL);
      GridLayout layout = new GridLayout();
      container.setLayout(layout);
      layout.numColumns = 3;

      createControlsForProjectName(container);

      createControlsForProjectLocation(container);

      // Place cursor in textfield
      projectNameTextfield.setFocus();

      // Set controls and update
      setControl(container);
      dialogChanged();
   }

   public void createControlsForProjectLocation(final Composite container)
   {
      defaultLocationCheckbox = new Button(container, SWT.CHECK);
      defaultLocationCheckbox.setText("Use default location");
      createDummyLabel(container); // dummy label
      createDummyLabel(container); // dummy label

      projectLocationLabel = createDummyLabel(container);
      projectLocationLabel.setText("&Location:");
      projectLocationTextfield = new Text(container, SWT.BORDER | SWT.SINGLE);
      GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
      projectLocationTextfield.setLayoutData(gd2);

      projectLocationTextfield.addModifyListener(new ModifyListener() {

         @Override
         public void modifyText(final ModifyEvent e)
         {
            final String text = projectLocationTextfield.getText();
            if (text.isEmpty())
            {
               setErrorMessage("Project location must not be empty");
            } else
            {
               setProjectLocation(projectLocationTextfield.getText());
            }

            dialogChanged();
         }
      });

      browseTargetDirectoryButton = new Button(container, SWT.PUSH);
      browseTargetDirectoryButton.setText("Browse...");
      GridData gd3 = new GridData();
      browseTargetDirectoryButton.setLayoutData(gd3);
      browseTargetDirectoryButton.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(final SelectionEvent event)
         {
            DirectoryDialog dialog = new DirectoryDialog(container.getShell(), SWT.SAVE);
            dialog.setText("Select location for metamodel");
            File initialDeploymentDirectory = ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile();
            dialog.setFilterPath(initialDeploymentDirectory.getAbsolutePath());

            String destinationDirectory = dialog.open();

            if (destinationDirectory != null)
            {
               projectLocationTextfield.setText(destinationDirectory + File.separator + projectName);
            }
         }

      });

      defaultLocationCheckbox.addSelectionListener(new SelectionListener() {
         @Override
         public void widgetSelected(final SelectionEvent e)
         {
            defaultLocationSelectionChanged();
         }

         @Override
         public void widgetDefaultSelected(final SelectionEvent e)
         {
            defaultLocationSelectionChanged();
         }

      });
      defaultLocationCheckbox.setSelection(useDefaultLocation);
      defaultLocationSelectionChanged();
   }

   public void createControlsForProjectName(final Composite container)
   {
      // Create control for entering project name
      Label label = createDummyLabel(container);
      label.setText("&Project name:");

      projectNameTextfield = new Text(container, SWT.BORDER | SWT.SINGLE);
      GridData gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      projectNameTextfield.setLayoutData(gd);
      projectNameTextfield.addModifyListener(new ModifyListener() {
         @Override
         public void modifyText(final ModifyEvent e)
         {
            projectName = projectNameTextfield.getText();

            if (!useDefaultLocation && projectName.isEmpty())
            {
               setErrorMessage("Project name must not be empty.");
            }

            dialogChanged();
         }
      });
   }

   public Label createDummyLabel(final Composite container)
   {
      return new Label(container, SWT.NULL);
   }

   public void defaultLocationSelectionChanged()
   {
      useDefaultLocation = defaultLocationCheckbox.getSelection();
      projectLocationLabel.setEnabled(!useDefaultLocation);
      projectLocationTextfield.setEnabled(!useDefaultLocation);
      browseTargetDirectoryButton.setEnabled(!useDefaultLocation);

      if (useDefaultLocation)
      {
         setProjectLocation(null);
      } else
      {
         setProjectLocation(projectLocationTextfield.getText());
      }
   }

   @Override
   public void setVisible(final boolean visible)
   {
      super.setVisible(visible);
   }

   @Override
   public boolean canFlipToNextPage()
   {
      return super.canFlipToNextPage() && getErrorMessage() == null;
   }

   public String getProjectName()
   {
      return projectName;
   }

   /**
    * Returns the selected project location.
    * 
    * If the default location should be used, null is returned.
    * 
    * @return
    */
   public IPath getProjectLocation()
   {
      return projectLocation == null ? null : new Path(projectLocation);
   }

   private void setProjectLocation(final String projectLocation)
   {
      this.projectLocation = projectLocation;
   }

   private final void updateStatus(final String message)
   {
      setErrorMessage(message);
      setPageComplete(message == null);
   }

   private void dialogChanged()
   {
      IStatus validity = validateProjectName(projectName, AbstractMoflonWizard.getModuleID());

      if (validity.isOK())
         updateStatus(null);
      else
         updateStatus(validity.getMessage());
   }

   /**
    * Checks if given name is a valid name for a new project in the current workspace.
    * 
    * @param projectName
    *           Name of project to be created in current workspace
    * @param pluginId
    *           ID of bundle
    * @return A status object indicating success or failure and a relevant message.
    */
   private static IStatus validateProjectName(final String projectName, final String pluginId)
   {
      // Check if anything was entered at all
      if (projectName.length() == 0)
         return new Status(IStatus.ERROR, pluginId, "Name must be specified");

      // Check if name is a valid path for current platform
      IStatus validity = ResourcesPlugin.getWorkspace().validateName(projectName, IResource.PROJECT);
      if (!validity.isOK())
         return new Status(IStatus.ERROR, pluginId, validity.getMessage());

      // Check if no other project with the same name already exists in workspace
      IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
      for (IProject project : projects)
      {
         if (project.getName().equals(projectName))
         {
            return new Status(IStatus.ERROR, pluginId, "A project with this name exists already.");
         }
      }

      // Everything was fine
      return new Status(IStatus.OK, pluginId, "Project name is valid");
   }

}