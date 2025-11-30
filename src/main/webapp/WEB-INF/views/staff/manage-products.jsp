<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 

<html>
<head>
    <title>Manage Products</title>
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
            <h1 style="margin: 0;"><i class="fa-solid fa-box-open"></i> Product Management</h1>
            
            <div>
                <a href="${pageContext.request.contextPath}/staff/dashboard" class="btn" style="background-color: #6c757d; color: white; margin-right: 10px;">
                    <i class="fa-solid fa-arrow-left"></i> Back to dashboard
                </a>
                
                <a href="${pageContext.request.contextPath}/staff/product/add" class="btn btn-success">
                    <i class="fa-solid fa-plus"></i> Add New Product
                </a>
            </div>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th>Supplier ID</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${productList}">
                    <tr>
                        <td>${p.id}</td>
                        <td style="font-weight: bold; color: #333;">${p.name}</td>
                        <td style="color: #666; font-style: italic;">
                            ${p.categoryName != null ? p.categoryName : 'Uncategorized'}
                        </td>
                        <td style="color: #28a745; font-weight: bold;">$${p.price}</td>
                        
                        <td style="font-weight: bold;">
                            <c:choose>
                                <c:when test="${p.stock == 0}">
                                    <span style="color: red;">❌ 0</span>
                                </c:when>
                                <c:when test="${p.stock <= 10}">
                                    <span style="color: orange;">⚠️ ${p.stock}</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: green;">${p.stock}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        
                        <td>${p.supplierId}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/staff/product/edit?id=${p.id}" class="btn btn-warning">
                                <i class="fa-solid fa-pen"></i> Edit
                            </a>
                            <a href="${pageContext.request.contextPath}/staff/product/delete?id=${p.id}" 
                               class="btn btn-danger" 
                               onclick="return confirm('Are you sure you want to delete this product?');">
                               <i class="fa-solid fa-trash"></i> Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</body>
</html>