package com.foodapp.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.foodapp.daoimpl.RestaurantDAOImpl;
import com.foodapp.model.Restaurant;
import com.foodapp.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private final RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        if (session.getAttribute("userId") == null) {
            resp.sendRedirect("login");
            return;
        }

        req.setAttribute("restaurants", restaurantDAO.getAllRestaurants());
        req.setAttribute("orders", getAllOrders());
        req.setAttribute("menuItems", getAllMenuItems());

        req.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        if (session.getAttribute("userId") == null) {
            resp.sendRedirect("login");
            return;
        }

        String action = req.getParameter("action");

        try {
            if ("addRestaurant".equals(action)) {
                addRestaurant(req);
                session.setAttribute("adminMessage", "Restaurant added successfully.");
            } else if ("addMenuItem".equals(action)) {
                addMenuItem(req);
                session.setAttribute("adminMessage", "Menu item added successfully.");
            } else if ("updateOrderStatus".equals(action)) {
                updateOrderStatus(req);
                session.setAttribute("adminMessage", "Order status updated.");
            } else {
                session.setAttribute("adminError", "Unknown admin action.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("adminError", "Admin action failed: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/admin");
    }

    private void addRestaurant(HttpServletRequest req) throws Exception {
        String sql = "INSERT INTO restaurants(name, cuisine, rating, delivery_time, image_url, type) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, req.getParameter("name"));
            ps.setString(2, req.getParameter("cuisine"));
            ps.setDouble(3, parseDouble(req.getParameter("rating"), 4.0));
            ps.setInt(4, parseInt(req.getParameter("deliveryTime"), 30));
            ps.setString(5, req.getParameter("imageUrl"));
            ps.setString(6, req.getParameter("type"));

            ps.executeUpdate();
        }
    }

    private void addMenuItem(HttpServletRequest req) throws Exception {
        String sql = "INSERT INTO menu_items(restaurant_id, name, description, price, type) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, parseInt(req.getParameter("restaurantId"), 0));
            ps.setString(2, req.getParameter("name"));
            ps.setString(3, req.getParameter("description"));
            ps.setDouble(4, parseDouble(req.getParameter("price"), 0));
            ps.setString(5, req.getParameter("type"));

            ps.executeUpdate();
        }
    }

    private void updateOrderStatus(HttpServletRequest req) throws Exception {
        String sql = "UPDATE orders SET status=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, req.getParameter("status"));
            ps.setInt(2, parseInt(req.getParameter("orderId"), 0));

            ps.executeUpdate();
        }
    }

    private List<Map<String, Object>> getAllOrders() {
        List<Map<String, Object>> orders = new ArrayList<>();

        String sql = "SELECT o.id, o.user_id, o.total_amount, o.status, o.payment_status, "
                + "o.address, o.order_date, u.name AS customer_name "
                + "FROM orders o LEFT JOIN users u ON o.user_id = u.id "
                + "ORDER BY o.id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> order = new HashMap<>();
                order.put("id", rs.getInt("id"));
                order.put("userId", rs.getInt("user_id"));
                order.put("customerName", rs.getString("customer_name"));
                order.put("totalAmount", rs.getDouble("total_amount"));
                order.put("status", rs.getString("status"));
                order.put("paymentStatus", rs.getString("payment_status"));
                order.put("address", rs.getString("address"));
                order.put("orderDate", rs.getTimestamp("order_date"));

                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    private List<Map<String, Object>> getAllMenuItems() {
        List<Map<String, Object>> items = new ArrayList<>();

        String sql = "SELECT m.id, m.restaurant_id, m.name, m.description, m.price, m.type, "
                + "r.name AS restaurant_name "
                + "FROM menu_items m LEFT JOIN restaurants r ON m.restaurant_id = r.id "
                + "ORDER BY m.id DESC LIMIT 20";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", rs.getInt("id"));
                item.put("restaurantId", rs.getInt("restaurant_id"));
                item.put("restaurantName", rs.getString("restaurant_name"));
                item.put("name", rs.getString("name"));
                item.put("description", rs.getString("description"));
                item.put("price", rs.getDouble("price"));
                item.put("type", rs.getString("type"));

                items.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    private int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return fallback;
        }
    }

    private double parseDouble(String value, double fallback) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return fallback;
        }
    }
}
