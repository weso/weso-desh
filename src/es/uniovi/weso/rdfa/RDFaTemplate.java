package es.uniovi.weso.rdfa;


/** 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

public interface RDFaTemplate {

	String getAlternatesContentTypesFooter(String resUri);

	String getAlternatesContentTypesHead(String resUri);

	RDFaConfigurationBean getBean();

	String getTemplate();

	String getView();

	String getView(RDFaConfigurationBean bean);

	boolean isConfigurationLoaded();

	void loadConfiguration();
	
	void setBean(RDFaConfigurationBean bean);
	
	void setTemplate(String template);

}