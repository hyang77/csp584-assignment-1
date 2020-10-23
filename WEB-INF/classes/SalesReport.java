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

@WebServlet("/SalesReport")

public class SalesReport extends HttpServlet {


	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        String name = "Trending";

        ArrayList<ArrayList<String>> SoldProductsList = MySqlDataStoreUtilities.getSoldProducts();
        ArrayList<ArrayList<String>> TotalSalesList = MySqlDataStoreUtilities.getTotalSales();
        

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        //generate sold product report
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Sold Products Report</a>");
        pw.print("</h2><div class='entry'><table id='bestseller'>");
        //print table header
        pw.print("<tr>");
        pw.print("<th>Product Name</th>");
        pw.print("<th>Product Price</th>");
        pw.print("<th>Sold Amount</th>");
        pw.print("<th>Total Sales</th>");
        pw.print("</tr>");
        //create total sales report table
        for (ArrayList<String> Item: SoldProductsList) {
            pw.print("<tr>");
            for (int i = 0; i < Item.size(); i++) {
                pw.print("<td>");
                pw.print(Item.get(i));
                pw.print("</td>");
            }
            pw.print("</tr>");
        }
        
        pw.print("</table></div></div></div>");
        
        //click the buttont to view the sold product bar chart
		pw.print("<div id='content'><div class='post'>");
        pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Sold Products Data Visualization</a></h2>"
                + "<div class='entry'>");
        pw.print("<h3><button id='btnGetChartData2'>View Chart</h3>");
        pw.println("<div id='chart_div'></div>");
        pw.println("</div></div></div>");
        pw.println("<script type='text/javascript' src=\"https://www.gstatic.com/charts/loader.js\"></script>");
        pw.println("<script type='text/javascript' src='SoldProductChart.js'></script>");

        //generate total sales report
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Total Daily Sales Report</a>");
        pw.print("</h2><div class='entry'><table id='bestseller'>");
        //print table header
        pw.print("<tr>");
        pw.print("<th>Date</th>");
        pw.print("<th>Total Sales</th>");
        pw.print("</tr>");
        //create total sales report table
        for (ArrayList<String> TotalSalesItem: TotalSalesList) {
            pw.print("<tr>");
            for (int i = 0; i < TotalSalesItem.size(); i++) {
                pw.print("<td>");
                pw.print(TotalSalesItem.get(i));
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
            ArrayList<SoldProduct> SoldProductForChart = MySqlDataStoreUtilities.getSoldProductForChart();
            //convert object into json String
            String soldProductJson = new Gson().toJson(SoldProductForChart);
            
            response.setContentType("application/JSON");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(soldProductJson);
        } catch(Exception e) {
            e.printStackTrace();
        }

	}

}