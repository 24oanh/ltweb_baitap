<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý danh mục</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<h2>Danh sách danh mục</h2>
<a href="${pageContext.request.contextPath}/admin/category/add">Thêm danh mục mới</a>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Tên danh mục</th>
        <th>Hình ảnh</th>
        <th>Hành động</th>
    </tr>
    <c:forEach var="c" items="${categories}">
        <tr>
            <td>${c.id}</td>
            <td>${c.name}</td>
            <td><img src="${c.image}" width="50"/></td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/category/edit?id=${c.id}">Sửa</a> |
                <a href="${pageContext.request.contextPath}/admin/category/delete?id=${c.id}" 
                   onclick="return confirm('Bạn có chắc muốn xóa?');">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
