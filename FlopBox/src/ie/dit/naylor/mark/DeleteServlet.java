package ie.dit.naylor.mark;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class DeleteServlet extends HttpServlet {
	/**
	 * 
	 */
	public KeyFactory key;
	private static final long serialVersionUID = 1L;
	private com.google.appengine.api.blobstore.BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException 
	{
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		blobstoreService.delete(blobKey);
		
		FilterPredicate filter = new FilterPredicate("imagekey", FilterOperator.EQUAL, blobKey.getKeyString());
		Query q = new Query("Image").setFilter(filter);
		PreparedQuery pq = datastore.prepare(q);
		// query datastore for all images with that key which will be just one, and then delete it from both blobstore and datastore
		for (Entity result : pq.asIterable()) 
		{
			Key toDel = result.getKey();
			datastore.delete(toDel);
		}
		// pause execution.. did this to try and deal with an error where the link wasnt being removed from the download servlet page
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.sendRedirect("/download");
	}

}