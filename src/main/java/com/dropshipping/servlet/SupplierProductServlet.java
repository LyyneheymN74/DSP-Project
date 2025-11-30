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
import java.util.List;

@WebServlet(name = "SupplierProductServlet", urlPatterns = "/supplier/products")
public class SupplierProductServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("currentUser");

        if (user == null || !"SUPPLIER".equals(user.getRole().name())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Product> myProducts = productDAO.getProductsBySupplier(user.getId());
        
        request.setAttribute("productList", myProducts);
        request.getRequestDispatcher("/WEB-INF/views/supplier/my-products.jsp").forward(request, response);
    }
}