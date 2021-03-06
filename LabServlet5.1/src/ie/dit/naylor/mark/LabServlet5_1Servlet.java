package ie.dit.naylor.mark;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class LabServlet5_1Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		// the user service class gives login and logout methods
		UserService userService = UserServiceFactory.getUserService();
		// getuserprincipal returns the name of the authenticated user if one exists
		Principal myPrincipal = req.getUserPrincipal();
		String emailAddress = null;
		// save current url
		String thisURL = req.getRequestURI();
		// gives a URL for a login page and passes thisURL so user will be redirected after they login
		String loginURL = userService.createLoginURL(thisURL);
		// creates a URL for a logout page and passes thisURL so user will be redirected there
		String logoutURL = userService.createLogoutURL(thisURL);
	
		resp.setContentType("text/html");
		// if no username exists then user isnt logged in
		if(myPrincipal == null) 
		{
			resp.getWriter().println("<p>You are Not Logged In time</p>");
			resp.getWriter().println("<p>You can <a href=\""+loginURL+ "\">sign in here</a>.</p>");
		} // end if not logged in
		// otherwise user must be logged in
		if(myPrincipal !=null)
		{
			emailAddress = myPrincipal.getName(); // get username from myPrincipal object
			resp.getWriter().println("<p>You are Logged in as (email): "+emailAddress+"</p>"); // display what username user is logged in under
			resp.getWriter().println("<p>You can <a href=\"" + logoutURL +"\">sign out</a>.</p>"); // display link to logout page
		} // end if logged in
	}
}
