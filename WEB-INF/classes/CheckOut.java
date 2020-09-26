import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

@WebServlet("/CheckOut")

//once the user clicks buy now button page is redirected to checkout page where user has to give checkout information

public class CheckOut extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities Utility = new Utilities(request, pw);
		storeOrders(request, response);
	}
	
	protected void storeOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
        {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
        HttpSession session=request.getSession(); 

		//get the order product details	on clicking submit the form will be passed to submitorder page	
		
	    String userName = session.getAttribute("username").toString();
        String orderTotal = request.getParameter("orderTotal");
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='CheckOut' action='Payment' method='post'>");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<table  class='gridtable'><tr><td>Customer Name:</td><td>");
		pw.print(userName);
		pw.print("</td></tr>");
		// for each order iterate and display the order name price
		for (OrderItem oi : utility.getCustomerOrders()) 
		{
			pw.print("<tr><td> Product Purchased:</td><td>");
			pw.print(oi.getName()+"</td></tr><tr><td>");
			pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
			pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
			pw.print("Product Price:</td><td>"+ oi.getPrice());
			pw.print("</td></tr>");
		}
		pw.print("<tr><td>");
        pw.print("Total Order Cost</td><td>"+orderTotal);
		pw.print("<input type='hidden' name='orderTotal' value='"+orderTotal+"'>");
		pw.print("</td></tr></table><p>Customer Information: </p><table><tr></tr><tr></tr>");	
		pw.print("<tr><td>");
     	pw.print("Credit/accountNo</td>");
		pw.print("<td><input type='text' name='creditCardNo'>");
		pw.print("</td></tr>");
		pw.print("<tr><td>");
	    pw.print("Customer Address</td>");
		pw.print("<td><input type='text' name='userAddress'>");
        pw.print("</td></tr>");
		pw.print("<tr><td><input type='radio' name='pickup' value='pickup'> Store Pickup</input></td>");
		pw.print("<td><input type='radio' name='delivery' value='delivery'> Home Delivery</input></td></tr>");
		pw.print("</table><p>Choose Location: </p><table>");
		pw.print("<tr><td><input type='radio' name='store1' value='store1'> 3320 South Cicero Avenue Cicero, IL 60804</input></td></tr>");
		pw.print("<tr><td><input type='radio' name='store2' value='store2'> 7050 S Cicero Ave Bedford Park, IL 60638</input></td></tr>");
		pw.print("<tr><td><input type='radio' name='store3' value='store3'> 1300 Des Plaines Ave Forest Park, IL 60130</input></td></tr>");
		pw.print("<tr><td><input type='radio' name='store4' value='store4'> 9450 Joliet Rd Hodgkins, IL 60525</input></td></tr>");
		pw.print("<tr><td><input type='radio' name='store5' value='store5'> 10260 S Harlem Ave Bridgeview, IL 60455</input></td></tr>");
		pw.print("<tr><td><input type='radio' name='store6' value='store6'> 4700 135th St Crestwood, IL 60418</input></td></tr>");
		pw.print("<tr><td><input type='radio' name='store7' value='store7'> 1100 5Th Ave Hammond, IN 46320</input></td></tr>");
		pw.print("<tr><td><input type='radio' name='store8' value='store8'> 6840 N McCormick Blvd Lincolnwood, IL 60712</input></td></tr>");
		pw.print("<tr><td><input type='radio' name='store9' value='store9'> 900 South Route 83 Villa Park, IL 60181</input></td></tr>");
		pw.print("<tr><td><input type='radio' name='store10' value='store10'> 2189 75th St Darien, IL 60561</input></td></tr>");
		pw.print("<tr><td colspan='2'>");
		pw.print("<input type='submit' name='submit' class='btnbuy'>");
        pw.print("</td></tr>");
		pw.print("</table></form></div></div></div>");		
		utility.printHtml("Footer.html");
	    }
        catch(Exception e)
		{
         System.out.println(e.getMessage());
		}  			
		}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	    {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    }
}
