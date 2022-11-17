import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

import bean.LoginBean;

public class LoginDao extends Thread{

    Semaphore sem;

    public int getUserStatus(LoginBean loginBean) throws ClassNotFoundException {
        int status = 2;

        Class.forName("com.mysql.cj.jdbc.Driver");
        
        

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soen387_school", "root", "synystergates");

             // Step 2:Create a statement using connection object
        	sem.acquire(); 
        	PreparedStatement preparedStatement = connection
                     .prepareStatement("select * from user where id = ? and password = ? ")) {
            preparedStatement.setString(1, loginBean.getUsername());
            preparedStatement.setString(2, loginBean.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            sem.release();
            if (rs.next()) {
                status = rs.getInt("isAdmin");
            }
            
            

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
            sem.release();
        }
        return status;
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