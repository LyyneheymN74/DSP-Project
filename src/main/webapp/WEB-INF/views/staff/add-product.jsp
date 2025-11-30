<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Add New Product</title>
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
        <h1><i class="fa-solid fa-box-open"></i> Add New Product</h1>
        <hr style="border: 0; border-top: 1px solid #eee; margin-bottom: 20px;">

        <form action="${pageContext.request.contextPath}/staff/product/add" method="post" class="form-container">
            
            <div class="form-group">
                <label class="form-label">Product Name:</label>
                <input type="text" name="name" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label">Category:</label>
                <select name="categoryId" class="form-control" required>
                    <option value="">-- Select Category --</option>
                    <c:forEach var="c" items="${categoryList}">
                        <option value="${c.id}">${c.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label class="form-label">Description:</label>
                <textarea name="description" rows="4" class="form-control"></textarea>
            </div>

            <div style="display: flex; gap: 20px;">
                <div class="form-group" style="flex: 1;">
                    <label class="form-label">Price ($):</label>
                    <input type="number" step="0.01" name="price" class="form-control" required>
                </div>

                <div class="form-group" style="flex: 1;">
                    <label class="form-label">Stock Quantity:</label>
                    <input type="number" name="stock" class="form-control" required>
                </div>
            </div>

            <div class="form-group">
                <label class="form-label">Assign to Supplier:</label>
                <select name="supplierId" class="form-control" required>
                    <option value="">-- Select a Supplier --</option>
                    <c:forEach var="s" items="${supplierList}">
                        <option value="${s.id}">${s.username}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-actions" style="justify-content: center;">
                <button type="submit" class="btn btn-success" style="width: 200px;">Create Product</button>
                <a href="${pageContext.request.contextPath}/staff/products" class="btn btn-danger" style="width: 150px; text-align: center;">Cancel</a>
            </div>
        </form>
    </div>

</body>
</html>