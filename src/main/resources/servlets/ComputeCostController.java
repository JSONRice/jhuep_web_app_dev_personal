package resources.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import resources.dispatch.ComputeCostBean;

/**
 * @author jsnrice
 */
public class ComputeCostController extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(ComputeCostController.class.getName());
    private ComputeCostBean computeCostBean;
    private HttpSession session;

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
        session = request.getSession();

        // User wishes to compute cost of form and see results.jsp
        if (request.getParameter("computeCost") != null) {
            computeCostBean = new ComputeCostBean();

            // Set all the attribute properties into the bean:
            LOGGER.log(Level.INFO, "Name set in controller.");
            computeCostBean.setName(request.getParameter("name"));
            LOGGER.log(Level.INFO, "Email set in controller.");
            computeCostBean.setEmail(request.getParameter("email"));
            computeCostBean.setSelectedCourses(request.getParameterValues("courses"));
            LOGGER.log(Level.INFO, "Courses set in controller.");
            computeCostBean.setStatus(request.getParameter("user_type"));
            LOGGER.log(Level.INFO, "Employment status set in controller.");
            computeCostBean.setCost(request.getParameter("user_type"));
            LOGGER.log(Level.INFO, "Cost set in controller.");
            computeCostBean.setHotel(request.getParameter("hotelaccomodation"));
            computeCostBean.setParking(request.getParameter("parkingaccomodation"));

            // Set the accomodations map if any where selected above:
            ArrayList<String> accomodations = new ArrayList<>();
            if (computeCostBean.getHotel() != null && computeCostBean.getHotel()) {
                accomodations.add("Hotel Accomodation");
            }
            if (computeCostBean.getParking() != null && computeCostBean.getParking()) {
                accomodations.add("Parking Permit");
            }

            computeCostBean.setAccomodations((String[]) accomodations.toArray(new String[accomodations.size()]));
            LOGGER.log(Level.INFO, "All accomodations accounted for.");

            // Finally compute the total cost:
            computeCostBean.computeTotalCost();
            LOGGER.log(Level.INFO, "Total cost computed in bean.");
        } // User wishes to remove a course and see updated results.jsp
        else if (request.getParameter("remove") != null) {
            String course = (String) request.getParameter("course");

            ArrayList<String> selectedCourses = computeCostBean.getSelectedCourses();
            if (selectedCourses.contains(course)) {
                selectedCourses.remove(course);
                LOGGER.log(Level.INFO, "Removed the following course: {0}", course);

                // update the courses list in the bean:
                String[] courses = toArray(selectedCourses);
                LOGGER.log(Level.INFO, "Course listing before remove: {0}", selectedCourses.toString());
                computeCostBean.setSelectedCourses(courses);
                LOGGER.log(Level.INFO, "Course listing after remove: {0}", computeCostBean.getSelectedCourses().toString());
                
                // update the total cost:
                computeCostBean.computeTotalCost();
            }
            // If the user removed their last course then automatically route to index.jsp:
            if (computeCostBean.getSelectedCourses().size() <= 0) {
                if (!response.isCommitted()) {
                    // Else if no errors store our updated bean and dispatch (route) to the results page:
                    session.setAttribute("computeCostBean", computeCostBean);
                    RequestDispatcher resultsDispatcher
                            = getServletConfig().getServletContext().getRequestDispatcher("/index.jsp");
                    resultsDispatcher.forward(request, response);
                }
                return; // -- preempt
            }
        }
        // If there was an exception and a previous redirect, prevent the folowing redirect:
        if (!response.isCommitted()) {
            // Else if no errors store our updated bean and dispatch (route) to the results page:
            session.setAttribute("computeCostBean", computeCostBean);
            RequestDispatcher resultsDispatcher
                    = getServletConfig().getServletContext().getRequestDispatcher("/results.jsp");
            resultsDispatcher.forward(request, response);
        }
    }

    public String[] toArray(ArrayList<String> arrlist) {
        if (arrlist == null) {
            return null;
        }
        int size = arrlist.size();
        String array[] = new String[size];
        for (int j = 0; j < size; j++) {
            array[j] = arrlist.get(j);
        }
        return array;
    }

    /**
     * @description Given a Throwable (Exception) convert the stack trace to a
     * String and return.
     * @param t
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
     *
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
