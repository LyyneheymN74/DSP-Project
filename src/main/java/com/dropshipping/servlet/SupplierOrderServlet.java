package com.dropshipping.servlet;

import com.dropshipping.dao.OrderDAO;
import com.dropshipping.model.Role;
import com.dropshipping.model.SupplierOrderView;
import com.dropshipping.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SupplierOrderServlet", urlPatterns = "/supplier/orders")
public class SupplierOrderServlet extends HttpServlet {

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

        if (user == null || user.getRole() != Role.SUPPLIER) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<SupplierOrderView> myOrders = orderDAO.getOrdersForSupplier(user.getId());
        
        request.setAttribute("orderList", myOrders);
        request.getRequestDispatcher("/WEB-INF/views/supplier/manage-orders.jsp").forward(request, response);
    }
}