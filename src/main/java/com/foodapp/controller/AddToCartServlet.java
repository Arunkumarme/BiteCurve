package com.foodapp.controller;

import com.foodapp.model.CartItem;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int id = Integer.parseInt(request.getParameter("menuItemId"));

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        boolean itemExists = false;

        // ✅ CHECK EXISTING
        for (CartItem item : cart) {
            if (item.getItemId() == id) {
                item.setQuantity(item.getQuantity() + 1);
                itemExists = true;
                break;
            }
        }

        // ✅ ADD NEW
        if (!itemExists) {
            CartItem newItem = new CartItem();
            newItem.setItemId(id);
            newItem.setName(name);
            newItem.setPrice(price);
            newItem.setQuantity(1);

            cart.add(newItem);
        }

        session.setAttribute("cart", cart);

        response.getWriter().print("added");
    }
}