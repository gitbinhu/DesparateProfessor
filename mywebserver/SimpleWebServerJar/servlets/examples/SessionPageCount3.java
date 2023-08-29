import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


// Use an instance of this class for each user.
class SessionInfo {
    int pageCount;     // Number of pages accessed.
    long startTime;    // Time at which session started in milliseconds.
}


public class SessionPageCount3 extends HttpServlet {

    static final boolean debug = false;

    // Store session info (page count) by username in hashtable.
    Hashtable sessionTable = new Hashtable ();

    public void doGet (HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException
    {
	// Set the content type of the response etc ..
	resp.setContentType ("text/html");
	PrintWriter out = resp.getWriter ();
	out.println ("<html><head><title> Visit Count</title></head><body>");

	// During debugging, it helps to print out the parameters in the response.
	if (debug) {
	    printParams (out, req);
	}

	// Write out the action.

	// CHANGE TO YOUR PORT NUMBER:
	out.println ("<form action=\"http://unix.seas.gwu.edu:40127/servlets/examples/SessionPageCount3\" method=\"post\">");

	// out.println ("<form action=\"http://localhost:40127/servlets/examples/SessionPageCount3\" method=\"post\">");


	// Get the page info from hidden fields:
	String whichPage = req.getParameter ("page");
	String userName = req.getParameter ("username");

	// First extract session info.
	SessionInfo session = (SessionInfo) sessionTable.get (userName);

	// Check if inactive session.
	if (session != null) {
	    long minutesSince = (System.currentTimeMillis() - session.startTime) / (1000*60);
	    if (minutesSince >= 1) {
		out.println ("Session timed out. Please login again.");
		out.println ("</form></body></html>");
		out.flush ();
		return;
	    }
	}
	else {
	    // Make session.
	    session = new SessionInfo ();
	    // Get current page count, if it exists, from file.
	    File f = new File (userName);
	    if (! f.exists()) {
		// Create one and write zero.
		f.createNewFile ();
		PrintWriter pw = new PrintWriter (new FileWriter(f));
		pw.println (0);
		pw.close ();
	    }
	    // Read from file.
	    LineNumberReader lnr = new LineNumberReader (new FileReader (f));
	    String pageCountStr = lnr.readLine ();
	    try {
		session.pageCount = Integer.parseInt (pageCountStr.trim());
	    }
	    catch (NumberFormatException e) {
		session.pageCount = 0;
	    }
	    lnr.close ();
	    // Record the start of the session.
	    session.startTime = System.currentTimeMillis ();
	}

	// Active session. Continue ...

	// Determine action based on which page fired the request.
	if (whichPage.equalsIgnoreCase ("loginpage")) {
	    handleLoginPage (out, userName);
	}
	else if (whichPage.equalsIgnoreCase ("menupage")) {
	    handleMenuPage (out, userName, req);
	}
	else if (whichPage.equalsIgnoreCase ("featurepage")) {
	    // Back button on Time or Fortune page
	    handleLoginPage (out, userName);
	}
	else {
	    handleError (out);
	}
    
	// Page count.

	session.pageCount ++;
	sessionTable.put (userName, session);
	out.println ("<br> <font color=\"#FF0000\">You have made " + session.pageCount + " page requests</font> in your lifetime");
	out.println ("<input type=\"hidden\" name=\"pagecount\" value=\"" + session.pageCount + "\">");

	// Write current count to file:
	File outFile = new File (userName);
	outFile.delete ();
	PrintWriter pw = new PrintWriter (new FileWriter (userName));
	pw.println (session.pageCount);
	pw.close ();


	// Last part.
	out.println ("</form></body></html>");
	out.flush ();
    }
  

    public void doPost (HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException
    {
	// We need to implement POST because the forms are written as such.
	doGet (req, resp);
    }
  

    void handleLoginPage (PrintWriter out, String userName)
    {
	out.println ("Hello " + userName + "! Choose from these options: ");

	// The two buttons.
	out.println ("<input type=\"submit\" name=\"Time\" value=\"Time\">");
	out.println ("<input type=\"submit\" name=\"Fortune\" value=\"Fortune\">");

	// Write out the extracted username:
	out.println ("<input type=\"hidden\" name=\"username\" value=\"" + userName + "\">");
	out.println ("<input type=\"hidden\" name=\"page\" value=\"menupage\">");
    }


    void handleMenuPage (PrintWriter out, String userName, HttpServletRequest req)
    {
	// Check which button was pressed:
	String timeButtonPressed = req.getParameter ("Time");

	out.println (userName + ", ");

	if (timeButtonPressed != null) {
	    // Time event.
	    Date d = new Date ();
	    out.println ("the date/time is: " + d + ".");
	}
	else {
	    // Fortune event.
	    String fortune = "";
	    if ( (int)userName.charAt(0) % 2 == 0) {
		fortune = "go out, meet people. Tomorrow is going to be great for you.";
	    }
	    else {
		fortune = "stay at home. Tomorrow is going to be a lousy day for you.";
	    }
	    out.println (fortune);
	}

	// A "Back" button:
	out.println ("<br><input type=\"submit\" name=\"Back\" value=\"Back\">");

	// Need to write the hidden fields:
	out.println ("<input type=\"hidden\" name=\"username\" value=\"" + userName + "\">");
	out.println ("<input type=\"hidden\" name=\"page\" value=\"featurepage\">");
    }


    void handleError (PrintWriter out)
    {
	out.println ("Enter username: <input type=\"text\" name=\"username\">");
	out.println ("<input type=\"submit\" value=\"login\">");
	out.println ("<input type=\"hidden\" name=\"page\" value=\"loginpage\">");
    }


    // For debugging:

    void printParams (PrintWriter out, HttpServletRequest req)
    {
	out.println ("<p><hr><p> Request parameters: <ul>");
	Enumeration e = req.getParameterNames();
	while (e.hasMoreElements()) {
	    String name = (String) e.nextElement();
	    String value = req.getParameter (name);
	    if (value != null)
		out.println ("<li> name=[" + name + "] value=[" + value + "]");
	    else
		out.println ("<li> name=[" + name + "] did not have a value");
	}
	out.println ("</ul><hr>");
    }

}
