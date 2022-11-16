package soen387.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import soen387.Course;
import soen387.DAO.CourseDAO;

@WebServlet("/create")
public class CreateCourseServlet extends HttpServlet {

    private static final long serialVersionUID = 1;
    private CourseDAO courseDAO;

    public void init() {
        courseDAO = new CourseDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //startDate and endDate are set programmatically
        String courseCode = request.getParameter("courseCode");
        String title = request.getParameter("title");
        String semester = request.getParameter("semester");
        String room = request.getParameter("room");
        String days = request.getParameter("days");
        String time = request.getParameter("time");
        String instructor = request.getParameter("instructor");

        Course courseTest = new Course();
        courseTest.setCourseCode(courseCode);
        courseTest.setTitle(title);
        courseTest.setSemester(semester);
        courseTest.setRoom(room);
        courseTest.setDays(days);
        courseTest.setTime(time);
        courseTest.setInstructor(instructor);


        try {
            courseDAO.create(courseTest);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        response.sendRedirect("test_courseCreated.jsp");
    }
}
