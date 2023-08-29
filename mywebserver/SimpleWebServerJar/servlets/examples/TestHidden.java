import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class TestHidden extends HttpServlet {

    public void doGet (HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException
    {
	// Set the content type of the response.
	resp.setContentType ("text/html");
    
	// Extract the PrintWriter to write the response.
	PrintWriter out = resp.getWriter ();
    
	// The first part of the response.
	out.println ("<html>");
	out.println ("<head><title> Test </title></head>");
	out.println ("<body>");
    
	// Now get the parameters and output them back.
	out.println ("Request parameters: ");
	Enumeration e = req.getParameterNames();
	while (e.hasMoreElements()) {
	    String name = (String) e.nextElement();
	    String value = req.getParameter (name);
	    if (value != null)
		out.println ("<li> name=[" + name + "] value=[" + value + "]");
	    else
		out.println ("<li> name=[" + name + "] did not have a value");
	}
    
	// Last part.
	out.println ("</body>");
	out.println ("</html>");
	out.flush();
    
	// Screen I/O
	System.out.println ("Inside servlet ... servlet complete");
    }
  
    public void doPost (HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException
    {
	doGet (req, resp);
    }
  
}
