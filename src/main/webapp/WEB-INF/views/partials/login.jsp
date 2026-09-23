<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BiteCurve | Login</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/premium.css">
</head>
<body>

<main class="auth-page">
    <section class="auth-story">
        <span class="eyebrow">Welcome back</span>
        <h1>Your next meal is already waiting.</h1>
        <p>Sign in to explore restaurants, reorder favorites, and move from craving to checkout in a few clean taps.</p>
    </section>

    <section class="auth-panel">
        <div class="auth-card">
            <h2>Login</h2>
            <p>Enter your account details to continue to BiteCurve.</p>

            <% if(request.getAttribute("error") != null) { %>
                <div class="message error"><%= request.getAttribute("error") %></div>
            <% } %>

            <form class="form-grid" action="<%= request.getContextPath() %>/login" method="post">
                <div class="field">
                    <label for="email">Email</label>
                    <input id="email" type="email" name="email" placeholder="you@example.com" required>
                </div>

                <div class="field">
                    <label for="password">Password</label>
                    <input id="password" type="password" name="password" placeholder="Enter password" required>
                </div>

                <button class="btn" type="submit">Login</button>
            </form>

            <div class="auth-links">
                <a href="<%= request.getContextPath() %>/register">Create a new account</a>
                <a href="<%= request.getContextPath() %>/home">Browse restaurants first</a>
            </div>
        </div>
    </section>
</main>

</body>
</html>

