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

@WebServlet(name = "ProductEditServlet", urlPatterns = "/admin/product/edit")
public class ProductEditServlet extends HttpServlet {

    private ProductDAO productDAO;
    private UserDAO userDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        userDAO = new UserDAO();
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            
            request.setAttribute("product", productDAO.getProductById(id));
            request.setAttribute("supplierList", userDAO.getSuppliers());
            request.setAttribute("categoryList", categoryDAO.getAllCategories());
            
            request.getRequestDispatcher("/WEB-INF/views/admin/edit-product.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String desc = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int supplierId = Integer.parseInt(request.getParameter("supplierId"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setDescription(desc);
        p.setPrice(price);
        p.setStock(stock);
        p.setSupplierId(supplierId);
        p.setCategoryId(categoryId);

        productDAO.updateProduct(p);

        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
}   