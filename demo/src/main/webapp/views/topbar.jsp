<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Header</title>
</head>
<body>
    <c:choose>
        <c:when test="${sessionScope.account == null}">
            <div class="col-sm-6">
                <ul class="list-inline right-topbar pull-right">
                    <li>
                        <a href="${pageContext.request.contextPath}/Login">Đăng nhập</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
                    </li>
                    <li>
                        <i class="search fa fa-search search-button"></i>
                    </li>
                </ul>
            </div>
        </c:when>
        <c:otherwise>
            <div class="col-sm-6">
                <ul class="list-inline right-topbar pull-right">
                    <li>
                        <a href="${pageContext.request.contextPath}/member/myaccount">
                            ${sessionScope.account.fullName}
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/Logout">Đăng Xuất</a>
                    </li>
                    <li>
                        <i class="search fa fa-search search-button"></i>
                    </li>
                </ul>
            </div>
        </c:otherwise>
    </c:choose>
</body>
</html>