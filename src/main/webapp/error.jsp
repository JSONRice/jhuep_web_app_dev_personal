<%-- 
    Document    : error
    Description : display error message to browser 
    Created on  : Jun 27, 2015, 1:05:39 PM
    Author      : jsnrice
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="resources.dispatch.ErrorBean" %>
<jsp:useBean id="error" class="resources.dispatch.ErrorBean" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <h2 class="error-header">Form Error:</h2>
        <p>
            <%= error.getErrorMsg() %>
        </p>
        <form name="error" id="return" action="index.jsp" method="get">
            <input type="submit" id="submission" value="Refill Out Form">
        </form>
        <br/>
        <i>Note: hit browser back button to get back to form with saved fields if you don't want to refill entire form.</i>
    </body>
</html>
