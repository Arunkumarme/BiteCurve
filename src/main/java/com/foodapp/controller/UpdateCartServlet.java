package com.foodapp.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import com.foodapp.model.CartItem;

@WebServlet("/update-cart")
public class UpdateCartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        int itemId = Integer.parseInt(request.getParameter("itemId"));
        int change = Integer.parseInt(request.getParameter("change"));

        if (cart != null) {
            for (CartItem item : cart) {

                if (item.getItemId() == itemId) {
                    int newQty = item.getQuantity() + change;

                    if (newQty > 0) {
                        item.setQuantity(newQty);
                    }

                    break;
                }
            }
        }

        response.setStatus(200);
    }
}