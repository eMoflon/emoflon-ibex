package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.emf.EMFEdge;

public class ModelChangeUtil {

	private final Resource[] observedRes;
	private final ModelChangeProtocol protocol;

	public ModelChangeUtil(Resource[] observedRes, ModelChangeProtocol protocol) {
		this.observedRes = observedRes;
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

		private void attach() {
			for (int i = 0; i < observedRes.length; i++)
				observedRes[i].eAdapters().add(this);
		}

		private void detach() {
			for (int i = 0; i < observedRes.length; i++)
				observedRes[i].eAdapters().remove(this);
		}

	}

	public List<Notification> undo(Notification notification, boolean enhanced) {
		return undoOne(notification, null, enhanced);
	}

	public List<Notification> undoOne(Notification notification, EObject element, boolean enhanced) {
		boolean containment = false;
		if(notification.getFeature() == null)
			containment = true;
		else if (notification.getFeature() instanceof EReference)
			containment = ((EReference) notification.getFeature()).isContainment();

		Object notifier = notification.getNotifier();
		NotificationLogger logger = new NotificationLogger();
		logger.attach();

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

		logger.detach();
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
			if (enhanced)
				hookInResource(eObjNotifier);
			else {
				EList<EObject> eList = (EList<EObject>) eObjNotifier
						.eGet((EStructuralFeature) notification.getFeature());
				eList.add(notification.getPosition(), (EObject) notification.getOldValue());
			}
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
			if (enhanced)
				hookInResource(eObjNotifier);
			else {
				EList<EObject> eList = (EList<EObject>) eObjNotifier
						.eGet((EStructuralFeature) notification.getFeature());
				if (undoOne)
					eList.add(element);
				else
					eList.addAll((List<EObject>) notification.getOldValue());
			}
		} else if (notifier instanceof Resource) {
			Resource resNotifier = (Resource) notifier;
			if (undoOne)
				resNotifier.getContents().add(element);
			else
				resNotifier.getContents().addAll((List<EObject>) notification.getOldValue());
		}
	}

	private void undoSet(Notification notification, Object notifier, boolean enhanced) {
		// TODO adrianm: add enhanced mode
		EObject eObjNotifier = (EObject) notifier;
		eObjNotifier.eSet((EStructuralFeature) notification.getFeature(), notification.getOldValue());
	}

	private void hookInResource(EObject element) {
		LinkedList<Runnable> operations = new LinkedList<>();
		collectOperations(element, operations);
		operations.forEach(operation -> operation.run());
	}

	private void collectOperations(Object element, LinkedList<Runnable> ops) {
		if (element instanceof Resource)
			return;

		EObject eObjElt = (EObject) element;
		if (eObjElt.eResource() == null) {
			EObject lastElement = eObjElt;
			while (lastElement.eContainer() != null)
				lastElement = lastElement.eContainer();
			for (Notification n : protocol.getReverseRemovals(lastElement)) {
				if (n.getFeature() == null || ((EReference) n.getFeature()).isContainment()) {
					switch (n.getEventType()) {
					case Notification.REMOVE:
						ops.addFirst(() -> undoRemove(n, n.getNotifier(), false));
						collectOperations(n.getNotifier(), ops);
						return;
					case Notification.REMOVE_MANY:
						final EObject effLastElt = lastElement;
						ops.addFirst(() -> undoRemoveMany(n, n.getNotifier(), effLastElt, false));
						collectOperations(n.getNotifier(), ops);
						return;
					}
				}
			}
		}
	}

	public List<Notification> undoDeleteNode(EObject node) {
		NotificationLogger logger = new NotificationLogger();
		logger.attach();
		this.hookInResource(node);
		logger.detach();
		return logger.notifications;
	}

	public List<Notification> undoDeleteEdge(EMFEdge edge) {
		NotificationLogger logger = new NotificationLogger();
		logger.attach();

		for (Notification n : protocol.getRemovals(edge.getSource())) {
			if (edge.getType().equals(n.getFeature())) {
				undoOne(n, edge.getTarget(), false);
				logger.detach();
				return logger.notifications;
			}
		}
		for (Notification n : protocol.getChanges(edge.getSource())) {
			if (edge.getType().equals(n.getFeature())) {
				undoOne(n, edge.getTarget(), false);
				logger.detach();
				return logger.notifications;
			}
		}
		for (Notification n : protocol.getRemovals(edge.getTarget())) {
			if (edge.getType().equals(n.getFeature())) {
				undoOne(n, edge.getSource(), false);
				logger.detach();
				return logger.notifications;
			}
		}
		for (Notification n : protocol.getChanges(edge.getTarget())) {
			if (edge.getType().equals(n.getFeature())) {
				undoOne(n, edge.getSource(), false);
				logger.detach();
				return logger.notifications;
			}
		}

		logger.detach();
		return logger.notifications;
	}
	
	public List<Notification> deleteNode(EObject node) {
		NotificationLogger logger = new NotificationLogger();
		logger.attach();
		EcoreUtil.delete(node, true);
		logger.detach();
		return logger.notifications;
	}

}
