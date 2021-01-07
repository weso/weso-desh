package es.uniovi.weso.rdfa.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import es.uniovi.weso.configuration.ConfigurationLoader;
import es.uniovi.weso.rdfa.RDFaGenerator;
import es.uniovi.weso.rdfa.types.PlainLiteral;
import es.uniovi.weso.util.StringUtil;

/**
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @since 2011-07-01
 */

public class RDFaGeneratorImpl implements RDFaGenerator {

	private int deepLevel;
	private StringBuffer rdfaTriples;
	private final Log LOG = LogFactory.getLog(getClass());
	private ConfigurationLoader config;
	private int nodeNumber = 0;

	public RDFaGeneratorImpl() {
		super();
		deepLevel = 0;
	}

	public String generate(InputStream model) {
		String rdfa = null;
		try {
			LOG.debug("entrando a translateToRdfa");
			rdfa = this.translateToRdfa(StringUtil.slurp(model));
			nodeNumber = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e);
			e.printStackTrace();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			LOG.error("Exception for Parser configuration in RDFa generation");
		} catch (TransformerFactoryConfigurationError e) {
			LOG.error("Exception in RDFaGeneratorImpl.translateToRdfa");
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
			LOG.error("Exception in RDFaGeneratorImpl.translateToRdfa");
		}
		return rdfa;
	}

	public String translateToRdfa(String data)
			throws ParserConfigurationException,
			TransformerFactoryConfigurationError, TransformerException,
			UnsupportedEncodingException {
		rdfaTriples = new StringBuffer(1000);
		final StringBuffer bufferOutput = new StringBuffer(1000);
		RDFDatatype rtype = PlainLiteral.PLAIN_LITERAL_TYPE;
		TypeMapper.getInstance().registerDatatype(rtype);
		Model model = ModelFactory.createDefaultModel();

		byte[] latin1;
		latin1 = data.getBytes("UTF-8");
		byte[] utf8 = new String(latin1, "UTF-8").getBytes("UTF-8");
		LOG.debug("model.read:");
		LOG.debug("data:" + data);

		model.read(new ByteArrayInputStream(utf8), null);

		ResIterator iter = model.listSubjects();
		bufferOutput.append("<div class=\"rdfa-content\">");

		/* PUT HERE SOME HEADER */
		bufferOutput.append("<h1>WESO DESH - Linked Data Frontend</h1>");
		/* Only for BCN */

		while (iter.hasNext()) {
			Resource subject = (Resource) iter.next();
			bufferOutput.append("<div class=\"entity\">");
			bufferOutput.append(transformRecursively(subject));
			bufferOutput.append("</div>");

		}
		bufferOutput.append("</div>");
		bufferOutput.append(rdfaTriples);
		return bufferOutput.toString();
	}

	private StringBuffer transformRecursively(Resource subject) {
		deepLevel++;

		StringBuffer bufferOutput = new StringBuffer();

		StmtIterator iter = subject.listProperties();
		RDFaBuffer rdfaBuffer = new RDFaBuffer();
		if (iter.hasNext()) {

			topWrapper(subject, bufferOutput);

			while (iter.hasNext()) {
				StringBuffer buffer = new StringBuffer();
				deepLevel++;
				Statement stmt = iter.nextStatement();
				Property predicate = stmt.getPredicate();
				RDFNode object = stmt.getObject();

				buffer.append("<li class=\"level-" + deepLevel + "\" > ");
				if (object.isResource()) {

					if (object.toString().equals(subject.toString())) {

						rdfaTriples.append(divForSubject(subject,
								divForProperty(predicate, object)));

						buffer.append(linkForProperty(predicate, object));

					} else {
						rdfaTriples.append(divForSubject(subject,
								divForResource(predicate, object)));
						buffer.append(linkForResource(predicate));

						buffer.append(transformRecursively(object.asResource()));

					}
				} else {

					buffer.append(linkForProperty(predicate, object));

					rdfaTriples.append(divForSubject(subject,
							divForProperty(predicate, object)));

				}
				buffer.append("</li>").append(
						System.getProperty("line.separator"));
				rdfaBuffer.add(predicate.toString(), buffer);
				deepLevel--;
			}

			bufferOutput.append(rdfaBuffer.get());
			bottomWrapper(bufferOutput);
		} else {
			bufferOutput.append(finalNodeWrapper(subject));
		}

		deepLevel--;
		return bufferOutput;
	}

	public StringBuffer linkForResource(Property property) {
		return new StringBuffer().append("<a href=\"")
				.append(StringEscapeUtils.escapeXml(property.toString()))
				.append("\"  > ").append(replaceByPrefix(property.toString()))
				.append("</a> = ");
	}

	public StringBuffer linkForProperty(Property property, RDFNode value) {

		StringBuffer type = new StringBuffer("");
		StringBuffer objectValue = new StringBuffer("");
		try {
			if (value.asNode().isLiteral()) {

				objectValue.append(value.asLiteral().getValue());
				if (value.asLiteral().getDatatypeURI() != null) {
					type.append("<span class=\"class-type\">^^")
							.append(replaceByPrefix(value.asLiteral()
									.getDatatypeURI())).append("</span>");
				}

			} else {
				objectValue.append(value.toString());
			}
		}/*
		 * catch (com.hp.hpl.jena.datatypes.DatatypeFormatException e) {
		 * LOG.debug(e); LOG.debug(
		 * "Resuelve el bug de virtuoso: http://sourceforge.net/mailarchive/message.php?msg_id=28739713 "
		 * );
		 * objectValue.append(value.asLiteral().getValue().toString().substring
		 * (0, 3)); if (value.asLiteral().getDatatypeURI() != null) {
		 * type.append("<span class=\"class-type\">^^")
		 * .append(replaceByPrefix(value.asLiteral().getDatatypeURI()))
		 * .append("</span>"); }
		 * 
		 * }
		 */
		catch (Exception e) {
			LOG.debug(e.toString());
			LOG.error(e);
			e.printStackTrace();
		}

		return new StringBuffer()
				.append("<a href=\"")
				.append(StringEscapeUtils.escapeXml(property.toString()))
				.append("\"  > ")
				.append(replaceByPrefix(property.toString()))
				.append("</a> = <span class=\"value\">\"")
				.append(StringEscapeUtils.escapeXml(replaceByPrefix(objectValue
						.toString()))).append("\"").append(type)
				.append("</span> ")
				.append(System.getProperty("line.separator"));

	}

	public StringBuffer divForSubject(Resource subject, StringBuffer value) {
		return new StringBuffer("<div ")
				.append("xmlns=\"http://www.w3.org/1999/xhtml\"  about=\"")
				.append(StringEscapeUtils.escapeXml(subject.toString()))
				.append("\">").append(System.getProperty("line.separator"))
				.append(value).append("</div>")
				.append(System.getProperty("line.separator"));
	}

	public StringBuffer divForResource(Property property, RDFNode object) {
		return new StringBuffer("<div rel=\"")
				.append(replaceByPrefix(property.toString()))
				.append("\" resource=\"")
				.append(StringEscapeUtils.escapeXml(object.toString()))
				.append("\" />").append(System.getProperty("line.separator"));
	}

	public StringBuffer divForProperty(Property property, RDFNode object) {

		StringBuffer type = new StringBuffer("");
		try {
			if (object.asNode().isLiteral()) {
				if (object.asLiteral().getDatatypeURI() != null) {
					type.append("datatype=\"")
							.append(replaceByPrefix(object.asLiteral()
									.getDatatypeURI())).append("\"");
				}
			}
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
		}
		try {
			return new StringBuffer("<div property=\"")
					.append(replaceByPrefix(property.toString()))
					.append("\" content=\"")
					.append(StringEscapeUtils.escapeXml(object.asLiteral()
							.getValue().toString())).append("\" ").append(type)
					.append(" />").append(System.getProperty("line.separator"));
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
			return null;
		}
	}

	private void topWrapper(Resource subject, StringBuffer bufferOutput) {
		bufferOutput
				.append("<ul> ")
				.append("<li class=\"level-" + deepLevel + "\" >")
				.append(System.getProperty("line.separator"))
				.append("<a href=\"")
				.append(StringEscapeUtils.escapeXml(subject.toString()))
				.append("\" > ")
				.append(StringEscapeUtils.escapeXml(replaceByPrefix(subject
						.toString()))).append("</a>")
				.append(System.getProperty("line.separator")).append("<ul>")
				.append(System.getProperty("line.separator"));
	}

	private void bottomWrapper(StringBuffer bufferOutput) {
		bufferOutput.append("</ul>")
				.append(System.getProperty("line.separator")).append("</li>")
				.append(System.getProperty("line.separator")).append("</ul> ")
				.append(System.getProperty("line.separator"));
	}

	private StringBuffer finalNodeWrapper(Resource subject) {
		String div = "div" + nodeNumber;
		nodeNumber++;
		return new StringBuffer()
				.append("<a class=\"resource\" href=\"")
				.append(StringEscapeUtils.escapeXml(subject.toString()))
				.append("\" > ")
				.append(StringEscapeUtils.escapeXml(replaceByPrefix(subject
						.toString())))
				.append("</a> ")
				.append(addAjaxExpender(div,
						StringEscapeUtils.escapeXml(subject.toString())))
				.append(System.getProperty("line.separator"));
	}

	private String addAjaxExpender(String div, String escapeXml) {
		/*
		 * return "<img onclick=\"loadTriples('"+div+"','"+escapeXml+
		 * "');\" src=\"http://localhost:8080/weso-desh/images/more.png\" />" +
		 * "<div id=\""+div+"\"></div>";
		 */
		return "";
	}

	public String replaceByPrefix(String nsName) {
		String value = nsName;
		Iterator<String> i = config.getNamespacesMap().keySet().iterator();
		while (i.hasNext()) {
			String namespace = (String) i.next();
			String prefix = config.getNamespacesMap().get(namespace);
			value = value.replaceAll(namespace, prefix + ":");
		}
		return value;
	}

	public String getHtmlPrefixes() {
		StringBuffer prefixes = new StringBuffer();

		Iterator<String> i = config.getNamespacesMap().keySet().iterator();
		while (i.hasNext()) {
			String namespace = (String) i.next();
			String prefix = config.getNamespacesMap().get(namespace);
			prefixes.append(" xmlns:").append(prefix).append("=\"")
					.append(namespace).append("\" ");
		}
		return prefixes.toString();

	}

	public void setConfig(ConfigurationLoader config) {
		this.config = config;
	}

	public ConfigurationLoader getConfig() {
		return config;
	}

}
