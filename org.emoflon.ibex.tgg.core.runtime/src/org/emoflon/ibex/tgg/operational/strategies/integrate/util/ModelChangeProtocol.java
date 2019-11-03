package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;

public class ModelChangeProtocol {
	
	private final Resource[] observedRes;
	public final ModelChangeUtil util;

	private Map<Object, List<Notification>> additions;
	private Map<Object, List<Notification>> removals;
	private Map<Object, List<Notification>> changes;

	private Map<Object, List<Notification>> reverseAdditions;
	private Map<Object, List<Notification>> reverseRemovals;
	private Map<Object, List<Notification>> reverseChanges;

	private EContentAdapter adapter;

	public ModelChangeProtocol(Resource... observedRes) {
		this.observedRes = observedRes;
		this.util = new ModelChangeUtil(observedRes, this);
		
		additions = new HashMap<>();
		removals = new HashMap<>();
		changes = new HashMap<>();

		reverseAdditions = new HashMap<>();
		reverseRemovals = new HashMap<>();
		reverseChanges = new HashMap<>();

		adapter = new EContentAdapter() {
			@Override
			public void notifyChanged(Notification notification) {
				switch (notification.getEventType()) {
				case Notification.ADD:
				case Notification.ADD_MANY:
					addToAdditions(notification.getNotifier(), notification);
					break;
				case Notification.REMOVE:
				case Notification.REMOVE_MANY:
					addToRemovals(notification.getNotifier(), notification);
					break;
				case Notification.SET:
					addToChanges(notification.getNotifier(), notification);
					break;
				}
				super.notifyChanged(notification);
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

	public Map<Object, List<Notification>> getAdditions() {
		return additions;
	}

	public Map<Object, List<Notification>> getRemovals() {
		return removals;
	}

	public Map<Object, List<Notification>> getChanges() {
		return changes;
	}

	public Map<Object, List<Notification>> getReverseAdditions() {
		return reverseAdditions;
	}

	public Map<Object, List<Notification>> getReverseRemovals() {
		return reverseRemovals;
	}

	public Map<Object, List<Notification>> getReverseChanges() {
		return reverseChanges;
	}

	public List<Notification> getAdditions(Object element) {
		return additions.getOrDefault(element, new ArrayList<>());
	}

	public List<Notification> getRemovals(Object element) {
		return removals.getOrDefault(element, new ArrayList<>());
	}

	public List<Notification> getChanges(Object element) {
		return changes.getOrDefault(element, new ArrayList<>());
	}

	public List<Notification> getReverseAdditions(Object element) {
		return reverseAdditions.getOrDefault(element, new ArrayList<>());
	}

	public List<Notification> getReverseRemovals(Object element) {
		return reverseRemovals.getOrDefault(element, new ArrayList<>());
	}

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

}
