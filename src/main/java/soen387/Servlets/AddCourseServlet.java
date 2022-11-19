package soen387.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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

    public void init() {
        enrollmentDAO = new EnrollmentDAO();
        userDAO = new UserDAO();
        courseDAO = new CourseDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int currentStudentId = Integer.parseInt(request.getParameter("id"));
            String selectedSemester = request.getParameter("semesterChoice");
            String selectedCourse = request.getParameter("courseCode");

            User currentStudent = userDAO.get(currentStudentId);
            Course courseSelectedToAdd = courseDAO.get(selectedCourse);

            Enrollment newEnrollment = new Enrollment(currentStudent, courseSelectedToAdd);

            enrollmentDAO.create(newEnrollment);


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}