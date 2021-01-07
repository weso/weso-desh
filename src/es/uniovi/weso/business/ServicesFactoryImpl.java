package es.uniovi.weso.business;

import es.uniovi.weso.business.service.UserServices;
import es.uniovi.weso.business.service.impl.UserServicesImpl;


public class ServicesFactoryImpl implements ServicesFactory {

	@Override
	public UserServices getUserServices() {
		return new UserServicesImpl();
	}

}
