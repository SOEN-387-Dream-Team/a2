package soen387;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class DataMapperImpl implements UserDataMapper{

    //should be as an actual database
    private final List<User> users = new ArrayList<>();

    public List <User> getUsers() {
        return this.users;
    }

    @Override
    public Optional<User> find(int userID){
        for (final User user: this.getUsers()) {
            if (user.getID() == userID) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public void findEnrolledClasses(User student) throws DataMapperException {
        String ENROLLED_COURSES_WITH_ID = "SELECT * FROM student_courses WHERE id=?"
                //TODO : add connection to front-end selection
                ;
        int result = 0;
        try (Connection connection = DriverManager
                //TODO : change connection to database url
                .getConnection("jdbc:mysql://localhost:3306/test", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(ENROLLED_COURSES_WITH_ID))
        {
            //TODO : add connection to front-end selection
            //preparedStatement.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void findEnrolledClasses(User student, String semester) throws DataMapperException {
        String ENROLLED_COURSES_WITH_SEMESTER = "SELECT * FROM student_courses WHERE id=? and semester=?"
                //TODO : add connection to front-end selection
                ;
        int result = 0;
        try (Connection connection = DriverManager
                //TODO : change connection to database url
                .getConnection("jdbc:mysql://localhost:3306/test", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(ENROLLED_COURSES_WITH_SEMESTER))
        {
            //TODO : add connection to front-end selection
            //preparedStatement.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void findCourseStudents(Course course) throws DataMapperException {
        String NON_ENROLLED_COURSES = "SELECT * FROM courses WHERE courseCode NOT IN (SELECT courseCode FROM student_courses WHERE id=?) and semester=?"
                //TODO : add connection to front-end selection
                ;
        int result = 0;
        try (Connection connection = DriverManager
                //TODO : change connection to database url
                .getConnection("jdbc:mysql://localhost:3306/test", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(NON_ENROLLED_COURSES))
        {
            //TODO : add connection to front-end selection
            //preparedStatement.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void findUnenrolledClasses(User student) throws DataMapperException {
        String NON_ENROLLED_COURSES = "SELECT * FROM courses WHERE courseCode NOT IN (SELECT courseCode FROM student_courses WHERE id=?) and semester=?"
                //TODO : add connection to front-end selection
                ;
        int result = 0;
        try (Connection connection = DriverManager
                //TODO : change connection to database url
                .getConnection("jdbc:mysql://localhost:3306/test", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(NON_ENROLLED_COURSES))
        {
            //TODO : add connection to front-end selection
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createUser(User user) throws DataMapperException {
        String INSERT_USERS_SQL = "INSERT INTO users" +
                "  (id, first_name, last_name, address, email, phoneNum, dateOB, courses, password, isAdmin) VALUES " +
                " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        int result = 0;
        //unsure what to add here
        //Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                //TODO : change connection to database url
                .getConnection("jdbc:mysql://localhost:3306/test", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL))
        {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getPhoneNum());
            preparedStatement.setString(8, user.getDOB());
            preparedStatement.setString(9, user.getPass());
            preparedStatement.setBoolean(10, user.getAdminStatus());

            System.out.println(preparedStatement);

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
                throw new RuntimeException(e);
        }
        return result;
    }
}
