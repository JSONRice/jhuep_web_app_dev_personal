package resources.dispatch;

/**
 *
 * @author jsnrice
 * @description This class is meant to support the SessionController and
 * each setter method that alters data should be synchronized in order to make 
 * this class thread safe in case more than one thread should ever attempt to 
 * call a method at the same time. This class has setters for performing server-side
 * validation and getters to allow data to be retrieved within the bean from the
 * JSP view. This follows the Service-to-Worker design pattern. 
 */
public class SessionBean {
    
}
