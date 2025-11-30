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

import com.dropshipping.dao.CategoryDAO;

@WebServlet(name = "SupplierProductAddServlet", urlPatterns = "/supplier/product/add")
public class SupplierProductAddServlet extends HttpServlet {

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("DEBUG: Supplier Add Page - Categories found: " + categoryDAO.getAllCategories().size());
        request.setAttribute("categoryList", categoryDAO.getAllCategories());
        request.getRequestDispatcher("/WEB-INF/views/supplier/add-product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("currentUser");

        String name = request.getParameter("name");
        String desc = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        Product p = new Product();
        p.setName(name);
        p.setDescription(desc);
        p.setPrice(price);
        p.setStock(stock);
        p.setSupplierId(user.getId());
        p.setCategoryId(categoryId); 

        productDAO.addProduct(p);

        response.sendRedirect(request.getContextPath() + "/supplier/products");
    }
}