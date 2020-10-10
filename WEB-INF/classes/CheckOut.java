import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.Date;

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
			pw.print("<input type='' name='orderPrice' value='"+oi.getPrice()+"'>");
			pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
			pw.print("Product Price:</td><td>"+ oi.getPrice());
			pw.print("</td></tr>");
		}
		pw.print("<tr><td>");

        pw.print("Total Order Cost</td><td>"+orderTotal);
		pw.print("<input type='hidden' name='orderTotal' value='"+orderTotal+"'>");

		pw.print("</td></tr></table><p>Customer Information: </p><table><tr></tr><tr></tr>");

		//credit num
		pw.print("<tr><td>");
     	pw.print("Credit/accountNo</td>");
		pw.print("<td><input type='text' name='creditCardNo'>");
		pw.print("</td></tr>");

		//address
		pw.print("<tr><td>");
	    pw.print("Customer Address</td>");
		pw.print("<td><input type='text' name='userAddress'>");
		pw.print("</td></tr>");

		//delivery option
		pw.print("<tr><td> Delivery Option: </td>");
		pw.print("<td>");
		pw.print("<select name='delivery'>");
		pw.print("<option value='homedelivery' selected>Home Delivery</option>");
		pw.print("<option value='storepickup'>Store Pickup</option>");   
		pw.print("</td></tr>");

		//store id
		pw.print("<tr><td> Store: </td>");
		pw.print("<td>");
		pw.print("<select name='storeid'>");
		pw.print("<option value='South Cicero' selected>South Cicero</option>");
		pw.print("<option value='Forest Park'>Forest Park</option>");
		pw.print("<option value='Joliet Rd'>Joliet Rd</option>");
		pw.print("<option value='Bridgeview'>Bridgeview</option>");
		pw.print("<option value='Crestwood'>Crestwood</option>");
		pw.print("<option value='Hammond'>Hammond</option>");
		pw.print("<option value='Lincolnwood'>Lincolnwood</option>");
		pw.print("<option value='Villa Park'>Villa Park</option>");
		pw.print("<option value='Darien'>Darien</option>");
		pw.print("<option value='Bedford Park'>Bedford Park</option>");      
		pw.print("</td></tr>");

		//purchaseDate
		Date date = new Date();
		pw.print("<input type='hidden' name='purchaseDate' value="+date.toString()+">");
		//shipDate
		pw.print("<input type='hidden' name='shipDate' value=''>");
		//product ID
		//category
		//quantity
		//discount
		//total sale


		


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
