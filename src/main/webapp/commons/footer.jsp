<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
    .footer {
        background: linear-gradient(90deg, #00dfd8, #007cf0);
        color: white;
        padding: 30px 20px;
        text-align: center;
        font-family: 'Segoe UI', sans-serif;
        margin-top: 50px;
    }

    .footer p {
        margin: 8px 0;
        font-size: 16px;
    }

    .footer ul {
        list-style: none;
        padding: 0;
        margin: 10px 0 0;
        display: flex;
        justify-content: center;
        gap: 20px;
        flex-wrap: wrap;
    }

    .footer ul li {
        display: inline;
    }

    .footer ul li a {
        color: #fff;
        text-decoration: none;
        font-weight: 500;
        padding: 6px 12px;
        border-radius: 6px;
        transition: background 0.3s ease, color 0.3s ease;
    }

    .footer ul li a:hover {
        background-color: rgba(255, 255, 255, 0.2);
        color: #f1f1f1;
    }

    /* Optional: small screen adjustments */
    @media (max-width: 500px) {
        .footer ul {
            flex-direction: column;
            gap: 10px;
        }
    }
</style>

<div class="footer">
    <p>&copy; 2025 My Website | All rights reserved.</p>
    <p>Follow Us:</p>
    <ul>
        <li><a href="https://www.facebook.com" target="_blank">Facebook</a></li>
        <li><a href="https://twitter.com" target="_blank">Twitter</a></li>
        <li><a href="https://www.instagram.com" target="_blank">Instagram</a></li>
    </ul>
</div>