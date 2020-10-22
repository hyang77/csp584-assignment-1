import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/Inventory")

public class Inventory extends HttpServlet {


	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        String name = "Trending";
        
		ArrayList<ArrayList<String>> InventoryItems = MySqlDataStoreUtilities.getInventoryList();
		ArrayList<ArrayList<String>> RebatedProductsList = MySqlDataStoreUtilities.getRebatedProducts();

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");

		//generate inventory list
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Inventory List</a>");
        pw.print("</h2><div class='entry'><table id='bestseller'>");
        //print table header
        pw.print("<tr>");
        pw.print("<th>Product Name</th>");
        pw.print("<th>Product rice</th>");
        pw.print("<th>Units left</th>");
        pw.print("</tr>");
        //create inventory list table
        for (ArrayList<String> InventoryItem: InventoryItems) {
            pw.print("<tr>");
            for (int i = 0; i < InventoryItem.size(); i++) {
                pw.print("<td>");
                pw.print(InventoryItem.get(i));
                pw.print("</td>");
            }
            pw.print("</tr>");
        }
		pw.print("</table></div></div></div>");	

		//generate products that have manufacturer rebates
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Products that Have Manufacturer Rebates</a>");
        pw.print("</h2><div class='entry'><table id='bestseller'>");
        //print table header
        pw.print("<tr>");
        pw.print("<th>Product Name</th>");
        pw.print("<th>Discount</th>");
        pw.print("</tr>");
        for (ArrayList<String> Item: RebatedProductsList) {
            pw.print("<tr>");
            for (int i = 0; i < Item.size(); i++) {
                pw.print("<td>");
                pw.print(Item.get(i));
                pw.print("</td>");
            }
            pw.print("</tr>");
        }
		pw.print("</table></div></div></div>");	
		
		
		
		
		
		utility.printHtml("Footer.html");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}