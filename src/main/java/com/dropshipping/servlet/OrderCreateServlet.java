package com.dropshipping.servlet;

import com.dropshipping.dao.OrderDAO;
import com.dropshipping.dao.ProductDAO;
import com.dropshipping.model.Order;
import com.dropshipping.model.Product;
import com.dropshipping.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "OrderCreateServlet", urlPatterns = "/order/create")
public class OrderCreateServlet extends HttpServlet {

    private ProductDAO productDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idStr = request.getParameter("productId");
        String qtyStr = request.getParameter("quantity"); 
        
        if (idStr != null) {
            int productId = Integer.parseInt(idStr);
            int quantity = (qtyStr != null) ? Integer.parseInt(qtyStr) : 1; 

            Product product = productDAO.getProductById(productId);
            
            double totalPrice = product.getPrice() * quantity;
            
            request.setAttribute("product", product);
            request.setAttribute("quantity", quantity);       
            request.setAttribute("totalPrice", totalPrice);   
            
            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("currentUser");

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity")); 
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice")); 
        
        Order order = new Order();
        order.setUserId(user.getId());
        order.setTotalPrice(totalPrice);
        order.setStatus("PENDING");

        int orderId = orderDAO.createOrder(order, productId, quantity);

        if (orderId > 0) {
            response.sendRedirect(request.getContextPath() + "/order/success");
        } else if (orderId == -2) {
            response.sendRedirect(request.getContextPath() + "/home?error=out_of_stock");
        } else {
            response.sendRedirect(request.getContextPath() + "/home?error=true");
        }
    }
}