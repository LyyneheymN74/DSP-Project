package com.dropshipping.model;

import java.sql.Timestamp;

public class SupplierOrderView {
    private int orderId;
    private String productName;
    private int quantity;
    private String customerName;
    private String status;
    private Timestamp orderDate;
    private String shippingAddress;
    private String customerPhone;
    private int supplierId;
    private String supplierName;
    private double totalPrice;

    public SupplierOrderView() {}

    public SupplierOrderView(int orderId, String productName, int quantity, String customerName, String status, Timestamp orderDate) {
        this.orderId = orderId;
        this.productName = productName;
        this.quantity = quantity;
        this.customerName = customerName;
        this.status = status;
        this.orderDate = orderDate;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}