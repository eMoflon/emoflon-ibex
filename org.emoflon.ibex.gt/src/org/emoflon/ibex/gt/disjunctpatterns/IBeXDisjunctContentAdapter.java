package org.emoflon.ibex.gt.disjunctpatterns;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * class which remembers node updates for attribute updates
 *
 */
public class IBeXDisjunctContentAdapter extends EContentAdapter {
	
	private Set<Object> changedObjects;
	public IBeXDisjunctContentAdapter(ResourceSet rsSet) {
		
		changedObjects = new HashSet<Object>();		
		
		for(Resource r : rsSet.getResources()) {
			r.eAdapters().add(this);
			Notification notification = new ENotificationImpl(null, Notification.ADD, null, null, r);
			notifyChanged(notification);
		}
		
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		if(notification.getEventType() == Notification.SET) {
			changedObjects.add(notification.getNotifier());	
		}
	}
	
	public final Set<Object> getChangedObjects(){
		return changedObjects;
	}

	public void cleanUpdatedSets() {
		changedObjects.clear();
	}
}
