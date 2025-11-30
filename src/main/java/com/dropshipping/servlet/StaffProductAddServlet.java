package com.dropshipping.servlet;

import com.dropshipping.dao.ProductDAO;
import com.dropshipping.dao.UserDAO;
import com.dropshipping.dao.CategoryDAO;
import com.dropshipping.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "StaffProductAddServlet", urlPatterns = "/staff/product/add")
public class StaffProductAddServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private UserDAO userDAO = new UserDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("supplierList", userDAO.getSuppliers());
        request.setAttribute("categoryList", categoryDAO.getAllCategories());
        request.getRequestDispatcher("/WEB-INF/views/staff/add-product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String desc = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int supplierId = Integer.parseInt(request.getParameter("supplierId"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        Product p = new Product();
        p.setName(name);
        p.setDescription(desc);
        p.setPrice(price);
        p.setStock(stock);
        p.setSupplierId(supplierId);
        p.setCategoryId(categoryId);

        productDAO.addProduct(p);
        response.sendRedirect(request.getContextPath() + "/staff/products");
    }
}