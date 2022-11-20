<%@ page import="soen387.Course" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: mauran
  Date: 2022-11-20
  Time: 2:09 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Student Report</title>
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
    List<Course> coursesEnrolledByStudent = (List<Course>) request.getAttribute("data");
    String studentName = (String)  request.getAttribute("studentName");
    String redirectToAdminPage = "AdminPage.jsp";

    for (Course c: coursesEnrolledByStudent
    ) {
      System.out.println(c);
    }
  %>

  <%=
  //Display of the results (Table Header)
          "<div class='row'>" +
                  "<div class='col'>" +
                  "<h1>" + (studentName) + "'s Student Report</h1>" +
                  "</div>" +
                  "</div>" +

                  "<div class='row'>" +
                  "<div class='col'>" +
                  "<table  class='table table-dark table-striped table-bordered border-light table-hover'>" +
                  "<tr class='table-primary'>" +
                  "<th>Course Code</th>" +
                  "<th>Title</th>" +
                  "<th>Semester</th>" +
                  "</tr>" +
                  "</div>" +
                  "</div>"
  %>

  <%--Table Data--%>
  <% for (Course c: coursesEnrolledByStudent) { %>
  <%=
  "<tr>" +
          "<th>" + (c.getCourseCode()) + "</th>" +
          "<th>" + (c.getTitle())+ "</th>" +
          "<th>" + (c.getSemester()) + "</th>" +
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