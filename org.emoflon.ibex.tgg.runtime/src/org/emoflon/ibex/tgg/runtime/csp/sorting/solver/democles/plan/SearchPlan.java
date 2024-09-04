/*
 * Democles, Declarative Model Query Framework for Monitoring Heterogeneous Embedded Systems
 * Copyright (C) 2010  Gergely Varro
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * Contributors:
 * 		Gergely Varro <gervarro@cs.bme.hu> - initial API and implementation and/or initial documentation
 */
package org.emoflon.ibex.tgg.runtime.csp.sorting.solver.democles.plan;

import java.util.LinkedList;
import java.util.List;

import org.emoflon.ibex.tgg.runtime.csp.sorting.solver.democles.common.Adornment;
import org.emoflon.ibex.tgg.runtime.csp.sorting.solver.democles.common.Combiner;

class SearchPlan<C extends Combiner<C,O>, O> implements Comparable<SearchPlan<C,O>> {
	private boolean hasFreshLists;
	private C root;
	private final Adornment adornment;
	private final float cost;
	private final float productCost;
	private List<WeightedOperation<O>> present;
	private List<WeightedOperation<O>> future;

	@SuppressWarnings("unchecked")
	private static <U> WeightedOperation<U>[] createArray(int size) {
		return new WeightedOperation[size];
	}
	
	SearchPlan(final Adornment adornment, final float cost,final float productCost,  C root, List<WeightedOperation<O>> present, List<WeightedOperation<O>> future) {
		this.hasFreshLists = false;
		this.adornment = adornment;
		this.cost = cost;
		this.productCost=productCost;
		this.root = root;
		this.present = present;
		this.future = future;
	}
	
	final void refreshOperationLists() {
		if (hasFreshLists) {
			return;
		}
		
		WeightedOperation<O>[] pArray = createArray(present.size());
		pArray = present.toArray(pArray);
		WeightedOperation<O>[] fArray = createArray(future.size());
		fArray = future.toArray(fArray);
		present = new LinkedList<WeightedOperation<O>>();
		future = new LinkedList<WeightedOperation<O>>();
		
		C lastRoot = root;
		for (int p = 0, f = 0; p < pArray.length || f < fArray.length; ) {
			WeightedOperation<O> smaller = 
				p < pArray.length && (f >= fArray.length || pArray[p].compareTo(fArray[f]) <= 0) ? 
						pArray[p++] : fArray[f++];
			if ((lastRoot == null || !lastRoot.hasSameOrigin(smaller.operation)) && 
					adornment.equals(adornment.or(smaller.freeMask))) {
				if (adornment.and(smaller.boundMask).cardinality() == 0) {
					if (smaller.freeMask.cardinality() == 0) {
						// Check operation that is immediately processed
						root = root.combine(smaller.operation);
					} else {
						// Extend operation that can be executed in this round
						this.present.add(smaller);
					}
				} else {
					// Extend operation that can be executed in the future
					this.future.add(smaller);
				}
			}
		}
		hasFreshLists = true;
	}
	
	final Adornment getAdornment() {
		return adornment;
	}
	
	final float getCost() {
		return cost;
	}
	final float getProductCost(){
		return productCost;
	}
	final C getRoot() {
		return root;
	}
	
	final List<WeightedOperation<O>> getPresentOperations() {
		return present;
	}
	
	final List<WeightedOperation<O>> getFutureOperations() {
		return future;
	}
	
	public int compareTo(SearchPlan<C,O> o) {
		return (cost < o.cost ? -1 : (cost == o.cost ? 0 : 1));
	}
}
