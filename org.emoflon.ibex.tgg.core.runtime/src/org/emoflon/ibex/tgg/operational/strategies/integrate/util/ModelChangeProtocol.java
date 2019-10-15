package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;

public class ModelChangeProtocol {
	
	private Map<EObject, List<Notification>> added;
	private Map<EObject, List<Notification>> removed;
	private Map<EObject, List<Notification>> changed;
	
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
					addToAdded((EObject) notification.getNotifier(), notification);					
					break;
				case Notification.REMOVE:
				case Notification.REMOVE_MANY:
					addToRemoved((EObject) notification.getNotifier(), notification);
					break;
				case Notification.SET:
					addToChanged((EObject) notification.getNotifier(), notification);
					break;
				}
				super.notifyChanged(notification);
			}
		};
	}
	
	public void attachProtocolAdapterTo(Resource resource) {
		resource.eAdapters().add(adapter);
	}

	public Map<EObject, List<Notification>> getAdded() {
		return added;
	}

	public Map<EObject, List<Notification>> getRemoved() {
		return removed;
	}

	public Map<EObject, List<Notification>> getChanged() {
		return changed;
	}

	public void addToAdded(EObject notifier, Notification notification) {
		added.computeIfAbsent(notifier, k -> new ArrayList<>());
		added.get(notifier).add(notification);
	}
	
	public void addToRemoved(EObject notifier, Notification notification) {
		removed.computeIfAbsent(notifier, k -> new ArrayList<>());
		removed.get(notifier).add(notification);
	}
	
	public void addToChanged(EObject notifier, Notification notification) {
		changed.computeIfAbsent(notifier, k -> new ArrayList<>());
		changed.get(notifier).add(notification);
	}

}
