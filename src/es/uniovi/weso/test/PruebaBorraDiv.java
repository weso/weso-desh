package es.uniovi.weso.test;

import java.io.IOException;
import java.io.InputStream;

import org.htmlcleaner.XPatherException;
import org.junit.Test;

import es.uniovi.weso.util.StringUtil;


/** 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

public class PruebaBorraDiv {

	@Test
	public void test() throws IOException, XPatherException {
		InputStream inputStream = PruebaBorraDiv.class
		.getResourceAsStream("/html/rdfa-template.html");
		String html = StringUtil.slurp(inputStream);
	//	System.out.println(html);
		html = html.replaceAll("(?s)<!--.*?-->", "");
	//	System.out.println(html);
	}


}
