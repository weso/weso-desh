package es.uniovi.weso.business;

import es.uniovi.weso.business.service.UserServices;


public interface ServicesFactory {

	UserServices getUserServices();
	
}
