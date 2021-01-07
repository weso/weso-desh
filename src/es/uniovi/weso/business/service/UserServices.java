package es.uniovi.weso.business.service;


import es.uniovi.weso.business.BusinessException;
import es.uniovi.weso.model.User;

public interface UserServices {

	public void createUser(User user) throws BusinessException;

	public boolean existsUser(Long userUid) throws BusinessException;

	public boolean isUser(String username) throws BusinessException;

	public User getUser(String username, String password) throws BusinessException;
	
	public User getUser(Long uid) throws BusinessException;

	

}
