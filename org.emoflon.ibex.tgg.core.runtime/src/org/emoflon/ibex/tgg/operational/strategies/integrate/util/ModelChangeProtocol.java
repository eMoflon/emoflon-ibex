package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;

public class ModelChangeProtocol {

	private Map<Object, List<Notification>> added;
	private Map<Object, List<Notification>> removed;
	private Map<Object, List<Notification>> changed;

	private EContentAdapter adapter;

	public ModelChangeProtocol() {
		added = new HashMap<>();
		removed = new HashMap<>();
		changed = new HashMap<>();

		adapter = new EContentAdapter() {
			@Override
			public void notifyChanged(Notification notification) {
				switch (notification.getEventType()) {
				case Notification.ADD:
				case Notification.ADD_MANY:
					addToAdded(notification.getNotifier(), notification);
					break;
				case Notification.REMOVE:
				case Notification.REMOVE_MANY:
					addToRemoved(notification.getNotifier(), notification);
					break;
				case Notification.SET:
					addToChanged(notification.getNotifier(), notification);
					break;
				}
				super.notifyChanged(notification);
			}
		};
	}

	public void attachAdapterTo(Resource... resources) {
		for (int i = 0; i < resources.length; i++)
			resources[i].eAdapters().add(adapter);
	}

	public void detachAdapterFrom(Resource... resources) {
		for (int i = 0; i < resources.length; i++)
			resources[i].eAdapters().remove(adapter);
	}

	public Map<Object, List<Notification>> getAdded() {
		return added;
	}

	public Map<Object, List<Notification>> getRemoved() {
		return removed;
	}

	public Map<Object, List<Notification>> getChanged() {
		return changed;
	}

	public void addToAdded(Object notifier, Notification notification) {
		added.computeIfAbsent(notifier, k -> new ArrayList<>());
		added.get(notifier).add(notification);
	}

	public void addToRemoved(Object notifier, Notification notification) {
		removed.computeIfAbsent(notifier, k -> new ArrayList<>());
		removed.get(notifier).add(notification);
	}

	public void addToChanged(Object notifier, Notification notification) {
		changed.computeIfAbsent(notifier, k -> new ArrayList<>());
		changed.get(notifier).add(notification);
	}

	public void undo(Notification notification) {
		Object notifier = notification.getNotifier();
		switch (notification.getEventType()) {
		case Notification.ADD:
			undoAdd(notification, notifier);
			break;
		case Notification.ADD_MANY:
			undoAddMany(notification, notifier);
			break;
		case Notification.REMOVE:
			undoRemove(notification, notifier);
			break;
		case Notification.REMOVE_MANY:
			undoRemoveMany(notification, notifier);
			break;
		case Notification.SET:
			undoSet(notification, notifier);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void undoAdd(Notification notification, Object notifier) {
		if (notifier instanceof EObject) {
			EObject eObjNotifier = (EObject) notifier;
			EList<EObject> eList = (EList<EObject>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
			eList.remove(notification.getNewValue());
		} else if (notifier instanceof Resource) {
			Resource resNotifier = (Resource) notifier;
			resNotifier.getContents().remove(notification.getNewValue());
		}
	}

	@SuppressWarnings("unchecked")
	private void undoAddMany(Notification notification, Object notifier) {
		if (notifier instanceof EObject) {
			EObject eObjNotifier = (EObject) notifier;
			EList<EObject> eList = (EList<EObject>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
			eList.removeAll((List<EObject>) notification.getNewValue());
		} else if (notifier instanceof Resource) {
			Resource resNotifier = (Resource) notifier;
			resNotifier.getContents().removeAll((List<EObject>) notification.getNewValue());
		}
	}

	@SuppressWarnings("unchecked")
	private void undoRemove(Notification notification, Object notifier) {
		if (notifier instanceof EObject) {
			EObject eObjNotifier = (EObject) notifier;
			EList<EObject> eList = (EList<EObject>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
			eList.add(notification.getPosition(), (EObject) notification.getOldValue());
		} else if (notifier instanceof Resource) {
			Resource resNotifier = (Resource) notifier;
			resNotifier.getContents().add(notification.getPosition(), (EObject) notification.getOldValue());
		}
	}

	@SuppressWarnings("unchecked")
	private void undoRemoveMany(Notification notification, Object notifier) {
		if (notifier instanceof EObject) {
			EObject eObjNotifier = (EObject) notifier;
			EList<EObject> eList = (EList<EObject>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
			eList.addAll((List<EObject>) notification.getOldValue());
		} else if (notifier instanceof Resource) {
			Resource resNotifier = (Resource) notifier;
			resNotifier.getContents().addAll((List<EObject>) notification.getOldValue());
		}
	}

	private void undoSet(Notification notification, Object notifier) {
		EObject eObjNotifier = (EObject) notifier;
		eObjNotifier.eSet((EStructuralFeature) notification.getFeature(), notification.getOldValue());
	}

	public void redo(Notification notification) {
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
	private void redoAdd(Notification notification, Object notifier) {
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
	private void redoAddMany(Notification notification, Object notifier) {
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
	private void redoRemove(Notification notification, Object notifier) {
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
	private void redoRemoveMany(Notification notification, Object notifier) {
		if (notifier instanceof EObject) {
			EObject eObjNotifier = (EObject) notifier;
			EList<EObject> eList = (EList<EObject>) eObjNotifier.eGet((EStructuralFeature) notification.getFeature());
			eList.removeAll((List<EObject>) notification.getOldValue());
		} else if (notifier instanceof Resource) {
			Resource resNotifier = (Resource) notifier;
			resNotifier.getContents().removeAll((List<EObject>) notification.getOldValue());
		}
	}

	private void redoSet(Notification notification, Object notifier) {
		EObject eObjNotifier = (EObject) notifier;
		eObjNotifier.eSet((EStructuralFeature) notification.getFeature(), notification.getNewValue());
	}

}
