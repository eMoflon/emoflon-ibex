package org.emoflon.ibex.tgg.operational;

/**
 * PROTOCOL_NACS corresponds to the classical TGG approach where elements are marked only once
 * ILP means that elements are marked multiple times and an optimization problem is to be solved
 * @author leblebici
 *
 */
public enum OperationStrategy {
	ILP, PROTOCOL_NACS
}
