package es.uniovi.weso.actions.login;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import es.uniovi.weso.model.User;

public class LoginInterceptor extends AbstractInterceptor implements
		StrutsStatics {

	private static final long serialVersionUID = -769315766562688179L;
	private transient final Log LOG = LogFactory.getLog(getClass());
	
	@Override
	public String intercept(ActionInvocation inv) throws Exception {
		ActionContext context = inv.getInvocationContext();
		User user = (User) context.getSession().get("user");
		String uri = context.getName().toLowerCase();
		LOG.debug("LoginInterceptor->uri: "+uri);
		/*
		if (user == null){
			if(!uri.contains("login") 
					&& !uri.contains("registerform")
					&& !uri.contains("home")
					&& !uri.contains("createuser")) {
				return inv.invoke();
			}
		}
		else {
			if(uri.contains("createuser") 
					|| uri.contains("home") 
					|| uri.contains("registerform") 
					|| uri.contains("login")) {
				return "profile";
			}
		}
		*/
		return inv.invoke();
	}

}