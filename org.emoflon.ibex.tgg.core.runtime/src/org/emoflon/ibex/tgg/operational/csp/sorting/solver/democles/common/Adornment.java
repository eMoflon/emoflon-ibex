/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.common;

import java.util.Iterator;

public class Adornment implements Iterable<Boolean> {
	private static final int OFFSET = 5;

	private static final int ELM_SIZE = 1 << OFFSET;

	private static final int RIGHT_BITS = ELM_SIZE - 1;

	private static final int[] TWO_N_ARRAY = new int[] { 0x1, 0x2, 0x4,
		0x8, 0x10, 0x20, 0x40, 0x80, 0x100, 0x200, 0x400, 0x800,
		0x1000, 0x2000, 0x4000, 0x8000, 0x10000, 0x20000, 0x40000,
		0x80000, 0x100000, 0x200000, 0x400000, 0x800000, 0x1000000,
		0x2000000, 0x4000000, 0x8000000, 0x10000000, 0x20000000,
		0x40000000, 0x80000000 };
	
	public static final boolean B = false;
	public static final boolean F = true;
	public static final Adornment BOUND = new Adornment(new boolean[] { B });
	public static final Adornment FREE = new Adornment(new boolean[] { F });
	public static final Adornment BOUND_BOUND = new Adornment(new boolean[] { B, B });
	public static final Adornment FREE_BOUND = new Adornment(new boolean[] { F, B });
	public static final Adornment BOUND_FREE = new Adornment(new boolean[] { B, F });
	public static final Adornment FREE_FREE = new Adornment(new boolean[] { F, F });

	private final int[] bits;
	private final int length;
	
	public Adornment() {
		this(0);
	}

	public Adornment(final int nbits) {
		if (nbits < 0) {
			throw new NegativeArraySizeException();
		}
		this.bits = new int[(nbits >> OFFSET) + ((nbits & RIGHT_BITS) > 0 ? 1 : 0)];
		this.length = nbits;
	}

	public Adornment(final boolean[] bits) {
		this(bits.length);
		for (int i = 0; i < bits.length; i++) {
			set(i, bits[i]);
		}
	}

	private Adornment(final Adornment bitSet) {
		this.bits = new int[bitSet.bits.length];
		this.length = bitSet.length;
		System.arraycopy(bitSet.bits, 0, bits, 0, bitSet.bits.length);
	}

	/**
	 * Compares the argument to this {@code BitSet} and returns whether they are
	 * equal. The object must be an instance of {@code BitSet} with the same
	 * bits set.
	 * 
	 * @param obj
	 *            the {@code BitSet} object to compare.
	 * @return a {@code boolean} indicating whether or not this {@code BitSet} and
	 *         {@code obj} are equal.
	 * @see #hashCode
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Adornment) {
			Adornment other = (Adornment) obj;
			if (length != other.length) {
				return false;
			}
			int[] bsBits = other.bits;
			assert bits.length == bsBits.length;
			for (int i = 0; i < bits.length; i++) {
				if (bits[i] != bsBits[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Computes the hash code for this {@code BitSet}. If two {@code BitSet}s are equal
	 * the have to return the same result for {@code hashCode()}.
	 * 
	 * @return the {@code int} representing the hash code for this bit
	 *         set.
	 * @see #equals
	 * @see java.util.Hashtable
	 */
	@Override
	public int hashCode() {
		int size = (length >> 3) + ((length & 7) > 0 ? 1 : 0);
		byte[] bytes = new byte[size + Integer.SIZE / Byte.SIZE];
		int index = 0;
		for (int i = 0; i < bits.length; i++) {
			bytes[index++] = (byte) bits[i];
			bytes[index++] = (byte) (bits[i] >>> 8);
			bytes[index++] = (byte) (bits[i] >>> 16);
			bytes[index++] = (byte) (bits[i] >>> 24);
		}
		index = size;
		bytes[index++] = (byte) length;
		bytes[index++] = (byte) (length >>> 8);
		bytes[index++] = (byte) (length >>> 16);
		bytes[index++] = (byte) (length >>> 24);
		return MurmurHash.hash32(bytes, bytes.length);
		/*
		long x = 1234;
		for (int i = 0; i < bits.length; i++) {
			x ^= bits[i] * (i + 1);
		}
		return (int) ((x >> 32) ^ x);
		*/
	}

	/**
	 * Retrieves the bit at index {@code pos}. Grows the {@code BitSet} if
	 * {@code pos > size}.
	 * 
	 * @param pos
	 *            the index of the bit to be retrieved.
	 * @return {@code true} if the bit at {@code pos} is set,
	 *         {@code false} otherwise.
	 * @throws IndexOutOfBoundsException
	 *             if {@code pos} is negative.
	 * @see #clear(int)
	 * @see #set(int)
	 * @see #clear()
	 * @see #clear(int, int)
	 * @see #set(int, boolean)
	 * @see #set(int, int)
	 * @see #set(int, int, boolean)
	 */
	public boolean get(int pos) {
		if (pos < 0 || pos >= length) {
			throw new IndexOutOfBoundsException();
		}
		return (bits[pos >> OFFSET] & TWO_N_ARRAY[pos & RIGHT_BITS]) != 0;
	}

	/**
	 * Sets the bit at index {@code pos} to 1.
	 * 
	 * @param pos
	 *            the index of the bit to set.
	 * @throws IndexOutOfBoundsException
	 *             if {@code pos} is negative.
	 * @see #clear(int)
	 * @see #clear()
	 * @see #clear(int, int)
	 */
	public void set(int pos) {
		if (pos < 0 || pos >= length) {
			throw new IndexOutOfBoundsException();
		}
		bits[pos >> OFFSET] |= TWO_N_ARRAY[pos & RIGHT_BITS];
	}

	public void set(int pos1, int pos2) {
		if (pos1 < 0 || pos2 < 0 || pos1 >= length || pos2 >= length || pos2 < pos1) {
			throw new IndexOutOfBoundsException();
		}

		int idx1 = pos1 >> OFFSET;
		int idx2 = pos2 >> OFFSET;
		int factor1 = (~0) << (pos1 & RIGHT_BITS);
		int factor2 = (~0) >>> (RIGHT_BITS - (pos2 & RIGHT_BITS));

		if (idx1 == idx2) {
			bits[idx1] |= (factor1 & factor2);
		} else {
			bits[idx1] |= factor1;
			bits[idx2] |= factor2;
			for (int i = idx1 + 1; i < idx2; i++) {
				bits[i] = (~0);
			}
		}
	}

	/**
	 * Sets the bit at index {@code pos} to {@code val}.
	 * 
	 * @param pos
	 *            the index of the bit to set.
	 * @param val
	 *            value to set the bit.
	 * @throws IndexOutOfBoundsException
	 *             if {@code pos} is negative.
	 * @see #set(int)
	 */
	public void set(int pos, boolean val) {
		if (val) {
			set(pos);
		} else {
			clear(pos);
		}
	}

	/**
	 * Clears all the bits in this {@code BitSet}.
	 * 
	 * @see #clear(int)
	 * @see #clear(int, int)
	 */
	public void clear() {
		for (int i = 0; i < bits.length; i++) {
			bits[i] = 0;
		}
	}

	/**
	 * Clears the bit at index {@code pos}. Grows the {@code BitSet} if
	 * {@code pos > size}.
	 * 
	 * @param pos
	 *            the index of the bit to clear.
	 * @throws IndexOutOfBoundsException
	 *             if {@code pos} is negative.
	 * @see #clear(int, int)
	 */
	public void clear(int pos) {
		if (pos < 0 || pos >= length) {
			throw new IndexOutOfBoundsException();
		}
		bits[pos >> OFFSET] &= ~(TWO_N_ARRAY[pos & RIGHT_BITS]);
	}

	/**
	 * Clears the bits starting from {@code pos1} to {@code pos2}.
	 * 
	 * @param pos1
	 *            beginning position.
	 * @param pos2
	 *            ending position.
	 * @throws IndexOutOfBoundsException
	 *             if {@code pos1} or {@code pos2} is negative, or if
	 *             {@code pos2} is smaller than {@code pos1}.
	 * @see #clear(int)
	 */
	public void clear(int pos1, int pos2) {
		if (pos1 < 0 || pos2 < 0 || pos1 >= length || pos2 >= length || pos2 < pos1) {
			throw new IndexOutOfBoundsException();
		}

		int idx1 = pos1 >> OFFSET;
		int idx2 = pos2 >> OFFSET;
		int factor1 = (~0) << (pos1 & RIGHT_BITS);
		int factor2 = (~0) >>> (RIGHT_BITS - (pos2 & RIGHT_BITS));

		if (idx1 == idx2) {
			bits[idx1] &= ~(factor1 & factor2);
		} else {
			bits[idx1] &= ~factor1;
			bits[idx2] &= ~factor2;
			for (int i = idx1 + 1; i < idx2; i++) {
				bits[i] = 0;
			}
		}
	}

	/**
	 * Flips the bit at index {@code pos}. Grows the {@code BitSet} if
	 * {@code pos > size}.
	 * 
	 * @param pos
	 *            the index of the bit to flip.
	 * @throws IndexOutOfBoundsException
	 *             if {@code pos} is negative.
	 * @see #flip(int, int)
	 */
	public void flip(int pos) {
		if (pos < 0 || pos >= length) {
			throw new IndexOutOfBoundsException();
		}
		bits[pos >> OFFSET] ^= TWO_N_ARRAY[pos & RIGHT_BITS];
	}

	/**
	 * Flips the bits starting from {@code pos1} to {@code pos2}.
	 * 
	 * @param pos1
	 *            beginning position.
	 * @param pos2
	 *            ending position.
	 * @throws IndexOutOfBoundsException
	 *             if {@code pos1} or {@code pos2} is negative, or if
	 *             {@code pos2} is smaller than {@code pos1}.
	 * @see #flip(int)
	 */
	public void flip(int pos1, int pos2) {
		if (pos1 < 0 || pos2 < 0 || pos1 >= length || pos2 >= length || pos2 < pos1) {
			throw new IndexOutOfBoundsException();
		}

		int idx1 = pos1 >> OFFSET;
		int idx2 = pos2 >> OFFSET;
		int factor1 = (~0) << (pos1 & RIGHT_BITS);
		int factor2 = (~0) >>> (RIGHT_BITS - (pos2 & RIGHT_BITS));

		if (idx1 == idx2) {
			bits[idx1] ^= (factor1 & factor2);
		} else {
			bits[idx1] ^= factor1;
			bits[idx2] ^= factor2;
			for (int i = idx1 + 1; i < idx2; i++) {
				bits[i] ^= (~0);
			}
		}
	}

	/**
	 * Returns the number of bits this {@code BitSet} has.
	 * 
	 * @return the number of bits contained in this {@code BitSet}.
	 * @see #length
	 */
	public int size() {
		return length;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(get(i) ? "F" : "B");
		}
		return sb.toString();
	}
	
	public String toString(boolean firstCapital, boolean allCapital, String separator) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(separator != null && i > 0 ? separator : "");
			if (get(i)) {
				sb.append(firstCapital || allCapital ? "F" : "f");
				sb.append(allCapital ? "REE" : "ree");
			} else {
				sb.append(firstCapital || allCapital ? "B" : "b");
				sb.append(allCapital ? "OUND" : "ound");
			}
		}
		return sb.toString();
	}

	/**
	 * Returns the position of the first bit that is {@code true} on or after {@code pos}.
	 * 
	 * @param pos
	 *            the starting position (inclusive).
	 * @return -1 if there is no bits that are set to {@code true} on or after {@code pos}.
	 */
	public int nextSetBit(int pos) {
		if (pos < 0 || pos >= length) {
			throw new IndexOutOfBoundsException();
		}

		if (pos >= bits.length << OFFSET) {
			return -1;
		}

		int idx = pos >> OFFSET;
		// first check in the same bit set element
		if (bits[idx] != 0) {
			for (int j = pos & RIGHT_BITS; j < ELM_SIZE; j++) {
				if (((bits[idx] & (TWO_N_ARRAY[j])) != 0)) {
					return (idx << OFFSET) + j;
				}
			}

		}
		idx++;
		while (idx < bits.length && bits[idx] == 0) {
			idx++;
		}
		if (idx == bits.length) {
			return -1;
		}

		// we know for sure there is a bit set to true in this element
		// since the bitset value is not 0
		for (int j = 0; j < ELM_SIZE; j++) {
			if (((bits[idx] & (TWO_N_ARRAY[j])) != 0)) {
				return (idx << OFFSET) + j;
			}
		}

		return -1;
	}

	/**
	 * Returns the position of the first bit that is {@code false} on or after {@code pos}.
	 * 
	 * @param pos
	 *            the starting position (inclusive).
	 * @return the position of the next bit set to {@code false}, even if it is further
	 *         than this {@code BitSet}'s size.
	 */
	public int nextClearBit(int pos) {
		if (pos < 0 || pos >= length) {
			throw new IndexOutOfBoundsException();
		}

		if (pos >= bits.length << OFFSET) {
			return -1;
		}

		int idx = pos >> OFFSET;
		// first check in the same bit set element
		if (bits[idx] != (~0)) {
			for (int j = pos & RIGHT_BITS; j < ELM_SIZE; j++) {
				if (((bits[idx] & (TWO_N_ARRAY[j])) == 0)) {
					return (idx << OFFSET) + j;
				}
			}
		}
		idx++;
		while (idx < bits.length && bits[idx] == (~0)) {
			idx++;
		}
		if (idx == bits.length) {
			return -1;
		}

		// we know for sure there is a bit set to true in this element
		// since the bitset value is not 0
		for (int j = 0; j < ELM_SIZE; j++) {
			if (((bits[idx] & (TWO_N_ARRAY[j])) == 0)) {
				return (idx << OFFSET) + j;
			}
		}

		return -1;
	}

	/**
	 * Returns true if all the bits in this {@code BitSet} are set to false.
	 * 
	 * @return {@code true} if the {@code BitSet} is empty,
	 *         {@code false} otherwise.
	 */
	public boolean isEmpty() {
		for (int idx = 0; idx < bits.length; idx++) {
			if (bits[idx] != 0) {
				return false;
			}
		}
		return true;
	}
	
	public final Adornment and(Adornment other) {
		return new Adornment(this).internalAnd(other);
	}
	
	private final Adornment internalAnd(Adornment other) {
		if (length != other.length) {
			throw new IllegalArgumentException("The lengths of bit arrays differ");
		}
		for (int i = 0; i < bits.length; i ++) {
			bits[i] &= other.bits[i];
		}
		int mask = (~0) >>> (RIGHT_BITS - ((length - 1) & RIGHT_BITS));
		bits[bits.length - 1] &= mask;
		return this;
	}

	public final Adornment xor(Adornment other) {
		return new Adornment(this).internalXor(other);
	}
	
	private final Adornment internalXor(Adornment other) {
		if (length != other.length) {
			throw new IllegalArgumentException("The lengths of bit arrays differ");
		}
		for (int i = 0; i < bits.length; i ++) {
			bits[i] ^= other.bits[i];
		}
		int mask = (~0) >>> (RIGHT_BITS - ((length - 1) & RIGHT_BITS));
		bits[bits.length - 1] &= mask;
		return this;
	}

	public final Adornment or(Adornment other) {
		return new Adornment(this).internalOr(other);
	}
	
	private final Adornment internalOr(Adornment other) {
		if (length != other.length) {
			throw new IllegalArgumentException("The lengths of bit arrays differ");
		}
		for (int i = 0; i < bits.length; i ++) {
			bits[i] |= other.bits[i];
		}
		int mask = (~0) >>> (RIGHT_BITS - ((length - 1) & RIGHT_BITS));
		bits[bits.length - 1] &= mask;
		return this;
	}

	public final Adornment not() {
		return new Adornment(this).internalNot();
	}
	
	private final Adornment internalNot() {
		for (int i = 0; i < bits.length; i ++) {
			bits[i] = ~bits[i];
		}
		int mask = (~0) >>> (RIGHT_BITS - ((length - 1) & RIGHT_BITS));
		bits[bits.length - 1] &= mask;
		return this;
	}

	/**
	 * Returns the number of bits that are {@code true} in this {@code BitSet}.
	 * 
	 * @return the number of {@code true} bits in the set.
	 */
	public int cardinality() {
		int count = 0;
		for (int idx = 0; idx < bits.length; idx++) {
			count += pop(bits[idx]);
		}
		return count;
	}

	private final int pop(int x) {
		x = x - ((x >>> 1) & 0x55555555);
		x = (x & 0x33333333) + ((x >>> 2) & 0x33333333);
		x = (x + (x >>> 4)) & 0x0f0f0f0f;
		x = x + (x >>> 8);
		x = x + (x >>> 16);
		return x & 0x0000003f;
	}

	/*
	final int numberOfSetBits(int i) {
		i = i - ((i >> 1) & 0x55555555);
		i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
		return ((i + (i >> 4) & 0xF0F0F0F) * 0x1010101) >> 24;
	}
	*/

	public boolean fulfills(Adornment constraint) {
		if (length != constraint.length) {
			return false;
		}
		for (int i = 0; i < bits.length; i++) {
			if (bits[i] != (bits[i] & constraint.bits[i])) {
				return false;
			}
		}
		return true;
	}

	public Iterator<Boolean> iterator() {
		return new Iterator<Boolean>() {
			private final Adornment original = Adornment.this;
			private int index = 0;

			public boolean hasNext() {
				return index < original.size();
			}

			public Boolean next() {
				return original.get(index++);
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
}