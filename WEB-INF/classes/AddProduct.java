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

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String price = request.getParameter("price");
		String category = request.getParameter("categorie");
		String manufacture = request.getParameter("manufacture");
		String discount = request.getParameter("discount");
		
		if(id == null) id = "";
		if(name == null) name = "";
		if(price == null) price = "";
		if(category == null) category = "";
		if(manufacture == null) manufacture = "";
		if(discount == null) discount = "";

		String tv_select = "";
		String sound_select = "";
		String phone_select = "";

		if(category.equals("tv")){
			tv_select = "selected";
		}
		else if(category.equals("soundsystem")){
			sound_select = "selected";
		}
		else if(category.equals("phone")){
			phone_select = "selected";
		}

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		//utility.printHtml("AddProduct.html");
		pw.print("<div id='content'><form method='post' action='AddProduct' class='form-horizontal'><fieldset><legend>Add PRODUCTS</legend>");
		pw.print("<div class='form-group'>" +
			    "<label class='col-md-4 control-label' for='product_id'>PRODUCT ID</label>" +
			    "<div class='col-md-4'>" +
				    "<input id='product_id' name='product_id' class='form-control input-md' required='' type='text' value='"+id+"'>" +
			    "</div>" +
			"</div>");
		pw.print("<div class='form-group'>" +
			    "<label class='col-md-4 control-label' for='product_name'>PRODUCT NAME</label>" +
			    "<div class='col-md-4'>" +
				    "<input id='product_name' name='product_name' class='form-control input-md' required='' type='text' value='"+name+"'>" +
			    "</div>" +
			"</div>");
		pw.print("<div class='form-group'>" +
			    "<label class='col-md-4 control-label' for='product_price'>PRODUCT PRICE</label>" +
			    "<div class='col-md-4'>" +
				    "<input id='product_price' name='product_price' class='form-control input-md' required='' type='text' value='"+price+"'>" +
			    "</div>" +
			"</div>");
		pw.print("<div class='form-group'>" +
			    "<label class='col-md-4 control-label' for='product_categorie'>PRODUCT CATEGORY</label>" +
			    "<div class='col-md-4'>" +
			    	"<select id='product_categorie' name='product_categorie' class='form-control'>" +
				"<option value='tv' "+ tv_select +">tv</option>" +
				"<option value='soundsystem' "+ sound_select +">soundsystem</option>" +
				"<option value='phone' "+phone_select+">phone</option>" +
				"</select>" +
			    "</div>" +
			"</div>");
		pw.print("<div class='form-group'>" +
			    "<label class='col-md-4 control-label' for='manufacture'>MANUFACTURE</label>" +
			    "<div class='col-md-4'>" +
				    "<input id='manufacture' name='manufacture' class='form-control input-md' required='' type='text' value='"+manufacture+"'>" +
			    "</div>" +
			"</div>");
		pw.print("<div class='form-group'>" +
			    "<label class='col-md-4 control-label' for='discount'>DISCOUNT</label>" +
			    "<div class='col-md-4'>" +
				    "<input id='discount' name='discount' class='form-control input-md' required='' type='text' value='"+discount+"'>" +
			    "</div>" +
			"</div>");
		pw.print("<div class='form-group'>" +
				"<div class='col-md-4'>" +
					"<input type='submit' id='btnadd' name='btnadd' class='btn btn-primary'>" +
				"</div>" +
			"</div>");
		pw.print("</fieldset></form></div>");
		utility.printHtml("Footer.html");
		
	}
		
				
	

}
