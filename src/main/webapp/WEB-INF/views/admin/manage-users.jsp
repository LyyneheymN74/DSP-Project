<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 

<html>
<head>
    <title>Manage Users</title>
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
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h1><i class="fa-solid fa-users"></i> User Management</h1>
            <a href="${pageContext.request.contextPath}/admin/user/add" class="btn btn-primary">
                <i class="fa-solid fa-plus"></i> Add New User
            </a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.id}</td>
                        <td style="font-weight: bold;">${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.phone != null ? user.phone : '-'}</td>
                        <td>
                            <span style="padding: 4px 8px; border-radius: 4px; background-color: #e9ecef; font-size: 12px; font-weight: bold;">
                                ${user.role}
                            </span>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/user/edit?id=${user.id}" class="btn btn-warning">
                                <i class="fa-solid fa-pen-to-square"></i> Edit
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/user/delete?id=${user.id}" 
                               class="btn btn-danger" 
                               onclick="return confirm('Are you sure you want to delete this user?');">
                               <i class="fa-solid fa-trash"></i> Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div style="margin-top: 30px; border-top: 1px solid #eee; padding-top: 20px;">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn" style="background-color: #6c757d; color: white;">
                <i class="fa-solid fa-arrow-left"></i> Back to Dashboard
            </a>
        </div>
    </div>

</body>
</html>