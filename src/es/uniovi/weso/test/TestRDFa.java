package es.uniovi.weso.test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;


/** 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

public class TestRDFa {


	@Test
	public void recorridoTest2() throws ParserConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {
		StringBuffer bufferOutput = new StringBuffer();
		Model m = ModelFactory.createDefaultModel();
		//m.read("file:C:/rdf-prueba/datos.rdf");
		// m.read("http://datos.bcn.cl/recurso/cl/DTO/ministerio-de-obras-publicas_fiscalia/2011-06-01/863-EXENTO/datos.rdf");
		
		 m.read( "http://datos.bcn.cl/recurso/cl/LEY/ministerio-del-interior_subsecretaria-del-interior/2003-05-09/19871/es@2009-06-11/datos.rdf" );
		ResIterator iter = m.listSubjects();
		bufferOutput.append("<div class=\"rdfa-content\">");
		while (iter.hasNext()) {
			Resource subject = (Resource) iter.next();
			bufferOutput.append(recorre(subject));
		}
		bufferOutput.append("</div>");
	//	System.out.println(bufferOutput.toString());
		//System.out.println(new XmlFormatter().format(bufferOutput.toString()));

		
	}

	public StringBuffer recorre(Resource subject)  {
		StringBuffer buffer = new StringBuffer();
		StringBuffer bufferOutput = new StringBuffer();

		bufferOutput.append("<ul> ");
		bufferOutput.append("<li about=\"" + StringEscapeUtils.escapeXml(subject.toString()) + "\"> ");
		bufferOutput.append("<a href=\"" + StringEscapeUtils.escapeXml(subject.toString()) + "\"> "
				+ StringEscapeUtils.escapeXml(subject.toString()) + "</a> ");
		bufferOutput.append("<ul> ");

		StmtIterator iter = subject.listProperties();

		while (iter.hasNext()) {

			Statement stmt = iter.nextStatement();
			Property predicate = stmt.getPredicate();
			RDFNode object = stmt.getObject();

			buffer.append("<li rel=\"" + StringEscapeUtils.escapeXml(predicate.toString()));
			buffer.append("\" content=\"" + StringEscapeUtils.escapeXml(object.toString()) + "\"> ");
			buffer.append("<a href=\"" + StringEscapeUtils.escapeXml(predicate.toString()) + "\"> ");
			buffer.append(predicate.toString() + "</a> ");

			if (object.isResource()) {
				StringBuffer buffer2 = recorre(object.asResource());
				buffer.append(buffer2);
			} else {
				buffer.append("<strong>" + StringEscapeUtils.escapeXml(object.toString()) + "</strong> ");
			}
			buffer.append("</li> ");
		}
		bufferOutput.append(buffer);
		bufferOutput.append("</ul> ");
		bufferOutput.append("</li> ");
		bufferOutput.append("</ul> ");

		return bufferOutput;
	}

}
