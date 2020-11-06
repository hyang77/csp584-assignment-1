import java.sql.*;
import java.util.*;
import java.util.Date;
                	
public class MySqlDataStoreUtilities
{
static Connection conn = null;
static String message;
public static String getConnection()
{

	try
	{
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","1234");							
	message="Successfull";
	return message;
	}
	catch(SQLException e)
	{
		message="unsuccessful";
		     return message;
	}
	catch(Exception e)
	{
		message=e.getMessage();
		return message;
	}
}


public static void deleteOrder(int orderId,String orderName)
{
	try
	{
		
		getConnection();
		String deleteOrderQuery ="Delete from customerorders where OrderId=? and productId=?";
		PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
		pst.setInt(1,orderId);
		pst.setString(2,orderName);
		pst.executeUpdate();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

public static void createTransaction(String loginId, String customerName, int customerAge, String customerOccupation, String creditCardNumber, int orderId, Date orderDate, Date expectedDeliveryDate, Date actualDeliveryDate, String productId, String productName, String category, String manufacturer, double reviewRating, String deliveryTrackingId, String deliveryType, String deliveryZipCode, String transactionStatus, boolean orderReturned, boolean orderDeliverOnTime) {
	try {
		getConnection();
		String createTransactionQuery = "INSERT INTO Transactions(loginId, customerName, customerAge, customerOccupation, creditCardNumber, orderId, orderDate, expectedDeliveryDate, actualDeliveryDate, productId, productName, category, manufacturer, reviewRating, deliveryTrackingId, deliveryType, deliveryZipCode, transactionStatus, orderReturned, orderDeliverOnTime)" + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

		PreparedStatement pst = conn.prepareStatement(createTransactionQuery);

		//set the parameter for each column and execute the prepared statement
		pst.setString(1,loginId);
		pst.setString(2,customerName);
		pst.setInt(3,customerAge);
		pst.setString(4,customerOccupation);
		pst.setString(5,creditCardNumber);
		pst.setInt(6,orderId);
		pst.setDate(7,new java.sql.Date(orderDate.getTime()));
		pst.setDate(8,new java.sql.Date(expectedDeliveryDate.getTime()));
		pst.setDate(9,new java.sql.Date(actualDeliveryDate.getTime()));
		pst.setString(10,productId);
		pst.setString(11,productName);
		pst.setString(12,category);
		pst.setString(13,manufacturer);
		pst.setDouble(14,reviewRating);
		pst.setString(15,deliveryTrackingId);
		pst.setString(16,deliveryType);
		pst.setString(17,deliveryZipCode);
		pst.setString(18,transactionStatus);
		pst.setBoolean(19,orderReturned);
		pst.setBoolean(20,orderDeliverOnTime);

		pst.execute();

	} catch(Exception e) {
		e.printStackTrace();
	}
}

public static void insertOrder(int orderId,String userName,double price,String userAddress,String creditCardNo, Date purchaseDate, Date shipDate, String productId, String category, int quantity, double shippingCost, double discount, double totalSales, String storeId, String storeAddress)
//MySqlDataStoreUtilities.insertOrder(orderId, username(), orderName, orderPrice, userAddress, creditCardNo, purchaseDate, shipDate, quantity, category, shippingCost, discount, totalSales, storeId, storeAddress);
{
	try
	{
	
		getConnection();
		String insertIntoCustomerOrderQuery = "INSERT INTO customerOrders(orderId,userName,price,userAddress,creditCardNo,purchaseDate,shipDate,productId,category,quantity,shippingCost,discount,totalSales,storeId,storeAddress)"
		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";	
			
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		//set the parameter for each column and execute the prepared statement
		pst.setInt(1,orderId);
		pst.setString(2,userName);
		pst.setDouble(3,price);
		pst.setString(4,userAddress);
		pst.setString(5,creditCardNo);
		java.sql.Date pd = new java.sql.Date(purchaseDate.getTime());
		pst.setDate(6,pd);
		java.sql.Date sd = new java.sql.Date(shipDate.getTime());
		pst.setDate(7,sd);
		pst.setString(8,productId);
		pst.setString(9,category);
		pst.setInt(10,quantity);
		pst.setDouble(11,shippingCost);
		pst.setDouble(12,discount);
		pst.setDouble(13,totalSales);
		pst.setString(14,storeId);
		pst.setString(15,storeAddress);

		pst.execute();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}		
}

public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder()
{	

	HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();
		
	try
	{					

		getConnection();
        //select the table 
		String selectOrderQuery ="select * from customerorders";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();	
		ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
		while(rs.next())
		{
			if(!orderPayments.containsKey(rs.getInt("orderId")))
			{	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(rs.getInt("orderId"), arr);
			}
			ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("orderId"));			
			System.out.println("data is"+rs.getInt("orderId"));

			//add to orderpayment hashmap
			OrderPayment order= new OrderPayment(rs.getInt("orderId"),rs.getString("userName"),rs.getString("productId"),rs.getDouble("price"),rs.getString("userAddress"),rs.getString("creditCardNo"),rs.getString("category"), rs.getDouble("discount"), rs.getString("storeId"));
			listOrderPayment.add(order);
		}
				
					
	}
	catch(Exception e)
	{
		System.out.println(e.toString());
	}
	return orderPayments;
}


public static void insertUser(String username,String password,String repassword,String usertype)
{
	try
	{	

		getConnection();
		String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,usertype) "
		+ "VALUES (?,?,?,?);";	
				
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
		pst.setString(1,username);
		pst.setString(2,password);
		pst.setString(3,repassword);
		pst.setString(4,usertype);
		pst.execute();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
}

public static HashMap<String,User> selectUser()
{	
	HashMap<String,User> hm=new HashMap<String,User>();
	try 
	{
		getConnection();
		Statement stmt=conn.createStatement();
		String selectCustomerQuery="select * from  Registration";
		ResultSet rs = stmt.executeQuery(selectCustomerQuery);
		while(rs.next())
		{	User user = new User(rs.getString("username"),rs.getString("password"),rs.getString("usertype"));
				hm.put(rs.getString("username"), user);
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return hm;			
}

public static ArrayList<Inventory> getInventoryForChart() 
{
	ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
	try 
	{
		getConnection();
		Statement stmt=conn.createStatement();
		String selectInventoryQuery="SELECT productdetails.productName,Inventory.quantity" + " FROM productdetails" + " INNER JOIN Inventory ON productdetails.Id = Inventory.productId " + "ORDER BY Inventory.quantity;";
		ResultSet rs = stmt.executeQuery(selectInventoryQuery);
		while(rs.next())
		{	
			Inventory inventory= new Inventory(rs.getString("productName"), rs.getInt("quantity"));
			inventoryList.add(inventory);	
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return inventoryList;
}

public static ArrayList<SoldProduct> getSoldProductForChart() 
{
	ArrayList<SoldProduct> soldProductList = new ArrayList<SoldProduct>();
	try 
	{
		getConnection();
		Statement stmt=conn.createStatement();
		String selectSoldProductQuery="SELECT productId AS productName, SUM(quantity) * price AS total_sales " + "FROM CustomerOrders " + "GROUP BY productId " + "ORDER BY total_sales DESC;";
		ResultSet rs = stmt.executeQuery(selectSoldProductQuery);
		while(rs.next())
		{	
			SoldProduct soldproduct= new SoldProduct(rs.getString("productName"), rs.getDouble("total_sales"));
			soldProductList.add(soldproduct);	
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return soldProductList;
}

	

public static ArrayList<ArrayList<String>> getInventoryList()
{	
	ArrayList<ArrayList<String>> InventoryList = new ArrayList<ArrayList<String>>();
	try 
	{
		getConnection();
		Statement stmt=conn.createStatement();
		String selectInventoryQuery="SELECT productdetails.productName,productdetails.productPrice,Inventory.quantity" + " FROM productdetails" + " INNER JOIN Inventory ON productdetails.Id = Inventory.productId " + "ORDER BY Inventory.quantity;";
		ResultSet rs = stmt.executeQuery(selectInventoryQuery);
		int i = 0;
		while(rs.next())
		{	
			InventoryList.add(new ArrayList<String>());
			InventoryList.get(i).add(rs.getString("productName"));
			InventoryList.get(i).add(Double.toString(rs.getDouble("productPrice")));
			InventoryList.get(i).add(Integer.toString(rs.getInt("quantity")));
			i++;			
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return InventoryList;			
}

public static ArrayList<ArrayList<String>> getSoldProducts()
{	
	ArrayList<ArrayList<String>> SoldProductList = new ArrayList<ArrayList<String>>();
	try 
	{
		// SELECT productId AS productName, price, SUM(quantity) As sold_quantity, SUM(quantity) * price AS total_sales
		// FROM CustomerOrders
		// GROUP BY productId;
		getConnection();
		Statement stmt=conn.createStatement();
		String selectSoldProductQuery="SELECT productId AS productName, price, SUM(quantity) As sold_quantity, SUM(quantity) * price AS total_sales " + "FROM CustomerOrders " + "GROUP BY productId " + "ORDER BY total_sales DESC;";
		ResultSet rs = stmt.executeQuery(selectSoldProductQuery);
		int i = 0;
		while(rs.next())
		{		
			SoldProductList.add(new ArrayList<String>());
			SoldProductList.get(i).add(rs.getString("productName"));
			SoldProductList.get(i).add(Double.toString(rs.getDouble("price")));
			SoldProductList.get(i).add(Integer.toString(rs.getInt("sold_quantity")));
			SoldProductList.get(i).add(Double.toString(rs.getDouble("total_sales")));
			i++;

		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return SoldProductList;			
}

public static ArrayList<ArrayList<String>> getProductsOnSaleList()
{	
	ArrayList<ArrayList<String>> ProductsOnSaleList = new ArrayList<ArrayList<String>>();
	try 
	{
		// SELECT productId AS productName, price, SUM(quantity) As sold_quantity, SUM(quantity) * price AS total_sales
		// FROM CustomerOrders
		// GROUP BY productId;
		getConnection();
		Statement stmt=conn.createStatement();
		String selectProductsOnSaleQuery="select * from productOnSale Order by onSaleDate;";
		ResultSet rs = stmt.executeQuery(selectProductsOnSaleQuery);
		int i = 0;
		while(rs.next())
		{		
			ProductsOnSaleList.add(new ArrayList<String>());
			ProductsOnSaleList.get(i).add(rs.getString("productId"));
			ProductsOnSaleList.get(i).add(rs.getDate("onSaleDate").toString());
			i++;

		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return ProductsOnSaleList;			
}

public static ArrayList<ArrayList<String>> getTotalSales()
{	
	//SELECT DATE(purchaseDate), SUM(totalSales)
	// FROM CustomerOrders
	// GROUP BY purchaseDate;
	ArrayList<ArrayList<String>> TotalSalesList = new ArrayList<ArrayList<String>>();
	try 
	{
		
		getConnection();
		Statement stmt=conn.createStatement();
		String selectTotalSalesQuery="SELECT DATE(purchaseDate) AS date, SUM(totalSales) AS total_sales " + "FROM CustomerOrders " + "GROUP BY date " + "ORDER BY date DESC";
		ResultSet rs = stmt.executeQuery(selectTotalSalesQuery);
		int i = 0;
		while(rs.next())
		{		
			TotalSalesList.add(new ArrayList<String>());
			TotalSalesList.get(i).add(rs.getDate("date").toString());
			TotalSalesList.get(i).add(Double.toString(rs.getDouble("total_sales")));
			i++;
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return TotalSalesList;			
}


public static ArrayList<ArrayList<String>> getRebatedProducts()
{	
	// SELECT productName, productDiscount
	// FROM productdetails;
	ArrayList<ArrayList<String>> RebatedProductsList = new ArrayList<ArrayList<String>>();
	try 
	{
		
		getConnection();
		Statement stmt=conn.createStatement();
		String selectRebatedProductsQuery="SELECT productName, productDiscount " + "FROM productdetails " + "ORDER BY productDiscount DESC;";
		ResultSet rs = stmt.executeQuery(selectRebatedProductsQuery);
		int i = 0;
		while(rs.next())
		{		
			RebatedProductsList.add(new ArrayList<String>());
			RebatedProductsList.get(i).add(rs.getString("productName"));
			RebatedProductsList.get(i).add(Double.toString(rs.getDouble("productDiscount")));
			i++;
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return RebatedProductsList;			
}

public static void Insertproducts()
{
	try{
		
		
		getConnection();
		
		
		String truncatetableacc = "delete from Product_accessories;";
		PreparedStatement pstt = conn.prepareStatement(truncatetableacc);
		pstt.executeUpdate();
		
		String truncatetableprod = "delete from  Productdetails;";
		PreparedStatement psttprod = conn.prepareStatement(truncatetableprod);
		psttprod.executeUpdate();
		
				
		
		String insertProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount)" +
		"VALUES (?,?,?,?,?,?,?,?);";
		for(Map.Entry<String,Accessory> entry : SaxParserDataStore.accessories.entrySet())
		{   
			String name = "accessories";
	        Accessory acc = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,acc.getId());
			pst.setString(3,acc.getName());
			pst.setDouble(4,acc.getPrice());
			pst.setString(5,acc.getImage());
			pst.setString(6,acc.getRetailer());
			pst.setString(7,acc.getCondition());
			pst.setDouble(8,acc.getDiscount());
			
			pst.executeUpdate();
			
			
		}
		
		for(Map.Entry<String,Tv> entry : SaxParserDataStore.tvs.entrySet())
		{   
	        Tv tv = entry.getValue();
			String name = "tvs";
			
						
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,tv.getId());
			pst.setString(3,tv.getName());
			pst.setDouble(4,tv.getPrice());
			pst.setString(5,tv.getImage());
			pst.setString(6,tv.getRetailer());
			pst.setString(7,tv.getCondition());
			pst.setDouble(8,tv.getDiscount());
			
			pst.executeUpdate();

			try{
			HashMap<String,String> acc = tv.getAccessories();
			String insertAccessoryQurey = "INSERT INTO  Product_accessories(productName,accessoriesName)" +
			"VALUES (?,?);";
			for(Map.Entry<String,String> accentry : acc.entrySet())
			{
				PreparedStatement pstacc = conn.prepareStatement(insertAccessoryQurey);
				pstacc.setString(1,tv.getId());
				pstacc.setString(2,accentry.getValue());
				pstacc.executeUpdate();
			}
			}catch(Exception et){
				et.printStackTrace();
			}
		}
		for(Map.Entry<String,SoundSystem> entry : SaxParserDataStore.soundsystems.entrySet())
		{   
			String name = "soundsystems";
	        SoundSystem soundsystem = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,soundsystem.getId());
			pst.setString(3,soundsystem.getName());
			pst.setDouble(4,soundsystem.getPrice());
			pst.setString(5,soundsystem.getImage());
			pst.setString(6,soundsystem.getRetailer());
			pst.setString(7,soundsystem.getCondition());
			pst.setDouble(8,soundsystem.getDiscount());
			
			pst.executeUpdate();
			
			
		}
		for(Map.Entry<String,Phone> entry : SaxParserDataStore.phones.entrySet())
		{   
			String name = "phones";
	        Phone phone = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,phone.getId());
			pst.setString(3,phone.getName());
			pst.setDouble(4,phone.getPrice());
			pst.setString(5,phone.getImage());
			pst.setString(6,phone.getRetailer());
			pst.setString(7,phone.getCondition());
			pst.setDouble(8,phone.getDiscount());
			
			pst.executeUpdate();
			
			
		}
		
	}catch(Exception e)
	{
  		e.printStackTrace();
	}
} 

public static HashMap<String,Tv> getTvs()
{	
	HashMap<String,Tv> hm=new HashMap<String,Tv>();
	try 
	{
		getConnection();
		
		String selectTv="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectTv);
		pst.setString(1,"tvs");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Tv tv = new Tv(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), tv);
				tv.setId(rs.getString("Id"));
				
				try
				{
				String selectaccessory = "Select * from Product_accessories where productName=?";
				PreparedStatement pstacc = conn.prepareStatement(selectaccessory);
				pstacc.setString(1,rs.getString("Id"));
				ResultSet rsacc = pstacc.executeQuery();
				
				HashMap<String,String> acchashmap = new HashMap<String,String>();
				while(rsacc.next())
				{    
					if (rsacc.getString("accessoriesName") != null){
					
					 acchashmap.put(rsacc.getString("accessoriesName"),rsacc.getString("accessoriesName"));
					 tv.setAccessories(acchashmap);
					}
					 
				}					
				}catch(Exception e)
				{
						e.printStackTrace();
				}
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return hm;			
}

public static HashMap<String,SoundSystem> getSoundSystems()
{	
	HashMap<String,SoundSystem> hm=new HashMap<String,SoundSystem>();
	try 
	{
		getConnection();
		
		String selectSoundSystem="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectSoundSystem);
		pst.setString(1,"soundsystems");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	SoundSystem sound = new SoundSystem(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), sound);
				sound.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return hm;			
}

public static HashMap<String,Phone> getPhones()
{	
	HashMap<String,Phone> hm=new HashMap<String,Phone>();
	try 
	{
		getConnection();
		
		String selectPhone="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectPhone);
		pst.setString(1,"phones");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Phone phone = new Phone(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), phone);
				phone.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return hm;			
}

public static HashMap<String,Accessory> getAccessories()
{	
	HashMap<String,Accessory> hm=new HashMap<String,Accessory>();
	try 
	{
		getConnection();
		
		String selectAcc="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectAcc);
		pst.setString(1,"accessories");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Accessory acc = new Accessory(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), acc);
				acc.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return hm;			
}

	
}	