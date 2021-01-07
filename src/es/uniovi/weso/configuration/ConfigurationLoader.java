package es.uniovi.weso.configuration;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.jdom.JDOMException;

/** 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

public interface ConfigurationLoader {

	String getCustomParameterDivisor(String patternId, String param);

	String getDefaultContentType();

	String getDefaultExtension();

	String getEndpointURI();

	String getErrorPage();

	String getFormat(String format);

	Map<String, String> getFormats();

	Map<String, String> getFormatsLabels();

	String getNamespacePrefix(String namespace);

	Set<String> getNamespaces();

	Map<String, String> getNamespacesMap();

	Set<String> getParameterNamesById(String patternId);

	Map<String, String> getRules();

	String getUrlBase();

	boolean hasParams(String patternId);

	boolean isConfigurationLoaded();

	void loadConfiguration() throws JDOMException, IOException;
	
	void setNamespaces(Map<String, String> namespaces);
	
	String[] translateValue(String patternId, String param, String value);

}