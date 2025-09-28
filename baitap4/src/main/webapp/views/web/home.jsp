<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #e0f7fa, #ffffff);
        }

        .profile-card {
            max-width: 400px;
            background-color: #fff;
            margin: 80px auto;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
            text-align: center;
        }

        .profile-image {
            width: 120px;
            height: 120px;
            object-fit: cover;
            border-radius: 50%;
            border: 4px solid #00acc1;
            margin-bottom: 20px;
        }

        h2 {
            margin: 0;
            color: #007c91;
            font-size: 24px;
        }

        p {
            margin: 10px 0;
            font-size: 16px;
            color: #555;
        }

        .btn-edit {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #00acc1;
            color: #fff;
            text-decoration: none;
            border-radius: 8px;
            transition: background-color 0.3s ease;
        }

        .btn-edit:hover {
            background-color: #008b9c;
        }
    </style>
</head>
<body>

    <div class="profile-card">
        <!-- Ảnh đại diện -->
        <c:choose>
            <c:when test="${not empty user.image}">
                <img src="${pageContext.request.contextPath}${user.image}" alt="Profile Image" class="profile-image">
            </c:when>
            <c:otherwise>
                <img src="${pageContext.request.contextPath}/images/default-avatar.png" alt="Default Image" class="profile-image">
            </c:otherwise>
        </c:choose>

        <!-- Thông tin -->
        <h2>Welcome, <c:out value="${user.fullname}" />!</h2>
        <p>📞 Phone: <c:out value="${user.phone}" /></p>

        <!-- Nút chỉnh sửa -->
        <a href="${pageContext.request.contextPath}/profile" class="btn-edit">Edit Profile</a>
    </div>

</body>
</html>