import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddProduct")


public class AddProduct extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		String id = request.getParameter("product_id");
		String name = request.getParameter("product_name");
		String price = request.getParameter("product_name");
		String category = request.getParameter("product_name");
		String manufacture = request.getParameter("product_name");
		String condition = "New";
		String discount = request.getParameter("product_name");


		System.out.println(id);
		System.out.println(name);
		System.out.println(price);
		System.out.println(category);
		System.out.println(manufacture);
		System.out.println(discount);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		utility.printHtml("AddProduct.html");
		utility.printHtml("Footer.html");
		
	}
		
				
	

}
