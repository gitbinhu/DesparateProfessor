import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloWorld extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {

    System.out.println ("HelloWorld servlet: inside the doGet() method");
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    out.println("<HTML>");
    out.println("<HEAD><TITLE>Hello World</TITLE></HEAD>");
    out.println("<BODY>");
    out.println("<BIG>Hello World 2</BIG>");
    out.println("<p>Welcome to the world of servlets</p>");
    out.println("<p>We hope you are <b>ready</b></p>");
    out.println("</BODY></HTML>");
    out.flush ();
    //out.close ();
  }
  public void doPost(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {

    System.out.println ("doPost");
  }
  

}
