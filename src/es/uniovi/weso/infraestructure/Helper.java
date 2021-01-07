package es.uniovi.weso.infraestructure;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Esta clase solo tiene métodos de utilidad usados desde @see Factories
 * 
 * @author alb
 *
 */
public class Helper {

	private static final Log LOG  = LogFactory.getLog(Helper.class);
	
	/**
	 * Devuelve instancia de la clase factory deseada. Crea un objeto a partir
	 * del nombre de la clase
	 * 
	 * @param file  
	 * 		El fichero de propiedades 
	 * @param factoryType 
	 * 		El nombre de la propiedad en el fichero de proerties
	 * @return 
	 * 		El objeto de la clase factoria adecuada
	 */
	static Object createFactory(String file, String factoryType) {
		
		String className = loadProperty(file, factoryType);
		try {
			Class<?> clazz = Class.forName(className);
			return clazz.newInstance();	
		} catch (ClassNotFoundException e) {
			LOG.debug("ClassNotFoundException:"+className);
		//	throw new RuntimeException(e);
		} catch (InstantiationException e) {
			LOG.debug("InstantiationException:"+className);
		//		throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			LOG.debug("IllegalAccessException:"+className);
		//		throw new RuntimeException(e);
		} catch (Exception e){
			LOG.debug(e+":"+className);
			e.printStackTrace();
		//	throw new RuntimeException(e);
		}
		return null;
	}

	/**
	 * Carga un propiedad desde fichero de propiedades
	 * Lanza runtime exception si no existe la propiedad
	 * @param file
	 * @param property
	 * @return
	 */
	static String loadProperty(String file, String property) {
		Properties p = new Properties();
		try {
			
			p.load(Factories.class.getResourceAsStream(file));
			
		} catch (IOException e) {
		//	throw new RuntimeException(e);
		}
		String value = p.getProperty(property);
		if (value == null){
		//	throw new RuntimeException("Property not found in " + file);
		}
		return value;
	}

}
