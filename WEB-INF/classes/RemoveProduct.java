import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RemoveProduct")

public class RemoveProduct extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* From the HttpServletRequest variable name,type,maker and acessories information are obtained.*/
		Utilities utility = new Utilities(request, pw);
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String maker = request.getParameter("maker");
		String access = request.getParameter("access");

        //get id from the selected product
        System.out.println(name + "was deleted!");
        // System.out.println(type);

        if(type.equals("tvs")) {
            SaxParserDataStore.tvs.remove(name);
            SaxParserDataStore.save();
            response.sendRedirect("TvList");
            // System.out.println("tv");
        } else if (type.equals("soundsystems")) {
            SaxParserDataStore.soundsystems.remove(name);
            SaxParserDataStore.save();
            response.sendRedirect("SoundSystemList");
            // System.out.println("sound");
        } else if (type.equals("phones")) {
            SaxParserDataStore.phones.remove(name);
            SaxParserDataStore.save();
            response.sendRedirect("PhoneList");
            // System.out.println("phone");
        } else {
            SaxParserDataStore.accessories.remove(name);
            SaxParserDataStore.save();
            response.sendRedirect("AccessoryList");
            // System.out.println("acc");
        }

        // read an updated ProductCatalog.xml to reflect updates in the dabatase
        MySqlDataStoreUtilities.Insertproducts();
        System.out.println("Productdetails table updated");

    }
}