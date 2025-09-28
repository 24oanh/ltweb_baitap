<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
body {
	font-family: Arial, sans-serif;
	background: #f2f2f2;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

.login-container {
	background: #fff;
	padding: 20px 30px;
	border-radius: 10px;
	box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
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
	<div class="login-container">
		<form action="${pageContext.request.contextPath}/login" method="post">
			<h2>Đăng nhập</h2>

			<c:if test="${alert != null}">
				<div class="alert">${alert}</div>
			</c:if>

			<input type="text" placeholder="Tên đăng nhập" name="username"
				class="form-control" required> <input type="password"
				placeholder="Mật khẩu" name="password" class="form-control" required>

			<label> <input type="checkbox" name="remember"> Ghi
				nhớ đăng nhập
			</label>

			<button type="submit" class="btn">Đăng nhập</button>

			<div class="extra">
				Chưa có tài khoản? <a
					href="${pageContext.request.contextPath}/register">Đăng ký</a><br>
				<a href="${pageContext.request.contextPath}/forgot">Quên
					mật khẩu?</a>
			</div>

		</form>
	</div>
</body>
</html>
