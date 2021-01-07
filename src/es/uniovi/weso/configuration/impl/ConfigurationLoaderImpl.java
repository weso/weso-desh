package es.uniovi.weso.configuration.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import es.uniovi.weso.configuration.ConfigurationLoader;

/**
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

@SuppressWarnings("unchecked")
public final class ConfigurationLoaderImpl implements ConfigurationLoader,
		ServletContextAware {

	private final Log LOG = LogFactory.getLog(getClass());

	private Map<String, String> rules = new HashMap<String, String>();
	private Map<String, String> formats = new HashMap<String, String>();
	private Map<String, String> formatsLabels = new HashMap<String, String>();
	private Map<String, Map<String, Map<String, String>>> parametersMapped = new HashMap<String, Map<String, Map<String, String>>>();
	private Map<String, Map<String, String>> customParameterDivisors = new HashMap<String, Map<String, String>>();
	private Map<String, String> namespaces = new HashMap<String, String>();

	private String endpointURI;
	private String errorPage;
	private String defaultContentType;
	private String defaultExtension;
	private String urlBase;
	private @Autowired
	ServletContext servletContext;
	private boolean configurationIsLoaded;

	public ConfigurationLoaderImpl() {
		super();
		this.configurationIsLoaded = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#loadConfiguration()
	 */
	@Override
	public void loadConfiguration() throws JDOMException, IOException {

		LOG.debug("loading ConfigurationLoaderImpl");

		InputStream xmlFile = servletContext
				.getResourceAsStream("/WEB-INF/urlrewrite.xml");
		LOG.debug(xmlFile);
		SAXBuilder builder = new SAXBuilder(false);
		LOG.debug(builder);
		Document xmlDocument = builder.build(xmlFile);
		Element rootElement = xmlDocument.getRootElement();

		LOG.debug(rootElement.getChildren("rule"));

		List<Element> queries = rootElement.getChildren("rule");
		endpointURI = rootElement.getChildText("endpointURI");
		urlBase = rootElement.getChildText("urlBase");
		errorPage = rootElement.getChildText("errorPage");
		Iterator<Element> query = queries.iterator();
		while (query.hasNext()) {
			Element node = (Element) query.next();
			rules.put(node.getChildText("id"), node.getChildText("sparql"));
			if (node.getChildText("valueMapping") != null) {
				Element translationElement = node.getChild("valueMapping");
				List<Element> params = translationElement.getChildren();
				Iterator<Element> param = params.iterator();
				Map<String, Map<String, String>> paramsMap = new HashMap<String, Map<String, String>>();
				while (param.hasNext()) {
					Element p = (Element) param.next();
					List<Element> values = p.getChildren("value");
					Iterator<Element> valuesI = values.iterator();
					Map<String, String> valuesMap = new HashMap<String, String>();
					while (valuesI.hasNext()) {
						Element value = (Element) valuesI.next();
						valuesMap.put(value.getAttributeValue("key"),
								value.getText());
					}
					paramsMap.put(p.getAttributeValue("name"), valuesMap);
				}
				parametersMapped.put(node.getChildText("id"), paramsMap);
			}
			LOG.debug(node.getChildText("customParameterDivisor"));
			if (node.getChildText("customParameterDivisor") != null) {
				Element divisorElement = node
						.getChild("customParameterDivisor");
				List<Element> params = divisorElement.getChildren();
				Iterator<Element> param = params.iterator();
				Map<String, String> paramsMap = new HashMap<String, String>();
				while (param.hasNext()) {
					Element p = (Element) param.next();
					paramsMap.put(p.getAttributeValue("before"),
							p.getAttributeValue("divideWith"));
					LOG.debug(p.getAttributeValue("before"));
					LOG.debug(p.getAttributeValue("divideWith"));

				}
				customParameterDivisors.put(node.getChildText("id"), paramsMap);
			}

		}
		rootElement = xmlDocument.getRootElement();
		Element elementFormat = rootElement.getChild("formats");
		List<Element> formatsXml = elementFormat.getChildren("format");
		Iterator<Element> format = formatsXml.iterator();
		while (format.hasNext()) {
			Element node = (Element) format.next();
			formats.put(node.getChildText("extension"),
					node.getChildText("contentType"));
			formatsLabels.put(node.getChildText("extension"),
					node.getAttributeValue("label"));

			if (node.getChild("contentType").getAttributeValue("default") != null) {
				if (node.getChild("contentType").getAttributeValue("default")
						.equals("true")) {
					defaultContentType = node.getChildText("contentType");
				}
			}
			if (node.getChild("extension").getAttributeValue("default") != null) {
				if (node.getChild("extension").getAttributeValue("default")
						.equals("true")) {
					defaultExtension = node.getChildText("extension");
				}
			}

		}

		rootElement = xmlDocument.getRootElement();
		Element elementNamespace = rootElement.getChild("namespaces");
		List<Element> namespacesXml = elementNamespace.getChildren("namespace");
		Iterator<Element> namespace = namespacesXml.iterator();
		while (namespace.hasNext()) {
			Element node = (Element) namespace.next();
			namespaces.put(node.getChildText("uri"),
					node.getAttributeValue("prefix"));
			LOG.debug(node.getChildText("uri"));
		}

		configurationIsLoaded = true;
		LOG.debug("loaded ConfigurationLoaderImpl");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.ConfigurationLoader#getNamespacePrefix(java
	 * .lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#getNamespacePrefix
	 * (java.lang.String)
	 */
	@Override
	public String getNamespacePrefix(String namespace) {
		return namespaces.get(namespace);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.uniovi.weso.configuration.ConfigurationLoader#getNamespaces()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#getNamespaces()
	 */

	@Override
	public Set<String> getNamespaces() {
		return namespaces.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.uniovi.weso.configuration.ConfigurationLoader#getRules()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see es.uniovi.weso.configuration.impl.ConfigurationLoader#getRules()
	 */

	@Override
	public Map<String, String> getRules() {
		return rules;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.uniovi.weso.configuration.ConfigurationLoader#getEndpointURI()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#getEndpointURI()
	 */

	@Override
	public String getEndpointURI() {
		return endpointURI;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.ConfigurationLoader#getFormat(java.lang.
	 * String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#getFormat(java.
	 * lang.String)
	 */
	@Override
	public String getFormat(String format) {
		return formats.get(format);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.uniovi.weso.configuration.ConfigurationLoader#getFormats()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see es.uniovi.weso.configuration.impl.ConfigurationLoader#getFormats()
	 */

	@Override
	public Map<String, String> getFormats() {
		return formats;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.ConfigurationLoader#translateValue(java.
	 * lang.String, java.lang.String, java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#translateValue(
	 * java.lang.String, java.lang.String, java.lang.String)
	 */

	@Override
	public String[] translateValue(String patternId, String param, String value) {
		String[] parameters = new String[1];
		parameters[0] = parametersMapped.get(patternId).get(param).get(value);
		return parameters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.ConfigurationLoader#getCustomParameterDivisor
	 * (java.lang.String, java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see es.uniovi.weso.configuration.impl.ConfigurationLoader#
	 * getCustomParameterDivisor(java.lang.String, java.lang.String)
	 */

	@Override
	public String getCustomParameterDivisor(String patternId, String param) {
		if (customParameterDivisors.get(patternId) != null)
			return customParameterDivisors.get(patternId).get(param);
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#hasParams(java.
	 * lang.String)
	 */

	@Override
	public boolean hasParams(String patternId) {
		return parametersMapped.containsKey(patternId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#getParameterNamesById
	 * (java.lang.String)
	 */

	@Override
	public Set<String> getParameterNamesById(String patternId) {
		return parametersMapped.get(patternId).keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.uniovi.weso.configuration.impl.ConfigurationLoader#getErrorPage()
	 */

	@Override
	public String getErrorPage() {
		return errorPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#setNamespaces(java
	 * .util.Map)
	 */

	@Override
	public void setNamespaces(Map<String, String> namespaces) {
		this.namespaces = namespaces;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#getNamespacesMap()
	 */

	@Override
	public Map<String, String> getNamespacesMap() {
		return namespaces;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.uniovi.weso.configuration.impl.ConfigurationLoader#getUrlBase()
	 */

	@Override
	public String getUrlBase() {
		// TODO Auto-generated method stub
		return urlBase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#getFormatsLabels()
	 */

	@Override
	public Map<String, String> getFormatsLabels() {
		return formatsLabels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#isConfigurationLoaded
	 * ()
	 */
	@Override
	public boolean isConfigurationLoaded() {
		LOG.debug(configurationIsLoaded);
		return configurationIsLoaded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#getServletContext()
	 */

	public ServletContext getServletContext() {
		return servletContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#setServletContext
	 * (javax.servlet.ServletContext)
	 */

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.uniovi.weso.configuration.impl.ConfigurationLoader#getLOG()
	 */

	public Log getLOG() {
		return LOG;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#getDefaultContentType
	 * ()
	 */
	@Override
	public String getDefaultContentType() {
		return defaultContentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.uniovi.weso.configuration.impl.ConfigurationLoader#getDefaultExtension
	 * ()
	 */
	@Override
	public String getDefaultExtension() {
		return defaultExtension;
	}

}
