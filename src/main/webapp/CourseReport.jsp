<%@ page import="soen387.User" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: mauran
  Date: 2022-11-20
  Time: 12:28 a.m.
  To change this template use File | Settings | File Templates.
--%>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Course Report</title>
  <link rel="stylesheet" href="css/AdminPageStyle.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script type="text/javascript" src="js/AdminOptions.js"></script>
  <script type="text/javascript" src="js/Validation.js"></script>
</head>
<%@ include file="navbar.jsp" %>
<!--Navigation bar with bootstrap header -->
<%
  System.out.println("User ID is: ");
  System.out.println(session.getAttribute("id"));
  if (session.getAttribute("id") == null || session.getAttribute("isAdmin").equals("false"))
  {
    session.invalidate();
    response.sendRedirect("MainPage.jsp");
  }
%>
<body>

<div class='container text-center'>
    <%
        List<User> studentsEnrolledInCourse = (List<User>) request.getAttribute("data");
      String selectedCourseName = (String)  request.getAttribute("selectedCourseName");
      String redirectToAdminPage = "AdminPage.jsp";

    for (User a: studentsEnrolledInCourse
         ) {
        System.out.println(a);
    }
    %>

<%=
        //Display of the results (Table Header)
         "<div class='row'>" +
         "<div class='col'>" +
         "<h1>" + (selectedCourseName) + " Course Report</h1>" +
         "</div>" +
         "</div>" +

         "<div class='row'>" +
         "<div class='col'>" +
         "<table  class='table table-dark table-striped table-bordered border-light table-hover'>" +
            "<tr class='table-primary'>" +
                                "<th>Student ID</th>" +
                                "<th>First Name</th>" +
                                "<th>Last Name</th>" +
                            "</tr>" +
         "</div>" +
         "</div>"
%>

<%--Table Data--%>
   <% for (User u: studentsEnrolledInCourse) { %>
           <%=
            "<tr>" +
                "<th>" + (u.getID()) + "</th>" +
                "<th>" + (u.getFirstName())+ "</th>" +
                "<th>" + (u.getLastName()) + "</th>" +
            "</tr>"
            %>
       <% } %>

<%--    Table Footer with close button --%>
 <%=
        "</table>" +
        "<div class='row'>" +
           "<div class='col'>" +
           "<a href=''>" %>
            <%=
            "<button type='button' class='btn btn-primary'>Close Report</button></a>" +
            "</div>" +
                    "</div>"
            %>

</div>
</body>
</html>