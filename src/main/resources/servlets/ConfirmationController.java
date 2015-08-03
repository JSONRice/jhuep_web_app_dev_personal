package resources.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import resources.dispatch.ComputeCostBean;
import resources.dispatch.EmailSenderBean;

/**
 * @author jsnrice
 * @description manages the routing between the results.jsp and confirmation.jsp
 * Once a user confirms their results form, then they must hit submit to book the conference.
 */
public class ConfirmationController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ConfirmationController.class.getName());
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
        LOGGER.log(Level.INFO, "Routing from results.jsp to confirmation.jsp");        

        session = request.getSession();
        computeCostBean = (ComputeCostBean) session.getAttribute("computeCostBean");
        
        String emailstatus;
        String emailmsg;
        
        try {                        
            EmailSenderBean sendemail = new EmailSenderBean();
            
            try {
                // Extract values to formulate a cohesive email message:
                emailmsg = "User Registered: " + computeCostBean.getName() + "\n";
                emailmsg += "Status: "         + computeCostBean.getStatus() + "\n";
                emailmsg += "Hotel: "          + computeCostBean.getHotel() + "\n";
                emailmsg += "Parking: "        + computeCostBean.getParking() + "\n";
                emailmsg += "Selected Courses: \n";
                emailmsg += "**********************\n";                
                ArrayList<String> courses = computeCostBean.getSelectedCourses();
                for (String course : courses) {
                    emailmsg += "\t" + course + "\n";
                }
                emailmsg += "**********************\n";                
                emailmsg += "Total Cost: " + computeCostBean.getCost() + "\n";
                                
                /*****
                 * Tell the email sender we want the email to go to our Johns Hopkins address with the
                 * known address. This is important because the grader will need to access our account
                 * with the given passwd to verify the logic.
                 ***/
                String sendto = computeCostBean.getEmail();
                EmailSenderBean.sendMail(sendto, 
                                    sendto, "JHU Conference Registration", 
                                    emailmsg, 
                                    false);
                emailstatus = "Email sent to " + sendto;
            }
            catch (MessagingException e) {
                LOGGER.log(Level.SEVERE, "\nSending email failed:\n");
                LOGGER.log(Level.SEVERE, e.getMessage());                
                emailstatus = "\nError sending email.\n" + e.getMessage();
            }
            
            // forward to email confirmation page to let the
            // user know email has been either sent or not sent
            request.setAttribute("emailstatus", emailstatus);
            
            RequestDispatcher confirmationDispatcher
                = getServletConfig().getServletContext().getRequestDispatcher("/confirmation.jsp");
            confirmationDispatcher.forward(request, response);            
        } 
        finally {
            LOGGER.log(Level.INFO, "\n\nConfirmationController process request complete.");
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
