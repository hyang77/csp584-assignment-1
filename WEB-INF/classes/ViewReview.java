import java.io.IOException;
import java.io.PrintWriter;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@WebServlet("/ViewReview")

public class ViewReview extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    Utilities utility= new Utilities(request, pw);
		review(request, response);
	}
	protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
        {           
            response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request,pw);
			
			if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to view Review");
			response.sendRedirect("Login");
			return;
			}

			String productName=request.getParameter("name");	
			//select review info from database and store in the hashmap	 
			HashMap<String, ArrayList<Review>> hm= MongoDBDataStoreUtilities.selectReview();
			//declare vaiable for displaying in the table
			
			String category = "";
			String price = "";
			String retailerpin = "";

			String retailerstoreid = "";
			String city ="";
			String retailerstate = "";

			String productonsale = "";
			String manufacture = "";
			String rebate = "";
			
			String userName = "";
			String age = "";
			String gender = "";
			String occupation = "";

			String reviewRating = "";
			String reviewDate = "";
			String reviewText = "";	

			
            utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			//title
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Review</a>");
			pw.print("</h2><div class='entry'>");
			
			//if there are no reviews for product print no review else iterate over all the reviews using cursor and print the reviews in a table
			if(hm==null)
			{
				pw.println("<h2>Mongo Db server is not up and running</h2>");
			}
			else
			{
                if(!hm.containsKey(productName)){
					pw.println("<h2>There are no reviews for this product.</h2>");
				}else{
					for (Review r : hm.get(productName)){		
						pw.print("<table class='gridtable'>");

						//name
						pw.print("<tr>");
						pw.print("<td> Product Name: </td>");
						productName = r.getProductName();
						pw.print("<td>" +productName+ "</td>");
						pw.print("</tr>");
						//category
						pw.print("<tr>");
						pw.print("<td> Product Category: </td>");
						category = r.getProductType();
						pw.print("<td>" +category+ "</td>");
						pw.print("</tr>");
						//price
						pw.print("<tr>");
						pw.print("<td> Product Price: </td>");
						price = r.getPrice();
						pw.print("<td>" +price+ "</td>");
						pw.print("</tr>");
						//store id
						pw.print("<tr>");
						pw.print("<td> Store ID: </td>");
						retailerstoreid = r.getStoreId();
						pw.print("<td>" +retailerstoreid+ "</td>");
						pw.print("</tr>");
						//store zip
						pw.print("<tr>");
						pw.print("<td> Store Zip: </td>");
						retailerpin = r.getRetailerPin();
						pw.print("<td>" +retailerpin+ "</td>");
						pw.print("</tr>");
						//store city
						pw.print("<tr>");
						pw.print("<td> Store City: </td>");
						city = r.getRetailerCity();
						pw.print("<td>" +city+ "</td>");
						pw.print("</tr>");
						//store state
						pw.print("<tr>");
						pw.print("<td> Store State: </td>");
						retailerstate = r.getState();
						pw.print("<td>" +retailerstate+ "</td>");
						pw.print("</tr>");
						//product on sale
						pw.print("<tr>");
						pw.print("<td> Product On Sale: </td>");
						productonsale = r.getProductOnSale();
						pw.print("<td>" +productonsale+ "</td>");
						pw.print("</tr>");
						//manu name 
						pw.print("<tr>");
						pw.print("<td> Manufacturer Name: </td>");
						manufacture = r.getProductMaker();
						pw.print("<td>" +manufacture+ "</td>");
						pw.print("</tr>");
						//manu rebate
						pw.print("<tr>");
						pw.print("<td> Manufacturer Rebate: </td>");
						rebate = r.getRebate();
						pw.print("<td>" +rebate+ "</td>");
						pw.print("</tr>");
						//username
						pw.print("<tr>");
						pw.print("<td> User ID: </td>");
						userName = r.getUserName();
						pw.print("<td>" +userName+ "</td>");
						pw.print("</tr>");
						//age
						pw.print("<tr>");
						pw.print("<td> User Age: </td>");
						age = r.getAge();
						pw.print("<td>" +age+ "</td>");
						pw.print("</tr>");
						//gender
						pw.print("<tr>");
						pw.print("<td> User Gender: </td>");
						gender = r.getGender();
						pw.print("<td>" +gender+ "</td>");
						pw.print("</tr>");
						//occupation
						pw.print("<tr>");
						pw.print("<td> User Occupation: </td>");
						occupation = r.getOccupation();
						pw.print("<td>" +occupation+ "</td>");
						pw.print("</tr>");
						//rating
						pw.println("<tr>");
						pw.println("<td> Review Rating: </td>");
						reviewRating = r.getReviewRating().toString();
						pw.print("<td>" +reviewRating+ "</td>");
						pw.print("</tr>");
						//date
						pw.print("<tr>");
						pw.print("<td> Review Date: </td>");
						reviewDate = r.getReviewDate().toString();
						pw.print("<td>" +reviewDate+ "</td>");
						pw.print("</tr>");	
						//text
						pw.print("<tr>");
						pw.print("<td> Review Text: </td>");
						reviewText = r.getReviewText();
						pw.print("<td>" +reviewText+ "</td>");
						pw.print("</tr>");
						

						pw.println("</table>");
					}					
							
				}
			}	       
            pw.print("</div></div></div>");		
			utility.printHtml("Footer.html");
	                     	
        }
        catch(Exception e)
		{
            System.out.println(e.getMessage());
		}  			
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
    }
}
