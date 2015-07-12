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
    private String status;
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
    
    public synchronized void setName(String n) {
        this.name = n;
    }
    
    public synchronized void setEmail(String e) {
        this.email = e;
    }
    
    public synchronized void setStatus(String employmentStatus) {
        this.status = employmentStatus;
    }

    public synchronized void setCost(String employmentStatus) {
        // Speakers are not charged:
        if (employmentStatus == null) {
            return;
        }
        
        if (employmentStatus.equals("Speaker")) {
            this.cost = 0.00;
        } 
        else if (employmentStatus.equals("JHU Employee")) {
            this.cost = 850.00;
        } 
        else if (employmentStatus.equals("JHU Student")) {
            this.cost = 1000.00;
        } 
        else if (employmentStatus.equals("Other")) {
            this.cost = 1350.00;
        }
    }

    public synchronized void setCourses(String[] myCourses)
            throws NullPointerException {
        // reset (remove) any old fields in case any where selected:
        courses.clear();
        if (myCourses == null){
            throw new NullPointerException("No Courses Where Selected");            
        }
        courses.addAll(Arrays.asList(myCourses));
        if (courses.isEmpty()) {
            throw new NullPointerException("No Courses Where Selected");
        }
    }

    public synchronized void setAccomodations(String[] accomodations) {
        // reset (remove) any old fields in case any where selected:
        accomodationCostPairs.clear();
        totalCurrentAccomCost = 0.00;
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
    
    public synchronized String getStatus() {
        return status;
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
