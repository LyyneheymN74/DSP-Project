package com.dropshipping.servlet;

import com.dropshipping.dao.ProductDAO;
import com.dropshipping.model.Product;
import com.dropshipping.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SupplierProductDeleteServlet", urlPatterns = "/supplier/product/delete")
public class SupplierProductDeleteServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (user == null || !"SUPPLIER".equals(user.getRole().name())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int productId = Integer.parseInt(idStr);
                
                Product product = productDAO.getProductById(productId);
                
                if (product != null && product.getSupplierId() == user.getId()) {
                    productDAO.deleteProduct(productId);
                } else {
                    System.out.println("Security Alert: Supplier " + user.getUsername() + " tried to delete unauthorized product " + productId);
                }
                
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/supplier/products");
    }
}