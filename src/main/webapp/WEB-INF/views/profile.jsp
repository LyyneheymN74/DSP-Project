<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>My Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        /* Local overrides for the profile form */
        .profile-form { max-width: 500px; margin: 0 auto; }
        .form-group { margin-bottom: 20px; }
        .form-label { display: block; margin-bottom: 8px; font-weight: 600; color: #333; }
        .form-input { 
            width: 100%; 
            padding: 12px; 
            border: 1px solid #ddd; 
            border-radius: 6px; 
            font-size: 16px;
            box-sizing: border-box;
            transition: border-color 0.3s;
        }
        .form-input:focus { border-color: #1565c0; outline: none; }
        .form-input:disabled { background-color: #f8f9fa; color: #666; cursor: not-allowed; }
        
        .alert-success { background-color: #d4edda; color: #155724; padding: 15px; border-radius: 6px; margin-bottom: 20px; text-align: center;}
        .alert-error { background-color: #f8d7da; color: #721c24; padding: 15px; border-radius: 6px; margin-bottom: 20px; text-align: center;}
    </style>
</head>
<body>

    <div class="main-header">
        <a href="${pageContext.request.contextPath}/home" class="brand">DSP Store</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/my-orders">My Orders</a>
            <a href="${pageContext.request.contextPath}/profile" style="color: #1565c0;">Profile</a>
            <a href="${pageContext.request.contextPath}/logout" style="color: #dc3545;">Logout</a>
        </div>
    </div>

    <div class="cart-container">
        <h1 style="text-align: center; margin-bottom: 30px;">
            <i class="fa-solid fa-user-gear" style="color: #1565c0;"></i> Edit Profile
        </h1>

        <c:if test="${not empty successMessage}">
            <div class="alert-success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert-error">${errorMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/profile" method="post" class="profile-form">
            
            <div class="form-group">
                <label class="form-label">Username</label>
                <input type="text" value="${user.username}" disabled class="form-input">
                <small style="color: #888;">Username cannot be changed.</small>
            </div>

            <div class="form-group">
                <label class="form-label">Email Address</label>
                <input type="email" name="email" value="${user.email}" required class="form-input">
            </div>

            <div class="form-group">
                <label class="form-label">Phone Number</label>
                <input type="tel" name="phone" value="${user.phone}" placeholder="0901234567" required class="form-input">
            </div>

            <div class="form-group">
                <label class="form-label">Password</label>
                <input type="text" name="password" value="${user.password}" required class="form-input">
            </div>

            <div style="text-align: center; margin-top: 30px;">
                <button type="submit" class="btn btn-checkout" style="width: 100%;">Save Changes</button>
            </div>
            
            <div style="text-align: center; margin-top: 15px;">
                <a href="${pageContext.request.contextPath}/home" style="color: #666; text-decoration: none;">Cancel</a>
            </div>
        </form>
    </div>

</body>
</html>