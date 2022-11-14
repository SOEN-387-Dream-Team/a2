package soen387.DAO;

import soen387.Course;
import soen387.DataMapperException;
import soen387.User;
import soen387.UserDataMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UserDAO implements UserDataMapper {

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
        //find all the courses student is enrolled in
        String ENROLLED_COURSES_WITH_ID = "SELECT * FROM student_courses WHERE id=?"
                //TODO : add connection to front-end selection
                ;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soen387_school", "admin", "admin");
             Statement stat = connection.createStatement();
             ResultSet result = stat.executeQuery(ENROLLED_COURSES_WITH_ID);)
        {
            while(result.next()){
                System.out.print("Course Name: " + result.getString("title"));
                System.out.print(", Course Code: " + result.getString("courseCode"));
                System.out.print(", Semester: " + result.getString("semester"));
                System.out.println(", Instructor: " + result.getString("instructor"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void findEnrolledClasses(User student, String semester) throws DataMapperException {
        //find all the courses student is enrolled within a semester
        String ENROLLED_COURSES_WITH_SEMESTER = "SELECT * FROM student_courses WHERE id=? and semester=?"
                //TODO : add connection to front-end selection
                ;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soen387_school", "admin", "admin");
             Statement stat = connection.createStatement();
             ResultSet result = stat.executeQuery(ENROLLED_COURSES_WITH_SEMESTER);)
        {
            while(result.next()){
                System.out.print("Course Name: " + result.getString("title"));
                System.out.print(", Course Code: " + result.getString("courseCode"));
                System.out.println(", Instructor: " + result.getString("instructor"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void findCourseStudents(Course course) throws DataMapperException {
        //find all students in a course
        String STUDENTS_IN_COURSE = "SELECT DISTINCT s.id, u.firstName, u.lastName FROM student_courses s JOIN user u ON s.id = u.id WHERE s.courseCode=UPPER(?)";
                //TODO : add connection to front-end selection
                ;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soen387_school", "admin", "admin");
             Statement stat = connection.createStatement();
             ResultSet result = stat.executeQuery(STUDENTS_IN_COURSE);)
        {
            while(result.next()){
                System.out.print("Student ID: " + result.getString("id"));
                System.out.print("Student Name: " + result.getString("firstName") + " " + result.getString("lastName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void findUnenrolledClasses(User student) throws DataMapperException {
        //find all courses student is not enrolled in
        String NON_ENROLLED_COURSES = "SELECT * FROM courses WHERE courseCode NOT IN (SELECT courseCode FROM student_courses WHERE id=?) and semester=?"
                //TODO : add connection to front-end selection
                ;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soen387_school", "admin", "admin");
             Statement stat = connection.createStatement();
             ResultSet result = stat.executeQuery(NON_ENROLLED_COURSES);)
        {
            while(result.next()){
                System.out.print("Course Code: " + result.getString("courseCode"));
                System.out.print(", Semester: " + result.getString("semester"));
                System.out.println(", Instructor: " + result.getString("instructor"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //done
    @Override
    public int createUser(User user) throws DataMapperException {
        //add a user with these parameters
        String INSERT_USERS_SQL = "INSERT INTO users" +
                "  (id, first_name, last_name, address, email, phoneNum, dateOB, courses, password, isAdmin) VALUES " +
                " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        int result = 0;

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

    @Override
    public void registerCourse(User student, Course course) throws DataMapperException {

        String NUM_ENROLL_COURSES = "SELECT * FROM student_courses c INNER JOIN courses c ON s.courseCode = c.courseCode WHERE s.id = ? AND c.semester =?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soen387_school", "admin", "admin");
             Statement stat = connection.createStatement();
             ResultSet result = stat.executeQuery(NUM_ENROLL_COURSES);)
        {
            int size = 0;
            if (result != null){
                result.last();
                size = result.getRow();
            }
            if(size < 5) {
                //check to see if we are within the 1 week time limit
                String DATE_SQL = "SELECT startDate FROM courses c WHERE CURDATE() < DATE_ADD(c.startDate, INTERVAL 7 DAYS";
                try (ResultSet dateResult = stat.executeQuery(DATE_SQL); )
                {
                    int dateValid = 0;
                    if (dateResult != null){
                        dateResult.last();
                        dateValid = dateResult.getRow();
                    }
                    //On time to register
                    if (dateValid > 0){
                        //Add course to student
                        String ENROLL_SQL = "INSERT INTO student_courses(id, courseCode) VALUES (?, UPPER(?))";
                        try (ResultSet enrollResult = stat.executeQuery(DATE_SQL); )
                        {
                            System.out.println("Successfully enrolled in " + enrollResult.getString("courseCode") + ".");
                        }
                    }
                    //could not enroll due to date limit
                    else{
                        System.out.println("Could not enroll due to the date restriction.");
                    }
                }
            }
            else {
                System.out.println("Can not enroll to any more classes; max of 5 classes already reached.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropCourse(User student, Course course) throws DataMapperException {
        String COURSES_SQL = "SELECT * FROM student_courses WHERE courseCode = UPPER(?) AND id=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soen387_school", "admin", "admin");
             Statement stat = connection.createStatement();
             ResultSet result = stat.executeQuery(COURSES_SQL);) {
            int size = 0;
            if (result != null) {
                result.last();
                size = result.getRow();
            }
            if(size > 0){
                //check if the class has ended already or not
                String DATE_SQL = "SELECT * FROM courses c WHERE CURDATE() < c.endDate AND c.courseCODE = UPPER(?)";
                try (ResultSet dateResult = stat.executeQuery(DATE_SQL); ) {
                    int dateValid = 0;
                    if (dateResult != null) {
                        dateResult.last();
                        dateValid = dateResult.getRow();
                    }
                    if (dateValid > 0){
                        String DROP_SQL = "DELETE FROM student_courses WHERE courseCode = UPPER(?) AND id=?";
                        try (ResultSet dropResult = stat.executeQuery(DROP_SQL);){
                            System.out.println("Course " + dropResult.getString("courseCode") + " successfully dropped");
                        }
                    }
                    else{
                        System.out.println("Error occurred Course unsuccessfully dropped.");
                    }
                }
            }
            else{
                System.out.println("Error occurred, Student is not enrolled in this course.");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
