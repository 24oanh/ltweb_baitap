<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="week2.model.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa danh mục</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .header {
            text-align: center;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        input[type="text"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
        input[type="text"]:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0,123,255,0.3);
        }
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin-right: 10px;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #545b62;
        }
        .button-group {
            text-align: center;
            margin-top: 30px;
        }
        .error {
            color: #dc3545;
            padding: 10px;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .form-info {
            background-color: #d1ecf1;
            border: 1px solid #bee5eb;
            color: #0c5460;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .image-preview {
            margin-top: 10px;
        }
        .image-preview img {
            max-width: 100px;
            max-height: 100px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>Chỉnh sửa danh mục</h2>
        </div>
        
        <%-- Display error message if exists --%>
        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null && !error.trim().isEmpty()) { %>
            <div class="error">
                <%= error %>
            </div>
        <% } %>
        
        <% 
            Category category = (Category) request.getAttribute("category");
            if (category != null) {
        %>
            <div class="form-info">
                Đang chỉnh sửa danh mục ID: <%= category.getId() %>
            </div>
            
            <form method="post" action="<%=request.getContextPath()%>/admin/category/edit">
                <input type="hidden" name="id" value="<%= category.getId() %>">
                
                <div class="form-group">
                    <label for="name">Tên danh mục: <span style="color: red;">*</span></label>
                    <input type="text" id="name" name="name" 
                           value="<%= category.getName() != null ? category.getName() : "" %>" 
                           required maxlength="100"
                           placeholder="Nhập tên danh mục">
                </div>
                
                <div class="form-group">
                    <label for="image">Đường dẫn hình ảnh:</label>
                    <input type="text" id="image" name="image" 
                           value="<%= category.getImage() != null ? category.getImage() : "" %>"
                           placeholder="VD: images/category1.jpg">
                    
                    <% if (category.getImage() != null && !category.getImage().trim().isEmpty()) { %>
                        <div class="image-preview">
                            <p>Hình ảnh hiện tại:</p>
                            <img src="<%=request.getContextPath()%>/<%= category.getImage() %>" 
                                 alt="Current image"
                                 onerror="this.src='<%=request.getContextPath()%>/images/no-image.png'">
                        </div>
                    <% } %>
                </div>
                
                <div class="button-group">
                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                    <a href="<%=request.getContextPath()%>/admin/category/list" 
                       class="btn btn-secondary">Hủy</a>
                </div>
            </form>
        <% } else { %>
            <div class="error">
                Không tìm thấy thông tin danh mục để chỉnh sửa.
                <br><br>
                <a href="<%=request.getContextPath()%>/admin/category/list" 
                   class="btn btn-secondary">Quay lại danh sách</a>
            </div>
        <% } %>
    </div>
    
    <script>
        // Validate form trước khi submit
        document.querySelector('form').addEventListener('submit', function(e) {
            const name = document.getElementById('name').value.trim();
            
            if (!name) {
                alert('Tên danh mục không được để trống!');
                e.preventDefault();
                return false;
            }
            
            if (name.length > 100) {
                alert('Tên danh mục không được quá 100 ký tự!');
                e.preventDefault();
                return false;
            }
        });
        
        // Preview image khi thay đổi đường dẫn
        document.getElementById('image').addEventListener('blur', function() {
            const imagePath = this.value.trim();
            const preview = document.querySelector('.image-preview');
            
            if (imagePath) {
                if (!preview) {
                    const newPreview = document.createElement('div');
                    newPreview.className = 'image-preview';
                    newPreview.innerHTML = '<p>Preview:</p><img alt="Preview">';
                    this.parentNode.appendChild(newPreview);
                }
                
                const img = document.querySelector('.image-preview img');
                img.src = '<%=request.getContextPath()%>/' + imagePath;
                img.onerror = function() {
                    this.src = '<%=request.getContextPath()%>/images/no-image.png';
                };
            }
        });
    </script>
</body>
</html>