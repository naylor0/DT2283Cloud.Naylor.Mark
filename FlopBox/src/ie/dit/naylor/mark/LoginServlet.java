package ie.dit.naylor.mark;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
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
			resp.getWriter().println("<br><font size=\"4\"><p>You are not logged in</font></p><br>");
			resp.getWriter().println("<p><font size=\"4\">You can <a href=\""+loginURL+ "\">sign in here</a>.</p></font><br>");
			resp.getWriter().println("<p><font size=\"4\">Continue as guest <a href=\""+ "index.html"+ "\">here</a>.</p></font><br>");
		} // end if not logged in
		// otherwise user must be logged in
		if(myPrincipal !=null)
		{
			resp.sendRedirect("/download");
		} // end if logged in
	}
}