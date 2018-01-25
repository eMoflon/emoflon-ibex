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
package org.emoflon.ibex.tgg.operational.csp.sorting;

import org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.common.Combiner;

import language.csp.TGGAttributeConstraint;

public class SimpleCombiner implements Combiner<SimpleCombiner, TGGAttributeConstraint> {
	private final Chain<TGGAttributeConstraint> last;
	
	public SimpleCombiner() {
		this.last = null;
	}
	
	private SimpleCombiner(final SimpleCombiner src, final TGGAttributeConstraint second) {
		this.last = new Chain<TGGAttributeConstraint>(second, src.last);
	}
	
	public final SimpleCombiner combine(final TGGAttributeConstraint second) {
		return new SimpleCombiner(this, second);
	}

	public final boolean hasSameOrigin(TGGAttributeConstraint operation) {
		return last != null && last.getValue() == operation;
	}
	
	public final Chain<TGGAttributeConstraint> getRoot() {
		return last;
	}
}
