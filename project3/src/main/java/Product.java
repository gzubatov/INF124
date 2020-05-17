/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author swantoma
 */
public class Product extends HttpServlet {

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
        
        try {
            PrintWriter out = response.getWriter();
            String log = "";

            try {
                String dbDriver = "com.mysql.cj.jdbc.Driver";
                Class.forName(dbDriver);
            } catch (Exception e) {
                log += "<h2>Driver connection error</h2>";
                log += "<h2>" + e.toString() + "</h2>";
            }

            Properties properties = new Properties();
            properties.setProperty("useSSL", "false");
            properties.setProperty("allowPublicKeyRetrieval", "true");
            properties.setProperty("serverTimezone", "UTC");
            properties.setProperty("user", "swantoma");
            properties.setProperty("password", "tmp123!");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arts_and_crafts", properties);
            Statement statement = con.createStatement();
            ResultSet productRSet = statement.executeQuery("SELECT * FROM products WHERE pid = " + request.getParameter("pid") + ";");

            productRSet.next();
            int pid = productRSet.getInt("pid");
            double price = productRSet.getDouble("price");
            String name = productRSet.getString("name");
            String description = productRSet.getString("description");
            String details = productRSet.getString("details");
            String image = productRSet.getString("image");
            String[] detailsSplit = details.split(",");
            
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());          
            Cookie cookie = new Cookie( timeStamp, Integer.toString(pid));
            cookie.setMaxAge(60*60*24); // 1 day
            response.addCookie(cookie);
            response.setContentType("text/html;charset=UTF-8");   

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
            out.println("<li><a href=\"./index.html\">Home</a></li>");
            out.println("<li><a href=\"./pages/about.html\">About</a></li>");
            out.println("</ul>");
            out.println("</div>");
            out.println("</div>");
            out.println("");
            out.println("");
            out.println("");
            out.println("<div id=\"main\">");
            out.println("<div id=\"info\">");
            out.println("<img id=\"product_image\" src=\"" + image + "\" alt=\"Product image\">");
            out.println("<div>");
            out.println("<h3 id=\"name\">" + name + " - $" + price + " </h3>");
            out.println("<form method = \"post\" action = \"/Cart\">");
            out.println("<input type = \"submit\" name =\"cart\" value=\"Add To Cart\">");
            out.print("</form>");
            out.println("<h5 id=\"pid\">pid: " + pid + "</h5>");
            out.println("<p id=\"description\">" + description + "</p>");
            out.println("<p id=\"details\">");

            out.println("<ul>");
            for (String s : detailsSplit) {
                out.println("<li>" + s + "</li>");
            }
            out.println("</ul></p>");

            out.println("</div>");
            out.println("");
            out.println("</div>");
            out.println("");
            out.println("</div>");
            out.println("");            
            out.println("</body>");
            out.println("");
            
            out.println("</html>");
        } catch (Exception e) {
            ;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
