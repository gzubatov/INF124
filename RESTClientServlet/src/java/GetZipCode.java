
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author swantoma
 */
public class GetZipCode extends HttpServlet {

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
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String log = "";
            String zipCode = request.getParameter("zipCode");

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
            ResultSet zipRSet;
            zipRSet = statement.executeQuery("SELECT z.city, z.state, t.combined_rate FROM zip_codes as z, tax_rates as t WHERE z.zip = " + zipCode + " AND z.zip = t.zip;");
            zipRSet.next();

            out.println("{");
            out.println("\"state\": \"" + zipRSet.getString("state") + "\",");
            out.println("\"city\": \"" + zipRSet.getString("city") + "\",");
            out.println("\"combined_rate\": " + zipRSet.getDouble("combined_rate"));
            out.println("}");
            con.close();
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
