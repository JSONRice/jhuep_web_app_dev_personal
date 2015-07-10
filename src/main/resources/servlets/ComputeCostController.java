package resources.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import resources.dispatch.ComputeCostBean;
import resources.dispatch.ErrorBean;

/**
 * @author jsnrice
 */
public class ComputeCostController extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(ComputeCostController.class.getName());

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
        ComputeCostBean costBean = new ComputeCostBean();
        // Any errors that are found will be thrown as a NullPointerException 
        // with a defined error message (e.g. no courses selected):
        try {
            costBean.setName(request);
            costBean.setEmail(request);
            costBean.setCourses(request);
            costBean.setAccomodations(request);
            costBean.setCost(request);
            costBean.computeTotalCost();
        } // If any errors are found route to an error page:
        catch (NullPointerException npe) {
            String stackTrace = convertStackTrace(npe);
            LOGGER.log(Level.SEVERE, "Caught a NullPointerException in controller:\n{0}", stackTrace);
            ErrorBean error;
            String errorMsg = npe.getMessage();
            if (errorMsg == null || errorMsg.isEmpty()) {
                error = new ErrorBean("Unhandled form error detected. Please contact web master.");
            } else {
                error = new ErrorBean(errorMsg);
            }
            request.setAttribute("error", error);
            RequestDispatcher errorDispatcher
                    = getServletConfig().getServletContext().getRequestDispatcher("/error.jsp");
            errorDispatcher.forward(request, response);
        }
        // If there was an exception and a previous redirect, prevent the folowing redirect:
        if (!response.isCommitted()) {
            // Else if no errors store our results and dispatch (route) to the results page:
            request.setAttribute("results", costBean);
            RequestDispatcher resultsDispatcher
                    = getServletConfig().getServletContext().getRequestDispatcher("/results.jsp");
            resultsDispatcher.forward(request, response);
        }
    }

    /**
     * @description Given a Throwable (Exception) convert the stack trace to a String and return.
     * @param e
     * @return 
     */
    public static String convertStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
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
