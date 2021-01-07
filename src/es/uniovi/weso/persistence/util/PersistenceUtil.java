package es.uniovi.weso.persistence.util;



import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PersistenceUtil {
	private static EntityManagerFactory emf = null;
	private static ThreadLocal<EntityManager> emThread = 
		new ThreadLocal<EntityManager>();
	private static final Log LOG  = LogFactory.getLog(PersistenceUtil.class);
	
	public static EntityManager getEntityManager() {
		 return getEmf().createEntityManager();
	}

	private static EntityManagerFactory getEmf() {
		if (emf == null){
			String persistenceUnitName = loadPersistentUnitName();
			emf = Persistence.createEntityManagerFactory(persistenceUnitName);
		}
		return emf;
	}

	public static EntityManager getCurrentEntityManager() {
		EntityManager entityManager = emThread.get();
		if (entityManager == null){
			entityManager = getEmf().createEntityManager();
			emThread.set(entityManager);
		}
		if (! entityManager.isOpen()){
			entityManager = getEmf().createEntityManager();
			emThread.set(entityManager);
		}
		return entityManager;
	}

	private static String loadPersistentUnitName() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(PersistenceUtil.class
					.getResourceAsStream("/META-INF/persistence.xml"));

			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("persistence-unit");
			
			return ((Element)nl.item(0)).getAttribute("name");

		} catch (ParserConfigurationException e1) {
			LOG.error(e1);
	//		throw new RuntimeException(e1);
		} catch (SAXException e1) {
			LOG.error(e1);
	//		throw new RuntimeException(e1);
		} catch (IOException e1) {
			LOG.error(e1);
	//		throw new RuntimeException(e1);
		}
		return null;
	}
}
