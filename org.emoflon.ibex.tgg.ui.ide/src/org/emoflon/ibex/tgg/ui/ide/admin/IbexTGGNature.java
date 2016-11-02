package org.emoflon.ibex.tgg.ui.ide.admin;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.runtime.natures.ProjectConfiguratorNature;

public class IbexTGGNature implements IProjectNature {
	public static final String IBEX_TGG_NATURE_ID = "org.emoflon.ibex.tgg.nature";
	public static final String XTEXT_BUILDER_ID = "org.eclipse.xtext.ui.shared.xtextBuilder";
	public static final String XTEXT_NATURE_ID = "org.eclipse.xtext.ui.shared.xtextNature";

	@Override
	public ICommand[] updateBuildSpecs(final IProjectDescription description,
			ICommand[] buildSpecs, final boolean added) throws CoreException {
		if (added) {
			int integrationBuilderPosition = ProjectUtil.indexOf(buildSpecs, CoreActivator.INTEGRATION_BUILDER_ID);
			// Insert or move Xtext builder before IntegrationBuilder
			int xtextBuilderPosition = ProjectUtil.indexOf(buildSpecs, XTEXT_BUILDER_ID);
			if (xtextBuilderPosition < 0) {
				final ICommand xtextBuilder = description.newCommand();
				xtextBuilder.setBuilderName(XTEXT_BUILDER_ID);
				buildSpecs = Arrays.copyOf(buildSpecs, buildSpecs.length + 1);
				xtextBuilderPosition = buildSpecs.length - 1;
				buildSpecs[xtextBuilderPosition] = xtextBuilder;
			}
			if (integrationBuilderPosition < xtextBuilderPosition) {
				final ICommand xtextBuilder = buildSpecs[xtextBuilderPosition];
				System.arraycopy(buildSpecs, integrationBuilderPosition, buildSpecs, integrationBuilderPosition + 1,
						xtextBuilderPosition - integrationBuilderPosition);
				xtextBuilderPosition = integrationBuilderPosition++;
				buildSpecs[xtextBuilderPosition] = xtextBuilder;
			}
			// Insert or move MOSL-TGG builder before IntegrationBuilder and after Xtext builder
			int moslTGGBuilderPosition = ProjectUtil.indexOf(buildSpecs, IbexTGGBuilder.BUILDER_ID);
			if (moslTGGBuilderPosition < 0) {
				final ICommand moslTGGBuilder = description.newCommand();
				moslTGGBuilder.setBuilderName(IbexTGGBuilder.BUILDER_ID);
				buildSpecs = Arrays.copyOf(buildSpecs, buildSpecs.length + 1);
				moslTGGBuilderPosition = buildSpecs.length - 1;
				buildSpecs[moslTGGBuilderPosition] = moslTGGBuilder;
			}
			assert xtextBuilderPosition < integrationBuilderPosition;
			if (xtextBuilderPosition > moslTGGBuilderPosition) {
				final ICommand moslTGGBuilder = buildSpecs[moslTGGBuilderPosition];
				System.arraycopy(buildSpecs, moslTGGBuilderPosition + 1, buildSpecs, moslTGGBuilderPosition,
						xtextBuilderPosition - moslTGGBuilderPosition);
				moslTGGBuilderPosition = xtextBuilderPosition--;
				buildSpecs[moslTGGBuilderPosition] = moslTGGBuilder;
			}
			if (integrationBuilderPosition < moslTGGBuilderPosition) {
				final ICommand moslTGGBuilder = buildSpecs[moslTGGBuilderPosition];
				System.arraycopy(buildSpecs, integrationBuilderPosition, buildSpecs, integrationBuilderPosition + 1,
						moslTGGBuilderPosition - integrationBuilderPosition);
				moslTGGBuilderPosition = integrationBuilderPosition++;
				buildSpecs[moslTGGBuilderPosition] = moslTGGBuilder;
			}
		} else {
			int xtextBuilderPosition = ProjectUtil.indexOf(buildSpecs, XTEXT_BUILDER_ID);
			if (xtextBuilderPosition >= 0) {
				buildSpecs = ProjectUtil.remove(buildSpecs, xtextBuilderPosition);
			}
			int moslTGGBuilderPosition = ProjectUtil.indexOf(buildSpecs, IbexTGGBuilder.BUILDER_ID);
			if (moslTGGBuilderPosition >= 0) {
				buildSpecs = ProjectUtil.remove(buildSpecs, moslTGGBuilderPosition);
			}
		}
		return buildSpecs;
	}

	@Override
	public String[] updateNatureIDs(String[] natureIDs, final boolean added) throws CoreException {
		if (added) {
			if (ProjectUtil.indexOf(natureIDs, XTEXT_NATURE_ID) < 0) {
				natureIDs = Arrays.copyOf(natureIDs, natureIDs.length + 1);
				natureIDs[natureIDs.length - 1] = XTEXT_NATURE_ID;
			}
			if (ProjectUtil.indexOf(natureIDs, IBEX_TGG_NATURE_ID) < 0) {
				natureIDs = Arrays.copyOf(natureIDs, natureIDs.length + 1);
				natureIDs[natureIDs.length - 1] = IBEX_TGG_NATURE_ID;
			}
		} else {
			int xtextNaturePosition = ProjectUtil.indexOf(natureIDs, XTEXT_NATURE_ID);
			if (xtextNaturePosition >= 0) {
				natureIDs = ProjectUtil.remove(natureIDs, xtextNaturePosition);
			}
			int moslTGGNaturePosition = ProjectUtil.indexOf(natureIDs, IBEX_TGG_NATURE_ID);
			if (xtextNaturePosition >= 0) {
				natureIDs = ProjectUtil.remove(natureIDs, moslTGGNaturePosition);
			}
		}
		return natureIDs;
	}

	@Override
	public void configure() throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deconfigure() throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IProject getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProject(IProject project) {
		// TODO Auto-generated method stub
		
	}
}
