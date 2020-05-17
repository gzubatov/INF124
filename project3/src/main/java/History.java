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
public class History extends HttpServlet {

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
            ResultSet productRSet;

            Cookie[] cookies = request.getCookies();
            out.println("<div id=\"history\">");
            out.println("<h1>History</h1>");
            out.println("<div class=\"history\">");

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    productRSet = statement.executeQuery("SELECT * FROM products WHERE pid = " + cookie.getValue() + ";");
                    productRSet.next();
                    out.println("<a href=http://localhost:8080/project3/Product?pid=" + productRSet.getInt("pid") + ">");
                    out.println("<div class=\"product_history\" id = " + productRSet.getInt("pid") + ">");
                    out.println("<img src='" + productRSet.getString("image") + "'/>");
                    out.println("<div><p>" + productRSet.getString("name") + "</p><p>" + productRSet.getDouble("price") + "</p></div></div>");
                    out.println("</a>");
                }
            }
            out.println("</div>");
            out.println("</div>");

        } catch (Exception e) {
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
