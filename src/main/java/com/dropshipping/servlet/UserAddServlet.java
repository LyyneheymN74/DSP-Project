package com.dropshipping.servlet;

import com.dropshipping.dao.UserDAO;
import com.dropshipping.model.Role;
import com.dropshipping.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserAddServlet", urlPatterns = "/admin/user/add")
public class UserAddServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/add-user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String roleStr = request.getParameter("role");

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(Role.valueOf(roleStr)); 

        boolean success = userDAO.addUser(newUser);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/users");
        } else {
            request.setAttribute("errorMessage", "Could not save user. Username might be taken.");
            request.getRequestDispatcher("/WEB-INF/views/admin/add-user.jsp").forward(request, response);
        }
    }
}