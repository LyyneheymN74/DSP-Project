<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Edit User</title>
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
        <h1><i class="fa-solid fa-user-pen"></i> Edit User</h1>
        <hr style="border: 0; border-top: 1px solid #eee; margin-bottom: 20px;">

        <form action="${pageContext.request.contextPath}/admin/user/edit" method="post" class="form-container">
            <input type="hidden" name="id" value="${user.id}">

            <div class="form-group">
                <label class="form-label">Username:</label>
                <input type="text" name="username" value="${user.username}" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label">Email Address:</label>
                <input type="email" name="email" value="${user.email}" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label">Phone Number:</label>
                <input type="tel" name="phone" value="${user.phone}" class="form-control" placeholder="090..." required>
            </div>

            <div class="form-group">
                <label class="form-label">Password:</label>
                <input type="text" name="password" value="${user.password}" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="form-label">Role:</label>
                <select name="role" class="form-control">
                    <option value="CUSTOMER" <c:if test="${user.role == 'CUSTOMER'}">selected</c:if>>Customer</option>
                    <option value="SUPPLIER" <c:if test="${user.role == 'SUPPLIER'}">selected</c:if>>Supplier</option>
                    <option value="STAFF" <c:if test="${user.role == 'STAFF'}">selected</c:if>>Staff</option>
                    <option value="ADMIN" <c:if test="${user.role == 'ADMIN'}">selected</c:if>>Admin</option>
                </select>
            </div>
            
            <div class="form-actions" style="justify-content: center;">
                <button type="submit" class="btn btn-primary" style="width: 200px;">Save Changes</button>
                <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-danger" style="width: 150px; text-align: center;">Cancel</a>
            </div>
        </form>
    </div>

</body>
</html>