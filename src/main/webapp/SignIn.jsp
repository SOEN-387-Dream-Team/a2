<%--
  User: gabriel
  Date: 9/11/22
  Time: 23:24
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%
    String userid = request.getParameter("userId");
    session.setAttribute("userid", userid);
    String password = request.getParameter("password");
    Class.forName("com.mysql.jdbc.Driver");
    out.println(password + userid);
    java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/soen387_school", "root", "synystergates");
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery("select * from user where id='" + userid + "' and password='" + password + "'");
    try {
        rs.next();
        if (rs.getString("password").equals(password) && rs.getString("id").equals(userid)) {
            out.println("<h2>Welcome " + rs.getString("firstName") + "</h2>");
            out.println("WELCOME");
        } else {
            out.println("Invalid password or username.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
