package com.dropshipping.servlet;

import com.dropshipping.dao.ProductDAO;
import com.dropshipping.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductManagementServlet", urlPatterns = "/admin/products")
public class ProductManagementServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Product> productList = productDAO.getAllProducts();
        
        request.setAttribute("productList", productList);
        
        request.getRequestDispatcher("/WEB-INF/views/admin/manage-products.jsp").forward(request, response);
    }
}