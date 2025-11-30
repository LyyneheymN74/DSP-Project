package com.dropshipping.servlet;

import com.dropshipping.dao.OrderDAO;
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

@WebServlet(name = "CustomerOrderServlet", urlPatterns = "/my-orders")
public class CustomerOrderServlet extends HttpServlet {

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

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<SupplierOrderView> myOrders = orderDAO.getOrdersForCustomer(user.getId());
        
        request.setAttribute("orderList", myOrders);
        request.getRequestDispatcher("/WEB-INF/views/my-orders.jsp").forward(request, response);
    }
}