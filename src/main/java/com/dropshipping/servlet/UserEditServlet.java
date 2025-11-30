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

@WebServlet(name = "UserEditServlet", urlPatterns = "/admin/user/edit")
public class UserEditServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            User user = userDAO.getUserById(id);
            
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/admin/edit-user.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/users");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String roleStr = request.getParameter("role");

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.valueOf(roleStr));

        userDAO.updateUser(user);

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}