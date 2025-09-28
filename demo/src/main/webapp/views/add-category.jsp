<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm danh mục</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], input[type="file"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 10px;
        }
        button:hover {
            background-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
        }
        .btn-secondary:hover {
            background-color: #545b62;
        }
        .error {
            color: #dc3545;
            padding: 10px;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            border-radius: 4px;
            margin-bottom: 15px;
        }
        .success {
            color: #155724;
            padding: 10px;
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            border-radius: 4px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <h2>Thêm danh mục mới</h2>
    
    <%-- Display error message if exists --%>
    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null && !error.trim().isEmpty()) { %>
        <div class="error">
            <%= error %>
        </div>
    <% } %>
    
    <%-- Display success message if exists --%>
    <% String success = (String) request.getAttribute("success"); %>
    <% if (success != null && !success.trim().isEmpty()) { %>
        <div class="success">
            <%= success %>
        </div>
    <% } %>
    
    <form method="post" action="<%=request.getContextPath()%>/admin/category/add" enctype="multipart/form-data">
        <div class="form-group">
            <label for="name">Tên danh mục *:</label>
            <input type="text" id="name" name="name" required 
                   value="<%=request.getParameter("name") != null ? request.getParameter("name") : ""%>">
        </div>
        
        <div class="form-group">
            <label for="image">Hình ảnh:</label>
            <input type="file" id="image" name="image" accept="image/*">
            <small style="color: #6c757d;">Chọn file hình ảnh (JPG, PNG, GIF). Tối đa 10MB.</small>
        </div>
        
        <button type="submit">Thêm danh mục</button>
        <button type="button" class="btn-secondary" onclick="window.location.href='<%=request.getContextPath()%>/admin/category/list'">
            Hủy
        </button>
    </form>
</body>
</html>