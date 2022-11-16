package soen387.DAO;

import soen387.Course;
import soen387.Enrollment;
import soen387.User;

import static soen387.DatabaseConnConstants.CONNECTION;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO implements Dao<Enrollment> {

    @Override
    //Create an Enrollment --> register a course to a student
    public void create(Enrollment enrollment) throws ClassNotFoundException {
        //Conditions that need to be met before registering
        if (isCourseLimitPerSemesterValid(enrollment.getUser(), enrollment.getCourse())) {
            String INSERT_ENROLLMENT_SQL = "INSERT INTO student_courses VALUES (?, UPPER(?));";

            Class.forName("com.mysql.jdbc.Driver");

            try (

                    PreparedStatement preparedStatement = CONNECTION.prepareStatement(INSERT_ENROLLMENT_SQL)) {

                preparedStatement.setInt(1, enrollment.getUser().getID());
                preparedStatement.setString(2, enrollment.getCourse().getCourseCode());

                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                // process sql exception
                printSQLException(e);
            }
        } else
            System.out.println("Unable to create enrollment");
    }

    public boolean isCourseLimitPerSemesterValid(User student, Course course) {
        boolean courseLimitFlag = false;
        boolean startDateLimitFlag = false;

        String SELECT_ENROLLED_COURSES = "SELECT * FROM student_courses c INNER JOIN courses c ON s.courseCode = c.courseCode WHERE s.id = ? AND c.semester = UPPER(?)";
        try (
                PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_ENROLLED_COURSES)) {
            preparedStatement.setInt(1, student.getID());
            preparedStatement.setString(2, course.getSemester());
            System.out.println(preparedStatement);

            ResultSet result = preparedStatement.executeQuery();

            int size = 0;
            if (result != null) {
                result.last();
                size = result.getRow();
            }
            if (size < 5) {
                courseLimitFlag = true;

                //check to see if we are within the 1 week time limit
                startDateLimitFlag = isCourseRegistrationWithinDateLimit(course);
            } else {
                System.out.println("Can not enroll to any more classes; max of 5 classes already reached.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (courseLimitFlag && startDateLimitFlag);
    }

    public boolean isCourseRegistrationWithinDateLimit(Course course) {
        boolean startDateLimitFlag = false;

        //check to see if we are within the 1 week time limit
        String SELECT_VALID_COURSE_STARTDATE_SQL = "SELECT startDate FROM courses c WHERE CURDATE() < DATE_ADD(c.startDate, INTERVAL 7 DAYS AND c.courseCode = UPPER(?);";
        try (
                PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_VALID_COURSE_STARTDATE_SQL)) {
            preparedStatement.setString(1, course.getCourseCode());
            System.out.println(preparedStatement);

            ResultSet result = preparedStatement.executeQuery();

            int dateValidCount = 0;
            if (result != null) {
                result.last();
                dateValidCount = result.getRow();
            }
            //On time to register
            if (dateValidCount > 0) {
                startDateLimitFlag = true;
            }
            //could not enroll due to date limit
            else {
                System.out.println("Could not enroll due to the date restriction.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return startDateLimitFlag;
    }

    @Override
    //Delete an Enrollment --> drop the course that the student was registered to.
    public void delete(Enrollment enrollment) throws ClassNotFoundException {

        //Conditions that need to be met before drop course
        if (isStudentEnrolledInCourse(enrollment.getUser(), enrollment.getCourse())) {
            String DELETE_ENROLLMENT_SQL = "DELETE FROM student_courses WHERE id=? AND courseCode = UPPER(?);";

            Class.forName("com.mysql.jdbc.Driver");

            try (
                    PreparedStatement preparedStatement = CONNECTION.prepareStatement(DELETE_ENROLLMENT_SQL)) {

                preparedStatement.setInt(1, enrollment.getUser().getID());
                preparedStatement.setString(2, enrollment.getCourse().getCourseCode());

                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                // process sql exception
                printSQLException(e);
            }
        } else
            System.out.println("Unable to delete enrollment");

    }

    /**
     * Used to check if a student is enrolled in a particular course
     *
     * @param student
     * @param course
     * @return
     */
    public boolean isStudentEnrolledInCourse(User student, Course course) {
        boolean studentEnrolledInCourseFlag = false;
        boolean endDateLimitFlag = false;

        String SELECT_ENROLLED_COURSE_SQL = "SELECT * FROM student_courses WHERE id=? AND courseCode = UPPER(?); ";
        try (
                PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_ENROLLED_COURSE_SQL)) {
            preparedStatement.setInt(1, student.getID());
            preparedStatement.setString(2, course.getCourseCode());
            System.out.println(preparedStatement);

            ResultSet result = preparedStatement.executeQuery();

            int size = 0;
            if (result != null) {
                result.last();
                size = result.getRow();
            }

            if (size > 0) {
                studentEnrolledInCourseFlag = true;

                //check if the class has ended already or not
                endDateLimitFlag = isCourseDropWithinDateLimit(course);
            } else
                System.out.println("Error occurred, Student is not enrolled in this course.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return studentEnrolledInCourseFlag && endDateLimitFlag;
    }

    public boolean isCourseDropWithinDateLimit(Course course) {
        boolean endDateLimitFlag = false;

        //check to see if we are within the semester time limit to drop course
        String SELECT_VALID_COURSE_ENDDATE_SQL = "SELECT * FROM courses c WHERE CURDATE() < c.endDate AND c.courseCODE = UPPER(?);";
        try (
                PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_VALID_COURSE_ENDDATE_SQL)) {
            preparedStatement.setString(1, course.getCourseCode());
            System.out.println(preparedStatement);

            ResultSet result = preparedStatement.executeQuery();

            int dateValidCount = 0;
            if (result != null) {
                result.last();
                dateValidCount = result.getRow();
            }
            //Have time to drop the course
            if (dateValidCount > 0) {
                endDateLimitFlag = true;
            }
            //could not drop due to end date expired
            else {
                System.out.println("Could not drop course due to the end date restriction.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return endDateLimitFlag;
    }

    /**
     * gets all the courses this particular student is enrolled in
     *
     * @param student
     * @return
     */
    public List<Course> getEnrolledCoursesForStudent(User student) throws ClassNotFoundException {
        String SELECT_ALL_ENROLLMENTS_SQL = "SELECT * FROM student_courses WHERE id=?;";

        Class.forName("com.mysql.jdbc.Driver");

        List<Course> allEnrolledCourses = new ArrayList<>();
        Course retrievedEnrolledCourse = null;
        CourseDAO courseDAO = new CourseDAO();

        try (
                PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_ALL_ENROLLMENTS_SQL)) {
            preparedStatement.setInt(1, student.getID());
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                retrievedEnrolledCourse = courseDAO.get(rs.getString("courseCode"));
                allEnrolledCourses.add(retrievedEnrolledCourse);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }

        return allEnrolledCourses;

    }

    /**
     * gets all the unenrolled courses this particular student
     *
     * @param student
     * @return
     */
    public List<Course> getUnenrolledCoursesForStudent(User student) throws ClassNotFoundException {
        String SELECT_ALL_UNENROLLMENTS_SQL = "SELECT * FROM courses WHERE courseCode NOT IN (SELECT courseCode FROM student_courses WHERE id=?);";
        Class.forName("com.mysql.jdbc.Driver");

        List<Course> allUenrolledCourses = new ArrayList<>();
        Course retrievedUnenrolledCourse = null;
        CourseDAO courseDAO = new CourseDAO();

        try (
                PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_ALL_UNENROLLMENTS_SQL)) {
            preparedStatement.setInt(1, student.getID());
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                retrievedUnenrolledCourse = courseDAO.get(rs.getString("courseCode"));
                allUenrolledCourses.add(retrievedUnenrolledCourse);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }

        return allUenrolledCourses;


    }

    /**
     * gets all the unenrolled courses this particular student for the semester they chose to enrol in
     *
     * @param student
     * @param selectedSemester
     * @return
     * @throws ClassNotFoundException
     */
    public List<Course> getUnenrolledCoursesForStudentBasedOnSemester(User student, String selectedSemester) throws ClassNotFoundException {
        String SELECT_ALL_UNENROLLMENTS_FOR_SEMESTER_SQL = "SELECT * FROM courses WHERE courseCode NOT IN (SELECT courseCode FROM student_courses WHERE id=?) and semester=?;";

        Class.forName("com.mysql.jdbc.Driver");

        List<Course> allUnenrolledCourses = new ArrayList<>();
        Course retrievedUnenrolledCourse = null;
        CourseDAO courseDAO = new CourseDAO();

        try (
                PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_ALL_UNENROLLMENTS_FOR_SEMESTER_SQL)) {
            preparedStatement.setInt(1, student.getID());
            preparedStatement.setString(2, selectedSemester);
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                retrievedUnenrolledCourse = courseDAO.get(rs.getString("courseCode"));
                allUnenrolledCourses.add(retrievedUnenrolledCourse);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }

        return allUnenrolledCourses;
    }


    public List<Course> getEnrolledCoursesForStudentBasedOnSemester(User student, String semester) throws ClassNotFoundException {
        //find all the courses student is enrolled within a semester
        String SELECT_ENROLLED_COURSES_FOR_SEMESTER_SQL = "SELECT * FROM student_courses WHERE id=? and semester=?";

        Class.forName("com.mysql.jdbc.Driver");

        List<Course> allEnrolledCourses = new ArrayList<>();
        Course retrievedEnrolledCourse = null;
        CourseDAO courseDAO = new CourseDAO();

        try (
                PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_ENROLLED_COURSES_FOR_SEMESTER_SQL)) {
            preparedStatement.setInt(1, student.getID());
            preparedStatement.setString(2, semester);
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                retrievedEnrolledCourse = courseDAO.get(rs.getString("courseCode"));
                allEnrolledCourses.add(retrievedEnrolledCourse);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }

        return allEnrolledCourses;

    }

    public List<User> getAllStudentsForEnrolledCourse(Course course) throws ClassNotFoundException {
        //find all students in a course
        String SELECT_STUDENTS_IN_COURSE_SQL = "SELECT DISTINCT s.id, u.firstName, u.lastName FROM student_courses s JOIN user u ON s.id = u.id WHERE s.courseCode=UPPER(?)";


        Class.forName("com.mysql.jdbc.Driver");

        List<User> allEnrolledStudents = new ArrayList<>();
        User retrievedEnrolledStudent = null;
        UserDAO userDAO = new UserDAO();

        try (
                PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_STUDENTS_IN_COURSE_SQL)) {
            preparedStatement.setString(1, course.getCourseCode());
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                retrievedEnrolledStudent = userDAO.get(rs.getInt("id"));
                allEnrolledStudents.add(retrievedEnrolledStudent);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }

        return allEnrolledStudents;
    }


    @Override
    public Enrollment get(int id) throws ClassNotFoundException {
        return null;
    }

    @Override
    public Enrollment get(String courseCode) throws ClassNotFoundException {
        return null;
    }

    @Override
    //Gets all Enrollments from the db
    public List<Enrollment> getAll() throws ClassNotFoundException {
        return null;
    }

    @Override
    public void update(Enrollment enrollment, String[] params) {
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