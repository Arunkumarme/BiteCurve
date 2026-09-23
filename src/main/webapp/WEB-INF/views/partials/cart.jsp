<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.foodapp.model.CartItem" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BiteCurve | Cart</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/premium.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="app-page">
    <div class="page-shell">
        <div class="page-title">
            <div>
                <h1>Your cart</h1>
                <p>Review quantities, remove anything you changed your mind about, and checkout when ready.</p>
            </div>
            <a class="btn-secondary" href="<%= request.getContextPath() %>/home">Add more food</a>
        </div>

        <%
        List<CartItem> list = (List<CartItem>) session.getAttribute("cart");
        double total = 0;

        if (list != null && !list.isEmpty()) {
            for (CartItem c : list) {
                total += c.getPrice() * c.getQuantity();
            }
        %>

        <div class="two-column">
            <section class="cart-list" aria-label="Cart items">
                <%
                for (CartItem c : list) {
                    double itemTotal = c.getPrice() * c.getQuantity();
                %>

                <article class="cart-item" id="card-<%= c.getItemId() %>">
                    <div>
                        <h3><%= c.getName() %></h3>
                        <p class="muted">&#8377;<%= c.getPrice() %> each</p>
                    </div>

                    <div class="qty-control" aria-label="Quantity control">
                        <button class="qty-btn" type="button" onclick="updateQty(<%= c.getItemId() %>, -1)">-</button>
                        <strong id="qty-<%= c.getItemId() %>"><%= c.getQuantity() %></strong>
                        <button class="qty-btn" type="button" onclick="updateQty(<%= c.getItemId() %>, 1)">+</button>
                    </div>

                    <div>
                        <strong class="price" id="price-<%= c.getItemId() %>">&#8377;<%= itemTotal %></strong>
                        <br>
                        <button class="remove-btn" type="button" onclick="removeItem(<%= c.getItemId() %>)">Remove</button>
                    </div>
                </article>

                <%
                }
                %>
            </section>

            <aside class="panel summary-panel">
                <h2>Order summary</h2>
                <p class="panel-copy">Your cart is saved for this session.</p>

                <div class="summary-row">
                    <span>Subtotal</span>
                    <strong>&#8377;<span id="total"><%= total %></span></strong>
                </div>
                <div class="summary-row">
                    <span>Delivery</span>
                    <strong>Included</strong>
                </div>
                <div class="summary-row total">
                    <span>Total</span>
                    <strong>&#8377;<%= total %></strong>
                </div>

                <form action="<%=request.getContextPath()%>/checkout" method="get">
                    <button class="btn" type="submit">Continue to checkout</button>
                </form>
            </aside>
        </div>

        <%
        } else {
        %>

        <div class="empty-state">
            Your cart is empty. Explore restaurants and add something good.
            <div class="action-space">
                <a class="btn" href="<%= request.getContextPath() %>/home">Explore restaurants</a>
            </div>
        </div>

        <%
        }
        %>
    </div>
</main>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

<script>
function updateQty(id, change) {
    fetch("<%= request.getContextPath() %>/update-cart?itemId=" + id + "&change=" + change)
        .then(() => location.reload());
}

function removeItem(id) {
    fetch("<%= request.getContextPath() %>/remove-item?itemId=" + id)
        .then(() => location.reload());
}
</script>
</body>
</html>

