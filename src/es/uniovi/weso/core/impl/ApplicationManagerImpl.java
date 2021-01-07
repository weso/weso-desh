package es.uniovi.weso.core.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import es.uniovi.weso.business.EmptyResultException;
import es.uniovi.weso.configuration.ConfigurationLoader;
import es.uniovi.weso.core.ApplicationManager;
import es.uniovi.weso.rdfa.RDFaConfigurationBean;
import es.uniovi.weso.rdfa.RDFaGenerator;
import es.uniovi.weso.rdfa.RDFaTemplate;
import es.uniovi.weso.rdfa.SparqlGenerator;

/**
 * 
 * 
 * This class provides the main business rules of the application
 * 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 *
 */

public class ApplicationManagerImpl implements ServletContextAware, ApplicationManager {

	private transient final Log LOG  = LogFactory.getLog(getClass());
	private transient @Autowired HttpServletRequest request;
	private transient ServletContext servletContext;
	private String seeOtherURI;
	private String defaultMimeType;
	private String defaultContentTypeExtension;
	private String defaultEndpointURI;
	private RDFaGenerator rdfaGenerator;
	private String urlBase;
	private RDFaTemplate rdfaTemplate;
	private boolean configurationIsLoaded;
	private String titleLabel;
	private String otherRepresentationLabel;	
	private ConfigurationLoader config;
	private String resourceURI;


	public ApplicationManagerImpl() {
		super();
		configurationIsLoaded = false;
	}

	public void loadConfiguration(){
		LOG.debug("loading configuration ApplicationManagerImpl");
		if(!config.isConfigurationLoaded()){
			try {
				config.loadConfiguration();
			} catch (JDOMException e) {
				LOG.error("Exception loading XML: "+e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				LOG.error("Exception loading file: "+e.getMessage());
				e.printStackTrace();
			}
		}
		this.defaultEndpointURI = config.getEndpointURI();
		this.urlBase =config.getUrlBase();
		this.defaultMimeType = config.getDefaultContentType();
		this.defaultContentTypeExtension = config.getDefaultExtension();
		configurationIsLoaded = true;
		LOG.debug("OK Loaded");
	}

	public boolean isConfigurationLoaded(){
		LOG.debug(configurationIsLoaded);
		return configurationIsLoaded;
	}

	private InputStream processRequest()  {
		LOG.debug("dentro de processRequest");
		InputStream processedRequest = null;
		boolean mustBeTransformedToHtml = false;
		String[] formats = null;
		boolean found = false;

		LOG.debug("despues del iterator");
		LOG.debug(request.getParameter("nombre_pais"));
		try{
			formats = request.getHeader("accept").toString().split(",");

			LOG.debug(request.getHeader("accept").toString());


			for (int i = 0; i < formats.length && !found; i++) {
				if (config.getFormats()
						.containsValue(formats[i])) {
					found = true;
					defaultMimeType = formats[i];
				}
			}
		} catch (Exception e){
			defaultMimeType = config.getDefaultContentType();
			LOG.debug(defaultMimeType);
		}

		if (!found) {
			defaultMimeType = config.getDefaultContentType();
		}

		if (request.getParameter("format") == null) {

			negotiateContent();
		} else {

			defaultContentTypeExtension = request.getParameter("format").toString();
			LOG.debug(defaultContentTypeExtension);
			defaultMimeType = config.getFormats().get(defaultContentTypeExtension);
			LOG.debug(defaultMimeType);
			if(defaultContentTypeExtension.equals("html")){

				mustBeTransformedToHtml = true;

			}
			if (config.getFormats()
					.containsKey(defaultContentTypeExtension)) {
				try {
					if(mustBeTransformedToHtml){
						processedRequest = new ByteArrayInputStream(generateRDFa().getBytes());
					} else {
						processedRequest = excutePost(defaultEndpointURI, ensambleGetQuery(loadSparql()));	
					}



				} catch (UnsupportedEncodingException e) {
					LOG.error(e.getMessage());
				} catch (EmptyResultException e) {
					seeOtherURI = "error.jsp";
				}
			} else {
				// redirigir a mensaje de url mal formada
				// TODO Auto-generated method stub
				
				LOG.error("URL mal formada");
			}

		}
		return processedRequest;
	}

	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#getUrlBase()
	 */
	@Override
	public String getUrlBase(){
		return urlBase;
	}

	private String generateRDFa() throws UnsupportedEncodingException, EmptyResultException {


		defaultMimeType = "application/rdf+xml";
		defaultContentTypeExtension ="rdf";
		String sparql = ensambleGetQuery(loadSparql());
		InputStream results = excutePost(defaultEndpointURI, sparql);	
		String formattedResults = null;
		try {

			formattedResults = rdfaGenerator.generate(results);

		} catch (TransformerFactoryConfigurationError e) {

			e.printStackTrace();
		}
		defaultMimeType = "application/xhtml+xml";
		defaultContentTypeExtension ="html";

		return applyRDFaTemplate(formattedResults);
	}


	private String applyRDFaTemplate( String content){

		StringBuffer resUri = new StringBuffer();
		resUri.append(getUrlBase());
		resUri.append(regenerateURI());
		if(!rdfaTemplate.isConfigurationLoaded()){
			rdfaTemplate.loadConfiguration();
		}
		LOG.debug("OK rdfaTemplate.loadConfiguration");
		LOG.debug(rdfaGenerator.getHtmlPrefixes());

		RDFaConfigurationBean bean = new RDFaConfigurationBean(
				rdfaGenerator.getHtmlPrefixes(),
				this.titleLabel,
				getUrlBase(),
				rdfaTemplate.getAlternatesContentTypesHead(resUri.toString()),
				content,
				this.otherRepresentationLabel,
				rdfaTemplate.getAlternatesContentTypesFooter(resUri.toString()),
				defaultEndpointURI,
				resourceURI
				
		);
		//LOG.debug(rdfaTemplate.getView(bean));
		return rdfaTemplate.getView(bean);

	}



	private String loadSparql() {
		String sparqlQuery = config
		.getRules()
		.get(request.getParameter("pattern_number")
				.toString());
		if (config.hasParams(
				request.getParameter("pattern_number").toString())) {
			translateParameters(
					request.getParameter("pattern_number")
					.toString(), request
					.getParameterMap());
		}
		sparqlQuery = SparqlGenerator.generate(sparqlQuery,
				request
				.getParameterMap());
		LOG.debug(request.getParameter("pattern_number"));

		LOG.debug(sparqlQuery);

		return sparqlQuery;
	}

	private String ensambleGetQuery(String sparqlQuery)
	throws UnsupportedEncodingException {

		String query = "query="	+ URLEncoder.encode(sparqlQuery, "UTF-8");
		String format = "format=" + URLEncoder.encode(config.getFormat(defaultContentTypeExtension), "UTF-8");

		LOG.debug(query);
		LOG.debug(format);

		String urlParameters =  format + "&" + query ;

		LOG.debug(urlParameters);
		return urlParameters;
	}

	private void translateParameters(String patternId,
			Map<String, String[]> requestParameters) {

		Iterator<String> i = config
		.getParameterNamesById(patternId).iterator();

		while (i.hasNext()) {
			String key = (String) i.next();
			requestParameters.put(
					key,
					config.translateValue(
							patternId, key, requestParameters.get(key)[0]));
		}

	}

	private StringBuffer regenerateURI(){
		StringBuffer uri = new StringBuffer();
		String getQuery = request.getQueryString();
		LOG.debug(getQuery);
		String[] getParams = getQuery.split("&");
		for (int i = 0; i < getParams.length; i++) {
			String key = (getParams[i].split("="))[0];

			if (!key.equals("pattern_number") && !key.equals("format")) {
				LOG.debug(getDivisor(request.getParameter("pattern_number"),key));
				LOG.debug(key);
				LOG.debug(request.getParameter(key));
				LOG.debug(request.getParameter("pattern_number"));
				if(getDivisor(request.getParameter("pattern_number"),key) == null){
					uri.append(request.getParameter(key)).append("/");	
				} else {
					uri.append(getDivisor(request.getParameter("pattern_number"),key))
					.append(request.getParameter(key));	
					LOG.debug(seeOtherURI);
				}
			}

		}
		String uriTemp = uri.toString();
		if(uriTemp.contains("/@")){
			uriTemp=uriTemp.replace("/@", "@");

			uri = new StringBuffer(uriTemp);
			uri.append("/");
		}
		LOG.debug(uri);
		
		
		setResourceURI(this.urlBase+uri.toString());
		return uri;
	}
	private void negotiateContent() {
		// TODO Auto-generated method stub

		this.seeOtherURI = "";
		String getQuery = request.getQueryString();
		LOG.debug(getQuery);
		if(getQuery==null){
			LOG.debug("Entrando a la URL raiz");
			seeOtherURI = "error.jsp";
		} else {
			String[] getParams = getQuery.split("&");
			for (int i = 0; i < getParams.length; i++) {
				String key = (getParams[i].split("="))[0];

				if (!key.equals("pattern_number")) {
					LOG.debug(getDivisor(request.getParameter("pattern_number"),key));
					LOG.debug(key);
					LOG.debug(request.getParameter(key));
					LOG.debug(request.getParameter("pattern_number"));
					if(getDivisor(request.getParameter("pattern_number"),key) == null){
						seeOtherURI += "/" + request.getParameter(key);	
					} else {
						seeOtherURI += getDivisor(request.getParameter("pattern_number"),key) + request.getParameter(key);	
						LOG.debug(seeOtherURI);
					}
				}

			}
			Set<String> keys = config.getFormats().keySet();
			Iterator<String> i = keys.iterator();
			while (i.hasNext()) {
				String key = (String) i.next();
				if (defaultMimeType.equals(config.getFormats().get(key))) {
					seeOtherURI += "/datos." + key;
					break;
				}
			}

			String baseURL = request.getRequestURL().toString();
			baseURL = baseURL.replace("/LinkedDataController.action", "");
			seeOtherURI = baseURL + seeOtherURI;
			LOG.debug("seeOtherURI: " + seeOtherURI);
		}

	}

	private String getDivisor(String string, String parameter) {
		return config.getCustomParameterDivisor(string, parameter);
	}


	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#getGraph()
	 */
	@Override
	public InputStream getGraph() {
		// TODO Auto-generated method stub
		// luego implementar cache
		LOG.debug("dentro de getGraph");
		return processRequest();
	}

	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#excutePost(java.lang.String, java.lang.String)
	 */
	@Override
	public InputStream excutePost(String targetURL, String urlParameters) throws EmptyResultException {
		URL url;
		HttpURLConnection connection = null;
		try {
			LOG.debug(targetURL);
			LOG.debug(urlParameters);
			url = new URL(targetURL);

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type",
			"application/x-www-form-urlencoded;charset=utf-8");

			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));
			
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			if (connection != null) {
				connection.disconnect();
			}
			if(response.toString().getBytes().length==0 || response.toString().getBytes().length==168)
			{
				throw new EmptyResultException();
			}	
			return new ByteArrayInputStream(response.toString().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			if (connection != null) {
				connection.disconnect();
			}
			return null;
		} 
	}

	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#setSeeOtherURI(java.lang.String)
	 */
	@Override
	public void setSeeOtherURI(String seeOtherURI) {
		this.seeOtherURI = seeOtherURI;
	}

	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#getSeeOtherURI()
	 */
	@Override
	public String getSeeOtherURI() {
		return seeOtherURI;
	}

	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#getContentType()
	 */
	@Override
	public String getContentType() {
		return defaultMimeType;
	}

	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#getContentTypeExtension()
	 */
	@Override
	public String getContentTypeExtension() {
		return defaultContentTypeExtension;
	}


	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#setServletContext(javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;

	}


	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#getRdfaGenerator()
	 */
	@Override
	public RDFaGenerator getRdfaGenerator() {
		return rdfaGenerator;
	}



	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#getRequest()
	 */
	@Override
	public HttpServletRequest getRequest() {
		return request;
	}



	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#setRequest(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}



	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#setRdfaGenerator(es.uniovi.weso.rdfa.RDFaGenerator)
	 */
	@Override
	public void setRdfaGenerator(RDFaGenerator rdfaGenerator) {
		this.rdfaGenerator = rdfaGenerator;
	}



	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#getLOG()
	 */
	@Override
	public Log getLOG() {
		return LOG;
	}



	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#getServletContext()
	 */
	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}




	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#getConfig()
	 */
	@Override
	public ConfigurationLoader getConfig() {
		return config;
	}



	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#setConfig(es.uniovi.weso.configuration.ConfigurationLoader)
	 */
	@Override
	public void setConfig(ConfigurationLoader config) {
		this.config = config;
	}



	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#getRdfaTemplate()
	 */
	@Override
	public RDFaTemplate getRdfaTemplate() {
		return rdfaTemplate;
	}



	/* (non-Javadoc)
	 * @see es.uniovi.weso.core.impl.ApplicationManager#setRdfaTemplate(es.uniovi.weso.rdfa.RDFaTemplate)
	 */
	@Override
	public void setRdfaTemplate(RDFaTemplate rdfaTemplate) {
		this.rdfaTemplate = rdfaTemplate;
	}

	@Override
	public boolean seeOther() {
		return !seeOtherURI.equals("");
	}

	@Override
	public void setTitleLabel(String text) {
		this.titleLabel=text;
	}

	@Override
	public void setOtherRepresentationLabel(String text) {
		this.otherRepresentationLabel=text;
	}


	public String getDefaultMimeType() {
		return defaultMimeType;
	}

	public void setDefaultMimeType(final String defaultMimeType) {
		this.defaultMimeType = defaultMimeType;
	}

	public void setResourceURI(final String uri) {
		this.resourceURI = uri;
		if(resourceURI.charAt(resourceURI.length()-1)=='/'){
			resourceURI = resourceURI.substring(0, uri.length()-1);	
		}
		
	}

	public String getResourceURI() {
		return resourceURI;
	}

}
