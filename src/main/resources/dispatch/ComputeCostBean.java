package resources.dispatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
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

    private final static Logger LOGGER = Logger.getLogger(ComputeCostBean.class.getName());

    // MEMBERS:
    
    private double cost;
    private double totalCost;
    private double totalCurrentAccomCost;
    private String name;
    private String status;
    private String email;
    private ArrayList<String> selectedCourses;

    /* Accomodations flags: */
    private Boolean hotel;

    private Boolean parking;
    
    private final String[] courseNames = {
                                    "A1 - J2EE Design Patterns", 
                                    "A2 - Enterprise Service Bus", 
                                    "A3 - Service Oriented Architecture", 
                                    "A4 - Web Services",                                     
                                    "A5 - Web Services Security",
                                    "A6 - Secure Messaging"
                                   };
    private ArrayList<String> selectedCoursesState;

    public ArrayList<String> getSelectedCoursesState() {
        return selectedCoursesState;
    }
   
    private HashMap<String, Double> accomodationCostPairs;
      
    public ComputeCostBean() {
        cost = totalCost = totalCurrentAccomCost = 0.00;
        name = "";
        email = "";

        hotel = false;
        parking = false;

        // 6 courses:
        selectedCoursesState = new ArrayList<>(6);
        selectedCoursesState.add("");
        selectedCoursesState.add("");
        selectedCoursesState.add("");
        selectedCoursesState.add("");
        selectedCoursesState.add("");
        selectedCoursesState.add("");           
        selectedCourses = new ArrayList<>();
        accomodationCostPairs = new HashMap<>();
    }

    public void setHotel(String hotel) {
        if (hotel == null || hotel.trim().isEmpty()) {
            LOGGER.log(Level.INFO, "Hotel accomodation not selected.");        
            this.hotel = false;
        }
        else {
            LOGGER.log(Level.INFO, "Hotel accomodation selected.");                    
            this.hotel = true;
        }
    }

    public void setParking(String parking) {
        if (parking == null || parking.trim().isEmpty()) {
            LOGGER.log(Level.INFO, "Parking accomodation not selected.");                                
            this.parking = false;
        }
        else {
            LOGGER.log(Level.INFO, "Parking accomodation selected.");                                
            this.parking = true;
        }
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

    public synchronized void setSelectedCourses(String[] myCourses)
            throws NullPointerException {
        // reset (remove) any old fields in case any where selected:
        if (myCourses == null){
            LOGGER.log(Level.WARNING, "No courses where selected. Array is null.");                                  
            throw new NullPointerException("No Courses Where Selected");            
        }
        LOGGER.log(Level.INFO, "Courses selected are: {0}", Arrays.toString(myCourses));
        selectedCourses.addAll(Arrays.asList(myCourses));
        LOGGER.log(Level.INFO, "Added the following to the selected courses list: {0}", selectedCourses.toString());
        if (selectedCourses.isEmpty()) {
            LOGGER.log(Level.WARNING, "No courses where selected. Array is empty.");                                              
            throw new NullPointerException("No Courses Where Selected");
        }

        // Clear all the states first:
        for (int i = 0; i < selectedCoursesState.size(); i++) {
            selectedCoursesState.set(i, "");
        }
        
        // Now that we know what courses the user desires to attend set their states:
        int i = 0;
        LOGGER.log(Level.INFO, "The list of known courses to compare against is: {0}", Arrays.toString(courseNames));
        
        int index = -1;
        for (String str : selectedCourses) {
            
            if ((index = Arrays.asList(courseNames).indexOf(str)) >= 0) {
                LOGGER.log(Level.INFO, "Setting 'selected' state for index: {0} course name -> {1}", 
                        new Object[]{index, courseNames[index]});                                      
                selectedCoursesState.set(index, "selected");
            }
            index = -1;
            i++;
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
                LOGGER.log(Level.INFO, "Adding {0} for ${1}", new Object[]{hotel, accomCost});
                accomodationCostPairs.put(hotel, accomCost);
            } else if (accomodation.equals(parking)) {
                accomCost = 10.00;
                LOGGER.log(Level.INFO, "Adding {0} for ${1}", new Object[]{parking, accomCost});                
                accomodationCostPairs.put(parking, accomCost);                
            }
            totalCurrentAccomCost += accomCost;
            // reset:
            accomCost = 0.00;
        }
        LOGGER.log(Level.INFO, "Total current accomodation cost is ${0} ", totalCurrentAccomCost);
    }
    
    public synchronized void computeTotalCost() {
        totalCost = 0.00;
        LOGGER.log(Level.INFO, "computeTotalCost(): totalCurrentAccomCost is -> ${0}", totalCurrentAccomCost);
        totalCost = totalCurrentAccomCost + (cost * (double) selectedCourses.size());
    }
    
    public Boolean getHotel() {
        return hotel;
    }
    
    public Boolean getParking() {
        return parking;
    }
    
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

    public synchronized ArrayList<String> getSelectedCourses() {
        return selectedCourses;
    }

    public synchronized HashMap<String, Double> getAccomodationCostPairs() {
        return accomodationCostPairs;
    }
}
