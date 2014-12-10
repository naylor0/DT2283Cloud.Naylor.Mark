package ie.dit.naylor.mark;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@SuppressWarnings("serial")
public class ViewServlet extends HttpServlet 
{
	// Populate arraylist from DB query then loop and print links
	public static ArrayList<DeleteServlet> files = DownloadServlet.files;
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		FilterPredicate filter = new FilterPredicate("public", FilterOperator.EQUAL, "public");
		Query q = new Query("Image").setFilter(filter);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);


		for (Entity result : pq.asIterable()) 
		{
			if(result.equals(null))
			{
				resp.getWriter().println("There are no public images!");
			}
			String fname = (String) result.getProperty("fname");
			String imagekey = (String) result.getProperty("key");
			String owner = (String) result.getProperty("owner");
			String isPublic = (String) result.getProperty("public");

			resp.getWriter().println("<p>View <a href=\"serve?blob-key=" + imagekey + "\">" + fname + "</a>.</p>");
		}
	}
}