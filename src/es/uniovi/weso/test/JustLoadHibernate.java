package es.uniovi.weso.test;




import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JustLoadHibernate {

	private static final Log LOG = LogFactory.getLog(JustLoadHibernate.class);
	
	public static void main(String[] args) {
		EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("wesodesh");
		emf.close();
		LOG.debug("--> Si no hay excepciones todo va bien");
		LOG.debug("\n\t (O no hay ninguna clase mapeada)");
	}
	


}
