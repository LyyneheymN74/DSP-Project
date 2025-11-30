package com.dropshipping.servlet;

import com.dropshipping.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ForgotPasswordServlet", urlPatterns = "/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("verify".equals(action)) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");

            if (userDAO.validateUserSecurity(username, email)) {
                request.setAttribute("verifiedUser", username);
                request.setAttribute("step", "reset"); // Tell JSP to show the password box
                request.getRequestDispatcher("/WEB-INF/views/forgot-password.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Username and Email do not match our records.");
                request.getRequestDispatcher("/WEB-INF/views/forgot-password.jsp").forward(request, response);
            }

        } else if ("reset".equals(action)) {
            String username = request.getParameter("username"); 
            String newPass = request.getParameter("newPassword");
            String confirmPass = request.getParameter("confirmPassword");

            if (newPass.equals(confirmPass)) {
                userDAO.updatePassword(username, newPass);
                
                request.setAttribute("successMessage", "Password reset successfully! Please login.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Passwords do not match.");
                request.setAttribute("verifiedUser", username); 
                request.setAttribute("step", "reset");
                request.getRequestDispatcher("/WEB-INF/views/forgot-password.jsp").forward(request, response);
            }
        }
    }
}