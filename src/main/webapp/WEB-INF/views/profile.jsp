<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BiteCurve | Profile</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/premium.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="app-page">
    <div class="page-shell">
        <div class="page-title">
            <div>
                <h1>My profile</h1>
                <p>Keep your delivery details ready for faster checkout.</p>
            </div>
        </div>

        <%
            String profileImage = (String) request.getAttribute("profileImage");
            String profileName = request.getAttribute("name") != null ? request.getAttribute("name").toString() : "U";
            String profileInitial = profileName.length() > 0 ? profileName.substring(0, 1).toUpperCase() : "U";
        %>

        <% if (request.getAttribute("success") != null) { %>
            <div class="message success"><%= request.getAttribute("success") %></div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
            <div class="message error"><%= request.getAttribute("error") %></div>
        <% } %>

        <div class="profile-layout">
            <aside class="panel">
                <% if (profileImage != null) { %>
                    <img class="profile-photo" src="<%= profileImage %>" alt="Profile photo">
                <% } else { %>
                    <div class="profile-initial"><%= profileInitial %></div>
                <% } %>
                <h2><%= profileName %></h2>
                <p class="panel-copy">Your saved profile powers a smoother cart and checkout flow.</p>
            </aside>

            <section class="panel">
                <h2>Account details</h2>

                <form class="form-grid" action="<%= request.getContextPath() %>/profile" method="post" enctype="multipart/form-data">
                    <div class="field">
                        <label for="profileImage">Profile photo</label>
                        <input id="profileImage" type="file" name="profileImage" accept="image/png,image/jpeg,image/webp,image/gif">
                        <p class="muted">Upload JPG, PNG, WEBP, or GIF up to 2 MB.</p>
                    </div>

                    <div class="field">
                        <label for="name">Full name</label>
                        <input id="name" type="text" name="name"
                               value="<%= request.getAttribute("name") != null ? request.getAttribute("name") : "" %>"
                               required>
                    </div>

                    <div class="field">
                        <label for="email">Email</label>
                        <input id="email" type="email" name="email"
                               value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
                               required>
                    </div>

                    <div class="field">
                        <label for="phone">Phone</label>
                        <input id="phone" type="text" name="phone"
                               value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>">
                    </div>

                    <div class="field">
                        <label for="address">Address</label>
                        <textarea id="address" name="address"><%= request.getAttribute("address") != null ? request.getAttribute("address") : "" %></textarea>
                    </div>

                    <div class="field">
                        <label for="password">New password</label>
                        <input id="password" type="password" name="password" placeholder="Leave empty to keep current password">
                    </div>

                    <div class="hero-actions">
                        <button class="btn" type="submit">Save profile</button>
                        <a class="btn-secondary" href="<%= request.getContextPath() %>/home">Cancel</a>
                    </div>
                </form>
            </section>
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>

