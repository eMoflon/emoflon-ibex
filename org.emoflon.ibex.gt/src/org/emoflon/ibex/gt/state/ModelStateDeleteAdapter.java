package org.emoflon.ibex.gt.state;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.emoflon.ibex.gt.StateModel.Link;
import org.emoflon.ibex.gt.StateModel.StateModelFactory;

public class ModelStateDeleteAdapter extends EContentAdapter {
	
	private StateModelFactory factory = StateModelFactory.eINSTANCE;
	private final Resource resource;
	private Map<EObject, Set<Integer>> nodesToWatch = new LinkedHashMap<>();
	private Map<Integer, Set<EObject>> watcherToNodes = new LinkedHashMap<>();
	private Map<Integer, Set<Link>> removedLinks = new LinkedHashMap<>();
	private boolean active = false;
	
	public ModelStateDeleteAdapter(final Resource resource) {
		this.resource = resource;
		this.resource.eAdapters().add(this);
		nodesToWatch = new LinkedHashMap<>();
	}
	
	public synchronized void removeAdapter() {
		resource.eAdapters().remove(this);
		nodesToWatch.clear();
		watcherToNodes.clear();
		removedLinks.clear();
	}
	
	public synchronized void addNodesToWatch(final Set<EObject> nodesToWatch, int watcherID) {
		Set<EObject> watchedNodes = watcherToNodes.get(watcherID);
		if(watchedNodes == null) {
			watchedNodes = new LinkedHashSet<>();
			watcherToNodes.put(watcherID, watchedNodes);
		}
		watchedNodes.addAll(nodesToWatch);
		
		for(EObject node : nodesToWatch) {
			Set<Integer> watchers = this.nodesToWatch.get(node);
			if(watchers == null) {
				watchers = new LinkedHashSet<>();
				this.nodesToWatch.put(node, watchers);
			}
			watchers.add(watcherID);
		}
	}
	
	public synchronized void clear(int watcherID) {
		Set<EObject> nodes = watcherToNodes.get(watcherID);
		if(nodes == null)
			return;
		
		for(EObject node : nodes) {
			if(!nodesToWatch.containsKey(node))
				continue;
			
			nodesToWatch.get(node).remove(watcherID);
			if(nodesToWatch.get(node).isEmpty())
				nodesToWatch.remove(node);
		}
		
		watcherToNodes.remove(watcherID);
		removedLinks.remove(watcherID);
	}
	
	public Set<Link> getRemovedLinks(int watcherID) {
		Set<Link> removed = removedLinks.get(watcherID);
		if(removed != null)
			return removed;
		else
			return new LinkedHashSet<>(); 
	}
	
	public synchronized void setActive() {
		active = true;
	}
	
	public synchronized void setInactive() {
		active = false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void notifyChanged(Notification notification) {	
		super.notifyChanged(notification);
		if(!active)
			return;
		
		switch (notification.getEventType()) {
			case Notification.REMOVE -> {
				Object feature = notification.getFeature();
				if(feature == null)
					break;
				
//				Ignore resource notifications, since these are untyped/undefined containment edges.
				if(!(notification.getNotifier() instanceof EObject))
					break;
				
				if(nodesToWatch.keySet().contains(notification.getNotifier()) || nodesToWatch.keySet().contains(notification.getOldValue())) {
					Link link = factory.createLink();
					link.setSrc((EObject) notification.getNotifier());
					link.setTrg((EObject) notification.getOldValue());
					link.setType((EReference) feature);
					Set<Integer> watchers  = nodesToWatch.get(notification.getNotifier());
					if(watchers == null)
						watchers = nodesToWatch.get(notification.getOldValue());
					
					for(Integer watcher : watchers) {
						Set<Link> watchedLinks = removedLinks.get(watcher);
						if(watchedLinks == null) {
							watchedLinks = new LinkedHashSet<>();
							removedLinks.put(watcher, watchedLinks);
						}
						watchedLinks.add(link);
					}
				}
			}
			case Notification.REMOVE_MANY -> {
				Object feature = notification.getFeature();
				if(feature == null)
					break;
				
//				Ignore resource notifications, since these are untyped/undefined containment edges.
				if(!(notification.getNotifier() instanceof EObject))
					break;
				
				List<EObject> oldValues = (List<EObject>) notification.getOldValue();
				for(EObject oldValue : oldValues) {
					if(nodesToWatch.keySet().contains(notification.getNotifier()) || nodesToWatch.keySet().contains(oldValue)) {
						Link link = factory.createLink();
						link.setSrc((EObject) notification.getNotifier());
						link.setTrg(oldValue);
						link.setType((EReference) feature);
						Set<Integer> watchers  = nodesToWatch.get(notification.getNotifier());
						if(watchers == null)
							watchers = nodesToWatch.get(oldValue);
						
						for(Integer watcher : watchers) {
							Set<Link> watchedLinks = removedLinks.get(watcher);
							if(watchedLinks == null) {
								watchedLinks = new LinkedHashSet<>();
								removedLinks.put(watcher, watchedLinks);
							}
							watchedLinks.add(link);
						}
					}
				}
			}
			case Notification.REMOVING_ADAPTER -> {
				Object feature = notification.getFeature();
				if(feature == null || !(feature instanceof EReference))
					break;
//				Ignore resource notifications, since these are untyped/undefined containment edges.
				if(!(notification.getNotifier() instanceof EObject))
					break;
				
				if(nodesToWatch.keySet().contains(notification.getNotifier()) || nodesToWatch.keySet().contains(notification.getOldValue())) {
					Link link = factory.createLink();
					link.setSrc((EObject) notification.getNotifier());
					link.setTrg((EObject) notification.getOldValue());
					link.setType((EReference) feature);
					Set<Integer> watchers  = nodesToWatch.get(notification.getNotifier());
					if(watchers == null)
						watchers = nodesToWatch.get(notification.getOldValue());
					
					for(Integer watcher : watchers) {
						Set<Link> watchedLinks = removedLinks.get(watcher);
						if(watchedLinks == null) {
							watchedLinks = new LinkedHashSet<>();
							removedLinks.put(watcher, watchedLinks);
						}
						watchedLinks.add(link);
					}
				}
			}
			case Notification.SET -> {
				Object feature = notification.getFeature();
				if(feature == null || !(feature instanceof EReference))
					break;
				
				if(nodesToWatch.keySet().contains(notification.getNotifier()) || nodesToWatch.keySet().contains(notification.getOldValue())) {
					Link link = factory.createLink();
					link.setSrc((EObject) notification.getNotifier());
					link.setTrg((EObject) notification.getOldValue());
					link.setType((EReference) feature);
					Set<Integer> watchers  = nodesToWatch.get(notification.getNotifier());
					for(Integer watcher : watchers) {
						Set<Link> watchedLinks = removedLinks.get(watcher);
						if(watchedLinks == null) {
							watchedLinks = new LinkedHashSet<>();
							removedLinks.put(watcher, watchedLinks);
						}
						watchedLinks.add(link);
					}
				}
			}
		}
	
	}
	
	
}
