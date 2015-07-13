<%-- 
    Document   : index
    Created on : Jun 13, 2015, 9:09:48 PM
    Author     : jsnrice
--%>
<%@page import="java.util.Arrays"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="computeCostBean" class="resources.dispatch.ComputeCostBean" scope="session"/>
<jsp:setProperty name="computeCostBean" property="*"/>
<%
    Object[] tmp = computeCostBean.getSelectedCoursesState().toArray();
    String[] coursesStates = Arrays.copyOf(tmp, tmp.length, String[].class); 
    String a1 = "";
    String a2 = "";
    String a3 = "";
    String a4 = "";
    String a5 = "";
    String a6 = "";
    
    // We know that the coursesStates will be 6. One state for each course if initialized
    if (coursesStates.length >= 6) {
        a1 = coursesStates[0];
        a2 = coursesStates[1];
        a3 = coursesStates[2];
        a4 = coursesStates[3];
        a5 = coursesStates[4];
        a6 = coursesStates[5];
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>JHU Annual Software Development Seminar</title>
        <link href="css/standard.css" rel="stylesheet" type="text/css" />
        <link href="css/oform.css" rel="stylesheet" type="text/css" />
        <link href="css/hw.css" rel="stylesheet" type="text/css" />
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/indexErrorHandler.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
    </head>
    <body>
        <%@ include file="banner.jsp" %>
        <div id="pageContent" class="content">
            <form name="order" id="signup" action="ComputeCostController" method="post">
                <fieldset id="contactinfo">
                    <legend>Contact Information</legend>
                    <label for="name">Name</label> 
                    <input type="text" name="name" 
                           value="${computeCostBean.name}" autofocus>
                    <br/>
                    <label for="email">E-Mail</label>
                    <input type="email" name="email" 
                           value="${computeCostBean.email}"
                           title="Format must be: someuser@host.dom"> 
                </fieldset>
                <fieldset id="courseselections">
                    <legend>Select Your Courses</legend>
                    <select name="courses" id="courses" multiple>
                        <option value="A1 - J2EE Design Patterns" id="A1" <%= a1 %>>A1 - J2EE Design Patterns</option>
                        <option value="A2 - Enterprise Service Bus" id="A2" <%= a2 %>>A2 - Enterprise Service Bus</option>
                        <option value="A3 - Service Oriented Architecture" id="A3" <%= a3 %>>A3 - Service Oriented Architecture</option> 
                        <option value="A4 - Web Services" id="A4" <%= a4 %>>A4 - Web Services</option>
                        <option value="A5 - Web Services Security" id="A5" <%= a5 %>>A5 - Web Services Security</option>			                        
                        <option value="A6 - Secure Messaging" id="A6" <%= a6 %>>A6 - Secure Messaging</option>
                    </select>
                    <br/>
                    <span id="select_instructions">Hold down the Ctrl (Windows-Linux) / Command (Mac) button to select multiple courses.</span>
                </fieldset>

                <fieldset id="additionaloptions">
                    <legend>Additional Fees and Charges</legend>
                    <input type="checkbox" 
                           name="hotelaccomodation" value="Hotel Accomodation"
                           ${computeCostBean.hotel == true ? 'checked' : ''}>Hotel Accommodation (Conference Guest Special Fee - Parking Included)
                    <br/>
                    <input type="checkbox" 
                           name="parkingaccomodation" value="Parking Permit"
                           ${computeCostBean.parking == true ? 'checked' : ''}>Parking Permit
                    <br/>
                </fieldset>	

                <fieldset id="employmentstatus">
                    <legend>Employment Status</legend>
                    <ul>
                        <li>
                            <input type="radio" name="user_type" value="JHU Employee" 
                                   ${computeCostBean.status == "JHU Employee" ? 'checked' : ''}>JHU Employee
                        </li>
                        <li>
                            <input type="radio" name="user_type" value="JHU Student" 
                                   ${computeCostBean.status == "JHU Student" ? 'checked' : ''}>JHU Student
                        </li>
                        <li>
                            <input type="radio" name="user_type" value="Speaker" 
                                   ${computeCostBean.status == "Speaker" ? 'checked' : ''}>Speaker
                        </li>
                        <li>
                            <input type="radio" name="user_type" value="Other" 
                                   ${computeCostBean.status == "Other" ? 'checked' : ''}>Other
                        </li>					
                    </ul>
                </fieldset>
                <input type="submit" id="submission" value="Compute Seminar Cost">
            </form>
        </div>
    </body>
</html>

