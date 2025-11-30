<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<html>
<head>
    <title>Manage Orders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

    <div class="main-header">
        <a href="${pageContext.request.contextPath}/staff/dashboard">DSP Staff Panel</a>
        <div>
            <a href="${pageContext.request.contextPath}/staff/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/logout" style="color: #ff6b6b;">Logout</a>
        </div>
    </div>

    <div class="container">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h1 style="margin: 0;"><i class="fa-solid fa-truck-fast"></i> Global Order Fulfillment</h1>
            
            <a href="${pageContext.request.contextPath}/staff/dashboard" class="btn" style="background-color: #6c757d; color: white;">
                <i class="fa-solid fa-arrow-left"></i> Back to Dashboard
            </a>
        </div>

        <c:if test="${param.msg == 'shipped'}">
            <div style="background-color: #d4edda; color: #155724; padding: 15px; margin-bottom: 20px; border-radius: 5px; border: 1px solid #c3e6cb;">
                ✅ Shipment processed successfully!
            </div>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Date</th>
                    <th>Supplier</th>
                    <th>Product</th>
                    <th>Customer</th>
                    <th>Contact</th>
                    <th>Address</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${orderList}">
                    <tr>
                        <td>#${o.orderId}</td>
                        <td style="font-size: 14px; color: #555;">
                            <fmt:formatDate value="${o.orderDate}" pattern="yyyy-MM-dd HH:mm" timeZone="Asia/Ho_Chi_Minh" />
                        </td>
                        <td style="font-weight: bold; color: #333;">${o.supplierName}</td>
                        <td>
                            <c:out value="${o.productName}" escapeXml="false" />
                        </td>
                        <td>${o.customerName}</td>
                        <td>${o.customerPhone}</td>
                        <td style="font-size: 13px;">${o.shippingAddress}</td>
                        
                        <td class="status-${o.status}" style="font-weight: bold;">
                            ${o.status}
                        </td>
                        
                        <td>
                            <c:if test="${o.status == 'PENDING'}">
                                <a href="${pageContext.request.contextPath}/staff/order/ship?id=${o.orderId}&supplierId=${o.supplierId}" 
                                   class="btn btn-primary"
                                   onclick="return confirm('Confirm shipment for Order #${o.orderId} (Supplier: ${o.supplierName})?');">
                                   Mark Shipped
                                </a>
                            </c:if>
                            <c:if test="${o.status == 'SHIPPED'}">
                                <span style="color: green; font-weight: bold;">
                                    <i class="fa-solid fa-check"></i> Processed
                                </span>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</body>
</html>