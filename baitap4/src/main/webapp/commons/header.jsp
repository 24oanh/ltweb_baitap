<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
    body {
        margin: 0;
        padding: 0;
    }

    .navbar {
        background: linear-gradient(90deg, #007cf0, #00dfd8);
        padding: 15px 30px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-family: 'Segoe UI', sans-serif;
        box-shadow: 0 3px 10px rgba(0,0,0,0.1);
    }

    .navbar .logo {
        font-size: 24px;
        font-weight: bold;
        color: white;
        text-decoration: none;
        transition: transform 0.3s ease;
    }

    .navbar .logo:hover {
        transform: scale(1.05);
    }

    .navbar nav ul {
        list-style: none;
        display: flex;
        margin: 0;
        padding: 0;
        gap: 25px;
    }

    .navbar nav ul li a {
        text-decoration: none;
        color: white;
        font-size: 16px;
        padding: 8px 15px;
        border-radius: 5px;
        transition: background 0.3s, color 0.3s;
    }

    .navbar nav ul li a:hover {
        background-color: rgba(255, 255, 255, 0.2);
        color: #fff;
    }

    /* Optional: sticky navbar */
    .navbar {
        position: sticky;
        top: 0;
        z-index: 999;
    }
</style>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="logo">My Website</a>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
            <li><a href="${pageContext.request.contextPath}/about">About</a></li>
            <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
        </ul>
    </nav>
</div>