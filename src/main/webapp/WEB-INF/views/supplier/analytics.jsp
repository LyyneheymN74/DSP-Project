<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<html>
<head>
    <title>My Performance</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/supplier.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        /* Specific Styles for Stats Cards */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 30px;
            margin-top: 20px;
        }

        .stat-card {
            background: #f8fafc;
            border-radius: 12px;
            padding: 25px;
            text-align: center;
            border: 1px solid #e2e8f0;
            transition: transform 0.2s;
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
        }

        .stat-icon {
            font-size: 40px;
            margin-bottom: 15px;
        }

        .stat-title {
            font-size: 14px;
            color: #64748b;
            text-transform: uppercase;
            letter-spacing: 1px;
            font-weight: 600;
        }

        .stat-value {
            font-size: 36px;
            font-weight: 800;
            margin-top: 10px;
            color: #1e293b;
        }

        /* Colors */
        .stat-green .stat-icon, .stat-green .stat-value { color: #059669; }
        .stat-blue .stat-icon, .stat-blue .stat-value { color: #2563eb; }
        .stat-orange .stat-icon, .stat-orange .stat-value { color: #d97706; }
    </style>
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

    <div class="dashboard-container">
        
        <a href="${pageContext.request.contextPath}/supplier/dashboard" style="text-decoration: none; color: #6b7280; font-size: 14px; display: inline-block; margin-bottom: 15px;">
            <i class="fa-solid fa-arrow-left"></i> Back to Dashboard
        </a>

        <div class="content-card">
            <div class="page-header" style="border-bottom: 1px solid #e5e7eb; padding-bottom: 20px; margin-bottom: 30px;">
                <div class="page-title">
                    <i class="fa-solid fa-chart-pie" style="color: #4338ca;"></i> Performance Analytics
                </div>
            </div>

            <div class="stats-grid">
                
                <div class="stat-card stat-green">
                    <div class="stat-icon"><i class="fa-solid fa-sack-dollar"></i></div>
                    <div class="stat-title">Total Revenue</div>
                    <div class="stat-value">
                        <fmt:formatNumber value="${stats.totalRevenue}" type="currency" currencySymbol="$" minFractionDigits="2" />
                    </div>
                </div>

                <div class="stat-card stat-blue">
                    <div class="stat-icon"><i class="fa-solid fa-file-invoice"></i></div>
                    <div class="stat-title">Total Orders</div>
                    <div class="stat-value">${stats.totalOrders}</div>
                </div>

                <div class="stat-card stat-orange">
                    <div class="stat-icon"><i class="fa-solid fa-box-open"></i></div>
                    <div class="stat-title">Products Sold</div>
                    <div class="stat-value">${stats.totalProductsSold}</div>
                </div>

            </div>
            
            <p style="text-align: center; color: #94a3b8; font-size: 13px; margin-top: 40px;">
                * Analytics data is calculated based on confirmed orders only.
            </p>

        </div>
    </div>

</body>
</html>