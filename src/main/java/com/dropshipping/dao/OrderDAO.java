package com.dropshipping.dao;

import com.dropshipping.model.Order;
import com.dropshipping.model.SupplierOrderView;
import com.dropshipping.model.AnalyticsData;
import com.dropshipping.model.CartItem;
import com.dropshipping.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.TimeZone;

public class OrderDAO {

    public int createOrder(Order order, int productId, int quantity) {
        String checkStockSQL = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
        String insertOrderSQL = "INSERT INTO orders (user_id, total_price, status) VALUES (?, ?, ?) RETURNING id";
        String insertItemSQL = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); 

            try (PreparedStatement stmt = conn.prepareStatement(checkStockSQL)) {
                stmt.setInt(1, quantity); 
                stmt.setInt(2, productId); 
                stmt.setInt(3, quantity); 
                
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated == 0) {
                    conn.rollback();
                    return -2; 
                }
            }

            int orderId = 0;
            try (PreparedStatement stmt = conn.prepareStatement(insertOrderSQL)) {
                stmt.setInt(1, order.getUserId());
                stmt.setDouble(2, order.getTotalPrice());
                stmt.setString(3, order.getStatus());
                
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }

            if (orderId > 0) {
                try (PreparedStatement stmt = conn.prepareStatement(insertItemSQL)) {
                    stmt.setInt(1, orderId);
                    stmt.setInt(2, productId);
                    stmt.setInt(3, quantity);
                    stmt.setDouble(4, order.getTotalPrice());
                    stmt.executeUpdate();
                }
            }

            conn.commit();
            return orderId;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return -1; 
        }
    }

    public List<SupplierOrderView> getOrdersForSupplier(int supplierId) {
        List<SupplierOrderView> list = new ArrayList<>();
        
        String sql = "SELECT o.id as order_id, " +
                     "STRING_AGG(p.name || ' (x' || oi.quantity || ')', '<br>') as product_summary, " +
                     "u.username as customer_name, MIN(oi.status) as status, o.created_at, " +
                     "o.shipping_address, o.customer_phone " +
                     "FROM order_items oi " +
                     "JOIN orders o ON oi.order_id = o.id " +
                     "JOIN products p ON oi.product_id = p.id " +
                     "JOIN users u ON o.user_id = u.id " +
                     "WHERE p.supplier_id = ? " +
                     "GROUP BY o.id, u.username, o.shipping_address, o.customer_phone, o.status, o.created_at " +
                     "ORDER BY o.created_at DESC";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SupplierOrderView view = new SupplierOrderView();
                view.setOrderId(rs.getInt("order_id"));
                view.setProductName(rs.getString("product_summary"));
                view.setQuantity(0);
                view.setCustomerName(rs.getString("customer_name"));
                view.setCustomerPhone(rs.getString("customer_phone"));
                view.setStatus(rs.getString("status"));
                Calendar utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                view.setOrderDate(rs.getTimestamp("created_at", utcCal));
                view.setShippingAddress(rs.getString("shipping_address"));
                list.add(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatus(int orderId, int supplierId, String newStatus) {
        String sql = "UPDATE order_items SET status = ? " +
                     "FROM products " +
                     "WHERE order_items.product_id = products.id " +
                     "AND order_items.order_id = ? " +
                     "AND products.supplier_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            stmt.setInt(3, supplierId);
            
            return stmt.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SupplierOrderView> getOrdersForCustomer(int userId) {
        List<SupplierOrderView> list = new ArrayList<>();
        
        String sql = "SELECT o.id as order_id, " +
                     "STRING_AGG(p.name || ' (x' || oi.quantity || ')', '<br>') as product_summary, " +
                     "u.username as customer_name, o.status, o.created_at, o.total_price, " +
                     "o.shipping_address, o.customer_phone " +
                     "FROM order_items oi " +
                     "JOIN orders o ON oi.order_id = o.id " +
                     "JOIN products p ON oi.product_id = p.id " +
                     "JOIN users u ON o.user_id = u.id " +
                     "WHERE o.user_id = ? " +
                     "GROUP BY o.id, u.username, o.shipping_address, o.customer_phone, o.status, o.created_at " +
                     "ORDER BY o.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SupplierOrderView view = new SupplierOrderView();
                view.setOrderId(rs.getInt("order_id"));
                view.setProductName(rs.getString("product_summary"));
                view.setStatus(rs.getString("status"));
                Calendar utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                view.setOrderDate(rs.getTimestamp("created_at", utcCal));
                view.setShippingAddress(rs.getString("shipping_address"));
                view.setCustomerPhone(rs.getString("customer_phone"));
                view.setTotalPrice(rs.getDouble("total_price"));
                list.add(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<SupplierOrderView> getAllOrders() {
        List<SupplierOrderView> list = new ArrayList<>();
        
        String sql = "SELECT o.id as order_id, " +
                     "s.id as supplier_id, s.username as supplier_name, " + 
                     "STRING_AGG(p.name || ' (x' || oi.quantity || ')', '<br>') as product_summary, " +
                     "u.username as customer_name, " +
                     "MIN(oi.status) as status, " + 
                     "o.created_at, o.shipping_address, o.customer_phone " +
                     "FROM order_items oi " +
                     "JOIN orders o ON oi.order_id = o.id " +
                     "JOIN products p ON oi.product_id = p.id " +
                     "JOIN users u ON o.user_id = u.id " +
                     "JOIN users s ON p.supplier_id = s.id " + 
                     "GROUP BY o.id, s.id, s.username, u.username, o.shipping_address, o.customer_phone, o.created_at " +
                     "ORDER BY o.created_at DESC, s.username";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SupplierOrderView view = new SupplierOrderView();
                view.setOrderId(rs.getInt("order_id"));
                view.setProductName(rs.getString("product_summary"));
                view.setCustomerName(rs.getString("customer_name"));
                view.setCustomerPhone(rs.getString("customer_phone"));
                view.setStatus(rs.getString("status"));
                Calendar utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                view.setOrderDate(rs.getTimestamp("created_at", utcCal));
                view.setShippingAddress(rs.getString("shipping_address"));
                view.setSupplierId(rs.getInt("supplier_id"));
                view.setSupplierName(rs.getString("supplier_name"));
                list.add(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int createOrderFromCart(int userId, double grandTotal, List<CartItem> cart, String address, String phone) {
        String checkStockSQL = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
        String insertOrderSQL = "INSERT INTO orders (user_id, total_price, status, shipping_address, customer_phone) VALUES (?, ?, 'PENDING', ?, ?) RETURNING id";
        String insertItemSQL = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); 

            int orderId = 0;
            try (PreparedStatement stmt = conn.prepareStatement(insertOrderSQL)) {
                stmt.setInt(1, userId);
                stmt.setDouble(2, grandTotal);
                stmt.setString(3, address);
                stmt.setString(4, phone);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) orderId = rs.getInt(1);
            }

            if (orderId == 0) throw new SQLException("Failed to create order");

            try (PreparedStatement stockStmt = conn.prepareStatement(checkStockSQL);
                PreparedStatement itemStmt = conn.prepareStatement(insertItemSQL)) {
                
                for (CartItem item : cart) {
                    stockStmt.setInt(1, item.getQuantity());
                    stockStmt.setInt(2, item.getProduct().getId());
                    stockStmt.setInt(3, item.getQuantity());
                    
                    int rows = stockStmt.executeUpdate();
                    if (rows == 0) {
                        throw new SQLException("Out of stock for: " + item.getProduct().getName());
                    }

                    itemStmt.setInt(1, orderId);
                    itemStmt.setInt(2, item.getProduct().getId());
                    itemStmt.setInt(3, item.getQuantity());
                    itemStmt.setDouble(4, item.getProduct().getPrice());
                    itemStmt.executeUpdate();
                }
            }

            conn.commit(); 
            return orderId;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return -1;
        }
    }

    public boolean updateAllItemsStatus(int orderId, String newStatus) {
        String sql = "UPDATE order_items SET status = ? WHERE order_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            
            return stmt.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public AnalyticsData getAnalytics() {
        AnalyticsData data = new AnalyticsData();
        
        String sqlRevenue = "SELECT SUM(total_price) FROM orders";
        String sqlOrders = "SELECT COUNT(id) FROM orders";
        String sqlItems = "SELECT SUM(quantity) FROM order_items";

        try (Connection conn = DBConnection.getConnection()) {
            
            try (PreparedStatement stmt = conn.prepareStatement(sqlRevenue);
                ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) data.setTotalRevenue(rs.getDouble(1));
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlOrders);
                ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) data.setTotalOrders(rs.getInt(1));
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlItems);
                ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) data.setTotalProductsSold(rs.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public AnalyticsData getSupplierAnalytics(int supplierId) {
        AnalyticsData data = new AnalyticsData();
        
        String sqlRevenue = "SELECT SUM(oi.price * oi.quantity) " +
                            "FROM order_items oi " +
                            "JOIN products p ON oi.product_id = p.id " +
                            "WHERE p.supplier_id = ?";
        
        String sqlOrders = "SELECT COUNT(DISTINCT oi.order_id) " +
                        "FROM order_items oi " +
                        "JOIN products p ON oi.product_id = p.id " +
                        "WHERE p.supplier_id = ?";
        
        String sqlItems = "SELECT SUM(oi.quantity) " +
                        "FROM order_items oi " +
                        "JOIN products p ON oi.product_id = p.id " +
                        "WHERE p.supplier_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            
            try (PreparedStatement stmt = conn.prepareStatement(sqlRevenue)) {
                stmt.setInt(1, supplierId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) data.setTotalRevenue(rs.getDouble(1));
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlOrders)) {
                stmt.setInt(1, supplierId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) data.setTotalOrders(rs.getInt(1));
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlItems)) {
                stmt.setInt(1, supplierId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) data.setTotalProductsSold(rs.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}