package com.dropshipping.model;

public class AnalyticsData {
    private double totalRevenue;
    private int totalOrders;
    private int totalProductsSold;

    // Getters and Setters
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }

    public int getTotalOrders() { return totalOrders; }
    public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }

    public int getTotalProductsSold() { return totalProductsSold; }
    public void setTotalProductsSold(int totalProductsSold) { this.totalProductsSold = totalProductsSold; }
}