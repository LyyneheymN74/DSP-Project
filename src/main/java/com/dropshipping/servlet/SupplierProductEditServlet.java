package com.dropshipping.servlet;

import com.dropshipping.dao.ProductDAO;
import com.dropshipping.dao.CategoryDAO;
import com.dropshipping.model.Product;
import com.dropshipping.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet(name = "SupplierProductEditServlet", urlPatterns = "/supplier/product/edit")
public class SupplierProductEditServlet extends HttpServlet {

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
        
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        if (user == null || !"SUPPLIER".equals(user.getRole().name())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr != null) {
            int productId = Integer.parseInt(idStr);
            Product product = productDAO.getProductById(productId);
            
            if (product != null && product.getSupplierId() == user.getId()) {
                request.setAttribute("product", product);
                request.setAttribute("categoryList", categoryDAO.getAllCategories());
                request.getRequestDispatcher("/WEB-INF/views/supplier/edit-product.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/supplier/products?error=unauthorized");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("currentUser");

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String desc = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setDescription(desc);
        p.setPrice(price);
        p.setStock(stock);
        p.setSupplierId(user.getId()); 
        p.setCategoryId(categoryId);

        productDAO.updateProduct(p);

        response.sendRedirect(request.getContextPath() + "/supplier/products");
    }
}