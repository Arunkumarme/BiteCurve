<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foodapp.model.Order" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BiteCurve | My Orders</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/premium.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="app-page">
    <div class="page-shell">
        <div class="page-title">
            <div>
                <h1>My orders</h1>
                <p>Track your past orders and see what is on the way.</p>
            </div>
            <a class="btn-secondary" href="<%= request.getContextPath() %>/home">Order again</a>
        </div>

        <%
            String reviewMessage = (String) session.getAttribute("reviewMessage");
            String reviewError = (String) session.getAttribute("reviewError");

            if (reviewMessage != null) {
                session.removeAttribute("reviewMessage");
        %>
                <div class="message success"><%= reviewMessage %></div>
        <%
            }

            if (reviewError != null) {
                session.removeAttribute("reviewError");
        %>
                <div class="message error"><%= reviewError %></div>
        <%
            }
        %>

        <section class="order-list">
            <%
            List<Order> orders = (List<Order>) request.getAttribute("orders");

            if (orders != null && !orders.isEmpty()) {
                for (Order o : orders) {
            %>

            <article class="order-card">
                <div class="card-body">
                    <div class="card-title-row">
                        <div>
                            <h3>Order #<%= o.getOrderId() %></h3>
                            <p class="muted"><%= o.getOrderTime() %></p>
                        </div>
                        <span class="status"><%= o.getStatus() %></span>
                    </div>

                    <div class="summary-row total">
                        <span>Total paid</span>
                        <strong>&#8377;<%= o.getTotalAmount() %></strong>
                    </div>

                    <div>
                        <strong>Items</strong>
                        <ul class="muted">
                        <%
                        if (o.getItems() != null && !o.getItems().isEmpty()) {
                            for(String item : o.getItems()) {
                        %>
                            <li><%= item %></li>
                        <%
                            }
                        } else {
                        %>
                            <li>Item details unavailable</li>
                        <%
                        }
                        %>
                        </ul>
                    </div>

                    <% if ("Delivered".equalsIgnoreCase(o.getStatus())) { %>
                        <% if (o.isReviewed()) { %>
                            <div class="message success">
                                Your rating: <strong><%= o.getReviewRating() %>/5</strong><br>
                                <%= o.getReviewText() %>
                            </div>
                        <% } else { %>
                            <form class="form-grid" action="<%= request.getContextPath() %>/submit-review" method="post">
                                <input type="hidden" name="orderId" value="<%= o.getOrderId() %>">

                                <div class="field">
                                    <label for="rating-<%= o.getOrderId() %>">Rating</label>
                                    <select id="rating-<%= o.getOrderId() %>" name="rating" required>
                                        <option value="5">5 - Excellent</option>
                                        <option value="4">4 - Good</option>
                                        <option value="3">3 - Average</option>
                                        <option value="2">2 - Poor</option>
                                        <option value="1">1 - Very poor</option>
                                    </select>
                                </div>

                                <div class="field">
                                    <label for="review-<%= o.getOrderId() %>">Write a review</label>
                                    <textarea id="review-<%= o.getOrderId() %>" name="reviewText" placeholder="Share your experience" required></textarea>
                                </div>

                                <button class="btn" type="submit">Submit review</button>
                            </form>
                        <% } %>
                    <% } %>
                </div>
            </article>

            <%
                }
            } else {
            %>

            <div class="empty-state">
                No orders yet. Your first BiteCurve order will appear here.
                <div class="action-space">
                    <a class="btn" href="<%= request.getContextPath() %>/home">Explore restaurants</a>
                </div>
            </div>

            <%
            }
            %>
        </section>
    </div>
</main>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>

