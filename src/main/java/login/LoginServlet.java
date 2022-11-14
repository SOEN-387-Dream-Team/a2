package login;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import bean.LoginBean;
import dao.LoginDao;

import static java.lang.System.out;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID =  1;
    private LoginDao loginDao;

    public void init() {
        loginDao = new LoginDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("userId");
        String password = request.getParameter("password");

        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(username);
        loginBean.setPassword(password);
        HttpSession session = request.getSession();
        try {
            if (loginDao.getUserStatus(loginBean) == 0) {
                session.setAttribute("id",username);
                session.setAttribute("isAdmin","false");
                response.sendRedirect("StudentPage.jsp");
            }
            else if (loginDao.getUserStatus(loginBean) == 1) {
                session.setAttribute("id",username);
                session.setAttribute("isAdmin","true");
                response.sendRedirect("AdminPage.jsp");
            }
            else {
                response.sendRedirect("MainPage.jsp");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
