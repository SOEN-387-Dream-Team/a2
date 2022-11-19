package soen387.Servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import soen387.Course;
import soen387.DAO.EnrollmentDAO;
import soen387.DAO.UserDAO;
import soen387.User;

@WebServlet("/submitStudentReport")
public class StudentReportServlet extends HttpServlet {
    private EnrollmentDAO enrollmentDAO;
    private UserDAO userDAO;

    public void init() {
        enrollmentDAO = new EnrollmentDAO();
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String studentName = request.getParameter("studentName");

            User studentSelectedForReport = userDAO.getByFirstNameAndLastName("alex", "smith");

            List<Course> coursesEnrolledByStudent = enrollmentDAO.getEnrolledCoursesForStudent(studentSelectedForReport);

            // Setting the attribute of the request object which will be later fetched by a JSP page
            request.setAttribute("data", coursesEnrolledByStudent);

            // Creating a RequestDispatcher object to dispatch the request to another resource
            RequestDispatcher rd = request.getRequestDispatcher("test_studentReport.jsp");

            rd.forward(request, response);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        response.sendRedirect("test_studentReport.jsp");
    }
}