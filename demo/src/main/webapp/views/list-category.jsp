<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="week2.model.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách danh mục</title>
    <!-- Thêm meta tags để tránh cache và auto-refresh -->
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        .btn-danger:hover {
            background-color: #c82333;
        }
        .btn-warning {
            background-color: #ffc107;
            color: black;
        }
        .btn-warning:hover {
            background-color: #e0a800;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f8f9fa;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .category-image {
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 4px;
        }
        .error {
            color: #dc3545;
            padding: 10px;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            border-radius: 4px;
            margin-bottom: 15px;
        }
        .empty-message {
            text-align: center;
            color: #6c757d;
            font-style: italic;
            padding: 40px;
        }
        .debug-info {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            padding: 10px;
            margin-bottom: 20px;
            font-family: monospace;
            font-size: 12px;
        }
    </style>
</head>
<body>

    <!-- Debug thông tin -->
    <div class="debug-info">
        <strong>Debug Info:</strong><br>
        Request Time: <%= new java.util.Date() %><br>
        Context Path: <%= request.getContextPath() %><br>
        Categories Count: <%= 
            (request.getAttribute("categories") != null) 
            ? ((List<Category>) request.getAttribute("categories")).size() 
            : "null" 
        %>
    </div>

    <div class="header">
        <h2>Danh sách danh mục</h2>
        <a href="<%=request.getContextPath()%>/admin/category/add" class="btn btn-primary">
            Thêm danh mục mới
        </a>
    </div>
    
    <%-- Display error message if exists --%>
    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null && !error.trim().isEmpty()) { %>
        <div class="error">
            <%= error %>
        </div>
    <% } %>
    
    <% 
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        if (categories != null && !categories.isEmpty()) {
    %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Hình ảnh</th>
                    <th>Tên danh mục</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <% for (Category category : categories) { %>
                    <tr>
                        <td><%= category.getId() %></td>
                        <td>
                            <% 
                                String imagePath = category.getImage();
                                if (imagePath != null && !imagePath.trim().isEmpty()) { 
                                    // Chuẩn hóa đường dẫn image
                                    if (!imagePath.startsWith("/")) {
                                        imagePath = "/" + imagePath;
                                    }
                            %>
                                <img src="<%=request.getContextPath()%><%= imagePath %>" 
                                     alt="<%= category.getName() %>" 
                                     class="category-image"
                                     onload="console.log('Image loaded: <%= imagePath %>')"
                                     onerror="console.log('Image failed: <%= imagePath %>'); this.style.display='none'; this.nextElementSibling.style.display='inline';">
                                <span style="display:none; color: #999;">No Image</span>
                            <% } else { %>
                                <span style="color: #999;">No Image</span>
                            <% } %>
                        </td>
                        <td><%= category.getName() %></td>
                        <td>
                            <a href="<%=request.getContextPath()%>/admin/category/edit?id=<%= category.getId() %>" 
                               class="btn btn-warning">Sửa</a>
                            <a href="javascript:void(0)" 
                               class="btn btn-danger"
                               onclick="deleteCategory(<%= category.getId() %>)">Xóa</a>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } else { %>
        <div class="empty-message">
            <p>Chưa có danh mục nào. <a href="<%=request.getContextPath()%>/admin/category/add">Thêm danh mục đầu tiên</a></p>
        </div>
    <% } %>

    <script>
        // Debug: Log khi trang load
        console.log('Page loaded at:', new Date());
        console.log('Categories count:', <%= 
            (request.getAttribute("categories") != null) 
            ? ((List<Category>) request.getAttribute("categories")).size() 
            : 0 
        %>);
        
        // Function để xóa category an toàn
        function deleteCategory(categoryId) {
            if (confirm('Bạn có chắc chắn muốn xóa danh mục này?')) {
                // Sử dụng POST thay vì GET để tránh cache
                var form = document.createElement('form');
                form.method = 'POST';
                form.action = '<%=request.getContextPath()%>/admin/category/delete';
                
                var hiddenField = document.createElement('input');
                hiddenField.type = 'hidden';
                hiddenField.name = 'id';
                hiddenField.value = categoryId;
                
                form.appendChild(hiddenField);
                document.body.appendChild(form);
                form.submit();
            }
        }
        
        // Ngăn chặn các request không mong muốn
        window.addEventListener('beforeunload', function() {
            console.log('Page unloading...');
        });
        
        // Kiểm tra nếu có interval hoặc timeout nào đó
        if (window.setInterval.toString().indexOf('[native code]') === -1) {
            console.warn('setInterval has been overridden!');
        }
        if (window.setTimeout.toString().indexOf('[native code]') === -1) {
            console.warn('setTimeout has been overridden!');
        }
    </script>
</body>
</html>