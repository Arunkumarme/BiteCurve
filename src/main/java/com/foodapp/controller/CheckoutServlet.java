package com.foodapp.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.foodapp.model.CartItem;
import com.foodapp.model.Order;
import com.foodapp.dao.OrderDAO;
import com.foodapp.daoimpl.OrderDAOImpl;
import com.foodapp.util.DBConnection;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    // ✅ LOAD CHECKOUT PAGE
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    HttpSession session = req.getSession();
	    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

	    if (cart == null || cart.isEmpty()) {
	        resp.sendRedirect("home");
	        return;
	    }

	    double total = 0;

	    for (CartItem c : cart) {
	        total += c.getPrice() * c.getQuantity();
	    }

	    req.setAttribute("total", total);
	    setDefaultAddress(req, session);

	    RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/checkout.jsp");
	    rd.forward(req, resp);
	}
    // ✅ PLACE ORDER
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    HttpSession session = req.getSession();
	    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

	    if (cart == null || cart.isEmpty()) {
	        resp.sendRedirect("home");
	        return;
	    }

	    double total = 0;

	    for (CartItem c : cart) {
	        total += c.getPrice() * c.getQuantity();
	    }

	    Order order = new Order();

	    Integer userId = (Integer) session.getAttribute("userId");
	    order.setUserId(userId != null ? userId : 1);

	    order.setTotalAmount(total);
	    order.setStatus("PLACED");

	    OrderDAO dao = new OrderDAOImpl();
	    dao.placeOrder(order);

	    // ❗ DO NOT CLEAR CART HERE (this was your bug)
	    // session.removeAttribute("cart");

	    req.setAttribute("total", total);
	    req.setAttribute("message", "Order Placed Successfully 🎉");

	    RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/checkout.jsp");
	    rd.forward(req, resp);
	}

    private void setDefaultAddress(HttpServletRequest req, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return;
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT address FROM users WHERE id=?")) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    req.setAttribute("defaultAddress", rs.getString("address"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
