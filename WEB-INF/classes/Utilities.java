import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*; 
import java.time.LocalDateTime;   
import java.util.Calendar;
import java.util.Random;


@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session; 
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



/*  Printhtml Function gets the html file name as function Argument, 
If the html file name is Header.html then It gets Username from session variables.
Account ,Cart Information ang Logout Options are Displayed*/

public void printHtml(String file) {
	String result = HtmlToString(file);
	//to print the right navigation in header of username cart and logout etc
	if (file == "Header.html") {
			result=result+"<div id='menu' style='float: right;'><ul>";
		if (session.getAttribute("username")!=null){
			String username = session.getAttribute("username").toString();
			username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
			if(session.getAttribute("usertype").equals("manager"))
			{
				result = result + "<li><a href='AddProduct'><span class='glyphicon'>Addproduct</span></a></li>"
					+ "<li><a href='InventoryChart'><span class='glyphicon'>Inventory</span></a></li>"
					+"<li><a href='SalesReport'><span class='glyphicon'>SalesReport</span></a></li>"
					+"<li><a href='DataVisualization'><span class='glyphicon'>Trending</span></a></li>"
					+"<li><a href='DataAnalytics'><span class='glyphicon'>DataAnalytics</span></a></li>"
					+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
					+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			}
			
			else if(session.getAttribute("usertype").equals("retailer")){
				result = result + "<li><a href='Registration'><span class='glyphicon'>Create Customer</span></a></li>"
					+ "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
					+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
					+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			}
			else
			{
			result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
					+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
					+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
					+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			}
		}
		else
			result = result +"<li><a href='ViewOrder'><span class='glyphicon'>View Order</span></a></li>"+ "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>";
			result = result +"<li><a href='Cart'><span class='glyphicon'>Cart("+CartCount()+")</span></a></li></ul></div></div><div id='page'>";
			pw.print(result);
	} else
			pw.print(result);
}	

	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} 
		catch (Exception e) {
		}
		return result;
	} 

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
	
	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/
	
	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
	
	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}
	
	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<String, User>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{		
				FileInputStream fileInputStream=new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\Tutorial_1\\UserDetails.txt"));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				hm= (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{
			}	
		User user = hm.get(username());
		return user;
	}
	
	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		int size=0;
		try
		{
			orderPayments =MySqlDataStoreUtilities.selectOrder();
				
		}
		catch(Exception e)
		{
			
		}
		for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
				size=entry.getKey();
		}
			
		return size;		
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}
	
	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

	public void storeProduct(String name,String type,String maker, String acc){
		if(!OrdersHashMap.orders.containsKey(username())){	
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		if(type.equals("tvs")){
			Tv tv;
			tv = SaxParserDataStore.tvs.get(name);
			OrderItem orderitem = new OrderItem(tv.getName(), tv.getPrice(), tv.getImage(), tv.getRetailer(), "tvs", tv.getDiscount());
			orderItems.add(orderitem);
		}
		if(type.equals("soundsystems")){
			SoundSystem soundsystem = null;
			soundsystem = SaxParserDataStore.soundsystems.get(name);
			OrderItem orderitem = new OrderItem(soundsystem.getName(), soundsystem.getPrice(), soundsystem.getImage(), soundsystem.getRetailer(), "soundsystems", soundsystem.getDiscount());
			orderItems.add(orderitem);
		}
		if(type.equals("phones")){
			Phone phone = null;
			phone = SaxParserDataStore.phones.get(name);
			OrderItem orderitem = new OrderItem(phone.getName(), phone.getPrice(), phone.getImage(), phone.getRetailer(), "phones", phone.getDiscount());
			orderItems.add(orderitem);
		}
		if(type.equals("accessories")){	
			Accessory accessory = SaxParserDataStore.accessories.get(name); 
			OrderItem orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage(), accessory.getRetailer(), "accessories", accessory.getDiscount());
			orderItems.add(orderitem);
		}
		
	}

	public boolean isDeliverOnTime(Date dateExpected, Date dateActual) {
		boolean flag = false;
		try {
			if (dateExpected.compareTo(dateActual) > 0) {
				flag = true;
			} else if (dateExpected.compareTo(dateActual) < 0) {
				flag = false;
			} else if (dateExpected.compareTo(dateActual) == 0) {
				flag = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String randomTrackingId() {
		StringBuilder trackingId = new StringBuilder(10);
		try {
			String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			for (int i = 0; i < 10; i++) {
				trackingId.append(chars.charAt(new Random().nextInt(chars.length())));
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
		return trackingId.toString();
	}

	// store the payment details for orders
	public void storePayment(int orderId,
		String orderName,double orderPrice,String userAddress,String creditCardNo,String customer, String category, double discount, String storeName, String retailer, String deliveryType){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
			// get the payment details file 
		try
		{
			orderPayments=MySqlDataStoreUtilities.selectOrder();
		}
		catch(Exception e)
		{
			
		}
		if(orderPayments==null)
		{
			orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		}
			// if there exist order id already add it into same list for order id or create a new record with order id
			
		if(!orderPayments.containsKey(orderId)){	
			ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
			orderPayments.put(orderId, arr);
		}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);		
		
		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo, category, discount, storeName);
		listOrderPayment.add(orderpayment);	
			
		Date purchaseDate = new Date(Calendar.getInstance().getTimeInMillis());
		Date shipDate = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(shipDate); 
		c.add(Calendar.DATE, 7);
		shipDate = c.getTime();
		int quantity = 1;
		double shippingCost = 9.99;
		double totalSales = orderPrice * quantity;
		//get store informtaion from database
		ArrayList<String> storeArr = MySqlDataStoreUtilities.getStoreInfo(storeName);
		String storeId = "";
		String storeAddress ="";
		if (deliveryType.equals("Home Delivery")) {
			storeId = "N/A";
			storeAddress = "N/A";
		} else {
			storeId = storeArr.get(0);
		    storeAddress = storeArr.get(2) + ", " + storeArr.get(3) + ", " + storeArr.get(4) + ", " + storeArr.get(5);

		}
			
		// variables to store transaction data
		//get customer data and store in variables
		ArrayList<String> customerArr = MySqlDataStoreUtilities.getCustomerInfo(username());
		String loginId = username(); //username
		String customerName = customerArr.get(0); //get customer's full name
		int customerAge = Integer.parseInt(customerArr.get(1)); //get customer's age
		String customerOccupation = customerArr.get(2); //get customer's occupation
		String creditCardNumber = creditCardNo;
		// int orderId
		// get random integar for date interval
		int randomExpected = new Random().nextInt(9);
		int ramdomActual = new Random().nextInt(9);
		//assign random date to expectedDeliveryDate and actualDeliveryDate
		Date orderDate = purchaseDate;
		//assign random date to expectedDeliveryDate and actualDeliveryDate
		Date expectedDeliveryDate = new Date();
		c.setTime(expectedDeliveryDate); 
		c.add(Calendar.DATE, randomExpected);
		expectedDeliveryDate = c.getTime();
		Date actualDeliveryDate = new Date();
		c.setTime(actualDeliveryDate); 
		c.add(Calendar.DATE, ramdomActual);
		actualDeliveryDate = c.getTime();

		String productId = orderName;
		String productName = orderName;
		// String category
		String manufacturer = retailer;
		double reviewRating = new Random().nextInt(5);
		String deliveryTrackingId = randomTrackingId();
		// String deliveryType
		String deliveryZipCode = "";
		if (deliveryType.equals("Home Delivery")) {
			deliveryZipCode = userAddress;
		} else {
			deliveryZipCode = storeArr.get(5);
		}
			 
		
		//create strings to select randomly
		String[] status = {"Approved","Disputed"};
		String transactionStatus = status[new Random().nextInt(status.length)];
		boolean orderReturned = new Random().nextBoolean();
		boolean orderDeliverOnTime = isDeliverOnTime(expectedDeliveryDate, actualDeliveryDate);


			// add order details into database
		try
		{	if(session.getAttribute("usertype").equals("retailer"))
			{
				MySqlDataStoreUtilities.insertOrder(orderId, customer, orderPrice, userAddress, creditCardNo, purchaseDate, shipDate, orderName, category, quantity, shippingCost, discount, totalSales, storeId, storeAddress);
			}else{
				MySqlDataStoreUtilities.insertOrder(orderId, username(), orderPrice, userAddress, creditCardNo, purchaseDate, shipDate, orderName, category, quantity, shippingCost, discount, totalSales, storeId, storeAddress);
				//create a transaction whenever an order is placed
				MySqlDataStoreUtilities.createTransaction(loginId, customerName, customerAge, customerOccupation, creditCardNumber, orderId, orderDate, expectedDeliveryDate, actualDeliveryDate, productId, productName, category, manufacturer, reviewRating, deliveryTrackingId, deliveryType, deliveryZipCode, transactionStatus, orderReturned, orderDeliverOnTime);
			}
		}
		catch(Exception e)
		{
			System.out.println("inside exception file not written properly");
		}	
	}
	
	public String storeReview(String productname,String producttype,String productmaker,String reviewrating,String reviewdate,String  reviewtext,String reatilerpin,String price,String city, String age, String gender, String occupation, String retailerstoreid, String rebate, String productonsale, String retailerstate){
		String message=MongoDBDataStoreUtilities.insertReview(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city,age,gender,occupation,retailerstoreid,rebate,productonsale,retailerstate);
			if(!message.equals("Successfull"))
			{ return "UnSuccessfull";
			}
			else
			{
			HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
			try
			{
				reviews=MongoDBDataStoreUtilities.selectReview();
			}
			catch(Exception e)
			{
				
			}
			if(reviews==null)
			{
				reviews = new HashMap<String, ArrayList<Review>>();
			}
				// if there exist product review already add it into same list for productname or create a new record with product name
				
			if(!reviews.containsKey(productname)){	
				ArrayList<Review> arr = new ArrayList<Review>();
				reviews.put(productname, arr);
			}
			ArrayList<Review> listReview = reviews.get(productname);		
			Review review = new Review(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city,age,gender,occupation,retailerstoreid,rebate,productonsale,retailerstate);
			listReview.add(review);	
				
				// add Reviews into database
			
			return "Successfull";	
			}
		}
		

	
	/* getConsoles Functions returns the Hashmap with all consoles in the store.*/

	public HashMap<String, Tv> getTvs(){
			HashMap<String, Tv> hm = new HashMap<String, Tv>();
			hm.putAll(SaxParserDataStore.tvs);
			return hm;
	}
	
	/* getGames Functions returns the  Hashmap with all Games in the store.*/

	public HashMap<String, SoundSystem> getSoundSystems(){
			HashMap<String, SoundSystem> hm = new HashMap<String, SoundSystem>();
			hm.putAll(SaxParserDataStore.soundsystems);
			return hm;
	}
	
	/* getTablets Functions returns the Hashmap with all Tablet in the store.*/

	public HashMap<String, Phone> getPhones(){
			HashMap<String, Phone> hm = new HashMap<String, Phone>();
			hm.putAll(SaxParserDataStore.phones);
			return hm;
	}
	
	/* getProducts Functions returns the Arraylist of consoles in the store.*/

	public ArrayList<String> getProducts(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Tv> entry : getTvs().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of games in the store.*/

	public ArrayList<String> getProductsSoundSystem(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, SoundSystem> entry : getSoundSystems().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of Tablets in the store.*/

	public ArrayList<String> getProductsPhones(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Phone> entry : getPhones().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	

}
