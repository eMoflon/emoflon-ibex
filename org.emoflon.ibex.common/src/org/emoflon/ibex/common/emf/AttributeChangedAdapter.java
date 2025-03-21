package org.emoflon.ibex.common.emf;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;

public class AttributeChangedAdapter extends EContentAdapter {
	protected final Set<Resource> resources = Collections.synchronizedSet(new LinkedHashSet<>());
	protected final Set<EObject> nodesToWatch = Collections.synchronizedSet(new LinkedHashSet<>());
	protected final Set<EAttribute> featuresToWatch = Collections.synchronizedSet(new LinkedHashSet<>());
	protected final Set<Consumer<Notification>> notificationConsumers = Collections
			.synchronizedSet(new LinkedHashSet<>());

	public AttributeChangedAdapter(final Resource... resources) {
		for (Resource resource : resources) {
			this.resources.add(resource);
			resource.eAdapters().add(this);
		}
	}

	public AttributeChangedAdapter(final Collection<Resource> resources) {
		for (Resource resource : resources) {
			this.resources.add(resource);
			resource.eAdapters().add(this);
		}
	}

	public synchronized void removeAdapter() {
		for (Resource resource : resources) {
			resource.eAdapters().remove(this);
		}
		resources.clear();
		nodesToWatch.clear();
		featuresToWatch.clear();
		notificationConsumers.clear();
	}

	public void addAttributeToWatch(final EAttribute attribute) {
		featuresToWatch.add(attribute);
	}

	public void addNodeToWatch(final EObject node) {
		nodesToWatch.add(node);
	}

	public void removeNodeToWatch(final EObject node) {
		nodesToWatch.remove(node);
	}

	public void addNotificationConsumer(final Consumer<Notification> consumer) {
		notificationConsumers.add(consumer);
	}

	public void removeNotificationConsumer(final Consumer<Notification> consumer) {
		notificationConsumers.remove(consumer);
	}

	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);

		if (notification.getFeature() == null || !(notification.getFeature() instanceof EAttribute))
			return;

		EAttribute feature = (EAttribute) notification.getFeature();
		if (!featuresToWatch.contains(feature))
			return;

		if (!nodesToWatch.contains(notification.getNotifier()))
			return;

		switch (notification.getEventType()) {
		case Notification.SET -> {
			for (Consumer<Notification> consumer : notificationConsumers) {
				consumer.accept(notification);
			}
		}
		}
	}
}
