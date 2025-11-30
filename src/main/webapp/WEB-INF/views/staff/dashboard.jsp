<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Staff Operations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

    <div class="main-header">
        <a href="#">DSP Staff Panel</a>
        
        <div style="display: flex; align-items: center;">
            <span style="margin-right: 15px; font-weight: normal;">
                Welcome, <strong>${currentUser.username}</strong>
            </span>
            
            <form action="${pageContext.request.contextPath}/logout" method="post" style="margin: 0;">
                <button type="submit" class="btn btn-danger" style="font-size: 12px; padding: 5px 10px;">
                    Logout
                </button>
            </form>
        </div>
    </div>

    <div class="container">
        <h1><i class="fa-solid fa-clipboard-list"></i> Operations Dashboard</h1>
        <p style="color: #666; margin-bottom: 30px;">
            Manage product catalog and oversee global order fulfillment.
        </p>

        <div style="display: flex; gap: 20px; flex-wrap: wrap;">
            
            <a href="${pageContext.request.contextPath}/staff/products" 
               class="btn btn-primary" 
               style="padding: 20px 30px; font-size: 18px; display: flex; align-items: center; gap: 10px; flex: 1;">
               <i class="fa-solid fa-boxes-packing"></i> Manage Products
            </a>

            <a href="${pageContext.request.contextPath}/staff/orders" 
               class="btn btn-success" 
               style="padding: 20px 30px; font-size: 18px; display: flex; align-items: center; gap: 10px; flex: 1;">
               <i class="fa-solid fa-truck-fast"></i> Global Fulfillment
            </a>

        </div>
    </div>

</body>
</html>