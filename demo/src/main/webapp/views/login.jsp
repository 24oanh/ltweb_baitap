<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-light">

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-5">
                <div class="card shadow-lg rounded-4 border-0">
                    <div class="card-body p-4">
                        <h2 class="text-center mb-4 text-primary">
                            <i class="fa fa-user-circle"></i> Đăng nhập
                        </h2>

                        <!-- Hiển thị thông báo lỗi -->
                        <c:if test="${alert != null}">
                            <div class="alert alert-danger text-center fw-bold">
                                ${alert}
                            </div>
                        </c:if>

                        <!-- Form đăng nhập -->
                        <form action="login" method="post">
                            <!-- Username -->
                            <div class="mb-3 input-group">
                                <span class="input-group-text bg-primary text-white">
                                    <i class="fa fa-user"></i>
                                </span>
                                <input type="text" class="form-control" 
                                       name="username" placeholder="Tên đăng nhập" 
                                       required autofocus>
                            </div>

                            <!-- Password -->
                            <div class="mb-3 input-group">
                                <span class="input-group-text bg-primary text-white">
                                    <i class="fa fa-lock"></i>
                                </span>
                                <input type="password" class="form-control" 
                                       name="password" placeholder="Mật khẩu" 
                                       required>
                            </div>

                            <!-- Submit button -->
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary fw-bold">
                                    <i class="fa fa-sign-in-alt"></i> Đăng nhập
                                </button>
                            </div>
                        </form>

                    </div>
                </div>
                <p class="text-center mt-3 text-muted">
                    © 2025 - Hệ thống quản lý
                </p>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
