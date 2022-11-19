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

@WebServlet("/AdminAjax")
public class AdminAjax extends HttpServlet {
    private UserDAO userdao;
    private CourseDAO courseDAO;

    public void init() {
        courseDAO = new CourseDAO();
        userdao = new UserDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        boolean courseReport = Boolean.valueOf(request.getParameter("CourseReport"));
        if (courseReport) { // Asking for course report
            try {
                List<Course> courses = courseDAO.getAll();
                for (Course element: courses) {
                    String code = element.getCourseCode();
                    String dropdownElement = "<option value=\"" + code + "\">" + code + "</option>";
                    pw.write(dropdownElement);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else { //Asks for student report
            try {
                List<User> students = userdao.getAllStudents();
                for (User s: students) {
                    String name = s.getFirstName() + " " + s.getLastName();
                    String dropdownElement = "<option value=\"" + name + "\">" + name + "</option>";
                    pw.write(dropdownElement);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
