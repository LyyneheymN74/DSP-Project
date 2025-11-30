<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>My Inventory</title>
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
                    <i class="fa-solid fa-boxes-stacked" style="color: #4338ca;"></i> My Inventory
                </div>
                <a href="${pageContext.request.contextPath}/supplier/product/add" class="btn btn-primary">
                    <i class="fa-solid fa-plus"></i> Add Product
                </a>
            </div>

            <table class="supplier-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Stock</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${productList}">
                        <tr>
                            <td>#${p.id}</td>
                            <td style="font-weight: 600;">${p.name}</td>
                            <td>
                                <span style="background: #eef2ff; color: #4338ca; padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: 600;">
                                    ${p.categoryName != null ? p.categoryName : 'Uncategorized'}
                                </span>
                            </td>
                            <td>$${p.price}</td>
                            
                            <td style="font-weight: bold;">
                                <c:choose>
                                    <c:when test="${p.stock == 0}"><span style="color: #ef4444;">❌ Out of Stock</span></c:when>
                                    <c:when test="${p.stock <= 10}"><span style="color: #f59e0b;">⚠️ ${p.stock} (Low)</span></c:when>
                                    <c:otherwise><span style="color: #10b981;">${p.stock}</span></c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <a href="${pageContext.request.contextPath}/supplier/product/edit?id=${p.id}" class="btn btn-secondary" style="padding: 6px 12px; font-size: 13px;">
                                    <i class="fa-solid fa-pen"></i> Edit
                                </a>
                                <a href="${pageContext.request.contextPath}/supplier/product/delete?id=${p.id}" 
                                   class="btn btn-danger" 
                                   style="padding: 6px 12px; font-size: 13px;"
                                   onclick="return confirm('Delete this product?');">
                                   <i class="fa-solid fa-trash"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</body>
</html>