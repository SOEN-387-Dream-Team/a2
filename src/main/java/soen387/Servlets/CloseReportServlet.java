package soen387.Servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import soen387.Course;
import soen387.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * This is used for the Close button of the Course Report and Student Report in the AdminPage
 */
@WebServlet("/closeReport")
public class CloseReportServlet extends HttpServlet  {


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            response.sendRedirect("AdminPage.jsp");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
