import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class A2Servlet extends HttpServlet { 

    String aName, userName, password, address;
    int orderNumber, numOrders = 0;
    double orderTotal;
    int numCloak = 0, numPlanner = 0, numCaffeine = 0, numQuill = 0, numDeadline = 0;
    double cloakPrice = 199.99, quillPrice = 39.99, deadlinePrice = 49.99, caffeinePrice = 79.99, plannerPrice = 29.99;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 

        String action = req.getParameter("action");
        String isLoggedIn = req.getParameter("loggedin");
        System.out.println("action=" + action);
        if("undergrad".equals(action)) {
            boolean success = handleUndergrad(req, res);
            System.out.println("In A2Servlet.if(undergrad). Success=" + success);
        }

        if("login".equals(action)) { 
            System.out.println("A2Servlet servlet: inside doGet() if(login) method");
           
            res.sendRedirect("/a2html/login.html");           
            
        }
        if("register".equals(action)) { 
            System.out.println("A2Servlet servlet: inside doGet() if(register) method");
    
            res.sendRedirect("/a2html/register.html");
        }
        if("Submit Registration".equals(action)) {
            System.out.println("A2Servlet servlet: inside doGet() if(registersub) method");
            boolean successfulLogin = handleRegisterSub(req, res);
            System.out.println(successfulLogin);
            if(successfulLogin) {
                System.out.println("Back in doGet() registration submit");
                System.out.println(aName + " " + userName);
                res.sendRedirect("/a2html/login.html");
            }else if (!successfulLogin){
                res.sendRedirect("/a2html/reregister.html");
            }

        }
        if("Login to Shop".equals(action)) {
            System.out.println("A2Servlet servlet: inside doGet() if(LoginToShop) method");
            boolean successfulLogin = handleLogin(req, res);
            System.out.println("Back in A2Servlet: successfulLogin=" + successfulLogin);
            if(successfulLogin) { 
                System.out.println("A2Servlet servlet: inside doGet() if(LTS)if(successfulLogin) method");
                System.out.println(aName + " " + userName);
                res.sendRedirect("/a2html/shop.html");
            }else if (!successfulLogin) {
                res.sendRedirect("/a2html/relogin.html");
            }
        }
        if("Deadline".equals(action)) { 
            System.out.println("A2Servlet servlet: inside doGet().if(Deadline)");
            numDeadline++;
            System.out.println("Deadlines in cart=" + numDeadline);
            res.sendRedirect("/a2html/shop.html"); 
        }
        if("Caffeine".equals(action)) {
            System.out.println("A2Servlet servlet: inside doGet().if(Caffeine)");
            numCaffeine++;
            System.out.println("Caffeines in cart=" + numCaffeine);
            res.sendRedirect("/a2html/shop.html");
        }
        if("Quill".equals(action)) {
            System.out.println("A2Servlet servlet: inside doGet().if(Quill)");
            numQuill++;
            System.out.println("Qulls in cart=" + numQuill);
            res.sendRedirect("/a2html/shop.html");
        }
        if("Planner".equals(action)) { 
            System.out.println("A2Servlet servlet: inside doGet().if(Planner)");
            numPlanner++;
            System.out.println("Planners in cart=" + numPlanner);
            res.sendRedirect("/a2html/shop.html");
        }
        if("Cloak".equals(action)) { 
            System.out.println("A2Servlet servlet: inside doGet().if(Cloak)");
            numCloak++;
            System.out.println("Cloaks in cart=" + numCloak);
            res.sendRedirect("/a2html/shop.html");
        }
        if("Checkout".equals(action)) {
            System.out.println("Current Cart Contents:");
            System.out.println("\tcloaks: " + numCloak + " planners: " + numPlanner + " quills: " + numQuill + " caffeines: " + numCaffeine + " deadlines: " + numDeadline);
            
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Desperate Professor Checkout</title></head>");
            out.println("<body>");
            out.println("<h1 style=\"text-align: center;\">The Desperate Professor Checkout</h1>");
            out.println("<h3 style=\"text-align: center;\">Please verify your cart. If you would like to add to your cart, click your browsers <em>back</em> button. To clear your cart and start over, click on the <em>Clear Cart</em> button, below.</h3>");
            out.println("<h3 style=\"text-align: center;\"> When you're satisfied, click on <em>Submit Order</em> below and we will use your on-file address and payment information to process your order.</h3>");
            if(numDeadline > 0){
                double itemTotal = numDeadline * deadlinePrice;
                String formattedItemTotal = String.format("%.2f", itemTotal);
                out.println("<p>The Deadline Defender - Quantity: " + numDeadline + " @ $49.99ea. Item Total = $" + formattedItemTotal + "</p>");
            }
            if(numCaffeine > 0) { 
                double itemTotal = numCaffeine * caffeinePrice;
                String formattedItemTotal = String.format("%.2f", itemTotal);
                out.println("<p>Caffeine Conductor's Kit(s) - Quantity: " + numCaffeine + " @ $79.99ea. Item Total = $" + formattedItemTotal  + "</p>");
            }
            if(numQuill > 0) { 
                double itemTotal = numQuill * quillPrice;
                String formattedItemTotal = String.format("%.2f", itemTotal);
                out.println("<p>Reviwer Rebuttal Quill(s) - Quantity: " + numQuill + " @ $39.99ea. Item total = $" + formattedItemTotal + "</p>");
            }
            if(numPlanner > 0) { 
                double itemTotal = numPlanner * plannerPrice;
                String formattedItemTotal = String.format("%.2f", itemTotal);
                out.println("<p>Procrastinator's Paradise Planner(s) - Quantity: " + numPlanner + " @ $29.99ea. = $" + formattedItemTotal + "</p>");
            }
            if(numCloak > 0) { 
                double itemTotal = numCloak * cloakPrice;
                String formattedItemTotal = String.format("%.2f", itemTotal);
                out.println("<p>Conference Conjurer's Cloak(s) - Quantity: " + numCloak + " @ $199.99ea. = $" + formattedItemTotal + "</p>");
            }
            out.println("<p></p>");
            orderTotal =  (((double)numDeadline * deadlinePrice) + ((double)numCaffeine * caffeinePrice) + ((double)numQuill * quillPrice) + ((double)numPlanner * plannerPrice) + ((double)numCloak * cloakPrice));
            String formattedOrderTotal = String.format("%.2f", orderTotal);
            out.println("<p>***ORDER TOTAL: $" + formattedOrderTotal + " TOTAL ITEMS: " + (numDeadline + numCaffeine + numQuill + numPlanner + numCloak) + "***</p>");
            out.println("<form action=\"../../servlets/a2servlets/A2Servlet\" method=\"post\">");
            out.println("<input type=\"submit\" name=\"action\" value=\"Clear\"></input>");
            out.println("<p></p>");
            out.println("<input type=\"submit\" name=\"action\" value=\"Submit Cart\"></input>");
            out.println("</body></html>");
            out.flush();
        }
        if("Clear".equals(action)) { 
            numDeadline = 0;
            numCaffeine = 0;
            numQuill = 0;
            numPlanner = 0;
            numCloak = 0;
            res.sendRedirect("/a2html/shop.html");
            System.out.println("In A2Servlet servlet .doGet().ifClear() statement");
            System.out.println(numDeadline);
            System.out.print(numCaffeine);
            System.out.print(numQuill);
            System.out.print(numPlanner);
            System.out.print(numCloak);
        }
        if("Submit Cart".equals(action)) { 
            boolean success = handleSubmitCart(req, res);
            System.out.println("In doGet().ifSubmitCart success=" + success);
            if(success){
               res.setContentType("text/html");
               PrintWriter out = res.getWriter();
               out.println("<!DOCTYPE html>");
               out.println("<html>");
               out.println("<head><title>Order Complete</title></head>");
               out.println("<body>");
               out.println("<BIG style=\"text-align:center;\">Order Complete</BIG>");
               out.println("<p>Your order number is: " + orderNumber + ".<b>Please save this number for tracking purposes</b></p>");
               out.println("<p><em>Thank you for your order, " + aName + "</em></p>");
               out.println("</body></html>");
               out.flush();
            }else{
                res.sendRedirect("/a2html/home.html");
            }
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
        System.out.println("In doPost(). Redirecting to doGet()");
        doGet(req, res);
    }
    public boolean handleSubmitCart(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
        System.out.println("In A2Servlet.handleSubmitCart()");
        boolean success = false;
        if((numCaffeine == 0) && (numDeadline == 0) && (numQuill == 0) && (numPlanner == 0) && (numCloak == 0)) {
            return success;
        }else if(userName == null){
            return success;
        }else{ 
            Random rand = new Random();
            orderNumber = 1000 + rand.nextInt(29001);
            String orderNumberString = String.valueOf(orderNumber);
            System.out.println("Order Number String="+ orderNumberString);
            String fileName = userName + orderNumberString + ".txt";
            System.out.println("New order file created: " + fileName);
            File f = new File(fileName);
            if(f.exists()) { 
                return success;
            }
            f.createNewFile();
            PrintWriter pw = new PrintWriter(new FileWriter(f));
            pw.println(userName);
            pw.println(orderNumberString);
            pw.println(numDeadline);
            pw.println(numCaffeine);
            pw.println(numQuill);
            pw.println(numPlanner);
            pw.println(numCloak);
            double total = (numDeadline * deadlinePrice) + (numCaffeine * caffeinePrice) + (numQuill * quillPrice) + (numPlanner * plannerPrice) + (numCloak * cloakPrice);
            pw.println(total);
            pw.println(aName);
            pw.println(address);
            this.numOrders++;
            pw.println(numOrders);
            pw.close();
            success = true;
        }
        return success;
    }

    public boolean handleUndergrad(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("In A2Servlet.handleUndergrad()");
        boolean success = false;
        System.out.println("A2Servlet servlet: inside doGet() method");
        
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Go Away!</head></title>");
        out.println("<body>");
        out.println("<BIG style=\"text-align: center;\">WARNING!!!</BIG>");
        out.println("<p></p>");
        out.println("<p>The Desperate Professor is just for professors and graduate students on TA-ships. Since you're an undergrad you should really just go away!</p>");
        out.println("<p>Now. Please.</p>");
        out.println("</body></html>");
        out.flush();
        success = true;
        return success;
    }
    public boolean handleLogin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
        boolean success = false;
        System.out.println("in handleLogin() success=" + success);
        String enteredUserName = req.getParameter("username");
        String enteredPassword = req.getParameter("password");
        String fileName = enteredUserName + ".txt";
        System.out.println("Search for file " + fileName);
        File f = new File(fileName);
        
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("In A2Servlet.handleLogin() currentDirectory=" + currentDirectory);
        if(f.exists()) { 
            //LOGIC
            try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                this.userName = br.readLine();
                System.out.println("enteredUserName=" + enteredUserName + " filedUserName=" + userName);
                this.password = br.readLine();
                if(!password.equals(enteredPassword)){
                    return success;
                }
                this.aName = br.readLine();
                this.address = br.readLine();
                this.numOrders = Integer.parseInt(br.readLine());
                success = true;
            }catch(IOException e){
                e.printStackTrace();
                return success;
            }
        }else if (!f.exists()) { 
            return success;
        }
        return success;
    }
    public boolean handleRegisterSub(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
        
        boolean success = false;
        numOrders = 0;
        System.out.println("In handleRegistrationSub() success=" + success);
        userName = req.getParameter("username");
        String fileName = userName + ".txt";
        File f = new File(fileName);
        System.out.println("Attempting to create file " + fileName);
        if(f.exists()) {
            return success;
        }else if (!f.exists()){
	    f.createNewFile();
            PrintWriter pw = new PrintWriter(new FileWriter(f));
            pw.println(userName);
            password = req.getParameter("password");
            pw.println(password);
            aName = req.getParameter("fullname");
            pw.println(aName);
            address = req.getParameter("address");
            pw.println(address);
            pw.println(this.numOrders);
            pw.close();
            success = true;
        }
        return success;
    }
}
