package es.uniovi.weso.rdfa;

import java.io.InputStream;


/** 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

public interface RDFaGenerator {

	String generate(InputStream model);
	
	String getHtmlPrefixes();
	
}
