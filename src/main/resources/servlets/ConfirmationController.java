package resources.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import resources.dispatch.EmailSenderBean;

/**
 * @author jsnrice
 * @description manages the routing between the results.jsp and confirmation.jsp
 * Once a user confirms their results form, then they must hit submit to book the conference.
 */
public class ConfirmationController extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(ConfirmationController.class.getName());
    private static final String ADDRESS = "jhuep605782@gmail.com";

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

        PrintWriter out = response.getWriter();

        
        
        String emailstatus = "Email Status Not Available. Please submit feedback later.";
        
        try {

            //get all the parameters from request object
            String name  =(String)request.getParameter("name");
                        
            EmailSenderBean sendemail = new EmailSenderBean();
            
            try
            {
                EmailSenderBean.sendMail(ADDRESS, 
                                    ADDRESS, name, 
                                    "Here is your confirmation email. Enjoy it.", 
                                    false);
                emailstatus = "Email Sent to user.";
            }
            catch (MessagingException e)
            {
                LOGGER.log(Level.SEVERE, "\nSending email failed:\n");
                LOGGER.log(Level.SEVERE, e.getMessage());                
                emailstatus = "\nERROR in Sending Email.\n" + e.getMessage();
            }
            
            //forward to email confirmation page to let the
            //user know email has been either sent or not sent
            request.setAttribute("emailstatus", emailstatus);
            
            RequestDispatcher confirmationDispatcher
                = getServletConfig().getServletContext().getRequestDispatcher("/confirmation.jsp");
            confirmationDispatcher.forward(request, response);            
        } 
        finally {
            LOGGER.log(Level.INFO, "\n\nClosing output stream in ConfirmationController");
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
