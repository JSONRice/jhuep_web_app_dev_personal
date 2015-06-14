<%-- 
    Document   : compute_cost
    Created on : Jun 11, 2015, 11:10:20 PM
    Author     : jsnrice
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
            <h4><%= request.getParameter("name") %></h4>
            <%
                String employmentstatus = request.getParameter("user_type");
                // Speakers are not charged:
                double cost = 0.00;
                if (employmentstatus == null || employmentstatus.equals("Speaker")){
                    cost = 0.00;
                }
                else if (employmentstatus.equals("JHU Employee")){
                    cost = 850.00;
                }
                else if (employmentstatus.equals("JHU Student")){
                    cost = 1000.00;
                }
                else if (employmentstatus.equals("Other")){
                    cost = 1350.00;
                }
            %>            
            You are registered as a <b><%= request.getParameter("user_type") %></b>
            <br/>
            <br/>
            Your e-mail confirmation will be sent to: <span class="email"><%= request.getParameter("email") %></span>
            <br/>
            <br/>
            <table class="grid">
                <tr>
                    <th>Your Courses</th>
                    <th>Cost</th>
                </tr>
                <%
                    int numCourses = 0;
                    String[] selectedCourses = request.getParameterValues("courses");
                    for (String course: selectedCourses){
                %>
                <tr>
                    <td class="grid-border"><%= course %></td><td class="grid-border cost"><%= String.format( "%.2f", cost ) %></td>
                </tr>
                <%
                        numCourses++;
                    }
                %>
                <!-- Insert an empty row to provide spacing between courses and accomodations: -->
                <tr class="empty-row"/>
                <%
                    String[] accomodations = request.getParameterValues("accomodations");
                    double currentAccomCost = 0.00;
                    double totalCurrentAccomCost = 0.00;
                    if (accomodations != null) {
                        for(String accomodation : accomodations) {
                            if (accomodation.equals("Hotel Accomodation")){
                                currentAccomCost = 185.00;
                            }
                            else if (accomodation.equals("Parking Permit")){
                                currentAccomCost = 10.00;
                            }
                %>
                <tr>
                    <td class="accomodation"><%= accomodation%></td>
                    <td class="cost"><%= String.format( "%.2f", currentAccomCost ) %></td>
                </tr>
                <%
                            totalCurrentAccomCost += currentAccomCost;
                            // reset:
                            currentAccomCost = 0.00;
                        }
                    }
                %>
                <tr>
                    <td><hr style="color:black"/><td>
                </tr>
                <tr>
                    <th>Total</th><td class="cost total-cost" id="total"><%= String.format( "%.2f", totalCurrentAccomCost + (cost * (double) numCourses)) %></td>
                </tr>
            </table>


        </div>
    </body>
</html>
