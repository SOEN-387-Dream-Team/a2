package soen387.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpSession;
import soen387.Course;
import soen387.DAO.CourseDAO;
import soen387.DAO.EnrollmentDAO;
import soen387.DAO.UserDAO;
import soen387.Enrollment;
import soen387.User;

@WebServlet("/dropCourse")
public class DropCourseServlet extends HttpServlet {
    private EnrollmentDAO enrollmentDAO;
    private UserDAO userDAO;
    private CourseDAO courseDAO;

    private HttpSession session;

    public void init() {
        enrollmentDAO = new EnrollmentDAO();
        userDAO = new UserDAO();
        courseDAO = new CourseDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        String selectedCourse = request.getParameter("dropCode");
        try {
            session = request.getSession();
            String currentStudentIdStr = (String) session.getAttribute("id");

            User currentStudent = userDAO.get(Integer.parseInt(currentStudentIdStr));
            Course courseSelectedToDrop = courseDAO.get(selectedCourse);

            Enrollment enrollmentToDrop = new Enrollment(currentStudent, courseSelectedToDrop);

            enrollmentDAO.delete(enrollmentToDrop);
            pw.write("<p> Course " + selectedCourse +  " was dropped </p");
            response.setHeader("Refresh", "3;url=StudentPage.jsp");


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            pw.write("<p> Course " + selectedCourse +  " was not able to be dropped </p");
            response.setHeader("Refresh", "3;url=StudentPage.jsp");
        }
    }
}