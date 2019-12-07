package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.delta.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.integrate.delta.ModelChanges;

public class ModelChangeProtocol {

	private final Resource[] observedRes;
	public final ModelChangeUtil util;

	@Deprecated
	private Map<Object, List<Notification>> additions;
	@Deprecated
	private Map<Object, List<Notification>> removals;
	@Deprecated
	private Map<Object, List<Notification>> changes;

	@Deprecated
	private Map<Object, List<Notification>> reverseAdditions;
	@Deprecated
	private Map<Object, List<Notification>> reverseRemovals;
	@Deprecated
	private Map<Object, List<Notification>> reverseChanges;

	private EContentAdapter adapter;

	private Map<GroupKey, ModelChanges> groupedModelChanges;
	private GroupKey currentKey;

	public class GroupKey {
		public GroupKey() {
		}
	}

	public ModelChangeProtocol(Resource... observedRes) {
		this.observedRes = observedRes;
		this.util = new ModelChangeUtil(observedRes, this);

		groupedModelChanges = new HashMap<>();
		currentKey = null;
		
		adapter = new EContentAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void notifyChanged(Notification n) {
				
				oldNotifyChanged(n);

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
						processSetNotif((EObject) n.getNotifier(), n.getFeature(), n.getOldValue(), n.getNewValue());
						break;
					}
				}

				super.notifyChanged(n);
			}
		};

		oldInit();
	}

	@Deprecated
	protected void oldInit() {
		additions = new HashMap<>();
		removals = new HashMap<>();
		changes = new HashMap<>();

		reverseAdditions = new HashMap<>();
		reverseRemovals = new HashMap<>();
		reverseChanges = new HashMap<>();
	}

	@Deprecated
	protected void oldNotifyChanged(Notification n) {
		switch (n.getEventType()) {
		case Notification.ADD:
		case Notification.ADD_MANY:
			addToAdditions(n.getNotifier(), n);
			break;
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
			addToRemovals(n.getNotifier(), n);
			break;
		case Notification.SET:
			addToChanges(n.getNotifier(), n);
			break;
		}
	}

	public void attachAdapter() {
		for (int i = 0; i < observedRes.length; i++)
			observedRes[i].eAdapters().add(adapter);
	}

	public void detachAdapter() {
		for (int i = 0; i < observedRes.length; i++)
			observedRes[i].eAdapters().remove(adapter);
	}

	@Deprecated
	public Map<Object, List<Notification>> getAdditions() {
		return additions;
	}

	@Deprecated
	public Map<Object, List<Notification>> getRemovals() {
		return removals;
	}

	@Deprecated
	public Map<Object, List<Notification>> getChanges() {
		return changes;
	}

	@Deprecated
	public Map<Object, List<Notification>> getReverseAdditions() {
		return reverseAdditions;
	}

	@Deprecated
	public Map<Object, List<Notification>> getReverseRemovals() {
		return reverseRemovals;
	}

	@Deprecated
	public Map<Object, List<Notification>> getReverseChanges() {
		return reverseChanges;
	}

	@Deprecated
	public List<Notification> getAdditions(Object element) {
		return additions.getOrDefault(element, new ArrayList<>());
	}

	@Deprecated
	public List<Notification> getRemovals(Object element) {
		return removals.getOrDefault(element, new ArrayList<>());
	}

	@Deprecated
	public List<Notification> getChanges(Object element) {
		return changes.getOrDefault(element, new ArrayList<>());
	}

	@Deprecated
	public List<Notification> getReverseAdditions(Object element) {
		return reverseAdditions.getOrDefault(element, new ArrayList<>());
	}

	@Deprecated
	public List<Notification> getReverseRemovals(Object element) {
		return reverseRemovals.getOrDefault(element, new ArrayList<>());
	}

	@Deprecated
	public List<Notification> getReverseChanges(Object element) {
		return reverseChanges.getOrDefault(element, new ArrayList<>());
	}

	private void addToAdditions(Object notifier, Notification notification) {
		additions.computeIfAbsent(notifier, k -> new ArrayList<>());
		additions.get(notifier).add(notification);

		if (notification.getNewValue() instanceof EObject) {
			reverseAdditions.computeIfAbsent(notification.getNewValue(), k -> new ArrayList<>());
			reverseAdditions.get(notification.getNewValue()).add(notification);
		} else if (notification.getNewValue() instanceof Collection) {
			for (Object newValue : (Collection<?>) notification.getNewValue()) {
				reverseAdditions.computeIfAbsent(newValue, k -> new ArrayList<>());
				reverseAdditions.get(newValue).add(notification);
			}
		}
	}

	private void addToRemovals(Object notifier, Notification notification) {
		removals.computeIfAbsent(notifier, k -> new ArrayList<>());
		removals.get(notifier).add(notification);

		if (notification.getOldValue() instanceof EObject) {
			reverseRemovals.computeIfAbsent(notification.getOldValue(), k -> new ArrayList<>());
			reverseRemovals.get(notification.getOldValue()).add(notification);
		} else if (notification.getOldValue() instanceof Collection) {
			for (Object oldValue : (Collection<?>) notification.getOldValue()) {
				reverseRemovals.computeIfAbsent(oldValue, k -> new ArrayList<>());
				reverseRemovals.get(oldValue).add(notification);
			}
		}
	}

	private void addToChanges(Object notifier, Notification notification) {
		changes.computeIfAbsent(notifier, k -> new ArrayList<>());
		changes.get(notifier).add(notification);

		if (notification.getFeature() instanceof EReference) {
			if (notification.getOldValue() != null) {
				reverseChanges.computeIfAbsent(notification.getOldValue(), k -> new ArrayList<>());
				reverseChanges.get(notification.getOldValue()).add(notification);
			}
			if (notification.getNewValue() != null) {
				reverseChanges.computeIfAbsent(notification.getNewValue(), k -> new ArrayList<>());
				reverseChanges.get(notification.getNewValue()).add(notification);
			}
		}
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
		ModelChanges changes = getModelChanges(currentKey);

		if (notifier instanceof Resource) {
			changes.getCreatedElements().add(newValue);
		} else if (notifier instanceof EObject) {
			changes.getCreatedEdges().add(new EMFEdge((EObject) notifier, newValue, feature));
			if (feature.isContainment())
				changes.getCreatedElements().add(newValue);
		}
	}

	private void processAddManyNotif(Object notifier, EReference feature, Collection<EObject> newValues) {
		ModelChanges changes = getModelChanges(currentKey);

		if (notifier instanceof Resource) {
			changes.getCreatedElements().addAll(newValues);
		} else if (notifier instanceof EObject) {
			newValues.forEach(
					newValue -> changes.getCreatedEdges().add(new EMFEdge((EObject) notifier, newValue, feature)));
			if (feature.isContainment())
				changes.getCreatedElements().addAll(newValues);
		}
	}

	private void processRemoveNotif(Object notifier, EReference feature, EObject oldValue) {
		ModelChanges changes = getModelChanges(currentKey);

		if (notifier instanceof Resource) {
			changes.getDeletedElements().add(oldValue);
		} else if (notifier instanceof EObject) {
			changes.getDeletedEdges().add(new EMFEdge((EObject) notifier, oldValue, feature));
			if (feature.isContainment())
				changes.getDeletedElements().add(oldValue);
		}
	}

	private void processRemoveManyNotif(Object notifier, EReference feature, Collection<EObject> oldValues) {
		ModelChanges changes = getModelChanges(currentKey);

		if (notifier instanceof Resource) {
			changes.getDeletedElements().addAll(oldValues);
		} else if (notifier instanceof EObject) {
			oldValues.forEach(
					oldValue -> changes.getDeletedEdges().add(new EMFEdge((EObject) notifier, oldValue, feature)));
			if (feature.isContainment())
				changes.getDeletedElements().addAll(oldValues);
		}
	}

	private void processSetNotif(EObject notifier, Object feature, Object oldValue, Object newValue) {
		ModelChanges changes = getModelChanges(currentKey);

		if (feature instanceof EReference) {
			EReference reference = (EReference) feature;
			if (oldValue != null) {
				changes.getDeletedEdges().add(new EMFEdge(notifier, (EObject) oldValue, reference));
				if (reference.isContainment())
					changes.getDeletedElements().add((EObject) oldValue);
			}
			if (newValue != null) {
				changes.getCreatedEdges().add(new EMFEdge(notifier, (EObject) newValue, reference));
				if (reference.isContainment())
					changes.getCreatedElements().add((EObject) newValue);
			}
		} else if (feature instanceof EAttribute) {
			changes.getAttributeChanges().add(new AttributeChange(notifier, (EAttribute) feature, oldValue, newValue));
		}
	}

}
