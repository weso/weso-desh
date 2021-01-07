package es.uniovi.weso.actions.linkeddata;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import es.uniovi.weso.core.ApplicationManager;

/**
 * Main controller of the application. 
 * 
 * This Struts2 Action responds all the linked data requests for generate RDF views.
 * 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @version 0.1
 * @since 2011-07-01
 */

public class LinkedDataController extends ActionSupport implements
ServletRequestAware, ApplicationAware {

	private static final long serialVersionUID = -9137765946247091093L;
	private HttpServletRequest request;
	private transient final Log LOG = LogFactory.getLog(getClass());
	private Map<String, Object> application;
	private ApplicationManager applicationManager;
	private InputStream resultStream;
	private String seeOtherURI;

	public String execute() throws Exception {

	
		String returnValue ="seeOther"; // NOPMD by Pancho on 11/3/11 12:02 AM

		if (!applicationManager.isConfigurationLoaded()) {
			applicationManager.loadConfiguration();	
		}
		LOG.debug("ahora a getGraph");
		applicationManager.setRequest(getRequest());
		applicationManager.setTitleLabel(getText("rdfaTitle"));
		applicationManager.setOtherRepresentationLabel(getText("otherRepresentations"));
		resultStream = applicationManager.getGraph();
		if (resultStream == null) {
			if (applicationManager.seeOther()) {
				setSeeOtherURI(applicationManager.getSeeOtherURI());
			}
		} else {
			setResultStream(resultStream);
			setSeeOtherURI("");	
			returnValue = applicationManager.getContentTypeExtension() + "Resp";
		} 
		return returnValue;
	}

	public Map<String, Object> getApplication() {
		return application;
	}

	public void setApplication(final Map<String, Object> application) {
		this.application = application;
	}

	public void setApplicationManager(final ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}

	public ApplicationManager getApplicationManager() {
		return applicationManager;
	}

	public void setResultStream(final InputStream xmlStream) {
		this.resultStream = xmlStream;
	}

	public InputStream getResultStream() {
		return resultStream;
	}

	public void setSeeOtherURI(final String seeOtherURI) {
		this.seeOtherURI = seeOtherURI;
	}

	public String getSeeOtherURI() {
		return seeOtherURI;
	}

	@Override
	public void setServletRequest(final HttpServletRequest arg0) {
		request = arg0;
	}

	public void setRequest(final HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}



}
