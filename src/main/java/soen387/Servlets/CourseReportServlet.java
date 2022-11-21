package soen387.Servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import soen387.Course;
import soen387.DAO.CourseDAO;
import soen387.DAO.EnrollmentDAO;
import soen387.User;

@WebServlet("/submitCourseReport")
public class CourseReportServlet extends HttpServlet {
    private EnrollmentDAO enrollmentDAO;
    private CourseDAO courseDAO;

    public void init() {
        enrollmentDAO = new EnrollmentDAO();
        courseDAO = new CourseDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Course courseSelectedForReport = courseDAO.get(request.getParameter("courseCode"));

            List<User> studentsEnrolledInCourse = enrollmentDAO.getAllStudentsForEnrolledCourse(courseSelectedForReport);

            // Setting the attribute of the request object which will be later fetched by a JSP page
            request.setAttribute("data", studentsEnrolledInCourse);
            request.setAttribute("selectedCourseName", courseSelectedForReport.getCourseCode());

            // Creating a RequestDispatcher object to dispatch the request to another resource
            RequestDispatcher rd = request.getRequestDispatcher("CourseReport.jsp");
            rd.forward(request, response);
            
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}