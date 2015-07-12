package resources.dispatch;

import java.util.logging.Logger;

/**
 *
 * @author jsnrice
 */
public class FormValidationBean {    
    private final static Logger LOGGER = Logger.getLogger(FormValidationBean.class.getName()); 
    
    public void checkFields(String name, String email) throws NullPointerException {
        if (name == null || name.trim().equals("")) {
            LOGGER.severe("No Name Was Entered");
            throw new NullPointerException("No Name Was Entered");            
        }
        if (email == null || email.trim().equals("")) {
            LOGGER.severe("No Email Was Entered");
            throw new NullPointerException("No Email Was Entered");
        }        
    }
}
