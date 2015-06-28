package resources.dispatch;

/**
 *
 * @author jsnrice
 * @description If any errors are detected within the controller (service) and worker
 * beans, then those errors get set here. The following fields are mandatory:
 * 
 * name - valid first and last name
 * email - valid email
 * courses - one or more courses attending
 * userType - must be set and is based on employment status (Professor, Student, Speaker, or Other)
 * 
 */
public class ErrorBean {
   private String errorMsg;
   
   public ErrorBean(){
       errorMsg = "";
   }
   
   public ErrorBean(final String defaultMsg){
       errorMsg = defaultMsg;
   }
   
   
   private void setErrorMsg(String msg){
       if (msg == null || msg.trim().isEmpty()){
           return;
       }
       this.errorMsg = msg;
   }
   
   public final String getErrorMsg(){
       return errorMsg;
   }
}
