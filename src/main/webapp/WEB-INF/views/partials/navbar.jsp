<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%
String user = (String) request.getSession().getAttribute("user");
%>

<div class="navbar-wrap">
    <nav class="navbar site-shell" aria-label="Main navigation">
        <a class="brand" href="<%=request.getContextPath()%>/home">
            <span class="brand-mark">BC</span>
            <span class="brand-text">
                <span class="brand-name">BiteCurve</span>
                <span class="brand-tagline">Food delivery</span>
            </span>
        </a>

        <div class="nav-links">
            <% if (user == null) { %>
                <a href="<%=request.getContextPath()%>/home">Home</a>
                <a href="<%=request.getContextPath()%>/login">Login</a>
                <a class="nav-cta" href="<%=request.getContextPath()%>/register">Create account</a>
            <% } else { %>
                <a href="<%=request.getContextPath()%>/home">Home</a>
                <a href="<%=request.getContextPath()%>/my-orders">Orders</a>
                <a href="<%=request.getContextPath()%>/profile">Profile</a>
                <a href="<%=request.getContextPath()%>/admin">Admin</a>
                <a href="<%=request.getContextPath()%>/cart">
                    Cart <span id="cartCount" class="cart-badge">0</span>
                </a>
                <span class="user-pill">Hi, <%= user %></span>
                <a class="nav-cta" href="<%=request.getContextPath()%>/logout">Logout</a>
            <% } %>
        </div>
    </nav>
</div>

<script>
function updateCartCount() {
    fetch("<%= request.getContextPath() %>/cart-count")
        .then(res => res.text())
        .then(count => {
            const el = document.getElementById("cartCount");
            if (el) el.innerText = count;
        })
        .catch(() => {});
}

document.addEventListener("DOMContentLoaded", updateCartCount);
</script>

