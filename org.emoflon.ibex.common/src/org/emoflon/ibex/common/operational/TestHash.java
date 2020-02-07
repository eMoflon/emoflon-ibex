package org.emoflon.ibex.common.operational;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TestHash {

	public static void main(String[] args) {
		final long seed = "HierStehtEinTollerSeed17".hashCode();
		
		List<Integer> ints = new LinkedList<>();
		for(int i = 250; i<255; i++) {
			ints.add(i);
		}
		for(int i = 250; i<255; i++) {
			ints.add(i);
		}
		for(Integer in : ints) {
			System.out.println(in.hashCode());
			System.out.println(collectionToSring(SimpleMatch.intToBytes(in.hashCode())));
			System.out.println(SimpleMatch.murmur3_64(SimpleMatch.intToByteArray(in.hashCode()), seed));
		}
		
		StringWrap str1 = new StringWrap("Hallo");
		StringWrap str2 = new StringWrap("Hallo");
		System.out.println(str1.equals(str2));
		
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
		final long seed = "HierStehtEinTollerSeed17".hashCode();
		return SimpleMatch.murmur3_64(SimpleMatch.intToByteArray(data.hashCode()), seed);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StringWrap))
			return false;
		StringWrap other = (StringWrap)obj;
		System.out.println("Other: "+other.data+" -> "+other.hash()+"/"+other.data.hashCode());
		System.out.println("This: "+data+" -> "+hash()+"/"+data.hashCode());
		return other.hash() == hash();
	}
}
