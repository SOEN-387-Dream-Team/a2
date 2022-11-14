package soen387;

import java.util.Optional;

public interface UserDataMapper {

    Optional<User> find(int userID);

    //method used by Admin to see all of a student's enrolled courses
    void findEnrolledClasses(User student) throws DataMapperException;


    void findEnrolledClasses(User student, String semester) throws DataMapperException;

    //method used by Admin to see all students in a course
    void findCourseStudents(Course course) throws DataMapperException;

    //method used by Student when choosing to add an enrolled course
    void findUnenrolledClasses(User student) throws DataMapperException;

    int createUser(User user) throws DataMapperException;

    void registerCourse(User student, Course course) throws DataMapperException;

    void dropCourse(User student, Course course) throws DataMapperException;
}
