package es.uniovi.weso.rdfa.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.uniovi.weso.configuration.ConfigurationLoader;
import es.uniovi.weso.rdfa.RDFaTemplate;
import es.uniovi.weso.rdfa.RDFaConfigurationBean;
import es.uniovi.weso.util.StringUtil;


/** 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

public class RDFaTemplateImpl implements Serializable, RDFaTemplate{

	private static final long serialVersionUID = 8346549487868173473L;
	private String template;
	private RDFaConfigurationBean bean;
	private final Log LOG  = LogFactory.getLog(getClass());
	private Map<String, String> formats;
	private Map<String, String> formatsLabels;
	private ConfigurationLoader config;
	private boolean configurationIsLoaded;
	
	
	private void loadHtmlTemplate() throws IOException{
		LOG.debug("loadHtmlTemplate");
		InputStream inputStream = RDFaTemplateImpl.class
		.getResourceAsStream("/es/uniovi/weso/html/rdfa-template.html");
		template = StringUtil.slurp(inputStream);
		template = template.replaceAll("(?s)<!--.*?-->", "");
		LOG.debug("loadHtmlTemplate end");
	}
	
	/* (non-Javadoc)
	 * @see es.uniovi.weso.rdfa.IRDFaTemplate#getView(es.uniovi.weso.rdfa.RDFaConfigurationBean)
	 */
	@Override
	public String getView(RDFaConfigurationBean bean){
		this.setBean(bean);
		return this.getView();
	}
	
	/* (non-Javadoc)
	 * @see es.uniovi.weso.rdfa.IRDFaTemplate#getView()
	 */
	@Override
	public String getView(){		
		String view = template.replaceAll("###HTML_PREFIXES###", 
				bean.getHtmlPrefixes());
		view = view.replaceAll("###PAGE_TITLE###", bean.getPageTitle());
		view = view.replaceAll("###RESOURCE_URI###", bean.getResourceURI());
		view = view.replaceAll("###URL_BASE###", bean.getUrlBase());
		view = view.replaceAll("###ALTERNATES_CONTENT_TYPES_HEAD###", 
				bean.getLabelsMimeHead());
		view = view.replaceAll("###CONTENT###", Matcher.quoteReplacement(bean.getContent()));
		view = view.replaceAll("###OTHER_REPRESENTATIONS###", 
				bean.getOtherRep());
		view = view.replaceAll("###ALTERNATES_CONTENT_TYPES_FOOTER###", 
				bean.getLabelsMimeFooter());
		view = view.replaceAll("###SPARQL_ENDPOINT###", 
				bean.getSparqlEndpoint());
		return view;
	}
	
	
	/* (non-Javadoc)
	 * @see es.uniovi.weso.rdfa.IRDFaTemplate#getAlternatesContentTypesHead(java.lang.String)
	 */
	@Override
	public String getAlternatesContentTypesHead(String resUri) {
		final StringBuffer value = new StringBuffer(1000);

		Iterator<String> i = formats.keySet().iterator();
		while (i.hasNext()) {
			String extension = (String) i.next();
			String contentType =  formats.get(extension);
			
			value.append("<link rel=\"alternate\" type=\"")
					.append(contentType)
					.append("\" href=\"")
					.append(resUri)
					.append("datos.")
					.append(extension)
					.append("\" title=\"Structured Descriptor Document (")
					.append(extension)  
					.append(" format)\" />");      
		}
		return value.toString();
	}
	
	/* (non-Javadoc)
	 * @see es.uniovi.weso.rdfa.IRDFaTemplate#getAlternatesContentTypesFooter(java.lang.String)
	 */
	@Override
	public String getAlternatesContentTypesFooter(String resUri) {
		StringBuffer value = new StringBuffer();

		Iterator<String> i = formatsLabels.keySet().iterator();
		while (i.hasNext()) {
			String extension = (String) i.next();
			String label = formatsLabels.get(extension);
			value.append("<li><a href=\"")
					.append(resUri)
					.append("datos.")
					.append(extension)
					.append("\" title=\"Structured Descriptor Document (")
					.append(label)  
					.append(" format)\" >")
					.append(label)
					.append("</a></li>");	
		}
		return value.toString();
	}

	@Override
	public void loadConfiguration() {
		formats = config.getFormats();
		formatsLabels = config.getFormatsLabels();
		try {
			loadHtmlTemplate();
		} catch (IOException e) {
			LOG.error("Error loading HTML Template");
			LOG.error(e.toString());
			e.printStackTrace();
		}		
	}

	@Override
	public boolean isConfigurationLoaded() {
		return this.configurationIsLoaded;
	}
	
	public ConfigurationLoader getConfig() {
		return config;
	}

	public void setConfig(ConfigurationLoader config) {
		this.config = config;
	}

	public Log getLOG() {
		return LOG;
	}

	public RDFaTemplateImpl() {
		super();
		configurationIsLoaded = false;
	}
	
	/* (non-Javadoc)
	 * @see es.uniovi.weso.rdfa.IRDFaTemplate#setTemplate(java.lang.String)
	 */
	@Override
	public void setTemplate(String template) {
		this.template = template;
	}

	/* (non-Javadoc)
	 * @see es.uniovi.weso.rdfa.IRDFaTemplate#getTemplate()
	 */
	@Override
	public String getTemplate() {
		return template;
	}

	/* (non-Javadoc)
	 * @see es.uniovi.weso.rdfa.IRDFaTemplate#getBean()
	 */
	@Override
	public RDFaConfigurationBean getBean() {
		return bean;
	}

	/* (non-Javadoc)
	 * @see es.uniovi.weso.rdfa.IRDFaTemplate#setBean(es.uniovi.weso.rdfa.RDFaConfigurationBean)
	 */
	@Override
	public void setBean(RDFaConfigurationBean bean) {
		this.bean = bean;
	}

}
