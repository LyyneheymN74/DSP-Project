package com.dropshipping.servlet;

import com.dropshipping.dao.OrderDAO;
import com.dropshipping.model.CartItem;
import com.dropshipping.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URL;

import com.dropshipping.vnpay.Config;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "CheckoutCartServlet", urlPatterns = "/order/checkout-cart")
public class CheckoutCartServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        if (address == null || address.trim().isEmpty() || phone == null || phone.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart?error=missing_info");
            return;
        }

        double grandTotal = 0;
        for (CartItem item : cart) grandTotal += item.getTotalPrice();

        // 1. Save Order to DB
        int orderId = orderDAO.createOrderFromCart(user.getId(), grandTotal, cart, address, phone);

        if (orderId > 0) {
            session.removeAttribute("cart");

            // 2. Prepare VNPay Data
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            
            // Use unique Ref: OrderID_Time
            String vnp_TxnRef = orderId + "_" + System.currentTimeMillis();
            
            String vnp_IpAddr = "8.8.8.8";
            String vnp_TmnCode = Config.vnp_TmnCode;
            
            long amount = (long) (grandTotal * 25000 * 100);
            
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            
            // Use Clean Order Info (No Spaces) to prevent hash errors
            vnp_Params.put("vnp_OrderInfo", "Order" + orderId); 
            
            vnp_Params.put("vnp_OrderType", "other");
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddress", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            // 3. Build Hash & Query
            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator<String> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    // Hash: Raw Data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString())); 
                    
                    // Query: Encoded Data
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            
            String queryUrl = query.toString();
            String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            
            String paymentUrl = Config.vnp_Url + "?" + queryUrl;

            System.out.println("Redirecting to: " + paymentUrl);
            
            response.sendRedirect(paymentUrl);
            
        } else {
            response.sendRedirect(request.getContextPath() + "/cart?error=stock");
        }
    }
}