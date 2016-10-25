package org.moflon.ide.mosl.core.scoping;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.SimpleScope;

import com.google.common.collect.Iterables;
import com.google.inject.Provider;

public class MoflonScope extends SimpleScope {

	public MoflonScope(List<EObject> objects) {
		super(new SimpleScope(Scopes.scopedElementsFor(accountForSubPackages(objects), 
													   new DefaultDeclarativeQualifiedNameProvider())), 
			  Scopes.scopedElementsFor(accountForSubPackages(objects), 
					                   new RootPackageAwareQualifiedNamedProvider()));
	}

	private static Collection<EObject> accountForSubPackages(List<EObject> objects) {		
		Set<EObject> allPackages = objects
			.stream()
			.flatMap(o -> EcoreUtil2.getAllContentsOfType(o, EPackage.class).stream())
			.collect(Collectors.toSet());
		
		allPackages.addAll(objects);
		
		return allPackages;
	}

	@Override
	public Iterable<IEObjectDescription> getElements(final EObject object) {
		Iterable<IEObjectDescription> defaultImpl = super.getElements(object);
		if (defaultImpl.iterator().hasNext())
			return defaultImpl;

		// If default does not work, try using the exact URI of object (no normalization etc)
		Iterable<IEObjectDescription> localElements = getLocalElementsByEObject(object, EcoreUtil.getURI(object));
		Iterable<IEObjectDescription> parentElements = getParentElements(new Provider<Iterable<IEObjectDescription>>() {
			@Override
			public Iterable<IEObjectDescription> get() {
				return getParent().getElements(object);
			}
		});
		Iterable<IEObjectDescription> result = Iterables.concat(localElements, parentElements);
		return result;
	}

}

class RootPackageAwareQualifiedNamedProvider extends DefaultDeclarativeQualifiedNameProvider {
	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if(obj instanceof EPackage){
			EPackage pack = EPackage.class.cast(obj);
			if(pack.eContainer() == null){
				IQualifiedNameConverter converter = new IQualifiedNameConverter.DefaultImpl();
				return converter.toQualifiedName(pack.getNsPrefix() + "." + pack.getName());
			}
		}
			
		return super.getFullyQualifiedName(obj);
	}
}