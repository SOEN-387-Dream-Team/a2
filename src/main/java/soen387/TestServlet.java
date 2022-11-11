package soen387;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import soen387.DAO.CourseDAO;

import java.io.IOException;

@WebServlet("/register")
public class TestServlet extends HttpServlet{

    private static final long serialVersionUID = 1;
    private CourseDAO courseDAO;

    public void init() {
        courseDAO = new CourseDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        String firstName = request.getParameter("firstName");
//        String lastName = request.getParameter("lastName");
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String address = request.getParameter("address");
//        String contact = request.getParameter("contact");


        //(code, title, semester, room, start, end, days, time, instructor)
        Course courseTest = new Course();
        courseTest.setCourseCode("test111");
        courseTest.setTitle("test");
        courseTest.setSemester("test");
        courseTest.setRoom("test");
        courseTest.setStartDate("test");
        courseTest.setEndDate("test");
        courseTest.setDays("test");
        courseTest.setTime("test");
        courseTest.setInstructor("test");



        try {
            courseDAO.create(courseTest);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        response.sendRedirect("courseCreated.jsp");
    }
}
