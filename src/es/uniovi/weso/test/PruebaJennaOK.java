package es.uniovi.weso.test;

import java.util.HashSet;
import java.util.Set;

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

public class PruebaJennaOK {

	private Set<String> resources = new HashSet<String>();
	private Set<String> subjects = new HashSet<String>();

	public void recorridoTest1() {

	//	System.out.print("recorridoTest1");
//		System.out.print("==============");
		Model m = ModelFactory.createDefaultModel();
		// m.read( "file:C:/rdf-prueba/datos.rdf" );
		m.read("file:C:/rdf-prueba/datos3.rdf");
		//m.read("http://datos.bcn.cl/recurso/cl/LEY/330/");
			
		// m.read( "file:C:/rdf-prueba/tipos-normas.rdf" );
		// m.write( System.out );

		StmtIterator iter = m.listStatements();

		while (iter.hasNext()) {

			Statement stmt = iter.nextStatement(); // get next statement
			Resource subject = stmt.getSubject(); // get the subject
			Property predicate = stmt.getPredicate(); // get the predicate
			RDFNode object = stmt.getObject(); // get the object

//			System.out.print(subject.toString());
//			System.out.print(" " + predicate.toString() + " ");
//			System.out.println(" " + object.toString() + " ");
//			System.out.println(object.toString() + " object.isAnon(): "
//					+ object.isAnon());
//			System.out.println(object.toString() + " object.isLiteral(): "
//					+ object.isLiteral());
//			System.out.println(object.toString() + " object.isResource(): "
//					+ object.isResource());
//			System.out.println(object.toString() + " object.isURIResource(): "
//					+ object.isURIResource());
//			System.out.println("");
//			System.out.println("");

			if (object.isResource()) {
				// resources.add((Resource) object);
			}

		}
	}

	@Test
	public void recorridoTest2() throws ParserConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {
		StringBuffer bufferOutput = new StringBuffer();
		Model m = ModelFactory.createDefaultModel();
		//m.read("file:C:/rdf-prueba/datos.rdf");
		//m.read("file:C:/rdf-prueba/datos3.rdf");
		m.read("http://datos.bcn.cl/recurso/cl/LEY/330/");
		// m.read( "file:C:/rdf-prueba/tipos-normas.rdf" );
		ResIterator iter = m.listSubjects();
		bufferOutput.append("<div class=\"rdfa-content\">");
		
		while (iter.hasNext()) {
			Resource subject = (Resource) iter.next();
			bufferOutput.append(recorre(subject));
		}
		bufferOutput.append("</div>");
//		System.out.println(bufferOutput.toString());
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
				
				if(object.toString().equals(subject.toString())){
					buffer.append("\"" + StringEscapeUtils.escapeXml(object.toString()) + "\" ");
				} else {
					StringBuffer temp = recorre(object.asResource());
					buffer.append(temp);
				}
			} else {
				buffer.append("\"" + StringEscapeUtils.escapeXml(object.toString()) + "\" ");
			}
			buffer.append("</li> ");
		}
		bufferOutput.append(buffer);
		bufferOutput.append("</ul></li></ul> ");


		return bufferOutput;
	}

}
