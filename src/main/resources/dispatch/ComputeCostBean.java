package resources.dispatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jsnrice
 * @description This class is meant to support the ComputeCostController and
 * each setter method that alters data should be synchronized in order to make 
 * this class thread safe in case more than one thread should ever attempt to 
 * call a method at the same time. This class has setters for performing server-side
 * validation and getters to allow data to be retrieved within the bean from the
 * JSP view. This follows the MVC architecture. 
 */
public class ComputeCostBean {

    // MEMBERS:
    
    private double cost;
    private double totalCost;
    private double totalCurrentAccomCost;
    private String name;
    private String email;
    private ArrayList<String> courses;
    private HashMap<String, Double> accomodationCostPairs;
    
    private final static Logger LOGGER = Logger.getLogger(ComputeCostBean.class.getName()); 
    
    // ACCESSORS:

    public ComputeCostBean() {
        cost = totalCost = totalCurrentAccomCost = 0.00;
        name = "";
        email = "";
        courses = new ArrayList<String>();
        accomodationCostPairs = new HashMap<String, Double>();
    }
    
    public synchronized void setName(HttpServletRequest request)
        throws NullPointerException {
        String name = request.getParameter("name");
        if (name == null || name.equals("")) {
            LOGGER.severe("No Name Was Entered");
            throw new NullPointerException("No Name Was Entered");
        }
        else {
            LOGGER.severe("Name set to: " + this.name);            
            this.name = name;
        }
    }
    
    public synchronized void setEmail(HttpServletRequest request)
        throws NullPointerException {
        String email = request.getParameter("email");
        if (email == null || email.equals("")) {
            LOGGER.severe("No Email Was Entered");
            throw new NullPointerException("No Email Was Entered");
        }
        else {
            LOGGER.severe("Email set to: " + this.email);            
            this.email = email;
        }
    }


    public synchronized void setCost(HttpServletRequest request)
            throws NullPointerException {
        String employmentStatus = request.getParameter("user_type");
        if (employmentStatus == null || employmentStatus.equals("")) {
            throw new NullPointerException("No Employment Status Was Selected");
        }
        // Speakers are not charged:
        if (employmentStatus.equals("Speaker")) {
            this.cost = 0.00;
        } else if (employmentStatus.equals("JHU Employee")) {
            this.cost = 850.00;
        } else if (employmentStatus.equals("JHU Student")) {
            this.cost = 1000.00;
        } else if (employmentStatus.equals("Other")) {
            this.cost = 1350.00;
        }
    }

    public synchronized void setCourses(HttpServletRequest request)
            throws NullPointerException {
        // reset (remove) any old fields in case any where selected:
        courses.clear();
        String[] myCourses = request.getParameterValues("courses");
        if (myCourses == null){
            throw new NullPointerException("No Courses Where Selected");            
        }
        courses.addAll(Arrays.asList(myCourses));
        if (courses.isEmpty()) {
            throw new NullPointerException("No Courses Where Selected");
        }
    }

    public synchronized void setAccomodations(HttpServletRequest request) {
        // reset (remove) any old fields in case any where selected:
        accomodationCostPairs.clear();
        totalCurrentAccomCost = 0.00;
        String[] accomodations = request.getParameterValues("accomodations");
        if (accomodations == null || accomodations.length <= 0) {
            return;
        }
        
        Double accomCost = 0.00;
        String hotel = "Hotel Accomodation";
        String parking = "Parking Permit";
        for (String accomodation : accomodations) {
            if (accomodation.equals(hotel)) {
                accomCost = 185.00;               
                accomodationCostPairs.put(hotel, accomCost);
            } else if (accomodation.equals(parking)) {
                accomCost = 10.00;
                accomodationCostPairs.put(parking, accomCost);                
            }
            totalCurrentAccomCost += accomCost;
            // reset:
            accomCost = 0.00;
        }
    }
    
    public synchronized void computeTotalCost() {
        totalCost = 0.00;
        totalCost = totalCurrentAccomCost + (cost * (double) courses.size());
    }
    
    // MUTATORS:
    
    public synchronized double getCost() {
        return cost;
    }

    public synchronized double getTotalCost() {
        return totalCost;
    }

    public synchronized double getTotalCurrentAccomCost() {
        return totalCurrentAccomCost;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized String getEmail() {
        return email;
    }

    public synchronized ArrayList<String> getCourses() {
        return courses;
    }

    public synchronized HashMap<String, Double> getAccomodationCostPairs() {
        return accomodationCostPairs;
    }
}
