package org.emoflon.ibex.tgg.runtime.strategies.integrate.modelchange;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.smartemf.runtime.notification.SmartContentAdapter;

public class ModelChangeProtocol {

	private final List<Resource> observedRes;

	private EContentAdapter adapter;
	private boolean attached;

	private Map<ChangeKey, ModelChanges> groupedModelChanges;
	private Set<ChangeKey> currentKeys;

	public static class ChangeKey {
		public ChangeKey() {
		}
	}

	public ModelChangeProtocol(Resource... observedRes) {
		this.observedRes = Arrays.asList(observedRes);

		groupedModelChanges = new HashMap<>();
		currentKeys = new HashSet<>();

		adapter = new SmartContentAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void notifyChanged(Notification n) {
				if (!currentKeys.isEmpty()) {
					switch (n.getEventType()) {
						case Notification.ADD -> {
							processAddNotif(n.getNotifier(), (EReference) n.getFeature(), (EObject) n.getNewValue());
						}
						case Notification.ADD_MANY -> {
							processAddManyNotif(n.getNotifier(), (EReference) n.getFeature(), (Collection<EObject>) n.getNewValue());
						}
						case Notification.REMOVE -> {
							processRemoveNotif(n.getNotifier(), (EReference) n.getFeature(), (EObject) n.getOldValue());
						}
						case Notification.REMOVE_MANY -> {
							processRemoveManyNotif(n.getNotifier(), (EReference) n.getFeature(), (Collection<EObject>) n.getOldValue());
						}
						case Notification.SET -> {
							if (n.getNotifier() instanceof EObject eObjNotifier)
								processSetNotif(eObjNotifier, n.getFeature(), n.getOldValue(), n.getNewValue());
						}
						case Notification.REMOVING_ADAPTER -> {
							if (n.getNotifier() instanceof EObject eObjNotifier)
								processRemovingAdapterNotif(eObjNotifier);
						}
					}
				}

				super.notifyChanged(n);
			}
		};
		attached = false;
	}

	/**
	 * Clears all recorded model changes, but keeps registered keys.
	 */
	public void clearAll() {
		for (ModelChanges modelChanges : groupedModelChanges.values())
			modelChanges.clearAll();
	}

	public void attachAdapter() {
		if (attached)
			return;

		for (Resource res : observedRes)
			res.eAdapters().add(adapter);

		attached = true;
	}

	public void detachAdapter() {
		if (!attached)
			return;

		for (Resource res : observedRes)
			res.eAdapters().remove(adapter);

		attached = false;
	}

	public void registerKey(ChangeKey key) {
		currentKeys.add(key);
		groupedModelChanges.computeIfAbsent(key, k -> new ModelChanges());
	}

	public void deregisterKey(ChangeKey key) {
		currentKeys.remove(key);
	}

	public ModelChanges getModelChanges(ChangeKey key) {
		return groupedModelChanges.get(key);
	}

	public Set<ModelChanges> getCurrentModelChanges() {
		return currentKeys.stream() //
				.map(k -> groupedModelChanges.get(k)) //
				.collect(Collectors.toSet());
	}

	private void processAddNotif(Object notifier, EReference feature, EObject newValue) {
		Set<ModelChanges> changes = getCurrentModelChanges();

		if (notifier instanceof Resource) {
			changes.forEach(c -> c.addCreatedElement(newValue));
		} else if (notifier instanceof EObject eObjNotifier) {
			changes.forEach(c -> c.addCreatedEdge(new EMFEdge(eObjNotifier, newValue, feature)));
			if (feature.isContainment()) {
				changes.forEach(c -> c.addCreatedElement(newValue));
				if (feature.getEOpposite() != null)
					changes.forEach(c -> c.addCreatedEdge(new EMFEdge(newValue, eObjNotifier, feature.getEOpposite())));
			}
		}
	}

	private void processAddManyNotif(Object notifier, EReference feature, Collection<EObject> newValues) {
		Set<ModelChanges> changes = getCurrentModelChanges();

		if (notifier instanceof Resource) {
			changes.forEach(c -> c.addAllCreatedElements(newValues));
		} else if (notifier instanceof EObject eObjNotifier) {
			newValues.forEach(newValue -> changes.forEach(c -> c.addCreatedEdge(new EMFEdge(eObjNotifier, newValue, feature))));
			if (feature.isContainment())
				changes.forEach(c -> c.addAllCreatedElements(newValues));
		}
	}

	private void processRemoveNotif(Object notifier, EReference feature, EObject oldValue) {
		Set<ModelChanges> changes = getCurrentModelChanges();

		if (notifier instanceof Resource)
			changes.forEach(c -> c.addDeletedEltContainedInRes(oldValue, (Resource) notifier));
		else if (notifier instanceof EObject eObjNotifier)
			changes.forEach(c -> c.addDeletedEdge(new EMFEdge(eObjNotifier, oldValue, feature)));
	}

	private void processRemoveManyNotif(Object notifier, EReference feature, Collection<EObject> oldValues) {
		Set<ModelChanges> changes = getCurrentModelChanges();

		if (notifier instanceof Resource)
			oldValues.forEach(oldValue -> changes.forEach(c -> c.addDeletedEltContainedInRes(oldValue, (Resource) notifier)));
		else if (notifier instanceof EObject eObjNotifier)
			oldValues.forEach(oldValue -> changes.forEach(c -> c.addDeletedEdge(new EMFEdge(eObjNotifier, oldValue, feature))));
	}

	private void processSetNotif(EObject notifier, Object feature, Object oldValue, Object newValue) {
		Set<ModelChanges> changes = getCurrentModelChanges();

		if (feature instanceof EReference reference) {
			if (oldValue != null) {
				changes.forEach(c -> c.addDeletedEdge(new EMFEdge(notifier, (EObject) oldValue, reference)));
			}
			if (newValue != null) {
				changes.forEach(c -> c.addCreatedEdge(new EMFEdge(notifier, (EObject) newValue, reference)));
				if (reference.isContainment())
					changes.forEach(c -> c.addCreatedElement((EObject) newValue));
			}
		} else if (feature instanceof EAttribute attribute) {
			changes.forEach(c -> c.addAttributeChange(new AttributeChange(notifier, attribute, oldValue, newValue)));
		}
	}

	private void processRemovingAdapterNotif(EObject notifier) {
		Set<ModelChanges> changes = getCurrentModelChanges();

		changes.forEach(c -> c.addDeletedElement(notifier));

	}

}
