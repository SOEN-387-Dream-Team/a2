package soen387.DAO;

import soen387.Course;
import soen387.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CourseDAO implements Dao<Course> {


    @Override
    public void create(Course course) {
        String INSERT_USERS_SQL = "INSERT INTO course" +
                "  (code, title, semester, room, start, end, days, time, instructor) VALUES " +
                " (?, ?, ?, ?, ?,?,?,?,?);";

        int result = 0;

        //Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soen387_school", "admin", "admin");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, course.getCourseCode());
            preparedStatement.setString(2, course.getTitle());
            preparedStatement.setString(3, course.getSemester());
            preparedStatement.setString(4, course.getRoom());
            preparedStatement.setString(5, course.getStartDate());
            preparedStatement.setString(6, course.getEndDate());
            preparedStatement.setString(7, course.getDays());
            preparedStatement.setString(8, course.getTime());
            preparedStatement.setString(9, course.getInstructor());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        System.out.println("Created new course");
        //return result;

    }

    @Override
    public Optional<Course> get(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Course> get(String idStr) {
        return Optional.empty();
    }

    @Override
    public List<Course> getAll() {
        return null;
    }

    @Override
    public void update(Course course, String[] params) {

    }

    @Override
    public void delete(Course course) {

    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
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
