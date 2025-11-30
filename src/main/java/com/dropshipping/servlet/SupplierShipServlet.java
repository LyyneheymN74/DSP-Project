package com.dropshipping.servlet;

import com.dropshipping.dao.OrderDAO;
import com.dropshipping.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "SupplierShipServlet", urlPatterns = "/supplier/order/ship")
public class SupplierShipServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("currentUser");
        String idStr = request.getParameter("id");
        
        if (idStr != null) {
            int orderId = Integer.parseInt(idStr);
            
            orderDAO.updateStatus(orderId, user.getId(), "SHIPPED");
        }
        
        response.sendRedirect(request.getContextPath() + "/supplier/orders?msg=shipped");
    }
}