<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.foodapp.model.Restaurant" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BiteCurve | Admin</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/premium.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="app-page">
    <div class="page-shell">
        <div class="page-title">
            <div>
                <h1>Admin panel</h1>
                <p>Manage restaurants, menu items, and live order status from one polished workspace.</p>
            </div>
        </div>

        <%
            String adminMessage = (String) session.getAttribute("adminMessage");
            String adminError = (String) session.getAttribute("adminError");

            if (adminMessage != null) {
                session.removeAttribute("adminMessage");
        %>
                <div class="message success"><%= adminMessage %></div>
        <%
            }

            if (adminError != null) {
                session.removeAttribute("adminError");
        %>
                <div class="message error"><%= adminError %></div>
        <%
            }

            List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
            List<Map<String, Object>> orders = (List<Map<String, Object>>) request.getAttribute("orders");
            List<Map<String, Object>> menuItems = (List<Map<String, Object>>) request.getAttribute("menuItems");
        %>

        <div class="admin-grid">
            <section class="panel">
                <h2>Add restaurant</h2>
                <p class="panel-copy">Create a new restaurant card for the home page.</p>

                <form class="form-grid" action="<%= request.getContextPath() %>/admin" method="post">
                    <input type="hidden" name="action" value="addRestaurant">

                    <div class="field">
                        <label for="restaurantName">Restaurant name</label>
                        <input id="restaurantName" type="text" name="name" required>
                    </div>

                    <div class="field">
                        <label for="cuisine">Cuisine</label>
                        <input id="cuisine" type="text" name="cuisine" required>
                    </div>

                    <div class="field">
                        <label for="restaurantType">Type</label>
                        <select id="restaurantType" name="type" required>
                            <option value="VEG">Veg</option>
                            <option value="NON_VEG">Non-Veg</option>
                            <option value="BOTH">Both</option>
                        </select>
                    </div>

                    <div class="field">
                        <label for="rating">Rating</label>
                        <input id="rating" type="number" name="rating" step="0.1" min="0" max="5" value="4.0" required>
                    </div>

                    <div class="field">
                        <label for="deliveryTime">Delivery time</label>
                        <input id="deliveryTime" type="number" name="deliveryTime" min="1" value="30" required>
                    </div>

                    <div class="field">
                        <label for="imageUrl">Image URL</label>
                        <input id="imageUrl" type="url" name="imageUrl" placeholder="https://example.com/food.jpg">
                    </div>

                    <button class="btn" type="submit">Add restaurant</button>
                </form>
            </section>

            <section class="panel">
                <h2>Add menu item</h2>
                <p class="panel-copy">Attach new dishes to a restaurant menu.</p>

                <form class="form-grid" action="<%= request.getContextPath() %>/admin" method="post">
                    <input type="hidden" name="action" value="addMenuItem">

                    <div class="field">
                        <label for="restaurantId">Restaurant</label>
                        <select id="restaurantId" name="restaurantId" required>
                            <%
                            if (restaurants != null) {
                                for (Restaurant restaurant : restaurants) {
                            %>
                                    <option value="<%= restaurant.getId() %>"><%= restaurant.getName() %></option>
                            <%
                                }
                            }
                            %>
                        </select>
                    </div>

                    <div class="field">
                        <label for="itemName">Item name</label>
                        <input id="itemName" type="text" name="name" required>
                    </div>

                    <div class="field">
                        <label for="description">Description</label>
                        <textarea id="description" name="description"></textarea>
                    </div>

                    <div class="field">
                        <label for="price">Price</label>
                        <input id="price" type="number" name="price" step="0.01" min="0" required>
                    </div>

                    <div class="field">
                        <label for="itemType">Type</label>
                        <select id="itemType" name="type" required>
                            <option value="VEG">Veg</option>
                            <option value="NON_VEG">Non-Veg</option>
                        </select>
                    </div>

                    <button class="btn" type="submit">Add menu item</button>
                </form>
            </section>

            <section class="panel full">
                <h2>Orders</h2>
                <p class="panel-copy">Update customer order status.</p>

                <% if (orders != null && !orders.isEmpty()) { %>
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Customer</th>
                                    <th>Total</th>
                                    <th>Payment</th>
                                    <th>Address</th>
                                    <th>Status</th>
                                    <th>Update</th>
                                </tr>
                            </thead>
                            <tbody>
                            <%
                            for (Map<String, Object> order : orders) {
                                Object orderId = order.get("id");
                                String currentStatus = order.get("status") != null ? order.get("status").toString() : "Placed";
                            %>
                                <tr>
                                    <td>#<%= orderId %></td>
                                    <td><%= order.get("customerName") != null ? order.get("customerName") : "User " + order.get("userId") %></td>
                                    <td>&#8377;<%= order.get("totalAmount") %></td>
                                    <td><%= order.get("paymentStatus") != null ? order.get("paymentStatus") : "-" %></td>
                                    <td><%= order.get("address") != null ? order.get("address") : "-" %></td>
                                    <td><span class="status"><%= currentStatus %></span></td>
                                    <td>
                                        <form class="hero-actions" action="<%= request.getContextPath() %>/admin" method="post">
                                            <input type="hidden" name="action" value="updateOrderStatus">
                                            <input type="hidden" name="orderId" value="<%= orderId %>">
                                            <select class="input" name="status">
                                                <option value="Placed" <%= "Placed".equals(currentStatus) ? "selected" : "" %>>Placed</option>
                                                <option value="Preparing" <%= "Preparing".equals(currentStatus) ? "selected" : "" %>>Preparing</option>
                                                <option value="Out for Delivery" <%= "Out for Delivery".equals(currentStatus) ? "selected" : "" %>>Out for Delivery</option>
                                                <option value="Delivered" <%= "Delivered".equals(currentStatus) ? "selected" : "" %>>Delivered</option>
                                                <option value="Cancelled" <%= "Cancelled".equals(currentStatus) ? "selected" : "" %>>Cancelled</option>
                                            </select>
                                            <button class="btn" type="submit">Save</button>
                                        </form>
                                    </td>
                                </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                <% } else { %>
                    <div class="empty-state">No orders found.</div>
                <% } %>
            </section>

            <section class="panel full">
                <h2>Latest menu items</h2>
                <p class="panel-copy">Recently available dishes across restaurants.</p>

                <% if (menuItems != null && !menuItems.isEmpty()) { %>
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Item</th>
                                    <th>Restaurant</th>
                                    <th>Type</th>
                                    <th>Price</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% for (Map<String, Object> item : menuItems) { %>
                                <tr>
                                    <td>#<%= item.get("id") %></td>
                                    <td><%= item.get("name") %></td>
                                    <td><%= item.get("restaurantName") != null ? item.get("restaurantName") : "Restaurant " + item.get("restaurantId") %></td>
                                    <td><span class="badge"><%= item.get("type") %></span></td>
                                    <td>&#8377;<%= item.get("price") %></td>
                                </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                <% } else { %>
                    <div class="empty-state">No menu items found.</div>
                <% } %>
            </section>
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>

