package org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

public class ModelChangeProtocol {

	private final Resource[] observedRes;

	private EContentAdapter adapter;

	private Map<ChangeKey, ModelChanges> groupedModelChanges;
	private Set<ChangeKey> currentKeys;

	public static class ChangeKey {
		public ChangeKey() {
		}
	}

	public ModelChangeProtocol(Resource... observedRes) {
		this.observedRes = observedRes;

		groupedModelChanges = new HashMap<>();
		currentKeys = new HashSet<>();

		adapter = new EContentAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void notifyChanged(Notification n) {
				if (!currentKeys.isEmpty()) {
					switch (n.getEventType()) {
					case Notification.ADD:
						processAddNotif(n.getNotifier(), (EReference) n.getFeature(), (EObject) n.getNewValue());
						break;
					case Notification.ADD_MANY:
						processAddManyNotif(n.getNotifier(), (EReference) n.getFeature(),
								(Collection<EObject>) n.getNewValue());
						break;
					case Notification.REMOVE:
						processRemoveNotif(n.getNotifier(), (EReference) n.getFeature(), (EObject) n.getOldValue());
						break;
					case Notification.REMOVE_MANY:
						processRemoveManyNotif(n.getNotifier(), (EReference) n.getFeature(),
								(Collection<EObject>) n.getOldValue());
						break;
					case Notification.SET:
						if (n.getNotifier() instanceof EObject)
							processSetNotif((EObject) n.getNotifier(), n.getFeature(), n.getOldValue(),
									n.getNewValue());
						break;
					}
				}

				super.notifyChanged(n);
			}
		};
	}

	public void attachAdapter() {
		for (int i = 0; i < observedRes.length; i++)
			observedRes[i].eAdapters().add(adapter);
	}

	public void detachAdapter() {
		for (int i = 0; i < observedRes.length; i++)
			observedRes[i].eAdapters().remove(adapter);
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
		} else if (notifier instanceof EObject) {
			changes.forEach(c -> c.addCreatedEdge(new EMFEdge((EObject) notifier, newValue, feature)));
			if (feature.isContainment())
				changes.forEach(c -> c.addCreatedElement(newValue));
		}
	}

	private void processAddManyNotif(Object notifier, EReference feature, Collection<EObject> newValues) {
		Set<ModelChanges> changes = getCurrentModelChanges();

		if (notifier instanceof Resource) {
			changes.forEach(c -> c.addAllCreatedElements(newValues));
		} else if (notifier instanceof EObject) {
			newValues.forEach(newValue -> changes
					.forEach(c -> c.addCreatedEdge(new EMFEdge((EObject) notifier, newValue, feature))));
			if (feature.isContainment())
				changes.forEach(c -> c.addAllCreatedElements(newValues));
		}
	}

	private void processRemoveNotif(Object notifier, EReference feature, EObject oldValue) {
		Set<ModelChanges> changes = getCurrentModelChanges();

		if (notifier instanceof Resource) {
			changes.forEach(c -> c.addDelEltContainedInRes(oldValue, (Resource) notifier));
		} else if (notifier instanceof EObject) {
			changes.forEach(c -> c.addDeletedEdge(new EMFEdge((EObject) notifier, oldValue, feature)));
			if (feature.isContainment())
				changes.forEach(c -> c.addDeletedElement(oldValue));
		}
	}

	private void processRemoveManyNotif(Object notifier, EReference feature, Collection<EObject> oldValues) {
		Set<ModelChanges> changes = getCurrentModelChanges();

		if (notifier instanceof Resource) {
			oldValues.forEach(
					oldValue -> changes.forEach(c -> c.addDelEltContainedInRes(oldValue, (Resource) notifier)));
		} else if (notifier instanceof EObject) {
			oldValues.forEach(oldValue -> changes
					.forEach(c -> c.addDeletedEdge(new EMFEdge((EObject) notifier, oldValue, feature))));
			if (feature.isContainment())
				changes.forEach(c -> c.addAllDeletedElements(oldValues));
		}
	}

	private void processSetNotif(EObject notifier, Object feature, Object oldValue, Object newValue) {
		Set<ModelChanges> changes = getCurrentModelChanges();

		if (feature instanceof EReference) {
			EReference reference = (EReference) feature;
			if (oldValue != null) {
				changes.forEach(c -> c.addDeletedEdge(new EMFEdge(notifier, (EObject) oldValue, reference)));
				if (reference.isContainment())
					changes.forEach(c -> c.addDeletedElement((EObject) oldValue));
			}
			if (newValue != null) {
				changes.forEach(c -> c.addCreatedEdge(new EMFEdge(notifier, (EObject) newValue, reference)));
				if (reference.isContainment())
					changes.forEach(c -> c.addCreatedElement((EObject) newValue));
			}
		} else if (feature instanceof EAttribute) {
			changes.forEach(
					c -> c.addAttributeChange(new AttributeChange(notifier, (EAttribute) feature, oldValue, newValue)));
		}
	}

}
