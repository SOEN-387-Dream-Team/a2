package soen387.DAO;

import soen387.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static soen387.DatabaseConnConstants.CONNECTION;

public class UserDAO implements Dao<User> {
	
    Semaphore sem;


    @Override
    public void create(User user) {

        String INSERT_USERS_SQL = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (
                
        	sem.acquire();
        	PreparedStatement preparedStatement = CONNECTION.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getPhoneNum());
            preparedStatement.setString(7, user.getDOB());
            preparedStatement.setString(8, user.getPass());
            preparedStatement.setBoolean(9, user.getAdminStatus());

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
            sem.release();
        } catch (SQLException e) {
        	sem.release();
            throw new RuntimeException(e);
        }
    }

    @Override
    public User get(int id) throws ClassNotFoundException {
        String SELECT_COURSE_SQL = "SELECT * FROM user" +
                "  WHERE id = ?; ";


        Class.forName("com.mysql.jdbc.Driver");

        User retrievedUser = null;
        try (

            sem.acquire(); 
        	PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_COURSE_SQL)) {

            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            sem.release();
            
            if (rs.next()) {

                int userId = id;
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String dateOfBirth = rs.getString("dateOfBirth");
                String password = rs.getString("password"); //Prob avoid this?
                boolean isAdmin = (rs.getInt("isAdmin") == 1) ? true : false;


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
        	sem.release();
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
        try (

                
        	sem.acquire();
        	PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_COURSE_SQL)) {

            preparedStatement.setString(1, fName);
            preparedStatement.setString(2, lName);
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            sem.release();
            
            if (rs.next()) {

                int userId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String dateOfBirth = rs.getString("dateOfBirth");
                String password = rs.getString("password"); //Prob avoid this?
                boolean isAdmin = (rs.getInt("isAdmin") == 1) ? true : false;


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
        	sem.release();
            printSQLException(e);
        }

        return retrievedUser;
    }

    public boolean isSignInValid(int userId, String typedPassword) throws ClassNotFoundException {
        String SELECT_AUTHENTICATE_USER_SQL = "SELECT * FROM user WHERE id=? AND password=?";

        Class.forName("com.mysql.jdbc.Driver");

        boolean flag = false;
        try (

                
        	sem.acquire();
        	PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_AUTHENTICATE_USER_SQL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, typedPassword);
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            sem.release();

            rs.next(); //move cursor to last row

            if (rs.getRow() > 0) { //get total row count
                flag = true;
            }

        } catch (SQLException e) {
            // process sql exception
        	sem.release();
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
        try (

                
        	sem.acquire();	
        	PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_ALLUSERS_SQL)) {
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            sem.release();
            
            while (rs.next()) {

                int userId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String dateOfBirth = rs.getString("dateOfBirth");
                String password = rs.getString("password");
                boolean isAdmin = (rs.getInt("isAdmin") == 1) ? true : false;

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
        	sem.release();
            printSQLException(e);
        }

        return allUsers;
    }

    public List<User> getAllStudents() throws ClassNotFoundException {
        String SELECT_ALLSTUDENTS_SQL = "SELECT * FROM user WHERE isAdmin = ?;";

        Class.forName("com.mysql.jdbc.Driver");

        List<User> allStudents = new ArrayList<>();
        User retrievedUser = null;
        try (

            sem.acquire();    
        	PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_ALLSTUDENTS_SQL)) {
            preparedStatement.setInt(1, 0); //isAdmin of 0 indicates students
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            sem.release();
            
            while (rs.next()) {

                int userId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String dateOfBirth = rs.getString("dateOfBirth");
                String password = rs.getString("password");
                boolean isAdmin = (rs.getInt("isAdmin") == 1) ? true : false;

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
        	sem.release();
            printSQLException(e);
        }

        return allStudents;
    }

    public List<User> getAllAdmins() throws ClassNotFoundException {
        String SELECT_ALLADMINS_SQL = "SELECT * FROM user WHERE isAdmin = ?;";

        Class.forName("com.mysql.jdbc.Driver");

        List<User> allAdmins = new ArrayList<>();
        User retrievedUser = null;
        try (

            sem.acquire();	
        	PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_ALLADMINS_SQL)) {
            preparedStatement.setInt(1, 1); //isAdmin of 1 indicates admin
            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            sem.release();
            
            while (rs.next()) {

                int userId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String dateOfBirth = rs.getString("dateOfBirth");
                String password = rs.getString("password");
                boolean isAdmin = (rs.getInt("isAdmin") == 1) ? true : false;

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
        	sem.release();
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
        }
    }
}
