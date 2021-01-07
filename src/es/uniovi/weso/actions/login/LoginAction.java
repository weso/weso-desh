package es.uniovi.weso.actions.login;




import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

import es.uniovi.weso.model.User;

public class LoginAction extends ActionSupport implements
	org.apache.struts2.interceptor.ServletRequestAware {

	private transient final Log LOG = LogFactory.getLog(getClass());
	private static final long serialVersionUID = 4913052780376925115L;
	private User user;
	private HttpServletRequest request;
	private String username;
	private String password;
	
	public void setServletRequest (HttpServletRequest httpServletRequest) {
		this.request = httpServletRequest;
	}
	
	public String execute() throws Exception {
	/*
		if (user!=null)	{
			request.getSession().setAttribute("user",user);
			LOG.debug("LoginAction->execute: " + user.getName() + " in session" );
			return SUCCESS;
		}
		else {
			return ERROR;
		}
		*/
		return SUCCESS;
		
	}

	
	@Override
	public void validate() {
		super.validate();
		/*
		try {
			user = UserServiceHelper.getUser(username,password);
		} catch (BusinessException e) {
			LOG.debug("LoginAction->validate:" + e.toString());
		}
		if(user==null){
			this.addFieldError("username", getText("incorrect.data"));
		}
		if(password!=null )
			if(password.matches("[^0-9]")){
				this.addFieldError("password", getText("password.notNumber"));
			}
		*/
	}

	public User getUser() {
		return user;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
