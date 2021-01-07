package es.uniovi.weso.business;

public interface CommandExecutor {

	Object execute(Command c) throws BusinessException;

}