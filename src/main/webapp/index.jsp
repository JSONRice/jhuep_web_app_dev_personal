<%-- 
    Document   : index
    Created on : Jun 13, 2015, 9:09:48 PM
    Author     : jsnrice
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <!--
        <div style="display:none;" id="modal" class="index-modal">
            <p>
                <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0; color: white">Hello World</span>
            </p>
        </div>
        -->
        <div id="pageContent" class="content">
            <form name="order" id="signup" action="ComputeCostController" method="post">
                <fieldset id="contactinfo">
                    <legend>Contact Information</legend>
                    <label for="name">Name</label> 
                    <input type="text" name="name" autofocus>
                    <br/>
                    <label for="email">E-Mail</label>
                    <input type="email" name="email"
                           title="Format must be: someuser@host.dom"> 
                </fieldset>
                <fieldset id="courseselections">
                    <legend>Select Your Courses</legend>
                    <select name="courses" id="courses" multiple>
                        <option value="A4 - Web Services" id="A4">A4 - Web Services</option>
                        <option value="A1 - J2EE Design Patterns" id="A1">A1 - J2EE Design Patterns</option>
                        <option value="A3 - Service Oriented Architecture" id="A3">A3 - Service Oriented Architecture</option>
                        <option value="A2 - Enterprise Service Bus" id="A2">A2 - Enterprise Service Bus</option>
                        <option value="A6 - Secure Messaging" id="A6">A6 - Secure Messaging</option>
                        <option value="A5 - Web Services Security" id="A5">A5 - Web Services Security</option>			
                    </select>
                    <br/>
                    <span id="select_instructions">Hold down the Ctrl (Windows-Linux) / Command (Mac) button to select multiple courses.</span>
                </fieldset>
                <fieldset id="additionaloptions">
                    <legend>Additional Fees and Charges</legend>
                    <input type="checkbox" name="accomodations" value="Hotel Accomodation">Hotel Accommodation (Conference Guest Special Fee - Parking Included)
                    <br/>
                    <input type="checkbox" name="accomodations" value="Parking Permit">Parking Permit
                    <br/>
                </fieldset>	
                <fieldset id="employmentstatus">
                    <legend>Employment Status</legend>
                    <ul>
                        <li>
                            <input type="radio" name="user_type" value="JHU Employee">JHU Employee
                        </li>
                        <li>
                            <input type="radio" name="user_type" value="JHU Student">JHU Student
                        </li>
                        <li>
                            <input type="radio" name="user_type" value="Speaker">Speaker
                        </li>
                        <li>
                            <input type="radio" name="user_type" value="Other">Other
                        </li>					
                    </ul>
                </fieldset>
                <input type="submit" id="submission" value="Compute Seminar Cost">
            </form>
        </div>
    </body>
</html>

