<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="main-header">
        DSP Dropshipping
    </div>

    <div class="container" style="margin-top: 50px;"> 
        <div class="header">
            <div class="text">Sign Up</div>
            <div class="underline"></div>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="error-msg">${errorMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="inputs">
                
                <div class="input">
                    <i class="fa-solid fa-user"></i>
                    <input type="text" name="username" placeholder="Username" required />
                </div>

                <div class="input">
                    <i class="fa-solid fa-envelope"></i>
                    <input type="email" name="email" placeholder="Email" required />
                </div>

                <div class="input">
                    <i class="fa-solid fa-phone"></i>
                    <input type="tel" name="phone" placeholder="Phone Number" required />
                </div>

                <div class="input">
                    <i class="fa-solid fa-lock"></i>
                    <input type="password" name="password" placeholder="Password" required />
                </div>

                <div class="input">
                    <i class="fa-solid fa-key"></i>
                    <input type="password" name="confirmPassword" placeholder="Confirm Password" required />
                </div>
            </div>

            <div class="submit-container">
                <button type="submit" class="submit">Sign Up</button>
                
                <a href="${pageContext.request.contextPath}/login" class="submit gray">Login</a>
            </div>
        </form>
    </div>
</body>
</html>