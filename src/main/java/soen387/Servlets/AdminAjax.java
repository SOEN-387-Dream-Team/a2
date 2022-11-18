package soen387.Servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AdminAjax")
public class AdminAjax extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = (String) request.getSession().getAttribute("id");
        String test = "<option value=" + "\"This is " + id + "\">hey</option>";
        response.setContentType("text/plain");
        PrintWriter pw = response.getWriter();
        pw.write(test);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
