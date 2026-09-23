package com.foodapp.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import com.foodapp.model.CartItem;
import com.foodapp.util.DBConnection;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            HttpSession session = req.getSession();

            Integer userIdObj = (Integer) session.getAttribute("userId");

            if (userIdObj == null) {
                resp.sendRedirect("login");
                return;
            }

            int userId = userIdObj;
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

            String address = req.getParameter("address");
            String payment = req.getParameter("payment");

            double total = 0;
            for (CartItem c : cart) {
                total += c.getPrice() * c.getQuantity();
            }

            Connection con = DBConnection.getConnection();

            // ✅ INSERT INTO orders
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO orders(user_id, total_amount, status, payment_status, address) VALUES (?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS
            );

            ps.setInt(1, userId);
            ps.setDouble(2, total);
            ps.setString(3, "Placed");
            ps.setString(4, payment);
            ps.setString(5, address);

            ps.executeUpdate();

            // get order id
            ResultSet rs = ps.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // ✅ INSERT INTO order_items
            PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO order_items(order_id, menu_item_id, quantity, price) VALUES (?,?,?,?)"
            );

            for (CartItem c : cart) {
            	System.out.println("ITEM ID = " + c.getItemId());
                ps2.setInt(1, orderId);
                ps2.setInt(2, c.getItemId());   // ✅ correct
                ps2.setInt(3, c.getQuantity());
                ps2.setDouble(4, c.getPrice());
                ps2.executeUpdate();
            }

            // clear cart
            session.removeAttribute("cart");

            // redirect
         // clear cart
            session.removeAttribute("cart");

            // redirect (CORRECT)
            resp.sendRedirect(req.getContextPath() + "/order-success.jsp?payment=" + payment);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/plain");
            resp.getWriter().print("ERROR: " + e.getMessage());

        }
        }
    }
