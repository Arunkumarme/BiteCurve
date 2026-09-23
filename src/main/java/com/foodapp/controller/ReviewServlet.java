package com.foodapp.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.foodapp.util.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/submit-review")
public class ReviewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect("login");
            return;
        }

        int orderId = parseInt(req.getParameter("orderId"), 0);
        int rating = parseInt(req.getParameter("rating"), 0);
        String reviewText = req.getParameter("reviewText");

        if (orderId <= 0 || rating < 1 || rating > 5 || reviewText == null || reviewText.trim().isEmpty()) {
            session.setAttribute("reviewError", "Please give a rating from 1 to 5 and write your review.");
            resp.sendRedirect(req.getContextPath() + "/my-orders");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            ensureReviewsTable(con);

            if (!isDeliveredOrderOwnedByUser(con, orderId, userId)) {
                session.setAttribute("reviewError", "Review is allowed only after your order is delivered.");
                resp.sendRedirect(req.getContextPath() + "/my-orders");
                return;
            }

            if (reviewExists(con, orderId, userId)) {
                session.setAttribute("reviewError", "You have already reviewed this order.");
                resp.sendRedirect(req.getContextPath() + "/my-orders");
                return;
            }

            String sql = "INSERT INTO reviews(order_id, user_id, rating, review_text) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                ps.setInt(2, userId);
                ps.setInt(3, rating);
                ps.setString(4, reviewText.trim());
                ps.executeUpdate();
            }

            session.setAttribute("reviewMessage", "Thank you. Your review has been saved.");
            resp.sendRedirect(req.getContextPath() + "/my-orders");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("reviewError", "Unable to save review. Please check the reviews table.");
            resp.sendRedirect(req.getContextPath() + "/my-orders");
        }
    }

    private void ensureReviewsTable(Connection con) throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS reviews ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "order_id INT NOT NULL, "
                + "user_id INT NOT NULL, "
                + "rating INT NOT NULL, "
                + "review_text TEXT NOT NULL, "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + "UNIQUE KEY unique_order_review (order_id, user_id)"
                + ")";

        try (Statement statement = con.createStatement()) {
            statement.execute(sql);
        }
    }

    private boolean isDeliveredOrderOwnedByUser(Connection con, int orderId, int userId) throws Exception {
        String sql = "SELECT status FROM orders WHERE id=? AND user_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && "Delivered".equalsIgnoreCase(rs.getString("status"));
            }
        }
    }

    private boolean reviewExists(Connection con, int orderId, int userId) throws Exception {
        String sql = "SELECT id FROM reviews WHERE order_id=? AND user_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return fallback;
        }
    }
}
