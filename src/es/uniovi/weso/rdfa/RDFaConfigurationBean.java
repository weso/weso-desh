package es.uniovi.weso.rdfa;


/** 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

public class RDFaConfigurationBean {

	private String htmlPrefixes;
	private String pageTitle;
	private String urlBase;
	private String labelsMimeHead;
	private String content;
	private String otherRep;
	private String labelsMimeFooter;
	private String sparqlEndpoint;
	private String resourceURI;
	
	public RDFaConfigurationBean(final String htmlPrefixes, final String pageTitle,
			final String urlBase, final String labelsMimeHead, final String content,
			final String otherRep, final String labelsMimeFooter,
			final String sparqlEndpoint, final String resourceURI) {
		super();
		this.htmlPrefixes = htmlPrefixes;
		this.pageTitle = pageTitle;
		this.urlBase = urlBase;
		this.labelsMimeHead = labelsMimeHead;
		this.content = content;
		this.otherRep = otherRep;
		this.labelsMimeFooter = labelsMimeFooter;
		this.sparqlEndpoint = sparqlEndpoint;
		this.resourceURI=resourceURI;
	}

	public RDFaConfigurationBean() {
		 super();
	}

	public String getHtmlPrefixes() {
		return htmlPrefixes;
	}

	public void setHtmlPrefixes(final String htmlPrefixes) {
		this.htmlPrefixes = htmlPrefixes;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(final String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getUrlBase() {
		return urlBase;
	}

	public void setUrlBase(final String urlBase) {
		this.urlBase = urlBase;
	}

	public String getLabelsMimeHead() {
		return labelsMimeHead;
	}

	public void setLabelsMimeHead(final String labelsMimeHead) {
		this.labelsMimeHead = labelsMimeHead;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public String getOtherRep() {
		return otherRep;
	}

	public void setOtherRep(final String otherRep) {
		this.otherRep = otherRep;
	}

	public String getLabelsMimeFooter() {
		return labelsMimeFooter;
	}

	public void setLabelsMimeFooter(final String labelsMimeFooter) {
		this.labelsMimeFooter = labelsMimeFooter;
	}

	public String getSparqlEndpoint() {
		return sparqlEndpoint;
	}

	public void setSparqlEndpoint(final String sparqlEndpoint) {
		this.sparqlEndpoint = sparqlEndpoint;
	}

	public void setResourceURI(final String resourceURI) {
		this.resourceURI = resourceURI;
	}

	public String getResourceURI() {
		return resourceURI;
	}
	
	
}
