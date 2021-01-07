package es.uniovi.weso.actions.user;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @version 0.1
 * @date 2011-11-15
 */
public class UserAction  extends ActionSupport implements
org.apache.struts2.interceptor.ServletRequestAware {

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}

	public String execute() throws Exception {
		return SUCCESS;
	}

}
