<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BiteCurve | Order Success</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/premium.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="success-page">
    <section class="panel success-card">
        <div class="success-mark">OK</div>
        <h1>Order placed successfully</h1>

        <%
        String payment = request.getParameter("payment");
        %>

        <p class="panel-copy">Payment method: <strong><%= payment != null ? payment : "Selected at checkout" %></strong></p>
        <p class="message success">Payment successful. Your order is now in progress.</p>

        <div class="hero-actions">
            <a class="btn" href="<%= request.getContextPath() %>/home">Back to home</a>
            <a class="btn-secondary" href="<%= request.getContextPath() %>/my-orders">View orders</a>
        </div>
    </section>
</main>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>

