/*
 * This servlet is mapped to index.html through web.xml.
 * This servlet will respond with the home page content including data 
 * pulled from MySQL database.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
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
public class Index extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String log = "";

        try {
            String dbDriver = "com.mysql.cj.jdbc.Driver";
            Class.forName(dbDriver);
        } catch (Exception e) {
            log += "<h2>Driver connection error</h2>";
            log += "<h2>" + e.toString() + "</h2>";
        }

        try {
            Properties properties = new Properties();
            properties.setProperty("useSSL", "false");
            properties.setProperty("allowPublicKeyRetrieval", "true");
            properties.setProperty("serverTimezone", "UTC");
            properties.setProperty("user", "swantoma");
            properties.setProperty("password", "tmp123!");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arts_and_crafts", properties);
            Statement statement = con.createStatement();
            ResultSet productRSet = statement.executeQuery("SELECT * FROM products;");
            // Create second statement for category query below, need two connection statements here
            statement = con.createStatement();
            PrintWriter out = response.getWriter();

            Cookie[] cookies = request.getCookies();

            out.println(log);
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<!--");
            out.println("Name:	        Email:");
            out.println("Greg Zubatov    gzubatov@uci.edu");
            out.println("Genesis Garcia  genesirg@uci.edu");
            out.println("Swan Toma	sktoma@uci.edu");
            out.println("-->");
            out.println("");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            out.println("<meta charset=\"utf-8\">");
            out.println("<meta name=\"author\" content=\"co-authored by Greg Zubatov, Swan Toma, Genesis Garcia\">");
            out.println("<meta name=\"description\" content=\"ECommerce Website\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
            out.println("<link href=\"https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@1,500&amp;display=swap\"");
            out.println("rel=\"stylesheet\">");
            out.println("<link rel=\"stylesheet\" href=\"./css/global.css\">");
            out.println("<link rel=\"stylesheet\" href=\"./css/index.css\">");
            out.println("<title>Ants-R-Us</title>");
            out.println("");
            out.println("</head>");
            out.println("");
            out.println("<body>");
            out.println("<!-- Navbar -->");
            out.println("<div class=\"navbar\">");
            out.println("<div class=\"logo\">");
            out.println("<img src=\"imgs/ant_logo.png\" alt=\"Ants R Us Logo\"></div>");
            out.println("<div class=\"pages\">");
            out.println("<ul class=\"navigation\">");
            out.println("<li><a href=\"index.html\">Home</a></li>");
            out.println("<li><a href=\"./pages/about.html\">About</a></li>");
            out.println("</ul>");
            out.println("</div>");
            out.println("</div>");
            out.println("");
            out.println("<!-- Jumbotron -->");
            out.println("<div class=\"jumbotron\">");
            out.println("<div>");
            out.println("<h1>Arts & Crafts</h1>");
            out.println("</div>");
            out.println("</div>");
            out.println("");
            out.println("<!--Main Content-->");
            out.println("<div id=\"main\">");
            out.println("<!--Filters-->");
            out.println("<div class=\"filterblock\">");
            out.println("");
            out.println("<div class=\"filter\">");
            out.println("<div class=\"search-container\">");
            out.println("<label for=\"search\">Search:</label>");
            out.println("<input type=\"text\" id=\"search\" name=\"search\">");
            out.println("</div>");
            out.println("<h3>Filter by Categories</h3>");
            out.println("<div class=\"checkbox\">");
            out.println("<input type=\"checkbox\" id=\"floral\" name=\"filter\"><label for=\"floral\"><span>Floral</span></label>");
            out.println("</div>");
            out.println("<div class=\"checkbox\">");
            out.println("<input type=\"checkbox\" id=\"crafts-hobbies\" name=\"filter\"><label for=\"crafts-hobbies\"><span>Crafts");
            out.println("and Hobbies</span></label>");
            out.println("</div>");
            out.println("<div class=\"checkbox\">");
            out.println("<input type=\"checkbox\" id=\"home-office\" name=\"filter\"><label for=\"home-office\"><span>Home");
            out.println("Office</span></label>");
            out.println("</div>");
            out.println("<div class=\"checkbox\">");
            out.println("<input type=\"checkbox\" id=\"knitting-crochet\" name=\"filter\"><label");
            out.println("for=\"knitting-crochet\"><span>Knitting and Crochet</span></label>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("<!--Table-->");
            out.println("<div class=\"table\" id=\"table\">");

            while (productRSet.next()) {

                String category_query = "SELECT c.category FROM products as p, categories as c, product_has_category as pc WHERE p.pid = pc.pid AND c.cid = pc.cid AND p.pid =" + productRSet.getInt("pid");
                String product_categories = "";
                ResultSet categoryRSet = statement.executeQuery(category_query);

                while (categoryRSet.next()) {
                    product_categories += categoryRSet.getString("category") + " ";
                }

                out.println("<a href=http://localhost:8080/project3/Product?pid=" + productRSet.getInt("pid") + ">");
                out.println("<div id = " + productRSet.getInt("pid") + " class='" + product_categories + "'>");
                out.println("<img src='" + productRSet.getString("image") + "'/>");
                out.println("<div><p>" + productRSet.getString("name") + "</p><p>" + productRSet.getDouble("price") + "</p></div></div>");
                out.println("</a>");
            }

            out.println("</div>");
            out.println("</div>");
            out.println("");

            if (cookies != null) {
                RequestDispatcher rd = request.getRequestDispatcher("/History");
                rd.include(request, response);
            }

            out.println("<!-- scripts-->");
            out.println("<script src=\"./js/index.js\"></script>");
            out.println("</body>");
            out.println("");
            out.println("</html>");

        } catch (Exception e) {
            //log += "<h2>Database connection error</h2>";
            log += "<h2>" + e.toString() + "</h2>";
        }
    }

    // <editor-fold defaultstate=\"collapsed\" desc=\"HttpServlet methods. Click on
    // the
    // + sign on the left to edit the code.\">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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

}
