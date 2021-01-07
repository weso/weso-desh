package es.uniovi.weso.util;

import java.io.IOException;
import java.io.InputStream;

import org.mozilla.universalchardet.UniversalDetector;


/** 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

public class StringUtil {
	
	public static String slurp (InputStream in) throws IOException {
	    StringBuffer out = new StringBuffer();
	    byte[] b = new byte[4096];
	    for (int n; (n = in.read(b)) != -1;) {
	        out.append(new String(b, 0, n));
	    }
	    return out.toString();
	}

	public static InputStream getStream(String fileName) {
		InputStream xslStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + fileName);
		if(xslStream==null){
			xslStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName); 
		}
		return xslStream;
	}

	public static String getEncoding(InputStream string) throws IOException {
		byte[] buf = new byte[4096];
		// (1)
		UniversalDetector detector = new UniversalDetector(null);

		// (2)
		int nread;
		while ((nread = string.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}

		// (3)
		detector.dataEnd();

		// (4)
		String encoding = detector.getDetectedCharset();
		if (encoding == null) {
			encoding = "UTF-8";
		}
		// (5)
		detector.reset();

		return encoding;
	}
}
