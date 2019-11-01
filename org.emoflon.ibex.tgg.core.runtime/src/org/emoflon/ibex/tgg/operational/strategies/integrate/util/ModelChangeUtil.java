package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class ModelChangeUtil {

	private final ModelChangeProtocol protocol;

	public ModelChangeUtil(ModelChangeProtocol protocol) {
		this.protocol = protocol;
	}

	private class NotificationLogger extends EContentAdapter {

		private List<Notification> notifications = new ArrayList<>();

		@Override
		public void notifyChanged(Notification notification) {
			switch (notification.getEventType()) {
			case Notification.ADD:
			case Notification.ADD_MANY:
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
			case Notification.SET:
				notifications.add(notification);
			}
			super.notifyChanged(notification);
		}

	}

	public List<Notification> undo(Notification notification, boolean enhanced) {
		return undoOne(notification, null, enhanced);
	}

	public List<Notification> undoOne(Notification notification, EObject element, boolean enhanced) {
		boolean containment = false;
		if (notification.getFeature() instanceof EReference)
			containment = ((EReference) notification.getFeature()).isContainment();

		Object notifier = notification.getNotifier();
		NotificationLogger logger = new NotificationLogger();
		Resource resource = null;
		if (notifier instanceof EObject)
			resource = ((EObject) notifier).eResource();
		else if (notifier instanceof Resource)
			resource = (Resource) notifier;
		resource.eAdapters().add(logger);

		switch (notification.getEventType()) {
		case Notification.ADD:
			undoAdd(notification, notifier, enhanced && containment);
			break;
		case Notification.ADD_MANY:
			undoAddMany(notification, notifier, element, enhanced && containment);
			break;
		case Notification.REMOVE:
			undoRemove(notification, notifier, enhanced && containment);
			break;
		case Notification.REMOVE_MANY:
			undoRemoveMany(notification, notifier, element, enhanced && containment);
			break;
		case Notification.SET:
			undoSet(notification, notifier, enhanced && containment);
			break;
		}

		resource.eAdapters().remove(logger);
		return logger.notifications;
	}

	private void undoAdd(Notification notification, Object notifier, boolean enhanced) {
		if (enhanced) {
			EcoreUtil.delete((EObject) notification.getNewValue(), true);
		} else {
			if (notifier instanceof EObject) {
				EObject eObjNotifier = (EObject) notifier;
				EList<?> eList = (EList<?>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
				eList.remove(notification.getNewValue());
			} else if (notifier instanceof Resource) {
				Resource resNotifier = (Resource) notifier;
				resNotifier.getContents().remove(notification.getNewValue());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void undoAddMany(Notification notification, Object notifier, EObject element, boolean enhanced) {
		boolean undoOne = element != null && ((List<?>) notification.getNewValue()).contains(element);

		if (enhanced) {
			if (undoOne)
				EcoreUtil.delete(element, true);
			else
				EcoreUtil.deleteAll((Collection<EObject>) notification.getNewValue(), true);
		} else {
			if (notifier instanceof EObject) {
				EObject eObjNotifier = (EObject) notifier;
				EList<?> eList = (EList<?>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
				if (undoOne)
					eList.remove(element);
				else
					eList.removeAll((List<?>) notification.getNewValue());
			} else if (notifier instanceof Resource) {
				Resource resNotifier = (Resource) notifier;
				if (undoOne)
					resNotifier.getContents().remove(element);
				else
					resNotifier.getContents().removeAll((List<?>) notification.getNewValue());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void undoRemove(Notification notification, Object notifier, boolean enhanced) {
		if (notifier instanceof EObject) {
			EObject eObjNotifier = (EObject) notifier;
			EList<EObject> eList = (EList<EObject>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
			eList.add(notification.getPosition(), (EObject) notification.getOldValue());
			if (enhanced)
				hookInResource(eObjNotifier);
		} else if (notifier instanceof Resource) {
			Resource resNotifier = (Resource) notifier;
			resNotifier.getContents().add(notification.getPosition(), (EObject) notification.getOldValue());
		}
	}

	@SuppressWarnings("unchecked")
	private void undoRemoveMany(Notification notification, Object notifier, EObject element, boolean enhanced) {
		boolean undoOne = element != null && ((List<?>) notification.getOldValue()).contains(element);

		if (notifier instanceof EObject) {
			EObject eObjNotifier = (EObject) notifier;
			EList<EObject> eList = (EList<EObject>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
			if (undoOne)
				eList.add(element);
			else
				eList.addAll((List<EObject>) notification.getOldValue());
			if (enhanced)
				hookInResource(eObjNotifier);
		} else if (notifier instanceof Resource) {
			Resource resNotifier = (Resource) notifier;
			if (undoOne)
				resNotifier.getContents().add(element);
			else
				resNotifier.getContents().addAll((List<EObject>) notification.getOldValue());
		}
	}

	private void hookInResource(EObject element) {
		if (element.eResource() == null) {
			EObject lastElement = element;
			while (lastElement.eContainer() != null)
				lastElement = lastElement.eContainer();
			for (Notification n : protocol.getReverseRemovals(lastElement)) {
				if (((EReference) n.getFeature()).isContainment()) {
					switch (n.getEventType()) {
					case Notification.REMOVE:
						undoRemove(n, n.getNotifier(), true);
						return;
					case Notification.REMOVE_MANY:
						undoRemoveMany(n, n.getNotifier(), lastElement, true);
						return;
					}
				}
			}
		}
	}

	private void undoSet(Notification notification, Object notifier, boolean enhanced) {
		// TODO adrianm: enhanced mode
		EObject eObjNotifier = (EObject) notifier;
		eObjNotifier.eSet((EStructuralFeature) notification.getFeature(), notification.getOldValue());
	}

	// TODO adrianm: remove if not needed any longer
	@Deprecated
	public static void redo(Notification notification) {
		Object notifier = notification.getNotifier();
		switch (notification.getEventType()) {
		case Notification.ADD:
			redoAdd(notification, notifier);
			break;
		case Notification.ADD_MANY:
			redoAddMany(notification, notifier);
			break;
		case Notification.REMOVE:
			redoRemove(notification, notifier);
			break;
		case Notification.REMOVE_MANY:
			redoRemoveMany(notification, notifier);
			break;
		case Notification.SET:
			redoSet(notification, notifier);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private static void redoAdd(Notification notification, Object notifier) {
		if (notifier instanceof EObject) {
			EObject eObjNotifier = (EObject) notifier;
			EList<EObject> eList = (EList<EObject>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
			eList.add(notification.getPosition(), (EObject) notification.getNewValue());
		} else if (notifier instanceof Resource) {
			Resource resNotifier = (Resource) notifier;
			resNotifier.getContents().add(notification.getPosition(), (EObject) notification.getNewValue());
		}
	}

	@SuppressWarnings("unchecked")
	private static void redoAddMany(Notification notification, Object notifier) {
		if (notifier instanceof EObject) {
			EObject eObjNotifier = (EObject) notifier;
			EList<EObject> eList = (EList<EObject>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
			eList.addAll((List<EObject>) notification.getNewValue());
		} else if (notifier instanceof Resource) {
			Resource resNotifier = (Resource) notifier;
			resNotifier.getContents().addAll((List<EObject>) notification.getNewValue());
		}
	}

	@SuppressWarnings("unchecked")
	private static void redoRemove(Notification notification, Object notifier) {
		if (notifier instanceof EObject) {
			EObject eObjNotifier = (EObject) notifier;
			EList<EObject> eList = (EList<EObject>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
			eList.remove(notification.getOldValue());
		} else if (notifier instanceof Resource) {
			Resource resNotifier = (Resource) notifier;
			resNotifier.getContents().remove(notification.getOldValue());
		}
	}

	@SuppressWarnings("unchecked")
	private static void redoRemoveMany(Notification notification, Object notifier) {
		if (notifier instanceof EObject) {
			EObject eObjNotifier = (EObject) notifier;
			EList<EObject> eList = (EList<EObject>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
			eList.removeAll((List<EObject>) notification.getOldValue());
		} else if (notifier instanceof Resource) {
			Resource resNotifier = (Resource) notifier;
			resNotifier.getContents().removeAll((List<EObject>) notification.getOldValue());
		}
	}

	private static void redoSet(Notification notification, Object notifier) {
		EObject eObjNotifier = (EObject) notifier;
		eObjNotifier.eSet((EStructuralFeature) notification.getFeature(), notification.getNewValue());
	}

}
