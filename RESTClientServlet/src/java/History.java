
import org.glassfish.jersey.client.ClientConfig;
import org.codehaus.jackson.map.ObjectMapper;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Greg Zubatov gzubatov@uci.edu
 * @author Genesis Garcia genesirg@uci.edu
 * @author Swan Toma sktoma@uci.edu
 */
public class History extends HttpServlet {

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
		String log = "";
		Statement statement = null;
		try {
			PrintWriter out = response.getWriter();

			ClientConfig config = new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			WebTarget target = client.target(getBaseURI());

			Cookie[] cookies = request.getCookies();
			out.println("<div id=\"history\">");
			out.println("<h1>History</h1>");
			out.println("<div class=\"history\">");

			List<Cookie> list = Arrays.asList(cookies);
			Collections.reverse(list);
			Set<String> pids = new HashSet<String>();
			String jsonResponse;
			ObjectMapper objectMapper;
			Product product;

			int count = 0;
			if (cookies != null) {
				for (Cookie cookie : list) {
					if (!pids.contains(cookie.getValue())) {
						jsonResponse = target.path("v1").path("api").path("products").path(cookie.getValue()).request()
								.accept(MediaType.APPLICATION_JSON).get(String.class);

						objectMapper = new ObjectMapper(); // This object is from the jackson library

						product = objectMapper.readValue(jsonResponse, Product.class);

						++count;
						pids.add(cookie.getValue());
						out.println("<a href=http://localhost:8080/RESTClientServlet/ProductPage.jsp?pid="
								+ product.getPid() + ">");
						out.println("<div class=\"product_history\" id = " + product.getPid() + ">");
						out.println("<img src='" + product.getImage() + "'/>");
						out.println(
								"<div><p>" + product.getName() + "</p><p>" + product.getPrice() + "</p></div></div>");
						out.println("</a>");
					}
					if (count == 5) {
						break;
					}
				}
			}
			out.println("</div>");
			out.println("</div>");
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
