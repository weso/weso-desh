package es.uniovi.weso.core;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;

import es.uniovi.weso.business.EmptyResultException;
import es.uniovi.weso.configuration.ConfigurationLoader;
import es.uniovi.weso.rdfa.RDFaGenerator;
import es.uniovi.weso.rdfa.RDFaTemplate;


/** 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

public interface ApplicationManager {

	InputStream excutePost(String targetURL, String urlParameters)  throws EmptyResultException;

	ConfigurationLoader getConfig();

	String getContentType();

	String getContentTypeExtension();

	InputStream getGraph();

	Log getLOG();

	RDFaGenerator getRdfaGenerator();

	RDFaTemplate getRdfaTemplate();

	HttpServletRequest getRequest();

	String getSeeOtherURI();

	ServletContext getServletContext();

	String getUrlBase();
	
	String getResourceURI();

	boolean isConfigurationLoaded();

	void loadConfiguration();

	boolean seeOther();

	void setConfig(ConfigurationLoader config);

	void setOtherRepresentationLabel(String text);

	void setRdfaGenerator(RDFaGenerator rdfaGenerator);
	
	void setRdfaTemplate(RDFaTemplate rdfaTemplate);
	
	void setRequest(HttpServletRequest request);

	void setSeeOtherURI(String seeOtherURI);

	void setServletContext(ServletContext arg0);

	void setTitleLabel(String text);

}