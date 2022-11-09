package org.emoflon.ibex.tgg.compiler;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.*;

public class ProtocolGenerator {

	public final static String PROTOCOL_NODE_PREFIX = "ProtocolNode_";
	public final static String PROTOCOL_EDGE_PREFIX = "check_";
	
	private EcoreFactory factory = EcoreFactory.eINSTANCE;
	private EPackage protocol;
	private Map<TGGRule, EClass> rule2protocol = new HashMap<>();
	private Collection<ProtocolNodeInformation> nodeInformations = new LinkedList<>();
	
	private TGGRuntimeModelPackage runtimePackage = TGGRuntimeModelPackage.eINSTANCE;
	
	public ProtocolInformation createProtocol(TGGModel model) {
		protocol = factory.createEPackage();
		protocol.setName(model.getName() + "Protocol");
		
		for(var rule : model.getRuleSet().getRules()) {
			protocol.getEClassifiers().add(createProtocolType(rule));
		}
		
		return new ProtocolInformation(protocol, rule2protocol, nodeInformations);
	}

	private EClass createProtocolType(TGGRule rule) {
		var protocolType = factory.createEClass();
		protocolType.setName(PROTOCOL_NODE_PREFIX + rule.getName());
		protocolType.getESuperTypes().add(runtimePackage.getProtocol());
		rule2protocol.put(rule, protocolType);
		var nodeToReference = new HashMap<TGGNode, EReference>();
		
		for(var node : rule.getNodes()) {
			var reference = factory.createEReference();
			reference.setName(PROTOCOL_EDGE_PREFIX + node.getName());
			reference.setEType(node.getType());
			reference.setLowerBound(1);
			reference.setUpperBound(1);

			protocolType.getEReferences().add(reference);
			nodeToReference.put(node, reference);
		}
		
		var nodeInformation = new ProtocolNodeInformation(protocolType, nodeToReference);
		nodeInformations.add(nodeInformation);
		return protocolType;
	}
	
	
}

record ProtocolInformation(EPackage metamodel, Map<TGGRule, EClass> ruleToType, Collection<ProtocolNodeInformation> nodes) {}

record ProtocolNodeInformation(EClass type, Map<TGGNode, EReference> nodeToReference) {}