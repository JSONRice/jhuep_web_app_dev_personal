/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jsnrice
 */
public class ComputeCost extends HttpServlet {

    /**
     * @description Write the default header with styling to the output stream.
     * This will be read back into the browser.
     * @param out
     */
    private void injectHeader(PrintWriter out) {
        String header = "<head>\n"
                + "        <title>JHU Annual Software Development Seminar - Compute Cost</title>\n"
                + "        <link href=\"css/standard.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
                + "        <link href=\"css/oform.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
                + "        <link href=\"css/hw.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "    </head>";
        out.println(header);
    }

    /**
     * @description Given a request object and output stream, process the body
     * and write (inject) back into the output stream which will be read back
     * into the browser.
     * @param request
     * @param out
     */
    private void injectBody(HttpServletRequest request, PrintWriter out) {
        String body = "<body>\n";

        // Haven't figured out how to inject JSP directly into servlets. For now just manually hard code banner:
        body += "<div id=\"heading\">\n"
                + "    <img src=\"images/jhu_logo_medium_nopadding.png\" alt=\"JHU Logo\" />\n"
                + "    <h3>JOHNS HOPKINS ANNUAL DEVELOPMENT SEMINAR</h3>\n"
                + "</div>\n"
                + "        <div id=\"pageContent\">	\n"
                + "            <hr/>\n"
                + "            <h4>" + request.getParameter("name") + "</h4>\n";

        String employmentstatus = request.getParameter("user_type");
        // Speakers are not charged:
        double cost = 0.00;
        if (employmentstatus == null || employmentstatus.equals("Speaker")) {
            cost = 0.00;
        } else if (employmentstatus.equals("JHU Employee")) {
            cost = 850.00;
        } else if (employmentstatus.equals("JHU Student")) {
            cost = 1000.00;
        } else if (employmentstatus.equals("Other")) {
            cost = 1350.00;
        }

        body += "            You are registered as a <b> " + request.getParameter("user_type") + "</b>\n"
                + "            <br/>\n"
                + "            <br/>\n"
                + "            Your e-mail confirmation will be sent to: <span class=\"email\">" + request.getParameter("email") + "</span>\n"
                + "            <br/>\n"
                + "            <br/>\n"
                + "            <table class=\"grid\">\n"
                + "                <tr>\n"
                + "                    <th>Your Courses</th>\n"
                + "                    <th>Cost</th>\n"
                + "                </tr>\n";

        int numCourses = 0;
        String[] selectedCourses = request.getParameterValues("courses");
        for (String course : selectedCourses) {
            numCourses++;
            body += "                <tr>\n"
                    + "                    <td class=\"grid-border\">" + course
                    + "</td><td class=\"grid-border cost\">" + String.format("%.2f", cost) + "</td>\n"
                    + "                </tr>\n";

        }

        body += "                <tr class=\"empty-row\"/>\n";
        String[] accomodations = request.getParameterValues("accomodations");
        double currentAccomCost = 0.00;
        double totalCurrentAccomCost = 0.00;
        if (accomodations != null) {
            for (String accomodation : accomodations) {
                if (accomodation.equals("Hotel Accomodation")) {
                    currentAccomCost = 185.00;
                } else if (accomodation.equals("Parking Permit")) {
                    currentAccomCost = 10.00;
                }

                body += "                <tr>\n"
                        + "                    <td class=\"accomodation\">" + accomodation + "</td>\n"
                        + "                    <td class=\"cost\">" + String.format("%.2f", currentAccomCost) + "</td>\n"
                        + "                </tr>\n";

                totalCurrentAccomCost += currentAccomCost;
                // reset:
                currentAccomCost = 0.00;
            }
        }

        body += "                <tr>\n"
                + "                    <td><hr style=\"color:black\"/><td>\n"
                + "                </tr>\n"
                + "                <tr>\n"
                + "                    <th>Total</th><td class=\"cost total-cost\" id=\"total\">"
                + String.format("%.2f", totalCurrentAccomCost + (cost * (double) numCourses)) + "</td>\n"
                + "                </tr>\n"
                + "            </table>\n"
                + "\n"
                + "\n"
                + "        </div>\n";
        body += "</body>\n";

        // write (inject) to output stream:
        out.println(body);
    }

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
        PrintWriter out = response.getWriter();
        try {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            this.injectHeader(out);
            this.injectBody(request, out);

            out.println("</html>");
        } finally {
            out.close();
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
