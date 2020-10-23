import com.google.gson.Gson;
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
		ArrayList<ArrayList<String>> ProductsOnSaleList = MySqlDataStoreUtilities.getProductsOnSaleList();
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

		//click the buttont to view the inventory list by bar chart
		pw.print("<div id='content'><div class='post'>");
        pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Inventory Data Visualization</a></h2>"
                + "<div class='entry'>");
            
        pw.print("<h3><button id='btnGetChartData'>View Chart</h3>");
        pw.println("<div id='chart_div'></div>");
        pw.println("</div></div></div>");
        pw.println("<script type='text/javascript' src=\"https://www.gstatic.com/charts/loader.js\"></script>");
        pw.println("<script type='text/javascript' src='InventoryChart.js'></script>");

		//get a list products that are currently on sale
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Products that are Currently On Sale</a>");
        pw.print("</h2><div class='entry'><table id='bestseller'>");
        //print table header
        pw.print("<tr>");
        pw.print("<th>Product Name</th>");
        pw.print("<th>On Sale Date</th>");
        pw.print("</tr>");
        for (ArrayList<String> Item: ProductsOnSaleList) {
            pw.print("<tr>");
            for (int i = 0; i < Item.size(); i++) {
                pw.print("<td>");
                pw.print(Item.get(i));
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<ArrayList<String>> InventoryForChart = MySqlDataStoreUtilities.getInventoryForChart();
			//convert object into json String
			String inventoryJson = new Gson().toJson(InventoryForChart);
			
			response.setContentType("application/JSON");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(inventoryJson);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}