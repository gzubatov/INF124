
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author swantoma
 */
public class Checkout extends HttpServlet {

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
			throws ServletException, IOException, SQLException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			HttpSession session = request.getSession(true);
			ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");
			int cartSize = cart.size();

			Properties properties = new Properties();
			properties.setProperty("useSSL", "false");
			properties.setProperty("allowPublicKeyRetrieval", "true");
			properties.setProperty("serverTimezone", "UTC");
			properties.setProperty("user", "swantoma");
			properties.setProperty("password", "tmp123!");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arts_and_crafts", properties);
			Statement statement = con.createStatement();
			ResultSet productRSet;

			/* TODO output your page here. You may use following sample code. */
			out.println("<!doctype html>");
			out.println("<html lang=\"en\">");
			out.println("");
			out.println("<!--");
			out.println("WRITTEN BY: Greg Zubatov, Swan Toma, Genesis Garcia");
			out.println("EMAIL: gzubatov@uci.edu, sktoma@uci.edu, genesirg@uci.edu");
			out.println("-->");
			out.println("");
			out.println("<head>");
			out.println("<meta charset=\"utf-8\">");
			out.println("<meta name=\"author\" content=\"co-authored by Greg Zubatov, .., .., .., Genesis Garcia\">");
			out.println("<meta name=\"description\" content=\"ECommerce Website\">");
			out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
			out.println("");
			out.println("<link rel=\"stylesheet\" href=\"./css/global.css\">");
			out.println("<link rel=\"stylesheet\" href=\"./css/products.css\">");
			out.println("<script src=\"./js/products.js\"></script>");
			out.println("");
			out.println("");
			out.println("");
			out.println("<title>Ant-R-Us</title>");
			out.println("");
			out.println("");
			out.println("</head>");
			out.println("");
			out.println("<body>");

			out.println("<!-- Navbar -->");
			out.println("<div class=\"navbar\">");
			out.println("<div class=\"logo\"><img src=\"./imgs/ant_logo.png\" alt=\"Ants R Us Logo\"></div>");
			out.println("<div class=\"pages\">");
			out.println("<ul class=\"navigation\">");
			out.println("<li><a href=\"/project3/Index\">Home</a></li>");
			out.println("<li><a href=\"./pages/about.html\">About</a></li>");
			out.println("<li><a href=\"/project3/Checkout\">Cart(" + cartSize + ")</a></li>");
			out.println("</ul>");
			out.println("</div>");
			out.println("</div>");

			out.println("<div id=\"main\">");
			out.println("<div id=\"cart\">");
			out.println("<ul>");
			double total = 0;
			for (String pid : cart) {
				productRSet = statement.executeQuery("SELECT * FROM products WHERE pid = " + pid + ";");

				if (productRSet.next()) {
					double price = productRSet.getDouble("price");
					out.println("<li>" + productRSet.getString("name") + " - $" + price + "</li>");
					total += price;
				}
			}
			out.println("</ul>");

			out.println("</div>");

			out.println("<div id=\"form\">");
			out.println("<h3>Enter your details</h3>");
			out.println("<form action=\"http://localhost:8080/project3/Checkout\" method=\"post\">");

			out.println("<div class=\"name\">");
			out.println("<div>");
			out.println("<label for=\"fname\">First name</label>");
			out.println("<input type=\"text\" id=\"fname\" name=\"fname\">");
			out.println("</div>");

			out.println("<div>");
			out.println("<label for=\"lname\">Last name</label>");
			out.println("<input type=\"text\" id=\"lname\" name=\"lname\">");
			out.println("</div>");
			out.println("</div>");

			out.println("<div class=\"phone\">");
			out.println("<label for=\"phonenum\">Phone Number</label>");
			out.println("<input placeholder=\"123-456-7890\" type=\"tel\" id=\"phonenum\" name=\"phonenum\"");
			out.println("pattern=\"[0-9]{3}[-][0-9]{3}[-][0-9]{4}\" title=\"Format: 123-456-7891\">");
			out.println("</div>");

			out.println("<label for=\"addr\">Shipping Address</label>");
			out.println("<input type=\"text\" id=\"addr\" name=\"addr\">");

			out.println("<div class=\"addr\">");
			out.println("<div>");
			out.println("<label for=\"city\">City</label>");
			out.println("<input type=\"text\" id=\"city\" name=\"city\" />");
			out.println("</div>");
			out.println("<div>");
			out.println("<label for=\"state\">State</label>");
			out.println("<select id=\"state\" name=\"state\">");
			out.println("<option value=\"AL\">AL</option>");
			out.println("<option value=\"AK\">AK</option>");
			out.println("<option value=\"AZ\">AZ</option>");
			out.println("<option value=\"AR\">AR</option>");
			out.println("<option value=\"CA\">CA</option>");
			out.println("<option value=\"CO\">CO</option>");
			out.println("<option value=\"CT\">CT</option>");
			out.println("<option value=\"DE\">DE</option>");
			out.println("<option value=\"DC\">DC</option>");
			out.println("<option value=\"FL\">FL</option>");
			out.println("<option value=\"GA\">GA</option>");
			out.println("<option value=\"HI\">HI</option>");
			out.println("<option value=\"ID\">ID</option>");
			out.println("<option value=\"IL\">IL</option>");
			out.println("<option value=\"IN\">IN</option>");
			out.println("<option value=\"IA\">IA</option>");
			out.println("<option value=\"KS\">KS</option>");
			out.println("<option value=\"KY\">KY</option>");
			out.println("<option value=\"LA\">LA</option>");
			out.println("<option value=\"ME\">ME</option>");
			out.println("<option value=\"MD\">MD</option>");
			out.println("<option value=\"MA\">MA</option>");
			out.println("<option value=\"MI\">MI</option>");
			out.println("<option value=\"MN\">MN</option>");
			out.println("<option value=\"MS\">MS</option>");
			out.println("<option value=\"MO\">MO</option>");
			out.println("<option value=\"MT\">MT</option>");
			out.println("<option value=\"NE\">NE</option>");
			out.println("<option value=\"NV\">NV</option>");
			out.println("<option value=\"NH\">NH</option>");
			out.println("<option value=\"NJ\">NJ</option>");
			out.println("<option value=\"NM\">NM</option>");
			out.println("<option value=\"NY\">NY</option>");
			out.println("<option value=\"NC\">NC</option>");
			out.println("<option value=\"ND\">ND</option>");
			out.println("<option value=\"OH\">OH</option>");
			out.println("<option value=\"OK\">OK</option>");
			out.println("<option value=\"OR\">OR</option>");
			out.println("<option value=\"PA\">PA</option>");
			out.println("<option value=\"RI\">RI</option>");
			out.println("<option value=\"SC\">SC</option>");
			out.println("<option value=\"SD\">SD</option>");
			out.println("<option value=\"TN\">TN</option>");
			out.println("<option value=\"TX\">TX</option>");
			out.println("<option value=\"UT\">UT</option>");
			out.println("<option value=\"VT\">VT</option>");
			out.println("<option value=\"VA\">VA</option>");
			out.println("<option value=\"WA\">WA</option>");
			out.println("<option value=\"WV\">WV</option>");
			out.println("<option value=\"WI\">WI</option>");
			out.println("<option value=\"WY\">WY</option>");
			out.println("</select>");
			out.println("</div>");
			out.println("<div>");
			out.println("<label for=\"zipcode\">Zip Code</label>");
			out.println("<input type=\"text\" id=\"zipcode\" name=\"zipcode\" />");
			out.println("</div>");
			out.println("</div>");

			out.println("<label for=\"shipping\">Shipping Method</label>");

			out.println("<select id=\"shipping\" name=\"shipping\">");
			out.println("<option value=\"overnight\">Overnight</option>");
			out.println("<option value=\"expedited\">2-days Expedited</option>");
			out.println("<option value=\"ground\">6-days Ground</option>");
			out.println("</select>");

			out.println("<label for=\"ccn\">Credit Card Number</label>");
			out.println("<input type=\"text\" id=\"ccn\" name=\"ccn\">");

			out.println("<div class=\"expDate\">");
			out.println("<label for=\"expdate\">Expiration Date</label>");

			out.println("<select id=\"expmo\" name=\"expmo\">");
			out.println("<option value=\"1\">1</option>");
			out.println("<option value=\"2\">2</option>");
			out.println("<option value=\"3\">3</option>");
			out.println("<option value=\"4\">4</option>");
			out.println("<option value=\"5\">5</option>");
			out.println("<option value=\"6\">6</option>");
			out.println("<option value=\"7\">7</option>");
			out.println("<option value=\"8\">8</option>");
			out.println("<option value=\"9\">9</option>");
			out.println("<option value=\"10\">10</option>");
			out.println("<option value=\"11\">11</option>");
			out.println("<option value=\"12\">12</option>");
			out.println("</select>");

			out.println("<select id=\"expyr\" name=\"expyr\">");
			out.println("<option value=\"2020\">2020</option>");
			out.println("<option value=\"2021\">2021</option>");
			out.println("<option value=\"2022\">2022</option>");
			out.println("<option value=\"2023\">2023</option>");
			out.println("<option value=\"2024\">2024</option>");
			out.println("<option value=\"2025\">2025</option>");
			out.println("<option value=\"2026\">2026</option>");
			out.println("<option value=\"2027\">2027</option>");
			out.println("<option value=\"2028\">2028</option>");
			out.println("<option value=\"2029\">2029</option>");
			out.println("<option value=\"2030\">2030</option>");
			out.println("<option value=\"2031\">2031</option>");
			out.println("</select>");
			out.println("</div>");

			out.println("<label for=\"security\">Security Code</label>");
			out.println("<input type=\"text\" id=\"security\" name=\"security\" placeholder=\"CVV\" />");

			out.println("<label for=\"price\">Price</label>");
			out.println("<input type =\"text\" id=\"price\" name=\"price\" value=$" + total + " readonly/>");
			out.println("<label for=\"tax\">Tax</label>");
			out.println("<input type =\"text\" id=\"tax\" name=\"tax\" readonly/>");
			out.println("<label for = \"total\">Total</label>");
			out.println("<input type =\"text\" id=\"total\" name=\"total\" readonly/>");

			out.println("<input type=\"submit\" value=\"Purchase\" />");
			out.println("</form>");

			out.println("</div>");
			out.println("</div>");
			out.println("");
			out.println("</body>");
			out.println("");
			out.println("</html>");
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
		try {
			processRequest(request, response);

			/*
			 * out.println("<h1>IN GET</h1>");
			 * out.println("<form method=\"post\" action=\"/Checkout\">");
			 * out.println("<input type=\"submit\" value=\"submit\"");
			 * out.println("</form>");
			 */
		} catch (SQLException ex) {
			Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
		}
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
		try (PrintWriter out = response.getWriter()) {
			// response.setContentType("text/html;charset=UTF-8");

			HttpSession session = request.getSession(true);
			ArrayList<String> pids = (ArrayList<String>) session.getAttribute("cart");
			Properties properties = new Properties();
			properties.setProperty("useSSL", "false");
			properties.setProperty("allowPublicKeyRetrieval", "true");
			properties.setProperty("serverTimezone", "UTC");
			properties.setProperty("user", "swantoma");
			properties.setProperty("password", "tmp123!");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arts_and_crafts", properties);

			String firstName = request.getParameter("lname");
			String lastName = request.getParameter("fname");
			String phoneNumber = request.getParameter("phonenum");
			String shippingAddress = request.getParameter("addr");
			String zip = request.getParameter("zipcode");
			String shipping = request.getParameter("shipping");
			String ccn = request.getParameter("ccn");
			String expmo = request.getParameter("expmo");
			String expyr = request.getParameter("expyr");
			String security = request.getParameter("security");
			String total = request.getParameter("total");
			total = total.substring(1);

			String query = "INSERT INTO orders (first_name, "
					+ "last_name, phone_number, shipping_address, zip_code, shipping_method, credit_card, expiration_month, expiration_year, security_code, price_total)"
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, firstName);
			st.setString(2, lastName);
			st.setString(3, phoneNumber);
			st.setString(4, shippingAddress);
			st.setInt(5, Integer.valueOf(zip));
			st.setString(6, shipping);
			st.setLong(7, Long.valueOf(ccn));
			st.setInt(8, Integer.valueOf(expmo));
			st.setInt(9, Integer.valueOf(expyr));
			st.setInt(10, Integer.valueOf(security));
			st.setDouble(11, Double.valueOf(total));
			int numAffectedRows = st.executeUpdate();
			int oid;

			if (numAffectedRows == 0) {
				throw new SQLException("Inserting into products_in_orders failed");
			}

			try (ResultSet generatedKeys = st.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					oid = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Inserting into products_in_orders failed, no ID obtained.");
				}
			}

			// hash table to store qty
			Hashtable<String, Integer> productQty = new Hashtable<String, Integer>();
			for (String pid : pids) {
				// productQty.put(pid, productQty.getOrDefault(pid, 0) + 1);
				if (productQty.containsKey(pid)) {
					productQty.put(pid, productQty.get(pid) + 1);
				} else {
					productQty.put(pid, 1);
				}
			}
			PreparedStatement pio_statement = null;
			Set<String> keys = productQty.keySet();
			con.setAutoCommit(false);
			String products_in_orders_query = "INSERT INTO products_in_orders (oid, pid, quantity) "
					+ "VALUES(?, ?, ?)";

			pio_statement = con.prepareStatement(products_in_orders_query);
			for (String key : keys) {
				out.println("<h1>" + key + "," + productQty.get(key) + "</h1>");
				pio_statement.setInt(1, oid);
				pio_statement.setInt(2, Integer.valueOf(key));
				pio_statement.setInt(3, productQty.get(key));
				pio_statement.addBatch();
			}

			session.setAttribute("cart", null);
			st.close();
			if (pio_statement != null) {
				pio_statement.executeBatch();
				pio_statement.close();
			}
			con.commit();

			RequestDispatcher rd = request.getRequestDispatcher("/Confirmation?oid=" + oid);
			rd.forward(request, response);
		} catch (Exception e) {
			;
		}
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

}
