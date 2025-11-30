package com.dropshipping.servlet;

import com.dropshipping.dao.ProductDAO;
import com.dropshipping.model.Role;
import com.dropshipping.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "StaffProductServlet", urlPatterns = "/staff/products")
public class StaffProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (user == null || user.getRole() != Role.STAFF) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("productList", productDAO.getAllProducts());
        request.getRequestDispatcher("/WEB-INF/views/staff/manage-products.jsp").forward(request, response);
    }
}