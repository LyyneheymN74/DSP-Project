<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<html>
<head>
    <title>Incoming Orders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/supplier.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

    <div class="supplier-header">
        <a href="${pageContext.request.contextPath}/supplier/dashboard" class="brand">
            <i class="fa-solid fa-parachute-box"></i> DSP Supplier Hub
        </a>
        <div class="user-info">
            <span>${currentUser.username}</span>
            <form action="${pageContext.request.contextPath}/logout" method="post" style="margin:0;">
                <button type="submit" class="logout-btn">Sign Out</button>
            </form>
        </div>
    </div>

    <div class="dashboard-container">
        
        <a href="${pageContext.request.contextPath}/supplier/dashboard" style="text-decoration: none; color: #6b7280; font-size: 14px; display: inline-block; margin-bottom: 15px;">
            <i class="fa-solid fa-arrow-left"></i> Back to Dashboard
        </a>

        <div class="content-card">
            <div class="page-header">
                <div class="page-title">
                    <i class="fa-solid fa-dolly" style="color: #4338ca;"></i> Incoming Orders
                </div>
            </div>

            <c:if test="${param.msg == 'shipped'}">
                <div style="background-color: #d1fae5; color: #065f46; padding: 15px; margin-bottom: 20px; border-radius: 8px; border: 1px solid #a7f3d0;">
                    ✅ Order marked as SHIPPED successfully!
                </div>
            </c:if>

            <c:if test="${empty orderList}">
                <p style="text-align: center; color: #6b7280; padding: 20px;">No pending orders found.</p>
            </c:if>

            <c:if test="${not empty orderList}">
                <table class="supplier-table">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Date</th>
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
                                <td style="color: #6b7280; font-size: 13px;">
                                    <fmt:formatDate value="${o.orderDate}" pattern="yyyy-MM-dd HH:mm" timeZone="Asia/Ho_Chi_Minh" />
                                </td>
                                <td>
                                    <c:out value="${o.productName}" escapeXml="false" />
                                </td>
                                <td>${o.customerName}</td>
                                <td>${o.customerPhone}</td>
                                <td style="font-size: 13px; max-width: 200px;">${o.shippingAddress}</td>
                                
                                <td style="font-weight: bold;">
                                    <c:choose>
                                        <c:when test="${o.status == 'PENDING'}">
                                            <span style="color: #d97706;">PENDING</span>
                                        </c:when>
                                        <c:when test="${o.status == 'SHIPPED'}">
                                            <span style="color: #059669;">SHIPPED</span>
                                        </c:when>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:if test="${o.status == 'PENDING'}">
                                        <a href="${pageContext.request.contextPath}/supplier/order/ship?id=${o.orderId}" 
                                           class="btn btn-primary"
                                           onclick="return confirm('Mark Order #${o.orderId} as Shipped?');">
                                           Mark Shipped
                                        </a>
                                    </c:if>
                                    <c:if test="${o.status == 'SHIPPED'}">
                                        <span style="color: #059669; font-weight: 600;">
                                            <i class="fa-solid fa-check"></i> Completed
                                        </span>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>

</body>
</html>