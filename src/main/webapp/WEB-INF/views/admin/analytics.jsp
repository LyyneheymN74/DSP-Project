<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<html>
<head>
    <title>Business Analytics</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        /* Specific Styles for Analytics Cards */
        .stats-container {
            display: flex;
            gap: 25px;
            justify-content: space-between;
            margin-top: 30px;
            margin-bottom: 40px;
        }

        .card {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.05);
            text-align: center;
            flex: 1; /* Make all cards equal width */
            transition: transform 0.3s ease;
            border: 1px solid #eee;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
        }

        .card-icon {
            font-size: 40px;
            margin-bottom: 15px;
            opacity: 0.8;
        }

        .card h3 {
            margin: 0;
            color: #666;
            font-size: 14px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .card .value {
            font-size: 32px;
            font-weight: 800;
            margin-top: 10px;
        }

        /* Card Colors */
        .card.green { border-bottom: 5px solid #28a745; }
        .card.green .value, .card.green .card-icon { color: #28a745; }

        .card.blue { border-bottom: 5px solid #007bff; }
        .card.blue .value, .card.blue .card-icon { color: #007bff; }

        .card.orange { border-bottom: 5px solid #ffc107; }
        .card.orange .value, .card.orange .card-icon { color: #ffc107; }
    </style>
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
        <h1><i class="fa-solid fa-chart-line"></i> Sales Analytics</h1>
        <p style="color: #666;">Overview of your store's performance.</p>
        <hr style="border: 0; border-top: 1px solid #eee;">

        <div class="stats-container">
            <div class="card green">
                <div class="card-icon"><i class="fa-solid fa-sack-dollar"></i></div>
                <h3>Total Revenue</h3>
                <div class="value">
                    <fmt:formatNumber value="${stats.totalRevenue}" type="currency" currencySymbol="$" minFractionDigits="2" maxFractionDigits="2" />
                </div>
            </div>

            <div class="card blue">
                <div class="card-icon"><i class="fa-solid fa-cart-shopping"></i></div>
                <h3>Total Orders</h3>
                <div class="value">${stats.totalOrders}</div>
            </div>

            <div class="card orange">
                <div class="card-icon"><i class="fa-solid fa-boxes-stacked"></i></div>
                <h3>Items Sold</h3>
                <div class="value">${stats.totalProductsSold}</div>
            </div>
        </div>

        <p style="text-align: center; color: #999; font-size: 13px;">
            * Data calculated from all-time sales history.
        </p>

        <div style="margin-top: 30px; border-top: 1px solid #eee; padding-top: 20px;">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn" style="background-color: #6c757d; color: white;">
                <i class="fa-solid fa-arrow-left"></i> Back to Dashboard
            </a>
        </div>

    </div>

</body>
</html>