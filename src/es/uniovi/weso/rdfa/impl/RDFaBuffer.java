package es.uniovi.weso.rdfa.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RDFaBuffer {

	private Map<String, StringBuffer> triples = new HashMap<String, StringBuffer>();
	
	
	
	public void add(String property, StringBuffer rdftriples) {
		StringBuffer value = new StringBuffer();
		if(triples.containsKey(property)){
			value = triples.get(property);
		} 
		value.append(rdftriples);
		triples.put(property, value);
	}
	
	public StringBuffer get(){
		StringBuffer value = new StringBuffer();
		for (Iterator<String> i = triples.keySet().iterator();i.hasNext();){
			value.append(triples.get(i.next()));
		}
		return value;
	}
	

}
