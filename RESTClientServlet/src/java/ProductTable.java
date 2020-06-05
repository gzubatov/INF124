
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.client.ClientConfig;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.List;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author swantoma
 */
public class ProductTable extends HttpServlet {

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

		try {
			ClientConfig config = new ClientConfig();

			Client client = ClientBuilder.newClient(config);

			WebTarget target = client.target(getBaseURI());

			String jsonResponse = target.path("v1").path("api").path("products").request(). // send a request
					accept(MediaType.APPLICATION_JSON). // specify the media type of the response
					get(String.class); // use the get method and return the response as a string

			ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library

			List<Product> productList = objectMapper.readValue(jsonResponse, new TypeReference<List<Product>>() {
			});

			PrintWriter out = response.getWriter();
			// out.println(jsonResponse);

			for (Product product : productList) {
				String catsResponse = target.path("v1").path("api").path("products").path("categories")
						.path(Integer.toString(product.getPid())).request().accept(MediaType.APPLICATION_JSON)
						.get(String.class);

				ObjectMapper catObjectMapper = new ObjectMapper(); // This object is from the jackson library

				List<Category> categories = catObjectMapper.readValue(catsResponse,
						new TypeReference<List<Category>>() {
						});

				String categoriesStr = "";
				for (Category c : categories) {
					categoriesStr += c.getCategory() + " ";
				}

				out.println("<a href=http://localhost:8080/RESTClientServlet/ProductPage.jsp?pid=" + product.getPid()
						+ ">");
				out.println("<div id = " + product.getPid() + " class='" + categoriesStr + "'>");
				out.println("<img src='" + product.getImage() + "'/>");
				out.println("<div><p>" + product.getName() + "</p><p>" + product.getPrice() + "</p></div></div>");
				out.println("</a>");
			}

		} catch (Exception e) {
		}
	}

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
		// Swan: mine was already running at this location, no need to change.
	}
}
