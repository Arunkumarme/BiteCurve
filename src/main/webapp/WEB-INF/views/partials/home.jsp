<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.foodapp.model.Restaurant" %>

<%!
private String unsplash(String photoId) {
    return "https://images.unsplash.com/" + photoId + "?auto=format&fit=crop&w=900&q=85";
}

private boolean hasImage(String value) {
    return value != null && !value.trim().isEmpty() && !"null".equalsIgnoreCase(value.trim());
}

private String restaurantImage(Restaurant restaurant) {
    String name = restaurant.getName() != null ? restaurant.getName().toLowerCase() : "";
    String cuisine = restaurant.getCuisine() != null ? restaurant.getCuisine().toLowerCase() : "";
    String key = name + " " + cuisine;

    if (key.contains("pizza") || key.contains("italian")) return unsplash("photo-1513104890138-7c749659a591");
    if (key.contains("burger") || key.contains("fast food")) return unsplash("photo-1568901346375-23c9450c58cd");
    if (key.contains("south") || key.contains("andhra") || key.contains("dosa")) return unsplash("photo-1668236543090-82eba5ee5976");
    if (key.contains("chinese") || key.contains("wok") || key.contains("noodle")) return unsplash("photo-1585032226651-759b368d7246");
    if (key.contains("dessert") || key.contains("cake") || key.contains("sweet")) return unsplash("photo-1551024601-bec78aea704b");
    if (key.contains("punjabi") || key.contains("north indian") || key.contains("dhaba")) return unsplash("photo-1631452180519-c014fe946bc7");
    if (key.contains("bbq") || key.contains("grill")) return unsplash("photo-1529193591184-b1d58069ecdd");
    if (key.contains("street")) return unsplash("photo-1601050690597-df0568f70950");
    if (key.contains("healthy") || key.contains("salad")) return unsplash("photo-1512621776951-a57141f2eefd");
    if (key.contains("juice") || key.contains("beverage")) return unsplash("photo-1622597467836-f3285f2131b8");
    if (key.contains("chicken")) return unsplash("photo-1598515214211-89d3c73ae83b");
    if (key.contains("meat") || key.contains("steak")) return unsplash("photo-1558030006-450675393462");
    if (key.contains("biryani") || key.contains("hyderabadi")) return unsplash("photo-1563379091339-03246963d51a");
    if (key.contains("kebab") || key.contains("mughlai")) return unsplash("photo-1599487488170-d11ec9c172f0");
    if (key.contains("seafood") || key.contains("fish")) return unsplash("photo-1565680018434-b513d5e5fd47");

    return hasImage(restaurant.getImageUrl())
            ? restaurant.getImageUrl()
            : unsplash("photo-1546069901-ba9599a7e63c");
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BiteCurve | Home</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/premium.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="app-page">
    <div class="page-shell">
        <section class="hero">
            <div class="hero-copy">
                <span class="eyebrow">Curated city kitchens</span>
                <h1>Restaurant food that feels first class.</h1>
                <p>Discover high-rated restaurants, quick delivery windows, and menus made for everything from comfort cravings to weekend dinners.</p>
                <div class="hero-actions">
                    <a class="btn" href="#restaurants">Explore restaurants</a>
                    <a class="btn-secondary" href="<%= request.getContextPath() %>/my-orders">Track my orders</a>
                </div>
            </div>

            <div class="hero-card" aria-label="Premium food presentation">
                <div class="hero-ticket">
                    <strong>Tonight's chef picks</strong>
                    <div class="ticket-row">
                        <span>Hot meals</span>
                        <span>Live cart</span>
                        <span>Fast checkout</span>
                    </div>
                </div>
            </div>
        </section>

        <section class="stats-strip" aria-label="Delivery highlights">
            <div class="stat">
                <strong>30m</strong>
                <span>Average delivery</span>
            </div>
            <div class="stat">
                <strong>4.8</strong>
                <span>Top partner rating</span>
            </div>
            <div class="stat">
                <strong>50%</strong>
                <span>First order offer</span>
            </div>
        </section>

        <section id="restaurants">
            <div class="page-title">
                <div>
                    <h1>Popular restaurants</h1>
                    <p>Browse the best kitchens available near you right now.</p>
                </div>
            </div>

            <div class="controls">
                <label class="search-box" for="searchInput">
                    <span>Search</span>
                    <input class="search-input" type="text" id="searchInput" placeholder="Search restaurants or cuisine" onkeyup="searchRestaurants()">
                </label>

                <div class="filter" aria-label="Restaurant type filter">
                    <button type="button" onclick="filterRestaurants('ALL', this)" class="active">All</button>
                    <button type="button" onclick="filterRestaurants('VEG', this)">Veg</button>
                    <button type="button" onclick="filterRestaurants('NON_VEG', this)">Non-Veg</button>
                </div>
            </div>

            <div class="restaurant-grid">
                <%
                List<Restaurant> list = (List<Restaurant>) request.getAttribute("restaurants");

                if (list != null && !list.isEmpty()) {
                    for (Restaurant r : list) {
                        String type = r.getType() != null ? r.getType().trim().toUpperCase() : "";
                        String cuisine = r.getCuisine() != null ? r.getCuisine() : "Chef specials";
                        String imageUrl = restaurantImage(r);
                %>

                <article class="restaurant-card" data-type="<%= type %>">
                    <div class="image-box">
                        <img src="<%= imageUrl %>" alt="<%= r.getName() %>" onerror="this.src='<%= unsplash("photo-1546069901-ba9599a7e63c") %>'">
                        <span class="type-chip"><%= type.equals("NON_VEG") ? "Non-Veg" : type.equals("VEG") ? "Veg" : "Featured" %></span>
                        <span class="time-chip"><%= r.getDeliveryTime() > 0 ? r.getDeliveryTime() : 30 %> min</span>
                    </div>

                    <div class="card-body">
                        <div class="card-title-row">
                            <h3><%= r.getName() %></h3>
                            <span class="rating"><%= r.getRating() %></span>
                        </div>

                        <p class="cuisine"><%= cuisine %></p>

                        <div class="card-meta">
                            <span class="price-note">Premium menu</span>
                            <a class="card-btn" href="<%= request.getContextPath() %>/menu?restaurantId=<%= r.getId() %>">View menu</a>
                        </div>
                    </div>
                </article>

                <%
                    }
                } else {
                %>

                <div class="empty-state">No restaurants available right now.</div>

                <%
                }
                %>
            </div>
        </section>
    </div>
</main>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

<script>
let currentFilter = "ALL";

function filterRestaurants(type, button) {
    currentFilter = type;
    applyFilters();

    document.querySelectorAll(".filter button").forEach(btn => btn.classList.remove("active"));
    if (button) button.classList.add("active");
}

function searchRestaurants() {
    applyFilters();
}

function applyFilters() {
    const searchInput = document.getElementById("searchInput");
    const searchText = searchInput ? searchInput.value.toLowerCase().trim() : "";
    const cards = document.querySelectorAll(".restaurant-card");

    cards.forEach(card => {
        const name = card.querySelector("h3").innerText.toLowerCase();
        const cuisine = card.querySelector(".cuisine").innerText.toLowerCase();
        const type = card.getAttribute("data-type");
        const matchesSearch = name.includes(searchText) || cuisine.includes(searchText);
        const matchesFilter = currentFilter === "ALL" || type === currentFilter || type === "BOTH";

        card.style.display = (matchesSearch && matchesFilter) ? "block" : "none";
    });
}
</script>
</body>
</html>

