<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<html>
<head>
    <title>Add Product</title>
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

    <div class="dashboard-container" style="max-width: 700px;">
        
        <a href="${pageContext.request.contextPath}/supplier/products" style="text-decoration: none; color: #6b7280; font-size: 14px; display: inline-block; margin-bottom: 15px;">
            <i class="fa-solid fa-arrow-left"></i> Back to Inventory
        </a>

        <div class="content-card">
            <div class="page-header" style="border-bottom: 1px solid #e5e7eb; padding-bottom: 20px; margin-bottom: 30px;">
                <div class="page-title">Edit Product</div>
            </div>

            <form action="${pageContext.request.contextPath}/supplier/product/edit" method="post">
                
                <div class="form-group">
                    <label class="form-label">Product Name</label>
                    <input type="text" name="name" class="form-control" required>
                </div>

                <div class="form-group">
                    <label class="form-label">Category</label>
                    <select name="categoryId" class="form-control" required>
                        <option value="">-- Select Category --</option>
                        <c:forEach var="c" items="${categoryList}">
                            <option value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label class="form-label">Description</label>
                    <textarea name="description" rows="4" class="form-control"></textarea>
                </div>

                <div style="display: flex; gap: 20px;">
                    <div class="form-group" style="flex: 1;">
                        <label class="form-label">Price ($)</label>
                        <input type="number" step="0.01" name="price" class="form-control" required>
                    </div>

                    <div class="form-group" style="flex: 1;">
                        <label class="form-label">Initial Stock</label>
                        <input type="number" name="stock" class="form-control" required>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary" style="flex: 2; justify-content: center;">
                        <i class="fa-solid fa-plus"></i> Save
                    </button>
                    <a href="${pageContext.request.contextPath}/supplier/products" class="btn btn-secondary" style="flex: 1; justify-content: center;">Cancel</a>
                </div>

            </form>
        </div>
    </div>

</body>
</html>