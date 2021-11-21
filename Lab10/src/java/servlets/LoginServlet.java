package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        User user = as.login(email, password);
        session.setAttribute("message", "");
        if (user == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            session.setAttribute("message", "Error logging in. Please try again.");
            return;
        }
        

        session.setAttribute("email", email);
        
        if (user.getRole().getRoleId() == 1) {
            
            session.setAttribute("isadmin", "isadmin");
            response.sendRedirect("admin");
        } else {
            response.sendRedirect("notes");
        }
    }
}
