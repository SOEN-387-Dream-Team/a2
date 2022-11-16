package login;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import soen387.DAO.UserDAO;
import soen387.User;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID =  1;
    private UserDAO userdao;

    public void init() {
        userdao = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("userId");
        int ID = Integer.parseInt(username);
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        try {
            if (userdao.isSignInValid(ID,password)) {
                User user = userdao.get(ID);
                if (user.getAdminStatus()) { // If user is admin
                    session.setAttribute("id",username);
                    session.setAttribute("isAdmin","true");
                    response.sendRedirect("AdminPage.jsp");
                }
                else { // User is student
                    session.setAttribute("id",username);
                    session.setAttribute("isAdmin","false");
                    response.sendRedirect("StudentPage.jsp");
                }
            }
            else {
                response.sendRedirect("MainPage.jsp");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
