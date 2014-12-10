package ie.dit.naylor.mark;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.datanucleus.store.types.sco.backed.Map;

@SuppressWarnings("serial")
public class DownloadServlet extends HttpServlet 
{
	private com.google.appengine.api.blobstore.BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	public static ArrayList<DeleteServlet> files = new ArrayList<DeleteServlet>();
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		UserService userService = UserServiceFactory.getUserService();
		userService.getCurrentUser();
		String thisURL = req.getRequestURI();
		String logoutURL = userService.createLogoutURL(thisURL);
		Principal myPrincipal = req.getUserPrincipal();
		String loginURL = userService.createLoginURL(thisURL);
		if(myPrincipal == null) 
		{
			resp.getWriter().println("<p>You are not logged in</p>");
			resp.getWriter().println("<p>You can <a href=\""+ loginURL + "\">sign in here</a>.</p>");
			resp.getWriter().println("<p>You can continue <a href=\"/view\"as guest</a>here.</p>");

		}
		if (myPrincipal.getName().equals("marknaylor2006@gmail.com") || myPrincipal.getName().equals("mark@dit.ie"))
		{
			resp.getWriter().println("<p>Upload <a href=\""+ "upload.jsp"+ "\">here</a>.</p>");
			FilterPredicate filter = new FilterPredicate("owner", FilterOperator.NOT_EQUAL, " ");
			Query q = new Query("Image").setFilter(filter);
			// Use PreparedQuery interface to retrieve results
			PreparedQuery pq = datastore.prepare(q);


			for (Entity result : pq.asIterable()) 
			{
			  String fname = (String) result.getProperty("fname");
			  String imagekey = (String) result.getProperty("imagekey");
			  String owner = (String) result.getProperty("owner");
			  String isPublic = (String) result.getProperty("public");

			  System.out.println(fname + ", " + imagekey + ", " + owner + ", " + isPublic);
			  resp.getWriter().print("<p>View <a href=\"serve?blob-key=" + imagekey + "\">" + fname + "</a>.</p>");
			  resp.getWriter().println("<p>   Delete <a href=\"delete?blob-key=" + imagekey + "\">" + fname + "</a>.</p><br>");
			}
			resp.getWriter().println("<p>You can <a href=\""+ logoutURL + "\">logout here</a>.</p><br>");
		}
		else if (myPrincipal.getName() != null)
		{
			resp.getWriter().println("<p>Upload <a href=\""+ "upload.jsp"+ "\">here</a>.</p>");
			FilterPredicate filter = new FilterPredicate("owner", FilterOperator.EQUAL, myPrincipal.getName());
			Query q = new Query("Image").setFilter(filter);
			// Use PreparedQuery interface to retrieve results
			PreparedQuery pq = datastore.prepare(q);


			for (Entity result : pq.asIterable()) 
			{
				String imagekey = (String) result.getProperty("imagekey");
				String fname = (String) result.getProperty("fname");
				String owner = (String) result.getProperty("owner");
				String isPublic = (String) result.getProperty("public");

				System.out.println(fname + ", " + imagekey + ", " + owner + ", " + isPublic);
				resp.getWriter().print("<p>View <a href=\"serve?blob-key=" + imagekey + "\">" + fname + "</a>.</p>");
				resp.getWriter().println("<p>   Delete <a href=\"delete?blob-key=" + imagekey + "\">" + fname + "</a>.</p><br>");
			}
			resp.getWriter().println("<p>You can <a href=\""+ logoutURL + "\">logout here</a>.</p><br>");
		}
	}
}