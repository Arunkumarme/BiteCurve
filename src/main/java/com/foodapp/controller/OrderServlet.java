package com.foodapp.controller;

import com.foodapp.model.Order;
import com.foodapp.util.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/my-orders")
public class OrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect("login");
            return;
        }

        List<Order> orders = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM orders WHERE user_id=? ORDER BY id DESC"
            );

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();

                int orderId = rs.getInt("id");

                o.setOrderId(orderId);
                o.setUserId(rs.getInt("user_id"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setOrderTime(rs.getTimestamp("order_date"));
                loadReview(con, o, orderId, userId);

                // 🔥 CREATE ITEM LIST
                List<String> items = new ArrayList<>();

                PreparedStatement ps2 = con.prepareStatement(
                    "SELECT menu_item_id, quantity FROM order_items WHERE order_id=?"
                );
                ps2.setInt(1, orderId);

                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    int itemId = rs2.getInt("menu_item_id");
                    int qty = rs2.getInt("quantity");

                    PreparedStatement ps3 = con.prepareStatement(
                        "SELECT name FROM menu_items WHERE id=?"
                    );
                    ps3.setInt(1, itemId);

                    ResultSet rs3 = ps3.executeQuery();

                    if (rs3.next()) {
                        String name = rs3.getString("name");
                        items.add(name + " x" + qty);
                    }
                    System.out.println("ITEM COUNT = " + items.size());

                    rs3.close();
                    ps3.close();
                }

                rs2.close();
                ps2.close();

                o.setItems(items);

                orders.add(o);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/orders.jsp")
           .forward(req, resp);
    }

    private void loadReview(Connection con, Order order, int orderId, int userId) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT rating, review_text FROM reviews WHERE order_id=? AND user_id=?"
            );

            ps.setInt(1, orderId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                order.setReviewed(true);
                order.setReviewRating(rs.getInt("rating"));
                order.setReviewText(rs.getString("review_text"));
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
  
