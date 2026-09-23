<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.foodapp.model.MenuItem" %>
<%@ page import="java.util.List" %>

<%!
private String unsplash(String photoId) {
    return "https://images.unsplash.com/" + photoId + "?auto=format&fit=crop&w=900&q=85";
}

private String menuItemImage(MenuItem item) {
    String name = item.getName() != null ? item.getName().toLowerCase() : "";
    String description = item.getDescription() != null ? item.getDescription().toLowerCase() : "";
    String key = name + " " + description;

    if (key.contains("pizza")) return unsplash("photo-1513104890138-7c749659a591");
    if (key.contains("burger")) return unsplash("photo-1568901346375-23c9450c58cd");
    if (key.contains("biryani") || key.contains("hyderabadi")) return unsplash("photo-1563379091339-03246963d51a");
    if (key.contains("dosa") || key.contains("idli") || key.contains("south indian")) return unsplash("photo-1668236543090-82eba5ee5976");
    if (key.contains("noodle") || key.contains("chinese") || key.contains("manchurian")) return unsplash("photo-1585032226651-759b368d7246");
    if (key.contains("fried rice") || key.contains("rice")) return unsplash("photo-1603133872878-684f208fb84b");
    if (key.contains("pasta")) return unsplash("photo-1621996346565-e3dbc646d9a9");
    if (key.contains("cake") || key.contains("dessert") || key.contains("donut") || key.contains("sweet")) return unsplash("photo-1551024601-bec78aea704b");
    if (key.contains("ice cream")) return unsplash("photo-1563805042-7684c019e1cb");
    if (key.contains("juice") || key.contains("shake") || key.contains("beverage") || key.contains("drink")) return unsplash("photo-1622597467836-f3285f2131b8");
    if (key.contains("salad") || key.contains("healthy")) return unsplash("photo-1512621776951-a57141f2eefd");
    if (key.contains("chicken")) return unsplash("photo-1598515214211-89d3c73ae83b");
    if (key.contains("bbq") || key.contains("grill") || key.contains("kebab")) return unsplash("photo-1529193591184-b1d58069ecdd");
    if (key.contains("paneer") || key.contains("curry") || key.contains("masala")) return unsplash("photo-1631452180519-c014fe946bc7");
    if (key.contains("fish") || key.contains("seafood") || key.contains("prawn")) return unsplash("photo-1565680018434-b513d5e5fd47");
    if (key.contains("sandwich") || key.contains("toast")) return unsplash("photo-1528735602780-2552fd46c7af");

    String[] fallbacks = {
        unsplash("photo-1546069901-ba9599a7e63c"),
        unsplash("photo-1540189549336-e6e99c3679fe"),
        unsplash("photo-1504674900247-0877df9cc836"),
        unsplash("photo-1498837167922-ddd27525d352")
    };

    return fallbacks[Math.abs(item.getItemId()) % fallbacks.length];
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BiteCurve | Menu</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/premium.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="app-page">
    <div class="page-shell">
        <div class="page-title">
            <div>
                <h1>Menu</h1>
                <p>Choose dishes, tune the filter, and add favorites straight to your cart.</p>
            </div>
            <a class="btn-secondary" href="<%= request.getContextPath() %>/cart">View cart</a>
        </div>

        <div class="controls">
            <label class="search-box" for="menuSearch">
                <span>Search</span>
                <input class="search-input" type="text" id="menuSearch" placeholder="Search food items" onkeyup="applyMenuFilters()">
            </label>

            <div class="filter" aria-label="Menu type filter">
                <button type="button" onclick="filterItems('ALL', this)" class="active">All</button>
                <button type="button" onclick="filterItems('VEG', this)">Veg</button>
                <button type="button" onclick="filterItems('NON_VEG', this)">Non-Veg</button>
            </div>
        </div>

        <div class="menu-grid">
            <%
            List<MenuItem> list = (List<MenuItem>) request.getAttribute("items");

            if (list != null && !list.isEmpty()) {
                for (MenuItem m : list) {
                    String type = m.getType() != null ? m.getType().trim().toUpperCase() : "";
                    String badgeClass = "VEG".equals(type) ? "veg" : ("NON_VEG".equals(type) ? "nonveg" : "");
                    String imageUrl = menuItemImage(m);
            %>

            <article class="menu-card" data-type="<%= type %>">
                <div class="menu-image">
                    <img src="<%= imageUrl %>" alt="<%= m.getName() %>" onerror="this.src='<%= unsplash("photo-1546069901-ba9599a7e63c") %>'">
                    <span class="type-chip"><%= "NON_VEG".equals(type) ? "Non-Veg" : "Veg" %></span>
                </div>

                <div class="card-body">
                    <div class="card-title-row">
                        <h3><%= m.getName() %></h3>
                        <span class="badge <%= badgeClass %>"><%= "NON_VEG".equals(type) ? "Non-Veg" : "Veg" %></span>
                    </div>

                    <p class="description"><%= m.getDescription() != null ? m.getDescription() : "Chef curated favorite." %></p>

                    <div class="menu-meta">
                        <span class="price">&#8377;<%= m.getPrice() %></span>
                        <button class="card-btn"
                            type="button"
                            data-id="<%= m.getItemId() %>"
                            data-name="<%= m.getName() %>"
                            data-price="<%= m.getPrice() %>"
                            onclick="handleAdd(this)">
                            Add to cart
                        </button>
                    </div>
                </div>
            </article>

            <%
                }
            } else {
            %>

            <div class="empty-state">No menu items available for this restaurant.</div>

            <%
            }
            %>
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

<script>
let currentMenuFilter = "ALL";

function filterItems(type, button) {
    currentMenuFilter = type;
    applyMenuFilters();

    document.querySelectorAll(".filter button").forEach(btn => btn.classList.remove("active"));
    if (button) button.classList.add("active");
}

function applyMenuFilters() {
    const input = document.getElementById("menuSearch");
    const searchText = input ? input.value.toLowerCase().trim() : "";
    const cards = document.querySelectorAll(".menu-card");

    cards.forEach(card => {
        const name = card.querySelector("h3").innerText.toLowerCase();
        const description = card.querySelector(".description").innerText.toLowerCase();
        const type = card.getAttribute("data-type");
        const matchesSearch = name.includes(searchText) || description.includes(searchText);
        const matchesFilter = currentMenuFilter === "ALL" || type === currentMenuFilter;

        card.style.display = (matchesSearch && matchesFilter) ? "block" : "none";
    });
}

function handleAdd(btn) {
    const id = btn.getAttribute("data-id");
    const name = btn.getAttribute("data-name");
    const price = btn.getAttribute("data-price");

    btn.disabled = true;
    btn.innerText = "Adding...";

    fetch("<%= request.getContextPath() %>/add-to-cart", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "menuItemId=" + encodeURIComponent(id) +
              "&name=" + encodeURIComponent(name) +
              "&price=" + encodeURIComponent(price)
    })
    .then(() => {
        if (typeof updateCartCount === "function") updateCartCount();
        btn.innerText = "Added";
        setTimeout(() => {
            btn.disabled = false;
            btn.innerText = "Add to cart";
        }, 900);
    })
    .catch(() => {
        btn.disabled = false;
        btn.innerText = "Try again";
    });
}
</script>
</body>
</html>

