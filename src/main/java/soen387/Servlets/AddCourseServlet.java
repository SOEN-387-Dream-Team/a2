package soen387.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import jakarta.servlet.http.HttpSession;
import soen387.Course;
import soen387.DAO.CourseDAO;
import soen387.DAO.EnrollmentDAO;
import soen387.DAO.UserDAO;
import soen387.Enrollment;
import soen387.User;

@WebServlet("/addCourse")
public class AddCourseServlet extends HttpServlet {
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

        try {
            session = request.getSession();
            String currentStudentIdStr = (String) session.getAttribute("id");
            String selectedSemester = request.getParameter("semesterChoice");
            String selectedCourse = request.getParameter("courseCode");

            User currentStudent = userDAO.get(Integer.parseInt(currentStudentIdStr));
            Course courseSelectedToAdd = courseDAO.get(selectedCourse);

            Enrollment newEnrollment = new Enrollment(currentStudent, courseSelectedToAdd);

            enrollmentDAO.create(newEnrollment);


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        response.sendRedirect("test_courseAdded.jsp");
    }
}