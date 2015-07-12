<%-- 
    Document   : this JSP mimics standard logout behavior and is applied within the confirmation.jsp When a user clicks on 'Add More Courses'
                 then this page is navigated to and the following occurs:
                 1. The session is cleared
                 2. The navigation is redirected back to the home page (index.jsp)

    Created on : Jul 12, 2015, 10:32:44 AM
    Author     : jsnrice
--%>
<%
    // Clear the session:
    session.invalidate();
    // Navigate back to home page:
    response.sendRedirect("index.jsp");
    return;
%>