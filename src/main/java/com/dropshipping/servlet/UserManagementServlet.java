package com.dropshipping.servlet;

import com.dropshipping.dao.UserDAO;
import com.dropshipping.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserManagementServlet", urlPatterns = "/admin/users")
public class UserManagementServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<User> userList = userDAO.getAllUsers();
        
        request.setAttribute("userList", userList);
        
        request.getRequestDispatcher("/WEB-INF/views/admin/manage-users.jsp").forward(request, response);
    }
}