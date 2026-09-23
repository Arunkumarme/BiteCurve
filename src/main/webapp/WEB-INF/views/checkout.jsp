<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BiteCurve | Checkout</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/premium.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="app-page">
    <div class="page-shell">
        <div class="page-title">
            <div>
                <h1>Checkout</h1>
                <p>Confirm your address and payment method before placing the order.</p>
            </div>
        </div>

        <%
            String defaultAddress = request.getAttribute("defaultAddress") != null
                    ? request.getAttribute("defaultAddress").toString()
                    : "";
        %>

        <div class="two-column">
            <section class="panel">
                <h2>Delivery details</h2>
                <p class="panel-copy">We will use this address for the current order.</p>

                <form class="form-grid" action="<%=request.getContextPath()%>/place-order" method="post">
                    <div class="field">
                        <label for="address">Delivery address</label>
                        <textarea id="address" name="address" placeholder="Enter delivery address" required><%= defaultAddress %></textarea>
                    </div>

                    <div>
                        <h3>Payment method</h3>
                        <div class="payment-options">
                            <label class="payment-option">
                                <input type="radio" name="payment" value="COD" checked>
                                Cash on delivery
                            </label>

                            <label class="payment-option">
                                <input type="radio" name="payment" value="UPI">
                                UPI
                            </label>

                            <label class="payment-option">
                                <input type="radio" name="payment" value="CARD">
                                Card
                            </label>
                        </div>
                    </div>

                    <button class="btn" type="submit">Place order</button>
                </form>
            </section>

            <aside class="panel summary-panel">
                <h2>Payment summary</h2>
                <p class="panel-copy">Final amount from your cart.</p>

                <div class="summary-row total">
                    <span>Total</span>
                    <strong>&#8377;${total}</strong>
                </div>

                <a class="btn-secondary" href="<%= request.getContextPath() %>/cart">Back to cart</a>
            </aside>
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>

