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
package org.emoflon.ibex.tgg.operational.csp.solver;

public class CodeGeneratorChain<T> {
	private final CodeGeneratorChain<T> next;
	private final T value;

	public CodeGeneratorChain(T element) {
		this(element, null);
	}
	
	CodeGeneratorChain(T value, CodeGeneratorChain<T> next) {
		this.value = value;
		this.next = next;
	}

	final CodeGeneratorChain<T> copy() {
		return copyAndMerge(null);
	}
	
	final CodeGeneratorChain<T> copyAndMerge(CodeGeneratorChain<T> otherChain) {
		if (next != null) {
			return new CodeGeneratorChain<T>(value, next.copyAndMerge(otherChain));
		} else {
			return new CodeGeneratorChain<T>(value, otherChain);
		}
	}
	
	final CodeGeneratorChain<T> reverseCopy() {
		return reverseCopy(null);
	}
	
	private final CodeGeneratorChain<T> reverseCopy(CodeGeneratorChain<T> reverseTail) {
		if (next != null) {
			return next.reverseCopy(new CodeGeneratorChain<T>(value, reverseTail));
		} else {
			return new CodeGeneratorChain<T>(value, reverseTail);
		}
	}
	
    public final T getValue() {
    	return value;
    }
    
    public final CodeGeneratorChain<T> getNext() {
    	return next;
    }
}
