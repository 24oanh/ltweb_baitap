<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quên mật khẩu</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
    body {
        font-family: Arial, sans-serif;
        background: #f2f2f2;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }
    .forgot-container {
        background: #fff;
        padding: 20px 30px;
        border-radius: 10px;
        box-shadow: 0px 0px 10px rgba(0,0,0,0.2);
        width: 350px;
    }
    h2 {
        text-align: center;
        margin-bottom: 20px;
    }
    .form-control {
        width: 100%;
        padding: 10px;
        margin: 8px 0;
        border: 1px solid #ccc;
        border-radius: 5px;
    }
    .btn {
        width: 100%;
        padding: 10px;
        background: #007bff;
        border: none;
        color: #fff;
        border-radius: 5px;
        cursor: pointer;
    }
    .btn:hover {
        background: #0056b3;
    }
    .alert {
        color: red;
        text-align: center;
        margin-bottom: 10px;
    }
    .extra {
        margin-top: 15px;
        text-align: center;
    }
</style>
</head>
<body>
    <div class="forgot-container">
        <!-- action phải trùng với servlet mapping /forgot -->
        <form action="${pageContext.request.contextPath}/forgot" method="post">
            <h2>Quên mật khẩu</h2>

            <c:if test="${alert != null}">
                <div class="alert">${alert}</div>
            </c:if>

            <input type="email" placeholder="Nhập email của bạn" name="email" class="form-control" required>

            <button type="submit" class="btn">Gửi yêu cầu</button>

            <div class="extra">
                <a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
            </div>
        </form>
    </div>
</body>
</html>
