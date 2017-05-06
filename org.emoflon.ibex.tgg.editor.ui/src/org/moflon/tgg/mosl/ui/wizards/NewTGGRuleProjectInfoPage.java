package org.moflon.tgg.mosl.ui.wizards;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.emoflon.ibex.tgg.ui.ide.admin.IbexTGGPlugin;
import org.moflon.util.LogUtils;

public class NewTGGRuleProjectInfoPage extends WizardPage
{

   private static final Logger logger = Logger.getLogger(NewTGGRuleProjectInfoPage.class);

   private String ruleName;

   private Optional<String> schema = Optional.empty();

   private Label schemaLocationLabel;

   private Text schemaLocationTextfield;

   private Text ruleNameTextfield;

   private Optional<IResource> ruleLocation = Optional.empty();

   private Optional<IProject> project = Optional.empty();

   public NewTGGRuleProjectInfoPage()
   {
      super("New TGG rule");
      ruleName = "";
      setProjectAndRuleLocation();
      setSchema();

      // Set information on the page
      setTitle("New TGG rule wizard");
      setDescription("Create a new TGG rule");
      setImageDescriptor(AbstractMoflonWizard.getImage("resources/icons/metamodelProjectWizard.gif"));
   }
   
   private void setProjectAndRuleLocation()
   {
      IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
      ISelection selection = window.getSelectionService().getSelection("org.eclipse.sirius.ui.tools.views.model.explorer");
      if (selection instanceof ITreeSelection)
      {
         ITreeSelection structuredSelection = (ITreeSelection) selection;
         Object elt = structuredSelection.getFirstElement();
         ruleLocation = determineLocationForNewRuleInProject(elt);
         project = ruleLocation.map(l -> l.getProject());
      }
   }

   private void setSchema()
   {
      schema = project.map(p -> p.getName());
   }

   @Override
   public void createControl(final Composite parent)
   {
      // Create root container
      Composite container = new Composite(parent, SWT.NULL);
      GridLayout layout = new GridLayout();
      container.setLayout(layout);
      layout.numColumns = 3;

      createControlsForRuleName(container);

      createControlsForSchemaLocation(container);

      // Place cursor in textfield
      ruleNameTextfield.setFocus();

      // Set controls and update
      setControl(container);
      dialogChanged();
   }

   public void createControlsForSchemaLocation(final Composite container)
   {
      schemaLocationLabel = createLabel(container);
      schemaLocationLabel.setText("&Schema:");
      schemaLocationTextfield = new Text(container, SWT.BORDER | SWT.SINGLE);
      schema.ifPresent(s -> schemaLocationTextfield.setText(s));

      GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
      gd2.horizontalSpan = 2;
      schemaLocationTextfield.setLayoutData(gd2);
      schemaLocationTextfield.addModifyListener(new ModifyListener() {
         @Override
         public void modifyText(final ModifyEvent e)
         {
            final String text = schemaLocationTextfield.getText();
            if (text.isEmpty() || text.equals(""))
            {
               setErrorMessage("Schema location must not be empty");
               schema = Optional.empty();
            } else
            {
               schema = Optional.of(text);
            }

            dialogChanged();
         }
      });
   }

   public void createControlsForRuleName(final Composite container)
   {
      // Create control for entering project name
      Label label = createLabel(container);
      label.setText("&Rule name:");

      ruleNameTextfield = new Text(container, SWT.BORDER | SWT.SINGLE);
      GridData gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      ruleNameTextfield.setLayoutData(gd);
      ruleNameTextfield.addModifyListener(new ModifyListener() {
         @Override
         public void modifyText(final ModifyEvent e)
         {
            ruleName = ruleNameTextfield.getText();

            if (ruleName.isEmpty())
            {
               setErrorMessage("Rule name must not be empty.");
            }

            dialogChanged();
         }
      });
   }

   public Label createLabel(final Composite container)
   {
      return new Label(container, SWT.NULL);
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

   public String getRuleName()
   {
      return ruleName;
   }

   public IResource getRuleLocation()
   {
      return ruleLocation.orElseThrow(() -> new IllegalStateException("Please choose a folder so I know where to put the new rule!"));
   }

   private Optional<IResource> determineLocationForNewRuleInProject(Object selectedElement)
   {
      if (selectedElement instanceof IJavaElement)
      {
         try
         {
            return Optional.of(((IJavaElement) selectedElement).getCorrespondingResource());
         } catch (JavaModelException e)
         {
            LogUtils.error(logger, e);
         }
      }

      if (selectedElement instanceof IFile)
         return Optional.of(((IFile) selectedElement).getParent());

      if (selectedElement instanceof IFolder)
         return Optional.of((IFolder) selectedElement);

      return Optional.empty();
   }

   public String getSchema()
   {
      return schema.orElse("schema");
   }

   private final void updateStatus(final String message)
   {
      setErrorMessage(message);
      setPageComplete(message == null);
   }

   private void dialogChanged()
   {
      IStatus validity = validate();

      if (validity.isOK())
         updateStatus(null);
      else
         updateStatus(validity.getMessage());
   }

   private IStatus validate()
   {
      if (ruleName.isEmpty())
         return new Status(IStatus.ERROR, IbexTGGPlugin.IBEX_TGG_ID, "Rule name must not be empty!");

      if (!schema.isPresent())
         return new Status(IStatus.ERROR, IbexTGGPlugin.IBEX_TGG_ID, "Schema name must not be empty!");

      return new Status(IStatus.OK, IbexTGGPlugin.IBEX_TGG_ID, "Rule is valid");
   }

}