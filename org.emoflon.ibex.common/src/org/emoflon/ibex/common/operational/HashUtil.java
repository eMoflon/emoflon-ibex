package org.emoflon.ibex.common.operational;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class HashUtil {
	
	public static long objectToHash(Object object) {
		final HashFunction hf = Hashing.murmur3_128("HierStehtEinTollerSeed17".hashCode());
		return hf.hashBytes(intToByteArray(object.hashCode())).asLong();
	}
	
	public static long collectionToHash(Collection<Object> objects) {
		final HashFunction hf = Hashing.murmur3_128("HierStehtEinTollerSeed17".hashCode());
		return hf.hashBytes(collectionHashToByteArray(objects)).asLong();
	}
	
	public static byte[] collectionHashToByteArray(final Collection<Object> objects) {
		byte[] bytes = new byte[objects.size()*8];
		int idx = 0;
		for(Object object : objects) {
			byte[] objectAsBytes = intToByteArray(object.hashCode());
			bytes[idx] = objectAsBytes[0];
			bytes[idx+1] = objectAsBytes[1];
			bytes[idx+2] = objectAsBytes[2];
			bytes[idx+3] = objectAsBytes[3];
			bytes[idx+4] = objectAsBytes[4];
			bytes[idx+5] = objectAsBytes[5];
			bytes[idx+6] = objectAsBytes[6];
			bytes[idx+7] = objectAsBytes[7];
			idx+=8;
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

	public static void main(String[] args) {
		List<Integer> ints = new LinkedList<>();
		for(int i = 250; i<255; i++) {
			ints.add(i);
		}
		for(int i = 250; i<255; i++) {
			ints.add(i);
		}
		for(Integer in : ints) {
			System.out.println(in.hashCode());
			System.out.println(arrayToSring(intToByteArray(in.hashCode())));
			System.out.println(objectToHash(in));
		}
		
		StringWrap str1 = new StringWrap("Hallo");
		StringWrap str2 = new StringWrap("Hallo");
		System.out.println(str1.equals(str2));
		
	}
	
	public static String arrayToSring(byte[] obj) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<obj.length; i++) {
			sb.append(obj[i]+" ");
		}
		return sb.toString();
	}
	
	public static String collectionToSring(List<Byte> obj) {
		return obj.stream().map(object -> object.toString()).reduce("", (sum, value) -> sum+" "+value);
	}
	
}

class StringWrap {
	final public Object data;
	
	public StringWrap(final Object data) {
		this.data = data;
	}
	
	public long hash() {
		return HashUtil.objectToHash(data);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StringWrap other))
			return false;
		System.out.println("Other: "+other.data+" -> "+other.hash()+"/"+other.data.hashCode());
		System.out.println("This: "+data+" -> "+hash()+"/"+data.hashCode());
		return other.hash() == hash();
	}
}
