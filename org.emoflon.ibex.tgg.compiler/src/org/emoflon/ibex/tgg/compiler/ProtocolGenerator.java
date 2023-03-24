package org.emoflon.ibex.tgg.compiler;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

import TGGRuntimeModel.TGGRuntimeModelPackage;

public class ProtocolGenerator {

	public final static String PROTOCOL_NODE_TYPE_PREFIX = "ProtocolNode_";
	public final static String PROTOCOL_EDGE_TYPE_PREFIX = "check_";
	public final static String PROTOCOL_NODE_PREFIX = "_protocol_";
	
	private EcoreFactory factory = EcoreFactory.eINSTANCE;
	private EPackage protocol;
	private Map<TGGRule, ProtocolNodeInformation> rule2information = new HashMap<>();
	
	private TGGRuntimeModelPackage runtimePackage = TGGRuntimeModelPackage.eINSTANCE;
	
	public ProtocolInformation createProtocol(TGGModel model) {
		protocol = factory.createEPackage();
		protocol.setName(model.getName() + "Protocol");
		protocol.setNsPrefix(model.getName());
		protocol.setNsURI("platform:/resource/" + model.getName() + "/model/" + model.getName() + ".ecore");
		
		for(var rule : model.getRuleSet().getRules()) {
			protocol.getEClassifiers().add(createProtocolType(rule));
		}
		
		return new ProtocolInformation(protocol, rule2information);
	}

	private EClass createProtocolType(TGGRule rule) {
		var protocolType = factory.createEClass();
		protocolType.setName(PROTOCOL_NODE_TYPE_PREFIX + rule.getName());
		protocolType.getESuperTypes().add(runtimePackage.getProtocol());
		var nodeToReference = new HashMap<TGGNode, EReference>();
		
		for(var node : rule.getNodes()) {
			var reference = factory.createEReference();
			reference.setName(PROTOCOL_EDGE_TYPE_PREFIX + node.getName());
			reference.setEType(node.getType());
			reference.setLowerBound(1);
			reference.setUpperBound(1);

			protocolType.getEReferences().add(reference);
			nodeToReference.put(node, reference);
		}
		
		var nodeInformation = new ProtocolNodeInformation(protocolType, nodeToReference);
		rule2information.put(rule, nodeInformation);

		return protocolType;
	}
}

record ProtocolInformation(EPackage metamodel, Map<TGGRule, ProtocolNodeInformation> ruleToInformation) {}

record ProtocolNodeInformation(EClass type, Map<TGGNode, EReference> nodeToReference) {}