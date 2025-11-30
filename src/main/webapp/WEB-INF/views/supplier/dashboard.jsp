<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Supplier Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/supplier.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

    <div class="supplier-header">
        <a href="#" class="brand">
            <i class="fa-solid fa-parachute-box"></i> DSP Supplier Hub
        </a>
        
        <div class="user-info">
            <span>Hello, <strong>${currentUser.username}</strong></span>
            <form action="${pageContext.request.contextPath}/logout" method="post" style="margin:0;">
                <button type="submit" class="logout-btn">Sign Out</button>
            </form>
        </div>
    </div>

    <div class="dashboard-container">
        <h1>Overview</h1>
        <p class="subtitle">Manage your products, orders, and performance from one place.</p>

        <div class="action-grid">
            
            <a href="${pageContext.request.contextPath}/supplier/products" class="action-card card-inventory">
                <div class="icon-box icon-inventory"><i class="fa-solid fa-boxes-stacked"></i></div>
                <div class="card-title">My Inventory</div>
                <div class="card-desc">Add new products, update stock levels, and manage pricing.</div>
            </a>

            <a href="${pageContext.request.contextPath}/supplier/orders" class="action-card card-orders">
                <div class="icon-box icon-orders"><i class="fa-solid fa-dolly"></i></div>
                <div class="card-title">Incoming Orders</div>
                <div class="card-desc">View pending orders from customers and mark items as shipped.</div>
            </a>

            <a href="${pageContext.request.contextPath}/supplier/analytics" class="action-card card-stats">
                <div class="icon-box icon-stats"><i class="fa-solid fa-chart-pie"></i></div>
                <div class="card-title">Performance</div>
                <div class="card-desc">Track your revenue, total items sold, and order volume.</div>
            </a>

        </div>
    </div>

</body>
</html>