package es.uniovi.weso.rdfa;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

public class SparqlGenerator {

	private final Log LOG  = LogFactory.getLog(getClass());
	
	public static String generate(String loadedSparql, Map<String,String[]> parameters) {
		String sparql = loadedSparql;
		try{
		
		Pattern pattern = Pattern.compile("#\\$[ A-Za-z0-9_-]+\\$#");
		Matcher matcher = pattern.matcher(sparql);
		while(matcher.find()){
			String a = matcher.group();
			String b = (((String[]) parameters.get(matcher.group().replace("#$","").replace("$#","")))[0]).toString();
			
			sparql = sparql.replace(a,b );
		}
		} catch(Exception e){
			
		}
		//LOG.debug(sparql);
		return sparql;
	}
		
}


