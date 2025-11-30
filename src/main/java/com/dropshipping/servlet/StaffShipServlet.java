package com.dropshipping.servlet;

import com.dropshipping.dao.OrderDAO;
import com.dropshipping.model.Role;
import com.dropshipping.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "StaffShipServlet", urlPatterns = "/staff/order/ship")
public class StaffShipServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (user == null || user.getRole() != Role.STAFF) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idStr = request.getParameter("id");
        String supIdStr = request.getParameter("supplierId");

        if (idStr != null && supIdStr != null) {
            int orderId = Integer.parseInt(idStr);
            int supplierId = Integer.parseInt(supIdStr);
            orderDAO.updateStatus(orderId, supplierId, "SHIPPED");
        }
        
        response.sendRedirect(request.getContextPath() + "/staff/orders?msg=shipped");
    }
}