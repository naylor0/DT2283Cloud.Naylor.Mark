package ie.dit.naylor.mark;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MathlabServlet2 extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello I'm MathLab Servlet 2!");
	}
}
