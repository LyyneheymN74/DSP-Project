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

@WebServlet(name = "SupplierAnalyticsServlet", urlPatterns = "/supplier/analytics")
public class SupplierAnalyticsServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        // Security: Supplier Only
        if (user == null || user.getRole() != Role.SUPPLIER) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get Specific Data for THIS Supplier
        AnalyticsData data = orderDAO.getSupplierAnalytics(user.getId());
        request.setAttribute("stats", data);
        
        // We can reuse the same JSP if we make the back-link dynamic, 
        // OR just copy it to /supplier/analytics.jsp to be safe.
        request.getRequestDispatcher("/WEB-INF/views/supplier/analytics.jsp").forward(request, response);
    }
}