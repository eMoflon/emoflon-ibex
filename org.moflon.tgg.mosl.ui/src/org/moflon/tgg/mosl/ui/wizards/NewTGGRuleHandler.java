package org.moflon.tgg.mosl.ui.wizards;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.utilities.LogUtils;
import org.moflon.ide.ui.UIActivator;
import org.moflon.ide.ui.admin.handlers.AbstractCommandHandler;

public class NewTGGRuleHandler extends AbstractCommandHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

		try {
			UIActivator.openWizard(NewTGGRuleWizard.NEW_TGG_RULE_WIZARD_ID, window);
		} catch (final Exception e) {
         LogUtils.error(logger, e, "Unable to open 'new TGG rule' wizard", e);
		}

		return null;
	}

}
