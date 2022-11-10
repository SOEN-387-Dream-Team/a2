package soen387;

import java.util.Optional;

public interface UserDataMapper {

    Optional<User> find(int userID);

    void findEnrolledClasses(User student) throws DataMapperException;
    void findEnrolledClasses(User student, String semester) throws DataMapperException;
    void findCourseStudents(Course course) throws DataMapperException;
    void isAdmin(User user) throws DataMapperException;
    void findUnenrolledClasses(User student) throws DataMapperException;
    void createUser(User user) throws DataMapperException;
}
