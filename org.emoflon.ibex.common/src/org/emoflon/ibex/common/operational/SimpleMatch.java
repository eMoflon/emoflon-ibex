package org.emoflon.ibex.common.operational;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Lists;

/**
 * A simple implementation of {@link IMatch}.
 */
public class SimpleMatch implements IMatch {
	/**
	 * The name of the pattern.
	 */
	private String patternName;
	
	protected long hash;
	protected boolean hashInit = false;

	/**
	 * The mapping between parameter names and objects.
	 */
	private final Map<String, Object> parameters;

	/**
	 * Initializes an empty match with the given pattern name.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 */
	public SimpleMatch(final String patternName) {
		this.patternName = patternName;
		//this.parameters = cfactory.createObjectToObjectHashMap();
		this.parameters = new LinkedHashMap<>();
	}

	/**
	 * Initializes the match as a copy of the given match.
	 * 
	 * @param match
	 *            the match to copy
	 */
	public SimpleMatch(final IMatch match) {
		this.patternName = match.getPatternName();
		//this.parameters = cfactory.createObjectToObjectHashMap();
		this.parameters = new LinkedHashMap<>();
		match.getParameterNames().forEach(parameterName -> {
			this.parameters.put(parameterName, match.get(parameterName));
		});
	}

	@Override
	public String getPatternName() {
		return this.patternName;
	}

	@Override
	public void setPatternName(final String patternName) {
		this.patternName = patternName;
	}

	@Override
	public Object get(final String name) {
		return parameters.get(name);
	}

	@Override
	public void put(final String name, final Object object) {
		parameters.put(name, object);
	}

	@Override
	public Collection<String> getParameterNames() {
		return parameters.keySet();
	}
	
	@Override
	public long getHashCode() {
		if(!hashInit) {
			hash = collectionToHash(parameters.values());
			hashInit = true;
		}
		return hash;
	}

	@Override
	public int hashCode() {
		return (int)getHashCode();
	}
	
	public static long collectionToHash(Collection<Object> objects) {
		final long seed = "HierStehtEinTollerSeed17".hashCode();
		List<Byte> bytes = new LinkedList<>();
		for(Object object : objects) {
			bytes.addAll(intToBytes(object.hashCode()));
		}
		byte[] byteArray = new byte[bytes.size()];
		int idx = 0;
		for(Byte b : bytes) {
			byteArray[idx]=b;
			idx++;
		}
		return murmur3_64(byteArray, seed);
	}
	
	public static List<Byte> intToBytes(final long value) {
		List<Byte> bytes = new LinkedList<>();
		final long mask = 0x00000000000000ff;
		for(int i = 0; i<8; i++) {
			bytes.add((byte)((value & mask<<i*8)>>i*8));
		}
		return bytes;
	}
	
	public static byte[] intToByteArray(final long value) {
		byte[] b = new byte[8];
		final long mask = 0x00000000000000ff;
		for(int i = 0; i<8; i++) {
			b[i]=((byte)((value & mask<<i*8)>>i*8));
		}
		return b;
	}
	
	public static long murmur_64_scramble(long k) {
	    k *= 0xcc9e2d51;
	    k = (k << 15) | (k >> 17);
	    k *= 0x1b873593;
	    return k;
	}
	public static long murmur3_64(final byte[] data, long seed)
	{
		long h = seed;
	    long k;
	    int lastIdx=0;
	    /* Read in groups of 8. */
	    for (int i = 0; i<data.length >> 3; i++) {
	        // Here is a source of differing results across endiannesses.
	        // A swap here has no effects on hash properties though.
	        k = arrayRangeToLong(data, i*8, i*8+7);
	        h ^= murmur_64_scramble(k);
	        h = (h << 13) | (h >> 19);
	        h = h * 5 + 0xe6546b64;
	        lastIdx = i*8+7;
	    }
	    /* Read the rest. */
	    k = 0;
	    for (int i = lastIdx+1; i<data.length; i++) {
	        k <<= 8;
	        k |= data[i];
	    }
	    // A swap is *not* necessary here because the preceeding loop already
	    // places the low bytes in the low places according to whatever endianness
	    // we use. Swaps only apply when the memory is copied in a chunk.
	    h ^= murmur_64_scramble(k);
	    /* Finalize. */
		h ^= data.length;
		h ^= h >> 16;
		h *= 0x85ebca6b;
		h ^= h >> 13;
		h *= 0xc2b2ae35;
		h ^= h >> 16;
		return h;
	}
	
	public static long arrayRangeToLong(final byte[] data, int start, int end) {
		long num = 0;
		for(int i = 0; i<end-start; i++) {
			num |= ((long)data[i+start])<<i*8;
		}
		return num;
	}

	@Override
	public boolean equals(final Object object) {
		// TODO adrianm, lfritsche: bad performance
		return object instanceof IMatch && isEqual((IMatch) object);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("IMatch for ").append(getPatternName()).append(" {").append(System.lineSeparator());

		for (final String parameterName : getParameterNames()) {
			s.append("	").append(parameterName);
			s.append(" -> ").append(get(parameterName)).append(System.lineSeparator());
		}
		s.append("}");

		return s.toString();
	}

	public IMatch copy() {
		return new SimpleMatch(this);
	}
}
