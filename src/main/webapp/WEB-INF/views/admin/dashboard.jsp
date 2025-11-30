<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>

    <div class="main-header">
        <a href="#">DSP Admin Panel</a>
        
        <div style="display: flex; align-items: center;">
            <span style="margin-right: 15px; font-weight: normal;">
                Welcome, <strong>${currentUser.username}</strong>
            </span>
            
            <form action="${pageContext.request.contextPath}/logout" method="post" style="margin: 0;">
                <button type="submit" class="btn btn-danger" style="font-size: 15px; padding: 5px 10px;">
                    Logout
                </button>
            </form>
        </div>
    </div>

    <div class="container">
        <h1>Dashboard Overview</h1>
        <p style="color: #666; margin-bottom: 30px;">
            Select a module below to manage your dropshipping store.
        </p>

        <div style="display: flex; gap: 20px; flex-wrap: wrap;">
            
            <a href="${pageContext.request.contextPath}/admin/users" 
               class="btn btn-primary" 
               style="padding: 20px 30px; font-size: 18px; display: flex; align-items: center; gap: 10px;">
               👥 Manage Users
            </a>

            <a href="${pageContext.request.contextPath}/admin/products" 
               class="btn btn-success" 
               style="padding: 20px 30px; font-size: 18px; display: flex; align-items: center; gap: 10px;">
               📦 Manage Products
            </a>

            <a href="${pageContext.request.contextPath}/admin/analytics" 
               class="btn btn-warning" 
               style="padding: 20px 30px; font-size: 18px; display: flex; align-items: center; gap: 10px;">
               📊 Sales Analytics
            </a>

        </div>
    </div>

</body>
</html>