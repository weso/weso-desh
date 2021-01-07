package es.uniovi.weso.business.service.impl;



import java.util.Set;

import javax.management.Notification;

import es.uniovi.weso.business.BusinessException;
import es.uniovi.weso.business.CommandExecutor;
import es.uniovi.weso.business.service.UserServices;
import es.uniovi.weso.infraestructure.Factories;
import es.uniovi.weso.model.User;

public class UserServicesImpl implements UserServices {

	private CommandExecutor executor = Factories.get().getExecutor()
			.getCommandExecutor();

	
	@Override
	public void createUser(User user) throws BusinessException {
	//	executor.execute(new CreateUserCommand(user));
	}

	@Override
	public boolean existsUser(Long userUid) throws BusinessException {
	//	return (Boolean) executor.execute(new ExistsUserCommand(userUid));
		return true;
	}


	@Override
	public boolean isUser(String username) throws BusinessException {
//		return (Boolean) executor.execute(new IsUserCommand(username));
		return true;
	}

	@Override
	public User getUser(String username, String password)
			throws BusinessException {
//		return (User) executor.execute(new GetUserCommand(username, password));
		return null;
	}

	@Override
	public User getUser(Long uid)
			throws BusinessException {
	//	return (User) executor.execute(new GetUserCommand(uid));
		return null;
	}


}
