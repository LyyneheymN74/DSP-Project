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

@WebServlet(name = "StaffOrderServlet", urlPatterns = "/staff/orders")
public class StaffOrderServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (user == null || user.getRole() != Role.STAFF) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("orderList", orderDAO.getAllOrders());
        request.getRequestDispatcher("/WEB-INF/views/staff/manage-orders.jsp").forward(request, response);
    }
}