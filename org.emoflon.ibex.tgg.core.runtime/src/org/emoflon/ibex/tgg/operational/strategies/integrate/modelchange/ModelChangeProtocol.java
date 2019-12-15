package org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.emoflon.ibex.common.emf.EMFEdge;

public class ModelChangeProtocol {

	private final Resource[] observedRes;
	public final ModelChangeUtil util;

	private EContentAdapter adapter;

	private Map<GroupKey, ModelChanges> groupedModelChanges;
	private GroupKey currentKey;

	public class GroupKey {
		public GroupKey() {
		}
	}

	public ModelChangeProtocol(Resource... observedRes) {
		this.observedRes = observedRes;
		this.util = new ModelChangeUtil(this);

		groupedModelChanges = new HashMap<>();
		currentKey = null;

		adapter = new EContentAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void notifyChanged(Notification n) {
				if (currentKey != null) {
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

	public void setGroupKey(GroupKey key) {
		currentKey = key;
		groupedModelChanges.computeIfAbsent(currentKey, k -> new ModelChanges());
	}

	public GroupKey getCurrentGroupKey() {
		return currentKey;
	}

	public void unsetGroupKey() {
		currentKey = null;
	}

	public ModelChanges getModelChanges(GroupKey key) {
		return groupedModelChanges.get(key);
	}

	public ModelChanges getCurrentModelChanges() {
		return groupedModelChanges.get(currentKey);
	}

	private void processAddNotif(Object notifier, EReference feature, EObject newValue) {
		ModelChanges changes = getCurrentModelChanges();

		if (notifier instanceof Resource) {
			changes.addCreatedElement(newValue);
		} else if (notifier instanceof EObject) {
			changes.addCreatedEdge(new EMFEdge((EObject) notifier, newValue, feature));
			if (feature.isContainment())
				changes.addCreatedElement(newValue);
		}
	}

	private void processAddManyNotif(Object notifier, EReference feature, Collection<EObject> newValues) {
		ModelChanges changes = getCurrentModelChanges();

		if (notifier instanceof Resource) {
			changes.addAllCreatedElements(newValues);
		} else if (notifier instanceof EObject) {
			newValues.forEach(newValue -> changes.addCreatedEdge(new EMFEdge((EObject) notifier, newValue, feature)));
			if (feature.isContainment())
				changes.addAllCreatedElements(newValues);
		}
	}

	private void processRemoveNotif(Object notifier, EReference feature, EObject oldValue) {
		ModelChanges changes = getCurrentModelChanges();

		if (notifier instanceof Resource) {
			changes.addDelEltContainedInRes(oldValue, (Resource) notifier);
		} else if (notifier instanceof EObject) {
			changes.addDeletedEdge(new EMFEdge((EObject) notifier, oldValue, feature));
			if (feature.isContainment())
				changes.addDeletedElement(oldValue);
		}
	}

	private void processRemoveManyNotif(Object notifier, EReference feature, Collection<EObject> oldValues) {
		ModelChanges changes = getCurrentModelChanges();

		if (notifier instanceof Resource) {
			oldValues.forEach(oldValue -> changes.addDelEltContainedInRes(oldValue, (Resource) notifier));
		} else if (notifier instanceof EObject) {
			oldValues.forEach(oldValue -> changes.addDeletedEdge(new EMFEdge((EObject) notifier, oldValue, feature)));
			if (feature.isContainment())
				changes.addAllDeletedElements(oldValues);
		}
	}

	private void processSetNotif(EObject notifier, Object feature, Object oldValue, Object newValue) {
		ModelChanges changes = getCurrentModelChanges();

		if (feature instanceof EReference) {
			EReference reference = (EReference) feature;
			if (oldValue != null) {
				changes.addDeletedEdge(new EMFEdge(notifier, (EObject) oldValue, reference));
				if (reference.isContainment())
					changes.addDeletedElement((EObject) oldValue);
			}
			if (newValue != null) {
				changes.addCreatedEdge(new EMFEdge(notifier, (EObject) newValue, reference));
				if (reference.isContainment())
					changes.addCreatedElement((EObject) newValue);
			}
		} else if (feature instanceof EAttribute) {
			changes.addAttributeChange(new AttributeChange(notifier, (EAttribute) feature, oldValue, newValue));
		}
	}

}
