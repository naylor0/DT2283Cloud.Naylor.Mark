package ie.dit.naylor.mark;

import java.io.IOException;
import javax.servlet.http.*;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class ServeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private com.google.appengine.api.blobstore.BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException 
	{
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		blobstoreService.serve(blobKey, res);
	}

}