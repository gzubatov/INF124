
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.glassfish.jersey.client.ClientConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author swantoma
 */
public class Confirmation extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		try {
			PrintWriter out = response.getWriter();
			/*
			 * Properties properties = new Properties(); properties.setProperty("useSSL",
			 * "false"); properties.setProperty("allowPublicKeyRetrieval", "true");
			 * properties.setProperty("serverTimezone", "UTC");
			 * properties.setProperty("user", "swantoma");
			 * properties.setProperty("password", "tmp123!"); Connection con =
			 * DriverManager.getConnection("jdbc:mysql://localhost:3306/arts_and_crafts",
			 * properties); int oid = Integer.parseInt(request.getParameter("oid")); String
			 * order_query =
			 * "SELECT first_name, last_name, phone_number, shipping_address, " +
			 * "zip_code, shipping_method, credit_card, price_total" +
			 * " FROM products_in_orders as pio, orders as o" +
			 * " WHERE pio.oid = o.oid AND o.oid = " + oid;
			 * 
			 * String products = "SELECT quantity, name" +
			 * " FROM products_in_orders as pio, products as p" +
			 * " WHERE pio.pid = p.pid AND pio.oid=" + oid;
			 * 
			 * Statement order_statement = con.createStatement(); Statement
			 * product_statement = con.createStatement(); Statement address_statement =
			 * con.createStatement();
			 * 
			 * ResultSet orderSet = order_statement.executeQuery(order_query); ResultSet
			 * productSet = product_statement.executeQuery(products); orderSet.next();
			 * String address_query = "SELECT city, state" + " FROM zip_codes WHERE zip=" +
			 * orderSet.getInt("zip_code"); ResultSet addressSet =
			 * address_statement.executeQuery(address_query); addressSet.next();
			 */

			String oid = request.getParameter("oid");

			ClientConfig config = new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			WebTarget target = client.target(getBaseURI());
			String jsonResponse = target.path("v1").path("api").path("orders").path(oid).request(). // send a request
					accept(MediaType.APPLICATION_JSON). // specify the media type of the response
					get(String.class); // use the get method and return the response as a string

			ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library
			Order order = objectMapper.readValue(jsonResponse, Order.class);

			out.println("<!doctype html>");
			out.println("<html lang=\"en\">");

			out.println("<!--");
			out.println("WRITTEN BY: Greg Zubatov, Swan Toma, Genesis Garcia");
			out.println("EMAIL: gzubatov@uci.edu, sktoma@uci.edu, genesirg@uci.edu");
			out.println("-->");

			out.println("<head>");
			out.println("<meta charset=\"utf-8\">");
			out.println("<meta name=\"author\" content=\"co-authored by Greg Zubatov, Genesis Garcia\">");
			out.println("<meta name=\"description\" content=\"ECommerce Website\">");
			out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");

			out.println("<link rel=\"stylesheet\" href=\"./css/global.css\">");
			out.println("<link rel=\"stylesheet\" href=\"./css/confirmation.css\">");

			out.println("<title>Ant-R-Us</title>");

			out.println("</head>");

			out.println("<body>");
			out.println("<!-- Navbar -->");
			out.println("<div class=\"navbar\">");
			out.println("<div class=\"logo\"><img src=\"./imgs/ant_logo.png\" alt=\"Ants R Us Logo\"></div>");
			out.println("<div class=\"pages\">");
			out.println("<ul class=\"navigation\">");
			out.println("<li><a href=\"/RESTClientServlet/index.jsp\">Home</a></li>");
			out.println("<li><a href=\"./pages/about.html\">About</a></li>");
			out.println("</ul>");
			out.println("</div>");
			out.println("</div>");

			out.println("<!--Confirmation Info-->");
			out.println("<div class = \"conf_info\">");
			out.println("<div class=\"thanks\">");
			out.println("<h1>Thank you," + order.getFirstName() + " " + order.getLastName() + " </h1>");
			out.println("</div>");
			out.println("<div class=\"details\">");
			out.println("<div>");
			out.println("<p><span>Order ID:</span>" + oid + "</p>");
			out.println("<p><span>Total:</span> $" + order.getPriceTotal() + "</p>");
			out.println("<p><span>Contact Number:</span>" + order.getPhoneNumber() + "</p>");
			out.println("</div>");

			out.println("<div>");
			out.println("<p><span>Shipping Address:</span></p>");
			out.println("<p>" + order.getShippingAddress() + "</p>"); // shipping addr.
			out.println("<p>" + order.getCity() + " ," + order.getState() + " " + order.getZipCode() + "</p>"); // city,
																												// state
																												// zipCode
			out.println("<p><span>Shipping Method: </span>" + order.getShippingMethod() + "</p>"); // shipping
																									// method
			out.println("<p><span>Credit card: </span> **** **** **** "
					+ Long.toString(order.getCreditCard()).substring(12) + "</p>"); // credit

			out.println("</div>");

			String[] pids = order.getPids().split(",");
			String pid;
			String qt;

			for (int i = 0; i < pids.length; ++i) {
				// pid = Character.toString(pids[i].charAt(0));
				// qt = pids[i].charAt(2);
				String[] pidQt = pids[i].split(":");
				pid = pidQt[0];
				qt = pidQt[1];
				jsonResponse = target.path("v1").path("api").path("products").path(pid).request(). // send a request
						accept(MediaType.APPLICATION_JSON). // specify the media type of the response
						get(String.class); // use the get method and return the response as a string
				objectMapper = new ObjectMapper(); // This object is from the jackson library
				Product product = objectMapper.readValue(jsonResponse, Product.class);

				out.println("<p><span>Name: </span>" + product.getName() + "</p>");
				out.println("<p><span>Quantity: </span>" + qt + "</p>");
			}

			// out.println("<h3>Your Products</h3>");

			/*
			 * while (productSet.next()) { out.println("<p><span>Name: </span>" +
			 * productSet.getString("name") + "</p>");
			 * out.println("<p><span>Quantity: </span>" + productSet.getInt("quantity") +
			 * "</p>"); }
			 */

			out.println("</div>");
			out.println("</div>");

			out.println("</body>");
			out.println("</html>");
		} catch (Exception e) {
			;
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

	private static URI getBaseURI() {
		// Change the URL here to make the client point to your service.
		return UriBuilder.fromUri("http://localhost:8080/project4").build();
	}
}