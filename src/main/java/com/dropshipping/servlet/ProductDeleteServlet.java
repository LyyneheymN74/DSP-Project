package com.dropshipping.servlet;

import com.dropshipping.dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProductDeleteServlet", urlPatterns = "/admin/product/delete")
public class ProductDeleteServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("id");
        
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                productDAO.deleteProduct(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
}