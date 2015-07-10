<%-- 
    Document    : results
    Description : results from the form
    Created on  : Jun 11, 2015, 11:10:20 PM
    Author      : jsnrice
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="resources.dispatch.ComputeCostBean" %>
<jsp:useBean id="results" class="resources.dispatch.ComputeCostBean" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <title>JHU Annual Software Development Seminar - Compute Cost</title>
        <link href="css/standard.css" rel="stylesheet" type="text/css" />
        <link href="css/oform.css" rel="stylesheet" type="text/css" />
        <link href="css/hw.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%@ include file="banner.jsp" %>
        <div id="pageContent">	
            <hr/>
            <h4><%= results.getName()%></h4>
            <%
                double cost = results.getCost();
            %>            
            You are registered as a <b><%= results.getName()%></b>
            <br/>
            <br/>
            Your e-mail confirmation will be sent to: <span class="email"><%= results.getEmail()%></span>
            <br/>
            <br/>
            <form name="results" id="confirmation" action="ConfirmationController" method="post">
                <table class="grid">
                    <tr>
                        <th>Your Courses</th>
                        <th>Cost</th>
                    </tr>
                    <%
                        ArrayList<String> selectedCourses = results.getCourses();
                        int numCourses = selectedCourses.size();
                        for (String course : selectedCourses) {
                    %>
                    <tr>
                        <td class="grid-border"><%= course%></td><td class="grid-border cost"><%= String.format("%.2f", cost)%></td><td class="grid-border remove"><input type="button" id="remove" value="Remove"></td>
                    </tr>
                    <%
                        }
                    %>
                    <!-- Insert an empty row to provide spacing between courses and accomodations: -->
                    <tr class="empty-row"/>
                    <%
                        HashMap<String, Double> accomodationCostPairs = results.getAccomodationCostPairs();
                        double totalCurrentAccomCost = 0.00;
                        if (accomodationCostPairs != null) {
                            Iterator itr = accomodationCostPairs.entrySet().iterator();
                            while (itr.hasNext()) {
                                Map.Entry pair = (Map.Entry) itr.next();

                                String accomodation = (String) pair.getKey();
                                Double currentAccomCost = (Double) pair.getValue();

                                itr.remove(); // avoids a ConcurrentModificationException

                    %>
                    <tr>
                        <td class="accomodation"><%= accomodation%></td>
                        <td class="cost"><%= String.format("%.2f", currentAccomCost)%></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                    <tr>
                        <td><hr style="color:black"/><td>
                    </tr>
                    <tr>
                        <th>Total</th><td class="cost total-cost" id="total"><%= String.format("%.2f", results.getTotalCost())%></td>
                    </tr>
                    <tr>
                        <td class="action-buttons">
                            <input type="button" id="edit" onclick="window.location = 'index.jsp'" value="Edit Information">
                            <!-- 
                                 As the homework describes: Clicking on "Add More Courses" should take the user back to the course selection page. 
                                 This does not mention any state should be preserved, so the page will be refreshed (new session). 
                            -->
                            <input type="button" id="add" onclick="window.location = 'index.jsp'" value="Add More Courses">                        
                            <input type="submit" id="confirm" value="Confirm Registration">
                        </td>                    
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>
