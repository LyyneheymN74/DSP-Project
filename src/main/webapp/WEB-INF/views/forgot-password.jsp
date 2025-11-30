<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Forgot Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

    <div class="main-header">
        DSP Dropshipping
    </div>

    <div class="container">
        <div class="header">
            <div class="text">Recovery</div>
            <div class="underline"></div>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="error-msg">${errorMessage}</div>
        </c:if>

        <c:if test="${step != 'reset'}">
            <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                <input type="hidden" name="action" value="verify">
                
                <div class="inputs">
                    <div class="input">
                        <i class="fa-solid fa-user"></i>
                        <input type="text" name="username" placeholder="Enter Username" required>
                    </div>
                    <div class="input">
                        <i class="fa-solid fa-envelope"></i>
                        <input type="email" name="email" placeholder="Enter Registered Email" required>
                    </div>
                </div>
                
                <div class="submit-container">
                    <a href="${pageContext.request.contextPath}/login" class="submit gray">Cancel</a>
                    <button type="submit" class="submit">Verify</button>
                </div>
            </form>
        </c:if>

        <c:if test="${step == 'reset'}">
            <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                <input type="hidden" name="action" value="reset">
                <input type="hidden" name="username" value="${verifiedUser}">
                
                <p style="text-align: center; color: #666; margin-top: 20px;">
                    Resetting password for: <strong>${verifiedUser}</strong>
                </p>
                
                <div class="inputs" style="margin-top: 20px;">
                    <div class="input">
                        <i class="fa-solid fa-lock"></i>
                        <input type="password" name="newPassword" placeholder="New Password" required>
                    </div>
                    <div class="input">
                        <i class="fa-solid fa-key"></i>
                        <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
                    </div>
                </div>
                
                <div class="submit-container">
                    <button type="submit" class="submit" style="background-color: #1565c0;">Save Password</button>
                </div>
            </form>
        </c:if>
    </div>

</body>
</html>