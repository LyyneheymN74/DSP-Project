package com.dropshipping.servlet; 
import com.dropshipping.dao.UserDAO;
import com.dropshipping.model.Role;
import com.dropshipping.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthServlet", urlPatterns = {"/login", "/auth/login"})
public class AuthServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String usernameInput = request.getParameter("username");
        String passwordInput = request.getParameter("password");

        User user = userDAO.checkLogin(usernameInput, passwordInput);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user); 

            switch (user.getRole()) {
                case ADMIN:
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                    break;
                case STAFF:
                    response.sendRedirect(request.getContextPath() + "/staff/dashboard");
                    break;
                case SUPPLIER:
                    response.sendRedirect(request.getContextPath() + "/supplier/dashboard");
                    break;
                case CUSTOMER:
                default:
                    response.sendRedirect(request.getContextPath() + "/home");
                    break;
            }
        } else {
            request.setAttribute("errorMessage", "Invalid username or password.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}