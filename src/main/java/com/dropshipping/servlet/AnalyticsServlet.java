package com.dropshipping.servlet;

import com.dropshipping.dao.OrderDAO;
import com.dropshipping.model.AnalyticsData;
import com.dropshipping.model.Role;
import com.dropshipping.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AnalyticsServlet", urlPatterns = "/admin/analytics")
public class AnalyticsServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Security: Admin Only
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (user == null || user.getRole() != Role.ADMIN) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get Data
        AnalyticsData data = orderDAO.getAnalytics();
        request.setAttribute("stats", data);
        
        request.getRequestDispatcher("/WEB-INF/views/admin/analytics.jsp").forward(request, response);
    }
}