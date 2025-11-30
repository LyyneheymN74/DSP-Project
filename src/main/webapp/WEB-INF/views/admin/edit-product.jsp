<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Edit Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

    <div class="main-header">
        <a href="${pageContext.request.contextPath}/admin/dashboard">DSP Admin Panel</a>
        <div>
            <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/logout" style="color: #ff6b6b;">Logout</a>
        </div>
    </div>

    <div class="container">
        <h1><i class="fa-solid fa-pen-to-square"></i> Edit Product</h1>
        <hr style="border: 0; border-top: 1px solid #eee; margin-bottom: 20px;">
        
        <form action="${pageContext.request.contextPath}/admin/product/edit" method="post" class="form-container">
            <input type="hidden" name="id" value="${product.id}">

            <div class="form-group">
                <label class="form-label">Product Name:</label>
                <input type="text" name="name" value="${product.name}" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label">Category:</label>
                <select name="categoryId" class="form-control" required>
                    <option value="">-- Select Category --</option>
                    <c:forEach var="c" items="${categoryList}">
                        <option value="${c.id}" <c:if test="${c.id == product.categoryId}">selected</c:if>>
                            ${c.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label class="form-label">Description:</label>
                <textarea name="description" rows="4" class="form-control">${product.description}</textarea>
            </div>

            <div style="display: flex; gap: 20px;">
                <div class="form-group" style="flex: 1;">
                    <label class="form-label">Price ($):</label>
                    <input type="number" step="0.01" name="price" value="${product.price}" class="form-control" required>
                </div>

                <div class="form-group" style="flex: 1;">
                    <label class="form-label">Stock Quantity:</label>
                    <input type="number" name="stock" value="${product.stock}" class="form-control" required>
                </div>
            </div>

            <div class="form-group">
                <label class="form-label">Supplier:</label>
                <select name="supplierId" class="form-control" required>
                    <option value="0">-- No Supplier --</option>
                    <c:forEach var="s" items="${supplierList}">
                        <option value="${s.id}" <c:if test="${s.id == product.supplierId}">selected</c:if>>
                            ${s.username}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-actions" style="justify-content: center;">
                <button type="submit" class="btn btn-primary" style="width: 200px;">Save Changes</button>
                <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-danger" style="width: 150px; text-align: center;">Cancel</a>
            </div>
        </form>
    </div>

</body>
</html>