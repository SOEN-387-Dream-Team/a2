package soen387.DAO;

<<<<<<< HEAD
import soen387.Course;
import soen387.DataMapperException;
import soen387.User;
import soen387.UserDataMapper;
=======
import soen387.User;
>>>>>>> 08374c609b155e7cbfea1943523abfba88b514b8

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
import java.util.Optional;

public final class UserDAO implements UserDataMapper {


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
=======

import static soen387.DatabaseConnConstants.DB_USER;
import static soen387.DatabaseConnConstants.DB_PASSWORD;


public class UserDAO implements Dao<User> {


    @Override
    public void create(User user) throws ClassNotFoundException {

    }

    @Override
    public User get(long id) throws ClassNotFoundException{
        String SELECT_COURSE_SQL = "SELECT * FROM user" +
                "  WHERE id = ?; ";


        Class.forName("com.mysql.jdbc.Driver");

        User retrievedUser = null;
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soen387_school", DB_USER, DB_PASSWORD);

             //Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COURSE_SQL)) {

            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                int userId = (int) id;
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String dateOfBirth = rs.getString("dateOfBirth");
                String password = rs.getString("password"); //Prob avoid this?
                boolean isAdmin = (rs.getInt("isAdmin") == 1) ? true: false;


                retrievedUser = new User(
                        userId,
                        firstName,
                        lastName,
                        address,
                        email,
                        phoneNumber,
                        dateOfBirth,
                        password,
                        isAdmin
                );
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }

        return retrievedUser;
    }

    @Override
    public User get(String idStr) throws ClassNotFoundException {
        return null;
    }

    public User getByFirstNameAndLastName(String fName, String lName) throws ClassNotFoundException {
        String SELECT_COURSE_SQL = "SELECT id FROM user u WHERE u.firstName=? AND u.lastName=?";


        Class.forName("com.mysql.jdbc.Driver");

        User retrievedUser = null;
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soen387_school", DB_USER, DB_PASSWORD);

             //Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COURSE_SQL)) {

            preparedStatement.setString(1, fName);
            preparedStatement.setString(2, lName);
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                int userId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String dateOfBirth = rs.getString("dateOfBirth");
                String password = rs.getString("password"); //Prob avoid this?
                boolean isAdmin = (rs.getInt("isAdmin") == 1) ? true: false;


                retrievedUser = new User(
                        userId,
                        firstName,
                        lastName,
                        address,
                        email,
                        phoneNumber,
                        dateOfBirth,
                        password,
                        isAdmin
                );
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }

        return retrievedUser;
    }

    public boolean isSignInValid(int userId, String typedPassword) throws ClassNotFoundException {
        String SELECT_AUTHENTICATE_USER_SQL = "SELECT * FROM user WHERE id=? AND password=?";

        Class.forName("com.mysql.jdbc.Driver");

        boolean flag = false;
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soen387_school", DB_USER, DB_PASSWORD);

             //Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHENTICATE_USER_SQL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, typedPassword);
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            rs.last(); //move cursor to last row

            if(rs.getRow() > 0){ //get total row count
                flag = true;
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }


        return flag;
    }

    @Override
    public List<User> getAll() throws ClassNotFoundException {
        String SELECT_ALLUSERS_SQL = "SELECT * FROM user;";

        Class.forName("com.mysql.jdbc.Driver");

        List<User> allUsers = new ArrayList<>();
        User retrievedUser = null;
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soen387_school", DB_USER, DB_PASSWORD);

             //Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALLUSERS_SQL)) {
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                int userId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String dateOfBirth = rs.getString("dateOfBirth");
                String password = rs.getString("password");
                boolean isAdmin = (rs.getInt("isAdmin") == 1) ? true: false;

                retrievedUser = new User(
                        userId,
                        firstName,
                        lastName,
                        address,
                        email,
                        phoneNumber,
                        dateOfBirth,
                        password,
                        isAdmin
                );
                allUsers.add(retrievedUser);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }

        return allUsers;
    }

    public List<User> getAllStudents() throws ClassNotFoundException {
        String SELECT_ALLSTUDENTS_SQL = "SELECT * FROM user WHERE isAdmin = ?;";

        Class.forName("com.mysql.jdbc.Driver");

        List<User> allStudents = new ArrayList<>();
        User retrievedUser = null;
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soen387_school", DB_USER, DB_PASSWORD);

             //Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALLSTUDENTS_SQL)) {
            preparedStatement.setInt(1, 0); //isAdmin of 0 indicates students
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                int userId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String dateOfBirth = rs.getString("dateOfBirth");
                String password = rs.getString("password");
                boolean isAdmin = (rs.getInt("isAdmin") == 1) ? true: false;

                retrievedUser = new User(
                        userId,
                        firstName,
                        lastName,
                        address,
                        email,
                        phoneNumber,
                        dateOfBirth,
                        password,
                        isAdmin
                );
                allStudents.add(retrievedUser);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }

        return allStudents;
    }

    public List<User> getAllAdmins() throws ClassNotFoundException {
        String SELECT_ALLADMINS_SQL = "SELECT * FROM user WHERE isAdmin = ?;";

        Class.forName("com.mysql.jdbc.Driver");

        List<User> allAdmins = new ArrayList<>();
        User retrievedUser = null;
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soen387_school", DB_USER, DB_PASSWORD);

             //Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALLADMINS_SQL)) {
            preparedStatement.setInt(1, 1); //isAdmin of 1 indicates admin
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                int userId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String dateOfBirth = rs.getString("dateOfBirth");
                String password = rs.getString("password");
                boolean isAdmin = (rs.getInt("isAdmin") == 1) ? true: false;

                retrievedUser = new User(
                        userId,
                        firstName,
                        lastName,
                        address,
                        email,
                        phoneNumber,
                        dateOfBirth,
                        password,
                        isAdmin
                );
                allAdmins.add(retrievedUser);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }

        return allAdmins;
    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

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
>>>>>>> 08374c609b155e7cbfea1943523abfba88b514b8
        }
    }
}
