package soen387.Servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import soen387.DAO.*;
import soen387.Course;
import soen387.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/StudentAjax")
public class StudentAjax extends HttpServlet {
    private EnrollmentDAO enrollmentDAO;

    public void init() {
        enrollmentDAO = new EnrollmentDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = (String) request.getSession().getAttribute("id");
        String semester = request.getParameter("chosenSemester");
        User student = new User();
        student.setID(Integer.parseInt(id));
        System.out.println(id + semester);
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        List<Course> courses = null;
        boolean studentReport = Boolean.valueOf(request.getParameter("addReport"));
        if (studentReport) { // Asking for add courses list
            try {
                courses = enrollmentDAO.getUnenrolledCoursesForStudentBasedOnSemester(student, semester);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else { // Asking for drop courses list
            try {
                courses = enrollmentDAO.getEnrolledCoursesForStudent(student);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        for (Course c: courses) {
            String code = c.getCourseCode();
            String dropdownElement = "<option value=\"" + code + "\">" + code + "</option>";
            pw.write(dropdownElement);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
