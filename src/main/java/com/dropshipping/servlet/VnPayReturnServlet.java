package com.dropshipping.servlet;

import com.dropshipping.vnpay.Config;
import com.dropshipping.dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "VnPayReturnServlet", urlPatterns = "/vnpay-return")
public class VnPayReturnServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        
        String signValue = Config.hmacSHA512(Config.vnp_HashSecret, com.dropshipping.vnpay.Config.getIpAddress(request)); // Placeholder logic
        
        String transactionStatus = request.getParameter("vnp_ResponseCode");
        String orderIdStr = request.getParameter("vnp_TxnRef");

        if ("00".equals(transactionStatus)) {
            if (orderIdStr != null) {
                try {
                    String realOrderIdStr = orderIdStr.split("_")[0]; 
                    int orderId = Integer.parseInt(realOrderIdStr);
                    
                    orderDAO.updateAllItemsStatus(orderId, "PAID"); 
                    
                    request.setAttribute("message", "Payment Successful! Order #" + realOrderIdStr + " is confirmed.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            request.getRequestDispatcher("/WEB-INF/views/order-success.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "Payment Failed or Canceled.");
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
        }
    }
}