/**
 */
package IBeXLanguage.provider;

import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXLanguagePackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link IBeXLanguage.IBeXCreatePattern} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXCreatePatternItemProvider extends IBeXPatternItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXCreatePatternItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(IBeXLanguagePackage.Literals.IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS);
			childrenFeatures.add(IBeXLanguagePackage.Literals.IBE_XCREATE_PATTERN__CONTEXT_NODES);
			childrenFeatures.add(IBeXLanguagePackage.Literals.IBE_XCREATE_PATTERN__CREATED_EDGES);
			childrenFeatures.add(IBeXLanguagePackage.Literals.IBE_XCREATE_PATTERN__CREATED_NODES);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns IBeXCreatePattern.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/IBeXCreatePattern"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((IBeXCreatePattern) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_IBeXCreatePattern_type")
				: getString("_UI_IBeXCreatePattern_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(IBeXCreatePattern.class)) {
		case IBeXLanguagePackage.IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS:
		case IBeXLanguagePackage.IBE_XCREATE_PATTERN__CONTEXT_NODES:
		case IBeXLanguagePackage.IBE_XCREATE_PATTERN__CREATED_EDGES:
		case IBeXLanguagePackage.IBE_XCREATE_PATTERN__CREATED_NODES:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors
				.add(createChildParameter(IBeXLanguagePackage.Literals.IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS,
						IBeXLanguageFactory.eINSTANCE.createIBeXAttributeAssignment()));

		newChildDescriptors.add(createChildParameter(IBeXLanguagePackage.Literals.IBE_XCREATE_PATTERN__CONTEXT_NODES,
				IBeXLanguageFactory.eINSTANCE.createIBeXNode()));

		newChildDescriptors.add(createChildParameter(IBeXLanguagePackage.Literals.IBE_XCREATE_PATTERN__CREATED_EDGES,
				IBeXLanguageFactory.eINSTANCE.createIBeXEdge()));

		newChildDescriptors.add(createChildParameter(IBeXLanguagePackage.Literals.IBE_XCREATE_PATTERN__CREATED_NODES,
				IBeXLanguageFactory.eINSTANCE.createIBeXNode()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify = childFeature == IBeXLanguagePackage.Literals.IBE_XCREATE_PATTERN__CONTEXT_NODES
				|| childFeature == IBeXLanguagePackage.Literals.IBE_XCREATE_PATTERN__CREATED_NODES;

		if (qualify) {
			return getString("_UI_CreateChild_text2",
					new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
