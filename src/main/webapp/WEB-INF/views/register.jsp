<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BiteCurve | Register</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/premium.css">
</head>
<body>

<main class="auth-page register">
    <section class="auth-story">
        <span class="eyebrow">Join BiteCurve</span>
        <h1>Build your perfect food run.</h1>
        <p>Create your account once, save delivery details, and make every order feel polished from search to checkout.</p>
    </section>

    <section class="auth-panel">
        <div class="auth-card">
            <h2>Create account</h2>
            <p>Tell us where to deliver your next favorite meal.</p>

            <form class="form-grid" action="<%= request.getContextPath() %>/register" method="post">
                <div class="field">
                    <label for="name">Full name</label>
                    <input id="name" type="text" name="name" placeholder="Your name" required>
                </div>

                <div class="field">
                    <label for="email">Email</label>
                    <input id="email" type="email" name="email" placeholder="you@example.com" required>
                </div>

                <div class="field">
                    <label for="password">Password</label>
                    <input id="password" type="password" name="password" placeholder="Create password" required>
                </div>

                <div class="field">
                    <label for="phone">Phone</label>
                    <input id="phone" type="text" name="phone" placeholder="Phone number">
                </div>

                <div class="field">
                    <label for="address">Address</label>
                    <input id="address" type="text" name="address" placeholder="Delivery address">
                </div>

                <button class="btn" type="submit">Create account</button>
            </form>

            <div class="auth-links">
                <a href="<%= request.getContextPath() %>/login">Already have an account? Login</a>
            </div>
        </div>
    </section>
</main>

</body>
</html>

