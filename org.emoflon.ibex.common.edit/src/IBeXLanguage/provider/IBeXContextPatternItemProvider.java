/**
 */
package IBeXLanguage.provider;

import IBeXLanguage.IBeXContextPattern;
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
 * This is the item provider adapter for a {@link IBeXLanguage.IBeXContextPattern} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXContextPatternItemProvider extends IBeXContextItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXContextPatternItemProvider(AdapterFactory adapterFactory) {
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
			childrenFeatures.add(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT);
			childrenFeatures.add(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS);
			childrenFeatures.add(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__INVOCATIONS);
			childrenFeatures.add(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__LOCAL_EDGES);
			childrenFeatures.add(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__LOCAL_NODES);
			childrenFeatures.add(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES);
			childrenFeatures.add(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__CSPS);
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
	 * This returns IBeXContextPattern.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/IBeXContextPattern"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((IBeXContextPattern) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_IBeXContextPattern_type")
				: getString("_UI_IBeXContextPattern_type") + " " + label;
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

		switch (notification.getFeatureID(IBeXContextPattern.class)) {
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT:
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_EDGES:
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_NODES:
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES:
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__CSPS:
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
				.add(createChildParameter(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT,
						IBeXLanguageFactory.eINSTANCE.createIBeXAttributeConstraint()));

		newChildDescriptors
				.add(createChildParameter(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS,
						IBeXLanguageFactory.eINSTANCE.createIBeXNodePair()));

		newChildDescriptors.add(createChildParameter(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__INVOCATIONS,
				IBeXLanguageFactory.eINSTANCE.createIBeXPatternInvocation()));

		newChildDescriptors.add(createChildParameter(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__LOCAL_EDGES,
				IBeXLanguageFactory.eINSTANCE.createIBeXEdge()));

		newChildDescriptors.add(createChildParameter(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__LOCAL_NODES,
				IBeXLanguageFactory.eINSTANCE.createIBeXNode()));

		newChildDescriptors.add(createChildParameter(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES,
				IBeXLanguageFactory.eINSTANCE.createIBeXNode()));

		newChildDescriptors.add(createChildParameter(IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__CSPS,
				IBeXLanguageFactory.eINSTANCE.createIBeXCSP()));
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

		boolean qualify = childFeature == IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__LOCAL_NODES
				|| childFeature == IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES;

		if (qualify) {
			return getString("_UI_CreateChild_text2",
					new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
