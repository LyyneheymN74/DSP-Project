<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="main-header">
        DSP Dropshipping
    </div>

    <div class="container">
        <div class="header">
            <div class="text">Login</div>
            <div class="underline"></div>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="error-msg">${errorMessage}</div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="success-msg">${successMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="inputs">
                <div class="input">
                    <i class="fa-solid fa-user"></i>
                    <input type="text" name="username" placeholder="Username" required />
                </div>
                
                <div class="input">
                    <i class="fa-solid fa-lock"></i>
                    <input type="password" name="password" placeholder="Password" required />
                </div>
            </div>

            <div class="forgot-password">
                Forgot Password? <a href="${pageContext.request.contextPath}/forgot-password">Click Here!</a>
            </div>

            <div class="submit-container">
                <a href="${pageContext.request.contextPath}/register" class="submit gray">Sign Up</a>
                
                <button type="submit" class="submit">Login</button>
            </div>
        </form>
    </div>
</body>
</html>