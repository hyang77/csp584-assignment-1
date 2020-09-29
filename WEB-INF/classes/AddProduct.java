import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/AddProduct")


public class AddProduct extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		String id = request.getParameter("product_id");
		String name = request.getParameter("product_name");
		String price = request.getParameter("product_price");
		String category = request.getParameter("product_categorie");
		String manufacture = request.getParameter("manufacture");
		String condition = "New";
		String discount = request.getParameter("discount");


		if (category.equals("tv")) {

			//add new product into hashmap
			Tv tv = new Tv();
			tv.setId(id);
			tv.setName(name);
			tv.setPrice(Double.parseDouble(price));
			tv.setImage("");
			tv.setRetailer(manufacture);
			tv.setCondition(condition);
			tv.setDiscount(Double.parseDouble(discount));
			SaxParserDataStore.tvs.put(tv.getId(), tv);
			SaxParserDataStore.save();
			System.out.println("New tv added successfully!");
			
			response.sendRedirect("TvList");
		} else if ( category.equals("soundsystem")) {

			//add new product into hashmap
			SoundSystem soundsystem = new SoundSystem();
			soundsystem.setId(id);
			soundsystem.setName(name);
			soundsystem.setPrice(Double.parseDouble(price));
			soundsystem.setImage("");
			soundsystem.setRetailer(manufacture);
			soundsystem.setCondition(condition);
			soundsystem.setDiscount(Double.parseDouble(discount));
			SaxParserDataStore.soundsystems.put(soundsystem.getId(), soundsystem);
			SaxParserDataStore.save();
			System.out.println("New soundsystem added successfully!");
		
			response.sendRedirect("SoundSystemList");
		} else if ( category.equals("phone")) {
			//add new product into hashmap
			Phone phone = new Phone();
			phone.setId(id);
			phone.setName(name);
			phone.setPrice(Double.parseDouble(price));
			phone.setImage("");
			phone.setRetailer(manufacture);
			phone.setCondition(condition);
			phone.setDiscount(Double.parseDouble(discount));
			SaxParserDataStore.phones.put(phone.getId(), phone);
			SaxParserDataStore.save();
			System.out.println("New phone added successfully!");

			response.sendRedirect("PhoneList");
		} else {

			//add new product into hashmap
			Accessory accessory = new Accessory();
			accessory.setId(id);
			accessory.setName(name);
			accessory.setPrice(Double.parseDouble(price));
			accessory.setImage("");
			accessory.setRetailer(manufacture);
			accessory.setCondition(condition);
			accessory.setDiscount(Double.parseDouble(discount));
			SaxParserDataStore.accessories.put(accessory.getId(), accessory);
			SaxParserDataStore.save();
			System.out.println("New phone added successfully!");
			
			response.sendRedirect("AccessoryList");
		}
		
		
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
