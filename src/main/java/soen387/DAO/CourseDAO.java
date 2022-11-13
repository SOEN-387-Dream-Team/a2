package soen387.DAO;

import soen387.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO implements Dao<Course> {


    @Override
    public void create(Course course) throws ClassNotFoundException {
        String INSERT_COURSE_SQL = "INSERT INTO courses" +
                "  (courseCode, title, semester, room, startDate, endDate, days, time, instructor) VALUES " +
                " (?, ?, ?, ?, ?,?,?,?,?);";

        String startDate = "";
        String endDate = "";

        // Logic for start and end date based on semester
        if (course.getSemester().equals("FALL-2022")) {
            startDate = "2022-10-09";
            endDate = "2022-12-31";
        } else if (course.getSemester().equals("WINTER-2023")) {
            startDate = "2023-01-05";
            endDate = "2023-04-30";
        } else if (course.getSemester().equals("SUMMER1-2023")) {
            startDate = "2023-05-01";
            endDate = "2023-08-31";
        } else if (course.getSemester().equals("SUMMER2-2023")) {
            startDate = "2023-06-01";
            endDate = "2023-08-31";
        }

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soen387_school", "admin", "admin");

             //Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COURSE_SQL)) {

            preparedStatement.setString(1, course.getCourseCode());
            preparedStatement.setString(2, course.getTitle());
            preparedStatement.setString(3, course.getSemester());
            preparedStatement.setString(4, course.getRoom());
            preparedStatement.setString(5, startDate);
            preparedStatement.setString(6, endDate);
            preparedStatement.setString(7, course.getDays());
            preparedStatement.setString(8, course.getTime());
            preparedStatement.setString(9, course.getInstructor());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
    }

    @Override
    public Course get(long id) {
        return null;
    }

    @Override
    public Course get(String idStr) throws ClassNotFoundException {

        String SELECT_COURSE_SQL = "SELECT * FROM courses" +
                "  WHERE courseCode = ?; ";


        Class.forName("com.mysql.jdbc.Driver");

        Course retrievedCourse = null;
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soen387_school", "admin", "admin");

             //Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COURSE_SQL)) {


            preparedStatement.setString(1, idStr);


            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                String courseCode = idStr;
                String title = rs.getString("title");
                String semester = rs.getString("semester");
                String room = rs.getString("room");
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                String days = rs.getString("days");
                String time = rs.getString("time");
                String instructor = rs.getString("instructor");


                retrievedCourse = new Course(
                        courseCode,
                        title,
                        semester,
                        room,
                        startDate,
                        endDate,
                        days,
                        time,
                        instructor
                );
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }

        return retrievedCourse;
    }

    @Override
    public List<Course> getAll() throws ClassNotFoundException {
        String SELECT_ALLCOURSES_SQL = "SELECT * FROM courses;";

        Class.forName("com.mysql.jdbc.Driver");

        List<Course> allCourses = new ArrayList<>();
        Course retrievedCourse = null;
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soen387_school", "admin", "admin");

             //Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALLCOURSES_SQL)) {
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                String courseCode =  rs.getString("courseCode");;
                String title = rs.getString("title");
                String semester = rs.getString("semester");
                String room = rs.getString("room");
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                String days = rs.getString("days");
                String time = rs.getString("time");
                String instructor = rs.getString("instructor");


                retrievedCourse = new Course(
                        courseCode,
                        title,
                        semester,
                        room,
                        startDate,
                        endDate,
                        days,
                        time,
                        instructor
                );
                allCourses.add(retrievedCourse);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }

        return allCourses;
    }

    public boolean isCourseRegisterWithinDateLimit(Course c) throws ClassNotFoundException {

        String SELECT_COURSE_STARTDATE_SQL = "SELECT startDate" +
                                        "FROM courses c" +
                                        "WHERE CURDATE() < DATE_ADD(c.startDate, INTERVAL 7 DAY)" +
                                        "AND c.courseCode = UPPER(?)";

        Class.forName("com.mysql.jdbc.Driver");

        boolean flag = false;
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soen387_school", "admin", "admin");

             //Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COURSE_STARTDATE_SQL)) {
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            rs.last(); //move cursor to last row

            if(rs.getRow() > 0){ //get total row count
                flag = true; //we are within the date limit to add the course
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }


        return flag;
    }

    @Override
    public void update(Course course, String[] params) {

    }

    @Override
    public void delete(Course course) {

    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
