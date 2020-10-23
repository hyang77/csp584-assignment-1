import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/SoldProduct")


public class SoldProduct extends HttpServlet {
    private String productName;
	private double totalSales;
	
	public SoldProduct(String productName, double totalSales){
		this.productName=productName;
		this.totalSales=totalSales;
	}
	
	public String getproductName() {
		return productName;
	}

	public void setproductName(String productName) {
		this.productName = productName;
	}

	public double getQuantity() {
		return totalSales;
	}

	public void setQuantity(double totalSales) {
		this.totalSales = totalSales;
	}

}
