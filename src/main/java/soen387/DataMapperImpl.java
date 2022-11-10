package soen387;

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

    }

    @Override
    public void findEnrolledClasses(User student, String semester) throws DataMapperException {

    }

    @Override
    public void findCourseStudents(Course course) throws DataMapperException {

    }

    @Override
    public void isAdmin(User user) throws DataMapperException {

    }

    @Override
    public void findUnenrolledClasses(User student) throws DataMapperException {

    }

    @Override
    public void createUser(User user) throws DataMapperException {

    }
}
