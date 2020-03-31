/**
 */
package StochasticLanguage.provider;

import IBeXLanguage.provider.CommonEditPlugin;

import StochasticLanguage.GTStochasticDistribution;
import StochasticLanguage.GTStochasticFunction;
import StochasticLanguage.StochasticLanguageFactory;
import StochasticLanguage.StochasticLanguagePackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link StochasticLanguage.GTStochasticFunction} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class GTStochasticFunctionItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTStochasticFunctionItemProvider(AdapterFactory adapterFactory) {
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

			addDistributionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Distribution feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDistributionPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_GTStochasticFunction_distribution_feature"),
						getString("_UI_PropertyDescriptor_description", "_UI_GTStochasticFunction_distribution_feature",
								"_UI_GTStochasticFunction_type"),
						StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__DISTRIBUTION, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
			childrenFeatures.add(StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__MEAN);
			childrenFeatures.add(StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__SD);
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
	 * This returns GTStochasticFunction.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/GTStochasticFunction"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		GTStochasticDistribution labelValue = ((GTStochasticFunction) object).getDistribution();
		String label = labelValue == null ? null : labelValue.toString();
		return label == null || label.length() == 0 ? getString("_UI_GTStochasticFunction_type")
				: getString("_UI_GTStochasticFunction_type") + " " + label;
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

		switch (notification.getFeatureID(GTStochasticFunction.class)) {
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__DISTRIBUTION:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__MEAN:
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__SD:
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

		newChildDescriptors.add(createChildParameter(StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__MEAN,
				StochasticLanguageFactory.eINSTANCE.createGTAttribute()));

		newChildDescriptors.add(createChildParameter(StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__MEAN,
				StochasticLanguageFactory.eINSTANCE.createGTNumber()));

		newChildDescriptors.add(createChildParameter(StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__MEAN,
				StochasticLanguageFactory.eINSTANCE.createGTTwoParameterCalculation()));

		newChildDescriptors.add(createChildParameter(StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__MEAN,
				StochasticLanguageFactory.eINSTANCE.createGTOneParameterCalculation()));

		newChildDescriptors.add(createChildParameter(StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__SD,
				StochasticLanguageFactory.eINSTANCE.createGTAttribute()));

		newChildDescriptors.add(createChildParameter(StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__SD,
				StochasticLanguageFactory.eINSTANCE.createGTNumber()));

		newChildDescriptors.add(createChildParameter(StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__SD,
				StochasticLanguageFactory.eINSTANCE.createGTTwoParameterCalculation()));

		newChildDescriptors.add(createChildParameter(StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__SD,
				StochasticLanguageFactory.eINSTANCE.createGTOneParameterCalculation()));
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

		boolean qualify = childFeature == StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__MEAN
				|| childFeature == StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION__SD;

		if (qualify) {
			return getString("_UI_CreateChild_text2",
					new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return CommonEditPlugin.INSTANCE;
	}

}
